package selfstudy.empsign;
import selfstudy.error.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
class emp extends JFrame implements ActionListener {
    
    private static Connection conn;
	private static Statement st;
	private static ResultSet re1,re2,re,re3,re4;
    JPanel  first_panel,second_panel,final_panel;
    JLabel  head_label,empid_label,ordid_label,cname_label,cid_label,time_label,cadd_label,price_label,pay_label,
            empid_lab,ordid_lab,cname_lab,cid_lab,time_lab,cadd_lab,price_lab,pay_lab,waste_label,waste_lab,cno_label,cno_lab,message;
    JButton delivered, logout;
    JFrame frame;
    
    public emp(int eid){
        
        //Heading
    	head_label = new JLabel("Pending Order");
    	head_label.setForeground(Color.GRAY);
    	head_label.setFont(new Font("Serif", Font.BOLD, 30));
        
        // employee Label
        empid_label = new JLabel();
        empid_label.setText(" Employee ID :");
        empid_label.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
        empid_lab = new JLabel();
        
        // order id
        ordid_label = new JLabel();
        ordid_label.setText(" Order ID :");
        ordid_label.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
        ordid_lab = new JLabel();
        
        //cus name
        cname_label = new JLabel();
        cname_label.setText(" Customer Name :");
        cname_label.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
        cname_lab = new JLabel();
        
       
        //cus id
        cid_label = new JLabel();
        cid_label.setText(" Customer ID :");
        cid_label.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
        cid_lab = new JLabel();
        
        // estimated time
        time_label = new JLabel();
        time_label.setText(" Estimated Time :");
        time_label.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
        time_lab = new JLabel();
        
        //Address
        cadd_label = new JLabel();
        cadd_label.setText(" Customer Address :");
        time_label.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
        waste_label = new JLabel();
        cadd_lab = new JLabel();
        cadd_label.setSize(50,30);
        waste_lab = new JLabel();
        
         // contact no
        cno_label = new JLabel();
        cno_label.setText(" Contact Number :");
        cno_label.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
        cno_lab = new JLabel();
        
         // price
        price_label = new JLabel();
        price_label.setText(" Total Price :");
        price_label.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
        price_lab = new JLabel();
        
        // waste
        waste_label = new JLabel();
        waste_label.setText(" ");
        waste_lab = new JLabel();
        
         // payment mode
        pay_label = new JLabel();
        pay_label.setText(" Payment Mode:");
        pay_label.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
        pay_lab = new JLabel();

          message = new JLabel();
        //delivered Button
        delivered = new JButton("Delivered");
        
        //Logout Button
        logout = new JButton("Log Out");
        
        //First Panel
        first_panel = new JPanel(new GridLayout(4,4));
        
        first_panel.add(ordid_label); 
        first_panel.add(ordid_lab);
        
        first_panel.add(empid_label); 
        first_panel.add(empid_lab);
        
        first_panel.add(cid_label);   
        first_panel.add(cid_lab);
        
        first_panel.add(time_label);  
        first_panel.add(time_lab);
        
        first_panel.add(cname_label); 
        first_panel.add(cname_lab);
        
        first_panel.add(cno_label);   
        first_panel.add(cno_lab);
        
        //first_panel.add(price_label); 
        //first_panel.add(price_lab);
        
        first_panel.add(cadd_label);
        first_panel.add(cadd_lab);
        //first_panel.add(waste_label);
        
        first_panel.add(pay_label);   
        first_panel.add(pay_lab);
        
      
        //Second Panel
        second_panel = new JPanel(new GridLayout(3,2));
    
        second_panel.add(waste_label);
        //second_panel.add(cadd_lab); 
        second_panel.add(waste_lab);
        second_panel.add(price_label); 
        second_panel.add(price_lab);
        second_panel.add(delivered);
        second_panel.add(logout);
        

        
        frame = new JFrame("Employee");
		Container pane = frame.getContentPane();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(head_label, BorderLayout.PAGE_START);
        
        first_panel.setBackground(Color.pink);
        Font l=new Font (Font.MONOSPACED,Font.PLAIN, 13);
	Color c=Color.decode("#761137");
	first_panel.setForeground(c);
	first_panel.setFont(l);
        
        second_panel.setBackground(Color.pink);
	second_panel.setForeground(c);
	second_panel.setFont(l);
        
        
        empid_lab.setBackground(Color.pink);
	empid_lab.setForeground(c);
	empid_lab.setFont(l);
        
        cid_lab.setBackground(Color.pink);
	cid_lab.setForeground(c);
	cid_lab.setFont(l);
        
        ordid_lab.setBackground(Color.pink);
	ordid_lab.setForeground(c);
	ordid_lab.setFont(l);
        
        cname_lab.setBackground(Color.pink);
	cname_lab.setForeground(c);
	cname_lab.setFont(l);
        
        cadd_lab.setBackground(Color.pink);
	cadd_lab.setForeground(c);
	cadd_lab.setFont(l);
        
        time_lab.setBackground(Color.pink);
	time_lab.setForeground(c);
	time_lab.setFont(l);
        
        price_lab.setBackground(Color.pink);
	price_lab.setForeground(c);
	price_lab.setFont(l);
        
        pay_lab.setBackground(Color.pink);
	pay_lab.setForeground(c);
	pay_lab.setFont(l);
        
        cno_lab.setBackground(Color.pink);
	cno_lab.setForeground(c);
	cno_lab.setFont(l);
        
        //delivered.setBackground(Color.pink);
	delivered.setForeground(c);
	delivered.setFont(l);
        
        //logout.setBackground(Color.pink);
	logout.setForeground(c);
	logout.setFont(l);
        
        message.setBackground(Color.pink);
	message.setForeground(c);
	message.setFont(l);
        //frame.setSize(500, 250);
        //frame.setVisible(true);
        
        String url = "jdbc:mysql://localhost:3306/food";
        		String driver = "com.mysql.cj.jdbc.Driver";
        		String userName = "root";
        		String password = "password";
                        //int eid = 101;
                     try{
                         
                         Class.forName(driver).newInstance(); 
					conn = DriverManager.getConnection(url,userName,password);
					st = conn.createStatement();
                                        
                                        re1 = st.executeQuery("select * from orders where order_status <> 'delivered' and emp_id = "+eid);
                                      if(re1.next()){
                                        ordid_lab.setText(re1.getString("order_id"));
                                        empid_lab.setText(re1.getString("emp_id"));
                                        cid_lab.setText(re1.getString("cus_id"));
                                        time_lab.setText(re1.getString("estimated_time"));
                                        price_lab.setText(re1.getString("total_price"));
                                        
                                          //cid = re1.getInt("cus_id");
                                      }
                                        re2 = st.executeQuery("select * from customer where cus_id = "+(re1.getString("cus_id")));
                                      if(re2.next()){
                                        cname_lab.setText(re2.getString("cus_name"));
                                        cadd_lab.setText(re2.getString("cus_address"));
                                        cno_lab.setText(re2.getString("cus_contact"));
                                      }
                                        re3 = st.executeQuery("select *from orders e natural join (select * from customer c natural join payment p) AS A where order_status <> 'delivered' and emp_id=" +eid);
                                        if(re3.next()){
                                            int pay = re3.getInt("cash");
                                              if(pay==0)
                                                 pay_lab.setText("Card");
                                              else
                                                  pay_lab.setText("Cash");
                                        }
                         
                     }catch (Exception e) { 
                             
        			e.printStackTrace(); 
        			//new except();
        		}
        logout.addActionListener(this);
        delivered.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
                            
                String url = "jdbc:mysql://localhost:3306/food";
				String driver = "com.mysql.cj.jdbc.Driver";
				String userName = "root";
				String password = "password";
                            
                            try { 
					Class.forName(driver).newInstance(); 
					conn = DriverManager.getConnection(url,userName,password);
					st = conn.createStatement();
                                        
                                        re = st.executeQuery("select * from orders where order_status <> 'delivered' and emp_id = "+eid);
                                        
                                        //int oid = re.getInt("order_id");
                                        
                                        
                                       if(re.next()){
                                           int oid = re.getInt("order_id");
					 st.executeUpdate("update orders set order_status = 'delivered' where order_id="+oid);
                                         //st.executeUpdate("update emp set status='available' where emp_id="+eid);
                                         //st.executeUpdate("update emp set order_id ="+oid+" where emp_id="+eid);
                                         
                                       }
                            }catch (Exception e) { 
					e.printStackTrace(); 
					new except();
				}
                            }
                    });
                                        
        
        frame.add(first_panel, BorderLayout.PAGE_START);
        frame.add(second_panel, BorderLayout.CENTER);
        frame.setSize(500, 250);
        frame.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
		final_panel = new JPanel(new GridLayout(1,0));
		final_panel.add(message);
		frame.remove(first_panel);
                frame.remove(second_panel);
		frame.repaint();
		frame.add(final_panel, BorderLayout.CENTER);
		message.setText("Logout Successfully");
                
                Font l=new Font (Font.MONOSPACED,Font.PLAIN, 13);
	Color c=Color.decode("#761137");
                final_panel.setBackground(Color.pink);
	final_panel.setForeground(c);
	final_panel.setFont(l);
	}
}

