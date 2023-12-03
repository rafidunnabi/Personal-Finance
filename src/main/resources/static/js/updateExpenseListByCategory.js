var tableBodyExpense = document.getElementById("tableBodyExpense");
if(expenseList.length){
     for (var i = 0; i < expenseList.length; i++) {

         console.log(expenseList[i]);
       var expense = expenseList[i];

       var row = tableBodyExpense.insertRow(i);

       var cell1 = row.insertCell(0);
       cell1.textContent = expense.category;

       var cell2 = row.insertCell(1);
       cell2.textContent = expense.description;

       var cell3 = row.insertCell(2);
       cell3.textContent = expense.date;

       var cell4 = row.insertCell(3);
       cell4.textContent = expense.amount;

       var cell5 = row.insertCell(4);
       cell5.innerHTML = '<button type="button" class="btn-red btn-danger btn-sm px-3" data-category="' + expense.category + '" data-description="' + expense.description + '" data-date="' + expense.date + '" data-amount="' + expense.amount + '" data-id="' + expense.id + '" data-bs-toggle="modal" data-bs-target="#exampleModalEdit" onclick="populateEditForm(this)"><i class="fa-regular fa-pen-to-square"></i></button>';
     }
}
else if(isCategoryClickedExpense)
{
    tableBodyExpense.innerHTML = "No records found";
}
