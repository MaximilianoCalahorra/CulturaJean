//Importamos las siguientes funciones:
import { getPurchasesFiltersValues, updatePurchasesFilterOptions } from "/js/purchases.js";

import 
{ 
	applyOrderButtonId,
	applyFiltersButtonId,
	buttonIds,
	orderName,
	defaultOrder,
	dateInputIds,
	timeInputIds,
	priceInputIds
} from "/js/purchasesAndSales.js";

import 
{ 
	getOrderValue, 
	getSelectedValues,
	descheckedAndDisableOtherOptions, 
	updateCheckboxes,
	reinicializeInputs,
	changeStatusOtherOptions,
	checkFiltersState,
	updatePagination
} from "/js/general.js";

//Cantidad de ventas por página:
const size = 6;

//Names de las secciones de filtro:
const filterSections = ["username", "methodOfPay"];

//Id de la sección:
const containerIdS = "salesSection";

/* OBTENEMOS LOS VALORES DE CADA FILTRO DE LAS VENTAS */
function getSalesFiltersValues()
{
	return {
		...getPurchasesFiltersValues(), //Demás filtros.
		usernames: getSelectedValues("username"), //Usernames.
	}
}

/* OBTENEMOS LAS VENTAS QUE CUMPLEN CON DETERMINADOS FILTROS Y ORDENADAS SEGÚN DETERMINADO CRITERIO */
async function applyFilterSales(filtersData, page = 0, size = 6)
{
	//Realizamos la consulta para obtener las ventas:
    return fetch(`/sale/sales/filter?page=${page}&size=${size}`, {
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
        return response.json(); //Retornamos el JSON con las ventas.
    })
    .then(data => data)
    .catch(error => 
    {
        console.error("Error in the Fetch request:", error);
        throw error; //Re-lanza el error para que pueda ser manejado en la función que llame a filterSales.
    });
}

/* ACTUALIZAMOS LAS OPCIONES DE CADA FILTRO SEGÚN EL LISTADO DE VENTAS ACTUAL */
function updateSalesFilterOptions(filters, selectedFilters) 
{
    //Actualizamos la sección de filtros:
    updateCheckboxes("usernameContainer", "username", filters.usernames, selectedFilters.usernames);
    
    //Actualizamos las demás secciones:
    updatePurchasesFilterOptions(filters, selectedFilters); 
}

/* GENERAMOS EL HTML CON LOS DATOS DE LAS VENTAS OBTENIDAS */
function generateHTMLForSales(sales) 
{
    let html = '';
    sales.forEach(sale => 
    {
        html += `<tr>
                	<td>
                    	<details>
                            <summary>${sale.purchaseId}</summary>
                            <summary>Details Of The Sale</summary>
                            <table border="3">
                                <thead>
                                    <tr>
                                        <th>Sale Item Id</th>
                                        <th>Username</th>
                                        <th>Product Code</th>
                                        <th>Amount</th>
                                        <th>Subtotal Sale</th>
                                    </tr>
                                </thead>
                                <tbody>`;
                                
        sale.purchaseItems.forEach(purchaseItem => 
        {
            html += `<tr>
                        <td>${purchaseItem.purchaseItemId}</td>
                        <td>${purchaseItem.product.code}</td>
                        <td>${purchaseItem.amount}</td>
                        <td>${purchaseItem.totalPrice}</td>
                    </tr>`;
        });

        html += `       </tbody>
                        </table>
                    </details>
                </td>
                <td>${sale.member.username}</td>
                <td>${sale.methodOfPay}</td>
                <td>${sale.dateTime}</td>
                <td>${sale.totalPrice}</td>
            </tr>`;
    });
    return html;
}

