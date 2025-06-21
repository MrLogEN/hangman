package cz.vse.java.hangman.server;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import cz.vse.java.hangman.api.commands.Command;
import cz.vse.java.hangman.api.commands.CommandFactory;
import cz.vse.java.hangman.api.messages.Message;
import cz.vse.java.hangman.api.messages.client.request.*;
import cz.vse.java.hangman.api.messages.serialization.MessageWrapper;
import cz.vse.java.hangman.server.commands.LoginCommand;

public class MessageHandler implements CommandFactory {

    private final RoomManager roomManager;
    private final Map<Class<?>, Function<Message, Command>> commandFactories = new HashMap<>();

    public MessageHandler(RoomManager roomManager){
        this.roomManager = roomManager;
        commandFactories.put(ClientLoginMessage.class, message -> new LoginCommand((ClientLoginMessage) message, roomManager));
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
}
