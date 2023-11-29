function populateEditBudgetForm(button) {
    console.log("Button Clicked");
    var category = button.getAttribute("data-category");
    var description = button.getAttribute("data-description");
    var date = button.getAttribute("data-date");
    var amount = button.getAttribute("data-amount");
    var id = button.getAttribute("data-id");
  
    console.log(category);
    console.log(description);
    console.log(date);
    console.log(amount);
    console.log(id);
  
    document.getElementById("idInput").value = id;
    document.getElementById("amountInput").value = amount;
    document.getElementById("categorySelect").value = category;
    document.getElementById("dateInput").value = date;
    document.getElementById("descriptionInput").value = description;
  }