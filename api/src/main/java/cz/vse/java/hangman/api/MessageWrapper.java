package cz.vse.java.hangman.api;
/**
 * This record servers as a wrapper to a message instance.
 * Every @{link Message} must be wrapped in this class 
 * so the type is conveyed.
 *
 * @param message instance of a {@link Message} subclass
 * @param type the actual type of the {@code message} parameter
 */
public record MessageWrapper<T extends Message>(T message, Class<T> type) {
}

