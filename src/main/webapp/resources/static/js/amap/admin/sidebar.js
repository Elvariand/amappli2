let btnCollapse = document.getElementById("button-collapse");
let btnCollapse2 = document.getElementById("button-collapse-2");
let paddedDiv = document.getElementById("padded-div");

btnCollapse.addEventListener("click", function() {
    if (btnCollapse.classList.contains('collapsed')) {
        paddedDiv.classList.remove('p-4');
        paddedDiv.classList.add('p-0');
    } else {
        paddedDiv.classList.remove('p-0');
        paddedDiv.classList.add('p-4');
    }
});

btnCollapse2.addEventListener("click", function() {
    if (btnCollapse2.classList.contains('collapsed')) {
        paddedDiv.classList.remove('p-4');
        paddedDiv.classList.add('p-0');
    } else {
        paddedDiv.classList.remove('p-0');
        paddedDiv.classList.add('p-4');
    }
});