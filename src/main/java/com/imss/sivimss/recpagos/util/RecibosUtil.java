package com.imss.sivimss.recpagos.util;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.imss.sivimss.recpagos.model.ReciboPago;

public class RecibosUtil {

	private Logger log = LoggerFactory.getLogger(RecibosUtil.class);
	
	public DatosRequest insertar(ReciboPago reciboPago, String idUsuarioAlta) {
		
		DatosRequest request = new DatosRequest();
		Map<String, Object> parametro = new HashMap<>();

		final QueryHelper q = new QueryHelper("INSERT INTO SVT_RECIBO_PAGO");
		q.agregarParametroValues("NUM_FOLIO", "'" + reciboPago.getNumFolio() + "'");
		q.agregarParametroValues("ID_DELEGACION", "'" + reciboPago.getIdDelegacion() + "'");
		q.agregarParametroValues("ID_VELATORIO", "'" + reciboPago.getIdVelatorio() + "'");
		q.agregarParametroValues("ID_PAGO_DETALLE", "'" + reciboPago.getIdPagoDetalle() + "'");
		q.agregarParametroValues("FEC_RECIBO_PAGO", "'" + reciboPago.getFecReciboPago() + "'");
		q.agregarParametroValues("NOM_CONTRATANTE", "'" + reciboPago.getNomContratante() + "'");
		q.agregarParametroValues("CAN_RECIBO_PAGO", "'" + reciboPago.getCanReciboPago() + "'");
		q.agregarParametroValues("CAN_TRAMITES", "'" + reciboPago.getCanTramites() + "'");
		q.agregarParametroValues("REF_TRAMITES", "'" + reciboPago.getDescTramites() + "'");
		q.agregarParametroValues("CAN_DERECHOS", "'" + reciboPago.getCanDerechos() + "'");
		q.agregarParametroValues("REF_DERECHOS", "'" + reciboPago.getDescDerechos() + "'");
		q.agregarParametroValues("CAN_SUMA", "'" + reciboPago.getCanSuma() + "'");
		q.agregarParametroValues("CAN_TOTAL", "'" + reciboPago.getCanTotal() + "'");
		q.agregarParametroValues("REF_AGENTE_FUNERAL_MAT", "'" + reciboPago.getAgenteFuneMat() + "'");
		q.agregarParametroValues("REF_RECIBE_MAT", "'" + reciboPago.getRecibeMat() + "'");
		q.agregarParametroValues("IND_ACTIVO", "1");
		q.agregarParametroValues("ID_USUARIO_ALTA", "'" + idUsuarioAlta + "'");
		
		String query = q.obtenerQueryInsertar();
		log.info( query );
		String encoded = DatatypeConverter.printBase64Binary(query.getBytes());
		parametro.put(AppConstantes.QUERY, encoded);
		request.setDatos(parametro);

		return request;
	}
	
	public String consultaFolios(String idVelatorio) {
		StringBuilder query = new StringBuilder("");
		
		query.append( "SELECT "
				+ "DISTINCT(OS.ID_ORDEN_SERVICIO) AS idOds, "
				+ "OS.CVE_FOLIO AS folioOds "
				+ "FROM SVC_ORDEN_SERVICIO OS "
				+ "INNER JOIN SVT_PAGO_BITACORA PB ON PB.ID_REGISTRO = OS.ID_ORDEN_SERVICIO "
				+ "INNER JOIN SVT_PAGO_DETALLE PD ON PD.ID_PAGO_BITACORA = PB.ID_PAGO_BITACORA "
				+ "WHERE "
				+ "PB.ID_FLUJO_PAGOS = '1' "
				+ "AND OS.ID_ESTATUS_ORDEN_SERVICIO = '2' "
				+ "AND OS.ID_VELATORIO = '" + idVelatorio + "'"
				+ "AND PD.CVE_ESTATUS = '4' ");
		log.info( query.toString() );
		
		return query.toString();
	}
	
