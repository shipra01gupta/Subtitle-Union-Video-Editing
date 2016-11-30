/*
 *  This is the main class for GUI for the application SUV-Pro
 */


import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/*
 * Starting of the class which then connects different classes
 */


public class GUI extends JFrame
{
	
	private JPanel main= new JPanel();																			
	
	private JButton jbtn= new JButton("Launch");
	private JButton ok= new JButton("OK");
	private JButton save= new JButton("Save as");
	private JButton[] panelButton= new JButton[3];
	
	private JLabel jlbl3= new JLabel("Guided By:- Asst.Prof. Dr. R. Sunitha              ");
	private JLabel jlbl1= new JLabel("Done By:- Kumari Sipra              ");
	private JLabel jlbl2= new JLabel("Done By:- Vaisakh S "); 
	private JLabel jlbl= new JLabel(), jlbl4= new JLabel();
	
	private JTextField area= new JTextField(10);
	
	private Boolean open_flag= false;
	private Boolean ok_flag= false;
	
	NextLevel nl= new NextLevel();
	panel p= new panel();
	
	private ImageIcon icon= new ImageIcon("Images/1.gif");
	
	private String reference= null, path= null, holder= null,srt_reference= null;
	private String cut_reference= null, target_reference= null;
	private String[] names= {"Open_SRT", "Open_Cut", "Select_target"};
	
	private int flag= 0;
	private int i= 0, intervals;
	
	/*
	 * Constructor of the main class in which main button is implemented and the main panel is added to the frame
	 */
	
	public GUI()
	{
		
		try 
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} 
		catch (ClassNotFoundException | InstantiationException	| IllegalAccessException | UnsupportedLookAndFeelException e) 
		{
			e.printStackTrace();
		}
		
		
		Toolkit t= Toolkit.getDefaultToolkit();
		Dimension d= t.getScreenSize();
		setSize(d.width,d.height);
		setTitle("SUV-Pro");
		
		Image icon= t.getImage("Images/icon.jpg");
		setIconImage(icon);

		main.setBackground(Color.GRAY);
		
		jlbl1.setForeground(Color.white);
		jlbl2.setForeground(Color.white);
		jlbl3.setForeground(Color.pink);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		for(int k= 0; k < names.length; ++k)
		{
			panelButton[k]= new JButton(names[k]);
		}
		
		p.add(jlbl3);
		p.add(jlbl1);
		p.add(jlbl2);
		add(p);
		
		jbtn.setBorderPainted(false);
		jbtn.setRolloverEnabled(false);
		jbtn.setFocusPainted(false);
		main.add(jbtn,BorderLayout.SOUTH);
		add(main,BorderLayout.SOUTH);
		
		/*
		 * ActionListener for the main button named Launch
		 */
		
		jbtn.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				remove(main);
				p.im= new ImageIcon("Images/bcg.jpg");
				p.ic= p.im.getImage();
				
				jlbl1.setText("");
				jlbl2.setText("");
				jlbl3.setText("");
			
				p.add(nl.nextPanel,BorderLayout.NORTH);
				add(p);
				
				repaint();
				revalidate();
				
