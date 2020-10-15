package guru.springframework.sfgpetclinic.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import guru.springframework.sfgpetclinic.ControllerTests;
import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest implements ControllerTests {

	@Mock
    OwnerService ownerService;
	
	@Mock
	BindingResult result;

	@InjectMocks
    OwnerController ownerController;

    @Test
    void testProcessCreationFormReturnErrorView() {
    	when(result.hasErrors()).thenReturn(true);
    	String returnedView = ownerController.processCreationForm(new Owner(1l, "Matteo", "Molinari"), result);
    	
    	assertEquals(OwnerController.VIEWS_OWNER_CREATE_OR_UPDATE_FORM, returnedView);
    	verify(result).hasErrors();
    }
    
    @Test
    void testProcessCreationFormReturnOwnerView() {
    	Owner savedOwner = new Owner(2l, "Matteo", "Molinari");
    	
    	when(result.hasErrors()).thenReturn(false);
    	when(ownerService.save(any(Owner.class))).thenReturn(savedOwner);

    	String returnedView = ownerController.processCreationForm(new Owner(1l, "Matteo", "Molinari"), result);
    	
    	assertEquals("redirect:/owners/" + savedOwner.getId(), returnedView);
    	verify(ownerService).save(any(Owner.class));
    }


}