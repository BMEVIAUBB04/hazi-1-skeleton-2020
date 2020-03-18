package hu.bme.aut.logistics.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import hu.bme.aut.logistics.model.Address;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class AddressRepositoryIT {
	
	@Autowired
    EntityManager em;

    @Autowired
    AddressRepository addressRepository;
    
    List<Address> budapestAddresses = new ArrayList<>();
    
    List<Address> addressesInside = new ArrayList<>();
    
    List<Long> addressIdsToRename = new ArrayList<>();
    
    public List<Address> getAddressesOfCity(String city){
        //TODO: itt hívd meg az általad írt repository metódust a 4.a. feladatra
    	return null;
    }
    
    public List<Address> getAddressesInside(double topLeftLat, double topLeftLong, double bottomRightLat, double bottomRightLong){
        //TODO: itt hívd meg az általad írt repository metódust a 4.b. feladatra 
    	return null;
    }
    
    public void renameStreet(String country, String zipCode, String oldStreet, String newStreet){
        //TODO: itt hívd meg az általad írt repository metódust a 4.c. feladatra 
    }
    
    @BeforeEach
    public void init() {
        budapestAddresses.add(saveAddress(47.29, 19.04, "HU", "Budapest", "1111", "street1", "2"));
        budapestAddresses.add(saveAddress(47.30, 19.06, "HU", "budapest", "1111", "street1", "2"));
        budapestAddresses.add(saveAddress(47.31, 19.05, "HU", "BUDAPEST", "1111", "street1", "2"));
        saveAddress(50, 19, "HU", "Other city", "1111", "street1", "2");
        
        addressesInside.add(saveAddress(47.31, 17.05, "HU", "Other city 2", "1111", "street1", "2"));
        addressesInside.add(saveAddress(48.31, 16.05, "HU", "Other city 3", "1111", "street1", "2"));
        
        addressIdsToRename.add(saveAddress(52, 13, "DE", "Berlin", "1111", "old street", "15").getId());
        addressIdsToRename.add(saveAddress(52, 13, "DE", "Berlin", "1111", "old street", "16").getId());
        saveAddress(52, 13, "DE", "Berlin", "1111", "other street", "16");
        saveAddress(52, 13, "DE", "Berlin", "1112", "old street", "15");
        saveAddress(52, 13, "US", "Berlin", "1111", "old street", "15");
    }

    Address saveAddress(double lat, double lng, String country, String city, String zipCode, String street, String number) {
        Address address = new Address(lat, lng, country, city, zipCode, street, number);
        return addressRepository.save(address);
    }
    
    @Test
    void testGetAddressesByCity() throws Exception {
        List<Address> foundBudapestAddresses = getAddressesOfCity("budapest");
        assertThat(foundBudapestAddresses).containsExactlyInAnyOrderElementsOf(budapestAddresses);
        
        List<Address> foundBudAddresses = getAddressesOfCity("bud");
        assertThat(foundBudAddresses).isEmpty();
    }
    
    @Test
    void testGetAddressesInside() throws Exception {
        List<Address> foundAddressesInside = getAddressesInside(49, 16, 47, 18);
        assertThat(foundAddressesInside).containsExactlyInAnyOrderElementsOf(addressesInside);
    }
    
    @Test
	void testRenameStreet() throws Exception {
    	
		String newStreet = "renamed street";
		renameStreet("DE", "1111", "old street", newStreet);
		em.clear();
		assertThat(addressRepository.findAll().stream()
				.filter(a -> a.getStreet().equals(newStreet))
				.map(Address::getId))
				.containsExactlyInAnyOrderElementsOf(addressIdsToRename);
	}
        
}
