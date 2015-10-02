package com.maverick.di;

import javax.swing.ImageIcon;

import org.junit.Test;

public class InfoBoxTest {
    public final static String SHORT_TEXT = "Hello worl!";
    public final static String LONG_TEXT = "Lorem ipsum dolor sit amet, consectetur adipisicing elit, " +
    		"sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";
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
        displayInfoBox(Position.topLeft);
    }

    @Test
    public void topRight() throws InterruptedException {
        displayInfoBox(Position.topRight);
    }

    @Test
    public void bottomLeft() throws InterruptedException {
        displayInfoBox(Position.bottomLeft);
    }

    @Test
    public void bottomRight() throws InterruptedException {
        displayInfoBox(Position.bottomRight);
    }

    public void displayInfoBox(Position position) throws InterruptedException {
        displayInfoBox(position, SHORT_TEXT);
        displayInfoBox(position, LONG_TEXT);
        displayInfoBox(position, HTML_TEXT);
    }

    public void displayInfoBox(Position position, String text) throws InterruptedException {        
        InfoBox b = new InfoBox(text,
            new ImageIcon(getClass().getResource("/images/large/warning.png")),
            position);
        b.show();
        Thread.sleep(2500);
        b.hide();
    }
}
