
const barCanvasYear = document.getElementById('barYear');
        const ctxBarYear = barCanvasYear.getContext('2d');
        const barChartYear = new Chart(ctxBarYear, {
            type: 'bar',
            data: {
                labels: ['This Year', 'Last Year'],
                datasets: [{
                    label: 'Income',
                    data: [2000, 1500],
                    backgroundColor: '#ED1D24',
                    borderColor: '#ED1D24',
                    categoryPercentage: 0.7,
                    barPercentage: 0.5,
                }, {
                    label: 'Expense',
                    data: [1200, 1000],
                    backgroundColor: 'cyan',
                    borderColor: 'cyan',
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