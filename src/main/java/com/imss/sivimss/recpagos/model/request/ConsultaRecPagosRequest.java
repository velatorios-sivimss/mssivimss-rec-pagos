package com.imss.sivimss.recpagos.model.request;

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
public class ConsultaRecPagosRequest {

	private Integer idPagoBitacora;
	private String folio;
	private String delegacion;
	private String velatorio;
	private String lugar;
	private String fecha;
	private String recibimos;
	private String cantidad;
	private String tramites;
	private String descTramites;
	private String derechos;
	private String descDerechos;
	private String total;
	private String totalFinal;
	private String tipoReporte;
	private String rutaNombreReporte;
	
}
