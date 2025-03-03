package isika.p3.amappli.controllers.amap;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import isika.p3.amappli.entities.contract.Contract;
import isika.p3.amappli.entities.contract.ContractType;
import isika.p3.amappli.entities.product.Product;
import isika.p3.amappli.entities.tenancy.Options;
import isika.p3.amappli.entities.tenancy.PickUpSchedule;
import isika.p3.amappli.entities.tenancy.Tenancy;
import isika.p3.amappli.entities.workshop.Workshop;
import isika.p3.amappli.repo.amappli.TenancyRepository;
import isika.p3.amappli.service.amap.ContractService;
import isika.p3.amappli.service.amap.GraphismService;
import isika.p3.amappli.service.amap.ProductService;
import isika.p3.amappli.service.amap.WorkshopService;

@Controller
@RequestMapping("/amap/{tenancyAlias}/shop")
public class ShopController {

	private final ContractService contractService;
	private final TenancyRepository tenancyRepository;
	private final ProductService productService;
	private final WorkshopService workshopService;
	private final GraphismService graphismService;

	public ShopController(GraphismService graphismService, ContractService contractService, TenancyRepository tenancyRepository,
			ProductService productService, WorkshopService workshopService) {
		this.contractService = contractService;
		this.tenancyRepository = tenancyRepository;
		this.productService = productService;
		this.workshopService = workshopService;
		this.graphismService = graphismService;
	}

	/**
	 * Displays all shoppable contracts in the shop view.
	 */
	@GetMapping("/contracts")
	public String showAllShoppableContracts(Model model, @PathVariable("tenancyAlias") String tenancyAlias, @RequestParam(value = "contractType", required = false) String contractType) {

		Tenancy tenancy = tenancyRepository.findByTenancyAlias(tenancyAlias)
	            .orElseThrow(() -> new IllegalArgumentException("Tenancy not found for alias: " + tenancyAlias));
		
		Options options = tenancy.getOptions();

	    List<Contract> contracts = contractService.findShoppableContractsByTenancy(tenancy);

	    long vegetableCount = contracts.stream()
	            .filter(contract -> "VEGETABLES_CONTRACT".equals(contract.getContractType().name()))
	            .count();

	    long fruitCount = contracts.stream()
	            .filter(contract -> "FRUITS_CONTRACT".equals(contract.getContractType().name()))
	            .count();

	    long mixedCount = contracts.stream()
	            .filter(contract -> "MIX_CONTRACT".equals(contract.getContractType().name()))
	            .count();
	    
	    if (contractType != null) {
	        try {
	            ContractType type = ContractType.valueOf(contractType);
	            contracts = contractService.findShoppableContractsByTypeAndTenancy(type, tenancy);
	        } catch (IllegalArgumentException e) {
	            contracts = contractService.findShoppableContractsByTenancy(tenancy); // Valeur non valide
	        }
	    } else {
	        contracts = contractService.findShoppableContractsByTenancy(tenancy);
	    }

	    model.addAttribute("vegetableCount", vegetableCount);
	    model.addAttribute("fruitCount", fruitCount);
	    model.addAttribute("mixedCount", mixedCount);
	    model.addAttribute("contracts", contracts);
	    model.addAttribute("options", options);
	    model.addAttribute("tenancyAlias", tenancyAlias);
	    graphismService.setUpModel(tenancyAlias, model);

	    return "amap/front/shop-contracts";
	}


	/**
	 * Displays all shoppable products in the shop view.
	 */
	@GetMapping("/products")
	public String showAllShoppableProducts(Model model, @PathVariable("tenancyAlias") String tenancyAlias) {
		
		Tenancy tenancy = tenancyRepository.findByTenancyAlias(tenancyAlias)
				.orElseThrow(() -> new IllegalArgumentException("Tenancy not found for alias: " + tenancyAlias));

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.FRENCH);

