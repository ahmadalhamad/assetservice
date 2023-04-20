package com.example.assetservice.client;

import com.example.assetservice.model.License;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class LicenseClient {
    private RestTemplate restTemplate;

    public LicenseClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Optional<License> getOrganization(int organizationId){
        ResponseEntity<License> restExchange =
                restTemplate.exchange(
                        "http://organization-service/organizations/{organizationId}",
                        HttpMethod.GET,
                        null, License.class, organizationId);

        return Optional.ofNullable(restExchange.getBody());
    }
}
