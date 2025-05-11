package com.autosco.autoscolombia.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.autosco.autoscolombia.Model.Car;

@Service
public interface CarService {
    
    Car findByPlate(String plate);
    List<Car> getAllCars();
    void save(Car car);
    void delete(Long id);
    void update(Car car);
    Car getCarById(Long id);
    
}
