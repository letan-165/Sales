function openDialog(
  action,
  userId = null,
  email = null,
  password = null,
  phone = null,
  userName = null,
  role = null
) {
  const dialog = new bootstrap.Modal(document.getElementById("dialog"), {});
  const dialogLabel = document.getElementById("dialogLabel");
  const confirmButton = document.getElementById("confirmButton");
  const userIDField = document.getElementById("userID");
  const passwordField = document.getElementById("passWord");

  document.getElementById("addEditContent").style.display = "block";
  document.getElementById("deleteContent").style.display = "none";

  if (action === "add") {
    dialogLabel.textContent = "Thêm mới";
    confirmButton.textContent = "Thêm";
    document.getElementById("userID").value = "";
    document.getElementById("email").value = "";
    document.getElementById("passWord").value = "";
    document.getElementById("phone").value = "";
    document.getElementById("userName").value = "";
    document.getElementById("role").value = "";
    passwordField.type = "text";
    passwordField.readOnly = false;
    userIDField.readOnly = false;
  } else if (action === "edit") {
    dialogLabel.textContent = "Sửa người dùng";
    confirmButton.textContent = "Lưu";
    document.getElementById("userID").value = userId;
    document.getElementById("email").value = email;
    document.getElementById("passWord").value = password;
    document.getElementById("phone").value = phone;
    document.getElementById("userName").value = userName;
    document.getElementById("role").value = role;
    passwordField.type = "password";
    passwordField.readOnly = true;
    userIDField.readOnly = true;
  }

  dialog.show();
}
function searchUsers() {
  // Lấy giá trị tìm kiếm từ ô input và chuyển thành mảng từ
  const input = document.getElementById("searchInput").value.toLowerCase().split(" ");
  
  // Lấy tất cả các hàng trong bảng người dùng
  const rows = document.querySelectorAll("#list tr");

  // Duyệt qua từng hàng
  rows.forEach((row) => {
    // Lấy toàn bộ nội dung của hàng và chuyển thành chữ thường
    const rowText = row.innerText.toLowerCase();

    // Kiểm tra xem có từ nào trong chuỗi tìm kiếm xuất hiện trong hàng không
    const isMatch = input.every((term) => rowText.includes(term));

    // Nếu có từ khớp, hiển thị hàng; ngược lại, ẩn hàng
    row.style.display = isMatch ? "" : "none";
  });
}

