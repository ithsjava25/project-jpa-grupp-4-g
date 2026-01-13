package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class TravelGameController {

    @FXML private ImageView mapView;
    @FXML private Pane drawingPane;
    @FXML private ListView<String> logList;
    @FXML private Button rollButton;


    @FXML private Label currentPlayerLabel;
    @FXML private Label nextPlayerLabel;
    @FXML private Label lastRollLabel;
    @FXML private Label currentCreditsLabel;
    @FXML private Label currentTurnLabel;
    @FXML private Label currentLocationLabel;
    @FXML private Label destinationLabel;

    private MapVisualizer visualizer;

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
        visualizer = new MapVisualizer(drawingPane);

        mapView.setImage(new Image(getClass().getResourceAsStream("/assets/map.png")));

        double width = 900;
        double height = 700;

        mapView.setFitWidth(width);
        mapView.setFitHeight(height);

        drawingPane.setPrefSize(width, height);
        drawingPane.setMaxSize(width, height);

        logList.getItems().add("🌍 Spelet startade. Klicka på kartan för att sätta destination. Tryck ROLL.");

        Platform.runLater(this::updateGraphics);
    }

    public void setupGame(String playerName) {
        GameConfig.MODE = GameMode.GUI;

        emf = Persistence.createEntityManagerFactory("jpa-hibernate-mysql");
        em = emf.createEntityManager();

        transports = em.createQuery("select t from Transport t", Transport.class).getResultList();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Traveler p1 = new Traveler(playerName, App.randomLocation(em));
            Location dest1 = App.randomLocation(em);
            p1.setDestinationPos(dest1.getX(), dest1.getY());

            Traveler p2 = new Traveler("Player 2", App.randomLocation(em));
            Location dest2 = App.randomLocation(em);
            p2.setDestinationPos(dest2.getX(), dest2.getY());

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

        logList.getItems().add("✅ " + players.size() + " spelare skapade.");
        syncHudAndMap();
    }

    @FXML
    public void onRoll(ActionEvent actionEvent) {
        if (wonGame || players.isEmpty()) return;

        rollButton.setDisable(true);

        Traveler current = players.get(currentPlayerIndex);

        // Välj rimlig diceCount för GUI:
        // tar första transportens diceCount om den finns annars 1.
        int guiDice = 1;
        if (transports != null && !transports.isEmpty()) {
            guiDice = transports.getFirst().getDiceCount();
        }

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Traveler managed = em.find(Traveler.class, current.getId());



            int rolled = managed.rollDice(guiDice);
            managed.setAvailableMovement(rolled);

            lastRollLabel.setText(String.valueOf(rolled));
            logList.getItems().add("🎲 " + managed.playerName + " slog " + rolled);


            try {
                managed.getDestinationPosX();
            } catch (NullPointerException npe) {
                logList.getItems().add("📍 Välj destination genom att klicka på kartan!");
                managed.setAvailableMovement(0);
                tx.commit();
                players.set(currentPlayerIndex, managed);
                rollButton.setDisable(false);
                syncHudAndMap();
                return;
            }


            managed.autoMove();

            if (managed.checkIfPlayerIsAtDestination()) {
                managed.increaseScore();

                Location newDest = App.randomLocation(em);
                managed.setDestinationPos(newDest.getX(), newDest.getY());
                logList.getItems().add("🎯 Ny destination: " + newDest.getName() + " [" + newDest.getX() + "," + newDest.getY() + "]");
            }

            managed.checkIfPlayerHasPenalties();
            managed.checkIfPlayerHasBonus();



            if (managed.checkScore()) {
                logList.getItems().add("🏆 " + managed.playerName + " vinner!");
                wonGame = true;
            }

            tx.commit();

            players.set(currentPlayerIndex, managed);

        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            if (!wonGame) rollButton.setDisable(false);
            else rollButton.setDisable(true);
        }

        if (!wonGame) {
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        }

        syncHudAndMap();
    }

    @FXML
    private void onMapClicked(MouseEvent event) {
        if (wonGame || players.isEmpty()) return;

        double cellWidth = drawingPane.getWidth() / GRID_SIZE;
        double cellHeight = drawingPane.getHeight() / GRID_SIZE;

        int clickedX = (int) (event.getX() / cellWidth);
        int clickedY = (int) ((drawingPane.getHeight() - event.getY()) / cellHeight);

        clickedX = clampToGrid(clickedX);
        clickedY = clampToGrid(clickedY);

        Traveler current = players.get(currentPlayerIndex);

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Traveler managed = em.find(Traveler.class, current.getId());
            managed.setDestinationPos(clickedX, clickedY);
            tx.commit();
            players.set(currentPlayerIndex, managed);
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }

        logList.getItems().add("📍 " + current.playerName + " destination = [" + clickedX + "," + clickedY + "]");
        syncHudAndMap();
    }

    private void syncHudAndMap() {
        updateHud();
        updateGraphics();
    }

    private void updateHud() {
        if (players.isEmpty()) return;

        Traveler current = players.get(currentPlayerIndex);

        currentPlayerLabel.setText(current.playerName);

        int next = (currentPlayerIndex + 1) % players.size();
        nextPlayerLabel.setText(players.get(next).playerName);

        // credits/turns
        try {
            currentCreditsLabel.setText(String.valueOf(current.getCredits()));
        } catch (Exception e) {
            currentCreditsLabel.setText("-");
        }

        // Om ni vill använda playerMovement.getTurns():
        try {
            currentTurnLabel.setText(String.valueOf(current.getTurns()));
        } catch (Exception e) {
            // fallback: om Traveler har getTurnCount()
            try {
                currentTurnLabel.setText(String.valueOf(current.getTurnCount()));
            } catch (Exception ignored) {
                currentTurnLabel.setText("-");
            }
        }

        currentLocationLabel.setText("[" + clampToGrid(current.getPlayerPosX()) + "," + clampToGrid(current.getPlayerPosY()) + "]");

        // Destination
        try {
            destinationLabel.setText("[" + current.getDestinationPosX() + "," + current.getDestinationPosY() + "]");
        } catch (Exception e) {
            destinationLabel.setText("-");
        }
    }

    private void updateGraphics() {
        double w = drawingPane.getWidth();
        double h = drawingPane.getHeight();
        if (w <= 0 || h <= 0) return;

        visualizer.drawGrid(w, h);

        List<int[]> positions = new ArrayList<>();
        for (Traveler t : players) {
            positions.add(new int[]{ clampToGrid(t.getPlayerPosX()), clampToGrid(t.getPlayerPosY()) });
        }


        visualizer.drawPlayers(positions, currentPlayerIndex, w, h);
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

    public void shutdown() {
        try {
            if (em != null && em.isOpen()) em.close();
        } catch (Exception ignored) {}
        try {
            if (emf != null && emf.isOpen()) emf.close();
        } catch (Exception ignored) {}
    }
}
