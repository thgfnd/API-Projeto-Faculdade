document.addEventListener('DOMContentLoaded', () => {
    const cardContainer = document.getElementById('vehicle-card-container');
    const logoutBtn = document.getElementById('logout-btn');
    const searchInput = document.getElementById('search-cpf'); // Campo de busca

    // Modais
    const editModal = document.getElementById('edit-modal');
    const editForm = document.getElementById('edit-vehicle-form');
    const editCancelBtn = document.getElementById('edit-cancel-btn');

    const detailsModal = document.getElementById('details-modal');
    const detailsTitle = document.getElementById('details-title');
    const detailsOwner = document.getElementById('details-owner');
    const detailsVehicle = document.getElementById('details-vehicle');
    const detailsOil = document.getElementById('details-oil');
    const detailsCancelBtn = document.getElementById('details-cancel-btn');

    // Armazena todos os veículos para o filtro
    let allVehicles = [];

    async function fetchAllVehicles() {
        try {
            const response = await fetch('/api/veiculos');
            if (!response.ok) {
                if (response.status === 401 || response.status === 403) window.location.href = '/';
                return;
            }

            allVehicles = await response.json(); // Salva a lista completa
            renderVehicles(allVehicles); // Chama a nova função de renderização

        } catch (error) {
            console.error('Erro ao buscar veículos:', error);
        }
    }

    // NOVA FUNÇÃO para renderizar os cards
    function renderVehicles(vehicles) {
        cardContainer.innerHTML = ''; // Limpa o container

        vehicles.forEach(vehicle => {
            // Lógica para definir o status (baseado no DTO)
            let statusClass = 'status-em-dia';
            let statusText = 'Em Dia';
            // Verifica se a string de status contém o aviso
            if (vehicle.statusOleo && vehicle.statusOleo.includes('⚠️')) {
                statusClass = 'status-pendente';
                statusText = 'Pendente';
            }

            const card = document.createElement('div');
            card.className = 'vehicle-card';
            // Adiciona o CPF do usuário ao card como um data-attribute (só números)
            card.setAttribute('data-cpf', vehicle.usuario.cpf.replace(/[^0-9]/g, ''));

            card.innerHTML = `
                <h3>${vehicle.modelo} (${vehicle.ano})</h3>
                <p><strong>Placa:</strong> ${vehicle.placa}</p>
                <p><strong>Dono:</strong> ${vehicle.usuario.nome}</p>
                <p><strong>CPF:</strong> ${vehicle.usuario.cpf}</p>
                <span class="status-tag ${statusClass}">${statusText}</span>
                <div class="card-actions">
                    <button class="details-btn" data-id="${vehicle.id}">Ver Detalhes</button>
                    <button class="edit-btn" data-id="${vehicle.id}">Editar</button>
                    <button class="delete-btn" data-id="${vehicle.id}">Excluir</button>
                </div>
            `;
            cardContainer.appendChild(card);
        });
    }

    // Event listener para a barra de busca
    searchInput.addEventListener('input', (e) => {
        const searchText = e.target.value.toLowerCase().replace(/[^0-9]/g, ''); // Pega só os números

        // Itera por todos os cards no DOM
        const cards = cardContainer.querySelectorAll('.vehicle-card');
        cards.forEach(card => {
            const cardCpf = card.getAttribute('data-cpf');

            if (cardCpf.includes(searchText)) {
                card.style.display = ''; // Mostra o card (volta ao padrão)
            } else {
                card.style.display = 'none'; // Esconde o card
            }
        });
    });

    // Listener de eventos nos cards (para os botões)
    cardContainer.addEventListener('click', async (e) => {
        const vehicleId = e.target.dataset.id;
        if (!vehicleId) return;

        // --- Botão Excluir ---
        if (e.target.classList.contains('delete-btn')) {
            if (confirm(`Tem certeza que deseja excluir o veículo?`)) {
                try {
                    const response = await fetch(`/api/veiculos/${vehicleId}`, { method: 'DELETE' });
                    if (response.ok) {
                        alert('Veículo excluído!');
                        fetchAllVehicles(); // Recarrega os cards
                    } else {
                        alert('Falha ao excluir.');
                    }
                } catch (error) { console.error('Erro ao excluir:', error); }
            }
        }

        // --- Botão Editar ---
        if (e.target.classList.contains('edit-btn')) {
            try {
                const response = await fetch(`/api/veiculos/${vehicleId}`);
                if (!response.ok) { alert('Não foi possível carregar dados para edição.'); return; }
                const vehicle = await response.json();

                document.getElementById('edit-vehicle-id').value = vehicle.id;
                document.getElementById('edit-placa').value = vehicle.placa;
                document.getElementById('edit-modelo').value = vehicle.modelo;
                document.getElementById('edit-ano').value = vehicle.ano;
                document.getElementById('edit-quilometragem').value = vehicle.quilometragem;
                editModal.style.display = 'block';
            } catch (error) { console.error('Erro ao buscar para edição:', error); }
        }

        // --- Botão Ver Detalhes ---
        if (e.target.classList.contains('details-btn')) {
            try {
                const vehicleResponse = await fetch(`/api/veiculos/${vehicleId}`);
                const statusResponse = await fetch(`/api/veiculos/${vehicleId}/status-oleo`);

                if (!vehicleResponse.ok || !statusResponse.ok) {
                    alert('Não foi possível carregar os detalhes do veículo.');
                    return;
                }

                const vehicle = await vehicleResponse.json();
                const statusText = await statusResponse.text();

                const dono = vehicle.usuario;
                const controle = vehicle.controleTrocaOleo;

                detailsTitle.textContent = `Detalhes de: ${vehicle.modelo} (${vehicle.placa})`;

                detailsOwner.innerHTML = `
                    <p><strong>Nome:</strong> ${dono.nome}</p>
                    <p><strong>Email:</strong> ${dono.email}</p>
                    <p><strong>CPF:</strong> ${dono.cpf}</p>
                    <p><strong>Celular:</strong> ${dono.celular}</p>
                `;

                detailsVehicle.innerHTML = `
                    <p><strong>Placa:</strong> ${vehicle.placa}</p>
                    <p><strong>Modelo:</strong> ${vehicle.modelo}</p>
                    <p><strong>Ano:</strong> ${vehicle.ano}</p>
                    <p><strong>KM Atual:</strong> ${vehicle.quilometragem} km</p>
                `;

                // Garante que o objeto de controle existe antes de tentar acessá-lo
                if (controle) {
                    detailsOil.innerHTML = `
                        <p><strong>Status:</strong> ${statusText.replace(/\n/g, '<br>')}</p>
                        <p><strong>Última Troca (Data):</strong> ${controle.dataUltimaTroca}</p>
                        <p><strong>Última Troca (KM):</strong> ${controle.kmUltimaTroca} km</p>
                        <p><strong>Intervalo (KM):</strong> ${controle.intervaloKm} km</p>
                        <p><strong>Intervalo (Meses):</strong> ${controle.intervaloMeses} meses</p>
                    `;
                } else {
                    detailsOil.innerHTML = "<p>Controle de óleo não configurado para este veículo.</p>";
                }

                detailsModal.style.display = 'block';

            } catch (error) { console.error('Erro ao buscar detalhes:', error); }
        }
    });

    // --- Lógica do Modal de Edição ---
    editForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const vehicleId = document.getElementById('edit-vehicle-id').value;
        const updatedVehicle = {
            placa: document.getElementById('edit-placa').value,
            modelo: document.getElementById('edit-modelo').value,
            ano: parseInt(document.getElementById('edit-ano').value),
            quilometragem: parseInt(document.getElementById('edit-quilometragem').value),
        };
        try {
            const response = await fetch(`/api/veiculos/${vehicleId}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(updatedVehicle)
            });
            if (response.ok) {
                alert('Veículo atualizado!');
                editModal.style.display = 'none';
                fetchAllVehicles(); // Recarrega os cards
            } else { alert('Falha ao atualizar.'); }
        } catch (error) { console.error('Erro ao atualizar:', error); }
    });

    editCancelBtn.addEventListener('click', () => { editModal.style.display = 'none'; });

    // --- Lógica do Novo Modal de Detalhes ---
    detailsCancelBtn.addEventListener('click', () => {
        detailsModal.style.display = 'none';
    });

    // --- Logout ---
    logoutBtn.addEventListener('click', () => { document.getElementById('logout-form').submit(); });

    // --- Chamada Inicial ---
    fetchAllVehicles(); // Carrega os cards de veículos
});