package isika.p3.amappli.service.amap.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import isika.p3.amappli.entities.tenancy.Graphism;
import isika.p3.amappli.entities.tenancy.Options;
import isika.p3.amappli.entities.tenancy.PickUpSchedule;
import isika.p3.amappli.entities.tenancy.Tenancy;
import isika.p3.amappli.entities.user.Address;
import isika.p3.amappli.repo.amappli.TenancyRepository;
import isika.p3.amappli.security.CustomUserDetails;
import isika.p3.amappli.service.amap.GraphismService;

@Service
public class GraphismServiceImpl implements GraphismService {

	@Autowired
	private TenancyRepository tenancyRepo;
	

	public void setUpModel(String alias, Model model) {
		Tenancy tenancy = getTenancyByAlias(alias);
		addTenancyInfos(tenancy, model);
		addGraphismAttributes(tenancy, model);
		addNextPickUpDateToModel(model, tenancy);
		getUserInfoFromContext(model);
	}
	
	public void addNextPickUpDateToModel(Model model, Tenancy tenancy) {
	    Optional<PickUpSchedule> pickUpSchedule = Optional.ofNullable(tenancy.getPickUpSchedule());
	    if (pickUpSchedule.isPresent()) {
	        LocalDateTime[] nextPickUp = pickUpSchedule.get().getNextPickUpLocalDateTimes();
	        LocalDateTime nextDeliveryDate = nextPickUp[0];

	        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("'Le' EEEE dd MMMM yyyy", Locale.FRENCH);
	        String formattedNextDeliveryDate = nextDeliveryDate.format(formatter1);

	        model.addAttribute("nextPickUpDate", formattedNextDeliveryDate);
	    } else {
	        model.addAttribute("nextPickUpDate", "Non disponible");
	    }
    }
	
	public void addTenancyInfos(Tenancy tenancy, Model model) {
		// add tenancy infos
		model.addAttribute("tenancy", tenancy);
        model.addAttribute("tenancyName", tenancy.getTenancyName());
        model.addAttribute("tenancySlogan", tenancy.getTenancySlogan());
        model.addAttribute("options", tenancy.getOptions() != null ? tenancy.getOptions() : Options.builder().option1Active(false).option2Active(false).build());
        // add tenancy address
        Address address = tenancy.getAddress();
        model.addAttribute("addressLine1", address != null ? address.getLine1() : null);
        model.addAttribute("addressLine2", address != null ? address.getLine2() : null);
        model.addAttribute("addressPostCode", address != null ? address.getPostCode() : null);
        model.addAttribute("addressCity", address != null ? address.getCity() : null);
        model.addAttribute("email", tenancy.getEmail());
        model.addAttribute("phoneNumber", tenancy.getContactInfo() != null ? tenancy.getContactInfo().getPhoneNumber() : null);
       //add tenancy logo
        Graphism graphism = tenancy.getGraphism();
        String logoBase64 = graphism != null ? graphism.getLogoImg() : null;
        String logoImgType = graphism != null ? graphism.getLogoImgType() : null;
        model.addAttribute("logoBase64", logoBase64);
        model.addAttribute("logoImgType", logoImgType);
	}
	
	public void  addGraphismAttributes(Tenancy tenancy, Model model) {
		// set up model with all basic necessary informations concerning tenancy and graphism
		model.addAttribute("mapStyleLight", getMapStyleLightByTenancyAlias(tenancy));
		model.addAttribute("mapStyleDark", getMapStyleDarkByTenancyAlias(tenancy));
		model.addAttribute("latitude", getLatitudeByTenancyAlias(tenancy));
		model.addAttribute("longitude", getLongitudeByTenancyAlias(tenancy));
		
		model.addAttribute("tenancy", tenancy);
		
		model.addAttribute("cssStyle", getColorPaletteByTenancyAlias(tenancy));
		
		model.addAttribute("font", getFontByTenancyAlias(tenancy));
	}
	
