package com.example.curso.webapp.curso_sb.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController // Anotación para indicar que es un controlador REST
@RequestMapping("/d")
public class UserRestController {

    @GetMapping("/rest")
    public Map<String, String> details() { // Cambia el tipo de retorno a Map
        Map<String, String> response = new HashMap<>();
        response.put("title", "Hola desde Spring Boot");
        // Puedes agregar más datos aquí
        response.put("message", "Este es un mensaje desde el controlador REST");
        return response; // Retorna el Map que se convertirá automáticamente a JSON
    }
}