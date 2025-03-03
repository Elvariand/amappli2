document.addEventListener("DOMContentLoaded", function () {
    let formPart1 = document.getElementById("form-part-1");
    let formPart2 = document.getElementById("form-part-2");
    let connectPart = document.getElementById("connect");
    let signUpButton = document.getElementById("sign-up-button");
    let progressBar = document.getElementById("the-progress-bar");

    formPart2.style.display = "none";

    signUpButton.addEventListener("click", function() {
        continueSignUp();
    });

    function continueSignUp() {
        connectPart.style.display = "none";
        formPart1.style.display = "none";
        formPart2.style.display = "block";
        progressBar.style.width = "50%";
        progressBar.ariaValueNow = "50";
    }


});