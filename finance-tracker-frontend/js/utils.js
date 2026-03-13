const API_BASE = "http://localhost:8080/api";

function getUser() {
    const raw = sessionStorage.getItem("user");
    return raw ? JSON.parse(raw) : null;
}

function saveUser(user) {
    sessionStorage.setItem("user", JSON.stringify(user));
}

function logout() {
    sessionStorage.removeItem("user");
    window.location.href = "/login.html";
}

function requireAuth() {
    const user = getUser();
    if (!user) {
        window.location.href = "/login.html";
        return null;
    }
    return user;
}

function authHeaders(userId) {
    return {
        "Content-Type": "application/json",
        "X-User-Id": userId
    };
}

function showAlert(elementId, message, type = "error") {
    const el = document.getElementById(elementId);
    if (!el) return;
    el.textContent = message;
    el.className = `alert alert-${type} show`;
    setTimeout(() => {
        el.className = "alert";
    }, 4000);
}

function formatCurrency(amount) {
    return "$" + parseFloat(amount).toFixed(2);
}

function formatDate(dateStr) {
    if (!dateStr) return "";
    const d = new Date(dateStr);
    return d.toLocaleDateString("en-US", { year: "numeric", month: "short", day: "numeric" });
}

function capitalize(str) {
    if (!str) return "";
    return str.charAt(0).toUpperCase() + str.slice(1);
}

function setupLogout() {
    const btn = document.getElementById("logoutBtn");
    if (btn) {
        btn.addEventListener("click", logout);
    }
}

function setActiveNav(pageName) {
    document.querySelectorAll(".nav-item").forEach(item => {
        item.classList.remove("active");
        if (item.dataset.page === pageName) {
            item.classList.add("active");
        }
    });
}

function fillUserName() {
    const user = getUser();
    const el = document.getElementById("userName");
    if (el && user) {
        el.textContent = user.name;
    }
}
