package com.imss.sivimss.recpagos.service.impl;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;

import com.imss.sivimss.recpagos.util.AppConstantes;
import com.imss.sivimss.recpagos.util.DatosRequest;
import com.imss.sivimss.recpagos.util.LogUtil;
import com.imss.sivimss.recpagos.util.ProviderServiceRestTemplate;
import com.imss.sivimss.recpagos.util.RecibosUtil;
import com.imss.sivimss.recpagos.util.Response;

import java.util.Map;
import java.util.logging.Level;

import javax.xml.bind.DatatypeConverter;

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
	
	@Value("${endpoints.dominio}")
	private String urlDomino;

	@Value("${generales.pdf-reporte-rec-pagos}")
	private String nombrePdfReportes;
	
	@Value("${plantilla.detalle-rec-pagos}")
	private String nombrePdfDetalleRecPagos;

	@Value("${endpoints.ms-reportes}")
	private String urlReportes;
	
	@Autowired
	private ProviderServiceRestTemplate providerRestTemplate;
	
	@Autowired
	private LogUtil logUtil;
	
	private static final String CONSULTA = "consulta";
	
	private static final String ERROR_AL_DESCARGAR_DOCUMENTO= "64"; // Error en la descarga del documento.Intenta nuevamente.

	private static final String CONSULTA_PAGINADA = "/generico/paginado";
	
	private static final String CREAR = "/generico/crear";
	
	private static final String CONSULTA_GENERICA = "/generico/consulta";
	
	@Override
	public Response<Object> consultarRecPagos(DatosRequest request, Authentication authentication) throws IOException {
		RecPagos recPagos= new RecPagos();
		
		Map<String, Object> dato = recPagos.obtenerRecPagos(request).getDatos();
		String query = (String) dato.get(AppConstantes.QUERY);
		query = new String(DatatypeConverter.parseBase64Binary(query));
		
		logUtil.crearArchivoLog(Level.INFO.toString(), this.getClass().getSimpleName(), 
				this.getClass().getPackage().toString(), "",CONSULTA +" " + query, authentication);
		
		Response<Object> response = providerRestTemplate.consumirServicio(dato, urlDomino + CONSULTA_PAGINADA, 
				authentication);
		
		return MensajeResponseUtil.mensajeConsultaResponse( response, SIN_INFORMACION );
	}

	@Override
	public Response<Object> buscarFiltrosRecPagos(DatosRequest request, Authentication authentication) throws IOException {
		Gson gson = new Gson();
		
		RecPagosRequest recPagosRequest = gson.fromJson(String.valueOf(request.getDatos().get(AppConstantes.DATOS)), RecPagosRequest.class);
		
		RecPagos recPagos = new RecPagos(recPagosRequest);

		Map<String, Object> dato = recPagos.buscarFiltrosRecPagos(request,recPagos).getDatos();
		String query = (String) dato.get(AppConstantes.QUERY);
		query = new String(DatatypeConverter.parseBase64Binary(query));
		
		logUtil.crearArchivoLog(Level.INFO.toString(), this.getClass().getSimpleName(), 
				this.getClass().getPackage().toString(), "",CONSULTA +" " + query, authentication);
		
		Response<Object> response = providerRestTemplate.consumirServicio(dato
				, urlDomino + CONSULTA_PAGINADA,
				authentication);
		
		return MensajeResponseUtil.mensajeConsultaResponse(response, SIN_INFORMACION);
	}

	@Override
	public Response<Object> agregarRecibo(DatosRequest request, Authentication authentication) throws IOException {
		Gson gson = new Gson();

		String datosJson = String.valueOf(request.getDatos().get(AppConstantes.DATOS));
		UsuarioDto usuarioDto = gson.fromJson((String) authentication.getPrincipal(), UsuarioDto.class);
		ReciboPago reciboPago = gson.fromJson(datosJson, ReciboPago.class);
		RecibosUtil recibosUtil = new RecibosUtil();
		DatosRequest datos = recibosUtil.insertar(reciboPago, usuarioDto.getIdUsuario().toString());
		
		Map<String, Object> dato = datos.getDatos();
		String query = (String) dato.get(AppConstantes.QUERY);
		query = new String(DatatypeConverter.parseBase64Binary(query));
		
		logUtil.crearArchivoLog(Level.INFO.toString(), this.getClass().getSimpleName(), 
				this.getClass().getPackage().toString(), "",CONSULTA +" " + query, authentication);
		
		
		return providerRestTemplate.consumirServicio(dato, urlDomino + CREAR,
				authentication);
	}
	

	@Override
	public Response<Object> generarDocumento(DatosRequest request, Authentication authentication)throws IOException {
		Gson gson = new Gson();
		String datosJson = String.valueOf(request.getDatos().get(AppConstantes.DATOS));
	
		RecPagosRequest recPagosRequest = gson.fromJson(datosJson, RecPagosRequest.class);
		RecPagos recPagos = new RecPagos(recPagosRequest);
		
		ReporteDto reporteDto= gson.fromJson(datosJson, ReporteDto.class);
		Map<String, Object> envioDatos = recPagos.generarReportePDF(reporteDto, nombrePdfReportes);
		
		logUtil.crearArchivoLog(Level.INFO.toString(), this.getClass().getSimpleName(), 
				this.getClass().getPackage().toString(), "",CONSULTA +" " + envioDatos, authentication);
		
		return MensajeResponseUtil.mensajeConsultaResponse(providerRestTemplate.consumirServicioReportes(envioDatos, urlReportes,authentication)
				, ERROR_AL_DESCARGAR_DOCUMENTO);
		
	}
	
	@Override
	public Response<Object> generarDocumentoDetalleRecPagos(DatosRequest request, Authentication authentication)
			throws IOException {
		Gson gson = new Gson();
		PlantillaRecPagosRequest plantillaRecPagosRequest = gson.fromJson(String.valueOf(request.getDatos().get(AppConstantes.DATOS)), PlantillaRecPagosRequest.class);
		Map<String, Object> envioDatos = new RecPagos().generarPlantillaControlSalidaDonacionPDF(plantillaRecPagosRequest,nombrePdfDetalleRecPagos);
		
		logUtil.crearArchivoLog(Level.INFO.toString(), this.getClass().getSimpleName(), 
				this.getClass().getPackage().toString(), "",CONSULTA +" " + envioDatos, authentication);
		
		return MensajeResponseUtil.mensajeConsultaResponse(providerRestTemplate.consumirServicioReportes(envioDatos, urlReportes, authentication)
				, ERROR_AL_DESCARGAR_DOCUMENTO);
	}
	
	@Override
	public Response<Object> buscarDatosReporteRecPagos(DatosRequest request, Authentication authentication) throws IOException {
		Gson gson = new Gson();
		
		ConsultaRecPagosRequest consultaRecPagosRequest = gson.fromJson(String.valueOf(request.getDatos().get(AppConstantes.DATOS)), ConsultaRecPagosRequest.class);
		
		ConsultarRecPagos consultarRecPagos = new ConsultarRecPagos(consultaRecPagosRequest);
		
		Map<String, Object> dato = consultarRecPagos.buscarDatosReporteRecPagos(request,consultarRecPagos).getDatos();
		String query = (String) dato.get(AppConstantes.QUERY);
		query = new String(DatatypeConverter.parseBase64Binary(query));
		
		logUtil.crearArchivoLog(Level.INFO.toString(), this.getClass().getSimpleName(), 
				this.getClass().getPackage().toString(), "",CONSULTA +" " + query, authentication);
		
		Response<Object> response = providerRestTemplate.consumirServicio( dato, urlDomino + CONSULTA_GENERICA, authentication );
		
		
		return MensajeResponseUtil.mensajeConsultaResponse(response, SIN_INFORMACION);
		
	}


}
