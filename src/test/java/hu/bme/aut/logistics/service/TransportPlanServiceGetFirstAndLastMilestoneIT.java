package hu.bme.aut.logistics.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import hu.bme.aut.logistics.model.Section;

@SpringBootTest
@AutoConfigureTestDatabase
public class TransportPlanServiceGetFirstAndLastMilestoneIT extends TransportPlanServiceIT {

    @BeforeEach
    void clearDB() {
    	sectionRepository.deleteAllInBatch();
    	milestoneRepository.deleteAllInBatch();
    	transportPlanRepository.deleteAllInBatch();
    }
    
    @Test
    void testThatNonExistingPlanThrows() throws Exception {
        assertThrows(IllegalArgumentException.class, () -> {
            transportPlanService.getFirstAndLastMilestone(1L);
        });
    }

    @Test
    void testThatCallingForPlanWithNoSectionsReturnsEmptyList() throws Exception {
        givenEmptyPlan();
        assertThat(transportPlanService.getFirstAndLastMilestone(plan.getId())).isEmpty();
    }
    
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    void testThatCallingForPlanWithNSectionsReturnsFirstAndLastMilestone(int n) throws Exception {
        givenPlanWithSections(n);
        Section firstSection = sections.get(0);
        Section lastSection = sections.get(n-1);
		assertThat(transportPlanService.getFirstAndLastMilestone(plan.getId()))
        	.containsExactly(firstSection.getFromMilestone(), lastSection.getToMilestone());
    }
}
