package Windows;
import javax.swing.JFrame;//���
import javax.swing.JPanel;//���
import javax.swing.JButton;//��ť
import javax.swing.JLabel;//��ǩ
import javax.swing.JTextField;//�ı���

import java.awt.Font;//����
import java.awt.Color;//��ɫ

import demo.jfreechart;

import java.awt.event.ActionListener;//�¼�����
import java.awt.event.ActionEvent;//�¼�����

import javax.swing.JOptionPane;//��Ϣ����

import org.jfree.ui.RefineryUtilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Date;
import java.util.Map;
import java.awt.Dimension;
import java.util.Calendar; 
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import mail.SendmailUtil;

public class windows extends JFrame{
 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
public JPanel pnluser;
 public JLabel lbluserMain;
 public JLabel lbluserKey;
 public JLabel lbluserMail;
 public JTextField txtKey;
 public JTextField txtMail;
 public JButton btnSub;
 public JButton btnReset;
 
 public windows(){
  pnluser = new JPanel();
  lbluserMain = new JLabel();
  lbluserKey = new JLabel();
  lbluserMail = new JLabel();
  txtKey = new JTextField();
  txtMail = new JTextField();
  btnSub = new JButton();
  btnReset = new JButton();
  userInit();
 }
 
 public void userInit(){
  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//���ùرտ�ܵ�ͬʱ��������
  this.setSize(500,300);//���ÿ�ܴ�СΪ��300,��200
  this.setResizable(false);//���ÿ�ܲ����Ըı��С
  this.setTitle("TwitterAlert");//���ÿ�ܱ���
  this.pnluser.setLayout(null);//������岼�ֹ���
  //this.pnluser.setBackground(Color.cyan);//������屳����ɫ
  this.lbluserMain.setText("twitteralert");//���ñ�ǩ����
  this.lbluserMain.setFont(new Font("����",Font.BOLD | Font.ITALIC,26));//���ñ�ǩ����
  this.lbluserMain.setForeground(Color.RED);//���ñ�ǩ������ɫ
  this.lbluserKey.setText("key:");
  this.lbluserMail.setText("e-mail:");
  this.btnSub.setText("search");
  this.btnReset.setText("reset");
  this.lbluserMain.setBounds(170,25,200,20);//���ñ�ǩx����120,y����15,��60,��20
  this.lbluserKey.setBounds(150,75,80,20);
  this.lbluserMail.setBounds(150,115,80,25);
  this.txtKey.setBounds(210,75,120,20);
  this.txtMail.setBounds(210,115,120,20);
  this.btnSub.setBounds(150,160,80,25);
  this.btnSub.addActionListener(new ActionListener()//������ʵ��ActionListener�ӿ�
   {
    public void actionPerformed(ActionEvent e){
     try {
		btnsub_ActionEvent(e);
	} catch (AddressException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (UnsupportedEncodingException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (MessagingException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
    }    
   }
  ); 
  this.btnReset.setBounds(250,160,80,25);
  this.btnReset.addActionListener(new ActionListener()//������ʵ��ActionListener�ӿ�
   {
    public void actionPerformed(ActionEvent e){
     btnreset_ActionEvent(e);
    }    
   }
  );   
  this.pnluser.add(lbluserMain);//���ر�ǩ�����
  this.pnluser.add(lbluserKey);
  this.pnluser.add(lbluserMail);
  this.pnluser.add(txtKey);
  this.pnluser.add(txtMail);
  this.pnluser.add(btnSub);
  this.pnluser.add(btnReset);
  this.add(pnluser);//������嵽���
  this.setVisible(true);//���ÿ�ܿ���  
 }
 
 public void btnsub_ActionEvent(ActionEvent e) throws AddressException, UnsupportedEncodingException, MessagingException {
  String Key = txtKey.getText();
  String Mail = String.valueOf(txtMail.getText());
  if(Key.equals("")){
   JOptionPane.showMessageDialog(null,"key can't be empty","error",JOptionPane.ERROR_MESSAGE);
   return;
  }
  jfreechart demo = new jfreechart(Key,Key);
  demo.pack();
  RefineryUtilities.centerFrameOnScreen(demo);
  demo.setVisible(true);
  int fazhi=demo.time1+demo.time2+demo.time3+demo.time4+demo.time5;
  if(Mail.length()>0){
if(fazhi>100)
{ 
	String text ="The Number of Tweets << " +Key + ">> :" + fazhi;
	  Map<String,String> map= new HashMap<String,String>();
      SendmailUtil mail = new SendmailUtil("1904783949@qq.com","wvjufmsgeizgicff");
      map.put("mail.smtp.host", "smtp.qq.com");
      map.put("mail.smtp.auth", "true");
      map.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
      map.put("mail.smtp.port", "465");
      map.put("mail.smtp.socketFactory.port", "465");
      mail.setPros(map);
      mail.initMessage();
      List<String> list = new ArrayList<String>();
      list.add(Mail);
      mail.setRecipients(list);
      mail.setSubject(Key);
      mail.setDate(new Date());
      mail.setFrom("MY");
      mail.setContent(text, "text/html; charset=UTF-8");
      System.out.println(mail.sendMessage());
	}

  
  }
 
  
 }
 
 public void btnreset_ActionEvent(ActionEvent e){
  txtKey.setText("");
  txtMail.setText("");
 }
 
 public static void main(String[] args){
  new windows();
 }
}