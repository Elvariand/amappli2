document.addEventListener("DOMContentLoaded", function () {

     // Showing the sample URL
     let tenancyAliasInput = document.getElementById('input-tenancyAlias');
     let tenancyUrlExampleSpan = document.getElementById('amap-url-example');
 
     tenancyAliasInput.addEventListener('input', function() {
         tenancyUrlExampleSpan.innerText = tenancyAliasInput.value;
     });


    // Previewing the logo
    let inputFile = document.getElementById('input-tenancyLogo');
    let previewImage = document.getElementById('preview-logo');
    let cancelButton = document.getElementById('cancel-preview-logo');

    // Store the original logo
    let originalImageSrc = previewImage.src;

    let temporaryImageUrl = null; // for uploaded file

    inputFile.addEventListener('change', function (event) {
        let file = event.target.files[0];
        if (file) {
            // erase previously upload img if necessary
            if (temporaryImageUrl) {
                URL.revokeObjectURL(temporaryImageUrl);
            }

            // Create a new object URL for the uploaded file
            temporaryImageUrl = URL.createObjectURL(file);

            // Update the preview image
            previewImage.src = temporaryImageUrl;

            // Show the cancel button
            cancelButton.style.display = 'block';
        }
    });

    cancelButton.addEventListener('click', function () {
        // Put back the original logo
        previewImage.src = originalImageSrc;

        // Cleanup
        inputFile.value = '';

        cancelButton.style.display = 'none';

        if (temporaryImageUrl) {
            URL.revokeObjectURL(temporaryImageUrl);
            temporaryImageUrl = null;
        }
    });

    // Preselecting the current font and color
    selectRadioButtons(fontValue,colorValue);

    function selectRadioButtons(fontValue, colorValue) {
    // Select the radio button for font
        let fontRadio = document.querySelector(`input[name="fontChoice"][value="${fontValue}"]`);
        if (fontRadio) {
            fontRadio.checked = true;
        } else {
            console.warn(`Font value "${fontValue}" not found!`);
        }

        // Select the radio button for color palette
        let colorRadio = document.querySelector(`input[name="colorPalette"][value="${colorValue}"]`);
        if (colorRadio) {
            colorRadio.checked = true;
        } else {
            console.warn(`Color palette value "${colorValue}" not found!`);
        }
    }

    let formSections = document.getElementById("content-form-sections");

    formSections.addEventListener("change", function (event) {
        if (event.target.classList.contains("file-input")) {
            const input = event.target;
            const file = input.files[0]; // Get the selected file
    
            if (file) {
                const reader = new FileReader();
    
                reader.onload = function (e) {
                    // Locate the corresponding preview image
                    const img = input.parentElement.querySelector(".preview-img");
                    if (img) {
                        img.src = e.target.result; // Update the image preview
                    }
                };
    
                // Read the file as a data URL
                reader.readAsDataURL(file);
            }
        }
    });
    
    let addMoreButton = document.getElementById("add-more-button");
    let currentIndex = document.querySelectorAll(".content-form-section").length;

    addMoreButton.addEventListener("click", function () {
        // Create a new section
        const newSection = document.createElement("div");
        newSection.className = "form-section";
        newSection.dataset.index = currentIndex;

        newSection.innerHTML = `
            <div class="mb-3">
                <label for="input-title-${currentIndex}" class="form-label">Titre</label>
                <input class="form-control" 
                       id="input-title-${currentIndex}" 
                       placeholder="titre" 
                       name="contents[${currentIndex}].contentTitle" />
            </div>
            <div class="row g-3 align-items-start">
                <div class="col-11 col-md-5">
                    <label for="input-text-${currentIndex}" class="form-label">Contenu</label>
                    <textarea class="form-control" rows="5"
                              id="input-text-${currentIndex}" 
                              placeholder="contenu" 
                              name="contents[${currentIndex}].contentText"></textarea>
                </div>
                <div class="col-11 col-md-5 d-flex flex-column justify-content-center">
                    <input class="form-control mb-3 file-input" type="file" 
                           id="input-img-${currentIndex}" 
                           name="contents[${currentIndex}].image"
                           accept="image/png,image/jpeg,image/svg">
                    <img class="img-fluid file-input preview-img" style="height: 100%;" src="" alt="votre image"/>
                </div>
            </div>
            <hr>
        `;

        // Append the new section to the form
        formSections.appendChild(newSection);

        // Increment the index for the next section
        currentIndex++;
    });

    // Image preview for content blocks and values
    const fileInputs = document.querySelectorAll(".file-input");
    
    fileInputs.forEach(input => {
        input.addEventListener("change", function () {
            const file = this.files[0]; // Get the selected file
            if (file) {
                const reader = new FileReader();

                reader.onload = function (e) {
                    // Locate the sibling <img> element
                    const img = input.parentElement.querySelector(".preview-img");
                    if (img) {
                        img.src = e.target.result; // Update the image preview
                    }
                };

                // Read the file as a data URL
                reader.readAsDataURL(file);
            }
        });
    });
});