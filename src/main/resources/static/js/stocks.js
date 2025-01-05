//Importamos las siguientes funciones:
import 
{ 
	getOrderValue,
	updateCheckboxes, 
	descheckedAndDisableOtherOptions, 
	reinicializeInputs,
	checkFiltersState,
	updatePagination
} from "/js/general.js";

import 
{ 
	applyOrderButtonId,
	applyFiltersButtonId,
	buttonIds,
	orderName,
	defaultOrder,
	priceInputIds,
	updateColorCheckboxes
} from "/js/productsAndStocks.js";

import { getProductsFiltersValues } from "/js/products.js";

//Names de las secciones de filtros:
const filterSections = ["cat", "gen", "size", "col"];

//Cantidad de stocks por página:
const size = 12;

//Id de la sección:
const containerIdS = "stocksSection";

//Ids de los inputs de cantidad:
const amountInputIds = ["amount", "fAmount", "uAmount", "rFAmount", "rUAmount"];

/* OBTENEMOS LOS VALORES DE CADA FILTRO DE LOS STOCKS */
function getStocksFiltersValues()
{
	return {
		...getProductsFiltersValues(), //Otros filtros.
		actualAmount: document.getElementById("amount").value || "", //Cantidad actual específica.
		fromActualAmount: document.getElementById("fAmount").value || "", //Cantidad actual o igual a.
		untilActualAmount: document.getElementById("uAmount").value || "", //Cantidad actual menor o igual a.
		rangeFromActualAmount: document.getElementById("rFAmount").value || "", //Cantidad actual mayor o igual dentro de un rango.
		rangeUntilActualAmount: document.getElementById("rUAmount").value || "", //Cantidad actual menor o igual dentro de un rango.
		state: document.querySelector('input[name="ena"]:checked') ? document.querySelector('input[name="ena"]:checked').value : "all" //Estados.
	};
}

/* OBTENEMOS LOS STOCKS QUE CUMPLEN CON DETERMINADOS FILTROS Y ORDENADAS SEGÚN DETERMINADO CRITERIO */
async function applyFilterStocks(filtersData, page = 0, size = 12)
{
	//Realizamos la consulta para obtener los stocks:
    return fetch(`/product/products/admin/filter?page=${page}&size=${size}`, {
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
        return response.json(); //Retornamos el JSON con los stocks.
    })
    .then(data => data)
    .catch(error => 
    {
        console.error("Error in the Fetch request:", error);
        throw error; //Re-lanza el error para que pueda ser manejado en la función que llame a filterStocks.
    });
}

/* ACTUALIZAMOS LAS OPCIONES DE CADA FILTRO SEGÚN EL LISTADO DE STOCKS ACTUAL */
function updateStocksFilterOptions(filters, selectedFilters) 
{
	//Actualizamos las secciones de filtros:
    updateCheckboxes("categoriesContainer", "cat", filters.categories, selectedFilters.categories); 
    updateCheckboxes("gendersContainer", "gen", filters.genders, selectedFilters.genders); 
    updateCheckboxes("sizesContainer", "size", filters.sizes, selectedFilters.sizes); 
    updateColorCheckboxes("colorsContainer", "col", filters.colors, selectedFilters.colors);
    
    //Cerramos los details de estados, de precios y de cantidades:
    document.getElementById("enabledContainer").removeAttribute("open"); //Contenedor general.
    
    document.getElementById("salePricesContainer").removeAttribute("open"); //Contenedor general.
    document.getElementById("specificSalePriceContainer").removeAttribute("open"); //Contenedor precio específico.
    document.getElementById("fromSalePriceContainer").removeAttribute("open"); //Contenedor precio mayor o igual a.
    document.getElementById("untilSalePriceContainer").removeAttribute("open"); //Contenedor precio menor o igual a.
    document.getElementById("rangeSalePriceContainer").removeAttribute("open"); //Contenedor precio dentro de un rango.
    
    document.getElementById("actualAmountContainer").removeAttribute("open"); //Contenedor general.
    document.getElementById("specificActualAmountContainer").removeAttribute("open"); //Contenedor cantidad específico.
    document.getElementById("fromActualAmountContainer").removeAttribute("open"); //Contenedor cantidad mayor o igual a.
    document.getElementById("untilActualAmountContainer").removeAttribute("open"); //Contenedor cantidad menor o igual a.
    document.getElementById("rangeActualAmountContainer").removeAttribute("open"); //Contenedor cantidad dentro de un rango.
}

