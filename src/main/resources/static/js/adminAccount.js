/* OBTENEMOS EL CRITERIO DE ORDENAMIENTO SELECCIONADO */
function getOrderValue()
{
	return document.querySelector('select[name="order"]').value || "orderAscByProductCode"; //Por defecto es 'orderAscByProductCode'.
}

/* OBTENEMOS LOS VALORES SELECCIONADOS DE UNA SECCIÓN DE FILTRADO, OPTIMIZANDO SI CORRESPONDE ENVIAR "all" */
function getSelectedValues(sectionName)
{
	//Obtenemos todas las opciones menos la de "all":
    const options = document.querySelectorAll(`input[name="${sectionName}"]:not([value="all"]`);
    
    //Obtenemos la opción de "all":
    const optionAll = document.querySelector(`input[name="${sectionName}"][value="all"]`);
    
    //Obtenemos la cantidad de opciones máxima que hay para el filtro:
    const maxOptions = document.getElementById(`${sectionName}MaxValues`);
    const maxOptionsValue = maxOptions.dataset.maxValues;
    
    let selectedValues = [];
    
    //Si está seleccionada la opción de "all":
    if(optionAll.checked)
    {
		selectedValues = [optionAll.value]; //Vamos a filtrar solo por ese criterio.
	}
	else //Por el contrario, si hay más opciones seleccionadas:
	{
		//Obtenemos todos los valores seleccionados menos el de "all":
	    selectedValues = Array.from(options).filter(option => option.checked && option.value !== "all").map(option => option.value);
	
	    //Si todas las opciones están seleccionadas excepto "all" y la cantidad de seleccionadas es igual a la máxima de todas las posibles:
	    if(selectedValues.length === options.length && selectedValues.length === maxOptionsValue) 
	    {
	        selectedValues = ["all"]; //Solo hay que tener en cuenta la opción de "all".
	    }
	}

    return selectedValues; //Devolvemos los valores seleccionados.
}

/* OBTENEMOS EL USERNAME DEL ADMINISTRADOR */
function getAdminUsernameValue()
{
	return [document.getElementById("adminUsername").dataset.adminUsername];
}

/* OBTENEMOS LOS VALORES DE CADA FILTRO */
function getFiltersValues()
{
	return filters =
	{
		productCodes: getSelectedValues("pCode"), //Códigos de producto.
		supplierNames: getSelectedValues("sName"), //Nombre de proveedor.
		adminUsernames: getAdminUsernameValue(), //Username del administrador.
		amount: document.querySelector('input[name="amount"]').value || "-1", //Cantidad.
		fromAmount: document.querySelector('input[name="fAmount"]').value || "-1", //Cantidad mayor o igual a.
		untilAmount: document.querySelector('input[name="uAmount"]').value || "-1", //Cantidad menor o igual a.
		rangeFromAmount: document.querySelector('input[name="rFAmount"]').value || "-1", //Cantidad mayor o igual a dentro de un rango.
		rangeUntilAmount: document.querySelector('input[name="rUAmount"]').value || "-1", //Cantidad menor o igual a dentro de un rango.
		delivered: document.querySelector('input[name="delivered"]:checked') ? 
				   document.querySelector('input[name="delivered"]:checked').value : 'all' //Estado de los pedidos.
	};
}

/* ACTIVAMOS LA OPCIÓN DE TODOS EN UNA SECCIÓN Y DESACTIVAMOS LAS DEMÁS */
function descheckedAndDisableOtherOptions(sectionName)
{	
	//Obtenemos todas las opciones menos la de "all":
	let options = document.querySelectorAll(`input[name="${sectionName}"]:not([value="all"])`); 

    //Desmarcamos y desactivamos todas:
    options.forEach(option => 
    {
        option.checked = false;
        option.disabled = true;
	});
}

/* REESTABLECEMOS LOS INPUTS DE CANTIDAD */
function reinicializeAmounts()
{	
	document.getElementById("amount").value = ""; //Cantidad específica.
	document.getElementById("fAmount").value = ""; //Cantidad mayor o igual a.
	document.getElementById("uAmount").value = ""; //Cantidad menor o igual a.
	document.getElementById("rFAmount").value = ""; //Cantidad mayor o igual a dentro de un rango.
	document.getElementById("rUAmount").value = ""; //Cantidad menor o igual a dentro de un rango.
}

