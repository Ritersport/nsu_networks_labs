package ru.nsu.leorita.client.ui;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import ru.nsu.leorita.client.Client;
import ru.nsu.leorita.model.*;

public class RootController implements ControllerLifecycle {
    GameModel model;
    Client client;
    @FXML
    Button exit;
    View view;
    @FXML
    private Canvas field;
    @FXML
    private Button startServerButton;
    @FXML
    private Slider heightSlider;
    @FXML
    private Slider widthSlider;
    @FXML
    private Slider delaySlider;
    @FXML
    private Slider foodsSlider;
    @FXML
    private Label heightLabel;
    @FXML
    private Label widthLabel;
    @FXML
    private Label foodsLabel;
    @FXML
    private Label delayLabel;
    @FXML
    private TextField playerNameField;
    @FXML
    private TextField gameNameField;
    @FXML
    private ListView<String> serversList;
    @FXML
    private Label rightStatus;
    @FXML
    private Label leftStatus;
    @FXML
    private Button joinPlayerButton;
    @FXML
    private Button joinViewerButton;
    private Boolean isGameStarted = false;

    public RootController() {
        model = new GameModelImpl();
    }

    @Override
    public void onStart() {
        this.view = new GraphicView(field, serversList, rightStatus, leftStatus);
        widthSlider.valueProperty().addListener((observable, oldValue, newValue) -> widthLabel.setText(String.valueOf(newValue.intValue())));
        heightSlider.valueProperty().addListener((observable, oldValue, newValue) -> heightLabel.setText(String.valueOf(newValue.intValue())));
        delaySlider.valueProperty().addListener((observable, oldValue, newValue) -> delayLabel.setText(String.valueOf(newValue.intValue())));
        foodsSlider.valueProperty().addListener((observable, oldValue, newValue) -> foodsLabel.setText(String.valueOf(newValue.intValue())));

        gameNameField.textProperty().addListener((observable, oldValue, newValue) -> {
            startServerButton.setDisable("".equals(newValue));
        });
        model.createClient(view);
        client = model.getClient();
    }

    public void handleKeyboard(KeyEvent keyEvent) {
        if (!model.getLocalPlayer().getRole().equals(NodeRole.VIEWER)) {
            switch (keyEvent.getCode()) {
                case W:
                    client.goNorth();
                    break;
                case S:
                    client.goSouth();
                    break;
                case A:
                    client.goWest();
                    break;
                case D:
                    client.goEast();
                    break;
            }
        }
    }

    public void onStartServerButtonClick() {
        String playerName;
        if ("".equals(playerNameField.getText())) {
            playerName = "player1";
        } else {
            playerName = playerNameField.getText();
        }
        startServerButton.setDisable(true);
        gameNameField.setEditable(false);
        playerNameField.setEditable(false);

        joinPlayerButton.setDisable(true);
        joinViewerButton.setDisable(true);

        model.startClient(new GamePlayer(playerName, 1, NodeRole.MASTER, PlayerType.HUMAN, 0), true);
        model.startServer(new GameConfig((int) widthSlider.getValue(), (int) heightSlider.getValue(), (int) foodsSlider.getValue(), (int) delaySlider.getValue(), gameNameField.getText()));
        isGameStarted = true;
    }

    public void onUpdateButtonClick() {
        view.drawNewGameList();
    }

    public void onJoinPlayerButtonClick() {
        String selcetedString = serversList.getSelectionModel().getSelectedItem();
        model.setLocalPlayerRole(NodeRole.NORMAL);
        model.setLocalPlayerName(playerNameField.getText());
        client.chooseGame(new String(selcetedString.getBytes(), 11, selcetedString.length() - 11), PlayerType.HUMAN, playerNameField.getText(), NodeRole.NORMAL);
        joinPlayerButton.setDisable(true);
        joinViewerButton.setDisable(true);
    }

    public void onJoinViewerButtonClick() {
        String selcetedString = serversList.getSelectionModel().getSelectedItem();
        model.setLocalPlayerRole(NodeRole.VIEWER);
        model.setLocalPlayerName(playerNameField.getText());
        client.chooseGame(new String(selcetedString.getBytes(), 11, selcetedString.length() - 11), PlayerType.HUMAN, playerNameField.getText(), NodeRole.VIEWER);
        joinViewerButton.setDisable(true);
        joinPlayerButton.setDisable(true);
    }

    public void onExit() {
        onStop();
    }

    @Override
    public void onStop() {
        view.shutdown();
        model.shutdown();
    }
}