		List<Map<String, Object>> formattedProducts = productService.findShoppableProductsByTenancy(tenancy)
	        .stream()
	        .map(product -> {
	            Map<String, Object> productData = new HashMap<>();
	            productData.put("productName", product.getProductName());
	            productData.put("productDescription", product.getProductDescription());
	            productData.put("productPrice", product.getProductPrice());
	            productData.put("imageData", product.getImageData());
	            productData.put("imageType", product.getImageType());
	            productData.put("id", product.getId());
	            if (product.getExpirationDate() != null) {
	                productData.put("expirationDate", product.getExpirationDate().format(formatter));
	            }
	            return productData;
	        })
	        .toList();
	    
	    List<Contract> contracts = contractService.findShoppableContractsByTenancy(tenancy);

	    long vegetableCount = contracts.stream()
	            .filter(contract -> "VEGETABLES_CONTRACT".equals(contract.getContractType().name()))
	            .count();

	    long fruitCount = contracts.stream()
	            .filter(contract -> "FRUITS_CONTRACT".equals(contract.getContractType().name()))
	            .count();

	    long mixedCount = contracts.stream()
	            .filter(contract -> "MIX_CONTRACT".equals(contract.getContractType().name()))
	            .count();
	    
	    Options options = tenancy.getOptions();
	    
	    model.addAttribute("options", options);
		model.addAttribute("products", formattedProducts);
	    model.addAttribute("vegetableCount", vegetableCount);
	    model.addAttribute("fruitCount", fruitCount);
	    model.addAttribute("mixedCount", mixedCount);
	    model.addAttribute("contracts", contracts);
		model.addAttribute("tenancyAlias", tenancyAlias);
		graphismService.setUpModel(tenancyAlias, model);

