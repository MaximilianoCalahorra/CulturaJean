//Actualizamos la información de la vista en base a los cambios realizados que llegan por parámetro:
function updateView(updatedData) 
{
    //Actualizamos la cantidad del producto en el ítem:
    document.querySelector(`.amount-item[data-product-id="${updatedData.productId}"]`).innerText = updatedData.amount;
    
    //Actualizamos el subtotal de la compra/total del ítem:
    document.querySelector(`.subtotal-item[data-product-id="${updatedData.productId}"]`).innerText = updatedData.subtotal;
    
    //Actualizamos el total de la compra:
    document.querySelector('.purchase-total').innerText = `Total = USD ${updatedData.purchaseTotal}`;
    
    //Obtenemos los botones de agregar/decrementar unidades del ítem:
    const addAmountButton = document.querySelector(`.add-amount-button[data-product-id="${updatedData.productId}"]`);    
    const substractAmountButton = document.querySelector(`.substract-amount-button[data-product-id="${updatedData.productId}"]`); 
    
    //Si la nueva cantidad alcanzó el total de stock:
    if(updatedData.maximumStock)
    {
		addAmountButton.setAttribute("disabled", true);	//Deshabilitamos el botón de incrementar la cantidad.
	}
	else
	{
		addAmountButton.removeAttribute("disabled"); //Habilitamos el botón de incrementar la cantidad.
	}
	
	//Si la nueva cantidad alcanzó el mínimo de la compra (1):
	if(updatedData.minimumStock)
	{
		substractAmountButton.setAttribute("disabled", true); //Deshabilitamos el botón de decrementar la cantidad.
	}
	else
	{
		substractAmountButton.removeAttribute("disabled"); //Habilitamos el botón de decrementar la cantidad.
	}
}

//Actualizamos la cantidad del ítem y lo reflejamos en la vista:
async function updateAmount(productId, change)
{
	try
	{
		//Intentamos llevar adelante el aumento/decremento de la cantidad del ítem y obtenemos el resultado:
        const response = await fetch(`/purchase/updatePurchaseItem`, {
            method: 'POST',
            headers: 
            {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(
			{
				//Pasamos el id del producto y la cantidad a incrementar/decrementar al método:
                productId: productId,
                change: change
            }),
        });
        
        //Si no hubo errores:
        if(response.ok) 
        {
            const updatedData = await response.json();
            updateView(updatedData);  //Llamamos a una función que actualiza la vista y le pasamos la información que devolvió el método.
        }
        else
        {
            console.error('Error updating the item');
        }
    } 
    catch(error)
    {
        console.error('Network error:', error);
    }
}

//Seleccionamos el contenedor que agrupa todos los ítems del carrito:
const cartItemsContainer = document.querySelector("tbody");

//Escuchamos el evento 'click' en el contenedor:
cartItemsContainer.addEventListener("click", function (event)
{
    const target = event.target;

    //Verificamos si el clic fue en un botón de restar:
    if(target.classList.contains("substract-amount-button")) 
    {
        const productId = target.getAttribute("data-product-id"); //Obtenemos el id del producto del ítem.
        updateAmount(productId, -1); //Disminuimos en 1 la cantidad.
    }
    
    //Verificamos si el clic fue en un botón de sumar:
    else if(target.classList.contains("add-amount-button")) 
    {
        const productId = target.getAttribute("data-product-id"); //Obtenemos el id del producto del ítem.
        updateAmount(productId, 1); //Aumentamos en 1 la cantidad.
    }
});