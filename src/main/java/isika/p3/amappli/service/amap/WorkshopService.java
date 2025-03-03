package isika.p3.amappli.service.amap;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import isika.p3.amappli.dto.amap.WorkshopDTO;
import isika.p3.amappli.entities.tenancy.Tenancy;
import isika.p3.amappli.entities.workshop.Workshop;

public interface WorkshopService {
	
	void save(WorkshopDTO workshopDTO, String tenancyAlaias);

	List<Workshop> findAll();

	void deleteById(Long id);

	Workshop findById(Long id);
	
	void updateWorkshop(WorkshopDTO updatedWorkshopDTO, MultipartFile image, String tenancyAlias);
	
	List<Workshop> findAll(Long tenancyId);
	
	List<Workshop> findAll(String tenancyAlias);
	
	List<Workshop> findShoppableWorkshopsByTenancy(Tenancy tenancy);

}
