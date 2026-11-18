package com.hrms.employee_service.dto.address;

import com.hrms.employee_service.dto.common.RequestDto;
import com.hrms.employee_service.model.Address;
import lombok.Getter;
import lombok.Setter;

import java.text.DecimalFormat;

@Getter
@Setter
public class AddressRequestDto extends RequestDto {
    public interface Create extends RequestDto.Create {}

    private String country;
    private String state;
    private String city;
    private String location;
    private String postalCode;
    private DecimalFormat latitude;
    private DecimalFormat longitude;

    public Address toAddress() {
        Address address = new Address();
        address.setCountry(country);
        address.setState(state);
        address.setCity(city);
        address.setLocation(location);
        address.setPostalCode(postalCode);
        address.setLatitude(latitude);
        address.setLongitude(longitude);
        return address;
    }
}
