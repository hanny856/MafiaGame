
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JLabel;
import javax.swing.border.*;

public class MafiaLogin extends JFrame implements ActionListener {
	JPanel imagePanel, loginButtonPanel, exitButtonPanel, idPanel, ipPanel;
	JButton bttonLogin, bttonExit;
	JTextField idtext, iptext;
	JLabel /*idLable, ipLable,*/ imageLabel;
	ImageIcon changeIcon, changed_Icon;
	Image changeImage, changed_Image, image3;

	public static String ip, id;
	
	void init() {

		//이미지 크기변경: 이미지를 jlabel에 넣은 후 jpanel에 붙임(imageicon->image(크기변경)->imageicon) //
		changeIcon = new ImageIcon(".\\img\\Login.png"); 					
		changeIcon = new ImageIcon(".\\img\\Login.png");
		changeImage = changeIcon.getImage();  //ImageIcon을 Image로 변환.
		changed_Image= changeImage.getScaledInstance(600, 400, java.awt.Image.SCALE_SMOOTH); // 변경된이미지 => 변경할이미지
		changed_Icon = new ImageIcon(changed_Image); //Image로 ImageIcon 생성
		imageLabel = new JLabel("",changed_Icon,JLabel.CENTER);
		imageLabel.setVerticalTextPosition(JLabel.CENTER);
		imageLabel.setHorizontalTextPosition(JLabel.RIGHT);

		imagePanel = new JPanel(); //이미지 배경 패널 
		imagePanel.add(imageLabel);
		loginButtonPanel = new JPanel(); // 로그인버튼 패널
		exitButtonPanel = new JPanel(); //종료버튼 패널
		idPanel = new JPanel(); //id 패널
		ipPanel = new JPanel(); //ip패널
		
		//JLabel idLabel = new JLabel("ID:");
		//JLabel ipLabel = new JLabel("IP:");
		imagePanel.setBackground(new Color(255, 255, 255)); //배경 패널 (+빈공간 색 채우기)

		 // id, ip 입력창
		idtext = new JTextField(17);
		iptext = new JTextField(17);
		idtext.setBorder(new LineBorder(Color.BLACK, 1));
		iptext.setBorder(new LineBorder(Color.BLACK, 1));
		idtext.setForeground(Color.WHITE);
		iptext.setForeground(Color.WHITE);
		idtext.setBackground(new Color(165, 6, 6)); 
		iptext.setBackground(new Color(165, 6, 6)); 

	//	idPanel.add(idLabel);  
		idPanel.add(idtext);
	//	ipPanel.add(ipLabel); 
		ipPanel.add(iptext);
		
		bttonLogin = new JButton("로그인");
		bttonExit = new JButton("종료");
		loginButtonPanel.setOpaque(false); //버튼패널 투명화(패널의 빈 공간을 투명화 함)
		exitButtonPanel.setOpaque(false);
		bttonLogin.addActionListener(this);
		bttonExit.addActionListener(this);
		
		loginButtonPanel.add(bttonLogin);  exitButtonPanel.add(bttonExit);
		//패널 위치 지정
		setLayout(null);
		add(loginButtonPanel,null);
		loginButtonPanel.setBounds(180,293,73,33);  //로그인버튼
		add(exitButtonPanel,null);
		exitButtonPanel.setBounds(260,293,60,33);//종료버튼
		add(imagePanel,null); 
		imagePanel.setBounds(0,0,600,500); //라벨(배경이미지)패널
		add(idPanel,null);
		idPanel.setBounds(112,230,260,39);//id 입력창
		add(ipPanel,null);
		ipPanel.setBounds(112,259,260,60); //ip입력창
		
		setUI();
	}
	
	void setUI() {
		try {	
			setTitle("Mafia_Login");
			setVisible(true);
	
			setResizable(false);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(750, 300, 606, 434); //로그인 전체 창 크기
		}catch(NullPointerException ne) {}
	}
	
	public void actionPerformed(ActionEvent e){
	
		Object obj = e.getSource();
		id = idtext.getText().trim();
		//String ip = iptext.getText().trim();
		

		if(obj == bttonLogin){ //로그인 버튼 이벤트
			
			if(idtext.getText().equals("")) { //로그인 실패1) id 입력 텍스트가 아무것도 없을 때
				JOptionPane.showMessageDialog(null, "ID를 입력해 주세요.", "ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
			}else if(id.indexOf(" ") != -1){ //로그인 실패2) id 사이에 공백 포함됬을 때,
			 	JOptionPane.showMessageDialog(null, "ID에 공백이 포함되어 있습니다.\n다시 입력해 주세요.", "ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);	
			}else if(id.length()> 5){ //로그인 실패3) id 5글자 초과 입력
			 	JOptionPane.showMessageDialog(null, "5글자 이하로 입력해 주세요.", "ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
			}else if(iptext.getText().equals("")) {//로그인 실패4) ip 입력 텍스트가 아무것도 없을 때
					JOptionPane.showMessageDialog(null, "IP를 입력해 주세요.", "ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
			}else { //로그인 성공
				String temp = iptext.getText().trim();
/*ip입력주소 확인*/if(temp.matches("(^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$)"))
				{
					ip = temp;
					setVisible(false);
					MafiaClient mc = new MafiaClient();	
					
				}else {
					JOptionPane.showMessageDialog(null, "잘못된 IP주소 입니다.", "ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
				}
			}
		}else if(obj ==bttonExit){ //종료 버튼 이벤트
			System.exit(0);		
			}
	}
		
	public static void main(String[] args) {
		MafiaLogin ml = new MafiaLogin();
		ml.init();
	}
}
