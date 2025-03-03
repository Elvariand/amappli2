package isika.p3.amappli.service.amap;

import org.springframework.ui.Model;

import isika.p3.amappli.entities.tenancy.Tenancy;

public interface GraphismService {

	Tenancy getTenancyByAlias(String alias);
	
	void setUpModel(String alias, Model model);
	
	Long getUserIdFromContext();

	void getUserInfoFromContext(Model model);
	
}
