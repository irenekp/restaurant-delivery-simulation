package selfstudy.error;
import java.awt.*;  
import java.awt.event.*;
public class except{
	private static Dialog d;  
    public except() {  
        Frame f= new Frame();  
        d = new Dialog(f , "Error", true); 
        Font l1=new Font (Font.MONOSPACED,Font.PLAIN, 13);
	    Color c=Color.decode("#761137");
	    d.setForeground(c);
	    d.setFont(l1);
        d.setLayout( new FlowLayout() );  
        Button b = new Button ("OK");  
        b.addActionListener ( new ActionListener()  
        {  
            public void actionPerformed( ActionEvent e )  
            {  
                except.d.setVisible(false); 
                except.d.dispose();
            }  
        });  
        Label l=new Label ("ERROR!");
        l.setBounds(50,50,50,30);
        d.add(l);
        l=new Label("Some of the operations you've performed are illegal.");
        l.setBounds(70,80,160,30);
        d.add(l);
        l=new Label("Please Try Again");
        l.setBounds(50,120,200,30);
        d.add(l);
        d.add(b);   
        d.setSize(300,300);    
        d.setVisible(true);  
    }  
    public static void main(String args[])  
    {  
        new except();  
    } 
}