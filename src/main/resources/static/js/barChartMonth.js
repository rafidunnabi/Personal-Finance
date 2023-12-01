const barCanvas = document.getElementById('barMonth');
        const ctxBar = barCanvas.getContext('2d');
        const barChart = new Chart(ctxBar, {
            type: 'bar',
            data: {
                labels: [BarTitleMonthlyBar, BarTitleCurrentMonth],
                datasets: [{
                    label: 'Income',
                    data: [incomeDataMonthlyBar.map(data => data.total)[0],incomeDataCurrentMonth.map(data => data.total)[0]],
                    backgroundColor: '#04bade',
                    borderColor: '#04bade',
                    categoryPercentage: 0.7,
                    barPercentage: 0.5,
                }, {
                    label: 'Expense',
                    data: [expenseDataMonthlyBar.map(data => data.total)[0], expenseDataCurrentMonth.map(data => data.total)[0]],
                    backgroundColor: '#ddc9b0',
                    borderColor: '#ddc9b0',
                    categoryPercentage: 0.7,
                    barPercentage: 0.5,
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                title: {
                    display: true,
                    text: 'Monthly Update',
                    fontSize: 26
                },
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero: true
                        }
                    }]
                }
            }
        });