/* GENERAMOS EL HTML CON LOS DATOS DE LOS STOCKS OBTENIDOS */
function generateHTMLForStocks(stocks) 
{
    let html = '';
    stocks.forEach(stock => 
    {
        html += `<tr id="row-${stock.product.productId}">
				    <td>${stock.product.productId}</td>
					<td>${stock.product.code}</td>
					<td>${stock.product.category}</td>
				    <td>${stock.product.gender}</td>
				    <td>${stock.product.size}</td>
					<td><span style="background-color: ${stock.product.color}; color: ${stock.product.color};">Color</span></td>
					<td>${stock.product.cost}</td>
					<td>${stock.product.salePrice}</td>
					<td>${stock.product.name}</td>
					<td>${stock.product.description}</td>
					<td class="enabled-status">${stock.product.enabled ? 'Yes' : 'No'}</td>
					<td>${stock.actualAmount}</td>
					<td>${stock.desirableAmount}</td>
					<td>${stock.minimumAmount}</td>
					<td>
						<button><a href="/product/edit/${stock.product.productId}">Edit</a></button>
						<button class="change-status-btn" data-product-id="${stock.product.productId}" data-current-status="${stock.product.enabled}">${stock.product.enabled ? 'Remove' : 'Enable'}</button>
						<button><a href="/supplyOrder/supplyOrderForm/${stock.product.productId}">Generate Supply Order</a></button>
				    </td>
				</tr>`;
    });
    return html;
}

/* GENERAMOS DETERMINADO HTML PARA CUANDO NO HAY STOCKS ENCONTRADOS */
function generateHTMLForEmptyStocks()
{
	//Obtenemos el body de la tabla:
	const tbody = document.getElementById("tbody");
	
	//Definimos una única fila con el mensaje de que no se encontraron resultados:
	tbody.innerHTML = 
	`<tr>
		<td colspan="15" style="text-align: center; font-style: italic; color: gray;">
            No results found.
        </td>
	</tr>`;	
    
    //Reinicializamos los valores de los filtros: 
    document.getElementById("cat-all").checked = true; //Categorías.
    document.getElementById("gen-all").checked = true; //Géneros.
    document.getElementById("size-all").checked = true; //Talles.
    document.getElementById("col-all").checked = true; //Colores.
    document.getElementById("ena-all").checked = true; //Estados.
    reinicializeInputs(priceInputIds, buttonIds); //Precios. 
    reinicializeInputs(amountInputIds, buttonIds); //Cantidades. 
}

/* APLICAMOS LOS FILTROS Y EL ORDENAMIENTO A LOS STOCKS Y ACTUALIZAMOS LA VISTA CON LOS QUE APLIQUEN */
function filterStocks(page = 0)
{
	const order = getOrderValue(orderName, defaultOrder); //Obtenemos el criterio de ordenamiento. 
	const filters = getStocksFiltersValues(); //Obtenemos los valores de filtrado.
	
	//Cargamos el criterio de ordenamiento y los de filtrado:
	const filtersData = {order, ...filters};
	
	//Filtramos y ordenamos según la configuración anterior:
	applyFilterStocks(filtersData, page, size)
	.then(data => 
	{
		//Actualizamos las opciones de cada tipo de filtro según el listado resultante:
		updateStocksFilterOptions(data.filtersOptions, filters);
		
		//Seleccionamos el body de la tabla:
		const tbody = document.getElementById("tbody");
		
		//Si hay al menos un producto después del filtro:
		if(data.stocks.length > 0)
		{
			//Generamos el HTML a partir de los datos obtenidos:
	        const htmlContent = generateHTMLForStocks(data.stocks);
	
	        //Actualizamos los stocks en la vista:
	        tbody.innerHTML = htmlContent;	
		}
		else
		{
			//Generamos el HTML acorde a no haber encontrado resultados:
			generateHTMLForEmptyStocks();
		}
		
		updatePagination(data.totalPages, Number.parseInt(page)); //Actualizamos las opciones de páginas.
    })
    .catch(error => 
    {
        console.error("There was an error applying the filters:", error);
    });
}

