package guru.springframework.sfgpetclinic.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import guru.springframework.sfgpetclinic.ControllerTests;
import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.fauxspring.Model;
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
	
	@Captor
	ArgumentCaptor<String> stringArgumentCaptor;
	
	@BeforeEach
	void beforeEach() {
		when(ownerService.findAllByLastNameLike(stringArgumentCaptor.capture()))
			.thenAnswer(invocation -> {
				List<Owner> owners = new ArrayList<>();
				
				String name = invocation.getArgument(0);
				if(name.equals("%Molinari%")) {
					owners.add(new Owner(1l, "Matteo", "Molinari"));
					return owners;
				} else if(name.equals("%MolinariDoNotFind%")) {
					return owners;
				} else if(name.equals("%MolinariFindMe%")) {
					owners.add(new Owner(1l, "Matteo", "Molinari1"));
					owners.add(new Owner(2l, "Matteo", "Molinari2"));
					return owners;
				}
				
				throw new RuntimeException("Invalid argument");
			});
	}

	@Test
	void processFindFormWildcardString() {
		Owner owner = new Owner(2l, "Matteo", "Molinari");
		List<Owner> owners = new ArrayList<>();
		
		String viewName = ownerController.processFindForm(owner, result, null);
		assertTrue(stringArgumentCaptor.getValue().equalsIgnoreCase("%molinari%"));
	}
	
	@Test
	void processFindFormWildcardStringAnnotation() {
		Owner owner = new Owner(2l, "Matteo", "Molinari");
		
		String viewName = ownerController.processFindForm(owner, result, null);
		assertTrue(stringArgumentCaptor.getValue().equalsIgnoreCase("%molinari%"));
	}
	
	@Test
	void processFindFormWildcardStringAnnotationNotFound() {
		Owner owner = new Owner(2l, "Matteo", "MolinariDoNotFind");
		
		String viewName = ownerController.processFindForm(owner, result, null);
		assertTrue(viewName.equalsIgnoreCase("owners/findOwners"));
	}
	
	@Test
	void processFindFormWildcardStringAnnotationManyFound() {
		Owner owner = new Owner(2l, "Matteo", "MolinariFindMe");
		
		String viewName = ownerController.processFindForm(owner, result, Mockito.mock(Model.class));
		assertTrue(viewName.equalsIgnoreCase("owners/ownersList"));
	}
	
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