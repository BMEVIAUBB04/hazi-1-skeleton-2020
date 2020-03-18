package hu.bme.aut.logistics.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

import hu.bme.aut.logistics.model.Milestone;
import hu.bme.aut.logistics.model.Section;
import hu.bme.aut.logistics.model.TransportPlan;
import hu.bme.aut.logistics.repository.MilestoneRepository;
import hu.bme.aut.logistics.repository.SectionRepository;
import hu.bme.aut.logistics.repository.TransportPlanRepository;

public class TransportPlanServiceIT {

	@Autowired
	TransportPlanService transportPlanService;

	@Autowired
	TransportPlanRepository transportPlanRepository;

	@Autowired
	MilestoneRepository milestoneRepository;

	@Autowired
	SectionRepository sectionRepository;

	@Autowired
	EntityManager em;

	TransportPlan plan;
	List<Section> sections;

	Milestone fromMilestone;

	Milestone toMilestone;


	void givenEmptyPlan() {
		plan = transportPlanRepository.save(new TransportPlan());
	}

	void givenPlanWithSections(int count) {
		givenEmptyPlan();
		sections = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			Section section = new Section();
			Milestone milestone1 = saveNewMilestone();
			Milestone milestone2 = saveNewMilestone();
			section.setFromMilestone(milestone1);
			section.setToMilestone(milestone2);
			section.setNumber(i);
			plan.addSection(section);
			sections.add(section);
		}
		
		sectionRepository.saveAll(sections);
		plan = transportPlanRepository.save(plan);
	}

	Milestone saveNewMilestone() {
		Milestone milestone = new Milestone();
		milestone.setPlannedTime(LocalDateTime.of(2020, 5, 12, 13, 0, 0));
		return milestoneRepository.save(milestone);
	}
	
    Optional<TransportPlan> fetchPlanFromDb() {
        em.flush();
        em.clear();
        return transportPlanRepository.findById(plan.getId());
    }
    

    void givenFromMilestone() {
        fromMilestone = saveNewMilestone();
    }
    
    void givenToMilestone() {
        toMilestone = saveNewMilestone();
    }
  
}