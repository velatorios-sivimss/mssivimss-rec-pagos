package com.imss.sivimss.recpagos.service;

import java.io.IOException;
import org.springframework.security.core.Authentication;

import com.imss.sivimss.recpagos.util.DatosRequest;
import com.imss.sivimss.recpagos.util.Response;

public interface RecPagosService {

	Response<Object> consultarRecPagos(DatosRequest request, Authentication authentication) throws IOException;
	
	Response<Object> buscarFiltrosRecPagos(DatosRequest request, Authentication authentication) throws IOException;
	
	Response<Object> agregarRecibo(DatosRequest request, Authentication authentication) throws IOException;

	Response<Object> generarDocumento(DatosRequest request, Authentication authentication) throws IOException;
	
	Response<Object> generarDocumentoDetalleRecPagos(DatosRequest request, Authentication authentication) throws IOException;

	Response<Object> buscarDatosReporteRecPagos(DatosRequest request, Authentication authentication) throws IOException;
	
}
