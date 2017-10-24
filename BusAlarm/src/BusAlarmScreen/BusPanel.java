package BusAlarmScreen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import Main.BusAlarm;

public class BusPanel extends JPanel implements Runnable, ActionListener {

	BusAlarm busalarm;

	Image imgbusicon = new ImageIcon(this.getClass().getResource("/BusIcon.png")).getImage();
	ImageIcon icmainScreen = new ImageIcon(this.getClass().getResource("/MainScreen2.jpg"));
	ImageIcon icbusIcon = new ImageIcon(this.getClass().getResource("/BusIcon.png"));
	ImageIcon icbusRoad_green = new ImageIcon(this.getClass().getResource("/busRoad_green.png"));
	ImageIcon icbusRoad_yellow = new ImageIcon(this.getClass().getResource("/busRoad_yelllow.png"));
	ImageIcon icbusRoad_red = new ImageIcon(this.getClass().getResource("/busRoad_red.png"));
	ImageIcon icbusStop = new ImageIcon(this.getClass().getResource("/busStopButton.png"));
	ImageIcon icbusStopSelect = new ImageIcon(this.getClass().getResource("/busStopButtonSelect.png"));
	ImageIcon icmainScreenBar = new ImageIcon(this.getClass().getResource("/MenuBar.png"));
	ImageIcon icfold = new ImageIcon(this.getClass().getResource("/foldbutton2.png"));
	ImageIcon icbusRoad;

	JLabel lbbusInfo;
	JLabel label;
	JButton bbusRoad[][] = new JButton[13][10];
	JButton bbusStop[][] = new JButton[13][10];
	JLabel lbbusStop[][] = new JLabel[13][10];
	JLabel lbmainScreenBar = new JLabel(icmainScreenBar);
	JButton bfoldButton = new JButton(icfold);

	Calendar calendar1 = Calendar.getInstance();
	int year = calendar1.get(Calendar.YEAR);
	int month = calendar1.get(Calendar.MONTH);
	int day = calendar1.get(Calendar.DAY_OF_MONTH);
	int hour = calendar1.get(Calendar.HOUR_OF_DAY);
	int min = calendar1.get(Calendar.MINUTE);
	int sec = calendar1.get(Calendar.SECOND);
	Timer timer;
	JLabel lbPresent;

	boolean menubarVisible = true;

	int i = 0, j = 0;
	int count = 0;
	BusAPI busapi = new BusAPI();

	int x, y;
	int busStop_x, busStop_y;
	Thread th;

	ArrayList Bus_List = new ArrayList();
	ArrayList BusStop_List = new ArrayList();
	Bus bus; // 버스 접근 키
	BusStop busStop;

	static int time;

	public BusPanel(BusAlarm busalarm) {

		super(true);
		setLayout(null);

		setBackground(new Color(255, 243, 225));
		label = new JLabel();
		label.setBackground(Color.YELLOW);
		label.setBounds(0, 0, 1280, 720);

		JScrollBar vbar = new JScrollBar(JScrollBar.VERTICAL, 1000, 80, 0, 1500);
		vbar.setBounds(1245, 0, 26, 686);
		vbar.addAdjustmentListener(new MyAdjustmentListener());

		add(vbar);
		add(label);

		lbPresent = new JLabel(year + "-" + month + "-" + day + "   " + hour + ":" + min + ":" + sec,
				SwingConstants.RIGHT);
		lbPresent.setVerticalAlignment(SwingConstants.TOP);
		lbPresent.setBounds(1010, 5, 220, 40);
		lbPresent.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		add(lbPresent);

		timer = new Timer(1000, this);
		timer.setInitialDelay(0);
		timer.start();

		ActionListener busListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Object obj = e.getSource();
				if (e.getSource() instanceof JButton) {
					final Frame frbusStop = new Frame("busStop");
					frbusStop.setVisible(true);
					frbusStop.addWindowListener(new WindowAdapter() {
						public void windowClosing(WindowEvent e) {
							frbusStop.setVisible(false);
							frbusStop.dispose();
						}
					});
					frbusStop.setSize(400, 250);
					frbusStop.setLocation(200, 200);
					frbusStop.setResizable(false);
					frbusStop.setLocationRelativeTo(null);
				}
			}
		};
		for (i = 0; i < 13; i++) {
			for (j = 0; j < 10; j++) {
				lbbusStop[i][j] = new JLabel();
				lbbusStop[i][j].setText("정류장");// busapi.GetBusStopInfo(count)
				count++;
				lbbusStop[i][j].setBounds(115 * j + 77, 140 * i + 226, 110, 60);
				add(lbbusStop[i][j]);

				bbusStop[i][j] = new JButton(icbusStop);
				bbusStop[i][j].setBounds(115 * j + 93, 140 * i + 220, 16, 16);
				BusAlarm.setButton(bbusStop[i][j]);
				add(bbusStop[i][j]);
				
				bbusStop[i][j].addActionListener(busListener);
			}
			for (j = 0; j < 9; j++) {
				int randomRoad = (int) (Math.random() * 3);

				if (randomRoad == 0) {
					icbusRoad = icbusRoad_green;
				} else if (randomRoad == 1) {
					icbusRoad = icbusRoad_red;
				} else {
					icbusRoad = icbusRoad_yellow;
				}

				bbusRoad[i][j] = new JButton(icbusRoad);
				bbusRoad[i][j].setBounds(115 * j + 96, 140 * i + 220, 120, 12);
				BusAlarm.setButton(bbusRoad[i][j]);
				add(bbusRoad[i][j]);
			}

		}

		// 메뉴바
		lbbusInfo = new JLabel();
		lbbusInfo.setFont(new Font("나눔스퀘어 Bold", Font.PLAIN, 18));
		lbbusInfo.setText("버스정보");// busapi.GetBusInfo()
		lbbusInfo.setSize(500, 100);
		lbbusInfo.setLocation(0, 0);
		add(lbbusInfo);

		lbmainScreenBar.setIcon(icmainScreenBar);
		lbmainScreenBar.setSize(1280, 120);
		lbmainScreenBar.setLocation(0, 0);
		add(lbmainScreenBar);

		bfoldButton.setIcon(icfold);
		bfoldButton.setSize(90, 26);
		bfoldButton.setLocation(BusAlarm.SCREEN_W / 2 - 45, 120);
		BusAlarm.setButton(bfoldButton);
		bfoldButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {

				if (menubarVisible == false) {
					lbmainScreenBar.setVisible(true);
					lbbusInfo.setVisible(true);
					menubarVisible = true;
					bfoldButton.setLocation(BusAlarm.SCREEN_W / 2 - 45, 120);
				} else {
					lbmainScreenBar.setVisible(false);
					lbbusInfo.setVisible(false);
					menubarVisible = false;
					bfoldButton.setLocation(BusAlarm.SCREEN_W / 2 - 45, 0);
				}

			}
		});
		add(bfoldButton);

