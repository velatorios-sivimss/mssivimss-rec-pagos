package com.imss.sivimss.recpagos.beans;

import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Value;

import com.imss.sivimss.recpagos.model.request.ConsultaRecPagosRequest;
import com.imss.sivimss.recpagos.util.AppConstantes;
import com.imss.sivimss.recpagos.util.DatosRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ConsultarRecPagos extends ConsultaRecPagosRequest{
	
	@Value("${plantilla.detalle-rec-pagos}")
	private String nombrePdfDetalleRecPagos;
	
	
	public ConsultarRecPagos(ConsultaRecPagosRequest consultaRecPagosRequest) {
		this.idPagoBitacora = consultaRecPagosRequest.getIdPagoBitacora();
		this.folio = consultaRecPagosRequest.getFolio();
		this.delegacion = consultaRecPagosRequest.getDelegacion();
		this.velatorio = consultaRecPagosRequest.getVelatorio();
		this.lugar = consultaRecPagosRequest.getLugar();
		this.fecha = consultaRecPagosRequest.getFecha();
		this.recibimos = consultaRecPagosRequest.getRecibimos();
		this.cantidad = consultaRecPagosRequest.getCantidad();
		this.tramites = consultaRecPagosRequest.getTramites();
		this.descTramites = consultaRecPagosRequest.getDescTramites();
		this.derechos = consultaRecPagosRequest.getDerechos();
		this.descDerechos = consultaRecPagosRequest.getDescDerechos();
		this.total = consultaRecPagosRequest.getTotal();
		this.totalFinal = consultaRecPagosRequest.getTotalFinal();
		this.tipoReporte = consultaRecPagosRequest.getTipoReporte();
	}


	public DatosRequest buscarDatosReporteRecPagos(DatosRequest request,ConsultarRecPagos consultarRecPagos) {
		
		StringBuilder query = new StringBuilder("SELECT \r\n"
				+ "OS.CVE_FOLIO AS claveFolio, \r\n"
				+ "DEL.DES_DELEGACION AS delegacion, \r\n"
				+ "VEL.DES_VELATORIO AS velatorio, \r\n"
				+ "OS.FEC_ALTA AS fecha, \r\n"
				+ "PB.NOM_CONTRATANTE AS recibimos, \r\n"
				+ "PD.IMP_IMPORTE AS cantidad, \r\n"
				+ "'reportes/plantilla/DetalleRecPagos.jrxml' AS rutaNombreReporte, \r\n"
				+ "'pdf' AS tipoReporte, \r\n"
				+ "OS.ID_VELATORIO AS idVelatorio,\r\n"
				+ "IFNULL( CPF.DES_FOLIO, 'NA') AS folioPF,\r\n"
				+ "PD.ID_PAGO_DETALLE AS idPagoDetalle\r\n"
				+ "FROM SVT_PAGO_DETALLE PD\r\n"
				+ "INNER JOIN SVT_PAGO_BITACORA PB ON PB.ID_PAGO_BITACORA = PD.ID_PAGO_BITACORA\r\n"
				+ "INNER JOIN SVC_ORDEN_SERVICIO OS ON OS.ID_ORDEN_SERVICIO = PB.ID_REGISTRO\r\n"
				+ "INNER JOIN SVC_VELATORIO VEL ON VEL.ID_VELATORIO = OS.ID_VELATORIO\r\n"
				+ "INNER JOIN SVC_DELEGACION DEL ON DEL.ID_DELEGACION = VEL.ID_DELEGACION\r\n"
				+ "INNER JOIN SVC_FINADO F ON F.ID_ORDEN_SERVICIO = OS.ID_ORDEN_SERVICIO\r\n"
				+ "LEFT JOIN SVT_CONVENIO_PF CPF ON CPF.ID_CONVENIO_PF = F.ID_CONTRATO_PREVISION\r\n"
				+ "WHERE ");
		if (consultarRecPagos.getIdPagoBitacora() != null) {
			query.append(" PD.ID_PAGO_DETALLE = '" + consultarRecPagos.getIdPagoBitacora() + "' ");
		}
		query.append(" LIMIT 1 ");
		

		String encoded = DatatypeConverter.printBase64Binary(query.toString().getBytes());
		request.getDatos().put("rutaNombreReporte", nombrePdfDetalleRecPagos);
		request.getDatos().put(AppConstantes.QUERY, encoded);
		return request;
	}

}