/* OBTENEMOS LOS PEDIDOS QUE CUMPLEN CON DETERMINADOS FILTROS Y ORDENADOS SEGÚN DETERMINADO CRITERIO */
async function applyFilterSupplyOrders(filtersData)
{
	//Realizamos la consulta para obtener los pedidos:
    return fetch(`/myAccount/admin/filter`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(filtersData)
    })
    .then(response => 
    {
		//Si hubo algún error:
        if(!response.ok) 
        {
            throw new Error("Error in the response of server");
        }
        return response.json(); //Retornamos el JSON con los pedidos.
    })
    .then(data => data)
    .catch(error => 
    {
        console.error("Error in the Fetch request:", error);
        throw error; //Re-lanza el error para que pueda ser manejado en la función que llame a filterSupplyOrders.
    });
}

/* GENERAMOS EL HTML CON LOS DATOS DE LOS PEDIDOS OBTENIDOS */
function generateHTMLForSupplyOrders(supplyOrders) 
{
    let html = '';
    let delivered;
    supplyOrders.forEach(supplyOrder => 
    {
		if(supplyOrder.delivered)
		{
			delivered = "Yes";
		}
		else
		{
			delivered = "No";
		}
		
        html += `<tr id="row-${supplyOrder.supplyOrderId}">
        			<td>${supplyOrder.supplyOrderId}</td>
                    <td>${supplyOrder.product.code}</td>
                    <td>${supplyOrder.member.username}</td>
                    <td>${supplyOrder.supplier.name}</td>
                    <td>${supplyOrder.amount}</td>
                    <td>${delivered}</td>
                 </tr>`;
    });
    return html;
}


/* ACTUALIZAMOS LOS CHECKBOXES DE UNA SECCIÓN DE FILTRO */
function updateCheckboxes(containerId, name, values, selectedValues) 
{	
	const container = document.getElementById(containerId); //Obtenemos el elemento padre.
	container.removeAttribute("open"); //Cerramos las opciones del details para no ocupar demasiado espacio de la pantalla.

    //Limpiamos las opciones actuales (excepto el checkbox "All"):
    const options = container.querySelectorAll('input[type="checkbox"]:not([value="all"])');
    options.forEach(option => option.parentElement.remove());

    //Generamos el checkbox para las demás opciones:
    values.forEach((value, index) => 
    {
		//Definimos la primera parte de la opción:
		let option = `<div>
	                      <input type="checkbox" name=${name} value="${value}" id="${name}-${index + 1}"`;
		            
		//Si está seleccionada la opción "all" en este tipo de filtro:    
		if(selectedValues.includes("all"))
		{
			//Entonces generamos las opciones pero desactivadas:
			option += ` disabled>
		              <label for="${name}-${index + 1}">${value}</label>
		          </div>`;
		}
		
		//Si no está seleccionada la opción de "all" y dentro de las seleccionadas está la opción que estamos estamos evaluando:
		else if(selectedValues.includes(value))
		{
			//Entonces la insertamos al HTML marcada:
			option += ` checked>
					  <label for="${name}-${index + 1}">${value}</label>
		          </div>`;	
		}
		else //Para el resto de los casos:
		{
			//Entonces generamos la opción por defecto, sin marcar y habilitada:
			option += `>
					  <label for="${name}-${index + 1}">${value}</label>
		          </div>`;
		}
		
        container.insertAdjacentHTML("beforeend", option); //Agregamos la opción al final.
    });
}

