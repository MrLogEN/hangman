package cz.vse.java.hangman;

import java.io.ObjectOutputStream;

public class MessageWriter implements Runnable {
    private final ObjectOutputStream objectOutputStream;

    public MessageWriter(ObjectOutputStream objectOutputStream) {
        this.objectOutputStream = objectOutputStream;
    }

    @Override
    public void run() {

    }
}
