package com.imss.sivimss.recpagos.service.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;

import com.imss.sivimss.recpagos.util.AppConstantes;
import com.imss.sivimss.recpagos.util.ConvertirGenerico;
import com.imss.sivimss.recpagos.util.DatosRequest;
import com.imss.sivimss.recpagos.util.ProviderServiceRestTemplate;
import com.imss.sivimss.recpagos.util.Response;

import java.util.Map;

import com.imss.sivimss.recpagos.util.MensajeResponseUtil;
import com.imss.sivimss.recpagos.beans.RecPagos;
import com.imss.sivimss.recpagos.model.request.PlantillaRecPagosRequest;
import com.imss.sivimss.recpagos.model.request.RecPagosRequest;
import com.imss.sivimss.recpagos.model.request.ReporteDto;
import com.imss.sivimss.recpagos.model.request.UsuarioRequest;
import com.imss.sivimss.recpagos.service.RecPagosService;

@Service
public class RecPagosServiceImpl implements RecPagosService {

	private static final String SIN_INFORMACION = "45";  // No se encontró información relacionada a tu búsqueda.
	
	@Value("${endpoints.dominio-consulta}")
	private String urlConsulta;
	
	@Value("${endpoints.dominio-consulta-paginado}")
	private String urlConsultaPaginado;
	
	@Value("${endpoints.pdf-reporte-rec-pagos}")
	private String nombrePdfReportes;
	
	@Value("${plantilla.detalle-rec-pagos}")
	private String nombrePdfDetalleRecPagos;

	@Value("${endpoints.ms-reportes}")
	private String urlReportes;
	

	@Value("${endpoints.tipoReporte}")
	private String tipoReporte;
	
	@Autowired
	private ProviderServiceRestTemplate providerRestTemplate;
	
	private static final String ERROR_AL_DESCARGAR_DOCUMENTO= "64"; // Error en la descarga del documento.Intenta nuevamente.

	
	@Override
	public Response<?> consultarRecPagos(DatosRequest request, Authentication authentication) throws IOException {
		RecPagos recPagos= new RecPagos();
		return MensajeResponseUtil.mensajeConsultaResponse( providerRestTemplate.consumirServicio(recPagos.obtenerRecPagos(request).getDatos(), urlConsultaPaginado,
				authentication), SIN_INFORMACION );
	}

	@Override
	public Response<?> buscarFiltrosRecPagos(DatosRequest request, Authentication authentication) throws IOException {
		Gson gson = new Gson();
		
		RecPagosRequest recPagosRequest = gson.fromJson(String.valueOf(request.getDatos().get(AppConstantes.DATOS)), RecPagosRequest.class);
		
		RecPagos recPagos = new RecPagos(recPagosRequest);

		return MensajeResponseUtil.mensajeConsultaResponse(providerRestTemplate.consumirServicio(recPagos.buscarFiltrosRecPagos(request,recPagos).getDatos(), urlConsultaPaginado,
				authentication), SIN_INFORMACION);
	}
	

	@Override
	public Response<?> generarDocumento(DatosRequest request, Authentication authentication)throws IOException {
		Gson gson = new Gson();
		String datosJson = String.valueOf(request.getDatos().get(AppConstantes.DATOS));
	
		RecPagosRequest recPagosRequest = gson.fromJson(datosJson, RecPagosRequest.class);
		RecPagos recPagos = new RecPagos(recPagosRequest);
		
		ReporteDto reporteDto= gson.fromJson(datosJson, ReporteDto.class);
		Map<String, Object> envioDatos = recPagos.generarReportePDF(reporteDto, nombrePdfReportes);
		return MensajeResponseUtil.mensajeConsultaResponse(providerRestTemplate.consumirServicioReportes(envioDatos, urlReportes,authentication)
				, ERROR_AL_DESCARGAR_DOCUMENTO);
		
	}
	
	@Override
	public Response<?> generarDocumentoDetalleRecPagos(DatosRequest request, Authentication authentication)
			throws IOException {
		Gson gson = new Gson();
		PlantillaRecPagosRequest plantillaRecPagosRequest = gson.fromJson(String.valueOf(request.getDatos().get(AppConstantes.DATOS)), PlantillaRecPagosRequest.class);
		Map<String, Object> envioDatos = new RecPagos().generarPlantillaControlSalidaDonacionPDF(plantillaRecPagosRequest,nombrePdfDetalleRecPagos);
		return MensajeResponseUtil.mensajeConsultaResponse(providerRestTemplate.consumirServicioReportes(envioDatos, urlReportes, authentication)
				, ERROR_AL_DESCARGAR_DOCUMENTO);
	}
	
}