/* GENERAMOS DETERMINADO HTML PARA CUANDO NO HAY VENTAS ENCONTRADAS */
function generateHTMLForEmptySales()
{
	//Obtenemos el body de la tabla:
	const tbody = document.getElementById("tbodyDataTable");
	
	//Definimos una única fila con el mensaje de que no se encontraron resultados:
	tbody.innerHTML = 
	`<tr>
		<td colspan="5" style="text-align: center; font-style: italic; color: gray;">
            No results found.
        </td>
	</tr>`;	
    
    //Reinicializamos los valores de los filtros: 
    document.getElementById("methodOfPay-all").checked = true; //Métodos de pago.
    reinicializeInputs(dateInputIds, buttonIds); //Fechas. 
    reinicializeInputs(timeInputIds, buttonIds); //Horas. 
    reinicializeInputs(priceInputIds, buttonIds); //Precios.
    document.getElementById("username-all").checked = true; //Usernames.
}

/* APLICAMOS LOS FILTROS Y EL ORDENAMIENTO A LAS VENTAS Y ACTUALIZAMOS LA VISTA CON LAS QUE APLIQUEN */
function filterSales(page = 0)
{
	const order = getOrderValue(orderName, defaultOrder); //Obtenemos el criterio de ordenamiento. 
	const filters = getSalesFiltersValues(); //Obtenemos los valores de filtrado.
	
	//Cargamos el criterio de ordenamiento y los de filtrado:
	const filtersData = {order, ...filters};
	
	//Filtramos y ordenamos según la configuración anterior:
	applyFilterSales(filtersData, page, size)
	.then(data => 
	{
		//Actualizamos las opciones de cada tipo de filtro según el listado resultante:
		updateSalesFilterOptions(data.filtersOptions, filters);
		
		//Seleccionamos el body de la tabla:
		const tbody = document.getElementById("tbodyDataTable");
		
		//Si hay al menos un pedido después del filtro:
		if(data.purchases.length > 0)
		{
			//Generamos el HTML a partir de los datos obtenidos:
	        const htmlContent = generateHTMLForSales(data.purchases); 
	
	        //Actualizamos los pedidos en la vista:
	        tbody.innerHTML = htmlContent;	
		}
		else
		{
			//Generamos el HTML acorde a no haber encontrado resultados:
			generateHTMLForEmptySales();
		}
		
		updatePagination(data.totalPages, Number.parseInt(page)); //Actualizamos las opciones de páginas.
    })
    .catch(error => 
    {
        console.error("There was an error applying the filters:", error);
    });
}

/* RESETEAMOS LOS FILTROS DE LAS VENTAS Y OBTENEMOS LAS QUE APLIQUEN A ESA CONFIGURACIÓN */
function resetSalesFilters()
{
	const order = getOrderValue(orderName, defaultOrder); //Obtenemos el criterio de ordenamiento.
	
	//Definimos los nuevos valores de filtrado:
	const filters =
	{
		date: "",
		fromDate: "",
		untilDate: "",
		rangeFromDate: "",
		rangeUntilDate: "",
		fromTime: "",
		untilTime: "",
		rangeFromTime: "",
		rangeUntilTime: "",
		usernames: ["all"],
		methodsOfPay: ["all"],
		fromPrice: "",
		untilPrice: "",
		rangeFromPrice: "",
		rangeUntilPrice: ""
	};
	
	//Definimos el conjunto de los nuevos valores de filtrado:
	const filtersData = {order, ...filters};
	
	//Filtramos y ordenamos según la configuración anterior:
	applyFilterSales(filtersData)
	.then(data => 
	{
		//Actualizamos las opciones de cada tipo de filtro según el listado obtenido:
		updateSalesFilterOptions(data.filtersOptions, filters);
		
		//Si hubo resultados luego del filtrado:
		if(data.purchases.length > 0)
		{	
			//Seleccionamos el body de la tabla:
			const tbody = document.getElementById("tbodyDataTable");
			
			//Generamos el HTML a partir de los datos obtenidos:
		    const htmlContent = generateHTMLForSales(data.purchases);
		        
			//Actualizamos los pedidos en la vista:
		    tbody.innerHTML = htmlContent;
		    
		    //Tildamos la opción "all" para el filtro de usernames:
		    const allOptionUsernames = document.getElementById("username-all");
	    	allOptionUsernames.checked = true;
		    
		    //Destildamos las demás opciones para el filtro de usernames:
		   	descheckedAndDisableOtherOptions("username");
		   	
		    //Tildamos la opción "all" para el filtro de métodos de pago:
		    const allOptionMethodsOfPay = document.getElementById("methodOfPay-all");
	    	allOptionMethodsOfPay.checked = true;
		    
		    //Destildamos las demás opciones para el filtro de métodos de pago:
		   	descheckedAndDisableOtherOptions("methodOfPay");
		     
		    //Limpiamos el valor de los inputs de fecha, hora y precio:
		    reinicializeInputs(dateInputIds, buttonIds);
		    reinicializeInputs(timeInputIds, buttonIds);
		    reinicializeInputs(priceInputIds, buttonIds);
		}
		else
		{
			//Generamos el HTML acorde a no haber encontrado resultados:
			generateHTMLForEmptySales();
		}
		
		//Actualizamos las opciones de páginas:
		updatePagination(data.totalPages, 0);
    })
    .catch(error => 
    {
        console.error("There was an error applying the filters:", error);
    });
};

