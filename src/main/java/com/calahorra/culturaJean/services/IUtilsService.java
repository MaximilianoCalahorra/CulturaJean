package com.calahorra.culturaJean.services;

import java.util.List;

///Interfaz IUtilsService:
public interface IUtilsService
{
	//Métodos auxiliares:
	
	//Limpiamos el filtro simple por si no hay que tenerlo en cuenta para el filtrado:
	public String cleanFilter(String filter);
	
	//Limpiamos el filtro de lista por si no hay que tenerlo en cuenta para el filtrado:
	public List<String> cleanFilter(List<String> filters);
	
	//Dividimos los valores de un arreglo de String y los pasamos a una lista:
	public List<String> convertPostgresArrayToList(String[] postgresArray);
	
	//Convertimos el valor de un filtro de String a Float o null según corresponda:
	public Float convertStringFilterToFloat(String filter);
	
	//Convertimos el valor de un filtro de String a Integer o null según corresponda:
	public Integer convertStringFilterToInteger(String filter);
	
	//Convertimos el valor de un filtro de String a Boolean o null según corresponda:
	public Boolean convertStringFilterToBoolean(String filter);
	
	//Convertimos el valor de un filtro de List<String> a List<Character> o null según corresponda:
	public List<Character> convertListStringFilterToListCharacter(List<String> filters);
}
