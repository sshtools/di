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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsDevice;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicArrowButton;

import com.sun.jna.platform.WindowUtils;

public class PoptartManager implements ActionListener, MouseListener {
	private static PoptartManager instance;
	private Position position = Position.bottomRight;
	private RoundedRectangleWindow popup;
	private PoptartPanel panel;
	private Timer timer;
	private List<Poptart> poptarts = new ArrayList<Poptart>();
	private GraphicsDevice screenDevice;

	PoptartManager() {
	}

	public static PoptartManager getInstance() {
		if (instance == null) {
			instance = new PoptartManager();
		}
		return instance;
	}

	public void setScreen(GraphicsDevice screenDevice) {
		this.screenDevice = screenDevice;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public synchronized void popup(String text, Icon icon, ActionListener callback) {
		Poptart poptart = new Poptart(text, icon, callback);
		poptarts.add(poptart);
		while (poptarts.size() > 1000) {
			poptarts.remove(0);
		}
		showPoptart(poptart);
	}

	void showPoptart(Poptart poptart) {
		// hidePopup();
		if (popup == null) {
			panel = new PoptartPanel(poptart);
			popup = new RoundedRectangleWindow(null, panel, false,
					screenDevice == null ? null : screenDevice.getDefaultConfiguration());
			try {
				Method m = popup.getClass().getMethod("setFocusableWindowState", new Class[] { boolean.class });
				m.invoke(popup, new Object[] { Boolean.FALSE });
			} catch (Exception e) {
			}
			try {
				Method m = popup.getClass().getMethod("setAlwaysOnTop", new Class[] { boolean.class });
				m.invoke(popup, new Object[] { Boolean.TRUE });
			} catch (Exception e) {
			}
			popup.addMouseListener(this);
			try {
				WindowUtils.setWindowAlpha(popup, 0.9f);
			} catch (Exception e) {
			}
		} else
			panel.setPoptart(poptart);
		popup.setLocation(position.getPosition(popup, screenDevice));
		if (!popup.isVisible()) {
			popup.setVisible(true);
		}
		if (timer == null) {
			timer = new Timer(10000, this);
			timer.setRepeats(false);
			timer.start();
		} else {
			timer.restart();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		hidePopup();
	}

	void hidePopup() {
		if (popup != null) {
			popup.dispose();
			popup.removeMouseListener(this);
			popup = null;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (panel != null && panel.getPoptart().getCallback() != null) {
			panel.getPoptart().getCallback()
					.actionPerformed(new ActionEvent(panel, ActionEvent.ACTION_PERFORMED, panel.getPoptart().getText()));
		}
		hidePopup();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@SuppressWarnings("serial")
	public class PoptartPanel extends JPanel {
		private JLabel textLabel;
		private JLabel iconLabel;
		private Poptart poptart;
		private BasicArrowButton previous;
		private BasicArrowButton next;

		public PoptartPanel(Poptart poptart) {
			this.poptart = poptart;
			setLayout(new BorderLayout());
			setSize(new Dimension(300, 300));
			// Top
			iconLabel = new JLabel(poptart.getIcon());
			iconLabel.setVerticalAlignment(SwingConstants.CENTER);
			iconLabel.setOpaque(true);
			Color bg = UIManager.getDefaults().getColor("ToolTip.background");
			iconLabel.setBackground(bg == null ? Color.white : bg.darker());
			iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
			// Text
			textLabel = new JLabel(poptart.getText());
			textLabel.setVerticalAlignment(SwingConstants.CENTER);
			textLabel.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
			Color color = UIManager.getDefaults().getColor("ToolTip.foreground");
			textLabel.setForeground(color == null ? Color.black : color.darker());
			// Close
			JButton closeButton = new BasicArrowButton(SwingConstants.NORTH);
			closeButton.setVerticalAlignment(SwingConstants.TOP);
			JPanel close = new JPanel(new BorderLayout());
			close.setOpaque(false);
			close.add(closeButton, BorderLayout.NORTH);
			close.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 8));
			closeButton.addActionListener(PoptartManager.this);
			// Navigation
			final int tartIdx = poptarts.indexOf(poptart);
			JPanel nav = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			nav.setOpaque(false);
			next = new BasicArrowButton(SwingConstants.EAST);
			if (tartIdx >= poptarts.size() - 1) {
				next.setEnabled(false);
			} else {
				next.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						showPoptart(poptarts.get(tartIdx + 1));
					}
				});
			}
			previous = new BasicArrowButton(SwingConstants.WEST);
			if (tartIdx == 0) {
				previous.setEnabled(false);
			} else {
				previous.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						showPoptart(poptarts.get(tartIdx - 1));
					}
				});
			}
			nav.add(previous);
			nav.add(next);
			nav.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 8));
			// Center
			JPanel center = new JPanel(new BorderLayout());
			center.add(textLabel, BorderLayout.CENTER);
			center.add(close, BorderLayout.EAST);
			center.add(nav, BorderLayout.SOUTH);
			center.setOpaque(false);
			//
			add(iconLabel, BorderLayout.WEST);
			add(center, BorderLayout.CENTER);
			setOpaque(true);
			setForeground(color);
			setBackground(bg.brighter());
			setFont(UIManager.getDefaults().getFont("ToolTip.font"));
		}

		public void setPoptart(Poptart poptart) {
			this.poptart = poptart;
			final int tartIdx = poptarts.indexOf(poptart);
			next.setEnabled(tartIdx < poptarts.size() - 1);
			previous.setEnabled(tartIdx > 0);
			iconLabel.setIcon(poptart.getIcon());
			textLabel.setText(poptart.getText());
		}

		public Poptart getPoptart() {
			return poptart;
		}
	}

	class Poptart {
		String text;
		Icon icon;
		ActionListener callback;

		Poptart(String text, Icon icon, ActionListener callback) {
			this.text = text;
			this.icon = icon;
			this.callback = callback;
		}

		public ActionListener getCallback() {
			return callback;
		}

		public String getText() {
			return text;
		}

		public Icon getIcon() {
			return icon;
		}
	}
}
