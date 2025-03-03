document.addEventListener("DOMContentLoaded", () => {
    const workshopsContainer = document.querySelector(".row.row-cols-1"); // Conteneur des ateliers
    const sortBySelect = document.getElementById("sortByWorkshops");
    const searchBar = document.getElementById("searchBar");

    let workshops = Array.from(document.querySelectorAll(".row.row-cols-1 .col")); // Liste des ateliers

    // Fonction pour parser une date au format "'Le' dd MMMM yyyy 'à' HH:mm"
    function parseDateTime(dateTimeString) {
        const [datePart, timePart] = dateTimeString.replace("Le", "").trim().split("à");
        const [day, month, year] = datePart.trim().split(" ");
        const [hours, minutes] = timePart.trim().split(":");
        const months = ["janvier", "février", "mars", "avril", "mai", "juin", "juillet", "août", "septembre", "octobre", "novembre", "décembre"];
        const monthIndex = months.indexOf(month.toLowerCase());
        return new Date(year, monthIndex, parseInt(day), parseInt(hours), parseInt(minutes));
    }

    // Fonction pour trier les ateliers
    function sortWorkshops(criteria) {
        workshops.sort((a, b) => {
            const nameA = a.querySelector(".card-title").innerText.toLowerCase();
            const nameB = b.querySelector(".card-title").innerText.toLowerCase();
            const priceA = parseFloat(a.querySelector(".card-text b").innerText.replace("€", "").trim());
            const priceB = parseFloat(b.querySelector(".card-text b").innerText.replace("€", "").trim());
            const dateA = parseDateTime(a.querySelector(".card-text.text-muted").innerText.trim());
            const dateB = parseDateTime(b.querySelector(".card-text.text-muted").innerText.trim());

            if (criteria === "name") {
                return nameA.localeCompare(nameB);
            } else if (criteria === "workshopDateTime") {
                return dateA - dateB; // Tri par ordre chronologique
            } else if (criteria === "priceDesc") {
                return priceB - priceA;
            } else if (criteria === "priceAsc") {
                return priceA - priceB;
            }
        });

        renderWorkshops();
    }

    // Fonction pour filtrer les ateliers
    function filterWorkshops(query) {
        const lowerCaseQuery = query.toLowerCase();
        workshops.forEach(workshop => {
            const name = workshop.querySelector(".card-title").innerText.toLowerCase();
            workshop.style.display = name.includes(query) ? "" : "none";
        });
    }

    // Fonction pour rendre les ateliers triés
    function renderWorkshops() {
        workshopsContainer.innerHTML = ""; // Vide le conteneur
        workshops.forEach(workshop => workshopsContainer.appendChild(workshop)); // Ajoute les ateliers triés
    }

    // Événement pour le tri
    sortBySelect.addEventListener("change", (e) => {
        const selectedOption = e.target.value;
        sortWorkshops(selectedOption);
    });

    // Événement pour la barre de recherche
    searchBar.addEventListener("input", (e) => {
        const query = e.target.value;
        filterWorkshops(query);
    });

    // Initialisation des ateliers au chargement de la page
    renderWorkshops();
});
