package com.hoffnis.model;


import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Pessoa implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@NotNull(message="Não pode ser Nulo")
	@NotEmpty(message= "Por favor preencha o campo")
	private String modelo;
	@NotNull(message="Não pode ser Nulo")
	@NotEmpty(message= "Por favor preencha o campo")
	private String cor;
	@NotNull(message="Não pode ser Nulo")
	@NotEmpty(message= "Por favor preencha o campo")
	private String placa;
	
	@OneToMany(mappedBy="pessoa", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Proprietario> proprietario;
	
	public void setProprietario(List<Proprietario> proprietario) {
		this.proprietario = proprietario;
	}
	
	public List<Proprietario> getProprietario(){
		return proprietario;
	}
	
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getCor() {
		return cor;
	}
	public void setCor(String cor) {
		this.cor = cor;
	}
	

}
