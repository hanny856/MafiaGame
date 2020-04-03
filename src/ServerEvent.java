import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.*;

class ServerEvent implements ActionListener {
	MafiaServer ms;
	ServerSocket ss;
	Socket s;
	public static final int MAX_CLIENT = 6; //�ִ��ο�
	Random r = new Random();

	int port = 5500;
	int readyNum, okNum, killTarget;
	int killNum = -1;
	int killNum2 = -1;
	int healNum = -1;
	int killCount = 0;
	int mafiaNumber = 2;
	int citizenNumber = 4;
	String checkStr;
	boolean gameStart = false;

	String duration;
	String firstPick, secondPick, thirdPick, fourthPick, fifthPick, sixthPick;

	int[] votes = new int[6];
	Vector<Integer> killVote = new Vector<Integer>();

	LinkedHashMap<String, DataOutputStream> clientList = new LinkedHashMap<String, DataOutputStream>();
	Vector<String> v = new Vector<String>();
	Vector<String> w = new Vector<String>();
	//HashMap<String, String> clientInfo = new HashMap<String, String>(); ���̵�� �������� �Բ� �ֱ�??

	ServerEvent(MafiaServer ms){
		this.ms = ms;
	}
	public void actionPerformed(ActionEvent e){
		Object obj = e.getSource();
		if(obj == ms.bOpen){//���� ���� ��ư�� ������ �������� �����ϴ� �޼ҵ带 ���� 6�����
			openServer(); 
		}else if(obj == ms.bClose){ // ���� �ݴ� ��ư�� ������ �����ִ� ���������� �ݴ� �޼ҵ� ����
			closeServer();
		}
	}
	void openServer(){ //���� ���� �� ��� / 6�� �̻��� �� ���� �߰� Ŭ���� ��
		new Thread() {
			public void run() {
				try {
					ms.ta.append("������ ���Ƚ��ϴ�."+ "\n");
					ss = new ServerSocket(port);
					while(true) {
						s = ss.accept();
						if(clientList.size()>=MAX_CLIENT || gameStart == true ) {// || ���ӽ�ŸƮ���� �϶�
							s.close();
						}else {
							Thread sm = new ServerManager(s);
							sm.start(); //������ id �߰� ������� ǥ��
						}
					}
				}catch(IOException ie) {
				}
			}
		}.start();
	}
	void closeServer(){ //���� ���������� �����µ� �̹� ����Ǿ��ִ� Ŭ���̾�Ʈ ���ᰡ �ȵǴ°� �´µ�
			try{
				ss.close();
				System.exit(0);
			}catch(IOException ie) {			
			}catch(NullPointerException npe) {
				ms.ta.append("�����ִ� ������ �����ϴ�"+ "\n");
			}
	}
	void pln(String str) {
		System.out.println(str);
	}
	void systemMsg(String msg){ //broadcast
		//if(duration.equals("Day"))
		Iterator<String> it = clientList.keySet().iterator();
		while(it.hasNext()) {
			try {
				DataOutputStream dos = clientList.get(it.next());
				dos.writeUTF(msg);
				dos.flush();
			}catch(IOException ie) {}
		}
	}
	void systemMsgone(String msg, String target){ //�������� �ѻ������ ���ϱ�
		//if(duration.equals("Day"))
		try{
			DataOutputStream dos = clientList.get(target);
			dos.writeUTF(msg);
			dos.flush();
		}catch(IOException ie) {}
	}

	class ServerManager extends Thread{ //���� ����� ���̵�� ������ Ŭ���̾�Ʈ ����Ʈ�� ���
										//���̵� �ߺ� �� ��� ���Ͽ��� X
		Socket s;
		DataInputStream dis;
		DataOutputStream dos;
		
