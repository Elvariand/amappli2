package isika.p3.amappli.controllers.amappli;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class Rerouter {

	@GetMapping()
	public String home(Model model) {
	 	return "amappli/home";
	}

}
