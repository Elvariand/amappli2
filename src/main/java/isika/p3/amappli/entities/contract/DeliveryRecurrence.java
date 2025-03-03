package isika.p3.amappli.entities.contract;

public enum DeliveryRecurrence {
	
	WEEKLY("Hebdomadaire"),
    BIMONTHLY("Bimensuel"),
    MONTHLY("Mensuel");
	
	private final String displayName;
	
	DeliveryRecurrence(String displayName) {
        this.displayName = displayName;
    }
	
	public String getDisplayName() {
        return displayName;
    }
   

}
