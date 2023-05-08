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
		
		StringBuilder query = new StringBuilder("SELECT "
				+ " PB.CVE_FOLIO AS folio, "
				+ " DEL.DES_DELEGACION AS delegacion, "
				+ " VEL.NOM_VELATORIO AS velatorio, "
				+ " 'Tapachula,Chiapas,Mx' AS lugar, "
				+ " PB.FEC_ODS AS fecha, "
				+ " PB.NOM_CONTRATANTE AS recibimos, "
				+ " '$50,000.00 (Cincuenta mil pesos)' AS cantidad, "
				+ " '$35,000.00' AS tramites, "
				+ " 'Descripcion del tramite' AS descTramites, "
				+ " '$15,000.00' AS derechos, "
				+ " 'Descripcion del tramite' AS descDerechos, "
				+ " '$50,000.00' AS total, "
				+ " '$50,000.00' AS totalFinal, "
				+ " 'reportes/plantilla/DetalleRecPagos.jrxml' AS rutaNombreReporte, "
				+ " 'pdf' AS tipoReporte "
				+ " FROM SVT_PAGO_BITACORA as PB "
				+ " INNER JOIN SVC_ORDEN_SERVICIO OS ON OS.ID_ORDEN_SERVICIO = PB.ID_PAGO_BITACORA "
				+ " INNER JOIN SVC_CONTRATANTE CON ON CON.ID_CONTRATANTE = OS.ID_CONTRATANTE "
				+ " INNER JOIN SVC_PERSONA PER ON PER.ID_PERSONA = CON.ID_PERSONA "
				+ " INNER JOIN SVC_VELATORIO VEL ON VEL.ID_VELATORIO = PB.ID_VELATORIO "
				+ " INNER JOIN SVC_DELEGACION DEL ON DEL.ID_DELEGACION = VEL.ID_DELEGACION ");
			query.append(" WHERE IFNULL(ID_PAGO_BITACORA,0) > 0 " );
		if (consultarRecPagos.getIdPagoBitacora() != null) {
			query.append(" AND PB.ID_PAGO_BITACORA = ").append(consultarRecPagos.getIdPagoBitacora());
		}
		query.append(" LIMIT 1 ");
		

		String encoded = DatatypeConverter.printBase64Binary(query.toString().getBytes());
		request.getDatos().put("rutaNombreReporte", nombrePdfDetalleRecPagos);
		request.getDatos().put(AppConstantes.QUERY, encoded);
		return request;
	}

}
