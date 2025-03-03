package isika.p3.amappli.service.amap;

import java.util.List;

import isika.p3.amappli.entities.user.Address;

public interface AddressService {

	List<Address> findAll();
	
	Address save(Address address);
	
	Address findById(Long addressId);
	
	void deleteById(Long addressId);
}
