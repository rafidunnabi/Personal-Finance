const barCanvas = document.getElementById('barMonth');
        const ctxBar = barCanvas.getContext('2d');
        const barChart = new Chart(ctxBar, {
            type: 'bar',
            data: {
                labels: ['This Month', 'Last Month'],
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