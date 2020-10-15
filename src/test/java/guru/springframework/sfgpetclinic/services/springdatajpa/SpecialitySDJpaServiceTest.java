package guru.springframework.sfgpetclinic.services.springdatajpa;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doThrow;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.repositories.SpecialtyRepository;

@ExtendWith(MockitoExtension.class)
public class SpecialitySDJpaServiceTest {
	
	@Mock
    SpecialtyRepository specialityRepository;

	@InjectMocks
	SpecialitySDJpaService service;
	
	@Test
	void testDeleteByObject() {
		Speciality speciality = new Speciality();
		
		service.delete(speciality);
		
		verify(specialityRepository).delete(any(Speciality.class));
	}
	
	@Test
	void findByIdTest() {
		Speciality speciality = new Speciality();
		
		when(specialityRepository.findById(1l)).thenReturn(Optional.of(speciality));
		
		Speciality foundSpeciality = service.findById(1l);
		
		assertNotNull(foundSpeciality);
		
		verify(specialityRepository).findById(anyLong());
	}
	
	@Test
	void deleteById() {
		service.deleteById(1l);
		
		verify(specialityRepository).deleteById(1l);
	}
	
	@Test
	void deleteByIdAtLeastOnce() {
		service.deleteById(1l);
		service.deleteById(1l);
		
		verify(specialityRepository, atLeastOnce()).deleteById(1l);
	}
	
	@Test
	void delete() {
		service.delete(new Speciality());
	}
	
	@Test 
	void testDoThrow() {
		doThrow(new RuntimeException("boom")).when(specialityRepository).delete(any());
		
		assertThrows(RuntimeException.class, () -> specialityRepository.delete(new Speciality()));
		
		verify(specialityRepository).delete(any());
	}

}
