function showNotification(message, isError = false) {
  const notification = document.getElementById("notification");
  notification.innerHTML = `<div class="alert ${
    isError ? "alert-danger" : "alert-success"
  }">${message}</div>`;
  setTimeout(() => (notification.innerHTML = ""), 3000);
}

function openDialog(
  action,
  invoiceID = null,
  invoiceTime = null,
  totalAmount = null,
  status = null,
  description = null
) {
  const dialog = new bootstrap.Modal(document.getElementById("dialog"));
  const dialogLabel = document.getElementById("dialogLabel");
  const confirmButton = document.getElementById("confirmButton");

  if (action === "add") {
    dialogLabel.textContent = "Thêm mới hóa đơn";
    confirmButton.textContent = "Thêm";
    document.getElementById("addEditContent").style.display = "block";
    document.getElementById("deleteContent").style.display = "none";
    document.getElementById("styleInvoiceID").style.display = "none";

    document.getElementById("invoiceID").value = "";
    document.getElementById("invoiceTime").value = "";
    document.getElementById("totalAmount").value = "";
    document.getElementById("status").value = "";
    document.getElementById("description").value = "";
  } else if (action === "edit") {
    dialogLabel.textContent = "Sửa hóa đơn";
    confirmButton.textContent = "Lưu";
    document.getElementById("addEditContent").style.display = "block";
    document.getElementById("deleteContent").style.display = "none";
    document.getElementById("styleInvoiceID").style.display = "block";

    document.getElementById("invoiceID").value = invoiceID;
    document.getElementById("invoiceTime").value = invoiceTime;
    document.getElementById("totalAmount").value = totalAmount;
    document.getElementById("status").value = status;
    document.getElementById("description").value = description;
  }

  dialog.show();
}
// Xóa hóa đơn dựa trên mã hóa đơn
console.log(`Xóa hóa đơn: ${invoiceId}`);

const rows = document.querySelectorAll("#invoiceList tr");
rows.forEach((row) => {
  if (row.children[0].textContent === invoiceId) {
    row.remove();
  }
});
showNotification("Hóa đơn đã được xóa thành công!");

// Đóng dialog
bootstrap.Modal.getInstance(document.getElementById("dialog")).hide();
// Tìm kiếm hóa đơn
function searchInvoices() {
  const input = document.getElementById("searchInput").value.toLowerCase();
  const filterWords = input.split(" "); // Tách các từ tìm kiếm
  const table = document.getElementById("invoiceList");
  const rows = table.getElementsByTagName("tr");

  for (let i = 0; i < rows.length; i++) {
    const rowText = rows[i].innerText.toLowerCase();
    const match = filterWords.every(word => rowText.includes(word)); // Kiểm tra xem từng từ tìm kiếm có xuất hiện trong nội dung hàng không

    if (match) {
      rows[i].style.display = ""; // Hiển thị hàng nếu phù hợp
    } else {
      rows[i].style.display = "none"; // Ẩn hàng nếu không phù hợp
    }
  }
}
