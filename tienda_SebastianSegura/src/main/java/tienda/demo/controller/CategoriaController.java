package tienda.demo.controller;

import tienda.demo.domain.Categoria;
import tienda.demo.service.CategoriaService;
import tienda.demo.service.impl.FirebaseStorageServiceImpl;
import lombok.extern.slf4j.Slf4j; // Manejo controladores con interfaz en la vista
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@Slf4j
@RequestMapping("/categoria") // Creando EndPoint, este es el API
public class CategoriaController {
    
    @Autowired 
    private CategoriaService categoriaService; //categoriaService traer adentro la lista anteriormente creada

    @GetMapping("/listado")
    public String listado(Model model) { 
        var categorias = categoriaService.getCategorias(false); // se crea variable categoria que obtienen todas las categorias que vienen de getCategorias
        model.addAttribute("categorias", categorias); // "categorias" es un nombre que le estamos dando a la variable
        model.addAttribute("totalCategorias", categorias.size()); // totalCategorias == categorias.size()
        return "/categoria/listado";
    }
    
    @GetMapping("/nuevo")
    public String categoriaNuevo (Categoria categoria) {
        return "/categoria/modifica";
    }
    
    @Autowired
    private FirebaseStorageServiceImpl firebaseStorageService;
    
    @PostMapping("/guardar")
    public String categoriaGuardar (Categoria categoria,
            @RequestParam("imagenFile") MultipartFile imagenFile) {
        System.out.println("hola mundo");
        if (!imagenFile.isEmpty()) {
            categoriaService.save(categoria);
            categoria.setRutaImagen(
                firebaseStorageService.cargaImagen(
                imagenFile,
                "categorias",
                categoria.getIdCategoria()));
        }
        categoriaService.save(categoria);
        return "redirect:/categoria/listado";
    }
    
    @GetMapping("/eliminar/{idCategoria}")
    public String categoriaEliminar(Categoria categoria) {
        categoriaService.delete(categoria);
        return "redirect:/categoria/listado";
    }
    
    @GetMapping("/modificar/{idCategoria}")
    public String categoriaModificar (Categoria categoria, Model model) {
        categoria = categoriaService.getCategoria(categoria);
        model.addAttribute("categorias", categoria);
        return "/categoria/modifica";
    }
}
