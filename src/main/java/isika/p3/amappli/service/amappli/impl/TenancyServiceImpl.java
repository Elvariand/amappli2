package isika.p3.amappli.service.amappli.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

import org.springframework.stereotype.Service;

import isika.p3.amappli.entities.tenancy.HomePageContent;
import isika.p3.amappli.api.NominatimAPI;
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
import isika.p3.amappli.dto.amappli.NewTenancyDTO;
import isika.p3.amappli.dto.amappli.ValueDTO;
import isika.p3.amappli.entities.tenancy.ContentBlock;
import isika.p3.amappli.entities.tenancy.Graphism;
import isika.p3.amappli.entities.tenancy.Options;
import isika.p3.amappli.entities.tenancy.Tenancy;
import isika.p3.amappli.entities.user.User;
import isika.p3.amappli.exceptions.TenancyAliasAlreadyTakenException;
import isika.p3.amappli.repo.amap.ContentBlockRepository;
import isika.p3.amappli.repo.amap.UserRepository;
import isika.p3.amappli.repo.amappli.TenancyRepository;
import isika.p3.amappli.service.amappli.TenancyService;
import isika.p3.amappli.util.MultiPartFileToBase64;
import jakarta.transaction.Transactional;

@Service
public class TenancyServiceImpl implements TenancyService {

	private final TenancyRepository tenancyRepository;

	private final UserRepository userRepository;

	private final ContentBlockRepository contentBlockRepository;

	public TenancyServiceImpl(TenancyRepository tenancyRepository, UserRepository userRepository, ContentBlockRepository contentBlockRepository) {
		this.tenancyRepository = tenancyRepository;
		this.userRepository = userRepository;
		this.contentBlockRepository = contentBlockRepository;
	}
	
	@Override
	public HomePageContent getHomePageContentByTenancyId(Long id) {
		Tenancy tenancy = tenancyRepository.findById(id).orElse(null);

		if (tenancy != null) {
			return tenancy.getHomePageContent();
		}

		return null;
	}

	@Override
	public Tenancy createTenancy(Tenancy tenancy) {

		if (tenancy.getHomePageContent() == null) {
			HomePageContent homePageContent = HomePageContent.builder().subTitle("Default Subtitle")
					.showSuppliers(false).tenancy(tenancy).build();
			tenancy.setHomePageContent(homePageContent);
		}

		return tenancyRepository.save(tenancy);
	}

	@Override
	public List<Tenancy> getAllTenancies() {
		return (List<Tenancy>) tenancyRepository.findAll();
	}

	@Override
	public Tenancy getTenancyById(Long id) {
		return tenancyRepository.findById(id).orElse(null);
	}

	@Override
	public void deleteTenancy(Long id) {
		tenancyRepository.deleteById(id);
	}

