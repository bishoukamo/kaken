const loginForm = document.getElementById("loginForm");
const loginResult = document.getElementById("loginResult");

loginForm.addEventListener("submit", async (event) => {

    event.preventDefault();

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    // パスワードの長さをチェック
    if (password.length < 8) {
        loginResult.textContent = "パスワードは8文字以上で入力してください";
        return;
    }

    try {
        const response = await fetch("/api/login", {
            method: "POST",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify({
                username: username,
                password: password
            })
        });

        if (!response.ok) {
            console.error("HTTPエラー:", response.status);
            loginResult.textContent = "サーバーエラー";
            return;
        }

        const data = await response.json();

        if (data.success) {
            localStorage.setItem("loggedIn", "true");
            window.location.href = "/home";
        } else {
            loginResult.textContent = "ログイン失敗";
        }

    } catch (error) {
        console.error("Fetchエラー:", error);
        loginResult.textContent = "ネットワークエラー";
    }
});