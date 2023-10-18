package com.imss.sivimss.recpagos.beans;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.imss.sivimss.recpagos.model.request.PlantillaRecPagosRequest;
import com.imss.sivimss.recpagos.model.request.RecPagosRequest;
import com.imss.sivimss.recpagos.model.request.ReporteDto;
import com.imss.sivimss.recpagos.model.request.UsuarioRequest;
import com.imss.sivimss.recpagos.util.AppConstantes;
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
	private static final Logger log = LoggerFactory.getLogger(RecPagos.class);

	public RecPagos(RecPagosRequest recPagosRequest) {
		this.claveFolio = recPagosRequest.getClaveFolio();
		this.nomContratante = recPagosRequest.getNomContratante();
		this.fechaInicio = recPagosRequest.getFecIniODS();
		this.fechaFin = recPagosRequest.getFecFinODS();
		this.nivel = recPagosRequest.getNivel();
	}

	public RecPagos(UsuarioRequest usuarioRequest) {
		this.nivel = usuarioRequest.getIdOficina();
		this.idRol = usuarioRequest.getIdRol();
	}

	public DatosRequest obtenerRecPagos(DatosRequest request) {
		String query = "SELECT "
				+ "PD.ID_PAGO_DETALLE AS idPagoBitacora, "
				+ "OS.FEC_ALTA AS fOds, "
				+ "OS.CVE_FOLIO AS claveFolio, "
				+ "PB.NOM_CONTRATANTE AS nomContratante, "
				+ "EOS.DES_ESTATUS AS claveEstatusPago, "
				+ "RP.ID_RECIBO_PAGO AS idReciboPago "
				+ "FROM SVT_PAGO_DETALLE PD "
				+ "INNER JOIN SVT_PAGO_BITACORA PB ON PB.ID_PAGO_BITACORA = PD.ID_PAGO_BITACORA "
				+ "INNER JOIN SVC_ORDEN_SERVICIO OS ON OS.ID_ORDEN_SERVICIO = PB.ID_REGISTRO "
				+ "INNER JOIN SVC_ESTATUS_ORDEN_SERVICIO EOS ON EOS.ID_ESTATUS_ORDEN_SERVICIO = OS.ID_ESTATUS_ORDEN_SERVICIO "
				+ "LEFT JOIN SVT_RECIBO_PAGO RP ON RP.ID_PAGO_DETALLE = PD.ID_PAGO_DETALLE "
				+ "WHERE "
				+ "OS.ID_ESTATUS_ORDEN_SERVICIO = '2' "
				+ "AND PB.CVE_ESTATUS_PAGO = '2' "
				+ "AND PB.ID_FLUJO_PAGOS = '1' "
				+ "ORDER BY OS.ID_ORDEN_SERVICIO ASC ";
		log.info(query);
		String encoded = DatatypeConverter.printBase64Binary(query.getBytes());
		request.getDatos().put(AppConstantes.QUERY, encoded);

		return request;
	}

	public DatosRequest buscarFiltrosRecPagos(DatosRequest request, RecPagosRequest recPagos) {

		StringBuilder query = new StringBuilder(
				"SELECT "
						+ "PD.ID_PAGO_DETALLE AS idPagoBitacora, "
						+ "OS.FEC_ALTA AS fOds, "
						+ "OS.CVE_FOLIO AS claveFolio, "
						+ "PB.NOM_CONTRATANTE AS nomContratante, "
						+ "EOS.DES_ESTATUS AS claveEstatusPago, "
						+ "RP.ID_RECIBO_PAGO AS idReciboPago "
						+ "FROM SVT_PAGO_DETALLE PD "
						+ "INNER JOIN SVT_PAGO_BITACORA PB ON PB.ID_PAGO_BITACORA = PD.ID_PAGO_BITACORA "
						+ "INNER JOIN SVC_ORDEN_SERVICIO OS ON OS.ID_ORDEN_SERVICIO = PB.ID_REGISTRO "
						+ "INNER JOIN SVC_ESTATUS_ORDEN_SERVICIO EOS ON EOS.ID_ESTATUS_ORDEN_SERVICIO = OS.ID_ESTATUS_ORDEN_SERVICIO "
						+ "INNER JOIN SVC_VELATORIO VEL ON VEL.ID_VELATORIO = OS.ID_VELATORIO "
						+ "LEFT JOIN SVT_RECIBO_PAGO RP ON RP.ID_PAGO_DETALLE = PD.ID_PAGO_DETALLE "
						+ "WHERE "
						+ "OS.ID_ESTATUS_ORDEN_SERVICIO = '2' "
						+ "AND PB.CVE_ESTATUS_PAGO IN (2, 4, 8) "
						+ "AND PB.ID_FLUJO_PAGOS = '1' ");
		
		if (recPagos.getClaveFolio() != null) {
			query.append( "AND OS.CVE_FOLIO LIKE CONCAT('" + recPagos.getClaveFolio() + "', '%') " );
		}
		
		if (recPagos.getNomContratante() != null) {
			query.append( "AND PB.NOM_CONTRATANTE LIKE CONCAT('" + recPagos.getNomContratante() + "', '%') " );
		}
		
		if (recPagos.getFecIniODS() != null && recPagos.getFecFinODS() != null) {
			query.append("AND OS.FEC_ALTA BETWEEN '" + recPagos.getFecIniODS() + "'"
					+ " AND '" + recPagos.getFecFinODS() + "' ");
		}
		
		if (recPagos.getIdVelatorio() != null) {
			query.append( "AND OS.ID_VELATORIO = '" + recPagos.getIdVelatorio() + "' " );
		}
		
		if (recPagos.getIdDelegacion() != null) {
			query.append( "AND VEL.ID_DELEGACION = '" + recPagos.getIdDelegacion() + "' " );
		}
		
		query.append(" ORDER BY OS.ID_ORDEN_SERVICIO ASC ");

		log.info(query.toString());
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
		log.info(condicion);
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
		envioDatos.put("folioPF", plantillaRecPagosRequest.getFolioPF());

		return envioDatos;
	}

}
