//Importamos las siguientes funciones:
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

//Names de las secciones de filtros:
const filterSections = ["cat", "gen", "size", "col"];

//Cantidad de productos por página:
const size = 6;

//Id de la sección:
const containerIdP = "productsSection";

/* OBTENEMOS LOS VALORES DE CADA FILTRO DE LOS PRODUCTOS */
export function getProductsFiltersValues()
{
	return {
		categories: getSelectedValues("cat"), //Categorías.
		genders: getSelectedValues("gen"), //Géneros.
		sizes: getSelectedValues("size"), //Talles.
		colors: getSelectedValues("col"), //Colores.
		salePrice: document.getElementById("sPri").value || "", //Precio específico.
		fromSalePrice: document.getElementById("fSPri").value || "", //Precio mayor o igual a.
		untilSalePrice: document.getElementById("uSPri").value || "", //Precio menor o igual a.
		rangeFromSalePrice: document.getElementById("rFSPri").value || "", //Precio mayor o igual dentro de un rango.
		rangeUntilSalePrice: document.getElementById("rUSPri").value || "", //Precio menor o igual dentro de un rango.
	};
}

/* OBTENEMOS LOS PRODUCTOS QUE CUMPLEN CON DETERMINADOS FILTROS Y ORDENADAS SEGÚN DETERMINADO CRITERIO */
async function applyFilterProducts(filtersData, page = 0, size = 6)
{
	//Realizamos la consulta para obtener los productos:
    return fetch(`/product/products/filter?page=${page}&size=${size}`, {
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
        return response.json(); //Retornamos el JSON con los productos.
    })
    .then(data => data)
    .catch(error => 
    {
        console.error("Error in the Fetch request:", error);
        throw error; //Re-lanza el error para que pueda ser manejado en la función que llame a filterProducts.
    });
}

/* ACTUALIZAMOS LAS OPCIONES DE CADA FILTRO SEGÚN EL LISTADO DE PRODUCTOS ACTUAL */
async function updateProductsFilterOptions(filters, selectedFilters) 
{
	//Actualizamos las secciones de filtros:
    updateCheckboxes("categoriesContainer", "cat", filters.categories, selectedFilters.categories); 
    updateCheckboxes("gendersContainer", "gen", filters.genders, selectedFilters.genders); 
    updateCheckboxes("sizesContainer", "size", filters.sizes, selectedFilters.sizes); 
    updateColorCheckboxes("colorsContainer", "col", filters.colors, selectedFilters.colors);
    
    //Cerramos los details de precios:
    document.getElementById("salePricesContainer").removeAttribute("open"); //Contenedor general.
    document.getElementById("specificSalePriceContainer").removeAttribute("open"); //Contenedor precio específico.
    document.getElementById("fromSalePriceContainer").removeAttribute("open"); //Contenedor precio mayor o igual a.
    document.getElementById("untilSalePriceContainer").removeAttribute("open"); //Contenedor precio menor o igual a.
    document.getElementById("rangeSalePriceContainer").removeAttribute("open"); //Contenedor precio dentro de un rango.
}

/* GENERAMOS EL HTML CON LOS DATOS DE LOS PRODUCTOS OBTENIDOS */
function generateHTMLForProducts(products, role) 
{
    let html = '';
    products.forEach(product => 
    {
        html += `<tr>
					<td>${product.name}</td>
					<td>${product.salePrice}</td>
					<td><img src="/assets/img/products/${product.gender}/${product.category}/${product.imageName}.jpeg"></td>
					<td>
						<button><a href="/product/moreDetails/${role}/${product.productId}">More Details</a></button>
					</td>
				</tr>`;
    });
    return html;
}

/* GENERAMOS DETERMINADO HTML PARA CUANDO NO HAY PRODUCTOS ENCONTRADOS */
function generateHTMLForEmptyProducts()
{
	//Obtenemos el body de la tabla:
	const tbody = document.getElementById("tbody");
	
	//Definimos una única fila con el mensaje de que no se encontraron resultados:
	tbody.innerHTML = 
	`<tr>
		<td colspan="4" style="text-align: center; font-style: italic; color: gray;">
            No results found.
        </td>
	</tr>`;	
    
    //Reinicializamos los valores de los filtros: 
    document.getElementById("cat-all").checked = true; //Categorías.
    document.getElementById("gen-all").checked = true; //Géneros.
    document.getElementById("size-all").checked = true; //Talles.
    document.getElementById("col-all").checked = true; //Colores.
    reinicializeInputs(priceInputIds, buttonIds); //Precios. 
}

