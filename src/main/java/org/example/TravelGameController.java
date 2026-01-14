package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.example.service.JourneyService;
import org.example.service.PlayerEventService;

import java.util.ArrayList;
import java.util.List;

public class TravelGameController {

    @FXML private DialogPane winConLabel;
    @FXML private ImageView mapView;
    @FXML private StackPane drawingPane;
    @FXML private Pane gridLayer;
    @FXML private Pane markerLayer;
    @FXML private Pane playerLayer;
    @FXML private ListView<String> logList;
    @FXML private Button rollButton;
    @FXML private VBox movesBox;
    @FXML private StackPane mapContainer;



    @FXML private Label currentPlayerLabel;
    @FXML private Label nextPlayerLabel;
    @FXML private Label lastRollLabel;
    @FXML private Label currentCreditsLabel;
    @FXML private Label currentTurnLabel;
    @FXML private Label currentLocationLabel;
    @FXML private Label destinationLabel;
    @FXML private Label currentPointLabel;

    private MapVisualizer visualizer;
    private JourneyService journeyService;
    private PlayerEventService eventService;

    private PossibleMoves selectedMove = null;   // <-- spelarens val
    private boolean awaitingMoveChoice = false;  // <-- om vi väntar på att spelaren väljer

    private static final int GRID_SIZE = 50;

    // JPA
    private EntityManagerFactory emf;
    private EntityManager em;

    // Game state
    private final List<Traveler> players = new ArrayList<>();
    private int currentPlayerIndex = 0;
    private boolean wonGame = false;


    private List<Transport> transports = new ArrayList<>();

    @FXML
    private void initialize() {
        visualizer = new MapVisualizer(gridLayer, markerLayer, playerLayer);


        mapView.setImage(new Image(getClass().getResourceAsStream("/assets/map.png")));

        // viktiga små grejer för layout
        mapContainer.setMinSize(0, 0);
        mapContainer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        drawingPane.setMouseTransparent(false);

        // bind EFTER att scenen/layouten är klar → undviker pref-size loop
        Platform.runLater(() -> {
            mapView.fitWidthProperty().bind(mapContainer.widthProperty());
            mapView.fitHeightProperty().bind(mapContainer.heightProperty());

            drawingPane.prefWidthProperty().bind(mapContainer.widthProperty());
            drawingPane.prefHeightProperty().bind(mapContainer.heightProperty());

            updateGraphics();
        });

        logList.getItems().add("🌍 spelet startade. tryck roll.");
    }


    public void setupGame(String playerName) {
        GameConfig.MODE = GameMode.GUI;

        emf = Persistence.createEntityManagerFactory("jpa-hibernate-mysql");
        em = emf.createEntityManager();

        // ✅ bootstrap här (gui-entré) så du slipper App.main
        new org.example.service.BootstrapService(em).initialize();

        eventService = new PlayerEventService();
        eventService.setGuiLog(logList);

        journeyService = new JourneyService(em, eventService);

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            // ✅ välj startplatser som du vet har länkar i seed
            Location stockholm = getLocationByName("Stockholm");
            Location berlin = getLocationByName("Berlin");
            Location paris = getLocationByName("Paris");

            Traveler p1 = new Traveler(playerName, stockholm);
            Traveler p2 = new Traveler("Player 2", berlin);

            // (valfritt) om du fortfarande visar destinationLabel i hud:
            // sätt en "visuell destination" som är en riktig location, inte fri klick
            p1.setDestinationPos(paris.getX(), paris.getY());
            p2.setDestinationPos(stockholm.getX(), stockholm.getY());

            em.persist(p1);
            em.persist(p2);

            players.clear();
            players.add(p1);
            players.add(p2);

            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }

        logList.getItems().add("✅ " + players.size() + " spelare skapade (start = stockholm/berlin).");

