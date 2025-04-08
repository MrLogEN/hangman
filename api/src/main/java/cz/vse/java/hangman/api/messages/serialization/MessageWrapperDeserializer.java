package cz.vse.java.hangman.api.messages.serialization;

import com.google.gson.*;
import cz.vse.java.hangman.api.messages.Message;

import java.lang.reflect.Type;

public class MessageWrapperDeserializer implements JsonDeserializer<MessageWrapper> {
    @Override
    public MessageWrapper deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String typeName = jsonObject.get("type").getAsString();
        JsonObject messageJson = jsonObject.get("message").getAsJsonObject();

        try {

            Class<?> klass = Class.forName(typeName);
            Message message = context.deserialize(messageJson, klass);

            return new MessageWrapper(message, klass.asSubclass(Message.class));
        }
        catch (ClassNotFoundException ex) {
            throw new JsonParseException("Cannot find " + typeName + " class.", ex);
        }
    }

}
