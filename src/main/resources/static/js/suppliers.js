/* OBTENEMOS LOS PROVEEDORES ORDENADOS POR EL CRITERIO INDICADO */
async function orderSuppliers(order)
{
	return fetch(`/supplier/suppliers/order?order=${order}&page=0&size=10`,{
		method: 'GET'		
	})
	.then(response => 
    {
		//Si hubo algún error:
        if(!response.ok) 
        {
            throw new Error("Error in the response of server");
        }
        
        return response.json(); //Retornamos el JSON con los pedidos.
    })
    .then(data => data)
    .catch(error => 
    {
        console.error("Error in the Fetch request:", error);
        throw error;
    });
}

/* GENERAMOS EL HTML CON LOS PROVEEDORES INDICADOS */
function generateHTMLForSuppliers(suppliers)
{
	let html = '';
    suppliers.forEach(supplier => 
    {
        html += `<tr>
        			<td>${supplier.supplierId}</td>
                    <td>${supplier.name}</td>
                    <td>${supplier.address}</td>
                    <td>${supplier.email}</td>
                    <td>${supplier.phoneNumber}</td>
                 </tr>`;
    });
    return html;	
}

/* GENERAMOS EL HTML PARA CUANDO NO SE ENCONTRARON PROVEEDORES */
function generateHTMLForEmptySuppliers()
{
	//Obtenemos el body de la tabla:
	const tbody = document.getElementById("supplierTBodyTable");
	
	//Definimos una única fila con el mensaje de que no se encontraron resultados:
	tbody.innerHTML = 
	`<tr>
        <td colspan="5" style="text-align: center; font-style: italic; color: gray;">
            No results found.
        </td>
    </tr>`;	
}

/* ESCUCHAMOS EL EVENTO DE CARGA DEL DOM */
//Una vez que el DOM cargó por completo:
document.addEventListener('DOMContentLoaded', () =>
{
    //Ante un clic en el botón de aplicar determinado ordenamiento:
    document.getElementById('applyOrderButton').addEventListener('click', () =>
    {
		//Obtenemos el criterio de ordenamiento elegido:
        const order = document.querySelector("select[name='order']").value;

		//Obtenemos los proveedores ordenados:
       	orderSuppliers(order)
       	.then(data => 
       	{
			//Si al menos hay un proveedor:
			if(data.suppliers.length > 0)
			{
				//Seleccionamos la etiqueta donde van los proveedores:
				const tbody = document.getElementById("supplierTBodyTable");
				
				//Cargamos los proveedores en la vista:
				tbody.innerHTML = generateHTMLForSuppliers(data.suppliers);
			}
			else
			{
				//Generamos el mensaje indicando que no hay proveedores:
				generateHTMLForEmptySuppliers();
			}
		})
	    .catch(error => 
	    {
	        console.error("There was an error ordering the suppliers", error);
	    });
    });
});
