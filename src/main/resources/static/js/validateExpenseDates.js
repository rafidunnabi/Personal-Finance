function validateExpenseDates() {
  var startDate = document.getElementById("startDateInputExpense").value;
  var endDate = document.getElementById("endDateInputExpense").value;

  if (startDate > endDate) {
    alert("End date must be equal to or after the start date.");
    return false;
  }

  return true;
}