//Una vez que el DOM cargó por completo:
document.addEventListener('DOMContentLoaded', () =>
{
    //Ante un clic en el botón de aplicar determinado ordenamiento:
    document.getElementById('applyOrderButton').addEventListener('click', () =>
    {
		//Obtenemos el criterio de ordenamiento elegido y el de filtrado:
        const order = document.querySelector("select[name='order']").value; 
        const enabled = document.querySelector("input[name='enabled']:checked") ? document.querySelector("input[name='enabled']:checked").value : 'all';

        //Realizamos la consulta para obtener los clientes:
        fetch(`/customer/customers/filter?order=${order}&enabled=${enabled}`, {
            method: 'GET'
        })
        //Pasamos la respuesta a texto:
        .then(response => response.text())
        .then(data => {
            //Actualizamos los clientes con los que obtuvimos:
            document.getElementById('customerTable').innerHTML = data;
        })
        .catch(error => console.error("Error en la solicitud Fetch:", error));
    });

    //Ante un clic en el botón de aplicar el filtro:
    document.getElementById('applyFilterButton').addEventListener('click', () => 
    {
		//Obtenemos el criterio de ordenamiento y el de filtrado elegido:
        const order = document.querySelector("select[name='order']").value;
        const enabled = document.querySelector("input[name='enabled']:checked").value;

        //Realizamos la consulta para obtener los clientes:
        fetch(`/customer/customers/filter?order=${order}&enabled=${enabled}`, {
            method: 'GET'
        })
        //Pasamos la respuesta a texto:
        .then(response => response.text())
        .then(data => {
            //Actualizamos los clientes con los que obtuvimos:
            document.getElementById('customerTable').innerHTML = data;
        })
        .catch(error => console.error("Error en la solicitud Fetch:", error));
    });
});

//Seleccionamos el contenedor de la tabla de clientes y escuchamos eventos de clic en él:
document.getElementById("customerTableContainer").addEventListener("click", (event) =>
{
	//Si el clic se hizo sobre algún botón de cambiar estado:
	if(event.target.classList.contains("change-status-btn"))
	{
    	const button = event.target; //Obtenemos el botón clicado.
    	const memberId = button.getAttribute("data-member-id"); //Obtenemos el id del cliente.
        const enabled = button.getAttribute("data-current-status"); //Obtenemos su estado actual.

        //Cambiamos el estado del cliente:
        fetch(`/customer/changeEnabled/${memberId}/${enabled}`, {
            method: 'POST'
        })
        //Pasamos a JSON la respuesta del controlador:
        .then(response => response.json())
        .then(data => 
        {
			//Si no hubo errores:
            if(data.success) 
            {   
				//Obtenemos el nuevo estado del cliente:
				const newStatus = data.newStatus;
				
				//Obtenemos la fila del cliente:
				const row = document.getElementById(`row-${memberId}`);
				
				//Obtenemos el valor actual del filtro:
                const currentFilter = document.querySelector("input[name='enabled']:checked").value;
                
                //Si está en todos los clientes:
                if(currentFilter === "all")
               	{	
					//Obtenemos la celda donde va el valor de 'enabled':
    				const enabledCell = row.querySelector(".enabled-status");
					enabledCell.textContent = newStatus; //Actualiza el estado del cliente en la tabla.
			  
			        button.setAttribute("data-current-status", newStatus); //Actualizamos el estado del cliente en el botón.	
				}
				else
				{
					//Eliminamos la fila porque no coincide con el filtro aplicado:
            		row.remove();
				}
            }
            else
            {
                console.error("Error al cambiar el estado:", data.error);
            }
        })
        .catch(error => console.error("Error en la solicitud Fetch:", error));
    }
});
