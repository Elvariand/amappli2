package isika.p3.amappli.initialization;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import isika.p3.amappli.entities.auth.Permission;
import isika.p3.amappli.entities.auth.Role;
import isika.p3.amappli.entities.contract.Contract;
import isika.p3.amappli.entities.contract.ContractType;
import isika.p3.amappli.entities.contract.ContractWeight;
import isika.p3.amappli.entities.contract.DeliveryRecurrence;
import isika.p3.amappli.entities.membership.MembershipFee;
import isika.p3.amappli.entities.order.Order;
import isika.p3.amappli.entities.order.OrderItem;
import isika.p3.amappli.entities.order.OrderStatus;
import isika.p3.amappli.entities.order.Shoppable;
import isika.p3.amappli.entities.payment.Payment;
import isika.p3.amappli.entities.payment.PaymentType;
import isika.p3.amappli.entities.product.Product;
import isika.p3.amappli.entities.tenancy.ColorPalette;
import isika.p3.amappli.entities.tenancy.ContentBlock;
import isika.p3.amappli.entities.tenancy.FontChoice;
import isika.p3.amappli.entities.tenancy.Graphism;
import isika.p3.amappli.entities.tenancy.HomePageContent;
import isika.p3.amappli.entities.tenancy.Options;
import isika.p3.amappli.entities.tenancy.PickUpSchedule;
import isika.p3.amappli.entities.tenancy.Tenancy;
import isika.p3.amappli.entities.user.Address;
import isika.p3.amappli.entities.user.CompanyDetails;
import isika.p3.amappli.entities.user.ContactInfo;
import isika.p3.amappli.entities.user.User;
import isika.p3.amappli.entities.workshop.Workshop;
import isika.p3.amappli.repo.amap.ContractRepository;
import isika.p3.amappli.repo.amap.OrderItemRepository;
import isika.p3.amappli.repo.amap.OrderRepository;
import isika.p3.amappli.repo.amap.PaymentRepository;
import isika.p3.amappli.repo.amap.ProductRepository;
import isika.p3.amappli.repo.amap.UserRepository;
import isika.p3.amappli.repo.amap.WorkshopRepository;
// import isika.p3.amappli.repo.amappli.OptionsRepository;
import isika.p3.amappli.repo.amappli.PermissionRepository;
import isika.p3.amappli.repo.amappli.TenancyRepository;
import isika.p3.amappli.service.amap.AddressService;
import isika.p3.amappli.service.amap.CompanyDetailsService;
import isika.p3.amappli.service.amap.ContactInfoService;
import isika.p3.amappli.service.amap.RoleService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class DataInitializationService {

	@Autowired
	private TenancyRepository tenancyRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PermissionRepository permissionRepository;
	@Autowired
	private RoleService roleService;
	@Autowired
	private AddressService addressService;
	@Autowired
	private ContactInfoService contactInfoService;
	@Autowired
	private CompanyDetailsService companyDetailsService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private ContractRepository contractRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private WorkshopRepository workshopRepository;
	// @Autowired
	// private OptionsRepository optionsRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private OrderItemRepository orderItemRepository;
	@Autowired
	private PaymentRepository paymentRepository;
	@PersistenceContext
	private EntityManager entityManager;

	public void dataInit() {
		try {
		 	tenancyInit();
		 	permissionInit();
		 	roleInit();
		 } catch (Exception e) {
		 	e.printStackTrace();
		 }
		try {
			for (Tenancy t : tenancyRepository.findAll()) {
				userInit(t);
				initMembershipFee(t);
				// Génération de contrats pour chaque tenancy
				try {
					contractInitForTenancy(t);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
		 	e.printStackTrace();
		}
		try {
			contractInitForTenancy(6L);
			productInit();
			workshopInit();
			initAllOrders();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

// test method to avoid initializing everything while developing and testing 
// accessible via http://localhost:8080/Amappli/data-init/test
	public void WIPInit() {
		initOrder(1L);
	}

	@Transactional
	public void permissionInit() {
		// permissions creation
		Permission permission1 = Permission.builder().name("gestion plateforme").build();
		Permission permission2 = Permission.builder().name("gestion utilisateurs amap").build();
		Permission permission3 = Permission.builder().name("creation contrats amap").build();
		Permission permission4 = Permission.builder().name("creation atelier amap").build();
		Permission permission5 = Permission.builder().name("modification page accueil amap").build();
		Permission permission6 = Permission.builder().name("modification statut commande amap").build();

		permissionRepository.save(permission1);
		permissionRepository.save(permission2);
		permissionRepository.save(permission3);
		permissionRepository.save(permission4);
		permissionRepository.save(permission5);
		permissionRepository.save(permission6);
	}

	@Transactional
	public void roleInit() {

		Permission permission1 = permissionRepository.findById(1l).orElse(null);
		Permission permission2 = permissionRepository.findById(2l).orElse(null);
		Permission permission3 = permissionRepository.findById(3l).orElse(null);
		Permission permission4 = permissionRepository.findById(4l).orElse(null);
		Permission permission5 = permissionRepository.findById(5l).orElse(null);
		Permission permission6 = permissionRepository.findById(6l).orElse(null);

		// roles creation
		Role AdminPlateforme = Role.builder().name("AdminPlateforme").tenancy(null).hidden(true).build();
		Role Admin = Role.builder().name("Admin").tenancy(null).hidden(false).build();
		Role Member = Role.builder().name("Adherent").tenancy(null).hidden(false).build();
		Role Supplier = Role.builder().name("Producteur").tenancy(null).hidden(false).build();
		Role OnlyTenancy2 = Role.builder().name("AgriMember").tenancy(tenancyRepository.findById(2l).orElse(null))
				.hidden(false).build();

		AdminPlateforme.getPermissions().add(permission1);
		AdminPlateforme.getPermissions().add(permission2);
		AdminPlateforme.getPermissions().add(permission3);
		AdminPlateforme.getPermissions().add(permission4);
		AdminPlateforme.getPermissions().add(permission5);
		AdminPlateforme.getPermissions().add(permission6);

		Admin.getPermissions().add(permission2);
		Admin.getPermissions().add(permission3);
		Admin.getPermissions().add(permission4);
		Admin.getPermissions().add(permission5);
		Admin.getPermissions().add(permission6);

		// Member.getPermissions().add(permission2);

		Supplier.getPermissions().add(permission3);
		Supplier.getPermissions().add(permission4);
		Supplier.getPermissions().add(permission6);

		OnlyTenancy2.getPermissions().add(permission5);

		// roleRepository.save(AdminPlateforme);
		// roleRepository.save(Admin);
		// roleRepository.save(Member);
		// roleRepository.save(Supplier);
		// roleRepository.save(OnlyTenancy2);

		roleService.createRole(AdminPlateforme);
		roleService.createRole(Admin);
		roleService.createRole(Member);
		roleService.createRole(Supplier);
		roleService.createRole(OnlyTenancy2);
	}

	@Transactional
	public void userInit(Tenancy tenancy) {

		Role AdminPlateforme = roleService.findByName("AdminPlateforme");
		Role Admin = roleService.findByName("Admin");
		Role Member = roleService.findByName("Adhérent");
		Role Supplier = roleService.findByName("Producteur");

		// users creation
		User user1 = User.builder().email("marie.durand@example" + tenancy.getTenancyId() + ".com")
				.password("AMAPamap11@").isActive(true).tenancy(null).build();
		user1.getRoles().add(AdminPlateforme);
		saveUser(user1);
		Address a1 = Address.builder().line2("5 avenue des Roses").line1("Appartement 12").postCode("44000")
				.city("Nantes").user(user1).build();
		addressService.save(a1);
		ContactInfo ci1 = ContactInfo.builder().name("Durand").firstName("Marie").phoneNumber("0601010101").user(user1)
				.build();
		contactInfoService.save(ci1);
		user1.setAddress(a1);
		user1.setContactInfo(ci1);
		updateUser(user1);

		User user2 = User.builder().email("lucas.martin@example" + tenancy.getTenancyId() + ".com")
				.password("AMAPamap11@").isActive(true).tenancy(tenancy).build();
		user2.getRoles().add(Admin);
		saveUser(user2);
		Address a2 = Address.builder().line2("12 rue de la Liberté").line1("Bâtiment B").postCode("44100")
				.city("Nantes").user(user2).build();
		addressService.save(a2);
		ContactInfo ci2 = ContactInfo.builder().name("Martin").firstName("Lucas").phoneNumber("0612345678").user(user2)
				.build();
		contactInfoService.save(ci2);
		user2.setAddress(a2);
		user2.setContactInfo(ci2);
		updateUser(user2);

		User user3 = User.builder().email("jeanne.lemoine@example" + tenancy.getTenancyId() + ".com")
				.password("AMAPamap11@").isActive(true).tenancy(tenancy).build();
		user3.getRoles().add(Member);
		user3.getRoles().add(Supplier);
		saveUser(user3);

		Address a3 = Address.builder().line2("15 boulevard des Alpes").line1("").postCode("44120").city("Vertou")
				.user(user3).build();
		addressService.save(a3);
		ContactInfo ci3 = ContactInfo.builder().name("Lemoine").firstName("Jeanne").phoneNumber("0678987654")
				.user(user3).build();
		contactInfoService.save(ci3);
		CompanyDetails cd3 = CompanyDetails.builder().companyName("L'arbre sur la colline")
				.siretNumber("48151623422025").user(user3).build();
		companyDetailsService.save(cd3);
		user3.setAddress(a3);
		user3.setContactInfo(ci3);
		user3.setCompanyDetails(cd3);
		updateUser(user3);

		User user4 = User.builder().email("thomas.dupuis@example" + tenancy.getTenancyId() + ".com")
				.password("AMAPamap11@").isActive(true).tenancy(tenancy).build();
		user4.getRoles().add(Member);
		saveUser(user4);
		Address a4 = Address.builder().line2("28 rue du Marché").line1("").postCode("44200").city("Nantes").user(user4)
				.build();
		addressService.save(a4);
		ContactInfo ci4 = ContactInfo.builder().name("Dupuis").firstName("Thomas").phoneNumber("0611223344").user(user4)
				.build();
		contactInfoService.save(ci4);
		user4.setAddress(a4);
		user4.setContactInfo(ci4);
		updateUser(user4);

		User user5 = User.builder().email("claire.fournier@example" + tenancy.getTenancyId() + ".com")
				.password("AMAPamap11@").isActive(true).tenancy(tenancy).build();
		user5.getRoles().add(Supplier);
		saveUser(user5);
		Address a5 = Address.builder().line2("31 rue des Lilas").line1("Appartement 4").postCode("44130")
				.city("Machecoul").user(user5).build();
		addressService.save(a5);
		ContactInfo ci5 = ContactInfo.builder().name("Fournier").firstName("Claire").phoneNumber("0698765432")
				.user(user5).build();
		contactInfoService.save(ci5);
		CompanyDetails cd5 = CompanyDetails.builder().companyName("Le verger exotique").siretNumber("16497583162945")
				.user(user5).build();
		companyDetailsService.save(cd5);
		user5.setCompanyDetails(cd5);
		user5.setAddress(a5);
		user5.setContactInfo(ci5);
		updateUser(user5);

		User user6 = User.builder().email("charlotte.petit@example" + tenancy.getTenancyId() + ".com")
				.password("AMAPamap11@").isActive(true).tenancy(tenancy).build();
		user6.getRoles().add(Member);
		saveUser(user6);
		Address a6 = Address.builder().line2("42 rue du Soleil").line1("").postCode("35000").city("Rennes").user(user6)
				.build();
		ContactInfo ci6 = ContactInfo.builder().name("Petit").firstName("Charlotte").phoneNumber("0612345670")
				.user(user6).build();
		addressService.save(a6);
		contactInfoService.save(ci6);
		user6.setAddress(a6);
		user6.setContactInfo(ci6);
		updateUser(user6);

		User user7 = User.builder().email("victor.martin@example" + tenancy.getTenancyId() + ".com")
				.password("AMAPamap11@").isActive(true).tenancy(tenancy).build();
		user7.getRoles().add(Supplier);
		saveUser(user7);
		Address a7 = Address.builder().line2("7 place des Halles").line1("").postCode("13000").city("Marseille")
				.user(user7).build();
		ContactInfo ci7 = ContactInfo.builder().name("Martin").firstName("Victor").phoneNumber("0677654321").build();
		CompanyDetails cd7 = CompanyDetails.builder().companyName("Croissance Luxuriante").siretNumber("85496312563415")
				.user(user7).build();
		companyDetailsService.save(cd7);
		addressService.save(a7);
		contactInfoService.save(ci7);
		user7.setAddress(a7);
		user7.setContactInfo(ci7);
		user7.setCompanyDetails(cd7);
		updateUser(user7);

		User user8 = User.builder().email("elise.muller@example" + tenancy.getTenancyId() + ".com")
				.password("AMAPamap11@").isActive(true).tenancy(tenancy).build();
		user8.getRoles().add(Member);
		saveUser(user8);
		Address a8 = Address.builder().line2("56 avenue du Général Leclerc").line1("Batiment A").postCode("60000")
				.city("Beauvais").user(user8).build();
		ContactInfo ci8 = ContactInfo.builder().name("Muller").firstName("Elise").phoneNumber("0698765430").user(user8)
				.build();
		addressService.save(a8);
		contactInfoService.save(ci8);
		user8.setAddress(a8);
		user8.setContactInfo(ci8);
		updateUser(user8);

		User user9 = User.builder().email("jean.benoit@example" + tenancy.getTenancyId() + ".com")
				.password("AMAPamap11@").isActive(true).tenancy(tenancy).build();
		user9.getRoles().add(Supplier);
		saveUser(user9);
		Address a9 = Address.builder().line2("23 rue de la République").line1("Appartement 7").postCode("75001")
				.city("Paris").user(user9).build();
		ContactInfo ci9 = ContactInfo.builder().name("Benoît").firstName("Jean").phoneNumber("0687654321").user(user9)
				.build();
		CompanyDetails cd9 = CompanyDetails.builder().companyName("Bosquet de Solpétal").siretNumber("34867591342684")
				.user(user9).build();
		companyDetailsService.save(cd9);
		user9.setCompanyDetails(cd9);
		addressService.save(a9);
		contactInfoService.save(ci9);
		user9.setAddress(a9);
		user9.setContactInfo(ci9);
		updateUser(user9);

		User user10 = User.builder().email("amelie.rousseau@example" + tenancy.getTenancyId() + ".com")
				.password("AMAPamap11@").isActive(true).tenancy(tenancy).build();
		user10.getRoles().add(Admin);
		saveUser(user10);
		Address a10 = Address.builder().line2("1 avenue de la Mer").line1("").postCode("30000").city("Nîmes")
				.user(user10).build();
		ContactInfo ci10 = ContactInfo.builder().name("Rousseau").firstName("Amélie").phoneNumber("0623456789")
				.user(user10).build();
		addressService.save(a10);
		contactInfoService.save(ci10);
		user10.setAddress(a10);
		user10.setContactInfo(ci10);
		updateUser(user10);

		User user11 = User.builder().email("henri.durand@example" + tenancy.getTenancyId() + ".com")
				.password("AMAPamap11@").isActive(true).tenancy(tenancy).build();
		user11.getRoles().add(Member);
		saveUser(user11);
		Address a11 = Address.builder().line2("19 rue des Fleurs").line1("Appartement 5").postCode("33000")
				.city("Bordeaux").user(user11).build();
		ContactInfo ci11 = ContactInfo.builder().name("Durand").firstName("Henri").phoneNumber("0645671234")
				.user(user11).build();
		addressService.save(a11);
		contactInfoService.save(ci11);
		user11.setAddress(a11);
		user11.setContactInfo(ci11);
		updateUser(user11);

		User user12 = User.builder().email("manon.fabre@example" + tenancy.getTenancyId() + ".com")
				.password("AMAPamap11@").isActive(true).tenancy(tenancy).build();
		user12.getRoles().add(Supplier);
		saveUser(user12);
		Address a12 = Address.builder().line2("22 rue des Lilas").line1("Rez-de-chaussée").postCode("92000")
				.city("Nanterre").user(user12).build();
		ContactInfo ci12 = ContactInfo.builder().name("Fabre").firstName("Manon").phoneNumber("0691234567").user(user12)
				.build();
		CompanyDetails cd12 = CompanyDetails.builder().companyName("L'arbre des récits").siretNumber("12458691342748")
				.user(user12).build();
		companyDetailsService.save(cd12);
		addressService.save(a12);
		contactInfoService.save(ci12);
		user12.setAddress(a12);
		user12.setContactInfo(ci12);
		user12.setCompanyDetails(cd12);
		updateUser(user12);

		User user13 = User.builder().email("paul.brun@example" + tenancy.getTenancyId() + ".com")
				.password("AMAPamap11@").isActive(true).tenancy(tenancy).build();
		user13.getRoles().add(Member);
		saveUser(user13);
		Address a13 = Address.builder().line2("8 avenue de la Gare").line1("2ème étage").postCode("44000")
				.city("Nantes").user(user13).build();
		ContactInfo ci13 = ContactInfo.builder().name("Brun").firstName("Paul").phoneNumber("0687987654").user(user13)
				.build();
		addressService.save(a13);
		contactInfoService.save(ci13);
		user13.setAddress(a13);
		user13.setContactInfo(ci13);
		updateUser(user13);

		User user14 = User.builder().email("sophie.martin@example" + tenancy.getTenancyId() + ".com")
				.password("AMAPamap11@").isActive(true).tenancy(tenancy).build();
		user14.getRoles().add(Supplier);
		saveUser(user14);
		Address a14 = Address.builder().line2("13 rue de la Liberté").line1("").postCode("44000").city("Nantes")
				.user(user14).build();
		ContactInfo ci14 = ContactInfo.builder().name("Martin").firstName("Sophie").phoneNumber("0678987654")
				.user(user14).build();
		CompanyDetails cd14 = CompanyDetails.builder().companyName("Le verger de Sophie").siretNumber("15468297316482")
				.user(user14).build();
		companyDetailsService.save(cd14);
		addressService.save(a14);
		contactInfoService.save(ci14);
		user14.setAddress(a14);
		user14.setContactInfo(ci14);
		user14.setCompanyDetails(cd14);
		updateUser(user14);

		User user15 = User.builder().email("lucie.lafaye@example" + tenancy.getTenancyId() + ".com")
				.password("AMAPamap11@").isActive(true).tenancy(tenancy).build();
		user15.getRoles().add(Admin);
		saveUser(user15);
		Address a15 = Address.builder().line2("5 rue du Pont").line1("").postCode("69000").city("Lyon").user(user15)
				.build();
		ContactInfo ci15 = ContactInfo.builder().name("Lafaye").firstName("Lucie").phoneNumber("0694567890")
				.user(user15).build();
		addressService.save(a15);
		contactInfoService.save(ci15);
		user15.setAddress(a15);
		user15.setContactInfo(ci15);
		updateUser(user15);

		User user16 = User.builder().email("alain.dubois@example" + tenancy.getTenancyId() + ".com")
				.password("AMAPamap11@").isActive(true).tenancy(tenancy).build();
		user16.getRoles().add(Supplier);
		saveUser(user16);
		Address a16 = Address.builder().line2("20 rue de la Gare").line1("").postCode("75002").city("Paris")
				.user(user16).build();
		ContactInfo ci16 = ContactInfo.builder().name("Dubois").firstName("Alain").phoneNumber("0685559876")
				.user(user16).build();
		CompanyDetails cd16 = CompanyDetails.builder().companyName("Ferme De Sutters").siretNumber("94683716523446")
				.user(user16).build();
		companyDetailsService.save(cd16);
		addressService.save(a16);
		contactInfoService.save(ci16);
		user16.setAddress(a16);
		user16.setContactInfo(ci16);
		user16.setCompanyDetails(cd16);
		updateUser(user16);

		User user17 = User.builder().email("laura.lemoine@example" + tenancy.getTenancyId() + ".com")
				.password("AMAPamap11@").isActive(true).tenancy(tenancy).build();
		user17.getRoles().add(Member);
		saveUser(user17);
		Address a17 = Address.builder().line2("11 rue de la Paix").line1("").postCode("35000").city("Rennes")
				.user(user17).build();
		ContactInfo ci17 = ContactInfo.builder().name("Lemoine").firstName("Laura").phoneNumber("0622345678")
				.user(user17).build();
		addressService.save(a17);
		contactInfoService.save(ci17);
		user17.setAddress(a17);
		user17.setContactInfo(ci17);
		updateUser(user17);

		User user18 = User.builder().email("antoine.morel@example" + tenancy.getTenancyId() + ".com")
				.password("AMAPamap11@").isActive(true).tenancy(tenancy).build();
		user18.getRoles().add(Member);
		saveUser(user18);
		Address a18 = Address.builder().line2("14 rue des Champs").line1("").postCode("54000").city("Nancy")
				.user(user18).build();
		ContactInfo ci18 = ContactInfo.builder().name("Morel").firstName("Antoine").phoneNumber("0686123456")
				.user(user18).build();
		addressService.save(a18);
		contactInfoService.save(ci18);
		user18.setAddress(a18);
		user18.setContactInfo(ci18);
		updateUser(user18);

		User user19 = User.builder().email("laurence.vincent@example" + tenancy.getTenancyId() + ".com")
				.password("AMAPamap11@").isActive(true).tenancy(tenancy).build();
		user19.getRoles().add(Supplier);
		saveUser(user19);
		Address a19 = Address.builder().line2("5 rue de la Liberté").line1("Appartement 2").postCode("74000")
				.city("Annecy").user(user19).build();
		ContactInfo ci19 = ContactInfo.builder().name("Vincent").firstName("Laurence").phoneNumber("0623456789")
				.user(user19).build();
		CompanyDetails cd19 = CompanyDetails.builder().companyName("Aux pommiers dorés").siretNumber("16435329487654")
				.user(user19).build();
		companyDetailsService.save(cd19);
		addressService.save(a19);
		contactInfoService.save(ci19);
		user19.setAddress(a19);
		user19.setContactInfo(ci19);
		user19.setCompanyDetails(cd19);
		updateUser(user19);

		User user20 = User.builder().email("bernard.morvan@example" + tenancy.getTenancyId() + ".com")
				.password("AMAPamap11@").isActive(true).tenancy(tenancy).build();
		user20.getRoles().add(Member);
		saveUser(user20);
		Address a20 = Address.builder().line2("23 rue de la Lune").line1("").postCode("56000").city("Vannes")
				.user(user20).build();
		ContactInfo ci20 = ContactInfo.builder().name("Morvan").firstName("Bernard").phoneNumber("0612347890")
				.user(user20).build();
		addressService.save(a20);
		contactInfoService.save(ci20);
		user20.setAddress(a20);
		user20.setContactInfo(ci20);
		updateUser(user20);

	}

	public User saveUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	public User updateUser(User user) {
		return userRepository.save(user);
	}

	/******************************************************
	 * FIRST TENANCY
	 ********************************************************/

	@Transactional
	public void tenancyInit() throws IOException {
		// Création des Options
		Options potager = Options.builder().option1Active(false).option2Active(false).build();
		Options verger = Options.builder().option1Active(true).option2Active(false).build();
		Options ferme = Options.builder().option1Active(true).option2Active(true).build();

		// Now embedded
		// optionsRepository.save(potager);
		// optionsRepository.save(verger);
		// optionsRepository.save(ferme);

		// Création d'une Tenancy
		Tenancy t1 = Tenancy.builder().tenancyName("BioColi").tenancyAlias("biocoli")
				.tenancySlogan("Bio & Local, avec un zeste de folie!").email("contact@biocoli.fr")
				.address(new Address("", "12 avenue de la localité", "69000", "Lyon")).dateCreated(LocalDateTime.now())
				.dateLastModified(LocalDateTime.now()).tenancyLatitude("45.7400000") // lyon
				.options(potager).tenancyLongitude("4.6370000").membershipFeePrice(new BigDecimal("10.0")).build();

		// Création du ContactInfo pour la Tenancy
		ContactInfo contactInfo1 = ContactInfo.builder().name("Jean").firstName("Dupont").phoneNumber("0612345678")
				.build();
		t1.setContactInfo(contactInfo1);

		// Chargement des images en Base64
		String imagelogo = loadImageFromResources("logobiocoli.png");

		// Création du Graphism pour Tenancy
		Graphism graphism1 = Graphism.builder().colorPalette(ColorPalette.PALETTE1).fontChoice(FontChoice.FUTURA)
				.logoImgType("image/png").logoImg(imagelogo).build();
		t1.setGraphism(graphism1);

		// Création du HomePageContent pour la Tenancy
		HomePageContent homePageContent1 = HomePageContent.builder().subTitle("UN PANIER BIO, LOCAL et de SAISON")
				.showSuppliers(true).tenancy(t1) // Association de HomePageContent avec Tenancy
				.build();

		// Création des 4 ContentBlocks pour la Tenancy
		String imagevalue0 = loadImageFromResources("presentationbiocoli.png");
		String imagevalue1 = loadImageFromResources("value1.png");
		String imagevalue2 = loadImageFromResources("value2.png");
		String imagevalue3 = loadImageFromResources("value3.png");

		// 1. Le ContentBlock pour la présentation de l'AMAP
		ContentBlock presentationBlock = ContentBlock.builder().isValue(false).contentTitle("Présentation de BioColi")
				.contentText(
						"BioColi est une AMAP dédiée à la distribution de paniers bio, locaux et de saison, visant à reconnecter les consommateurs avec des produits de qualité, cultivés près de chez eux. À travers notre plateforme, nous mettons en avant des produits frais, issus de fermes locales qui respectent des pratiques agricoles durables. Notre engagement va au-delà de la simple consommation : nous offrons aux membres une expérience enrichissante, où l’échange direct avec les producteurs permet de mieux comprendre l'origine des produits et de soutenir une agriculture plus respectueuse de l'environnement. Rejoindre BioColi, c'est faire le choix d’un avenir plus responsable, local et respectueux de la planète.")

				.contentImgName("value0.png").contentImgTypeMIME("image/png").contentImg(imagevalue0) 
				.homePageContent(homePageContent1).build();

		// 2. ContentBlock pour la première valeur de l'AMAP
		ContentBlock valueBlock1 = ContentBlock.builder().isValue(true) // Ce block représente une valeur de l'AMAP
				.contentTitle("Soutien à l'Agriculture Durable")
				.contentText(
						"Chez BioColi, nous nous engageons fermement à soutenir l'agriculture durable en collaborant avec des producteurs locaux qui partagent notre vision d’une agriculture respectueuse de l’environnement. Nos fermes partenaires adoptent des pratiques de culture biologique, préservent la biodiversité et minimisent l’utilisation de produits chimiques. En vous inscrivant chez BioColi, vous contribuez à encourager des pratiques agricoles respectueuses des écosystèmes, permettant ainsi de protéger les sols, l'eau et la santé des générations futures. Nous croyons que chaque geste compte, et que chaque panier de produits bio que vous consommez est un soutien direct à un modèle agricole plus vertueux.")
				.contentImgName("value1.png").contentImgTypeMIME("image/png").contentImg(imagevalue1)

				.homePageContent(homePageContent1).build();

		// 3. ContentBlock pour la deuxième valeur de l'AMAP
		ContentBlock valueBlock2 = ContentBlock.builder().isValue(true) // Ce block représente une valeur de l'AMAP
				.contentTitle("Transparence et Traçabilité")
				.contentText(
						"La transparence et la traçabilité sont au cœur de notre démarche chez BioColi. Chaque produit que vous recevez dans votre panier est issu de fermes locales partenaires, et nous nous assurons que vous puissiez suivre l’intégralité du parcours de vos produits. Grâce à une chaîne de production et de distribution maîtrisée, nous garantissons la qualité et la provenance de chaque ingrédient. Cette transparence vous permet de savoir exactement d'où provient ce que vous consommez, de la ferme jusqu’à votre assiette, et de soutenir des pratiques agricoles éthiques et responsables. Chez BioColi, nous pensons que la confiance se construit sur des informations claires et accessibles, et nous mettons tout en œuvre pour que vous ayez l’assurance que vos choix sont les bons.")
				.contentImgName("value1.png").contentImgTypeMIME("image/png").contentImg(imagevalue2)
				.homePageContent(homePageContent1).build();

		// 4. ContentBlock pour la troisième valeur de l'AMAP
		ContentBlock valueBlock3 = ContentBlock.builder().isValue(true) // Ce block représente une valeur de l'AMAP
				.contentTitle("Frais et Local")
				.contentText(
						"Nos produits sont soigneusement récoltés à leur apogée, garantissant leur fraîcheur optimale, et sont livrés directement des fermes locales à votre domicile, en respectant les circuits courts. Cette approche non seulement vous assure des produits ultra-frais, mais réduit également notre empreinte carbone en limitant les transports longue distance. En choisissant BioColi, vous faites le choix de soutenir l'économie locale tout en réduisant l'impact environnemental des produits que vous consommez. Nos producteurs, fiers de leurs savoir-faire, cultivent des fruits, légumes et autres produits dans le respect des saisons, vous offrant ainsi des aliments naturellement savoureux, tout en préservant la planète.")
				.contentImgName("value1.png").contentImgTypeMIME("image/png").contentImg(imagevalue3)
				.homePageContent(homePageContent1).build();

		// Ajouter les ContentBlocks dans HomePageContent
		homePageContent1.getContents().add(presentationBlock);
		homePageContent1.getContents().add(valueBlock1);
		homePageContent1.getContents().add(valueBlock2);
		homePageContent1.getContents().add(valueBlock3);

		// Création et configuration du PickUpSchedule
		PickUpSchedule pickUpSchedule1 = PickUpSchedule.builder().dayOfWeek(DayOfWeek.MONDAY) // Jour de ramassage
				.startHour(LocalTime.of(12, 0)) // Heure de début
				.endHour(LocalTime.of(19, 0)) // Heure de fin
				.build();
		t1.setPickUpSchedule(pickUpSchedule1);

		// Associer le HomePageContent à la Tenancy
		t1.setHomePageContent(homePageContent1);

		// Sauvegarder la Tenancy avec ses ContentBlocks et HomePageContent
		tenancyRepository.save(t1);

		/******************************************************
		 * SECOND TENANCY
		 ********************************************************/
		// Création d'une Tenancy
		Tenancy t2 = Tenancy.builder().tenancyName("Agrinov").tenancyAlias("agrinov")
				.tenancySlogan("L'innovation au service de l'agriculture de proximité").email("agrinov@gmail.com")
				.address(new Address("", "Cave voutée de la Garenne Valentin", "63000", "Clermont-Ferrand"))
				.dateCreated(LocalDateTime.now()).dateLastModified(LocalDateTime.now()).tenancyLatitude("45.6150000") // clermont-ferrand
				.options(verger).tenancyLongitude("3.2680000").membershipFeePrice(new BigDecimal("15.0")).build();

		// Création du ContactInfo pour la Tenancy
		ContactInfo contactInfo2 = ContactInfo.builder().name("Jean").firstName("Dupont").phoneNumber("0622345678")
				.build();
		t2.setContactInfo(contactInfo2);

		// Chargement des images en Base64
		String imagelogo2 = loadImageFromResources("logoagrinov.png");
		// Création du Graphism pour Tenancy
		Graphism graphism2 = Graphism.builder().colorPalette(ColorPalette.PALETTE2).fontChoice(FontChoice.NUNITO)
				.logoImgType("image/png").logoImg(imagelogo2).build();
		t2.setGraphism(graphism2);

		// Création du HomePageContent pour la Tenancy
		HomePageContent homePageContent2 = HomePageContent.builder().subTitle("Un panier bio, local et de saison.")
				.showSuppliers(true).tenancy(t2) // Association de HomePageContent avec Tenancy
				.build();
		
		String imagevalueZ = loadImageFromResources("presentationagrinov.png");
		String imagevalueA = loadImageFromResources("valuet2a.png");
		String imagevalueB = loadImageFromResources("valuet2b.png");
		String imagevalueC = loadImageFromResources("valuet2c.png");

		// Création des 4 ContentBlock pour la Tenancy

		// 1. Le ContentBlock pour la présentation de l'AMAP
		ContentBlock presentationBlock2 = ContentBlock.builder().isValue(false).contentTitle("Présentation d'Agrinov")
				.contentText(
						"Agrinov est une AMAP qui a pour mission de promouvoir l’agriculture locale, bio et responsable, en facilitant l’accès à des paniers de produits de saison. En tant que plateforme, Agrinov permet aux consommateurs de soutenir directement les producteurs locaux et de participer à un modèle de consommation plus durable et respectueux de l'environnement. Nous mettons en valeur une sélection de produits frais, cultivés dans des fermes locales qui respectent des méthodes agricoles écologiques. Chaque panier livré chez vous porte l’engagement d’une agriculture plus verte, plus solidaire et plus transparente, contribuant ainsi à un cercle vertueux entre producteurs et consommateurs. Rejoindre Agrinov, c’est prendre part à un projet de société, où l'alimentation saine et locale est au cœur de notre action pour un monde plus durable.")
				.contentImgName("valuet2a.png").contentImgTypeMIME("image/png").contentImg(imagevalueZ) 
				.homePageContent(homePageContent2).build();

		// 2. ContentBlock pour la première valeur de l'AMAP
		ContentBlock valueBlock21 = ContentBlock.builder().isValue(true) // Ce block représente une valeur de l'AMAP
				.contentTitle("Soutien à l'Agriculture Durable")
				.contentText(
						"Agrinov s’engage pleinement à soutenir l’agriculture durable en collaborant avec des fermes locales pratiquant une agriculture respectueuse de l’environnement. Nos partenaires utilisent des techniques agricoles biologiques qui préservent les sols, la biodiversité et l’eau, tout en réduisant les émissions de gaz à effet de serre. En choisissant Agrinov, vous contribuez à une production alimentaire plus responsable, tout en soutenant un modèle agricole qui respecte la nature et les générations futures. Nos membres participent à une démarche active de préservation de l’environnement, en consommant des produits frais et locaux, en saison, réduisant ainsi leur empreinte écologique.")
				.contentImgName("valuet2b.png").contentImgTypeMIME("image/png").contentImg(imagevalueA)
				.homePageContent(homePageContent2) // Association avec HomePageContent
				.build();

		// 3. ContentBlock pour la deuxième valeur de l'AMAP
		ContentBlock valueBlock22 = ContentBlock.builder().isValue(true) // Ce block représente une valeur de l'AMAP
				.contentTitle("Transparence et Traçabilité")
				.contentText(
						"À Agrinov, la transparence est au cœur de notre démarche. Tous les produits que nous distribuons proviennent de fermes locales et sont totalement traçables. Nous garantissons à nos membres une transparence totale sur l’origine de chaque produit, de la ferme à l’assiette. Cette transparence vous permet de savoir exactement qui produit ce que vous consommez, et sous quelles conditions. Chaque produit est accompagné de son parcours, du champ jusqu’à la livraison, afin que vous puissiez être certain de soutenir une agriculture respectueuse, éthique et locale. Nous croyons fermement que la confiance se bâtit sur la transparence et l'accès à l'information.")
				.contentImgName("valuet2c.png").contentImgTypeMIME("image/png").contentImg(imagevalueB)
				.homePageContent(homePageContent2) // Association avec HomePageContent
				.build();

		// 4. ContentBlock pour la troisième valeur de l'AMAP
		ContentBlock valueBlock23 = ContentBlock.builder().isValue(true) // Ce block représente une valeur de l'AMAP
				.contentTitle("Frais et Local")
				.contentText(
						"Les produits que vous recevez via Agrinov sont toujours frais, car ils proviennent directement des fermes locales, cueillis à leur apogée de maturité. Ce modèle de distribution permet de garantir une qualité exceptionnelle et une fraîcheur incomparable, tout en réduisant l’impact environnemental lié au transport. En choisissant nos paniers, vous participez activement à une économie circulaire, en soutenant des fermes locales qui cultivent des produits en fonction des saisons. Chaque panier est une invitation à découvrir des produits nouveaux, frais et locaux, qui respectent les cycles naturels de la terre et vous offrent un goût authentique.")
				.contentImgName("value1.png").contentImgTypeMIME("image/png").contentImg(imagevalueC)
				.homePageContent(homePageContent2).build();

		// Ajouter les ContentBlock dans HomePageContent
		homePageContent2.getContents().add(presentationBlock2);
		homePageContent2.getContents().add(valueBlock21);
		homePageContent2.getContents().add(valueBlock22);
		homePageContent2.getContents().add(valueBlock23);

		// Création et configuration du PickUpSchedule
		PickUpSchedule pickUpSchedule2 = PickUpSchedule.builder().dayOfWeek(DayOfWeek.SATURDAY) // Jour de ramassage
				.startHour(LocalTime.of(17, 0)) // Heure de début
				.endHour(LocalTime.of(20, 0)) // Heure de fin
				.build();
		t2.setPickUpSchedule(pickUpSchedule2);

		// Associer le HomePageContent à la Tenancy
		t2.setHomePageContent(homePageContent2);

		// Sauvegarder la Tenancy avec ses ContentBlock et HomePageContent
		tenancyRepository.save(t2);

		/******************************************************
		 * THIRD TENANCY
		 ********************************************************/
		// Création d'une Tenancy
		Tenancy t3 = Tenancy.builder().tenancyName("Groots").tenancyAlias("groots")
				.tenancySlogan("Des racines aujourd'hui pour des arbres demain").email("groots@gmail.com")
				.address(new Address("", "16 rue D'Astorg", "44100", "Nantes")).dateCreated(LocalDateTime.now())
				.dateLastModified(LocalDateTime.now()).tenancyLatitude("46.5050000") // nantes -> exemple peu de
																						// dénivelés
				.options(ferme).tenancyLongitude("-0.5140000").membershipFeePrice(new BigDecimal("20.0")).build();

		// Création du ContactInfo pour la Tenancy
		ContactInfo contactInfo3 = ContactInfo.builder().name("Jean").firstName("Dupont").phoneNumber("0612345678")
				.build();
		t3.setContactInfo(contactInfo3);

		// Chargement des images en Base64
		String imagelogo3 = loadImageFromResources("logogroots.png");

		// Création du Graphism pour Tenancy
		Graphism graphism3 = Graphism.builder().colorPalette(ColorPalette.PALETTE3).fontChoice(FontChoice.AUDIOWIDE)
				.logoImgType("image/png").logoImg(imagelogo3).build();
		t3.setGraphism(graphism3);

		// Création du HomePageContent pour la Tenancy
		HomePageContent homePageContent3 = HomePageContent.builder().subTitle("Un panier bio, local et de saison.")
				.showSuppliers(true).tenancy(t3) // Association de HomePageContent avec Tenancy
				.build();

		
				String imagevalueA0 = loadImageFromResources("presentationgroots.png");
				String imagevalueA1 = loadImageFromResources("valuet3a.png");
				String imagevalueB1 = loadImageFromResources("valuet5c.png");
				String imagevalueC1 = loadImageFromResources("valuet3c.png");
				
		// Création des 4 ContentBlock pour la Tenancy

		// 1. Le ContentBlock pour la présentation de l'AMAP
		ContentBlock presentationBlock3 = ContentBlock.builder().isValue(false)
				.contentTitle("Groots - Connectez-vous aux producteurs locaux")
				.contentText(
						"Groots est une AMAP innovante qui vous connecte directement avec vos producteurs locaux. Notre objectif est de faciliter l'accès à des produits frais, bio et locaux tout en garantissant une meilleure transparence dans la chaîne de distribution. Chaque panier de produits est l'occasion de soutenir l'agriculture locale, de découvrir des produits de saison cultivés dans le respect de l'environnement, et de renforcer les liens entre les consommateurs et les producteurs. Grâce à Groots, vous avez la possibilité de participer activement à un modèle économique durable et solidaire, où la qualité des produits et la transparence de la provenance sont au cœur de notre démarche.")
				.contentImgName("value1.png").contentImgTypeMIME("image/png").contentImg(imagevalueA0) 
				.homePageContent(homePageContent3).build();

		// 2. ContentBlock pour la première valeur de l'AMAP
		ContentBlock valueBlock31 = ContentBlock.builder().isValue(true) // Ce block représente une valeur de l'AMAP
				.contentTitle("Soutien à l'Agriculture Durable")
				.contentText(
						"Groots soutient une agriculture durable qui respecte l'environnement et les principes de l'agroécologie. En favorisant les fermes locales qui adoptent des méthodes de culture biologiques et respectueuses des sols, nous participons activement à la préservation de la biodiversité et des ressources naturelles. Nos membres soutiennent des producteurs qui travaillent en harmonie avec la nature, en utilisant des pratiques agricoles visant à réduire l'empreinte écologique et à favoriser la santé des écosystèmes. Chaque panier acheté est un pas de plus vers un avenir plus vert et plus respectueux de notre planète.")
				.contentImgName("value3a.png").contentImgTypeMIME("image/png").contentImg(imagevalueA1)
				.homePageContent(homePageContent3) // Association avec HomePageContent
				.build();

		// 3. ContentBlock pour la deuxième valeur de l'AMAP
		ContentBlock valueBlock32 = ContentBlock.builder().isValue(true) // Ce block représente une valeur de l'AMAP
				.contentTitle("Transparence et Traçabilité")
				.contentText(
						"À Groots, nous croyons que la transparence est essentielle pour établir une relation de confiance entre les producteurs et les consommateurs. C'est pourquoi tous nos produits sont entièrement traçables. Chaque panier que vous recevez est une promesse de qualité et de transparence : vous pouvez suivre le parcours de chaque produit, depuis la ferme où il a été cultivé jusqu'à votre table. Grâce à notre plateforme, vous accédez à toutes les informations nécessaires pour connaître l'origine de vos produits et soutenir une économie locale et responsable.")
				.contentImgName("valuet3b.png").contentImgTypeMIME("image/png").contentImg(imagevalueB1)
				.homePageContent(homePageContent3) // Association avec HomePageContent
				.build();

		// 4. ContentBlock pour la troisième valeur de l'AMAP
		ContentBlock valueBlock33 = ContentBlock.builder().isValue(true) // Ce block représente une valeur de l'AMAP
				.contentTitle("Frais et Local")
				.contentText(
						"Groots s'engage à vous offrir des produits frais, cultivés localement et livrés directement des fermes aux consommateurs. En choisissant Groots, vous choisissez la fraîcheur, la qualité et la proximité. Nos fermes partenaires cultivent des produits de saison, cueillis à maturité et livrés dans les plus brefs délais pour garantir une fraîcheur optimale. Ce modèle de distribution réduit l'empreinte carbone, soutient l'agriculture locale et permet à nos membres de savourer des produits en pleine saison, tout en renforçant les liens avec les producteurs locaux.")
				.contentImgName("valuet3c.png").contentImgTypeMIME("image/png").contentImg(imagevalueC1)
				.homePageContent(homePageContent3).build();

		// Ajouter les ContentBlock dans HomePageContent
		homePageContent3.getContents().add(presentationBlock3);
		homePageContent3.getContents().add(valueBlock31);
		homePageContent3.getContents().add(valueBlock32);
		homePageContent3.getContents().add(valueBlock33);

		// Création et configuration du PickUpSchedule
		PickUpSchedule pickUpSchedule3 = PickUpSchedule.builder().dayOfWeek(DayOfWeek.TUESDAY) // Jour de ramassage
				.startHour(LocalTime.of(17, 0)) // Heure de début
				.endHour(LocalTime.of(20, 0)) // Heure de fin
				.build();
		t3.setPickUpSchedule(pickUpSchedule3);

		// Associer le HomePageContent à la Tenancy
		t3.setHomePageContent(homePageContent3);

		// Sauvegarder la Tenancy avec ses ContentBlock et HomePageContent
		tenancyRepository.save(t3);

		/******************************************************
		 * FOURTH TENANCY
		 ********************************************************/
		Tenancy t4 = Tenancy.builder().tenancyName("GreenMaven").tenancyAlias("greenmaven")
				.tenancySlogan("Un havre de fraîcheur pour vos produits locaux").email("greenHaven@gmail.com")
				.address(new Address("", "24 rue des Lilas", "65240", "Aulon")).dateCreated(LocalDateTime.now())
				.dateLastModified(LocalDateTime.now()).tenancyLatitude("46.0170000") // aulon, creuse
				.options(potager).tenancyLongitude("1.6550000").membershipFeePrice(new BigDecimal("10.0")).build();

		// Création du ContactInfo pour la Tenancy
		ContactInfo contactInfo4 = ContactInfo.builder().name("Jean").firstName("Dupont").phoneNumber("0612345678")
				.build();
		t4.setContactInfo(contactInfo4);

		// Chargement des images en Base64
		String imagelogo4 = loadImageFromResources("logogreenmaven.png");

		Graphism graphism4 = Graphism.builder().colorPalette(ColorPalette.PALETTE4).fontChoice(FontChoice.GRENZE)
				.logoImgType("image/png").logoImg(imagelogo4).build();
		t4.setGraphism(graphism4);

		HomePageContent homePageContent4 = HomePageContent.builder().subTitle("Manger sainement avec GreenHaven")
				.showSuppliers(true).tenancy(t4).build();
		
		String imagevalueB0 = loadImageFromResources("presentationgreenmaven1.png");
		String imagevalueA2 = loadImageFromResources("valuet4a.png");
		String imagevalueB2 = loadImageFromResources("valuet4b.png");
		String imagevalueC2 = loadImageFromResources("valuet4c.png");

		// 1. Le ContentBlock pour la présentation de GreenHaven
		ContentBlock presentationBlock4 = ContentBlock.builder().isValue(false)
				.contentTitle("Présentation de GreenHaven")
				.contentText(
						"GreenHaven est une AMAP dédiée à la durabilité, à la qualité des produits et à la préservation de notre environnement. Nous proposons des paniers de produits locaux, cultivés dans le respect des cycles naturels et des principes de l'agriculture durable. Chaque panier contient des produits frais, soigneusement sélectionnés, cultivés par nos fermiers partenaires dans une démarche écoresponsable. En rejoignant GreenHaven, vous soutenez des pratiques agricoles qui respectent la biodiversité et l'équilibre écologique tout en bénéficiant de produits de saison, gages de qualité et de fraîcheur.")
				.contentImgName("value1.png").contentImgTypeMIME("image/png").contentImg(imagevalueB0)
				.homePageContent(homePageContent4).build();

		// 2. ContentBlock pour la première valeur de l'AMAP : Respect de la Nature
		ContentBlock valueBlock41 = ContentBlock.builder().isValue(true).contentTitle("Respect de la Nature")
				.contentText(
						"À GreenHaven, nous plaçons le respect de la nature au cœur de nos pratiques. Chaque producteur avec lequel nous collaborons adopte des méthodes de culture qui préservent la biodiversité, limitent l'utilisation de produits chimiques et favorisent des solutions naturelles. Nos fermiers prennent soin de la terre, respectent les cycles de la nature et utilisent des techniques qui restaurent la fertilité des sols. En choisissant GreenHaven, vous soutenez un modèle agricole durable qui protège notre planète pour les générations futures.")
				.contentImgName("valuet4a.png").contentImgTypeMIME("image/png").contentImg(imagevalueA2)
				.homePageContent(homePageContent4).build();

		// 3. ContentBlock pour la deuxième valeur de l'AMAP : Produits de Qualité
		ContentBlock valueBlock42 = ContentBlock.builder().isValue(true).contentTitle("Produits de Qualité")
				.contentText(
						"La qualité de nos produits est une priorité absolue. Chaque panier que nous proposons contient des produits soigneusement sélectionnés pour leur fraîcheur, leur saveur et leur qualité nutritionnelle. Nos fermiers, partenaires de confiance, cultivent des fruits, légumes et produits dérivés selon des critères stricts, garantissant des produits de saison et respectueux des normes les plus élevées en matière d'agriculture biologique et durable. En choisissant GreenHaven, vous êtes assuré de consommer des produits sains et de qualité supérieure, cultivés dans le respect de l'environnement.")
				.contentImgName("valuetab.png").contentImgTypeMIME("image/png").contentImg(imagevalueB2)
				.homePageContent(homePageContent4).build();

		// 4. ContentBlock pour la troisième valeur de l'AMAP : Communauté Locale
		ContentBlock valueBlock43 = ContentBlock.builder().isValue(true).contentTitle("Communauté Locale").contentText(
				"GreenHaven est une AMAP ancrée dans sa communauté locale. Nous travaillons en étroite collaboration avec des producteurs locaux passionnés qui partagent notre vision d'une agriculture durable et responsable. Notre objectif est de renforcer les liens entre les membres de la communauté, de soutenir les fermiers locaux et de favoriser une économie circulaire. En rejoignant GreenHaven, vous devenez une partie prenante de ce réseau solidaire, où les produits de qualité se rencontrent avec des valeurs humaines fortes.")
				.contentImgName("valuetac.png").contentImgTypeMIME("image/png").contentImg(imagevalueC2)
				.homePageContent(homePageContent4).build();

		homePageContent4.getContents().add(presentationBlock4);
		homePageContent4.getContents().add(valueBlock41);
		homePageContent4.getContents().add(valueBlock42);
		homePageContent4.getContents().add(valueBlock43);

		// Création et configuration du PickUpSchedule
		PickUpSchedule pickUpSchedule4 = PickUpSchedule.builder().dayOfWeek(DayOfWeek.FRIDAY) // Jour de ramassage
				.startHour(LocalTime.of(14, 0)) // Heure de début
				.endHour(LocalTime.of(20, 0)) // Heure de fin
				.build();
		t4.setPickUpSchedule(pickUpSchedule4);

		t4.setHomePageContent(homePageContent4);

		tenancyRepository.save(t4);

		/******************************************************
		 * FIFTH TENANCY
		 ********************************************************/
		Tenancy t5 = Tenancy.builder().tenancyName("La carotte de Chantenay").tenancyAlias("lacarottechantenay")
				.tenancySlogan("Des champs verts pour des générations durables").email("lacarottedechantenay@gmail.com")
				.address(new Address("", "25 avenue des Champs Verts", "74000", "Annecy"))
				.dateCreated(LocalDateTime.now()).dateLastModified(LocalDateTime.now()).tenancyLatitude("45.8440000") // annecy,
				.options(ferme).tenancyLongitude("6.1940000").membershipFeePrice(new BigDecimal("15.0")).build();

		// Création du ContactInfo pour la Tenancy
		ContactInfo contactInfo5 = ContactInfo.builder().name("Jean").firstName("Dupont").phoneNumber("0612345678")
				.build();
		t5.setContactInfo(contactInfo5);

		// Chargement des images en Base64
		String imagelogo5 = loadImageFromResources("logolacarottechantenay.png");

		// Création du Graphism pour la Tenancy
		Graphism graphism5 = Graphism.builder().colorPalette(ColorPalette.PALETTE5).fontChoice(FontChoice.CAVEAT)
				.logoImgType("image/png").logoImg(imagelogo5).build();
		t5.setGraphism(graphism5);

		// Création du HomePageContent pour la Tenancy
		HomePageContent homePageContent5 = HomePageContent.builder().subTitle("Cultivons ensemble un avenir durable.")
				.showSuppliers(true).tenancy(t5).build();

		
		String imagevalueC0 = loadImageFromResources("presentationlacarottechantenay.png");
		String imagevalueA3 = loadImageFromResources("valuet5a.png");
		String imagevalueB3 = loadImageFromResources("valuet5b.png");
		String imagevalueC3 = loadImageFromResources("valuet5c.png");

		// Création des ContentBlock
		// 1. Le ContentBlock pour la présentation de La carotte de Chantenay
		ContentBlock presentationBlock5 = ContentBlock.builder().isValue(false)
				.contentTitle("Présentation de La carotte de Chantenay")
				.contentText(
						"La carotte de Chantenay est une AMAP dédiée à la production de légumes biologiques, locaux et de qualité, ancrée dans un engagement durable envers l'environnement. Nous mettons en avant la carotte de Chantenay, un produit emblématique, tout en diversifiant notre offre de légumes de saison cultivés avec passion. En rejoignant notre communauté, vous soutenez l'agriculture biologique tout en accédant à des produits frais et savoureux, cultivés dans le respect de la nature.")
				.contentImgName("value1.png").contentImgTypeMIME("image/png").contentImg(imagevalueC0)
				.homePageContent(homePageContent5).build();

		// 2. ContentBlock pour la première valeur de l'AMAP : Écologie et Durabilité
		ContentBlock valueBlock51 = ContentBlock.builder().isValue(true).contentTitle("Écologie et Durabilité")
				.contentText(
						"À La carotte de Chantenay, chaque produit soutient une agriculture écologique et durable. Nous nous engageons à respecter les principes de l'agriculture biologique et à utiliser des méthodes respectueuses de l'environnement. Nous préservons la biodiversité et limitons l'utilisation d'intrants chimiques, afin de cultiver des produits sains et respectueux des cycles naturels. Choisir notre AMAP, c'est participer activement à la protection de la planète en soutenant des pratiques agricoles responsables.")
				.contentImgName("valuet5a.png").contentImgTypeMIME("image/png").contentImg(imagevalueA3)
				.homePageContent(homePageContent5).build();

		// 3. ContentBlock pour la deuxième valeur de l'AMAP : Engagement Communautaire
		ContentBlock valueBlock52 = ContentBlock.builder().isValue(true).contentTitle("Engagement Communautaire")
				.contentText(
						"Nous croyons fermement en l'importance de l'engagement communautaire et en la collaboration avec les acteurs locaux. La carotte de Chantenay œuvre pour impliquer la communauté dans des actions concrètes en faveur de l'environnement et de l'agriculture durable. Nous organisons des événements, des ateliers et des rencontres avec nos producteurs afin de renforcer les liens entre les membres de l'AMAP et de sensibiliser à l'importance de soutenir l'agriculture locale. Ensemble, nous construisons un réseau solidaire qui profite à tous.")
				.contentImgName("valuet5b.png").contentImgTypeMIME("image/png").contentImg(imagevalueB3)
				.homePageContent(homePageContent5).build();

		// 4. ContentBlock pour la troisième valeur de l'AMAP : Qualité Supérieure
		ContentBlock valueBlock53 = ContentBlock.builder().isValue(true).contentTitle("Qualité Supérieure").contentText(
				"La carotte de Chantenay et les autres produits que nous proposons sont soigneusement sélectionnés pour leur goût et leur qualité. Chaque légume est cultivé avec soin dans des conditions optimales pour garantir une fraîcheur incomparable et des saveurs authentiques. Nos producteurs sont des experts passionnés, soucieux de produire des aliments sains et savoureux tout en respectant l'environnement. En choisissant notre AMAP, vous profitez de produits de saison, locaux et de qualité supérieure.")
				.contentImgName("value1.png").contentImgTypeMIME("image/png").contentImg(imagevalueC3)
				.homePageContent(homePageContent5).build();

		homePageContent5.getContents().add(presentationBlock5);
		homePageContent5.getContents().add(valueBlock51);
		homePageContent5.getContents().add(valueBlock52);
		homePageContent5.getContents().add(valueBlock53);

		// Création et configuration du PickUpSchedule
		PickUpSchedule pickUpSchedule5 = PickUpSchedule.builder().dayOfWeek(DayOfWeek.FRIDAY) // Jour de ramassage
				.startHour(LocalTime.of(9, 0)) // Heure de début
				.endHour(LocalTime.of(19, 0)) // Heure de fin
				.build();
		t5.setPickUpSchedule(pickUpSchedule5);

		t5.setHomePageContent(homePageContent5);

		// Sauvegarder la Tenancy
		tenancyRepository.save(t5);

		/******************************************************
		 * SIXTH TENANCY
		 ********************************************************/
		Tenancy t6 = Tenancy.builder().tenancyName("TerraLocal").tenancyAlias("terralocal")
				.tenancySlogan("Des produits de la Terre pour les gens d'ici").email("terralocal@gmail.com")
				.address(new Address("", "10 rue des Cultures", "13260", "Cassis")).dateCreated(LocalDateTime.now())
				.dateLastModified(LocalDateTime.now()).tenancyLatitude("43.2140000") // cassis, bord de mer
				.options(verger).tenancyLongitude("5.5370000").membershipFeePrice(new BigDecimal("20.0")).build();

		// Création du ContactInfo pour la Tenancy
		ContactInfo contactInfo6 = ContactInfo.builder().name("Jean").firstName("Dupont").phoneNumber("0612345678")
				.build();
		t6.setContactInfo(contactInfo6);

		// Chargement des images en Base64
		String imagelogo6 = loadImageFromResources("logoterralocal.png");

		// Création du Graphism pour la Tenancy
		Graphism graphism6 = Graphism.builder().colorPalette(ColorPalette.PALETTE6).fontChoice(FontChoice.LIMELIGHT)
				.logoImgType("image/png").logoImg(imagelogo6).build();
		t6.setGraphism(graphism6);

		// Création du HomePageContent pour la Tenancy
		HomePageContent homePageContent6 = HomePageContent.builder().subTitle("Vivez local, mangez local.")
				.showSuppliers(true).tenancy(t6).build();
		
		String imagevalueD0 = loadImageFromResources("presentationterralocal.png");
		String imagevalueA4 = loadImageFromResources("valuet6a.png");
		String imagevalueB4 = loadImageFromResources("valuet6c.png");
		String imagevalueC4 = loadImageFromResources("valuet5c.png");

		// 1. Le ContentBlock pour la présentation de TerraLocal
		ContentBlock presentationBlock6 = ContentBlock.builder().isValue(false)
				.contentTitle("Présentation de TerraLocal")
				.contentText(
						"TerraLocal est une AMAP engagée à mettre en avant les produits locaux afin de soutenir les agriculteurs de notre région. Nous croyons que chaque consommateur mérite d'avoir accès à des produits de qualité, cultivés près de chez lui, dans un respect total de l'environnement et des producteurs. Notre mission est de rapprocher les citoyens des producteurs locaux pour un futur plus durable, en offrant des produits frais, sains et responsables. Grâce à notre AMAP, vous soutenez directement les fermes locales tout en bénéficiant de produits locaux de haute qualité.")
				.contentImgName("value1.png").contentImgTypeMIME("image/png").contentImg(imagevalueD0)
				.homePageContent(homePageContent6).build();

		// 2. ContentBlock pour la première valeur de l'AMAP : Origine Garantie
		ContentBlock valueBlock61 = ContentBlock.builder().isValue(true).contentTitle("Origine Garantie").contentText(
				"À TerraLocal, nous certifions l'origine locale et biologique de tous nos produits. Chaque article que nous proposons provient directement des fermes de notre région, cultivé dans le respect des normes agricoles biologiques les plus strictes. Nos producteurs sont des artisans passionnés, soucieux de préserver l'environnement et de produire des aliments sains et goûteux. Choisir TerraLocal, c'est avoir la garantie d'une traçabilité totale et d'un respect profond des cycles naturels.")
				.contentImgName("value6a.png").contentImgTypeMIME("image/png").contentImg(imagevalueA4)
				.homePageContent(homePageContent6).build();

		// 3. ContentBlock pour la deuxième valeur de l'AMAP : Convivialité et Partage
		ContentBlock valueBlock62 = ContentBlock.builder().isValue(true).contentTitle("Convivialité et Partage")
				.contentText(
						"Nous mettons un accent particulier sur la convivialité et le partage au sein de notre communauté. TerraLocal organise régulièrement des événements, des rencontres et des ateliers pour renforcer les liens entre nos membres et nos producteurs locaux. Ces moments de partage permettent d’échanger autour de la Terre, de la production locale et de l'importance de consommer responsable. Nous croyons que la solidarité et la collaboration sont des piliers essentiels pour un monde plus juste et durable.")
				.contentImgName("valuet6c.png").contentImgTypeMIME("image/png").contentImg(imagevalueB4)
				.homePageContent(homePageContent6).build();

		// 4. ContentBlock pour la troisième valeur de l'AMAP : Respect des Saisons
		ContentBlock valueBlock63 = ContentBlock.builder().isValue(true).contentTitle("Respect des Saisons")
				.contentText(
						"Chez TerraLocal, nous respectons les cycles naturels des saisons pour vous offrir uniquement des produits de saison, cultivés dans les meilleures conditions. Nous croyons que chaque légume ou fruit a son moment idéal pour pousser et se récolter, et nous nous engageons à respecter ces cycles pour vous offrir des produits toujours frais, pleins de saveurs et bénéfiques pour votre santé. En consommant des produits de saison, vous contribuez à préserver l'environnement tout en soutenant les producteurs locaux.")
				.contentImgName("valuet6b.png").contentImgTypeMIME("image/png").contentImg(imagevalueC4)
				.homePageContent(homePageContent6).build();

		homePageContent6.getContents().add(presentationBlock6);
		homePageContent6.getContents().add(valueBlock61);
		homePageContent6.getContents().add(valueBlock62);
		homePageContent6.getContents().add(valueBlock63);

		t6.setHomePageContent(homePageContent6);

		// Création et configuration du PickUpSchedule
		PickUpSchedule pickUpSchedule6 = PickUpSchedule.builder().dayOfWeek(DayOfWeek.WEDNESDAY) // Jour de ramassage
				.startHour(LocalTime.of(8, 0)) // Heure de début
				.endHour(LocalTime.of(19, 0)) // Heure de fin
				.build();
		t6.setPickUpSchedule(pickUpSchedule6);

		// Sauvegarder la Tenancy
		tenancyRepository.save(t6);

	}

	public void contractInitForTenancy(Tenancy tenancy) throws IOException {

		List<User> producers = userRepository.findByTenancyAndRole(tenancy, "Producteur");

		User producer0 = producers.get(0);
		User producer1 = producers.get(1);
		User producer2 = producers.get(2);
		User producer3 = producers.get(3);
		User producer4 = producers.get(4);

		String panierlegumesbio = loadImageFromResources("panierlegumesbio.png");
		String paniermixteautomne = loadImageFromResources("paniermixteautomne.png");
		String panierracine = loadImageFromResources("panierracine.png");
		String paniermixtehiver = loadImageFromResources("paniermixtehiver.png");
		String paniermixtebio = loadImageFromResources("paniermixtebio.png");
		String panierlegumeshiver = loadImageFromResources("panierlegumeshiver.png");
		String panierlegumesfraisprintemps = loadImageFromResources("panierlegumesfraisprintemps.png");
		String panierfruitsexotiques = loadImageFromResources("panierfruitsexotiques.png");
		String panierfruitsete = loadImageFromResources("panierfruitsete.png");
		String panierexotique = loadImageFromResources("panierexotique.png");
		String paniernoel = loadImageFromResources("paniernoel.png");

		List<Contract> contracts = new ArrayList<>();

		contracts.add(Contract.builder().contractName("Panier de légumes bio")
				.contractType(ContractType.VEGETABLES_CONTRACT)
				.contractDescription("Un panier hebdomadaire avec des légumes frais")
				.contractWeight(ContractWeight.AVERAGE).contractPrice(new BigDecimal("25.50"))
				.dateCreation(LocalDate.now()).startDate(LocalDate.of(2025, 2, 1)).endDate(LocalDate.of(2025, 6, 26))
				.deliveryRecurrence(DeliveryRecurrence.WEEKLY).quantity(15).shoppable(true).imageType("image/png")
				.pickUpSchedule(tenancy.getPickUpSchedule()).address(tenancy.getAddress()).tenancy(tenancy)
				.user(producer0).imageData(panierlegumesbio).build());

		contracts.add(Contract.builder().contractName("Panier mixte").contractType(ContractType.MIX_CONTRACT)
				.contractDescription("Un panier mensuel avec une variété de fruits et légumes de saison.")
				.contractWeight(ContractWeight.BIG).contractPrice(new BigDecimal("45.00")).dateCreation(LocalDate.now())
				.startDate(LocalDate.of(2024, 11, 1)).endDate(LocalDate.of(2025, 3, 18))
				.deliveryRecurrence(DeliveryRecurrence.MONTHLY).quantity(20).shoppable(true).imageType("image/png")
				.imageData(paniermixteautomne).pickUpSchedule(tenancy.getPickUpSchedule()).address(tenancy.getAddress())
				.tenancy(tenancy).user(producer2).build());

		contracts.add(Contract.builder().contractName("Panier de légumes d'hiver")
				.contractType(ContractType.VEGETABLES_CONTRACT)
				.contractDescription("Un panier bimensuel avec des légumes frais pour l'hiver.")
				.contractWeight(ContractWeight.SMALL).contractPrice(new BigDecimal("15.00"))
				.dateCreation(LocalDate.now()).startDate(LocalDate.of(2024, 12, 1)).endDate(LocalDate.of(2025, 5, 28))
				.deliveryRecurrence(DeliveryRecurrence.BIMONTHLY).quantity(5).shoppable(true).imageType("image/png")
				.imageData(panierlegumeshiver).pickUpSchedule(tenancy.getPickUpSchedule()).address(tenancy.getAddress())
				.tenancy(tenancy).user(producer3).build());

		contracts.add(Contract.builder().contractName("Panier fruité de Victor")
				.contractType(ContractType.FRUITS_CONTRACT)
				.contractDescription("Un panier hebdomadaire avec des fruits exotiques tels que mangues, ananas, etc.")
				.contractWeight(ContractWeight.AVERAGE).contractPrice(new BigDecimal("30.00"))
				.dateCreation(LocalDate.now()).startDate(LocalDate.of(2025, 1, 12)).endDate(LocalDate.of(2025, 9, 30))
				.deliveryRecurrence(DeliveryRecurrence.WEEKLY).quantity(15).shoppable(true).imageType("image/png")
				.imageData(panierfruitsexotiques).pickUpSchedule(tenancy.getPickUpSchedule())
				.address(tenancy.getAddress()).tenancy(tenancy).user(producer4).build());

		contracts.add(Contract.builder().contractName("Panier mixte post-Noël").contractType(ContractType.MIX_CONTRACT)
				.contractDescription("Un panier spécial Noël avec des produits festifs.")
				.contractWeight(ContractWeight.BIG).contractPrice(new BigDecimal("60.00")).dateCreation(LocalDate.now())
				.startDate(LocalDate.of(2024, 10, 1)).endDate(LocalDate.of(2025, 12, 25))
				.deliveryRecurrence(DeliveryRecurrence.MONTHLY).quantity(10).shoppable(true).imageType("image/png")
				.imageData(paniernoel).pickUpSchedule(tenancy.getPickUpSchedule()).address(tenancy.getAddress())
				.tenancy(tenancy).user(producer0).build());

		contracts.add(Contract.builder().contractName("Panier de fruits variés")
				.contractType(ContractType.FRUITS_CONTRACT).contractDescription("Pommes, poires, cerises, noix, raisin")
				.contractWeight(ContractWeight.AVERAGE).contractPrice(new BigDecimal("22.00"))
				.dateCreation(LocalDate.now()).startDate(LocalDate.of(2025, 1, 3)).endDate(LocalDate.of(2025, 5, 2))
				.deliveryRecurrence(DeliveryRecurrence.WEEKLY).quantity(12).shoppable(true).imageType("image/png")
				.imageData(panierfruitsete).pickUpSchedule(tenancy.getPickUpSchedule()).address(tenancy.getAddress())
				.tenancy(tenancy).user(producer0).build());

		contracts.add(Contract.builder().contractName("Panier de légumes frais")
				.contractType(ContractType.VEGETABLES_CONTRACT)
				.contractDescription("Carottes, navets, radis, laitue, épinards").contractWeight(ContractWeight.SMALL)
				.contractPrice(new BigDecimal("18.50")).dateCreation(LocalDate.now())
				.startDate(LocalDate.of(2025, 2, 1)).endDate(LocalDate.of(2025, 5, 6))
				.deliveryRecurrence(DeliveryRecurrence.WEEKLY).quantity(8).shoppable(true).imageType("image/png")
				.imageData(panierlegumesfraisprintemps).pickUpSchedule(tenancy.getPickUpSchedule())
				.address(tenancy.getAddress()).tenancy(tenancy).user(producer1).build());

		contracts.add(Contract.builder().contractName("Panier mixte - Hiver").contractType(ContractType.MIX_CONTRACT)
				.contractDescription("Pommes de terre, oignons, poireaux, pommes, oranges")
				.contractWeight(ContractWeight.BIG).contractPrice(new BigDecimal("40.00")).dateCreation(LocalDate.now())
				.startDate(LocalDate.of(2024, 12, 1)).endDate(LocalDate.of(2025, 3, 21))
				.deliveryRecurrence(DeliveryRecurrence.BIMONTHLY).quantity(20).shoppable(true).imageType("image/png")
				.imageData(paniermixtehiver).pickUpSchedule(tenancy.getPickUpSchedule()).address(tenancy.getAddress())
				.tenancy(tenancy).user(producer2).build());

		contracts.add(Contract.builder().contractName("Panier sucré")
				.contractType(ContractType.FRUITS_CONTRACT)
				.contractDescription("Mangues, ananas, kiwis, bananes, papayes").contractWeight(ContractWeight.AVERAGE)
				.contractPrice(new BigDecimal("30.00")).dateCreation(LocalDate.now())
				.startDate(LocalDate.of(2024, 11, 1)).endDate(LocalDate.of(2025, 9, 30))
				.deliveryRecurrence(DeliveryRecurrence.MONTHLY).quantity(10).shoppable(true).imageType("image/png")
				.imageData(panierexotique).pickUpSchedule(tenancy.getPickUpSchedule()).address(tenancy.getAddress())
				.tenancy(tenancy).user(producer3).build());

		contracts.add(Contract.builder().contractName("Panier de légumes racines")
				.contractType(ContractType.VEGETABLES_CONTRACT)
				.contractDescription("Betteraves, carottes, pommes de terre, céleri-rave, panais")
				.contractWeight(ContractWeight.BIG).contractPrice(new BigDecimal("35.00")).dateCreation(LocalDate.now())
				.startDate(LocalDate.of(2024, 10, 1)).endDate(LocalDate.of(2025, 5, 18))
				.deliveryRecurrence(DeliveryRecurrence.MONTHLY).quantity(18).shoppable(true).imageType("image/png")
				.imageData(panierracine).pickUpSchedule(tenancy.getPickUpSchedule()).address(tenancy.getAddress())
				.tenancy(tenancy).user(producer4).build());

		contracts.add(Contract.builder().contractName("Panier mixte bio - Printemps").contractType(ContractType.MIX_CONTRACT)
				.contractDescription("Tomates, poivrons, courgettes, abricots, melons")
				.contractWeight(ContractWeight.AVERAGE).contractPrice(new BigDecimal("25.00"))
				.dateCreation(LocalDate.now()).startDate(LocalDate.of(2025, 1, 9)).endDate(LocalDate.of(2025, 6, 21))
				.deliveryRecurrence(DeliveryRecurrence.WEEKLY).quantity(15).shoppable(true).imageType("image/png")
				.imageData(paniermixtebio).pickUpSchedule(tenancy.getPickUpSchedule()).address(tenancy.getAddress())
				.tenancy(tenancy).user(producer0).build());

		for (Contract contract : contracts) {
			contractRepository.save(contract);
			System.out.println("Contrat créé : " + contract.getContractName());
		}
	}

	public void productInit() throws IOException {

		List<Tenancy> vergerTenancies = tenancyRepository.findAll().stream()
				.filter(t -> t.getOptions() != null && Boolean.TRUE.equals(t.getOptions().getOption1Active())
						&& Boolean.FALSE.equals(t.getOptions().getOption2Active()))
				.toList();

		for (Tenancy tenancy : vergerTenancies) {
			List<User> producers = userRepository.findByTenancyAndRole(tenancy, "Producteur");

			String compotepoire = loadImageFromResources("compotepoire.png");
			String compotepommes = loadImageFromResources("compotepommes.png");
			String confiturefraise = loadImageFromResources("confiturefraise.png");
			String confitureabricot = loadImageFromResources("confitureabricot.png");
			String geleecoing = loadImageFromResources("geleecoing.png");
			String mielacacia = loadImageFromResources("mielacacia.png");
			String mielfleur = loadImageFromResources("mielfleur.png");

			List<Product> products = new ArrayList<>();
			products.add(Product.builder().productName("Confiture de fraises")
					.productDescription("Confiture artisanale à base de fraises bio, sans additifs.")
					.productPrice(new BigDecimal("5.50")).productStock(100).dateCreation(LocalDate.now())
					.fabricationDate(LocalDate.of(2025, 1, 15)).expirationDate(LocalDate.of(2025, 12, 15))
					.imageType("image/png").imageData(confiturefraise).deliveryRecurrence(DeliveryRecurrence.MONTHLY)
					.pickUpSchedule(tenancy.getPickUpSchedule()).address(tenancy.getAddress()).tenancy(tenancy)
					.user(producers.get(0)).shoppable(true).build());

			products.add(Product.builder().productName("Miel d'acacia")
					.productDescription("Miel pur d'acacia, récolté dans les vergers locaux.")
					.productPrice(new BigDecimal("7.00")).productStock(80).dateCreation(LocalDate.now())
					.fabricationDate(LocalDate.of(2025, 3, 1)).expirationDate(LocalDate.of(2026, 3, 1))
					.imageType("image/png").imageData(mielacacia).deliveryRecurrence(DeliveryRecurrence.WEEKLY)
					.pickUpSchedule(tenancy.getPickUpSchedule()).address(tenancy.getAddress()).tenancy(tenancy)
					.user(producers.get(1 % producers.size())).shoppable(true).build());

			products.add(Product.builder().productName("Compote de pommes")
					.productDescription("Compote maison sans sucre ajouté, fabriquée à partir de pommes bio.")
					.productPrice(new BigDecimal("4.50")).productStock(120).dateCreation(LocalDate.now())
					.fabricationDate(LocalDate.of(2025, 2, 10)).expirationDate(LocalDate.of(2025, 8, 10))
					.imageType("image/png").imageData(compotepommes).deliveryRecurrence(DeliveryRecurrence.BIMONTHLY)
					.pickUpSchedule(tenancy.getPickUpSchedule()).address(tenancy.getAddress()).tenancy(tenancy)
					.user(producers.get(2 % producers.size())).shoppable(true).build());

			products.add(Product.builder().productName("Gelée de coing")
					.productDescription("Gelée artisanale à base de coings frais et parfumés.")
					.productPrice(new BigDecimal("6.50")).productStock(60).dateCreation(LocalDate.now())
					.fabricationDate(LocalDate.of(2025, 3, 20)).expirationDate(LocalDate.of(2025, 11, 20))
					.imageType("image/png").imageData(geleecoing).deliveryRecurrence(DeliveryRecurrence.MONTHLY)
					.pickUpSchedule(tenancy.getPickUpSchedule()).address(tenancy.getAddress()).tenancy(tenancy)
					.user(producers.get(3 % producers.size())).shoppable(true).build());

			products.add(Product.builder().productName("Confiture d'abricots")
					.productDescription("Confiture sucrée et délicate, préparée avec des abricots de saison.")
					.productPrice(new BigDecimal("5.00")).productStock(90).dateCreation(LocalDate.now())
					.fabricationDate(LocalDate.of(2025, 5, 1)).expirationDate(LocalDate.of(2025, 12, 1))
					.imageType("image/png").imageData(confitureabricot).deliveryRecurrence(DeliveryRecurrence.WEEKLY)
					.pickUpSchedule(tenancy.getPickUpSchedule()).address(tenancy.getAddress()).tenancy(tenancy)
					.user(producers.get(4 % producers.size())).shoppable(true).build());

			products.add(Product.builder().productName("Compote de poires")
					.productDescription("Compote maison faite à partir de poires bio et mûres.")
					.productPrice(new BigDecimal("4.80")).productStock(70).dateCreation(LocalDate.now())
					.fabricationDate(LocalDate.of(2025, 2, 25)).expirationDate(LocalDate.of(2025, 8, 25))
					.imageType("image/png").imageData(compotepoire).deliveryRecurrence(DeliveryRecurrence.BIMONTHLY)
					.pickUpSchedule(tenancy.getPickUpSchedule()).address(tenancy.getAddress()).tenancy(tenancy)
					.user(producers.get(0)).shoppable(true).build());

			products.add(Product.builder().productName("Miel de fleurs")
					.productDescription("Miel de fleurs sauvages, idéal pour les infusions et desserts.")
					.productPrice(new BigDecimal("6.80")).productStock(75).dateCreation(LocalDate.now())
					.fabricationDate(LocalDate.of(2025, 4, 15)).expirationDate(LocalDate.of(2026, 4, 15))
					.imageType("image/png").imageData(mielfleur).deliveryRecurrence(DeliveryRecurrence.MONTHLY)
					.pickUpSchedule(tenancy.getPickUpSchedule()).address(tenancy.getAddress()).tenancy(tenancy)
					.user(producers.get(1 % producers.size())).shoppable(true).build());

			for (Product product : products) {
				productRepository.save(product);
				System.out.println("Produit créé : " + product.getProductName() + " pour la tenancy : "
						+ tenancy.getTenancyName());
			}
		}
	}

	public void workshopInit() throws IOException {

		List<Tenancy> vergerTenancies = tenancyRepository.findAll().stream()
				.filter(t -> t.getOptions() != null && Boolean.TRUE.equals(t.getOptions().getOption1Active())
						&& Boolean.FALSE.equals(t.getOptions().getOption2Active()))
				.toList();

		for (Tenancy tenancy : vergerTenancies) {
			List<User> producers = userRepository.findByTenancyAndRole(tenancy, "Producteur");

			String atelierapiculture = loadImageFromResources("atelierapiculture.png");
			String atelierfraise = loadImageFromResources("atelierfraise.png");
			String ateliercuisine = loadImageFromResources("ateliercuisine.png");
			String atelierperma = loadImageFromResources("atelierperma.png");
			String ateliertransfo = loadImageFromResources("ateliertransfo.png");

			List<Workshop> workshops = new ArrayList<>();
			workshops.add(Workshop.builder().workshopName("Atelier de fabrication de confiture")
					.workshopDescription("Apprenez à fabriquer des confitures artisanales avec des fruits frais.")
					.workshopDateTime(LocalDateTime.of(2025, 5, 15, 10, 0)).workshopPrice(new BigDecimal("25.00"))
					.workshopDuration(120) // en minutes
					.minimumParticipants(5).maximumParticipants(20).address(producers.get(0).getAddress()) // Adresse de
																											// l'utilisateur
					.tenancy(tenancy).user(producers.get(0)).shoppable(true).imageType("image/png")
					.imageData(atelierfraise).dateCreation(LocalDate.now()).build());

			workshops.add(Workshop.builder().workshopName("Atelier d'apiculture")
					.workshopDescription("Découvrez les bases de l'apiculture et apprenez à récolter du miel.")
					.workshopDateTime(LocalDateTime.of(2025, 6, 20, 14, 0)).workshopPrice(new BigDecimal("30.00"))
					.workshopDuration(180).minimumParticipants(3).maximumParticipants(15)
					.address(producers.get(1 % producers.size()).getAddress()).tenancy(tenancy)
					.user(producers.get(1 % producers.size())).shoppable(true).imageType("image/png")
					.imageData(atelierapiculture).dateCreation(LocalDate.now()).build());

			workshops.add(Workshop.builder().workshopName("Atelier de cuisine bio")
					.workshopDescription("Cuisinez des plats savoureux à partir de produits bio et locaux.")
					.workshopDateTime(LocalDateTime.of(2025, 7, 10, 18, 0)).workshopPrice(new BigDecimal("20.00"))
					.workshopDuration(90).minimumParticipants(4).maximumParticipants(12)
					.address(producers.get(2 % producers.size()).getAddress()).tenancy(tenancy)
					.user(producers.get(2 % producers.size())).shoppable(true).imageType("image/png")
					.imageData(ateliercuisine).dateCreation(LocalDate.now()).build());

			workshops.add(Workshop.builder().workshopName("Atelier de permaculture")
					.workshopDescription("Introduction aux techniques de permaculture pour votre jardin.")
					.workshopDateTime(LocalDateTime.of(2025, 8, 5, 9, 30)).workshopPrice(new BigDecimal("15.00"))
					.workshopDuration(120).minimumParticipants(6).maximumParticipants(25)
					.address(producers.get(3 % producers.size()).getAddress()).tenancy(tenancy)
					.user(producers.get(3 % producers.size())).shoppable(true).imageType("image/png")
					.imageData(atelierperma).dateCreation(LocalDate.now()).build());

			workshops.add(Workshop.builder().workshopName("Atelier de transformation de fruits")
					.workshopDescription("Transformez vos fruits en compotes, jus et autres produits savoureux.")
					.workshopDateTime(LocalDateTime.of(2025, 9, 15, 11, 0)).workshopPrice(new BigDecimal("28.00"))
					.workshopDuration(150).minimumParticipants(8).maximumParticipants(18)
					.address(producers.get(4 % producers.size()).getAddress()).tenancy(tenancy)
					.user(producers.get(4 % producers.size())).shoppable(true).imageType("image/png")
					.imageData(ateliertransfo).dateCreation(LocalDate.now()).build());

			for (Workshop workshop : workshops) {
				workshopRepository.save(workshop);
				System.out.println("Atelier créé : " + workshop.getWorkshopName() + " pour la tenancy : "
						+ tenancy.getTenancyName());
			}
		}
	}

	public void initMembershipFee(Tenancy tenancy) {
		List<User> users = userRepository.findByTenancy_TenancyId(tenancy.getTenancyId());
		for (User user : users) {
			// random day logic
			LocalDate randomStartDate = randomDate();
			// create fee and link it to user
			MembershipFee fee = MembershipFee.builder().info("Cotisation annuelle").stock(1)
					.price(user.getTenancy().getMembershipFeePrice().doubleValue()).dateBeginning(randomStartDate)
					.dateEnd(randomStartDate.plusDays(365)).build();
			fee.setUser(user);
			user.setMembershipFee(fee);
			// save by cascade
			userRepository.save(user);
		}
	}

	public LocalDate randomDate() {
		LocalDate endDate = LocalDate.of(2025, 1, 10);
		LocalDate firstDate = LocalDate.of(2024, 1, 20);
		long daysBetween = ChronoUnit.DAYS.between(firstDate, endDate);
		int randomDays = new Random().nextInt((int) daysBetween + 1);
		LocalDate randomDate = firstDate.plusDays(randomDays);
		return randomDate;
	}

	public void initAllOrders() {
		int randomOrdersQuantity = new Random().nextInt(10);
		for (int j = 0; j < randomOrdersQuantity; j++) {
			for (Long i = 1L; i < 120; i++) {
				initOrder(i);
			}
		}
	}

	@Transactional
	public void initOrder(Long userId) {
		User user = userRepository.findUserWithOrders(userId);

		int randomQuantityOrderItems = new Random().nextInt(5) + 1;

		LocalDate randomDate = randomDate();
		int randomQuantityShoppable = new Random().nextInt(3) + 1;

		// build order base
		Order order = Order.builder().totalAmount(0).orderDate(randomDate).orderStatus(OrderStatus.DONE).orderPaid(true)
				.user(user).build();
		// get random order item from shoppables
		for (int i = 0; i < randomQuantityOrderItems; i++) {
			Shoppable shoppable = getRandomShoppable();
			if (shoppable == null) {
				System.out.println("Shoppable null, jump iteration");
				continue; // ignore iteration if no shoppable is found
			}
			OrderItem item = OrderItem.builder().quantity(randomQuantityShoppable).unitPrice(shoppable.getPrice())
					.total(randomQuantityShoppable * shoppable.getPrice())
//					.shoppable(shoppable)
					.build();
			// i know it's not pretty but it handles the jpa limits
			orderItemRepository.save(item);
			item.setShoppable(shoppable);
			orderItemRepository.save(item);
			order.getOrderItems().add(item);
			order.setTotalAmount(order.getTotalAmount() + randomQuantityShoppable * shoppable.getPrice());
			orderRepository.save(order);
			item.setOrder(order);
			orderItemRepository.save(item);
		}
		addPaymentToOrder(order, user, randomDate);
		addOrderToUser(order, user);
	}

	private void addPaymentToOrder(Order order, User user, LocalDate randomDate) {
		// create payment
		Payment payment = Payment.builder().paymentDate(randomDate.atStartOfDay()).paymentType(getRandomPaymentType())
				.paymentAmount(BigDecimal.valueOf(order.getTotalAmount())).build();
		payment.setOrder(order);
		order.getPayments().add(payment);
		paymentRepository.save(payment);
		orderRepository.save(order);
	}
	
	private void addOrderToUser(Order order, User user) {
		user.getOrders().add(order);
		userRepository.save(user);
	}

	private PaymentType getRandomPaymentType() {
		int randomChoice = new Random().nextInt(2) + 1;
		switch (randomChoice) {
		case 0:
			return PaymentType.card;
		case 1:
			return PaymentType.check;
		case 2:
			return PaymentType.cash;
		default:
			throw new IllegalStateException("Valeur aléatoire invalide : " + randomChoice);
		}
	}
	
	private Shoppable getRandomShoppable() {
		Random random = new Random();
		int randomChoice = random.nextInt(2) + 1;
		switch (randomChoice) {
		case 0:
			return productRepository.findRandom();
		case 1:
			return contractRepository.findRandom();
		case 2:
			return workshopRepository.findRandom();
		default:
			throw new IllegalStateException("Valeur aléatoire invalide : " + randomChoice);
		}
	}

	public void contractInitForTenancy(Long tenancyId) throws IOException {

		Tenancy tenancy = tenancyRepository.findById(tenancyId)
				.orElseThrow(() -> new IllegalArgumentException("Tenancy not found with ID: " + tenancyId));

		List<User> producers = userRepository.findByTenancyAndRole(tenancy, "Producteur");

		if (producers.isEmpty()) {
			throw new IllegalStateException("No producers found for the tenancy with ID: " + tenancyId);
		}

		String paniersoupe = loadImageFromResources("paniersoupe.png");
		String panierratatouille = loadImageFromResources("panierratatouille.png");
		String panierregionfrance = loadImageFromResources("panierregionfrance.png");
		String paniersaveurautomne = loadImageFromResources("paniersaveurautomne.png");
		String paniercroquant = loadImageFromResources("paniercroquant.png");

		Contract contract1 = Contract.builder().contractName("Panier soupe 5 légumes")
				.contractType(ContractType.VEGETABLES_CONTRACT)
				.contractDescription("Un panier composé pour réaliser une délicieuse soupe aux 5 légumes\r\n" + "\r\n"
						+ "Environs 4 kilo de légumes dans le panier")
				.contractWeight(ContractWeight.AVERAGE).contractPrice(new BigDecimal("10.95"))
				.dateCreation(LocalDate.now()).startDate(LocalDate.of(2025, 4, 1)).endDate(LocalDate.of(2025, 6, 30))
				.deliveryRecurrence(DeliveryRecurrence.BIMONTHLY).quantity(15).shoppable(true).imageType("image/png")
				.pickUpSchedule(tenancy.getPickUpSchedule()).address(tenancy.getAddress()).tenancy(tenancy)
				.user(producers.get(0)).imageData(paniersoupe).build();

		Contract contract2 = Contract.builder().contractName("Saveur automnales")
				.contractType(ContractType.VEGETABLES_CONTRACT)
				.contractDescription(
						"Carottes, panais, céleri-rave, pommes de terre, betteraves. Idéal pour des purées, soupes ou rôtis au four.")
				.contractWeight(ContractWeight.AVERAGE).contractPrice(new BigDecimal("11.50"))
				.dateCreation(LocalDate.now()).startDate(LocalDate.of(2025, 1, 10)).endDate(LocalDate.of(2025, 6, 30))
				.deliveryRecurrence(DeliveryRecurrence.WEEKLY).quantity(12).shoppable(true).imageType("image/png")
				.pickUpSchedule(tenancy.getPickUpSchedule()).address(tenancy.getAddress()).tenancy(tenancy)
				.user(producers.get(3)).imageData(paniersaveurautomne).build();

		Contract contract3 = Contract.builder().contractName("Panier spécial ratatouille")
				.contractType(ContractType.VEGETABLES_CONTRACT)
				.contractDescription(
						"Aubergines, poivrons, tomates, oignons, courgettes. Tout ce qu'il faut pour une ratatouille traditionnelle.")
				.contractWeight(ContractWeight.AVERAGE).contractPrice(new BigDecimal("20.00"))
				.dateCreation(LocalDate.now()).startDate(LocalDate.of(2025, 5, 1)).endDate(LocalDate.of(2025, 9, 30))
				.deliveryRecurrence(DeliveryRecurrence.WEEKLY).quantity(10).shoppable(true).imageType("image/png")
				.imageData(panierratatouille).pickUpSchedule(tenancy.getPickUpSchedule()).address(tenancy.getAddress())
				.tenancy(tenancy).user(producers.get(0)).build();

		Contract contract4 = Contract.builder().contractName("Panier douceur et croquant")
				.contractType(ContractType.FRUITS_CONTRACT)
				.contractDescription("Pommes, poires, raisins, noix, figues. Une douceur automnale.")
				.contractWeight(ContractWeight.BIG).contractPrice(new BigDecimal("30.00")).dateCreation(LocalDate.now())
				.startDate(LocalDate.of(2025, 10, 1)).endDate(LocalDate.of(2025, 12, 31))
				.deliveryRecurrence(DeliveryRecurrence.MONTHLY).quantity(12).shoppable(true).imageType("image/png")
				.imageData(paniercroquant).pickUpSchedule(tenancy.getPickUpSchedule()).address(tenancy.getAddress())
				.tenancy(tenancy).user(producers.get(1)).build();

		Contract contract5 = Contract.builder().contractName("Panier Régions de France")
				.contractType(ContractType.MIX_CONTRACT)
				.contractDescription(
						"Une sélection des légumes et fruits emblématiques des régions françaises : choux de Bretagne, pommes de Normandie, raisins du Sud-Ouest.")
				.contractWeight(ContractWeight.BIG).contractPrice(new BigDecimal("40.00")).dateCreation(LocalDate.now())
				.startDate(LocalDate.of(2025, 1, 1)).endDate(LocalDate.of(2025, 12, 31))
				.deliveryRecurrence(DeliveryRecurrence.MONTHLY).quantity(15).shoppable(true).imageType("image/png")
				.imageData(panierregionfrance).pickUpSchedule(tenancy.getPickUpSchedule()).address(tenancy.getAddress())
				.tenancy(tenancy).user(producers.get(2)).build();

		contractRepository.save(contract1);
		contractRepository.save(contract2);
		contractRepository.save(contract3);
		contractRepository.save(contract4);
		contractRepository.save(contract5);

	}

	private String loadImageFromResources(String imageName) throws IOException {
		InputStream imageStream = getClass().getClassLoader().getResourceAsStream("image/" + imageName);
		if (imageStream == null) {
			throw new IOException("Image not found in resources: " + imageName);
		}
		byte[] imageBytes = imageStream.readAllBytes();
		return Base64.getEncoder().encodeToString(imageBytes);
	}

}
