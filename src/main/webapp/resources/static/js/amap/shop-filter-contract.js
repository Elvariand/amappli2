document.addEventListener("DOMContentLoaded", () => {
    const contractsContainer = document.querySelector(".row.row-cols-1"); // Conteneur des contrats
    const sortBySelect = document.getElementById("sortByContracts");
    const searchBar = document.getElementById("searchBar");

    let contracts = Array.from(document.querySelectorAll(".row.row-cols-1 .col")); // Liste des contrats

    // Fonction pour trier les contrats
    function sortContracts(criteria) {
        contracts.sort((a, b) => {
            const nameA = a.querySelector(".card-title").innerText.toLowerCase();
            const nameB = b.querySelector(".card-title").innerText.toLowerCase();
            const priceA = parseFloat(a.querySelector(".card-text b").innerText.replace("€", "").trim());
            const priceB = parseFloat(b.querySelector(".card-text b").innerText.replace("€", "").trim());

            if (criteria === "name") {
                return nameA.localeCompare(nameB);
            } else if (criteria === "priceDesc") {
                return priceB - priceA;
            } else if (criteria === "priceAsc") {
                return priceA - priceB;
            }
        });

        renderContracts();
    }

    // Fonction pour filtrer les contrats
    function filterContracts(query) {
        const lowerCaseQuery = query.toLowerCase();
        contracts.forEach(contract => {
            const name = contract.querySelector(".card-title").innerText.toLowerCase();
            contract.style.display = name.includes(lowerCaseQuery) ? "" : "none";
        });
    }

    // Fonction pour rendre les contrats triés
    function renderContracts() {
        contractsContainer.innerHTML = ""; // Vide le conteneur
        contracts.forEach(contract => contractsContainer.appendChild(contract)); // Ajoute les contrats triés
    }

    // Événement pour le tri
    sortBySelect.addEventListener("change", (e) => {
        const selectedOption = e.target.value;
        sortContracts(selectedOption);
    });

    // Événement pour la barre de recherche
    searchBar.addEventListener("input", (e) => {
        const query = e.target.value;
        filterContracts(query);
    });

    // Initialisation des contrats au chargement de la page
    renderContracts();
});
