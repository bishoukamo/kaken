const loggedIn = localStorage.getItem("loggedIn");

if (loggedIn !== "true") {
    window.location.href = "/login";
}

const logoutButton = document.getElementById("logoutButton");

logoutButton.addEventListener("click", () => {

    localStorage.removeItem("loggedIn");

    window.location.href = "/login";

});