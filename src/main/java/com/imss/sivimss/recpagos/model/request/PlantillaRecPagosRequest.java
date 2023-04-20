package com.imss.sivimss.recpagos.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PlantillaRecPagosRequest {

	@JsonProperty
	private String folio;
	@JsonProperty
	private String delegacion;
	@JsonProperty
	private String velatorio;
	@JsonProperty
	private String lugar;
	@JsonProperty
	private String fecha;
	@JsonProperty
	private String recibimos;
	@JsonProperty
	private String cantidad;
	@JsonProperty
	private String tramites;
	@JsonProperty
	private String descTramites;
	@JsonProperty
	private String derechos;
	@JsonProperty
	private String descDerechos;
	@JsonProperty
	private String total;
	@JsonProperty
	private String totalFinal;
	@JsonProperty
	private String tipoReporte;
	
}
