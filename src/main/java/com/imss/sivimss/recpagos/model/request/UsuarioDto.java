package com.imss.sivimss.recpagos.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsuarioDto {

	private String idVelatorio;

	private Integer idRol;

	private Integer idDelegacion;
	
	private String idOficina;

	private Integer idUsuario;
	
	private String desRol;

	private String nombre;

	private String curp;

	private String cveMatricula;
	
	private String cveUsuario;
	
}