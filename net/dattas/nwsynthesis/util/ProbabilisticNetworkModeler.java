package net.dattas.nwsynthesis.util;

import java.util.*;

import net.dattas.nwsynthesis.databean.*;
public class ProbabilisticNetworkModeler {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		int branch = 3;
		int height = 3;
		
		System.out.println("Branch = " + branch + " Height = " + height +"\n");
		ProbabilisticNetworkModeler bbm = new ProbabilisticNetworkModeler();
		Vector<String> vertexIDs = bbm.generateVertices(height, branch);
		System.out.println("Vertices " + vertexIDs +"\n");
		
		Vector<Vector<Integer>> levels = bbm.generateLevels(height, branch);
		System.out.println("No of levels " + levels.size());
		System.out.println("levels " + levels + "\n");
		
		Vector<AffiliationDataBean> affiliations = bbm.generateAffiliations(levels, branch);
		System.out.println("No of affiliations(edges) " + affiliations.size() + "\n");
		*/
		System.out.println("In the main() method of ProbabilisticNetworkModeler; nothing here!");
		
		
	}
	
	public Vector<String> generateVertices(int height, int branch)
	{
		Vector<String> vertexIDs = new Vector<String>();
		int level_minus_one = height;
		int vertexCount = 1;
		
		while(level_minus_one>0)
		{	
			double newVertices = java.lang.Math.pow (branch, level_minus_one);
			int newVerticesInt = (new Double(newVertices)).intValue();
			vertexCount = vertexCount + newVerticesInt;
			level_minus_one--;
		//	System.out.println(vertexCount);
		}
		//vertexCount = vertexCount+1;
		
		//System.out.println("Total vertices = " + vertexCount);
		
		for(int i=0; i<vertexCount; i++)
		{
			vertexIDs.add((new Integer(i+1)).toString());
		}
		//System.out.println(vertexIDs);
		return vertexIDs;
	}
	
	public Vector<Vector<Integer>> generateLevels(int height, int branch)
	{
		Vector<Vector<Integer>> levels = new Vector<Vector<Integer>>();
		Vector<Integer> levelZero = new Vector<Integer>();
		levelZero.add(1);
		levels.add(levelZero);
		
		Vector<Integer> levelWidths = new Vector<Integer>();
		
		for(int i=1; i<=height; i++)
		{
			int tempWidth = (new Double(java.lang.Math.pow (branch, i)).intValue());
			levelWidths.add(new Integer(tempWidth));
		}
	
		//System.out.println("levelwidth"+levelWidths);
		int noOfLevels = levelWidths.size();
		int lastVertex = 1;
	
		for(int i=0; i<noOfLevels; i++)
		{
			int noOfVerticesAtThisLevel = levelWidths.get(i).intValue();
			// System.out.println("noofverticesatthis level"+noOfVerticesAtThisLevel);
			Vector<Integer> levelVertices = new Vector<Integer>();
			for(int j=1; j<= noOfVerticesAtThisLevel; j++)
			{
				int addedVertex = lastVertex + 1;
				//System.out.println("addedVertex " + addedVertex);
				levelVertices.add(new Integer(addedVertex));
				
				
				
				int lastVertexAtThisLevel = levelVertices.lastElement().intValue();	
				//System.out.println("lastVertexAtThisLevel " + lastVertexAtThisLevel);
				lastVertex = lastVertexAtThisLevel;
				
			}
			levels.add(levelVertices);
			//System.out.println("Vertices at this level " + levelVertices);
		}
		
		
		return levels;
	}

	// 1. Pass parameter p = probability of leaf nodes affiliating; check extent of edge growth
	// 2. Pass parameter q = probability of peers affiliating, at parameter l = level of peers; 
	// check for extent of diameter shrinkage
	
	public Vector<AffiliationDataBean> generateAffiliations(Vector<Vector<Integer>> levels, int branch, int height, double p, 
			int peerLevel, double q, double randomAffiliationProbability, Vector<String> vertexIDs)
	{
		Vector<AffiliationDataBean> affiliations = new Vector<AffiliationDataBean>();
		Vector <Hashtable <Integer,Vector<Integer>>> leafNodesForParents = new Vector<Hashtable<Integer,Vector<Integer>>>();
		
		
		int noOfLevels = levels.size();
		
		// Start: Generate the usual tree affiliations and record leaves under each parent
		
		for(int i=0; i<noOfLevels-1; i++)
		{
			int currentLevelNo = i;
			int nextLevelNo = i+1;
			
			//System.out.println("\nThis is level no: " + currentLevelNo);
			
			
			Vector<Integer> currentLevel = levels.get(currentLevelNo);
			//System.out.println("currentLevel " + currentLevel);
			Vector<Integer> nextLevel = levels.get(nextLevelNo);
			//System.out.println("nextLevel " + nextLevel + "\n");
			
			int noOfVerticesInCurrentLevel = currentLevel.size();
		
			
			int currentAffiliationSource = 0;
			int currentAffliationDestination = 0;
			
			//System.out.println("*Edges");
			int counter = 0;
			for(int j=0; j<noOfVerticesInCurrentLevel; j++)
			{
				currentAffiliationSource = currentLevel.get(j);
				//System.out.println("currentAffiliationSource " + currentAffiliationSource);
				Vector<Integer> leavesForAParent = new Vector<Integer>();
				
				int start = counter;
				int end = start + branch;
				
				for (int k=start; k<end; k++)
				{
					
					currentAffliationDestination = nextLevel.get(k).intValue();
					
					// Detecting the leaf nodes for each parent and adding to a vector
					if(currentLevelNo == (noOfLevels-2))
						{
							leavesForAParent.add(currentAffliationDestination);
						}
					
					//System.out.println("*Edge");
					//System.out.println(currentAffiliationSource + " " + currentAffliationDestination);
					AffiliationDataBean affiliationDataBean = new AffiliationDataBean();
					affiliationDataBean.setEntitySource(currentAffiliationSource);
					affiliationDataBean.setEntityDestination(currentAffliationDestination);
					affiliationDataBean.setEntityWeight(1);
					affiliations.add(affiliationDataBean);
					counter++;
					
					
					
				}
				// For each parent (key), storing the vector of corresponding leaf nodes vector (value) as key-value
				// in hashtable; adding each hashtable to a vector
				
				if(currentLevelNo == (noOfLevels-2))
				{
					Hashtable<Integer, Vector<Integer>> parentLeaves = new Hashtable<Integer, Vector<Integer>>();
					parentLeaves.put(currentAffiliationSource,leavesForAParent);
					leafNodesForParents.add(parentLeaves);
					//System.out.println("leafnodesforparents"+leafNodesForParents);
				}
				
			}
			
			
			
		}
		//System.out.println("Final leafNodesForParents " + leafNodesForParents);
		// End: Generate the usual tree affiliations and record leaves under each parent
		
		
		// Start: Generate affiliations due to connections between leaf nodes under same parent
		
		Vector<AffiliationDataBean> leafAffiliations = new Vector<AffiliationDataBean>();
		
		
		double maxNoOfLinksBetnLeafVertices = Binomial.choose2(branch);
		double actualNoOfLinksBetnLeafVertices = Math.rint(p*maxNoOfLinksBetnLeafVertices);
		//System.out.println("Max no of links betn leaf vertices of same parent: " + maxNoOfLinksBetnLeafVertices);
		//System.out.println("Actual no of links betn leaf vertices of same parent: " + actualNoOfLinksBetnLeafVertices);
		
		Random random1 = new Random();
		Random random2 = new Random();
		
		Vector<String> newEdgeIDsBetnLeaves = new Vector<String>();
		
		
		//System.out.println("\nThe parents with their leaf nodes are: \n");
		for(int i=0; i<leafNodesForParents.size(); i++)
		{
			Hashtable<Integer, Vector<Integer>> temp = leafNodesForParents.elementAt(i);
		//	System.out.println("manish");
			//System.out.println(temp);
			Vector<Integer> leaves = new Vector<Integer>();
			Enumeration<Vector<Integer>> enumLeaves = temp.elements();
			while(enumLeaves.hasMoreElements())
					{
						leaves = enumLeaves.nextElement();
						//System.out.println("Leaves " + leaves);
						
						int counter = (new Double(actualNoOfLinksBetnLeafVertices)).intValue();
						while(counter>0)
						{
							int source = random1.nextInt(branch);
							int dest = random2.nextInt(branch);
							Integer sourceVertex = leaves.elementAt(source);
							Integer destVertex = leaves.elementAt(dest);
						
						
							if(!sourceVertex.equals(destVertex) && !newEdgeIDsBetnLeaves.contains(sourceVertex+"_"+destVertex) && !newEdgeIDsBetnLeaves.contains(destVertex+"_"+sourceVertex) )
								{
								
									AffiliationDataBean affiliationDataBean = new AffiliationDataBean();
									affiliationDataBean.setEntitySource(sourceVertex.intValue());
									affiliationDataBean.setEntityDestination(destVertex.intValue());
									affiliationDataBean.setEntityWeight(1);
									String newEdge = sourceVertex+"_"+destVertex;
									
									leafAffiliations.add(affiliationDataBean);
									newEdgeIDsBetnLeaves.add(newEdge);
									counter = counter-1;
								
								}
						}
					}
		}
		//System.out.println("No of new edges are: " + newEdgeIDsBetnLeaves.size());
		//System.out.println("New edges between leaves: " + newEdgeIDsBetnLeaves);
		affiliations.addAll(leafAffiliations);
		
		// End: Generate affiliations due to connections between leaf nodes under same parent
		
		// Start: Generate affiliations due to connections between peers at same level
		
		Vector<AffiliationDataBean> levelAffiliations = new Vector<AffiliationDataBean>();
		Vector<String> newEdgeIDsBetweenLevels = new Vector<String>();
		
		//int peerLevel = 2;
		//double q = 0.75;
		Random random3 = new Random();
		Random random4 = new Random();
		
		if(peerLevel < height)
		{
			Vector<Integer> verticesAtThisLevel = levels.elementAt(peerLevel);
			//System.out.println("Vertices at this peer level: " + verticesAtThisLevel);
			int noOfVerticesAtThisLevel = verticesAtThisLevel.size();
			double maxNoOfLinksBetnVerticesAtThisLevel = Binomial.choose2(noOfVerticesAtThisLevel);
			double actualNoOfLinksBetnVerticesAtThisLevel = Math.rint(q*maxNoOfLinksBetnVerticesAtThisLevel);
			
			//System.out.println("Max no of links betn vertices at same level: " + maxNoOfLinksBetnVerticesAtThisLevel);
			//System.out.println("Actual no of links betn vertices at same level: " + actualNoOfLinksBetnVerticesAtThisLevel);
			
			int counter = (new Double(actualNoOfLinksBetnVerticesAtThisLevel)).intValue();
			while(counter>0)
			{
				int source = random3.nextInt(noOfVerticesAtThisLevel);
				int dest = random4.nextInt(noOfVerticesAtThisLevel);
				
				Integer sourceVertex = verticesAtThisLevel.elementAt(source);
				Integer destVertex = verticesAtThisLevel.elementAt(dest);
			
			
				if(!sourceVertex.equals(destVertex) && !newEdgeIDsBetweenLevels.contains(sourceVertex+"_"+destVertex) && !newEdgeIDsBetweenLevels.contains(destVertex+"_"+sourceVertex) )
					{
					
						AffiliationDataBean affiliationDataBean = new AffiliationDataBean();
						affiliationDataBean.setEntitySource(sourceVertex.intValue());
						affiliationDataBean.setEntityDestination(destVertex.intValue());
						affiliationDataBean.setEntityWeight(1);
						String newEdge = sourceVertex+"_"+destVertex;
						
						levelAffiliations.add(affiliationDataBean);
						newEdgeIDsBetweenLevels.add(newEdge);
						counter = counter-1;
					
					}
			}
			//System.out.println("newedfeidbetweenlevels"+newEdgeIDsBetweenLevels);
			
		}
		else
		{
			System.out.println("peerLevel is the leaf level!");
		}
		//System.out.println("No of new edges are: " + newEdgeIDsBetnLevels.size());
		//System.out.println("New edges: " + newEdgeIDsBetnLevels);
	   // System.out.println("New edges between peers at same level: " + newEdgeIDsBetweenLevels);
		affiliations.addAll(levelAffiliations);
		
		// End: Generate affiliations due to connections between peers at same level
		
		// Start: Add random affiliations
		Vector<String> affiliationAliases = new Vector<String>();
		//double randomAffiliationProbability = randAffProb;
		int howManyAffiliationsBeforeRandom = affiliations.size();
		double howManyRandomAffiliationsToAdd = randomAffiliationProbability * howManyAffiliationsBeforeRandom;
		//System.out.println("howManyAffiliationsBeforeRandom: " + howManyAffiliationsBeforeRandom);
		//System.out.println("howManyRandomAffiliationsToAdd: " + howManyRandomAffiliationsToAdd);
		
		Vector<AffiliationDataBean> randomAffilations = new Vector<AffiliationDataBean>();
		
		// Store affiliations for easy lookup to avod duplicates
		for(int i=0; i<affiliations.size(); i++)
		{
			AffiliationDataBean affiliationDataBean = affiliations.elementAt(i);
			int source = affiliationDataBean.getEntitySource();
			int dest = affiliationDataBean.getEntityDestination();
			String affiliationAlias = source + "_" + dest;
			affiliationAliases.add(affiliationAlias);
			//System.out.println("afflication"+affiliationAliases);
		}
		
		
		int counter = (new Double(Math.rint(howManyRandomAffiliationsToAdd)).intValue());
		//System.out.println("counter: " + counter);
		while(counter > 0)
		{
			Random random5 = new Random();
			Random random6 = new Random();
			
			int source = random5.nextInt(vertexIDs.size());
			int dest = random6.nextInt(vertexIDs.size());
			
			String sourceNew = vertexIDs.elementAt(source);
			String destNew = vertexIDs.elementAt(dest);
			
			String affiliationAliasNew = sourceNew + "_" + destNew;
			String affiliationAliasNewRev = destNew + "_" + sourceNew;
			
			
			if( !(sourceNew.equalsIgnoreCase(destNew)) && !(affiliationAliases.contains(affiliationAliasNew)) && !(affiliationAliases.contains(affiliationAliasNewRev)) )
			{
				AffiliationDataBean affiliationDataBean = new AffiliationDataBean();
				affiliationDataBean.setEntitySource(new Integer(sourceNew));
				affiliationDataBean.setEntityDestination(new Integer(destNew));
				affiliationDataBean.setEntityWeight(1);
				//System.out.println("sourceNew, destNew: " + sourceNew + " " + destNew);
				//System.out.println("affiliationAliasNew " + affiliationAliasNew);
				randomAffilations.add(affiliationDataBean);
				affiliationAliases.add(affiliationAliasNew);
				
				//counter = counter-1;
			}
			counter = counter-1;
		}
		
		//System.out.println("randomAffilations: " + randomAffilations);
		//System.out.println("randomAffilations.size(): " + randomAffilations.size());
		// End: Add random affiliations
		
		
		affiliations.addAll(randomAffilations);
		
		return affiliations;
	}
	
}
