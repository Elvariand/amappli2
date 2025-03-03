package isika.p3.amappli.dto.amappli;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RoleDTO {

    private Long roleId;
    private String roleName;
    //We transfer permissions ids not the full objects
    private List<Long> permissions = new ArrayList<Long>();

}