		return "amap/front/shop-products";
	}

	/**
	 * Displays all shoppable products in the shop view.
	 */
	@GetMapping("/workshops")
	public String showAllShoppableWorkshops(Model model, @PathVariable("tenancyAlias") String tenancyAlias) {
		
		Tenancy tenancy = tenancyRepository.findByTenancyAlias(tenancyAlias)
				.orElseThrow(() -> new IllegalArgumentException("Tenancy not found for alias: " + tenancyAlias));

	    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("'Le' dd MMMM yyyy 'à' HH:mm", Locale.FRENCH);

	    List<Map<String, Object>> formattedWorkshops = workshopService.findShoppableWorkshopsByTenancy(tenancy)
	        .stream()
	        .map(workshop -> {
	            Map<String, Object> workshopData = new HashMap<>();
	            workshopData.put("workshopName", workshop.getWorkshopName());
	            workshopData.put("workshopDescription", workshop.getWorkshopDescription());
	            workshopData.put("workshopPrice", workshop.getWorkshopPrice());
	            workshopData.put("imageData", workshop.getImageData());
	            workshopData.put("workshopDuration",workshop.getWorkshopDuration());
	            workshopData.put("imageType", workshop.getImageType());
	            workshopData.put("id",workshop.getId());
	            if (workshop.getWorkshopDateTime() != null) {
	                workshopData.put("workshopDateTime", workshop.getWorkshopDateTime().format(dateTimeFormatter));
	            }
	            return workshopData;
	        })
	        .toList();
	    
	    List<Contract> contracts = contractService.findShoppableContractsByTenancy(tenancy);

	    long vegetableCount = contracts.stream()
	            .filter(contract -> "VEGETABLES_CONTRACT".equals(contract.getContractType().name()))
	            .count();

	    long fruitCount = contracts.stream()
	            .filter(contract -> "FRUITS_CONTRACT".equals(contract.getContractType().name()))
	            .count();

	    long mixedCount = contracts.stream()
	            .filter(contract -> "MIX_CONTRACT".equals(contract.getContractType().name()))
	            .count();

	    Options options = tenancy.getOptions();
	    
	    model.addAttribute("options", options);
	    model.addAttribute("vegetableCount", vegetableCount);
	    model.addAttribute("fruitCount", fruitCount);
	    model.addAttribute("mixedCount", mixedCount);
	    model.addAttribute("contracts", contracts);
	    model.addAttribute("workshops", formattedWorkshops);
		model.addAttribute("tenancyAlias", tenancyAlias);
		graphismService.setUpModel(tenancyAlias, model);

		return "amap/front/shop-workshops";
	}

	/**
	 * Displays details for a specific contract by id.
	 */
	@GetMapping("/contracts/{id}")
	public String showContractDetails(@PathVariable("tenancyAlias") String tenancyAlias, @PathVariable("id") Long id,
			Model model) {

		Tenancy tenancy = tenancyRepository.findByTenancyAlias(tenancyAlias)
				.orElseThrow(() -> new IllegalArgumentException("Tenancy not found for alias: " + tenancyAlias));

		Contract contract = contractService.findById(id);


		if (contract.getAddress() != null) {
			model.addAttribute("address", contract.getAddress());
		} else if (contract.getTenancy() != null && contract.getTenancy().getAddress() != null) {
			model.addAttribute("address", contract.getTenancy().getAddress());
		} else {
			model.addAttribute("address", null);
		}
		
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'le' dd MMMM yyyy", Locale.FRENCH);
	    if (contract.getEndDate() != null) {
	        String formattedEndDate = contract.getEndDate().format(formatter);
	        model.addAttribute("formattedEndDate", formattedEndDate);
	    } else {
	        model.addAttribute("formattedEndDate", "Non spécifiée");
	    }
	    
	    Optional<PickUpSchedule> pickUpSchedule = Optional.ofNullable(tenancy.getPickUpSchedule());
	    if (pickUpSchedule.isPresent()) {
	        LocalDateTime[] nextPickUp = pickUpSchedule.get().getNextPickUpLocalDateTimes();
	        LocalDateTime nextDeliveryDate = nextPickUp[0];

	        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("'le' EEEE dd MMMM yyyy", Locale.FRENCH);
	        String formattedNextDeliveryDate = nextDeliveryDate.format(formatter1);

	        model.addAttribute("formattedNextDeliveryDate", formattedNextDeliveryDate);
	    } else {
	        model.addAttribute("formattedNextDeliveryDate", "Non disponible");
	    }
	    
	    List<Contract> contracts = contractService.findShoppableContractsByTenancy(tenancy);
	    long vegetableCount = contracts.stream()
	            .filter(c -> "VEGETABLES_CONTRACT".equals(c.getContractType().name()))
	            .count();

	    long fruitCount = contracts.stream()
	            .filter(c -> "FRUITS_CONTRACT".equals(c.getContractType().name()))
	            .count();

	    long mixedCount = contracts.stream()
	            .filter(c -> "MIX_CONTRACT".equals(c.getContractType().name()))
	            .count();
	    
	    Options options = tenancy.getOptions();
	    
	    model.addAttribute("options", options);
	    model.addAttribute("vegetableCount", vegetableCount);
	    model.addAttribute("fruitCount", fruitCount);
	    model.addAttribute("mixedCount", mixedCount);
	    model.addAttribute("contracts", contracts);
		model.addAttribute("contract", contract);
//		model.addAttribute("nextDeliveryDate", nextDeliveryDate);
		graphismService.setUpModel(tenancyAlias, model);
		return "amap/front/shopping-contract-detail";
	}

	/**
	 * Displays details for a specific product by id.
	 */
	@GetMapping("/products/{id}")
	public String showProductDetails(@PathVariable("tenancyAlias") String tenancyAlias, @PathVariable("id") Long id,
			Model model) {

		Tenancy tenancy = tenancyRepository.findByTenancyAlias(tenancyAlias)
				.orElseThrow(() -> new IllegalArgumentException("Tenancy not found for alias: " + tenancyAlias));

		Product product = productService.findById(id);
		
	    List<Contract> contracts = contractService.findShoppableContractsByTenancy(tenancy);

		if (product.getAddress() != null) {
			model.addAttribute("address", product.getAddress());
		} else if (product.getTenancy() != null && product.getTenancy().getAddress() != null) {
			model.addAttribute("address", product.getTenancy().getAddress());
		} else {
			model.addAttribute("address", null);
		}
		
	    // Formatter pour les dates
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.FRENCH);

	    // Formatage des dates
	    String formattedFabricationDate = product.getFabricationDate() != null
	            ? product.getFabricationDate().format(formatter)
	            : null;

	    String formattedExpirationDate = product.getExpirationDate() != null
	            ? product.getExpirationDate().format(formatter)
	            : null;
	    
	    long vegetableCount = contracts.stream()
	            .filter(contract -> "VEGETABLES_CONTRACT".equals(contract.getContractType().name()))
	            .count();

	    long fruitCount = contracts.stream()
	            .filter(contract -> "FRUITS_CONTRACT".equals(contract.getContractType().name()))
	            .count();

	    long mixedCount = contracts.stream()
	            .filter(contract -> "MIX_CONTRACT".equals(contract.getContractType().name()))
	            .count();
	    
	    model.addAttribute("vegetableCount", vegetableCount);
	    model.addAttribute("fruitCount", fruitCount);
	    model.addAttribute("mixedCount", mixedCount);
	    
	    Options options = tenancy.getOptions();
	    
	    model.addAttribute("options", options);

	    // Ajout des dates formatées au modèle
	    model.addAttribute("formattedFabricationDate", formattedFabricationDate);
	    model.addAttribute("formattedExpirationDate", formattedExpirationDate);
		model.addAttribute("product", product);
		graphismService.setUpModel(tenancyAlias, model);
	    model.addAttribute("contracts", contracts);
		model.addAttribute("tenancyAlias", tenancyAlias);
		
		return "amap/front/shopping-product-detail";
	}

	/**
	 * Displays details for a specific contract by id.
	 */
	@GetMapping("/workshops/{id}")
	public String showWorkshopDetails(@PathVariable("tenancyAlias") String tenancyAlias, @PathVariable("id") Long id,
			Model model) {
		
		Tenancy tenancy = tenancyRepository.findByTenancyAlias(tenancyAlias)
				.orElseThrow(() -> new IllegalArgumentException("Tenancy not found for alias: " + tenancyAlias));
		
		Workshop workshop = workshopService.findById(id);
				if (workshop.getAddress() != null) {
			model.addAttribute("address", workshop.getAddress());
		} else {
			model.addAttribute("address", null);
		}
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy 'à' HH:mm", Locale.FRENCH);
	   
	    String formattedWorkshopDateTime = workshop.getWorkshopDateTime().format(formatter);
	    List<Contract> contracts = contractService.findShoppableContractsByTenancy(tenancy);

	    long vegetableCount = contracts.stream()
	            .filter(contract -> "VEGETABLES_CONTRACT".equals(contract.getContractType().name()))
	            .count();

	    long fruitCount = contracts.stream()
	            .filter(contract -> "FRUITS_CONTRACT".equals(contract.getContractType().name()))
	            .count();

	    long mixedCount = contracts.stream()
	            .filter(contract -> "MIX_CONTRACT".equals(contract.getContractType().name()))
	            .count();
	    
	    Options options = tenancy.getOptions();
	    
	    model.addAttribute("options", options);
	    
	    model.addAttribute("vegetableCount", vegetableCount);
	    model.addAttribute("fruitCount", fruitCount);
	    model.addAttribute("mixedCount", mixedCount);
	    model.addAttribute("formattedWorkshopDateTime", formattedWorkshopDateTime);
		model.addAttribute("workshop", workshop);
		model.addAttribute("contracts", contracts);
		graphismService.setUpModel(tenancyAlias, model);
		return "amap/front/shopping-workshop-detail";
	}

}
