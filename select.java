import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

import net.dattas.nwsynthesis.control.NetworkModelingController;

import sd.nwmodels.GenerateModel;


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
		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_hhmmssSSSSSS_a");
	    
	    String timeStamp = sdf.format(cal.getTime());
		if(i == 1)
		{
			GenerateModel gm = new GenerateModel();
			GenerateModel.start();
			
		}
		else if(i == 2)
		{
			NetworkModelingController nm = new NetworkModelingController();
			NetworkModelingController.manualRun(timeStamp);
		} 
		else
		{
			System.out.println("Error! Try again");
		}
	}

}