public class empsign extends JFrame implements ActionListener{
	private static Connection conn;
	private static Statement st;
	private static ResultSet re;
    JPanel SignUpPanel, p, LoginPanel;
    JLabel label, user_label, password_label, smessage, contact_label, dob_label, message, sname_label, spassword_label;
    JTextField userName_text, userContact_text, userDob_text, suserName_text;
    JPasswordField password_text, spassword_text;
    JButton signup, login;
    JFrame frame;
    JTabbedPane tabPane;

    public empsign(){
    	
    	//Font
    	Font I = new Font(Font.MONOSPACED, Font.PLAIN, 13);
        Color c = Color.decode("#761137");
        
    	//Heading
    	label = new JLabel("Employee SignUp / Login");
    	label.setForeground(Color.GRAY);
    	label.setFont(new Font("Serif", Font.BOLD, 30));
    	label.setBackground(Color.pink);
        
        // User Label
        user_label = new JLabel();
        user_label.setText(" User Name :");
        userName_text = new JTextField();
        sname_label = new JLabel();
        sname_label.setText(" User Name : ");
        suserName_text = new JTextField();
        
        // Password
        password_label = new JLabel();
        password_label.setText(" Password :");
        password_text = new JPasswordField();
        spassword_label = new JLabel();
        spassword_label.setText(" Password :");
        spassword_text = new JPasswordField();
        
        //Contact
        contact_label = new JLabel();
        contact_label.setText(" Mobile Number : ");
        userContact_text = new JTextField();
        
 
        //DOB
        dob_label = new JLabel();
        dob_label.setText(" Data Of Birth : ");
        userDob_text = new JTextField();
        
        message = new JLabel();
        smessage = new JLabel();

        //Signup Button
        signup = new JButton("SIGN-UP");
        
        //Login Button
        login = new JButton("Login");
        
        //SignUp 
        SignUpPanel = new JPanel(new GridLayout(5, 1));
        
        SignUpPanel.add(sname_label);
        SignUpPanel.add(suserName_text);
        SignUpPanel.add(contact_label);
        SignUpPanel.add(userContact_text);
        SignUpPanel.add(dob_label);
        SignUpPanel.add(userDob_text);
        SignUpPanel.add(spassword_label);
        SignUpPanel.add(spassword_text);
        SignUpPanel.add(smessage);
        SignUpPanel.add(signup);
        SignUpPanel.setBackground(Color.pink);
        SignUpPanel.setForeground(c);
        SignUpPanel.setFont(I);
        
        //Login
        LoginPanel = new JPanel(new GridLayout(3,1));
        
        LoginPanel.add(user_label);
        LoginPanel.add(userName_text);
        LoginPanel.add(password_label);
        LoginPanel.add(password_text);
        LoginPanel.add(message);
        LoginPanel.add(login);
        LoginPanel.setBackground(Color.pink);
        LoginPanel.setForeground(c);
        LoginPanel.setFont(I);
        
      //Tabs
    	tabPane = new JTabbedPane();
    	tabPane.addTab("SignUp", SignUpPanel);
    	tabPane.addTab("Login", LoginPanel);
        
        frame = new JFrame("SignUp/Login");
		Container pane = frame.getContentPane();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Adding the listeners to components..
        signup.addActionListener(this);
        
        //For Login
        login.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent ae) {
        		int eid;
                String name = userName_text.getText();
                String pass = password_text.getText();
                
            	String url = "jdbc:mysql://localhost:3306/food";
        		String driver = "com.mysql.cj.jdbc.Driver";
        		String userName = "root";
        		String password = "password";
        		try { 
        			Class.forName(driver).newInstance(); 
        			conn = DriverManager.getConnection(url,userName,password);
        			st = conn.createStatement();
        			
        			re = st.executeQuery("select * from EMPLOYEE");
        			int f = 0;
                    while(re.next()) {
                    	String n = re.getString("emp_name");
                    	String p = re.getString("emp_password");
                    	if(n.equals(name)){
                    		if(p.contentEquals(pass)) {
                    			eid = re.getInt("emp_id");
                    			message.setText("Login Sucessfull !!! ");
                    			message.setForeground(Color.GREEN);
                    			//new Payment(cid);
                    			new emp(eid);
                    		}
                    		else {
                    			message.setText("Wrong Password !!! ");
                    			message.setForeground(Color.RED);
                    		}
                    		f = 1;
                    		break;
                    	}
                    }
                    if(f == 0) {
                    	message.setText("Wrong Email ID !!! ");
                    	message.setForeground(Color.RED);
                    }
                    	
        			conn.close();
        		}catch (Exception e) { 
        			e.printStackTrace(); 
        			new except();
        		}
                //frame.dispose();
        	}
        });
        //add(p, BorderLayout.CENTER);
        frame.add(label, BorderLayout.PAGE_START);
        frame.add(tabPane, BorderLayout.CENTER);
        frame.setSize(500, 250);
        frame.setVisible(true);
        
    }
    
    //For Sign-Up
    @Override
    public void actionPerformed(ActionEvent ae) {
        String Name = suserName_text.getText();
        String contact =userContact_text.getText();
        String dob = userDob_text.getText();
        String pass = spassword_text.getText();
        String reg = "KA01S47";
        String num[] = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15"};
        
    	String url = "jdbc:mysql://localhost:3306/food";
		String driver = "com.mysql.cj.jdbc.Driver";
		String userName = "root";
		String password = "password";
		try { 
			Class.forName(driver).newInstance(); 
			conn = DriverManager.getConnection(url,userName,password);
			st = conn.createStatement();
			PreparedStatement myPreStatement = conn.prepareStatement("INSERT into EMPLOYEE(emp_name, emp_contact, dob, emp_password,veh_reg_no, salary) values(?, ?, ?, ?, ?, ?)");
			myPreStatement.setString(1, Name);
			myPreStatement.setString(2, contact);
            myPreStatement.setString(3, dob);
            myPreStatement.setString(4, pass);
            myPreStatement.setString(5, (reg+num[0]));
            myPreStatement.setInt(6, 0);
            myPreStatement.execute();
			conn.close();
		}catch (Exception e) { 
			e.printStackTrace(); 
			new except();
		}
		for(int i=1; i < num.length; i++)
			num[i-1] = num[i];
        frame.dispose();
    }

}


