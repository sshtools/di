/**
 * Desktop Integration - Librarary that provides various platform specific desktop integrations via JNA.
 * Copyright © 2012 SSHTOOLS Limited (support@sshtools.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
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
