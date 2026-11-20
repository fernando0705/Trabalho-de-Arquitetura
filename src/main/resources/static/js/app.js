const API = '/api';

let paciente = null;
let medicos = [];
let horarioSelecionado = null;

const $ = (id) => document.getElementById(id);

function mostrarMensagem(id, texto, tipo) {
    const el = $(id);
    el.textContent = texto;
    el.className = 'mensagem ' + tipo;
}

function esconderMensagem(id) {
    $(id).className = 'mensagem oculto';
}

function hoje() {
    return new Date().toISOString().split('T')[0];
}

async function entrar() {
    const cpfDigitado = $('cpf').value.replace(/\D/g, '');

    if (!cpfDigitado) {
        mostrarMensagem('login-erro', 'Informe o CPF.', 'erro');
        return;
    }

    try {
        const resposta = await fetch(API + '/pacientes');
        const pacientes = await resposta.json();

        const encontrado = pacientes.find(
            (p) => (p.cpf || '').replace(/\D/g, '') === cpfDigitado
        );

        if (!encontrado) {
            mostrarMensagem('login-erro', 'CPF não encontrado.', 'erro');
            return;
        }

        paciente = encontrado;
        $('nome-paciente').textContent = paciente.nome;
        $('tela-login').classList.add('oculto');
        $('tela-app').classList.remove('oculto');
        esconderMensagem('login-erro');
        $('cpf').value = '';

        await carregarMedicos();
        $('data').value = hoje();
        $('data-agenda').value = hoje();
        await carregarHorarios();
        await carregarAgenda();
    } catch (e) {
        mostrarMensagem('login-erro', 'Erro ao conectar com o servidor.', 'erro');
    }
}

function sair() {
    paciente = null;
    horarioSelecionado = null;
    $('tela-app').classList.add('oculto');
    $('tela-login').classList.remove('oculto');
    $('lista-consultas').innerHTML = '<p class="vazio">Nenhuma consulta para esta data.</p>';
}

async function carregarMedicos() {
    const resposta = await fetch(API + '/medicos');
    medicos = await resposta.json();

    $('medico').innerHTML = medicos
        .map((m) => '<option value="' + m.id + '">' + m.nome + ' — ' + (m.especialidade || '') + '</option>')
        .join('');
}

function nomeMedico(id) {
    const m = medicos.find((x) => x.id === id);
    return m ? m.nome : 'Médico';
}

function gradeDeHorarios(periodo) {
    const inicio = periodo === 'manha' ? 8 : 14;
    const fim = periodo === 'manha' ? 12 : 18;
    const lista = [];

    for (let h = inicio; h < fim; h++) {
        lista.push(String(h).padStart(2, '0') + ':00');
        lista.push(String(h).padStart(2, '0') + ':30');
    }

    return lista;
}

function somarMinutos(hora, minutos) {
    const [h, m] = hora.split(':').map(Number);
    const total = h * 60 + m + minutos;

    return String(Math.floor(total / 60)).padStart(2, '0') + ':' +
        String(total % 60).padStart(2, '0');
}

async function carregarHorarios() {
    const medicoId = $('medico').value;
    const data = $('data').value;
    const periodo = $('periodo').value;
    const container = $('horarios');

    horarioSelecionado = null;
    $('btn-agendar').disabled = true;

    if (!medicoId || !data) {
        container.innerHTML = '<p class="vazio">Escolha médico, data e período.</p>';
        return;
    }

    try {
        const resposta = await fetch(API + '/consultas/agenda?data=' + data);
        const consultas = await resposta.json();

        const ocupados = consultas
            .filter((c) => c.medicoId === medicoId && c.status === 'AGENDADA')
            .map((c) => c.inicio.substring(11, 16));

        const livres = gradeDeHorarios(periodo).filter((h) => !ocupados.includes(h));

        if (livres.length === 0) {
            container.innerHTML = '<p class="vazio">Nenhum horário livre neste período.</p>';
            return;
        }

        container.innerHTML = livres
            .map((h) => '<button class="horario" data-hora="' + h + '">' + h + '</button>')
            .join('');

        container.querySelectorAll('.horario').forEach((botao) => {
            botao.addEventListener('click', () => {
                container.querySelectorAll('.horario').forEach((b) => b.classList.remove('selecionado'));
                botao.classList.add('selecionado');
                horarioSelecionado = botao.dataset.hora;
                $('btn-agendar').disabled = false;
            });
        });
    } catch (e) {
        container.innerHTML = '<p class="vazio">Erro ao carregar horários.</p>';
    }
}

