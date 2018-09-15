package demo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Date;
import java.util.Map;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Calendar; 
import java.text.SimpleDateFormat;
import java.util.Properties;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import mail.SendmailUtil;
import twitter4j.*;

import java.text.ParseException;

/**
 * @author Administrator
 */
public class jfreechart extends ApplicationFrame {
		
		 private static final long serialVersionUID = 1L;
	static String sdate1=null;
	static String sdate2=null;
	static String sdate3=null;
	static String sdate4=null;
	static String sdate5=null;
	public static int time1=0;
	public static int time2=0;
	public static int time3=0;
	public static int time4=0;
	public static int time5=0;
	public static String getSpecifiedDayBefore(String specifiedDay){ 
		Calendar c = Calendar.getInstance(); 
		Date date=null; 
		try { 
		date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay); 
		} catch (ParseException e) { 
		e.printStackTrace(); 
		} 
		c.setTime(date); 
		int day=c.get(Calendar.DATE); 
		c.set(Calendar.DATE,day-1); 

		String dayBefore=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()); 
		return dayBefore; 
		} 
	public static String getSpecifiedDayAfter(String specifiedDay){ 
		Calendar c = Calendar.getInstance(); 
		Date date=null; 
		try { 
		date = new SimpleDateFormat("yyyy-MM-dd").parse(specifiedDay); 
		} catch (ParseException e) { 
		e.printStackTrace(); 
		} 
		c.setTime(date); 
		int day=c.get(Calendar.DATE); 
		c.set(Calendar.DATE,day+1); 

		String dayAfter=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()); 
		return dayAfter; 
		} 
	 static int search(String key, int count, String sinceDate,  
            String endDate) 
            {
		try
		 {
		 Query query=new Query();
		 query.setQuery(key);
		 query.setCount(100);
		 query.setSince(sinceDate);
		 query.setUntil(endDate);
		 QueryResult result = null;
		 
		 int mumber=0; 
		 
		 Twitter twitter = new TwitterFactory().getInstance(); 
		 
		 
		 do {  
             result = twitter.search(query);  
             List<Status> tweets = result.getTweets();  
             mumber+=tweets.size();
             if(mumber>=count)break;  
         } while ((query = result.nextQuery()) != null);  
            return mumber;
		 } catch (TwitterException te) {
	            te.printStackTrace();
	            System.out.println("Failed to search tweets: " + te.getMessage());
	            System.exit(-1);
	            return -1;
	        }
            }
	
		 public jfreechart(String str,String Key) {
		  super(str);
		  Date ddate=new Date();;
			 sdate1=(new SimpleDateFormat("yyyy-MM-dd")).format(ddate);
			 sdate2=getSpecifiedDayBefore(sdate1);
			 sdate3=getSpecifiedDayBefore(sdate2);
			 sdate4=getSpecifiedDayBefore(sdate3);
			 sdate5=getSpecifiedDayBefore(sdate4);

			 time5=search(Key,2000,sdate1,getSpecifiedDayAfter(sdate1));
			 time4=search(Key,2000,sdate2,getSpecifiedDayAfter(sdate2));
			 time3=search(Key,2000,sdate3,getSpecifiedDayAfter(sdate3));
			 time2=search(Key,2000,sdate4,getSpecifiedDayAfter(sdate4));
			 time1=search(Key,2000,sdate5,getSpecifiedDayAfter(sdate5));
		

		  //����ͼƬ���裺
		  CategoryDataset dataset = createDataset();
		  JFreeChart chart = createChart(dataset);
		  ChartPanel chartPanel = new ChartPanel(chart);
		  //������ʾ
		  chartPanel.setPreferredSize(new Dimension(700, 300));
		  setContentPane(chartPanel);
		  
		  
		  
		  
		  
		 }
		 public CategoryDataset createDataset()
		 {
		  String series1 = "1";
		  // ��
		  String category1 = sdate5;
		  String category2 = sdate4;
		  String category3 = sdate3;
		  String category4 = sdate2;
		  String category5 = sdate1;

		  // ��������Դ
		  DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		  // ��������
		  dataset.addValue(time1, series1, category1);
		  dataset.addValue(time2, series1, category2);
		  dataset.addValue(time3, series1, category3);
		  dataset.addValue(time4, series1, category4);
		  dataset.addValue(time5, series1, category5);

		  return dataset;
		 }
		 public JFreeChart createChart(CategoryDataset dataset) {
			  // create the chart..
			  JFreeChart chart = ChartFactory.createBarChart("analysis",// ����
			    "time",// x��
			    "num",// y��
			    dataset,// ����
			    PlotOrientation.VERTICAL,// ��λ��VERTICAL����ֱ
			    false,
			    false,// �Ƿ����ɹ���
			    false);// �Ƿ�����URL����
			  chart.setBackgroundPaint(Color.white);
			  Font font = new Font("����", 10, 20);
			  TextTitle title = chart.getTitle();
			  title.setFont(font);
			  CategoryPlot plot = (CategoryPlot) chart.getPlot();
			  plot.setBackgroundPaint(Color.white);
			  plot.setRangeGridlinePaint(Color.BLACK);
			  plot.getDomainAxis().setTickLabelFont(font);
			  plot.getDomainAxis().setLabelFont(font);
			  NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
			  rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
			  rangeAxis.setUpperMargin(0.15);
			  rangeAxis.setLabelFont(font);
			  CategoryItemRenderer renderer = plot.getRenderer();
			  renderer.setSeriesItemLabelsVisible(0, Boolean.TRUE);
			  CategoryAxis domainAxis = plot.getDomainAxis();
			  domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);// ��б45��
			  BarRenderer renderer1 = new BarRenderer();// �������ӵ��������
			  renderer1.setMinimumBarLength(0.5);
			  renderer1.setItemMargin(0.1);
			  renderer1.setShadowVisible(false);
			  plot.setRenderer(renderer1);
			  plot.setBackgroundAlpha((float) 0.5);
		

			  return chart;
			 }
		 public  JPanel createPaned() {
				
			  JFreeChart chart = createChart(createDataset());
			  return new ChartPanel(chart);
			 }
		 
		 /* 	public void date(String key){
			Date ddate=new Date();;
			 sdate1=(new SimpleDateFormat("yyyy-MM-dd")).format(ddate);
			 sdate2=getSpecifiedDayBefore(sdate1);
			 sdate3=getSpecifiedDayBefore(sdate2);
			 sdate4=getSpecifiedDayBefore(sdate3);
			 sdate5=getSpecifiedDayBefore(sdate4);

			 time5=search(key,1000,sdate1,getSpecifiedDayAfter(sdate1));
			 time4=search(key,1000,sdate2,getSpecifiedDayAfter(sdate2));
			 time3=search(key,1000,sdate3,getSpecifiedDayAfter(sdate3));
			 time2=search(key,1000,sdate4,getSpecifiedDayAfter(sdate4));
			 time1=search(key,1000,sdate5,getSpecifiedDayAfter(sdate5));
		}
		 }*
	public static void main(String[] args) throws AddressException, MessagingException, UnsupportedEncodingException {
		String key="earthquake";
		Date ddate=new Date();;
		 sdate1=(new SimpleDateFormat("yyyy-MM-dd")).format(ddate);
		 sdate2=getSpecifiedDayBefore(sdate1);
		 sdate3=getSpecifiedDayBefore(sdate2);
		 sdate4=getSpecifiedDayBefore(sdate3);
		 sdate5=getSpecifiedDayBefore(sdate4);

		 time5=search(key,1000,sdate1,getSpecifiedDayAfter(sdate1));
		 time4=search(key,1000,sdate2,getSpecifiedDayAfter(sdate2));
		 time3=search(key,1000,sdate3,getSpecifiedDayAfter(sdate3));
		 time2=search(key,1000,sdate4,getSpecifiedDayAfter(sdate4));
		 time1=search(key,1000,sdate5,getSpecifiedDayAfter(sdate5));

		 
		 jfreechart demo = new jfreechart(key);
		  demo.pack();
		  RefineryUtilities.centerFrameOnScreen(demo);
		  demo.setVisible(true);
		int asd=time1+time2+time3+time4+time5;
		  if (asd>100)
		  {
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
		        list.add("451060195@qq.com");
		        mail.setRecipients(list);
		        mail.setSubject(key);
		        mail.setDate(new Date());
		        mail.setFrom("MY");
		        mail.setContent("test", "text/html; charset=UTF-8");
		        System.out.println(mail.sendMessage());
};
		 
	}*/
	 }