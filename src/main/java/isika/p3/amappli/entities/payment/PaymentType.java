package isika.p3.amappli.entities.payment;

public enum PaymentType {

    card("Carte bleue"),
    cash("Espèces"),
    check("Chèque");
	
	private String displayName;

	private PaymentType(String displayName) {
		this.displayName = displayName;
	}
	
	public String getDisplayName() {
		return displayName;
	}
}