/* RESETEAMOS LOS FILTROS DE LOS STOCKS Y OBTENEMOS LOS QUE APLIQUEN A ESA CONFIGURACIÓN */
function resetStocksFilters()
{
	const order = getOrderValue(orderName, defaultOrder); //Obtenemos el criterio de ordenamiento.
	
	//Definimos los nuevos valores de filtrado:
	const filters =
	{
		categories: ["all"],
		genders: ["all"],
		sizes: ["all"],
		colors: ["all"],
		salePrice: "",
		fromSalePrice: "",
		untilSalePrice: "",
		rangeFromSalePrice: "",
		rangeUntilSalePrice: "",
		actualAmount: "",
		fromActualAmount: "",
		untilActualAmount: "",
		rangeFromActualAmount: "",
		rangeUntilActualAmount: "",
		state: "all"
	};
	
	//Definimos el conjunto de los nuevos valores de filtrado:
	const filtersData = {order, ...filters};
	
	//Filtramos y ordenamos según la configuración anterior:
	applyFilterStocks(filtersData)
	.then(data => 
	{
		//Actualizamos las opciones de cada tipo de filtro según el listado obtenido:
		updateStocksFilterOptions(data.filtersOptions, filters);
		
		//Si hubo resultados luego del filtrado:
		if(data.stocks.length > 0)
		{	
			//Seleccionamos el body de la tabla:
			const tbody = document.getElementById("tbody");
			
			//Generamos el HTML a partir de los datos obtenidos:
		    const htmlContent = generateHTMLForStocks(data.stocks);
		        
			//Actualizamos los stocks en la vista:
		    tbody.innerHTML = htmlContent;
		    
		    //Tildamos la opción "all" para el filtro de categorías:
		    const allOptionCategories = document.getElementById("cat-all");
	    	allOptionCategories.checked = true;
		    
		    //Destildamos las demás opciones para el filtro de categorías:
		   	descheckedAndDisableOtherOptions("cat");
		   	
		    //Tildamos la opción "all" para el filtro de géneros:
		    const allOptionGenders = document.getElementById("gen-all");
	    	allOptionGenders.checked = true;
		    
		    //Destildamos las demás opciones para el filtro de géneros:
		   	descheckedAndDisableOtherOptions("gen");
		   	
		    //Tildamos la opción "all" para el filtro de talles:
		    const allOptionSizes = document.getElementById("size-all");
	    	allOptionSizes.checked = true;
		    
		    //Destildamos las demás opciones para el filtro de talles:
		   	descheckedAndDisableOtherOptions("size");
		   	
		    //Tildamos la opción "all" para el filtro de colores:
		    const allOptionColors = document.getElementById("col-all");
	    	allOptionColors.checked = true;
		    
		    //Destildamos las demás opciones para el filtro de colores:
		   	descheckedAndDisableOtherOptions("col");
		     
		    //Limpiamos el valor de los inputs de precio y de cantidad:
		    reinicializeInputs(priceInputIds, buttonIds);
		    reinicializeInputs(amountInputIds, buttonIds);
		}
		else
		{
			//Generamos el HTML acorde a no haber encontrado resultados:
			generateHTMLForEmptyStocks();
		}
		
		//Actualizamos las opciones de páginas:
		updatePagination(data.totalPages, 0);
    })
    .catch(error => 
    {
        console.error("There was an error applying the filters:", error);
    });
};

