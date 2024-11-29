//Importamos funciones para el manejo de los mensajes de error:
import { showError, hideError } from "/js/errorMessage.js"; 

// Función para convertir el valor del input `type="time"` a minutos desde la medianoche:
const parseTimeToMinutes = (time) => 
{
    const [hours, minutes] = time.split(":").map(Number);
    return hours * 60 + minutes;
};

//Función para verificar la validez de los inputs de rango de horas:
const validateRangeTimeInputs = (rangeFromTimeId, rangeUntilTimeId) => 
{
	//Seleccionamos ambos inputs:
	const rangeFromTimeInput = document.getElementById(rangeFromTimeId);
	const rangeUntilTimeInput = document.getElementById(rangeUntilTimeId);
	
	//Obtenemos la hora dada a cada uno:
    const fromTime = rangeFromTimeInput.value;
    const untilTime = rangeUntilTimeInput.value;

	//Suponemos que la asignación es inválida:
	let valid = false;
	
	//Si ambas horas no se completaron:
    if(!fromTime && !untilTime) 
    {
		//Ocultamos los mensajes de error y la configuración es válida:
		hideError(rangeFromTimeInput);
		hideError(rangeUntilTimeInput);
        valid = true;
    }
    //Si una de las horas está completa y la otra no:
    else if(!fromTime || !untilTime) 
    {
		//Determinamos que la configuración es inválida:
        valid = false;
        
        //Si es la hora desde la incompleta:
        if(!fromTime) showError(rangeFromTimeInput, "From time must be filled."); //Definimos el mensaje.
        
        //Por el contario, si es la fecha hasta:
        else showError(rangeUntilTimeInput, "Until time must be filled."); //Definimos el mensaje.
    }
    //Si ambas horas están completas pero la hora desde es posterior a la hora hasta:
    else if(parseTimeToMinutes(fromTime) > parseTimeToMinutes(untilTime))
    {
		//No es una configuración válida y definimos el mensaje de error:
		valid = false;
		showError(rangeUntilTimeInput, "Until time must be higher than or equal to from time.");
	}
	//Si ambas horas están completadas y la hora desde no es posterior a la hora hasta:
	else 
	{
		//La configuración es válida y ocultamos los mensajes de error:
		valid = true;
		hideError(rangeFromTimeInput);
		hideError(rangeUntilTimeInput);
	}

	return valid; //Retornamos si es válida o no.
};

//Función principal que recibe el id de los inputs y valida las horas ingresadas:
export const validateTimes = (config) =>
{
	//Determinamos si la configuración de horas es válida:
	const isRangeValid = validateRangeTimeInputs(config.rangeFromTimeId, config.rangeUntilTimeId);
	
	//Seleccionamos el botón de aplicar filtros:
	const button = document.getElementById(config.applyFiltersButtonId);
	
	//Si alguna validación encontró una inconsistencia y hay mensaje en la vista:
	if(document.querySelectorAll(".error-message").length > 0)
	{
		button.disabled = true; //El botón debe permanecer deshabilitado sin importar el estado de los filtros.
	}
	else //Por el contrario, si los filtros son válidos:
	{
		//Habilitamos o deshabilitamos el botón según si la configuración de horas es válida o no, respectivamente:
		button.disabled = !isRangeValid;	
	}
};
