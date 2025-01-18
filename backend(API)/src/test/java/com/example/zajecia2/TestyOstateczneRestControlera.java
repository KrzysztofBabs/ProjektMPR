package com.example.zajecia2;

import com.example.zajecia2.controllers.MyRestController;
import com.example.zajecia2.exceptions.CantDeleteAuto_NotFoundException;
import com.example.zajecia2.exceptions.NotFoundException;
import com.example.zajecia2.model.Auto;
import com.example.zajecia2.services.AutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
//import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;


@WebMvcTest(MyRestController.class)
public class TestyOstateczneRestControlera {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AutoService mockAutoService;
    @InjectMocks
    private MyRestController autoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicjalizuje mocki i wstrzykuje je do kontrolera
    }



    @Test
    public void testWyswietlAuta() {

        AutoService mockAutoService = mock(AutoService.class);

        Auto auto1 = new Auto( "Toyota", 1999);
        Auto auto2 = new Auto( "Honda", 2000);
        List<Auto> mockAuta = Arrays.asList(auto1, auto2);
        when(mockAutoService.getAll()).thenReturn(mockAuta);

        MyRestController myRestController = new MyRestController(mockAutoService);
        List<Auto> wynik = myRestController.wyswietlAuta();

        assertEquals(2, wynik.size());
        assertEquals("Toyota", wynik.get(0).getModel());
        assertEquals("Honda", wynik.get(1).getModel());
    }
    @Test
    public void testDodajAuto(){
        AutoService mockAutoService=mock(AutoService.class);
        MyRestController myRestController= new MyRestController(mockAutoService);
        Auto autoDoDodania = new Auto("Toyota",2000);
        myRestController.dodajAuto(autoDoDodania);
        verify(mockAutoService).add(autoDoDodania);

    }



    @Test
    public void aktualizujAuto2_Sukces(){
        Auto auto = new Auto();
        doNothing().when(mockAutoService).update(auto);
        ResponseEntity<Auto> response = autoController.aktualizujAuto2(auto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNull(response.getBody());
    }

    @Test
    public void aktualizujAuto2_Blad(){
        Auto auto = new Auto();
        doThrow(new NotFoundException()).when(mockAutoService).update(auto);
        ResponseEntity<Auto> response = autoController.aktualizujAuto2(auto);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testUsunAutoPoId_Sukces() {
        Long id = 1L;
        doNothing().when(mockAutoService).deleteAutoById(id);   // chodzi w tym o to ze doNothing nie zwroci zadnego wyjaktku wiec tak naprawde daje kod 200
        ResponseEntity<Void> response = autoController.usunAutoPoId(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }
    @Test
    public void testUsunAutoPoId_Blad() {
        Long id = 1L;
        doThrow(new NotFoundException()).when(mockAutoService).deleteAutoById(id);
        ResponseEntity<Void> response = autoController.usunAutoPoId(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }








//    @Test
//    public void testUsunAutoJestSukces() {
//
//        AutoService mockAutoService = mock(AutoService.class);
//        MyRestController myRestController = new MyRestController(mockAutoService);
//
//        Long idDoUsuniecia = 1L;
//
//        when(mockAutoService.existsById(idDoUsuniecia)).thenReturn(true);
//
////        ResponseEntity<String> response = myRestController.deleteAutoById(idDoUsuniecia);
//
//        verify(mockAutoService, times(1)).deleteAutoById(idDoUsuniecia);
////        assertEquals(200, response.getStatusCodeValue());
//    }

//    @Test
//    public void testUsunAutoNieZanlezione() {
//        AutoService mockAutoService = mock(AutoService.class);
//        MyRestController myRestController = new MyRestController(mockAutoService);
//        Long idNieistniejacegoAuta = 99L;
//
////        symulacja ze auta nie ma w bazie
//        when(mockAutoService.existsById(idNieistniejacegoAuta)).thenReturn(false);
//
//        assertThrows(CantDeleteAuto_NotFoundException.class,() -> {
//            myRestController.usunAutoPoId(idNieistniejacegoAuta);
//        });
//
//        //weryfikacja ze metoda deleteAutoById nie zostala wywoana w ogole
//        verify(mockAutoService, never()).deleteAutoById(anyLong());
//    }







    //    @Test
//    public void testUsunAuto(){
//        AutoService mockAutoService = mock(AutoService.class);
//        MyRestController myRestController = new MyRestController(mockAutoService);
////        String modelDoUsuniecia = "Toyota";
//        Long idDoUsuniecia=1L;
//        myRestController.usunAutoPoId(idDoUsuniecia);
//        verify(mockAutoService,times(1)).deleteAutoById(idDoUsuniecia);
//
//    }

    //    @Test
//    public void testAktualizujAuto(){
//        AutoService mockAutoService=mock(AutoService.class);
//        MyRestController myRestController= new MyRestController(mockAutoService);
//        Auto autoDoAktualizacji = new Auto("Toyota",2000);
//        myRestController.aktualizujAuto2(autoDoAktualizacji);
//        verify(mockAutoService).update(autoDoAktualizacji);
//
//    }



//    @Test
//    public void testGetByModel(){
//
//        AutoService mockAutoService=mock(AutoService.class);
//        Auto auto1 = new Auto("Toyota", 1999);
//        Auto auto2 = new Auto("Cupra", 2024);
////        List<Auto> mockAuta = Arrays.asList(auto1,auto2);
//        when(mockAutoService.getByModel("Toyota")).thenReturn(Arrays.asList(auto1));
//        when(mockAutoService.getByModel("Cupra")).thenReturn(Arrays.asList(auto2));
//
////        utworzenie controlera ze zmockowanym serwisem
//        MyRestController myRestController = new MyRestController(mockAutoService);
//
//
//        List<Auto> wynik0 = myRestController.getByModel("Toyota");
//        List<Auto> wynik1 = myRestController.getByModel("Cupra");
//
//        assertEquals(1,wynik0.size());
//        assertEquals("Toyota", wynik0.get(0).getModel());
//        assertEquals("Cupra", wynik1.get(0).getModel());
//
//    }

//    @Test
//    public void testGetByRokProdukcji(){
//        AutoService mockAutoService=mock(AutoService.class);
//        Auto auto1 = new Auto("Toyota", 1999);
//        Auto auto2 = new Auto("Cupra", 2024);
//
//        when(mockAutoService.getByRokProdukcji(1999)).thenReturn(Arrays.asList(auto1));
//        when(mockAutoService.getByRokProdukcji(2024)).thenReturn(Arrays.asList(auto2));
//
//        MyRestController myRestController = new MyRestController(mockAutoService);
//
//        List<Auto> wynik0 = myRestController.getByRokProdukcji(1999);
//        List<Auto> wynik1 = myRestController.getByRokProdukcji(2024);
//
//        assertEquals(1,wynik0.size());
//        assertEquals(1999, wynik0.get(0).getRokProdukcji());
//        assertEquals(2024, wynik1.get(0).getRokProdukcji());
//
//    }




//    @Test
//    public void testAktualizujAuto() throws Exception {
//        Auto autoDoAktualizacji = new Auto("Toyota", 2000);
//
//        mockMvc.perform(patch("/auto/update")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"model\": \"Toyota\", \"rokProdukcji\": 2000}"))
//                .andExpect(status().isOk());
//
//        verify(mockAutoService, times(1)).update1(autoDoAktualizacji);
//    }





}
