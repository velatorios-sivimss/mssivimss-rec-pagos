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

	private Integer idPagoBitacora;
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
	private String fechaInicio;
	private String fechaFin;

	public RecPagos(RecPagosRequest recPagosRequest) {
		this.claveFolio = recPagosRequest.getClaveFolio();
		this.nomContratante = recPagosRequest.getNomContratante();
		this.fechaInicio = recPagosRequest.getFechaInicio();
		this.fechaFin = recPagosRequest.getFechaFin();
		this.nivel = recPagosRequest.getNivel();
	}

	public RecPagos(UsuarioRequest usuarioRequest) {
		this.nivel = usuarioRequest.getIdOficina();
		this.idRol = usuarioRequest.getIdRol();
	}

	public DatosRequest obtenerRecPagos(DatosRequest request) {
		String query = "SELECT PB.ID_PAGO_BITACORA as idPagoBitacora, \r\n "
				+ "PB.FEC_ODS as fOds, PB.CVE_FOLIO as claveFolio, \r\n" + "PB.NOM_CONTRATANTE as nomContratante, \r\n"
				+ "PB.CVE_ESTATUS_PAGO as claveEstatusPago FROM svt_pago_bitacora as PB "
				+ "ORDER BY ID_PAGO_BITACORA ASC ";
		String encoded = DatatypeConverter.printBase64Binary(query.getBytes());
		request.getDatos().put(AppConstantes.QUERY, encoded);

		return request;
	}

	public DatosRequest buscarFiltrosRecPagos(DatosRequest request, RecPagos recPagos) {

		StringBuilder query = new StringBuilder(
				"SELECT PB.ID_PAGO_BITACORA as idPagoBitacora, PB.FEC_ODS as fOds, PB.CVE_FOLIO as claveFolio, "
						+ " PB.NOM_CONTRATANTE as nomContratante, " + " PB.CVE_ESTATUS_PAGO as claveEstatusPago "
						+ " FROM svt_pago_bitacora as PB ");
		query.append(" WHERE IFNULL(ID_PAGO_BITACORA,0) > 0");
		if (recPagos.getClaveFolio() != null) {
			query.append(" AND PB.CVE_FOLIO = '" + this.claveFolio + "'");
		}
		if (recPagos.getNomContratante() != null) {
			query.append(" AND PB.NOM_CONTRATANTE = '" + this.nomContratante + "'");
		}
		if (recPagos.fechaInicio != null && recPagos.fechaFin != null) {
			query.append(" AND PB.FEC_ODS >= '" + this.fechaInicio + "'");
			query.append(" AND PB.FEC_ODS <= '" + this.fechaFin + "'");
		}
		query.append(" ORDER BY PB.ID_PAGO_BITACORA DESC ");

		String encoded = DatatypeConverter.printBase64Binary(query.toString().getBytes());
		request.getDatos().put(AppConstantes.QUERY, encoded);
		return request;
	}

	public Map<String, Object> generarReportePDF(ReporteDto reporteDto, String nombrePdfReportes) {
		Map<String, Object> envioDatos = new HashMap<>();
		String condicion = " ";
		if (this.claveFolio != null) {
			condicion = condicion + " AND PB.CVE_FOLIO = '" + this.claveFolio + "'";
		}
		if (this.nomContratante != null) {
			condicion = condicion + " AND PB.NOM_CONTRATANTE = '"
					+ this.nomContratante + "'";
		}
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
		envioDatos.put("rutaNombreReporte", nombrePdfDetalleRecPagos);
		envioDatos.put("tipoReporte", plantillaRecPagosRequest.getTipoReporte());

		return envioDatos;
	}

}
