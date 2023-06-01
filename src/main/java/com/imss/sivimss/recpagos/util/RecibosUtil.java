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
		q.agregarParametroValues("DESC_TRAMITES", "'" + reciboPago.getDescTramites() + "'");
		q.agregarParametroValues("CAN_DERECHOS", "'" + reciboPago.getCanDerechos() + "'");
		q.agregarParametroValues("DESC_DERECHOS", "'" + reciboPago.getDescDerechos() + "'");
		q.agregarParametroValues("CAN_SUMA", "'" + reciboPago.getCanSuma() + "'");
		q.agregarParametroValues("CAN_TOTAL", "'" + reciboPago.getCanTotal() + "'");
		q.agregarParametroValues("AGENTE_FUNERAL_MAT", "'" + reciboPago.getAgenteFuneMat() + "'");
		q.agregarParametroValues("RECIBE_MAT", "'" + reciboPago.getRecibeMat() + "'");
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
				+ "AND OS.ID_VELATORIO = '" + idVelatorio + "'");
		
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
		
		return query.toString();
	}
	
}
