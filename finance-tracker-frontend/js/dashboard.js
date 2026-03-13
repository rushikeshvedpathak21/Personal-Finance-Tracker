const user = requireAuth();

fillUserName();
setupLogout();

async function loadDashboard() {
    try {
        const headers = authHeaders(user.id);

        const [expenseRes, incomeRes] = await Promise.all([
            fetch(`${API_BASE}/expenses`, { headers }),
            fetch(`${API_BASE}/income`, { headers })
        ]);

        const expenseData = await expenseRes.json();
        const incomeData = await incomeRes.json();

        const expenses = expenseData.data || [];
        const incomes = incomeData.data || [];

        const now = new Date();
        const thisMonth = now.getMonth();
        const thisYear = now.getFullYear();

        const monthlyExpenses = expenses.filter(e => {
            const d = new Date(e.expenseDate);
            return d.getMonth() === thisMonth && d.getFullYear() === thisYear;
        });

        const monthlyIncome = incomes.filter(i => {
            const d = new Date(i.incomeDate);
            return d.getMonth() === thisMonth && d.getFullYear() === thisYear;
        });

        const totalExpenses = monthlyExpenses.reduce((sum, e) => sum + parseFloat(e.amount), 0);
        const totalIncome = monthlyIncome.reduce((sum, i) => sum + parseFloat(i.amount), 0);
        const balance = totalIncome - totalExpenses;

        document.getElementById("totalIncome").textContent = formatCurrency(totalIncome);
        document.getElementById("totalExpenses").textContent = formatCurrency(totalExpenses);

        const balanceEl = document.getElementById("balance");
        balanceEl.textContent = formatCurrency(balance);
        balanceEl.classList.add(balance >= 0 ? "positive" : "negative");

        renderRecentExpenses(expenses.slice(0, 5));
        renderRecentIncome(incomes.slice(0, 5));

    } catch (err) {
        console.error("Failed to load dashboard", err);
    }
}

function renderRecentExpenses(expenses) {
    const tbody = document.getElementById("recentExpenses");
    if (expenses.length === 0) {
        tbody.innerHTML = `<tr><td colspan="3"><div class="empty-state"><div class="empty-icon"></div><p>No expenses yet</p></div></td></tr>`;
        return;
    }
    tbody.innerHTML = expenses.map(e => `
        <tr>
            <td><span class="badge badge-category">${capitalize(e.category)}</span></td>
            <td style="color: var(--danger); font-weight: 600;">-${formatCurrency(e.amount)}</td>
            <td>${formatDate(e.expenseDate)}</td>
        </tr>
    `).join("");
}

function renderRecentIncome(incomes) {
    const tbody = document.getElementById("recentIncome");
    if (incomes.length === 0) {
        tbody.innerHTML = `<tr><td colspan="3"><div class="empty-state"><div class="empty-icon"></div><p>No income yet</p></div></td></tr>`;
        return;
    }
    tbody.innerHTML = incomes.map(i => `
        <tr>
            <td>${capitalize(i.source)}</td>
            <td style="color: var(--success); font-weight: 600;">+${formatCurrency(i.amount)}</td>
            <td>${formatDate(i.incomeDate)}</td>
        </tr>
    `).join("");
}

loadDashboard();
