package isika.p3.amappli.dto.amappli;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ValueDTO {
    
    private String name;

    private String description;

    private MultipartFile file;

}
