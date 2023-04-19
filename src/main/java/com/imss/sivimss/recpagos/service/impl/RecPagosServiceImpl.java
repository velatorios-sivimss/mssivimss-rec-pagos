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
import com.imss.sivimss.recpagos.util.MensajeResponseUtil;
import com.imss.sivimss.recpagos.beans.RecPagos;
import com.imss.sivimss.recpagos.model.ReciboPago;
import com.imss.sivimss.recpagos.model.request.RecPagosRequest;
import com.imss.sivimss.recpagos.model.request.UsuarioDto;
import com.imss.sivimss.recpagos.service.RecPagosService;

@Service
public class RecPagosServiceImpl implements RecPagosService {

	private static final String SIN_INFORMACION = "45";  // No se encontró información relacionada a tu búsqueda.
	
	@Value("${endpoints.dominio-consulta}")
	private String urlConsulta;
	
	@Value("${endpoints.dominio-consulta-paginado}")
	private String urlConsultaPaginado;
	
	@Autowired
	private ProviderServiceRestTemplate providerRestTemplate;
	
	@Value("${endpoints.dominio-crear}")
	private String urlGenericoCrear;
	
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
	public Response<?> agregarRecibo(DatosRequest request, Authentication authentication) throws IOException {
		Gson gson = new Gson();

		String datosJson = String.valueOf(request.getDatos().get(AppConstantes.DATOS));
		UsuarioDto usuarioDto = gson.fromJson((String) authentication.getPrincipal(), UsuarioDto.class);
		ReciboPago reciboPago = gson.fromJson(datosJson, ReciboPago.class);
		RecibosUtil recibosUtil = new RecibosUtil();
		DatosRequest datos = recibosUtil.insertar(reciboPago, usuarioDto.getIdUsuario().toString());
		
		
		return providerRestTemplate.consumirServicio(datos.getDatos(), urlGenericoCrear,
				authentication);
	}
	
}
