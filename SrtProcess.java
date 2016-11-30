import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Vector;

/**
 * This class is the main timestamp which converts the given time in string to it's respective time in hours,minutes,seconds 
 * and milliseconds.
 *
 */
class Srt_Timestamp
{
	public long hours, minutes, seconds, milliseconds,ms;
	public long millisec_time;
	public Srt_Timestamp(String intime)
	{
		String[] firstsplit= intime.split(":");
		hours = Integer.parseInt(firstsplit[0]);
		minutes = Integer.parseInt(firstsplit[1]);	
		String[] secondsplit= firstsplit[2].split(",");
		seconds= Integer.parseInt(secondsplit[0]);
		milliseconds= Integer.parseInt(secondsplit[1]);
		ms= hours * 60;
		ms= (ms + minutes) * 60;
		ms= (ms + seconds) * 1000;
		ms= ms + milliseconds;
		millisec_time= ms;
	}

	public String toString()
	{
		String str= " "+hours+":"+minutes+":"+seconds+","+milliseconds;
		return(str);
	} 
}
/**
 * This class holds the records in a file which has start time ,end time,displacment and dialogue
 *
 */

class Srt_Record
{
	Srt_Timestamp starttime,endtime;
	String dialogue;
	long displacement;
	public Srt_Record(String stime, String etime, String dialog)
	{
		dialogue = dialog;
		starttime= new Srt_Timestamp(stime);
		endtime= new Srt_Timestamp(etime);
		displacement= endtime.millisec_time - starttime.millisec_time;
	}

	public String toString()
	{
		String str= starttime + " --> " + endtime + "\n" + dialogue;
		return(str);
	}
}
/**
 * This class is for converting,filling_gap in srt_record,sorting,performing. 
 */

