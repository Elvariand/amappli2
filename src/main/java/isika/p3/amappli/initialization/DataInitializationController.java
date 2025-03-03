package isika.p3.amappli.initialization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/data-init")
public class DataInitializationController {
	
	@Autowired
	private DataInitializationService initService;

	@GetMapping("")
	public String initializeData() {
		initService.dataInit();
		return "dispatch";
	}
	
	@GetMapping("/test")
	public String initializeWIP() {
		initService.WIPInit();
		return "dispatch";
	}
	
}
