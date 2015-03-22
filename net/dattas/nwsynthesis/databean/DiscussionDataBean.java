package net.dattas.nwsynthesis.databean;

import java.sql.*;

import net.dattas.nwsynthesis.databean.ParentDataBean;

public class DiscussionDataBean extends ParentDataBean {
	
	private int workitemid;
	private int creatorid;
	private String comment;
	private Timestamp createdon;
	public int getWorkitemid() {
		return workitemid;
	}
	public void setWorkitemid(int workitemid) {
		this.workitemid = workitemid;
	}
	public int getCreatorid() {
		return creatorid;
	}
	public void setCreatorid(int creatorid) {
		this.creatorid = creatorid;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Timestamp getCreatedon() {
		return createdon;
	}
	public void setCreatedon(Timestamp createdon) {
		this.createdon = createdon;
	}

}
