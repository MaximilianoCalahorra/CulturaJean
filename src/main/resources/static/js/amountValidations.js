//Importamos funciones para el manejo de los mensajes de error:
import { showError, hideError } from "/js/errorMessage.js";

//Importamos la función para chequear si todas las secciones de checkboxes tienen al menos uno marcado:
import { checkFiltersState } from "/js/general.js";

//Función para validar la cantidad ingresada en el input contra la mínima esperada:
const validateSingleInput = (input, min) => 
{
	//Obtenemos el valor ingresado:
    const value = input.value.trim();

    //Si el campo está vacío, es válido (no aplicar filtro por este criterio)
    if(value === "") 
    {
        hideError(input); //Ocultamos el mensaje de error.
        return true;
    }

    //Si no es un número:
    if(isNaN(value)) 
    {
        showError(input, "This field requires a valid number."); //Mostramos el mensaje de error.
        return false;
    }

	//Convertimos el valor en cadena de texto a número:
    const numericValue = parseFloat(value);

    //Si el valor es menor al mínimo permitido:
    if(numericValue < min) 
    {
        showError(input, `Value must be at least ${min}.`); //Mostramos el mensaje de error.
        return false;
    }

	//Si superó la validación, ocultamos el mensaje:
    hideError(input);
    return true;
};

//Función para validar los inputs pensados para rangos:
const validateRangeInputs = (fromInput, untilInput, min) => 
{
	//Obtenemos los valores desde y hasta del rango:
    const fromValue = fromInput.value.trim();
    const untilValue = untilInput.value.trim();

    //Si ambos están vacíos, el rango es válido:
    if(fromValue === "" && untilValue === "") 
    {
		//Ocultamos los mensajes de error para ambos inputs:
        hideError(fromInput);
        hideError(untilInput);
        return true;
    }

    //Si alguno de los inputs está incompleto, el otro debe estarlo también:
    if(fromValue === "" || untilValue === "") 
    {
		//El completo es el de hasta, así que mostramos el mensaje de error para el de desde:
        if(fromValue === "") showError(fromInput, "This field is required if 'Until' is filled.");
        
        //El completo es el de desde, así que mostramos el mensaje de error para el de hasta:
        if(untilValue === "") showError(untilInput, "This field is required if 'From' is filled.");
        return false;
    }

    //Validamos que ambos inputs se completen con números:
    if(isNaN(fromValue)) 
    {
        showError(fromInput, "This field requires a valid number."); //Mensaje de error.
        return false;
    }
    
    if(isNaN(untilValue)) 
    {
        showError(untilInput, "This field requires a valid number."); //Mensaje de error.
        return false;
    }

	//Obtenemos los valores en número de ambos inputs a partir de sus versiones en cadena de texto:
    const fromNumeric = parseFloat(fromValue);
    const untilNumeric = parseFloat(untilValue);

    //Validamos si los valores son menores al mínimo:
    if(fromNumeric < min)
    {
        showError(fromInput, `From value must be at least ${min}.`); //Mensaje de error.
        return false;
    }
    
    if(untilNumeric < min) 
    {
        showError(untilInput, `Until value must be at least ${min}.`); //Mensaje de error.
        return false;
    }

    //Si el valor desde es mayor al valor hasta:
    if(fromNumeric > untilNumeric) 
    {
        showError(untilInput, "Until value must be greater or equal to From value."); //Mensaje de error.
        return false;
    }

	//Si se superaron todas las validaciones, ocultamos los mensajes de error para ambos inputs:
    hideError(fromInput);
    hideError(untilInput);
    return true;
};

