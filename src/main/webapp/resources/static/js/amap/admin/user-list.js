document.addEventListener("DOMContentLoaded", () => {
	const searchBar = document.getElementById("searchBar");
	const sortBy = document.getElementById("sortBy");
	const tableBody = document.querySelector("tbody");

	// Fonction pour trier les lignes
	function sortRows(criteria) {
		const rows = Array.from(tableBody.querySelectorAll("tr"));
		rows.sort((a, b) => {
			const valueA = getValue(a, criteria);
			const valueB = getValue(b, criteria);

			if (criteria === "creditAsc") {
				return parseFloat(valueA) - parseFloat(valueB);
			} else if (criteria === "creditDesc") {
				return parseFloat(valueB) - parseFloat(valueA);
			} else {
				return valueA.localeCompare(valueB, "fr-FR");
			}
		});

		rows.forEach(row => tableBody.appendChild(row));
	}

	function getValue(row, criteria) {
		if (criteria === "name") return row.cells[0].innerText.trim();
		if (criteria === "role") return row.cells[5].innerText.trim();
		if (criteria === "creditAsc" || criteria === "creditDesc") return row.cells[3].innerText.replace("€", "").trim();
		return "";
	}

	// Fonction pour filtrer les lignes
	function filterRows(query) {
		const rows = tableBody.querySelectorAll("tr");
		rows.forEach(row => {
			const name = row.cells[0].innerText.toLowerCase();
			const producer = row.cells[5].innerText.toLowerCase();
			if (name.includes(query) || producer.includes(query)) {
				row.style.display = "";
			} else {
				row.style.display = "none";
			}
		});
	}

	// Événements
	sortBy.addEventListener("change", () => {
		sortRows(sortBy.value);
	});

	searchBar.addEventListener("input", () => {
		const query = searchBar.value.toLowerCase();
		filterRows(query);
	});
});