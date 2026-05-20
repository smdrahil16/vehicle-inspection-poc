package com.example.demo.controller;

import com.example.demo.dto.CarResponseDto;
import com.example.demo.service.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    private CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<CarResponseDto> saveCar(
            @RequestParam String vinNumber,
            @RequestParam String carNumber,
            @RequestParam String carModel,
            @RequestParam String inspectionType,
            @RequestParam(required = false) String comments,
            @RequestParam MultipartFile photo
    )   throws IOException{
        CarResponseDto response = carService.
                saveCar(
                        vinNumber,
                        carNumber,
                        carModel,
                        inspectionType,
                        comments,
                        photo
                );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CarResponseDto>> getAllCars(){
        List<CarResponseDto> response = carService.getAllCars();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/test")
    public String test() {
        return "NEW DEPLOYMENT WORKING";
    }

    @GetMapping("/vin/{vinNumber}")
    public ResponseEntity<
            CarResponseDto>
    getCarByVinNumber(

            @PathVariable
            String vinNumber
    ) {

        CarResponseDto response =
                carService
                        .getCarByVinNumber(
                                vinNumber
                        );

        if (response == null) {

            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity
                .ok(response);
    }

}
