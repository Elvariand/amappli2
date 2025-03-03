package isika.p3.amappli.controllers.amap;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import isika.p3.amappli.dto.amap.ContentBlockDTO;
import isika.p3.amappli.dto.amap.TenancyUpdateAddressDTO;
import isika.p3.amappli.dto.amap.TenancyUpdateColorFontDTO;
import isika.p3.amappli.dto.amap.TenancyUpdateHomePageContentDTO;
import isika.p3.amappli.dto.amap.TenancyUpdateLogo;
import isika.p3.amappli.dto.amap.TenancyUpdateMemberShipFeePriceDTO;
import isika.p3.amappli.dto.amap.TenancyUpdateNameAliasDTO;
import isika.p3.amappli.dto.amap.TenancyUpdateOptionsDTO;
import isika.p3.amappli.dto.amap.TenancyUpdatePickUpDTO;
import isika.p3.amappli.dto.amap.TenancyUpdateSloganDTO;
import isika.p3.amappli.entities.tenancy.ColorPalette;
import isika.p3.amappli.entities.tenancy.ContentBlock;
import isika.p3.amappli.entities.tenancy.FontChoice;
import isika.p3.amappli.entities.tenancy.Tenancy;
import isika.p3.amappli.service.amap.GraphismService;
import isika.p3.amappli.service.amappli.TenancyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/amap")
public class TenancyEditController {
    
    private final TenancyService tenancyService;

    private final GraphismService graphismService;


    public TenancyEditController(TenancyService tenancyService, GraphismService graphismService) {
        this.tenancyService = tenancyService;
        this.graphismService = graphismService;
    }
    
