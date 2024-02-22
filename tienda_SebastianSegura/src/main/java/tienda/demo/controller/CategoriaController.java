/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tienda.demo.controller;

import tienda.demo.service.CategoriaService;
import lombok.extern.slf4j.Slf4j; // Manejo controladores con interfaz en la vista
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/categoria") // Creando EndPoint, este es el API
public class CategoriaController {
    
    @Autowired 
    private CategoriaService categoriaService; //categoriaService traer adentro la lista anteriormente creada

    @GetMapping("/listado")
    public String inicio(Model model) { 
        var categorias = categoriaService.getCategorias(false); // se crea variable categoria que obtienen todas las categorias que vienen de getCategorias
        model.addAttribute("categorias", categorias); // "categorias" es un nombre que le estamos dando a la variable
        model.addAttribute("totalCategorias", categorias.size()); // totalCategorias == categorias.size()
        return "/categoria/listado";
    }
}
