package selfstudy.restlist;
import selfstudy.error.*;
import java.sql.PreparedStatement;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import selfstudy.payment.*;
public class restaurant{
	private static Connection conn;
	private static Statement st;
	private static ResultSet re, re2;
	private static Dialog d;
	static String res=null;
	static int res_id;     
	static int cus_id;      //Please assign this from login page
	static String cus_add; //Please assign this from login page
	static int emp_id;  //Please assign this from employee assigned algorithm
	static float ttime=0;
	static float total=0;
	static int order_id;
public restaurant(int cid) {
	  try {
		  cus_id=cid;
		  Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/FOOD","root","password");  //Enter your own Password
			Statement stmt=con.createStatement();  
			//PAGE 1: RESTAURANT NAMES.
			ResultSet rs1=stmt.executeQuery("select cus_address from customer where cus_id="+cid);
			while(rs1.next())
			{
				cus_add=rs1.getString("cus_address");
			}
			ResultSet rs=stmt.executeQuery("select res_name from RESTAURANT"); 
	//CONVERTING ResultSet from query into string format to create individual restaurant nodes in page
			ArrayList<String> arr = new ArrayList<String>();
			while (rs.next()) {
				arr.add(rs.getString("res_name"));
			}
			    JFrame frame = new JFrame("Restaurants");
			    //Restaurant page has restaurant as root node and names as child nodes from JTREE.
			    DefaultMutableTreeNode root = new DefaultMutableTreeNode("Restaurants");
			    DefaultMutableTreeNode child[] = new DefaultMutableTreeNode[arr.size()];
			    for(int i=0;i<arr.size();i++) {
			    child[i] = new DefaultMutableTreeNode(arr.get(i));
			    System.out.println(arr.get(i));
			    root.add(child[i]);
			    }
			    JTree tree = new JTree(root);
		        //ACTION WHEN A RESTAURANT IS CLICKED ON:
			    JScrollPane ScrollPane = new JScrollPane(tree);
			    tree.addTreeSelectionListener(new TreeSelectionListener() {
			        public void valueChanged(TreeSelectionEvent e) {
			          DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
			          //obtain name of node in res1 and convert it into sql applicable format. ex: res1=jalsa, res='jalsa'
			          String res1=node.toString();
			          //System.out.println("You selected " + res1);
			          res="'"+res1+"'";
			          try{
			        	  //saving res_id of selected res
			        	  Statement stmt=con.createStatement();    
					      ResultSet rs2=stmt.executeQuery("Select res_id from restaurant where res_name="+res);
					      while (rs2.next()) {
							    res_id = Integer.parseInt(rs2.getString("res_id"));
					      }
					      System.out.println(res_id);
					      //obtaining menu of selected res in order of category
			        	  ResultSet rs=stmt.executeQuery("select * from menu where res_id='"+res_id+"' order by item_category");
					    
						ArrayList<String> items = new ArrayList<String>();
						ArrayList<String> prices = new ArrayList<String>();
						while (rs.next()) {
							//save item name and price for full menu
							items.add(rs.getString("item_name"));
							prices.add(rs.getString("item_price"));
						}
						stmt=con.createStatement();  
						//inorder to ensure each item appears under its own category, i have obtained the categories in order along with their counts
						rs=stmt.executeQuery("select item_category,count(*) from menu where res_id='"+res_id+"' group by item_category");
						ArrayList<String> categories = new ArrayList<String>();
						ArrayList<String> count = new ArrayList<String>();
						while (rs.next()) {
							categories.add(rs.getString("item_category"));
							count.add(rs.getString("count(*)"));
						}
						Map< String,Integer> orders= new HashMap< String,Integer>(); //map created to select all saved orders
						//Menu Window
				        Frame f1= new Frame(res+" | Menu");
				        //initializing required variables
				        final Label label = new Label();          
				        label.setAlignment(Label.CENTER);  
				        label.setSize(400,100);
				        f1.add(label);
				        int y=110;
				        Checkbox checkbox1=null;
				        Label cat=new Label(categories.get(0));
				        cat.setBounds(100,100,200,15);
				        f1.add(cat);
				        int catcnt=0;
				        int cnt=Integer.parseInt(count.get(0));
				        //dynamically adds check boxes for each menu item under it's own category
				        for(int i=0;i<items.size();i++)
				        {
				        	//used to check if the same category is going on or if items in the category are over, if so, moves to next category.
				        	if(cnt==0)
				        	{
				        		catcnt++;
				                cat=new Label(categories.get(catcnt));
				                cat.setBounds(100,y,200,15);
				                y+=20;
				                f1.add(cat);
				                cnt=Integer.parseInt(count.get(catcnt));
				        	}
				        	//adds menu item
				        	checkbox1= new Checkbox(items.get(i));  
				            checkbox1.setBounds(100,y, 200,50);
				            Label l1=new Label(prices.get(i));  
				            l1.setBounds(300,y, 200,50); 
				            y+=50;
				            f1.add(checkbox1);
				            f1.add(l1);
				            String s=items.get(i);
				            //what happens when a menu item is checked:
				            checkbox1.addItemListener(new ItemListener() {  
				                public void itemStateChanged(ItemEvent e) {               
				                   label.setText(s+"Checkbox: "   
				                   + (e.getStateChange()==1?"checked":"unchecked"));  
				                   if(e.getStateChange()==1)
				                   {
				                	   //if checked, dialog comes up and asks for quantity
				                	   d = new Dialog(f1 , "Dialog Example", true);  
				                	   d.setBackground(Color.pink);
				       			    Font l=new Font (Font.MONOSPACED,Font.PLAIN, 13);
				       			    Color c=Color.decode("#761137");
				       			    d.setForeground(c);
				       			    d.setFont(l);
				                       d.setLayout( new FlowLayout() );  
				                       d.add( new Label ("Enter Number of units: "));  
				                       TextField tf1=new TextField();  
				                       tf1.setBounds(50,50,150,20);
				                       d.add(tf1);
				                       Button b = new Button ("OK");
				                       //when ok pressed in dialog. it saves both item name and qty in a map
				                       b.addActionListener ( new ActionListener()  
				                       {  
				                           public void actionPerformed( ActionEvent e )  
				                           {  
				                        	   int s2=Integer.parseInt(tf1.getText());
				                        	   orders.put(s, s2);
				                        	   Set< Map.Entry< String,Integer> > st = orders.entrySet();    
				                               for (Map.Entry< String,Integer> me:st) 
				                               { 
				                                   System.out.print(me.getKey()+":"); 
				                                   System.out.println(me.getValue()); 
				                               }
				                               System.out.println();
				                               d.setVisible(false);  
				                           }  
				                       });  
				                       d.add(b); 
				                       d.setSize(300,300);    
				                       d.setVisible(true);
				                       
				                   }
				                   else
				                   {
				                	   //if unchecked, removes item name and qty from map
				                	   orders.remove(s);
				                	   Set< Map.Entry< String,Integer> > st = orders.entrySet();    
				                       for (Map.Entry< String,Integer> me:st) 
				                       { 
				                           System.out.print(me.getKey()+":"); 
				                           System.out.println(me.getValue()); 
				                       }
				                       System.out.println();
				                   }
				                }  
				             });
				            cnt--;
				        }    
				        Label don=new Label("Place Order");
				        don.setBounds(100,y,100,50);
				        f1.add(don);
				        Button done=new Button("OK");
				        done.setBounds(100,y+50, 50,50);
				        //on pressing ok, order is confirmed
				        done.addActionListener ( new ActionListener()  
				        {  
				            public void actionPerformed( ActionEvent e )  
				            {  
				            	
				               f1.setVisible(false);
				               f1.dispose();
				               	   try { 
				               		   //new order page shows order details
				               		Statement stmt=con.createStatement();  
				                      Frame ord= new Frame("Orders Placed");
				                      Set< Map.Entry< String,Integer> > st = orders.entrySet();    
				                      //initializes variables and page layout
				                      Label item=null;
				                      Label time=null;
				                      Label price=null;
				                      Label units=null;
				                      Label title=new Label("Your Order:");
				                      title.setBounds(100, 50,100,30);
				                      ord.add(title);
				                      item=new Label("Item |");
				                      item.setBounds(100, 75, 200,30);
				                      ord.add(item);
				                      units=new Label("Units |");
				                      units.setBounds(300, 75, 100,30);
				                      ord.add(units);
				                      price=new Label("Price |");
				                      price.setBounds(400, 75, 100,30);
				                      ord.add(price);
				                      time=new Label("Time |");
				                      time.setBounds(500, 75, 100,30);
				                      ord.add(time);
				                      int y=100;
				                      //for each item, adds new row of labels with name, time,units,total price of that item
				                      for (Map.Entry< String,Integer> me:st)
				                      {
				                   	   String s=me.getKey();
				                   	   item = new Label(me.getKey());
				                   	   item.setBounds(100, y, 200,50);
				                   	   units=new Label(Integer.toString(me.getValue()));
				                   	   units.setBounds(300,y,100,50);
				                   	   ResultSet rs=stmt.executeQuery("select item_price, item_time from menu where item_name='"+s+"' AND res_id in(select res_id from restaurant where res_name="+res+")");
				                   	   while (rs.next()) {
				               				total+=Integer.parseInt(rs.getString("item_price"))*me.getValue();//increases total
				               				price=new Label(Integer.toString(Integer.parseInt(rs.getString("item_price"))*me.getValue())); //multiplies price by qty
				               				price.setBounds(400,y,100,50);
				               				time=new Label(rs.getString("item_time")+" mins");
				               				ttime+=Integer.parseInt(rs.getString("item_time")); //increases time
				               				time.setBounds(500,y,100,50);
				               			}
				                   	   ord.add(item);
				                   	   ord.add(units);
				                   	   ord.add(price);
				                   	   ord.add(time);
				                   	   
				                   	   y+=50;
				                      }
				                      Label total_time = new Label("Estimated Time : " + ttime);
				                      total_time.setBounds(100,y,200,30);
				                     ord.add(total_time);
				                     
				                      Label tot1= new Label("Total: "+total);
				                      //delivery charge
				                      tot1.setBounds(100,y+50,200,30);
				                      ord.add(tot1);
				                      Label tot2= new Label("Delivery Charges:"+30);
				                      //tax
				                      tot2.setBounds(100,y+100,200,30);
				                      ord.add(tot2);
				                      total+=30;
				                      Label tot3= new Label("Service Tax[10%]:"+0.1*total);
				                      //total
				                      tot3.setBounds(100,y+150,200,30);
				                      ord.add(tot3);
				                      total*=1.10;
				                      Label tot4= new Label("Total Payable:"+total);
				                      //HERE CODE FOR PAYMENT TYPES NEEDS TO COME???
				                      //place order: make change in data base
				                      tot4.setBounds(100,y+200,200,30);
				                      ord.add(tot4);
				                      Label tot5= new Label("Billing Address:");
				                      //HERE CODE FOR PAYMENT TYPES NEEDS TO COME???
				                      //place order: make change in data base
				                      tot5.setBounds(100,y+230,200,30);
				                      ord.add(tot5);
				                      TextField tb=new TextField(cus_add);
				                      tb.setBounds(100,y+260,200,50);
				                      ord.add(tb);
				                      Button but = new Button ("PLACE");
				                      Button but2 =new Button("PAYMENT");
				                      Button but3= new Button("CANCEL");
				                      but3.setBounds(300,y+300,200,50);
				                      but3.addActionListener(new ActionListener() {
				                    	  public void actionPerformed(ActionEvent e) {
				                    		  ord.dispose();
				                    	  }
				                      });
				                      ord.add(but3);
				                      but2.setBounds(100,y+300,200,50);
				                      but2.addActionListener(new ActionListener() {
				                    	  public void actionPerformed(ActionEvent e) {
				                    		  new payment(cid);
				                    	  }
				                      });
				                      ord.add(but2);
				                      ord.setBackground(Color.pink);
				      			    Font l=new Font (Font.MONOSPACED,Font.PLAIN, 13);
				      			    Color c=Color.decode("#761137");
				      			    ord.setForeground(c);
				      			    ord.setFont(l);
				                      but.addActionListener ( new ActionListener()  
				                      {  
				                          public void actionPerformed( ActionEvent e )  
				                          {  
				                        	 cus_add=tb.getText(); 
				                        	 System.out.println("address :" + cus_add);
				                        	 //TO ensure unique order id, it tkaes count of order table, increments by one and assigns that as order id.
				                        	 
				                        	  try(Statement stmt = con.createStatement();){
				                        		  	System.out.println("try");
						                        	 //String sql="insert into orders(order_status,order_time,order_address,estimated_time,total_price,res_id,cus_id) values("+ p + ","+ttime+","+cus_add+","+(ttime+30)+","+total+","+res_id+","+cid+","+")";
				                        		      //stmt.executeUpdate("insert into orders(order_status,order_time,order_address,estimated_time,total_price,res_id,cus_id) values("+ p + ","+ttime+","+cus_add+","+(ttime+30)+","+total+","+res_id+","+cid+","+")");
				                        		    
				                        		  	PreparedStatement myPreStatement = con.prepareStatement("insert into orders(order_status,order_time,order_address,estimated_time,total_price,res_id,cus_id) values(?, ?, ?, ?, ?, ?, ?)");
				                        			
				                        			myPreStatement.setString(1, "placed");
				                        			myPreStatement.setFloat(2, ttime);
				                                    myPreStatement.setString(3, cus_add);
				                                    myPreStatement.setFloat(4, (ttime));
				                                    myPreStatement.setFloat(5, total);
				                                    myPreStatement.setInt(6, res_id);
				                                    myPreStatement.setInt(7, cid);
				                                    myPreStatement.execute();
				                        		  	System.out.println("insert");
				                        		  re = stmt.executeQuery("select * from EMPLOYEE");
				                        		  System.out.println("employee");
				                      			int f = 0;
				                      			String d = "accepted";
				                      			while(re.next()) {
				                                  	String e1 = re.getString("emp_status");
				                                  	int eid = re.getInt("Emp_Id");
				                                  	if(e1.equals("available")) {
				                                  		System.out.println("equal");
				                                  		emp_id = eid;
				                                  		stmt.executeUpdate("update ORDERs set emp_id="+ eid+ " where cus_id="+ cid);
				                                  		System.out.println("orders update");
				                                  		stmt.executeUpdate("update Employee set emp_status= '"+ d + "' where emp_id="+eid);
				                                  		System.out.println("employee employee");
				                                  		f = 1;
				                                  		break;
				                                  	}
				                                  }
				                      			if(f==0) {
				                      				
				                      				re = stmt.executeQuery("select emp_id from orders where emp_id in (select emp_id from orders group by emp_id having count(order_id) < 2) and estimated_time = (select min(estimated_time) from orders)");
				                      				re.next();
				                      				stmt.executeUpdate("update ORDERs set emp_id="+ re.getInt("emp_id")+ " where cus_id="+ cid);
				                      				stmt.executeUpdate("update Employee set emp_status='"+d + "' where emp_id="+emp_id);
				                      			}
				                        		      //inserts into order table: note: lot of variables here have been self initialized by me but it should be taken from other tables and modules
				             
				                        		      
				                        		      int item_id;
				                        	  }catch (SQLException e8) {
			                        		      e8.printStackTrace();
			                        		      new except();
			                        		    }
				                        	  try (Statement stmt = con.createStatement();){
				                        		  
				                        		  String item_id=null;    
				                        		  for (Map.Entry< String,Integer> me:st)
								                      {
								                   	   String s=me.getKey();
								                   	   String sql="select item_id from menu where item_name='"+me.getKey()+"' and res_id="+res_id;
								                   	   ResultSet rs4=stmt.executeQuery(sql);
								                   	   
								                   	   while (rs4.next()) {
				              						    item_id=rs4.getString("item_id");
				              						  
								                      }
								                   	sql="select max(order_id) from orders";
								                   	   rs4=stmt.executeQuery(sql);
								                   	   
								                   	   while (rs4.next()) {
				              						    order_id=Integer.parseInt(rs4.getString("max(order_id)"));
				              		
								                      }
								                   	sql="insert into order_item values("+order_id+",'"+item_id+"',"+me.getValue()+")";
								                   	stmt.executeUpdate(sql);
								                   	
								                      }
				                        		      System.out.println("Database updated successfully ");
				                        		    } catch (SQLException e8) {
				                        		      e8.printStackTrace();
				                        		    }
				                             
				                              ord.setVisible(false); 
				                              ord.dispose();
				                          }  
				                      }); 
				                      but.setBounds(100,y+350,50,50);
				                      ord.add(but);
				                      ord.setSize(700,500);  
				                      ord.setLayout(null);  
				                      ord.setVisible(true);   
				               	   }catch(Exception e1){ 
				                      	e1.printStackTrace();
				                      	System.out.println(e1);
				                      	 new except();
				               	   }
				            }  
				        });
				        f1.add(done);
				        f1.setSize(700,500);  
				        f1.setLayout(null);
				        f1.setBackground(Color.pink);
					    Font l=new Font (Font.MONOSPACED,Font.PLAIN, 13);
					    Color c=Color.decode("#761137");
					    f1.setForeground(c);
					    f1.setFont(l);
				        f1.setVisible(true);
			          }catch(Exception e1){ 
			           	e1.printStackTrace();
			         	System.out.println(e1);}
			        }
			      });
			    frame.getContentPane().add(ScrollPane, BorderLayout.CENTER);
			    frame.setSize(700, 400);
			    frame.setBackground(Color.pink);
			    Font l=new Font (Font.MONOSPACED,Font.PLAIN, 13);
			    Color c=Color.decode("#761137");
			    frame.setForeground(c);
			    frame.setFont(l);
			    frame.setVisible(true);
	  }catch(Exception e){ 
      	e.printStackTrace();
      	System.out.println(e);
      	 new except();
	 }
}
public static void main(String args[]) {
	  new restaurant(5);
}
}