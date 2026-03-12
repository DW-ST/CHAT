const API = "https://chat-production-4426.up.railway.app/api/solicitudes";

function enviar() {
    fetch(API, {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({
            usuario: document.getElementById("usuario").value,
            mensaje: document.getElementById("mensaje").value,
            prioridad: parseInt(document.getElementById("prioridad").value)
        })
    })
    .then(res => {
        if(!res.ok) throw new Error("Error en el POST");
        return res.json();
    })
    .then(data => {
        alert("Enviado correctamente");
        cargar();
    })
    .catch(err => {
        console.error(err);
        alert("Error al guardar");
    });
}

function cargar() {
    fetch(API + "/fifo")
    .then(res => res.json())
    .then(data => {

        let html = "";

        data.forEach(s => {

            let horas = s.horasTranscurridas;
            let clase = "";

            if (horas <= 1) {
                clase = "verde";
            } else if (horas > 1 && horas < 3) {
                clase = "naranja";
            } else {
                clase = "rojo";
            }

            html += `
                <div class="card ${clase}">
                    <b>${s.usuario ?? "Sin usuario"}</b><br>
                    ${s.mensaje ?? "Sin mensaje"}<br>
                    Tiempo: ${s.tiempoFormateado}<br>
                    Estado: ${s.estado}
            `;

            if (typeof ES_SOPORTE !== "undefined" && ES_SOPORTE && s.estado === "PENDIENTE") {
                html += `
                    <button onclick="atender(${s.id})">
                        Marcar como Atendida
                    </button>
                `;
            }

            html += `</div>`;
        });

        document.getElementById("lista").innerHTML = html;
    });
}

function calcularHoras(fecha){
    let inicio = new Date(fecha);
    let ahora = new Date();
    return (ahora - inicio) / 1000 / 60 / 60;
}

function atender(id) {

    const username = prompt("Usuario soporte:");
    const password = prompt("Contraseña:");

    fetch(API + "/atender/" + id, {
        method: "PUT",
        headers: {
            "Authorization": "Basic " + btoa(username + ":" + password)
        }
    })
    .then(res => {
        if (!res.ok) {
            alert("No autorizado");
            return;
        }
        return res.json();
    })
    .then(() => {
        cargar();
    });
}

setInterval(cargar, 5000);
cargar();