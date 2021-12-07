package poly.cinema.service.impl;

import java.io.File;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import poly.cinema.service.UploadMovieService;
@Service
public class UploadMovieImpl implements UploadMovieService {
	@Autowired
	ServletContext app ;

	@Override
	public File save(MultipartFile file, String folder) {
		File dir = new File(app.getRealPath("/assets/images/image/movies/"+folder));
		if(!dir.exists()) {
			dir.mkdirs();
		}
		String s = System.currentTimeMillis() + file.getOriginalFilename();
		String name  = Integer.toHexString(s.hashCode()) + s.substring(s.lastIndexOf("."));
		
		try {
			File saveFile = new File(dir ,name);
			file.transferTo(saveFile);
			System.out.println(dir.getAbsolutePath());
			return saveFile;
		} catch (Exception e) {
			throw new RuntimeException(e);
 		}
	}
	
	
	@Override
	public Boolean test(String image) {
		File dir = new File(app.getRealPath("/assets/dist/img/" + image));
		
		System.out.println(dir.exists());
		return dir.exists();
	}
}