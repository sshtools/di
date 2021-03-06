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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.Popup;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.EventListenerList;

public abstract class AbstractInfoBox {

    private JLabel content;
    private Popup popup;
    private EventListenerList listeners = new EventListenerList();
    private boolean visible;
    private MouseAdapter listener;
    private Component parent;
    private boolean useDropShadow;

    public AbstractInfoBox(String text, Component parent) {
        this(text, null, parent);
    }

    public AbstractInfoBox(String text, Icon icon, Component parent) {
        this.parent = parent;
        content = new JLabel(text);
        content.setIconTextGap(10);
        content.setBorder(new EmptyBorder(0, 8, 0, 8));
        content.setSize(content.getPreferredSize());
        content.setVerticalTextPosition(JLabel.TOP);
        content.setOpaque(true);
        content.setForeground(UIManager.getDefaults().getColor("infoText"));
        content.setBackground(UIManager.getDefaults().getColor("info"));
        content.setFont(UIManager.getDefaults().getFont("ToolTip.font"));
        if (icon != null) {
            content.setIcon(icon);
        }
    }
    
    public Component getParent() {
        return parent;
    }

    public Component getContent() {
        return content;
    }
    
    public Dimension getPreferredSize() {
        return content.getPreferredSize();
    }
    
    protected abstract Popup createPopup();

    public void show() {
        if(!visible) {
            if(popup == null) {
                popup = createPopup();
            }
            popup.show();
            visible = true;
            if(listener == null) {
                listener = new MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                        content.getParent().removeMouseListener(this);
                        popupClicked();
                    }
                };
                content.getParent().addMouseListener(listener);
            }
        }
    }
    
    public void hide() {
        if(visible) {
            popup.hide();
            visible = false;
        }
    }

    public void addActionListener(ActionListener listener) {
        listeners.add(ActionListener.class, listener);
    }

    public void removeActionListener(ActionListener listener) {
        listeners.remove(ActionListener.class, listener);
    }

    public boolean isUseDropShadow() {
        return useDropShadow;
    }

    public void setUseDropShadow(boolean useDropShadow) {
        this.useDropShadow = useDropShadow;
    }

    void popupClicked() {
        ActionEvent evt = null;
        for (ActionListener l : listeners.getListeners(ActionListener.class)) {
            if (evt == null) {
                evt = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, content.getText());
            }
            l.actionPerformed(evt);
        }
        hide();
    }
}
