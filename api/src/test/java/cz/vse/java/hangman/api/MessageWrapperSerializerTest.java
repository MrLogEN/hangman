package cz.vse.java.hangman.api;


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
		String expected = "{\"type\":\"cz.vse.java.hangman.api.DummyMessage\","
				+ "\"message\":{\"id\":10,\"name\":\"Some name\",\"desc\":\"Some desc\"}}";

		String actual = gson.toJson(wrapper);

		assertEquals(expected, actual);
	}
}
