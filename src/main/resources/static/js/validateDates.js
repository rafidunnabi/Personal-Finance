function validateDates() {
  var startDate = document.getElementById("startDateInput").value;
  var endDate = document.getElementById("endDateInput").value;

  if (startDate > endDate) {
    alert("End date must be equal to or after the start date.");
    return false;
  }

  return true;
}
