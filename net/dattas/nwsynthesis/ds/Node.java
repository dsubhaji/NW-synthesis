package net.dattas.nwsynthesis.ds;

import java.util.*;
import java.lang.*;

public class Node{
	
	public int level;
	public int key;
	public Vector<Node> child;

	public static void main(String[] args){

	return ;
}

	public Node(){
}

	Node(int level,int branch,int key){
		
			this.level=level;
			this.key=key;
			child = new Vector<Node>(branch);
			for(int i=0;i<branch;i++)
				child.add(i,null);
}
}