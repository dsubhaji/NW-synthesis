package sd.nwmodels;

import java.util.*;

public class GenerateModel {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		start();
	}
	public static void start()
	{		
		System.out.println("In Main method");
		
		ProbabilisticNetworkModel pm = new ProbabilisticNetworkModel();
		
		PajekFileGenerator pj = new PajekFileGenerator();
		Scanner in = new Scanner(System.in);
		 
	      System.out.println("Enter the total number of time steps T : ");
		      pm.setToTalNoOfTimeSteps(in.nextInt());
		      
		      System.out.println("Enter the value of m1 : ");
		      pm.setM1(in.nextDouble());
	
		      System.out.println("Enter the value of c1 : ");
		      pm.setC1(in.nextDouble());
		      
		     // System.out.println("Enter the value of m2 : ");
		      //pm.setM2(in.nextDouble());
	
		     // System.out.println("Enter the value of c2 : ");
		      //pm.setC2(in.nextDouble());
		      
		      System.out.println("Enter the number of members in a team : ");
		      pm.setNoOfMembersInTeam(in.nextInt());
	      
	     //double initialProbability = 0;
	      
	      System.out.println("Total time steps are " + pm.getToTalNoOfTimeSteps() );
	      for(int i=1; i<=pm.getToTalNoOfTimeSteps();i++)
	      {
	    	 // System.out.println("loping over time steps ");
	    	  
	    	  int noOfTeams = (int)(pm.getM1() * i + pm.getC1());
	    	  pm.setNoOfTeams(noOfTeams);
	    	  
	    	  //System.out.println("generating vertices ");
	    	  Vector<Integer> vertexIDs = pm.generateVertices(3);
	    	  
	    	  //System.out.println("generating levels ");
	    	  Vector<Vector<Integer>> levels = pm.generateLevels();
	    	  
	    	  double p;
	    	  
	    	  do
	    	  {
	    		  System.out.println("Enter the value of probability(between 0 and 1) for time step "+ i +" : ");
		    	  p = in.nextDouble();
	    	  }while(p<0 || p >1);

	    	  //double p = pm.getM2()*i + pm.getC2();
	    	  
	    	  /*
	    	  if(i==1)
	    	  {
	    		  initialProbability = p;
	    	  }
	    	  
	    	  p = (p-initialProbability)/p;
	    	  
	    	  */
	    	  System.out.println("Generating affiliations with probability : " + p);
	    	  Vector<Affiliations> afflns= pm.generateAffiliations(levels, p,vertexIDs);
	    	  
	    	  //System.out.println("affiliations size is " + afflns.size());
	    	  
	    	  pj.formatPajekInput(afflns, vertexIDs, i);
	    	  
	      }
	      
 

	}

}
