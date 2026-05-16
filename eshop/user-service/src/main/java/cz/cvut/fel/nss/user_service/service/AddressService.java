package cz.cvut.fel.nss.user_service.service;

import cz.cvut.fel.nss.user_service.model.DTO.AddressDTO;
import cz.cvut.fel.nss.user_service.model.DTO.AddressRequest;

import java.util.List;

/**
 * Service API for address management
 * All operations under email of user
 */
public interface AddressService {
    /** Return all addresses of user */
    List<AddressDTO> getAddressesForUser(String userEmail);

    /** Add new address and return their DTO. */
    AddressDTO addAddressForUser(String userEmail, AddressRequest request);

    /**
     * Update user's address
     * @throws jakarta.persistence.EntityNotFoundException if address does not exist
     *         or don't belong to this address
     */
    AddressDTO updateAddressForUser(String userEmail, Long addressId, AddressRequest request);

    /**
     * Delete user's address
     * @throws jakarta.persistence.EntityNotFoundException if address does not exist
     *      *         or don't belong to this address
     */
    void deleteAddressForUser(String userEmail, Long addressId);
}