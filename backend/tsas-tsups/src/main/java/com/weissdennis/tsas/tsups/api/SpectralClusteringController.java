package com.weissdennis.tsas.tsups.api;

import com.weissdennis.tsas.tsups.service.SpectralClusteringService;
import org.ejml.simple.SimpleMatrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/ts3/spectral-clustering")
public class SpectralClusteringController {

    private final SpectralClusteringService spectralClusteringService;

    @Autowired
    public SpectralClusteringController(SpectralClusteringService spectralClusteringService) {
        this.spectralClusteringService = spectralClusteringService;
    }

    @RequestMapping(path = "/laplacian", method = RequestMethod.GET)
    public HttpEntity<SimpleMatrix> getGraphLaplacian(@RequestParam(required = false) Double minRelation) {
        if (minRelation == null) {
            minRelation = 0d;
        }
        return new ResponseEntity<>(spectralClusteringService.getGraphLaplacian(minRelation), HttpStatus.OK);
    }

}
