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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.Point;
import java.awt.Shape;
import java.awt.Window;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;

import javax.swing.Box;
import javax.swing.JWindow;

import com.sun.jna.platform.WindowUtils;

public class RoundedRectangleWindow extends JWindow {
    protected final int Y_OFFSET = 50;
    protected final int ARC = 25;

    private Point offset;
    private Area mask;
    private Dimension maskSize;
    private ComponentListener moveTracker = new ComponentAdapter() {
        public void componentMoved(ComponentEvent e) {
            Point where = 
                e.getComponent().isShowing() 
                ? e.getComponent().getLocationOnScreen()
                : e.getComponent().getLocation();
            setLocation(where.x - offset.x, where.y - offset.y);
            // TODO preserve stacking order (linux)
        }
    }; 
    public RoundedRectangleWindow(Window owner, Component content, boolean useDropShadow) {
        this(owner, content, useDropShadow, null);
    }
    
    public RoundedRectangleWindow(Window owner, Component content, boolean useDropShadow, GraphicsConfiguration config) {
        super(owner, config);
        setFocusableWindowState(false);
        setName("###overrideRedirect###");
        getContentPane().setBackground(content.getBackground());
        getContentPane().add(content, BorderLayout.CENTER);
        getContentPane().add(Box.createVerticalStrut(Y_OFFSET), BorderLayout.SOUTH);
        if(owner != null) {
            owner.addComponentListener(moveTracker);
        }
        setSize(getPreferredSize());
        mask = new Area(getMask(getWidth(), getHeight()));
        maskSize = getSize();
        WindowUtils.setWindowMask(this, mask);
        if (EnvironmentUtils.useDropShadow() && useDropShadow) {
            new DropShadow(this, mask);
        }
    }
    
    public void setBounds(int x, int y, int w, int h) {
        super.setBounds(x, y, w, h);
        Dimension size = new Dimension(w, h);
        if (mask != null && !size.equals(maskSize)) {
            mask.subtract(mask);
            mask.add(new Area(getMask(w, h)));
            maskSize = size;
        }
    }
    
    public void setAnchorLocation(int x, int y) {
        super.setLocation(x, y);
        Window owner = getOwner();
        if (owner != null) {
            Point ref = owner.isShowing()
                ? owner.getLocationOnScreen() : owner.getLocation();
            offset = new Point(ref.x - x, ref.y - y);
        }
    }
    
    public void dispose() {
        super.dispose();
        getOwner().removeComponentListener(moveTracker);
    }

    protected Shape getMask(int w, int h) {
        Shape shape = new RoundRectangle2D.Float(0, 0, w, h-Y_OFFSET, 
                                                 ARC, ARC);
        return shape;
    }
    
    public Dimension getPreferredSize() {
        Dimension size = super.getPreferredSize();
        size.height += Y_OFFSET;
        return size;
    }
}