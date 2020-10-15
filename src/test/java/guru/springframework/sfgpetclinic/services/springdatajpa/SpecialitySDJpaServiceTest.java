package guru.springframework.sfgpetclinic.services.springdatajpa;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;

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
    SpecialtyRepository specialtyRepository;

	@InjectMocks
	SpecialitySDJpaService service;
	
	@Test
	void testDeleteByObject() {
		Speciality speciality = new Speciality();
		
		service.delete(speciality);
		
		verify(specialtyRepository).delete(any(Speciality.class));
	}
	
	@Test
	void findByIdTest() {
		Speciality speciality = new Speciality();
		
		when(specialtyRepository.findById(1l)).thenReturn(Optional.of(speciality));
		
		Speciality foundSpeciality = service.findById(1l);
		
		assertNotNull(foundSpeciality);
		
		verify(specialtyRepository).findById(anyLong());
	}
	
	@Test
	void deleteById() {
		service.deleteById(1l);
		
		verify(specialtyRepository).deleteById(1l);
	}
	
	@Test
	void deleteByIdAtLeastOnce() {
		service.deleteById(1l);
		service.deleteById(1l);
		
		verify(specialtyRepository, atLeastOnce()).deleteById(1l);
	}
	
	@Test
	void delete() {
		service.delete(new Speciality());
	}

}
