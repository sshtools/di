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

import javax.swing.ImageIcon;

import org.junit.Assume;
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
    	Assume.assumeTrue(!"true".equals(System.getProperty("java.awt.headless")));
        PoptartManager mgr = new PoptartManager();
        for (int i = 0; i < 100; i++) {
            mgr.popup("Contact " + i + " has just signed on", new ImageIcon(getClass().getResource("/images/large/warning.png")), null);
            Thread.sleep(100);
        }
        // Popups should hide
        Thread.sleep(12000);
    }
}
