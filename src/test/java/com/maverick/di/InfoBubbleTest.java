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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.junit.Assume;
import org.junit.Test;

public class InfoBubbleTest {
    
    public final static String HTML_TEXT = 
   "<html>Lorem ipsum dolor sit amet, consectetur adipisicing elit,<br>" +
    "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.<br>" +
    "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris<br>" +
    "nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in<br>" +
    "reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla<br>" +
    "pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa<br>" +
    "qui officia deserunt mollit anim id est laborum.</html>";
    
    static {
        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
    }

    @Test
    public void topLeft() throws InterruptedException {
    	Assume.assumeTrue(!"true".equals(System.getProperty("java.awt.headless")));
        GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] devices = genv.getScreenDevices();
        DisplayMode mode = devices[0].getDisplayMode();
        Dimension w = new Dimension(mode.getWidth(), mode.getHeight());
        for(int i = 0 ; i < 10 ; i++) {
            JFrame f = new JFrame("Frame " + i);
            f.getContentPane().setLayout(new BorderLayout());
            f.getContentPane().add(new JLabel("Top"), BorderLayout.NORTH);
            f.getContentPane().add(new JLabel("Bottom"), BorderLayout.SOUTH);
            f.getContentPane().add(new JLabel("Left"), BorderLayout.WEST);
            f.getContentPane().add(new JLabel("Right"), BorderLayout.EAST);
            JLabel l = new JLabel("*");
            f.getContentPane().add(l, BorderLayout.CENTER);
            f.pack();
            f.setLocation((int)(Math.random() * ( w.width - f.getSize().width )),(int)( Math.random() * ( w.height - f.getSize().height ) ) );
            f.setVisible(true);
            InfoBubble bubble = new InfoBubble(HTML_TEXT, new ImageIcon(getClass().getResource("/images/large/error.png")),
                l, new Point(l.getSize().width / 2, l.getHeight() / 2));
            bubble.show();
            Thread.sleep(1500);
            bubble.hide();
            f.dispose();
        }
    }
}
