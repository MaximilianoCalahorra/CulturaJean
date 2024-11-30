//Importamos la función para ocultar un mensaje de error en la vista:
import { hideError } from "/js/errorMessage.js";

//Importamos la función para validar los inputs de fechas:
import { validateDates } from "/js/dateValidations.js";

//Importamos la función para validar los inputs de horas:
import { validateTimes } from "/js/timeValidations.js";

//Importamos la función para validar los inputs de precios:
import { configPriceValidationsGroup } from "/js/priceValidations.js";

//Importamos la función para chequear si todas las secciones de checkboxes tienen al menos uno marcado:
import { checkFiltersState } from "/js/general.js";

/* OBTENEMOS EL CRITERIO DE ORDENAMIENTO SELECCIONADO PARA LAS VENTAS/COMPRAS */
export function getOrderValue(orderId)
{
	return document.getElementById(orderId).value || "orderDescByDate";
}

/* OBTENEMOS LOS VALORES SELECCIONADOS DE UNA SECCIÓN DE FILTRADO, OPTIMIZANDO SI CORRESPONDE ENVIAR "all" */
export function getSelectedValues(sectionName)
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

/* ACTIVAMOS LA OPCIÓN DE TODOS EN UNA SECCIÓN Y DESACTIVAMOS LAS DEMÁS */
export function descheckedAndDisableOtherOptions(sectionName)
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

/* REESTABLECEMOS LOS INPUTS DE FECHA DE LAS COMPRAS/VENTAS */
export function reinicializeDates()
{	
	//Fecha específica:
	const dateInput = document.getElementById("date");
	dateInput.value = ""; 
	hideError(dateInput);
	
	//Fecha posterior o igual a:
	const fromDateInput = document.getElementById("fDate");
	fromDateInput.value = ""; 
	hideError(fromDateInput);
	
	//Fecha anterior o igual a:
	const untilDateInput = document.getElementById("uDate");
	untilDateInput.value = ""; 
	hideError(untilDateInput);
	
	//Fecha posterior o igual a dentro de un rango:
	const rangeFromDateInput = document.getElementById("rFDate");
	rangeFromDateInput.value = ""; 
	hideError(rangeFromDateInput);
	
	//Fecha anterior o igual a dentro de un rango:
	const rangeUntilDateInput = document.getElementById("rUDate");
	rangeUntilDateInput.value = ""; 
	hideError(rangeUntilDateInput);
	
	//Habilitamos el botón de aplicar filtros:
	document.getElementById("applyFiltersButton").disabled = false;
}

/* REESTABLECEMOS LOS INPUTS DE HORA DE LAS COMPRAS/VENTAS */
export function reinicializeTimes()
{	
	//Hora posterior o igual a:
	const fromTimeInput = document.getElementById("fTime");
	fromTimeInput.value = ""; 
	hideError(fromTimeInput);
	
	//Hora anterior o igual a:
	const untilTimeInput = document.getElementById("uTime");
	untilTimeInput.value = ""; 
	hideError(untilTimeInput);
	
	//Hora posterior o igual a dentro de un rango:
	const rangeFromTimeInput = document.getElementById("rFTime");
	rangeFromTimeInput.value = ""; 
	hideError(rangeFromTimeInput);
	
	//Hora anterior o igual a dentro de un rango:
	const rangeUntilTimeInput = document.getElementById("rUTime");
	rangeUntilTimeInput.value = ""; 
	hideError(rangeUntilTimeInput);
	
	//Habilitamos el botón de aplicar filtros:
	document.getElementById("applyFiltersButton").disabled = false;
}

/* REESTABLECEMOS LOS INPUTS DE PRECIO DE LAS COMPRAS/VENTAS */
export function reinicializePrices()
{	
	//Precio mayor o igual a:
	const fromPriceInput = document.getElementById("fPrice");
	fromPriceInput.value = ""; 
	hideError(fromPriceInput);
	
	//Precio menor o igual a:
	const untilPriceInput = document.getElementById("uPrice");
	untilPriceInput.value = ""; 
	hideError(untilPriceInput);
	
	//Precio mayor o igual a dentro de un rango:
	const rangeFromPriceInput = document.getElementById("rFPrice");
	rangeFromPriceInput.value = ""; 
	hideError(rangeFromPriceInput);
	
	//Precio menor o igual a dentro de un rango:
	const rangeUntilPriceInput = document.getElementById("rUPrice");
	rangeUntilPriceInput.value = ""; 
	hideError(rangeUntilPriceInput);
	
	//Habilitamos el botón de aplicar filtros:
	document.getElementById("applyFiltersButton").disabled = false;
}

//Función para calcular el subtotal de un ítem:
const calculateSubtotal = (amount, price) => amount * price;

//Función para calcular el total de la compra:
const calculateTotalSale = (purchaseItems) => 
{
    return purchaseItems.reduce((total, item) => total + calculateSubtotal(item.amount, item.product.salePrice), 0);
};