		ServerManager(Socket s){
			this.s=s;
			try {
				dis = new DataInputStream(this.s.getInputStream());
				dos = new DataOutputStream(this.s.getOutputStream());
			}catch(IOException ie) {}
		}
		public void run() { //��/���� �� clientList�� ���/���� ��ε�ĳ����
			String clientID = "";
			try {
				clientID = dis.readUTF();
				if(!clientList.containsKey(clientID)) {
					clientList.put(clientID, dos);
					v.add(clientID);
					w.add(clientID);
					setList();
					systemMsg("<<"+clientID+">> ���� �����ϼ̽��ϴ�. (���� ������ ��: "+clientList.size()+"��)"+ "\n");
					ms.ta.append("<<"+clientID+">> ���� �����ϼ̽��ϴ�. (���� ������ ��: "+clientList.size()+"��)"+ "\n");
				}else if(clientList.containsKey(clientID)) {
					s.close(); //�г��� �ߺ����� ���� ���� �ź�(���� �Ǵµ� �̹� �α��� �� �ߺ��� ���̵� Ŭ���̾�Ʈ�� �����)
					String msg = "�г��� �ߺ����� ���� ������ �źε˴ϴ�.";
					dos.writeUTF(msg);
					dos.flush();
				}
				while(dis != null) {
					String msg = dis.readUTF();
					if(msg.equals("//Ready")){
						ms.ta.append(clientID+"�Բ��� �غ�Ϸ�Ǿ����ϴ�");
						readyNum++;
						if(readyNum>=6 && readyNum==clientList.size()) {
							systemMsg("//C!ear!");
							gameStart = true;
							systemMsg("��� �غ� �ǽ� �� ������...");
							systemMsg("");
							Thread.sleep(2000);
							systemMsg("�׷� ������ �����ϰڽ��ϴ�.");
							systemMsg("");
							Thread.sleep(2000);
							systemMsg("6���� �������� ���� �� �������� ���� ������ �˴ϴ�.");
							systemMsg("�� �߿����� ������ �ǻ�, �����̽� �е� �Ѻо� �ֽ��ϴ�.");
							systemMsg("�׻Ӹ� �ƴ϶� ������ ������� ���̷��� �ϴ� �Ǵ�鵵 �����ϰ� �ֽ��ϴ�.");
							systemMsg("");
							Thread.sleep(3000);
							systemMsg("�����е��� ���� ���� ���� ��ü�� ����� �Ǵ��� �������� ���Ƴ��� �մϴ�.");
							systemMsg("�׷��� ���ϸ� �����е��� ��� �Ǵ���� �տ� �װԵ��״ϱ��...");
							systemMsg("");
							Thread.sleep(3000);
							systemMsg("(���Ǿ��� ���� �ùκ��� ���ų� �������� ���Ǿ��� �¸��� �˴ϴ�.)");
							systemMsg("(���ǾƸ� ���� ó���Ͽ� 0���� �Ǹ� �ù��� �¸��� �˴ϴ�.)");
							systemMsg("(5�� �� ������ ���۵Ǹ�, �ӹ��� �־����� �˴ϴ�. ������ ���ϴ�.)\n");
							Thread.sleep(5000);
							setRole();
							noticeRole();
							duration = "Day";
							stopWatch sw = new stopWatch();
							sw.start();
						}
					}else if(msg.startsWith("//Ban")){
						String banNumStr = msg.substring(6);
						int banNum = Integer.parseInt(banNumStr);
						votes[banNum]++;
					}else if(msg.startsWith("//Kill")){
						String killNumStr = msg.substring(7);
						if(killNum == -1){
							killNum = Integer.parseInt(killNumStr);
						}else{
							killNum2 = Integer.parseInt(killNumStr);
						}
						killCount++;
					}else if(msg.startsWith("//Heal")){
						String healNumStr = msg.substring(7);
						healNum = Integer.parseInt(healNumStr);
					}else if(msg.startsWith("//Check")){
						checkStr = msg.substring(8);
					}else if(msg.startsWith("//!!Died")){
						String dddd = msg.substring(9);
						systemMsgone("//U!Died", dddd);
					}else if(msg.startsWith("//M!nusMaf!aNumber")){
						mafiaNumber--;
						checkNumber();
					}else if(msg.startsWith("//M!nusC!t!zenNumber")){
						citizenNumber--;
						checkNumber();
					}else if(msg.equals("//OK")){
						okNum++;
						if(okNum == clientList.size() && duration.equals("Quit")){
							systemMsg("");
							systemMsg("ó���� ��ǥ�� �Ϸ� �Ǿ����ϴ�. ������ ���� ���� ó������ ����� �����ϰ� �ɱ��...\n");
							Thread.sleep(3000);
							banResult();
							systemMsg("");
							Thread.sleep(3000);
							systemMsg("");
							systemMsg("�ذ� �� �ɼ� �ڷ� �Ѿ�� �����ϰ� �ϴ��� ��Ȳ������ ����� �ֽ��ϴ�.");
							systemMsg("�� ã�ƿ� ��ҿ� ����ϴ°� ���ڱ���.");
							systemMsg("");
							Thread.sleep(9000);
							systemMsg("//Night");
							duration = "Night";
							okNum = 0;
						}else if(okNum == clientList.size() && duration.equals("Night")){
							systemMsg("");
							systemMsg("��ο� ���� �������� ���� ���� ��ҽ��ϴ�...\n");
							Thread.sleep(3000);
							rolePlay();
							checker();
							systemMsg("");
							Thread.sleep(3000);
							duration = "Day";
							stopWatch sw = new stopWatch();
							sw.start();
							okNum = 0;
						}
					}else{
						systemMsg(msg);
					}
				}
			}catch(InterruptedException ite){
			}catch(IOException ie) {
				clientList.remove(clientID); //���� ��� ���� ��
				setList();
				systemMsg("<<"+clientID+">> ���� �����ϼ̽��ϴ�. (���� ������ ��: "+clientList.size()+"��)"+ "\n");
				ms.ta.append("<<"+clientID+">> ���� �����ϼ̽��ϴ�. (���� ������ ��: "+clientList.size()+"��)"+ "\n");
				closeAll();
				if(clientList.isEmpty()==true) {//Ŭ���̾�Ʈ �ٳ������� �������� �����
					try {
						ss.close();
						System.exit(0);
					}catch(IOException ie2) {}
				}
			}
		}

