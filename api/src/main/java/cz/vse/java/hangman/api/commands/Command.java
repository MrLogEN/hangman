package cz.vse.java.hangman.api.commands;

/**
 * <p>Must be implemented by every command.
 * Concrete commands should have <i>Command</i> suffix.</p>
 *
 * <p>Example implementation:
 * <pre><code>
 *     public class Light {
 *         void turnOn();
 *         void turnOff();
 *     }
 *
 *     public class TurnOnCommand implements Command {
 *         private Light light;
 *         public TurnOnCommand(Light light){
 *             this.light = light;
 *         }
 *
 *        {@literal @Override}
 *         void execute(){
 *             light.turnOn();
 *         }
 *     }
 * </code></pre></p>
 */
public interface Command {
    /**
     * Runs command's logic.
     * Data to a command can be passed through its constructor.
     */
    void execute();
}
