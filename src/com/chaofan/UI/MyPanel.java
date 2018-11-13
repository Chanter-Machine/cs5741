package com.chaofan.UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.omg.CORBA.PUBLIC_MEMBER;

import com.chaofan.test.Cart;
import com.chaofan.test.Consumer;
import com.chaofan.test.Container;
import com.chaofan.test.ContainerTest3;
import com.chaofan.test.DynameicControl;
import com.chaofan.test.Producer;
import com.chaofan.test.Role;
import com.chaofan.test.TillStatus;

public class MyPanel extends JPanel implements Runnable, ActionListener{
	
	Image cartImage, tillImage, basketImage;
	Icon stopImage;
	
	ExecutorService service;
	Configuration configuration;

	Vector<Consumer> consumers;
	Vector<Container> containers;
	Producer producer;
	DynameicControl dc;
	
	Vector<Future<Long>> timeConsumationOfConsumer;
	Future<Integer> lostConsumer;
	JButton jb_stop;
//	public MyPanel() {
//		
//		cartImage = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/resource/cart.png"));
//		tillImage = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/resource/till.jpg")); 
//		till = new Till(5, 1);
//	}
	
	/**
	 * 
	 * @param num is the number of tills
	 * @param size is the size of each till
	 */
	public MyPanel(Configuration configuration) {
		this.setLayout(null);
		loadResource() ;
		this.configuration = configuration;
		service = Executors.newFixedThreadPool(configuration.getMaxiumOfTill()+10);
		timeConsumationOfConsumer = new Vector<Future<Long>>();
		containers = new Vector<Container>();
		addContainers();
		producer = new Producer(containers, 1, configuration);
		consumers = new Vector<Consumer>(configuration.getMaxiumOfTill());
		addConsumer();
//		new Thread(producer).start();
		dc = new DynameicControl(containers, configuration, service, timeConsumationOfConsumer, consumers);
		startThreads();
	}
	
	public void loadResource() {
		cartImage = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/resource/cart1.png"));
		stopImage = new ImageIcon("/resource/funny.jpg");
		tillImage = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/resource/till.jpg")); 
		basketImage = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/resource/basket.png")); 
		
	}
	
	public void addConsumer() {
		for(int i=0;i<configuration.getRestrictiveTills();i++) {
			Consumer consumer = new Consumer(containers.get(i), i, TillStatus.opening, true);
			containers.get(i).setRole(Role.restrictive);
			containers.get(i).setTillStatus(TillStatus.opening);
			consumers.add(consumer);
		}
		for(int i=configuration.getRestrictiveTills();i<configuration.getInitTills();i++) {
			Consumer consumer = new Consumer(containers.get(i), i, TillStatus.opening, true);
			containers.get(i).setRole(Role.general);
			containers.get(i).setTillStatus(TillStatus.opening);
			consumers.add(consumer);
		}
		for(int i=configuration.getInitTills(); i<configuration.getMaxiumOfTill(); i++) {
			Consumer consumer = new Consumer(containers.get(i), i, TillStatus.closing, false);
			containers.get(i).setRole(Role.general);
			containers.get(i).setTillStatus(TillStatus.closing);
			consumers.add(consumer);
		}
			
	}
	
	
	public void startThreads() {
//		service.execute(producer);
		lostConsumer = service.submit(producer);
		for(int i=0;i<configuration.getInitTills();i++) {
//			service.execute(consumers.get(i));
			if(containers.get(i).getTillStatus()==TillStatus.opening) {
				timeConsumationOfConsumer.add(service.submit(consumers.get(i)));
			}
		}
		
		service.execute(dc);
	}
	
	public void addContainers() {
		for(int i=0;i<configuration.getMaxiumOfTill();i++) {
			containers.add(new ContainerTest3(configuration.getSizeOfEachTill()));
		}
	}
	
	public void drwaCart(Graphics g) {
//		g.drawImage(cartImage, 80, 30, 60, 60, this);
		Cart cart;
		for(int i=0;i<this.configuration.getMaxiumOfTill();i++) {
			
			int temp = containers.get(i).getList().size();
			g.setFont(new Font("TimesRoman", Font.PLAIN, 12));
			for(int j=0;j< temp;j++) {
				if(containers.get(i).getRole()==Role.restrictive) {
					
					g.drawImage(basketImage, 90+j*50, 30+i*80, 60, 60, this);
				}
				else {
					g.drawImage(cartImage, 90+j*50, 30+i*80, 60, 60, this);
					
				}
				g.setColor(Color.WHITE);
				cart = containers.get(i).getList().get(j);
				g.drawString(cart.getNumber()+"", 110+j*50, 40+i*80);
			}
		}
	}
	
	public void drawTill(Graphics g) {
		g.setColor(Color.WHITE);
		for(int i=0;i<configuration.getMaxiumOfTill();i++) {
			g.drawLine(30, 20+80*i, 500, 20+80*i);
			g.drawImage(tillImage, 30, 30+80*i,60,60,this);
			g.drawLine(30, 100+80*i, 500, 100+80*i);
		}
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0,0,1000,700);
		this.drwaCart(g);
		drawTill(g);
		drawInfo(g);
//		System.out.println("Test");
	}
	
	public void drawInfo(Graphics g) {
		g.setColor(Color.RED);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 22));
		long totalWatiTime = 0;
		
		g.drawString("Total customer:", 600, 30);
		g.drawString(producer.getSumOfCustomer()+"", 900, 30);
		
		g.drawString("Lost customer:", 600, 60);
		g.drawString(producer.getSumOfLostCustomer()+"", 900, 60);
		
		g.drawString("Total Products proceed:", 600, 90);
		int totalProducts = 0;
		for(int i=0;i<configuration.getMaxiumOfTill();i++) {
			totalProducts += containers.get(i).getTotalProducts();
			totalWatiTime += consumers.get(i).getTotalWaitTIme();
		}
		g.drawString(totalProducts+"", 900, 90);
		
		g.drawString("Average customer wait time:    "+(totalWatiTime/(producer.getSumOfCustomer()-producer.getSumOfLostCustomer())), 600, 120);
		
		
		g.drawString("Average checkout utilisation:    ", 600, 150);
		g.drawString("Average products per trolley:    "+producer.getTotalProducts()/producer.getSumOfCustomer(), 600, 180);

	}
	
	public void shutdownAll() {
		producer.setSwitch_on(false);
		
		for (Consumer consumer : consumers) {
			consumer.setSwitch_on(false);
		}
	}
	
	public Vector<Consumer> getConsumers() {
		return consumers;
	}

	public Producer getProducer() {
		return producer;
	}

	public ExecutorService getService() {
		return service;
	}

	public void setService(ExecutorService service) {
		this.service = service;
	}

	@Override
	public void run() {
//		int i=0;
//		try {
//			TimeUnit.SECONDS.sleep(3);
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
		while(true) {
			this.repaint();
//			System.out.println("paint");
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
