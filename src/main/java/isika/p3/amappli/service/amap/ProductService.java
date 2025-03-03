package isika.p3.amappli.service.amap;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import isika.p3.amappli.dto.amap.ProductDTO;
import isika.p3.amappli.entities.product.Product;
import isika.p3.amappli.entities.tenancy.Tenancy;

public interface ProductService {

	void save(ProductDTO productDTO, String tenancyAlias);

	List<Product> findAll();

	void deleteById(Long id);

	Product findById(Long id);

	void updateProduct(ProductDTO updatedProductDTO, MultipartFile image, String tenancyAlias);

	List<Product> findAll(Long tenancyId);

	List<Product> findAll(String tenancyAlias);

	List<Product> findShoppableProductsByTenancy(Tenancy tenancy);
}
