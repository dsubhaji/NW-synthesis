import net.dattas.nwsynthesis.control.*;

import java.util.Scanner;

import net.dattas.nwsynthesis.databean.AffiliationDataBean;
import net.dattas.nwsynthesis.ioformat.*;
import net.dattas.nwsynthesis.util.*;
import sd.nwmodels.*;

public class select {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Choose(Press 1 or 2):");
		System.out.println("1.Network Growth Model");
		System.out.println("2.Network Connection Model");
		Scanner in = new Scanner(System.in);
		String a = in.nextLine();
		int i = (new Integer(a)).intValue();
		
		if(i == 1)
		{
			GenerateModel gm = new GenerateModel();
			gm.start();
			
		}
		else if(i == 2)
		{
			NetworkModelingController nm = new NetworkModelingController();
			nm.manualRun();
		} 
		else
		{
			System.out.println("Error! Try again");
		}
	}

}
