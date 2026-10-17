async function loadUsers() {

    const response = await fetch("/api/users");

    const users = await response.json();

    const usersDiv = document.getElementById("users");

    users.forEach(user => {

        usersDiv.innerHTML += `
        <p>${user.name} (${user.age}歳) (${user.nationality})</p>
    `;

    });
}

loadUsers();