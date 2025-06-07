package com.github.frog.features.resources.controller;

import com.github.frog.features.resources.dto.ResourceResponse;
import com.github.frog.features.resources.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/resources")
@RequiredArgsConstructor
public class ResourceController {

    private final ResourceService resourceService;

    @PostMapping(value = "upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResourceResponse> createResource(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.status(HttpStatus.OK).body(resourceService.createResource(file));
    }

    @GetMapping("download/{id}")
    public ResponseEntity<ResourceResponse> getResourceById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(resourceService.getResourceById(id));
    }
}
