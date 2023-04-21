package com.imss.sivimss.recpagos.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReporteDto {

	private Integer idVelatorio;
	private Integer idDelegacion;
	private String fechaInicio;
	private String fechaFin;
	private String claveFolio;
	private String nomContratante;
	private String tipoReporte;
	
}