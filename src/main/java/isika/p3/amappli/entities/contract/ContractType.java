package isika.p3.amappli.entities.contract;

public enum ContractType {

	FRUITS_CONTRACT("Panier de fruits"), VEGETABLES_CONTRACT("Panier de légumes"), MIX_CONTRACT("Panier mixte");

	private final String displayName;

	ContractType(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}
}
