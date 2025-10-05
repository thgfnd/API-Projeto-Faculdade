document.addEventListener('DOMContentLoaded', () => {
    const welcomeMessage = document.getElementById('client-welcome');
    const vehicleList = document.getElementById('client-vehicle-list');
    const logoutBtn = document.getElementById('logout-btn');
    const addMyVehicleForm = document.getElementById('add-my-vehicle-form');
    const detailsModal = document.getElementById('details-modal');
    const detailsTitle = document.getElementById('details-title');
    const detailsOilStatus = document.getElementById('details-oil-status');
    const editIntervaloKm = document.getElementById('edit-intervalo-km');
    const editIntervaloMeses = document.getElementById('edit-intervalo-meses');
    const saveIntervalsBtn = document.getElementById('save-intervals-btn');
    const updateKmInput = document.getElementById('update-km-input');
    const updateKmBtn = document.getElementById('update-km-btn');
    const registerOilChangeBtn = document.getElementById('register-oil-change-btn');
    const detailsCancelBtn = document.getElementById('details-cancel-btn');
    let currentVehicleId = null;

    async function fetchClientData() {
        try {
            const userResponse = await fetch('/api/user/me');
            if (userResponse.ok) { const user = await userResponse.json(); welcomeMessage.textContent = `Bem-vindo, ${user.nome}!`; }
            const vehiclesResponse = await fetch('/api/veiculos/meus-veiculos');
            if (!vehiclesResponse.ok) { if (vehiclesResponse.status === 401 || vehiclesResponse.status === 403) window.location.href = '/'; return; }
            const vehicles = await vehiclesResponse.json();
            vehicleList.innerHTML = '';
            vehicles.forEach(vehicle => {
                const li = document.createElement('li');
                li.innerHTML = `
                    <span>${vehicle.modelo} (${vehicle.ano}) - Placa: ${vehicle.placa}</span>
                    <button class="details-btn" data-id="${vehicle.id}" data-title="${vehicle.modelo} (${vehicle.placa})">Ver Detalhes</button>
                `;
                vehicleList.appendChild(li);
            });
        } catch (error) { console.error('Erro ao buscar dados do cliente:', error); }
    }

    addMyVehicleForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const carroData = {
            placa: document.getElementById('placa-cliente').value,
            modelo: document.getElementById('modelo-cliente').value,
            ano: parseInt(document.getElementById('ano-cliente').value),
            quilometragem: parseInt(document.getElementById('quilometragem-cliente').value),
            dataUltimaTroca: document.getElementById('dataUltimaTroca-cliente').value,
            kmUltimaTroca: parseInt(document.getElementById('kmUltimaTroca-cliente').value),
            intervaloKm: parseInt(document.getElementById('intervaloKm-cliente').value),
            intervaloMeses: parseInt(document.getElementById('intervaloMeses-cliente').value)
        };
        try {
            const response = await fetch('/api/veiculos/meus-veiculos', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(carroData)
            });
            if (response.ok) {
                alert('Seu veículo foi adicionado com sucesso!');
                addMyVehicleForm.reset();
                fetchClientData();
            } else { alert('Falha ao adicionar seu veículo.'); }
        } catch (error) { console.error('Erro ao adicionar veículo:', error); }
    });

    vehicleList.addEventListener('click', async (e) => {
        if (e.target.classList.contains('details-btn')) {
            currentVehicleId = e.target.dataset.id;
            detailsTitle.textContent = e.target.dataset.title;
            try {
                const carResponse = await fetch(`/api/veiculos/${currentVehicleId}`);
                const statusResponse = await fetch(`/api/veiculos/${currentVehicleId}/status-oleo`);
                if (carResponse.ok && statusResponse.ok) {
                    const vehicle = await carResponse.json();
                    const statusText = await statusResponse.text();
                    detailsOilStatus.textContent = statusText;
                    editIntervaloKm.value = vehicle.controleTrocaOleo.intervaloKm;
                    editIntervaloMeses.value = vehicle.controleTrocaOleo.intervaloMeses;
                    detailsModal.style.display = 'block';
                } else {
                    alert('Não foi possível carregar os detalhes do veículo.');
                }
            } catch (error) { console.error('Erro ao buscar detalhes:', error); }
        }
    });

    saveIntervalsBtn.addEventListener('click', async () => {
        const newIntervaloKm = editIntervaloKm.value;
        const newIntervaloMeses = editIntervaloMeses.value;
        if (!newIntervaloKm || !newIntervaloMeses || !currentVehicleId) return;
        try {
            const response = await fetch(`/api/veiculos/${currentVehicleId}/intervalos-troca?intervaloKm=${newIntervaloKm}&intervaloMeses=${newIntervaloMeses}`, {
                method: 'PUT'
            });
            if (response.ok) {
                alert('Intervalos atualizados com sucesso!');
                detailsModal.style.display = 'none';
            } else {
                alert('Falha ao atualizar os intervalos.');
            }
        } catch (error) { console.error('Erro ao salvar intervalos:', error); }
    });

    updateKmBtn.addEventListener('click', async () => {
        const kmAtual = updateKmInput.value;
        if (!kmAtual || !currentVehicleId) return;
        try {
            const response = await fetch(`/api/veiculos/${currentVehicleId}/quilometragem?kmAtual=${kmAtual}`, { method: 'PUT' });
            if (response.ok) {
                alert('Quilometragem atualizada!');
                updateKmInput.value = '';
                detailsModal.style.display = 'none';
                fetchClientData();
            } else { alert('Falha ao atualizar a quilometragem.'); }
        } catch (error) { console.error('Erro ao atualizar quilometragem:', error); }
    });

    registerOilChangeBtn.addEventListener('click', async () => {
        if (!currentVehicleId) return;
        try {
            const response = await fetch(`/api/veiculos/${currentVehicleId}/registrar-oleo`, { method: 'POST' });
            if (response.ok) {
                alert('Troca de óleo registrada com sucesso!');
                detailsModal.style.display = 'none';
                fetchClientData();
            } else {
                alert('Falha ao registrar a troca de óleo.');
            }
        } catch (error) { console.error('Erro ao registrar troca de óleo:', error); }
    });

    detailsCancelBtn.addEventListener('click', () => {
        detailsModal.style.display = 'none';
        currentVehicleId = null;
    });

    logoutBtn.addEventListener('click', () => {
        document.getElementById('logout-form').submit();
    });

    fetchClientData();
});