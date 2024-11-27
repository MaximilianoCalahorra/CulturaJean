//Importamos la función para ocultar un mensaje de error en la vista:
import { hideError } from "/js/errorMessage.js";

/* OBTENEMOS EL CRITERIO DE ORDENAMIENTO SELECCIONADO PARA LOS PEDIDOS DE APROVISIONAMIENTO/LOTES */
function getOrderValue(orderName, defaultValue)
{
	return document.querySelector(`select[name="${orderName}"]`).value || `${defaultValue}`;
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

/* OBTENEMOS LOS VALORES DE CADA FILTRO DE LOS PEDIDOS */
function getSupplyOrdersFiltersValues()
{
	return {
		productCodes: getSelectedValues("pCode"), //Códigos de producto.
		supplierNames: getSelectedValues("sName"), //Nombre de proveedor.
		adminUsernames: [],
		amount: document.querySelector('input[name="amount"]').value || "-1", //Cantidad.
		fromAmount: document.querySelector('input[name="fAmount"]').value || "-1", //Cantidad mayor o igual a.
		untilAmount: document.querySelector('input[name="uAmount"]').value || "-1", //Cantidad menor o igual a.
		rangeFromAmount: document.querySelector('input[name="rFAmount"]').value || "-1", //Cantidad mayor o igual a dentro de un rango.
		rangeUntilAmount: document.querySelector('input[name="rUAmount"]').value || "-1" //Cantidad menor o igual a dentro de un rango.
	};
}

/* OBTENEMOS LOS VALORES DE CADA FILTRO DE LOS LOTES */
function getLotsFiltersValues()
{
	return {
		stockIds: getSelectedValues("stockId"), //Ids de stock.
		receptionDate: document.querySelector('input[name="rDate"]').value || "", //Fecha de recepción.
		fromReceptionDate: document.querySelector('input[name="fRDate"]').value || "", //Fecha de recepción posterior o igual a.
		untilReceptionDate: document.querySelector('input[name="uRDate"]').value || "", //Fecha de recepción anterior o igual a.
		rangeFromReceptionDate: document.querySelector('input[name="rFRDate"]').value || "", //Fecha de recepción posterior o igual a dentro de un rango.
		rangeUntilReceptionDate: document.querySelector('input[name="rURDate"]').value || "", //Fecha de recepción anterior o igual a dentro de un rango.
		amount: document.querySelector('input[name="eAmount"]').value || "-1", //Cantidad.
		fromAmount: document.querySelector('input[name="fEAmount"]').value || "-1", //Cantidad mayor o igual a.
		untilAmount: document.querySelector('input[name="uEAmount"]').value || "-1", //Cantidad menor o igual a.
		rangeFromAmount: document.querySelector('input[name="rFEAmount"]').value || "-1", //Cantidad mayor o igual a dentro de un rango.
		rangeUntilAmount: document.querySelector('input[name="rUEAmount"]').value || "-1" //Cantidad menor o igual a dentro de un rango.
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

/* REESTABLECEMOS LOS INPUTS DE CANTIDAD DE LOS PEDIDOS */
function reinicializeSupplyOrdersAmounts()
{	
	//Cantidad específica:
	const amountInput = document.getElementById("amount");
	amountInput.value = ""; 
	hideError(amountInput);
	
	//Cantidad mayor o igual a:
	const fromAmountInput = document.getElementById("fAmount");
	fromAmountInput.value = ""; 
	hideError(fromAmountInput);
	
	//Cantidad menor o igual a:
	const untilAmountInput = document.getElementById("uAmount");
	untilAmountInput.value = ""; 
	hideError(untilAmountInput);
	
	//Cantidad mayor o igual a dentro de un rango:
	const rangeFromAmountInput = document.getElementById("rFAmount");
	rangeFromAmountInput.value = ""; 
	hideError(rangeFromAmountInput);
	
	//Cantidad menor o igual a dentro de un rango:
	const rangeUntilAmountInput = document.getElementById("rUAmount");
	rangeUntilAmountInput.value = ""; 
	hideError(rangeUntilAmountInput);
	
	//Habilitamos el botón de aplicar filtros:
	document.getElementById("applyFiltersSOButton").disabled = false;
}

/* REESTABLECEMOS LOS INPUTS DE CANTIDAD DE LOS LOTES */
function reinicializeLotsAmounts()
{	
	//Cantidad específica:
	const amountInput = document.getElementById("eAmount");
	amountInput.value = ""; 
	hideError(amountInput);
	
	//Cantidad mayor o igual a:
	const fromAmountInput = document.getElementById("fEAmount");
	fromAmountInput.value = ""; 
	hideError(fromAmountInput);
	
	//Cantidad menor o igual a:
	const untilAmountInput = document.getElementById("uEAmount");
	untilAmountInput.value = ""; 
	hideError(untilAmountInput);
	
	//Cantidad mayor o igual a dentro de un rango:
	const rangeFromAmountInput = document.getElementById("rFEAmount");
	rangeFromAmountInput.value = ""; 
	hideError(rangeFromAmountInput);
	
	//Cantidad menor o igual a dentro de un rango:
	const rangeUntilAmountInput = document.getElementById("rUEAmount");
	rangeUntilAmountInput.value = ""; 
	hideError(rangeUntilAmountInput);
	
	//Habilitamos el botón de aplicar filtros:
	document.getElementById("applyFiltersLButton").disabled = false;
}

/* REESTABLECEMOS LOS INPUTS DE FECHAS DE LOS LOTES */
function reinicializeLotsDates()
{	
	//Fecha específica:
	const dateInput = document.getElementById("rDate");
	dateInput.value = ""; 
	hideError(dateInput);
	
	//Fecha posterior o igual a:
	const fromDateInput = document.getElementById("fRDate");
	fromDateInput.value = ""; 
	hideError(fromDateInput);
	
	//Fecha anterior o igual a:
	const untilDateInput = document.getElementById("uRDate");
	untilDateInput.value = ""; 
	hideError(untilDateInput);
	
	//Fecha posterior o igual a dentro de un rango.
	const rangeFromDateInput = document.getElementById("rFRDate");
	rangeFromDateInput.value = ""; 
	hideError(rangeFromDateInput);
	
	//Fecha anterior o igual a dentro de un rango.
	const rangeUntilDateInput = document.getElementById("rURDate");
	rangeUntilDateInput.value = ""; 
	hideError(rangeUntilDateInput);
	
	//Habilitamos el botón de aplicar filtros:
	document.getElementById("applyFiltersLButton").disabled = false;
}

/* OBTENEMOS LOS PEDIDOS QUE CUMPLEN CON DETERMINADOS FILTROS Y ORDENADOS SEGÚN DETERMINADO CRITERIO */
async function applyFilterSupplyOrders(filtersData)
{
	//Realizamos la consulta para obtener los pedidos:
    return fetch(`/lot/lots/supplyOrdersToLots/filter`, {
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

/* OBTENEMOS LOS LOTES QUE CUMPLEN CON DETERMINADOS FILTROS Y ORDENADOS SEGÚN DETERMINADO CRITERIO */
async function applyFilterLots(filtersData)
{
	//Realizamos la consulta para obtener los lotes:
    return fetch(`/lot/lots/filter`, {
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
        return response.json(); //Retornamos el JSON con los lotes.
    })
    .then(data => data)
    .catch(error => 
    {
        console.error("Error in the Fetch request:", error);
        throw error; //Re-lanza el error para que pueda ser manejado en la función que llame a filterLots.
    });
}

/* GENERAMOS EL HTML CON LOS DATOS DE LOS PEDIDOS OBTENIDOS */
function generateHTMLForSupplyOrders(supplyOrders) 
{
    let html = '';
    supplyOrders.forEach(supplyOrder => 
    {
		html += `<tr id="row-${supplyOrder.supplyOrderId}">
        			<td>${supplyOrder.supplyOrderId}</td>
                    <td>${supplyOrder.product.code}</td>
                    <td>${supplyOrder.member.username}</td>
                    <td>${supplyOrder.supplier.name}</td>
                    <td>${supplyOrder.amount}</td>
                    <td>
						<button type="button" 
								class="register-lot-btn" 
								data-supply-order-id="${supplyOrder.supplyOrderId}">
								Register Lot
						</button>
					</td>
                 </tr>`;
    });
    return html;
}

/* GENERAMOS EL HTML CON LOS DATOS DE LOS LOTES OBTENIDOS */
function generateHTMLForLots(lots) 
{
    let html = '';
    lots.forEach(lot => 
    {
		html += `<tr>
					<td>${lot.lotId}</td>
					<td>${lot.stock.stockId}</td>
					<td>${lot.receptionDate}</td>
					<td>${lot.initialAmount}</td>
					<td>${lot.existingAmount}</td>
					<td>${lot.purchasePrice}</td>
					<td>${lot.supplyOrder.supplyOrderId}</td>
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
		else if(selectedValues.includes(String(value)))
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

/* ACTUALIZAMOS LAS OPCIONES DE CADA FILTRO SEGÚN EL LISTADO DE PEDIDOS ACTUAL */
function updateSupplyOrdersFilterOptions(data, selectedFilters) 
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
}

/* ACTUALIZAMOS LAS OPCIONES DE CADA FILTRO SEGÚN EL LISTADO DE LOTES ACTUAL */
function updateLotsFilterOptions(data, selectedFilters) 
{
    //Extraemos valores únicos para el filtro de ids de stock del listado devuelto:
    const stockIds = [...new Set(data.map(item => item.stock.stockId))]; //Opciones de id de stock.
	
	//Actualizar la sección de filtro:
    updateCheckboxes("stockIdContainer", "stockId", stockIds, selectedFilters.stockIds);
    
    //Cerramos los details de fechas:
    document.getElementById("datesContainer").removeAttribute("open"); //Contenedor general.
    document.getElementById("receptionDateContainer").removeAttribute("open");  //Contenedor fecha específica.
    document.getElementById("fromReceptionDateContainer").removeAttribute("open"); //Contenedor fecha posterior o igual a.
    document.getElementById("untilReceptionDateContainer").removeAttribute("open"); //Contenedor fecha anterior o igual a.
    document.getElementById("rangeReceptionDateContainer").removeAttribute("open"); //Contenedor fecha dentro de un rango.
    
    //Cerramos los details de cantidades:
    document.getElementById("existingAmountsContainer").removeAttribute("open"); //Contenedor general.
    document.getElementById("eAmountContainer").removeAttribute("open");  //Contenedor cantidad específica.
    document.getElementById("fromEAmountContainer").removeAttribute("open"); //Contenedor cantidad mayor o igual a.
    document.getElementById("untilEAmountContainer").removeAttribute("open"); //Contenedor cantidad menor o igual a.
    document.getElementById("rangeEAmountContainer").removeAttribute("open"); //Contenedor cantidad dentro de un rango.
}

/* GENERAMOS DETERMINADO HTML PARA CUANDO NO HAY PEDIDOS ENCONTRADOS */
function generateHTMLForEmptySupplyOrders()
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
    reinicializeSupplyOrdersAmounts(); //Cantidades.
}

/* GENERAMOS DETERMINADO HTML PARA CUANDO NO HAY LOTES ENCONTRADOS */
function generateHTMLForEmptyLots()
{
	//Obtenemos el body de la tabla:
	const tbody = document.getElementById("tbodyLotsTable");
	
	//Definimos una única fila con el mensaje de que no se encontraron resultados:
	tbody.innerHTML = 
	`<tr>
        <td colspan="7" style="text-align: center; font-style: italic; color: gray;">
            No results found.
        </td>
    </tr>`;	
    
    //Reinicializamos los valores de los filtros: 
    document.getElementById("stockId-all").checked = true; //Ids de stock.
    reinicializeLotsDates(); //Fechas.
    reinicializeLotsAmounts(); //Cantidades.
}

/* APLICAMOS LOS FILTROS Y EL ORDENAMIENTO A LOS PEDIDOS Y ACTUALIZAMOS LA VISTA CON LOS QUE APLIQUEN */
function filterSupplyOrders()
{
	const order = getOrderValue("orderSO", "orderDescByAmount"); //Obtenemos el criterio de ordenamiento.
	const filters = getSupplyOrdersFiltersValues(); //Obtenemos los valores de filtrado.
	
	//Cargamos el criterio de ordenamiento y los de filtrado:
	const filtersData = {order, ...filters};
	
	//Filtramos y ordenamos según la configuración anterior:
	applyFilterSupplyOrders(filtersData)
	.then(data => 
	{
		//Actualizamos las opciones de cada tipo de filtro según el listado resultante:
		updateSupplyOrdersFilterOptions(data, filters);
		
		//Seleccionamos el body de la tabla:
		const tbody = document.getElementById("tbodySupplyOrdersTable");
		
		//Si hay al menos un pedido después del filtro:
		if(data.length > 0)
		{
			//Generamos el HTML a partir de los datos obtenidos:
	        const htmlContent = generateHTMLForSupplyOrders(data);
	
	        //Actualizamos los pedidos en la vista:
	        tbody.innerHTML = htmlContent;	
		}
		else
		{
			//Generamos el HTML acorde a no haber encontrado resultados:
			generateHTMLForEmptySupplyOrders();
		}
    })
    .catch(error => 
    {
        console.error("There was an error applying the filters:", error);
    });
}

/* APLICAMOS LOS FILTROS Y EL ORDENAMIENTO A LOS LOTES Y ACTUALIZAMOS LA VISTA CON LOS QUE APLIQUEN */
function filterLots()
{
	const order = getOrderValue("orderL", "orderAscByExistingAmount"); //Obtenemos el criterio de ordenamiento.
	const filters = getLotsFiltersValues(); //Obtenemos los valores de filtrado.
	
	//Cargamos el criterio de ordenamiento y los de filtrado:
	const filtersData = {order, ...filters};
	
	//Filtramos y ordenamos según la configuración anterior:
	applyFilterLots(filtersData)
	.then(data => 
	{
		//Actualizamos las opciones de cada tipo de filtro según el listado resultante:
		updateLotsFilterOptions(data, filters);
		
		//Seleccionamos el body de la tabla:
		const tbody = document.getElementById("tbodyLotsTable");
		
		//Si hay al menos un lote después del filtro:
		if(data.length > 0)
		{
			//Generamos el HTML a partir de los datos obtenidos:
	        const htmlContent = generateHTMLForLots(data);
	
	        //Actualizamos los pedidos en la vista:
	        tbody.innerHTML = htmlContent;	
		}
		else
		{
			//Generamos el HTML acorde a no haber encontrado resultados:
			generateHTMLForEmptyLots();
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

/* VERIFICAMOS SI TODAS LAS SECCIONES DE LOS PEDIDOS TIENEN AL MENOS UN CHECKBOX MARCADO */
function checkSupplyOrdersFiltersState() 
{
	//Obtenemos el estado de las secciones:
	const allSectionsChecked = isAnyCheckedInSection("pCode") && isAnyCheckedInSection("sName");  
	
	//Seleccionamos el botón de aplicar filtros:
	const applyButton = document.getElementById("applyFiltersSOButton");;  
	
    //Habilitamos o deshabilitamos el botón según el estado de las secciones:
	applyButton.disabled = !allSectionsChecked;
}

/* VERIFICAMOS SI TODAS LAS SECCIONES DE LOS LOTES TIENEN AL MENOS UN CHECKBOX MARCADO */
function checkLotsFiltersState() 
{
	//Obtenemos el estado de la sección:
	const allSectionsChecked = isAnyCheckedInSection("stockId");  
	
	//Seleccionamos el botón de aplicar filtros:
	const applyButton = document.getElementById("applyFiltersLButton");;  
	
    //Habilitamos o deshabilitamos el botón según el estado de la sección:
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

/* RESETEAMOS LOS FILTROS DE LOS PEDIDOS Y OBTENEMOS LOS PEDIDOS QUE APLIQUEN A ESA CONFIGURACIÓN */
function resetSupplyOrdersFilters()
{
	const order = getOrderValue("orderSO", "orderDescByAmount"); //Obtenemos el criterio de ordenamiento.
	
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
	const filtersData = {order, ...filters};
	
	//Filtramos y ordenamos según la configuración anterior:
	applyFilterSupplyOrders(filtersData)
	.then(data => 
	{
		//Actualizamos las opciones de cada tipo de filtro según el listado obtenido:
		updateSupplyOrdersFilterOptions(data, filters);
		
		//Si hubo resultados luego del filtrado:
		if(data.length > 0)
		{	
			//Seleccionamos el body de la tabla:
			const tbody = document.getElementById("tbodySupplyOrdersTable");
			
			//Generamos el HTML a partir de los datos obtenidos:
		    const htmlContent = generateHTMLForSupplyOrders(data);
		        
			//Actualizamos los pedidos en la vista:
		    tbody.innerHTML = htmlContent;
		    
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
		    reinicializeSupplyOrdersAmounts();
		}
		else
		{
			//Generamos el HTML acorde a no haber encontrado resultados:
			generateHTMLForEmptySupplyOrders();
		}
    })
    .catch(error => 
    {
        console.error("There was an error applying the filters:", error);
    });
};

/* RESETEAMOS LOS FILTROS DE LOS LOTES Y OBTENEMOS LOS LOTES QUE APLIQUEN A ESA CONFIGURACIÓN */
function resetLotsFilters()
{
	const order = getOrderValue("orderL", "orderAscByExistingAmount"); //Obtenemos el criterio de ordenamiento.
	
	//Definimos los nuevos valores de filtrado:
	const filters =
	{
		stockIds: [],
		receptionDate: "",
		fromReceptionDate: "",
		untilReceptionDate: "",
		rangeFromReceptionDate: "",
		rangeUntilReceptionDate: "",
		amount: "-1",
		fromAmount: "-1",
		untilAmount: "-1",
		rangeFromAmount: "-1",
		rangeUntilAmount: "-1"
	};
	
	//Definimos el conjunto de los nuevos valores de filtrado:
	const filtersData = {order, ...filters};
	
	//Filtramos y ordenamos según la configuración anterior:
	applyFilterLots(filtersData)
	.then(data => 
	{	
	    //Actualizamos las opciones de cada tipo de filtro según el listado obtenido:
	    updateLotsFilterOptions(data, filters);
		
		//Si hubo resultados luego del filtrado:
		if(data.length > 0)
		{	
			//Seleccionamos el body de la tabla:
			const tbody = document.getElementById("tbodyLotsTable");
			
			//Generamos el HTML a partir de los datos obtenidos:
		    const htmlContent = generateHTMLForLots(data);
		        
			//Actualizamos los pedidos en la vista:
		    tbody.innerHTML = htmlContent;
		    
		    //Tildamos la opción "all" para el filtro de ids de stock:
		    const allOptionStockIds = document.getElementById("stockId-all");
	    	allOptionStockIds.checked = true;
		    
		    //Destildamos las demás opciones para el filtro de ids de stock:
		   	descheckedAndDisableOtherOptions("stockId");
		     
		    //Limpiamos el valor de los inputs de fechas:
		    reinicializeLotsDates();
		    
		    //Limpiamos el valor de los inputs de cantidad:
		    reinicializeLotsAmounts();
		}
		else
		{
			//Generamos el HTML acorde a no haber encontrado resultados:
			generateHTMLForEmptyLots();
		}
    })
    .catch(error => 
    {
        console.error("There was an error applying the filters:", error);
    });
};

/* ESCUCHAMOS LA ENTRADA DE DATOS EN LOS INPUTS DE CANTIDADES DE LOS PEDIDOS Y DE LOS LOTES */

//Definimos la configuración para los inputs de los pedidos:
const amountsSupplyOrdersConfig =
{
    inputs: 
    [
        { id: "amount", min: 1 },
        { id: "fAmount", min: 1 },
        { id: "uAmount", min: 1 },
        { range: ["rFAmount", "rUAmount"], min: 1 }
    ],
    button: "applyFiltersSOButton"
};

//Definimos la configuración para los inputs de los lotes:
const amountsLotsConfig =
{
    inputs: 
    [
        { id: "eAmount", min: 0 },
        { id: "fEAmount", min: 0 },
        { id: "uEAmount", min: 0 },
        { range: ["rFEAmount", "rUEAmount"], min: 0 }
    ],
    button: "applyFiltersLButton"
}

//Unificamos ambas configuraciones:
const amountsConfig = [amountsSupplyOrdersConfig, amountsLotsConfig];

//Cuando carga el DOM asignamos la configuración a los inputs para poder hacer las validaciones:
import { configAmountValidations } from "/js/amountValidations.js";
document.addEventListener("DOMContentLoaded", () => configAmountValidations(amountsConfig));

/* ESCUCHAMOS LA ENTRADA DE DATOS EN LOS INPUTS DE FECHAS DE LOS LOTES */

//Definimos la configuración de los inputs de las fechas:
const datesConfig = {rangeFromDateId: "rFRDate", rangeUntilDateId: "rURDate", applyFiltersButtonId: "applyFiltersLButton"};

//Cuando se selecciona una fecha en el input de fecha desde en un rango o en el de fecha hasta de un rango, validamos:
import { validateDates } from "/js/dateValidations.js";
document.getElementById(datesConfig.rangeFromDateId).addEventListener("change", () => validateDates(datesConfig));
document.getElementById(datesConfig.rangeUntilDateId).addEventListener("change", () => validateDates(datesConfig));

/* ORDENAMOS LOS PEDIDOS */ 
document.getElementById("applyOrderSOButton").addEventListener("click", () => filterSupplyOrders());

/* ORDENAMOS LOS LOTES */ 
document.getElementById("applyOrderLButton").addEventListener("click", () => filterLots());

/* FILTRAMOS LOS PEDIDOS */ 
document.getElementById("applyFiltersSOButton").addEventListener("click", () => filterSupplyOrders());

/* FILTRAMOS LOS LOTES */ 
document.getElementById("applyFiltersLButton").addEventListener("click", () => filterLots());

/* RESETEO DE LOS FILTROS DE LOS PEDIDOS */
document.getElementById("resetAllSOButton").addEventListener("click", () => resetSupplyOrdersFilters());

/* RESETEO DE LOS FILTROS DE LOS LOTES */
document.getElementById("resetAllLButton").addEventListener("click", () => resetLotsFilters());

/* DETECTAMOS CLICS EN EL CHECKBOX "all" DE CÓDIGOS DE PRODUCTO DE PEDIDOS */
document.getElementById("pCode-all").addEventListener("click", (event) => changeStatusOtherOptions(event, "pCode"));

/* DETECTAMOS CLICS EN EL CHECKBOX "all" DE NOMBRES DE PROVEEDOR DE PEDIDOS */
document.getElementById("sName-all").addEventListener("click", (event) => changeStatusOtherOptions(event, "sName"));

/* DETECTAMOS CLICS EN EL CHECKBOX "all" DE IDS DE STOCK DE LOTES */
document.getElementById("stockId-all").addEventListener("click", (event) => changeStatusOtherOptions(event, "stockId"));

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
            checkSupplyOrdersFiltersState(); //Habilitamos o deshabilitamos el botón según el estado del filtro.
        }
    });
});

/* AGREGAMOS LISTENERS AL CONTENEDOR DE LAS SECCIÓN DE FILTRO POR ID DE STOCK DE LOS LOTES */
//Obtenemos el elemento contenedor del input:
const sectionContainer = document.querySelector('details summary:has(~ input[name="stockId"])').parentElement;

//Escuchamos clics en él:
sectionContainer.addEventListener("click", (event) => 
{
    //Si el clic fue en un input dentro de la sección:
    if(event.target.tagName === "INPUT" && event.target.type === "checkbox") 
    {
        checkLotsFiltersState(); //Habilitamos o deshabilitamos el botón según el estado del filtro.
    }
});

/* ALTA DE LOTE */
//Seleccionamos el contenedor de los pedidos de aprovisionamiento y escuchamos eventos de clic en él:
document.getElementById("tbodySupplyOrdersTable").addEventListener("click", (event) =>
{
	//Si el clic se hizo sobre algún botón de dar de alta un lote:
	if(event.target.classList.contains("register-lot-btn"))
	{
		
    	const button = event.target; //Obtenemos el botón clicado.
    	const supplyOrderId = button.getAttribute("data-supply-order-id"); //Obtenemos el id del pedido de aprovisionamiento.

        //Damos de alta el lote:
        fetch(`/lot/registerLot/${supplyOrderId}`, {
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
                
                //Eliminamos la fila porque ahora el pedido ya generó un lote:
            	row.remove();
            	
            	const order = getOrderValue("orderSO", "orderDescByAmount"); //Obtenemos el criterio de ordenamiento.
            	const filters = getSupplyOrdersFiltersValues(); //Obtenemos los criterios de filtrado.
            	
            	const filtersData = {order, ...filters}; //Definimos el conjunto de datos para filtrar.
            
            	applyFilterSupplyOrders(filtersData)
            	.then(supplyOrders => 
            	{
					//Obtenemos los valores seleccionados para filtrar:
	            	const selectedValues =
	            	{
						productCodes: getSelectedValues("pCode"),
						supplierNames: getSelectedValues("sName")
					};
					
					//Actualizamos las opciones según el listado resultante y las opciones seleccionadas:
					updateSupplyOrdersFilterOptions(supplyOrders, selectedValues);
					
					//Si no se encontraron pedidos que puedan dar de alta lotes:
					if(supplyOrders.length <= 0)
					{
						//Generamos el HTML acorde a no haber encontrado resultados:
						generateHTMLForEmptySupplyOrders();
					}					
				})
            	.catch(error => 
			    {
			        console.error("There was an error applying the filters:", error);
			    });
            	
            	//Aplicamos los filtros y ordenamientos de los lotes y actualizamos la vista con los que apliquen:
            	filterLots();
            }
            else
            {
                console.error("Error generating lot:", data.error);
            }
        })
        .catch(error => console.error("Error in the Fetch request:", error));
    }
});