//Agregamos los oyentes de eventos solo si se trata de la página de ventas:
if(document.getElementById("usernameContainer"))
{
	/* ORDENAMOS LAS VENTAS */ 
	document.getElementById(applyOrderButtonId).addEventListener("click", () => filterSales());
	
	/* FILTRAMOS LAS VENTAS */ 
	document.getElementById(applyFiltersButtonId).addEventListener("click", () => filterSales());
	
	/* RESETEO DE LOS FILTROS DE LAS VENTAS */
	document.getElementById("resetAllButton").addEventListener("click", () => resetSalesFilters());	
	
	/* DETECTAMOS CLICS EN EL CHECKBOX "all" DE USERNAMES */
	document.getElementById("username-all").addEventListener("click", (event) => changeStatusOtherOptions(event, "username"));
	
	/* AGREGAMOS LISTENERS AL CONTENEDOR DE LAS SECCIÓN DE FILTRO POR USERNAME DE LAS VENTAS */
	//Obtenemos el elemento contenedor del input:
	const sectionUsernameContainer = document.querySelector('details summary:has(~ input[name="username"])').parentElement;
	
	//Escuchamos clics en él:
	sectionUsernameContainer.addEventListener("click", (event) => 
	{
	    //Si el clic fue en un input dentro de la sección:
	    if(event.target.tagName === "INPUT" && event.target.type === "checkbox") 
	    {
	        checkFiltersState(filterSections, buttonIds, containerIdS); //Habilitamos o deshabilitamos los botones según el estado del filtro.
	    }
	});
	
	/* AGREGAMOS LISTENERS AL CONTENEDOR DE LAS SECCIÓN DE FILTRO POR MÉTODO DE PAGO DE LAS VENTAS */
	//Obtenemos el elemento contenedor del input:
	const sectionMethodOfPayContainer = document.querySelector('details summary:has(~ input[name="methodOfPay"])').parentElement;
	
	//Escuchamos clics en él:
	sectionMethodOfPayContainer.addEventListener("click", (event) => 
	{
	    //Si el clic fue en un input dentro de la sección:
	    if(event.target.tagName === "INPUT" && event.target.type === "checkbox") 
	    {
	        checkFiltersState(filterSections, buttonIds, containerIdS); //Habilitamos o deshabilitamos los botones según el estado del filtro.
	    }
	});
	
	/* OBTENEMOS LAS VENTAS CORRESPONDIENTES A CADA PÁGINA SEGÚN LOS FILTROS SELECCIONADOS */
	document.addEventListener("DOMContentLoaded", function () 
	{
	    const paginationContainer = document.getElementById("pagination"); //Seleccionamos el contenedor de los botones de las páginas.
	    paginationContainer.addEventListener("click", function (event) 
	    {
	        const button = event.target; //Obtenemos el botón de página clicleado.
	        if(button.tagName === "BUTTON")
	        {
	            const pageNum = button.getAttribute("data-page"); //Obtenemos el número de página en cuestión.
	            filterSales(pageNum); //Disparamos la solicitud de las ventas de esa página.
	        }
	    });
	});
}
