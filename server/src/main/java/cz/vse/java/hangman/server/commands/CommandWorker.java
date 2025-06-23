package cz.vse.java.hangman.server.commands;

import cz.vse.java.hangman.api.commands.Command;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Workers can run commands in a separate thread.
 */
public class CommandWorker implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(CommandWorker.class);
    private final Command command;
    private final List<Thread> workerThreads;

    public CommandWorker(Command command, List<Thread> workerThreads) {
        this.command = command;
        this.workerThreads = workerThreads;
    }

    @Override
    public void run() {
        try {
            synchronized(workerThreads) {
                workerThreads.add(Thread.currentThread());
            }
            logger.debug("Trying to execute: {}", command.getClass().getName());
            command.execute();

            logger.debug("Command {} executed successfully.", command.getClass().getName());
        } catch (Exception e) {
            logger.error("Failed to execute command: {}", command.getClass().getName(), e);
        }
        finally {
            synchronized (workerThreads) {
                workerThreads.remove(Thread.currentThread());
            }
        }
    }
}
