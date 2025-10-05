document.addEventListener('DOMContentLoaded', () => {
    const loginError = document.getElementById('login-error');

    // Verifica se a URL atual contém "?error=true"
    const params = new URLSearchParams(window.location.search);
    if (params.has('error')) {
        loginError.textContent = "Email ou senha inválidos. Tente novamente.";
    }
});