		public void setList() {
			String[] keys = new String[clientList.size()];
			int index = 0;
			for(Map.Entry<String, DataOutputStream> mapEntry : clientList.entrySet()) {
				keys[index] = mapEntry.getKey();
				index++;
			}
			for(int i = 0; i<clientList.size(); i++){
				systemMsg("//CList"+keys[i]+" "+i);
			}
		}

		void setRole(){
			boolean flag = false;
			int ran = r.nextInt(v.size());
			firstPick = v.get(ran);
			systemMsgone("//U!mafia", firstPick);
			v.remove(firstPick);
			ran = r.nextInt(v.size());
			secondPick = v.get(ran);
			systemMsgone("//U!mafia", secondPick);
			v.remove(secondPick);
			ran = r.nextInt(v.size());
			thirdPick = v.get(ran);
			systemMsgone("//U!Doctor", thirdPick);
			v.remove(thirdPick);
			ran = r.nextInt(v.size());
			fourthPick = v.get(ran);
			systemMsgone("//U!Police", fourthPick);
			v.remove(fourthPick);
			ran = r.nextInt(v.size());
			fifthPick = v.get(ran);
			systemMsgone("//U!Citizen", fifthPick);
			v.remove(fifthPick);
			ran = r.nextInt(v.size());
			sixthPick = v.get(ran);
			systemMsgone("//U!Citizen", sixthPick);
			v.remove(sixthPick);
		}

