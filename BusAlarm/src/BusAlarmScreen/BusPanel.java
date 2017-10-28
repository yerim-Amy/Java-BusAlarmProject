package BusAlarmScreen;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
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
	ImageIcon icbusSeat= new ImageIcon(this.getClass().getResource("/BusSeat.png"));

	JLabel lbbusInfo;
	JLabel label;
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
	int busStop_x=0, busStop_y=0;
	Thread th;

	ArrayList Bus_List = new ArrayList();
	ArrayList BusStop_List = new ArrayList();
	Bus bus; // 버스 접근 키
	BusStop busStop;
	BusRoad bbusRoad;


	static int time;
	static int busCnt;
	
	ActionListener busListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() instanceof BusStop) {
				final Frame frbusStop = new Frame("busStop");
				JPanel p= new JPanel();
				JLabel l=new JLabel("랄랄랄");
				p.setBackground(Color.WHITE);
				p.add(l);
				frbusStop.add(p);
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
			if (e.getSource() instanceof Bus) {
				final Frame frbusStop = new Frame("Bus");
				JPanel p =new JPanel();
				p.setLayout(new FlowLayout());
				JLabel lbbusSeat = new JLabel(icbusSeat);
				lbbusSeat.setBounds(0,0,263,523);
				p.add(lbbusSeat);
				JLabel l=new JLabel("현재 승객수 : "+Integer.toString(bus.busPassenger));
				p.setBackground(Color.WHITE);
				p.add(l);
				frbusStop.add(p);
				frbusStop.setVisible(true);
				frbusStop.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						frbusStop.setVisible(false);
						frbusStop.dispose();
					}
				});
				frbusStop.setSize(300, 600);
				frbusStop.setLocation(200, 200);
				frbusStop.setResizable(false);
				frbusStop.setLocationRelativeTo(null);
			}
		}
	};

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

		for (i = 0; i < 13; i++) {
			for (j = 0; j < 10; j++) {
				lbbusStop[i][j] = new JLabel();
				lbbusStop[i][j].setText("정류장");// busapi.GetBusStopInfo(count)
				count++;
				lbbusStop[i][j].setBounds(115 * j + 77, 140 * i + 226, 110, 60);
				add(lbbusStop[i][j]);

				busStop_x=115 * j + 90;
				busStop_y=140 * i + 227;

				busStop=new BusStop(busStop_x, busStop_y);
				busStop.setIcon(icbusStop);
				busStop.setBounds(busStop.pos.x, busStop.pos.y,16,16);
				BusAlarm.setButton(busStop);
				BusStop_List.add(busStop);
				add(busStop);
				
				busStop.addActionListener(busListener);
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

//				bbusRoad[i][j] = new JButton(icbusRoad);
//				bbusRoad[i][j].setBounds(115 * j + 96, 140 * i + 220, 120, 12);
//				BusAlarm.setButton(bbusRoad[i][j]);
//				add(bbusRoad[i][j]);
			}

		}
		addMenu();

		init();
		start();
	}

	class MyAdjustmentListener implements AdjustmentListener {
		public void adjustmentValueChanged(AdjustmentEvent e) {

			for (int i = 0; i < 13; i++) {
				for (int j = 0; j < 10; j++) {
					lbbusStop[i][j].setLocation(115 * j + 77, e.getValue() - (-140 * i + 774));
					add(lbbusStop[i][j]);
				}
		
//				for (int j = 0; j < 9; j++) {
//					bbusRoad[i][j].setLocation(115 * j + 96, e.getValue() - (-140 * i + 780));
//					BusAlarm.setButton(bbusRoad[i][j]);
//					add(bbusRoad[i][j]);
//				}
			}
			for(int i=0; i<BusStop_List.size();++i)
			{
				busStop=(BusStop)(BusStop_List.get(i));
				busStop.setBounds(busStop.pos.x,busStop.pos.y+ lbbusStop[1][1].getY() - 366,16,16);
				//System.out.println(busStop.pos.x+" "+busStop.pos.y);
				add(busStop);
			}
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
	}

	public void start() {
		th = new Thread(this);
		th.start();
	}

	public void run() {
		try {
			while (true) {
				BusProcess();
				for (int i = 0; i < Bus_List.size(); ++i) {
					bus = (Bus) (Bus_List.get(i));
					bus.move();
					bus.setBounds(bus.pos.x, bus.pos.y +lbbusStop[1][1].getY() - 315, 48, 65);
				//	System.out.println(bus.pos.x+" "+bus.pos.y);//100,240,380,....
					for(int j=0; j<BusStop_List.size();++j)
					{
						busStop=(BusStop)(BusStop_List.get(j));
						if(bus.pos.x+10==busStop.pos.x && bus.pos.y+127==busStop.pos.y)
						{
							System.out.println(bus.name+" 도착");
							bus.arriveBus();
						}
					}
					
					add(bus);
				}
				Thread.sleep(10);
				time++;

			}
		} catch (Exception e) {
		}
	}

	public void BusProcess() { // 버스 처리 메소드

		if (time % 600 == 0) {
			bus = new Bus(x, y,busCnt); // 좌표 체크하여 넘기기
			busCnt++;
			Bus_List.add(bus); // 버스 추가
			bus.setIcon(icbusIcon);
			bus.setBounds(bus.pos.x, bus.pos.y + lbbusStop[1][1].getY() - 315, 48, 65);
			BusAlarm.setButton(bus);
			bus.addActionListener(busListener);
			add(bus);

		}
	}

	public void addMenu() {
		// 메뉴바
		lbbusInfo = new JLabel();
		lbbusInfo.setFont(new Font("나눔스퀘어 Bold", Font.PLAIN, 18));
		lbbusInfo.setText("버스정보");// busapi.GetBusInfo()
		lbbusInfo.setSize(500, 100);
		lbbusInfo.setLocation(0, 0);
		add(lbbusInfo);

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
	}

}
