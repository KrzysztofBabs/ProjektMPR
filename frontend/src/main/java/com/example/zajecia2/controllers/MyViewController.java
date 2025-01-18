package com.example.zajecia2.controllers;


import com.example.zajecia2.exceptions.CantUpdateAuto_NotFoundException;
import com.example.zajecia2.exceptions.NotFoundException;

import com.example.zajecia2.exceptions.NotFoundExceptionInUpdating;
import com.example.zajecia2.model.Auto;
import com.example.zajecia2.services.AutoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MyViewController {
    private AutoService autoService;

    public MyViewController(AutoService autoService) {
        this.autoService = autoService;
    }

    @GetMapping("/view/all")
    public String displayAllCars(Model model){
        List<Auto> listaAut = autoService.getAll();
        model.addAttribute("Auta", listaAut);
        return "viewAll";

    }


    @GetMapping("/view/add")
    public String addAuto(Model model){
        model.addAttribute("Auto", new Auto());
        return "add";
    }
    @PostMapping("/view/add")
    public String sendForm(@ModelAttribute Auto auto){
        autoService.create(auto);
        return "redirect:/view/all";
    }




    @GetMapping("/view/delete")
    public String deleteCar(Model model){
        model.addAttribute("Auto", new Auto());
        return "delete";
    }


    @PostMapping("/view/delete")
    public String deleteCar(@RequestParam("id") Long id, Model model) {
        try{
            ResponseEntity<Void> response = autoService.deleteById(id);
            return "redirect:/view/all";
        }catch(NotFoundException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "errorPage";
        }

    }




    @GetMapping("/view/update")
    public String updateCar(Model model){
        model.addAttribute("Auto", new Auto());
        return "update";
    }



    @PostMapping("/view/update")
    public String updateCar(@ModelAttribute Auto auto, Model model){
        try{
            ResponseEntity<Auto> response = autoService.updateAuto(auto);
            if(response.getStatusCode() == HttpStatus.OK){
                return "redirect:/view/all";
            }

        }catch(NotFoundExceptionInUpdating ex){
            model.addAttribute("errorMessage", ex.getMessage());
            return "errorPage";

        }
        return "redirect:/view/all";

    }

//    @PostMapping("/view/update")
//    public String updateCar(@ModelAttribute Auto auto){
//        autoService.updateAuto(auto);
//        return "redirect:/view/all";
//    }


}








//@PostMapping("/view/delete")
//public String deleteCar(@RequestParam("id") Long id, Model model){
//    try{
//         ResponseEntity<Void> response = autoService.deleteById(id);
//         if(response.getStatusCode()== HttpStatus.NOT_FOUND){
//             return "errorPage";
//         }
//
//    }catch(NotFoundException ex){
//        model.addAttribute("errorMessage", ex.getMessage());
//        return "errorPage";
//    }
//    return "redirect:/view/all";
//}
//    @PostMapping("/view/delete")
//    public String deleteCar(@RequestParam("id") Long id){
//        autoService.deleteById(id);
//        return "redirect:/view/all";
//
//    }
