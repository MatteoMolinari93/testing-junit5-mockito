package guru.springframework.sfgpetclinic.services.springdatajpa;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import guru.springframework.sfgpetclinic.repositories.SpecialtyRepository;

@ExtendWith(MockitoExtension.class)
public class SpecialitySDJpaServiceTest {
	
	@Mock
    SpecialtyRepository specialtyRepository;

	@InjectMocks
	SpecialitySDJpaService service;
	
	@Test
	void delete() {
		service.deleteById(1l);
	}

}
