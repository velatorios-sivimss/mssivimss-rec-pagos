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
public class RecPagosResponse {

	@JsonProperty(value = "idPagoBit")
	private Integer ID_PAGO_BITACORA;
	
	@JsonProperty(value = "idRegistro")
	private Integer ID_REGISTRO;
	
	@JsonProperty(value = "idFlujoPagos")
	private Integer ID_FLUJO_PAGOS;
	
	@JsonProperty(value = "idVelatorio")
	private Integer ID_VELATORIO;
	
	@JsonProperty(value = "fOds")
	private String FEC_ODS;
	
	@JsonProperty(value = "nomContratante")
	private String NOM_CONTRATANTE;
	
	@JsonProperty(value = "claveFolio")
	private String CVE_FOLIO;
	
	@JsonProperty(value = "descValor")
	private String DESC_VALOR;
	
	@JsonProperty(value = "clavePago")
	private String CVE_ESTATUS_PAGO;

	
}
