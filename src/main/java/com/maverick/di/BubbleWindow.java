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