package isika.p3.amappli.entities.contract;

public enum ContractWeight {

	SMALL("Petit (2-4kg)"), AVERAGE("Moyen (5-8kg)"), BIG("Grand (9kg et +)");

	private final String displayName;

	ContractWeight(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}
}
