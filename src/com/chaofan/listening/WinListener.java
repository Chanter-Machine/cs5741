package com.chaofan.listening;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.chaofan.UI.MyWIndow;

public class WinListener implements ActionListener {

	MyWIndow MyWIndow;
	
	public MyWIndow getMyWIndow() {
		return MyWIndow;
	}


	public void setMyWIndow(MyWIndow myWIndow) {
		MyWIndow = myWIndow;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()=="jb_confirm") {
			System.out.println("fuck off");
		}
	}
	
}
