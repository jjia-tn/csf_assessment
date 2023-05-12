package ibf2022.batch2.csf.backend.services;

// import java.io.File;
// import java.io.FileInputStream;
// import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
// import java.util.LinkedList;
// import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UnzipService {
    
    public void unzip(MultipartFile file) {

        //buffer for read and write data to file
        // byte[] buffer = new byte[1024];
        try {
            InputStream is = file.getInputStream();
            ZipInputStream zis = new ZipInputStream(is);
            ZipEntry ze = zis.getNextEntry();
            while (ze != null) {

                // String fileName = ze.getName();
                // listOfFiles.add();
                
                //close this ZipEntry
                zis.closeEntry();
                ze = zis.getNextEntry();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }    
    }

}
