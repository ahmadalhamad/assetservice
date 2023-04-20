package com.example.assetservice.controller;

import com.example.assetservice.model.Asset;
import com.example.assetservice.model.License;
import com.example.assetservice.repository.AssetRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/assets")
public class AssetController {
    private AssetRepository repository;

    public AssetController(AssetRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Asset> getAssets(){
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Asset getAsset(@PathVariable int id){
        Asset out = repository.getReferenceById(id);
        if(out != null) {
            return out;
        } else {
            throw new IllegalStateException("Asset id is invalid.");
        }
    }

    public Asset getLicensing(@PathVariable int id){

        Optional<License> maybeLicense = (Optional<License>) repository.findById(id);
        if(maybeLicense.isPresent()){
            License license = maybeLicense.get();
            Optional<License> maybeOrganization = licenseClient
                    .getOrganization(license.getOrganizationId());
            if(maybeOrganization.isPresent()){
                Organization organization = maybeOrganization.get();
                license.setOrganization(organization);
                return license;
            }
        } else {
            throw new IllegalStateException("licensing id is invalid.");
        }
        return null;
    }




    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public int create(@RequestBody Asset asset){
        Asset addedAsset = repository.save(asset);
        return addedAsset.getId();
    }

}
