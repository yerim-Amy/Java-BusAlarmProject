package BusAlarmScreen;

import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class BusStop {

	ImageIcon icbusStop = new ImageIcon(this.getClass().getResource("/busStopButton.png"));

	Point pos; //���� ��ǥ
	JButton bbusStop;

	BusStop(int x, int y) {
		pos = new Point(x, y); //������ ��ǥ�� üũ
	}
	

}
