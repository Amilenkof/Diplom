package ru.skypro.homework.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;

@RestController
@RequestMapping("ads")
public class AdsController {

    @GetMapping
    public ResponseEntity<AdsDto> getAllAds() {
        return ResponseEntity.ok(new AdsDto());
    }

    @PostMapping()
    public ResponseEntity<AdDto> addAd(@RequestBody MultipartFile image,
                                       @RequestBody CreateOrUpdateAdDto createOrUpdateAdDto) {
        return ResponseEntity.ok(new AdDto());
    }

    @GetMapping("/{adId}")
    public ResponseEntity<ExtendedAdDto> getAd(@PathVariable long adId) {
        return ResponseEntity.ok(new ExtendedAdDto());
    }

    @DeleteMapping("/{adId}")
    public ResponseEntity<Void> deleteAd(@PathVariable long adId) {
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{adId}")
    public ResponseEntity<AdDto> updateInfoAd(@PathVariable long adId,
                                              @RequestBody CreateOrUpdateAdDto createOrUpdateAdDto) {
        return ResponseEntity.ok(new AdDto());
    }

    @GetMapping("/me")
    public ResponseEntity<AdsDto> getAdsMe() {
        return ResponseEntity.ok(new AdsDto());
    }

}