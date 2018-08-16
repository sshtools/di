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
/**
 * 
 */
package com.maverick.di;

import java.awt.Component;
import java.awt.Shape;
import java.awt.Window;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;

public final class BubbleWindow extends RoundedRectangleWindow {
    
    public BubbleWindow(Window owner, Component content, boolean useDropShadow) {
        super(owner, content, useDropShadow);
    }

    protected Shape getMask(int w, int h) {
        Shape shape = super.getMask(w, h);
        Area area = new Area(shape);
        GeneralPath path = new GeneralPath();
        path.moveTo(w/3, h-1);
        path.lineTo(w/2, h-1-Y_OFFSET);
        path.lineTo(w*2/3, h-1-Y_OFFSET);
        path.closePath();
        area.add(new Area(path));
        return area;
    }
}