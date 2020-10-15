package guru.springframework.sfgpetclinic.services.springdatajpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.repositories.VisitRepository;

@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceTest {

	@Mock
	VisitRepository visitRepository;
	
	@InjectMocks
	VisitSDJpaService service;

	@Test
    void testFindAll() {
		Visit visit = new Visit();
		
		Set<Visit> visits = new HashSet<>();
		visits.add(visit);
		
		when(visitRepository.findAll()).thenReturn(visits);
		
		Set<Visit> foundVisits = service.findAll();
		
		verify(visitRepository).findAll();
		assertEquals(1, foundVisits.size());
    }

	@Test
    void testFindById() {
		Visit visit = new Visit();
		
		when(visitRepository.findById(any())).thenReturn(Optional.of(visit));
		
		Visit foundVisit = service.findById(1l);
				
		verify(visitRepository).findById(1l);
		assertNotNull(foundVisit);
    }

	@Test
    void testSave() {
		when(visitRepository.save(any(Visit.class))).thenReturn(new Visit(1l));
		
		Visit newVisit = service.save(new Visit());
		
		assertNotNull(newVisit);
		verify(visitRepository).save(any(Visit.class));
    }

	@Test
    void testDelete() {
		service.delete(new Visit());
		
		verify(visitRepository).delete(any(Visit.class));
    }

	@Test
    void testDeleteById() {
		service.deleteById(1l);
		
		verify(visitRepository).deleteById(1l);
    }
}