//		bbusStop[0][0].addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				Object obj = e.getSource();
//				if ((JButton) obj == bbusStop[0][0]) {
//					final Frame frbusStop = new Frame("busStop");
//					frbusStop.setVisible(true);
//					frbusStop.addWindowListener(new WindowAdapter() {
//						public void windowClosing(WindowEvent e) {
//							frbusStop.setVisible(false);
//							frbusStop.dispose();
//						}
//					});
//					frbusStop.setSize(400, 250);
//					frbusStop.setLocation(200, 200);
//					frbusStop.setResizable(false);
//					frbusStop.setLocationRelativeTo(null);
//				}
//			}
//		});

		
		init();
		start();
	}
	
	class MyAdjustmentListener implements AdjustmentListener {
		public void adjustmentValueChanged(AdjustmentEvent e) {

			for (int i = 0; i < 13; i++) {
				for (int j = 0; j < 10; j++) {
					lbbusStop[i][j].setLocation(115 * j + 77, e.getValue() - (-140 * i + 774));
					add(lbbusStop[i][j]);
					bbusStop[i][j].setLocation(115 * j + 93, e.getValue() - (-140 * i + 780));
					BusAlarm.setButton(bbusStop[i][j]);
					add(bbusStop[i][j]);
				}
				for (int j = 0; j < 9; j++) {
					bbusRoad[i][j].setLocation(115 * j + 96, e.getValue() - (-140 * i + 780));
					BusAlarm.setButton(bbusRoad[i][j]);
					add(bbusRoad[i][j]);
				}
			}

			// label.setText(" New Value is " + e.getValue() + " ");
			repaint();

		}
	}

	public void actionPerformed(ActionEvent e) {
		++sec;
		Calendar calendar2 = Calendar.getInstance();
		year = calendar2.get(Calendar.YEAR);
		month = calendar2.get(Calendar.MONTH);
		day = calendar2.get(Calendar.DAY_OF_MONTH);
		hour = calendar2.get(Calendar.HOUR_OF_DAY);
		min = calendar2.get(Calendar.MINUTE);
		sec = calendar2.get(Calendar.SECOND);
		lbPresent.setText(year + "-" + month + "-" + day + "   " + hour + ":" + min + ":" + sec);
	}

	public void init() { // 편의를 위해 init 에서 기본적인 세팅을 합니다.
		x = 100;
		y = 100;
		busStop_x = 85;
		busStop_y = 85;
	}

	public void start() {
		th = new Thread(this);
		th.start();
	}

	public void run() {
		try {
			BusStopProcess();

			while (true) {
				BusProcess();

				repaint();
				Thread.sleep(40);
				time++;
			}
		} catch (Exception e) {
		}
	}

	public void BusStopProcess() {
		busStop = new BusStop(busStop_x, busStop_y);
		BusStop_List.add(busStop);
	}

	public void BusProcess() { // 버스 처리 메소드
		if (time % 600 == 0) {
			bus = new Bus(x, y); // 좌표 체크하여 넘기기
			Bus_List.add(bus); // 버스 추가
		}
	}

	public void paint(Graphics g) {
		super.paint(g);

		for (int i = 0; i < Bus_List.size(); ++i) {
			bus = (Bus) (Bus_List.get(i));
			g.drawImage(imgbusicon, bus.pos.x, bus.pos.y + 15 + lbbusStop[1][1].getY() - 330, this);
			bus.move();
		}
	}

}
