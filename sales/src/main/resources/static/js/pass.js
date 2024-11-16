document.addEventListener("DOMContentLoaded", function () {
    const passwordInput = document.getElementById("newPassword");
    const togglePassword = document.getElementById("togglePassword");
    const toggleIcon = togglePassword.querySelector("img");
  
    togglePassword.addEventListener("click", function () {
      if (passwordInput.type === "password") {
        passwordInput.type = "text"; 
        toggleIcon.src = "/img/eye_hide.png"; 
        toggleIcon.alt = "hide visibility";
      } else {
        passwordInput.type = "password"; 
        toggleIcon.src = "/img/eye_show.png"; 
        toggleIcon.alt = "show visibility";
      }
    });
  });
  