package BusAlarmScreen;

import java.awt.Point;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;

public class Bus extends JButton{

	Point pos; //���� ��ǥ
	int busPassenger=0;//���� �°���
	String degreeOfCongestion =null;//����ȥ�⵵
	String name;
	Bus(int x, int y, int busCnt) {
		pos = new Point(x, y); //������ ��ǥ�� üũ
		name=Integer.toString(busCnt);
	}
	
	int bus_speed=1;

	public void move() { // ���� �̵��� ���� �޼ҵ�
				
		if(pos.x>1100 || pos.x<10)
		{
			bus_speed=-bus_speed;
			pos.y+=140;			
		}
		pos.x+=bus_speed;	
		
	}
	
	public void countCongestion(int busPassenger)
	{
		if(busPassenger>40){
			degreeOfCongestion="ȥ��";
		}
		else if(busPassenger>25){
			degreeOfCongestion="����";
		}else {
			degreeOfCongestion="����";
		}
	}
	
	public void arriveBus()
	{
		bus_speed=0;
		Timer m_timer = new Timer();
		TimerTask m_task=new TimerTask(){
			public void run(){
				bus_speed=1;
			}
		};
		m_timer.schedule(m_task, 1000);
		
	}
	
}
