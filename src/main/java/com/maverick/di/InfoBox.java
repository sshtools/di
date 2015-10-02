package com.maverick.di;

import java.awt.Point;

import javax.swing.Icon;
import javax.swing.Popup;

public class InfoBox extends AbstractInfoBox {

    private Position position;

    public InfoBox(String text, Position position) {
        this(text, null, position);
    }

    public InfoBox(String text, Icon icon, Position position) {
        super(text, icon, null);
        this.position = position;
    }
    
    public Popup createPopup() {
        return new Popup() {
            private RoundedRectangleWindow w;
            public void show() {
                w = new RoundedRectangleWindow(null, getContent(), isUseDropShadow());
                w.pack();
                Point where = position.getPosition(w, null);
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
