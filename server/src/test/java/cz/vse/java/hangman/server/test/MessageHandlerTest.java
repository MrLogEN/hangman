package cz.vse.java.hangman.server.test;

import cz.vse.java.hangman.api.messages.client.request.*;
import cz.vse.java.hangman.server.ClientHandler;
import cz.vse.java.hangman.server.MessageHandler;
import cz.vse.java.hangman.server.RoomManager;
import cz.vse.java.hangman.server.commands.*;
import cz.vse.java.hangman.api.commands.Command;
import cz.vse.java.hangman.api.messages.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MessageHandlerTest {

    private RoomManager roomManager;
    private ClientHandler handler;
    private MessageHandler messageHandler;

    @BeforeEach
    void setUp() {
        roomManager = mock(RoomManager.class);
        handler = mock(ClientHandler.class);
        messageHandler = new MessageHandler(roomManager, handler);
    }

    @Test
    void testFromMessageWithSupportedMessage() {
        ClientLoginMessage loginMessage = mock(ClientLoginMessage.class);
        Command command = messageHandler.fromMessage(loginMessage);

        assertNotNull(command);
        assertTrue(command instanceof LoginCommand);
    }

    @Test
    void testFromMessageWithUnsupportedMessage() {
        Message unsupportedMessage = mock(Message.class);

        UnsupportedOperationException exception = assertThrows(
            UnsupportedOperationException.class,
            () -> messageHandler.fromMessage(unsupportedMessage)
        );

        assertEquals("Unsupported message type: " + unsupportedMessage.getClass().getName(), exception.getMessage());
    }

    @Test
    void testRegisterFactories() {
        ClientJoinRoomMessage joinRoomMessage = mock(ClientJoinRoomMessage.class);
        Command command = messageHandler.fromMessage(joinRoomMessage);

        assertNotNull(command);
        assertTrue(command instanceof JoinRoomCommand);
    }

}

