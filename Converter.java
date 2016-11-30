/* import com.xuggle.mediatool.IMediaReader;

import com.xuggle.mediatool.IMediaWriter;

import com.xuggle.mediatool.ToolFactory;


/*
 * This is the class for converting
 */


public class Converter 
{
    

	String output;
    
	
/*
	 * Constuctor of the class which converts the video to another format
	 */
	
	
public Converter(String str, String out)
    {

    	
System.out.println("Thie input: "+str);	
    	
System.out.println("Thie output: "+output);
    
IMediaReader mediaReader = ToolFactory.makeReader(str);
    	
IMediaWriter mediaWriter = ToolFactory.makeWriter(out, mediaReader);
    	
mediaReader.addListener(mediaWriter);
    	
while (mediaReader.readPacket() == null) ;
   
 }

} */
