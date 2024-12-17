//Importamos una funcionalidad que necesitamos:
import { getOrderValue } from "/js/general.js";

//Ids de los botones de aplicar ordenamiento y filtros:
const applyOrderButtonId = "applyOrderButton";
const applyFilterButtonId = "applyFilterButton";

//Name de la etiqueta que permite seleccionar el ordenamiento:
const orderName = "order";

//Criterio de ordenamiento por defecto:
const defaultOrder = "orderAscByLastName";

/* OBTENEMOS LOS CLIENTES FILTRADOS Y ORDENADOS */
async function filteredCustomers(order, enabled)
{
	//Hacemos la solicitud al controller:
	return fetch(`/customer/customers/filter?order=${order}&enabled=${enabled}`,{
		method: 'GET'
	})
	.then(response => 
    {
		//Si hubo algún error:
        if(!response.ok) 
        {
            throw new Error("Error in the response of server");
        }
        
        return response.json(); //Retornamos el JSON con los clientes.
    })
    .then(customers => customers)
    .catch(error => 
    {
        console.error("Error in the Fetch request:", error);
        throw error;
    });
}

/* GENERAMOS EL HTML PARA MOSTRAR LOS CLIENTES */
function generateHTMLForCustomers(customers)
{
	let html = '';
	
	customers.forEach(customer => 
	{
		html += `<tr id="row-${customer.memberId}">
        			<td>${customer.memberId}</td>
                    <td>${customer.username}</td>
                    <td class="enabled-status">${customer.enabled}</td>
                    <td>${customer.name}</td>
                    <td>${customer.lastName}</td>
                    <td>${customer.email}</td>
                    <td>
						<button
							class="change-status-btn" 
							data-member-id="${customer.memberId}" 
							data-current-status="${customer.enabled}">
							Change Enabled
					 	</button>
					</td>
                 </tr>`;
	});
	
	return html;	
}

/* GENERAMOS EL MENSAJE INDICANDO QUE NO HAY CLIENTES */
function generateHTMLForEmptyCustomers()
{
	const html = `<tr>
				      <td colspan="7" style="text-align: center; font-style: italic; color: gray;">
				           No results found.
				      </td>
			      </tr>`;	
	return html;
}

/* OBTENEMOS EL CRITERIO DE FILTRADO PARA EL ESTADO DE LOS CLIENTESS */
function getEnabled()
{
	return document.querySelector('input[name="enabled"]:checked') ? document.querySelector('input[name="enabled"]:checked').value : 'all';
}

/* APLICAMOS EL FILTRADO Y ORDENAMIENTO SELECCIONADOS */
function applyFilter()
{
	//Obtenemos el criterio de ordenamiento y el de filtrado elegidos:
    const order = getOrderValue(orderName, defaultOrder);
    const enabled = getEnabled();

    //Realizamos la consulta para obtener los clientes:
    filteredCustomers(order, enabled)
    .then(customers =>
    {
		//Seleccionamos el cuerpo de la tabla:
		const tbody = document.getElementById("tBodyCustomerTable");
		
		//Seleccionamos el contenedor del filtro por estado de los clientes:
		const container = document.getElementById("enabledFilterContainer");
		
		//Cerramos las opciones del details:
		container.removeAttribute("open");
		
		//Si al menos hay un cliente:
		if(customers.length > 0)
		{
			//Cargamos los clientes en la vista:
			tbody.innerHTML = generateHTMLForCustomers(customers);
		}
		else
		{
			//Cargamos el mensaje de que no hay clientes en la vista:
			tbody.innerHTML = generateHTMLForEmptyCustomers();
		}
	})
	.catch(error => 
    {
        console.error("There was an error applying the filters and order:", error);
    });
}

/* ESCUCHAMOS EL EVENTO DE CARGA DEL DOM */
document.addEventListener('DOMContentLoaded', () =>
{
    //Ante un clic en el botón de aplicar determinado ordenamiento:
    document.getElementById(applyOrderButtonId).addEventListener('click', applyFilter);

    //Ante un clic en el botón de aplicar el filtro:
    document.getElementById(applyFilterButtonId).addEventListener('click', applyFilter)
});

//Seleccionamos el cuerpo de la tabla de clientes y escuchamos eventos de clic en él:
document.getElementById("tBodyCustomerTable").addEventListener("click", (event) =>
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
            		
            		//Seleccionamos el cuerpo de la tabla:
      				const tbody = document.getElementById("tBodyCustomerTable");
            		
            		//Si se quedó sin clientes:
            		if(!tbody.hasChildNodes())
            		{
						//Cargamos el mensaje de que no hay clientes:
						tbody.innerHTML = generateHTMLForEmptyCustomers();	
					}
				}
            }
            else
            {
                console.error("Error changing state:", data.error);
            }
        })
        .catch(error => console.error("Error in the Fetch request:", error));
    }
});
