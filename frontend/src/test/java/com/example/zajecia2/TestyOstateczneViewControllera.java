package com.example.zajecia2;

import com.example.zajecia2.controllers.MyViewController;
import com.example.zajecia2.exceptions.NotFoundException;
import com.example.zajecia2.exceptions.NotFoundExceptionInUpdating;
import com.example.zajecia2.model.Auto;
import com.example.zajecia2.services.AutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.thymeleaf.spring6.view.ThymeleafView;

import java.util.Arrays;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//@WebMvcTest(MyViewController.class)
public class TestyOstateczneViewControllera {

    @Mock
    private AutoService autoService;
    @InjectMocks
    private MyViewController myViewController;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/templates/");
        viewResolver.setSuffix(".html");

        mockMvc = MockMvcBuilders.standaloneSetup(myViewController)
                .setViewResolvers(viewResolver)
                .build();
    }

    private MockMvc mockMvc;


    @Test
    public void testDisplayAllCars() throws Exception {
        List<Auto> mockCars = Arrays.asList(
                new Auto("Toyota", 2020),
                new Auto("Honda", 2021)
        );
        when(autoService.getAll()).thenReturn(mockCars);
        mockMvc = MockMvcBuilders.standaloneSetup(myViewController).build();


        mockMvc.perform(get("/view/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("viewAll"))
                .andExpect(model().attribute("Auta", mockCars));

        verify(autoService, times(1)).getAll();
    }



    @Test
    public void testAddAuto_Get() throws Exception {
        mockMvc.perform(get("/view/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("add"))
                .andExpect(model().attributeExists("Auto"));
    }

    @Test
    public void testSendForm_Post() throws Exception {
        Auto auto = new Auto("Toyota", 2022);

        mockMvc.perform(post("/view/add")
                        .param("model", auto.getModel())
                        .param("rokProdukcji", String.valueOf(auto.getRokProdukcji())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/view/all"));

        // Sprawdzamy, czy metoda `create` została wywołana z odpowiednimi danymi
        ArgumentCaptor<Auto> autoCaptor = ArgumentCaptor.forClass(Auto.class);
        verify(autoService, times(1)).create(autoCaptor.capture());


        Auto capturedAuto = autoCaptor.getValue();
        assertNotNull(capturedAuto);
        assertEquals("Toyota", capturedAuto.getModel());
        assertEquals(2022, capturedAuto.getRokProdukcji());
    }








    @Test
    public void testUpdateCar_Get() throws Exception {
        mockMvc.perform(get("/view/update"))
                .andExpect(status().isOk())  // Oczekujemy statusu 200 OK
                .andExpect(view().name("update"));
    }


    @Test
    public void testUpdateCar_Post_Sukces() throws Exception {
        Auto auto = new Auto();

        auto.setId(1L);
        auto.setModel("toyota");
        auto.setRokProdukcji(2025);

        // Mockowanie odpowiedzi z autoService
        when(autoService.updateAuto(any(Auto.class)))
                .thenReturn(new ResponseEntity<>(auto, HttpStatus.OK));

        // Wykonanie testu
        mockMvc.perform(post("/view/update")
                        .flashAttr("auto", auto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/view/all"));

        verify(autoService, times(1)).updateAuto(any(Auto.class));
    }

    @Test
    public void testUpdateCar_Blad() throws Exception {
        Auto auto = new Auto();
        auto.setId(1L);
        // Mockowanie wyjątku, który ma zostać rzucony
        when(autoService.updateAuto(any(Auto.class)))
                .thenThrow(new NotFoundExceptionInUpdating("Nie mogę zaktualizować auta, ponieważ ono nie istnieje!"));

        // Wykonanie testu
        mockMvc.perform(post("/view/update")
                        .flashAttr("auto", auto))
                .andExpect(status().isOk())
                .andExpect(view().name("errorPage"))
                .andExpect(model().attribute("errorMessage", "Nie mogę zaktualizować auta, ponieważ ono nie istnieje!"));
    }






    @Test
    public void testDeleteCar_Get() throws Exception {
        mockMvc.perform(get("/view/delete"))
                .andExpect(status().isOk())
                .andExpect(view().name("delete"));
    }

    @Test
    public void testDeleteCar_Post_Sukces() throws Exception {
        Long carId = 1L;

        // Symulujemy, że auto istnieje
        mockMvc.perform(post("/view/delete")
                        .param("id", carId.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/view/all"));

        verify(autoService, times(1)).deleteById(carId);
    }

    @Test
    public void testDeleteCar_Post_Blad() throws Exception {
        Long carId = 1L;

        // Symulujemy, że auto nie istnieje, rzucając wyjątek
        doThrow(new NotFoundException())
                .when(autoService).deleteById(anyLong());

        mockMvc.perform(post("/view/delete")
                        .param("id", carId.toString()))
                .andExpect(status().isOk())
                .andExpect(view().name("errorPage"))
                .andExpect(model().attributeExists("errorMessage"));
    }








    //    @Test
//    public void testSendForm_Post() throws Exception {
//        Auto auto = new Auto("Toyota", 2022);
//
//        // Przesyłanie formularza
//        mockMvc.perform(post("/view/add")
//                        .flashAttr("Auto", auto)) // Używamy flashAttr z odpowiednią nazwą atrybutu
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/view/all"));
//
//        // Używamy ArgumentCaptor do przechwycenia obiektu Auto przekazanego do metody create
//        ArgumentCaptor<Auto> autoCaptor = ArgumentCaptor.forClass(Auto.class);
//
//        // Weryfikujemy, że metoda create została wywołana raz z odpowiednim obiektem
//        verify(autoService, times(1)).create(autoCaptor.capture());
//
//        // Sprawdzamy, czy przekazany obiekt Auto ma odpowiednie dane
//        Auto capturedAuto = autoCaptor.getValue();
////        assertEquals("Toyota", capturedAuto.getModel());
////        assertEquals(2022, capturedAuto.getRokProdukcji());
//    }



    //    @Test
//    public void testUpdateCarPost() throws Exception {
//        // Tworzymy obiekt Auto
//        Auto auto = new Auto("Toyota", 2022);
//        auto.setId(1L); // Ustawiamy przykładowe ID
//
//        // Przesyłanie formularza
//        mockMvc.perform(post("/view/update")
//                        .flashAttr("Auto", auto))  // flashAttr z obiektem Auto
//                .andExpect(status().is3xxRedirection())  // Oczekujemy przekierowania
//                .andExpect(redirectedUrl("/view/all"));  // Oczekujemy przekierowania do listy aut
//
//        // Używamy ArgumentCaptor, aby przechwycić obiekt przekazywany do metody updateAuto
//        ArgumentCaptor<Auto> autoCaptor = ArgumentCaptor.forClass(Auto.class);
//
//        // Weryfikujemy, że metoda updateAuto została wywołana raz z odpowiednim obiektem Auto
//        verify(autoService, times(1)).updateAuto(autoCaptor.capture());
//
//        // Sprawdzamy, czy obiekt Auto ma odpowiednie dane
//        Auto capturedAuto = autoCaptor.getValue();
//        assertNotNull(capturedAuto);  // Upewniamy się, że obiekt nie jest null
//        assertEquals("Toyota", capturedAuto.getModel());  // Weryfikujemy model
//        assertEquals(2022, capturedAuto.getRokProdukcji());  // Weryfikujemy rok produkcji
//        assertEquals(1L, capturedAuto.getId());  // Weryfikujemy, że ID jest ustawione
//    }

    //    @Test
//    public void testUpdateCarPost() throws Exception {
//        Auto auto = new Auto("Toyota", 2022);
//        auto.setId(1L);
//
//        mockMvc.perform(post("/view/update")
//                        .param("id", String.valueOf(auto.getId()))
//                        .param("model", auto.getModel())
//                        .param("rokProdukcji", String.valueOf(auto.getRokProdukcji())))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/view/all"));
//
//        ArgumentCaptor<Auto> autoCaptor = ArgumentCaptor.forClass(Auto.class);
//
//        verify(autoService, times(1)).updateAuto(autoCaptor.capture());
//
//
//        Auto capturedAuto = autoCaptor.getValue();
//        assertNotNull(capturedAuto);
//        assertEquals("Toyota", capturedAuto.getModel());
//        assertEquals(2022, capturedAuto.getRokProdukcji());
//        assertEquals(1L, capturedAuto.getId());
//    }



}
