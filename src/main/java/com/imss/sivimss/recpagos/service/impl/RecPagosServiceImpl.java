package com.imss.sivimss.recpagos.service.impl;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;

import com.imss.sivimss.recpagos.util.AppConstantes;
import com.imss.sivimss.recpagos.util.DatosRequest;
import com.imss.sivimss.recpagos.util.ProviderServiceRestTemplate;
import com.imss.sivimss.recpagos.util.RecibosUtil;
import com.imss.sivimss.recpagos.util.Response;

import java.util.Map;

import com.imss.sivimss.recpagos.util.MensajeResponseUtil;
import com.imss.sivimss.recpagos.beans.ConsultarRecPagos;
import com.imss.sivimss.recpagos.beans.RecPagos;
import com.imss.sivimss.recpagos.model.request.ConsultaRecPagosRequest;
import com.imss.sivimss.recpagos.model.request.PlantillaRecPagosRequest;
import com.imss.sivimss.recpagos.model.request.RecPagosRequest;
import com.imss.sivimss.recpagos.model.request.ReporteDto;
import com.imss.sivimss.recpagos.model.ReciboPago;
import com.imss.sivimss.recpagos.model.request.UsuarioDto;
import com.imss.sivimss.recpagos.service.RecPagosService;

@Service
public class RecPagosServiceImpl implements RecPagosService {

	private static final String SIN_INFORMACION = "45";  // No se encontró información relacionada a tu búsqueda.
	
	@Value("${endpoints.dominio-consulta}")
	private String urlConsulta;
	
	@Value("${endpoints.dominio-consulta-paginado}")
	private String urlConsultaPaginado;
	
	@Value("${generales.pdf-reporte-rec-pagos}")
	private String nombrePdfReportes;
	
	@Value("${plantilla.detalle-rec-pagos}")
	private String nombrePdfDetalleRecPagos;

	@Value("${endpoints.ms-reportes}")
	private String urlReportes;
	
	@Value("${endpoints.dominio-crear}")
	private String urlGenericoCrear;
	
	@Value("${endpoints.dominio-consulta}")
	private String urlConsultaGenerica;
	
	@Autowired
	private ProviderServiceRestTemplate providerRestTemplate;
	
	private static final String ERROR_AL_DESCARGAR_DOCUMENTO= "64"; // Error en la descarga del documento.Intenta nuevamente.

	@Override
	public Response<Object> consultarRecPagos(DatosRequest request, Authentication authentication) throws IOException {
		RecPagos recPagos= new RecPagos();
		return MensajeResponseUtil.mensajeConsultaResponse( providerRestTemplate.consumirServicio(recPagos.obtenerRecPagos(request, authentication).getDatos(), urlConsultaPaginado,
				authentication), SIN_INFORMACION );
	}

	@Override
	public Response<Object> buscarFiltrosRecPagos(DatosRequest request, Authentication authentication) throws IOException {
		Gson gson = new Gson();
		
		RecPagosRequest recPagosRequest = gson.fromJson(String.valueOf(request.getDatos().get(AppConstantes.DATOS)), RecPagosRequest.class);
		
		RecPagos recPagos = new RecPagos(recPagosRequest);

		return MensajeResponseUtil.mensajeConsultaResponse(providerRestTemplate.consumirServicio(recPagos.buscarFiltrosRecPagos(request,recPagos, authentication).getDatos(), urlConsultaPaginado,
				authentication), SIN_INFORMACION);
	}

	@Override
	public Response<Object> agregarRecibo(DatosRequest request, Authentication authentication) throws IOException {
		Gson gson = new Gson();

		String datosJson = String.valueOf(request.getDatos().get(AppConstantes.DATOS));
		UsuarioDto usuarioDto = gson.fromJson((String) authentication.getPrincipal(), UsuarioDto.class);
		ReciboPago reciboPago = gson.fromJson(datosJson, ReciboPago.class);
		RecibosUtil recibosUtil = new RecibosUtil();
		DatosRequest datos = recibosUtil.insertar(reciboPago, usuarioDto.getIdUsuario().toString());
		
		
		return providerRestTemplate.consumirServicio(datos.getDatos(), urlGenericoCrear,
				authentication);
	}
	

	@Override
	public Response<Object> generarDocumento(DatosRequest request, Authentication authentication)throws IOException {
		Gson gson = new Gson();
		String datosJson = String.valueOf(request.getDatos().get(AppConstantes.DATOS));
	
		RecPagosRequest recPagosRequest = gson.fromJson(datosJson, RecPagosRequest.class);
		RecPagos recPagos = new RecPagos(recPagosRequest);
		
		ReporteDto reporteDto= gson.fromJson(datosJson, ReporteDto.class);
		Map<String, Object> envioDatos = recPagos.generarReportePDF(reporteDto, nombrePdfReportes, authentication);
		return MensajeResponseUtil.mensajeConsultaResponse(providerRestTemplate.consumirServicioReportes(envioDatos, urlReportes,authentication)
				, ERROR_AL_DESCARGAR_DOCUMENTO);
		
	}
	
	@Override
	public Response<Object> generarDocumentoDetalleRecPagos(DatosRequest request, Authentication authentication)
			throws IOException {
		Gson gson = new Gson();
		PlantillaRecPagosRequest plantillaRecPagosRequest = gson.fromJson(String.valueOf(request.getDatos().get(AppConstantes.DATOS)), PlantillaRecPagosRequest.class);
		Map<String, Object> envioDatos = new RecPagos().generarPlantillaControlSalidaDonacionPDF(plantillaRecPagosRequest,nombrePdfDetalleRecPagos, authentication);
		return MensajeResponseUtil.mensajeConsultaResponse(providerRestTemplate.consumirServicioReportes(envioDatos, urlReportes, authentication)
				, ERROR_AL_DESCARGAR_DOCUMENTO);
	}
	
	@Override
	public Response<Object> buscarDatosReporteRecPagos(DatosRequest request, Authentication authentication) throws IOException {
		Gson gson = new Gson();
		
		ConsultaRecPagosRequest consultaRecPagosRequest = gson.fromJson(String.valueOf(request.getDatos().get(AppConstantes.DATOS)), ConsultaRecPagosRequest.class);
		
		ConsultarRecPagos consultarRecPagos = new ConsultarRecPagos(consultaRecPagosRequest);
		
		return MensajeResponseUtil.mensajeConsultaResponse(providerRestTemplate.consumirServicio(consultarRecPagos.buscarDatosReporteRecPagos(request,consultarRecPagos).getDatos(), urlConsultaGenerica,
				authentication), SIN_INFORMACION);
		
	}


}
