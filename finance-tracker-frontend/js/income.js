const user = requireAuth();

fillUserName();
setupLogout();

document.getElementById("incomeDate").valueAsDate = new Date();

async function loadIncome() {
    try {
        const res = await fetch(`${API_BASE}/income`, { headers: authHeaders(user.id) });
        const data = await res.json();
        renderIncome(data.data || []);
    } catch (err) {
        console.error("Failed to load income", err);
    }
}

function renderIncome(incomes) {
    const tbody = document.getElementById("incomeList");
    if (incomes.length === 0) {
        tbody.innerHTML = `<tr><td colspan="4"><div class="empty-state"><div class="empty-icon"></div><p>No income recorded yet</p></div></td></tr>`;
        return;
    }
    tbody.innerHTML = incomes.map(i => `
        <tr>
            <td>${capitalize(i.source)}</td>
            <td style="color: var(--success); font-weight: 600;">+${formatCurrency(i.amount)}</td>
            <td><span class="badge badge-income">${capitalize(i.frequency)}</span></td>
            <td>${formatDate(i.incomeDate)}</td>
        </tr>
    `).join("");
}

document.getElementById("incomeForm").addEventListener("submit", async function (e) {
    e.preventDefault();

    const body = {
        amount: parseFloat(document.getElementById("amount").value),
        source: document.getElementById("source").value,
        incomeDate: document.getElementById("incomeDate").value,
        frequency: document.getElementById("frequency").value
    };

    try {
        const res = await fetch(`${API_BASE}/income`, {
            method: "POST",
            headers: authHeaders(user.id),
            body: JSON.stringify(body)
        });

        const data = await res.json();

        if (data.success) {
            showAlert("incomeAlert", "Income added successfully!", "success");
            document.getElementById("incomeForm").reset();
            document.getElementById("incomeDate").valueAsDate = new Date();
            loadIncome();
        } else {
            showAlert("incomeAlert", data.message, "error");
        }
    } catch (err) {
        showAlert("incomeAlert", "Failed to add income. Check your connection.", "error");
    }
});

loadIncome();
