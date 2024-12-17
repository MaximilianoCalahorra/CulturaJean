//Importamos las siguientes funciones:
import { getPurchasesFiltersValues, updatePurchasesFilterOptions, generateHTMLForEmptyPurchases } from "/js/purchases.js";

import 
{ 
	applyOrderButtonId,
	applyFiltersButtonId,
	buttonIds,
	orderName,
	defaultOrder,
	dateInputIds,
	timeInputIds,
	priceInputIds,
	generateHTMLForSalesOrPurchases 
} from "/js/purchasesAndSales.js";

import 
{ 
	getOrderValue, 
	getSelectedValues,
	descheckedAndDisableOtherOptions, 
	updateCheckboxes,
	reinicializeInputs,
	changeStatusOtherOptions,
	checkFiltersState
} from "/js/general.js";

const filterSections = ["username", "methodOfPay"];

/* OBTENEMOS LOS VALORES DE CADA FILTRO DE LAS VENTAS */
function getSalesFiltersValues()
{
	return {
		...getPurchasesFiltersValues(), //Demás filtros.
		usernames: getSelectedValues("username"), //Usernames.
	}
}

/* OBTENEMOS LAS VENTAS QUE CUMPLEN CON DETERMINADOS FILTROS Y ORDENADAS SEGÚN DETERMINADO CRITERIO */
async function applyFilterSales(filtersData)
{
	//Realizamos la consulta para obtener las ventas:
    return fetch(`/sale/sales/filter`, {
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
function updateSalesFilterOptions(sales, selectedFilters) 
{
    //Extraemos valores únicos para los usernames:
    const usernames = [...new Set(sales.map(item => item.member.username))];
	
	//Actualizamos la sección de filtros:
    updateCheckboxes("usernameContainer", "username", usernames, selectedFilters.usernames);
    
    //Actualizamos las demás secciones:
    updatePurchasesFilterOptions(sales, selectedFilters); 
}

/* GENERAMOS DETERMINADO HTML PARA CUANDO NO HAY VENTAS ENCONTRADAS */
function generateHTMLForEmptySales()
{
	//Generamos el HTML y reinicializamos los otros filtros:
	generateHTMLForEmptyPurchases();
	
	//Reinicializamos los valores del filtro de usernames: 
    document.getElementById("username-all").checked = true; //Usernames.
}

/* APLICAMOS LOS FILTROS Y EL ORDENAMIENTO A LAS VENTAS Y ACTUALIZAMOS LA VISTA CON LAS QUE APLIQUEN */
function filterSales()
{
	const order = getOrderValue(orderName, defaultOrder); //Obtenemos el criterio de ordenamiento. 
	const filters = getSalesFiltersValues(); //Obtenemos los valores de filtrado.
	
	//Cargamos el criterio de ordenamiento y los de filtrado:
	const filtersData = {order, ...filters};
	
	//Filtramos y ordenamos según la configuración anterior:
	applyFilterSales(filtersData)
	.then(data => 
	{
		//Actualizamos las opciones de cada tipo de filtro según el listado resultante:
		updateSalesFilterOptions(data, filters);
		
		//Seleccionamos el body de la tabla:
		const tbody = document.getElementById("tbodyDataTable");
		
		//Si hay al menos un pedido después del filtro:
		if(data.length > 0)
		{
			//Generamos el HTML a partir de los datos obtenidos:
	        const htmlContent = generateHTMLForSalesOrPurchases(data); 
	
	        //Actualizamos los pedidos en la vista:
	        tbody.innerHTML = htmlContent;	
		}
		else
		{
			//Generamos el HTML acorde a no haber encontrado resultados:
			generateHTMLForEmptySales();
		}
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
		fromPrice: "-1",
		untilPrice: "-1",
		rangeFromPrice: "-1",
		rangeUntilPrice: "-1"
	};
	
	//Definimos el conjunto de los nuevos valores de filtrado:
	const filtersData = {order, ...filters};
	
	//Filtramos y ordenamos según la configuración anterior:
	applyFilterSales(filtersData)
	.then(data => 
	{
		//Actualizamos las opciones de cada tipo de filtro según el listado obtenido:
		updateSalesFilterOptions(data, filters);
		
		//Si hubo resultados luego del filtrado:
		if(data.length > 0)
		{	
			//Seleccionamos el body de la tabla:
			const tbody = document.getElementById("tbodyDataTable");
			
			//Generamos el HTML a partir de los datos obtenidos:
		    const htmlContent = generateHTMLForSalesOrPurchases(data);
		        
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
	        checkFiltersState(filterSections, buttonIds); //Habilitamos o deshabilitamos los botones según el estado del filtro.
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
	        checkFiltersState(filterSections, buttonIds); //Habilitamos o deshabilitamos los botones según el estado del filtro.
	    }
	});
}
