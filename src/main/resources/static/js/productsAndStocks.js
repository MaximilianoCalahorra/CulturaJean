//Importamos la función para validar los inputs de precios:
import { configPriceValidationsGroup } from "/js/priceValidations.js";

//Importamos la función para chequear si todas las secciones de checkboxes tienen al menos uno marcado:
import { changeStatusOtherOptions } from "/js/general.js";

//Ids de los botones de aplicar ordenamiento y filtros:
export const applyOrderButtonId = "applyOrderButton";
export const applyFiltersButtonId = "applyFiltersButton";
export const buttonIds = [applyOrderButtonId, applyFiltersButtonId];

//Name de la etiqueta que permite seleccionar el ordenamiento:
export const orderName = "order";

//Criterio de ordenamiento por defecto:
let defaultOrderAux = "p.name ASC";
if(document.getElementById("statesContainer"))
{
	defaultOrderAux = "s.actual_amount ASC";
}
export const defaultOrder = defaultOrderAux;

//Ids de los inputs precios:
export const priceInputIds = ["sPri", "fSPri", "uSPri", "rFSPri", "rUSPri"];

//Names de las secciones de filtro:
let filterSections = ["cat", "gen", "size", "col"];
if(document.getElementById("statesContainer"))
{
	filterSections.push("ena");
}

//Ids de las secciones:
const containerIdP = "productsSection";
const containerIdS = "stocksSection";

let containerId = containerIdP;
if(document.getElementById("statesContainer"))
{
	containerId = containerIdS;
}

//Definimos la configuración para los inputs de precios:
const pricesConfig =
{
    inputs: 
    [
        { id: "sPri", min: 0 },
        { id: "fSPri", min: 0 },
        { id: "uSPri", min: 0 },
        { range: ["rFSPri", "rUSPri"], min: 0 }
    ],
    buttonIds: buttonIds,
    containerId: containerId
};

/* ACTUALIZAMOS LOS CHECKBOXES DE LA SECCIÓN COLOR */
export function updateColorCheckboxes(containerId, name, values, selectedValues) 
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
		              <label for="${name}-${index + 1}" style="background-color: ${value}; color: ${value};">Color</label>
		          </div>`;
		}
		
		//Si no está seleccionada la opción de "all" y dentro de las seleccionadas está la opción que estamos estamos evaluando:
		else if(selectedValues.includes(String(value)))
		{
			//Entonces la insertamos al HTML marcada:
			option += ` checked>
					  <label for="${name}-${index + 1}" style="background-color: ${value}; color: ${value};">Color</label>
		          </div>`;	
		}
		else //Para el resto de los casos:
		{
			//Entonces generamos la opción por defecto, sin marcar y habilitada:
			option += `>
					  <label for="${name}-${index + 1}" style="background-color: ${value}; color: ${value};">Color</label>
		          </div>`;
		}
		
		container.insertAdjacentHTML("beforeend", option); //Agregamos la opción al final.
    });
}

/* DETECTAMOS CLICS EN EL CHECKBOX "all" DE CATEGORÍAS */
document.getElementById("cat-all").addEventListener("click", (event) => changeStatusOtherOptions(event, "cat"));

/* DETECTAMOS CLICS EN EL CHECKBOX "all" DE GÉNEROS */
document.getElementById("gen-all").addEventListener("click", (event) => changeStatusOtherOptions(event, "gen"));

/* DETECTAMOS CLICS EN EL CHECKBOX "all" DE TALLES */
document.getElementById("size-all").addEventListener("click", (event) => changeStatusOtherOptions(event, "size"));

/* DETECTAMOS CLICS EN EL CHECKBOX "all" DE COLORES */
document.getElementById("col-all").addEventListener("click", (event) => changeStatusOtherOptions(event, "col"));

/* ESCUCHAMOS LA ENTRADA DE DATOS EN LOS INPUTS DE PRECIOS */
//Cuando carga el DOM asignamos la configuración a los inputs para poder hacer las validaciones:
document.addEventListener("DOMContentLoaded", () => configPriceValidationsGroup(pricesConfig, filterSections));
