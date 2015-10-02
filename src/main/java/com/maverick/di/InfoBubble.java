package com.maverick.di;

import java.awt.Component;
import java.awt.Point;
import java.awt.Window;

import javax.swing.Icon;
import javax.swing.Popup;
import javax.swing.SwingUtilities;

public class InfoBubble extends AbstractInfoBox {
    private Point position;

    public InfoBubble(String text, Component parent, Point position) {
        this(text, null, parent, position);
    }

    public InfoBubble(String text, Icon icon, Component parent, Point position) {
        super(text, icon, parent);
        this.position = position;
    }
    
    public Popup createPopup() {
        final Point origin = 
            getParent() == null ? new Point(0, 0)
                : (getParent().isShowing()
                   ? getParent().getLocationOnScreen() : getParent().getLocation());
        final Window windowParent = getParent() != null 
            ? SwingUtilities.getWindowAncestor(getParent()) : null;
        origin.translate(position.x, position.y);
        return new Popup() {
            private BubbleWindow w;
            public void show() {
                w = new BubbleWindow(windowParent, getContent(), isUseDropShadow());
                w.pack();
                Point where = new Point(origin);
                where.translate(-w.getWidth()/3, -w.getHeight());
                w.setAnchorLocation(where.x, where.y);
                w.setVisible(true);
            }
            public void hide() {
                w.setVisible(false);
                w.dispose();
            }
        };
    }
}
