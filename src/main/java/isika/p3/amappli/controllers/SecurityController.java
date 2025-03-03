package isika.p3.amappli.controllers;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import isika.p3.amappli.entities.tenancy.ColorPalette;
import isika.p3.amappli.entities.tenancy.FontChoice;
import isika.p3.amappli.security.CustomUserDetails;
import isika.p3.amappli.service.amap.GraphismService;

@Controller
public class SecurityController {
    
    private final GraphismService graphismService;


    public SecurityController(GraphismService graphismService) {
        this.graphismService = graphismService;
    }
  

    @GetMapping("/amappli/login")
    public String goToLogin(@RequestParam(name = "error", required = false) String error,Model model){
        
        if (error != null) {
            model.addAttribute("error", "Email ou mot de passe incorrect.");
        }

        model.addAttribute("mapStyleLight", "mapbox://styles/tiroirmorgane/cm4sw37wr001301s12frm2l2y");
        model.addAttribute("mapStyleDark", "mapbox://styles/tiroirmorgane/cm52cqefg003101sa878udky6");
        model.addAttribute("cssStyle", ColorPalette.PALETTE1);
        model.addAttribute("font", FontChoice.FUTURA);

        return "amappli/front/amapplilogin";
    }

    @GetMapping("/amap/{tenancyAlias}/login")
    public String goToTenancyLogin(@RequestParam(name = "error", required = false) String error, @PathVariable("tenancyAlias") String alias, Model model) {

        model.addAttribute("tenancyAlias", alias);
        if (error != null) {
            model.addAttribute("error", "Email ou mot de passe incorrect.");
        }
       
        graphismService.setUpModel(alias, model);

        return "amap/amaplogin/login";
    }

    @GetMapping("/showpermissions")
    public String showLoggedUserPermissions(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails loggedUserInfo = (CustomUserDetails) authentication.getPrincipal();

        System.out.println(authentication.getPrincipal().toString());
        model.addAttribute("principal", authentication.getPrincipal());

        model.addAttribute("userId", loggedUserInfo.getAdditionalInfoByKey("userId"));
        model.addAttribute("firstName", loggedUserInfo.getAdditionalInfoByKey("firstName"));
        
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        model.addAttribute("authorities", authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        return "secexamples/showloggeduserpermissions"; 
    }

    @GetMapping("/amap/{tenancyAlias}/forbidden")
    public String forbidden(@PathVariable("tenancyAlias") String alias, Model model){
        graphismService.setUpModel(alias, model);

        return "amap/front/error/access-denied";
    }
}
