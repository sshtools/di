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
    	Assume.assumeTrue(!"true".equals(System.getProperty("java.awt.headless")));
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
