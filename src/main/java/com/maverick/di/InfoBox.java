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
