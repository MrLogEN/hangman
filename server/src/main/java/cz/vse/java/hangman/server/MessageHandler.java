package cz.vse.java.hangman.server;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import cz.vse.java.hangman.api.commands.Command;
import cz.vse.java.hangman.api.commands.CommandFactory;
import cz.vse.java.hangman.api.messages.Message;
import cz.vse.java.hangman.api.messages.client.request.*;
import cz.vse.java.hangman.server.commands.CreateRoomCommand;
import cz.vse.java.hangman.server.commands.JoinRoomCommand;
import cz.vse.java.hangman.server.commands.LeaveRoomCommand;
import cz.vse.java.hangman.server.commands.ListRoomsCommand;
import cz.vse.java.hangman.server.commands.LoginCommand;
import cz.vse.java.hangman.server.commands.StartGameCommand;
import cz.vse.java.hangman.server.commands.TakeGuessCommand;

/**
 * This class is resposible for creating {@link Command}
 * instances from {@link Message} insances.
 */
public class MessageHandler implements CommandFactory {

    private final RoomManager roomManager;
    private final ClientHandler handler;
    private final Map<Class<?>, Function<Message, Command>> commandFactories = new HashMap<>();

    public MessageHandler(RoomManager roomManager, ClientHandler handler){
        this.roomManager = roomManager;
        this.handler = handler;
        registerFactories();
    }

    @Override
    public Command fromMessage(Message message) {
        Function<Message, Command> factory = commandFactories.get(message.getClass());
        if(factory != null) {
            return factory.apply(message);
        }
        else {
            throw new UnsupportedOperationException("Unsupported message type: "+ message.getClass().getName());
        }
    }

    private void registerFactories() {
        commandFactories.put(ClientLoginMessage.class, message -> new LoginCommand((ClientLoginMessage) message, roomManager, handler));
        commandFactories.put(ClientCreateRoomMessage.class, message -> new CreateRoomCommand((ClientCreateRoomMessage) message, roomManager, handler));
        commandFactories.put(ClientJoinRoomMessage.class, message -> new JoinRoomCommand((ClientJoinRoomMessage) message, roomManager, handler));
        commandFactories.put(ClientLeaveRoomMessage.class, message -> new LeaveRoomCommand((ClientLeaveRoomMessage) message, roomManager, handler));
        commandFactories.put(ClientListRoomsMessage.class, message -> new ListRoomsCommand((ClientListRoomsMessage) message, roomManager, handler));
        commandFactories.put(ClientStartGameMessage.class, message -> new StartGameCommand((ClientStartGameMessage) message, roomManager, handler));
        commandFactories.put(ClientTakeGuessMessage.class, message -> new TakeGuessCommand((ClientTakeGuessMessage) message, roomManager, handler));
    }
}
