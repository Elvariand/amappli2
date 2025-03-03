package isika.p3.amappli.dto.amap;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;


@Getter @Setter
public class ContentBlockDTO {
    
    private Long contentBlockId;

    private String contentTitle;

    private String contentText;

     private String contentImgName;

    private String contentImgTypeMIME;

    private String contentImg;

    private MultipartFile image;
}