				actionDone();
			}
		});
	}
	
	/*
	 * Implementation of the method in which all the home page buttons are implemented
	 */

	public void actionDone()
	{	
		
		JPanel jpnlm= new JPanel();
		JPanel inner= new JPanel();
		JPanel center= new JPanel();
		
		panel p1= new panel();
		
		JFileChooser jfc= new JFileChooser("bca2013");
		
		JButton open= new JButton("OPEN");
		JButton out;
		
		JMenuBar bar= new JMenuBar();
		JMenu nav= new JMenu("NAVIGATOR");
		JMenuItem back= new JMenuItem("HOME");
		
		open.setFocusable(false);
		open.setBorderPainted(false);
		open.setRolloverEnabled(false);
		
		jpnlm.setLayout(new BorderLayout());
		
		nav.add(back);
		bar.add(nav);
		
		/*
		 * Implementation of the navigator button named HOME
		 */
		
		back.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (open_flag == false || ok_flag == false)
				{
					ActionListener[] a= open.getActionListeners();
					ActionListener[] b= ok.getActionListeners();
					int i,j;
					for (i= 0,j= a.length; i < j; i++)
					{
						
						open.removeActionListener(a[i]);
					}
					for(i= 0; i < b.length; ++i)
						ok.removeActionListener(b[i]);
					
					open_flag= true;
					ok_flag= true;
				}
				
				remove(jpnlm);
				
				for(int k= 0; k < 3; ++k)
					center.remove(panelButton[k]);
				
				jlbl.setText("");
				inner.remove(ok);
				inner.remove(save);
				jlbl4.setText("");
				inner.remove(area);
				center.remove(area);
				center.remove(ok);
				area.setText("");
				
				p1.im= new ImageIcon("Images/bcg.jpg");
				p1.ic= p1.im.getImage();
				p1.add(nl.nextPanel,BorderLayout.NORTH);
				++i;
				
				add(p1);
				
				revalidate();
				repaint();
				
			}
		});
		
		/*
		 * Implementation of the first home button named MediaPlayer
		 */

		nl.jbtn[0].addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				
				if (open_flag == false)
				{
					ActionListener[] a= open.getActionListeners();
					for (int i= 0,j= a.length; i < j; i++)
						open.removeActionListener(a[i]);
				}
				open_flag= false;
				
				open.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e) 
					{

						int value= jfc.showOpenDialog(jpnlm);
						if(value == JFileChooser.APPROVE_OPTION)
						{
							File file= jfc.getSelectedFile();

							reference= file.toString();
							
							String a= Parsing(reference);
					    	
							if(a.equals("mp4") || a.equals("avi") || a.equals("VOB") || a.equals("flv") || a.equals("mpeg") ||  a.equals("MOV") || a.equals("mpg"))
								new MediaPlayer(file.toString());
							else
								JOptionPane.showMessageDialog(jpnlm, "MediaPlayer Could Not Recogonize The File", "InFo", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				});
				if(i == 0)
					remove(p);
				else
					remove(p1);
				
				
				
				inner.add(open, BorderLayout.NORTH);
				inner.setBackground(Color.black);
			    jpnlm.add(inner,BorderLayout.NORTH);
				jpnlm.add(bar, BorderLayout.WEST);
				open.setBounds(0, 0, 10, 10);
				open.setForeground(Color.cyan);
				jpnlm.setBackground(Color.darkGray);
				add(jpnlm);
				
				repaint();
				revalidate();
			}
		});
		
		/*
		 * Implementation of the second home button named Converter
		 */
		
		/*nl.jbtn[1].addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
			
				if (open_flag == false)
				{
					ActionListener[] a= open.getActionListeners();
					for (int i= 0,j= a.length; i < j; i++)
						open.removeActionListener(a[i]);
				}
				open_flag= false;
				
				open.addActionListener(new ActionListener() 
				{
					
					public void actionPerformed(ActionEvent e) 
					{
						int value= jfc.showOpenDialog(jpnlm);
						if(value == JFileChooser.APPROVE_OPTION)
						{
							File file= jfc.getSelectedFile();

							reference= file.toString();
							
							String a= Parsing(reference);
					    	
							if(a.equals("mp4") || a.equals("avi") || a.equals("VOB") || a.equals("flv") || a.equals("mpeg") ||  a.equals("MOV") || a.equals("mpg"))
							{
								inner.add(save,BorderLayout.CENTER);
								repaint();
								revalidate();
								
								save.addActionListener(new ActionListener() 
								{
									public void actionPerformed(ActionEvent e)
									{
										int val= jfc.showSaveDialog(jpnlm);
										if(value == JFileChooser.APPROVE_OPTION)
										{
											File file= jfc.getSelectedFile();

											holder= file.toString();

											String a= Parsing(holder);
							
											
											if(a.equals("mp4") || a.equals("avi") || a.equals("VOB") || a.equals("flv") || a.equals("mpeg") ||  a.equals("MOV") || a.equals("mpg"))
									    	{
									    		JFrame frame= new JFrame();
									    		Thread th= new Thread(new Runnable() 
									    		{
									    			public void run() 
									    			{

									    				new Converter(reference,holder);
									    				frame.dispose();
									    				JOptionPane.showMessageDialog(jpnlm, "Converted");
									    			}

									    		});
									    		th.start();

									    		JOptionPane.showMessageDialog(frame, "Converting in Process. Plz wait till the Confirmation message", "convertion", JOptionPane.INFORMATION_MESSAGE, icon);
										    								
									    	}
									    	else
												JOptionPane.showMessageDialog(jpnlm, "You selected an invalid name and format for the output file", "InFo", JOptionPane.INFORMATION_MESSAGE);

										}
										
									}
								});
								
								
							}
							else
								JOptionPane.showMessageDialog(jpnlm, "Converter Could Not Recogonize The File", "InFo", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				});
				
				
				
				if(i == 0)
					remove(p);
				else
					remove(p1);
			
				center.remove(jlbl4);
				jlbl4.setText("");
				
				inner.add(open, BorderLayout.NORTH);
				inner.setBackground(Color.black);
			    jpnlm.add(inner,BorderLayout.NORTH);
				jpnlm.add(bar, BorderLayout.WEST);
				open.setBounds(0, 0, 10, 10);
				open.setForeground(Color.cyan);
				jpnlm.setBackground(Color.darkGray);
				
				add(jpnlm);
				
				repaint();
				revalidate();
			}
		});*/
		
		/*
		 * Implementation of the third home button named Editing
		 */
		
		nl.jbtn[2].addActionListener(new ActionListener() 
		{
			
			
			public void actionPerformed(ActionEvent e) 
			{
				
				
				if (ok_flag == false)
				{
					ActionListener[] a= ok.getActionListeners();
					for (int i= 0,j= a.length; i < j; i++)
						ok.removeActionListener(a[i]);
				}
				ok_flag= false;
				
				
				
				if (open_flag == false)
				{
					ActionListener[] a= open.getActionListeners();
					for (int i= 0,j= a.length; i < j; i++)
						open.removeActionListener(a[i]);
				}
				open_flag= false;
				
				open.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int value= jfc.showOpenDialog(jpnlm);


						if(value == JFileChooser.APPROVE_OPTION)
						{

							File file= jfc.getSelectedFile();
							reference= file.toString();

							String a= Parsing(reference);
				
							if(a.equals("mp4") || a.equals("avi") || a.equals("VOB") || a.equals("flv") || a.equals("mpeg"))
							{

								for(int k= 0; k < names.length; ++k)
									center.add(panelButton[k],BorderLayout.CENTER);
								
								
								
								
								ActionListener srt= new ActionListener() 
								{
									public void actionPerformed(ActionEvent e) 
									{
										int value= jfc.showOpenDialog(jpnlm);
										

										if(value == JFileChooser.APPROVE_OPTION)
										{

											File file= jfc.getSelectedFile();
											srt_reference= file.toString();

											String a= Parsing(srt_reference);
											
											if(a.equals("srt") )
												System.out
														.println("Matched");
											else
											{
												JOptionPane.showMessageDialog(jpnlm, "SUV-Pro Could Not Recogonize The File (Select a srt file)", "InFo", JOptionPane.INFORMATION_MESSAGE);

											}
												
												
										}
									}
								};

								ActionListener cut= new ActionListener() 
								{
									public void actionPerformed(ActionEvent e) 
									{
										int value= jfc.showOpenDialog(jpnlm);


										if(value == JFileChooser.APPROVE_OPTION)
										{

											File file= jfc.getSelectedFile();
											cut_reference= file.toString();

										}
										String a= Parsing(cut_reference);

										if(a.equals("txt") )
										{

										}
											
										else
										{
											JOptionPane.showMessageDialog(jpnlm, "SUV-Pro Could Not Recogonize The File (Select a txt File)", "InFo", JOptionPane.INFORMATION_MESSAGE);
										}
									}
								};
								
								ActionListener target= new ActionListener() 
								{
									public void actionPerformed(ActionEvent e) 
									{
										int value= jfc.showSaveDialog(jpnlm);
										

										if(value == JFileChooser.APPROVE_OPTION)
										{

											File file= jfc.getSelectedFile();
											target_reference= file.toString();
											
											String a= Parsing(target_reference);
											
											if (a.equals("mp4"))
											{
													
											}
											else
												JOptionPane.showMessageDialog(jpnlm, "Please enter the name and format of the required video file( preffered format: mp4)");
											
										}
									}
								};
								panelButton[0].addActionListener(srt);
								panelButton[1].addActionListener(cut);
								panelButton[2].addActionListener(target);
								center.add(ok);
								
								ok.addActionListener(new ActionListener() 
								{
									public void actionPerformed(ActionEvent e) 
									{
										
										
										if(srt_reference != null && cut_reference != null)
										{
											JFrame frame= new JFrame();
											Thread th= new Thread(new Runnable() 
											{
												public void run() 
												{
													new SrtProcess(srt_reference, cut_reference, reference, target_reference);
													frame.dispose();
													JOptionPane.showMessageDialog(jpnlm, "Successfully Edited in"+target_reference);
												}
											});
											th.start();
										    JOptionPane.showMessageDialog(frame, "Editing in Process. Plz wait till the Confirmation message", "SUV-Pro", JOptionPane.INFORMATION_MESSAGE, icon);

										}
										
										if(srt_reference == null && cut_reference != null)
										{
											int res= JOptionPane.showConfirmDialog(jpnlm, "Do you want to edit the video alone..?");
											switch(res)	
											{
											case JOptionPane.YES_OPTION:	
												JFrame frame= new JFrame();
												Thread th= new Thread(new Runnable() 
												{
													public void run() 
													{
														new SrtProcess(srt_reference, cut_reference, reference, target_reference);
														frame.dispose();
														JOptionPane.showMessageDialog(jpnlm, "Successfully Edited in"+target_reference);
													}
												});
												th.start();
											    JOptionPane.showMessageDialog(frame, "Editing in Process. Plz wait till the Confirmation message", "SUV-Pro", JOptionPane.INFORMATION_MESSAGE, icon);

											}
										}
										
										if((srt_reference == null && cut_reference == null) || (cut_reference == null))
											JOptionPane.showMessageDialog(jpnlm, "You havent chosen the requiste File: ", "InFo", JOptionPane.INFORMATION_MESSAGE);
										
									}
								});
								
								repaint();
								revalidate();
							}
							else
								JOptionPane.showMessageDialog(jpnlm, "SUV-Pro Could Not Recogonize The File", "InFo", JOptionPane.INFORMATION_MESSAGE);

						}
					}
				});
				if(i == 0)
					remove(p);
				else
					remove(p1);
				
				inner.add(open, BorderLayout.NORTH);
				inner.setBackground(Color.black);
			    jpnlm.add(inner,BorderLayout.NORTH);
				jpnlm.add(bar, BorderLayout.WEST);
				center.setBackground(Color.gray);
				jpnlm.add(center,BorderLayout.CENTER);
				open.setBounds(0, 0, 10, 10);
				open.setForeground(Color.cyan);
				jpnlm.setBackground(Color.darkGray);
				add(jpnlm);
				repaint();
				revalidate();
			}
		});
		
		/*
		 * Implementation of the fourth home button named ScreenRecorder
		 */
		
		nl.jbtn[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				jlbl.setText("Enter the Seconds For Which You Want to Capture the Screen");
				jlbl.setForeground(Color.white);
				inner.remove(open);
				if(i == 0)
					remove(p);
				else
					remove(p1);
//				remove(p);
				
				if (ok_flag == false)
				{
					ActionListener[] a= ok.getActionListeners();
					for (int i= 0,j= a.length; i < j; i++)
						ok.removeActionListener(a[i]);
				}
				ok_flag= false;
				
				ok.addActionListener(new ActionListener() 
				{
					public void actionPerformed(ActionEvent e) 
					{
						JFrame frame= new JFrame();
					    Thread th= new Thread(new Runnable() 
					    {
							public void run() 
							{
								new ScreenRecorder(area.getText());
								frame.dispose();
								JOptionPane.showMessageDialog(jpnlm, "ScreenRecording is done");
							}
						});
						th.start();
					    JOptionPane.showMessageDialog(frame, "ScreenRecording in Process. Plz wait till the Confirmation message", "ScreenRecording", JOptionPane.INFORMATION_MESSAGE, icon);

					}
				});
				inner.add(jlbl, BorderLayout.NORTH);
				inner.add(area,BorderLayout.NORTH);
				inner.add(ok, BorderLayout.NORTH);
				inner.setBackground(Color.black);
			    jpnlm.add(inner,BorderLayout.NORTH);
				jpnlm.add(bar, BorderLayout.WEST);
				open.setForeground(Color.cyan);
				jpnlm.setBackground(Color.darkGray);
				add(jpnlm);
				repaint();
				revalidate();

				
			}
		});
		
		/*
		 * Implementation of the fifth home button named About Us
		 */
		
		nl.jbtn[4].addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				JFrame aboutFrame= new JFrame("About Us");
				aboutFrame.setSize(1100, 700);
				aboutFrame.setResizable(false);
				aboutFrame.setLocationRelativeTo(center);
				aboutFrame.setVisible(true);
				
				panel aboutPanel= new panel();
				aboutPanel.im= new ImageIcon("Images/aboutus.png");
				aboutPanel.ic= aboutPanel.im.getImage();
				
				aboutFrame.getContentPane().add(aboutPanel);
				
				revalidate();
				repaint();
			}
		});
		
	}
	
	
	/*
	 * This method just parses the string to get the format of the file and returns the format back to the caller
	 */
	
	public String Parsing(String name)
	{
		String a= null;
		
		for (int i= name.length(); i >= 1; --i)
		{
			a= name.substring(i-1, i);
			if( a.equals("."))
			{
				a= name.substring(i, name.length());
				break;
			}
		}
		
		return a;
	}
	
	
	/*
	 * Starting of the main function
	 */
	
	public static void main(String[] args)
	{
		EventQueue.invokeLater(
     			 new Runnable(){
       			 public void run()
        			{
         				 GUI a= new GUI();
         				 a.setVisible(true);
        			}
      			}
    		);

	}


}

