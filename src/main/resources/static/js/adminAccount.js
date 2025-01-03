//Importamos las funcionalidad que necesitamos:
import 
{ 
	getOrderValue, 
	getSelectedValues, 
	descheckedAndDisableOtherOptions, 
	updateCheckboxes, 
	changeStatusOtherOptions, 
	checkFiltersState, 
	reinicializeInputs,
	updatePagination
} from "/js/general.js";

import { configAmountValidationsGroup } from "/js/amountValidations.js";

//Cantidad de pedidos de aprovisionamiento por página:
const size = 10;

//Ids de los botones de ordenar y aplicar filtros:
const applyOrderButtonId = "applyOrderButton";
const applyFiltersButtonId = "applyFilterButton";
const buttonIds = [applyOrderButtonId, applyFiltersButtonId];

//Valor de ordenamiento por defecto:
const defaultOrder = "p.code ASC";

//Name de la etiqueta para seleccionar el orden:
const orderName = "order";

//Ids de los inputs de cantidad:
const amountInputIds = ["amount", "fAmount", "uAmount", "rFAmount", "rUAmount"];

//Definimos la configuración para los inputs de los pedidos de aprovisionamiento:
const amountsSupplyOrdersConfig =
{
    inputs: 
    [
        { id: "amount", min: 1 },
        { id: "fAmount", min: 1 },
        { id: "uAmount", min: 1 },
        { range: ["rFAmount", "rUAmount"], min: 1 }
    ],
    buttonIds: buttonIds
};

//Definimos la configuración de los filtros a chequear para habilitar o no los botones:
const sectionsFilters = ["pCode", "sName", "delivered"];

/* OBTENEMOS EL USERNAME DEL ADMINISTRADOR */
function getAdminUsernameValue()
{
	return [document.getElementById("adminUsername").dataset.adminUsername];
}

/* OBTENEMOS LOS VALORES DE CADA FILTRO */
function getFiltersValues()
{
	return {
		productCodes: getSelectedValues("pCode"), //Códigos de producto.
		supplierNames: getSelectedValues("sName"), //Nombre de proveedor.
		adminUsernames: getAdminUsernameValue(), //Username del administrador.
		amount: document.querySelector('input[name="amount"]').value || "", //Cantidad.
		fromAmount: document.querySelector('input[name="fAmount"]').value || "", //Cantidad mayor o igual a.
		untilAmount: document.querySelector('input[name="uAmount"]').value || "", //Cantidad menor o igual a.
		rangeFromAmount: document.querySelector('input[name="rFAmount"]').value || "", //Cantidad mayor o igual a dentro de un rango.
		rangeUntilAmount: document.querySelector('input[name="rUAmount"]').value || "", //Cantidad menor o igual a dentro de un rango.
		delivered: document.querySelector('input[name="delivered"]:checked') ? 
				   document.querySelector('input[name="delivered"]:checked').value : 'all' //Estado de los pedidos.
	};
}

