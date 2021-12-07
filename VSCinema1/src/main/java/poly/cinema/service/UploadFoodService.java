package poly.cinema.service;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

public interface UploadFoodService {

	File save(MultipartFile file, String folder);

}