async function agendar() {
    if (!horarioSelecionado || !paciente) return;

    const data = $('data').value;
    const inicio = data + 'T' + horarioSelecionado + ':00';
    const fim = data + 'T' + somarMinutos(horarioSelecionado, 30) + ':00';

    const corpo = {
        pacienteId: paciente.id,
        medicoId: $('medico').value,
        inicio: inicio,
        fim: fim
    };

    try {
        const resposta = await fetch(API + '/consultas', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(corpo)
        });

        if (resposta.status === 201) {
            mostrarMensagem('agendar-mensagem', 'Consulta agendada com sucesso.', 'sucesso');
            await carregarHorarios();
            await carregarAgenda();
        } else {
            const erro = await resposta.json().catch(() => ({}));
            mostrarMensagem('agendar-mensagem', erro.erro || 'Não foi possível agendar.', 'erro');
            await carregarHorarios();
        }
    } catch (e) {
        mostrarMensagem('agendar-mensagem', 'Erro ao conectar com o servidor.', 'erro');
    }
}

async function carregarAgenda() {
    const data = $('data-agenda').value;
    const container = $('lista-consultas');

    if (!data) {
        container.innerHTML = '<p class="vazio">Escolha uma data.</p>';
        return;
    }

    try {
        const resposta = await fetch(API + '/consultas/agenda?data=' + data);
        const consultas = await resposta.json();

        if (!consultas.length) {
            container.innerHTML = '<p class="vazio">Nenhuma consulta para esta data.</p>';
            return;
        }

        consultas.sort((a, b) => a.inicio.localeCompare(b.inicio));

        container.innerHTML = consultas.map((c) => {
            const hora = c.inicio.substring(11, 16);
            const botao = c.status === 'AGENDADA'
                ? '<button class="btn-cancelar" data-id="' + c.id + '">Cancelar</button>'
                : '';

            return '<div class="consulta">' +
                '<div class="consulta-info">' +
                '<strong>' + hora + ' — ' + nomeMedico(c.medicoId) + '</strong>' +
                '<span class="status ' + c.status + '">' + c.status + '</span>' +
                '</div>' +
                botao +
                '</div>';
        }).join('');

        container.querySelectorAll('.btn-cancelar').forEach((botao) => {
            botao.addEventListener('click', () => cancelar(botao.dataset.id));
        });
    } catch (e) {
        container.innerHTML = '<p class="vazio">Erro ao carregar a agenda.</p>';
    }
}

async function cancelar(id) {
    try {
        const resposta = await fetch(API + '/consultas/' + id, { method: 'DELETE' });

        if (resposta.status === 204) {
            mostrarMensagem('agenda-mensagem', 'Consulta cancelada.', 'sucesso');
        } else {
            const erro = await resposta.json().catch(() => ({}));
            mostrarMensagem('agenda-mensagem', erro.erro || 'Não foi possível cancelar.', 'erro');
        }

        await carregarAgenda();
        await carregarHorarios();
    } catch (e) {
        mostrarMensagem('agenda-mensagem', 'Erro ao conectar com o servidor.', 'erro');
    }
}

function configurarAbas() {
    document.querySelectorAll('.aba').forEach((aba) => {
        aba.addEventListener('click', () => {
            document.querySelectorAll('.aba').forEach((a) => a.classList.remove('ativa'));
            aba.classList.add('ativa');

            const alvo = aba.dataset.aba;
            $('aba-agendar').classList.toggle('oculto', alvo !== 'agendar');
            $('aba-agenda').classList.toggle('oculto', alvo !== 'agenda');

            if (alvo === 'agenda') carregarAgenda();
        });
    });
}

function configurarEventos() {
    $('btn-entrar').addEventListener('click', entrar);
    $('cpf').addEventListener('keydown', (e) => {
        if (e.key === 'Enter') entrar();
    });
    $('btn-sair').addEventListener('click', sair);
    $('btn-agendar').addEventListener('click', agendar);
    $('medico').addEventListener('change', carregarHorarios);
    $('data').addEventListener('change', carregarHorarios);
    $('periodo').addEventListener('change', carregarHorarios);
    $('data-agenda').addEventListener('change', carregarAgenda);
}

configurarAbas();
configurarEventos();