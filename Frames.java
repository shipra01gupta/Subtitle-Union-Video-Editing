
import java.awt.image.BufferedImage;

import java.io.File;

import java.util.Scanner;

import java.util.concurrent.TimeUnit;

import java.awt.Graphics2D;

import java.awt.Image;


import javax.swing.ImageIcon;


import com.xuggle.mediatool.IMediaWriter;

import com.xuggle.mediatool.MediaListenerAdapter;

import com.xuggle.mediatool.ToolFactory;

import com.xuggle.mediatool.IMediaReader;

import com.xuggle.mediatool.event.IAudioSamplesEvent;

import com.xuggle.mediatool.event.IVideoPictureEvent;

import com.xuggle.xuggler.Global;

import com.xuggle.xuggler.IAudioSamples;

import com.xuggle.xuggler.IContainer;

import com.xuggle.xuggler.IStream;

import com.xuggle.xuggler.IStreamCoder;

import com.xuggle.xuggler.ICodec;

import com.xuggle.xuggler.ICodec.Type;



/*
 * Implementation of the main class which opens the media file and capture the frames out of it
 */


public class Frames 
{
	
private static int index= -1;
	
private int i= 0;
	
private int n_images= 0;
	
	
private String name= null;
	
private static String out= "suv.mp4";

	private static String out1= "suv.mp3";
	
private static String output= null;
	
	
private double seconds= 0.01;
	
	
private long mic=0, mica= 0;

	private long count= 0, cntr= 0;
	
private long var, var2;
	long bwframes= (long)(Global.DEFAULT_PTS_PER_SECOND * seconds);
	

	float arr2[];
	
	
	
private static IContainer cont= IContainer.make();
	
private static IMediaWriter writer= ToolFactory.makeWriter(out);
	
private static IMediaWriter writer1= ToolFactory.makeWriter(out1);
	
	
private int counter= 0;
	
   
 private IStreamCoder coderV= null;
  
  private IStreamCoder coderA= null;
	


	
int ind, aud, audio, video;


	/*
	 * Constructor of the main class where container is opened and all coders are assigned. later media file is decoded by reader
	 */
	
	
public Frames(String cut_file, String input_file, String outPut_file) {

		
output= outPut_file;
		
		
arr2= getInput(cut_file);
		
var= (long)arr2[0];
		
var2= (long)arr2[1];
		
name= input_file;
		
		
int r=  cont.open(name,IContainer.Type.READ, null);
		
if(r < 0)
			
System.out.println("Container could not open the file");

		
IMediaReader reader= ToolFactory.makeReader(cont);

		
int numS= cont.getNumStreams();
		
for(int i= 0; i < numS; ++i)
		{
			
IStream stream= cont.getStream(i);
			
IStreamCoder coder= stream.getStreamCoder();
			
if(coder.getCodecType() == Type.CODEC_TYPE_VIDEO)
            {
           
     coderV= coder;
            }
			
if(coder.getCodecType() == Type.CODEC_TYPE_AUDIO)
				
coderA= coder;

		}

		
ind= writer.addVideoStream(0,0,ICodec.ID.CODEC_ID_MPEG4, coderV.getWidth(),coderV.getHeight());
		
aud= writer1.addAudioStream(0, 0,coderA.getChannels(), coderA.getSampleRate());

		
reader.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);
		
reader.addListener(new CuttingListener());


		
while(true)
		{
			
if(reader.readPacket() != null)
				
break;

		
}
		
		
AboutToClose();



	}

	
/*
	 * implementation of the method AboutToClose which inokes the other class which encode both audio and video
	 */
	

	public void AboutToClose()
	{
		
writer.close();
		
MakingVideo mv= new MakingVideo(out, out1, output);
		
mv.ActioTaken();
	
}

	
	
/*
	 * This method is responsible for collecting and storing the input for the editing the video
	 */
	

	public static float[] getInput (String cutfile)
	{
		
int n= 0;
		
		
Scanner in= null,count= null;
		
		
try
		
int= new Scanner(new File(cutfile));
			
count= new Scanner(new File(cutfile));

			
while(count.hasNextFloat())
			{
			
	n++;
				
count.nextFloat();
			
}
		
}
		
catch(Exception e)
        	{
         
    		System.out.println("ERROR in frame= "+e);
        	
}

		
float arr[]= new float[n];
		
		
for (int i= 0, j= 1; i < arr.length && in.hasNextFloat(); ++i)
		
{
			
			
if(in.hasNextFloat())
			{
			
  arr[i]= (in.nextFloat()*1000000);
			
}
			
else
			
{
				
break;
			
}
			
if(arr[i] <= 0 )
			
{
				
System.out.println("Sorry...!!! The input is invalid...!! Plz continue from the last..:");
				
			}
			if(i != 0)
			{
				if(arr[j] < arr[i - 1])
				{
					System.out.println("Sorry the input is not valid...: plz continue from last..:");
					
				}
				++j;
			}
		}
		return(arr);
	}

	
	/*
	 * this class is responsible for decoding both the audio stream and video stream by the help of reader
	 */
	
	public class CuttingListener extends MediaListenerAdapter {
		
		
		/*
		 *This method decodes the audio leaving the parts which falls in the category for editing
		 */
		
		public void onAudioSamples(IAudioSamplesEvent event)
		{
			
			counter = counter + 1;
			audio++;
			
			IAudioSamples samples;
			
			if((event.getTimeStamp() <= var) || event.getTimeStamp() >= var2)
			{
				samples= event.getAudioSamples();
				writer1.encodeAudio(aud, event.getAudioSamples());
			}
			else
			{
				count++;
				if(event.getTimeStamp() >= (var2 - 20000))
				{
					System.out.println("i1: "+i);

					if(arr2.length != (i+2))
				
	{
						
var= (long)arr2[i+2];
						
var2= (long)arr2[i+3];
						
i= i+2;
					
}
				
}
			
}
		
}
		
		
/*
		 *This method decodes the video leaving the parts which falls in the category for editing
		 */

		
public void onVideoPicture(IVideoPictureEvent event)
		{
			
			
counter = counter + 1;
			
video++;
			
			
if(event.getStreamIndex() != index )
			
{
				
if(index == -1)
					
index= event.getStreamIndex();
				
else
					
return;
			
}
			
			
if((event.getTimeStamp() <= var) || event.getTimeStamp() >= var2)
			
{
				
n_images++;
				
try
				
{
					
LogoToImage(event.getImage());
				
}
				
catch(Exception e){}
				
				
writer.encodeVideo(ind,event.getImage(),mic, TimeUnit.MICROSECONDS);
				
mic= (long)mic + 33367;

			
}
			
else
			
{
				
count++;
				
if(event.getTimeStamp() >= (var2 - 30000))
				
{
					
System.out.println("i1: "+i);

					
if(arr2.length != (i+2))
					
{
						
var= (long)arr2[i+2];
						
System.out.println("i2: "+i);
						
var2= (long)arr2[i+3];
						
System.out.println("i3: "+i);
						
i= i+2;
					
}
				
}
			
			
}
			
		
}
		
		
		
/*
		 * This method puts the logo of our application to each frames of the edited video
		 */

	
	
private void LogoToImage(BufferedImage image)
		
{
			
cntr++;
			
String outing= out+cntr+".png";
			
Graphics2D g= image.createGraphics();
			
ImageIcon im= new ImageIcon("Images/logo.png");
			
Image i;
			
i= im.getImage();
			
g.drawImage(i,image.getWidth()-100,image.getHeight()-100,100,80,null);

		
}

	
}



}
