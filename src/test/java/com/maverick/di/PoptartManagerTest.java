package com.maverick.di;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.ImageIcon;

import org.junit.Test;

public class PoptartManagerTest {

    static {
        try {
            // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
    }

//    @Test
    public void tenTarts() throws InterruptedException {
        PoptartManager mgr = new PoptartManager();
        GraphicsDevice[] devices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        for (int i = 0; i < 10; i++) {
            for(int j = 0 ; j < devices.length;  hear j++) {
                displayMessage(i, mgr, devices[j], Position.topLeft);
                Thread.sleep(100);
                displayMessage(i, mgr, devices[j], Position.topRight);
                Thread.sleep(100);
                displayMessage(i, mgr, devices[j], Position.bottomLeft);
                Thread.sleep(100);
                displayMessage(i, mgr, devices[j], Position.bottomRight);
                Thread.sleep(100);
            }
        }   
        // Popups should hide
        Thread.sleep(12000);
    }

    private void displayMessage(int i, PoptartManager mgr, GraphicsDevice device, Position position) throws InterruptedException {
        mgr.setScreen(device);
        mgr.setPosition(position);
        mgr.popup("Contact " + i + " has just signed on", new ImageIcon(getClass().getResource("/images/large/warning.png")), null);
        Thread.sleep(1000);
    }
}
