package com.cs5741.UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageTypeSpecifier;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MyWIndow extends JFrame implements ActionListener {
	MyPanel mp = null;
	ConfigPanel cp = null;
	Configuration configuration;
	Thread thread;
	JMenuBar menuBar;
	JMenuItem itemConfig, itemStart, itemStop;

	public MyWIndow() {
		super();
		addMenu();
		cp = new ConfigPanel(this);
//		mp = new MyPanel(8,6);
		setFrame();
		add(cp);
//		thread = new Thread(mp);
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setFrame() {
		setVisible(true);
		setSize(1000, 750);
		setDefaultCloseOperation(this.EXIT_ON_CLOSE);
	}

	public void refreshFrame() {
		invalidate();
		validate();
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;

		remove(cp);
		cp = null;
//		remove(menuBar);
		mp = new MyPanel(this.configuration);
		this.repaint();
//		getContentPane().add(mp);
		add(mp);
		new Thread(mp).start();

		refreshFrame();
	}

	public void showConfiguration() {
		if (mp != null) {
			remove(mp);
		}
	}

	public void addMenu() {
		menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		JMenu menuFile = new JMenu("Setting");
		menuBar.add(menuFile);

		itemConfig = new JMenuItem("Configuration");
		menuFile.add(itemConfig);
		itemConfig.addActionListener(this);
		itemConfig.setActionCommand("Configuration");
		
		itemStop = new JMenuItem("Stop");
		menuFile.add(itemStop);
		itemStop.addActionListener(this);
		itemStop.setActionCommand("stop");
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "Configuration") {
			if (mp != null) {
				mp.shutdownAll();
				remove(mp);
				mp = null;

			}
			if (cp != null) {

				remove(cp);
			}
			refreshFrame();
			cp = new ConfigPanel(this);
			add(cp);
			refreshFrame();
		}
		if (e.getActionCommand() == "stop") {
			if(mp!=null) {
				mp.shutdownAll();
			}
		} 
	}

}
