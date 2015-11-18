package net.dattas.nwsynthesis.util;

import java.util.*;
import java.util.Vector;
import java.lang.*;

import net.dattas.nwsynthesis.databean.*;
import net.dattas.nwsynthesis.ds.Btree;
import net.dattas.nwsynthesis.ds.Btree;

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
	
	private void traverse(Node focusNode, Vector<AffiliationDataBean> affiliations, Hashtable<Integer, Vector<Integer>> parentLeavesSet, int branch){
		 
		if(!(isALeaf(focusNode))){
	
					Vector<Integer> leavesUnderSameParent = new Vector<Integer>();
					for(int i=0;i<branch;i++)
					{
						
							AffiliationDataBean affiliationDataBean = new AffiliationDataBean();
							affiliationDataBean.setEntitySource(focusNode.key);
							affiliationDataBean.setEntityDestination(focusNode.child.elementAt(i).key);	
							affiliationDataBean.setEntityWeight(1);
							affiliations.add(affiliationDataBean);
							if(isALeaf(focusNode.chhild.elementAt(i))){
										leavesUnderSameParent.add(focusNode.elementAt(i).key);			
					}
					}
					parentLeavesSet.put(focusNode.key,leavesUnderSameParent);
					for(int i=0;i<branch;i++)
					{ 					
							traverse(focusNode.elementAt(i), affiliations, parentLeavesSet, branch);			
					}

		}
}
	// 1. Pass parameter p = probability of leaf nodes affiliating; check extent of edge growth
	// 2. Pass parameter q = probability of peers affiliating, at parameter l = level of peers; 
	// check for extent of diameter shrinkage
	
	public Vector<AffiliationDataBean> generateAffiliations(double p, int peerLevel, double q, double randomAffiliationProbability, Btree tree)
	{
		Vector<AffiliationDataBean> affiliations = new Vector<AffiliationDataBean>();
		Hashtable<Integer,Vector<Integer>> parentLeavesSet = new Hashtable<Integer,Vector<Integer>>();	
		traverse(tree.root,affiliations,parentLeavesSet,tree.branch);
		
		Vector<AffiliationDataBean> leafAffiliations = new Vector<AffiliationDataBean>();
		Random random1 = new Random();
		Random random2 = new Random();
		Vector<String> newEdgeIDsBetnLeaves = new Vector<String>();
		Vector<Integer> leavesUnderSameParent = new Vector<Integer>();
		Enumeration<Vector<Integer>> enumOfSet = parentLeavesSet.elements();
		while(enumOfSet.hasMoreElements())
		{
			leavesUnderSameParent = enumOfSet.nextElement();
			int cap = leavesUnderSameParent.size();
			double maxNoOfLinksBetnLeafVertices = Binomial.choose2(cap);
			double actualNoOfLinksBetnLeafVertices = Math.rint(p*maxNoOfLinksBetnLeafVertices);
			int counter = (new Double(actualNoOfLinksBetnLeafVertices)).intValue();
			while(counter>0)
			{
				int source = random1.nextInt(cap);
				int dest = random2.nextInt(cap);
				Integer sourceVertex = leavesUnderSameParent.elementAt(source).intValue();
				Integer destVertex = leavesUnderSameParent.elementAt(dest).intValue();
				if(!sourceVertex.equals(destVertex) && !newEdgeIDsBetnLeaves.contains(sourceVertex+"_"+destVertex) && !newEdgeIDsBetnLeaves.contains(destVertex+"_"+sourceVertex) )
				{		
						AffiliationDataBean affiliationDataBean = new AffiliationDataBean();
						affiliationDataBean.setEntitySource(sourceVertex.intValue());
						affiliationDataBean.setEntityDestination(destVertex.intValue());
						affiliationDataBean.setEntityWeight(1);
						String newEdge = sourceVertex+"_"+destVertex;
						leafAffiliations.add(affiliationDataBean);
						newEdgeIDsBetnLeaves.add(newEdge);
						--counter;
				}	
			}
		}
		
	  	affiliations.addAll(leafAffiliations);
		// End: Generate affiliations due to connections between leaf nodes under same parent
		
		
		// Start: Generate affiliations due to connections between peers at same level
		Vector<AffiliationDataBean> levelAffiliations = new Vector<AffiliationDataBean>();
		Vector<String> newEdgeIDsBetweenLevels = new Vector<String>();
		Random random3 = new Random();
		Random random4 = new Random();
		if(peerLevel < tree.height)
		{
			Vector<Integer> verticesAtThisLevel = new Vector<Integer>();
			tree.nodesAtSameLevel(peerLevel,tree.root,verticesAtThisLevel); 
			int noOfVerticesAtThisLevel = verticesAtThisLevel.size();
			double maxNoOfLinksBetnVerticesAtThisLevel = Binomial.choose2(noOfVerticesAtThisLevel);
			double actualNoOfLinksBetnVerticesAtThisLevel = Math.rint(q*maxNoOfLinksBetnVerticesAtThisLevel);
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
					--counter;
				}	
			}
		//	System.out.println("newedfeidbetweenlevels"+newEdgeIDsBetweenLevels);
			
		}
		else
		{
			System.out.println("peerLevel is the leaf level! or is more than the height of the tree... ");
		}
		affiliations.addAll(levelAffiliations);
		// End: Generate affiliations due to connections between peers at same level
		
		// Start: Add random affiliations
		Vector<String> affiliationLookUpTable = new Vector<String>();
		int howManyAffiliationsBeforeRandom = affiliations.size();
		double howManyRandomAffiliationsToAdd = randomAffiliationProbability * howManyAffiliationsBeforeRandom;
		Vector<AffiliationDataBean> randomAffilations = new Vector<AffiliationDataBean>();
		
		// Store affiliations for easy lookup to avod duplicates
		
		for(int i=0; i<affiliations.size(); i++)
		{
			AffiliationDataBean affiliationDataBean = affiliations.elementAt(i);
			int source = affiliationDataBean.getEntitySource();
			int dest = affiliationDataBean.getEntityDestination();
			String affiliationAlias = source + "_" + dest;
			affiliationLookUpTable.add(affiliationAlias);
		}
		
		
		int counter = (new Double(Math.rint(howManyRandomAffiliationsToAdd)).intValue());
		Vector<Integer> nodeKeys = new Vector<Integer>();
		tree.getNodeKeys(tree.root,nodeKeys);
		Random random5 = new Random();
		Random random6 = new Random();
		while(counter > 0)
		{
			int source = random5.nextInt(nodeKeys.size());
			int dest = random6.nextInt(nodeKeys.size());
			
			int sourceNew = nodeKeys.elementAt(source).intValue();
			int destNew = nodeKeys.elementAt(dest).intValue();
			
			String affiliationAliasNew = sourceNew + "_" + destNew;
			String affiliationAliasNewRev = destNew + "_" + sourceNew;
			
			
			if( !(sourceNew==destNew) && !(affiliationLookUpTable.contains(affiliationAliasNew)) && !(affiliationLookUpTable.contains(affiliationAliasNewRev)) )
			{
				AffiliationDataBean affiliationDataBean = new AffiliationDataBean();
				affiliationDataBean.setEntitySource(new Integer(sourceNew));
				affiliationDataBean.setEntityDestination(new Integer(destNew));
				affiliationDataBean.setEntityWeight(1);
				randomAffilations.add(affiliationDataBean);
				affiliationLookUpTable.add(affiliationAliasNew);
				--counter;
			}
		}

		// End: Add random affiliations
		
		
		affiliations.addAll(randomAffilations);
		
		return affiliations;
}
	
	public Vector<AffiliationDataBean> generateAffiliations_levels(double p, int peerLevel, double q, double randomAffiliationProbability, Btree tree)
	{
		Vector<AffiliationDataBean> affiliations = new Vector<AffiliationDataBean>();
		Hashtable<Integer,Vector<Integer>> parentLeavesSet = new Hashtable<Integer,Vector<Integer>>();	
		traverse(tree.root,affiliations,parentLeavesSet,tree.branch);
		
		Vector<AffiliationDataBean> leafAffiliations = new Vector<AffiliationDataBean>();
		Random random1 = new Random();
		Random random2 = new Random();
		Vector<String> newEdgeIDsBetnLeaves = new Vector<String>();
		Vector<Integer> leavesUnderSameParent = new Vector<Integer>();
		Enumeration<Vector<Integer>> enumOfSet = parentLeavesSet.elements();
		while(enumOfSet.hasMoreElements())
		{
			leavesUnderSameParent = enumOfSet.nextElement();
			int cap = leavesUnderSameParent.size();
			double maxNoOfLinksBetnLeafVertices = Binomial.choose2(cap);
			double actualNoOfLinksBetnLeafVertices = Math.rint(p*maxNoOfLinksBetnLeafVertices);
			int counter = (new Double(actualNoOfLinksBetnLeafVertices)).intValue();
			while(counter>0)
			{
				int source = random1.nextInt(cap);
				int dest = random2.nextInt(cap);
				Integer sourceVertex = leavesUnderSameParent.elementAt(source).intValue();
				Integer destVertex = leavesUnderSameParent.elementAt(dest).intValue();
				if(!sourceVertex.equals(destVertex) && !newEdgeIDsBetnLeaves.contains(sourceVertex+"_"+destVertex) && !newEdgeIDsBetnLeaves.contains(destVertex+"_"+sourceVertex) )
				{		
						AffiliationDataBean affiliationDataBean = new AffiliationDataBean();
						affiliationDataBean.setEntitySource(sourceVertex.intValue());
						affiliationDataBean.setEntityDestination(destVertex.intValue());
						affiliationDataBean.setEntityWeight(1);
						String newEdge = sourceVertex+"_"+destVertex;
						leafAffiliations.add(affiliationDataBean);
						newEdgeIDsBetnLeaves.add(newEdge);
						--counter;
				}	
				
			}
		}
		
	  	affiliations.addAll(leafAffiliations);
		// End: Generate affiliations due to connections between leaf nodes under same parent
		
		
		// Start: Generate affiliations due to connections between peers at same level
		
		Vector<AffiliationDataBean> levelAffiliations = new Vector<AffiliationDataBean>();
		Vector<String> newEdgeIDsBetweenLevels = new Vector<String>();
		Random random3 = new Random();
		Random random4 = new Random();
		if(peerLevel < tree.height)
		{
			Double linearlyVaryingProb=q;
			while(peerLevel < tree.height)
			{
				Vector<Integer> verticesAtThisLevel = new Vector<Integer>();
				tree.nodesAtSameLevel(peerLevel,tree.root,verticesAtThisLevel); 
				int noOfVerticesAtThisLevel = verticesAtThisLevel.size();
				double maxNoOfLinksBetnVerticesAtThisLevel = Binomial.choose2(noOfVerticesAtThisLevel);
				double actualNoOfLinksBetnVerticesAtThisLevel = Math.rint(linearlyVaryingProb*maxNoOfLinksBetnVerticesAtThisLevel);
				int counter = (new Double(actualNoOfLinksBetnVerticesAtThisLevel)).intValue();
				int count=peerLevel;
				linearlyVaryingProb=q/++count;
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
						--counter;
					}
			}
			peerLevel++;
		}	
		}
		else
		{
			System.out.println("peerLevel is the leaf level!");
		}
		affiliations.addAll(levelAffiliations);
			
		// End: Generate affiliations due to connections between peers at same level
		
		// Start: Add random affiliations
		Vector<String> affiliationLookUpTable = new Vector<String>();
		int howManyAffiliationsBeforeRandom = affiliations.size();
		double howManyRandomAffiliationsToAdd = randomAffiliationProbability * howManyAffiliationsBeforeRandom;
		Vector<AffiliationDataBean> randomAffilations = new Vector<AffiliationDataBean>();
		
		// Store affiliations for easy lookup to avod duplicates
				
		for(int i=0; i<affiliations.size(); i++)
		{
			AffiliationDataBean affiliationDataBean = affiliations.elementAt(i);
			int source = affiliationDataBean.getEntitySource();
			int dest = affiliationDataBean.getEntityDestination();
			String affiliationAlias = source + "_" + dest;
			affiliationLookUpTable.add(affiliationAlias);
		}		
		int counter = (new Double(Math.rint(howManyRandomAffiliationsToAdd)).intValue());
		Vector<Integer> nodeKeys = new Vector<Integer>();
		tree.getNodeKeys(tree.root,nodeKeys);
		Random random5 = new Random();
		Random random6 = new Random();
		while(counter > 0)
		{
			int source = random5.nextInt(nodeKeys.size());
			int dest = random6.nextInt(nodeKeys.size());
		
			int sourceNew = nodeKeys.elementAt(source).intValue();
			int destNew = nodeKeys.elementAt(dest).intValue();
					
			String affiliationAliasNew = sourceNew + "_" + destNew;
			String affiliationAliasNewRev = destNew + "_" + sourceNew;
						
			if( !(sourceNew==destNew) && !(affiliationLookUpTable.contains(affiliationAliasNew)) && !(affiliationLookUpTable.contains(affiliationAliasNewRev)) )
			{
				AffiliationDataBean affiliationDataBean = new AffiliationDataBean();
				affiliationDataBean.setEntitySource(new Integer(sourceNew));
				affiliationDataBean.setEntityDestination(new Integer(destNew));
				affiliationDataBean.setEntityWeight(1);
				randomAffilations.add(affiliationDataBean);
				affiliationLookUpTable.add(affiliationAliasNew);
				--counter;
			}
		}
		// End: Add random affiliations
				
		affiliations.addAll(randomAffilations);
		
		return affiliations;
	}

	
}
