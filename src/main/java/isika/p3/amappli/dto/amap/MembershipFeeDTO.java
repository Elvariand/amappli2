package isika.p3.amappli.dto.amap;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MembershipFeeDTO {

    private Long id;
    private String info; // Description ou informations sur l'adhésion
    private double price; // Montant de l'adhésion
    private LocalDate dateBeginning; // Date de début de l'adhésion
    private LocalDate dateEnd; // Date de fin de l'adhésion
    private String status; // Statut ("Active", "Inactive", etc.)

}
