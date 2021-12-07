package poly.cinema.rest.controller;

import java.io.File;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import poly.cinema.service.UploadService;

@CrossOrigin("*")
@RestController
public class UploadRestController {
	@Autowired
	UploadService upservice ;
	
	@GetMapping("/rest/test/{image}")
	public Boolean testimage(@PathVariable("image") String image) {		
		Boolean test = upservice.test(image);
		return test;
	} 
	
	@PostMapping("/rest/upload/{folder}")
	public JsonNode uploadimage (@PathParam("file") MultipartFile file , @PathVariable("folder") String folder) {
		File savefile = upservice.save(file, folder);
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		node.put("name", savefile.getName());
		node.put("size", savefile.length());
		return node ;
	}

}