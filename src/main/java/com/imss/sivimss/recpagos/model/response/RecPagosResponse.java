package com.imss.sivimss.recpagos.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.google.gson.annotations.SerializedName;

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

	@SerializedName("ID_PAGO_BITACORA")
	private Integer idPagoBitacora;
	
	@SerializedName("ID_REGISTRO")
	private Integer idRegistro;
	
	@SerializedName("ID_FLUJO_PAGOS")
	private Integer idFlujoPagos;
	
	@SerializedName("ID_VELATORIO")
	private Integer idVelatorio;
	
	@SerializedName("FEC_ODS")
	private String fOds;
	
	@SerializedName("NOM_CONTRATANTE")
	private String nomContratante;
	
	@SerializedName("CVE_FOLIO")
	private String claveFolio;
	
	@SerializedName("DESC_VALOR")
	private String descValor;
	
	@SerializedName("CVE_ESTATUS_PAGO")
	private String clavePago;

	
}
