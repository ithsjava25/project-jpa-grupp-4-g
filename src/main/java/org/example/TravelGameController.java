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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TravelGameController {

    @FXML private DialogPane winConLabel;
    @FXML private ImageView mapView;
    @FXML private StackPane drawingPane;
    @FXML private Pane gridLayer;
    @FXML private Pane markerLayer;
    @FXML private Pane pathLayer;
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

    private PossibleMoves selectedMove = null;
    private boolean awaitingMoveChoice = false;

    private List<PossibleMoves> shownMoves = List.of();
    private final Map<PossibleMoves, Button> moveButtons = new HashMap<>();

    private Location lastClickedDestination = null;
    private int cycleIndex = 0;

    private static final int GRID_SIZE = 50;

    private EntityManagerFactory emf;
    private EntityManager em;

    private final List<Traveler> players = new ArrayList<>();
    private int currentPlayerIndex = 0;
    private boolean wonGame = false;

    private List<Transport> transports = new ArrayList<>();

    @FXML
    private void initialize() {
        visualizer = new MapVisualizer(gridLayer, markerLayer, pathLayer, playerLayer);

        mapView.setImage(new Image(getClass().getResourceAsStream("/assets/map.png")));

        mapContainer.setMinSize(0, 0);
        mapContainer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        drawingPane.setMouseTransparent(false);

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

        new org.example.service.BootstrapService(em).initialize();

        eventService = new PlayerEventService();
        eventService.setGuiLog(logList);

        journeyService = new JourneyService(em, eventService);

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Location stockholm = getLocationByName("Stockholm");
            Location berlin = getLocationByName("Berlin");
            Location paris = getLocationByName("Paris");

            Traveler p1 = new Traveler(playerName, stockholm);
            Traveler p2 = new Traveler("Player 2", berlin);

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

        movesBox.getChildren().clear();
        syncHudAndMap();
    }

    @FXML
    public void onRoll(ActionEvent actionEvent) {
        if (wonGame || players.isEmpty()) return;

        Traveler currentRef = players.get(currentPlayerIndex);
        Traveler current = em.find(Traveler.class, currentRef.getId());
        if (current == null) return;

        if (current.isTravelling()) {
            awaitingMoveChoice = false;
            selectedMove = null;
            rollButton.setText("ROLL");
            movesBox.getChildren().clear();
            shownMoves = List.of();
            moveButtons.clear();
            lastClickedDestination = null;
            cycleIndex = 0;

            doContinueJourney(current.getId());
            return;
        }

        if (!awaitingMoveChoice) {
            selectedMove = null;
            movesBox.getChildren().clear();
            moveButtons.clear();
            lastClickedDestination = null;
            cycleIndex = 0;

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

            shownMoves = moves;

            for (PossibleMoves m : moves) {
                String text =
                    m.getFrom().getName() + " -> " + m.getTo().getName()
                        + " | " + m.getTransport().getType()
                        + " | dist=" + m.getDistance()
                        + " | cost=" + m.getTransport().getCostPerMove();

                Button b = new Button(text);
                b.setMaxWidth(Double.MAX_VALUE);

                b.setOnAction(e -> {
                    if (selectedMove == m) return;

                    selectedMove = m;
                    highlightSelectedMoveButton(b);

                    Location dest = m.getTo();
                    if (dest != null) {
                        lastClickedDestination = dest;
                        cycleIndex = 0;
                    }

                    logList.getItems().add(
                        "✅ valt: " + m.getFrom().getName() + " -> " + m.getTo().getName()
                            + " (" + m.getTransport().getType() + ")"
                    );
                });

                movesBox.getChildren().add(b);
                moveButtons.put(m, b);
            }

            updateGraphicsWithMoves(current, moves);

            awaitingMoveChoice = true;
            rollButton.setText("CONFIRM MOVE");
            logList.getItems().add("👉 välj en resa till höger eller klicka på kartan och tryck confirm move");
            return;
        }

        if (selectedMove == null) {
            logList.getItems().add("⚠️ välj en resa först");
            return;
        }

        doMove(current.getId(), selectedMove);

        awaitingMoveChoice = false;
        selectedMove = null;
        rollButton.setText("ROLL");
        movesBox.getChildren().clear();
        shownMoves = List.of();
        moveButtons.clear();
        lastClickedDestination = null;
        cycleIndex = 0;
    }

    @FXML
    private void onMapClicked(MouseEvent event) {
        if (wonGame || players.isEmpty()) return;

        if (!awaitingMoveChoice || shownMoves == null || shownMoves.isEmpty()) {
            logList.getItems().add("ℹ️ tryck roll först för att visa möjliga resor.");
            return;
        }

        double w = mapView.getBoundsInLocal().getWidth();
        double h = mapView.getBoundsInLocal().getHeight();
        if (w <= 0 || h <= 0) return;

        int gx = screenToGridX(event.getX(), w);
        int gy = screenToGridY(event.getY(), h);

        Location nearestDest = null;
        double best = Double.MAX_VALUE;

        for (PossibleMoves m : shownMoves) {
            Location to = m.getTo();
            int tx = clampToGrid(to.getX());
            int ty = clampToGrid(to.getY());

            double dx = tx - gx;
            double dy = ty - gy;
            double d2 = dx * dx + dy * dy;

            if (d2 < best) {
                best = d2;
                nearestDest = to;
            }
        }

        if (nearestDest == null) return;

        List<PossibleMoves> options = movesTo(nearestDest);
        if (options.isEmpty()) return;

        if (lastClickedDestination != null
            && lastClickedDestination.getId() != null
            && nearestDest.getId() != null
            && lastClickedDestination.getId().equals(nearestDest.getId())) {
            cycleIndex = (cycleIndex + 1) % options.size();
        } else {
            lastClickedDestination = nearestDest;
            cycleIndex = 0;
        }

        PossibleMoves chosen = options.get(cycleIndex);
        selectedMove = chosen;

        Button b = moveButtons.get(chosen);
        if (b != null) highlightSelectedMoveButton(b);

        logList.getItems().add(
            "🗺️ valt via karta: " + chosen.getFrom().getName() + " -> " + chosen.getTo().getName()
                + " (" + chosen.getTransport().getType() + ")"
        );
    }

    private List<PossibleMoves> movesTo(Location dest) {
        if (dest == null || shownMoves == null || shownMoves.isEmpty()) return List.of();
        if (dest.getId() == null) {
            return shownMoves.stream()
                .filter(m -> m.getTo() != null
                    && m.getTo().getX() == dest.getX()
                    && m.getTo().getY() == dest.getY())
                .toList();
        }
        return shownMoves.stream()
            .filter(m -> m.getTo() != null && m.getTo().getId() != null && m.getTo().getId().equals(dest.getId()))
            .toList();
    }

    private int screenToGridX(double x, double totalWidth) {
        double cellWidth = totalWidth / GRID_SIZE;
        int gx = (int) Math.floor(x / cellWidth);
        return clampToGrid(gx);
    }

    private int screenToGridY(double y, double totalHeight) {
        double cellHeight = totalHeight / GRID_SIZE;
        int gyFromTop = (int) Math.floor(y / cellHeight);
        int gy = (GRID_SIZE - 1) - gyFromTop;
        return clampToGrid(gy);
    }

    private void syncHudAndMap() {
        updateHud();
        updateGraphics();
    }

    private void updateHud() {
        if (players.isEmpty()) return;

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
        visualizer.clearMarkers();

        redrawAllPathsFromDb(w, h);

        List<double[]> positions = new ArrayList<>();
        for (Traveler tRef : players) {
            Traveler t = em != null && tRef.getId() != null ? em.find(Traveler.class, tRef.getId()) : tRef;
            if (t == null) continue;
            positions.add(computePlayerGridPos(t));
        }
        visualizer.drawPlayersInterpolated(positions, currentPlayerIndex, w, h);
    }

    private double[] computePlayerGridPos(Traveler t) {
        double baseX = clampToGrid(t.getPlayerPosX());
        double baseY = clampToGrid(t.getPlayerPosY());

        if (!t.isTravelling() || t.getId() == null || em == null) {
            return new double[]{baseX, baseY};
        }

        List<Journey> list = em.createQuery(
                "select j from Journey j " +
                    "join fetch j.locationLink ll " +
                    "join fetch ll.fromLocation " +
                    "join fetch ll.toLocation " +
                    "where j.traveler.id = :id " +
                    "order by j.turnNumber desc, j.id desc",
                Journey.class
            )
            .setParameter("id", t.getId())
            .setMaxResults(1)
            .getResultList();

        if (list.isEmpty()) return new double[]{baseX, baseY};

        Journey j = list.get(0);
        LocationLink ll = j.getLocationLink();
        int total = ll.getDistance();
        if (total <= 0) return new double[]{baseX, baseY};

        Location from = ll.getFromLocation();
        Location to = ll.getToLocation();

        int remainingAfter = j.getRemainingDistance();
        double fracAfter = (double) (total - remainingAfter) / total;

        double gx = from.getX() + (to.getX() - from.getX()) * fracAfter;
        double gy = from.getY() + (to.getY() - from.getY()) * fracAfter;

        gx = Math.max(0.0, Math.min(GRID_SIZE - 1, gx));
        gy = Math.max(0.0, Math.min(GRID_SIZE - 1, gy));

        return new double[]{gx, gy};
    }

    private void redrawAllPathsFromDb(double w, double h) {
        visualizer.clearPaths();

        for (int i = 0; i < players.size(); i++) {
            Traveler tRef = players.get(i);
            Long id = tRef.getId();
            if (id == null) continue;

            List<Journey> js = em.createQuery(
                    "select j from Journey j " +
                        "join fetch j.locationLink ll " +
                        "join fetch ll.fromLocation " +
                        "join fetch ll.toLocation " +
                        "where j.traveler.id = :id " +
                        "order by j.turnNumber asc, j.id asc",
                    Journey.class
                )
                .setParameter("id", id)
                .getResultList();

            for (Journey j : js) {
                LocationLink ll = j.getLocationLink();
                Location from = ll.getFromLocation();
                Location to = ll.getToLocation();

                int total = ll.getDistance();
                if (total <= 0) continue;

                int moved = j.getDistanceMoved();
                int remainingAfter = j.getRemainingDistance();
                int remainingBefore = remainingAfter + moved;

                double fracBefore = (double) (total - remainingBefore) / total;
                double fracAfter = (double) (total - remainingAfter) / total;

                visualizer.addRouteProgressSegment(
                    i,
                    clampToGrid(from.getX()), clampToGrid(from.getY()),
                    clampToGrid(to.getX()), clampToGrid(to.getY()),
                    fracBefore, fracAfter,
                    w, h
                );
            }
        }
    }

    private void updateGraphicsWithMoves(Traveler managed, List<PossibleMoves> moves) {
        updateGraphics();

        double w = drawingPane.getWidth();
        double h = drawingPane.getHeight();

        List<Location> destinations = moves.stream()
            .map(PossibleMoves::getTo)
            .distinct()
            .toList();

        visualizer.drawDestMarkers(destinations, w, h);
    }

    private int clampToGrid(int v) {
        return Math.max(0, Math.min(GRID_SIZE - 1, v));
    }



    private void doMove(Long travelerId, PossibleMoves chosen) {
        if (wonGame) return;

        rollButton.setDisable(true);

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Traveler managed = em.find(Traveler.class, travelerId);

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
                managed.increaseScore();
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

    private void highlightSelectedMoveButton(Button selected) {
        for (var node : movesBox.getChildren()) {
            if (node instanceof Button b) b.setStyle("");
        }
        selected.setStyle("-fx-border-color: white; -fx-border-width: 2; -fx-font-weight: bold;");
    }

    private Location getLocationByName(String name) {
        List<Location> list = em.createQuery("select l from Location l where l.name = :n order by l.id", Location.class)
            .setParameter("n", name)
            .setMaxResults(1)
            .getResultList();
        if (list.isEmpty()) throw new IllegalStateException("No location found with name: " + name);
        return list.get(0);
    }

    private void doContinueJourney(Long travelerId) {
        if (wonGame) return;

        rollButton.setDisable(true);

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Traveler managed = em.find(Traveler.class, travelerId);

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
                managed.increaseScore();
                doesPlayerWin();
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
