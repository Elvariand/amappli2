package isika.p3.amappli.entities.contract;

public enum DeliveryDay {

	MONDAY("Lundi"),
    TUESDAY("Mardi"),
    WEDNESDAY("Mercredi"),
    THURSDAY("Jeudi"),
    FRIDAY("Vendredi"),
    SATURDAY("Samedi"),
    SUNDAY("Dimanche");
	
	private final String displayName;
	
	DeliveryDay(String displayName) {
        this.displayName = displayName;
    }
	
    public String getDisplayName() {
        return displayName;
    }
}
