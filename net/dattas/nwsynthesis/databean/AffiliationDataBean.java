package net.dattas.nwsynthesis.databean;

import net.dattas.nwsynthesis.databean.ParentDataBean;

public class AffiliationDataBean extends ParentDataBean {
	

	
	private int entitySource = 0;
	private int entityDestination = 0;
	private int entityWeight = 0;
	
	private String categorySource = "";
	private String categoryDestination = "";
	private int categoryWeight = 0;
	
	
	
	public int getEntitySource() {
		return entitySource;
	}
	public void setEntitySource(int sourceWI) {
		this.entitySource = sourceWI;
	}
	public int getEntityDestination() {
		return entityDestination;
	}
	public void setEntityDestination(int destWI) {
		this.entityDestination = destWI;
	}
	public int getEntityWeight() {
		return entityWeight;
	}
	public void setEntityWeight(int weight) {
		this.entityWeight = weight;
	}
	public String getCategorySource() {
		return categorySource;
	}
	public void setCategorySource(String categorySource) {
		this.categorySource = categorySource;
	}
	public String getCategoryDestination() {
		return categoryDestination;
	}
	public void setCategoryDestination(String categoryDestination) {
		this.categoryDestination = categoryDestination;
	}
	public int getCategoryWeight() {
		return categoryWeight;
	}
	public void setCategoryWeight(int categoryWeight) {
		this.categoryWeight = categoryWeight;
	}

}