/* OBTENEMOS LOS PEDIDOS QUE CUMPLEN CON DETERMINADOS FILTROS Y ORDENADOS SEGÚN DETERMINADO CRITERIO */
async function applyFilterSupplyOrders(filtersData, page = 0, size = 10)
{
	//Realizamos la consulta para obtener los pedidos:
    return fetch(`/myAccount/admin/filter?page=${page}&size=${size}`, {
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
                    <td>${supplyOrder.supplier.name}</td>
                    <td>${supplyOrder.amount}</td>
                    <td>${delivered}</td>
                 </tr>`;
    });
    return html;
}

/* ACTUALIZAMOS LAS OPCIONES DE CADA FILTRO SEGÚN EL LISTADO DE PEDIDOS ENTREGADOS/NO ENTREGADOS ACTUAL */
function updateFilterOptions(filters, selectedFilters) 
{
	//Actualizar cada sección de filtros:
    updateCheckboxes("pCodeContainer", "pCode", filters.productCodes, selectedFilters.productCodes);
    updateCheckboxes("sNameContainer", "sName", filters.supplierNames, selectedFilters.supplierNames);
    
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
        <td colspan="5" style="text-align: center; font-style: italic; color: gray;">
            No results found.
        </td>
    </tr>`;	
    
    //Reinicializamos los valores de los filtros: 
    document.getElementById("pCode-all").checked = true; //Códigos de producto.
    document.getElementById("sName-all").checked = true; //Nombres de proveedor.
    reinicializeInputs(amountInputIds, buttonIds); //Cantidades.
    document.getElementById("delivered-all").checked = true; //Estado de los pedidos.
}

/* APLICAMOS LOS FILTROS Y EL ORDENAMIENTO A LOS PEDIDOS Y ACTUALIZAMOS LA VISTA CON LOS QUE APLIQUEN */
function filterSupplyOrders(page = 0)
{
	const order = getOrderValue(orderName, defaultOrder); //Obtenemos el criterio de ordenamiento.
	const filters = getFiltersValues(); //Obtenemos los valores de filtrado.
	
	//Cargamos el criterio de ordenamiento y los de filtrado:
	const filtersData = {order, ...filters};
	
	//Filtramos y ordenamos según la configuración anterior:
	applyFilterSupplyOrders(filtersData, page, size)
	.then(data => 
	{
		//Actualizamos las opciones de cada tipo de filtro según el listado resultante:
		updateFilterOptions(data.filtersOptions, filters);
		
		//Seleccionamos el body de la tabla:
		const tbody = document.getElementById("tbodySupplyOrdersTable");
		
		//Si hay al menos un pedido después del filtro:
		if(data.supplyOrders.length > 0)
		{
			//Generamos el HTML a partir de los datos obtenidos:
	        const htmlContent = generateHTMLForSupplyOrders(data.supplyOrders);
	
	        //Actualizamos los pedidos en la vista:
	        tbody.innerHTML = htmlContent;	
		}
		else
		{
			//Generamos el HTML acorde a no haber encontrado resultados:
			generateHTMLForEmptyResults();
		}
		
		updatePagination(data.totalPages, Number.parseInt(page)); //Actualizamos las opciones de páginas.
    })
    .catch(error => 
    {
        console.error("There was an error applying the filters:", error);
    });
}

/* RESETEAMOS LOS FILTROS DE LOS PEDIDOS Y OBTENEMOS LOS PEDIDOS QUE APLIQUEN A ESA CONFIGURACIÓN */
function resetFilters()
{
	const order = getOrderValue(orderName, defaultOrder); //Obtenemos el criterio de ordenamiento.
	
	//Definimos los nuevos valores de filtrado:
	const filters =
	{
		productCodes: ["all"],
		supplierNames: ["all"],
		adminUsernames: getAdminUsernameValue(),
		amount: "",
		fromAmount: "",
		untilAmount: "",
		rangeFromAmount: "",
		rangeUntilAmount: "",
		delivered: "all"
	};
	
	//Definimos el conjunto de los nuevos valores de filtrado:
	const filtersData = {order, ...filters};
	
	//Filtramos y ordenamos según la configuración anterior:
	applyFilterSupplyOrders(filtersData)
	.then(data => 
	{
		//Actualizamos las opciones de cada tipo de filtro según el listado obtenido:
	    updateFilterOptions(data.filtersOptions, filters);
		
		//Si hubo resultados luego del filtrado:
		if(data.supplyOrders.length > 0)
		{	
			//Seleccionamos el body de la tabla:
			const tbody = document.getElementById("tbodySupplyOrdersTable");
			
			//Generamos el HTML a partir de los datos obtenidos:
		    const htmlContent = generateHTMLForSupplyOrders(data.supplyOrders);
		        
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
		    reinicializeInputs(amountInputIds, buttonIds);
		    
		    //Tildamos la opción "all" para el filtro de estado de los pedidos:
		    const allOptionDelivered = document.getElementById("delivered-all");
	    	allOptionDelivered.checked = true;
		}
		else
		{
			//Generamos el HTML acorde a no haber encontrado resultados:
			generateHTMLForEmptyResults();
		}
		
		//Actualizamos las opciones de páginas:
		updatePagination(data.totalPages, 0);
    })
    .catch(error => 
    {
        console.error("There was an error applying the filters:", error);
    });
};

/* ESCUCHAMOS LA ENTRADA DE DATOS EN LOS INPUTS DE CANTIDADES */
//Cuando carga el DOM asignamos la configuración a los inputs para poder hacer las validaciones:
document.addEventListener("DOMContentLoaded", () => configAmountValidationsGroup(amountsSupplyOrdersConfig, sectionsFilters));

/* ORDENAMOS LOS PEDIDOS */ 
document.getElementById(applyOrderButtonId).addEventListener("click", () => filterSupplyOrders());

/* FILTRAMOS LOS PEDIDOS */ 
document.getElementById(applyFiltersButtonId).addEventListener("click", () => filterSupplyOrders());

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
            checkFiltersState(sectionsFilters, buttonIds); //Habilitamos o deshabilitamos los botones según el estado de los filtros.
        }
    });
});

/* OBTENEMOS LOS PEDIDOS DE APROVISIONAMIENTO CORRESPONDIENTES A CADA PÁGINA SEGÚN LOS FILTROS SELECCIONADOS */
document.addEventListener("DOMContentLoaded", function () 
{
    const paginationContainer = document.getElementById("pagination"); //Seleccionamos el contenedor de los botones de las páginas.
    paginationContainer.addEventListener("click", function (event) 
    {
        const button = event.target; //Obtenemos el botón de página clicleado.
        if(button.tagName === "BUTTON")
        {
            const pageNum = button.getAttribute("data-page"); //Obtenemos el número de página en cuestión.
            filterSupplyOrders(pageNum); //Disparamos la solicitud de los pedidos de aprovisionamiento de esa página.
        }
    });
});
