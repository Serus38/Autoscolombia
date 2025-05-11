package com.autosco.autoscolombia.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/index")
public class IndexController {
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("title", "Inicio - Autos Colombia");
        return "index";
    }

    
    
}
