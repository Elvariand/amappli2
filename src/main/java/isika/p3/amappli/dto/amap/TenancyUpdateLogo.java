package isika.p3.amappli.dto.amap;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TenancyUpdateLogo {
    
    private String logoImgType;

    private String logoImg;

    private MultipartFile file;
}