	public String consultaTramites(String idVelatorio) {
		StringBuilder query = new StringBuilder("");
		
		query.append( "SELECT "
				+ "ID_TRAMITE AS idTramite, "
				+ "DES_NOM_TRAMITE AS desTramite, "
				+ "IMP_TRAMITE AS importe "
				+ "FROM "
				+ "SVC_TRAMITE "
				+ "WHERE "
				+ "IND_ACTIVO = '1' "
				+ "AND  IND_TIPO_TRAMITE = '0' "
				+ "AND ID_VELATORIO = '" + idVelatorio + "'");
		log.info( query.toString() );
		
		return query.toString();
	}
	
	public String consultaDerechos(String idVelatorio) {
		StringBuilder query = new StringBuilder("");
		
		query.append( "SELECT "
				+ "ID_TRAMITE AS idDerecho, "
				+ "DES_NOM_TRAMITE AS desDerecho, "
				+ "IMP_TRAMITE AS importe "
				+ "FROM "
				+ "SVC_TRAMITE "
				+ "WHERE "
				+ "IND_ACTIVO = '1' "
				+ "AND  IND_TIPO_TRAMITE = '1' "
				+ "AND ID_VELATORIO = '" + idVelatorio + "'");
		log.info( query.toString() );
		
		return query.toString();
	}
	
	public String consultaReciboPago(String idReciboPago) {
		
		StringBuilder query = new StringBuilder("");
		
		query.append( "SELECT  "
				+ "RP.NUM_FOLIO AS claveFolio,  "
				+ "DEL.DES_DELEGACION AS delegacion,  "
				+ "VEL.DES_VELATORIO AS velatorio,  "
				+ "RP.FEC_RECIBO_PAGO AS fecha,  "
				+ "RP.NOM_CONTRATANTE AS recibimos,  "
				+ "RP.CAN_RECIBO_PAGO AS cantidad,  "
				+ "'reportes/plantilla/DetalleRecPagos.jrxml' AS rutaNombreReporte,  "
				+ "'pdf' AS tipoReporte,  "
				+ "OS.ID_VELATORIO AS idVelatorio, "
				+ "RP.CAN_TRAMITES AS canTramites, "
				+ "RP.REF_TRAMITES AS descTramites, "
				+ "RP.CAN_DERECHOS AS canDerechos, "
				+ "RP.REF_DERECHOS AS descDerechos, "
				+ "RP.CAN_SUMA AS canSuma, "
				+ "RP.CAN_TOTAL AS canTotal, "
				+ "RP.REF_AGENTE_FUNERAL_MAT AS agenteFuneMat, "
				+ "RP.REF_RECIBE_MAT AS recibeMat, "
				+ "IFNULL( CPF.DES_FOLIO, 'NA') AS folioPF, "
				+ "CONCAT(LPAD(RP.ID_RECIBO_PAGO, 5, '0'), OS.ID_VELATORIO ) AS folio "
				+ "FROM SVT_RECIBO_PAGO RP "
				+ "INNER JOIN SVT_PAGO_DETALLE PD ON PD.ID_PAGO_DETALLE = RP.ID_PAGO_DETALLE "
				+ "INNER JOIN SVT_PAGO_BITACORA PB ON PB.ID_PAGO_BITACORA = PD.ID_PAGO_BITACORA "
				+ "INNER JOIN SVC_ORDEN_SERVICIO OS ON OS.ID_ORDEN_SERVICIO = PB.ID_REGISTRO "
				+ "INNER JOIN SVC_VELATORIO VEL ON VEL.ID_VELATORIO = RP.ID_VELATORIO "
				+ "INNER JOIN SVC_DELEGACION DEL ON DEL.ID_DELEGACION = RP.ID_DELEGACION "
				+ "INNER JOIN SVC_FINADO F ON F.ID_ORDEN_SERVICIO = OS.ID_ORDEN_SERVICIO "
				+ "LEFT JOIN SVT_CONVENIO_PF CPF ON CPF.ID_CONVENIO_PF = F.ID_CONTRATO_PREVISION "
				+ "WHERE " );
		query.append( "RP.ID_RECIBO_PAGO = '" + idReciboPago + "' " );
		query.append( "LIMIT 1" );
		log.info( query.toString() );
		
		return query.toString();
	}
	
}
