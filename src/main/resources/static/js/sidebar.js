const sidebar = document.querySelector(".sidebar");
fetch("/sidebar.html")
  .then((res) => res.text())
  .then((data) => {
    sidebar.innerHTML = data;
  });
