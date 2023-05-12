package ibf2022.batch2.csf.backend.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import ibf2022.batch2.csf.backend.models.Archive;
import ibf2022.batch2.csf.backend.repositories.ArchiveRepository;
import ibf2022.batch2.csf.backend.repositories.ImageRepository;
import jakarta.json.Json;
import jakarta.json.JsonObject;

@Controller
@RequestMapping
// @CrossOrigin(origins = "*")
public class UploadController {

	@Autowired
	private ImageRepository imageRepo;

	@Autowired
	private ArchiveRepository archiveRepo;

	// TODO: Task 2, Task 3, Task 4

	@PostMapping(path = "/upload")
    //     , consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> upload(@RequestPart MultipartFile archive, @RequestPart String name, @RequestPart String title,
		@RequestPart String comments) {

		String bundleId = "";
        String url = "";
        try {
			url = imageRepo.upload(archive, name, title, comments);
			bundleId = archiveRepo.recordBundle(name, title, comments, url);

			JsonObject payload = Json.createObjectBuilder()
				.add("bundleId", bundleId)
				.build();

			return ResponseEntity
			.status(HttpStatus.valueOf(201))
			.contentType(MediaType.APPLICATION_JSON)
			.body(payload.toString());

        } catch (IOException e) {

			JsonObject payload = Json.createObjectBuilder()
				.add("error", e.getMessage())
				.build();

            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
				.contentType(MediaType.APPLICATION_JSON)
                .body(payload.toString());
        }
		
    }

	// TODO: Task 5

	@GetMapping(path = "/bundle/{bundleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getBundle(@PathVariable String bundleId) {

        List<Archive> listOfArchives = archiveRepo.getBundleByBundleId(bundleId);

		if (!listOfArchives.isEmpty()) {

			return ResponseEntity
				.status(HttpStatus.OK)
				.contentType(MediaType.APPLICATION_JSON)
				.body(listOfArchives.get(0).toString());
		}
		return ResponseEntity
			.status(HttpStatus.NOT_FOUND)
			.contentType(MediaType.APPLICATION_JSON)
			.body("invalid bundleId");
    }

	// TODO: Task 6

	@GetMapping(path = "/bundles", produces = MediaType.APPLICATION_JSON_VALUE) 
    public ResponseEntity<String> getBundles(){

		List<Archive> listOfArchives = archiveRepo.getBundles();

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(listOfArchives.toString());
    }

}
