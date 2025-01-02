//Importamos las siguientes funciones:
import 
{ 
	generateHTMLForSalesOrPurchases,
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
	checkFiltersState,
	updatePagination
} from "/js/general.js";

//Cantidad de compras por página:
const size = 6;

let usernames;
if(!document.getElementById("usernameContainer"))
{
	//Username del cliente:
	usernames = [document.getElementById("username").dataset.username];	
}

//Name de la sección de filtro:
const filterSections = ["methodOfPay"];

/* OBTENEMOS LOS VALORES DE CADA FILTRO DE LAS COMPRAS */
export function getPurchasesFiltersValues()
{
	return {
		date: document.getElementById("date").value || "", //Fecha.
		fromDate: document.getElementById("fDate").value || "", //Fecha posterior o igual a.
		untilDate: document.getElementById("uDate").value || "", //Fecha anterior o igual a.
		rangeFromDate: document.getElementById("rFDate").value || "", //Fecha posterior o igual a dentro de un rango.
		rangeUntilDate: document.getElementById("rUDate").value || "", //Fecha anterior o igual a dentro de un rango.
		fromTime: document.getElementById("fTime").value || "", //Hora posterior o igual a.
		untilTime: document.getElementById("uTime").value || "", //Hora anterior o igual a.
		rangeFromTime: document.getElementById("rFTime").value || "", //Hora posterior o igual a dentro de un rango.
		rangeUntilTime: document.getElementById("rUTime").value || "", //Hora anterior o igual a dentro de un rango.
		methodsOfPay: getSelectedValues("methodOfPay"), //Métodos de pago.
		fromPrice: document.getElementById("fPrice").value || "", //Fecha posterior o igual a.
		untilPrice: document.getElementById("uPrice").value || "", //Fecha anterior o igual a.
		rangeFromPrice: document.getElementById("rFPrice").value || "", //Fecha posterior o igual a dentro de un rango.
		rangeUntilPrice: document.getElementById("rUPrice").value || "" //Fecha anterior o igual a dentro de un rango.
	};
}

/* OBTENEMOS LAS COMPRAS QUE CUMPLEN CON DETERMINADOS FILTROS Y ORDENADAS SEGÚN DETERMINADO CRITERIO */
async function applyFilterPurchases(filtersData, page = 0, size = 6)
{
	//Realizamos la consulta para obtener las compras:
    return fetch(`/myAccount/customer/filter?page=${page}&size=${size}`, {
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
        return response.json(); //Retornamos el JSON con las compras.
    })
    .then(data => data)
    .catch(error => 
    {
        console.error("Error in the Fetch request:", error);
        throw error; //Re-lanza el error para que pueda ser manejado en la función que llame a filterPurchases.
    });
}

/* ACTUALIZAMOS LAS OPCIONES DE CADA FILTRO SEGÚN EL LISTADO DE COMPRAS ACTUAL */
export function updatePurchasesFilterOptions(filters, selectedFilters) 
{
	//Actualizamos la sección de filtros:
    updateCheckboxes("methodOfPayContainer", "methodOfPay", filters.methodsOfPay, selectedFilters.methodsOfPay); 
    
    //Cerramos los details de fechas:
    document.getElementById("datesContainer").removeAttribute("open"); //Contenedor general.
    document.getElementById("dateContainer").removeAttribute("open");  //Contenedor fecha específica.
    document.getElementById("fromDateContainer").removeAttribute("open"); //Contenedor fecha posterior o igual a.
    document.getElementById("untilDateContainer").removeAttribute("open"); //Contenedor fecha anterior o igual a.
    document.getElementById("rangeDateContainer").removeAttribute("open"); //Contenedor fecha dentro de un rango.
    
    //Cerramos los details de horas:
    document.getElementById("timesContainer").removeAttribute("open"); //Contenedor general.
    document.getElementById("fromTimeContainer").removeAttribute("open"); //Contenedor hora posterior o igual a.
    document.getElementById("untilTimeContainer").removeAttribute("open"); //Contenedor hora anterior o igual a.
    document.getElementById("rangeTimeContainer").removeAttribute("open"); //Contenedor hora dentro de un rango.
    
    //Cerramos los details de precios:
    document.getElementById("pricesContainer").removeAttribute("open"); //Contenedor general.
    document.getElementById("fromPriceContainer").removeAttribute("open"); //Contenedor precio mayor o igual a.
    document.getElementById("untilPriceContainer").removeAttribute("open"); //Contenedor precio menor o igual a.
    document.getElementById("rangePriceContainer").removeAttribute("open"); //Contenedor precio dentro de un rango.
}

/* GENERAMOS DETERMINADO HTML PARA CUANDO NO HAY COMPRAS ENCONTRADAS */
export function generateHTMLForEmptyPurchases()
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
}

