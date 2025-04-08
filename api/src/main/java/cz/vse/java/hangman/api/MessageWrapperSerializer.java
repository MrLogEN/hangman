package cz.vse.java.hangman.api;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import java.lang.reflect.Type;

/**
 * Implementation of JsonSerializer<T> interface for {@link MessageWrapper} type.
 * Should be registered through 
 * <pre><code>
 * new GsonBuilder()
*		.registerTypeAdapter(
*			MessageWrapper.class,
*			new MessageWrapperSerializer<>())
*		.create();
 *</code></pre>
 */	
public final class MessageWrapperSerializer<T extends Message> implements JsonSerializer<MessageWrapper<T>> {

	@Override
	public JsonElement serialize(MessageWrapper<T> src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject json = new JsonObject();
		
		json.addProperty("type", src.type().getName());

		JsonElement messageJson = context.serialize(src.message(), src.type());
		json.add("message", messageJson);

		return json;
	}
}