/* ACTUALIZAMOS LAS OPCIONES DE CADA FILTRO SEGÚN EL LISTADO DE PEDIDOS ENTREGADOS/NO ENTREGADOS ACTUAL */
function updateFilterOptions(data, selectedFilters) 
{
    //Extraemos valores únicos para cada filtro del listado devuelto:
    const productCodes = [...new Set(data.map(item => item.product.code))]; //Opciones de código de producto.
    const supplierNames = [...new Set(data.map(item => item.supplier.name))]; //Opciones de nombres de proveedor.
	
	//Actualizar cada sección de filtros:
    updateCheckboxes("pCodeContainer", "pCode", productCodes, selectedFilters.productCodes);
    updateCheckboxes("sNameContainer", "sName", supplierNames, selectedFilters.supplierNames);
    
    //Cerramos los details de cantidades:
    document.getElementById("amountsContainer").removeAttribute("open"); //Contenedor general.
    document.getElementById("amountContainer").removeAttribute("open");  //Contenedor cantidad específica.
    document.getElementById("fromAmountContainer").removeAttribute("open"); //Contenedor cantidad mayor o igual a.
    document.getElementById("untilAmountContainer").removeAttribute("open"); //Contenedor cantidad menor o igual a.
    document.getElementById("rangeAmountContainer").removeAttribute("open"); //Contenedor cantidad dentro de un rango.
    
    //Cerramos el details del estado de los pedidos:
    document.getElementById("deliveredContainer").removeAttribute("open"); 
}

/* GENERAMOS DETERMINADO HTML PARA CUANDO NO HAY RESULTADOS ENCONTRADOS */
function generateHTMLForEmptyResults()
{
	//Obtenemos el body de la tabla:
	const tbody = document.getElementById("tbodySupplyOrdersTable");
	
	//Definimos una única fila con el mensaje de que no se encontraron resultados:
	tbody.innerHTML = 
	`<tr>
        <td colspan="6" style="text-align: center; font-style: italic; color: gray;">
            No results found.
        </td>
    </tr>`;	
    
    //Reinicializamos los valores de los filtros: 
    document.getElementById("pCode-all").checked = true; //Códigos de producto.
    document.getElementById("sName-all").checked = true; //Nombres de proveedor.
    reinicializeAmounts(); //Cantidades.
    document.getElementById("delivered-all").checked = true; //Estado de los pedidos.
}

/* APLICAMOS LOS FILTROS Y EL ORDENAMIENTO A LOS PEDIDOS Y ACTUALIZAMOS LA VISTA CON LOS QUE APLIQUEN */
function filterSupplyOrders()
{
	const order = getOrderValue(); //Obtenemos el criterio de ordenamiento.
	const filters = getFiltersValues(); //Obtenemos los valores de filtrado.
	
	//Cargamos el criterio de ordenamiento y los de filtrado:
	const filtersData = {order, ...filters};
	
	//Filtramos y ordenamos según la configuración anterior:
	applyFilterSupplyOrders(filtersData)
	.then(data => 
	{
		//Actualizamos las opciones de cada tipo de filtro según el listado resultante:
		updateFilterOptions(data, filters);
		
		//Seleccionamos el body de la tabla:
		const tbody = document.getElementById("tbodySupplyOrdersTable");
		
		//Si hay al menos un pedido después del filtro:
		if(/*data.length > 0*/ false)
		{
			//Generamos el HTML a partir de los datos obtenidos:
	        const htmlContent = generateHTMLForSupplyOrders(data);
	
	        //Actualizamos los pedidos en la vista:
	        tbody.innerHTML = htmlContent;	
		}
		else
		{
			//Generamos el HTML acorde a no haber encontrado resultados:
			generateHTMLForEmptyResults();
		}
    })
    .catch(error => 
    {
        console.error("There was an error applying the filters:", error);
    });
}

/* VERIFICAMOS SI HAY AL MENOS UN CHECKBOX MARCADO EN UNA SECCIÓN */
function isAnyCheckedInSection(sectionName) 
{
    return Array.from(document.querySelectorAll(`input[name="${sectionName}"]:checked`)).length > 0;
}

/* VERIFICAMOS SI TODAS LAS SECCIONES TIENEN AL MENOS UN CHECKBOX MARCADO */
function checkFiltersState() 
{
	//Obtenemos el estado de las secciones:
	const allSectionsChecked = isAnyCheckedInSection("pCode") && isAnyCheckedInSection("sName") && isAnyCheckedInSection("delivered");  
	
	//Seleccionamos el botón de aplicar filtros:
	const applyButton = document.getElementById("applyFilterButton");;  
	
    //Habilitamos o deshabilitamos el botón según el estado de las secciones:
	applyButton.disabled = !allSectionsChecked;
}

