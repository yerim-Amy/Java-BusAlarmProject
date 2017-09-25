package BusAlarmScreen;

import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Main.BusAlarm;

public class Bus{
	
	int x,y;
	String busType;
	
	BusAlarm busalarm;
	
	public Bus(int x, int y, String busType)
	{
		this.x=x;
		this.y=y;
		this.busType=busType;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setBusType(String busType) {
		this.busType = busType;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String getBusType() {
		return busType;
	}
	
	
//	public void paint(Graphics g)
//	{
//		if(busType.equals("short"))
//		{
//			g.drawImage(icbusIcon, x, y, null);
//		}
//		else if(busType.equals("long"))
//		{
//			g.drawImage(icbusIcon, x, y, null);
//		}
//	}
	public void move()
	{
		x+=BusAlarm.BUS_SPEED;
	}
//	@Override
//	public void run() 
//	{
//		try
//		{
//			while(true)
//			{
//				move();
//				Thread.sleep(BusAlarm.SLEEP_TIME);
//			
//			}
//		}
//		catch(Exception e)
//		{
//			System.err.println(e.getMessage());
//		}
//	}
	
}
