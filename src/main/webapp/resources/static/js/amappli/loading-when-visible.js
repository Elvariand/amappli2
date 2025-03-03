const losBoxes = document.querySelectorAll(".cardboard");
// const elGallery = document.querySelector(".gallery");

let options = {
    threshold: 0.25
};

let observer = new IntersectionObserver((entries) => {
    entries.forEach(({ target, intersectionRatio, boundingClientRect }) => {
        target.dataset.visible = intersectionRatio > 0.25;

        if (target.nodeName !== "IMG") {

            let viewportPosition = 1;
            if (intersectionRatio > 0.24) {
                viewportPosition = 0;
            } else if (boundingClientRect.y < 0) {
                viewportPosition = -1;
            }

            target.dataset.viewportPosition = viewportPosition;
            target.style.setProperty("--ix-ratio", intersectionRatio);
        }
    });
}, options);

losBoxes.forEach((elBox) => {
    observer.observe(elBox);
});
