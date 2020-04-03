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
	String[] vitalSign = {"����","����","����","����","����","����"};
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
	//�⺻ GUI
		setTitle("���Ǿ� ���� Ver 1.0");

		//����г�//
		changeIcon = new ImageIcon(".\\img\\background.png");			
		changeIcon = new ImageIcon(".\\img\\background.png");
		changeImage = changeIcon.getImage();  //ImageIcon�� Image�� ��ȯ.
		changed_Image= changeImage.getScaledInstance(944, 621, java.awt.Image.SCALE_SMOOTH); // ������̹��� => �������̹���
		changed_Icon = new ImageIcon(changed_Image); //Image�� ImageIcon ����

		backgroundLabel = new JLabel("",changed_Icon,JLabel.CENTER);
		backgroundLabel.setVerticalTextPosition(JLabel.CENTER);
		backgroundLabel.setHorizontalTextPosition(JLabel.RIGHT);
		backgroundPanel = new JPanel(); //����г�
		backgroundPanel.add(backgroundLabel);

		backgroundPanel.setBounds(0, 0, 156, 500);	
		contentPane = new Container();
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));

		panel_Main = new JPanel();
		panel_Main.setFont(new Font("�����ٸ����", Font.PLAIN, 14));
		panel_Main.setBackground(new Color(30, 26, 23));
		panel_Main.setLayout(null);
		//ä�� ����
		JPanel panel_Chat = new JPanel();
		panel_Chat.setBounds(30, 30, 650, 560);
		panel_Chat.setLayout(null);
		panel_Chat.setOpaque(true);
		//panel_Chat.setVisible(true);
		//textArea2.setEditable(false);

		textArea = new JTextArea();
		textArea.setFont(new Font("�����ٸ����", Font.PLAIN, 14));
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
		scrollPane.setFont(new Font("�����ٸ����", Font.PLAIN, 14));
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
	  
		//������ ����
		JPanel panel_List = new JPanel();
		panel_List.setBounds(710, 240, 200, 240);
		panel_List.setBackground(new Color(24, 23, 23));
		panel_List.setLayout(null);
	  
		Client1 = new Label("������ �����...");
		Client1.setFont(new Font("�����ٸ����", Font.BOLD, 13));
		Client1.setAlignment(Label.CENTER);
		Client1.setBackground(new Color(24, 23, 23));
		Client1.setBounds(0, 3, 120, 20);
		Client1.setForeground(Color.WHITE);
	  
		Client2 = new Label("������ �����...");
		Client2.setFont(new Font("�����ٸ����", Font.BOLD, 13));
		Client2.setAlignment(Label.CENTER);
		Client2.setBackground(new Color(24, 23, 23));
		Client2.setBounds(0, 23, 120, 20);
		Client2.setForeground(Color.WHITE);
	  
		Client3 = new Label("������ �����...");
		Client3.setFont(new Font("�����ٸ����", Font.BOLD, 13));
		Client3.setAlignment(Label.CENTER);
		Client3.setBackground(new Color(24, 23, 23));
		Client3.setBounds(0, 43, 120, 20);
		Client3.setForeground(Color.WHITE);
	  
		Client4 = new Label("������ �����...");
		Client4.setFont(new Font("�����ٸ����", Font.BOLD, 13));
		Client4.setAlignment(Label.CENTER);
		Client4.setBackground(new Color(24, 23, 23));
		Client4.setBounds(0, 63, 120, 20);
		Client4.setForeground(Color.WHITE);
	  
		Client5 = new Label("������ �����...");
		Client5.setFont(new Font("�����ٸ����", Font.BOLD, 13));
		Client5.setAlignment(Label.CENTER);
		Client5.setBackground(new Color(24, 23, 23));
		Client5.setBounds(0, 83, 120, 20);
		Client5.setForeground(Color.WHITE);
	  
		Client6 = new Label("������ �����...");
		Client6.setFont(new Font("�����ٸ����", Font.BOLD, 13));
		Client6.setAlignment(Label.CENTER);
		Client6.setBackground(new Color(24, 23, 23));
		Client6.setBounds(0, 103, 120, 20);
		Client6.setForeground(Color.WHITE);

	  //Ÿ�̸� ����
		JLabel label_Timer_Back = new JLabel(new ImageIcon(".\\img\\timer.png"));
		label_Timer = new JLabel("00 : 00");
		//label_Timer.setHorizontalTextPosition(JLabel.CENTER);
		//label_Timer.setHorizontalAlignment(JLabel.CENTER);
		label_Timer.setFont(new Font("�����ٸ����", Font.PLAIN, 30));
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

	  //���� �ȳ� ����
		JPanel panel_Role = new JPanel();
		panel_Role.setBorder(new LineBorder(new Color(255, 255, 255), 2, true));
		panel_Role.setBounds(710, 500, 200, 89);
		panel_Role.setBackground(Color.WHITE);
		panel_Role.setLayout(null);
	  
		whats_Your = new Label(/*"����� ������"*/);
		//whats_Your.setFont(new Font("�����ٸ����", Font.BOLD, 12));
		whats_Your.setAlignment(Label.CENTER);
		//whats_Your.setBackground(Color.WHITE);
		//whats_Your.setBounds(40, 5, 120, 20);
	  
		try{
			job_img = ImageIO.read(new File(".\\img\\job_nothing.png"));
			label_Role = new JLabel(new ImageIcon(job_img));
			
			//label_Role.setFont(new Font("�����ٸ����", Font.BOLD, 30));
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

		//��ư ���� (�غ�/����)
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
			
			// �̺�Ʈ ������ �߰�
			textField.addKeyListener(new Sender(s, nickName));
			btn_Ready.addActionListener(new Sender(s, nickName));
		}catch(UnknownHostException uh){
			JOptionPane.showMessageDialog(null, "ȣ��Ʈ�� ã�� �� �����ϴ�!", "ERROR", JOptionPane.WARNING_MESSAGE);
		}catch(IOException io){
			JOptionPane.showMessageDialog(null, "���� ���� ����!\n������ ���� �ִ� �� �����ϴ�.", "ERROR", JOptionPane.WARNING_MESSAGE);
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
			if(e.getSource() == btn_Ready){ // '�غ�' ��ư
				try{
					btn_img = ImageIO.read(new File(".\\img\\ready_2.png"));
					dos.writeUTF(">> " + nickName + " �� �غ� �Ϸ� !");
					dos.flush();
					dos.writeUTF("//Ready");
					dos.flush();
				}catch(IOException io){}
					btn_Ready.setIcon(new ImageIcon(btn_img));
					btn_Ready.setEnabled(false);
			}
		}

		public void keyReleased(KeyEvent e){ // ä�� �Է�
			if(e.getKeyCode() == KeyEvent.VK_ENTER){
				String chat = textField.getText();
				textField.setText("");
				if(job.equals("�����")){
					textArea.append("�����ڴ� ���� �� �� �����ϴ�.");
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
					if(msg.startsWith("//CList")){ // ��ɾ� : Ŭ���̾�Ʈ ��� ����
						playerName = msg.substring(7, msg.indexOf(" "));
						//playerScore = msg.substring(msg.indexOf(" ") + 1, msg.indexOf("#"));
						playerIdx = msg.substring(msg.indexOf(" ") + 1);
						updateClientList();		   // Ŭ���̾�Ʈ ��� ����
					}else if(msg.indexOf("//mafia") != -1){
						String mafiaTalk = msg.substring(13);
						if(job.equals("���Ǿ�")){
							textArea.append("���Ǿ�(���ǾƸ�����) >> "+mafiaTalk+"\n");
						}
					}else if(msg.startsWith("//U!mafia")){
						try{
							job_img = ImageIO.read(new File(".\\img\\job_mafia.png"));
						}catch(IOException ie){}
						label_Role.setIcon(new ImageIcon(job_img));
						job = "���Ǿ�";
					}else if(msg.startsWith("//U!Doctor")){
						try{
							job_img = ImageIO.read(new File(".\\img\\job_doctor.png"));
						}catch(IOException ie){}
						label_Role.setIcon(new ImageIcon(job_img));
						job = "�ǻ�";
					}else if(msg.startsWith("//U!Police")){
						try{
							job_img = ImageIO.read(new File(".\\img\\job_police.png"));
						}catch(IOException ie){}
						label_Role.setIcon(new ImageIcon(job_img));
						job = "����";
					}else if(msg.startsWith("//U!Citizen")){
						try{
							job_img = ImageIO.read(new File(".\\img\\job_citizen.png"));
						}catch(IOException ie){}
						label_Role.setIcon(new ImageIcon(job_img));
						job = "�ù�";
					}else if(msg.startsWith("//U!Died")){
						if(job.equals("�����")){
							dos.writeUTF("���� ����� �ٽ� ���̴ٴ�... ���߰�� ���� ���� ��Ȥ�� ���̱���...");
							dos.writeUTF("����Ǿ��� �� �������� ��Ƴ������� ���� �Ǵ� ���縦 �����ؾ߸� �մϴ�.");
							dos.writeUTF("�׷��� �ڽ��� ��Ƴ��� ��ȸ�� �Ժη� �����ؼ��� �ȵȴٰ� �����帮�� �ͱ���.\n");
						}else{
							textArea.append("����� ������ ��ſ��Դ� �ں���� ���߱���. ����� ���� �δ��ϰ� �ľ�� �ֽ��ϴ�.\n");
							if(job.equals("���Ǿ�")){
								dos.writeUTF("//M!nusMaf!aNumber");
							}else{
								dos.writeUTF("//M!nusC!t!zenNumber");
							}
							job = "�����";
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
						textArea.append("������ 10�ʵڿ� ����˴ϴ�.");
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
					textArea.append(">> �������� ������ ���������ϴ�. \n>> 3�� �� ���α׷��� �����մϴ� ..");
					try{
						Thread.sleep(3000);
						System.exit(0);
					}catch(InterruptedException it){}
				}
			}
		}

		public void deadNotice(Object deadMan, int deadManNum){
			textArea.append("\n");
			textArea.append(deadMan+"�� ��ü�� �߰ߵǾ����ϴ�... �� ����� ��ü�� ���� �����̿������...\n");
			vitalSign[deadManNum] = "���";
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

		public void updateClientList(){ // Ŭ���̾�Ʈ ��� �߰�
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
		public void deleteClientList(){ // Ŭ���̾�Ʈ ��� ����
			if(Integer.parseInt(playerIdx) == 0){
				Client2.setText("������ �����...");
				Client3.setText("������ �����...");
				Client4.setText("������ �����...");
				Client5.setText("������ �����...");
				Client6.setText("������ �����...");
				playerList[1]=null;
			}else if(Integer.parseInt(playerIdx) == 1){
				Client3.setText("������ �����...");
				Client4.setText("������ �����...");
				Client5.setText("������ �����...");
				Client6.setText("������ �����...");
				playerList[2]=null;
			}else if(Integer.parseInt(playerIdx) == 2){
				Client4.setText("������ �����...");
				Client5.setText("������ �����...");
				Client6.setText("������ �����...");
				playerList[3]=null;
			}else if(Integer.parseInt(playerIdx) == 3){
				Client5.setText("������ �����...");
				Client6.setText("������ �����...");
				playerList[4]=null;
			}else if(Integer.parseInt(playerIdx) == 4){
				Client6.setText("������ �����...");
				playerList[5]=null;
			}
		}
		public void quit(){
			textArea.append("ó���ڸ� ������ �ð��� �Խ��ϴ�...\n");
			if(!job.equals("�����")){
				try{
					Thread.sleep(1000);
					Object selectedName 
					= JOptionPane.showInputDialog(null, "ó���ϰ� ���� ����� �����ϼ���.", "Target",
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
					textArea.append("�����ڴ� �δ��ϰ� ���� ���� ���� �ʰ��ִ�...");
					textArea.append("");
					dos.writeUTF("//OK"); 
				}catch(IOException ie){
				}
			}
		}
		public void night(){
			textArea.append("���� �Ǿ����ϴ�... �Ǵ���� ��Ҽӿ��� �����̱� �����մϴ�...\n");
			try{
				if(job.equals("���Ǿ�")){
					Object selectedName 
					= JOptionPane.showInputDialog(null, "������ ����� �����ϼ���.", "Target",
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
				}else if(job.equals("�ǻ�")){
					Object selectedName 
					= JOptionPane.showInputDialog(null, "������ ���迡������ ���ϰ���� ����� �����ϼ���.", "Target",
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
				}else if(job.equals("����")){
					Object selectedName 
					= JOptionPane.showInputDialog(null, "��ü�� �˰���� ����� �����ϼ���.", "Target",
												  JOptionPane.INFORMATION_MESSAGE, null,
												  playerList, playerList[0]);
					dos.writeUTF("//Check "+selectedName);
					dos.writeUTF("//OK");
				}else if(job.equals("�����")){
					textArea.append("�����ڴ� �δ��ϰ� ���� ���� ���� �ʰ��ִ�...");
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