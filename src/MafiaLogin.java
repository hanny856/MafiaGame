
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

		//�̹��� ũ�⺯��: �̹����� jlabel�� ���� �� jpanel�� ����(imageicon->image(ũ�⺯��)->imageicon) //
		changeIcon = new ImageIcon(".\\img\\Login.png"); 					
		changeIcon = new ImageIcon(".\\img\\Login.png");
		changeImage = changeIcon.getImage();  //ImageIcon�� Image�� ��ȯ.
		changed_Image= changeImage.getScaledInstance(600, 400, java.awt.Image.SCALE_SMOOTH); // ������̹��� => �������̹���
		changed_Icon = new ImageIcon(changed_Image); //Image�� ImageIcon ����
		imageLabel = new JLabel("",changed_Icon,JLabel.CENTER);
		imageLabel.setVerticalTextPosition(JLabel.CENTER);
		imageLabel.setHorizontalTextPosition(JLabel.RIGHT);

		imagePanel = new JPanel(); //�̹��� ��� �г� 
		imagePanel.add(imageLabel);
		loginButtonPanel = new JPanel(); // �α��ι�ư �г�
		exitButtonPanel = new JPanel(); //�����ư �г�
		idPanel = new JPanel(); //id �г�
		ipPanel = new JPanel(); //ip�г�
		
		//JLabel idLabel = new JLabel("ID:");
		//JLabel ipLabel = new JLabel("IP:");
		imagePanel.setBackground(new Color(255, 255, 255)); //��� �г� (+����� �� ä���)

		 // id, ip �Է�â
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
		
		bttonLogin = new JButton("�α���");
		bttonExit = new JButton("����");
		loginButtonPanel.setOpaque(false); //��ư�г� ����ȭ(�г��� �� ������ ����ȭ ��)
		exitButtonPanel.setOpaque(false);
		bttonLogin.addActionListener(this);
		bttonExit.addActionListener(this);
		
		loginButtonPanel.add(bttonLogin);  exitButtonPanel.add(bttonExit);
		//�г� ��ġ ����
		setLayout(null);
		add(loginButtonPanel,null);
		loginButtonPanel.setBounds(180,293,73,33);  //�α��ι�ư
		add(exitButtonPanel,null);
		exitButtonPanel.setBounds(260,293,60,33);//�����ư
		add(imagePanel,null); 
		imagePanel.setBounds(0,0,600,500); //��(����̹���)�г�
		add(idPanel,null);
		idPanel.setBounds(112,230,260,39);//id �Է�â
		add(ipPanel,null);
		ipPanel.setBounds(112,259,260,60); //ip�Է�â
		
		setUI();
	}
	
	void setUI() {
		try {	
			setTitle("Mafia_Login");
			setVisible(true);
	
			setResizable(false);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(750, 300, 606, 434); //�α��� ��ü â ũ��
		}catch(NullPointerException ne) {}
	}
	
	public void actionPerformed(ActionEvent e){
	
		Object obj = e.getSource();
		id = idtext.getText().trim();
		//String ip = iptext.getText().trim();
		

		if(obj == bttonLogin){ //�α��� ��ư �̺�Ʈ
			
			if(idtext.getText().equals("")) { //�α��� ����1) id �Է� �ؽ�Ʈ�� �ƹ��͵� ���� ��
				JOptionPane.showMessageDialog(null, "ID�� �Է��� �ּ���.", "ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
			}else if(id.indexOf(" ") != -1){ //�α��� ����2) id ���̿� ���� ���ԉ��� ��,
			 	JOptionPane.showMessageDialog(null, "ID�� ������ ���ԵǾ� �ֽ��ϴ�.\n�ٽ� �Է��� �ּ���.", "ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);	
			}else if(id.length()> 5){ //�α��� ����3) id 5���� �ʰ� �Է�
			 	JOptionPane.showMessageDialog(null, "5���� ���Ϸ� �Է��� �ּ���.", "ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
			}else if(iptext.getText().equals("")) {//�α��� ����4) ip �Է� �ؽ�Ʈ�� �ƹ��͵� ���� ��
					JOptionPane.showMessageDialog(null, "IP�� �Է��� �ּ���.", "ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
			}else { //�α��� ����
				String temp = iptext.getText().trim();
/*ip�Է��ּ� Ȯ��*/if(temp.matches("(^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$)"))
				{
					ip = temp;
					setVisible(false);
					MafiaClient mc = new MafiaClient();	
					
				}else {
					JOptionPane.showMessageDialog(null, "�߸��� IP�ּ� �Դϴ�.", "ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
				}
			}
		}else if(obj ==bttonExit){ //���� ��ư �̺�Ʈ
			System.exit(0);		
			}
	}
		
	public static void main(String[] args) {
		MafiaLogin ml = new MafiaLogin();
		ml.init();
	}
}
