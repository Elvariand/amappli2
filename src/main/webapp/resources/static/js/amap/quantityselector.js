document.addEventListener("DOMContentLoaded", () => {
    const decreaseButton = document.getElementById("decreaseQuantity");
    const increaseButton = document.getElementById("increaseQuantity");
    const quantitySpan = document.getElementById("currentQuantity");
    const quantityInput = document.getElementById("quantity");

    let currentQuantity = 1; // Valeur initiale

    decreaseButton.addEventListener("click", () => {
        if (currentQuantity > 1) {
            currentQuantity--;
            quantitySpan.textContent = currentQuantity;
            quantityInput.value = currentQuantity; // Mise à jour du champ hidden
        }
    });

    increaseButton.addEventListener("click", () => {
        currentQuantity++;
        quantitySpan.textContent = currentQuantity;
        quantityInput.value = currentQuantity; // Mise à jour du champ hidden
    });
});