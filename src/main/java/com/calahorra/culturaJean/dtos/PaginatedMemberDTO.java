package com.calahorra.culturaJean.dtos;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

///Clase PaginatedMemberDTO:
@Getter @Setter @NoArgsConstructor
public class PaginatedMemberDTO 
{
	//Atributos:
    private List<MemberDTO> members;
    private int totalPages;
    private long totalElements;
}