//Agregamos los oyentes de eventos solo si se trata de la página de stocks:
if(document.getElementById("enabledContainer"))
{
	/* ORDENAMOS LOS STOCKS */ 
	document.getElementById(applyOrderButtonId).addEventListener("click", () => filterStocks());
	
	/* FILTRAMOS LOS STOCKS */ 
	document.getElementById(applyFiltersButtonId).addEventListener("click", () => filterStocks());
	
	/* RESETEO DE LOS FILTROS DE LOS STOCKS */
	document.getElementById("resetAllButton").addEventListener("click", () => resetStocksFilters());
	
	/* AGREGAMOS LISTENERS AL CONTENEDOR DE LAS SECCIONES DE FILTRO DE LOS STOCKS */
	//Obtenemos el elemento contenedor del input:
	filterSections.forEach(sectionName => 
	{
		const sectionContainer = document.querySelector(`details summary:has(~ input[name="${sectionName}"])`).parentElement;
	
		//Escuchamos clics en él:
		sectionContainer.addEventListener("click", (event) => 
		{
		    //Si el clic fue en un input dentro de la sección:
		    if(event.target.tagName === "INPUT" && event.target.type === "checkbox") 
		    {
		        checkFiltersState(filterSections, buttonIds, containerIdS); //Habilitamos o deshabilitamos los botones según el estado del filtro.
		    }
		});
	});
	
	/* OBTENEMOS LOS STOCKS CORRESPONDIENTES A CADA PÁGINA SEGÚN LOS FILTROS SELECCIONADOS */
	document.addEventListener("DOMContentLoaded", function () 
	{
	    const paginationContainer = document.getElementById("pagination"); //Seleccionamos el contenedor de los botones de las páginas.
	    paginationContainer.addEventListener("click", function (event) 
	    {
	        const button = event.target; //Obtenemos el botón de página clicleado.
	        if(button.tagName === "BUTTON")
	        {
	            const pageNum = button.getAttribute("data-page"); //Obtenemos el número de página en cuestión.
	            filterStocks(pageNum); //Disparamos la solicitud de los stocks de esa página.
	        }
	    });
	});
}

//Seleccionamos el cuerpo de la tabla de stocks y escuchamos eventos de clic en él:
document.getElementById("tbody").addEventListener("click", (event) =>
{
	//Si el clic se hizo sobre algún botón de cambiar estado:
	if(event.target.classList.contains("change-status-btn"))
	{
    	const button = event.target; //Obtenemos el botón clicado.
    	const productId = button.getAttribute("data-product-id"); //Obtenemos el id del producto.
        const enabled = button.getAttribute("data-current-status"); //Obtenemos su estado actual.

        //Cambiamos el estado del producto:
        fetch(`/product/changeEnabled/${productId}/${enabled}`, {
            method: 'POST'
        })
        //Pasamos a JSON la respuesta del controlador:
        .then(response => response.json())
        .then(data => 
        {
			//Si no hubo errores:
            if(data.success) 
            {   
				//Obtenemos el nuevo estado del producto:
				const newStatus = data.newStatus;
				
				//Definimos el texto a mostrar en la celda y en el botón:
				//Suponemos que el producto quedó deshabilitado.
				let newStatusText = "No";
				button.textContent = "Enable";
				
				//Si está habilitado:
				if(newStatus)
				{
					//Cambiamos los textos:
					newStatusText = "Yes";
					button.textContent = "Remove";
				}
				
				//Obtenemos la fila del stock:
				const row = document.getElementById(`row-${productId}`);
				
				//Obtenemos el valor actual del filtro:
                const currentFilter = document.querySelector("input[name='ena']:checked").value;
                
                //Si está en todos los stocks:
                if(currentFilter === "all")
               	{	
					//Obtenemos la celda donde va el valor de 'enabled':
    				const enabledCell = row.querySelector(".enabled-status");
					enabledCell.textContent = newStatusText; //Actualiza el estado del producto en la tabla.
			  
			        button.setAttribute("data-current-status", newStatus); //Actualizamos el estado del producto en el botón.	
				}
				else
				{
					//Eliminamos la fila porque no coincide con el filtro aplicado:
            		row.remove();
            		
            		//Obtenemos los stocks que se adecúan a los filtros y actualizamos las opciones de los filtros:
            		filterStocks();
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
