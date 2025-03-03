document
	.addEventListener(
		"DOMContentLoaded",
		function() {
			const workshopDateTime = document
				.getElementById("workshopDateTime");
			const minParticipantsInput = document
				.getElementById("minimumParticipants");
			const maxParticipantsInput = document
				.getElementById("maximumParticipants");

			// Initialiser la date et heure minimale pour le champ "datetime-local"
			function initializeMinDateTime() {
				const now = new Date();
				now.setDate(now.getDate() + 1); // Ajouter 1 jour pour interdire la sélection du jour actuel ou passé
				now.setHours(9, 0, 0, 0); // Réinitialiser à 09:00 par défaut

				const year = now.getFullYear();
				const month = String(now.getMonth() + 1)
					.padStart(2, "0");
				const day = String(now.getDate()).padStart(2,
					"0");
				const hours = String(now.getHours()).padStart(
					2, "0");
				const minutes = String(now.getMinutes())
					.padStart(2, "0");

				const defaultDateTime = `${year}-${month}-${day}T${hours}:${minutes}`;
				workshopDateTime.setAttribute("min",
					minDateTime);
				workshopDateTime.value = minDateTime;
			}

			// Validation du nombre minimum et maximum de participants
			document
				.querySelector("form")
				.addEventListener(
					"submit",
					function(e) {
						const minParticipants = parseInt(
							minParticipantsInput.value,
							10);
						const maxParticipants = parseInt(
							maxParticipantsInput.value,
							10);

						if (isNaN(minParticipants)
							|| isNaN(maxParticipants)) {
							e.preventDefault();
							alert("Veuillez renseigner des valeurs valides pour les participants.");
							return;
						}

						if (maxParticipants < minParticipants) {
							e.preventDefault();
							alert("Le nombre de participants maximum doit être supérieur ou égal au nombre de participants minimum.");
							return;
						}

						// Validation de la date/heure de l'atelier
						const selectedDateTime = new Date(
							workshopDateTime.value);
						const now = new Date();
						now.setDate(now.getDate() + 1); // Date minimale : demain
						now.setHours(9, 0, 0, 0); // Heure minimale : 09:00

						if (selectedDateTime < now) {
							e.preventDefault();
							alert("La date et l'heure de l'atelier doivent être au moins demain à partir de 09:00.");
							return;
						}
					});

			// Initialiser la date minimale pour le champ "datetime-local"
			if (workshopDateTime) {
				initializeMinDateTime();
			}
		});