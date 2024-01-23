package com.ssafy.farmily.service.file;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.farmily.entity.Image;

public interface FileService {

	Image saveImage(MultipartFile file);

	default List<Image> saveImages(Collection<MultipartFile> files) {
		return files.stream()
			.map(this::saveImage)
			.toList();
	}

	Image connectImage(URI uri);

	default Image connectImage(String uri) throws URISyntaxException {
		return connectImage(new URI(uri));
	}
}
