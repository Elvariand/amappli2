package isika.p3.amappli.service.amap;

import java.util.List;

import isika.p3.amappli.entities.user.ContactInfo;

public interface ContactInfoService {

	List<ContactInfo> findAll();
	
	ContactInfo save(ContactInfo contactInfo);
	
	ContactInfo findById(Long contactInfoId);
	
	void deleteById(Long contactInfoId);
}
