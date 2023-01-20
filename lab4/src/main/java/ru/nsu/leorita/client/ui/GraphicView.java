package ru.nsu.leorita.client.ui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import ru.nsu.leorita.model.*;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;

public class GraphicView implements View {
    private static final int CELL_SIZE = 20;
    private Canvas field;
    private ArrayList<String> servers;
    private ListView<String> serversView;
    private Label rightStatus;

    public GraphicView(Canvas field, ListView<String> serversList, Label rightStatus) {
        this.field = field;
        this.serversView = serversList;
        this.servers = new ArrayList<>();
        this.rightStatus = rightStatus;
    }

    @Override
    public void updateGameList(AbstractMap<Long, GameAnnouncement> games) {
        servers.clear();
        var iter = games.values().iterator();
        for (int i = 0; i < games.size(); i++) {
            servers.add(iter.next().getGameName());
        }

    }

    @Override
    public void showError(String message) {
        rightStatus.setText(message);
    }

    @Override
    public void drawNewGameList() {
        serversView.getItems().clear();
        for (String gameName : servers) {
            serversView.getItems().add("Game name: " + gameName);
        }
    }

    @Override
    public void repaintField(GameState state, GameConfig config) {
        drawGrid(config.getWidth(), config.getHeight());
        state.getFoods().forEach(this::drawFood);
        state.getSnakes().forEach((id, snake) -> {
            if (snake.getPlayerId() == state.getLocalId()) {
                drawSnake(snake, Color.BLUEVIOLET);
            } else {
                drawSnake(snake, Color.BLACK);
            }
        });
    }

    @Override
    public void clearPlayField() {
        serversView.getItems().clear();
    }

    private void drawGrid(int width, int height) {
        GraphicsContext context = field.getGraphicsContext2D();

        context.clearRect(0.0, 0.0, field.getWidth(), field.getHeight());
        context.setFill(Color.ANTIQUEWHITE);
        context.setLineWidth(0.5);

        for (int i = 0; i < width + 1; i++) {
            context.strokeLine(
                    (CELL_SIZE * i),
                    0.0,
                    (CELL_SIZE * i),
                    (height * CELL_SIZE)
            );
        }
        for (int i = 0; i < height + 1; i++) {
            context.strokeLine(
                    0.0,
                    (CELL_SIZE * i),
                    (width * CELL_SIZE),
                    (CELL_SIZE * i)
            );
        }
    }

    private void drawFood(Coord food) {
        GraphicsContext context = field.getGraphicsContext2D();
        context.setFill(Color.TOMATO);
        context.fillOval((CELL_SIZE * food.getX()) + 4, (CELL_SIZE * food.getY()) + 4, 12.0, 12.0);
    }

    private void drawSnake(Snake snake, Color color) {
        GraphicsContext context = field.getGraphicsContext2D();
        context.setFill(color);

        snake.getBody().forEach(coord -> context.fillRect(
                coord.getX() * CELL_SIZE,
                coord.getY() * CELL_SIZE,
                CELL_SIZE,
                CELL_SIZE
        ));
    }

    @Override
    public void shutdown() {

    }
}
