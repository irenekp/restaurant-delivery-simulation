package selfstudy.payment;
import selfstudy.error.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
public class payment extends JFrame implements ActionListener{	
	private static Connection conn;	
	private static Statement st;	
	private static ResultSet re1, re, re2, re3;	
	JFrame frame;	
	JPanel panel, opanel, mpanel, ipanel, p, pan;	
	JRadioButton cash, card, newcard, existing_card;	
	JButton npay, epay, ok, addcard;	
	ButtonGroup bg, cg;	
	JLabel cardNo, ecardNo, cvv, valid_till, evalid_till,  owner_name, eowner_name, label, other, message, mes, mes_label;	
	JTextField cardNo_text, validTill_text, owner_text, other_text;	
	JPasswordField cvv_text;	
	JComboBox<String> ecardtype, cardtype;	
	String cardtypes[] = {"SBI", "HDFC", "ICICI", "AXIS", "CANARA"};	
	int cus_id;	
	public payment(int cid){	
		 	
		//Font	
    	Font I = new Font(Font.MONOSPACED, Font.PLAIN, 13);	
        Color c = Color.decode("#761137");	
		cus_id = cid;	
		//Heading	
		label = new JLabel("Payment");	
    	label.setForeground(Color.GRAY);	
    	label.setFont(new Font("Serif", Font.BOLD, 30));	
    	label.setBackground(Color.pink);	
			
		//cardNo	
		cardNo = new JLabel();	
		cardNo.setText("Card Number : ");	
		ecardNo = new JLabel();	
		cardNo_text = new JTextField();	
			
		//valid_till	
		valid_till = new JLabel();	
		evalid_till = new JLabel();	
		valid_till.setText("Valid Thru : ");	
		validTill_text = new JTextField();	
			
		//owner_name	
		owner_name = new JLabel();	
		eowner_name = new JLabel();	
		owner_name.setText("Card Owner Name : ");	
		owner_text = new JTextField();	
			
		//other	
		other = new JLabel();	
		other.setText("Card Type : ");	
		other_text = new JTextField();	
			
		//message	
		message = new JLabel();	
		mes = new JLabel();	
			
		//cvv	
		cvv = new JLabel();	
		cvv.setText("Cvv : ");	
		cvv_text = new JPasswordField();	
			
		//cash	
		cash = new JRadioButton("Cash");	
		cash.addActionListener(this);	
			
		//pay	
		npay = new JButton("Pay");	
		epay = new JButton("Pay");	
		ok = new JButton("Okay");	
		addcard = new JButton("Add Card");	
			
		//cards	
		card = new JRadioButton("Card");	
		newcard = new JRadioButton("New Card");	
		existing_card = new JRadioButton("Existing Card");	
			
		//Card Type List	
		cardtype = new JComboBox<String>(cardtypes);	
			
		//panels	
		opanel = new JPanel(new GridLayout(1,1));	
		opanel.add(cash);	
		opanel.add(card);	
		opanel.setBackground(Color.pink);	
		opanel.setForeground(c);	
		opanel.setFont(I);	
			
			
		//Radio Button Groups	
		bg = new ButtonGroup();	
		bg.add(cash);	
		bg.add(card);	
		cg = new ButtonGroup();	
		cg.add(newcard);	
		cg.add(existing_card);	
			
		frame = new JFrame("Payment");	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		frame.setSize(500, 250);	
        frame.setVisible(true);	
        frame.setBackground(Color.pink);	
		frame.setForeground(c);	
		frame.setFont(I);	
        frame.add(label, BorderLayout.PAGE_START);	
		frame.add(opanel, BorderLayout.CENTER);	
			
				
		//Action Listeners to the components	
		npay.addActionListener(new ActionListener() {	
			public void actionPerformed(ActionEvent ae) {	
				String cn = cardNo_text.getText();	
		        String cv = cvv_text.getText();	
		        String ct = cardtype.getSelectedItem().toString();	
		        String vt = validTill_text.getText();	
		        String oN = owner_text.getText();	
		        int pay_id = 1;	
		        	
		    	String url = "jdbc:mysql://localhost:3306/FOOD";	
				String driver = "com.mysql.cj.jdbc.Driver";	
				String userName = "root";	
				String password = "password";	
				try { 	
					Class.forName(driver).newInstance(); 	
					conn = DriverManager.getConnection(url,userName,password);	
					st = conn.createStatement();	
					re = st.executeQuery("select max(pid) from payment");	
					if(re.next()) {	
							pay_id = re.getInt("max(pid)");	
					}	
					PreparedStatement myPreStatement = conn.prepareStatement("INSERT into card(pid, card_no, cvv, card_type, valid_till, owner_name) values(?, ?, ?, ?, ?, ?)");	
					myPreStatement.setInt(1, pay_id);	
					myPreStatement.setString(2, cn);	
		            myPreStatement.setString(3, cv);	
		            myPreStatement.setString(4, ct);	
		            myPreStatement.setString(5, vt);	
		            myPreStatement.setString(6, oN);	
		            myPreStatement.execute();	
		            		
					conn.close();	
				}catch (Exception e) { 	
					e.printStackTrace(); 	
					new except();	
				}	
					
				mpanel = new JPanel(new GridLayout(1,0));	
				mpanel.add(message);	
				mpanel.setBackground(Color.pink);	
				mpanel.setForeground(c);	
				mpanel.setFont(I);	
				frame.remove(panel);	
				frame.repaint();	
				frame.add(mpanel, BorderLayout.CENTER);	
				message.setText("Payment Successfull...!!! Your order will be delivered to you soon...!!!");	
				frame.dispose();	
        	}	
				
		});	
			
		epay.addActionListener(new ActionListener() {	
			public void actionPerformed(ActionEvent ae) {	
		        String cv = cvv_text.getText();	
		        	
		    	String url = "jdbc:mysql://localhost:3306/FOOD";	
				String driver = "com.mysql.cj.jdbc.Driver";	
				String userName = "root";	
				String password = "password";	
				try { 	
					Class.forName(driver).newInstance(); 	
					conn = DriverManager.getConnection(url,userName,password);	
					st = conn.createStatement();	
					String ct = ecardtype.getSelectedItem().toString();	
                    re3 = st.executeQuery("select * from payment natural join card where card_type='"+ct+"' and cus_id="+cid+"");	
                    re3.next();	
                    if(cv.contentEquals(re3.getString("cvv"))) {	
                    	message.setText("Payment Successfull...!!! Your order will be delivered to you soon...!!!");	
                    	mpanel = new JPanel(new GridLayout(1,0));	
        				mpanel.add(message);	
        				mpanel.setBackground(Color.pink);	
        				mpanel.setForeground(c);	
        				mpanel.setFont(I);	
        				frame.remove(panel);	
        				frame.repaint();	
        				frame.add(mpanel, BorderLayout.CENTER);	
                    }	
                    else {	
                    	mes.setForeground(Color.red);	
                    	mes.setText("Wrong CVV !!!");	
                    }	
					conn.close();	
				}catch (Exception e) { 	
					e.printStackTrace(); 	
					new except();	
				}	
				frame.dispose();	
        	}	
		});	
			
		card.addActionListener(new ActionListener() {	
			public void actionPerformed(ActionEvent ae) {	
				String url = "jdbc:mysql://localhost:3306/FOOD";	
				String driver = "com.mysql.cj.jdbc.Driver";	
				String userName = "root";	
				String password = "password";	
				try { 	
					Class.forName(driver).newInstance(); 	
					conn = DriverManager.getConnection(url,userName,password);	
					st = conn.createStatement();	
					st.executeUpdate("insert into payment(cus_id) values(" + cid + ")");	
				}	
				catch(Exception e) {	
					e.printStackTrace();	
					new except();	
				}	
				ipanel = new JPanel(new GridLayout(1,1));	
				ipanel.add(newcard);	
				ipanel.add(existing_card);	
				ipanel.setBackground(Color.pink);	
				ipanel.setForeground(c);	
				ipanel.setFont(I);	
        		frame.remove(opanel);	
				frame.repaint();	
				frame.add(ipanel, BorderLayout.CENTER);	
        	}	
		});	
			
		newcard.addActionListener(new ActionListener() {	
			public void actionPerformed(ActionEvent ae) {	
        		panel = new JPanel(new GridLayout(6,1));	
        			
        		panel.add(other);	
        		panel.add(cardtype);	
        		panel.add(cardNo);	
        		panel.add(cardNo_text);	
        		panel.add(cvv);	
        		panel.add(cvv_text);	
        		panel.add(valid_till);	
        		panel.add(validTill_text);	
        		panel.add(owner_name);	
        		panel.add(owner_text);	
        		panel.add(mes);	
        		panel.add(npay);	
        		panel.setBackground(Color.pink);	
        		panel.setForeground(c);	
        		panel.setFont(I);	
        		frame.remove(ipanel);	
				frame.repaint();	
				frame.add(panel, BorderLayout.CENTER);	
        	}	
		});	
			
		addcard.addActionListener(new ActionListener() {	
			public void actionPerformed(ActionEvent ae) {	
        		panel = new JPanel(new GridLayout(6,1));	
        			
        		panel.add(other);	
        		panel.add(cardtype);	
        		panel.add(cardNo);	
        		panel.add(cardNo_text);	
        		panel.add(cvv);	
        		panel.add(cvv_text);	
        		panel.add(valid_till);	
        		panel.add(validTill_text);	
        		panel.add(owner_name);	
        		panel.add(owner_text);	
        		panel.add(mes);	
        		panel.add(npay);	
        		panel.setBackground(Color.pink);	
        		panel.setForeground(c);	
        		panel.setFont(I);	
        		frame.remove(pan);	
				frame.repaint();	
				frame.add(panel, BorderLayout.CENTER);	
        	}	
		});	
			
		existing_card.addActionListener(new ActionListener() {	
			public void actionPerformed(ActionEvent ae) {	
				frame.remove(ipanel);	
				frame.repaint();	
				String ecardtypes[];	
				String url = "jdbc:mysql://localhost:3306/FOOD";	
        		String driver = "com.mysql.cj.jdbc.Driver";	
        		String userName = "root";	
        		String password = "password";	
        		try { 	
        			Class.forName(driver).newInstance(); 	
        			conn = DriverManager.getConnection(url,userName,password);	
        			st = conn.createStatement();	
        				
        			re1 = st.executeQuery("select count(distinct card_type) as count from payment natural join card where cus_id="+cid+"");	
        			int n;	
        			if(re1.next()) {	
            			//	
        			}	
        			n = re1.getInt("count");	
        			if(n == 0) {	
        				mes_label = new JLabel();	
        				mes_label.setText(" No Existing Card present. So add a newcard !!!");	
                        pan = new JPanel(new GridLayout(2,1));	
                        pan.setBackground(Color.pink);	
                        pan.setForeground(c);	
                        pan.setFont(I);	
                        pan.add(mes_label);	
                        pan.add(addcard);	
                        frame.add(pan, BorderLayout.CENTER);	
        			}	
        			else {	
        				ecardtypes = new String[n];	
            			re = st.executeQuery("select distinct card_type from payment natural join card where cus_id="+cid+"");	
            			int i = 0;	
                        while(re.next() && i<n)	
                        	ecardtypes[i++] = re.getString("card_type");	
                        ecardtype = new JComboBox<String>(ecardtypes);	
                        p = new JPanel(new GridLayout(4,2));	
                        p.add(other);	
                        p.add(ecardtype);	
                        p.add(mes);	
                        p.add(ok);	
                        p.setBackground(Color.pink);	
                		p.setForeground(c);	
                		p.setFont(I);	
                        frame.add(p, BorderLayout.CENTER);	
        			}	
            			
        			conn.close();	
        		}catch (Exception e) { 	
        			e.printStackTrace(); 	
        			new except();	
        		}	
        		//frame.add(pay);	
        	}	
		});	
		ok.addActionListener(new ActionListener() {	
			public void actionPerformed(ActionEvent ae) {	
				frame.remove(p);	
				frame.repaint();	
				String url = "jdbc:mysql://localhost:3306/FOOD";	
        		String driver = "com.mysql.cj.jdbc.Driver";	
        		String userName = "root";	
        		String password = "password";	
        		try { 	
        			Class.forName(driver).newInstance(); 	
        			conn = DriverManager.getConnection(url,userName,password);	
        			st = conn.createStatement();	
        				
        			String ct = ecardtype.getSelectedItem().toString();	
                    re2 = st.executeQuery("select * from payment natural join card where card_type='"+ct+"' and cus_id="+cid+"");	
                    if(re2.next()) {	
                    	//	
                    }	
                    ecardNo.setText(re2.getString("card_no"));	
                    evalid_till.setText(re2.getString("valid_till"));	
                    eowner_name.setText(re2.getString("owner_name"));	
                    	
                    panel = new JPanel(new GridLayout(6,1));	
            			
            		panel.add(cardNo);	
            		panel.add(ecardNo);	
            		panel.add(cvv);	
            		panel.add(cvv_text);	
            		panel.add(valid_till);	
            		panel.add(evalid_till);	
            		panel.add(owner_name);	
            		panel.add(eowner_name);	
            		panel.add(mes);	
            		panel.add(epay);	
            		panel.setBackground(Color.pink);	
            		panel.setForeground(c);	
            		panel.setFont(I);	
    				frame.add(panel, BorderLayout.CENTER);	
        			conn.close();	
        		}catch (Exception e) { 	
        			e.printStackTrace();	
        			new except();	
        		}	
			}	
		});	
        		
	}	
        		
	public void actionPerformed(ActionEvent ae) {	
		String url = "jdbc:mysql://localhost:3306/FOOD";	
		String driver = "com.mysql.cj.jdbc.Driver";	
		String userName = "root";	
		String password = "password";	
		try { 	
			Class.forName(driver).newInstance(); 	
			conn = DriverManager.getConnection(url,userName,password);	
			st = conn.createStatement();	
					
			st.executeUpdate("insert into payment(cash, cus_id) values(1, "+cus_id+")");	
				
			mpanel = new JPanel(new GridLayout(1,0));	
			mpanel.add(message);	
			frame.remove(opanel);	
			frame.repaint();	
			frame.add(mpanel, BorderLayout.CENTER);	
			message.setText("Your order will be delivered to you soon.  Enjoy the delicious Food !!!");	
		}	
		catch(Exception e) {	
			e.printStackTrace();	
			new except();	
		}	
		frame.dispose();	
			
	}	
		
	/**public static void main(String[] args) {	
		//int c;	
        new payment(3);	
    }	**/
}