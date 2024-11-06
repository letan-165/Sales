document.addEventListener("DOMContentLoaded", function () {
  const passwordInput = document.getElementById("password");
  const togglePassword = document.getElementById("togglePassword");

  togglePassword.addEventListener("click", function () {
    const isPassword = passwordInput.type === "password";
    passwordInput.type = isPassword ? "text" : "password";

    togglePassword.querySelector("img").src = isPassword
      ? "img/eye_hide.png"
      : "img/eye_show.png";
  });
});

const modal = document.getElementById("forgotPasswordModal");
const forgotPasswordLink = document.getElementById("forgotPasswordLink");
const closeModal = document.getElementById("closeModal");

forgotPasswordLink.onclick = function (event) {
  event.preventDefault();
  modal.style.display = "block";
};

closeModal.onclick = function () {
  modal.style.display = "none";
};

window.onclick = function (event) {
  if (event.target == modal) {
    modal.style.display = "none";
  }
};
