package com.calahorra.culturaJean.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

///Entidad UserRole:
@Entity
@Getter @Setter @NoArgsConstructor
@Table(name="user_role", uniqueConstraints=@UniqueConstraint(columnNames= {"role", "member_id"}))
public class UserRole 
{
	//Atributos:
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_id", nullable=false)
	private Member member;

	@Column(name="role", nullable=false, length=100)
	private String role;

	@CreationTimestamp
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;

	//Constructor:
	public UserRole(int id, Member member, String role)
	{
		this.id = id;
		this.member = member;
		this.role = role;
	}
}
