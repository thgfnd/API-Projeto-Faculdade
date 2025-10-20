document.addEventListener('DOMContentLoaded', () => {
    const cadastroForm = document.getElementById('cadastro-form');
    const errorMessage = document.getElementById('error-message');

    cadastroForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        errorMessage.textContent = ''; // Limpa mensagens de erro anteriores

        const nome = document.getElementById('nome').value;
        const cpf = document.getElementById('cpf').value;
        const celular = document.getElementById('celular').value;
        const email = document.getElementById('email').value;
        const senha = document.getElementById('senha').value;
        const confirmarSenha = document.getElementById('confirmarSenha').value;

        // Validacao e cadastro
         const regexApenasNumeros = /^[0-9]+$/;

          if (!regexApenasNumeros.test(cpf) || cpf.length !== 11) {
                     errorMessage.textContent = 'CPF inválido! Por favor, digite 11 números.';
                     return;
                 }

                 if (!regexApenasNumeros.test(celular) || (celular.length < 10 || celular.length > 11)) {
                     errorMessage.textContent = 'Celular inválido! Digite de 10 a 11 números, incluindo o DDD.';
                     return;
                 }



        if (senha !== confirmarSenha) {
            errorMessage.textContent = 'As senhas não coincidem!';
            return;
        }

        const userData = { nome, cpf, celular, email, senha };

        try {
            const response = await fetch('/api/usuarios/registrar', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(userData),
            });

            if (response.ok) {
                alert('Cadastro realizado com sucesso! Você será redirecionado para a tela de login.');
                window.location.href = '/'; // Redireciona para o login
            } else {
                const errorText = await response.text();
                errorMessage.textContent = errorText;
            }
        } catch (error) {
            console.error('Erro ao realizar cadastro:', error);
            errorMessage.textContent = 'Ocorreu um erro no servidor. Tente novamente mais tarde.';
        }
    });
});