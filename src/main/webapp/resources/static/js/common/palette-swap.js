let switchers = document.querySelectorAll(".swap-palette");

switchers.forEach(switcher => {
    switcher.addEventListener("click", function () {


        body.classList.remove('theme-1');
        body.classList.remove('theme-2');
        body.classList.remove('theme-3');
        body.classList.remove('theme-4');
        body.classList.remove('theme-5');
        body.classList.remove('theme-6');

        switch (switcher.innerText) {
            case "2":
                body.classList.add('theme-2');
                styleMapboxLight = "mapbox://styles/tiroirmorgane/cm52ch9kt00ch01r13h25gm01";
                styleMapboxDark = "mapbox://styles/tiroirmorgane/cm52cuoxd00c101sa4gdl1bye";
                break;
            case "3":
                body.classList.add('theme-3');
                styleMapboxLight = "mapbox://styles/tiroirmorgane/cm52cizgb00c201s94zpr3c3w";
                styleMapboxDark = "mapbox://styles/tiroirmorgane/cm52cw61a00cl01qohw2ifo3i";
                break;
            case "4":
                body.classList.add('theme-4');
                styleMapboxLight = "mapbox://styles/tiroirmorgane/cm52cjqtu002z01sa8typ15f6";
                styleMapboxDark = "mapbox://styles/tiroirmorgane/cm52cxc4o00cm01qo3eei2j9x";
                break;
            case "5":
                body.classList.add('theme-5');
                styleMapboxLight = "mapbox://styles/tiroirmorgane/cm52claak00dh01qyb016bm90";
                styleMapboxDark = "mapbox://styles/tiroirmorgane/cm52cyg8t00c301s95paxgbyb";
                break;
            case "6":
                body.classList.add('theme-6');
                styleMapboxLight = "mapbox://styles/tiroirmorgane/cm52dmxt500c401s9ckayancx";
                styleMapboxDark = "mapbox://styles/tiroirmorgane/cm52czmoi00ci01r1g3po4h2b";
                break;
            default:
                body.classList.add('theme-1');
                styleMapboxLight = "mapbox://styles/tiroirmorgane/cm4sw37wr001301s12frm2l2y";
                styleMapboxDark = "mapbox://styles/tiroirmorgane/cm52cqefg003101sa878udky6";
                break;
        }

        if (body.classList.contains('light')) {
            map.setStyle(styleMapboxLight);
        } else {
            map.setStyle(styleMapboxDark);
        }
    });
});