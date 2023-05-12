package ibf2022.batch2.csf.backend.repositories;

// import java.io.File;
// import java.io.FileInputStream;
import java.io.IOException;
// import java.util.HashMap;
// import java.util.Map;
// import java.util.StringTokenizer;
// import java.util.UUID;

// import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
// import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
// import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
// import com.amazonaws.services.s3.model.S3Object;

// import ibf2022.batch2.csf.backend.services.UnzipService;

@Repository
public class ImageRepository {

	@Autowired
	private AmazonS3 s3Client;

	// @Autowired
	// private UnzipService unzipSvc;

	// @Value("${DO_STORAGE_BUCKETNAME}")
    private String bucketName = "tjjibf22022";

	//TODO: Task 3
	// You are free to change the parameter and the return type
	// Do not change the method's name

	// public Object upload(/* any number of parameters here */) {
	// 	return null;
	// }

    public String upload(MultipartFile file, String name, String title, String comments) throws IOException {

        // Map<String, String> userData = new HashMap<>();
        // userData.put("uploadDateTime", LocalDateTime.now().toString());
        // userData.put("originalFilename", file.getOriginalFilename());
        // // userData.put("comments", comments);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        // metadata.setUserMetadata(userData);

        // String key = UUID.randomUUID().toString().substring(0, 8);
        // StringTokenizer tk = new StringTokenizer(file.getOriginalFilename(), ".");
        // int count = 0;
        // String filenameExt = "";
        // while (tk.hasMoreTokens()) {
        //     if (count == 1) {
        //         filenameExt = tk.nextToken();
        //         break;
        //     } else {
        //         filenameExt = tk.nextToken();
        //         count++;
        //     }
        // }
        // if (filenameExt.equals("blob"))
        //     filenameExt = "png";

        PutObjectRequest putRequest = new PutObjectRequest(
            bucketName, "csf_assessment/%s".formatted(file.getOriginalFilename()), file.getInputStream(), metadata);
        putRequest = putRequest.withCannedAcl(CannedAccessControlList.PublicRead);
        s3Client.putObject(putRequest);

		String url = "https://tjjibf22022.sgp1.digitaloceanspaces.com/csf_assessment/" + file.getOriginalFilename();

		return url;
    }

    // public S3Object getObject(String key) {

    //     key = "myobject%s.png".formatted(key);
    //     GetObjectRequest getReq = new GetObjectRequest(bucketName, key);
    //     S3Object result = s3Client.getObject(getReq);

    //     return result;
    // }
}
