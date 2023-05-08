package com.imss.sivimss.recpagos.util;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.lang.Nullable;


public class MultipartInputStreamFileResource extends InputStreamResource {

    private final String filename;

    private Logger log = LoggerFactory.getLogger(MultipartInputStreamFileResource.class);
    
    MultipartInputStreamFileResource(InputStream inputStream, String filename) {
        super(inputStream);
        this.filename = filename;
    }

    @Override
    public String getFilename() {
        return this.filename;
    }

    @Override
    public long contentLength() throws IOException {
        return -1; // we do not want to generally read the whole stream into memory ...
    }
    
    @Override
	public boolean equals(@Nullable Object other) {
    	if (! super.equals(other)) {
    	      return false;
    	    }
    	
    	MultipartInputStreamFileResource objeto = (MultipartInputStreamFileResource)other;
    	
    	return filename.equals( objeto.getFilename() );
    	
	}
    
    @Override
	public int hashCode() {
		try {
			return getInputStream().hashCode();
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		return 0;
	}
}
