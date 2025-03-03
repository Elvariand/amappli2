package isika.p3.amappli.service.amap.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import isika.p3.amappli.dto.amap.ProductDTO;
import isika.p3.amappli.entities.contract.DeliveryRecurrence;
import isika.p3.amappli.entities.product.Product;
import isika.p3.amappli.entities.tenancy.Tenancy;
import isika.p3.amappli.entities.user.Address;
import isika.p3.amappli.entities.user.User;
import isika.p3.amappli.repo.amap.AddressRepository;
import isika.p3.amappli.repo.amap.ProductRepository;
import isika.p3.amappli.repo.amap.UserRepository;
import isika.p3.amappli.repo.amappli.TenancyRepository;
import isika.p3.amappli.service.amap.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
	private final UserRepository userRepository;
	private final TenancyRepository tenancyRepository;

    public ProductServiceImpl(ProductRepository productRepository, UserRepository userRepository, AddressRepository addressRepository, TenancyRepository tenancyRepository) {
        this.productRepository = productRepository;
		this.userRepository = userRepository;
		this.tenancyRepository = tenancyRepository;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public void save(ProductDTO productDTO, String tenancyAlias) {
        Product product = new Product();
        
        Tenancy tenancy = tenancyRepository.findByTenancyAlias(tenancyAlias)
                .orElseThrow(() -> new IllegalArgumentException("Tenancy not found for alias: " + tenancyAlias));
        product.setTenancy(tenancy);
        
        // Associer le PickupSchedule depuis la tenancy
        if (tenancy.getPickUpSchedule() != null) {
            product.setPickUpSchedule(tenancy.getPickUpSchedule());
        } else {
            throw new IllegalArgumentException("Pickup Schedule non défini pour la tenancy.");
        }
        
        // Récupération de l'utilisateur sélectionné s'il y a un ID d'utilisateur
        if (productDTO.getUserId() != null) {
            User user = userRepository.findById(productDTO.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found for ID: " + productDTO.getUserId()));
            product.setUser(user);
        } else {
            product.setUser(null); // Si aucun utilisateur n'est sélectionné
        }
        
        // Récupération de l'adresse associée à la tenancy
        Address tenancyAddress = tenancy.getAddress();
        if (tenancyAddress == null) {
            throw new IllegalArgumentException("No address found for the tenancy.");
        }
        product.setAddress(tenancyAddress);

        product.setProductName(productDTO.getProductName());
        product.setProductPrice(productDTO.getProductPrice());
        product.setProductStock(productDTO.getProductStock());
        product.setFabricationDate(productDTO.getFabricationDate());
        product.setExpirationDate(productDTO.getExpirationDate());
        product.setDateCreation(productDTO.getDateCreation());
        product.setDateCreation(LocalDate.now());
        product.setProductDescription(productDTO.getProductDescription());
        product.setDeliveryRecurrence(DeliveryRecurrence.valueOf(productDTO.getDeliveryRecurrence()));

        // Traitement de l'image
        if (!productDTO.getImage().isEmpty()) {
            if (productDTO.getImage().getSize() > 20971520) { // 20 MB max
                throw new IllegalArgumentException("La taille de l'image dépasse la limite de 20MB.");
            }
            try {
                product.setImageType(productDTO.getImage().getContentType());
                byte[] imageBytes = productDTO.getImage().getBytes();
                product.setImageData(Base64.getEncoder().encodeToString(imageBytes));
            } catch (IOException e) {
            	e.printStackTrace();
            }
        }


        productRepository.save(product);
    }

	@Override
	public void updateProduct(ProductDTO updatedProductDTO, MultipartFile image, String tenancyAlias) {

		Product existingProduct = productRepository.findById(updatedProductDTO.getId()).orElse(null);
	
		if (existingProduct == null) {
	        throw new IllegalArgumentException("Le produit avec l'ID " + updatedProductDTO.getId() + " n'existe pas.");
	    }

	    // Mise à jour des champs non liés à l'image
	    existingProduct.setProductName(updatedProductDTO.getProductName() != null ? updatedProductDTO.getProductName() : existingProduct.getProductName());
	    existingProduct.setProductDescription(updatedProductDTO.getProductDescription() != null ? updatedProductDTO.getProductDescription() : existingProduct.getProductDescription());
	    existingProduct.setProductPrice(updatedProductDTO.getProductPrice() != null ? updatedProductDTO.getProductPrice() : existingProduct.getProductPrice());
	    existingProduct.setProductStock(updatedProductDTO.getProductStock() != null ? updatedProductDTO.getProductStock() : existingProduct.getProductStock());
	    existingProduct.setFabricationDate(updatedProductDTO.getFabricationDate() != null ? updatedProductDTO.getFabricationDate() : existingProduct.getFabricationDate());
	    existingProduct.setExpirationDate(updatedProductDTO.getExpirationDate() != null ? updatedProductDTO.getExpirationDate() : existingProduct.getExpirationDate());
//	    existingProduct.setDeliveryDay(updatedProductDTO.getDeliveryDay() != null ? DeliveryDay.valueOf(updatedProductDTO.getDeliveryDay()) : existingProduct.getDeliveryDay());
	    existingProduct.setDeliveryRecurrence(updatedProductDTO.getDeliveryRecurrence() != null ? DeliveryRecurrence.valueOf(updatedProductDTO.getDeliveryRecurrence()) : existingProduct.getDeliveryRecurrence());

	    if (updatedProductDTO.getUserId() != null) {
	        User newUser = userRepository.findById(updatedProductDTO.getUserId())
	                .orElseThrow(() -> new IllegalArgumentException("Utilisateur avec l'ID " + updatedProductDTO.getUserId() + " n'existe pas."));
	        existingProduct.setUser(newUser);
	    }
	    
	    // Gestion de l'image
	    if (image != null && !image.isEmpty()) {
	        try {
	            existingProduct.setImageType(image.getContentType());
	            byte[] imageBytes = image.getBytes();
	            existingProduct.setImageData(Base64.getEncoder().encodeToString(imageBytes));
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    // Sauvegarde du produit mis à jour
	    productRepository.save(existingProduct);
	
	}

	@Override
	public List<Product> findAll(Long tenancyId) {
		return ((List<Product>) productRepository.findAll()).stream()
				.filter(u -> u.getTenancy().getTenancyId() == tenancyId).toList();
	}

	@Override
	public List<Product> findAll(String tenancyAlias) {
		return productRepository.findByTenancyAlias(tenancyAlias);
	}

	@Override
	public List<Product> findShoppableProductsByTenancy(Tenancy tenancy) {
	    return productRepository.findByTenancy(tenancy).stream()
	            .filter(Product::isShoppable) // Garder uniquement les contrats shoppables
	            .toList();
	}
}
