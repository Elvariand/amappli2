document.addEventListener("DOMContentLoaded", function () {
   

    // Form Nav
    let allFormParts = document.querySelectorAll('.form-part');
    let currentIndex = 0;
    let progressBar = document.getElementById("the-progress-bar");
    let progressBarIncrements = 100/allFormParts.length;

    let goBackButton = document.getElementById("go-back-button");
    let continueButton = document.getElementById("continue-button");
    let submitButton = document.getElementById("submit-button");

    submitButton.style.display = "none";

    continueButton.addEventListener("click", function() {
        if (currentIndex < allFormParts.length - 1) {
                currentIndex++;
                updateVisibility();
				if(currentIndex == 12) {
					setTheme1();
				}
                if(currentIndex == allFormParts.length - 1) {
                    computeOptionChoice();
                }
                else if ( currentIndex == allFormParts.length - 2){
                    removeOptionChoice();
                }
            }
    });

    goBackButton.addEventListener("click", function() {
        if (currentIndex > 0){
            currentIndex--;
            updateVisibility();
				if(currentIndex == 10) {
					setTheme1();
				}
            if(currentIndex == allFormParts.length - 1) {
                computeOptionChoice(); 
            }
            else if ( currentIndex == allFormParts.length - 2){
                removeOptionChoice();
            }
        }
    });

    submitButton.addEventListener("click", function (event) {
        // Prevent form submission if invalid
        if (!checkFormValidity()) {
            event.preventDefault();
            // Show all form parts
            allFormParts.forEach(part => part.style.display = "block");
            goBackButton.style.display = "none";
            // Scroll to the first invalid field
            const firstInvalidField = document.querySelector('.form-part :invalid');
            if (firstInvalidField) {
                firstInvalidField.scrollIntoView({ behavior: "smooth", block: "center" });
                firstInvalidField.focus();
            }
        }
    });

    updateVisibility();

    // console.log("errorpresent value in external script:", window.errorpresent);
    console.log("value of errorpresent");
    console.log(errorpresent);
    if(errorpresent == "true"){
        console.log("errors are present");
        allFormParts.forEach(part => part.style.display = "block");
        goBackButton.style.display = "none";
    }

    function updateVisibility() {
        //update progress bar
        progressBar.style.width = progressBarIncrements*currentIndex+"%";
        progressBar.ariaValueNow = progressBarIncrements*currentIndex;
        
        // show current form part
        allFormParts.forEach((part, index) => {
        if (index === currentIndex) {
            part.style.display = "block"; // Show the current part
        } else {
            part.style.display = "none"; // Hide all other parts
        }
        });

        // hide go back or continue button if first or last part of the form
        if (currentIndex == 0){
            goBackButton.style.display = "none";
            // change the text of the continue button for the first one
            continueButton.innerText = "C'est parti!";
        }
        else{
            goBackButton.style.display = "block";
            continueButton.innerText = "Continuer";
        }

        if (currentIndex == allFormParts.length-1){ //last page of the form
            continueButton.style.display = "none";
            submitButton.style.display = "block";
        }
        else{
            continueButton.style.display = "block";
            submitButton.style.display = "none";
        }

    };

    // Showing the sample URL
    let tenancyAliasInput = document.getElementById('input-tenancy-alias');
    let tenancyUrlExampleSpan = document.getElementById('amap-url-example');

    tenancyAliasInput.addEventListener('input', function() {
        tenancyUrlExampleSpan.innerText = tenancyAliasInput.value;
    });

    // Choosing options based on quizz
    
    function computeOptionChoice() {
        let productsMarketPlaceResponse = document.querySelector('input[name="question-1"]:checked').value;
        let eventsResponse = document.querySelector('input[name="question-3"]:checked').value;
        let onlinePaymentResponse = document.querySelector('input[name="question-4"]:checked').value;
        let supplierAccountResponse = document.querySelector('input[name="question-5"]:checked').value;
        let customRolesResponse = document.querySelector('input[name="question-6"]:checked').value;
        let lightDarkResponse =  document.querySelector('input[name="question-8"]:checked').value;

        let recommandation= "Cette option vous permettra d'avoir toutes les fonctionnalités souhaitées!";
        let option3button = document.getElementById("option-3-label");
        let option2button = document.getElementById("option-2-label");
        let option1button = document.getElementById("option-1-label");

        if(lightDarkResponse == "true" ||  customRolesResponse == "true"){
            option3button.setAttribute("data-bs-container","body");
            option3button.setAttribute("data-bs-toggle","popover");
            option3button.setAttribute("data-bs-placement","top");
            option3button.setAttribute("data-bs-content",recommandation);

   /*         option1button.removeAttribute("data-bs-container");
            option1button.removeAttribute("data-bs-toggle");
            option1button.removeAttribute("data-bs-content");

            option2button.removeAttribute("data-bs-container");
            option2button.removeAttribute("data-bs-toggle");
            option2button.removeAttribute("data-bs-content");*/
        }
        else if(productsMarketPlaceResponse == "true" || onlinePaymentResponse == "true" || supplierAccountResponse == "true" || eventsResponse == "true") {
            option2button.setAttribute("data-bs-container","body");
            option2button.setAttribute("data-bs-toggle","popover");
            option2button.setAttribute("data-bs-placement","top");
            option2button.setAttribute("data-bs-content",recommandation);

  /*          option1button.removeAttribute("data-bs-container");
            option1button.removeAttribute("data-bs-toggle");
            option1button.removeAttribute("data-bs-content");

            option3button.removeAttribute("data-bs-container");
            option3button.removeAttribute("data-bs-toggle");
            option3button.removeAttribute("data-bs-content");*/
        }
        else {
            option1button.setAttribute("data-bs-container","body");
            option1button.setAttribute("data-bs-toggle","popover");
            option1button.setAttribute("data-bs-placement","top");
            option1button.setAttribute("data-bs-content",recommandation);

     /*       option3button.removeAttribute("data-bs-container");
            option3button.removeAttribute("data-bs-toggle");
            option3button.removeAttribute("data-bs-content");

            option2button.removeAttribute("data-bs-container");
            option2button.removeAttribute("data-bs-toggle");
            option2button.removeAttribute("data-bs-content");*/
        }

         // Enable Popover
        const popoverTriggerList = document.querySelectorAll('[data-bs-toggle="popover"]');
        const popoverList = [...popoverTriggerList].map(popoverTriggerEl => new bootstrap.Popover(popoverTriggerEl).show());
    };

    function removeOptionChoice(){
		
        let popover = document.querySelectorAll(".popover-body").parentElement;
		
		if(popover[0] != null) {
			popover.forEach(pops => {
				pops.remove();
			})
		}
				popover.remove();

		
        // [option1button, option2button, option3button].forEach(popover => {popover.dispose()});

        // option1button.removeAttribute("data-bs-container");
        // option1button.removeAttribute("data-bs-toggle");
        // option1button.removeAttribute("data-bs-content");
        // option1button.removeAttribute("data-bs-placement");

        // option2button.removeAttribute("data-bs-container");
        // option2button.removeAttribute("data-bs-toggle");
        // option2button.removeAttribute("data-bs-content");
        // option2button.removeAttribute("data-bs-placement");

        // option3button.removeAttribute("data-bs-container");
        // option3button.removeAttribute("data-bs-toggle");
        // option3button.removeAttribute("data-bs-content");
        // option3button.removeAttribute("data-bs-placement");
    };
    // Input Control

    let inputTenancyName = document.getElementById('input-tenancy-name');
    let tenancyNameErrorElmt = document.getElementById("input-tenancy-name-error");
    // let inputTenancyAlias = document.getElementById('input-tenancy-alias');
    // let inputTenancySlogan = document.getElementById('input-tenancy-slogan');
    // let inputMembershipPrice = document.getElementById('input-membership-price');
    // let dayOfWeekSelect = document.getElementById('dayOfWeek');
    // let startHour = document.getElementById('startHour');
    // let endHour = document.getElementById('endHour');
    // let inputAddressLine1 = document.getElementById('address-line1');
    // let inputAddressLine2 = document.getElementById('address-line2');
    // let inputAddressPostcode = document.getElementById('address-postcode');
    // let inputAddressCity = document.getElementById('address-city');
    // let inputLogo = document.getElementById('input-logo');
    // let inputValueName0 = document.getElementById('input-value-name-0');
    // let inputValueDescription0 = document.getElementById('input-value-description-0');
    // let inputValueFile0 = document.getElementById('input-value-file-0');
    // let inputValueName1 = document.getElementById('input-value-name-1');
    // let inputValueDescription1 = document.getElementById('input-value-description-1');
    // let inputValueFile1 = document.getElementById('input-value-file-1'); 
    // let inputValueName2 = document.getElementById('input-value-name-2');
    // let inputValueDescription2 = document.getElementById('input-value-description-2');
    // let inputValueFile2 = document.getElementById('input-value-file-2'); 
    // let inputHpTitle = document.getElementById('input-hp-title');
    // let inputHpText = document.getElementById('input-hp-text');
    // let inputHpImage = document.getElementById('input-hp-image');

    function checkTenancyNameLength() {
        console.log("hey!")
        let tenancyNameValue = inputTenancyName.value;
        if(tenancyNameValue.length > 100){
            tenancyNameErrorElmt.textContent = "Le nom de votre AMAP ne doit pas faire plus de 100 charactères";
        }
        else if(tenancyNameValue.length < 4){
            tenancyNameErrorElmt.textContent = "Le nom de votre AMAP doit faire au moins 4 charactères";
        }
        else {
            tenancyNameErrorElmt.textContent = "";
        }
    }

    inputTenancyName.addEventListener('input', checkTenancyNameLength);

    function checkFormValidity() {
        const form = document.querySelector('form'); // Adjust selector if your form has a specific ID or class
        return form.checkValidity(); // This checks the validity of all form fields
    }


    document.querySelectorAll(".palette-img").forEach( img => {
        img.addEventListener('click', function () {

        document.body.classList.remove('theme-1');
        document.body.classList.remove('theme-2');
        document.body.classList.remove('theme-3');
        document.body.classList.remove('theme-4');
        document.body.classList.remove('theme-5');
        document.body.classList.remove('theme-6');

        let src=img.getAttribute('src');
        switch (src.substring(src.length-5,src.length-4)) {
            case "2":
                document.body.classList.add('theme-2');
                styleMapboxLight = "mapbox://styles/tiroirmorgane/cm52ch9kt00ch01r13h25gm01";
                styleMapboxDark = "mapbox://styles/tiroirmorgane/cm52cuoxd00c101sa4gdl1bye";
                break;
            case "3":
                document.body.classList.add('theme-3');
                styleMapboxLight = "mapbox://styles/tiroirmorgane/cm52cizgb00c201s94zpr3c3w";
                styleMapboxDark = "mapbox://styles/tiroirmorgane/cm52cw61a00cl01qohw2ifo3i";
                break;
            case "4":
                document.body.classList.add('theme-4');
                styleMapboxLight = "mapbox://styles/tiroirmorgane/cm52cjqtu002z01sa8typ15f6";
                styleMapboxDark = "mapbox://styles/tiroirmorgane/cm52cxc4o00cm01qo3eei2j9x";
                break;
            case "5":
                document.body.classList.add('theme-5');
                styleMapboxLight = "mapbox://styles/tiroirmorgane/cm52claak00dh01qyb016bm90";
                styleMapboxDark = "mapbox://styles/tiroirmorgane/cm52cyg8t00c301s95paxgbyb";
                break;
            case "6":
                document.body.classList.add('theme-6');
                styleMapboxLight = "mapbox://styles/tiroirmorgane/cm52dmxt500c401s9ckayancx";
                styleMapboxDark = "mapbox://styles/tiroirmorgane/cm52czmoi00ci01r1g3po4h2b";
                break;
            default:
                document.body.classList.add('theme-1');
                styleMapboxLight = "mapbox://styles/tiroirmorgane/cm4sw37wr001301s12frm2l2y";
                styleMapboxDark = "mapbox://styles/tiroirmorgane/cm52cqefg003101sa878udky6";
                break;
        }

        if (document.body.classList.contains('light')) {
            map.setStyle(styleMapboxLight);
        } else {
            map.setStyle(styleMapboxDark);
        }
            
        });
    });
	
	function setTheme1() {
		if(!document.body.classList.contains('theme-1')) {
		document.body.classList.remove('theme-2');
		document.body.classList.remove('theme-3');
		document.body.classList.remove('theme-4');
		document.body.classList.remove('theme-5');
		document.body.classList.remove('theme-6');
		document.body.classList.add('theme-1');
		styleMapboxLight = "mapbox://styles/tiroirmorgane/cm4sw37wr001301s12frm2l2y";
		styleMapboxDark = "mapbox://styles/tiroirmorgane/cm52cqefg003101sa878udky6";
        if (document.body.classList.contains('light')) {
            map.setStyle(styleMapboxLight);
        } else {
            map.setStyle(styleMapboxDark);
        }
		}
	}

});
