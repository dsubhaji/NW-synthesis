package sd.nwmodels;

import java.util.Vector;
import java.util.*;

public class ProbabilisticNetworkModel {

	private int ToTalNoOfTimeSteps; // T in nw growth model
	public int getToTalNoOfTimeSteps() {
		return ToTalNoOfTimeSteps;
	}

	public void setToTalNoOfTimeSteps(int toTalNoOfTimeSteps) {
		ToTalNoOfTimeSteps = toTalNoOfTimeSteps;
	}

	public Double getM1() {
		return m1;
	}

	public void setM1(Double m1) {
		this.m1 = m1;
	}

	public Double getC1() {
		return c1;
	}
	

	public void setC1(Double c1) {
		this.c1 = c1;
	}

	public Double getM2() {
		return m2;
	}

	public void setM2(Double m2) {
		this.m2 = m2;
	}

	public Double getC2() {
		return c2;
	}

	public void setC2(Double c2) {
		this.c2 = c2;
	}

	public int getNoOfMembersInTeam() {
		return noOfMembersInTeam;
	}

	public void setNoOfMembersInTeam(int noOfMembersInTeam) {
		this.noOfMembersInTeam = noOfMembersInTeam;
	}

	private Double m1;
	private Double c1;
	private Double m2;
	private Double c2;
	
	private int noOfMembersInTeam; // d in nw growth model
	
	private int noOfTeams;
	
	
	public int getNoOfTeams() {
		return noOfTeams;
	}

	public void setNoOfTeams(int noOfTeams) {
		this.noOfTeams = noOfTeams;
	}

	public Vector<Integer> generateVertices(int noOfLevels)
	{
		Vector<Integer> vertexIDs = new Vector<Integer>();

		int vertexCount = 1; // Adding the root Node
		
		for(int i=1; i<=noOfTeams; i++) 
		{
			// To do add the logic of levels later.
			vertexCount = vertexCount + 1; // Adding the level one team lead nodes.
			vertexCount = vertexCount + noOfMembersInTeam; //Adding members in each team
		}
		
		for(int i=0; i<vertexCount; i++)
		{
			vertexIDs.add(new Integer(i+1));
		}
		return vertexIDs;
	}
	
	public Vector<Vector<Integer>> generateLevels()
	{
		Vector<Vector<Integer>> levels = new Vector<Vector<Integer>>();
		
		Vector<Integer> levelZero = new Vector<Integer>();
		levelZero.add(1);
		levels.add(levelZero);
		
		//Level width here will be no of vertices in each level.
		
		Vector<Integer> levelWidths = new Vector<Integer>();
		System.out.println("No of teams is " + noOfTeams);
		levelWidths.add(noOfTeams);
		levelWidths.add(noOfTeams*noOfMembersInTeam);
		
		
		int noOfLevels = levelWidths.size();
		int lastVertex = 1;
	
		for(int i=0; i<noOfLevels; i++)
		{
			int noOfVerticesAtThisLevel = levelWidths.get(i).intValue();
			//System.out.println("No of vertex at "+ i +" level are " + noOfVerticesAtThisLevel); 
			Vector<Integer> levelVertices = new Vector<Integer>();
			for(int j=1; j<= noOfVerticesAtThisLevel; j++)
			{
				int addedVertex = lastVertex + 1;
			//	System.out.println("addedVertex " + addedVertex);
				levelVertices.add(new Integer(addedVertex));
				
								
				int lastVertexAtThisLevel = levelVertices.lastElement().intValue();	
			//	System.out.println("lastVertexAtThisLevel " + lastVertexAtThisLevel);
				lastVertex = lastVertexAtThisLevel;
				
			}
			levels.add(levelVertices);
		//	System.out.println("Vertices at this level " + levelVertices);
			

			}
		
		for(int i = 0; i < levels.size(); i++)
		{
			//System.out.println("Level : " + i);
			Vector<Integer> currentLevel = levels.get(i);
			
			for(int j = 0; j < currentLevel.size();j++)
			{
				//System.out.println(currentLevel.elementAt(j));
			}
			
		}
		
 	return levels;
	}


