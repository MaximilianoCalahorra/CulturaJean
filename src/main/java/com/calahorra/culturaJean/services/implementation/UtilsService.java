package com.calahorra.culturaJean.services.implementation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.calahorra.culturaJean.services.IUtilsService;

import lombok.NoArgsConstructor;

///Clase UtilsService:
@Service("utilsService")
@NoArgsConstructor
public class UtilsService implements IUtilsService
{
	//Métodos auxiliares:
	
	//Limpiamos el filtro simple por si no hay que tenerlo en cuenta para el filtrado:
	@Override
	public String cleanFilter(String filter) 
	{
		//Si el filtro ya es nulo y está vacío ("") o tiene el valor de "all":
		if(filter != null && (filter.isEmpty() || filter.equals("all")))
		{
			filter = null; //Definimos que no hay que filtrar por él.
		}
		return filter; //Retornamos el filtro.
	}
	
	//Limpiamos el filtro de lista por si no hay que tenerlo en cuenta para el filtrado:
	@Override
	public List<String> cleanFilter(List<String> filters) 
	{
		//Si el filtro ya es nulo y está vacío ("") o tiene el valor de "all":
		if(filters != null && (filters.isEmpty() || filters.contains("all")))
		{
			filters = null; //Definimos que no hay que filtrar por él.
		}
		return filters; //Retornamos el filtro.
	}
	
	//Dividimos los valores de un arreglo de String y los pasamos a una lista:
	@Override
	public List<String> convertPostgresArrayToList(String[] postgresArray) 
	{ 
		if(postgresArray != null) return Arrays.asList(postgresArray); //Si hay algo para convertir.
		return new ArrayList<>(); //Si no hay nada para convertir.
	}

	//Convertimos el valor de un filtro de String a Float o null según corresponda:
	@Override
	public Float convertStringFilterToFloat(String filter) 
	{
		Float floatFilter = null; //Valor por defecto.
        String cleanFilter = cleanFilter(filter); //Adecuamos el filtro en su versión String.
        
        //Si la versión adecuada no es null, entonces parseamos su valor a Float para asignarlo al filtro Float:
        if(cleanFilter != null) floatFilter = Float.parseFloat(cleanFilter);
        
        return floatFilter; //Retornamos el filtro con el tipo de dato apropiado.
	}
	
	//Convertimos el valor de un filtro de String a Integer o null según corresponda:
	@Override
	public Integer convertStringFilterToInteger(String filter) 
	{
		Integer intFilter = null; //Valor por defecto.
		String cleanFilter = cleanFilter(filter); //Adecuamos el filtro en su versión String.
		
		//Si la versión adecuada no es null, entonces parseamos su valor a Integer para asignarlo al filtro Integer:
		if(cleanFilter != null) intFilter = Integer.parseInt(cleanFilter);
		
		return intFilter; //Retornamos el filtro con el tipo de dato apropiado.
	}
	
	//Convertimos el valor de un filtro de String a Boolean o null según corresponda:
	@Override
	public Boolean convertStringFilterToBoolean(String filter) 
	{
		Boolean booleanFilter = null; //Valor por defecto.
		String cleanFilter = cleanFilter(filter); //Adecuamos el filtro en su versión String.
		
		//Si la versión adecuada no es null, entonces parseamos su valor a Boolean para asignarlo al filtro Boolean:
		if(cleanFilter != null) booleanFilter = Boolean.parseBoolean(cleanFilter);
		
		return booleanFilter; //Retornamos el filtro con el tipo de dato apropiado.
	}
	
	//Convertimos el valor de un filtro de String a LocalDate o null según corresponda:
	@Override
	public LocalDate convertStringFilterToLocalDate(String filter) 
	{
		LocalDate localDateFilter = null; //Valor por defecto.
		String cleanFilter = cleanFilter(filter); //Adecuamos el filtro en su versión String.
		
		//Si la versión adecuada no es null, entonces parseamos su valor a LocalDate para asignarlo al filtro LocalDate:
		if(cleanFilter != null) localDateFilter = LocalDate.parse(cleanFilter);
		
		return localDateFilter; //Retornamos el filtro con el tipo de dato apropiado.
	}
		
	//Convertimos el valor de un filtro de String a LocalTime o null según corresponda:
	@Override
	public LocalTime convertStringFilterToLocalTime(String filter) 
	{
		LocalTime localTimeFilter = null; //Valor por defecto.
		String cleanFilter = cleanFilter(filter); //Adecuamos el filtro en su versión String.
		
		//Si la versión adecuada no es null, entonces parseamos su valor a LocalTime para asignarlo al filtro LocalTime:
		if(cleanFilter != null) localTimeFilter = LocalTime.parse(cleanFilter);
		
		return localTimeFilter; //Retornamos el filtro con el tipo de dato apropiado.
	}
	
	//Convertimos el valor de un filtro de List<String> a List<Character> o null según corresponda:
	@Override
	public List<Character> convertListStringFilterToListCharacter(List<String> filters)
	{
		List<Character> listCharacterFilter = null; //Valor por defecto.
        List<String> filterClean = cleanFilter(filters); //Adecuamos el filtro en su versión List<String>.
        
        //Si la versión adecuada no es null, entonces parseamos el valor de cada elemento a Character para asignarlo al filtro List<Character>:
        if(filterClean != null && !filterClean.isEmpty()) listCharacterFilter = filterClean.stream().map(i -> i.charAt(0)).toList();
        
        return listCharacterFilter; //Retornamos el filtro con el tipo de dato apropiado.
	}
	
	//Convertimos el valor de un filtro de List<String> a List<Integer> o null según corresponda:
	@Override
	public List<Integer> convertListStringFilterToListInteger(List<String> filters)
	{
		List<Integer> listIntegerFilter = null; //Valor por defecto.
        List<String> filterClean = cleanFilter(filters); //Adecuamos el filtro en su versión List<String>.
        
        //Si la versión adecuada no es null, entonces parseamos el valor de cada elemento a Integer para asignarlo al filtro List<Integer>:
        if(filterClean != null && !filterClean.isEmpty()) listIntegerFilter = filterClean.stream().map(i -> Integer.parseInt(i)).toList();
        
        return listIntegerFilter; //Retornamos el filtro con el tipo de dato apropiado.
	}
}
