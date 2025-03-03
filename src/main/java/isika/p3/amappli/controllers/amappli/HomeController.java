package isika.p3.amappli.controllers.amappli;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/amappli")
public class HomeController {

	@GetMapping()
	public String home(Model model) {
	 	return "amappli/home";
	}
	
	@GetMapping("/home")
	public String home2(Model model) {
		return "amappli/home";
	}
	

	@GetMapping("/contact")
	public String contact() {
	    return "amappli/front/contactAmappli"; 
	}
	
	@GetMapping("/about")
	public String aboutUs() {
	    return "amappli/front/aboutAmappli"; 
	}
	
	@GetMapping("/features")
	public String getfeatures() {
	    return "amappli/front/featuresPage"; 
	}
	
	@GetMapping("/legal")
	public String legalDisclaimer() {
		return "amappli/front/legal/legalPage"; 
	}
	
	@GetMapping("/gcu")
	public String gcu() {
		return "amappli/front/legal/gcu"; 
	}

}
