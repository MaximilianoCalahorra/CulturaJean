//Definimos una función para que los elementos se comporten de determinada forma según la cantidad de stock del producto elegido:
function configurateElementsSwitchStock()
{
	const sizeSelect = document.getElementById("size-select"); //Obtenemos el selector de producto.
    const selectedOption = sizeSelect.options[sizeSelect.selectedIndex]; //Obtenemos el producto seleccionado.
    const selectedStockAmount = selectedOption.getAttribute("data-amount"); //Obtenemos el stock actual del producto seleccionado.
    const stockMessage = document.getElementById("stock-message"); //Obtenemos el elemento que lleva el mensaje sobre el stock.
    const amountInput = document.getElementById("amount-input"); //Obtenemos el input que permite elegir la cantidad del producto.
    const addShoppingCarButton = document.getElementById("add-shopping-car-button"); //Obtenemos el botón de agregar al carrito.
    
    //Si no hay stock del producto elegido:
    if(selectedStockAmount == 0)
    {
		amountInput.setAttribute("disabled", true); //Deshabilitamos el input para elegir la cantidad.
		addShoppingCarButton.setAttribute("disabled", true); //Deshabilitamos el botón para añadir el ítem al carrito.
		stockMessage.textContent = "Product without stock. Try later!"; //Definimos el mensaje relacionado al stock.
	}
	else //Por el contrario, si hay aunque sea hay una unidad del mismo:
	{
		amountInput.removeAttribute("disabled"); //Habilitamos el input para elegir la cantidad.
		addShoppingCarButton.removeAttribute("disabled"); //Habilitamos el botón para añadir el ítem al carrito.
		amountInput.max = selectedStockAmount; //Actualizamos el valor máximo del input de cantidad con la cantidad actual de stock.
		stockMessage.textContent = "Available stock! There are " + selectedStockAmount + " unit/s of this product"; //Definimos el mensaje relacionado al stock.
	}
}

//Escuchamos el evento de carga de la página y configuramos los elementos:
document.addEventListener("DOMContentLoaded", function(){configurateElementsSwitchStock()});

//Escuchamos el evento de cambios en el producto elegido y configuramos los elementos:
document.getElementById("size-select").addEventListener("change", function(){configurateElementsSwitchStock()});