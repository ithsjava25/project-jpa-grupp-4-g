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

import java.lang.reflect.Method;
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

    private int playerX = 0;
    private int playerY = 0;

    private static final int GRID_SIZE = 50;

    // JPA
    private EntityManagerFactory emf;
    private EntityManager em;

    private final List<Traveler> players = new ArrayList<>();
    private int currentPlayerIndex = 0;
    private boolean wonGame = false;

    private Transport plane;

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

        logList.getItems().add("🌍 GUI redo. Ange namn för att starta.");

        Platform.runLater(this::updateGraphics);
    }

    public void setupGame(String playerName) {
        // Starta JPA
        emf = Persistence.createEntityManagerFactory("jpa-hibernate-mysql");
        em = emf.createEntityManager();

        plane = em.createQuery("select t from Transport t", Transport.class)
            .setMaxResults(1)
            .getSingleResult();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Traveler p1 = new Traveler(playerName, App.randLoc(em));
            Location dest1 = App.randLoc(em);
            p1.setDestinationPos(dest1.getX(), dest1.getY());

            Traveler p2 = new Traveler("Player 2", App.randLoc(em));
            Location dest2 = App.randLoc(em);
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

        logList.getItems().add("✅ Spelare skapade. Klicka på kartan för att sätta destination. Tryck ROLL för tur.");

        syncUiPositionFromCurrentPlayer();
        updateHud();
    }

    public void onRoll(ActionEvent actionEvent) {
        if (wonGame || players.isEmpty()) return;

        Traveler current = players.get(currentPlayerIndex);

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Traveler managed = em.find(Traveler.class, current.getId());

            managed.playerTurnByMode(plane.getDiceCount());


            Integer rolled = tryGetInt(managed, "getAvailableMovement");
            if (rolled != null) lastRollLabel.setText(String.valueOf(rolled));
            else lastRollLabel.setText("-");

            logList.getItems().add("🎲 " + managed.playerName + " tog en tur!");

            if (managed.checkIfPlayerIsAtDestination()) {
                Location newDest = App.randLoc(em);
                managed.setDestinationPos(newDest.getX(), newDest.getY());
                logList.getItems().add("🎯 Ny destination: " + newDest.getName() + " [" + newDest.getX() + "," + newDest.getY() + "]");
            }

            if (managed.checkScore()) {
                logList.getItems().add("🏆 " + managed.playerName + " vinner!");
                wonGame = true;
                rollButton.setDisable(true);
            }

            tx.commit();

            // uppdatera referens
            players.set(currentPlayerIndex, managed);

        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }

        if (!wonGame) {
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        }

        syncUiPositionFromCurrentPlayer();
        updateHud();
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
            managed.setDestinationPos(clickedX, clickedY); // klasskompisens logik-stöd
            tx.commit();

            players.set(currentPlayerIndex, managed);
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }

        logList.getItems().add("📍 Destination satt till [" + clickedX + "," + clickedY + "] för " + current.playerName);
        updateHud();
        updateGraphics();
    }

    private void syncUiPositionFromCurrentPlayer() {
        if (players.isEmpty()) return;

        Traveler t = players.get(currentPlayerIndex);
        Location loc = t.getCurrentLocation();

        if (loc != null) {
            playerX = clampToGrid(loc.getX());
            playerY = clampToGrid(loc.getY());
        }

        updateGraphics();
    }

    private int clampToGrid(int v) {
        return Math.max(0, Math.min(GRID_SIZE - 1, v));
    }

    private void updateGraphics() {
        double w = drawingPane.getWidth();
        double h = drawingPane.getHeight();

        if (w > 0 && h > 0) {
            visualizer.drawGrid(w, h);
            visualizer.drawPlayer(playerX, playerY, w, h);
        }
    }

    private void updateHud() {
        if (players.isEmpty()) return;

        Traveler current = players.get(currentPlayerIndex);

        currentPlayerLabel.setText(current.playerName);
        currentCreditsLabel.setText(current.getMoney() != null ? current.getMoney().toString() : "0");
        currentTurnLabel.setText(String.valueOf(current.getTurnCount()));

        if (current.getCurrentLocation() != null) {
            currentLocationLabel.setText(current.getCurrentLocation().getName()
                + " [" + current.getCurrentLocation().getX() + "," + current.getCurrentLocation().getY() + "]");
        } else {
            currentLocationLabel.setText("-");
        }

        Integer dx = tryGetInt(current, "getDestinationPosX");
        if (dx == null) dx = tryGetInt(current, "getDestinationX");
        Integer dy = tryGetInt(current, "getDestinationPosY");
        if (dy == null) dy = tryGetInt(current, "getDestinationY");

        if (dx != null && dy != null) destinationLabel.setText("[" + dx + "," + dy + "]");
        else destinationLabel.setText("-");

        int next = (currentPlayerIndex + 1) % players.size();
        nextPlayerLabel.setText(players.get(next).playerName);
    }


    private Integer tryGetInt(Object obj, String methodName) {
        try {
            Method m = obj.getClass().getMethod(methodName);
            Object val = m.invoke(obj);
            if (val instanceof Integer i) return i;
        } catch (Exception ignored) {}
        return null;
    }

    public void startMockJourney() {
        logList.getItems().add("📜 Demo path!");
        List<int[]> journeyPath = new ArrayList<>();
        journeyPath.add(new int[]{playerX, playerY});
        journeyPath.add(new int[]{10, 15});
        journeyPath.add(new int[]{25, 20});
        journeyPath.add(new int[]{40, 45});
        visualizer.animateJourney(journeyPath, drawingPane.getWidth(), drawingPane.getHeight());
        playerX = 40;
        playerY = 45;
        updateGraphics();
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
