// mo access token for mapbox 
mapboxgl.accessToken = 'pk.eyJ1IjoidGlyb2lybW9yZ2FuZSIsImEiOiJja2cyOXRqYWkwcDdsMnRwaWtzYWR3Zjc2In0.yp4z_Ui7Ukj7I4p6au750g';

//initial values
let zoom = 14;
let userLatitude = 42.1880896; // corse
let userLongitude = 9.0684138;
const fallbackStyle = 'mapbox://styles/tiroirmorgane/cm4pjeh6h007k01r0fhs20pkd'; //dark theme as fallback (when no tenancy)
//checks if styleMabox is defined, if not shows the fallback style (if we don't define it in the jsp file like in amapplis jsp for eg)
var mapStyle = typeof styleMapboxLight !== 'undefined' ? document.body.classList.contains("dark") ? styleMapboxDark : styleMapboxLight : fallbackStyle;
var mapLatitude = (typeof latitude !== 'undefined' && latitude !== "") ? parseFloat(latitude) : userLatitude;
var mapLongitude = (typeof longitude !== 'undefined' && longitude !== "") ? parseFloat(longitude) : userLongitude;


// create map
var map = new mapboxgl.Map({
    container: 'map', 
    style: mapStyle, 
    center: [mapLongitude, mapLatitude], // coordinates (longitude, latitude)
    zoom: zoom 
});

// disable all interactions with map
map.scrollZoom.disable();
map.boxZoom.disable();
map.dragPan.disable();
map.dragRotate.disable();
map.keyboard.disable();
map.doubleClickZoom.disable();
map.touchZoomRotate.disable();

let copyrights = document.getElementsByClassName("mapboxgl-ctrl-bottom-right")[0];
let watermark = document.getElementsByClassName("mapboxgl-ctrl-bottom-left")[0];

copyrights.remove();
watermark.remove();

/* TENTATIVE de mise à jour de la carte en temps réel -> faudrait plutôt enregistrer les coordonnées dans la database pour une meilleure performance

// get coordinates from city and postCode of tenancy
async function fetchCoordinates(tenancyCity, tenancyPostCode) {
    const url = `https://nominatim.openstreetmap.org/search?format=json&addressdetails=1&limit=1&q=${encodeURIComponent(tenancyCity)} ${encodeURIComponent(tenancyPostCode)}`;

    try {
        const response = await fetch(url, {
            headers: { "User-Agent": "amappli/1.0 (no-contact)" } 
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const data = await response.json();
        if (data.length > 0) {
            const { lat, lon } = data[0];
            return { userLatitude: parseFloat(lat), userLongitude: parseFloat(lon) };
        } else {
            throw new Error("No results found for the specified city and postcode");
        }
    } catch (error) {
        console.error("Error fetching coordinates:", error);
        return null; 
    }
}

// Update map dynamically based on tenancyCity and tenancyPostCode
async function updateMapWithCoordinates(tenancyCity, tenancyPostCode) {
    const coordinates = await fetchCoordinates(tenancyCity, tenancyPostCode);

    if (coordinates) {
        const { userLatitude, userLongitude } = coordinates;

        // Update map center
        map.setCenter([userLongitude, userLatitude]);
        console.log(`Map updated to: Latitude ${userLatitude}, Longitude ${userLongitude}`);
    } else {
        console.warn("Unable to update map due to invalid coordinates.");
    }
}

document.addEventListener("DOMContentLoaded", () => {
    updateMapWithCoordinates(tenancyCity, tenancyPostCode);
});
*/

