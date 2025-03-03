package isika.p3.amappli.controllers.amappli;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import isika.p3.amappli.entities.tenancy.ContentBlock;
import isika.p3.amappli.entities.tenancy.HomePageContent;
import isika.p3.amappli.entities.tenancy.Tenancy;
import isika.p3.amappli.service.amap.GraphismService;
import isika.p3.amappli.service.amappli.TenancyService;

@Controller
@RequestMapping("/amap")
public class TenancyController {

	private final TenancyService tenancyService;
	private final GraphismService graphismService;
	

	public TenancyController(TenancyService tenancyService, GraphismService graphismService) {
		this.tenancyService = tenancyService;
		this.graphismService = graphismService;
	}

	@GetMapping("/getall")
	public String getAllTenancies(Model model) {
		List<Tenancy> tenancies = tenancyService.getAllTenancies();
		model.addAttribute("tenancies", tenancies);
		System.out.println(tenancies.size());
		return "amappli/back/tenancy/tenancy-list";
	}
	
	@GetMapping("{tenancyAlias}/home")
	public String getHomePageContentByAlias(@PathVariable("tenancyAlias") String alias, Model model) {
	    // Récupérer le contenu de la page d'accueil
	    HomePageContent homePageContent = tenancyService.getHomePageContentByTenancyAlias(alias);
	   // Tenancy tenancy = tenancyService.getTenancyByAlias(alias);

	    // Vérifier si les données existent
	    if (homePageContent != null) {

	        // Récupérer et filtrer les ContentBlocks pour la page d'accueil
	        List<ContentBlock> contentBlocks = homePageContent.getContents();
	        
	        // Filtrer les ContentBlocks avec isValue == true
	        List<ContentBlock> valueBlocks = contentBlocks.stream()
	            .filter(ContentBlock::isValue)
	            .collect(Collectors.toList());

	        model.addAttribute("valueBlocks", valueBlocks);

	        // Block de présentation
	        List<ContentBlock> presentationBlocks = contentBlocks.stream()
	            .filter(block -> !block.isValue()).collect(Collectors.toList());
	           // .findFirst()
	          // .orElse(null);

	        model.addAttribute("presentationBlocks", presentationBlocks);
	        
	        graphismService.setUpModel(alias, model);

	    } else {
	        model.addAttribute("message", "Page d'accueil non trouvée.");
	    }

	    return "amap/front/homePage"; // Vue JSP correspondante
	}


	@GetMapping("/{id}")
	public String getTenancyById(@PathVariable Long id, Model model) {
		Tenancy tenancy = tenancyService.getTenancyById(id);
		model.addAttribute("tenancy", tenancy);
		  // Ajouter les informations générales de la tenancy
        model.addAttribute("tenancy", tenancy);
        model.addAttribute("tenancyName", tenancy.getTenancyName());
        model.addAttribute("tenancySlogan", tenancy.getTenancySlogan());
		return "tenancy-details";
	
	}



	@GetMapping("/create")
	public String showCreateForm(Model model) {
		model.addAttribute("tenancy", new Tenancy());
		return "tenancy-form";
	}

	@PostMapping("/create")
	public String createTenancy(@ModelAttribute("tenancy") Tenancy tenancy) {
		tenancyService.createTenancy(tenancy);
		return "redirect:/tenancies";
	}

	@GetMapping("/delete/{id}")
	public String deleteTenancy(@PathVariable Long id) {
		tenancyService.deleteTenancy(id);
		return "redirect:/tenancies";
	}
	
	
	@GetMapping("/{tenancyAlias}/amapPage")
	public String amapPage(@PathVariable("tenancyAlias") String alias, Model model) {
	    // Récupérer la tenancy
	    Tenancy tenancy = tenancyService.getTenancyByAlias(alias);

	    if (tenancy != null) {
	        graphismService.setUpModel(alias, model);
	    } else {
	        model.addAttribute("message", "Tenancy non trouvée.");
	    }

	    return "amap/front/amapPage"; 
	}


	
	@GetMapping("/{tenancyAlias}/contact")
	public String contactPage(@PathVariable("tenancyAlias") String alias, Model model) {
	    // Récupérer la tenancy
	    Tenancy tenancy = tenancyService.getTenancyByAlias(alias);

	    if (tenancy != null) {
	        graphismService.setUpModel(alias, model);
	    } else {
	        model.addAttribute("message", "Tenancy non trouvée.");
	    }

	    return "amap/front/contactPage"; 
	}




}
