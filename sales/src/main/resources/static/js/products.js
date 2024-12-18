function searchProducts() {
  const input = document.getElementById("searchInput").value.toLowerCase();
  const filterWords = input.split(" "); // Tách các từ khóa
  const table = document.getElementById("list");
  const rows = table.getElementsByTagName("tr");

  for (let i = 0; i < rows.length; i++) {
      const rowText = rows[i].innerText.toLowerCase();
      
      // Kiểm tra nếu có ít nhất một từ trong filterWords khớp với nội dung của hàng
      const match = filterWords.some(word => rowText.includes(word));

      if (match || input === "") {
          rows[i].style.display = ""; // Hiển thị hàng nếu khớp hoặc ô tìm kiếm trống
      } else {
          rows[i].style.display = "none"; // Ẩn hàng nếu không khớp
      }
  }
}

function openDialog(
  action,
  productID = null,
  productName = null,
  price = null,
  priceImport = null,
  type = null,
  status = null,
  description = null
) {
  const dialog = new bootstrap.Modal(document.getElementById("dialog"));
  const dialogLabel = document.getElementById("dialogLabel");
  const confirmButton = document.getElementById("confirmButton");
  const productIDField = document.getElementById("productID");

  document.getElementById("addEditContent").style.display = "block";
  document.getElementById("deleteContent").style.display = "none";
  if (action === "add") {
    dialogLabel.textContent = "Thêm sản phẩm";
    confirmButton.textContent = "Thêm";
    productIDField.style.display = "none";
    document.getElementById("productName").value = "";
    document.getElementById("price").value = "";
    document.getElementById("priceImport").value = "";
    document.getElementById("type").value = "";
    document.getElementById("status").value = "";
    document.getElementById("description").value = "";
  } else if (action === "edit") {
    dialogLabel.textContent = "Sửa sản phẩm";
    confirmButton.textContent = "Lưu";
    productIDField.style.display = "block";
    document.getElementById("productID").value = productID;
    document.getElementById("productName").value = productName;
    document.getElementById("price").value = price;
    document.getElementById("priceImport").value = priceImport;
    document.getElementById("type").value = type;
    document.getElementById("status").value = status;
    document.getElementById("description").value = description;
  }
  dialog.show();
}
