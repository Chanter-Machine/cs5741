package com.cs5741.UI;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class ConfigPanel extends JPanel implements ActionListener{
	JLabel jl_numOfTill, jl_maxTills,jl_restrictionTills, jl_cstmRate, jl_sizeOfEachTill, 
			jl_restrativeNum;
	JTextField jtf_numOfTIll, jtf_maxTills, jtf_restirictionTills, jtf_cstmRate, jtf_restrativeNum
				,jtf_sizeOfEachTill;
	JButton jb_confirm, jb_reset;
	MyWIndow mw;
	public ConfigPanel(MyWIndow myWIndow) {
		init();
		addComponent();
//		this.setLayout(new GridLayout(4, 2));
		this.setLayout(null);
//		this.setSize(300,400);
		addListener();
		mw = myWIndow;
	}
	public ConfigPanel(MyWIndow myWIndow, ActionListener al) {
		init();
		addComponent();
//		this.setLayout(new GridLayout(4, 2));
		this.setLayout(null);
//		this.setSize(300,400);
		addListener();
		mw = myWIndow;
	}
	
	public void init() {
		jl_numOfTill = new JLabel("Init Num of Till");
		jl_numOfTill.setLocation(30, 30);
		jl_numOfTill.setSize(250,50);
		jl_numOfTill.setFont(new Font("", 0, 20));
		
		jl_maxTills = new JLabel("Maxium Tills");
		jl_maxTills.setLocation(30, 60);
		jl_maxTills.setSize(300,50);
		jl_maxTills.setFont(new Font("", 0, 20));
		
		
		jl_restrictionTills = new JLabel("Num of Restrictive Tills");
		jl_restrictionTills.setLocation(30, 90);
		jl_restrictionTills.setSize(300,50);
		jl_restrictionTills.setFont(new Font("", 0, 20));
		
		jl_cstmRate = new JLabel("Rate of generating Customer");
		jl_cstmRate.setLocation(30, 120);
		jl_cstmRate.setSize(300,50);
		jl_cstmRate.setFont(new Font("", 0, 20));
		
		jl_sizeOfEachTill = new JLabel("Size of each Till");
		jl_sizeOfEachTill.setLocation(30, 150);
		jl_sizeOfEachTill.setSize(300, 50);
		jl_sizeOfEachTill.setFont(new Font("", 0, 20));
		
		jl_restrativeNum = new JLabel("Restrctive amount in cart");
		jl_restrativeNum.setLocation(30, 180);
		jl_restrativeNum.setSize(300, 50);
		jl_restrativeNum.setFont(new Font("", 0, 20));
		
		jtf_numOfTIll = new JTextField(8);
		jtf_numOfTIll.setLocation(300, 40);
		jtf_numOfTIll.setSize(150, 30);
		jtf_numOfTIll.setText("6");;
		
		jtf_maxTills = new JTextField(8);
		jtf_maxTills.setLocation(300, 70);
		jtf_maxTills.setSize(150, 30);
		jtf_maxTills.setText("8");
		
		jtf_restirictionTills = new JTextField(8);
		jtf_restirictionTills.setLocation(300, 100);
		jtf_restirictionTills.setSize(150, 30);
		jtf_restirictionTills.setText("3");
		
		jtf_cstmRate = new JTextField(8);
		jtf_cstmRate.setLocation(300, 130);
		jtf_cstmRate.setSize(150, 30);
		jtf_cstmRate.setText("10");
		
		jtf_sizeOfEachTill = new JTextField(8);
		jtf_sizeOfEachTill.setLocation(300, 160);
		jtf_sizeOfEachTill.setSize(150, 30);
		jtf_sizeOfEachTill.setText("5");
		
		jtf_restrativeNum = new JTextField(8);
		jtf_restrativeNum.setLocation(300, 190);
		jtf_restrativeNum.setSize(150, 30);
		jtf_restrativeNum.setText("100");
		
		jb_confirm = new JButton("Confirm");
		jb_confirm.setLocation(150,260);
		jb_confirm.setSize(80,40);
		
		jb_reset = new JButton("reset");
		jb_reset.setLocation(250,260);
		jb_reset.setSize(80,40);
	}
	
	public void addComponent() {
		add(jl_numOfTill);
		add(jtf_numOfTIll);
		add(jl_maxTills);
		add(jtf_maxTills);
		add(jl_restrictionTills);
		add(jtf_restirictionTills);
		add(jl_cstmRate);
		
		add(jtf_cstmRate);
		add(jl_sizeOfEachTill);
		add(jtf_sizeOfEachTill);
		add(jl_restrativeNum);
		add(jtf_restrativeNum);
		add(jb_confirm);
		add(jb_reset);
	}
	
	public void addListener() {
		jb_confirm.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==jb_confirm) {
			int numOfTIll = Integer.parseInt(jtf_numOfTIll.getText());
			int maxTill = Integer.parseInt(jtf_maxTills.getText().trim());
			int restrictionTill = Integer.parseInt(jtf_restirictionTills.getText().trim());
			int cstmRate = Integer.parseInt(jtf_cstmRate.getText().trim());
			int sizeOfEachTill = Integer.parseInt(jtf_sizeOfEachTill.getText().trim());
			int restrctiveAmount = Integer.parseInt(jtf_restrativeNum.getText().trim());
			Configuration configuration = 
					new Configuration(numOfTIll, maxTill, cstmRate, sizeOfEachTill, restrictionTill, restrctiveAmount);
			mw.setConfiguration(configuration);
		}
	}
}
