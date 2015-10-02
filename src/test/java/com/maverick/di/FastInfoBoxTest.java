package com.maverick.di;

import javax.swing.ImageIcon;

import org.junit.Test;

public class FastInfoBoxTest {
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
    public void fast() throws InterruptedException {
        for(int i = 0 ; i < 1000 ; i++) {
            InfoBox b = new InfoBox(HTML_TEXT,
                new ImageIcon(getClass().getResource("/images/large/warning.png")),
                Position.bottomRight);
            b.show();
            Thread.sleep(5);
            b.hide();
        }
    }
}
