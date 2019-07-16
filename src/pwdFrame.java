

import javax.swing.*;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class pwdFrame extends Frame implements ActionListener{
	
	
	
	JFrame frame;
	private JTextField keyname;
	private JTextField fullsec;
	JLabel l;
	JButton b;
	JButton b1;
	static String keyValue;
	static String PlainT;
	
	static String encryption;
	static String decryption;
	
	public static char PasswordArry[][] = new char[5][5]; //암호 판
	public static boolean CountText = false; //글자수 출력
	public static String zCheck =""; //z를 q로 바꿔주기 위함
	static String blankCheck=""; //띄어쓰기 확인 용
	int blankCheckCount=0; //개수 체크
							
	
	pwdFrame(){
		frame = new JFrame("다중 치환 암호 3113 이유정 ");
		frame.setSize(600,400);
		frame.getContentPane().setBackground(Color.pink);
		frame.setLocation(200,200);
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		l = new JLabel("키 값이 될 단어를 입력하세요.");
		l.setLocation(80,100);
		l.setSize(l.getPreferredSize());
		frame.add(l);
		
		keyname = new JTextField();
		keyname.setColumns(15);
		keyname.setSize(keyname.getPreferredSize());
		
		keyname.setLocation(260,100);
		keyname.setToolTipText("KEY");
		frame.add(keyname);
		
		l = new JLabel("평문 한 문장을 입력하세요.");
		l.setLocation(95,130);
		l.setSize(l.getPreferredSize());
		frame.add(l);
		
		fullsec = new JTextField();
		fullsec.setColumns(15);
		fullsec.setSize(fullsec.getPreferredSize());
		
		fullsec.setLocation(260,130);
		keyname.setToolTipText("SENTENCE");
		frame.add(fullsec);
		
		b = new JButton("암호화 하기");
		b.setSize(b.getPreferredSize());
		b.setLocation(150, 190);
		frame.add(b);
		
		b1 = new JButton("복호화 하기");
		b1.setSize(b.getPreferredSize());
		b1.setLocation(350, 190);
		frame.add(b1);
		
		frame.setVisible(true);
		
		
		//암호화 버튼 눌렀을 때
		b.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				keyValue = keyname.getText();
				PlainT = fullsec.getText();
				PArray(keyValue);//암호판으로 이동.
				new EnScreen(); //암호화 창 뜸
			}
		});
		
		//복호화 버튼 눌렀을 때
		b1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				keyValue = keyname.getText();
				PlainT = fullsec.getText();
				new DeScreen();//복호화 창 
			}
		});
	}
	
	public static void main(String args[]) {
		new pwdFrame(); //GUI가 보임
		
	}
	
	
	class EnScreen extends JFrame{ //암호화 
		EnScreen(){
			for( int i = 0 ; i < PlainT.length(); i++ ) 
			{
				if(PlainT.charAt(i)==' ') { //공백제거
					PlainT = PlainT.substring(0,i)+PlainT.substring(i+1,PlainT.length());
					blankCheck+=10;
				}
				else{
					blankCheck+=0;
				}
				if(PlainT.charAt(i)=='z') { //z를 q로 바꿔줌. 임의
					PlainT = PlainT.substring(0,i)+'q'+PlainT.substring(i+1,PlainT.length());
					zCheck+=1;
				}
				else {
					zCheck+=0;
				}
			}
			
			encryption = strEncryption(keyValue, PlainT);
			setTitle("암호화");
			
			JPanel NewWindowContainer = new JPanel();
			setContentPane(NewWindowContainer);
			
			JLabel Label1 = new JLabel(encryption);
			NewWindowContainer.setLayout(null);
			Label1.setBounds(200, 30, 150, 150);
			//Label1.setLocation(0, 0);
			NewWindowContainer.add(Label1);
			
			setSize(500,300);
			
			getContentPane().setBackground(Color.PINK);
			setResizable(false);
			setVisible(true);
			for( int i = 0 ; i < encryption.length(); i++ ) {
				if(encryption.charAt(i)==' ') //공백제거
					encryption = encryption.substring(0,i)+encryption.substring(i+1,encryption.length());//다음 걸 넣어줌
			}
		}	
	}
	
	class DeScreen extends JFrame{ //복호화 화면
		DeScreen(){
			for( int i = 0 ; i < encryption.length(); i++ ) {
				if(encryption.charAt(i)==' ') //공백제거
					encryption = encryption.substring(0,i)+encryption.substring(i+1,encryption.length()); //``
			}
	
			
			decryption = strDecryption(keyValue, encryption, zCheck); // 복호화 호출 함수.
			
			for( int i = 0 ; i < decryption.length() ; i++) {
				if(blankCheck.charAt(i)=='1') { //
					decryption = decryption.substring(0,i)+" "+decryption.substring(i,decryption.length());
				}
			}
			
			setSize(500,300);
			setTitle("복호화");
			
			
			JPanel NewWindowContainer = new JPanel();
			setContentPane(NewWindowContainer);
			
			JLabel Label1 = new JLabel(decryption);
			NewWindowContainer.setLayout(null);
			Label1.setBounds(200, 30, 150, 150);
			getContentPane().setBackground(Color.PINK);
			
			NewWindowContainer.add(Label1);
						
			setResizable(false);
			setVisible(true);
		}
	}
	
	private static void PArray(String keyname) {
		String DieKey = "";					// 중복 제거 
		boolean DuplicateCh = false;		// 중복 체크
		int keyLengthCount = 0;					// 입력한 값을 넣으려는 개수 
		
		keyname += "abcdefghijklmnopqrstuvwxyz"; 	// 키에 모든 알파벳을 추가.
		
		
		// 중복처리
		for( int i = 0 ; i < keyname.length() ; i++ ) {
			for( int j = 0 ; j < DieKey.length() ; j++ ) {
				if(keyname.charAt(i)==DieKey.charAt(j)) { //중복이면
					DuplicateCh = true; //중복 변수에 true
					break;
				}
			}
			if(!(DuplicateCh)) DieKey += keyname.charAt(i);
			DuplicateCh = false;
		}
		//배열에 대입
		for( int i = 0 ; i < PasswordArry.length ; i++ ){
			for( int j = 0; j <PasswordArry[i].length ; j++ ){
				PasswordArry[i][j] = DieKey.charAt(keyLengthCount++);
			}
		}		
		
		//배열에 대입
		for( int i = 0 ; i < PasswordArry.length ; i++ ) {
			for( int j = 0; j <PasswordArry[i].length ; j++ ) {
				System.out.print(PasswordArry[i][j]+"-");
			}
			System.out.println();
		}		
						
		
	}
	
	private static void encryption() {
		System.out.println("encryption");
		for( int i = 0 ; i < PlainT.length() ; i++ ) 
		{
			if(PlainT.charAt(i)==' ') { //공백제거
				PlainT = PlainT.substring(0,i)+PlainT.substring(i+1,PlainT.length()); //``
				PlainT+=10; //평문
			}
			else{
				PlainT+=0;
			}
			
			if(PlainT.charAt(i)=='z') { //z를 q로 바꿔줘서 처리함.
				PlainT = PlainT.substring(0,i)+'q'+PlainT.substring(i+1,PlainT.length());
				zCheck+=1;
			}
			else{
				zCheck+=0;
			}
		}
		
		encryption = strEncryption(keyValue, PlainT);
	}
	
	private static String strDecryption(String keyValue, String PlainT, String zCheck) {
		ArrayList<char[]> beforeDe = new ArrayList<char[]>(); //바꾸기 전 쌍자암호를 저장할 곳
		ArrayList<char[]> afterDe = new ArrayList<char[]>(); //바꾼 후의 쌍자암호 저장할 곳
		int x1 = 0 , x2 = 0 , y1 = 0, y2 = 0; //쌍자 암호 두 글자의 각각의 행,열 값
		String decStr ="";

		int lengthOddFlag = 1;
		
		
		for( int i = 0 ; i < PlainT.length() ; i+=2 )
		{
			char[] tmpArr = new char[2];
			tmpArr[0] = PlainT.charAt(i);
			tmpArr[1] = PlainT.charAt(i+1);
			beforeDe.add(tmpArr);
		}
		
		
		for(int i = 0 ; i < beforeDe.size() ; i++ )
		{

			char[] tmpArr = new char[2];
			for( int j = 0 ; j < PasswordArry.length ; j++ )
			{
				for( int k = 0 ; k < PasswordArry[j].length ; k++ )
				{
					if(PasswordArry[j][k] == beforeDe.get(i)[0])
					{
						x1 = j;
						y1 = k;
					}
					if(PasswordArry[j][k] == beforeDe.get(i)[1])
					{
						x2 = j;
						y2 = k;
					}
				}
			}
			
			if(x1==x2) //행이 같은 경우 각각 바로 아래열 대입
			{
				tmpArr[0] = PasswordArry[x1][(y1+4)%5];
				tmpArr[1] = PasswordArry[x2][(y2+4)%5];
			}
			else if(y1==y2) //열이 같은 경우 각각 바로 옆 열 대입
			{
				tmpArr[0] = PasswordArry[(x1+4)%5][y1];
				tmpArr[1] = PasswordArry[(x2+4)%5][y2];
			}
			else //행, 열 다른경우 각자 대각선에 있는 곳.
			{
				tmpArr[0] = PasswordArry[x2][y1];
				tmpArr[1] = PasswordArry[x1][y2];
			}
			
			afterDe.add(tmpArr);
			
		}
		
		for(int i = 0 ; i < afterDe.size() ; i++) { //중복 문자열 돌려놓음
			if(i!=afterDe.size()-1 && afterDe.get(i)[1]=='x'
					&& afterDe.get(i)[0]==afterDe.get(i+1)[0]){	
				decStr += afterDe.get(i)[0];
			}
			else{
				decStr += afterDe.get(i)[0]+""+afterDe.get(i)[1];
			}
		}
		
		
		
		for(int i = 0 ; i < zCheck.length() ; i++ ) { //z위치 찾아서 q로 돌려놓음
			if( zCheck.charAt(i) == '1' ) 
				decStr = decStr.substring(0,i)+'z'+decStr.substring(i+1,decStr.length());
			
		}
		
		if(CountText) decStr = decStr.substring(0,decStr.length()-1);
		return decStr;
	}
	
	
	private static String strEncryption(String keyValue, String PlainT){
		
		ArrayList<char[]> beforeEn = new ArrayList<char[]>();
		ArrayList<char[]> afterEn = new ArrayList<char[]>();
		int x1 = 0 , x2 = 0 , y1 = 0, y2 = 0;
		String encStr ="";		
		
		for( int i = 0 ; i < PlainT.length(); i+=2 ) { // arraylist 세팅
			char[] tmpArr = new char[2];
			tmpArr[0] = PlainT.charAt(i);
			
			try{
				if( PlainT.charAt(i) == PlainT.charAt(i+1)) //글이 반복되면 x추가
				{
					tmpArr[1] = 'x';
					i--;
					//System.out.println("X실행 중");
				}else{
					tmpArr[1] = PlainT.charAt(i+1);
				}
			}
			catch(StringIndexOutOfBoundsException e){
				tmpArr[1] = 'x'; 
				CountText = true;
			}
			beforeEn.add(tmpArr);
		}
		
		for(int i = 0 ; i < beforeEn.size() ; i++ ){
			System.out.print(beforeEn.get(i)[0]+""+beforeEn.get(i)[1]+" ");
		}
		System.out.println();
		
		for(int i = 0 ; i < beforeEn.size() ; i++ ){

			char[] tmpArr = new char[2];
			for( int j = 0 ; j < PasswordArry.length; j++ ) { //쌍자암호의 각각 위치체크
				for( int k = 0 ; k < PasswordArry[j].length; k++ )
				{
					if(PasswordArry[j][k] == beforeEn.get(i)[0]){
						x1 = j;
						y1 = k;
					}
					if(PasswordArry[j][k] == beforeEn.get(i)[1]){
						x2 = j;
						y2 = k;
					}
				}
			}
			
			if(x1==x2) { //행이 같은경우
				tmpArr[0] = PasswordArry[x1][(y1+1)%5];
				tmpArr[1] = PasswordArry[x2][(y2+1)%5];
			}
			else if(y1==y2) { //열이 같은 경우
				tmpArr[0] = PasswordArry[(x1+1)%5][y1];
				tmpArr[1] = PasswordArry[(x2+1)%5][y2];
			} 
			else { //행, 열 모두 다른경우
				tmpArr[0] = PasswordArry[x2][y1];
				tmpArr[1] = PasswordArry[x1][y2];
			}
			afterEn.add(tmpArr);
		}
		for(int i = 0 ; i < afterEn.size() ; i++) {
			encStr += afterEn.get(i)[0]+""+afterEn.get(i)[1]+" ";
		}
		return encStr;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}
	
	
	
}

