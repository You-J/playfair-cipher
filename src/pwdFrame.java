

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
	
	public static char PasswordArry[][] = new char[5][5]; //��ȣ ��
	public static boolean CountText = false; //���ڼ� ���
	public static String zCheck =""; //z�� q�� �ٲ��ֱ� ����
	static String blankCheck=""; //���� Ȯ�� ��
	int blankCheckCount=0; //���� üũ
							
	
	pwdFrame(){
		frame = new JFrame("���� ġȯ ��ȣ 3113 ������ ");
		frame.setSize(600,400);
		frame.getContentPane().setBackground(Color.pink);
		frame.setLocation(200,200);
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		l = new JLabel("Ű ���� �� �ܾ �Է��ϼ���.");
		l.setLocation(80,100);
		l.setSize(l.getPreferredSize());
		frame.add(l);
		
		keyname = new JTextField();
		keyname.setColumns(15);
		keyname.setSize(keyname.getPreferredSize());
		
		keyname.setLocation(260,100);
		keyname.setToolTipText("KEY");
		frame.add(keyname);
		
		l = new JLabel("�� �� ������ �Է��ϼ���.");
		l.setLocation(95,130);
		l.setSize(l.getPreferredSize());
		frame.add(l);
		
		fullsec = new JTextField();
		fullsec.setColumns(15);
		fullsec.setSize(fullsec.getPreferredSize());
		
		fullsec.setLocation(260,130);
		keyname.setToolTipText("SENTENCE");
		frame.add(fullsec);
		
		b = new JButton("��ȣȭ �ϱ�");
		b.setSize(b.getPreferredSize());
		b.setLocation(150, 190);
		frame.add(b);
		
		b1 = new JButton("��ȣȭ �ϱ�");
		b1.setSize(b.getPreferredSize());
		b1.setLocation(350, 190);
		frame.add(b1);
		
		frame.setVisible(true);
		
		
		//��ȣȭ ��ư ������ ��
		b.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				keyValue = keyname.getText();
				PlainT = fullsec.getText();
				PArray(keyValue);//��ȣ������ �̵�.
				new EnScreen(); //��ȣȭ â ��
			}
		});
		
		//��ȣȭ ��ư ������ ��
		b1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				keyValue = keyname.getText();
				PlainT = fullsec.getText();
				new DeScreen();//��ȣȭ â 
			}
		});
	}
	
	public static void main(String args[]) {
		new pwdFrame(); //GUI�� ����
		
	}
	
	
	class EnScreen extends JFrame{ //��ȣȭ 
		EnScreen(){
			for( int i = 0 ; i < PlainT.length(); i++ ) 
			{
				if(PlainT.charAt(i)==' ') { //��������
					PlainT = PlainT.substring(0,i)+PlainT.substring(i+1,PlainT.length());
					blankCheck+=10;
				}
				else{
					blankCheck+=0;
				}
				if(PlainT.charAt(i)=='z') { //z�� q�� �ٲ���. ����
					PlainT = PlainT.substring(0,i)+'q'+PlainT.substring(i+1,PlainT.length());
					zCheck+=1;
				}
				else {
					zCheck+=0;
				}
			}
			
			encryption = strEncryption(keyValue, PlainT);
			setTitle("��ȣȭ");
			
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
				if(encryption.charAt(i)==' ') //��������
					encryption = encryption.substring(0,i)+encryption.substring(i+1,encryption.length());//���� �� �־���
			}
		}	
	}
	
	class DeScreen extends JFrame{ //��ȣȭ ȭ��
		DeScreen(){
			for( int i = 0 ; i < encryption.length(); i++ ) {
				if(encryption.charAt(i)==' ') //��������
					encryption = encryption.substring(0,i)+encryption.substring(i+1,encryption.length()); //``
			}
	
			
			decryption = strDecryption(keyValue, encryption, zCheck); // ��ȣȭ ȣ�� �Լ�.
			
			for( int i = 0 ; i < decryption.length() ; i++) {
				if(blankCheck.charAt(i)=='1') { //
					decryption = decryption.substring(0,i)+" "+decryption.substring(i,decryption.length());
				}
			}
			
			setSize(500,300);
			setTitle("��ȣȭ");
			
			
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
		String DieKey = "";					// �ߺ� ���� 
		boolean DuplicateCh = false;		// �ߺ� üũ
		int keyLengthCount = 0;					// �Է��� ���� �������� ���� 
		
		keyname += "abcdefghijklmnopqrstuvwxyz"; 	// Ű�� ��� ���ĺ��� �߰�.
		
		
		// �ߺ�ó��
		for( int i = 0 ; i < keyname.length() ; i++ ) {
			for( int j = 0 ; j < DieKey.length() ; j++ ) {
				if(keyname.charAt(i)==DieKey.charAt(j)) { //�ߺ��̸�
					DuplicateCh = true; //�ߺ� ������ true
					break;
				}
			}
			if(!(DuplicateCh)) DieKey += keyname.charAt(i);
			DuplicateCh = false;
		}
		//�迭�� ����
		for( int i = 0 ; i < PasswordArry.length ; i++ ){
			for( int j = 0; j <PasswordArry[i].length ; j++ ){
				PasswordArry[i][j] = DieKey.charAt(keyLengthCount++);
			}
		}		
		
		//�迭�� ����
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
			if(PlainT.charAt(i)==' ') { //��������
				PlainT = PlainT.substring(0,i)+PlainT.substring(i+1,PlainT.length()); //``
				PlainT+=10; //��
			}
			else{
				PlainT+=0;
			}
			
			if(PlainT.charAt(i)=='z') { //z�� q�� �ٲ��༭ ó����.
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
		ArrayList<char[]> beforeDe = new ArrayList<char[]>(); //�ٲٱ� �� ���ھ�ȣ�� ������ ��
		ArrayList<char[]> afterDe = new ArrayList<char[]>(); //�ٲ� ���� ���ھ�ȣ ������ ��
		int x1 = 0 , x2 = 0 , y1 = 0, y2 = 0; //���� ��ȣ �� ������ ������ ��,�� ��
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
			
			if(x1==x2) //���� ���� ��� ���� �ٷ� �Ʒ��� ����
			{
				tmpArr[0] = PasswordArry[x1][(y1+4)%5];
				tmpArr[1] = PasswordArry[x2][(y2+4)%5];
			}
			else if(y1==y2) //���� ���� ��� ���� �ٷ� �� �� ����
			{
				tmpArr[0] = PasswordArry[(x1+4)%5][y1];
				tmpArr[1] = PasswordArry[(x2+4)%5][y2];
			}
			else //��, �� �ٸ���� ���� �밢���� �ִ� ��.
			{
				tmpArr[0] = PasswordArry[x2][y1];
				tmpArr[1] = PasswordArry[x1][y2];
			}
			
			afterDe.add(tmpArr);
			
		}
		
		for(int i = 0 ; i < afterDe.size() ; i++) { //�ߺ� ���ڿ� ��������
			if(i!=afterDe.size()-1 && afterDe.get(i)[1]=='x'
					&& afterDe.get(i)[0]==afterDe.get(i+1)[0]){	
				decStr += afterDe.get(i)[0];
			}
			else{
				decStr += afterDe.get(i)[0]+""+afterDe.get(i)[1];
			}
		}
		
		
		
		for(int i = 0 ; i < zCheck.length() ; i++ ) { //z��ġ ã�Ƽ� q�� ��������
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
		
		for( int i = 0 ; i < PlainT.length(); i+=2 ) { // arraylist ����
			char[] tmpArr = new char[2];
			tmpArr[0] = PlainT.charAt(i);
			
			try{
				if( PlainT.charAt(i) == PlainT.charAt(i+1)) //���� �ݺ��Ǹ� x�߰�
				{
					tmpArr[1] = 'x';
					i--;
					//System.out.println("X���� ��");
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
			for( int j = 0 ; j < PasswordArry.length; j++ ) { //���ھ�ȣ�� ���� ��ġüũ
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
			
			if(x1==x2) { //���� �������
				tmpArr[0] = PasswordArry[x1][(y1+1)%5];
				tmpArr[1] = PasswordArry[x2][(y2+1)%5];
			}
			else if(y1==y2) { //���� ���� ���
				tmpArr[0] = PasswordArry[(x1+1)%5][y1];
				tmpArr[1] = PasswordArry[(x2+1)%5][y2];
			} 
			else { //��, �� ��� �ٸ����
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

