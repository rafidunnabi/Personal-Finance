function populateEditBudgetForm(button) {
  console.log("Button Clicked");
  var category = button.getAttribute("budget-category");
  var description = button.getAttribute("budget-description");
  var startDate = button.getAttribute("budget-startDate");
  var endDate = button.getAttribute("budget-endDate");
  var amount = button.getAttribute("budget-amount");
  var id = button.getAttribute("budget-id");

  document.getElementById("budgetId").value = id;
  document.getElementById("budgetAmount").value = amount;
  document.getElementById("budgetCategory").value = category;
  document.getElementById("budgetStartDate").value = startDate;
  document.getElementById("budgetEndDate").value = endDate;
  document.getElementById("budgetDescription").value = description;
}
