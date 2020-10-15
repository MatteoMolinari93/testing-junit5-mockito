package guru.springframework.sfgpetclinic.services.springdatajpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.argThat;

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
	
	@Test
    void testSaveLambda() {
		final String MATCH_ME = "MATCH_ME";
		Speciality speciality = new Speciality();
		speciality.setDescription(MATCH_ME);
		
		Speciality savedSpeciality = new Speciality();
		savedSpeciality.setId(1l);
		
		when(specialityRepository.save(argThat(argument -> argument.getDescription().equals(MATCH_ME)))).thenReturn(savedSpeciality);
		
		Speciality returnedSpeciality = service.save(speciality);
		assertEquals(1l, returnedSpeciality.getId());
    }

}
