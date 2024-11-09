const transactionHistory = [];

        function addTransaction() {
            const transactionType = document.getElementById('transactionType').value;
            const description = document.getElementById('description').value;
            const amount = parseFloat(document.getElementById('amount').value);
            const date = document.getElementById('date').value;

            if (!transactionType || !description || isNaN(amount) || !date) {
                alert("Vui lòng điền đầy đủ thông tin!");
                return;
            }

            const entry = {
                date,
                type: transactionType === 'thu' ? 'Khoản thu' : 'Khoản chi',
                description,
                amount
            };
            transactionHistory.push(entry);
            updateHistoryTable();
            updateChart();
            alert('Giao dịch đã được thêm thành công!');
            document.getElementById('financeForm').reset();
        }

        function updateHistoryTable() {
            const tbody = document.getElementById('transactionHistory');
            tbody.innerHTML = '';

            transactionHistory.forEach((entry, index) => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${entry.date}</td>
                    <td>${entry.type}</td>
                    <td>${entry.description}</td>
                    <td>${entry.amount.toLocaleString()} VND</td>
                    <td>
                        <button class="btn btn-warning btn-sm me-2" onclick="editTransaction(${index})">Sửa</button>
                        <button class="btn btn-danger btn-sm" onclick="deleteTransaction(${index})">Xóa</button>
                    </td>
                `;
                tbody.appendChild(row);
            });
        }

        function editTransaction(index) {
            const entry = transactionHistory[index];
            document.getElementById('transactionType').value = entry.type === 'Khoản thu' ? 'thu' : 'chi';
            document.getElementById('description').value = entry.description;
            document.getElementById('amount').value = entry.amount;
            document.getElementById('date').value = entry.date;

            deleteTransaction(index);  // Xóa giao dịch cũ và yêu cầu người dùng lưu lại bản chỉnh sửa.
        }

        function deleteTransaction(index) {
            transactionHistory.splice(index, 1);
            updateHistoryTable();
            updateChart();
        }

        // Cập nhật biểu đồ
        function updateChart() {
            const labels = [];
            const data = {
                thu: 0,
                chi: 0
            };

            transactionHistory.forEach(entry => {
                if (entry.type === 'Khoản thu') {
                    data.thu += entry.amount;
                } else {
                    data.chi += entry.amount;
                }
                if (!labels.includes(entry.date)) {
                    labels.push(entry.date);
                }
            });

            const ctx = document.getElementById('financeChart').getContext('2d');
            new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: labels,
                    datasets: [{
                        label: 'Doanh thu',
                        data: new Array(labels.length).fill(data.thu),
                        backgroundColor: 'rgba(75, 192, 192, 0.2)',
                        borderColor: 'rgba(75, 192, 192, 1)',
                        borderWidth: 1
                    }, {
                        label: 'Chi phí',
                        data: new Array(labels.length).fill(data.chi),
                        backgroundColor: 'rgba(255, 99, 132, 0.2)',
                        borderColor: 'rgba(255, 99, 132, 1)',
                        borderWidth: 1
                    }]
                },
                options: {
                    responsive: true,
                    scales: {
                        y: {
                            beginAtZero: true
                        }
                    }
                }
            });
        }