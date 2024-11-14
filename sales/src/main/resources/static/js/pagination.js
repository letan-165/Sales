document.addEventListener("DOMContentLoaded", () => {
  const rowsPerPage = 10;
  let currentPage = 1;
  const table = document.querySelector("#list");
  const paginationContainer = document.querySelector("#pagination");

  function displayPage(page) {
    const rows = Array.from(table.querySelectorAll("tr"));
    const start = (page - 1) * rowsPerPage;
    const end = page * rowsPerPage;

    rows.forEach((row, index) => {
      row.style.display = index >= start && index < end ? "" : "none";
    });
  }

  function setupPagination(totalRows) {
    paginationContainer.innerHTML = "";
    const pageCount = Math.ceil(totalRows / rowsPerPage);

    for (let i = 1; i <= pageCount; i++) {
      const button = document.createElement("button");
      button.textContent = i;
      button.classList.add("btn", "btn-secondary", "mx-1");
      if (i === currentPage) {
        button.classList.add("active");
      }
      button.addEventListener("click", () => {
        if (currentPage !== i) {
          currentPage = i;
          displayPage(currentPage);
          updateButtonStyles();
        }
      });
      paginationContainer.appendChild(button);
    }

    updateButtonStyles();
  }

  function updateButtonStyles() {
    Array.from(paginationContainer.children).forEach((button, index) => {
      button.classList.toggle("active", index + 1 === currentPage);
    });
  }

  const totalRows = table.querySelectorAll("tr").length;
  displayPage(currentPage);
  setupPagination(totalRows);
});
