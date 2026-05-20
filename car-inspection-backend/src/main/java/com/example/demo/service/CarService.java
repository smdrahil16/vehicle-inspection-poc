package com.example.demo.service;

import com.example.demo.dto.CarResponseDto;
import com.example.demo.entity.Car;
import com.example.demo.repository.CarRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService {

    private CarRepository carRepository;
    private FileStorageService fileStorageService;

    public CarService(CarRepository carRepository,FileStorageService fileStorageService){
        this.carRepository=carRepository;
        this.fileStorageService=fileStorageService;
    }

    public CarResponseDto saveCar(
            String vinNumber,
            String carNumber,
            String carModel,
            String inspectionType,
            String comments,
            MultipartFile photo
    ) throws IOException{
        if (carRepository.existsByVinNumber(vinNumber)) {

            throw new RuntimeException(
                    "Car with this VIN already exists"
            );
        }

        String imagePath = fileStorageService.saveFile(photo);

        Car car = new Car();
        car.setVinNumber(vinNumber);
        car.setCarNumber(carNumber);
        car.setCarModel(carModel);
        car.setInspectionType(inspectionType);
        car.setComments(comments);
        car.setImagePath(imagePath);

        Car savedCar = carRepository.save(car);

        CarResponseDto response = new CarResponseDto();

        response.setId(savedCar.getId());
        response.setVinNumber(savedCar.getVinNumber());
        response.setCarNumber(savedCar.getCarNumber());
        response.setCarModel(savedCar.getCarModel());
        response.setInspectionType(savedCar.getInspectionType());
        response.setComments(savedCar.getComments());
        response.setImagePath(
                "http://localhost:8080/"
                        + savedCar.getImagePath()
                        .replace("\\", "/")
        );
        response.setCreatedAt(savedCar.getCreatedAt());

        return response;
    }

    public List<CarResponseDto> getAllCars(){

        List<Car> cars = carRepository.findAll();

        return cars.stream()
                .map(car -> {
                    CarResponseDto dto = new CarResponseDto();
                    dto.setId(car.getId());
                    dto.setVinNumber(car.getVinNumber());
                    dto.setCarNumber(car.getCarNumber());
                    dto.setCarModel(car.getCarModel());
                    dto.setInspectionType(
                            car.getInspectionType()
                    );
                    dto.setComments(car.getComments());
                    dto.setImagePath(
                            car.getImagePath()
                    );
                    dto.setCreatedAt(car.getCreatedAt());

                    return dto;
                })
                .collect(Collectors.toList());
    }

    public CarResponseDto
    getCarByVinNumber(
            String vinNumber
    ) {

        Optional<Car> carOptional =
                carRepository
                        .findByVinNumber(
                                vinNumber
                        );

        if (carOptional.isEmpty()) {

            return null;
        }

        Car car =
                carOptional.get();

        CarResponseDto dto =
                new CarResponseDto();

        dto.setId(car.getId());
        dto.setVinNumber(
                car.getVinNumber()
        );
        dto.setCarNumber(
                car.getCarNumber()
        );
        dto.setCarModel(
                car.getCarModel()
        );
        dto.setInspectionType(
                car.getInspectionType()
        );
        dto.setComments(
                car.getComments()
        );

        dto.setImagePath(
                car.getImagePath()
        );

        dto.setCreatedAt(
                car.getCreatedAt()
        );

        return dto;
    }

}
