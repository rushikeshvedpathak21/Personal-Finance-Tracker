const user = requireAuth();

fillUserName();
setupLogout();

const now = new Date();
document.getElementById("month").value = now.getMonth() + 1;
document.getElementById("year").value = now.getFullYear();

async function loadBudgets() {
    const month = now.getMonth() + 1;
    const year = now.getFullYear();

    try {
        const res = await fetch(`${API_BASE}/budgets?month=${month}&year=${year}`, {
            headers: authHeaders(user.id)
        });
        const data = await res.json();
        renderBudgets(data.data || []);
    } catch (err) {
        console.error("Failed to load budgets", err);
    }
}

function renderBudgets(budgets) {
    const container = document.getElementById("budgetList");

    if (budgets.length === 0) {
        container.innerHTML = `
            <div class="empty-state">
                <div class="empty-icon"></div>
                <p>No budgets set for this month</p>
            </div>`;
        return;
    }

    container.innerHTML = budgets.map(b => {
        const limit = parseFloat(b.monthlyLimit);
        const spent = parseFloat(b.amountSpent);
        const percent = limit > 0 ? Math.min((spent / limit) * 100, 100) : 0;

        let fillClass = "";
        if (percent >= 90) fillClass = "danger";
        else if (percent >= 70) fillClass = "warning";

        return `
            <div class="budget-item">
                <div class="budget-label">
                    <span>${capitalize(b.category)}</span>
                    <span>${formatCurrency(spent)} / ${formatCurrency(limit)}</span>
                </div>
                <div class="progress-bar-bg">
                    <div class="progress-bar-fill ${fillClass}" style="width: ${percent.toFixed(1)}%"></div>
                </div>
                <div class="budget-amounts">
                    <span>${percent.toFixed(0)}% used</span>
                    <span>${formatCurrency(limit - spent)} remaining</span>
                </div>
            </div>
        `;
    }).join("");
}

document.getElementById("budgetForm").addEventListener("submit", async function (e) {
    e.preventDefault();

    const body = {
        category: document.getElementById("category").value,
        monthlyLimit: parseFloat(document.getElementById("monthlyLimit").value),
        month: parseInt(document.getElementById("month").value),
        year: parseInt(document.getElementById("year").value)
    };

    try {
        const res = await fetch(`${API_BASE}/budgets`, {
            method: "POST",
            headers: authHeaders(user.id),
            body: JSON.stringify(body)
        });

        const data = await res.json();

        if (data.success) {
            showAlert("budgetAlert", "Budget saved!", "success");
            document.getElementById("budgetForm").reset();
            document.getElementById("month").value = now.getMonth() + 1;
            document.getElementById("year").value = now.getFullYear();
            loadBudgets();
        } else {
            showAlert("budgetAlert", data.message, "error");
        }
    } catch (err) {
        showAlert("budgetAlert", "Failed to save budget.", "error");
    }
});

loadBudgets();
