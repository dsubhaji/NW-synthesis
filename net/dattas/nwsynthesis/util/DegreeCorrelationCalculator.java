package net.dattas.nwsynthesis.util;

import java.util.*;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

public class DegreeCorrelationCalculator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String inputFilenNme1 = "JCN-W_Edges.csv";
		String inputFileName2 = "JCN-W_Vertex_Degree.csv";
		String outputFileName = "JCN-W_Degree_Correlations.csv";
		Hashtable<String, Integer> vertexDegree = new Hashtable<String, Integer>();
		
		CsvWriter writer = new CsvWriter(outputFileName);
		CsvReader reader1 = null;
		CsvReader reader2 = null;
		try
		{
			reader1 = new CsvReader(inputFilenNme1);
			reader2 = new CsvReader(inputFileName2);
			
			reader2.readHeaders();
			int vertexCount = 0;
			String vertex = "";
			String degree = "";
			
			while(reader2.readRecord())
			{
				vertex = reader2.get("Vertex");
				degree = reader2.get("Degree");
				//System.out.println(vertex + " " + degree);
				vertexDegree.put(vertex, new Integer(degree));
				vertexCount++;
				
				
			}
		//	System.out.println("No of vertices read " + vertexDegree.size());
			
		//	System.out.println(vertexDegree);
			
			reader1.readHeaders();
			int edgeCount = 0;
			while(reader1.readRecord())
			{
				String edgeSource = reader1.get("Vertex 1");
				String edgeDestination = reader1.get("Vertex 2");
			//	System.out.println(edgeSource + " " + edgeDestination);
				String edgeSourceDegree = 	vertexDegree.get(edgeSource).toString();
				String edgeDestinationDegree = vertexDegree.get(edgeDestination).toString();
				String edgeID = edgeSource + "_" + edgeDestination;
				
				System.out.println(edgeID + " " + edgeSourceDegree + " " + edgeDestinationDegree);
				
				writer.write(edgeID);
				writer.write(edgeSourceDegree);
				writer.write(edgeDestinationDegree);
				writer.endRecord();
				edgeCount++;
			}
		//	System.out.println("No of edges read " + edgeCount);
			System.out.println("Output printed in file: " + outputFileName);
			
		reader1.close();
		reader2.close();
		writer.close();
			
		}
		catch(Exception e)
		{
		 e.printStackTrace();	
		}
	}

}
