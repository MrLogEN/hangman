package cz.vse.java.hangman.controllers;

import cz.vse.java.hangman.ClientHandler;
import cz.vse.java.hangman.api.dtos.PlayerDTO;
import cz.vse.java.hangman.api.dtos.RoomDTO;
import cz.vse.java.hangman.api.messages.client.ClientMessageFactory;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Controller for controlling screen with list of rooms.
 */
public class RoomsController {

    private static final Logger logger = LoggerFactory.getLogger(RoomsController.class);
    @FXML
    private Button createRoomButton;


    private final javafx.collections.ObservableList<RoomDTO> roomsObservableList = javafx.collections.FXCollections.observableArrayList();
    @FXML
    private TableView<RoomDTO> roomsTable;

    @FXML
    private TableColumn<RoomDTO, String> nameColumn;

    @FXML
    private TableColumn<RoomDTO, Integer> maxPlayersColumn;

    @FXML
    private TableColumn<RoomDTO, Void> joinColumn;


    private PlayerDTO playerDTO;
    private ClientHandler clientHandler;

    /**
     * Initialize.
     */
    @FXML
    public void initialize() {
        logger.info("Initializing RoomsController: {}", this);
    }

    /**
     * Inits initial data of rooms controller.
     */
    public void initData() {
        roomsTable.setItems(roomsObservableList);
        nameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().name()));
        maxPlayersColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().maxPlayers()).asObject());
        addJoinButtonToTable();
        clientHandler.send(ClientMessageFactory.createClientListRoomsMessage(playerDTO.name()));
        logger.info("Requesting list of rooms from the server.");
    }

    /**
     * Handle refresh room list button.
     *
     * @param actionEvent the action event
     */

    @FXML
    private void handleRefreshRoomListButton(ActionEvent actionEvent) {
        clientHandler.send(ClientMessageFactory.createClientListRoomsMessage(playerDTO.name()));
        logger.info("Requesting an update of list of rooms from the server.");
    }

    /**
     * Handle create room button.
     *
     * @param actionEvent the action event
     */

    @FXML
    private void handleCreateRoomButton(ActionEvent actionEvent) {

        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/createroom.fxml"));
                Parent root = loader.load();

                CreateRoomController createRoomController = loader.getController();
                Registry.getInstance().setCreateRoomController(createRoomController);
                createRoomController.setClientHandler(clientHandler);
                createRoomController.setPlayerDTO(playerDTO);

                Stage popupStage = new Stage();
                popupStage.initModality(Modality.APPLICATION_MODAL);
                popupStage.initOwner(createRoomButton.getScene().getWindow());
                popupStage.setTitle("Vytvořit místnost");

                Scene scene = new Scene(root);
                popupStage.setScene(scene);
                popupStage.showAndWait();

            } catch (IOException e) {
                logger.error("Nepodařilo se otevřít okno pro vytvoření místnosti: {}", e.getMessage());
            }
        });
    }


    /**
     * Clears rooms list.
     */
    @FXML
    public void clearRooms() {
        logger.info("Clearing rooms list.");
        roomsObservableList.clear();

    }

    /**
     * Add room.
     *
     * @param room RoomFTO of the room
     */
    @FXML
    public void addRoom(RoomDTO room) {
        logger.info("Adding room: {} to the list.", room.name());
        roomsObservableList.add(room);
    }


    /**
     * Add join button to table.
     */
    @FXML
    public void addJoinButtonToTable() {
        joinColumn.setCellFactory(col -> new javafx.scene.control.TableCell<>() {
            private final Button joinButton = new Button("Připojit");

            {
                joinButton.setOnAction(event -> {
                    RoomDTO room = getTableView().getItems().get(getIndex());
                    handleJoinRoom(room);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(joinButton);
                }
            }
        });
    }


    /**
     *
     * Handles join room.
     * @param room RoomDTO of the room
     */

    private void handleJoinRoom(RoomDTO room) {
        logger.info("Player: {} is joining room: {}",playerDTO.name(), room.name());

        clientHandler.send(ClientMessageFactory.createClientJoinRoomMessage(playerDTO.name(), room.name()));
    }


    /**
     * Sets player dto.
     *
     * @param playerDTO the player dto
     */
    public void setPlayerDTO(PlayerDTO playerDTO) {
        this.playerDTO = playerDTO;
    }


    /**
     * Sets client handler.
     *
     * @param clientHandler the client handler
     */
    public void setClientHandler(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    @Override
    public String toString() {
        return "RoomsController{" +
                "clientHandler=" + clientHandler +
                ", playerDTO=" + playerDTO +
                ", roomsObservableList=" + roomsObservableList +
                '}';
    }
}
