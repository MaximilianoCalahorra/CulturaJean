//Función para mostrar el error por pantalla:
export const showError = (input, message) => 
{
	//Obtenemos si ese input ya tiene un mensaje de error asociado:
    let errorMessage = input.parentElement.querySelector(".error-message");
    
    //En caso de que no:
    if (!errorMessage) 
    {
		//Lo generamos:
        errorMessage = document.createElement("p");
        errorMessage.classList.add("error-message");
        errorMessage.style.color = "red";
        input.parentElement.appendChild(errorMessage);
    }
    
    //Reemplazamos el texto del mensaje por el del nuevo error:
    errorMessage.textContent = message;
};
  
//Función para remover un mensaje de error de la vista:
export const hideError = (input) => 
{
	//Obtenemos el elemento:
    const errorMessage = input.parentElement.querySelector(".error-message");
    
    //Si existe:
    if(errorMessage)
    {
		//Lo removemos:
        errorMessage.remove();
    }
};  
