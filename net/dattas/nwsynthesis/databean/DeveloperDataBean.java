package net.dattas.nwsynthesis.databean;

import net.dattas.nwsynthesis.databean.ParentDataBean;

public class DeveloperDataBean extends ParentDataBean{
	private int developerID = 0;
	private String developerName = "";
	
	public int getDeveloperID() {
		return developerID;
	}
	public void setDeveloperID(int developerID) {
		this.developerID = developerID;
	}
	public String getDeveloperName() {
		return developerName;
	}
	public void setDeveloperName(String developerName) {
		this.developerName = developerName;
	}

}
