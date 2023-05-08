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

	protected Integer idPagoBitacora;
	protected String folio;
	protected String delegacion;
	protected String velatorio;
	protected String lugar;
	protected String fecha;
	protected String recibimos;
	protected String cantidad;
	protected String tramites;
	protected String descTramites;
	protected String derechos;
	protected String descDerechos;
	protected String total;
	protected String totalFinal;
	protected String tipoReporte;
	protected String rutaNombreReporte;
	
}
