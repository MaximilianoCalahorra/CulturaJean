/* VERIFICAMOS SI HAY AL MENOS UN CHECKBOX MARCADO EN UNA SECCIÓN */
export function isAnyCheckedInSection(sectionName) 
{
    return Array.from(document.querySelectorAll(`input[name="${sectionName}"]:checked`)).length > 0;
}

/* VERIFICAMOS SI TODAS LAS SECCIONES TIENEN AL MENOS UN CHECKBOX MARCADO */
export function checkFiltersState(sections, buttonId) 
{
	//Suponemos que todas las secciones están con al menos una opción marcada:
	let allSectionsChecked = true;
	
	//Recorremos cada sección para saber si están todas marcadas o no:
	sections.forEach(section => allSectionsChecked = allSectionsChecked && isAnyCheckedInSection(section));
	
	//Seleccionamos el botón de aplicar filtros:
	const applyButton = document.getElementById(buttonId);  
	
	//Si alguna validación encontró una inconsistencia y hay mensaje en la vista:
	if(document.querySelectorAll(".error-message").length > 0)
	{
		applyButton.disabled = true; //El botón debe permanecer deshabilitado sin importar el estado de los filtros.
	}
	else //Por el contrario, si los filtros son válidos:
	{
		//Habilitamos o deshabilitamos el botón según el estado de las secciones:
		applyButton.disabled = !allSectionsChecked;	
	}
}