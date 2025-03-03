let keysPressed = {};

document.addEventListener('keydown', (event) => {
    keysPressed[event.key] = true;
    
    if (keysPressed['Alt'] && event.key == 't') {
        let inputs = document.querySelectorAll("input");
        let form = document.querySelector("form");
        

        switch (form.getAttribute("action")) {
            case "/Amappli/login":
                inputs.forEach(input => {
                    if (input.getAttribute("id") === "email") {	
                        if (input.value === "amelie.rousseau@example.com") {
							input.value = "sophie.legrand@example.com";
                        } else if (input.value === "sophie.legrand@example.com") {
							input.value = "laura.lemoine@example6.com";
                        } else if (input.value === "laura.lemoine@example6.com") {
							input.value = "lucas.martin@example6.com";
                        } else if (input.value ==="lucas.martin@example6.com") {
							input.value = "marie.durand@example1.com";
                        } else {
                            input.value = "amelie.rousseau@example.com";
                        }
                    }
                    if (input.getAttribute("id") === "motDePasse") {
                        input.value = "AMAPamap11@";
                    }
                });
                break;
            case "/Amappli/amap/amapdes5-1/admin/contracts/add":
            case "/Amappli/amap/amapdes5/admin/contracts/add":
            case "/Amappli/amap/biocoli/admin/contracts/add":
            case "/Amappli/amap/agrinov/admin/contracts/add":
            case "/Amappli/amap/groots/admin/contracts/add":
            case "/Amappli/amap/greenmaven/admin/contracts/add":
            case "/Amappli/amap/lacarottechantenay/admin/contracts/add":
            case "/Amappli/amap/terralocal/admin/contracts/add":
            case "/Amappli/amap/amapdes5-2/admin/contracts/add":
            case "/Amappli/amap/amapdes5-3/admin/contracts/add":
            case "/Amappli/amap/amapdes5-4/admin/contracts/add":
                document.getElementById("select-1").setAttribute("selected", true);
                document.getElementById("select-2").setAttribute("selected", true);
                document.getElementById("select-3").setAttribute("selected", true);
                document.getElementById("userId").children[1].setAttribute("selected", true);
                document.getElementById("contractDescription").value = "1 botte d\'asperges, 500g de petits pois (en gousses), 1 botte de radis roses, 1 laitue";
                inputs.forEach(input => {
					switch(input.getAttribute("id")) {
						case "contractName":
                            input.value = "Le bouquet du potager";
                        break;
						case "startDate":
                            input.value = "2025-01-10";
                        break;
						case "endDate":
                            input.value = "2025-04-10";
                        break;
						case "contractPrice":
                            input.value = "9.80";
                        break;
						case "quantity":
                            input.value = "10";
                        break;
				    }
				});
                break;
        	case "/Amappli/amap/biocoli/admin/suppliers/add":
            case "/Amappli/amap/agrinov/admin/suppliers/add":
            case "/Amappli/amap/groots/admin/suppliers/add":
            case "/Amappli/amap/greenmaven/admin/suppliers/add":
            case "/Amappli/amap/lacarottechantenay/admin/suppliers/add":
            case "/Amappli/amap/terralocal/admin/suppliers/add":
            case "/Amappli/amap/amapdes5/admin/suppliers/add":
            case "/Amappli/amap/amapdes5-1/admin/suppliers/add":
            case "/Amappli/amap/amapdes5-2/admin/suppliers/add":
            case "/Amappli/amap/amapdes5-3/admin/suppliers/add":
            case "/Amappli/amap/amapdes5-4/admin/suppliers/add":
                inputs.forEach(input => {
					switch(input.getAttribute("id")) {
						case "email":
                            input.value = "tristan.boyer@gmail.com";
                        break;
						case "password-1":
                            input.value = "AMAPamap11@";
                        break;
						case "password-2":
                            input.value = "AMAPamap11@";
                        break;
						case "name":
                            input.value = "Boyer";
                        break;
						case "first-name":
                            input.value = "Tristan";
                        break;
						case "phone":
                            input.value = "0736587514";
                        break;
						case "line-1":
                            input.value = "";
                        break;
						case "line-2":
                            input.value = "10 chemin du pré";
                        break;
						case "post-code":
                            input.value = "76000";
                        break;
						case "city":
                            input.value = "Rouen";
                            break;
						case "company-name":
                            input.value = "Le Domaine des Coteaux Verts";
                        break;
						case "siret":
                            input.value = "95976320843128";
                        break;
				    }
				});
                break;
        	case "/Amappli/amappli/start/signup":
                inputs.forEach(input => {
					switch(input.getAttribute("id")) {
						case "email":
                            input.value = "amelie.rousseau@example1.com";
                        break;
						case "motDePasse":
                            input.value = "AMAPamap11@";
                        break;
						case "confirmMotDePasse":
                            input.value = "AMAPamap11@";
                        break;
						case "nom":
                            input.value = "Rousseau";
                        break;
						case "prenom":
                            input.value = "Amélie";
                        break;
						case "telephone":
                            input.value = "0796584235";
                        break;
						case "complement":
                            input.value = "1er étage";
                        break;
						case "adresse":
                            input.value = "15 route du canal";
                        break;
						case "codePostal":
                            input.value = "76000";
                        break;
						case "ville":
                            input.value = "Rouen";
                        break;
				    }
				});
                break;
        	case "/Amappli/amap/amapdes5/signup":
        	case "/Amappli/amap/amapdes5-1/signup":
        	case "/Amappli/amap/amapdes5-2/signup":
        	case "/Amappli/amap/amapdes5-3/signup":
        	case "/Amappli/amap/amapdes5-4/signup":
                inputs.forEach(input => {
					switch(input.getAttribute("id")) {
						case "email":
                            input.value = "sophie.legrand@example.com";
                        break;
						case "motDePasse":
                            input.value = "AMAPamap11@";
                        break;
						case "confirmMotDePasse":
                            input.value = "AMAPamap11@";
                        break;
						case "nom":
                            input.value = "Legrand";
                        break;
						case "prenom":
                            input.value = "Sophie";
                        break;
						case "telephone":
                            input.value = "0721638788";
                        break;
						case "complement":
                            input.value = "";
                        break;
						case "adresse":
                            input.value = "13 Boulevard Poincaré";
                        break;
						case "codePostal":
                            input.value = "76000";
                        break;
						case "ville":
                            input.value = "Rouen";
                        break;
				    }
				});
            break;
        	case "/Amappli/amappli/start/creation":
                document.getElementById("dayOfWeek").children[7].setAttribute("selected", true);
                document.getElementById("input-value-description-0").value = "Nous sommes une association créée par cinq passionnés désireux de reconnecter producteurs et consommateurs autour d\'une agriculture locale, solidaire et durable. Notre mission est de proposer des paniers de fruits, légumes et autres produits de saison, cultivés avec respect pour la terre et ceux qui la travaillent. En rejoignant notre AMAP, vous soutenez directement des producteurs locaux tout en bénéficiant de produits frais et de qualité. Ensemble, construisons une communauté engagée pour une alimentation responsable et une économie de proximité";
                document.getElementById("input-value-description-1").innerText = "Valoriser les circuits courts permet de renforcer les liens entre les habitants d\'un territoire et leurs producteurs locaux. Cette proximité garantit des produits frais, respectueux des saisons, tout en réduisant l\'impact écologique lié au transport.";
                document.getElementById("input-value-description-2").innerHTML = "L\'AMAP s\'inscrit dans une démarche écoresponsable en favorisant des pratiques agricoles durables et respectueuses de la biodiversité. Elle encourage une consommation raisonnée, limitant le gaspillage et réduisant les emballages inutiles.";
                document.getElementById("input-hp-text").value = "Nous sommes une association créée par cinq passionnés désireux de reconnecter producteurs et consommateurs autour d\'une agriculture locale, solidaire et durable. Notre mission est de proposer des paniers de fruits, légumes et autres produits de saison, cultivés avec respect pour la terre et ceux qui la travaillent. En rejoignant notre AMAP, vous soutenez directement des producteurs locaux tout en bénéficiant de produits frais et de qualité. Ensemble, construisons une communauté engagée pour une alimentation responsable et une économie de proximité";
				inputs.forEach(input => {
					switch(input.getAttribute("id")) {
						case "input-tenancy-name":
                            input.value = "L’amap des 5";
                        break;
						case "input-tenancy-alias":
                            input.value = "amapdes5";
                        break;
						case "input-tenancy-slogan":
                            input.value = "Un lien direct avec la nature et ceux qui la cultivent";
                        break;
						case "input-membership-price":
                            input.value = "1";
                        break;
						case "startHour":
                            input.value = "09:00";
                        break;
						case "endHour":
                            input.value = "19:00";
                        break;
						case "address-postcode":
                            input.value = "76000";
                        break;
						case "address-line2":
                            input.value = "10 Rue Jeanne d'Arc";
                        break;
						case "address-city":
                            input.value = "Rouen";
                        break;
						case "input-value-name-0":
                            input.value = "Solidarité";
                        break;
						case "input-value-name-1":
                            input.value = "Proximité";
                        break;
						case "input-value-name-2":
                            input.value = "Respect de l\'environnement";
                        break;
						case "input-hp-title":
                            input.value = "Bienvenue à L'AMAP des 5 !";
                        break;
						case "font-futura":
                            input.setAttribute("checked", true);
                        break;
						case "question-1-false":
                            input.setAttribute("checked", true);
                        break;
						case "question-3-false":
                            input.setAttribute("checked", true);
                        break;
						case "question-4-false":
                            input.setAttribute("checked", true);
                        break;
						case "question-5-false":
                            input.setAttribute("checked", true);
                        break;
						case "question-6-false":
                            input.setAttribute("checked", true);
                        break;
						case "question-8-false":
                            input.setAttribute("checked", true);
                        break;
				    }
				});
            default:
                break;
        }

    } else if (keysPressed['Alt'] && event.key == 'k') {
        window.location.replace("http://localhost:8080/Amappli/amappli/home")
    }
});

document.addEventListener('keyup', (event) => {
    delete keysPressed[event.key];
});