class Calculator
{
 	/* In Timeconvertion which converts milliseconds to it's hours,minutes,seconds,milliseconds
	*/
	private  long hr,min,sec,ms,mstime;
	private  void TimeConvertion(long millisec)
	{
		mstime= millisec;
		hr= mstime / 3600000;
		mstime= mstime % 3600000;
		min= mstime / 60000;
		mstime= mstime % 60000;
		sec= mstime / 1000;
		mstime= mstime % 1000;
		ms= mstime;

	}
	/*
 	* In this the hours,minutes,seconds and milliseconds of the respective start time and 
	* end time is set.
	*/
	private Vector<Srt_Record> Convertingtime(Vector<Srt_Record> convert)
	{
		for(int s= 0; s < convert.size();s++)
                {
                	Srt_Record converting= new Srt_Record(00+":"+00+":"+00+","+000,00+":"+00+":"+00+","+000, null);
                        TimeConvertion(convert.get(s).starttime.millisec_time);
                        converting.starttime.hours= hr;
                        converting.starttime.minutes= min;
                        converting.starttime.seconds= sec;
                        converting.starttime.milliseconds= ms;
                        TimeConvertion(convert.get(s).starttime.millisec_time + convert.get(s).displacement);
                        converting.endtime.hours= hr;
                        converting.endtime.minutes= min;
                        converting.endtime.seconds= sec;
                        converting.endtime.milliseconds= ms;
                        converting.displacement= convert.get(s).displacement;
                        converting.dialogue= convert.get(s).dialogue;
                        convert.set(s,converting);
                }
		return(convert);
	
	}
	 /* In this the user given input is arranged in ascending order. 
 	*/
	public Vector<Srt_Record> Sorting(Vector<Srt_Record> user)
	{
		Vector<Srt_Record> temp = new Vector<Srt_Record>();
		for (int i = 0; i < user.size(); i++)
		{
			for (int j = i+1; j < user.size(); j++)
			{
				if(user.get(j).starttime.millisec_time < user.get(i).starttime.millisec_time)
				{
					temp.add(i,user.get(i));
					user.set(i,user.get(j));
					user.set(j, temp.get(i));
				}
			}
		}
		return(user);
	}
 	/* In this  the readfile gap is filled with the start time,end time ,displacement and dialogue
 	*/
	private Vector<Srt_Record> Fillgap(Vector<Srt_Record> input)
	{
		
		Vector<Srt_Record> modified= new Vector<Srt_Record>();
	 	long stime,etime,diff;			
		int position= 0;
		for(int i= 0; modified.size() <  ((input.size() * 2)- 1); i++)
		{ 
				
			modified.add(position,input.get(i));	
			if(modified.size() < ((input.size() * 2)- 1))
			{
               			Srt_Record temp= new Srt_Record(00+":"+00+":"+00+","+000,00+":"+00+":"+00+","+000, null);
				position++;
		 		stime= input.get(i).endtime.millisec_time + 1;	
		 		etime= input.get(i+1).starttime.millisec_time - 1;	
				diff= etime - stime;
				temp.starttime.millisec_time= stime;	
				temp.endtime.millisec_time= etime;	
				temp.displacement= diff;
				modified.add(position,temp);
			}
			position++;
		}
		return(modified);
	}
	 /* In this the main calculation is done i.e cutting the timestamp and changing the timestamp.
 	*/
	public  void Perform( Vector<Srt_Record> scannedinput ,Vector<Srt_Record> userinput, String target)
	{
		scannedinput= Fillgap(scannedinput);
		int i= 0,j= 0;
		long cum_dis= 0,previous_cum_dis= 0;
		long  s= scannedinput.get(j).starttime.millisec_time,d=  scannedinput.get(j).displacement,cs= userinput.get(i).starttime.millisec_time,cd= userinput.get(i).displacement;
		while( cd != -1)
		{
			
			if(cs < s + d )
			{
				if((cs + cd) <= (s + d))
				{
					cum_dis += cd ;
					cd= 0;
				}
				else
				{
					cd= cd - (d - (cs - s));
					cum_dis += d - (cs - s);
					cs= s + d;
				}
			}
			else
			{ 
				if( j == 0)
				{
			       	  	d= d - cum_dis  ;
                			Srt_Record temp= new Srt_Record(00+":"+00+":"+00+","+000,00+":"+00+":"+00+","+000, null);
                			temp.starttime.millisec_time= s;
                			temp.displacement= d;
                			temp.dialogue= scannedinput.get(j).dialogue;
                			scannedinput.set(j, temp);
                			previous_cum_dis += cum_dis;
                			cum_dis= 0;
                			j++;
                			s= scannedinput.get(j).starttime.millisec_time;
                			d=  scannedinput.get(j).displacement;
				}
				else
				{
					s= s - previous_cum_dis;
					d= d - cum_dis ;
                			Srt_Record temp= new Srt_Record(00+":"+00+":"+00+","+000,00+":"+00+":"+00+","+000, null);
                			temp.starttime.millisec_time= s;
                			temp.displacement= d;
                			temp.dialogue= scannedinput.get(j).dialogue;
                			scannedinput.set(j, temp);
                			previous_cum_dis += cum_dis;
                			cum_dis= 0;
                			j++;
                			s= scannedinput.get(j).starttime.millisec_time;
                			d=  scannedinput.get(j).displacement;
				}
			}
			if(cd == 0)
			{
				if(i+1 < userinput.size())
				{
					i++;
					cs= userinput.get(i).starttime.millisec_time;
					cd= userinput.get(i).displacement;
				}
				else
				{
					if( j == 0)
                                	{
                                        d= d - cum_dis;
                                        Srt_Record temp= new Srt_Record(00+":"+00+":"+00+","+000,00+":"+00+":"+00+","+000, null);
                                        temp.starttime.millisec_time= s;
                                        temp.displacement= d;
                                        temp.dialogue= scannedinput.get(j).dialogue;
                                        scannedinput.set(j, temp);
                                        previous_cum_dis += cum_dis;
                                        cum_dis= 0;
                                        j++;
                                        s= scannedinput.get(j).starttime.millisec_time;
                                        d=  scannedinput.get(j).displacement;
                               		}
                                	else
                               		{
                                        s= s - previous_cum_dis;
                                        d= d - cum_dis;
                                        Srt_Record temp= new Srt_Record(00+":"+00+":"+00+","+000,00+":"+00+":"+00+","+000, null);
                                        temp.starttime.millisec_time= s;
                                        temp.displacement= d ;
                                        temp.dialogue= scannedinput.get(j).dialogue;
                                        scannedinput.set(j, temp);
                                        previous_cum_dis += cum_dis;
                                        cum_dis= 0;
                                        j++;
                                        s= scannedinput.get(j).starttime.millisec_time;
                                        d=  scannedinput.get(j).displacement;
                                	}
					cd= -1;
				}
			}
		}
		while(j < scannedinput.size())
		{
			s= s - previous_cum_dis;
                                        Srt_Record temp= new Srt_Record(00+":"+00+":"+00+","+000,00+":"+00+":"+00+","+000, null);
                                        temp.starttime.millisec_time= s;
                                        temp.displacement= scannedinput.get(j).displacement ;
                                        temp.dialogue= scannedinput.get(j).dialogue;
                                        scannedinput.set(j, temp);
                                        j++;
                                        if(j < scannedinput.size() )
                                        {
                                        	s= scannedinput.get(j).starttime.millisec_time;
                                        	d=  scannedinput.get(j).displacement;	
                                        }
		}
		scannedinput= Convertingtime(scannedinput);	
		
		
		
		try
		{
			Output protem= new Output();
			protem.write(scannedinput,target);
		} 
		catch (FileNotFoundException e)
		{

			e.printStackTrace();
		}
	}

}

