/* OBTENEMOS EL CRITERIO DE ORDENAMIENTO PARA LOS PEDIDOS DE APROVISIONAMIENTO ENTREGADOS/NO ENTREGADOS */
function getOrder(delivered)
{
	//Definimos el valor por defecto:
	let order = "orderAscByProductCode";
	
	//Si se pide el de los pedidos entregados:
	if(delivered)
	{
		order = document.querySelector("select[name='orderD']").value || "orderAscByProductCode"; //Criterio de ordenamiento.
	}
	else //Por el contrario, si se pide el de los no entregados:
	{
		order = document.querySelector("select[name='orderU']").value || "orderAscByProductCode"; //Criterio de ordenamiento.
	}
	
	return order; //Retornamos el criterio de ordenamiento.
}

/* OBTENEMOS LOS VALORES SELECCIONADOS DE UNA SECCIÓN, OPTIMIZANDO SI CORRESPONDE ENVIAR "all" */
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

/* OBTENEMOS LOS VALORES DE CADA FILTRO PARA LOS PEDIDOS DE APROVISIONAMIENTO ENTREGADOS/NO ENTREGADOS */
function getFilters(delivered)
{
	//Inicializamos los valores de los filtros:
	let productCodes = ["all"];
	let supplierNames = ["all"];
	let adminUsernames = ["all"];
	let amount = "-1";
	let fromAmount = "-1";
	let untilAmount = "-1";
	let rangeFromAmount = "-1";
	let rangeUntilAmount = "-1";
	
	//Si se pide los de los pedidos entregados:
	if(delivered)
	{
		productCodes = getSelectedValues("pCodeD"); //Códigos de producto.
		supplierNames = getSelectedValues("sNameD"); //Nombres de proveedor.
		adminUsernames = getSelectedValues("usernameD"); //Usernames de administrador.
		amount = document.querySelector("input[name='amountD']").value || "-1"; //Cantidad.
		fromAmount = document.querySelector("input[name='fAmountD']").value || "-1"; //Cantidad mayor o igual.
		untilAmount = document.querySelector("input[name='uAmountD']").value || "-1"; //Cantidad menor o igual.
		rangeFromAmount = document.querySelector("input[name='rFAmountD']").value || "-1"; //Cantidad mayor o igual para un rango.
		rangeUntilAmount = document.querySelector("input[name='rUAmountD']").value || "-1"; //Cantidad menor o igual para un rango.
	}
	else //Por el contrario, si se pide los de los pedidos no entregados:
	{
		productCodes = getSelectedValues("pCodeU"); //Códigos de producto.
		supplierNames = getSelectedValues("sNameU"); //Nombres de proveedor.
		adminUsernames = getSelectedValues("usernameU"); //Usernames de administrador.
		amount = document.querySelector("input[name='amountU']").value || "-1"; //Cantidad.
		fromAmount = document.querySelector("input[name='fAmountU']").value || "-1"; //Cantidad mayor o igual.
		untilAmount = document.querySelector("input[name='uAmountU']").value || "-1"; //Cantidad menor o igual.
		rangeFromAmount = document.querySelector("input[name='rFAmountU']").value || "-1"; //Cantidad mayor o igual para un rango.
		rangeUntilAmount = document.querySelector("input[name='rUAmountU']").value || "-1"; //Cantidad menor o igual para un rango.
	}
	
	//Retornamos los valores de cada filtro:
	return {productCodes, supplierNames, adminUsernames, amount, fromAmount, untilAmount, rangeFromAmount, rangeUntilAmount}; 
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
function reinicializeAmounts(delivered)
{	
	//Pedidos entregados:
	if(delivered)
	{
		document.getElementById("amountD").value = ""; //Cantidad específica.
		document.getElementById("fAmountD").value = ""; //Cantidad mayor o igual a.
		document.getElementById("uAmountD").value = ""; //Cantidad menor o igual a.
		document.getElementById("rFAmountD").value = ""; //Cantidad mayor o igual a dentro de un rango.
		document.getElementById("rUAmountD").value = ""; //Cantidad menor o igual a dentro de un rango.
	}
	else //Pedidos no entregados:
	{
		document.getElementById("amountU").value = ""; //Cantidad específica.
		document.getElementById("fAmountU").value = ""; //Cantidad mayor o igual a.
		document.getElementById("uAmountU").value = ""; //Cantidad menor o igual a.
		document.getElementById("rFAmountU").value = ""; //Cantidad mayor o igual a dentro de un rango.
		document.getElementById("rUAmountU").value = ""; //Cantidad menor o igual a dentro de un rango.
	}
}

/* OBTENEMOS LOS PEDIDOS ENTREGADOS/NO ENTREGADOS QUE CUMPLEN CON DETERMINADOS FILTROS Y ORDENADOS SEGÚN DETERMINADO CRITERIO */
async function applyFilterSupplyOrders(filtersData)
{
	//Realizamos la consulta para obtener los pedidos:
    return fetch(`/supplyOrder/supplyOrders/filter`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(filtersData)
    })
    .then(response => 
    {
		//Si hubo algún error:
        if(!response.ok) 
        {
            throw new Error("Error en la respuesta del servidor");
        }
        return response.json(); //Retornamos el JSON con los pedidos.
    })
    .then(data => data)
    .catch(error => 
    {
        console.error("Error en la solicitud Fetch:", error);
        throw error; //Re-lanza el error para que pueda ser manejado en la función que llame a filterSupplyOrders.
    });
}

