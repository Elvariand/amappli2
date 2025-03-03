let range = document.getElementById("bg-range");

let table = document.querySelector("table");

let cookieName = "bg-table";
let cookieTable = getCookie("bg-table");
today = new Date();
let expiration = new Date(today.getTime() + 31 * 24 * 3600 * 1000); // plus 31 days
var val = 100;

range.addEventListener("input", function () {
    val = parseFloat(100-range.value);
    if (document.body.classList.contains("dark")) {
        table.setAttribute("style", "--bs-table-bg: color-mix(in srgb, var(--main-bg-color), transparent " + val + "%);");
    } else {
        table.setAttribute("style", "--bs-table-bg: color-mix(in srgb, var(--white-300), transparent " + val + "%);");
    }
    document.cookie = "bg-table=" + val + "; path=/; SameSite=Strict; expires=" + expiration.toUTCString();
    cookieTable = getCookie("bg-table");

});

switcher.addEventListener("click", function() {
    
    if (switcher.checked == true) {
        table.setAttribute("style", "--bs-table-bg: color-mix(in srgb, var(--main-bg-color), transparent " + val + "%);");
    } else {
        table.setAttribute("style", "--bs-table-bg: color-mix(in srgb, var(--white-300), transparent " + val + "%);");
    }
});

if (cookieTable != null) {
    val = parseFloat(cookieTable);
    if (document.body.classList.contains("dark")) {
        table.setAttribute("style", "--bs-table-bg: color-mix(in srgb, var(--main-bg-color), transparent " + val + "%);");
    } else {
        table.setAttribute("style", "--bs-table-bg: color-mix(in srgb, var(--white-300), transparent " + val + "%);");
    }
	range.value=val.toString();
    document.cookie = "bg-table=" + val + "; path=/; SameSite=Strict; expires=" + expiration.toUTCString();
} else {
    range.value="0";
    document.cookie = "bg-table=100; path=/; SameSite=Strict; expires=" + expiration.toUTCString();
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