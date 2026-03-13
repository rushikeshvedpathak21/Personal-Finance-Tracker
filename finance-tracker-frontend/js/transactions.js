const user = requireAuth();

fillUserName();
setupLogout();

let allTransactions = [];

async function loadTransactions() {
    try {
        const headers = authHeaders(user.id);

        const [expenseRes, incomeRes] = await Promise.all([
            fetch(`${API_BASE}/expenses`, { headers }),
            fetch(`${API_BASE}/income`, { headers })
        ]);

        const expenseData = await expenseRes.json();
        const incomeData = await incomeRes.json();

        const expenses = (expenseData.data || []).map(e => ({
            ...e,
            type: "expense",
            date: e.expenseDate,
            label: e.category,
            note: e.description || "-"
        }));

        const incomes = (incomeData.data || []).map(i => ({
            ...i,
            type: "income",
            date: i.incomeDate,
            label: i.source,
            note: i.frequency
        }));

        allTransactions = [...expenses, ...incomes].sort((a, b) => {
            return new Date(b.date) - new Date(a.date);
        });

        renderTransactions(allTransactions);
    } catch (err) {
        console.error("Failed to load transactions", err);
    }
}

function renderTransactions(transactions) {
    const tbody = document.getElementById("transactionList");

    if (transactions.length === 0) {
        tbody.innerHTML = `
            <tr><td colspan="5">
                <div class="empty-state">
                    <div class="empty-icon"></div>
                    <p>No transactions found</p>
                </div>
            </td></tr>`;
        return;
    }

    tbody.innerHTML = transactions.map(t => {
        const isExpense = t.type === "expense";
        const amountStr = isExpense
            ? `<span style="color: var(--danger); font-weight: 600;">-${formatCurrency(t.amount)}</span>`
            : `<span style="color: var(--success); font-weight: 600;">+${formatCurrency(t.amount)}</span>`;

        const typeBadge = isExpense
            ? `<span class="badge badge-expense">Expense</span>`
            : `<span class="badge badge-income">Income</span>`;

        return `
            <tr>
                <td>${formatDate(t.date)}</td>
                <td>${typeBadge}</td>
                <td><span class="badge badge-category">${capitalize(t.label)}</span></td>
                <td style="color: var(--text-muted); font-size: 13px;">${t.note}</td>
                <td>${amountStr}</td>
            </tr>
        `;
    }).join("");
}

function applyFilters() {
    const type = document.getElementById("filterType").value;
    const category = document.getElementById("filterCategory").value.toLowerCase();
    const keyword = document.getElementById("filterKeyword").value.toLowerCase();

    let filtered = allTransactions;

    if (type !== "all") {
        filtered = filtered.filter(t => t.type === type);
    }

    if (category) {
        filtered = filtered.filter(t => t.label.toLowerCase() === category);
    }

    if (keyword) {
        filtered = filtered.filter(t =>
            t.note.toLowerCase().includes(keyword) ||
            t.label.toLowerCase().includes(keyword)
        );
    }

    renderTransactions(filtered);
}

document.getElementById("applyFilter").addEventListener("click", applyFilters);

document.getElementById("filterKeyword").addEventListener("keyup", function (e) {
    if (e.key === "Enter") applyFilters();
});

loadTransactions();
