
import java.awt.BorderLayout;

import java.awt.Color;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;


import javax.swing.ImageIcon;

import javax.swing.JButton;

import javax.swing.JFrame;

import javax.swing.JPanel;


import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;



/*
 * Class for MediaPlayer which accepts a video file and creates a media player for playing it.
 */


public class MediaPlayer 
{
	
private int i= 0;
	
	
private JFrame frame;
	
	
private EmbeddedMediaPlayerComponent ecom;
	
	
private JPanel jpnl= new JPanel();
	
	
private JButton jbtn1, jbtn2, jbtn3, jbtn4;

	
/*
	 * Constructor of the  class which assigns and sets the property for all the fields
	 */
	
	
public MediaPlayer(String str) 
	{
		
ecom = new EmbeddedMediaPlayerComponent();
		
		
frame = new JFrame("SUV-Pro MediaPlayer");
		
frame.setSize(1200,600);
		
frame.setContentPane(ecom);
		
frame.setVisible(true);
		
		
ecom.getMediaPlayer().playMedia(str);
		
		
jpnl.setBackground(Color.gray);
		
		
jbtn1= new JButton("pause");
		
jbtn2= new JButton("play");
		
jbtn3= new JButton("stop");
		
jbtn4= new JButton("Exit");
		
		
jpnl.add(jbtn1);
		
jpnl.add(jbtn2);
		
jpnl.add(jbtn3);
		
jpnl.add(jbtn4);
		
		
frame.getContentPane().add(jpnl, BorderLayout.SOUTH);//check
		
		
jbtn1.addActionListener(new ActionListener() 
		{
			
public void actionPerformed(ActionEvent e) 
			{
			
	++i;
				
if(i <= 1)
				
 ecom.getMediaPlayer().pause();
			}
		
});
		
		
jbtn2.addActionListener(new ActionListener() 
		{
	
		public void actionPerformed(ActionEvent e) 
			{
	
			i= 0;
				
ecom.getMediaPlayer().play();
				
			
}
	
	});
		
		
jbtn3.addActionListener(new ActionListener() 
		{
			
public void actionPerformed(ActionEvent e) 
			{
				
ecom.getMediaPlayer().stop();
         
	}
		
});
		
		
jbtn4.addActionListener(new ActionListener() 
		{
			
public void actionPerformed(ActionEvent e) 
			{
				
System.exit(0);
			
}
		
});
		

    
}


}
		
	
	