		void noticeRole(){
			systemMsg("");
			systemMsgone("����� ���Ǿ��Դϴ�. �� �ٸ� ���Ǿƴ� '"+secondPick+"'�Դϴ�.", firstPick);
			systemMsgone("����� �� �ٸ� ���Ǿƿ� ���� ���� �ùε��� ���̰� �㿡 �׿������� �մϴ�.", firstPick);
			systemMsgone("!!!���̵Ǿ� ���ش���� �����Ҷ� ���Ǿư� ���� ����� �������� ������ ���ذ� ��ȿó���˴ϴ�.!!!", firstPick);
			systemMsgone("1234567890000 !!!(//mafia 'ä�ó���' �������� �Է��ϸ� ���ǾƳ����� ��ȭ�� �����մϴ�.)!!!", firstPick);
			systemMsgone("����� ���Ǿ��Դϴ�. �� �ٸ� ���Ǿƴ� '"+firstPick+"'�Դϴ�.", secondPick);
			systemMsgone("����� �� �ٸ� ���Ǿƿ� ���� ���� �ùε��� ���̰� �㿡 �׿������� �մϴ�.", secondPick);
			systemMsgone("!!!���̵Ǿ� ���ش���� �����Ҷ� ���Ǿư� ���� ����� �������� ������ ���ذ� ��ȿó���˴ϴ�.!!!", secondPick);
			systemMsgone("1234567890000 !!!(//mafia 'ä�ó���' �������� �Է��ϸ� ���ǾƳ����� ��ȭ�� �����մϴ�.)!!!", secondPick);
			systemMsgone("����� �ǻ��Դϴ�. ����� ���� �� ������ ���Ǿƿ��� ���ݴ��� �׾��", thirdPick);
			systemMsgone("�ù��� ���� �ǹ��� �ֽ��ϴ�. ���� ��� �ڽ��� ���� ��ų �� �־�߰���.", thirdPick);
			systemMsgone("����� �����Դϴ�. ������ ������ ���� ����� ���ù㵵 ������ �����ϴ�.", fourthPick);
			systemMsgone("����� ���� �� ����½�� �ι��� ���� ������ �� �ֽ��ϴ�.", fourthPick);
			systemMsgone("����� ������ �ù��Դϴ�. ���� �� ������ ���Ǿƿ��� �������ϴ� �����Դϴ�.",fifthPick);
			systemMsgone("����� ���ǾƸ� ���� ���� �ٸ��̵�� ������ϰ�, ������ �ι��� ó���ؾ� �մϴ�.", fifthPick);
			systemMsgone("����� ������ �ù��Դϴ�. ���� �� ������ ���Ǿƿ��� �������ϴ� �����Դϴ�.",sixthPick);
			systemMsgone("����� ���ǾƸ� ���� ���� �ٸ��̵�� ������ϰ�, ������ �ι��� ó���ؾ� �մϴ�.", sixthPick);
			systemMsg("");
		}

		void banResult(){
			int high = 0;
			int banned = 0;
			int count = 0;
			for(int i=0; i<votes.length; i++){
				if(votes[i] > high){
					high = votes[i];
					banned = i;
				}
			}
			for(int j=0; j<votes.length; j++){
				if(votes[j] == high){
					count++;
				}
			}
			if(count >= 2){
				sameBan();
			}else{
				noticeBan(banned);
			}
			for(int l=0; l<votes.length; l++){
				votes[l] = 0;
			}
			high = 0;
			banned = 0;
			count = 0;
		}

		void sameBan(){
			systemMsg("");
			systemMsg("�̷�...! �ùε��� �Ѹ������� ��ġġ ���� ó���� ��ǥ�� ������ ���Խ��ϴ�.");
			systemMsg("������ ��Ģ�� ó���� �Ϸ� �ѻ���� �����ϱ� ������, ������ ó���� ��ȿ�� ��ä�� �������� �˴ϴ�.");
			systemMsg("�ùε��� ���� ��ġ�� ������, ���Ǿƿ��� ������ ���ѱ�� �˴ϴ�!");
		}

		void noticeBan(int banned){
			systemMsg("");
			systemMsg("�ù� �������� �ǰ��� ���Ͽ� ó���ڰ� �����Ǿ����ϴ�.\n");
			systemMsg("���� ��ü�ʹ� �������... ������� �׸� ���� Į�� ���� �ܴ��� �ٰ����ϴ�...");
			systemMsg("//Murder "+banned);
		}

		void rolePlay(){
			killTarget = (-1);
			if(killCount == 2){
				if(killNum != killNum2){
					killTarget = (-1);
				}else{
					killTarget = killNum;
				}
			}else{
				killTarget = killNum;
			}
			if(killTarget == (-1) || killTarget == healNum){
				safe();
			}else{
				murder();
			}
			killCount = 0;
			killNum = (-1);
			killNum2 = (-1);
		}

