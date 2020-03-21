package hu.bme.aut.logistics.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import hu.bme.aut.logistics.model.Milestone;
import hu.bme.aut.logistics.model.Section;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class TransportPlanServiceRegisterDelayIT extends TransportPlanServiceIT {

	@Test
	void testThatNonExistingPlanThrows() throws Exception {
		assertThrows(IllegalArgumentException.class, () -> {
			transportPlanService.registerDelay(1L, 1L, 20);
		});
	}

	@Test
	void testThatNonExistingMilestoneThrows() throws Exception {
		givenEmptyPlan();
		assertThrows(IllegalArgumentException.class, () -> {
			transportPlanService.registerDelay(plan.getId(), 1L, 20);
		});
	}
	
	@Test
	void testThatMilestoneNotAssignedToPlanThrows() throws Exception {
		givenEmptyPlan();
		givenFromMilestone();
		assertThrows(IllegalArgumentException.class, () -> {
			transportPlanService.registerDelay(plan.getId(), fromMilestone.getId(), 20);
		});
	}
	
	@Test
	void testThatPlannedTimeOfMilestoneIsIncreased() throws Exception {

		givenPlanWithSections(1);
		Milestone originalMilestone = sections.get(0).getFromMilestone();
		LocalDateTime originalPlannedTime = originalMilestone.getPlannedTime();
		transportPlanService.registerDelay(plan.getId(), originalMilestone.getId(), 20);
		Milestone modifiedMilestone = fetchPlanFromDb().get().getSections().get(0).getFromMilestone();
		
		assertThat(modifiedMilestone.getPlannedTime()).isEqualTo(originalPlannedTime.plusMinutes(20));
	}
	
	@Test
	void testThatPlannedTimeOfNextToMilestoneIsIncreasedInCaseOfFromMilestone() throws Exception {

		givenPlanWithSections(1);
		Section section = sections.get(0);
		Milestone originalMilestone = section.getFromMilestone();
		LocalDateTime originalPlannedTimeOfNextMilestone = section.getToMilestone().getPlannedTime();
		transportPlanService.registerDelay(plan.getId(), originalMilestone.getId(), 20);
		Milestone modifiedNextMilestone = fetchPlanFromDb().get().getSections().get(0).getToMilestone();
		
		assertThat(modifiedNextMilestone.getPlannedTime()).isEqualTo(originalPlannedTimeOfNextMilestone.plusMinutes(20));
	}

	@Test
	void testThatPlannedTimeOfNextFromMilestoneIsIncreasedInCaseOfToMilestone() throws Exception {

		givenPlanWithSections(2);
		Milestone originalMilestone = sections.get(0).getToMilestone();
		LocalDateTime originalPlannedTimeOfNextMilestone = sections.get(1).getFromMilestone().getPlannedTime();
		transportPlanService.registerDelay(plan.getId(), originalMilestone.getId(), 20);
		Milestone modifiedNextMilestone = fetchPlanFromDb().get().getSections().get(1).getFromMilestone();
		
		assertThat(modifiedNextMilestone.getPlannedTime()).isEqualTo(originalPlannedTimeOfNextMilestone.plusMinutes(20));
	}
	
}
