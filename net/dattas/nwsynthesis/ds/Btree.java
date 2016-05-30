package net.dattas.nwsynthesis.ds;

import java.util.Vector;
import java.util.Scanner;
import java.util.Random;
import java.util.*;


public class Btree{
	
	public Node root;
	public int branch;
	public int height;
	public int max_key;

	public static void main(String args[]){
	
			System.out.println("enter the branch and height of the tree :");
			Scanner sc = new Scanner(System.in);
     			int branch = sc.nextInt();
			int height = sc.nextInt();
			Btree tree = new Btree(height,branch);
			System.out.println("�nside main of data structure\n Node keys are....\n");
			// add testing statements here 
			tree.traverse(tree.root);			
}

	
	public Btree(int height,int branch){
			
			this.branch=branch;
			this.height=height;
			max_key=1;
			this.root=new Node(0,branch,1);
			createTree(0,this.root);
			
}
	//just to print all nodes in the tree in the depth first technique
	public void traverse(Node focusNode){
	
			System.out.print(focusNode.key+" -> ");
			try{
				if(!isALeaf(focusNode))
				{
					for(int i=0;i<branch;i++)
					{
						traverse(focusNode.child.get(i));
					}
				}
			}catch(NullPointerException e){
			}
			return;
}
	public void getNodeKeys(Node focusNode, Vector<Integer> result){
		
		result.add(focusNode.key);
		if(!isALeaf(focusNode))
		{
			for(int i=0;i<branch;i++)
			{
				getNodeKeys(focusNode.child.elementAt(i),result);
			}
		}
}
	public void createTree(int level,Node focusNode){
		
			if(level<height)
			{
				for(int i=0;i<branch;i++)
				{
					//calculating the key of the ith child of the focusNode using key of focusNode
					int startingKeyOfThisLevel = 1;
					int startingKeyOfParentLevel = 1;
					for(int j=0;j<level;j++)
					{
						startingKeyOfParentLevel += Math.pow(branch,j);
					}
					startingKeyOfThisLevel = startingKeyOfParentLevel + new Double(Math.pow(branch,level)).intValue();
					int key = startingKeyOfThisLevel + i + (branch * ( focusNode.key - startingKeyOfParentLevel)) ;
					if(key>=max_key)
					{
						max_key=key;
					}
					focusNode.child.setElementAt(new Node(level+1,branch,key),i);
					createTree(level+1,focusNode.child.get(i));
				}
			}
			return;
}
	public boolean isALeaf(Node focusNode){

			for(int i=0;i<branch;i++)
			{
				if(focusNode.child.get(i)!=null)
				{
					return false;
				}
			}
			return true;	
}
	public boolean deleteable(Node focusNode){

			//we check whether all the child of a node are leaf and then only we can delete all the child of this particular node
			if(isALeaf(focusNode))
				return false;
			for(int i=0;i<branch;i++)
			{
				if(!(isALeaf(focusNode.child.get(i))))
					return false;
			}
			return true;
}
	
	public void nodesAtSameLevel(int level,Node focusNode,Vector<Integer> result){
		
		if(focusNode.key==level)
		{
				result.add(focusNode.key);
				return;
		}
		try{
			if(!isALeaf(focusNode))
			{
				for(int i=0;i<branch;i++)
				{
					nodesAtSameLevel(level, focusNode.child.get(i), result);
				}
			}
		}catch(NullPointerException e){
		}
		return;
	}

	public void deleteNode(Node root){
			
			Node focusNode=root;
			if(deleteable(focusNode))
			{
				for(int i=0;i<branch;i++)
				{
					focusNode.child.setElementAt(null,i);
				}
				return;
			}
			else
			{
				int i=0;
				while(isALeaf(focusNode.child.get(i)))
				{
					i++;
				}
				deleteNode(focusNode.child.get(i));
				return;
			}
}

	public void addNode(Node root){			
			
			Node focusNode=root;
			try{

			if(isALeaf(focusNode))
			{
				for(int i=0;i<branch;i++)
				{
					int startingKeyOfThisLevel = 1;
					int startingKeyOfParentLevel = 1;
					for(int j=0;j<focusNode.level;j++)
					{
						startingKeyOfParentLevel += Math.pow(branch,j);
					}
					startingKeyOfThisLevel = startingKeyOfParentLevel + new Double(Math.pow(branch,focusNode.level)).intValue();
					int key = startingKeyOfThisLevel + i + (branch * ( focusNode.key - startingKeyOfParentLevel)) ;
					if(max_key<startingKeyOfThisLevel)
					{
						++height;
					}
					if(max_key<=key)
					{
						max_key=key;
					}
					focusNode.child.setElementAt(new Node(focusNode.level+1,branch,key),i);
				}
				return;
			}
			else
			{
				Random rand = new Random();
				int x=rand.nextInt(branch);
				addNode(focusNode.child.get(x));
				return;
			}	}catch(NullPointerException e){}
										
}	
					
					
}				
			