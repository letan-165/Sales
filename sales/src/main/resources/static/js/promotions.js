// Hiển thị thông báo
function showNotification(message, isError = false) {
  const notification = document.getElementById("notification");
  notification.innerHTML = `<div class="alert ${
    isError ? "alert-danger" : "alert-success"
  }">${message}</div>`;
  setTimeout(() => (notification.innerHTML = ""), 3000);
}

// Mở dialog (Thêm/Sửa)
function openDialog(
  action,
  discountID = null,
  discount = null,
  startTime = null,
  endTime = null,
  minimum = null,
  quantity = null
) {
  const dialog = new bootstrap.Modal(document.getElementById("dialog"));
  const dialogLabel = document.getElementById("dialogLabel");
  const confirmButton = document.getElementById("confirmButton");

  if (action === "add") {
    dialogLabel.textContent = "Thêm mới khuyến mãi";
    confirmButton.textContent = "Thêm";
    document.getElementById("addEditContent").style.display = "block";
    document.getElementById("deleteContent").style.display = "none";

    // Reset các trường nhập liệu
    document.getElementById("discountID").value = "";
    document.getElementById("discount").value = "";
    document.getElementById("startTime").value = "";
    document.getElementById("endTime").value = "";
    document.getElementById("minimum").value = "";
    document.getElementById("quantity").value = "";
  } else if (action === "edit") {
    dialogLabel.textContent = "Sửa khuyến mãi";
    confirmButton.textContent = "Lưu";
    document.getElementById("addEditContent").style.display = "block";
    document.getElementById("deleteContent").style.display = "none";

    document.getElementById("discountID").value = discountID;
    document.getElementById("discount").value = discount;
    document.getElementById("startTime").value = startTime;
    document.getElementById("endTime").value = endTime;
    document.getElementById("minimum").value = minimum;
    document.getElementById("quantity").value = quantity;
  } else if (action === "delete") {
    dialogLabel.textContent = "Xóa khuyến mãi";
    confirmButton.textContent = "Xóa";
    document.getElementById("addEditContent").style.display = "none";
    document.getElementById("deleteContent").style.display = "block";
  }

  dialog.show();
}

// Tìm kiếm khuyến mãi
function searchInvoices() {
const searchInput = document.getElementById("searchInput").value.toLowerCase();
const rows = document.querySelectorAll("#promotionList tr");

rows.forEach(row => {
    const cells = row.getElementsByTagName("td");
    let isMatch = false;

    for (let i = 0; i < cells.length; i++) {
    const cellText = cells[i].innerText.toLowerCase();
    if (cellText.includes(searchInput)) {
        isMatch = true;
        break;
    }
    }

    row.style.display = isMatch ? "" : "none";
});
}
