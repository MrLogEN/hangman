package cz.vse.java.hangman.controllers;

import cz.vse.java.hangman.ClientHandler;
import cz.vse.java.hangman.api.dtos.PlayerDTO;
import cz.vse.java.hangman.api.dtos.RoomDTO;
import cz.vse.java.hangman.api.messages.client.ClientMessageFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RoomsController {

    private static final Logger logger = LoggerFactory.getLogger(RoomsController.class);
    @FXML
    private Button createRoomButton;
    @FXML
    private Button refreshRoomListButton;

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

    @FXML
    public void initialize() {
        roomsTable.setItems(roomsObservableList);
        nameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().name()));
        maxPlayersColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().maxPlayers()).asObject());
        addJoinButtonToTable();

        clientHandler.send(ClientMessageFactory.createClientListRoomsMessage(playerDTO.name()));
        logger.info("Requesting list of rooms from the server.");

    }

    @FXML
    private void handleRefreshRoomListButton(ActionEvent actionEvent) {
        clientHandler.send(ClientMessageFactory.createClientListRoomsMessage(playerDTO.name()));
        logger.info("Requesting an update of list of rooms from the server.");
    }

    @FXML
    private void handleCreateRoomButton(ActionEvent actionEvent) {


        //TODO
    }

    public PlayerDTO getPlayerDTO() {
        return playerDTO;
    }

    public void setPlayerDTO(PlayerDTO playerDTO) {
        this.playerDTO = playerDTO;
    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    public void setClientHandler(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    @FXML
    public void clearRooms() {
        logger.info("Clearing rooms list.");
        roomsObservableList.clear();

    }

    @FXML
    public void addRoom(RoomDTO room) {
        logger.info("Adding room: {} to the list.", room.name());
        roomsObservableList.add(room);
    }

    private void addJoinButtonToTable() {
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

    private void handleJoinRoom(RoomDTO room) {
        logger.info("Player: {} is joining room: {}",playerDTO.name(), room.name());

        clientHandler.send(ClientMessageFactory.createClientJoinRoomMessage(playerDTO.name(), room.name()));
    }


}
