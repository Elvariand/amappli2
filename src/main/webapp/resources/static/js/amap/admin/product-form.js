document
	.addEventListener(
		"DOMContentLoaded",
		function() {
			const form = document.querySelector("form");
			const fabricationDateInput = document
				.getElementById("fabricationDate");
			const expirationDateInput = document
				.getElementById("expirationDate");

			form
				.addEventListener(
					"submit",
					function(event) {
						const fabricationDate = new Date(
							fabricationDateInput.value);
						const expirationDate = new Date(
							expirationDateInput.value);

						// Vérifier si expirationDate est bien après fabricationDate
						if (expirationDate <= fabricationDate) {
							event.preventDefault(); // Empêche la soumission
							expirationDateInput.classList
								.add("is-invalid");
							const errorMessage = expirationDateInput.nextElementSibling;
							if (errorMessage
								&& errorMessage.classList
									.contains("invalid-feedback")) {
								errorMessage.textContent = "La date d'expiration doit être après la date de fabrication.";
							}
						} else {
							expirationDateInput.classList
								.remove("is-invalid");
							const errorMessage = expirationDateInput.nextElementSibling;
							if (errorMessage
								&& errorMessage.classList
									.contains("invalid-feedback")) {
								errorMessage.textContent = "";
							}
						}
					});
		});