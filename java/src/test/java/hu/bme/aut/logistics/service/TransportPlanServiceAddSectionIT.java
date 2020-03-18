package hu.bme.aut.logistics.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import hu.bme.aut.logistics.model.Milestone;
import hu.bme.aut.logistics.model.Section;
import hu.bme.aut.logistics.model.TransportPlan;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class TransportPlanServiceAddSectionIT extends TransportPlanServiceIT {
	
    @Test
    void testThatNonExistingPlanThrows() throws Exception {
        assertThrows(IllegalArgumentException.class, () -> {
            transportPlanService.addSection(1L, 1, 1, 0);
        });
    }

    @Test
    void testThatNonExistingFromMilestoneIdThrows() throws Exception {

        givenEmptyPlan();
        givenToMilestone();
        assertThrows(IllegalArgumentException.class, () -> {
            transportPlanService.addSection(plan.getId(), -1, toMilestone.getId(), 0);
        });
    }

    @Test
    void testThatNonExistingToMilestoneIdThrows() throws Exception {
        givenEmptyPlan();
        givenFromMilestone();
        assertThrows(IllegalArgumentException.class, () -> {
            transportPlanService.addSection(plan.getId(), fromMilestone.getId(), -1, 0);
        });
    }
    
    @Test
    void testThatTooSmallNumberThrows() throws Exception {
        givenEmptyPlan();
        givenFromMilestone();
        givenToMilestone();
        assertThrows(IllegalArgumentException.class, () -> {
            transportPlanService.addSection(plan.getId(), fromMilestone.getId(), toMilestone.getId(), -11);
        });
    }
    
    
    @Test
    void testThatTooLargeNumberThrows() throws Exception {
        givenEmptyPlan();
        givenFromMilestone();
        givenToMilestone();
        assertThrows(IllegalArgumentException.class, () -> {
            transportPlanService.addSection(plan.getId(), fromMilestone.getId(), toMilestone.getId(), 1);
        });
    }
    
    @Test
    void testAddSectionToEmptyPlan() throws Exception {
        givenEmptyPlan();
        givenFromMilestone();
        givenToMilestone();
        
        transportPlanService.addSection(plan.getId(), fromMilestone.getId(), toMilestone.getId(), 0);
        
        Optional<TransportPlan> optionalPlan = fetchPlanFromDb();
        assertThat(optionalPlan).isNotEmpty();
        TransportPlan planAfterAdd = optionalPlan.get();
        
        assertThat(planAfterAdd.getSections()).hasSize(1);
        Section savedSection = planAfterAdd.getSections().get(0);
        assertSection(savedSection, 0, fromMilestone, toMilestone);
    }

    Optional<TransportPlan> fetchPlanFromDb() {
        em.flush();
        em.clear();
        return transportPlanRepository.findById(plan.getId());
    }

    @Test
    void testAddSectionToEndOfNonEmptyPlan() throws Exception {
        givenPlanWithSections(1);
        givenFromMilestone();
        givenToMilestone();
        
        transportPlanService.addSection(plan.getId(), fromMilestone.getId(), toMilestone.getId(), 1);
        
        Optional<TransportPlan> optionalPlan = fetchPlanFromDb();
        assertThat(optionalPlan).isNotEmpty();
        TransportPlan planAfterAdd = optionalPlan.get();
        assertThat(planAfterAdd.getSections()).hasSize(2);
        
        Section oldSection = planAfterAdd.getSections().get(0);
        assertSection(oldSection, 0, sections.get(0).getFromMilestone(), fromMilestone);
        Section newSection = planAfterAdd.getSections().get(1);
        assertSection(newSection, 1, fromMilestone, toMilestone);
    }
    
    @Test
    void testAddSectionToMiddleOfPlan() throws Exception {
        givenPlanWithSections(2);
        givenFromMilestone();
        givenToMilestone();
        
        transportPlanService.addSection(plan.getId(), fromMilestone.getId(), toMilestone.getId(), 1);
        
        Optional<TransportPlan> optionalPlan = fetchPlanFromDb();
        assertThat(optionalPlan).isNotEmpty();
        TransportPlan planAfterAdd = optionalPlan.get();
        assertThat(planAfterAdd.getSections()).hasSize(3);
        
        Section oldSection1 = planAfterAdd.getSections().get(0);
        assertSection(oldSection1, 0, sections.get(0).getFromMilestone(), fromMilestone);
        Section newSection = planAfterAdd.getSections().get(1);
        assertSection(newSection, 1, fromMilestone, toMilestone);
        
        Section oldSection2 = planAfterAdd.getSections().get(2);
        assertSection(oldSection2, 2, toMilestone, sections.get(1).getToMilestone());
    }

    void assertSection(Section section, int expectedNumber, Milestone expectedFromMilestone, Milestone expectedToMilestone) {
        assertThat(section.getNumber()).isEqualTo(expectedNumber);
        assertThat(section.getFromMilestone()).isEqualTo(expectedFromMilestone);
        assertThat(section.getToMilestone()).isEqualTo(expectedToMilestone);
    }
}