	@PreAuthorize("hasAuthority('modification page accueil amap') and (hasAuthority(#alias) or hasAuthority('gestion plateforme'))")
    @GetMapping("/{tenancyAlias}/admin/edit")    
    public String editHomePageContent(@PathVariable("tenancyAlias") String alias, Model model) {

        graphismService.setUpModel(alias,model);
        Tenancy tenancy = tenancyService.getTenancyByAlias(alias);
        // Set existing info
        // Alias and Name
        TenancyUpdateNameAliasDTO updateNameAliasDTO = new TenancyUpdateNameAliasDTO();
        updateNameAliasDTO.setTenancyAlias(tenancy.getTenancyAlias());
        updateNameAliasDTO.setTenancyName(tenancy.getTenancyName());
        model.addAttribute("tenancyUpdateNameAliasDTO",updateNameAliasDTO);

        // Slogan
        TenancyUpdateSloganDTO tenancyUpdateSloganDTO = new TenancyUpdateSloganDTO();
        tenancyUpdateSloganDTO.setSlogan(tenancy.getTenancySlogan());
        model.addAttribute("tenancyUpdateSloganDTO", tenancyUpdateSloganDTO);

        // Logo
        TenancyUpdateLogo tenancyUpdateLogo = new TenancyUpdateLogo();
        tenancyUpdateLogo.setLogoImg(tenancy.getGraphism().getLogoImg());
        tenancyUpdateLogo.setLogoImgType(tenancy.getGraphism().getLogoImgType());
        model.addAttribute("tenancyUpdateLogo", tenancyUpdateLogo);

        // Address
        TenancyUpdateAddressDTO tenancyUpdateAddressDTO = new TenancyUpdateAddressDTO();
        tenancyUpdateAddressDTO.setAddress(tenancy.getAddress());
        model.addAttribute("tenancyUpdateAddressDTO",tenancyUpdateAddressDTO);
        
        // PickUpSchedule
        TenancyUpdatePickUpDTO tenancyUpdatePickUpDTO = new TenancyUpdatePickUpDTO();
        tenancyUpdatePickUpDTO.setPickUpSchedule(tenancy.getPickUpSchedule());
        model.addAttribute("tenancyUpdatePickUpDTO", tenancyUpdatePickUpDTO);

        // Font and Palette
        TenancyUpdateColorFontDTO tenancyUpdateColorFontDTO = new TenancyUpdateColorFontDTO();
        tenancyUpdateColorFontDTO.setColorPalette(tenancy.getGraphism().getColorPalette());
        tenancyUpdateColorFontDTO.setFontChoice(tenancy.getGraphism().getFontChoice());
        // Give the color palettes options for the form
        model.addAttribute("colorPalettes",ColorPalette.values());
        model.addAttribute("fontChoices", FontChoice.values());
        
        model.addAttribute("tenancyUpdateColorFontDTO",tenancyUpdateColorFontDTO);

        // HomePageContent
        List<ContentBlock> allContents = tenancy.getHomePageContent().getContents();
        // Separate values from other contents block
        Map<Boolean, List<ContentBlock>> sortedContents = allContents.stream().collect(Collectors.partitioningBy(cb -> cb.isValue()));
        List<ContentBlock> contents = sortedContents.get(false);
        List<ContentBlock> values = sortedContents.get(true);

        TenancyUpdateHomePageContentDTO tenancyUpdateHomePageContentDTO = new TenancyUpdateHomePageContentDTO();
        List<ContentBlockDTO> contentsDTO = contents.stream().map(
                                                cb -> {
                                                   ContentBlockDTO cbDTO = new ContentBlockDTO();
                                                   BeanUtils.copyProperties(cb, cbDTO);
                                                   return cbDTO;
                                                }
                                            ).collect(Collectors.toList());
        tenancyUpdateHomePageContentDTO.setContents(contentsDTO);
        model.addAttribute("tenancyUpdateHomePageContentDTO", tenancyUpdateHomePageContentDTO);
        
        TenancyUpdateHomePageContentDTO tenancyUpdateValuesDTO = new TenancyUpdateHomePageContentDTO();
        List<ContentBlockDTO> valuesDTO = values.stream().map(
                cb -> {
                ContentBlockDTO cbDTO = new ContentBlockDTO();
                BeanUtils.copyProperties(cb, cbDTO);
                return cbDTO;
                }
            ).collect(Collectors.toList());
        tenancyUpdateValuesDTO.setContents(valuesDTO);
        model.addAttribute("tenancyUpdateValuesDTO", tenancyUpdateValuesDTO);

        // Membership Fee Price
        TenancyUpdateMemberShipFeePriceDTO tenancyUpdateMemberShipFeePriceDTO = new TenancyUpdateMemberShipFeePriceDTO();
        tenancyUpdateMemberShipFeePriceDTO.setMembershipFeePrice(tenancy.getMembershipFeePrice());
        model.addAttribute("tenancyMembershipDTO", tenancyUpdateMemberShipFeePriceDTO);

        // Amappli subscription
        TenancyUpdateOptionsDTO tenancyUpdateOptionsDTO = new TenancyUpdateOptionsDTO();
        if(tenancy.getOptions().getOption1Active() && tenancy.getOptions().getOption2Active()){
            tenancyUpdateOptionsDTO.setCurrentSubscription("Ferme à 100€/an");
        }
        else if (tenancy.getOptions().getOption1Active()){
            tenancyUpdateOptionsDTO.setCurrentSubscription("Verger à 50€/an");
        }
        else{
            tenancyUpdateOptionsDTO.setCurrentSubscription("Potager à 0€/an");
        }
        model.addAttribute("tenancyUpdateOptionsDTO", tenancyUpdateOptionsDTO);

        return "/amap/back/edit/edithomepage";
    }

    @PreAuthorize("hasAuthority('modification page accueil amap') and (hasAuthority(#alias) or hasAuthority('gestion plateforme'))")
    @PostMapping("/{tenancyAlias}/admin/editthenameandalias")    
    public String editNameAlias(TenancyUpdateNameAliasDTO tenancyDTO, @PathVariable("tenancyAlias") String alias, Model model) {

        tenancyService.updateTenancyNameOrAlias(tenancyDTO,alias);

        // different redirect if tenancy alias has changed
        if(!tenancyDTO.getTenancyAlias().equals(alias)) {
            return "redirect:/amap/"+ tenancyDTO.getTenancyAlias() + "/admin/edit";
        }
        return "redirect:/amap/"+ alias + "/admin/edit";
    }

    @PreAuthorize("hasAuthority('modification page accueil amap') and (hasAuthority(#alias) or hasAuthority('gestion plateforme'))")
    @PostMapping("/{tenancyAlias}/admin/edittheslogan")
    public String editSlogan(TenancyUpdateSloganDTO tenancyDTO, @PathVariable("tenancyAlias") String alias, Model model){
        tenancyService.updateTenancySlogan(tenancyDTO, alias);
        return "redirect:/amap/"+ alias + "/admin/edit";
    }

