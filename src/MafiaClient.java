import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javafx.embed.swing.*;
import javafx.scene.media.*;
import java.util.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

class MafiaClient extends JFrame {
   
	Container contentPane;
	JPanel panel_Main, panel_Chat, panel_List, panel_Timer, panel_Role, backgroundPanel,  timer_Panel, rolePanel;
	JButton btn_Ready, btn_Exit, btn_Ready2;
	JLabel label_Timer, label_Timer_Back, label_chat,label_chat_back, backgroundLabel, label_Role;
	Label Client1, Client2, Client3, Client4, Client5, Client6 , whats_Your;
	JTextArea textArea, textArea2;
	JTextField textField;
	JScrollPane scrollPane,scrollPane2;
	Color color;
	ImageIcon changeIcon, changed_Icon, job_Nothing, change_Icon_Ready;
	Image changeImage, changed_Image;
	BufferedImage job_img, btn_img;


	ArrayList<String> plv = new ArrayList<String>();
	Object[] playerList = new Object[6];
	String[] vitalSign = {"생존","생존","생존","생존","생존","생존"};
	String job, myName;
	String deadManStr;

	String ip;
	int port = 5500;
	String playerName, playerIdx;

	MafiaClient(){
	  setUI();
	  startChat();
	}

	void setUI(){
	//기본 GUI
		setTitle("마피아 게임 Ver 1.0");

		//배경패널//
		changeIcon = new ImageIcon(".\\img\\background.png");			
		changeIcon = new ImageIcon(".\\img\\background.png");
		changeImage = changeIcon.getImage();  //ImageIcon을 Image로 변환.
		changed_Image= changeImage.getScaledInstance(944, 621, java.awt.Image.SCALE_SMOOTH); // 변경된이미지 => 변경할이미지
		changed_Icon = new ImageIcon(changed_Image); //Image로 ImageIcon 생성

		backgroundLabel = new JLabel("",changed_Icon,JLabel.CENTER);
		backgroundLabel.setVerticalTextPosition(JLabel.CENTER);
		backgroundLabel.setHorizontalTextPosition(JLabel.RIGHT);
		backgroundPanel = new JPanel(); //배경패널
		backgroundPanel.add(backgroundLabel);

		backgroundPanel.setBounds(0, 0, 156, 500);	
		contentPane = new Container();
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));

		panel_Main = new JPanel();
		panel_Main.setFont(new Font("나눔바른고딕", Font.PLAIN, 14));
		panel_Main.setBackground(new Color(30, 26, 23));
		panel_Main.setLayout(null);
		//채팅 영역
		JPanel panel_Chat = new JPanel();
		panel_Chat.setBounds(30, 30, 650, 560);
		panel_Chat.setLayout(null);
		panel_Chat.setOpaque(true);
		//panel_Chat.setVisible(true);
		//textArea2.setEditable(false);

		textArea = new JTextArea();
		textArea.setFont(new Font("나눔바른고딕", Font.PLAIN, 14));
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setBounds(0, 0, 640, 520);
		textArea.setForeground(Color.WHITE);
		textArea.setOpaque(true);
		textArea.setBorder(new LineBorder(new Color(7, 9, 9), 2, false));
		textArea.setBackground(new Color(7, 9, 9));
		Image img = new ImageIcon(".\\img\\chat_back.png").getImage();
		/*
		JTextArea textArea2 = new JTextArea(){
			{setOpaque(false);}
			public void paintComponent(Graphics g){
				g.drawImage(img,0,0,null);
				super.paintComponent(g);
			}
		};
		textArea2.setBounds(0,0,650,530);
		//panel_Chat.add(textArea2);
		//panel_Chat.add(textArea);*/

		scrollPane = new JScrollPane(textArea);
		scrollPane.setFont(new Font("나눔바른고딕", Font.PLAIN, 14));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(0, 0, 650, 530);
		scrollPane.setOpaque(false);
		scrollPane.setBackground(Color.RED);
		scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
		scrollPane.setBorder(new LineBorder(new Color(7, 9, 9), 2, false));
		
		textField = new JTextField();
		textField.setBorder(new LineBorder(new Color(255, 255, 255), 2, false));
		textField.setBounds(0, 530, 650, 35);
		textField.setColumns(10);
		textField.setOpaque(false);
	  
		//참여자 영역
		JPanel panel_List = new JPanel();
		panel_List.setBounds(710, 240, 200, 240);
		panel_List.setBackground(new Color(24, 23, 23));
		panel_List.setLayout(null);
	  
		Client1 = new Label("참여자 대기중...");
		Client1.setFont(new Font("나눔바른고딕", Font.BOLD, 13));
		Client1.setAlignment(Label.CENTER);
		Client1.setBackground(new Color(24, 23, 23));
		Client1.setBounds(0, 3, 120, 20);
		Client1.setForeground(Color.WHITE);
	  
		Client2 = new Label("참여자 대기중...");
		Client2.setFont(new Font("나눔바른고딕", Font.BOLD, 13));
		Client2.setAlignment(Label.CENTER);
		Client2.setBackground(new Color(24, 23, 23));
		Client2.setBounds(0, 23, 120, 20);
		Client2.setForeground(Color.WHITE);
	  
		Client3 = new Label("참여자 대기중...");
		Client3.setFont(new Font("나눔바른고딕", Font.BOLD, 13));
		Client3.setAlignment(Label.CENTER);
		Client3.setBackground(new Color(24, 23, 23));
		Client3.setBounds(0, 43, 120, 20);
		Client3.setForeground(Color.WHITE);
	  
		Client4 = new Label("참여자 대기중...");
		Client4.setFont(new Font("나눔바른고딕", Font.BOLD, 13));
		Client4.setAlignment(Label.CENTER);
		Client4.setBackground(new Color(24, 23, 23));
		Client4.setBounds(0, 63, 120, 20);
		Client4.setForeground(Color.WHITE);
	  
		Client5 = new Label("참여자 대기중...");
		Client5.setFont(new Font("나눔바른고딕", Font.BOLD, 13));
		Client5.setAlignment(Label.CENTER);
		Client5.setBackground(new Color(24, 23, 23));
		Client5.setBounds(0, 83, 120, 20);
		Client5.setForeground(Color.WHITE);
	  
		Client6 = new Label("참여자 대기중...");
		Client6.setFont(new Font("나눔바른고딕", Font.BOLD, 13));
		Client6.setAlignment(Label.CENTER);
		Client6.setBackground(new Color(24, 23, 23));
		Client6.setBounds(0, 103, 120, 20);
		Client6.setForeground(Color.WHITE);

	  //타이머 영역
		JLabel label_Timer_Back = new JLabel(new ImageIcon(".\\img\\timer.png"));
		label_Timer = new JLabel("00 : 00");
		//label_Timer.setHorizontalTextPosition(JLabel.CENTER);
		//label_Timer.setHorizontalAlignment(JLabel.CENTER);
		label_Timer.setFont(new Font("나눔바른고딕", Font.PLAIN, 30));
		label_Timer.setForeground(Color.WHITE);

		JPanel panel_Timer = new JPanel();
		panel_Timer.setBounds(710, 130, 200, 90);
		panel_Timer.setLayout(null);
		panel_Timer.add(label_Timer);
		panel_Timer.setOpaque(false);
		panel_Timer.setForeground(Color.WHITE);
	  
		label_Timer.setHorizontalTextPosition(SwingConstants.CENTER);
		label_Timer.setHorizontalAlignment(SwingConstants.CENTER);
		label_Timer_Back.setBounds(0, 0, 200, 90);
		label_Timer.setBounds(20, 20, 158, 57);

	  //역할 안내 영역
		JPanel panel_Role = new JPanel();
		panel_Role.setBorder(new LineBorder(new Color(255, 255, 255), 2, true));
		panel_Role.setBounds(710, 500, 200, 89);
		panel_Role.setBackground(Color.WHITE);
		panel_Role.setLayout(null);
	  
		whats_Your = new Label(/*"당신의 역할은"*/);
		//whats_Your.setFont(new Font("나눔바른고딕", Font.BOLD, 12));
		whats_Your.setAlignment(Label.CENTER);
		//whats_Your.setBackground(Color.WHITE);
		//whats_Your.setBounds(40, 5, 120, 20);
	  
		try{
			job_img = ImageIO.read(new File(".\\img\\job_nothing.png"));
			label_Role = new JLabel(new ImageIcon(job_img));
			
			//label_Role.setFont(new Font("나눔바른고딕", Font.BOLD, 30));
			//label_Role.setAlignment(Label.CENTER);
			label_Role.setHorizontalTextPosition(JLabel.CENTER);
			label_Role.setHorizontalAlignment(JLabel.CENTER);
			//label_Role.setBackground(Color.WHITE);
			//label_Role.setBounds(40, 35, 120, 30);
			label_Role.setBounds(0, -1, 200, 90);
			panel_Role.add(label_Role);
			JPanel rolePanel = new JPanel();
			rolePanel.add(label_Role);
			rolePanel.setOpaque(false); 
		}catch(IOException ie){}

		//버튼 영역 (준비/종료)
		try{
			btn_img = ImageIO.read(new File(".\\img\\ready.png"));
			btn_Ready = new JButton(new ImageIcon(btn_img));
			btn_Ready.setPressedIcon(new ImageIcon(".\\img\\ready_2.png"));
			btn_Ready.setSelectedIcon(new ImageIcon(".\\img\\ready_2.png"));
			btn_Ready.setFocusPainted(false);
			btn_Ready.setBorderPainted(false);
			btn_Ready.setContentAreaFilled(false);
			btn_Ready.setBounds(710, 30, 200, 80);
			btn_Ready.setOpaque(false);
		}catch(IOException ie){}

		//contentPane.add(panel_Main);
		contentPane.add(backgroundLabel);
		//panel_Main.add(scrollPane);
		//panel_Main.add(panel_Chat);
		backgroundLabel.add(panel_Chat);
		//textArea2.add(textArea);
		panel_Chat.add(textField);
		panel_Chat.add(scrollPane);
		//panel_Main.add(panel_List);
		backgroundLabel.add(panel_List);
		panel_List.add(Client1);
		panel_List.add(Client2);
		panel_List.add(Client3);
		panel_List.add(Client4);
		panel_List.add(Client5);
		panel_List.add(Client6);
		//panel_Main.add(panel_Timer);
		backgroundLabel.add(panel_Timer);
		panel_Timer.add(label_Timer_Back);
		label_Timer_Back.add(label_Timer);
		//panel_Main.add(panel_Role);
		backgroundLabel.add(panel_Role);
		panel_Role.add(whats_Your);
		panel_Role.add(label_Role);
		//panel_Main.add(btn_Ready);
		backgroundLabel.add(btn_Ready);
	  
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 950, 650);
	}

	public void startChat(){
		String nickName = MafiaLogin.id;
		String ip = MafiaLogin.ip;

		try{
			Socket s = new Socket(ip, port);
			Sender sender = new Sender(s, nickName);
			Listener listener = new Listener(s);
			new Thread(sender).start();
			new Thread(listener).start();
			
			// 이벤트 리스너 추가
			textField.addKeyListener(new Sender(s, nickName));
			btn_Ready.addActionListener(new Sender(s, nickName));
		}catch(UnknownHostException uh){
			JOptionPane.showMessageDialog(null, "호스트를 찾을 수 없습니다!", "ERROR", JOptionPane.WARNING_MESSAGE);
		}catch(IOException io){
			JOptionPane.showMessageDialog(null, "서버 접속 실패!\n서버가 닫혀 있는 것 같습니다.", "ERROR", JOptionPane.WARNING_MESSAGE);
			System.exit(0);
		}
	}

	class Sender extends Thread implements KeyListener, ActionListener {
		DataOutputStream dos;
		Socket s;
		String nickName;

		Sender(Socket s, String nickName){
			this.s = s;
			try{
				dos = new DataOutputStream(this.s.getOutputStream());
				this.nickName = nickName;
				myName = nickName;
			}catch(IOException io){}
		}

		public void run(){
			try{
				dos.writeUTF(nickName);
			}catch(IOException io){}
		}
		
		public void actionPerformed(ActionEvent e){
			if(e.getSource() == btn_Ready){ // '준비' 버튼
				try{
					btn_img = ImageIO.read(new File(".\\img\\ready_2.png"));
					dos.writeUTF(">> " + nickName + " 님 준비 완료 !");
					dos.flush();
					dos.writeUTF("//Ready");
					dos.flush();
				}catch(IOException io){}
					btn_Ready.setIcon(new ImageIcon(btn_img));
					btn_Ready.setEnabled(false);
			}
		}

		public void keyReleased(KeyEvent e){ // 채팅 입력
			if(e.getKeyCode() == KeyEvent.VK_ENTER){
				String chat = textField.getText();
				textField.setText("");
				if(job.equals("사망자")){
					textArea.append("죽은자는 말을 할 수 없습니다.");
				}else{
					try{
						dos.writeUTF(nickName + " >> " + chat);
						dos.flush();
					}catch(IOException io){}
				}
			}
		}
		public void keyTyped(KeyEvent e){}
		public void keyPressed(KeyEvent e){}
	}

	class Listener extends Thread {
		Socket s;
		DataInputStream dis;
		DataOutputStream dos;

		Listener(Socket s){
			this.s = s;
			try{
				dis = new DataInputStream(this.s.getInputStream());
				dos = new DataOutputStream(this.s.getOutputStream());

			}catch(IOException io){}
		}

		public void run(){
			while(dis != null){
				try{
					String msg = dis.readUTF();
					if(msg.startsWith("//CList")){ // 명령어 : 클라이언트 목록 갱신
						playerName = msg.substring(7, msg.indexOf(" "));
						//playerScore = msg.substring(msg.indexOf(" ") + 1, msg.indexOf("#"));
						playerIdx = msg.substring(msg.indexOf(" ") + 1);
						updateClientList();		   // 클라이언트 목록 갱신
					}else if(msg.indexOf("//mafia") != -1){
						String mafiaTalk = msg.substring(13);
						if(job.equals("마피아")){
							textArea.append("마피아(마피아만보임) >> "+mafiaTalk+"\n");
						}
					}else if(msg.startsWith("//U!mafia")){
						try{
							job_img = ImageIO.read(new File(".\\img\\job_mafia.png"));
						}catch(IOException ie){}
						label_Role.setIcon(new ImageIcon(job_img));
						job = "마피아";
					}else if(msg.startsWith("//U!Doctor")){
						try{
							job_img = ImageIO.read(new File(".\\img\\job_doctor.png"));
						}catch(IOException ie){}
						label_Role.setIcon(new ImageIcon(job_img));
						job = "의사";
					}else if(msg.startsWith("//U!Police")){
						try{
							job_img = ImageIO.read(new File(".\\img\\job_police.png"));
						}catch(IOException ie){}
						label_Role.setIcon(new ImageIcon(job_img));
						job = "경찰";
					}else if(msg.startsWith("//U!Citizen")){
						try{
							job_img = ImageIO.read(new File(".\\img\\job_citizen.png"));
						}catch(IOException ie){}
						label_Role.setIcon(new ImageIcon(job_img));
						job = "시민";
					}else if(msg.startsWith("//U!Died")){
						if(job.equals("사망자")){
							dos.writeUTF("죽인 사람을 다시 죽이다니... 눈뜨고는 보기 힘든 잔혹한 일이군요...");
							dos.writeUTF("어찌되었든 이 마을에서 살아남으려면 적이 되는 존재를 제거해야만 합니다.");
							dos.writeUTF("그러니 자신이 살아남을 기회를 함부로 낭비해서는 안된다고 말씀드리고 싶군요.\n");
						}else{
							textArea.append("운명의 여신이 당신에게는 자비롭지 못했군요. 당신의 몸은 싸늘하게 식어가고 있습니다.\n");
							if(job.equals("마피아")){
								dos.writeUTF("//M!nusMaf!aNumber");
							}else{
								dos.writeUTF("//M!nusC!t!zenNumber");
							}
							job = "사망자";
							try{
								job_img = ImageIO.read(new File(".\\img\\job_dead.png"));
							}catch(IOException ie){}
							label_Role.setIcon(new ImageIcon(job_img));
						}
					}else if(msg.startsWith("//C!ear!")){
						textArea.setText("");
					}else if(msg.startsWith("//Timer")){
						label_Timer.setText(msg.substring(8));
					}else if(msg.startsWith("//Quit")){
						quit();
					}else if(msg.startsWith("//Night")){
						night();
					}else if(msg.startsWith("//Murder")){
						deadManStr = msg.substring(9);
						int deadManNum = Integer.parseInt(deadManStr);
						Object deadMan = playerList[deadManNum];
						deadNotice(deadMan, deadManNum);
						if(deadMan.equals(myName)){
							dos.writeUTF("//!!Died " + deadMan);
						}
					}else if(msg.startsWith("//GameEnd")){
						textArea.append("게임이 10초뒤에 종료됩니다.");
						try{
							Thread.sleep(10000);
							System.exit(0);
						}catch(InterruptedException ite){
						}
					}else{
						textArea.append(msg + "\n");
						scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
					}
				}catch(IOException io){
					textArea.append(">> 서버와의 연결이 끊어졌습니다. \n>> 3초 후 프로그램을 종료합니다 ..");
					try{
						Thread.sleep(3000);
						System.exit(0);
					}catch(InterruptedException it){}
				}
			}
		}

		public void deadNotice(Object deadMan, int deadManNum){
			textArea.append("\n");
			textArea.append(deadMan+"의 시체가 발견되었습니다... 이 사람의 정체는 과연 무엇이였을까요...\n");
			vitalSign[deadManNum] = "사망";
			if(deadManNum == 0){
				Client1.setText(deadMan+" ("+vitalSign[deadManNum]+")");
			}else if(deadManNum == 1){
				Client2.setText(deadMan+" ("+vitalSign[deadManNum]+")");
			}else if(deadManNum == 2){
				Client3.setText(deadMan+" ("+vitalSign[deadManNum]+")");
			}else if(deadManNum == 3){
				Client4.setText(deadMan+" ("+vitalSign[deadManNum]+")");
			}else if(deadManNum == 4){
				Client5.setText(deadMan+" ("+vitalSign[deadManNum]+")");
			}else if(deadManNum == 5){
				Client6.setText(deadMan+" ("+vitalSign[deadManNum]+")");
			}
		}

		public void updateClientList(){ // 클라이언트 목록 추가
			if(Integer.parseInt(playerIdx) == 0){
				Client1.setText(playerName+" ("+vitalSign[0]+")");
				playerList[0]=playerName;
				deleteClientList();
			}else if(Integer.parseInt(playerIdx) == 1){
				Client2.setText(playerName+" ("+vitalSign[1]+")");
				playerList[1]=playerName;
				deleteClientList();
			}else if(Integer.parseInt(playerIdx) == 2){
				Client3.setText(playerName+" ("+vitalSign[2]+")");
				playerList[2]=playerName;
				deleteClientList();
			}else if(Integer.parseInt(playerIdx) == 3){
				Client4.setText(playerName+" ("+vitalSign[3]+")");
				playerList[3]=playerName;
				deleteClientList();
			}else if(Integer.parseInt(playerIdx) == 4){
				Client5.setText(playerName+" ("+vitalSign[4]+")");
				playerList[4]=playerName;
				deleteClientList();
			}else if(Integer.parseInt(playerIdx) == 5){
				Client6.setText(playerName+" ("+vitalSign[5]+")");
				playerList[5]=playerName;
				deleteClientList();
			}
		}
		public void deleteClientList(){ // 클라이언트 목록 제거
			if(Integer.parseInt(playerIdx) == 0){
				Client2.setText("참여자 대기중...");
				Client3.setText("참여자 대기중...");
				Client4.setText("참여자 대기중...");
				Client5.setText("참여자 대기중...");
				Client6.setText("참여자 대기중...");
				playerList[1]=null;
			}else if(Integer.parseInt(playerIdx) == 1){
				Client3.setText("참여자 대기중...");
				Client4.setText("참여자 대기중...");
				Client5.setText("참여자 대기중...");
				Client6.setText("참여자 대기중...");
				playerList[2]=null;
			}else if(Integer.parseInt(playerIdx) == 2){
				Client4.setText("참여자 대기중...");
				Client5.setText("참여자 대기중...");
				Client6.setText("참여자 대기중...");
				playerList[3]=null;
			}else if(Integer.parseInt(playerIdx) == 3){
				Client5.setText("참여자 대기중...");
				Client6.setText("참여자 대기중...");
				playerList[4]=null;
			}else if(Integer.parseInt(playerIdx) == 4){
				Client6.setText("참여자 대기중...");
				playerList[5]=null;
			}
		}
		public void quit(){
			textArea.append("처형자를 선택할 시간이 왔습니다...\n");
			if(!job.equals("사망자")){
				try{
					Thread.sleep(1000);
					Object selectedName 
					= JOptionPane.showInputDialog(null, "처형하고 싶은 사람을 선택하세요.", "Target",
												  JOptionPane.INFORMATION_MESSAGE, null,
												  playerList, playerList[0]);
					int ban = Arrays.binarySearch(playerList, selectedName);
					if(ban == -2){
						dos.writeUTF("//Ban "+"3");
						//dos.writeUTF("" +ban);
					}else if(ban == -7){
						dos.writeUTF("//Ban "+"4");
					}else if(ban == -1){
						dos.writeUTF("//Ban "+"5");
					}else{
					dos.writeUTF("//Ban "+ban);
					//dos.writeUTF("" +ban);
					}
					dos.writeUTF("//OK");
				}catch(InterruptedException ite){
				}catch(IOException ie){
				}
			}else{
				try{
					textArea.append("죽은자는 싸늘하게 식은 입을 열지 않고있다...");
					textArea.append("");
					dos.writeUTF("//OK"); 
				}catch(IOException ie){
				}
			}
		}
		public void night(){
			textArea.append("밤이 되었습니다... 악당들이 어둠속에서 움직이기 시작합니다...\n");
			try{
				if(job.equals("마피아")){
					Object selectedName 
					= JOptionPane.showInputDialog(null, "살해할 사람을 선택하세요.", "Target",
												  JOptionPane.INFORMATION_MESSAGE, null,
												 playerList, playerList[0]);
					int victim = Arrays.binarySearch(playerList, selectedName);
					if(victim == -2){
						dos.writeUTF("//Kill "+"3");
						//dos.writeUTF("" +ban);
					}else if(victim == -7){
						dos.writeUTF("//Kill "+"4");
					}else if(victim == -1){
						dos.writeUTF("//Kill "+"5");
					}else{
					dos.writeUTF("//Kill "+victim);
					//dos.writeUTF("" +ban);
					}
					dos.writeUTF("//OK");
				}else if(job.equals("의사")){
					Object selectedName 
					= JOptionPane.showInputDialog(null, "살해의 위험에서부터 구하고싶은 사람을 선택하세요.", "Target",
												  JOptionPane.INFORMATION_MESSAGE, null,
												  playerList, playerList[0]);
					int heal = Arrays.binarySearch(playerList, selectedName);
					if(heal == -2){
						dos.writeUTF("//Heal "+"3");
						//dos.writeUTF("" +ban);
					}else if(heal == -7){
						dos.writeUTF("//Heal "+"4");
					}else if(heal == -1){
						dos.writeUTF("//Heal "+"5");
					}else{
					dos.writeUTF("//Heal "+heal);
					//dos.writeUTF("" +heal);
					}
					//dos.writeUTF(""+heal);
					dos.writeUTF("//OK");
				}else if(job.equals("경찰")){
					Object selectedName 
					= JOptionPane.showInputDialog(null, "정체를 알고싶은 사람을 선택하세요.", "Target",
												  JOptionPane.INFORMATION_MESSAGE, null,
												  playerList, playerList[0]);
					dos.writeUTF("//Check "+selectedName);
					dos.writeUTF("//OK");
				}else if(job.equals("사망자")){
					textArea.append("죽은자는 싸늘하게 식은 입을 열지 않고있다...");
					textArea.append("");
					dos.writeUTF("//OK");
				}else{
					dos.writeUTF("//OK");
				}
			}catch(IOException ie){
			}
		}
	}
}