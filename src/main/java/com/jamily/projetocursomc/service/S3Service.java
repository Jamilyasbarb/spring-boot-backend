package com.jamily.projetocursomc.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.jamily.projetocursomc.service.exceptions.FileException;

@Service
public class S3Service {

	private Logger LOG = LoggerFactory.getLogger(S3Service.class);

	@Autowired
	private AmazonS3 s3client;

	@Value("${s3.bucket}")
	private String bucketName;

	public URI uploadFile(MultipartFile multipartFile) {
		try {
			String fileName = multipartFile.getOriginalFilename(); // extrai o nome do arquivo que foi enviado
			InputStream inputStream = multipartFile.getInputStream(); // encapsula o processamento de leitura a partir de
			// uma origem
			String contentType = multipartFile.getContentType(); // tipo do arquivo que foi enviado
			return uploadFile(inputStream, fileName, contentType);
		} catch (IOException e) {
			throw new FileException("Erro IO: " + e.getMessage());
		}
	}

	public URI uploadFile(InputStream inputStream, String fileName, String contentType) {
		try {
			ObjectMetadata meta = new ObjectMetadata();
			LOG.info("Iniciando upload");

			s3client.putObject(bucketName, fileName, inputStream, meta);
			LOG.info("Upload finalizando");

			return s3client.getUrl(bucketName, fileName).toURI();
		} catch (URISyntaxException e) {
			throw new FileException("Erro ao converter url para uri");
		}

	}

}
