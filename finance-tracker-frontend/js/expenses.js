const user = requireAuth();

fillUserName();
setupLogout();

document.getElementById("expenseDate").valueAsDate = new Date();

async function loadExpenses() {
    try {
        const res = await fetch(`${API_BASE}/expenses`, { headers: authHeaders(user.id) });
        const data = await res.json();
        renderExpenses(data.data || []);
    } catch (err) {
        console.error("Failed to load expenses", err);
    }
}

function renderExpenses(expenses) {
    const tbody = document.getElementById("expenseList");
    if (expenses.length === 0) {
        tbody.innerHTML = `<tr><td colspan="4"><div class="empty-state"><div class="empty-icon"></div><p>No expenses added yet</p></div></td></tr>`;
        return;
    }
    tbody.innerHTML = expenses.map(e => `
        <tr>
            <td><span class="badge badge-category">${capitalize(e.category)}</span></td>
            <td style="color: var(--danger); font-weight: 600;">-${formatCurrency(e.amount)}</td>
            <td>${formatDate(e.expenseDate)}</td>
            <td style="color: var(--text-muted); font-size: 13px;">${e.description || "-"}</td>
        </tr>
    `).join("");
}

document.getElementById("expenseForm").addEventListener("submit", async function (e) {
    e.preventDefault();

    const body = {
        amount: parseFloat(document.getElementById("amount").value),
        category: document.getElementById("category").value,
        expenseDate: document.getElementById("expenseDate").value,
        description: document.getElementById("description").value.trim() || null
    };

    try {
        const res = await fetch(`${API_BASE}/expenses`, {
            method: "POST",
            headers: authHeaders(user.id),
            body: JSON.stringify(body)
        });

        const data = await res.json();

        if (data.success) {
            showAlert("expenseAlert", "Expense added successfully!", "success");
            document.getElementById("expenseForm").reset();
            document.getElementById("expenseDate").valueAsDate = new Date();
            loadExpenses();
        } else {
            showAlert("expenseAlert", data.message, "error");
        }
    } catch (err) {
        showAlert("expenseAlert", "Failed to add expense. Check your connection.", "error");
    }
});

loadExpenses();
