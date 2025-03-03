package isika.p3.amappli.util;

import java.io.IOException;
import java.util.Base64;

import org.springframework.web.multipart.MultipartFile;

public class MultiPartFileToBase64 {
    
    public static String[] encodeMultiPartFile(MultipartFile file){
        String fileType = "";
        String fileData = "";
        if(!file.isEmpty()){
            fileType = file.getContentType();
            try {
                byte[] thebytes = file.getBytes();
				fileData = Base64.getEncoder().encodeToString(thebytes);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        return new String[] {fileType, fileData};
    }
}