        // rensa eventuella gamla val
        movesBox.getChildren().clear();
        syncHudAndMap();
    }

    @FXML
    public void onRoll(ActionEvent actionEvent) {
        if (wonGame || players.isEmpty()) return;

        // alltid börja med "fräsch" spelare från db
        Traveler currentRef = players.get(currentPlayerIndex);
        Traveler current = em.find(Traveler.class, currentRef.getId());
        if (current == null) return;

        // om spelaren är mitt i en resa: fortsätt direkt (ingen move-lista)
        if (current.isTravelling()) {
            // ✅ reset UI-state så du inte fastnar i "confirm move" / gammalt val
            awaitingMoveChoice = false;
            selectedMove = null;
            rollButton.setText("ROLL");
            movesBox.getChildren().clear();

            doContinueJourney(current.getId());
            return;
        }

        // om vi INTE väntar på val -> visa listan
        if (!awaitingMoveChoice) {
            selectedMove = null;
            movesBox.getChildren().clear();

            Location currentLocation = current.getCurrentLocation();
            if (currentLocation == null) {
                logList.getItems().add("❌ ingen aktuell plats");
                return;
            }

            List<PossibleMoves> moves = journeyService.findPossibleMoves(currentLocation);
            if (moves.isEmpty()) {
                logList.getItems().add("⛔ inga möjliga resor från " + currentLocation.getName());
                return;
            }

            // skapa knappar för alla moves
            for (PossibleMoves m : moves) {
                String text =
                    m.getFrom().getName() + " -> " + m.getTo().getName()
                        + " | " + m.getTransport().getType()
                        + " | dist=" + m.getDistance()
                        + " | cost=" + m.getTransport().getCostPerMove();

                Button b = new Button(text);
                b.setMaxWidth(Double.MAX_VALUE);

                b.setOnAction(e -> {
                    // ✅ logga bara om valet faktiskt ändras (stoppar spam)
                    if (selectedMove == m) return;

                    selectedMove = m;
                    highlightSelectedMoveButton(b);

                    logList.getItems().add(
                        "✅ valt: " + m.getFrom().getName() + " -> " + m.getTo().getName()
                            + " (" + m.getTransport().getType() + ")"
                    );
                });

                movesBox.getChildren().add(b);
            }

            updateGraphicsWithMoves(current, moves);

            awaitingMoveChoice = true;
            rollButton.setText("CONFIRM MOVE");
            logList.getItems().add("👉 välj en resa till höger och tryck confirm move");
            return;
        }

        // vi väntar på confirm -> kör vald resa
        if (selectedMove == null) {
            logList.getItems().add("⚠️ välj en resa först");
            return;
        }

        doMove(current.getId(), selectedMove);

        // reset för nästa tur
        awaitingMoveChoice = false;
        selectedMove = null;
        rollButton.setText("ROLL");
        movesBox.getChildren().clear();
    }

    @FXML
    private void onMapClicked(MouseEvent event) {
        // ✅ stäng av fri destination för databas-logik
        logList.getItems().add("ℹ️ destination väljs via möjliga resor (högerpanelen), inte kartklick.");
    }

    private void syncHudAndMap() {
        updateHud();
        updateGraphics();
    }

    private void updateHud() {
        if (players.isEmpty()) return;

        // ✅ bygg hud från managed entities, inte från listans gamla instanser
        Traveler currentRef = players.get(currentPlayerIndex);
        Traveler current = em.find(Traveler.class, currentRef.getId());
        if (current == null) return;

        currentPlayerLabel.setText(current.getPlayerName());

        int nextIndex = (currentPlayerIndex + 1) % players.size();
        Traveler nextRef = players.get(nextIndex);
        Traveler next = em.find(Traveler.class, nextRef.getId());
        nextPlayerLabel.setText(next != null ? next.getPlayerName() : nextRef.getPlayerName());

        currentCreditsLabel.setText(current.getMoney() != null ? current.getMoney().toPlainString() : "-");
        currentTurnLabel.setText(String.valueOf(current.getTurnCount()));
        currentPointLabel.setText(String.valueOf(current.getPlayerScore()));

        currentLocationLabel.setText("[" + clampToGrid(current.getPlayerPosX()) + "," + clampToGrid(current.getPlayerPosY()) + "]");

        // ✅ visa destination som targetLocation om spelaren är mitt i en resa
        if (current.isTravelling() && current.getTargetLocation() != null) {
            destinationLabel.setText("[" + current.getTargetLocation().getX() + "," + current.getTargetLocation().getY() + "]");
        } else {
            destinationLabel.setText("-");
        }
    }

    private void updateGraphics() {
        double w = drawingPane.getWidth();
        double h = drawingPane.getHeight();
        if (w <= 0 || h <= 0) return;

        visualizer.drawGrid(w, h);
        visualizer.clearMarkers(); // <- viktig: rensa markers när vi “basritar”

        List<int[]> positions = new ArrayList<>();
        for (Traveler t : players) {
            positions.add(new int[]{ clampToGrid(t.getPlayerPosX()), clampToGrid(t.getPlayerPosY()) });
        }
        visualizer.drawPlayers(positions, currentPlayerIndex, w, h);
    }

    private void updateGraphicsWithMoves(Traveler managed, List<PossibleMoves> moves) {
        updateGraphics(); // ritar grid + players + rensar markers

        double w = drawingPane.getWidth();
        double h = drawingPane.getHeight();

        List<Location> destinations = moves.stream()
            .map(PossibleMoves::getTo)
            .distinct()
            .toList();

        visualizer.drawDestMarkers(destinations, w, h); // <- markers syns nu stabilt
    }




    private int clampToGrid(int v) {
        return Math.max(0, Math.min(GRID_SIZE - 1, v));
    }

    public void startMockJourney() {
        logList.getItems().add("📜 Demo path!");
        if (players.isEmpty()) return;

        Traveler current = players.get(currentPlayerIndex);
        int px = clampToGrid(current.getPlayerPosX());
        int py = clampToGrid(current.getPlayerPosY());

        List<int[]> journeyPath = new ArrayList<>();
        journeyPath.add(new int[]{px, py});
        journeyPath.add(new int[]{10, 15});
        journeyPath.add(new int[]{25, 20});
        journeyPath.add(new int[]{40, 45});
        visualizer.animateJourney(journeyPath, drawingPane.getWidth(), drawingPane.getHeight());
    }

    private void doMove(Long travelerId, PossibleMoves chosen) {
        if (wonGame) return;

        rollButton.setDisable(true);

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Traveler managed = em.find(Traveler.class, travelerId);

            // ✅ mål från valet (det här är stabilt och alltid rätt)
            String targetName = chosen.getTo().getName();

            Journey journey = journeyService.startNewJourneyTurn(managed, chosen);
            lastRollLabel.setText(String.valueOf(journey.getDistanceMoved()));

            if (managed.isTravelling()) {
                logList.getItems().add(
                    "🚀 " + safeName(managed)
                        + " reser " + chosen.getFrom().getName()
                        + " -> " + targetName
                        + " med " + chosen.getTransport().getType()
                        + " (rolled=" + journey.getDistanceMoved()
                        + ", remaining=" + journey.getRemainingDistance() + ")"
                );
            } else {
                logList.getItems().add(
                    "✅ " + safeName(managed)
                        + " kom fram till " + targetName
                        + " (rolled=" + journey.getDistanceMoved() + ")"
                );
            }

            tx.commit();
            players.set(currentPlayerIndex, managed);
            doesPlayerWin();
            if (!wonGame) {
                currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
            }
            syncHudAndMap();

        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            rollButton.setDisable(false);
        }
    }

    private void doesPlayerWin() {
        for (Traveler player : players) {
            if (player.checkScore()) {
                wonGame = true;
                System.out.println(player.getPlayerName() + " Wins the game. Congratulations");
                winConLabel.setContentText(player.getPlayerName() + " Wins the game. Congratulations");
            }
        }
    }


    private List<Location> distinctLocationsById(List<Location> locations) {
        java.util.Map<Long, Location> byId = new java.util.LinkedHashMap<>();
        for (Location l : locations) {
            if (l != null && l.getId() != null) {
                byId.putIfAbsent(l.getId(), l);
            }
        }
        return new java.util.ArrayList<>(byId.values());
    }


    private void highlightSelectedMoveButton(Button selected) {
        for (var node : movesBox.getChildren()) {
            if (node instanceof Button b) b.setStyle("");
        }
        selected.setStyle("-fx-border-color: white; -fx-border-width: 2; -fx-font-weight: bold;");
    }

    private Location getLocationByName(String name) {
        return em.createQuery("select l from Location l where l.name = :n", Location.class)
            .setParameter("n", name)
            .getSingleResult();
    }

    private String managedMoneyAsInt(Traveler t) {
        try {
            return t.getMoney().toBigInteger().toString(); // eller t.getMoney().toPlainString()
        } catch (Exception e) {
            return "-";
        }
    }

    private void doContinueJourney(Long travelerId) {
        if (wonGame) return;

        rollButton.setDisable(true);

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Traveler managed = em.find(Traveler.class, travelerId);

            // ✅ spara innan turnen (innan advance kan nolla targetLocation)
            Location targetBefore = managed.getTargetLocation();
            String targetName = (targetBefore != null) ? targetBefore.getName() : "?";

            Journey journey = journeyService.continueCurrentJourneyTurn(managed);
            lastRollLabel.setText(String.valueOf(journey.getDistanceMoved()));

            if (managed.isTravelling()) {
                logList.getItems().add(
                    "➡ " + safeName(managed)
                        + " fortsätter mot " + targetName
                        + " (rolled=" + journey.getDistanceMoved()
                        + ", remaining=" + journey.getRemainingDistance() + ")"
                );
            } else {
                logList.getItems().add("✅ " + safeName(managed) + " kom fram till " + targetName + "!");
            }

            tx.commit();
            players.set(currentPlayerIndex, managed);

            if (!managed.isTravelling() && !wonGame) {
                currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
            }

            syncHudAndMap();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            rollButton.setDisable(false);
        }
    }

    private String safeName(Traveler t) {
        if (t == null) return "?";
        if (t.getPlayerName() != null) return t.getPlayerName();
        return "?";
    }

    public void shutdown() {
        try {
            if (em != null && em.isOpen()) em.close();
        } catch (Exception ignored) {}
        try {
            if (emf != null && emf.isOpen()) emf.close();
        } catch (Exception ignored) {}
    }
}
