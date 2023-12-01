
const barCanvasYear = document.getElementById('barYear');
        const ctxBarYear = barCanvasYear.getContext('2d');
        const barChartYear = new Chart(ctxBarYear, {
            type: 'bar',
            data: {
                labels: [BarTitleYearlyBar, BarTitleCurrentYear],
                datasets: [{
                    label: 'Income',
                    data: [incomeDataYearlyBar.map(data => data.total)[0],incomeDataCurrentYear.map(data => data.total)[0]],
                    backgroundColor: '#20B2AA',
                    borderColor: '#20B2AA',
                    categoryPercentage: 0.7,
                    barPercentage: 0.5,
                }, {
                    label: 'Expense',
                    data: [expenseDataYearlyBar.map(data => data.total)[0], expenseDataCurrentYear.map(data => data.total)[0]],
                    backgroundColor: 'orange',
                    borderColor: 'orange',
                    categoryPercentage: 0.7,
                    barPercentage: 0.5,
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                title: {
                    display: true,
                    text: 'Yearly Update',
                    fontSize: 26
                },
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero: true
                        }
                    }],
                }
            }
        });