package com.imss.sivimss.recpagos.beans;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.DatatypeConverter;

import com.imss.sivimss.recpagos.model.request.RecPagosRequest;
import com.imss.sivimss.recpagos.model.request.UsuarioRequest;
import com.imss.sivimss.recpagos.util.AppConstantes;
import com.imss.sivimss.recpagos.util.DatosRequest;
import com.imss.sivimss.recpagos.util.QueryHelper;

import com.imss.sivimss.recpagos.util.DatosRequest;

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
public class RecPagos {

	private Integer idPagoBit;
	private Integer idRegistro;
	private Integer idFlujoPagos;
	private Integer idVelatorio;
	private String fOds;
	private String nomContratante;
	private String claveFolio;
	private String descValor;
	private String claveEstatusPago;
	private Integer nivel;
	private Integer idRol;

	public RecPagos(RecPagosRequest recPagosRequest) {
		this.idVelatorio= recPagosRequest.getIdVelatorio();
	}
	public RecPagos(UsuarioRequest usuarioRequest) {
		this.nivel= usuarioRequest.getIdOficina();
		this.idRol = usuarioRequest.getIdRol();
	}
	
	public DatosRequest obtenerRecPagos(DatosRequest request) {
		String query = "SELECT PB.ID_PAGO_BITACORA as idPagoBit, \r\n "
						+ "PB.FEC_ODS as fOds, PB.CVE_FOLIO as claveFolio, \r\n"
						+ "PB.CVE_ESTATUS_PAGO as claveEstatusPago from svt_pago_bitacora as PB"
						+ "ORDER BY ID_PAGO_BITACORA ASC";
		String encoded = DatatypeConverter.printBase64Binary(query.getBytes());
		request.getDatos().put(AppConstantes.QUERY, encoded);

		return request;
	}
	
	public DatosRequest buscarFiltrosRecPagos(DatosRequest request, RecPagos recPagos) {
		StringBuilder query = new StringBuilder("SELECT PB.ID_PAGO_BITACORA as idPagoBit, PB.FEC_ODS as fOds, PB.CVE_FOLIO as claveFolio,"
				+ " PB.CVE_ESTATUS_PAGO as claveEstatusPago "
				+ " from svt_pago_bitacora as PB ");
		query.append(" WHERE IFNULL(ID_PAGO_BITACORA,0) > 0" );
		if (recPagos.getNivel() != null) {
			query.append(" AND R.CVE_FOLIO = ").append(this.getClaveFolio());
		}		
		query.append("ORDER BY R.ID_PAGO_BITACORA DESC");
		
		String encoded = DatatypeConverter.printBase64Binary(query.toString().getBytes());
		request.getDatos().put(AppConstantes.QUERY, encoded);
		return request;
	}
	
}
