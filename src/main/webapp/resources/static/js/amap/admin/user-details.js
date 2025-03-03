let box = document.getElementById("role-box-Producteur");
let section = document.getElementById("supplier-section");

document.body.addEventListener("load", function() {
	
box = document.getElementById("role-box-Producteur");
section = document.getElementById("supplier-section");
});

if (box.checked == true) {
    section.classList.remove('d-none');
}

box.addEventListener("click", function() {

    if (box.checked == true) {
        section.classList.remove('d-none');
    } else {
        section.classList.add('d-none');
    }
});