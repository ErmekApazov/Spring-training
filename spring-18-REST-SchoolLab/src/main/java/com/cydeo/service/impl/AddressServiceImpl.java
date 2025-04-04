package com.cydeo.service.impl;

import com.cydeo.client.WeatherApiClient;
import com.cydeo.dto.AddressDTO;
import com.cydeo.dto.weather.WeatherDTO;
import com.cydeo.entity.Address;
import com.cydeo.util.MapperUtil;
import com.cydeo.repository.AddressRepository;
import com.cydeo.service.AddressService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final MapperUtil mapperUtil;
    private final WeatherApiClient weatherApiClient;

    @Value("${access_key}")
    private String access_key;

    public AddressServiceImpl(AddressRepository addressRepository, MapperUtil mapperUtil, WeatherApiClient weatherApiClient) {
        this.addressRepository = addressRepository;
        this.mapperUtil = mapperUtil;
        this.weatherApiClient = weatherApiClient;
    }

    @Override
    public List<AddressDTO> findAll() {
        return addressRepository.findAll()
                .stream()
                .map(address -> mapperUtil.convert(address, new AddressDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public AddressDTO findById(Long id) throws Exception {

        Address foundAddress = addressRepository.findById(id)
                .orElseThrow(() -> new Exception("No Address Found!"));

        AddressDTO addressDTO = mapperUtil.convert(foundAddress, new AddressDTO());
        addressDTO.setCurrentTemperature(getCurrentWeather(addressDTO.getCity()).getCurrent().getTemperature());

        return addressDTO;

    }

    @Override
    public AddressDTO update(AddressDTO addressDTO) throws Exception {

        // Check if the address exists in the database using its ID.
        // If not found, throw an exception to prevent updating a non-existent record.
        addressRepository.findById(addressDTO.getId()).orElseThrow(() -> new Exception("No Address Found!"));

        // Convert the received AddressDTO into an Address entity.
        // This is necessary because JPA repositories work with entity objects, not DTOs.
        Address addressToSave = mapperUtil.convert(addressDTO, new Address());

        // Save the updated Address entity to the database.
        // This ensures persistence of changes in the database.
        addressRepository.save(addressToSave);

        // Convert the saved Address entity back into an AddressDTO.
        // This is needed because the service layer typically returns DTOs, not entities, to the controller.
        AddressDTO updatedAddress = mapperUtil.convert(addressToSave, new AddressDTO());

        // Fetch the current temperature based on the updated address's city.
        // This adds additional useful data (weather information) to the response.
        updatedAddress.setCurrentTemperature(getCurrentWeather(updatedAddress.getCity()).getCurrent().getTemperature());

        // Return the fully updated AddressDTO with all relevant details.
        // This allows the API response to contain both address details and real-time weather data.
        return updatedAddress;

    }

    @Override
    public AddressDTO create(AddressDTO addressDTO) throws Exception {

        Optional<Address> foundAddress = addressRepository.findById(addressDTO.getId());

        if (foundAddress.isPresent()) {
            throw new Exception("Address Already Exists!");
        }

        Address addressToSave = mapperUtil.convert(addressDTO, new Address());

        addressRepository.save(addressToSave);

        return mapperUtil.convert(addressToSave, new AddressDTO());

    }

    private WeatherDTO getCurrentWeather(String city) {
        return weatherApiClient.getCurrentWeather(access_key, city);
    }

}