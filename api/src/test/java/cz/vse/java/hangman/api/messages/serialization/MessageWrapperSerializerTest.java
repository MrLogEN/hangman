package cz.vse.java.hangman.api.messages.serialization;


import cz.vse.java.hangman.api.messages.DummyMessage;
import cz.vse.java.hangman.api.messages.DummyMessageWithObject;
import cz.vse.java.hangman.api.messages.DummyObject;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MessageWrapperSerializerTest {

	@Test
	public void ShouldSerializeDummyMessage() {
		Gson gson = new GsonBuilder()
					.registerTypeAdapter(MessageWrapper.class, new MessageWrapperSerializer())
					.create();
		DummyMessage dummyMessage = new DummyMessage(10, "Some name", "Some desc");
		MessageWrapper wrapper = new MessageWrapper(dummyMessage, DummyMessage.class);
		String expected = "{\"type\":\"cz.vse.java.hangman.api.messages.DummyMessage\","
				+ "\"message\":{\"id\":10,\"name\":\"Some name\",\"desc\":\"Some desc\"}}";

		String actual = gson.toJson(wrapper);

		assertEquals(expected, actual);
	}

	@Test
	public void ShouldSerializeDummyMessageAlongWithAnObject() {
		Gson gson = new GsonBuilder()
				.registerTypeAdapter(MessageWrapper.class, new MessageWrapperSerializer())
				.create();
		DummyObject dummyObject = new DummyObject("Random parameter");
		DummyMessageWithObject dummyMessage = new DummyMessageWithObject(dummyObject);
		MessageWrapper wrapper = new MessageWrapper(dummyMessage, DummyMessageWithObject.class);

		String expected = "{\"type\":\"cz.vse.java.hangman.api.messages.DummyMessageWithObject\","
				+ "\"message\":{\"dummyObject\":{\"param\":\"Random parameter\"}}}";

		String actual = gson.toJson(wrapper);

		assertEquals(expected, actual);
	}
}
