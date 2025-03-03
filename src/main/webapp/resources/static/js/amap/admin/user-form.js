const submit = document.getElementById("submit-button");
const admin = document.getElementById("role-box-ADMIN")

submit.addEventListener("click", function(event) {
    if(admin.checked) {
        if (!window.confirm("Vous êtes sur le point d'enregistrer un nouvel utilisateur avec les droits d'administrateur, êtes vous sûr ?")) {
			event.preventDefault();
		}
    }
});