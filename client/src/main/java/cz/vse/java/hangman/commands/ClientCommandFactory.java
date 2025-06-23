package cz.vse.java.hangman.commands;

import cz.vse.java.hangman.Client;
import cz.vse.java.hangman.api.commands.Command;
import cz.vse.java.hangman.api.commands.CommandFactory;
import cz.vse.java.hangman.api.messages.Message;
import cz.vse.java.hangman.api.messages.server.request.ServerSyncClientGameMessage;
import cz.vse.java.hangman.api.messages.server.request.ServerSyncClientRoomMessage;
import cz.vse.java.hangman.api.messages.server.response.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Factory for creating commands from messages.
 */

public class ClientCommandFactory implements CommandFactory {
    private static final Logger logger = LoggerFactory.getLogger(Client.class);
    @Override
    public Command fromMessage(Message message) {


            if (message instanceof ServerLoginFailureMessage loginFailureMessage) {
                return new LoginFailureCommand(loginFailureMessage);

            } else if (message instanceof ServerLoginSuccessMessage loginSuccessMessage) {
                return new LoginSuccessCommand(loginSuccessMessage);

            } else if (message instanceof ServerListRoomsFailureMessage listRoomsFailureMessage) {
                return new ListRoomsFailureCommand(listRoomsFailureMessage);

            } else if (message instanceof ServerListRoomsSuccessMessage listRoomsSuccessMessage) {
                return new ListRoomsSuccessCommand(listRoomsSuccessMessage);

            } else if (message instanceof ServerCreateRoomFailureMessage createRoomFailureMessage) {
                return new CreateRoomFailureCommand(createRoomFailureMessage);

            } else if (message instanceof ServerCreateRoomSuccessMessage createRoomSuccessMessage) {
                return new CreateRoomSuccessCommand(createRoomSuccessMessage);

            } else if (message instanceof ServerJoinRoomFailureMessage joinRoomFailureMessage) {
                return new JoinRoomFailureCommand(joinRoomFailureMessage);

            } else if (message instanceof ServerJoinRoomSuccessMessage joinRoomSuccessMessage) {
                return new JoinRoomSuccessCommand(joinRoomSuccessMessage);

            } else if (message instanceof ServerStartGameFailureMessage startGameFailureMessage) {
                return new StartGameFailureCommand(startGameFailureMessage);

            } else if (message instanceof ServerStartGameSuccessMessage startGameSuccessMessage) {
                return new StartGameSuccessCommand(startGameSuccessMessage);

            } else if (message instanceof ServerTakeGuessFailureMessage takeGuessFailureMessage) {
                return new TakeGuessFailureCommand(takeGuessFailureMessage);

            } else if (message instanceof ServerTakeGuessSuccessMessage takeGuessSuccessMessage) {
                return new TakeGuessSuccessCommand(takeGuessSuccessMessage);

            } else if (message instanceof ServerLeaveRoomFailureMessage leaveRoomFailureMessage) {
                return new LeaveRoomFailureCommand(leaveRoomFailureMessage);

            } else if (message instanceof ServerLeaveRoomSuccessMessage leaveRoomSuccessMessage) {
                return new LeaveRoomSuccessCommand(leaveRoomSuccessMessage);

            }  else if (message instanceof ServerSyncClientGameMessage serverSyncClientGameMessage) {
                return new SyncClientGameCommand(serverSyncClientGameMessage);

            }else if (message instanceof ServerSyncClientRoomMessage serverSyncClientRoomMessage) {
                return new SyncClientRoomCommand(serverSyncClientRoomMessage);

            }



            else {
                logger.error("Error while creating command from message: {}", message);
                throw new IllegalArgumentException();
            }

            }






    }
