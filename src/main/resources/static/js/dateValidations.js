//Importamos funciones para el manejo de los mensajes de error:
import { showError, hideError } from "/js/errorMessage.js"; 

//Función para verificar la validez de los inputs de rango de fechas:
const validateRangeDateInputs = (rangeFromDateId, rangeUntilDateId) => 
{
	//Seleccionamos ambos inputs:
	const rangeFromDateInput = document.getElementById(rangeFromDateId);
	const rangeUntilDateInput = document.getElementById(rangeUntilDateId);
	
	//Obtenemos la fecha dada a cada uno:
    const fromDate = rangeFromDateInput.value;
    const untilDate = rangeUntilDateInput.value;

	//Suponemos que la asignación es inválida:
	let valid = false;
	
	//Si ambas fechas no se completaron:
    if(!fromDate && !untilDate) 
    {
		//Ocultamos los mensajes de error y la configuración es válida:
		hideError(rangeFromDateInput);
		hideError(rangeUntilDateInput);
        valid = true;
    }
    //Si una de las fechas está completa y la otra no:
    else if(!fromDate || !untilDate) 
    {
		//Determinamos que la configuración es inválida:
        valid = false;
        
        //Si es la fecha desde la incompleta:
        if(!fromDate) showError(rangeFromDateInput, "From date must be filled."); //Definimos el mensaje.
        
        //Por el contario, si es la fecha hasta:
        else showError(rangeUntilDateInput, "Until date must be filled."); //Definimos el mensaje.
    }
    //Si ambas fechas están completas pero la fecha desde es posterior a la fecha hasta:
    else if(new Date(fromDate) > new Date(untilDate))
    {
		//No es una configuración válida y definimos el mensaje de error:
		valid = false;
		showError(rangeUntilDateInput, "Until date must be higher than or equal to from date.");
	}
	//Si ambas fechas están completadas y la fecha desde no es posterior a la fecha hasta:
	else 
	{
		//La configuración es válida y ocultamos los mensajes de error:
		valid = true;
		hideError(rangeFromDateInput);
		hideError(rangeUntilDateInput);
	}

	return valid; //Retornamos si es válida o no.
};

//Función principal que recibe el id de los inputs y valida las fechas ingresadas:
export const validateDates = (config) =>
{
	//Determinamos si la configuración de fechas es válida:
	const isRangeValid = validateRangeDateInputs(config.rangeFromDateId, config.rangeUntilDateId);
	
	//Seleccionamos el botón de ordenar y el de aplicar filtros:
	const orderButton = document.getElementById(config.buttonIds[0]);
	const filterButton = document.getElementById(config.buttonIds[1]);
	
	//Si alguna validación encontró una inconsistencia y hay mensaje en la vista:
	if(document.querySelectorAll(".error-message").length > 0)
	{
		//Los botones deben permanecer deshabilitados sin importar el estado de los filtros:
		orderButton.disabled = true;
		filterButton.disabled = true;
	}
	else //Por el contrario, si los filtros son válidos:
	{
		//Habilitamos o deshabilitamos los botones según si la configuración de fechas es válida o no, respectivamente:
		orderButton.disabled = !isRangeValid;	
		filterButton.disabled = !isRangeValid;
	}
};
