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
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.Point;

public enum Position {
    topLeft, topRight, bottomLeft, bottomRight;

    public Point getPosition(Component component, GraphicsDevice device) {
        GraphicsConfiguration genv = device == null ? component.getGraphicsConfiguration() : device.getDefaultConfiguration();
        device = device == null ? genv.getDevice() : device;
        DisplayMode mode = device.getDisplayMode();
        Dimension d = new Dimension(mode.getWidth(), mode.getHeight());
        Dimension componentSize = component.getSize();

        // Calculate the starting position
        int wy = 0;
        switch (this) {
            case topLeft:
            case topRight:
                wy = 16;
                break;
            case bottomLeft:
            case bottomRight:
                wy = d.height - 48;
        }

        // Calculate the x (and y where appropriate) position
        int wx = 0;
        switch (this) {
            case topLeft:
                wx = 16;
                break;
            case bottomLeft:
                wy = wy - componentSize.height;
                wx = 16;
                break;
            case topRight:
                wx = d.width - componentSize.width - 16;
                break;
            case bottomRight:
                wy = wy - componentSize.height;
                wx = d.width - componentSize.width - 16;
                break;
        }

        // Increment the y position
        switch (this) {
            case topLeft:
            case topRight:
                wy += 16;
                break;
            case bottomLeft:
            case bottomRight:
                wy -= 16;
                break;
        }
        
        wx += genv.getBounds().x;
        wy += genv.getBounds().y;
        
        return new Point(wx, wy);
    }
    
    public String getName() {
        switch(this) {
            case topLeft:
                return "Top Left";
            case topRight:
                return "Top Right";
            case bottomLeft:
                return "Bottom Left";
            default:
                return "Bottom Right";
        }
    }
}