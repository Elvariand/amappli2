document.addEventListener('DOMContentLoaded', () => {
    if (latitude && longitude) {
        const map = L.map('dynamic-map').setView([latitude, longitude], 13);

        setTimeout(() => {
            map.invalidateSize(); // Corrige les dimensions de la carte
        }, 0); // Exécute après le rendu initial

        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            maxZoom: 19,
        }).addTo(map);

        L.marker([latitude, longitude]).addTo(map)
            .bindPopup('Nous sommes ici')
            .openPopup();
    } else {
        console.error('Les coordonnées latitude et longitude ne sont pas définies.');
    }
});
