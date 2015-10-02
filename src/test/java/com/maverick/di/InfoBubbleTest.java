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
import javax.swing.UIManager;

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