/**
* In this we are writing the edited srt_record into new or existing file.
*/
class Output
{

	public void write(Vector<Srt_Record> write, String output) throws FileNotFoundException
	{
		
		String a= null;
		for (int i= output.length(); i >= 1; --i)
		{
			a= output.substring(i-1, i);
			if( a.equals("."))
			{
				a= output.substring(0, i);
				break;
			}
		}
		output= a.concat("srt");
		
		System.out.println("The output: "+output); 
		 
		PrintWriter outputfile= new PrintWriter(output);
		int j= 0;
		for(int i= 0;i < write.size();i++)
		{
			if( write.get(i).displacement > 0 && write.get(i).dialogue != null)
			{
				outputfile.print(""+(j+1)+"\n");
				outputfile.print(write.get(i));
				j++;
			}
		}
		outputfile.close();
	}

}
/**
 * In this class the user given file addresses is read and stored in a vector
 * for editing the user given inputs.
 */


class SrtFile
{
	private static Vector<Srt_Record> readfile= new Vector<Srt_Record>();
	public SrtFile(String filepath)
	{
		Scanner srt = null;
		try
		{
			srt = new Scanner(new File(filepath));
		} 
		catch (FileNotFoundException e)
		{

			e.printStackTrace();
		}
		int count= 2;
		Boolean check,wrong= false,check2= true;
		String temp,dialog= "",starttime,endtime,tcount= Integer.toString(count);
		check= srt.hasNext();
		for(int j= 0;!check.equals(wrong);j++)
		{
			if(j == 0)
				srt.nextLine();
			temp= srt.nextLine(); 
			String [] s= temp.split(" --> ");
			starttime= s[0];
			endtime= s[1];
			temp= srt.nextLine(); 
			for(int i= 0;!temp.equals(tcount) && !check2.equals(wrong); i++)
			{
				dialog= dialog.concat("").concat(temp).concat("\n");
				temp= srt.nextLine();
				check2= srt.hasNext();
				if(check2 == wrong)
				{
					dialog= dialog.concat("").concat(temp).concat("\n");
				}
			}
			count++;
			tcount= Integer.toString(count);

			readfile.add(new Srt_Record(starttime, endtime, dialog));
			dialog= "";

			check= srt.hasNext();
		}
	}
	/*
	 * In this it call the perform  which is in calculation class 
	 */
	public static void Toperform(Vector<Srt_Record> user, String target)
	{
		Calculator protem= new Calculator();
		protem.Perform(readfile,user, target);

	}
}
/**
 * This class is the main which takes user given file name to read.
 * The number of cuts the user given in the cut file and it's value is read and 
 *  stored in a vector
 */

public class SrtProcess
{
	
	public  String output= null;
	
	public SrtProcess(String srtfile,String cutfile, String input_file, String target_output)	
	{
		
		
		output= target_output;
		
		long user_stime,user_etime;
		String filepath;
		Scanner input= null;
		Vector<Srt_Record> user=  new Vector<Srt_Record>();
		try
		{
			input= new Scanner(new File(cutfile));
		}
		catch(Exception e)
		{
			System.out.println("ERROR= "+e); 
		}
			filepath= srtfile;
			
			new Frames(cutfile, input_file, output);
			if(srtfile == null)
				return;
			SrtFile srt= new SrtFile(filepath);
			
			while(input.hasNextFloat())
			{
				Srt_Record record= new Srt_Record(00+":"+00+":"+00+","+000,00+":"+00+":"+00+","+000, null);
				user_stime= (long) (input.nextFloat() * 1000);
				user_etime= (long) (input.nextFloat() * 1000);
				record.starttime.millisec_time=  user_stime;
				record.endtime.millisec_time= user_etime ;
				record.displacement= record.endtime.millisec_time - record.starttime.millisec_time;
				user.add(record);
			}
			Calculator protem= new Calculator();
			user= protem.Sorting(user);
			SrtFile.Toperform(user, output);
		/*}
		catch(Exception e)
		{
			System.out.println("ERROR= "+e); 
		}*/
	}
}