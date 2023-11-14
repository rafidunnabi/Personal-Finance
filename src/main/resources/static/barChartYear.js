
const barCanvasYear = document.getElementById('barYear');
        const ctxBarYear = barCanvasYear.getContext('2d');
        const barChartYear = new Chart(ctxBarYear, {
            type: 'bar',
            data: {
                labels: ['This Year', 'Last Year'],
                datasets: [{
                    label: 'Income',
                    data: [2000, 1500],
                    backgroundColor: '#FF5733',
                    borderColor: '#FF5733',
                    barThickness: 40,
                }, {
                    label: 'Expense',
                    data: [1200, 1000],
                    backgroundColor: '#ddc9b0',
                    borderColor: '#ddc9b0',
                    barThickness: 40,
                }]
            },
            options: {
                responsive: true,
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
                    xAxes: [{
                                barPercentage: 0.4
                            }]
                }
            }
        });