package com.autosco.autoscolombia.Controller;

import java.util.List;

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

import com.autosco.autoscolombia.Model.User;
import com.autosco.autoscolombia.Services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@Controller
@RequestMapping(value = "/users")
@Tag(name = "UserController", description = "Controlador de usuarios")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/listar")
    @Operation(summary = "Listar usuarios")
    public String listUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users/listusers";
    }

    @GetMapping("/crear")
    @Operation(summary = "Crear usuarios")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "users/newuser";
    }

    @PostMapping("/guardar")
    @Operation(summary = "Guardar User", description = "Guarda un nuevo user o actualiza uno existente")
    public String saveUser(@ModelAttribute User user,
                            BindingResult result, RedirectAttributes redirectAttrs) {
        if (result.hasErrors()){
            return "users/newuser";
        }
        userService.save(user);
        redirectAttrs.addFlashAttribute("mensaje", "Registro guardado");
        return "redirect:/users/listar";
    }

    @GetMapping("/delete/{id}")
    @Operation(summary = "Eliminar user", description = "Elimina un user por su ID")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        userService.delete(id);
        redirectAttrs.addFlashAttribute("mensaje", "Registro eliminado");
        return "redirect:/users/listar";
    }

    @GetMapping("/edit/{id}")
    @Operation(summary = "Editar user", description = "Edita un user por su ID")
    public String editStudent(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "users/newuser";
    }

}
