package sd.nwmodels;

public class Affiliations {
	
	public int getEntitySource() {
		return entitySource;
	}
	public void setEntitySource(int entitySource) {
		this.entitySource = entitySource;
	}
	public int getEntityDestination() {
		return entityDestination;
	}
	public void setEntityDestination(int entityDestination) {
		this.entityDestination = entityDestination;
	}
	public int getEntityWeight() {
		return entityWeight;
	}
	public void setEntityWeight(int entityWeight) {
		this.entityWeight = entityWeight;
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
	private int entitySource = 0;
	private int entityDestination = 0;
	private int entityWeight = 0;
	
	private String categorySource = "";
	private String categoryDestination = "";
	private int categoryWeight = 0;
	

}
