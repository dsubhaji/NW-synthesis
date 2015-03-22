package net.dattas.nwsynthesis.databean;

import java.sql.*;

import net.dattas.nwsynthesis.databean.ParentDataBean;

public class WorkitemDataBean extends ParentDataBean {
	
	private int workitemid;
	private String workitemname;
	private String type;
	private Timestamp createdon;
	private int ownedby;
	public int getWorkitemid() {
		return workitemid;
	}
	public void setWorkitemid(int workitemid) {
		this.workitemid = workitemid;
	}
	public String getWorkitemname() {
		return workitemname;
	}
	public void setWorkitemname(String workitemname) {
		this.workitemname = workitemname;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Timestamp getCreatedon() {
		return createdon;
	}
	public void setCreatedon(Timestamp createdon) {
		this.createdon = createdon;
	}
	public int getOwnedby() {
		return ownedby;
	}
	public void setOwnedby(int ownedby) {
		this.ownedby = ownedby;
	}

}
