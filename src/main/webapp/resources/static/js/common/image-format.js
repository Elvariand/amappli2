		document.addEventListener("DOMContentLoaded", function () {
    const imageInput = document.getElementById('imageInput');
    const imagePreview = document.getElementById('imagePreview');

    if (imageInput && imagePreview) {
        imageInput.addEventListener('change', function () {
            const file = imageInput.files[0];
            if (file) {
                const reader = new FileReader();

                // Charger l'image et mettre à jour la preview
                reader.onload = function (e) {
                    imagePreview.src = e.target.result;
                };

                // Lire le contenu du fichier
                reader.readAsDataURL(file);
            }
        });
    }
});