document.addEventListener('DOMContentLoaded', () => {
    const vehicleList = document.getElementById('vehicle-list');
    const addForm = document.getElementById('add-vehicle-form');
    const logoutBtn = document.getElementById('logout-btn');
    const usuarioSelect = document.getElementById('usuario-select');
    const editModal = document.getElementById('edit-modal');
    const editForm = document.getElementById('edit-vehicle-form');
    const editCancelBtn = document.getElementById('edit-cancel-btn');

    async function fetchAndPopulateUsers() {
        try {
            // Assumindo que a rota /api/usuarios lista todos os usuários (necessário no UsuarioController)
            const response = await fetch('/api/usuarios');
            if (!response.ok) { console.error('Falha ao carregar usuários.'); return; }
            const users = await response.json();
            usuarioSelect.innerHTML = '<option value="" disabled selected>Selecione o Dono do Veículo</option>';
            users.forEach(user => {
                const option = document.createElement('option');
                option.value = user.id;
                option.textContent = `${user.nome} (${user.email})`;
                usuarioSelect.appendChild(option);
            });
        } catch (error) { console.error('Erro ao carregar usuários:', error); }
    }

    async function fetchAllVehicles() {
        try {
            const response = await fetch('/api/veiculos');
            if (!response.ok) { if (response.status === 401 || response.status === 403) window.location.href = '/'; return; }
            const vehicles = await response.json();
            vehicleList.innerHTML = '';
            vehicles.forEach(vehicle => {
                const li = document.createElement('li');
                li.innerHTML = `
                    <span>${vehicle.modelo} (${vehicle.ano}) - Dono: <strong>${vehicle.usuario.nome}</strong></span>
                    <div>
                        <button class="edit-btn" data-id="${vehicle.id}">Editar</button>
                        <button class="delete-btn" data-id="${vehicle.id}">Excluir</button>
                    </div>
                `;
                vehicleList.appendChild(li);
            });
        } catch (error) { console.error('Erro ao buscar veículos:', error); }
    }

    addForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const carroData = {
            placa: document.getElementById('placa').value,
            modelo: document.getElementById('modelo').value,
            ano: parseInt(document.getElementById('ano').value),
            quilometragem: parseInt(document.getElementById('quilometragem').value),
            dataUltimaTroca: document.getElementById('dataUltimaTroca').value,
            kmUltimaTroca: parseInt(document.getElementById('kmUltimaTroca').value),
            intervaloKm: parseInt(document.getElementById('intervaloKm').value),
            intervaloMeses: parseInt(document.getElementById('intervaloMeses').value)
        };
        const usuarioId = usuarioSelect.value;
        if (!usuarioId) { alert('Selecione o dono.'); return; }

        try {
            const response = await fetch(`/api/veiculos?usuarioId=${usuarioId}`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(carroData)
            });
            if (response.ok) {
                alert('Veículo adicionado!');
                addForm.reset();
                usuarioSelect.selectedIndex = 0;
                fetchAllVehicles();
            } else {
                const errorText = await response.text();
                alert(`Falha ao adicionar: ${errorText}`);
            }
        } catch (error) {
            console.error('Erro ao adicionar:', error);
        }
    });

    // O RESTANTE DO ARQUIVO CONTINUA IGUAL...
    vehicleList.addEventListener('click', async (e) => {
        const vehicleId = e.target.dataset.id;
        if (!vehicleId) return;
        if (e.target.classList.contains('delete-btn')) {
            if (confirm(`Tem certeza que deseja excluir o veículo?`)) {
                try {
                    const response = await fetch(`/api/veiculos/${vehicleId}`, { method: 'DELETE' });
                    if (response.ok) { alert('Veículo excluído!'); fetchAllVehicles(); } else { alert('Falha ao excluir.'); }
                } catch (error) { console.error('Erro ao excluir:', error); }
            }
        }
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
    });

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
                fetchAllVehicles();
            } else { alert('Falha ao atualizar.'); }
        } catch (error) { console.error('Erro ao atualizar:', error); }
    });

    editCancelBtn.addEventListener('click', () => { editModal.style.display = 'none'; });

    logoutBtn.addEventListener('click', () => { document.getElementById('logout-form').submit(); });

    fetchAllVehicles();
    fetchAndPopulateUsers();
});