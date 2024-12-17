//Importamos las funciones necesarias:
import 
{
	getOrderValue,
	getSelectedValues,
	descheckedAndDisableOtherOptions,
	reinicializeInputs,
	updateCheckboxes,
	checkFiltersState,
	changeStatusOtherOptions
} from "/js/general.js";

import { configAmountValidations } from "/js/amountValidations.js";

//Ids de las etiquetas para seleccionar el criterio de ordenamiento de los pedidos entregados y de los no entregados:
const uOrderName = "orderU";
const dOrderName = "orderD";

//Criterios de ordenamiento por defecto de ambos tipos de pedidos:
const uDefaultOrder = "orderAscByProductCode";
const dDefaultOrder = "orderAscByProductCode";

//Ids de los botones de aplicar ordenamiento y filtros de los pedidos no entregados:
const applyOrderDButtonId = "applyOrderDeliveredButton";
const applyFiltersDButtonId = "applyFilterDeliveredButton";
const dButtonIds = [applyOrderDButtonId, applyFiltersDButtonId];

//Ids de los botones de aplicar ordenamiento y filtros de los pedidos entregados:
const applyOrderUButtonId = "applyOrderUndeliveredButton";
const applyFiltersUButtonId = "applyFilterUndeliveredButton";
const uButtonIds = [applyOrderUButtonId, applyFiltersUButtonId];

//Ids de las secciones de filtro de los pedidos entregados y de los no entregados:
const dSections = ["pCodeD", "sNameD", "usernameD"];
const uSections = ["pCodeU", "sNameU", "usernameU"];

//Definimos la configuración para los inputs de los pedidos no entregados:
const amountsUConfig =
{
    inputs: 
    [
        { id: "amountU", min: 1 },
        { id: "fAmountU", min: 1 },
        { id: "uAmountU", min: 1 },
        { range: ["rFAmountU", "rUAmountU"], min: 1 }
    ],
    buttonIds: uButtonIds
};

//Definimos la configuración para los inputs de los pedidos entregados:
const amountsDConfig =
{
    inputs: 
    [
        { id: "amountD", min: 1 },
        { id: "fAmountD", min: 1 },
        { id: "uAmountD", min: 1 },
        { range: ["rFAmountD", "rUAmountD"], min: 1 }
    ],
	buttonIds: dButtonIds
}

//Unificamos ambas configuraciones:
const amountsConfig = [amountsUConfig, amountsDConfig];

