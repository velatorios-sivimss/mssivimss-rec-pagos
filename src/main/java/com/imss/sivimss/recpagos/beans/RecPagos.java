package com.imss.sivimss.recpagos.beans;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.DatatypeConverter;

import com.imss.sivimss.recpagos.model.request.PlantillaRecPagosRequest;
import com.imss.sivimss.recpagos.model.request.RecPagosRequest;
import com.imss.sivimss.recpagos.model.request.ReporteDto;
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
		this.claveFolio = recPagosRequest.getClaveFolio();
		this.idVelatorio = recPagosRequest.getIdVelatorio();
	}
	
	public RecPagos(UsuarioRequest usuarioRequest) {
		this.nivel= usuarioRequest.getIdOficina();
		this.idRol = usuarioRequest.getIdRol();
	}
	
	public DatosRequest obtenerRecPagos(DatosRequest request) {
		String query = "SELECT PB.ID_PAGO_BITACORA as idPagoBit, \r\n "
						+ "PB.FEC_ODS as fOds, PB.CVE_FOLIO as claveFolio, \r\n"
						+ "PB.NOM_CONTRATANTE as nomContratante, \r\n"
						+ "PB.CVE_ESTATUS_PAGO as claveEstatusPago FROM svt_pago_bitacora as PB "
						+ "ORDER BY ID_PAGO_BITACORA ASC ";
		String encoded = DatatypeConverter.printBase64Binary(query.getBytes());
		request.getDatos().put(AppConstantes.QUERY, encoded);

		return request;
	}
	
	public DatosRequest buscarFiltrosRecPagos(DatosRequest request,RecPagos recPagos) {
		
		StringBuilder query = new StringBuilder("SELECT PB.ID_PAGO_BITACORA as idPagoBit, PB.FEC_ODS as fOds, PB.CVE_FOLIO as claveFolio, "
				+ " PB.NOM_CONTRATANTE as nomContratante, "
				+ " PB.CVE_ESTATUS_PAGO as claveEstatusPago "
				+ " FROM svt_pago_bitacora as PB ");
			query.append(" WHERE IFNULL(ID_PAGO_BITACORA,0) > 0" );
		if (recPagos.getClaveFolio() != null) {
			query.append(" AND PB.CVE_FOLIO = ").append(recPagos.getClaveFolio());
		}
		if (recPagos.getNomContratante() != null) {
			query.append(" AND PB.NOM_CONTRATANTE = ").append(recPagos.getNomContratante());
		}
		query.append(" ORDER BY PB.ID_PAGO_BITACORA DESC ");
		
		String encoded = DatatypeConverter.printBase64Binary(query.toString().getBytes());
		request.getDatos().put(AppConstantes.QUERY, encoded);
		return request;
	}
	
	public Map<String, Object> generarReportePDF(ReporteDto reporteDto, String nombrePdfReportes) {
		Map<String, Object> envioDatos = new HashMap<>();
		String condicion = " ";
		if (this.claveFolio != null && this.idVelatorio != null) {
			condicion = condicion + " AND PB.CVE_FOLIO = " + this.claveFolio + "  AND PB.NOM_CONTRATANTE = "
					+ this.idVelatorio;
		}
	/*	if (this.fechaInicio != null && this.fechaFin != null) {
			condicion = condicion + " AND date_format(ssd.FEC_SOLICITUD,'%Y-%m-%d') >= '" + this.fechaInicio + "'"
					+ " AND date_format(ssd.FEC_SOLICITUD,'%Y-%m-%d') <= '" + this.fechaFin + "'";
			condicion1 = condicion1 + " AND date_format(sd.FEC_ALTA ,'%Y-%m-%d') >= '" + this.fechaInicio + "'"
					+ " AND date_format(sd.FEC_ALTA ,'%Y-%m-%d') <= '" + this.fechaFin + "'";
		}*/
		envioDatos.put("condicion", condicion);
		envioDatos.put("tipoReporte", reporteDto.getTipoReporte());
		envioDatos.put("rutaNombreReporte", nombrePdfReportes);

		return envioDatos;
	}

	public Map<String, Object> generarPlantillaControlSalidaDonacionPDF(
			PlantillaRecPagosRequest plantillaRecPagosRequest, String nombrePdfDetalleRecPagos) {
		Map<String, Object> envioDatos = new HashMap<>();
		
		envioDatos.put("folio", plantillaRecPagosRequest.getFolio());
		envioDatos.put("delegacion", plantillaRecPagosRequest.getDelegacion());
		envioDatos.put("velatorio", plantillaRecPagosRequest.getVelatorio());
		envioDatos.put("lugar", plantillaRecPagosRequest.getLugar());
		envioDatos.put("fecha", plantillaRecPagosRequest.getFecha());
		envioDatos.put("recibimos", plantillaRecPagosRequest.getRecibimos());
		envioDatos.put("cantidad", plantillaRecPagosRequest.getCantidad());
		envioDatos.put("tramites", plantillaRecPagosRequest.getTramites());
		envioDatos.put("descTramites", plantillaRecPagosRequest.getDescTramites());
		envioDatos.put("derechos", plantillaRecPagosRequest.getDerechos());
		envioDatos.put("descDerechos", plantillaRecPagosRequest.getDescDerechos());
		envioDatos.put("total", plantillaRecPagosRequest.getTotal());
		envioDatos.put("totalFinal", plantillaRecPagosRequest.getTotalFinal());
		envioDatos.put("rutaNombreReporte", nombrePdfDetalleRecPagos );
		envioDatos.put("tipoReporte", plantillaRecPagosRequest.getTipoReporte());
		
		return envioDatos;
	}
	
}
