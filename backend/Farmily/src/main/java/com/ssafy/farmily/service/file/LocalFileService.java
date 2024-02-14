package com.ssafy.farmily.service.file;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.farmily.dto.ImageDto;
import com.ssafy.farmily.repository.ImageRepository;

import lombok.extern.log4j.Log4j;

@Log4j
@Service
public class LocalFileService extends AbstractFileService {

	@Value("${file-server.local.dir-path}")
	private final String dirPath = null;

	@Value("${file-server.local.server-url}")
	private final String serverUrl = null;

	private Logger logger = LoggerFactory.getLogger(LocalFileService.class);

	public LocalFileService(ImageRepository imageRepository) {
		super(imageRepository);
	}

	@Override
	public ImageDto uploadImage(MultipartFile file) {
		try {
			File directory = new File(dirPath);
			if (!directory.isDirectory()) {
				directory.mkdirs();
			}
			String originalFileName = file.getOriginalFilename();
			String newFileName = UUID.randomUUID().toString();
			String extension = StringUtils.getFilenameExtension(originalFileName);
			String newPath = dirPath + File.separator + newFileName + "." + extension;
			String uri = serverUrl + "/" + newFileName + "." + extension;

			File newFile = new File(newPath);
			file.transferTo(newFile);

			logger.info("파일 업로드 성공: {} -> {}", originalFileName, uri);

			return ImageDto.of(uri, originalFileName);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}

	}
}
