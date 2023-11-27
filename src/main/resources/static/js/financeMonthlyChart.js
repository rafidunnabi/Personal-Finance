//// Get the canvas element
//const canvas = document.getElementById('myChart');
//
//// Create a Chart.js instance
//const ctx = canvas.getContext('2d');
//const chart = new Chart(ctx, {
//    type: 'line',
//    data: {
//        labels: ['1-Nov', '2-Nov', '3-Nov', '4-Nov', '5-Nov', '6-Nov', '7-Nov', '8-Nov', '9-Nov', '10-Nov', '11-Nov', '12-Nov', '13-Nov', '14-Nov'],
//        datasets: [{
//            label: 'Income',
//            data: [1000, 1200, 1500, 1800, 2100, 2400, 2700, 3000, 3300, 3600, 3900, 4200, 4500, 4800],
//            backgroundColor: 'transparent',
//            borderColor: 'orange'
//        }, {
//            label: 'Expense',
//            data: [500, 600, 700, 800, 900, 1000, 1100, 1200, 1300, 1400, 1500, 1600, 1700, 1800],
//            backgroundColor: 'transparent',
//            borderColor: 'cyan'
//        }]
//    },
//    options: {
//        responsive: true,
//        title: {
//            display: true,
//            text: 'Income vs Expense for Last Month'
//        },
//        scales: {
//            yAxes: [{
//                ticks: {
//                    beginAtZero: true
//                }
//            }]
//        }
//    }
//});

// chart.js

document.addEventListener('DOMContentLoaded', function () {
    // Get the canvas element
    const canvas = document.getElementById('myChart');

    // Assume that 'chartData' is available globally (you set it in your Thymeleaf model)
    const labels = chartData.labels;
    const incomeData = chartData.incomeData;
    const expenseData = chartData.expenseData;

    // Create a Chart.js instance
    const ctx = canvas.getContext('2d');
    const chart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: labels,
            datasets: [{
                label: 'Income',
                data: incomeData,
                backgroundColor: 'transparent',
                borderColor: 'orange'
            }, {
                label: 'Expense',
                data: expenseData,
                backgroundColor: 'transparent',
                borderColor: 'cyan'
            }]
        },
        options: {
            responsive: true,
            title: {
                display: true,
                text: 'Income vs Expense for Last Month'
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
});

