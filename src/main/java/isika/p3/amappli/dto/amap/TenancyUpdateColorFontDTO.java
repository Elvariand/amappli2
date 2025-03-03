package isika.p3.amappli.dto.amap;

import isika.p3.amappli.entities.tenancy.ColorPalette;
import isika.p3.amappli.entities.tenancy.FontChoice;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TenancyUpdateColorFontDTO {
    
    private FontChoice fontChoice;
    private ColorPalette colorPalette;

}
