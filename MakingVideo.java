import java.io.File;


import com.xuggle.xuggler.*;

import com.xuggle.xuggler.ICodec.Type;
import com.xuggle.mediatool.*;


/*
 * This is the class which encodes the intermediate audio and video file inta single edited media file
 */


public class MakingVideo
{
	
private int counterv= 0, countera= 0, offset= 0, index= 0, videoIn= -1, num= 0, audioIn= -1, indexa= 0;
	
	
private long indexing= 0;
	
private long indexingn= System.nanoTime();
	

	private String name= "suv.mp4";
	private String nameM= "suv.mp3";

	private String nameOut= "/home/bca2013/bca/6th_sem/Project/Alwin/cutting_Save.mp4";
	

	private IContainer cont= IContainer.make();

	private IContainer conta= IContainer.make();
	
	
private IMediaWriter writer= null;
	
	
private IStreamCoder coder= null;
	
private IStreamCoder coderau= null;

	
/*
	 * Constructor of the class where every fields of the class is assigned
	 */
	
	
public MakingVideo(String strv, String stra, String target)
	{
	
	name= strv;
		
nameM= stra;
		
nameOut= target;
		
cont.open(name, IContainer.Type.READ, null);
		
conta.open(nameM, IContainer.Type.READ, null);
		
writer= ToolFactory.makeWriter(nameOut);
	}


	/*
	 * This method decodes the intermediate file into IPackets and then encodes into one media file
	 */
	

	public void ActioTaken()
	{
		
	num= cont.getNumStreams();
			
			
for(int i= 0; i < num; ++i)
			{
				
IStream stream= cont.getStream(i);
				
IStream streamer= conta.getStream(i);
				
IStreamCoder coderAudio= streamer.getStreamCoder();
				
IStreamCoder newCoder= stream.getStreamCoder();
				
				
if(newCoder.getCodecType() == Type.CODEC_TYPE_VIDEO)
				
{
					
videoIn= i;
					
coder= newCoder;
				
}
				
				
if(coderAudio.getCodecType() == Type.CODEC_TYPE_AUDIO)
				
{
					
audioIn= i;
					
coderau= coderAudio;
				
}
			
}
			
			
index= writer.addVideoStream(0,0,ICodec.ID.CODEC_ID_MPEG4, coder.getWidth(),coder.getHeight());
			
indexa= writer.addAudioStream(1, 0, coderau.getChannels(), coderau.getSampleRate());
		
			
coder.open(null, null);
			
coderau.open(null, null);
			
			
IPacket packet= IPacket.make();
			
			
while(cont.readNextPacket(packet) >= 0)
			
{
				
				
if(packet.getStreamIndex() == videoIn)
				
{
					
					
counterv++;
					
IVideoPicture picture=	IVideoPicture.make(coder.getPixelType(), coder.getWidth(), coder.getHeight());
				
	int offset1= 0;
					
					
while(offset1 < packet.getSize())
					
{
						
int dcd= coder.decodeVideo(picture, packet, offset1);
						
if(dcd < 0)
							
System.out.println("Again error on decoding.....!!!");
						
						
offset1= offset1+dcd;
						
						
if(picture.isComplete())
						
{
							
writer.encodeVideo(index, picture);
							
					
	}
				
	}
				
}
			
}
			
			
IPacket packeta= IPacket.make();
			
			
while(conta.readNextPacket(packeta) >= 0)
			
{
				
if(packet.getStreamIndex() == audioIn)
				
{
					
countera++;
					
IAudioSamples samples = IAudioSamples.make(1024, coderau.getChannels());
					
int offseta= 0;

					
while(offseta < packeta.getSize())
					
{
						
int dcd= coderau.decodeAudio(samples, packeta, offseta);
						
if(dcd < 0)
							
System.out.println("Error in decoding audio: ");
						
offseta= offseta+dcd;
						
						
if(samples.isComplete())
						
{
							
writer.encodeAudio(indexa, samples);
						
}
					
}
				
}
			
}
			
writer.close();
			
File file= new File(name);
			
File filea= new File(nameM);
			
file.delete();
			
filea.delete();

	
}
	

}

