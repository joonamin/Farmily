package com.ssafy.farmily.service.file;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.farmily.entity.Image;

public interface FileService {

	Image saveImage(MultipartFile file);

	List<Image> saveImages(Collection<MultipartFile> files);

	Image connectImage(URI uri) throws URISyntaxException;

	Image connectImage(String uri) throws URISyntaxException;
}
