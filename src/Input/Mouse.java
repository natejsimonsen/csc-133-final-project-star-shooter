package Input;

import Data.Click;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Mouse implements MouseListener {
    private boolean isReady;
    private Click lastClick;

    public Mouse() {
        isReady = false;
        lastClick = null;
    }

    public static Point getMouseCoords() {
        return MouseInfo.getPointerInfo().getLocation();
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {
        lastClick = new Click(e.getX(), e.getY(), e.getButton());
        isReady = true;
    }

    public Click pollClick() {
        if(!isReady) return null;
        isReady = false;
        return lastClick;
    }

    public boolean isReady() {
        return isReady;
    }
}
