package com.maverick.di;

import javax.swing.ImageIcon;

import org.junit.Test;

public class FastPoptartManagerTest {

    static {
        try {
            // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
    }

    @Test
    public void tenTarts() throws InterruptedException {
        PoptartManager mgr = new PoptartManager();
        for (int i = 0; i < 1000; i++) {
            mgr.popup("Contact " + i + " has just signed on", new ImageIcon(getClass().getResource("/images/large/warning.png")), null);
            Thread.sleep(1);
        }
        // Popups should hide
        Thread.sleep(12000);
    }
}