//Validamos un conjunto de inputs:
const validateGroup = (group, sectionsFilters) => 
{
	//Descomponemos el grupo:
    const { inputs, buttonIds, containerId } = group;
    
    //Seleccionamos el botón de ordenar y el de aplicar filtros:
    const orderButton = document.getElementById(buttonIds[0]);
    const applyButton = document.getElementById(buttonIds[1]);

	//Suponemos que el dato de cada input es válido:
    let allValid = true;

	//Vamos a analizar cada input:
    inputs.forEach((inputConfig) => 
    {
		//Si se trata de inputs unitarios:
        if(inputConfig.id) 
        {
			//Seleccionamos el input en cuestión:
            const input = document.getElementById(inputConfig.id);
            
            //Validamos si el valor ingresado es mayor o igual al mínimo esperado:
            const isValid = validateSingleInput(input, inputConfig.min);
            
            //Si no es válido:
            if(!isValid) allValid = false; //Entonces el grupo de inputs tampoco lo es.
        }
        //Por el contrario, si es un input armado para un rango de valores:
        else if(inputConfig.range) 
        {
			//Descomponemos la configuración sobre el rango para obtener el id del input desde y el del input hasta:
            const [fromId, untilId] = inputConfig.range;
            
            //Seleccionamos ambos inputs:
            const fromInput = document.getElementById(fromId);
            const untilInput = document.getElementById(untilId);

			//Validamos que ambos inputs cumplan las condiciones:
            const isValid = validateRangeInputs(fromInput, untilInput, inputConfig.min);
            
            //Si no las cumplen:
            if(!isValid) allValid = false; //Entonces el grupo de inputs es inválido.
        }
    });

	//Si alguna validación encontró una inconsistencia y hay mensaje en la vista:
	if(document.getElementById(containerId).querySelectorAll(".error-message").length > 0)
	{
        //Los botones deben permanecer deshabilitados sin importar el estado de los filtros:
		orderButton.disabled = true;
        applyButton.disabled = true;
	}
	else //Por el contrario, si los filtros son válidos:
	{
		//Los botones se habilitan o deshabilitan según si el grupo de inputs es válido o no, respectivamente:
		orderButton.disabled = !allValid;
        applyButton.disabled = !allValid;	
	}
	
	//Habilitamos o deshabilitamos los botones según el estado de los checkboxes:
	checkFiltersState(sectionsFilters, buttonIds, containerId);
};

//Si se ingresa algún caracter que no sea un número, se convierte a "":
const enforceNumericInput = (input) => 
{
    input.addEventListener("input", () => 
    {
		//Obtenemos el valor ingresado:
        let value = input.value;

        //Permitir solo números y un único punto decimal:
        value = value.replace(/[^0-9]/g, "");
        
        //Evitar ceros iniciales consecutivos:
        if(value.startsWith("00")) 
        {
            value = value.replace(/^0+/, "0");
        }

        //Evitar múltiples ceros antes del primer dígito significativo:
        if(value.startsWith("0") && value.length > 1) 
        {
            value = value.replace(/^0+/, ""); //Elimina ceros a la izquierda.
        }

        input.value = value; // Actualiza el valor en el input
    });
};

//Definimos el comportamiento de cada input para validar según la configuración que se indique:
export const configAmountValidations = (config, sections) =>
{
	let i = 0;
	//Recorremos los grupos de configuración:
    config.forEach((group) => 
    {
		//Obtenemos las secciones a verificar si tienen por lo menos una opción marcada:
		const sectionsFilters = sections[i];
		
		//Definimos el compartamiento de cada input de ese grupo:
		configAmountValidationsGroup(group, sectionsFilters);
		i++;
    });
};

//Definimos el comportamiento de cada input para validar según la configuración que se indique de un grupo:
export const configAmountValidationsGroup = (config, sectionsFilters) =>
{
	//Recorremos cada input del grupo:
    config.inputs.forEach((inputConfig) => 
    {
		//Si es un input unitario:
        if(inputConfig.id) 
        {
            const input = document.getElementById(inputConfig.id); //Seleccionamos el input.
            enforceNumericInput(input); //Nos aseguramos que el valor no tome uno que no sea un número o "".
            input.addEventListener("input", () => validateGroup(config, sectionsFilters)); //Definimos que al completarlo se dispare la función de validar el grupo.
        } 
        //Por el contario, si es uno armado para rangos:
        else if(inputConfig.range) 
        {
            const [fromId, untilId] = inputConfig.range; //Obtenemos el id de ambos inputs.
            const fromInput = document.getElementById(fromId); //Seleccionamos el input desde.
            const untilInput = document.getElementById(untilId); //Seleccionamos el input hasta.

			//Nos aseguramos que el valor de ambos no tome uno que no sea un número o "":
            enforceNumericInput(fromInput);
            enforceNumericInput(untilInput);

			//Definimos que al completar cada uno se dispare la función de validar el grupo:
            fromInput.addEventListener("input", () => validateGroup(config, sectionsFilters));
            untilInput.addEventListener("input", () => validateGroup(config, sectionsFilters));
        }
    });
};
