//Importamos las funcionalidades que necesitamos:
import 
{ 
	getOrderValue,
 	getSelectedValues, 
 	checkFiltersState, 
 	descheckedAndDisableOtherOptions,
 	updateCheckboxes,
 	changeStatusOtherOptions,
 	reinicializeInputs
} from "/js/general.js";

import { configAmountValidations } from "/js/amountValidations.js";

import { validateDates } from "/js/dateValidations.js";

//Ids de los botones de ordenar y aplicar filtros de los pedidos:
const applyOrderSOButtonId = "applyOrderSOButton";
const applyFiltersSOButtonId = "applyFiltersSOButton";
const soButtonIds = [applyOrderSOButtonId, applyFiltersSOButtonId];

//Ids de los botones de ordenar y aplicar filtros de los lotes:
const applyOrderLButtonId = "applyOrderLButton";
const applyFiltersLButtonId = "applyFiltersLButton";
const lButtonIds = [applyOrderLButtonId, applyFiltersLButtonId];

//Criterios de ordenamiento por defecto de los pedidos y los lotes:
const defaultSOOrder = "orderDescByAmount";
const defaultLOrder = "orderAscByExistingAmount";

//Name de la etiqueta que permite seleccionar el ordenamiento de los pedidos y de los lotes:
const orderSOName = "orderSO";
const orderLName = "orderL";

//Ids de los inputs de cantidad de los pedidos:
const amountSOInputIds = ["amount", "fAmount", "uAmount", "rFAmount", "rUAmount"];

//Ids de los inputs de cantidad y fecha de los lotes:
const amountLInputIds = ["eAmount", "fEAmount", "uEAmount", "rFEAmount", "rUEAmount"];
const dateLInputIds = ["rDate", "fRDate", "uRDate", "rFRDate", "rURDate"];

//Definimos los nombres de las secciones:
let filtersSOSections = ["pCode", "sName"];
let filtersLSections = ["stockId"];

//Definimos la configuración para los inputs de los pedidos:
const amountsSOConfig =
{
    inputs: 
    [
        { id: "amount", min: 1 },
        { id: "fAmount", min: 1 },
        { id: "uAmount", min: 1 },
        { range: ["rFAmount", "rUAmount"], min: 1 }
    ],
    buttonIds: soButtonIds
};

//Definimos la configuración para los inputs de los lotes:
const amountsLConfig =
{
    inputs: 
    [
        { id: "eAmount", min: 0 },
        { id: "fEAmount", min: 0 },
        { id: "uEAmount", min: 0 },
        { range: ["rFEAmount", "rUEAmount"], min: 0 }
    ],
    buttonIds: lButtonIds
}

//Unificamos ambas configuraciones:
const amountsConfig = [amountsSOConfig, amountsLConfig];

//Definimos la configuración de los filtros a chequear para habilitar o no los botones:
const sections = [filtersSOSections, filtersLSections];

//Definimos la configuración de los inputs de las fechas:
const datesConfig = {rangeFromDateId: "rFRDate", rangeUntilDateId: "rURDate", buttonIds: lButtonIds};

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
    reinicializeInputs(amountSOInputIds, soButtonIds); //Cantidades.
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
    reinicializeInputs(dateLInputIds, lButtonIds); //Fechas.
    reinicializeInputs(amountLInputIds, lButtonIds); //Cantidades.
}

/* APLICAMOS LOS FILTROS Y EL ORDENAMIENTO A LOS PEDIDOS Y ACTUALIZAMOS LA VISTA CON LOS QUE APLIQUEN */
function filterSupplyOrders()
{
	const order = getOrderValue(orderSOName, defaultSOOrder); //Obtenemos el criterio de ordenamiento.
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
	const order = getOrderValue(orderLName, defaultLOrder); //Obtenemos el criterio de ordenamiento.
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

/* RESETEAMOS LOS FILTROS DE LOS PEDIDOS Y OBTENEMOS LOS PEDIDOS QUE APLIQUEN A ESA CONFIGURACIÓN */
function resetSupplyOrdersFilters()
{
	const order = getOrderValue(orderSOName, defaultSOOrder); //Obtenemos el criterio de ordenamiento.
	
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
		    reinicializeInputs(amountSOInputIds, soButtonIds);
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
	const order = getOrderValue(orderLName, defaultLOrder); //Obtenemos el criterio de ordenamiento.
	
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
		    reinicializeInputs(dateLInputIds, lButtonIds);
		    
		    //Limpiamos el valor de los inputs de cantidad:
		    reinicializeInputs(amountLInputIds, lButtonIds);
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
//Cuando carga el DOM asignamos la configuración a los inputs para poder hacer las validaciones:
document.addEventListener("DOMContentLoaded", () => configAmountValidations(amountsConfig, sections));

/* ESCUCHAMOS LA ENTRADA DE DATOS EN LOS INPUTS DE FECHAS DE LOS LOTES */
//Cuando se selecciona una fecha en el input de fecha desde en un rango o en el de fecha hasta de un rango, validamos:
document.getElementById(datesConfig.rangeFromDateId).addEventListener("change", () => 
{
	validateDates(datesConfig);
	checkFiltersState(filtersLSections, lButtonIds);	
});
document.getElementById(datesConfig.rangeUntilDateId).addEventListener("change", () => 
{
	validateDates(datesConfig);
	checkFiltersState(filtersLSections, lButtonIds);		
});

/* ORDENAMOS LOS PEDIDOS */ 
document.getElementById(applyOrderSOButtonId).addEventListener("click", () => filterSupplyOrders());

/* ORDENAMOS LOS LOTES */ 
document.getElementById(applyOrderLButtonId).addEventListener("click", () => filterLots());

/* FILTRAMOS LOS PEDIDOS */ 
document.getElementById(applyFiltersSOButtonId).addEventListener("click", () => filterSupplyOrders());

/* FILTRAMOS LOS LOTES */ 
document.getElementById(applyFiltersLButtonId).addEventListener("click", () => filterLots());

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
            checkFiltersState(filtersSOSections, soButtonIds); //Habilitamos o deshabilitamos los botones según el estado del filtro.
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
        checkFiltersState(filtersLSections, lButtonIds); //Habilitamos o deshabilitamos los botones según el estado del filtro.
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
            	
            	const order = getOrderValue(orderSOName, defaultSOOrder); //Obtenemos el criterio de ordenamiento.
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
