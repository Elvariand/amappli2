package isika.p3.amappli.dto.amappli;

import java.util.List;
import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

import isika.p3.amappli.entities.tenancy.ColorPalette;
import isika.p3.amappli.entities.tenancy.FontChoice;
import isika.p3.amappli.entities.tenancy.PickUpSchedule;
import isika.p3.amappli.entities.user.Address;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NewTenancyDTO {
    
    @NotBlank
    private String tenancyName;

    @NotBlank
    private String tenancyAlias;

    @NotBlank
    private String tenancySlogan;

    private BigDecimal membershipFeePrice;

    @Valid
    private Address address;

    private PickUpSchedule pickUpSchedule;

    private List<ValueDTO> values;

    private ColorPalette colorPalette;

    private MultipartFile logo;

    private String firstHomePageTitle;

    private String firstHomePageText;

    private MultipartFile firstHomePagePic;

    private String option;

    private FontChoice fontChoice;

}
