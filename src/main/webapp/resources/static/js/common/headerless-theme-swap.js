let body = document.body;

let cname = "amappliTheme";
let ctheme = getCookie("amappliTheme");
let today = new Date();
let expiry = new Date(today.getTime() + 31 * 24 * 3600 * 1000); // plus 31 days

if (typeof(styleMapboxLight) === "undefined") {
	var styleMapboxLight = "mapbox://styles/tiroirmorgane/cm4sw37wr001301s12frm2l2y";
	var styleMapboxDark = "mapbox://styles/tiroirmorgane/cm52cqefg003101sa878udky6";
}

if (ctheme != null) {
    if (ctheme === "dark") {
        body.classList.remove('light');
        body.classList.add('dark');
		switcher.setAttribute("checked",true);
		document.cookie = "amappliTheme=dark; path=/; SameSite=Strict; expires=" + expiry.toUTCString();
        map.setStyle(styleMapboxDark);
    } else {
        body.classList.remove('dark');
        body.classList.add('light');
        document.cookie = "amappliTheme=light; path=/; SameSite=Strict; expires=" + expiry.toUTCString();
        map.setStyle(styleMapboxLight);
    }
} else {
	    document.cookie = "amappliTheme=dark; path=/; SameSite=Strict; expires=" + expiry.toUTCString();
}


function setCookie(name, value, expire) {

    document.cookie = name + "=" + value + "; path=/; SameSite=Strict; expires=" + expire.toUTCString();

}

function getCookie(name) {

    var re = new RegExp(name + "=([^;]+)");
    var value = re.exec(document.cookie);
    return (value != null) ? value[1] : null;

}


function deleteCookie(name) {

    document.cookie = name + "=null; path=/; expires=" + expired.toGMTString();

}