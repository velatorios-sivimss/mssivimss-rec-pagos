package com.imss.sivimss.recpagos.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecPagosDto {

	private Integer idPagoBit;
	private Integer idRegistro;
	private Integer idFlujoPagos;
	private Integer idVelatorio;
	private String fOds;
	private String nomContratante;
	private String claveFolio;
	private String descValor;
	private String clavePago;
	
}
