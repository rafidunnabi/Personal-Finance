function validateAddBudgetDates() {
  var startDate = document.getElementById("startDateInputBudget").value;
  var endDate = document.getElementById("endDateInputBudget").value;

  if (startDate > endDate) {
    alert("End date must be equal to or after the start date.");
    return false;
  }

  return true;
}
