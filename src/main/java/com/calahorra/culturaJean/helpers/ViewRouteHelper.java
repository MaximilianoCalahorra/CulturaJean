package com.calahorra.culturaJean.helpers;

///Clase ViewRouteHelper:
public class ViewRouteHelper
{
	/**** Views ****/
	
	//HOME
	
	//Cargamos la vista de inicio del administrador:
	public final static String ADMIN_INDEX = "home/adminIndex";
	
	//Cargamos la vista de inicio del cliente:
	public final static String CUSTOMER_INDEX = "home/customerIndex";
	
	//Cargamos la vista de inicio del visitante:
	public final static String VISITOR_INDEX = "home/visitorIndex";
	
	//MEMBER
	
	//Cargamos el login:
	public final static String LOGIN = "member/login";
	
	//Cargamos el formulario que permite a un visitante registrarse como cliente:
	public final static String REGISTER_FORM = "member/register/form";
	
	//Cargamos la vista que informa los datos del nuevo cliente registrado:
	public final static String POST_REGISTER = "member/register/post";
	
	//Cargamos la vista que presenta la información sobre la cuenta al cliente:
	public final static String MY_ACCOUNT_CUSTOMER = "member/myAccount/customer";
	
	//Cargamos la vista que presenta la información sobre la cuenta al cliente:
	public final static String MY_ACCOUNT_ADMIN = "member/myAccount/admin";
	
	//Cargamos el formulario que permite modificar información de la cuenta:
	public final static String MODIFY_PROFILE_FORM = "member/myAccount/modifyProfile/form";
	
	//Cargamos la vista que presenta la nueva información sobre la cuenta al miembro:
	public final static String POST_MODIFY_PROFILE = "member/myAccount/modifyProfile/post";
	
	//HELP
	
	//Cargamos la vista de ayuda para el visitante:
	public final static String HELP_VISITOR = "help/visitor";
	
	//Cargamos la vista de ayuda para el cliente:
	public final static String HELP_CUSTOMER = "help/customer";
	
	//ABOUT US
	
	//Cargamos la vista de información sobre la empresa para el visitante:
	public final static String ABOUT_US_VISITOR = "aboutUs/visitor";
	
	//Cargamos la vista de información sobre la empresa para el cliente:
	public final static String ABOUT_US_CUSTOMER = "aboutUs/customer";
	
	//STORES
	
	//Cargamos la vista de información sobre las tiendas para el visitante:
	public final static String STORES_VISITOR = "stores/visitor";
	
	//Cargamos la vista de información sobre las tiendas para el cliente:
	public final static String STORES_CUSTOMER = "stores/customer";

	//SHOP
	
	//Cargamos la vista de los productos para el visitante:
	public final static String SHOP_VISITOR = "shop/visitor";
	
	//Cargamos la vista de los productos para el cliente:
	public final static String SHOP_CUSTOMER = "shop/customer";
	
	//Cargamos la vista que permite al administrador agregar un producto:
	public final static String ADD_PRODUCT_FORM = "shop/add/form";
	
	//Cargamos la vista que muestra al administrador el producto después de haber sido agregado:
	public final static String POST_ADD_PRODUCT = "shop/add/post";
	
	//Cargamos la vista que permite al administrador editar un producto:
	public final static String EDIT_PRODUCT_FORM = "shop/edit/form";
	
	//Cargamos la vista que muestra al administrador el producto después de haber sido editado:
	public final static String POST_EDIT_PRODUCT = "shop/edit/post";
	
	//Cargamos la vista que muestra más detalles acerca del producto seleccionado al visitante:
	public final static String MORE_DETAILS_PRODUCT_VISITOR = "shop/moreDetails/visitor";
	
	//Cargamos la vista que muestra más detalles acerca del producto seleccionado al cliente:
	public final static String MORE_DETAILS_PRODUCT_CUSTOMER = "shop/moreDetails/customer";
	
	//CUSTOMER
	
	//Cargamos la vista de los clientes para el administrador:
	public final static String CUSTOMERS = "customers/customers";
	
	//SALE
	
	//Cargamos la vista de las ventas para el administrador:
	public final static String SALES = "sales/sales";
	
	//SUPPLIER
	
	//Cargamos la vista de los proveedores para el administrador:
	public final static String SUPPLIERS = "suppliers/suppliers";
	
	//LOT
	
	//Cargamos la vista de los lotes para el administrador:
	public final static String LOTS = "lots/lots";
	
	//SUPPLY ORDER
	
	//Cargamos la vista de los pedidos de aprovisionamiento para el administrador:
	public final static String SUPPLY_ORDERS = "supplyOrders/supplyOrders";
	
	//Cargamos la vista que permite generar un nuevo pedido de aprovisionamiento para el administrador:
	public final static String SUPPLY_ORDER_FORM = "supplyOrders/add/form";
	
	//Cargamos la vista que permite visualizar el nuevo pedido de aprovisionamiento al administrador:
	public final static String POST_ADD_SUPPLY_ORDER = "supplyOrders/add/post";
	
	/**** Redirects ****/
	
	//Responde HomeController --> index():
	public final static String REDIRECT_INDEX = "/index";
	
	//Responde SupplyOrderController --> supplyOrders():
	public final static String REDIRECT_SUPPLY_ORDERS = "/supplyOrder/supplyOrders";
	
	//Responde LotController --> lots():
	public final static String REDIRECT_LOTS = "/lot/lots";
}
