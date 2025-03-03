package isika.p3.amappli.entities.order;

public enum OrderStatus {
	
	PENDING("Non récupérée"),
	IN_PROGRESS("En cours"),
	DONE("Récupérée");
	
	private String displayName;
	
	OrderStatus(String displayName){
		this.displayName = displayName;
	}
	
	public String getDisplayName() {
		return displayName;
	}

}
