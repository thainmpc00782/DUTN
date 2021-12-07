package poly.cinema.service;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

public interface UploadMovieService {
	File save(MultipartFile file, String folder);
	Boolean test(String image);
}