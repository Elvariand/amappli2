document.addEventListener("DOMContentLoaded", () => {
    const productsContainer = document.querySelector(".row.row-cols-2"); // Conteneur des produits
    const sortBySelect = document.getElementById("sortByProducts");
    const searchBar = document.getElementById("searchBar");

    let products = Array.from(document.querySelectorAll(".row.row-cols-2 .col")); // Liste des produits

    // Fonction pour parser une date au format "dd MMMM yyyy"
    function parseDate(dateString) {
        const [day, month, year] = dateString.split(" ");
        const months = ["janvier", "février", "mars", "avril", "mai", "juin", "juillet", "août", "septembre", "octobre", "novembre", "décembre"];
        const monthIndex = months.indexOf(month.toLowerCase());
        return new Date(year, monthIndex, parseInt(day));
    }

    // Fonction pour trier les produits
    function sortProducts(criteria) {
        products.sort((a, b) => {
            const nameA = a.querySelector(".card-title").innerText.toLowerCase();
            const nameB = b.querySelector(".card-title").innerText.toLowerCase();
            const priceA = parseFloat(a.querySelector(".card-text b").innerText.replace("€", "").trim());
            const priceB = parseFloat(b.querySelector(".card-text b").innerText.replace("€", "").trim());
            const dateA = parseDate(a.querySelector(".card-text.text-muted").innerText.replace("DLC:", "").trim());
            const dateB = parseDate(b.querySelector(".card-text.text-muted").innerText.replace("DLC:", "").trim());

            if (criteria === "name") {
                return nameA.localeCompare(nameB);
            } else if (criteria === "expirationDate") {
                return dateA - dateB; // Tri par ordre chronologique
            } else if (criteria === "priceDesc") {
                return priceB - priceA;
            } else if (criteria === "priceAsc") {
                return priceA - priceB;
            }
        });

        renderProducts();
    }

    // Fonction pour filtrer les produits
    function filterProducts(query) {
        const lowerCaseQuery = query.toLowerCase();
        products.forEach(product => {
            const name = product.querySelector(".card-title").innerText.toLowerCase();
            product.style.display = name.includes(lowerCaseQuery) ? "" : "none";
        });
    }

    // Fonction pour rendre les produits triés
    function renderProducts() {
        productsContainer.innerHTML = ""; // Vide le conteneur
        products.forEach(product => productsContainer.appendChild(product)); // Ajoute les produits triés
    }

    // Événement pour le tri
    sortBySelect.addEventListener("change", (e) => {
        const selectedOption = e.target.value;
        sortProducts(selectedOption);
    });

    // Événement pour la barre de recherche
    searchBar.addEventListener("input", (e) => {
        const query = e.target.value;
        filterProducts(query);
    });

    // Initialisation des produits au chargement de la page
    renderProducts();
});