/* ALTERAMOS LAS DEMÁS OPCIONES DE UNA SECCIÓN EN BASE AL ESTADO DE LA OPCIÓN "all" */
function changeStatusOtherOptions(event, sectionName)
{
	const isChecked = event.target.checked; //Obtenemos si fue marcado o no.

	//Si está marcada:
    if(isChecked) 
    {
		//Desmarcamos y deshabilitamos todas las opciones menos la de "all":
        descheckedAndDisableOtherOptions(sectionName);
    } 
    else 
    {
		//Obtenemos todas las opciones menos la de "all":
		const options = document.querySelectorAll(`input[name="${sectionName}"]:not([value="all"])`); 
        
        //Las habilitamos:
        options.forEach(option => 
        {
            option.disabled = false;
        });
    }
}

/* VALIDAMOS QUE LA CANTIDAD MÁXIMA DEL RANGO SEA MAYOR O IGUAL A LA MÍNIMA DEL RANGO */
function validateRangeWithMessage() 
{
	//Obtenemos el elemnento padre de los inputs:
    const container = document.getElementById("rangeAmountContainer");
    
    //Obtenemos la etiqueta del mensaje de error:
    let errorMessage = container.querySelector(".error-message");
    
    //Solo si esa etiqueta de mensaje de error no existe, la creamos:
    if (!errorMessage)
	{
        errorMessage = document.createElement("p"); //Definimos el tipo de etiqueta.
        errorMessage.className = "error-message"; //Le damos una clase.
        errorMessage.style.color = "red"; //Un color de texto.
        errorMessage.style.display = "none"; //Oculto por defecto
        container.appendChild(errorMessage); //Agregamos la etiqueta al contenedor.
    }

	//Obtenemos los inputs:
    const rangeFromAmountInput = document.getElementById("rFAmount");
    const rangeUntilAmountInput = document.getElementById("rUAmount");
    
    //Obtenemos el botón de aplicar filtros:
    const applyFiltersButton = document.getElementById("applyFilterButton");
    
    //Lo activamos:
    applyFiltersButton.disabled = false;

	//Obtenemos el valor de cada uno:
    const fromValue = parseInt(rangeFromAmountInput.value, 10);
    const untilValue = parseInt(rangeUntilAmountInput.value, 10);

	//Si ambos valores de inputs son números:
    if(!isNaN(fromValue) && !isNaN(untilValue)) 
    {
		//Si el valor mínimo es mayor al máximo:
        if(fromValue > untilValue) 
        {
            errorMessage.textContent = "Until value must be higher or equal than from value."; //Definimos el mensaje.
            errorMessage.style.display = "block"; //Lo hacemos visible.
            applyFiltersButton.disabled = true; //Desactivamos el botón de aplicar filtros.
        } 
        else 
        {
            errorMessage.style.display = "none"; //Ocultamos el mensaje.
        }
    } 
    //Si se completó uno de los valores:
    else if(!isNaN(fromValue) || !isNaN(untilValue))
    {
		errorMessage.textContent = "Both fields must be completed to apply filters."; //Definimos el mensaje.
        errorMessage.style.display = "block"; //Lo hacemos visible.
        applyFiltersButton.disabled = true; //Desactivamos el botón de aplicar filtros.
	}
	else
    {
        errorMessage.style.display = "none"; //Ocultamos el mensaje si alguno de los inputs está vacío.
    }
}

