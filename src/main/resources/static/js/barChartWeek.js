const barCanvasWeek = document.getElementById('barWeek');
        const ctxBarWeek = barCanvasWeek.getContext('2d');
        const barChartWeek = new Chart(ctxBarWeek, {
            type: 'bar',
            data: {
                labels: ['This Week', 'Last Week'],
                datasets: [{
                    label: 'Income',
                    data: [2000, 1500],
                    backgroundColor: '#50C878',
                    borderColor: '#50C878',
                    categoryPercentage: 0.7,
                    barPercentage: 0.5,
                }, {
                    label: 'Expense',
                    data: [1200, 1000],
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
                    text: 'Weekly Update',
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