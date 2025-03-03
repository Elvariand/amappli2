const contractTypeDisplayNames = {
	"FRUIT": "Panier de fruits",
	"VEGETABLE": "Panier de légumes",
	"MIXED": "Panier mixte"
};

const contractWeightDisplayNames = {
	"SMALL": "Petit",
	"AVERAGE": "Moyen",
	"BIG": "Grand"
};

function confirmUpdate() {
	const form = document.forms['contractForm'];
	let message = "Confirmez-vous les modifications suivantes ?\n\n";
	message += "Nom du contrat : " + form['contractName'].value + "\n";
	message += "Type : " + contractTypeDisplayNames[form['contractType'].value] + "\n";
	message += "Description : " + form['contractDescription'].value + "\n";
	message += "Poids : " + contractWeightDisplayNames[form['contractWeight'].value] + "\n";
	message += "Prix : " + form['contractPrice'].value + "\n";
	message += "Date de début : " + form['startDate'].value + "\n";
	message += "Date de fin : " + form['endDate'].value + "\n";

	return confirm(message);
}

document.addEventListener("DOMContentLoaded", function() {
	const form = document.forms['contractForm'];
	if (form) {
		form.addEventListener('submit', function(event) {
			if (!confirmUpdate()) {
				event.preventDefault();
			}
		});
	}
});