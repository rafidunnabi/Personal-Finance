const barCanvasDay = document.getElementById('barDay');
        const ctxBarDay = barCanvasDay.getContext('2d');
        const barChartDay = new Chart(ctxBarDay, {
            type: 'bar',
            data: {
                labels: ['Today', 'Yesterday'],
                datasets: [{
                    label: 'Income',
                    data: [2000, 1500],
                    backgroundColor: '#FF5733',
                    borderColor: '#FF5733',
                    categoryPercentage: 0.7,
                    barPercentage: 0.5,
                }, {
                    label: 'Expense',
                    data: [1200, 1000],
                    backgroundColor: '#7FB8A1',
                    borderColor: '#7FB8A1',
                    categoryPercentage: 0.7,
                    barPercentage: 0.5,
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                title: {
                    display: true,
                    text: 'Daily Update',
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