		void checker(){
			systemMsgone("", fourthPick);
			if(checkStr.equals(firstPick)){
				systemMsgone(firstPick+"�� ��ü�� '���Ǿ�'�Դϴ�.", fourthPick);
			}else if(checkStr.equals(secondPick)){
				systemMsgone(secondPick+"�� ��ü�� '���Ǿ�'�Դϴ�.", fourthPick);
			}else if(checkStr.equals(thirdPick)){
				systemMsgone(thirdPick+"�� ��ü�� '�ǻ�'�Դϴ�. ��Ű� �Բ� �ù��� ��Ű�� �����Դϴ�.", fourthPick);
			}else if(checkStr.equals(fourthPick)){
				systemMsgone("�̻��� ���̱���... �̷� ��ȸ�� �����Ͻôٴ�...", fourthPick);
				systemMsgone("Ȥ�ó� �𸣼ż� ����ŰŶ�� ����� �����Դϴ�.", fourthPick);
				systemMsgone("������ ��ȭ�� ��Ű�µ��� ����� ������ �߿��ϴٴ°��� ���� �����ּ���.", fourthPick);
			}else if(checkStr.equals(fifthPick)){
				systemMsgone(secondPick+"�� ��ü�� '�ù�'�Դϴ�. �Ǵ����κ��� ���Ѿ��� ������.", fourthPick);
			}else if(checkStr.equals(sixthPick)){
				systemMsgone(secondPick+"�� ��ü�� '�ù�'�Դϴ�. �Ǵ����κ��� ���Ѿ��� ������.", fourthPick);
			}
			systemMsgone("", fourthPick);
		}

		void safe(){
			systemMsg("��ΰ� �Ǵ��� �����ӿ��� ������ ���� �ѱ�µ� �����Ͽ����ϴ�!");
			systemMsg("���ù��� ����ڰ� �߻����� �ʾҽ��ϴ�.");
		}

		void murder(){
			systemMsg("������ ���Ǿƿ� ���� ����ڰ� �߻��Ͽ����ϴ�.");
			systemMsg("//Murder "+ killTarget);
		}

		void checkNumber(){
			if(mafiaNumber >= citizenNumber){
				systemMsg("�� ������ �ᱹ ���ǾƵ��� �ұ��� �Ǿ������ ���ҽ��ϴ�....");
				systemMsg("�ܾǹ����� �Ǵ���� ���� �ùε鸶�� �����ϰ� �׿����Ƚ��ϴ�....");
				systemMsg("");
				systemMsg("���Ǿƴ� "+firstPick+", "+secondPick+"�Դϴ�.");
				systemMsg(thirdPick+"�� ��ü�� �ǻ翴���ϴ�.");
				systemMsg(fourthPick+"�� ��ü�� �����̿����ϴ�.");
				systemMsg(fifthPick+"�� ��ü�� �ù��̿����ϴ�.");
				systemMsg(sixthPick+"�� ��ü�� �ù��̿����ϴ�.");
				systemMsg("//GameEnd");
			}else if(mafiaNumber == 0){
				systemMsg("�ùε��� ���� ���� ��ģ ���п� �� �������� ���ǾƵ��� ���� ����ĥ �� �־����ϴ�.");
				systemMsg("������ �� ������ ��ĥ ����鵵 �����е��̶�� �̰ܳ� �� �ְ���.");
				systemMsg("�����ε� �� ������ �����ֽñ� �ٶ��ϴ�.");
				systemMsg("");
				systemMsg("���Ǿƴ� "+firstPick+", "+secondPick+"�Դϴ�.");
				systemMsg(thirdPick+"�� ��ü�� �ǻ翴���ϴ�.");
				systemMsg(fourthPick+"�� ��ü�� �����̿����ϴ�.");
				systemMsg(fifthPick+"�� ��ü�� �ù��̿����ϴ�.");
				systemMsg(sixthPick+"�� ��ü�� �ù��̿����ϴ�.");
				systemMsg("//GameEnd");
			}
		}
			
		void closeAll() {
			try {
				if(dos != null) dos.close();
				if(dis != null) dis.close();
				if(s != null)s.close();
			}catch(IOException ie) {}
		}
	}

	class stopWatch extends Thread {
		long preTime = System.currentTimeMillis();
		
		public void run() {
			try{
				while(duration.equals("Day")){
					sleep(10);
					long time = System.currentTimeMillis() - preTime;
					systemMsg("//Timer " + (toTime(time)));
					if(toTime(time).equals("00 : 00")){
						systemMsg("//Quit");
						duration = "Quit";
						break;
					}
				}
			}catch(Exception e){
			}
		}
		
		String toTime(long time){
			int m = (int)(0-(time / 1000.0 / 60.0));
			int s = (int)(30-(time % (1000.0 * 60) / 1000.0));
			return String.format("%02d : %02d", m, s);
		}
	}
}
