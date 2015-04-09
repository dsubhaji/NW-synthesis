package sd.nwmodels;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Vector;
import java.util.Hashtable;

public class PajekFileGenerator {

	public  String formatPajekInput(Collection<Affiliations> userAffiliations, Vector<Integer> entities, int timeStep)
	{
		
		System.out.println("user affiliations count : " + userAffiliations.size() );
		Hashtable<Integer,Integer> mapping = new Hashtable<Integer,Integer>();
		FileOutputStream out;
		PrintStream p;
		String outputFileName = "";
		Integer[] vertices = entities.toArray(new Integer[0]);
		Arrays.sort(vertices);
		try{
			outputFileName = "NW_" + "TimeStep_" +  timeStep + ".net";
					
			out = new FileOutputStream(outputFileName);
			p = new PrintStream( out );
			for(int y=0;y<vertices.length;y++){
					mapping.put(vertices[y],(new Integer(y+1)));
			}
			//System.out.println(mapping);
			p.println ("*Vertices" + " " + mapping.size());
			System.out.println("There are " + mapping.size() + " vertices");
			for(int y = 0; y < vertices.length; y++)
			{
				p.println (y+1 + " " + "\"" + vertices[y] + "\"");
			}
			
			p.println("*Edges");
			//System.out.println("*Edges");
			int edgeCount = 0;
					
			if(userAffiliations != null)
				for(Affiliations bean : userAffiliations)
					{
						printAffiliations(p, bean, mapping);
						edgeCount++;
			}
			//System.out.println("linkWeightThreshold = " + linkWeightThreshold);
			System.out.println("There are " + edgeCount + " edges");
			System.out.println("See results in the file: " + outputFileName);
			
			p.close();
			out.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	
		return outputFileName;
	}

	private void printAffiliations(PrintStream p , Affiliations bean, Hashtable<Integer,Integer> mapping ){
		
		int sourceEntity = 0;
		int destEntity = 0;
		int weight = 0;
		sourceEntity = bean.getEntitySource();
		destEntity = bean.getEntityDestination();
		weight = bean.getEntityWeight();
		int sourceN =0;
		int destN=0;
		if(mapping.containsKey(sourceEntity))
			sourceN=mapping.get(sourceEntity);
		
		if( mapping.containsKey(destEntity))
			destN = mapping.get(destEntity);
		
		if(destN==0) return; //this is a fix , should be removed
		p.println(sourceN + " " + destN + " " + weight );// + " l " + tempAffiliation.getCommonRelation().toString());
	
	}
}
