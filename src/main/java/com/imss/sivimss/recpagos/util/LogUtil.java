package com.imss.sivimss.recpagos.util;

import com.google.gson.Gson;
import com.imss.sivimss.recpagos.model.request.UsuarioDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class LogUtil {
	 @Value("${ruta-log}")
	    private String rutaLog;
	    
	    @Value("$(spring.application.name)")
	    private String nombreApp;

	    private String formatoFechaLog = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(new Date());

	    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LogUtil.class);

	    private static final String GUION_CORCHETE =  "--- [ "; 
	    private static final String USUARIO = " , Usuario: ";

	    public void crearArchivoLog(String tipoLog, String origen, String clasePath, String mensaje, String tiempoEjecucion, Authentication authentication) throws IOException {

	        File archivo = new File(rutaLog + nombreApp + "_" + new SimpleDateFormat("ddMMyyyy").format(new Date()) + ".log");
	        try (FileWriter escribirArchivo = new FileWriter(archivo, true)){
	            Gson json = new Gson();
	            UsuarioDto usuarioDto = json.fromJson((String) authentication.getPrincipal(), UsuarioDto.class);
	            String contenido = "" + formatoFechaLog + GUION_CORCHETE + tipoLog + "] " + origen + " " + clasePath + " : " + mensaje + USUARIO + usuarioDto.getCveUsuario() + " - " + tiempoEjecucion;
	            log.info(contenido);
	            if (archivo.exists()) {
	                escribirArchivo.write(contenido);
	                escribirArchivo.write("\r\n");
	            } else {
	                if(!archivo.createNewFile())
	                    log.error("No se puede crear Archivo de log.");
	                escribirArchivo.write(contenido);
	                escribirArchivo.write("\r\n");
	            }
	        } catch (Exception e) {
	            log.error("No se puede escribir el log.");
	            log.error(e.getMessage());
	        }

	    }

	    public void crearArchivoLogDTO(String tipoLog, String origen, String clasePath, String mensaje, String tiempoEjecucion, UsuarioDto usuarioDto) throws IOException {
	    	
	            File archivo = new File(rutaLog + new SimpleDateFormat("ddMMyyyy").format(new Date()) + ".log");
	            
	        	try (FileWriter escribirArchivo = new FileWriter(archivo, true)) {
	            if (archivo.exists()) {
	                escribirArchivo.write("" + formatoFechaLog + GUION_CORCHETE + tipoLog + "] " + origen + " " + clasePath + " : " + mensaje + USUARIO + usuarioDto.getCveUsuario() + " - " + tiempoEjecucion);
	                escribirArchivo.write("\r\n");
	            } else {
	                if(!archivo.createNewFile())
	                    log.error("No se puede crear Archivo de log.");
	                escribirArchivo.write("" + formatoFechaLog + GUION_CORCHETE + tipoLog + "] " + origen + " " + clasePath + " : " + mensaje + USUARIO + usuarioDto.getCveUsuario() + " - " + tiempoEjecucion);
	                escribirArchivo.write("\r\n");
	            }
	        } catch (Exception e) {
	            log.error("No se puede escribir el log.");
	            log.error(e.getMessage());
	        }
	    }

}
