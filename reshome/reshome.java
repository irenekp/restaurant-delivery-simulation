package selfstudy.reshome;
import selfstudy.error.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import com.opencsv.CSVReader;
public class reshome {  
	static String res=null;
	static int res_id; 
	static String status;
static class Orders{
	private static Dialog d;
	//static String res=null;
	//static int res_id=1; 
Orders(){
	Frame f=new Frame(res+"| Orders Page");  
    //Label l1=new Label("Your Orders:");  
    //l1.setBounds(20,80, 300,30); 
    //f.add(l1);		
    try {
  		  Class.forName("com.mysql.cj.jdbc.Driver");  
  			Connection con=DriverManager.getConnection(  
  			"jdbc:mysql://localhost:3306/FOOD","root","password");  //Enter your own Password
  			Statement stmt=con.createStatement();  
  			//PAGE 1: RESTAURANT NAMES.
  			String sql="select i.order_id,order_status,i.item_id,item_name from(orders o join order_item i on o.order_id=i.order_id) join menu m on i.item_id=m.item_id where o.res_id="+res_id;
  			ResultSet rs=stmt.executeQuery(sql); 
  	//CONVERTING ResultSet from query into string format to create individual restaurant nodes in page
			ArrayList<String> order_id =new ArrayList<String>();
			ArrayList<String> order_status =new ArrayList<String>();
  			ArrayList<String> item_id =new ArrayList<String>();
  			ArrayList<String> item_name =new ArrayList<String>();
  			while (rs.next()) {
  				if(rs.getString("order_status").equals("placed")||rs.getString("order_status").equals("preparing"))
  				{
  					order_id.add(rs.getString("order_id"));
  					System.out.println("heyyy");
  					order_status.add(rs.getString("order_status"));
  					item_id.add(rs.getString("item_id"));
  					item_name.add(rs.getString("item_name"));
  				}
  			}
  			String[] OI = order_id.toArray(new String[0]);
  			final String[] OS = order_status.toArray(new String[0]);
  			String[] II = item_id.toArray(new String[0]);
  			String[] IN = item_name.toArray(new String[0]);
  			String array[][]= {OI,OS,II,IN};
  			for(int i=0;i<array.length;i++)
  			{
  				for(int j=0;j<array[i].length;j++)
  				{
  					System.out.println(array[i][j]+"  ");
  				}
  				System.out.println("//");
  			}
  			String data[][]=new String[array[0].length][array.length];
  			for(int i = 0; i < array[0].length; i++)
  	    	{
  	      	    for(int j=0; j < array.length; j++)
  	            {
  	                data[i][j]=array[j][i];
  	            }
  	        }
  			String[] columns= {"OrderID","Status","ItemID","ItemName"};
  			JTable jt=new JTable(data,columns);    
  		    jt.setBounds(30,40,200,300); 
  		  jt.setBackground(Color.pink);
		    Font l=new Font (Font.MONOSPACED,Font.PLAIN, 13);
		    Color c=Color.decode("#761137");
		    jt.setForeground(c);
		    jt.setFont(l);
  		    jt.setVisible(true);
  		    Button y=new Button("Back");
  		    y.setBounds(50,350,120,30);
  		  y.addActionListener ( new ActionListener()  
          {  
              public void actionPerformed( ActionEvent e )  
              {  
            	f.dispose();
           	    new reshome(res_id);
           	    
              }  
          }); 
  		  f.add(y);
  		    Button x=new Button("Update Status");
  		    x.setBounds(50,400,120,30);  
  		  x.addActionListener(new ActionListener(){  
		        public void actionPerformed(ActionEvent e){  
		        	  d = new Dialog(f , "Update Status", true);  
                    d.setLayout( new FlowLayout() );  
                    d.add( new Label ("Enter Order ID"));  
                    final List l2=new List(1, false);    
                    l2.setBounds(50,100,150,20);
                    for(int i=0;i<order_id.size();i++)
                    {
                  	  l2.add(order_id.get(i));
                    }
                    d.add(l2);
                    Label l= new Label ("Enter Status");
                    l.setBounds(50,150,200,20);
                    d.add(l);  
                    final List l1=new List(1, false);  
                    l1.setBounds(100,200, 100,70);  
                    l1.add("preparing");  
                    l1.add("cancelled");  
                    l1.add("prepared"); 
                    //l1.setVisible(true);
                    d.add(l1);
                    Button b = new Button ("OK");
                    d.setBackground(Color.pink);
					    Font l3=new Font (Font.MONOSPACED,Font.PLAIN, 13);
					    Color c=Color.decode("#761137");
					    d.setForeground(c);
					    d.setFont(l3);
                    //when ok pressed in dialog. it saves both item name and qty in a map
                    b.addActionListener ( new ActionListener()  
                    {  
                        public void actionPerformed( ActionEvent e )  
                        {  
                     	   int o_id=Integer.parseInt(l2.getItem(l2.getSelectedIndex()));
                         status=l1.getItem(l1.getSelectedIndex());
                         System.out.print(status);
                         try (Statement stmt = con.createStatement();){
               		      //inserts into order table: note: lot of variables here have been self initialized by me but it should be taken from other tables and modules
               		      String sql="update orders set order_status='"+status+"' where order_id="+o_id;
               		      stmt.executeUpdate(sql);
               	  }catch (SQLException e8) {
           		      e8.printStackTrace();
           		    }
                         new except();
                     d.setVisible(false);
                     d.dispose();
                     new reshome(res_id);
                       }  
                    });  
                    b.setBounds(50,250,50,50);
                    d.add(b);
                    Button c2=new Button("Cancel");
                    c2.setBounds(150,250,100,50);
                    c2.addActionListener(new ActionListener()  
                    {  
                        public void actionPerformed( ActionEvent e )  
                        {  
                     d.setVisible(false); 
                     new reshome(res_id);
                     d.dispose();
                       }  
                    });
                    d.add(c2);
                    d.setSize(300,300);    
                    d.setVisible(true);
		              f.setVisible(false);
		              d.dispose();
		              System.out.print("WORKED");
		            }  
		        });   
  		    f.add(x);  
  		    JScrollPane sp=new JScrollPane(jt);    
  		    f.add(sp);      
  		    f.setSize(400,400);
  		  f.setBackground(Color.pink);
		    //Font l=new Font (Font.MONOSPACED,Font.PLAIN, 13);
		    //Color c=Color.decode("#761137");
		    f.setForeground(c);
		    f.setFont(l);
  		    f.setVisible(true);   
  	  }catch(Exception e)
  	  {
  		  e.printStackTrace();
  		 new except();
  	  }
}
}
public reshome(int rid) {
	res_id=rid;
	String earnings="";
    Frame f=new Frame("Restaurant | Home Page");  
    Label l1=new Label("Welcome to your Home Page");  
    l1.setBounds(100,80, 300,30); 
    f.add(l1);
  	 try {
  		  Class.forName("com.mysql.cj.jdbc.Driver");  
  			Connection con=DriverManager.getConnection(  
  			"jdbc:mysql://localhost:3306/FOOD","root","password");  //Enter your own Password
  			Statement stmt=con.createStatement();  
  			//PAGE 1: RESTAURANT NAMES.
  			ResultSet rs=stmt.executeQuery("select res_earnings from Restaurant where res_id="+res_id); 
  	//CONVERTING ResultSet from query into string format to create individual restaurant nodes in page
  			while (rs.next()) {
  				earnings=rs.getString("res_earnings");
  			}
  	  }catch(Exception e)
  	  {
  		  e.printStackTrace();
  		new except();
  	  }
      l1=new Label("Your Earnings this week are:"+earnings);
      l1.setBounds(100,150,300,30); 
      f.add(l1);
    Button b=new Button("Edit Menu");  
    b.setBounds(150,250,95,30);  
    b.addActionListener(new ActionListener(){
    	public void actionPerformed(ActionEvent ae) {
    		
    		String url = "jdbc:mysql://localhost:3306/food";
    		String driver = "com.mysql.cj.jdbc.Driver";
    		String userName = "root";
    		String password = "password";
    		
    		try { 
    			Class.forName(driver).newInstance(); 
    			Connection conn = DriverManager.getConnection(url,userName,password);
    			Statement st = conn.createStatement();
    			ResultSet re = st.executeQuery("select * from menu where res_id = " + rid);
    			if(re.next()) {
    				st.executeUpdate("delete from menu where res_id="+ rid);
    			}
    				JFileChooser j = new JFileChooser();
            		int i = j.showOpenDialog(null);
            		 if(i==JFileChooser.APPROVE_OPTION){    
            		        File f= j.getSelectedFile();    
            		        String filepath=f.getPath();    
            		        try{  
            		        	FileReader filereader = new FileReader(filepath); 
            		            CSVReader csvReader = new CSVReader(filereader); 
            		            String[] nextRecord;
            		            String[] item = new String[6];
            		            while ((nextRecord = csvReader.readNext()) != null) {
            		            	int it = 0;
            		                for (String cell : nextRecord) { 
            		                	item[it++] = cell;
            		                } 
            		                PreparedStatement myPreStatement = conn.prepareStatement("INSERT into MENU(item_id,item_name, item_time, item_category, item_price,res_id) values(?,?, ?, ?, ?, ?)");
        		        			myPreStatement.setString(1, item[0]);
        		        			myPreStatement.setString(2, item[1]);
        		                    myPreStatement.setInt(3, Integer.parseInt(item[2]));
        		                    myPreStatement.setString(4, item[3]);
        		                    myPreStatement.setInt(5, Integer.parseInt(item[4]));
        		                    myPreStatement.setInt(6,res_id);
        		                    myPreStatement.execute();
            		            } 
            		            csvReader.close();
            		        }
            		        catch (Exception ex){
            		        	ex.printStackTrace();  
            		        	new except();
            		        } 
            		   }

        		                      
        		 conn.close();
    		}
    		catch (Exception e) { 
    			e.printStackTrace(); 
    			new except();
    		}
            //frame.dispose();
    	}
    });  
    f.add(b);  
    Button b1=new Button("View Orders");  
    b1.setBounds(150,300,95,30);  
    b1.addActionListener(new ActionListener(){  
        public void actionPerformed(ActionEvent e){
        	//add button handler to help view orders.
        	new Orders();
                f.setVisible(false); 
                f.dispose();
            }  
        }); 
    f.add(b1);  
    Button b2=new Button("Log Out");  
    b2.setBounds(150,350,95,30);  
    b2.addActionListener(new ActionListener(){  
        public void actionPerformed(ActionEvent e){
                f.setVisible(false); 
                f.dispose();
            }  
        }); 
   // b2.getForeground(Color.black);
    //Add button handler to log out.->return to login page, reinitiaize all variables
    f.add(b2);  
    f.setSize(400,400);  
    f.setLayout(null);  
    f.setBackground(Color.pink);
    Font l=new Font (Font.MONOSPACED,Font.PLAIN, 13);
    Color c=Color.decode("#761137");
    f.setForeground(c);
    f.setFont(l);
    f.setVisible(true);   
}
	public static void main(String args[]) {
		new reshome(1);
	}
}