import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//�������� �ؾ��ϴ� �� -> Ŭ���̾�Ʈ �ޱ�
class MafiaServer extends JFrame {
	Container cp;
	JButton bOpen, bClose;
	JTextArea ta;
	JScrollPane sp;
	JPanel p;

	MafiaServer(){
		base();
		init();
		setUI();
	}

	void base(){
		ta = new JTextArea();
		ta.setEditable(false);
		p = new JPanel();
		bOpen = new JButton("���� ����");
		bOpen.setPreferredSize(new Dimension(120, 40));
		bClose = new JButton("���� ����");
		bClose.setPreferredSize(new Dimension(120, 40));

		ActionListener listener = new ServerEvent(this);
		bOpen.addActionListener(listener);
		bClose.addActionListener(listener);
	}

	void init(){
		cp = getContentPane();

		p.setLayout(new GridLayout(1, 2));
		p.add(bOpen);
		p.add(bClose);
		cp.add(p, BorderLayout.SOUTH);

		sp = new JScrollPane(ta);
		cp.add(sp);
	}

	void setUI(){
		setTitle("JAVA MafiaGame ver1.0");
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 500);
	}

	public static void main(String[] args) {
		MafiaServer ms = new MafiaServer();
	}
}
