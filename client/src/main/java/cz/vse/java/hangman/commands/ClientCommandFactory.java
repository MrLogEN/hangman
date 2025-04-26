package cz.vse.java.hangman.commands;

import cz.vse.java.hangman.Client;
import cz.vse.java.hangman.api.commands.Command;
import cz.vse.java.hangman.api.commands.CommandFactory;
import cz.vse.java.hangman.api.messages.Message;
import cz.vse.java.hangman.api.messages.server.response.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientCommandFactory implements CommandFactory {
    private static final Logger logger = LoggerFactory.getLogger(Client.class);
    @Override
    public Command fromMessage(Message message) {



        return switch (message) {
            case ServerLoginFailureMessage m -> new LoginFailureCommand();
            case ServerLoginSuccessMessage m -> new LoginSuccessCommand();
            case ServerListRoomsFailureMessage m -> new ListRooomsFailureCommand();
            case ServerListRoomsSuccessMessage m -> new ListRoomsSuccessCommand();
            case ServerCreateRoomFailureMessage m -> new CreateRoomFailureCommand();
            case ServerCreateRoomSuccessMessage m -> new CreateRoomSuccessCommand();
            case ServerJoinRoomFailureMessage m -> new JoinRoomFailureCommand();
            case ServerJoinRoomSuccessMessage m -> new JoinRoomSuccessCommand();
            case ServerStartGameFailureMessage m -> new StartGameFailureCommand();
            case ServerStartGameSuccessMessage m -> new StartGameSuccessCommand();
            case ServerTakeGuessFailureMessage m -> new TakeGuessFailureCommand();
            case ServerTakeGuessSuccessMessage m -> new TakeGuessSuccessCommand();
            case ServerLeaveRoomFailureMessage m -> new LeaveRoomFailureCommand();
            case ServerLeaveRoomSuccessMessage m -> new LeaveRoomSuccessCommand();
            default -> {
                logger.error("Error while creating command from message: {}", message);
                throw new IllegalArgumentException();
            }

        };






        /*
        if (message instanceof ServerLoginFailureMessage) {
            return new LoginFailureCommand();
        } else if (message instanceof ServerLoginSuccessMessage) {
            return new LoginSuccessCommand();
        } else if (message instanceof ServerListRoomsFailureMessage) {
            return new ListRooomsFailureCommand();
        } else if (message instanceof ServerListRoomsSuccessMessage) {
            return new ListRoomsSuccessCommand();
        } else if (message instanceof ServerCreateRoomFailureMessage) {
            return new CreateRoomFailureCommand();
        } else if (message instanceof ServerCreateRoomSuccessMessage) {
            return new CreateRoomSuccessCommand();
        } else if (message instanceof ServerJoinRoomFailureMessage) {
            return new JoinRoomFailureCommand();
        } else if (message instanceof ServerJoinRoomSuccessMessage) {
            return new JoinRoomSuccessCommand();
        } else if (message instanceof ServerStartGameFailureMessage) {
            return new StartGameFailureCommand();

        } else if (message instanceof ServerStartGameSuccessMessage) {
            return new StartGameSuccessCommand();
        } else if (message instanceof ServerTakeGuessFailureMessage) {
            return new TakeGuessFailureCommand();

        } else if (message instanceof ServerTakeGuessSuccessMessage) {
            return new TakeGuessSuccessCommand();
        } else if (message instanceof ServerLeaveRoomFailureMessage) {
            return new LeaveRoomFailureCommand();
        } else if (message instanceof ServerLeaveRoomSuccessMessage) {
            return new LeaveRoomSuccessCommand();
        }


        return null;

         */
    }
}
