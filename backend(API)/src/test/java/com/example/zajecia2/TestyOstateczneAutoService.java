package com.example.zajecia2;

import com.example.zajecia2.exceptions.CantDeleteAuto_NotFoundException;
import com.example.zajecia2.exceptions.CantUpdateAuto_NotFoundException;
import com.example.zajecia2.exceptions.NotFoundException;
import com.example.zajecia2.model.Auto;
import com.example.zajecia2.repository.AutoRepository;
import com.example.zajecia2.services.AutoService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
//import static org.springframework.test.util.AssertionErrors.assertEquals;

public class TestyOstateczneAutoService {

    @Mock
    private AutoRepository autoRepository;

    @InjectMocks
    private AutoService autoService;

    public TestyOstateczneAutoService() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAll() {
        Auto auto1 = new Auto( "Toyota", 2001);
        Auto auto2 = new Auto( "Honda",2002);

        List<Auto> mockList = Arrays.asList(auto1, auto2);

        when(autoRepository.findAll()).thenReturn(mockList);

        List<Auto> wynik = autoService.getAll();

        assertEquals(2, wynik.size());
        assertEquals("Toyota", wynik.get(0).getModel());
        assertEquals("Honda", wynik.get(1).getModel());
    }

@Test
public void testAddAuto(){
    Auto auto = mock(Auto.class);
    String model = "Toyota";
    int rokProdukcji = 2022;

    when(auto.getModel()).thenReturn(model);
    when(auto.getRokProdukcji()).thenReturn(rokProdukcji);
    autoService.add(auto);
    verify(auto, times(1)).setIdentyfikator();

    // Weryfikacja, czy metoda save została wywołana na repozytorium
    // Z sprawdzaniem, czy przekazany obiekt do save() ma odpowiedni model i rok produkcji
    verify(autoRepository, times(1)).save(argThat(savedAuto ->
            savedAuto.getModel().equals(model) &&
                    savedAuto.getRokProdukcji() == rokProdukcji
    ));
}

    @Test
    public void testUpdate_Sukces(){
        Auto auto = mock(Auto.class);
        Long id = 1L;
        String model = "Toyota";
        int rokProdukcji = 2022;

        when(auto.getId()).thenReturn(id);
        when(auto.getModel()).thenReturn(model);
        when(auto.getRokProdukcji()).thenReturn(rokProdukcji);
        when(autoRepository.findById(id)).thenReturn(Optional.of(auto));

        autoService.update(auto);
        verify(auto, times(1)).getId();
        verify(auto, times(1)).getModel();
        verify(auto, times(1)).getRokProdukcji();
        verify(auto, times(1)).setModel(model);
        verify(auto, times(1)).setRokProdukcji(rokProdukcji);
        verify(auto, times(1)).setIdentyfikator();

        // Weryfikujemy, że metoda save została wywołana na repozytorium
        verify(autoRepository, times(1)).save(auto);
    }

    @Test
    public void testUpdate_Blad(){
        Auto auto = new Auto();
        auto.setId(1L);

        when(autoRepository.findById(1L)).thenReturn(Optional.empty());

        // Wywołanie metody update i sprawdzenie, czy wyjątek jest rzucany
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            autoService.update(auto);
        });

        assertEquals("Nie mogę zaaktualizować takiego auta, ponieważ ono nie istnieje!", exception.getMessage());
    }






@Test
public void testDeleteAutoById_Sukces() {
    Auto auto = mock(Auto.class);
    Long id = 1L;
    when(autoRepository.findById(id)).thenReturn(Optional.of(auto));
    autoService.deleteAutoById(id);
    verify(autoRepository, times(1)).findById(id); //sprawdzenie czy zostala wywolana na repozytorium
    verify(autoRepository, times(1)).delete(auto);
}

    @Test
    public void testDeleteAutoById_Blad() {
        Long id = 1L;
        when(autoRepository.findById(id)).thenReturn(Optional.empty()); //mockujemy ze nie ma takiego id w repo

        // Sprawdzamy, czy wyjątek został rzucony
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> autoService.deleteAutoById(id)
        );

        assertEquals("Nie mogę usunąć takiego auta, ponieważ ono nie istnieje!", exception.getMessage());

        // Weryfikujemy, że metoda delete nie została wywołana na repozytorium
        verify(autoRepository, times(0)).delete(any(Auto.class));

    }

    @Test
    public void testExistsByIdTrue(){
        when(autoRepository.existsById(1L)).thenReturn(true);
        assertTrue(autoService.existsById(1L));
        verify(autoRepository, times(1)).existsById(1L);
    }

    @Test
    public void testExistsByIdFalse(){
        when(autoRepository.existsById(1L)).thenReturn(false);
        assertFalse(autoService.existsById(1L));
        verify(autoRepository, times(1)).existsById(1L);
    }

}





