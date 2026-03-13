const loginForm = document.getElementById("loginForm");
const registerForm = document.getElementById("registerForm");

if (loginForm) {
    loginForm.addEventListener("submit", async function (e) {
        e.preventDefault();
        const email = document.getElementById("email").value.trim();
        const password = document.getElementById("password").value;

        try {
            const res = await fetch(`${API_BASE}/users/login`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ email, password })
            });

            const data = await res.json();

            if (data.success) {
                saveUser(data.data);
                window.location.href = "pages/dashboard.html";
            } else {
                showAlert("loginAlert", data.message, "error");
            }
        } catch (err) {
            showAlert("loginAlert", "Could not connect to server. Make sure the backend is running.", "error");
        }
    });
}

if (registerForm) {
    registerForm.addEventListener("submit", async function (e) {
        e.preventDefault();
        const name = document.getElementById("name").value.trim();
        const email = document.getElementById("email").value.trim();
        const password = document.getElementById("password").value;

        try {
            const res = await fetch(`${API_BASE}/users/register`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ name, email, password })
            });

            const data = await res.json();

            if (data.success) {
                showAlert("registerAlert", "Account created! Redirecting to login...", "success");
                setTimeout(() => {
                    window.location.href = "login.html";
                }, 1500);
            } else {
                showAlert("registerAlert", data.message, "error");
            }
        } catch (err) {
            showAlert("registerAlert", "Could not connect to server. Make sure the backend is running.", "error");
        }
    });
}
