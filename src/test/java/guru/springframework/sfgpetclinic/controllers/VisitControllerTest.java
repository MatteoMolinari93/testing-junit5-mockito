package guru.springframework.sfgpetclinic.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import guru.springframework.sfgpetclinic.ControllerTests;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.services.VisitService;
import guru.springframework.sfgpetclinic.services.map.PetMapService;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest implements ControllerTests {

    @Mock
    VisitService visitService;
    
    @Spy
    PetMapService petService;
    
    @InjectMocks
    VisitController controller;
    
    @Test
    void loadPetWithVisit() {
    	Map<String, Object> model = new HashMap<>();
    	Pet pet = new Pet(12l);
    	petService.save(pet);
    	
    	when(petService.findById(anyLong())).thenCallRealMethod();
    	
    	Visit visit = controller.loadPetWithVisit(12l, model);
    	
    	assertNotNull(visit);
    	assertNotNull(visit.getPet());
    	assertEquals(12l, visit.getPet().getId());
    }
}