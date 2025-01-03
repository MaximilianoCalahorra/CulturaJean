//Importamos la función para validar los inputs de fechas:
import { validateDates } from "/js/dateValidations.js";

//Importamos la función para validar los inputs de horas:
import { validateTimes } from "/js/timeValidations.js";

//Importamos la función para validar los inputs de precios:
import { configPriceValidationsGroup } from "/js/priceValidations.js";

//Importamos la función para chequear si todas las secciones de checkboxes tienen al menos uno marcado:
import { changeStatusOtherOptions, checkFiltersState } from "/js/general.js";

//Ids de los botones de aplicar ordenamiento y filtros:
export const applyOrderButtonId = "applyOrderButton";
export const applyFiltersButtonId = "applyFiltersButton";
export const buttonIds = [applyOrderButtonId, applyFiltersButtonId];

//Name de la etiqueta que permite seleccionar el ordenamiento:
export const orderName = "order";

//Criterio de ordenamiento por defecto:
export const defaultOrder = "orderDescByDateTime";

//Ids de los inputs de fechas, horas y precios:
export const dateInputIds = ["date", "fDate", "uDate", "rFDate", "rUDate"];
export const timeInputIds = ["fTime", "uTime", "rFTime", "rUTime"];
export const priceInputIds = ["fPrice", "uPrice", "rFPrice", "rUPrice"];

//Configuración de filtros a chequear para compras:
let filtersSections = ["methodOfPay"];

//Si se trata de ventas, agregamos un filtro más a chequear:
if(document.getElementById("usernameContainer"))
{
	filtersSections.push("username");
}

//Definimos la configuración de los inputs de las fechas:
const datesConfig = {rangeFromDateId: "rFDate", rangeUntilDateId: "rUDate", buttonIds: buttonIds};

//Definimos la configuración de los inputs de las horas:
const timesConfig = {rangeFromTimeId: "rFTime", rangeUntilTimeId: "rUTime", buttonIds: buttonIds};

//Definimos la configuración para los inputs de las compras/ventas:
const pricesConfig =
{
    inputs: 
    [
        { id: "fPrice", min: 0 },
        { id: "uPrice", min: 0 },
        { range: ["rFPrice", "rUPrice"], min: 0 }
    ],
    buttonIds: buttonIds
};

/* DETECTAMOS CLICS EN EL CHECKBOX "all" DE MÉTODOS DE PAGO */
document.getElementById("methodOfPay-all").addEventListener("click", (event) => changeStatusOtherOptions(event, "methodOfPay"));

/* ESCUCHAMOS LA ENTRADA DE DATOS EN LOS INPUTS DE FECHAS DE LAS COMPRAS/VENTAS */
//Cuando se selecciona una fecha en el input de fecha desde en un rango o en el de fecha hasta de un rango, validamos:
document.getElementById(datesConfig.rangeFromDateId).addEventListener("change", () => 
{
	validateDates(datesConfig); 
	checkFiltersState(filtersSections, buttonIds);
});
document.getElementById(datesConfig.rangeUntilDateId).addEventListener("change", () =>
{
	validateDates(datesConfig);	
	checkFiltersState(filtersSections, buttonIds);
});

/* ESCUCHAMOS LA ENTRADA DE DATOS EN LOS INPUTS DE HORAS DE LAS COMPRAS/VENTAS */
//Cuando se selecciona una fecha en el input de hora desde en un rango o en el de hora hasta de un rango, validamos:
document.getElementById(timesConfig.rangeFromTimeId).addEventListener("change", () => 
{
	validateTimes(timesConfig);
	checkFiltersState(filtersSections, buttonIds);	
});
document.getElementById(timesConfig.rangeUntilTimeId).addEventListener("change", () =>
{
	validateTimes(timesConfig);
	checkFiltersState(filtersSections, buttonIds);
});

/* ESCUCHAMOS LA ENTRADA DE DATOS EN LOS INPUTS DE PRECIOS DE LAS COMPRAS/VENTAS */
//Cuando carga el DOM asignamos la configuración a los inputs para poder hacer las validaciones:
document.addEventListener("DOMContentLoaded", () => configPriceValidationsGroup(pricesConfig, filtersSections));
