package net.dattas.nwsynthesis.ioformat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Vector;

import net.dattas.nwsynthesis.control.NetworkModelingController;
import net.dattas.nwsynthesis.databean.AffiliationDataBean;

public class PajekInputFormatter {

	public String time_folder; 
	public PajekInputFormatter(){
		
	}
	public PajekInputFormatter(String t) {
		this.time_folder=t;
	}
	
	public String formatPajekInput(Vector<AffiliationDataBean> affiliationDataBeans, Vector<String> vertices, int linkWeightThreshold, String affiliationType)
	{
		//System.out.println("Will format Pajek input now");
		Hashtable<String,Integer> mapping = new Hashtable<String,Integer>();
		
		FileOutputStream out;
		PrintStream p;
		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_hhmmssSSSSSS_a");
	    
	    String timestamp = sdf.format(cal.getTime());
	    String outputFileName = "";
	    
	    String dir=time_folder+"-nw-files";
		File directory = new File(dir);
		boolean output_dir = directory.mkdir();
		String dirName=directory.getAbsolutePath()+"\\";
	    		
		try{
			outputFileName ="NW_" + timestamp + ".net";
			
			out = new FileOutputStream(dirName+outputFileName);
			p = new PrintStream(out);
			
			
			for(int y = 0; y < vertices.size(); y++)
			{
					mapping.put(vertices.elementAt(y),(new Integer(y+1)));
			
				}
			// System.out.println(mapping.toString());
		
			//System.out.println("*Vertices" + " " + mapping.size());
			 p.println ("*Vertices" + " " + mapping.size());
			//System.out.println("There are " + mapping.size() + " vertices");
			for(int y = 0; y < vertices.size(); y++)
				{
			
					//System.out.println(y+1 + " " + "\"" + workitems.elementAt(y) + "\"");
				 	p.println (y+1 + " " + "\"" + vertices.elementAt(y) + "\"");
				}
			
			 p.println("*Edges");
			//System.out.println("*Edges");
			int edgeCount = 0;
			
			for(int z = 0; z < affiliationDataBeans.size(); z ++)
				{
					AffiliationDataBean tempAffiliation = new AffiliationDataBean();
					tempAffiliation = affiliationDataBeans.elementAt(z);
					String source = "";
					String dest = "";
					
					int sourceEntity = 0;
					int destEntity = 0;
					int weight = 0;
					if(affiliationType.equalsIgnoreCase("entity"))
					{
						sourceEntity = tempAffiliation.getEntitySource();
						destEntity = tempAffiliation.getEntityDestination();
						weight = tempAffiliation.getEntityWeight();
						source = new Integer(sourceEntity).toString();
						dest = new Integer(destEntity).toString();
					}
					else if(affiliationType.equalsIgnoreCase("category"))
					{
						source = tempAffiliation.getCategorySource();
						dest = tempAffiliation.getCategoryDestination();
						weight = tempAffiliation.getCategoryWeight();
					}
									
					
					if(weight > linkWeightThreshold)
					{
					//System.out.println(mapping.get(new Integer(sourceWI).toString()) + " " + mapping.get(new Integer(destWI).toString()) + " " + weight);
						//p.println(mapping.get(new Integer(sourceEntity).toString()) + " " + mapping.get(new Integer(destEntity).toString()) + " " + weight);
						p.println(mapping.get(source) + " " + mapping.get(dest) + " " + weight);
						edgeCount++;
					}
				}
			//System.out.println("linkWeightThreshold = " + linkWeightThreshold);
			//System.out.println("There are " + edgeCount + " edges");
			//System.out.println("See results in the file: " + outputFileName);
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return outputFileName;
		
	}
}
