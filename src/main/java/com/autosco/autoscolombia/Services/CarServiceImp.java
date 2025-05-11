package com.autosco.autoscolombia.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.autosco.autoscolombia.Model.Car;
import com.autosco.autoscolombia.Repository.CarRepository;

@Service
public class CarServiceImp implements CarService {

    CarRepository carRepository;

    public CarServiceImp(CarRepository carRepository) {
            this.carRepository = carRepository;
        }
    
        public Car findByPlate(String plate) {
            return carRepository.findByPlate(plate);
    }

    @Override
    public void delete(Long id) {
        carRepository.deleteById(id);
    }

    @Override
    public void save(Car car) {
        carRepository.save(car);
    }

    @Override
    public void update(Car car) {
        carRepository.save(car);
    }

    @Override
    public Car getCarById(Long id) {
        return carRepository.findById(id).get();
    }

    @Override
    public List<Car> getAllCars() {
        return (List<Car>) carRepository.findAll();
    }

    
}
