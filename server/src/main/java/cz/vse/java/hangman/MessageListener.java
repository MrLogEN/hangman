package cz.vse.java.hangman;

import java.io.ObjectInputStream;

public class MessageListener implements Runnable{
    private final ObjectInputStream inputStream;

    public MessageListener(ObjectInputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public void run() {

    }
}
