import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//서버에서 해야하는 것 -> 클라이언트 받기
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
		bOpen = new JButton("서버 시작");
		bOpen.setPreferredSize(new Dimension(120, 40));
		bClose = new JButton("서버 종료");
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