/* GENERAMOS EL HTML CON LOS DATOS DE LAS VENTAS/COMPRAS OBTENIDAS */
export function generateHTMLForSalesOrPurchases(purchases) 
{
    let html = '';
    purchases.forEach(purchase => 
    {
        const totalSale = calculateTotalSale(purchase.purchaseItems);
        html += `<tr>
                	<td>
                    	<details>
                            <summary>${purchase.purchaseId}</summary>
                            <summary>Details Of The Sale</summary>
                            <table border="3">
                                <thead>
                                    <tr>
                                        <th>Sale Item Id</th>
                                        <th>Product Code</th>
                                        <th>Amount</th>
                                        <th>Subtotal Sale</th>
                                    </tr>
                                </thead>
                                <tbody>`;
                                
        purchase.purchaseItems.forEach(purchaseItem => 
        {
            const subtotal = calculateSubtotal(purchaseItem.amount, purchaseItem.product.salePrice);
            html += `<tr>
                        <td>${purchaseItem.purchaseItemId}</td>
                        <td>${purchaseItem.product.code}</td>
                        <td>${purchaseItem.amount}</td>
                        <td>${subtotal.toFixed(2)}</td>
                    </tr>`;
        });

        html += `       </tbody>
                        </table>
                    </details>
                </td>
                <td>${purchase.member.username}</td>
                <td>${purchase.methodOfPay}</td>
                <td>${purchase.dateTime}</td>
                <td>${totalSale.toFixed(2)}</td>
            </tr>`;
    });
    return html;
}

/* ACTUALIZAMOS LOS CHECKBOXES DE UNA SECCIÓN DE FILTRO */
export function updateCheckboxes(containerId, name, values, selectedValues) 
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
	                      <input type="checkbox" name=${name} value="${value}" id="${name}-${index + 1}" autocomplete="off"`;            
		            
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

/* ALTERAMOS LAS DEMÁS OPCIONES DE UNA SECCIÓN EN BASE AL ESTADO DE LA OPCIÓN "all" */
export function changeStatusOtherOptions(event, sectionName)
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

/* DETECTAMOS CLICS EN EL CHECKBOX "all" DE MÉTODOS DE PAGO */
document.getElementById("methodOfPay-all").addEventListener("click", (event) => changeStatusOtherOptions(event, "methodOfPay"));

//Configuración de filtros a chequear para compras:
let filtersSections = ["methodOfPay"];

//Si se trata de ventas, agregamos un filtro más a chequear:
if(document.getElementById("usernameContainer"))
{
	filtersSections.push("username");
}

/* ESCUCHAMOS LA ENTRADA DE DATOS EN LOS INPUTS DE FECHAS DE LAS COMPRAS/VENTAS */
//Definimos la configuración de los inputs de las fechas:
const datesConfig = {rangeFromDateId: "rFDate", rangeUntilDateId: "rUDate", applyFiltersButtonId: "applyFiltersButton"};

//Cuando se selecciona una fecha en el input de fecha desde en un rango o en el de fecha hasta de un rango, validamos:
document.getElementById(datesConfig.rangeFromDateId).addEventListener("change", () => 
{
	validateDates(datesConfig); 
	checkFiltersState(filtersSections, "applyFiltersButton");
});
document.getElementById(datesConfig.rangeUntilDateId).addEventListener("change", () =>
{
	validateDates(datesConfig);	
	checkFiltersState(filtersSections, "applyFiltersButton");
});

/* ESCUCHAMOS LA ENTRADA DE DATOS EN LOS INPUTS DE HORAS DE LAS COMPRAS/VENTAS */
//Definimos la configuración de los inputs de las horas:
const timesConfig = {rangeFromTimeId: "rFTime", rangeUntilTimeId: "rUTime", applyFiltersButtonId: "applyFiltersButton"};

//Cuando se selecciona una fecha en el input de hora desde en un rango o en el de hora hasta de un rango, validamos:
document.getElementById(timesConfig.rangeFromTimeId).addEventListener("change", () => 
{
	validateTimes(timesConfig);
	checkFiltersState(filtersSections, "applyFiltersButton");	
});
document.getElementById(timesConfig.rangeUntilTimeId).addEventListener("change", () =>
{
	validateTimes(timesConfig);
	checkFiltersState(filtersSections, "applyFiltersButton");
});

/* ESCUCHAMOS LA ENTRADA DE DATOS EN LOS INPUTS DE PRECIOS DE LAS COMPRAS/VENTAS */
//Definimos la configuración para los inputs de las compras/ventas:
const pricesConfig =
{
    inputs: 
    [
        { id: "fPrice", min: 0 },
        { id: "uPrice", min: 0 },
        { range: ["rFPrice", "rUPrice"], min: 0 }
    ],
    button: "applyFiltersButton"
};

//Cuando carga el DOM asignamos la configuración a los inputs para poder hacer las validaciones:
document.addEventListener("DOMContentLoaded", () => configPriceValidationsGroup(pricesConfig, filtersSections, "applyFiltersButton"));
