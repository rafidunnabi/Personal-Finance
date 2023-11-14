
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
                    text: 'Monthly Update',
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