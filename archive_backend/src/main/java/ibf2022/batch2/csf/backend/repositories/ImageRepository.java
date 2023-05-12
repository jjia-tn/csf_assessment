package ibf2022.batch2.csf.backend.repositories;

import java.io.IOException;

// import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

// import ibf2022.batch2.csf.backend.services.UnzipService;

@Repository
public class ImageRepository {

	@Autowired
	private AmazonS3 s3Client;

	// @Autowired
	// private UnzipService unzipSvc;

	@Value("${DO_STORAGE_BUCKETNAME}")
    private String bucketName;

	// @Value("{DO_STORAGE_URL}")
	// private String url;

	//TODO: Task 3
	// You are free to change the parameter and the return type
	// Do not change the method's name

	// public Object upload(/* any number of parameters here */) {
	// 	return null;
	// }

    public String upload(MultipartFile file, String name, String title, String comments) throws IOException {

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        // metadata.setUserMetadata(userData);

        PutObjectRequest putRequest = new PutObjectRequest(
            bucketName, "csf_assessment/%s".formatted(file.getOriginalFilename()), file.getInputStream(), metadata);
        putRequest = putRequest.withCannedAcl(CannedAccessControlList.PublicRead);
        s3Client.putObject(putRequest);

		String url = "https://tjjibf22022.sgp1.digitaloceanspaces.com/csf_assessment/" + file.getOriginalFilename();

		return url;
    }

}
