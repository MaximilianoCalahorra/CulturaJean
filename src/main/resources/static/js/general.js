//Importamos la siguiente función:
import { hideError } from "/js/errorMessage.js";

/* OBTENEMOS EL CRITERIO DE ORDENAMIENTO SELECCIONADO */
export function getOrderValue(orderName, defaultValue)
{
	return document.querySelector(`select[name="${orderName}"]`).value || defaultValue;
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

/* VERIFICAMOS SI HAY AL MENOS UN CHECKBOX MARCADO EN UNA SECCIÓN */
export function isAnyCheckedInSection(sectionName) 
{
    return Array.from(document.querySelectorAll(`input[name="${sectionName}"]:checked`)).length > 0;
}

/* VERIFICAMOS SI TODAS LAS SECCIONES TIENEN AL MENOS UN CHECKBOX MARCADO */
export function checkFiltersState(sections, buttonIds) 
{
	//Suponemos que todas las secciones están con al menos una opción marcada:
	let allSectionsChecked = true;
	
	//Recorremos cada sección para saber si están todas marcadas o no:
	sections.forEach(section => allSectionsChecked = allSectionsChecked && isAnyCheckedInSection(section));
	
	//Seleccionamos el botón de aplicar filtros y el de ordenar:
	const orderButton = document.getElementById(buttonIds[0]);
	const filterButton = document.getElementById(buttonIds[1]);  
	
	//Si alguna validación encontró una inconsistencia y hay mensaje en la vista:
	if(document.querySelectorAll(".error-message").length > 0)
	{
		//Los botones deben permanecer deshabilitados sin importar el estado de los filtros:
		filterButton.disabled = true;
		orderButton.disabled = true;
	}
	else //Por el contrario, si los filtros son válidos:
	{
		//Habilitamos o deshabilitamos los botones según el estado de las secciones:
		filterButton.disabled = !allSectionsChecked;	
		orderButton.disabled = !allSectionsChecked;	
	}
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

/* ACTUALIZAMOS LOS CHECKBOXES DE UNA SECCIÓN DE FILTRO */
export function updateCheckboxes(containerId, name, values, selectedValues) 
{	
	const container = document.getElementById(containerId); //Obtenemos el elemento padre.
	container.removeAttribute("open"); //Cerramos las opciones del details para no ocupar demasiado espacio de la pantalla.

    //Limpiamos las opciones actuales (excepto el checkbox "All"):
    const options = container.querySelectorAll('input[type="checkbox"]:not([value="all"])');
    options.forEach(option => option.parentElement.remove());

	//Si hay alguna sección de filtros para añadir:
	if(values !== null)
	{
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

/* REESTABLECEMOS LOS VALORES DE UN GRUPO DE INPUTS */
export function reinicializeInputs(inputIds, buttonIds)
{	
	//Recorremos cada input:
	inputIds.forEach(inputId =>
	{
		const input = document.getElementById(inputId); //Obtenemos el input.
		input.value = ""; //Reinicializamos su valor.
		hideError(input); //Ocultamos el mensaje de error (si había).
	});
	
	//Habilitamos el botón de aplicar filtros y el de ordenar:
	buttonIds.forEach(buttonId => document.getElementById(buttonId).disabled = false);
}

/* ACTUALIZAMOS LOS BOTONES DE PÁGINAS */
export function updatePagination(totalPages, currentPage) 
{
    const pagination = document.getElementById('pagination'); //Seleccionamos el contenedor de los botones.
    pagination.innerHTML = ''; //Limpiamos los botones de paginación.

	//Por cada página:
    for(let i = 0; i < totalPages; i++) 
    {
        const button = document.createElement('button'); //Creamos un botón.
        button.textContent = i + 1; //Le damos el número.
        button.classList.toggle('active', i === currentPage);
        button.setAttribute("data-page", i);

        pagination.appendChild(button); //Agregamos el botón al contenedor.
    }
    
    //Si por lo menos tenemos una página:
    if(totalPages > 0)
    {
		//Cambiamos el color de fondo del botón de la página que se está mostrando:
    	document.querySelector(".active").setAttribute("style", "background-color: orange;");
	}
	else
	{
		pagination.innerHTML = ''; //Removemos los botones porque no hay resultados.
	}
}
