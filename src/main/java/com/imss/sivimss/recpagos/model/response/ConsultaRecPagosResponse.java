package com.imss.sivimss.recpagos.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
@JsonIgnoreType(value = true)
public class ConsultaRecPagosResponse {


@JsonProperty(value = "idPagoBitacora")
private Integer idPagoBitacora;

@JsonProperty(value = "folio")
private String folio;

@JsonProperty(value = "delegacion")
private String delegacion;

@JsonProperty(value = "velatorio")
private String velatorio;

@JsonProperty(value = "lugar")
private String lugar;

@JsonProperty(value = "fecha")
private String fecha;

@JsonProperty(value = "recibimos")
private String recibimos;

@JsonProperty(value = "cantidad")
private String cantidad;

@JsonProperty(value = "tramites")
private String tramites;

@JsonProperty(value = "descTramites")
private String descTramites;

@JsonProperty(value = "derechos")
private String derechos;

@JsonProperty(value = "descDerechos")
private String descDerechos;

@JsonProperty(value = "total")
private String total;

@JsonProperty(value = "totalFinal")
private String totalFinal;

@JsonProperty(value = "tipoReporte")
private String tipoReporte;

@JsonProperty(value = "rutaNombreReporte")
private String rutaNombreReporte;

}