/* GENERAMOS EL HTML CON LOS DATOS DE LOS PEDIDOS OBTENIDOS */
function generateHTMLForSupplyOrders(delivered, supplyOrders) 
{
    let html = '';
    supplyOrders.forEach(supplyOrder => 
    {
        html += `<tr id="row-${supplyOrder.supplyOrderId}">
        			<td>${supplyOrder.supplyOrderId}</td>
                    <td>${supplyOrder.product.code}</td>
                    <td>${supplyOrder.member.username}</td>
                    <td>${supplyOrder.supplier.name}</td>
                    <td>${supplyOrder.amount}</td>`;
        
        //Pedidos no entregados:
        if(!delivered)
        {
			//Agregamos el botón para poder registrarlo como entregado:
			html += `<td>
						<button class="register-delivered-btn" data-supply-order-id="${supplyOrder.supplyOrderId}">Register Delivered</button>
					</td>`;
		}
		
		html += `<tr>`;
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
function updateFilterOptions(data, delivered, selectedFilters) 
{
    //Extraemos valores únicos para cada filtro del listado devuelto:
    const productCodes = [...new Set(data.map(item => item.product.code))]; //Opciones de código de producto.
    const supplierNames = [...new Set(data.map(item => item.supplier.name))]; //Opciones de nombres de proveedor.
    const adminUsernames = [...new Set(data.map(item => item.member.username))]; //Opciones de usernames de administrador.

	//Pasamos los ids de las etiquetas:
    let pCode;
    let sName;
    let username;
   	let pCodeContainer;
   	let sNameContainer;
   	let usernameContainer;
   	let amountsContainer;
   	let amountContainer;
   	let fromAmountContainer;
   	let untilAmountContainer;
   	let rangeAmountContainer;
    
    //Pedidos entregados:
    if(delivered)
    {
		pCode = "pCodeD";
		sName = "sNameD";
		username = "usernameD";
		pCodeContainer = "pCodeDContainer";
		sNameContainer = "sNameDContainer";
		usernameContainer = "usernameDContainer";
		amountsContainer = "amountsDContainer";
		amountContainer = "amountDContainer";
		fromAmountContainer = "fromAmountDContainer";
		untilAmountContainer = "untilAmountDContainer";
		rangeAmountContainer = "rangeAmountDContainer";
	}
	else //Pedidos no entregados:
	{
		pCode = "pCodeU";
		sName = "sNameU";
		username = "usernameU";
		pCodeContainer = "pCodeUContainer";
		sNameContainer = "sNameUContainer";
		usernameContainer = "usernameUContainer";
		amountsContainer = "amountsUContainer";
		amountContainer = "amountUContainer";
		fromAmountContainer = "fromAmountUContainer";
		untilAmountContainer = "untilAmountUContainer";
		rangeAmountContainer = "rangeAmountUContainer";
	}
	
	//Actualizar cada sección de filtros:
    updateCheckboxes(pCodeContainer, pCode, productCodes, selectedFilters.productCodes);
    updateCheckboxes(sNameContainer, sName, supplierNames, selectedFilters.supplierNames);
    updateCheckboxes(usernameContainer, username, adminUsernames, selectedFilters.adminUsernames);
    
    //Cerramos los details de cantidades:
    document.getElementById(amountsContainer).removeAttribute("open"); //Contenedor general.
    document.getElementById(amountContainer).removeAttribute("open");  //Contenedor cantidad específica.
    document.getElementById(fromAmountContainer).removeAttribute("open"); //Contenedor cantidad mayor o igual a.
    document.getElementById(untilAmountContainer).removeAttribute("open"); //Contenedor cantidad menor o igual a.
    document.getElementById(rangeAmountContainer).removeAttribute("open"); //Contenedor cantidad dentro de un rango.
}

/* GENERAMOS DETERMINADO HTML PARA CUANDO NO HAY RESULTADOS ENCONTRADOS */
function generateHTMLForEmptyResults(delivered)
{
		//Configuración para los pedidos no entregados:
		let idTBody = "undeliveredSupplyOrderTBodyTable";
		let colspan = 6;
		let pCodeAll = "pCodeU-all";
		let sNameAll = "sNameU-all";
		let usernameAll = "usernameU-all";
		
		//Si se trata de pedidos entregados la modificamos:
		if(delivered)
		{
			idTBody = "deliveredSupplyOrderTBodyTable";
			colspan = 5;
			pCodeAll = "pCodeD-all";
			sNameAll = "sNameD-all";
			usernameAll = "usernameD-all";
		}
	
	//Obtenemos el body de la tabla:
	const tbody = document.getElementById(idTBody);
	
	//Definimos una única fila con el mensaje de que no se encontraron resultados:
	tbody.innerHTML = 
	`<tr>
        <td colspan="${colspan}" style="text-align: center; font-style: italic; color: gray;">
            No se encontraron resultados para los filtros aplicados.
        </td>
    </tr>`;	
    
    //Reinicializamos los valores de los filtros: 
    document.getElementById(pCodeAll).checked = true; //Códigos de producto.
    document.getElementById(sNameAll).checked = true; //Nombres de proveedor.
    document.getElementById(usernameAll).checked = true; //Usernames de administrador.
    reinicializeAmounts(delivered); //Cantidades.
}

/* APLICAMOS LOS FILTROS Y EL ORDENAMIENTO A LOS PEDIDOS ENTREGADOS/NO ENTREGADOS Y ACTUALIZAMOS LA VISTA CON LOS QUE APLIQUEN */
function filterSupplyOrders(delivered)
{
	const order = getOrder(delivered); //Obtenemos el criterio de ordenamiento.
	const filters = getFilters(delivered); //Obtenemos los valores de filtrado.
	
	//Cargamos el criterio de ordenamiento y los de filtrado:
	const filtersData = {order, ...filters, delivered};
	
	//Filtramos y ordenamos según la configuración anterior:
	applyFilterSupplyOrders(filtersData)
	.then(data => 
	{
		//Actualizamos las opciones de cada tipo de filtro según el listado resultante:
		updateFilterOptions(data, delivered, filters);
		
		//Configuración para los pedidos no entregados:
		let idTBody = "undeliveredSupplyOrderTBodyTable";
		
		//Si se trata de pedidos entregados la modificamos:
		if(delivered)
		{
			idTBody = "deliveredSupplyOrderTBodyTable";
		}
		
		//Seleccionamos el body de la tabla:
		const tbody = document.getElementById(idTBody);
		
		//Si hay al menos un pedido después del filtro:
		if(data.length > 0)
		{
			//Generamos el HTML a partir de los datos obtenidos:
	        const htmlContent = generateHTMLForSupplyOrders(delivered, data);
	
	        //Actualizamos los pedidos en la vista:
	        tbody.innerHTML = htmlContent;	
		}
		else
		{
			//Generamos el HTML acorde a no haber encontrado resultados:
			generateHTMLForEmptyResults(delivered);
		}
    })
    .catch(error => 
    {
        console.error("Ocurrió un error al filtrar los pedidos no entregados:", error);
    });
}

/* VERIFICAMOS SI HAY AL MENOS UN CHECKBOX MARCADO EN UNA SECCIÓN */
function isAnyCheckedInSection(sectionName) 
{
    return Array.from(document.querySelectorAll(`input[name="${sectionName}"]:checked`)).length > 0;
}

/* VERIFICAMOS SI TODAS LAS SECCIONES TIENEN AL MENOS UN CHECKBOX MARCADO */
function checkFiltersState(delivered) 
{
	let allSectionsChecked;  
	let applyButton;  
	
	//Pedidos entregados:
	if(delivered)
	{
		//Obtenemos si alguna opción de cada sección fue marcada:
		allSectionsChecked = isAnyCheckedInSection("pCodeD") && isAnyCheckedInSection("sNameD") && isAnyCheckedInSection("usernameD");
        
        //Obtenemos el botón de aplicar filtros:
        applyButton = document.getElementById("applyFilterDeliveredButton");
	}
	else //Pedidos no entregados:
	{
		//Obtenemos si alguna opción de cada sección fue marcada:
		allSectionsChecked = isAnyCheckedInSection("pCodeU") && isAnyCheckedInSection("sNameU") && isAnyCheckedInSection("usernameU");
        
        //Obtenemos el botón de aplicar filtros:
        applyButton = document.getElementById("applyFilterUndeliveredButton");
	}
    
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
function validateRangeWithMessage(delivered) 
{
	//Suponemos que es para pedidos no entregados y definimos algunas variables:
    let type = "U";
    let containerId = "rangeAmountUContainer";
    let applyFilterButtonId = "applyFilterUndeliveredButton";

	//Si es para pedidos entregados las modificamos:
    if (delivered) 
    {
        type = "D";
        containerId = "rangeAmountDContainer";
        applyFilterButtonId = "applyFilterDeliveredButton";
    }

	//Obtenemos el elemnento padre de los inputs:
    const container = document.getElementById(containerId);
    
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
    const rangeFromAmountInput = document.getElementById(`rFAmount${type}`);
    const rangeUntilAmountInput = document.getElementById(`rUAmount${type}`);
    
    //Obtenemos el botón de aplicar filtros:
    const applyFiltersButton = document.getElementById(applyFilterButtonId);
    
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
    else 
    {
        errorMessage.style.display = "none"; //Ocultamos el mensaje si alguno de los inputs está vacío.
    }
}

/* RESETEAMOS LOS FILTROS DE LOS PEDIDOS ENTREGADOS/NO ENTREGADOS Y OBTENEMOS LOS PEDIDOS QUE APLIQUEN A ESA CONFIGURACIÓN */
function resetFilters(delivered)
{
	const order = getOrder(delivered); //Obtenemos el criterio de ordenamiento.
	
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
		rangeUntilAmount: "-1"
	};
	
	//Definimos el conjunto de los nuevos valores de filtrado:
	const filtersData = {order, ...filters, delivered};
	
	//Filtramos y ordenamos según la configuración anterior:
	applyFilterSupplyOrders(filtersData)
	.then(data => 
	{
		//Si hubo resultados luego del filtrado:
		if(data.length > 0)
		{
			//Configuración para los pedidos no entregados:
			let idTBody = "undeliveredSupplyOrderTBodyTable";
			let pCode = "pCodeU";
			let sName = "sNameU";
			let username = "usernameU";
			
			//La modificamos si se trata de pedidos entregados:
			if(delivered)
			{
				idTBody = "deliveredSupplyOrderTBodyTable";
				pCode = "pCodeD";
				sName = "sNameD";
				username = "usernameD";
			}
			
			//Seleccionamos el body de la tabla:
			const tbody = document.getElementById(idTBody);
			
			//Generamos el HTML a partir de los datos obtenidos:
		    const htmlContent = generateHTMLForSupplyOrders(delivered, data);
		        
			//Actualizamos los pedidos en la vista:
		    tbody.innerHTML = htmlContent;
		    
		    //Actualizamos las opciones de cada tipo de filtro según el listado obtenido:
		    updateFilterOptions(data, delivered, filters);
		    
		    //Tildamos la opción "all" para el filtro de códigos de producto:
		    const allOptionProductCodes = document.getElementById(`${pCode}-all`);
	    	allOptionProductCodes.checked = true;
		    
		    //Destildamos las demás opciones para el filtro de códigos de producto:
		   	descheckedAndDisableOtherOptions(pCode);
		    
		    //Tildamos la opción "all" para el filtro de nombres de proveedor:
		    const allOptionSupplierNames = document.getElementById(`${sName}-all`);
	    	allOptionSupplierNames.checked = true;
		    
		    //Destildamos las demás opciones para el filtro de nombres de proveedor:
		    descheckedAndDisableOtherOptions(sName);
		    
		    //Tildamos la opción "all" para el filtro de usernames de administrador:
		    const allOptionAdminUsernames = document.getElementById(`${username}-all`);
	    	allOptionAdminUsernames.checked = true;
		    
		    //Destildamos las demás opciones para el filtro de usernames de administrador:
		    descheckedAndDisableOtherOptions(username);
		     
		    //Limpiamos el valor de los inputs de cantidad:
		    reinicializeAmounts(delivered);
		}
		else
		{
			//Generamos el HTML acorde a no haber encontrado resultados:
			generateHTMLForEmptyResults(delivered);
		}
		
		//Reiniciamos el mensaje de error de los inputs de rangos de cantidades:
		validateRangeWithMessage(delivered);
    })
    .catch(error => 
    {
        console.error("Ocurrió un error al filtrar los pedidos no entregados:", error);
    });
};

/* ESCUCHAMOS LA ENTRADA DE DATOS EN LOS INPUTS DE CANTIDADES ENTRE RANGOS */
document.getElementById("rFAmountD").addEventListener("input", () => validateRangeWithMessage(true));
document.getElementById("rUAmountD").addEventListener("input", () => validateRangeWithMessage(true));
document.getElementById("rFAmountU").addEventListener("input", () => validateRangeWithMessage(false));
document.getElementById("rUAmountU").addEventListener("input", () => validateRangeWithMessage(false));

/* ORDENAMOS LOS PEDIDOS ENTREGADOS */ 
document.getElementById("applyOrderDeliveredButton").addEventListener("click", () => filterSupplyOrders(true));

/* ORDENAMOS LOS PEDIDOS NO ENTREGADOS */ 
document.getElementById("applyOrderUndeliveredButton").addEventListener("click", () => filterSupplyOrders(false));

/* FILTRAMOS LOS PEDIDOS ENTREGADOS */ 
document.getElementById("applyFilterDeliveredButton").addEventListener("click", () => filterSupplyOrders(true));

/* FILTRAMOS LOS PEDIDOS NO ENTREGADOS */ 
document.getElementById("applyFilterUndeliveredButton").addEventListener("click", () => filterSupplyOrders(false));

/* RESETEO DE LOS FILTROS DE LOS PEDIDOS ENTREGADOS */
document.getElementById("resetAllDeliveredButton").addEventListener("click", () => resetFilters(true));

/* RESETEO DE LOS FILTROS DE LOS PEDIDOS NO ENTREGADOS */
document.getElementById("resetAllUndeliveredButton").addEventListener("click", () => resetFilters(false));

/* REGISTRO DE PEDIDO COMO ENTREGADO */
//Seleccionamos el contenedor de la tabla de pedidos de aprovisionamiento no entregados y escuchamos eventos de clic en él:
document.getElementById("undeliveredSupplyOrderTBodyTable").addEventListener("click", (event) =>
{
	//Si el clic se hizo sobre algún botón de registrar un pedido como entregado:
	if(event.target.classList.contains("register-delivered-btn"))
	{
    	const button = event.target; //Obtenemos el botón clicado.
    	const supplyOrderId = button.getAttribute("data-supply-order-id"); //Obtenemos el id del pedido de aprovisionamiento.

        //Cambiamos el estado del pedido:
        fetch(`/supplyOrder/registerDelivered/${supplyOrderId}`, {
            method: 'POST'
        })
        //Pasamos a JSON la respuesta del controlador:
        .then(response => response.json())
        .then(data => 
        {
			//Si no hubo errores:
            if(data.success) 
            {   
				//Obtenemos la fila del pedido:
				const row = document.getElementById(`row-${supplyOrderId}`);
                
                //Eliminamos la fila porque ahora el pedido está entregado:
            	row.remove();
            	
            	const delivered = false; //Son pedidos no entregados.
            	
            	const order = getOrder(delivered); //Obtenemos el criterio de ordenamiento.
            	const filters = getFilters(delivered); //Obtenemos los criterios de filtrado.
            	
            	const filtersData = {order, ...filters, delivered}; //Definimos el conjunto de datos para filtrar.
            
            	applyFilterSupplyOrders(filtersData)
            	.then(undeliveredSupplyOrders => 
            	{
					//Obtenemos los valores seleccionados para filtrar:
	            	const selectedValues =
	            	{
						productCodes: getSelectedValues("pCodeU"),
						supplierNames: getSelectedValues("sNameU"),
						adminUsernames: getSelectedValues("usernameU")
					};
					
					//Actualizamos las opciones según el listado resultante y las opciones seleccionadas:
					updateFilterOptions(undeliveredSupplyOrders, delivered, selectedValues);
					
					//Si no se encontraron pedidos sin entregar:
					if(undeliveredSupplyOrders.length <= 0)
					{
						//Generamos el HTML acorde a no haber encontrado resultados:
						generateHTMLForEmptyResults(delivered);
					}					
				})
            	.catch(error => 
			    {
			        console.error("Ocurrió un error al filtrar los pedidos no entregados:", error);
			    });
            	
            	//Aplicamos los filtros y ordenamientos de los pedidos entregados y actualizamos la vista con los que apliquen:
            	filterSupplyOrders(true);
            }
            else
            {
                console.error("Error al cambiar el estado:", data.error);
            }
        })
        .catch(error => console.error("Error en la solicitud Fetch:", error));
    }
});

/* DETECTAMOS CLICS EN EL CHECKBOX "all" DE CÓDIGOS DE PRODUCTO DE PEDIDOS ENTREGADOS */
document.getElementById("pCodeD-all").addEventListener("click", (event) => changeStatusOtherOptions(event, "pCodeD"));

/* DETECTAMOS CLICS EN EL CHECKBOX "all" DE CÓDIGOS DE PRODUCTO DE PEDIDOS NO ENTREGADOS */
document.getElementById("pCodeU-all").addEventListener("click", (event) => changeStatusOtherOptions(event, "pCodeU"));

/* DETECTAMOS CLICS EN EL CHECKBOX "all" DE NOMBRES DE PROVEEDOR DE PEDIDOS ENTREGADOS */
document.getElementById("sNameD-all").addEventListener("click", (event) => changeStatusOtherOptions(event, "sNameD"));

/* DETECTAMOS CLICS EN EL CHECKBOX "all" DE NOMBRES DE PROVEEDOR DE PEDIDOS NO ENTREGADOS */
document.getElementById("sNameU-all").addEventListener("click", (event) => changeStatusOtherOptions(event, "sNameU"));

/* DETECTAMOS CLICS EN EL CHECKBOX "all" DE USERNAMES DE ADMINISTRADOR DE PEDIDOS ENTREGADOS */
document.getElementById("usernameD-all").addEventListener("click", (event) => changeStatusOtherOptions(event, "usernameD"));

/* DETECTAMOS CLICS EN EL CHECKBOX "all" DE USERNAMES DE ADMINISTRADOR DE PEDIDOS NO ENTREGADOS */
document.getElementById("usernameU-all").addEventListener("click", (event) => changeStatusOtherOptions(event, "usernameU"));

/* AGREGAMOS LISTENERS A LOS CONTENEDORES DE LAS SECCIONES DE FILTRO DE LOS PEDIDOS ENTREGADOS */
["pCodeD", "sNameD", "usernameD"].forEach(sectionName => 
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

/* AGREGAMOS LISTENERS A LOS CONTENEDORES DE LAS SECCIONES DE FILTRO DE LOS PEDIDOS NO ENTREGADOS */
["pCodeU", "sNameU", "usernameU"].forEach(sectionName => 
{
	//Obtenemos el elemento contenedor del input:
    const sectionContainer = document.querySelector(`details summary:has(~ input[name="${sectionName}"])`).parentElement;

	//Escuchamos clics en él:
    sectionContainer.addEventListener("click", (event) => 
    {
        //Si el clic fue en un input dentro de la sección:
        if(event.target.tagName === "INPUT" && event.target.type === "checkbox") 
        {
            checkFiltersState(false); //Habilitamos o deshabilitamos el botón según el estado del filtro.
        }
    });
});
