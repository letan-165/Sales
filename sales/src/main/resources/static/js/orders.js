// Hiển thị thông báo
function showNotification(message, isError = false) {
    const notification = document.getElementById("notification");
    notification.innerHTML = `<div class="alert ${isError ? "alert-danger" : "alert-success"}">${message}</div>`;
    setTimeout(() => (notification.innerHTML = ""), 3000);
}

// Mở dialog (Thêm/Sửa)
function openDialog(action, orderID = null, productName = null, orderTime = null) {
    const dialog = new bootstrap.Modal(document.getElementById("dialog"));
    const dialogLabel = document.getElementById("dialogLabel");
    const confirmButton = document.getElementById("confirmButton");

    if (action === "add") {
        dialogLabel.textContent = "Thêm mới đơn hàng";
        confirmButton.textContent = "Xác nhận";
        document.getElementById("addEditContent").style.display = "block";
        document.getElementById("deleteContent").style.display = "none";
        document.getElementById("styleOrderID").style.display = "none";

        // Reset các trường trong form
        document.getElementById("orderID").value = "";
        document.getElementById("productName").value = "";
        document.getElementById("orderTime").value = "";
    } else if (action === "edit") {
        dialogLabel.textContent = "Sửa đơn hàng";
        confirmButton.textContent = "Lưu thay đổi";
        document.getElementById("addEditContent").style.display = "block";
        document.getElementById("deleteContent").style.display = "none";
        document.getElementById("styleOrderID").style.display = "block";

        // Gán giá trị cho các trường trong form
        document.getElementById("orderID").value = orderID;
        document.getElementById("productName").value = productName;
        document.getElementById("orderTime").value = orderTime;
    } else if (action === "delete") {
        dialogLabel.textContent = "Xóa đơn hàng";
        confirmButton.textContent = "Xóa";
        document.getElementById("addEditContent").style.display = "none";
        document.getElementById("deleteContent").style.display = "block";
    }

    dialog.show();
}

// Tìm kiếm đơn hàng
function searchOrders() {
    const searchInput = document.getElementById("searchInput").value.toLowerCase();
    const rows = document.querySelectorAll("#orderList tr");

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