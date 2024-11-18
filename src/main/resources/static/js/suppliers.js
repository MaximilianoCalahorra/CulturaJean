//Una vez que el DOM cargó por completo:
document.addEventListener('DOMContentLoaded', () =>
{
    //Ante un clic en el botón de aplicar determinado ordenamiento:
    document.getElementById('applyOrderButton').addEventListener('click', () =>
    {
		//Obtenemos el criterio de ordenamiento elegido:
        const order = document.querySelector("select[name='order']").value;

        //Realizamos la consulta para obtener los proveedores:
        fetch(`/supplier/suppliers/filter?order=${order}`, {
            method: 'GET'
        })
        //Pasamos la respuesta a texto:
        .then(response => response.text())
        .then(data => {
            //Actualizamos los proveedores con los que obtuvimos:
            document.getElementById('supplierTable').innerHTML = data;
        })
        .catch(error => console.error("Error en la solicitud Fetch:", error));
    });
});
