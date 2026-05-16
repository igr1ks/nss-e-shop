package cz.cvut.fel.nss.user_service.service;

import cz.cvut.fel.nss.user_service.model.BO.Address;
import cz.cvut.fel.nss.user_service.model.BO.User;
import cz.cvut.fel.nss.user_service.model.DTO.AddressDTO;
import cz.cvut.fel.nss.user_service.model.DTO.AddressRequest;
import cz.cvut.fel.nss.user_service.model.utils.UserMapper;
import cz.cvut.fel.nss.user_service.repository.AddressRepository;
import cz.cvut.fel.nss.user_service.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final UserMapper userMapper;

    public AddressServiceImpl(UserRepository userRepository,
                              AddressRepository addressRepository,
                              UserMapper userMapper) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AddressDTO> getAddressesForUser(String userEmail) {
        User user = findUserByEmail(userEmail);
        return user.getAddresses().stream()
                .map(userMapper::toAddressDTO)
                .toList();
    }

    @Override
    @Transactional
    public AddressDTO addAddressForUser(String userEmail, AddressRequest request) {
        User user = findUserByEmail(userEmail);

        Address address = new Address();
        address.setCity(request.getCity());
        address.setStreet(request.getStreet());
        address.setZipCode(request.getZipCode());
        user.addAddress(address);

        // Cascade=ALL na User.addresses -> Address se uloží spolu s userem.
        userRepository.save(user);
        return userMapper.toAddressDTO(address);
    }

    @Override
    @Transactional
    public AddressDTO updateAddressForUser(String userEmail, Long addressId, AddressRequest request) {
        Address address = findOwnedAddress(userEmail, addressId);

        address.setCity(request.getCity());
        address.setStreet(request.getStreet());
        address.setZipCode(request.getZipCode());

        Address saved = addressRepository.save(address);
        return userMapper.toAddressDTO(saved);
    }

    @Override
    @Transactional
    public void deleteAddressForUser(String userEmail, Long addressId) {
        Address address = findOwnedAddress(userEmail, addressId);

        // Remove from the owner
        address.getUser().removeAddress(address);
        userRepository.save(address.getUser());
    }

    // Helpers
    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + email));
    }

    /**
     * Find an address and check if belongs to this user
     * If address does not exist or belongs to someone else = 404 not found
     */
    private Address findOwnedAddress(String userEmail, Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new EntityNotFoundException("Address not found: " + addressId));

        if (!address.getUser().getEmail().equals(userEmail)) {
            throw new EntityNotFoundException("Address not found: " + addressId);
        }
        return address;
    }
}