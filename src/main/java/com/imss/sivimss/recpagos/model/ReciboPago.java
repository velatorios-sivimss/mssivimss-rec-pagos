package com.imss.sivimss.recpagos.model;

import com.fasterxml.jackson.annotation.JsonIgnoreType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@JsonIgnoreType(value = true)
public class ReciboPago {
	
	private Integer idReciboPago;
	private Integer idPagoDetalle;
	private String numFolio;
	private Integer idDelegacion;
	private Integer idVelatorio;
	private String fecReciboPago;
	private String nomContratante;
	private String canReciboPago;
	private String canTramites;
	private String descTramites;
	private String canDerechos;
	private String descDerechos;
	private String canSuma;
	private String canTotal;
	private String agenteFuneMat;
	private String recibeMat;
	
}
