package com.example.zajecia2.services;

import com.example.zajecia2.exceptions.*;
import com.example.zajecia2.model.Auto;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.HttpComponentsClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



//@Component
@Service
public class AutoService{
    private RestClient restClient;
    private StringUtilsService stringUtilsService;


//    @Autowired
    public AutoService() {
        this.restClient = RestClient.create("http://localhost:8081/");

    }

    public AutoService(RestClient restClient) {
        this.restClient = restClient;
    }

//    public Optional<Auto> getById(Long id){
//        return this.repository.findById(id);
//    }

    public List<Auto> getAll(){
        List<Auto> autoList = restClient.get()
                .uri("/auta/zRepo")
                .retrieve().body(new ParameterizedTypeReference<List<Auto>>() {});
        return autoList;
    }

    public void create(Auto auto){
        ResponseEntity<Void> response = this.restClient.post()
                .uri("/autoo/dodaj")
                .body(auto)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .toBodilessEntity();
    }


public ResponseEntity<Void> deleteById(Long id) {
    try{
        ResponseEntity<Void> response = this.restClient.delete()
                .uri("/auto/wyrzuc/{id}", id)
                .retrieve()
                .toBodilessEntity();

        return response;

    }catch(HttpClientErrorException.NotFound ex) { // tutaj dostaje info takie z backendu: ResponseEntity.status(HttpStatus.NOT_FOUND) i rzuca NFEx
        throw new NotFoundException(); // tutaj mi wystrzela do kontrolera do bloku catch i tam wyswietla komunikat
    }
}

    public ResponseEntity<Auto> updateAuto(Auto auto){
        try {
            ResponseEntity<Auto> response = this.restClient.put()
                    .uri("/autko/update")
                    .body(auto)
                    .contentType(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .toEntity(Auto.class);
            return response;
        }catch(HttpClientErrorException.NotFound ex){
            throw new NotFoundExceptionInUpdating();

        }

    }


//    public void updateAuto(Auto auto){
//        ResponseEntity<Auto> response = this.restClient.put()
//                .uri("/autko/update")
//                .body(auto)
//                .contentType(MediaType.APPLICATION_JSON)
//                .retrieve()
//                .toEntity(Auto.class);
//
//    }






    //    public void deleteById(Long id){
//        ResponseEntity<Void> response = this.restClient.delete()
//                .uri("/auto/wyrzuc/{id}", id)
//                .retrieve()
//                .toBodilessEntity();
//
//    }








//    public List<Auto> getByModel(String model){
//        List<Auto> autoList = restClient.get()
//                .uri("/auto/model/{model}")
//                .retrieve().body(new ParameterizedTypeReference<List<Auto>>() {});
//        return autoList;
//    }
//    public List<Auto> getById(Long id){
//        List<Auto> autoList = restClient.get()
//                .uri("/autoo/id/{id}")
//                .retrieve().body(new ParameterizedTypeReference<List<Auto>>() {});
//        return autoList;
//    }
//
//    public List<Auto> getByRokProdukcji(int rokProdukcji){
//        List<Auto> autoList = restClient.get()
//                .uri("/auto/rokProdukcji/{rokProdukcji}")
//                .retrieve()
//                .body(new ParameterizedTypeReference<List<Auto>>() {});
//        return autoList;
//    }



}










