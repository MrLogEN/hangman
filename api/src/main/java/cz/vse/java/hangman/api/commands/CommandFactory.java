package cz.vse.java.hangman.api.commands;

import cz.vse.java.hangman.api.messages.Message;

/**
 * Creating {@link Command} from {@link Message} in user-friendly manner.
 */
public interface CommandFactory {
    /**
     * Creates a {@link Command} based on a type of {@link Message} passed.
     * This {@link Command} can be passed for execution.
     * You can place validation of {@link Message} content in your implementation
     * of this interface.
     * @param message A {@link Message} to create a {@link Command} from.
     * @return A concrete instance of {@link Command}.
     */
    Command fromMessage(Message message);
}
