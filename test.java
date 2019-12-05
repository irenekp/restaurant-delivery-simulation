package selfstudy;
import selfstudy.cussign.*;
import selfstudy.empsign.*;
import selfstudy.ressign.*;
import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.awt.event.*;
public class test extends JFrame implements ActionListener{
	JButton user, emp, res;
	JPanel panel;
	JFrame frame;
	JLabel label, l1, l2, l3, l4, l5, l6, l7, l8, l9;
	test(){
		//Font
    	Font I = new Font(Font.MONOSPACED, Font.PLAIN, 20);
        Color c = Color.decode("#761137");
         //Labels
        label = new JLabel("            Welcome to Food Del");
        label.setForeground(Color.GRAY);
    	label.setFont(new Font("Serif", Font.BOLD, 30));
        label.setBackground(Color.pink);
        
        l1 = new JLabel();
        l2 = new JLabel();
        l3 = new JLabel();
        l4 = new JLabel();
        l5 = new JLabel();
        l6 = new JLabel();
        l7 = new JLabel();
        l8 = new JLabel();
        l9 = new JLabel();
    	
        //Buttons
        user = new JButton("User Login");
        user.setPreferredSize(new Dimension(20, 20));
        emp = new JButton("Employee Login");
        emp.setSize(10, 10);
        res = new JButton("Restaurant Login");
        res.setPreferredSize(new Dimension(20, 20));
        
        panel = new JPanel(new GridLayout(4, 3));
        //panel.add(l4);
        //panel.add(l5);
        panel.add(l7);
        panel.add(l8);
        panel.add(l9);
        panel.add(l1);
        panel.add(user);
        panel.add(l2);
        panel.add(l3);
        panel.add(emp);
        panel.add(l4);
        panel.add(l5);
        panel.add(res);
        panel.add(l6);
        
        panel.setBackground(Color.pink);
        panel.setForeground(c);
        panel.setFont(I);
        
        frame = new JFrame("Home");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(label, BorderLayout.PAGE_START);
        frame.add(panel, BorderLayout.CENTER);
        frame.setSize(500, 250);
        frame.setVisible(true);
        
        user.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent ae) {
        		new cussign();
        	}
        });	
        
        emp.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent ae) {
        		new empsign();
        	}
        });	
        
        res.addActionListener(this);
	}
	public static void main(String[] args) {
        new test();
    }
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		new ressign();
	}
}
/**package selfstudy;
//import selfstudy.error.*;
import selfstudy.cussign.*;
import selfstudy.empsign.*;
import selfstudy.ressign.*;
public class test{
	public static void main(String[] args) {
		int choice=2;
		switch(choice)
		{
		case 1:
		{
			new cussign();
			break;
		}
		case 2:
		{
			new ressign();
			break;
		}
		case 3:
		{
			new empsign();
			break;
		}
		}
        
    }
}
**/