/* APLICAMOS LOS FILTROS Y EL ORDENAMIENTO A LOS PRODUCTOS Y ACTUALIZAMOS LA VISTA CON LOS QUE APLIQUEN */
function filterProducts(role, page = 0)
{
	const order = getOrderValue(orderName, defaultOrder); //Obtenemos el criterio de ordenamiento. 
	const filters = getProductsFiltersValues(); //Obtenemos los valores de filtrado.
	
	//Cargamos el criterio de ordenamiento y los de filtrado:
	const filtersData = {order, ...filters};
	
	//Filtramos y ordenamos según la configuración anterior:
	applyFilterProducts(filtersData, page, size)
	.then(data => 
	{
		//Actualizamos las opciones de cada tipo de filtro según el listado resultante:
		updateProductsFilterOptions(data.filtersOptions, filters);
		
		//Seleccionamos el body de la tabla:
		const tbody = document.getElementById("tbody");
		
		//Si hay al menos un producto después del filtro:
		if(data.products.length > 0)
		{
			//Generamos el HTML a partir de los datos obtenidos:
	        const htmlContent = generateHTMLForProducts(data.products, role);
	
	        //Actualizamos los productos en la vista:
	        tbody.innerHTML = htmlContent;	
		}
		else
		{
			//Generamos el HTML acorde a no haber encontrado resultados:
			generateHTMLForEmptyProducts();
		}
		
		updatePagination(data.totalPages, Number.parseInt(page)); //Actualizamos las opciones de páginas.
    })
    .catch(error => 
    {
        console.error("There was an error applying the filters:", error);
    });
}

/* RESETEAMOS LOS FILTROS DE LOS PRODUCTOS Y OBTENEMOS LOS QUE APLIQUEN A ESA CONFIGURACIÓN */
function resetProductsFilters(role)
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
		rangeUntilSalePrice: ""
	};
	
	//Definimos el conjunto de los nuevos valores de filtrado:
	const filtersData = {order, ...filters};
	
	//Filtramos y ordenamos según la configuración anterior:
	applyFilterProducts(filtersData)
	.then(data => 
	{
		//Actualizamos las opciones de cada tipo de filtro según el listado obtenido:
		updateProductsFilterOptions(data.filtersOptions, filters);
		
		//Si hubo resultados luego del filtrado:
		if(data.products.length > 0)
		{	
			//Seleccionamos el body de la tabla:
			const tbody = document.getElementById("tbody");
			
			//Generamos el HTML a partir de los datos obtenidos:
		    const htmlContent = generateHTMLForProducts(data.products, role);
		        
			//Actualizamos los productos en la vista:
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
		     
		    //Limpiamos el valor de los inputs de precio:
		    reinicializeInputs(priceInputIds, buttonIds);
		}
		else
		{
			//Generamos el HTML acorde a no haber encontrado resultados:
			generateHTMLForEmptyProducts();
		}
		
		//Actualizamos las opciones de páginas:
		updatePagination(data.totalPages, 0);
    })
    .catch(error => 
    {
        console.error("There was an error applying the filters:", error);
    });
};

//Agregamos los oyentes de eventos solo si se trata de la página de productos:
if(!document.getElementById("enabledContainer"))
{
	//Obtenemos si el usuario es cliente o visitante:
	const role = document.getElementById("role").dataset.role;

	/* ORDENAMOS LOS PRODUCTOS */ 
	document.getElementById(applyOrderButtonId).addEventListener("click", () => filterProducts(role));
	
	/* FILTRAMOS LOS PRODUCTOS */ 
	document.getElementById(applyFiltersButtonId).addEventListener("click", () => filterProducts(role));
	
	/* RESETEO DE LOS FILTROS DE LOS PRODUCTOS */
	document.getElementById("resetAllButton").addEventListener("click", () => resetProductsFilters(role));
	
	/* AGREGAMOS LISTENERS AL CONTENEDOR DE LAS SECCIONES DE FILTRO DE LOS PRODUCTOS */
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
		        checkFiltersState(filterSections, buttonIds, containerIdP); //Habilitamos o deshabilitamos los botones según el estado del filtro.
		    }
		});
	});
	
	/* OBTENEMOS LOS PRODUCTOS CORRESPONDIENTES A CADA PÁGINA SEGÚN LOS FILTROS SELECCIONADOS */
	document.addEventListener("DOMContentLoaded", function () 
	{
	    const paginationContainer = document.getElementById("pagination"); //Seleccionamos el contenedor de los botones de las páginas.
	    paginationContainer.addEventListener("click", function (event) 
	    {
	        const button = event.target; //Obtenemos el botón de página clicleado.
	        if(button.tagName === "BUTTON")
	        {
	            const pageNum = button.getAttribute("data-page"); //Obtenemos el número de página en cuestión.
	            filterProducts(role, pageNum); //Disparamos la solicitud de los productos de esa página.
	        }
	    });
	});
}
