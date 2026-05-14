package cz.cvut.fel.nss.user_service.controller;

import cz.cvut.fel.nss.user_service.model.DTO.AddressDTO;
import cz.cvut.fel.nss.user_service.model.DTO.AddressRequest;
import cz.cvut.fel.nss.user_service.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for address management of the currently authenticated user
 */
@RestController
@RequestMapping("/users/me/addresses")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    // List all addresses
    @GetMapping
    public ResponseEntity<List<AddressDTO>> listMyAddresses(Authentication auth) {
        return ResponseEntity.ok(addressService.getAddressesForUser(auth.getName()));
    }

    // Add new address
    @PostMapping
    public ResponseEntity<AddressDTO> addAddress(@Valid @RequestBody AddressRequest request,
                                                 Authentication auth) {
        AddressDTO created = addressService.addAddressForUser(auth.getName(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Edit existing address
    @PutMapping("/{id}")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable Long id,
                                                    @Valid @RequestBody AddressRequest request,
                                                    Authentication auth) {
        AddressDTO updated = addressService.updateAddressForUser(auth.getName(), id, request);
        return ResponseEntity.ok(updated);
    }

    // Delete address
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id, Authentication auth) {
        addressService.deleteAddressForUser(auth.getName(), id);
        return ResponseEntity.noContent().build();
    }
}