package com.ssafy.farmily.service.file;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.farmily.dto.ImageDto;
import com.ssafy.farmily.repository.ImageRepository;

@Service
public class LocalFileService extends AbstractFileService {

	@Value("${file-server.local.path}")
	private final String path = null;

	public LocalFileService(ImageRepository imageRepository) {
		super(imageRepository);
	}

	@Override
	public ImageDto uploadImage(MultipartFile file) {
		try {
			File directory = new File(path);
			if (!directory.isDirectory()) {
				directory.mkdirs();
			}
			String originalFileName = file.getOriginalFilename();
			String newFileName = UUID.randomUUID().toString();
			String extension = StringUtils.getFilenameExtension(originalFileName);
			String newPath = path + File.separator + newFileName + "." + extension;

			File newFile = new File(newPath);
			file.transferTo(newFile);

			return ImageDto.of(newPath, originalFileName);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}

	}
}
