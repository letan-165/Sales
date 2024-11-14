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
  const input = document.getElementById("searchInput").value.toLowerCase();
  const filterWords = input.split(" "); // Tách từ khóa thành mảng
  const table = document.getElementById("userList");
  const rows = table.getElementsByTagName("tr");

  for (let i = 0; i < rows.length; i++) {
      const rowText = rows[i].innerText.toLowerCase();
      const match = filterWords.some(word => rowText.includes(word)); // Kiểm tra nếu bất kỳ từ nào có trong hàng

      if (match || input === "") {
          rows[i].style.display = ""; // Hiển thị hàng nếu khớp hoặc nếu ô tìm kiếm trống
      } else {
          rows[i].style.display = "none"; // Ẩn hàng nếu không khớp
      }
  }
}
