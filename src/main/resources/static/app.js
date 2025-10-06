document.addEventListener('DOMContentLoaded', () => {
    const loginForm = document.getElementById('login-form');
    const loginError = document.getElementById('login-error');

    // Verifica se há um parâmetro 'error=true' na URL após um login falho
    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.get('error')) {
        loginError.textContent = 'Usuário ou senha inválidos.';
    }

    // Este listener para o formulário de login agora apenas impede o envio padrão
    // se você quisesse uma validação extra antes de enviar.
    // Como estamos usando o formLogin do Spring Security, o submit direto funciona bem.
    loginForm.addEventListener('submit', (e) => {
        // Nada a fazer aqui por enquanto, o Spring Security lida com o submit.
        // e.preventDefault(); // Descomente se for fazer submit via Fetch API.
    });
});