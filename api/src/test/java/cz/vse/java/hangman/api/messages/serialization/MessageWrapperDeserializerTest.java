package cz.vse.java.hangman.api.messages.serialization;

import cz.vse.java.hangman.api.messages.DummyMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cz.vse.java.hangman.api.messages.DummyMessageWithObject;
import cz.vse.java.hangman.api.messages.DummyObject;
import cz.vse.java.hangman.api.messages.Message;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class MessageWrapperDeserializerTest {

    @Test
    public void jsonGetsDeserialized() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(MessageWrapper.class, new MessageWrapperDeserializer())
                .create();

        String json = "{\"type\":\"cz.vse.java.hangman.api.messages.DummyMessage\","
                + "\"message\":{\"id\":10,\"name\":\"Some name\",\"desc\":\"Some desc\"}}";

        MessageWrapper expectedWrapper = new MessageWrapper(
                new DummyMessage(10, "Some name", "Some desc"),
                DummyMessage.class);

        MessageWrapper actualWrapper = gson.fromJson(json, MessageWrapper.class);

        assertEquals(expectedWrapper.type(), actualWrapper.type());
        assertInstanceOf(DummyMessage.class, actualWrapper.message());

        DummyMessage dummyMessage = (DummyMessage) actualWrapper.message();

        int id = dummyMessage.id();
        String name = dummyMessage.name();
        String desc = dummyMessage.desc();

        assertEquals(10, id);
        assertEquals("Some name", name);
        assertEquals("Some desc", desc);
    }

    @Test
    public void jsonGetsDeserializedAlongWithPassedObject() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(MessageWrapper.class, new MessageWrapperDeserializer())
                .create();

        String json = "{\"type\":\"cz.vse.java.hangman.api.messages.DummyMessageWithObject\","
                + "\"message\":{\"dummyObject\":{\"param\":\"Random parameter\"}}}";


        MessageWrapper expectedWrapper = new MessageWrapper(
                new DummyMessageWithObject(new DummyObject("Random parameter")),
                DummyMessageWithObject.class);

        MessageWrapper actualWrapper = gson.fromJson(json, MessageWrapper.class);

        assertEquals(expectedWrapper.type(), actualWrapper.type());
        assertInstanceOf(DummyMessageWithObject.class, actualWrapper.message());

        DummyMessageWithObject dummyMessage = (DummyMessageWithObject) actualWrapper.message();


        String objectParam = dummyMessage.dummyObject().param();
        assertEquals("Random parameter", objectParam);
   }

}