	public Vector<Affiliations> generateAffiliations(Vector<Vector<Integer>> levels, double p, Vector<Integer> vertexIDs){
		
		Vector<Affiliations> affiliations = new Vector<Affiliations>();
		Vector <Hashtable <Integer,Vector<Integer>>> leafNodesForParents = new Vector<Hashtable<Integer,Vector<Integer>>>();
		
		
		int noOfLevels = levels.size();
		
		// Start: Generate the usual tree affiliations and record leaves under each parent
		
		for(int i=0; i<noOfLevels-1; i++)
		{
			int currentLevelNo = i;
			int nextLevelNo = i+1;
			//System.out.println("*total levels is : " + levels.size());
			
			
			Vector<Integer> currentLevel = levels.get(currentLevelNo);
			Vector<Integer> nextLevel = levels.get(nextLevelNo);
			
			int noOfVerticesInCurrentLevel = currentLevel.size();
		
			
			int currentAffiliationSource = 0;
			int currentAffliationDestination = 0;
			
			//System.out.println("*Edges");
			int counter = 0;
			for(int j=0; j<noOfVerticesInCurrentLevel; j++)
			{
				currentAffiliationSource = currentLevel.get(j);
				Vector<Integer> leavesForAParent = new Vector<Integer>();
				int end=0;
				int start = counter;
				
				if(i==0)
				{
					end = start + noOfTeams;
					//System.out.println("*end is in i = 0 : " + end);
				}
				else
				{
					end = start + noOfMembersInTeam;
					//System.out.println("*end is : " + end);
				}
				
				for (int k=start; k<end; k++)
				{
					
					//System.out.println("*next level no of elements is : " + nextLevel.size());
					
					currentAffliationDestination = nextLevel.get(k).intValue();
					
					// Detecting the leaf nodes for each parent and adding to a vector
					if(currentLevelNo == (noOfLevels-2))
						{
							leavesForAParent.add(currentAffliationDestination);
						}
					
				//	System.out.println("*Edge");
					//System.out.println(currentAffiliationSource + " " + currentAffliationDestination);
					Affiliations Affiliations = new Affiliations();
					Affiliations.setEntitySource(currentAffiliationSource);
					Affiliations.setEntityDestination(currentAffliationDestination);
					Affiliations.setEntityWeight(1);
					affiliations.add(Affiliations);
					counter++;
					
					
					
				}
				// For each parent (key), storing the vector of corresponding leaf nodes vector (value) as key-value
				// in hashtable; adding each hashtable to a vector
				
				if(currentLevelNo == (noOfLevels-2))
				{
					Hashtable<Integer, Vector<Integer>> parentLeaves = new Hashtable<Integer, Vector<Integer>>();
					parentLeaves.put(currentAffiliationSource,leavesForAParent);
					leafNodesForParents.add(parentLeaves);
				}
				
			}
			
			
			
		}
		//System.out.println("leafNodesForParents " + leafNodesForParents);
		// End: Generate the usual tree affiliations and record leaves under each parent
		
		
		// Start: Generate affiliations due to connections between leaf nodes under same parent
		
		
		Vector<Affiliations> leafAffiliations = new Vector<Affiliations>();
		
		
		double maxNoOfLinksBetnLeafVertices = Binomial.choose2(noOfMembersInTeam);
		double actualNoOfLinksBetnLeafVertices = Math.rint(maxNoOfLinksBetnLeafVertices*p);
		System.out.println("Max no of links betn leaf vertices of same parent: " + maxNoOfLinksBetnLeafVertices);
		System.out.println("Actual no of links betn leaf vertices of same parent: " + actualNoOfLinksBetnLeafVertices);
		
		Random random1 = new Random();
		Random random2 = new Random();
		
		Vector<String> newEdgeIDsBetnLeaves = new Vector<String>();
		
		
		//System.out.println("Size of leafNodesForParents is " + leafNodesForParents);
		//System.out.println("\nThe parents with their leaf nodes are: \n");
		for(int i=0; i<leafNodesForParents.size(); i++)
		{
			Hashtable<Integer, Vector<Integer>> temp = leafNodesForParents.elementAt(i);
			//System.out.println(temp);
			Vector<Integer> leaves = new Vector<Integer>();
			Enumeration<Vector<Integer>> enumLeaves = temp.elements();
			//System.out.println("Size of enum leaves  is " + enumLeaves);
			while(enumLeaves.hasMoreElements())
					{
						leaves = enumLeaves.nextElement();
						System.out.println("Leaves " + leaves);
						
						int counter = (new Double(actualNoOfLinksBetnLeafVertices)).intValue();
						while(counter>0)
						{
							//System.out.println("No of members in the team are " + noOfMembersInTeam);
							int source = random1.nextInt(noOfMembersInTeam);
							int dest = random2.nextInt(noOfMembersInTeam);
							//System.out.println("Random generated source is " + source);
							//System.out.println("Random generated dest is " + dest);
							Integer sourceVertex = leaves.elementAt(source);
							Integer destVertex = leaves.elementAt(dest);
		
							if(!sourceVertex.equals(destVertex) && !newEdgeIDsBetnLeaves.contains(sourceVertex+"_"+destVertex) && !newEdgeIDsBetnLeaves.contains(destVertex+"_"+sourceVertex) )
								{
								
									Affiliations Affiliations = new Affiliations();
									Affiliations.setEntitySource(sourceVertex.intValue());
									Affiliations.setEntityDestination(destVertex.intValue());
									Affiliations.setEntityWeight(1);
									String newEdge = sourceVertex+"_"+destVertex;
									//System.out.println("Adding an edge between " + sourceVertex + " and " + destVertex);
									
									
									leafAffiliations.add(Affiliations);
									newEdgeIDsBetnLeaves.add(newEdge);
									counter = counter-1;
									//System.out.println("counter value is " + counter);
								
								}
						}
					}
		}
		//System.out.println("No of new edges are: " + newEdgeIDsBetnLeaves.size());
	//	System.out.println("New edges between leaves: " + newEdgeIDsBetnLeaves);
		affiliations.addAll(leafAffiliations);
		
		// End: Generate affiliations due to connections between leaf nodes under same parent
		
		System.out.println("Done generating affiliations");
		return affiliations;
			
	}
}
