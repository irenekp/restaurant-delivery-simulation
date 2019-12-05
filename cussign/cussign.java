package selfstudy.cussign;
import selfstudy.error.*;
import selfstudy.restlist.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;


public class cussign extends JFrame implements ActionListener {
	private static Connection conn;
	private static Statement st;
	private static ResultSet re;
    JPanel SignUpPanel, p, LoginPanel;
    JLabel label, user_label, password_label, smessage, contact_label, email_label, address_label, message, semail_label, spassword_label;
    JTextField userName_text, userEmail_text, userContact_text, userAddress_text, suserEmail_text;
    JPasswordField password_text, spassword_text;
    JButton signup, login;
    JFrame frame;
    JTabbedPane tabPane;
    int cid;
    public cussign() {
    	
    	//Font
    	Font I = new Font(Font.MONOSPACED, Font.PLAIN, 13);
        Color c = Color.decode("#761137");
    	
    	//Heading
    	label = new JLabel("User SignUp / Login");
    	label.setForeground(Color.GRAY);
    	label.setFont(new Font("Serif", Font.BOLD, 30));
    	label.setBackground(Color.pink);
        
        // User Label
        user_label = new JLabel();
        user_label.setText(" User Name :");
        userName_text = new JTextField();
        
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
        
        //Email
        email_label = new JLabel();
        email_label.setText(" Email ID : ");
        userEmail_text = new JTextField();
        semail_label = new JLabel();
        semail_label.setText(" Email ID : ");
        suserEmail_text = new JTextField();
        
        //Address
        address_label = new JLabel();
        address_label.setText(" Address : ");
        userAddress_text = new JTextField();
        
        message = new JLabel();
        smessage = new JLabel();

        //Signup Button
        signup = new JButton("SIGN-UP");
        
        //Login Button
        login = new JButton("Login");
        
        //SignUp 
        SignUpPanel = new JPanel(new GridLayout(6, 1));
        
        SignUpPanel.add(user_label);
        SignUpPanel.add(userName_text);
        SignUpPanel.add(contact_label);
        SignUpPanel.add(userContact_text);
        SignUpPanel.add(semail_label);
        SignUpPanel.add(suserEmail_text);
        SignUpPanel.add(spassword_label);
        SignUpPanel.add(spassword_text);
        SignUpPanel.add(address_label);
        SignUpPanel.add(userAddress_text);
        SignUpPanel.add(smessage);
        SignUpPanel.add(signup);
        SignUpPanel.setBackground(Color.pink);
        SignUpPanel.setForeground(c);
        SignUpPanel.setFont(I);
        
        //Login
        LoginPanel = new JPanel(new GridLayout(3,1));
        
        LoginPanel.add(email_label);
        LoginPanel.add(userEmail_text);
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
        login.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent ae) {
                String email = userEmail_text.getText();
                String pass = password_text.getText();
                
            	String url = "jdbc:mysql://localhost:3306/food";
        		String driver = "com.mysql.cj.jdbc.Driver";
        		String userName = "root";
        		String password = "password";
        		try { 
        			Class.forName(driver).newInstance(); 
        			conn = DriverManager.getConnection(url,userName,password);
        			st = conn.createStatement();
        			
        			re = st.executeQuery("select * from CUSTOMER");
        			int f = 0;
                    while(re.next()) {
                    	String e = re.getString("cus_email");
                    	String p = re.getString("cus_password");
                    	cid = re.getInt("cus_id");	
                	//System.out.println(cid);	
                	if(e.compareTo(email) == 0){	
                    		if(p.contentEquals(pass)) {
                    			cid = re.getInt("cus_id");
                    			message.setText("Login Sucessfull !!! ");
                    			message.setForeground(Color.GREEN);
                    			new restaurant(cid);
                    			//
                    			frame.dispose();
                    			//new Payment(cid);
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
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        String Name = userName_text.getText();
        String contact =userContact_text.getText();
        String email = suserEmail_text.getText();
        String pass = spassword_text.getText();
        String address = userAddress_text.getText();
        
        
    	String url = "jdbc:mysql://localhost:3306/food";
		String driver = "com.mysql.cj.jdbc.Driver";
		String userName = "root";
		String password = "password";
		try { 
			//Class.forName(driver).newInstance(); 
			Class.forName("com.mysql.jdbc.Driver");  
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/FOOD","root","password");
			Statement stmt= conn.createStatement();
			PreparedStatement myPreStatement = conn.prepareStatement("INSERT into CUSTOMER(cus_name, cus_contact, cus_email, cus_password, cus_address) values(?, ?, ?, ?, ?)");
			myPreStatement.setString(1, Name);
			myPreStatement.setString(2, contact);
            myPreStatement.setString(3, email);
            myPreStatement.setString(4, pass);
            myPreStatement.setString(5, address);
            myPreStatement.execute();
			conn.close();
			frame.dispose();
		}catch (Exception e) { 
			e.printStackTrace(); 
			new except();
		}
        frame.dispose();
    }

}
