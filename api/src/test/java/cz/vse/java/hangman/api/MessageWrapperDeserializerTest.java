package cz.vse.java.hangman.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class MessageWrapperDeserializerTest {

    @Test
    public void jsonGetsDeserialized() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(MessageWrapper.class, new MessageWrapperDeserializer())
                .create();

        String json = "{\"type\":\"cz.vse.java.hangman.api.DummyMessage\","
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

}