/* RESETEAMOS LOS FILTROS DE LOS PEDIDOS Y OBTENEMOS LOS PEDIDOS QUE APLIQUEN A ESA CONFIGURACIÓN */
function resetFilters()
{
	const order = getOrderValue(); //Obtenemos el criterio de ordenamiento.
	
	//Definimos los nuevos valores de filtrado:
	const filters =
	{
		productCodes: [],
		supplierNames: [],
		adminUsernames: [],
		amount: "-1",
		fromAmount: "-1",
		untilAmount: "-1",
		rangeFromAmount: "-1",
		rangeUntilAmount: "-1",
		delivered: "all"
	};
	
	//Definimos el conjunto de los nuevos valores de filtrado:
	const filtersData = {order, ...filters};
	
	//Filtramos y ordenamos según la configuración anterior:
	applyFilterSupplyOrders(filtersData)
	.then(data => 
	{
		//Si hubo resultados luego del filtrado:
		if(data.length > 0)
		{	
			//Seleccionamos el body de la tabla:
			const tbody = document.getElementById("tbodySupplyOrdersTable");
			
			//Generamos el HTML a partir de los datos obtenidos:
		    const htmlContent = generateHTMLForSupplyOrders(data);
		        
			//Actualizamos los pedidos en la vista:
		    tbody.innerHTML = htmlContent;
		    
		    //Actualizamos las opciones de cada tipo de filtro según el listado obtenido:
		    updateFilterOptions(data, filters);
		    
		    //Tildamos la opción "all" para el filtro de códigos de producto:
		    const allOptionProductCodes = document.getElementById("pCode-all");
	    	allOptionProductCodes.checked = true;
		    
		    //Destildamos las demás opciones para el filtro de códigos de producto:
		   	descheckedAndDisableOtherOptions("pCode");
		    
		    //Tildamos la opción "all" para el filtro de nombres de proveedor:
		    const allOptionSupplierNames = document.getElementById("sName-all");
	    	allOptionSupplierNames.checked = true;
		    
		    //Destildamos las demás opciones para el filtro de nombres de proveedor:
		    descheckedAndDisableOtherOptions("sName");
		     
		    //Limpiamos el valor de los inputs de cantidad:
		    reinicializeAmounts();
		    
		    //Tildamos la opción "all" para el filtro de estado de los pedidos:
		    const allOptionDelivered = document.getElementById("delivered-all");
	    	allOptionDelivered.checked = true;
		}
		else
		{
			//Generamos el HTML acorde a no haber encontrado resultados:
			generateHTMLForEmptyResults();
		}
		
		//Reiniciamos el mensaje de error de los inputs de rangos de cantidades:
		validateRangeWithMessage();
    })
    .catch(error => 
    {
        console.error("There was an error applying the filters:", error);
    });
};

/* ESCUCHAMOS LA ENTRADA DE DATOS EN LOS INPUTS DE CANTIDADES ENTRE RANGOS */
document.getElementById("rFAmount").addEventListener("input", () => validateRangeWithMessage());
document.getElementById("rUAmount").addEventListener("input", () => validateRangeWithMessage());

/* ORDENAMOS LOS PEDIDOS */ 
document.getElementById("applyOrderButton").addEventListener("click", () => filterSupplyOrders());

/* FILTRAMOS LOS PEDIDOS */ 
document.getElementById("applyFilterButton").addEventListener("click", () => filterSupplyOrders());

/* RESETEO DE LOS FILTROS DE LOS PEDIDOS */
document.getElementById("resetAllButton").addEventListener("click", () => resetFilters());

/* DETECTAMOS CLICS EN EL CHECKBOX "all" DE CÓDIGOS DE PRODUCTO DE PEDIDOS */
document.getElementById("pCode-all").addEventListener("click", (event) => changeStatusOtherOptions(event, "pCode"));

/* DETECTAMOS CLICS EN EL CHECKBOX "all" DE NOMBRES DE PROVEEDOR DE PEDIDOS */
document.getElementById("sName-all").addEventListener("click", (event) => changeStatusOtherOptions(event, "sName"));

/* AGREGAMOS LISTENERS A LOS CONTENEDORES DE LAS SECCIONES DE FILTRO DE LOS PEDIDOS */
["pCode", "sName"].forEach(sectionName => 
{
	//Obtenemos el elemento contenedor del input:
    const sectionContainer = document.querySelector(`details summary:has(~ input[name="${sectionName}"])`).parentElement;

	//Escuchamos clics en él:
    sectionContainer.addEventListener("click", (event) => 
    {
        //Si el clic fue en un input dentro de la sección:
        if(event.target.tagName === "INPUT" && event.target.type === "checkbox") 
        {
            checkFiltersState(true); //Habilitamos o deshabilitamos el botón según el estado del filtro.
        }
    });
});
