package cz.vse.java.hangman.api;

import java.lang.reflect.Type;

/**
 * This record servers as a wrapper to a message instance.
 * Every @{link Message} must be wrapped in this class 
 * so the type is conveyed.
 *
 * @param message instance of a {@link Message} subclass
 * @param type the actual type of the {@code message} parameter
 */
public record MessageWrapper(Message message, Type type) {
    public MessageWrapper {
        if (type == null){
            throw new IllegalArgumentException("Type cannot be null");
        }
        if (message == null){
            throw new IllegalArgumentException("Message cannot be null");
        }
    }
}

