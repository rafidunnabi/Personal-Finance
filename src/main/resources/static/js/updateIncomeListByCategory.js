document.getElementById('incomeSearchForm').addEventListener('submit', function(event) {
    
    var tableBody = document.getElementById("tableBody");
    tableBody.innerHTML = "Hello There Moshi Moshi";
});


    

    /*
//     /*<![CDATA[*/
//   var incomeByCategoryList = /*[[${incomeByCategoryList}]]*/ [];
//   /*]]>*/

//     for (var i = 0; i < incomeByCategoryList.length; i++) {

//         console.log(incomeByCategoryList[i]);
//       var income = incomeByCategoryList[i];
        
//       var row = tableBody.insertRow(i);
  
//       var cell1 = row.insertCell(0);
//       cell1.textContent = income.category;
  
//       var cell2 = row.insertCell(1);
//       cell2.textContent = income.description;
  
//       var cell3 = row.insertCell(2);
//       cell3.textContent = income.date;
  
//       var cell4 = row.insertCell(3);
//       cell4.textContent = income.amount;
  
//       var cell5 = row.insertCell(4);
//       cell5.innerHTML = '<button type="button" class="btn btn-danger btn-sm px-3" data-category="' + income.category + '" data-description="' + income.description + '" data-date="' + income.date + '" data-amount="' + income.amount + '" data-id="' + income.id + '" data-bs-toggle="modal" data-bs-target="#exampleModalEdit" onclick="populateEditForm(this)"><i class="fa-regular fa-pen-to-square"></i></button>';
//     } 
