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
	public static final int MAX_CLIENT = 6; //최대인원
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
	//HashMap<String, String> clientInfo = new HashMap<String, String>(); 아이디와 생존여부 함께 넣기??

	ServerEvent(MafiaServer ms){
		this.ms = ms;
	}
	public void actionPerformed(ActionEvent e){
		Object obj = e.getSource();
		if(obj == ms.bOpen){//서버 시작 버튼을 누르면 서버소켓 생성하는 메소드를 실행 6명고정
			openServer(); 
		}else if(obj == ms.bClose){ // 서버 닫는 버튼을 누르면 열려있던 서버소켓을 닫는 메소드 실행
			closeServer();
		}
	}
	void openServer(){ //서버 오픈 및 대기 / 6명 이상일 때 소켓 추가 클로즈 됨
		new Thread() {
			public void run() {
				try {
					ms.ta.append("서버가 열렸습니다."+ "\n");
					ss = new ServerSocket(port);
					while(true) {
						s = ss.accept();
						if(clientList.size()>=MAX_CLIENT || gameStart == true ) {// || 게임스타트상태 일때
							s.close();
						}else {
							Thread sm = new ServerManager(s);
							sm.start(); //서버에 id 뜨고 몇명인지 표시
						}
					}
				}catch(IOException ie) {
				}
			}
		}.start();
	}
	void closeServer(){ //서버 정상적으로 닫히는데 이미 연결되어있는 클라이언트 종료가 안되는게 맞는듯
			try{
				ss.close();
				System.exit(0);
			}catch(IOException ie) {			
			}catch(NullPointerException npe) {
				ms.ta.append("열려있는 서버가 없습니다"+ "\n");
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
	void systemMsgone(String msg, String target){ //서버에서 한사람에게 말하기
		//if(duration.equals("Day"))
		try{
			DataOutputStream dos = clientList.get(target);
			dos.writeUTF(msg);
			dos.flush();
		}catch(IOException ie) {}
	}

	class ServerManager extends Thread{ //소켓 연결된 아이디와 데이터 클라이언트 리스트에 담기
										//아이디 중복 된 경우 소켓연결 X
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
		public void run() { //입/퇴장 시 clientList에 담고/빼고 브로드캐스팅
			String clientID = "";
			try {
				clientID = dis.readUTF();
				if(!clientList.containsKey(clientID)) {
					clientList.put(clientID, dos);
					v.add(clientID);
					w.add(clientID);
					setList();
					systemMsg("<<"+clientID+">> 님이 입장하셨습니다. (현재 접속자 수: "+clientList.size()+"명)"+ "\n");
					ms.ta.append("<<"+clientID+">> 님이 입장하셨습니다. (현재 접속자 수: "+clientList.size()+"명)"+ "\n");
				}else if(clientList.containsKey(clientID)) {
					s.close(); //닉네임 중복으로 소켓 연결 거부(실행 되는데 이미 로그인 된 중복된 아이디 클라이언트도 종료됨)
					String msg = "닉네임 중복으로 서버 접속이 거부됩니다.";
					dos.writeUTF(msg);
					dos.flush();
				}
				while(dis != null) {
					String msg = dis.readUTF();
					if(msg.equals("//Ready")){
						ms.ta.append(clientID+"님께서 준비완료되었습니다");
						readyNum++;
						if(readyNum>=6 && readyNum==clientList.size()) {
							systemMsg("//C!ear!");
							gameStart = true;
							systemMsg("모두 준비가 되신 것 같군요...");
							systemMsg("");
							Thread.sleep(2000);
							systemMsg("그럼 게임을 시작하겠습니다.");
							systemMsg("");
							Thread.sleep(2000);
							systemMsg("6명의 여러분은 지금 한 마을에서 같이 지내게 됩니다.");
							systemMsg("그 중에서는 직업이 의사, 경찰이신 분도 한분씩 있습니다.");
							systemMsg("그뿐만 아니라 선량한 사람들을 죽이려고 하는 악당들도 존재하고 있습니다.");
							systemMsg("");
							Thread.sleep(3000);
							systemMsg("여러분들은 서로 힘을 합쳐 정체를 숨기는 악당을 밝혀내어 막아내야 합니다.");
							systemMsg("그렇지 못하면 여러분들은 모두 악당들의 손에 죽게될테니까요...");
							systemMsg("");
							Thread.sleep(3000);
							systemMsg("(마피아의 수가 시민보다 같거나 많아지면 마피아의 승리가 됩니다.)");
							systemMsg("(마피아를 전부 처형하여 0명이 되면 시민의 승리가 됩니다.)");
							systemMsg("(5초 뒤 게임이 시작되며, 임무가 주어지게 됩니다. 건투를 빕니다.)\n");
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
							systemMsg("처형자 투표가 완료 되었습니다. 오늘은 과연 누가 처형자의 운명을 맞이하게 될까요...\n");
							Thread.sleep(3000);
							banResult();
							systemMsg("");
							Thread.sleep(3000);
							systemMsg("");
							systemMsg("해가 산 능선 뒤로 넘어가기 시작하고 하늘은 주황빛으로 물들고 있습니다.");
							systemMsg("곧 찾아올 어둠에 대비하는게 좋겠군요.");
							systemMsg("");
							Thread.sleep(9000);
							systemMsg("//Night");
							duration = "Night";
							okNum = 0;
						}else if(okNum == clientList.size() && duration.equals("Night")){
							systemMsg("");
							systemMsg("어두운 밤이 지나가고 드디어 날이 밝았습니다...\n");
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
				clientList.remove(clientID); //퇴장 기능 실행 됨
				setList();
				systemMsg("<<"+clientID+">> 님이 퇴장하셨습니다. (현재 접속자 수: "+clientList.size()+"명)"+ "\n");
				ms.ta.append("<<"+clientID+">> 님이 퇴장하셨습니다. (현재 접속자 수: "+clientList.size()+"명)"+ "\n");
				closeAll();
				if(clientList.isEmpty()==true) {//클라이언트 다나갔을때 서버종료 실행됨
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
			systemMsgone("당신은 마피아입니다. 또 다른 마피아는 '"+secondPick+"'입니다.", firstPick);
			systemMsgone("당신은 또 다른 마피아와 힘을 합쳐 시민들을 속이고 밤에 죽여나가야 합니다.", firstPick);
			systemMsgone("!!!밤이되어 살해대상을 선택할때 마피아가 같은 대상을 선택하지 않으면 살해가 무효처리됩니다.!!!", firstPick);
			systemMsgone("1234567890000 !!!(//mafia '채팅내용' 형식으로 입력하면 마피아끼리만 대화가 가능합니다.)!!!", firstPick);
			systemMsgone("당신은 마피아입니다. 또 다른 마피아는 '"+firstPick+"'입니다.", secondPick);
			systemMsgone("당신은 또 다른 마피아와 힘을 합쳐 시민들을 속이고 밤에 죽여나가야 합니다.", secondPick);
			systemMsgone("!!!밤이되어 살해대상을 선택할때 마피아가 같은 대상을 선택하지 않으면 살해가 무효처리됩니다.!!!", secondPick);
			systemMsgone("1234567890000 !!!(//mafia '채팅내용' 형식으로 입력하면 마피아끼리만 대화가 가능합니다.)!!!", secondPick);
			systemMsgone("당신은 의사입니다. 당신은 매일 밤 잔인한 마피아에게 공격당해 죽어가는", thirdPick);
			systemMsgone("시민을 구할 의무가 있습니다. 물론 당신 자신의 몸도 지킬 수 있어야겠죠.", thirdPick);
			systemMsgone("당신은 경찰입니다. 마을의 안전을 위해 당신은 오늘밤도 순찰을 나섭니다.", fourthPick);
			systemMsgone("당신은 매일 밤 수상쩍은 인물에 대해 조사할 수 있습니다.", fourthPick);
			systemMsgone("당신은 선량한 시민입니다. 매일 밤 잔인한 마피아에게 위협당하는 존재입니다.",fifthPick);
			systemMsgone("당신은 마피아를 막기 위해 다른이들과 토론을하고, 위험한 인물을 처형해야 합니다.", fifthPick);
			systemMsgone("당신은 선량한 시민입니다. 매일 밤 잔인한 마피아에게 위협당하는 존재입니다.",sixthPick);
			systemMsgone("당신은 마피아를 막기 위해 다른이들과 토론을하고, 위험한 인물을 처형해야 합니다.", sixthPick);
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
			systemMsg("이런...! 시민들이 한마음으로 뭉치치 못해 처형자 투표가 동률이 나왔습니다.");
			systemMsg("마을의 규칙상 처형은 하루 한사람만 가능하기 때문에, 오늘의 처형은 무효가 된채로 지나가게 됩니다.");
			systemMsg("시민들이 힘을 합치지 않으면, 마피아에게 마을을 빼앗기게 됩니다!");
		}

		void noticeBan(int banned){
			systemMsg("");
			systemMsg("시민 여러분의 의견을 통하여 처형자가 결정되었습니다.\n");
			systemMsg("그의 정체와는 상관없이... 사람들은 그를 향해 칼과 총을 겨누며 다가갑니다...");
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
				systemMsgone(firstPick+"의 정체는 '마피아'입니다.", fourthPick);
			}else if(checkStr.equals(secondPick)){
				systemMsgone(secondPick+"의 정체는 '마피아'입니다.", fourthPick);
			}else if(checkStr.equals(thirdPick)){
				systemMsgone(thirdPick+"의 정체는 '의사'입니다. 당신과 함께 시민을 지키는 존재입니다.", fourthPick);
			}else if(checkStr.equals(fourthPick)){
				systemMsgone("이상한 분이군요... 이런 기회를 낭비하시다니...", fourthPick);
				systemMsgone("혹시나 모르셔서 물어보신거라면 당신은 경찰입니다.", fourthPick);
				systemMsgone("마을의 평화를 지키는데엔 당신의 역할이 중요하다는것을 잊지 말아주세요.", fourthPick);
			}else if(checkStr.equals(fifthPick)){
				systemMsgone(secondPick+"의 정체는 '시민'입니다. 악당으로부터 지켜야할 존재죠.", fourthPick);
			}else if(checkStr.equals(sixthPick)){
				systemMsgone(secondPick+"의 정체는 '시민'입니다. 악당으로부터 지켜야할 존재죠.", fourthPick);
			}
			systemMsgone("", fourthPick);
		}

		void safe(){
			systemMsg("모두가 악당의 위협속에서 무사히 밤을 넘기는데 성공하였습니다!");
			systemMsg("오늘밤은 희생자가 발생하지 않았습니다.");
		}

		void murder(){
			systemMsg("잔인한 마피아에 의해 희생자가 발생하였습니다.");
			systemMsg("//Murder "+ killTarget);
		}

		void checkNumber(){
			if(mafiaNumber >= citizenNumber){
				systemMsg("이 마을은 결국 마피아들의 소굴이 되어버리고 말았습니다....");
				systemMsg("잔악무도한 악당들은 남은 시민들마저 무참하게 죽여버렸습니다....");
				systemMsg("");
				systemMsg("마피아는 "+firstPick+", "+secondPick+"입니다.");
				systemMsg(thirdPick+"의 정체는 의사였습니다.");
				systemMsg(fourthPick+"의 정체는 경찰이였습니다.");
				systemMsg(fifthPick+"의 정체는 시민이였습니다.");
				systemMsg(sixthPick+"의 정체는 시민이였습니다.");
				systemMsg("//GameEnd");
			}else if(mafiaNumber == 0){
				systemMsg("시민들이 서로 힘을 합친 덕분에 이 마을에서 마피아들을 전부 물리칠 수 있었습니다.");
				systemMsg("앞으로 이 마을에 닥칠 위기들도 여러분들이라면 이겨낼 수 있겠죠.");
				systemMsg("앞으로도 이 마을을 지켜주시길 바랍니다.");
				systemMsg("");
				systemMsg("마피아는 "+firstPick+", "+secondPick+"입니다.");
				systemMsg(thirdPick+"의 정체는 의사였습니다.");
				systemMsg(fourthPick+"의 정체는 경찰이였습니다.");
				systemMsg(fifthPick+"의 정체는 시민이였습니다.");
				systemMsg(sixthPick+"의 정체는 시민이였습니다.");
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
