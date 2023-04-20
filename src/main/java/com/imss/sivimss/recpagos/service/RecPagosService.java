package com.imss.sivimss.recpagos.service;

import java.io.IOException;
import org.springframework.security.core.Authentication;

import com.imss.sivimss.recpagos.util.DatosRequest;
import com.imss.sivimss.recpagos.util.Response;

public interface RecPagosService {

	Response<?> consultarRecPagos(DatosRequest request, Authentication authentication) throws IOException;
	
	Response<?> buscarFiltrosRecPagos(DatosRequest request, Authentication authentication) throws IOException;
	
	Response<?> generarDocumento(DatosRequest request, Authentication authentication) throws IOException;
	
	Response<?> generarDocumentoDetalleRecPagos(DatosRequest request, Authentication authentication) throws IOException;
	
}