/* APLICAMOS LOS FILTROS Y EL ORDENAMIENTO A LAS COMPRAS Y ACTUALIZAMOS LA VISTA CON LAS QUE APLIQUEN */
function filterPurchases(page = 0)
{
	const order = getOrderValue(orderName, defaultOrder); //Obtenemos el criterio de ordenamiento. 
	const filters = getPurchasesFiltersValues(); //Obtenemos los valores de filtrado.
	
	//Cargamos el criterio de ordenamiento y los de filtrado:
	const filtersData = {order, ...filters, usernames};
	
	//Filtramos y ordenamos según la configuración anterior:
	applyFilterPurchases(filtersData, page, size)
	.then(data => 
	{
		//Actualizamos las opciones de cada tipo de filtro según el listado resultante:
		updatePurchasesFilterOptions(data.filtersOptions, filters);
		
		//Si hay al menos una compra después del filtro:
		if(data.purchases.length > 0)
		{
			//Seleccionamos el body de la tabla:
			const tbody = document.getElementById("tbodyDataTable");
			
			//Generamos el HTML a partir de los datos obtenidos:
	        const htmlContent = generateHTMLForSalesOrPurchases(data.purchases);
	
	        //Actualizamos las compras en la vista:
	        tbody.innerHTML = htmlContent;	
		}
		else
		{
			//Generamos el HTML acorde a no haber encontrado resultados:
			generateHTMLForEmptyPurchases();
		}
		
		updatePagination(data.totalPages, Number.parseInt(page)); //Actualizamos las opciones de páginas.
    })
    .catch(error => 
    {
        console.error("There was an error applying the filters:", error);
    });
}

/* RESETEAMOS LOS FILTROS DE LAS COMPRAS Y OBTENEMOS LAS QUE APLIQUEN A ESA CONFIGURACIÓN */
function resetPurchasesFilters()
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
		methodsOfPay: ["all"],
		fromPrice: "",
		untilPrice: "",
		rangeFromPrice: "",
		rangeUntilPrice: ""
	};
	
	//Definimos el conjunto de los nuevos valores de filtrado:
	const filtersData = {order, ...filters, usernames};
	
	//Filtramos y ordenamos según la configuración anterior:
	applyFilterPurchases(filtersData)
	.then(data => 
	{
		//Actualizamos las opciones de cada tipo de filtro según el listado obtenido:
		updatePurchasesFilterOptions(data.filtersOptions, filters);
		
		//Si hubo resultados luego del filtrado:
		if(data.purchases.length > 0)
		{	
			//Seleccionamos el body de la tabla:
			const tbody = document.getElementById("tbodyDataTable");
			
			//Generamos el HTML a partir de los datos obtenidos:
		    const htmlContent = generateHTMLForSalesOrPurchases(data.purchases);
		        
			//Actualizamos las compras en la vista:
		    tbody.innerHTML = htmlContent;
		    
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
			generateHTMLForEmptyPurchases();
		}
		
		//Actualizamos las opciones de páginas:
		updatePagination(data.totalPages, 0);
    })
    .catch(error => 
    {
        console.error("There was an error applying the filters:", error);
    });
};

//Agregamos los oyentes de eventos solo si se trata de la página de compras:
if(!document.getElementById("usernameContainer"))
{
	/* ORDENAMOS LAS COMPRAS */ 
	document.getElementById(applyOrderButtonId).addEventListener("click", () => filterPurchases());
	
	/* FILTRAMOS LAS COMPRAS */ 
	document.getElementById(applyFiltersButtonId).addEventListener("click", () => filterPurchases());
	
	/* RESETEO DE LOS FILTROS DE LAS COMPRAS */
	document.getElementById("resetAllButton").addEventListener("click", () => resetPurchasesFilters());
	
	/* AGREGAMOS LISTENERS AL CONTENEDOR DE LAS SECCIÓN DE FILTRO POR MÉTODO DE PAGO DE LAS COMPRAS */
	//Obtenemos el elemento contenedor del input:
	const sectionMethodOfPayContainer = document.querySelector('details summary:has(~ input[name="methodOfPay"])').parentElement;
	
	//Escuchamos clics en él:
	sectionMethodOfPayContainer.addEventListener("click", (event) => 
	{
	    //Si el clic fue en un input dentro de la sección:
	    if(event.target.tagName === "INPUT" && event.target.type === "checkbox") 
	    {
	        checkFiltersState(filterSections, buttonIds); //Habilitamos o deshabilitamos los botones según el estado del filtro.
	    }
	});
	
	/* OBTENEMOS LAS COMPRAS CORRESPONDIENTES A CADA PÁGINA SEGÚN LOS FILTROS SELECCIONADOS */
	document.addEventListener("DOMContentLoaded", function () 
	{
	    const paginationContainer = document.getElementById("pagination"); //Seleccionamos el contenedor de los botones de las páginas.
	    paginationContainer.addEventListener("click", function (event) 
	    {
	        const button = event.target; //Obtenemos el botón de página clicleado.
	        if(button.tagName === "BUTTON")
	        {
	            const pageNum = button.getAttribute("data-page"); //Obtenemos el número de página en cuestión.
	            filterPurchases(pageNum); //Disparamos la solicitud de las compras de esa página.
	        }
	    });
	});
}