	@Transactional
	@Override
	public Tenancy createTenancyFromWelcomeForm(NewTenancyDTO newTenancyDTO, Long creatorUserId) {
		Tenancy tenancy = new Tenancy();
		
		// Name
		tenancy.setTenancyName(newTenancyDTO.getTenancyName());
		// Address
		tenancy.setAddress(newTenancyDTO.getAddress());

		// Compute GPS coordinates from Address
		String coordinates = NominatimAPI.getGPSFromAddress(newTenancyDTO.getAddress());
		if (coordinates != null) {
			String[] coor = coordinates.split(",");
			tenancy.setTenancyLatitude(coor[1]);
			tenancy.setTenancyLongitude(coor[0]);
		}
		
		// Alias
		String alias = newTenancyDTO.getTenancyAlias().trim().toLowerCase().replace(' ', '-');
		int copycats = this.getAllTenancies().stream().filter(t -> t.getTenancyAlias().equals(alias)).toList().size();		
		tenancy.setTenancyAlias(copycats == 0 ? alias : alias + "-" + copycats);

		// Slogan
		tenancy.setTenancySlogan(newTenancyDTO.getTenancySlogan());

		// MembershipFee Price
		tenancy.setMembershipFeePrice(newTenancyDTO.getMembershipFeePrice());

		// PickUpSchedule
		tenancy.setPickUpSchedule(newTenancyDTO.getPickUpSchedule());

		// Date created and last modified
		tenancy.setDateCreated(LocalDateTime.now());
		tenancy.setDateLastModified(LocalDateTime.now());

		// Graphism
		Graphism graphism = new Graphism();
		graphism.setColorPalette(newTenancyDTO.getColorPalette());
		graphism.setFontChoice(newTenancyDTO.getFontChoice());
		if (!newTenancyDTO.getLogo().isEmpty()) {
			graphism.setLogoImgType(newTenancyDTO.getLogo().getContentType());
			try {
				byte[] thebytes = newTenancyDTO.getLogo().getBytes();
				graphism.setLogoImg(Base64.getEncoder().encodeToString(thebytes));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		graphism.setTenancy(tenancy);
		tenancy.setGraphism(graphism);

		// Options
		Options options = new Options();
		if (newTenancyDTO.getOption().equals("option-1")) {
			options.setOption1Active(false);
			options.setOption2Active(false);
		} else if (newTenancyDTO.getOption().equals("option-2")) {
			options.setOption1Active(true);
			options.setOption2Active(false);
		} else if (newTenancyDTO.getOption().equals("option-3")) {
			options.setOption1Active(true);
			options.setOption2Active(true);

		}

		//options.addTenancy(tenancy);
		tenancy.setOptions(options);
		// HomePageContent
		HomePageContent homePageContent = new HomePageContent();
		// homePageContent.setTenancy(tenancy);
		// The first block
		ContentBlock cbi = new ContentBlock();
		cbi.setContentTitle(newTenancyDTO.getFirstHomePageTitle());
		cbi.setContentText(newTenancyDTO.getFirstHomePageText());
		if (!newTenancyDTO.getFirstHomePagePic().isEmpty()) {
			cbi.setContentImgName(newTenancyDTO.getFirstHomePagePic().getOriginalFilename());
			cbi.setContentImgTypeMIME(newTenancyDTO.getFirstHomePagePic().getContentType());
			try {
				byte[] thebytes = newTenancyDTO.getFirstHomePagePic().getBytes();
				cbi.setContentImg(Base64.getEncoder().encodeToString(thebytes));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		cbi.setHomePageContent(homePageContent);

		homePageContent.getContents().add(cbi);

		for (ValueDTO v : newTenancyDTO.getValues()) {
			if (v.getName().length() > 0) {
				ContentBlock cb = ContentBlock.builder().isValue(true).contentTitle(v.getName())
						.contentText(v.getDescription()).build();
				if (!v.getFile().isEmpty()) {
					cb.setContentImgName(v.getFile().getOriginalFilename());
					cb.setContentImgTypeMIME(v.getFile().getContentType());
					try {
						byte[] thebytes = v.getFile().getBytes();
						cb.setContentImg(Base64.getEncoder().encodeToString(thebytes));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				cb.setHomePageContent(homePageContent);
				homePageContent.getContents().add(cb);
			}
		}
		tenancy.setHomePageContent(homePageContent);
		homePageContent.setTenancy(tenancy);
		// Will cause error if tenancyAlias already taken
		try {
			tenancyRepository.save(tenancy);
			// Affect it to the creator user
			User creator = userRepository.findById(creatorUserId).get();
			creator.setTenancy(tenancy);
			userRepository.save(creator);
			return tenancy;
		} catch (RuntimeException e) {
			throw new TenancyAliasAlreadyTakenException("Cette url d'AMAP est déjà utilisée.");
		}
	}

	@Override
	public Tenancy getTenancyByAlias(String alias) {
		System.out.println(tenancyRepository.findByTenancyAlias(alias).get().getTenancyAlias());
		return tenancyRepository.findByTenancyAlias(alias).get();
	}

	@Override
	public HomePageContent getHomePageContentByTenancyAlias(String alias) {
		Tenancy tenancy = tenancyRepository.findByTenancyAlias(alias).orElse(null);

		if (tenancy != null) {
			return tenancy.getHomePageContent();
		}

		return null;
	}

	public void updateTenancyNameOrAlias(TenancyUpdateNameAliasDTO updateInfo, String alias){
		Tenancy tenancy = tenancyRepository.findByTenancyAlias(alias).get();
		if(tenancy != null){
			tenancy.setTenancyName(updateInfo.getTenancyName());
			tenancy.setTenancyAlias(updateInfo.getTenancyAlias());
			tenancyRepository.save(tenancy);
		}
	}

	public void updateTenancySlogan(TenancyUpdateSloganDTO updateInfo, String alias){
		Tenancy tenancy = tenancyRepository.findByTenancyAlias(alias).get();
		if(tenancy != null){
			tenancy.setTenancySlogan(updateInfo.getSlogan());
			tenancyRepository.save(tenancy);
		}
	}

	public void updateTenancyLogo(TenancyUpdateLogo updateInfo, String alias){
		Tenancy tenancy = tenancyRepository.findByTenancyAlias(alias).get();
		if(tenancy != null){
			String[] logoData = MultiPartFileToBase64.encodeMultiPartFile(updateInfo.getFile());
			tenancy.getGraphism().setLogoImgType(logoData[0]);
			tenancy.getGraphism().setLogoImg(logoData[1]);
			tenancyRepository.save(tenancy);
		}
	}

	public void updateTenancyAddress(TenancyUpdateAddressDTO updateInfo, String alias){
		Tenancy tenancy = tenancyRepository.findByTenancyAlias(alias).get();
		if(tenancy != null){
			tenancy.setAddress(updateInfo.getAddress());
			// Compute GPS coordinates from Address
			String coordinates = NominatimAPI.getGPSFromAddress(updateInfo.getAddress());
			if (coordinates != null) {
			String[] coor = coordinates.split(",");
			tenancy.setTenancyLatitude(coor[1]);
			tenancy.setTenancyLongitude(coor[0]);
			tenancyRepository.save(tenancy);
			}
		}
	}

	public void updateTenancyPickUpSchedule(TenancyUpdatePickUpDTO updateInfo, String alias){
		Tenancy tenancy = tenancyRepository.findByTenancyAlias(alias).get();
		if(tenancy != null){
			tenancy.setPickUpSchedule(updateInfo.getPickUpSchedule());
			tenancyRepository.save(tenancy);
		}
	}
	
	public void updateTenancyColorFont(TenancyUpdateColorFontDTO updateInfo, String alias){
		Tenancy tenancy = tenancyRepository.findByTenancyAlias(alias).get();
		if(tenancy != null){
			tenancy.getGraphism().setColorPalette(updateInfo.getColorPalette());
			tenancy.getGraphism().setFontChoice(updateInfo.getFontChoice());
			tenancyRepository.save(tenancy);
		}
	}

	public void updateTenancyHomePageContent(TenancyUpdateHomePageContentDTO updateInfo, String alias){
		Tenancy tenancy = tenancyRepository.findByTenancyAlias(alias).get();
		HomePageContent homePageContent = tenancy.getHomePageContent();
		// List<ContentBlock> contents = homePageContent.getContents();
		for(ContentBlockDTO cbDTO : updateInfo.getContents()){
			ContentBlock contentBlock;
			if(cbDTO.getContentBlockId() != null){
				contentBlock = contentBlockRepository.findById(cbDTO.getContentBlockId()).get();
			}
			else{
				contentBlock = new ContentBlock();
				contentBlock.setValue(false);
			}
			contentBlock.setContentTitle(cbDTO.getContentTitle());
			contentBlock.setContentText(cbDTO.getContentText());
			if (!cbDTO.getImage().isEmpty()) {
				contentBlock.setContentImgName(cbDTO.getImage().getOriginalFilename());
				contentBlock.setContentImgTypeMIME(cbDTO.getImage().getContentType());
				try {
					byte[] thebytes = cbDTO.getImage().getBytes();
					contentBlock.setContentImg(Base64.getEncoder().encodeToString(thebytes));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			contentBlock.setHomePageContent(homePageContent);
			contentBlockRepository.save(contentBlock);
		}
	}

	public void updateTenancyValues(TenancyUpdateHomePageContentDTO updateInfo, String alias){
		Tenancy tenancy = tenancyRepository.findByTenancyAlias(alias).get();
		HomePageContent homePageContent = tenancy.getHomePageContent();
		// List<ContentBlock> contents = homePageContent.getContents();
		for(ContentBlockDTO cbDTO : updateInfo.getContents()){
			ContentBlock contentBlock;
			if(cbDTO.getContentBlockId() != null){
				contentBlock = contentBlockRepository.findById(cbDTO.getContentBlockId()).get();
			}
			else{
				contentBlock = new ContentBlock();
				contentBlock.setValue(true);
			}
			contentBlock.setContentTitle(cbDTO.getContentTitle());
			contentBlock.setContentText(cbDTO.getContentText());
			if (!cbDTO.getImage().isEmpty()) {
				contentBlock.setContentImgName(cbDTO.getImage().getOriginalFilename());
				contentBlock.setContentImgTypeMIME(cbDTO.getImage().getContentType());
				try {
					byte[] thebytes = cbDTO.getImage().getBytes();
					contentBlock.setContentImg(Base64.getEncoder().encodeToString(thebytes));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			contentBlock.setHomePageContent(homePageContent);
			contentBlockRepository.save(contentBlock);
		}
	}

	public void updateTenancyMemberShipFeePrice(TenancyUpdateMemberShipFeePriceDTO updateInfo, String alias){
		Tenancy tenancy = tenancyRepository.findByTenancyAlias(alias).get();
		if(tenancy != null){
			tenancy.setMembershipFeePrice(updateInfo.getMembershipFeePrice());
			tenancyRepository.save(tenancy);
		}
	}

	public void updateTenancyOptions(TenancyUpdateOptionsDTO updateInfo, String alias){
		Tenancy tenancy = tenancyRepository.findByTenancyAlias(alias).get();
		if(tenancy != null){
			if (updateInfo.getOption().equals("option-1")) {
				tenancy.getOptions().setOption1Active(false);
				tenancy.getOptions().setOption2Active(false);
			} else if (updateInfo.getOption().equals("option-2")) {
				tenancy.getOptions().setOption1Active(true);
				tenancy.getOptions().setOption2Active(false);
			} else if (updateInfo.getOption().equals("option-3")) {
				tenancy.getOptions().setOption1Active(true);
				tenancy.getOptions().setOption2Active(true);
			}
			tenancyRepository.save(tenancy);
		}
	}
}