//Definimos la configuración de los filtros a chequear para habilitar o no el botón:
const sections = [uSections, dSections];

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
            No results found.
        </td>
    </tr>`;	
    
    //Reinicializamos los valores de los filtros: 
    document.getElementById(pCodeAll).checked = true; //Códigos de producto.
    document.getElementById(sNameAll).checked = true; //Nombres de proveedor.
    document.getElementById(usernameAll).checked = true; //Usernames de administrador.
    
    let amountInputIds = [];
    if(delivered)
    {
		amountInputIds = ["amountD", "fAmountD", "uAmountD", "rFAmountD", "rUAmountD"];	
	}
	else
	{
		amountInputIds = ["amountU", "fAmountU", "uAmountU", "rFAmountU", "rUAmountU"];
	}
    
    reinicializeInputs(amountInputIds); //Cantidades.
}

/* APLICAMOS LOS FILTROS Y EL ORDENAMIENTO A LOS PEDIDOS ENTREGADOS/NO ENTREGADOS Y ACTUALIZAMOS LA VISTA CON LOS QUE APLIQUEN */
function filterSupplyOrders(delivered)
{
	//Obtenemos el criterio de ordenamiento:
	let order;
	if(delivered)
	{
		order = getOrderValue(dOrderName, dDefaultOrder);
	}
	else
	{
		order = getOrderValue(uOrderName, uDefaultOrder);
	}
	
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
        console.error("There was an error applying the filters:", error);
    });
}

/* RESETEAMOS LOS FILTROS DE LOS PEDIDOS ENTREGADOS/NO ENTREGADOS Y OBTENEMOS LOS PEDIDOS QUE APLIQUEN A ESA CONFIGURACIÓN */
function resetFilters(delivered)
{
	//Obtenemos el criterio de ordenamiento:
	let order;
	if(delivered)
	{
		order = getOrderValue(dOrderName, dDefaultOrder);	
	}
	else
	{
		order = getOrderValue(uOrderName, uDefaultOrder);	
	}
	
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
		//Actualizamos las opciones de cada tipo de filtro según el listado obtenido:
		updateFilterOptions(data, delivered, filters);
		
		//Si hubo resultados luego del filtrado:
		if(data.length > 0)
		{
			//Configuración para los pedidos no entregados:
			let idTBody = "undeliveredSupplyOrderTBodyTable";
			let pCode = "pCodeU";
			let sName = "sNameU";
			let username = "usernameU";
			let amountInputIds = ["amountU", "fAmountU", "uAmountU", "rFAmountU", "rUAmountU"];
			let buttonIds = uButtonIds;
			
			//La modificamos si se trata de pedidos entregados:
			if(delivered)
			{
				idTBody = "deliveredSupplyOrderTBodyTable";
				pCode = "pCodeD";
				sName = "sNameD";
				username = "usernameD";
				amountInputIds = ["amountD", "fAmountD", "uAmountD", "rFAmountD", "rUAmountD"];
				buttonIds = dButtonIds;
			}
			
			//Seleccionamos el body de la tabla:
			const tbody = document.getElementById(idTBody);
			
			//Generamos el HTML a partir de los datos obtenidos:
		    const htmlContent = generateHTMLForSupplyOrders(delivered, data);
		        
			//Actualizamos los pedidos en la vista:
		    tbody.innerHTML = htmlContent;
		    
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
		    reinicializeInputs(amountInputIds, buttonIds);
		}
		else
		{
			//Generamos el HTML acorde a no haber encontrado resultados:
			generateHTMLForEmptyResults(delivered);
		}
    })
    .catch(error => 
    {
        console.error("There was an error applying the filters:", error);
    });
};

/* ORDENAMOS LOS PEDIDOS ENTREGADOS */ 
document.getElementById(applyOrderDButtonId).addEventListener("click", () => filterSupplyOrders(true));

/* ORDENAMOS LOS PEDIDOS NO ENTREGADOS */ 
document.getElementById(applyOrderUButtonId).addEventListener("click", () => filterSupplyOrders(false));

/* FILTRAMOS LOS PEDIDOS ENTREGADOS */ 
document.getElementById(applyFiltersDButtonId).addEventListener("click", () => filterSupplyOrders(true));

/* FILTRAMOS LOS PEDIDOS NO ENTREGADOS */ 
document.getElementById(applyFiltersUButtonId).addEventListener("click", () => filterSupplyOrders(false));

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
            	
            	const order = getOrderValue(uOrderName, uDefaultOrder); //Obtenemos el criterio de ordenamiento.
				
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
			        console.error("There was an error applying the filters:", error);
			    });
            	
            	//Aplicamos los filtros y ordenamientos de los pedidos entregados y actualizamos la vista con los que apliquen:
            	filterSupplyOrders(true);
            }
            else
            {
                console.error("Error changing state:", data.error);
            }
        })
        .catch(error => console.error("Error in the Fetch request:", error));
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
dSections.forEach(sectionName => 
{
	//Obtenemos el elemento contenedor del input:
    const sectionContainer = document.querySelector(`details summary:has(~ input[name="${sectionName}"])`).parentElement;

	//Escuchamos clics en él:
    sectionContainer.addEventListener("click", (event) => 
    {
        //Si el clic fue en un input dentro de la sección:
        if(event.target.tagName === "INPUT" && event.target.type === "checkbox") 
        {
            checkFiltersState(dSections, dButtonIds); //Habilitamos o deshabilitamos los botones según el estado del filtro.
        }
    });
});

/* AGREGAMOS LISTENERS A LOS CONTENEDORES DE LAS SECCIONES DE FILTRO DE LOS PEDIDOS NO ENTREGADOS */
uSections.forEach(sectionName => 
{
	//Obtenemos el elemento contenedor del input:
    const sectionContainer = document.querySelector(`details summary:has(~ input[name="${sectionName}"])`).parentElement;

	//Escuchamos clics en él:
    sectionContainer.addEventListener("click", (event) => 
    {
        //Si el clic fue en un input dentro de la sección:
        if(event.target.tagName === "INPUT" && event.target.type === "checkbox") 
        {
            checkFiltersState(uSections, uButtonIds); //Habilitamos o deshabilitamos los botones según el estado del filtro.
        }
    });
});

/* ESCUCHAMOS LA ENTRADA DE DATOS EN LOS INPUTS DE CANTIDADES DE LOS PEDIDOS ENTREGADOS Y DE LOS NO ENTREGADOS */
//Cuando carga el DOM asignamos la configuración a los inputs para poder hacer las validaciones:
document.addEventListener("DOMContentLoaded", () => configAmountValidations(amountsConfig, sections));