/*
 * Another class called NextLevel for the declaration and adding of all home page buttons
 */

class NextLevel
{
	public JButton[] jbtn= new JButton[5];
	
	public JPanel nextPanel= new JPanel();
	
	private String[] str= {"MediaPlayer", "Converter", "Editing", "ScreenRecording", "About Us"};
	private String[] str2= {"Opens a Video player for U..!", "Opens a converter which converts video to any format for U..!", "Opens an editing arena for video and audio editing for U..!", "Opens a Application Which allows you to capture the screeen and make a video out of that..!", "A few Words about the instruments behind this..!"};
	
	panel p1= new panel();
	
	public NextLevel()
	{
		nextPanel.setBackground(Color.getHSBColor(100.6f, 10.65f, 86.2f));
		for (int i= 0; i < jbtn.length; ++i)
		{
			jbtn[i]= new JButton(str[i]);
			jbtn[i].setBorderPainted(false);
			jbtn[i].setFocusable(false);
			jbtn[i].setRolloverEnabled(false);
			jbtn[i].setToolTipText(str2[i]);
			nextPanel.add(jbtn[i]);
		}

	}
	
	
	
}

/*
 * Implementation of the class panel where the main panel is created and image is added to it. later this panel is added to the main frame
 */

class panel extends JPanel
{
	public ImageIcon im= new ImageIcon("Images/1.jpg");
	public Image ic= im.getImage();
	
	int i= 0;
	
	public Dimension temp= new Dimension(20,20);
	
	private GridBagLayout gbl;
	private GridBagConstraints gbc;
	
	public panel()
	{
		setBackground(Color.black);
	}
	public void paintComponent (Graphics g)
	{	

		Graphics2D g2= (Graphics2D)g;
    		g2.drawImage(ic,0,0,getWidth(),getHeight(),null);
	}


}

