package cz.vse.java.hangman.server.commands;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

public class CommandWorkerFactory implements ThreadFactory{

    private final AtomicLong threadCount = new AtomicLong(1);

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setName("CommandWorker-" + threadCount.getAndIncrement());
        return thread;
    }

}
