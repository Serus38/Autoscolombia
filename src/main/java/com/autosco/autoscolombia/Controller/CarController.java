package com.autosco.autoscolombia.Controller;

import java.util.List;
import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;

import com.autosco.autoscolombia.Model.Car;
import com.autosco.autoscolombia.Services.CarService;
import com.autosco.autoscolombia.Services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@Controller
@RequestMapping(value = "/cars")
@Tag(name = "CarController", description = "Controlador de Carros")
public class CarController {

    @Autowired
    CarService carService;

    @Autowired
    UserService userService;

    @GetMapping("/listar")
    @Operation(summary = "Listar autos")
    public String listCar(Model model) {
        List<Car> cars = carService.getAllCars();
        model.addAttribute("cars", cars);
        return "cars/listcars";
    }

    @GetMapping("/crear")
    @Operation(summary = "Crear autos")
    public String newCar(Model model) {
        model.addAttribute("car", new Car());
        return "cars/newcar";
    }

    @PostMapping("/guardar")
    @Operation(summary = "Guardar auto", description = "Guarda un nuevo carro o actualiza uno existente")
    public String saveCar(@ModelAttribute Car car, BindingResult result, RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            return "cars/newcar";
        }

        // Verificar si la placa ya existe
        Car existingCar = carService.findByPlate(car.getPlate());
        if (existingCar != null && (car.getId() == null || !existingCar.getId().equals(car.getId()))) {
            redirectAttrs.addFlashAttribute("mensaje", "La placa ya existe. Por favor, ingrese una placa diferente.");
            return "redirect:/cars/crear";
        }

        // Registrar el tiempo de creación si es un nuevo auto
        if (car.getId() == null) { // Suponiendo que `id` es null para autos nuevos
            car.setCreationTime(LocalDateTime.now());
        }

        carService.save(car);
        redirectAttrs.addFlashAttribute("mensaje", "Registro guardado exitosamente.");
        return "redirect:/cars/listar";
    }

    @GetMapping("/delete/{id}")
    @Operation(summary = "Eliminar auto", description = "Elimina un carro por su ID")
    public String deleteCar(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        carService.delete(id);
        redirectAttrs.addFlashAttribute("mensaje", "Registro eliminado");
        return "redirect:/cars/listar";
    }

    @GetMapping("/edit/{id}")
    @Operation(summary = "Editar auto", description = "Edita un carro por su ID")
    public String editStudent(@PathVariable Long id, Model model) {
        Car car = carService.getCarById(id);
        model.addAttribute("car", car);
        return "cars/newcar";
    }

    @GetMapping("/pago")
    @Operation(summary = "Seleccionar vehículo para pago", description = "Permite seleccionar un vehículo para calcular el pago")
    public String selectCarForPayment(Model model) {
        List<Car> cars = carService.getAllCars(); // Obtener todos los vehículos
        model.addAttribute("cars", cars);
        return "Pay/pay"; // Asegúrate de que esta plantilla exista
    }

    @PostMapping("/pago/calcular")
    @Operation(summary = "Calcular pago", description = "Calcula el pago basado en el tiempo transcurrido para un vehículo seleccionado")
    public String calculatePayment(@RequestParam Long carId, Model model) {
        Car car = carService.getCarById(carId);
        if (car == null) {
            model.addAttribute("mensaje", "El vehículo seleccionado no existe.");
            return "redirect:/cars/pago";
        }

        if (car.getCreationTime() == null) {
            model.addAttribute("mensaje", "El tiempo de creación no está registrado.");
            return "redirect:/cars/pago";
        }

        // Calcular el tiempo transcurrido
        long totalMinutes = java.time.Duration.between(car.getCreationTime(), LocalDateTime.now()).toMinutes();
        long hoursElapsed = totalMinutes / 60;
        long minutesElapsed = totalMinutes % 60;

        // Calcular el valor a pagar
        double paymentPerHour = 3500; 
        double paymentPerMinute = paymentPerHour / 70;// Pago por minuto
        double payment = (hoursElapsed * paymentPerHour) + (minutesElapsed * paymentPerMinute);

        // Redondear el pago a un entero
        int roundedPayment = (int) Math.round(payment);

        // Pasar los datos al modelo
        List<Car> cars = carService.getAllCars(); // Obtener todos los vehículos
        model.addAttribute("cars", cars);
        model.addAttribute("car", car);
        model.addAttribute("hoursElapsed", hoursElapsed);
        model.addAttribute("minutesElapsed", minutesElapsed);
        model.addAttribute("payment", roundedPayment);
        return "Pay/pay";
    }

    @PostMapping("/pago/confirmar")
    @Operation(summary = "Confirmar pago", description = "Confirma el pago de un vehículo seleccionado y elimina el auto")
    public String confirmPayment(@RequestParam Long carId, RedirectAttributes redirectAttrs) {
        Car car = carService.getCarById(carId);
        if (car == null) {
            redirectAttrs.addFlashAttribute("mensaje", "El vehículo seleccionado no existe.");
            return "redirect:/cars/pago";
        }

        // Eliminar el auto de la base de datos
        carService.delete(carId);

        // Agregar un mensaje de confirmación
        redirectAttrs.addFlashAttribute("mensaje", "El pago del vehículo con placa " + car.getPlate() + " ha sido confirmado y el vehículo ha sido eliminado.");
        
        // Redirigir a la página de inicio
        return "redirect:/";
    }
}