	public Long getUserIdFromContext() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();
		//condition for when we don't want to login at each dev iteration
		if (principal instanceof CustomUserDetails) {
			CustomUserDetails loggedUserInfo = (CustomUserDetails) principal;
			return (Long) loggedUserInfo.getAdditionalInfoByKey("userId");
		} else return 1L;
	}
	
	public String getMapStyleLightByTenancyAlias(Tenancy tenancy) {
		if (tenancy == null || tenancy.getGraphism() == null) {
			return "mapbox://styles/tiroirmorgane/cm4sw37wr001301s12frm2l2y"; // theme1 white by default
		}

		String colorPalette = tenancy.getGraphism().getColorPalette().toString();

		switch (colorPalette) {
		case "PALETTE1":
			return "mapbox://styles/tiroirmorgane/cm4sw37wr001301s12frm2l2y"; // theme1 white
		case "PALETTE2":
			return "mapbox://styles/tiroirmorgane/cm52ch9kt00ch01r13h25gm01"; // theme2 white
		case "PALETTE3":
			return "mapbox://styles/tiroirmorgane/cm52cizgb00c201s94zpr3c3w"; // theme3 white
		case "PALETTE4":
			return "mapbox://styles/tiroirmorgane/cm52cjqtu002z01sa8typ15f6"; // theme4 white
		case "PALETTE5":
			return "mapbox://styles/tiroirmorgane/cm52claak00dh01qyb016bm90"; // theme5 white
		case "PALETTE6":
			return "mapbox://styles/tiroirmorgane/cm52dmxt500c401s9ckayancx"; // theme6 white
		default:
			return "mapbox://styles/tiroirmorgane/cm4sw37wr001301s12frm2l2y"; // theme1 white by default
		}
	}

	public String getMapStyleDarkByTenancyAlias(Tenancy tenancy) {
		if (tenancy == null || tenancy.getGraphism() == null) {
			return "mapbox://styles/tiroirmorgane/cm52cqefg003101sa878udky6"; // theme1 dark by default
		}

		String colorPalette = tenancy.getGraphism().getColorPalette().toString();

		switch (colorPalette) {
		case "PALETTE1":
			return "mapbox://styles/tiroirmorgane/cm52cqefg003101sa878udky6"; // theme1 dark
		case "PALETTE2":
			return "mapbox://styles/tiroirmorgane/cm52cuoxd00c101sa4gdl1bye"; // theme2 dark
		case "PALETTE3":
			return "mapbox://styles/tiroirmorgane/cm52cw61a00cl01qohw2ifo3i"; // theme3 dark
		case "PALETTE4":
			return "mapbox://styles/tiroirmorgane/cm52cxc4o00cm01qo3eei2j9x"; // theme4 dark
		case "PALETTE5":
			return "mapbox://styles/tiroirmorgane/cm52cyg8t00c301s95paxgbyb"; // theme5 dark
		case "PALETTE6":
			return "mapbox://styles/tiroirmorgane/cm52czmoi00ci01r1g3po4h2b"; // theme6 dark
		default:
			return "mapbox://styles/tiroirmorgane/cm52cqefg003101sa878udky6"; // theme1 dark by default
		}
	}

	@Override
	public Tenancy getTenancyByAlias(String alias) {
		return tenancyRepo.findByTenancyAlias(alias).orElse(null);
	}

	public String getColorPaletteByTenancyAlias(Tenancy tenancy) {
		if (tenancy == null || tenancy.getGraphism() == null) {
			return "theme-1";
		}

		String colorPalette = tenancy.getGraphism().getColorPalette().toString();

		switch (colorPalette) {
		case "PALETTE1":
			return "theme-1";
		case "PALETTE2":
			return "theme-2";
		case "PALETTE3":
			return "theme-3";
		case "PALETTE4":
			return "theme-4";
		case "PALETTE5":
			return "theme-5";
		case "PALETTE6":
			return "theme-6";
		default:
			return "theme-1";
		}
	}

	public String getFontByTenancyAlias(Tenancy tenancy) {
		if (tenancy == null || tenancy.getGraphism() == null) {
			return "futura";
		}
		
		String fontChoice = tenancy.getGraphism().getFontChoice().toString();
		switch (fontChoice) {
		case "FUTURA":
			return "futura";
		case "NUNITO":
			return "nunito-sans";
		case "AUDIOWIDE":
			return "audiowide";
		case "GRENZE":
			return "grenze";
		case "CAVEAT":
			return "caveat";
		case "LIMELIGHT":
			return "limelight";
		default:
			return "futura";
		}
	}
	
	public String getLatitudeByTenancyAlias(Tenancy tenancy) {
		if (tenancy == null || tenancy.getGraphism() == null) {
			return "42.1880896";
		} else return tenancy.getTenancyLatitude();
	}
	
	public String getLongitudeByTenancyAlias(Tenancy tenancy) {
		if (tenancy == null || tenancy.getGraphism() == null) {
			return "9.0684138";
		} else return tenancy.getTenancyLongitude();
	}
	
	public void getUserInfoFromContext(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();
		if (principal instanceof CustomUserDetails) {
			CustomUserDetails loggedUserInfo = (CustomUserDetails) principal;
			model.addAttribute("permissions", loggedUserInfo.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
			model.addAttribute("loggedUserFirstName",loggedUserInfo.getAdditionalInfoByKey("firstName"));
		}
	}
	
}