    @PreAuthorize("hasAuthority('modification page accueil amap') and (hasAuthority(#alias) or hasAuthority('gestion plateforme'))")
    @PostMapping("/{tenancyAlias}/admin/editthelogo")
    public String editLogo(TenancyUpdateLogo tenancyDTO, @PathVariable("tenancyAlias") String alias, Model model){
        tenancyService.updateTenancyLogo(tenancyDTO, alias);
        return "redirect:/amap/"+ alias + "/admin/edit";
    }

    @PreAuthorize("hasAuthority('modification page accueil amap') and (hasAuthority(#alias) or hasAuthority('gestion plateforme'))")
    @PostMapping("/{tenancyAlias}/admin/edittheaddress")
    public String editLogo(TenancyUpdateAddressDTO tenancyDTO, @PathVariable("tenancyAlias") String alias, Model model){
        tenancyService.updateTenancyAddress(tenancyDTO, alias);
        return "redirect:/amap/"+ alias + "/admin/edit";
    }

    @PreAuthorize("hasAuthority('modification page accueil amap') and (hasAuthority(#alias) or hasAuthority('gestion plateforme'))")
    @PostMapping("/{tenancyAlias}/admin/editthepickupschedule")
    public String editPickUp(TenancyUpdatePickUpDTO tenancyDTO, @PathVariable("tenancyAlias") String alias, Model model){
        tenancyService.updateTenancyPickUpSchedule(tenancyDTO, alias);
        return "redirect:/amap/"+ alias + "/admin/edit";
    }

    @PreAuthorize("hasAuthority('modification page accueil amap') and (hasAuthority(#alias) or hasAuthority('gestion plateforme'))")
    @PostMapping("/{tenancyAlias}/admin/editthefontandcolor")
    public String editFontColor(TenancyUpdateColorFontDTO tenancyDTO, @PathVariable("tenancyAlias") String alias, Model model){
        tenancyService.updateTenancyColorFont(tenancyDTO, alias);
        return "redirect:/amap/"+ alias + "/admin/edit";
    }

    @PreAuthorize("hasAuthority('modification page accueil amap') and (hasAuthority(#alias) or hasAuthority('gestion plateforme'))")
    @PostMapping("/{tenancyAlias}/admin/editthecontents")
    public String editContents(TenancyUpdateHomePageContentDTO tenancyDTO, @PathVariable("tenancyAlias") String alias, Model model){
        tenancyService.updateTenancyHomePageContent(tenancyDTO, alias);
        return "redirect:/amap/"+ alias + "/admin/edit";
    }

    @PreAuthorize("hasAuthority('modification page accueil amap') and (hasAuthority(#alias) or hasAuthority('gestion plateforme'))")
    @PostMapping("/{tenancyAlias}/admin/editthevalues")
    public String editValues(TenancyUpdateHomePageContentDTO tenancyDTO, @PathVariable("tenancyAlias") String alias, Model model){
        tenancyService.updateTenancyValues(tenancyDTO, alias);
        return "redirect:/amap/"+ alias + "/admin/edit";
    }

    @PreAuthorize("hasAuthority('modification page accueil amap') and (hasAuthority(#alias) or hasAuthority('gestion plateforme'))")
    @PostMapping("/{tenancyAlias}/admin/editthemembershipfee")
    public String editMemberShip(TenancyUpdateMemberShipFeePriceDTO tenancyDTO, @PathVariable("tenancyAlias") String alias, Model model){
        tenancyService.updateTenancyMemberShipFeePrice(tenancyDTO, alias);
        return "redirect:/amap/"+ alias + "/admin/edit";
    }

    @PreAuthorize("hasAuthority('modification page accueil amap') and (hasAuthority(#alias) or hasAuthority('gestion plateforme'))")
    @PostMapping("/{tenancyAlias}/admin/edittheoptions")
    public String editOptions(TenancyUpdateOptionsDTO tenancyDTO, @PathVariable("tenancyAlias") String alias, Model model){
        tenancyService.updateTenancyOptions(tenancyDTO, alias);
        return "redirect:/amap/"+ alias + "/admin/edit";
    }
}
