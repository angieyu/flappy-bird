package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener {
    private boolean spacePressed = false;
    private boolean spaceReleased = true;

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
