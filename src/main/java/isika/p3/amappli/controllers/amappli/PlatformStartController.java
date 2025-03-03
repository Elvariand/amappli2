package isika.p3.amappli.controllers.amappli;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import isika.p3.amappli.dto.amap.NewUserDTO;
import isika.p3.amappli.dto.amappli.NewTenancyDTO;
import isika.p3.amappli.dto.amappli.ValueDTO;
import isika.p3.amappli.entities.tenancy.ColorPalette;
//import isika.p3.amappli.entities.tenancy.ContentBlock;
import isika.p3.amappli.entities.tenancy.FontChoice;
import isika.p3.amappli.entities.tenancy.Tenancy;
import isika.p3.amappli.exceptions.EmailAlreadyExistsException;
import isika.p3.amappli.exceptions.TenancyAliasAlreadyTakenException;
import isika.p3.amappli.security.CustomUserDetails;
import isika.p3.amappli.service.amap.ContentBlockService;
import isika.p3.amappli.service.amap.UserService;
import isika.p3.amappli.service.amappli.TenancyService;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/amappli/start/")
public class PlatformStartController {

    private final UserService userService;
    private final TenancyService tenancyService;
   // private final ContentBlockService contentBlockService;

    public PlatformStartController(UserService userService, TenancyService tenancyService, ContentBlockService contentBlockService) {
        this.userService = userService;
        this.tenancyService = tenancyService;
       // this.contentBlockService = contentBlockService;
    }
    

    @GetMapping("/signup")    
    public String plateformUserSignUpForm(Model model){
        NewUserDTO newUserDTO = new NewUserDTO();
        model.addAttribute("newUserDTO",newUserDTO);
        return "amappli/front/platformstart/signup";
    }

    @PostMapping("/signup")
    public String plateformUserSignup(@Valid @ModelAttribute NewUserDTO newUserDTO, BindingResult result, Model model){
        if(result.hasErrors()){
            return "amappli/front/platformstart/signup";
        }
        
        // Write user in DB
        try {
            userService.addPlatformUser(newUserDTO);
        }
        catch (EmailAlreadyExistsException e)
        {
            model.addAttribute("emailError", e.getMessage());
            return "amappli/front/platformstart/signup";
        }
        return "redirect:/amappli/login";
    }

    @PreAuthorize("hasAuthority('modification page accueil amap')")
    @GetMapping("/creation")
    public String createTenancy(Model model) {

        // Get logged user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails loggedUserInfo = (CustomUserDetails) authentication.getPrincipal();

        String tenancyAlias = (String) loggedUserInfo.getAdditionalInfoByKey("tenancyAlias");
        if(tenancyAlias != null){
            return "redirect:/amap/" + tenancyAlias + "/home";
        }

        model.addAttribute("errorspresent", false);
        NewTenancyDTO newTenancyDTO = new NewTenancyDTO();
        // Three possibles values
        List<ValueDTO> values = new ArrayList<ValueDTO>();
        values.add(new ValueDTO());
        values.add(new ValueDTO());
        values.add(new ValueDTO());
        newTenancyDTO.setValues(values);
        model.addAttribute("newTenancyDTO",newTenancyDTO);
        // Give the color palettes and font options for the form
        model.addAttribute("colorPalettes",ColorPalette.values());
        model.addAttribute("fontChoices", FontChoice.values());
        return "amappli/front/platformstart/createtenancy";
    }

    @PreAuthorize("hasAuthority('modification page accueil amap')")
    @PostMapping("/creation")
    public String tenancyCreation(@Valid @ModelAttribute("newTenancyDTO") NewTenancyDTO newTenancyDTO, BindingResult result, Model model) {
        
        // Get logged user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails loggedUserInfo = (CustomUserDetails) authentication.getPrincipal();

        model.addAttribute("colorPalettes",ColorPalette.values());
        model.addAttribute("fontChoices", FontChoice.values());
        System.out.println(newTenancyDTO.getTenancyAlias());
        System.out.println("BindingResult has errors: " + result.hasErrors());
        if(result.hasErrors()){
            result.getAllErrors().forEach(error -> System.out.println(error));
            model.addAttribute("errorspresent", true);
            return "amappli/front/platformstart/createtenancy";
        }
        // Write tenancy to DB
        try {
            Tenancy tenancy = tenancyService.createTenancyFromWelcomeForm(newTenancyDTO, (Long) loggedUserInfo.getAdditionalInfoByKey("userId"));
            return "redirect:/amap/"+tenancy.getTenancyAlias()+"/home";
        }
        catch (TenancyAliasAlreadyTakenException e){
            model.addAttribute("aliasError", e.getMessage());
            model.addAttribute("errorspresent", true);
            return "amappli/front/platformstart/createtenancy";
        }
    }
    
}
