package com.imss.sivimss.recpagos.util;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateUtil {

	private final RestTemplate restTemplate;

	public RestTemplateUtil(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	/**
	 * Env&iacute;a una petici&oacute;n con Body y token.
	 *
	 * @param url
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Response<Object> sendPostRequestByteArrayToken(String url, EnviarDatosRequest body, String subject,
			Class<?> clazz) {
		Response<?> responseBody;
		HttpHeaders headers = RestTemplateUtil.createHttpHeadersToken(subject);

		HttpEntity<Object> request = new HttpEntity<>(body, headers);
		ResponseEntity<?> responseEntity = null;

		responseEntity = restTemplate.postForEntity(url, request, clazz);

		responseBody = (Response<List<String>>) responseEntity.getBody();

		return (Response<Object>) responseBody;
	}
	
	/**
	 * 
	 * @param subject
	 * @return
	 */
	private static HttpHeaders createHttpHeadersToken(String subject) {
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		header.set("Authorization", "Bearer " + subject);

		header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		return header;
	}
	
	
	/**
	 * 
	 * @param url
	 * @param body
	 * @param subject
	 * @param clazz
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public Response<Object> sendPostRequestByteArrayReportesToken(String url, DatosReporteDTO body, String subject,
			Class<?> clazz) {
		Response<?> responseBody;
		HttpHeaders headers = RestTemplateUtil.createHttpHeadersToken(subject);

		HttpEntity<Object> request = new HttpEntity<>(body, headers);
		ResponseEntity<?> responseEntity = null;
		responseEntity = restTemplate.postForEntity(url, request, clazz);
		responseBody = (Response<List<String>>) responseEntity.getBody();

		return (Response<Object>) responseBody;
	}
}
