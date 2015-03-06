package net.dattas.nwsynthesis.ioformat;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Vector;

import net.dattas.nwsynthesis.databean.AffiliationDataBean;


public class WeightAnalyzer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public void computeAverageWeight(Vector<AffiliationDataBean> affiliationDataBeans, Vector<String> vertices)
	{
		FileOutputStream out;
		PrintStream p;
		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_hhmmss_a");
	    
	    String timeStamp = sdf.format(cal.getTime());
		try
		{
		String outputFileName = "Weight_" + timeStamp + ".txt";
		out = new FileOutputStream(outputFileName);
		p = new PrintStream( out );
		
		
		
		for(int i=0; i<vertices.size(); i++)
		{
			int count = 0;
			float runningWeight = 0;
			float averageWeight = 0;
			String vertexID = vertices.elementAt(i);
			
			String tempVertexID = "";
			
			for(int j=0; j<affiliationDataBeans.size(); j++)
			{
				//count = 0;
				//runningWeight = 0;
				AffiliationDataBean affiliationDataBean = new AffiliationDataBean();
				affiliationDataBean = affiliationDataBeans.elementAt(j);
				tempVertexID = (new Integer((affiliationDataBean.getEntitySource())).toString());
				
				if(vertexID.equalsIgnoreCase(tempVertexID))
				{
					//System.out.println("In if");
					//System.out.println("vertexID " + vertexID);
					//System.out.println("tempVertexID " + tempVertexID);
					int weight = affiliationDataBean.getEntityWeight();
					float floatWeight = new Integer(weight).floatValue();
					
					if(weight>0)
					{
					//System.out.println("weight " + affiliationDataBean.getEntityWeight());
					runningWeight = runningWeight + floatWeight;
					count = count + 1;
					}
					//System.out.println("runningWeight " + runningWeight);
					//System.out.println("count " + count);
				}	
			}
			//System.out.println("i " + i);
			//System.out.println("vertex " + vertexID);
			//System.out.println("runningWeight " + runningWeight);
			//System.out.println("count " + count);
			if(count > 0)
			{
			averageWeight = runningWeight/count;
			}
			//System.out.println("averageWeight " + averageWeight);
			//p.println(vertexID + " " + averageWeight);
			p.println(averageWeight);
			
		}

	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
}
}