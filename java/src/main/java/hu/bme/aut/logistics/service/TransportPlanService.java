package hu.bme.aut.logistics.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.bme.aut.logistics.model.Milestone;
import hu.bme.aut.logistics.repository.MilestoneRepository;
import hu.bme.aut.logistics.repository.SectionRepository;
import hu.bme.aut.logistics.repository.TransportPlanRepository;

@Service
public class TransportPlanService {

    @Autowired
    TransportPlanRepository transportPlanRepository;
    
    @Autowired
    MilestoneRepository milestoneRepository;
    
    @Autowired
    SectionRepository sectionRepository;
    
    //TODO: Megvalósítani az 5. a. feladat szerint
    public List<Milestone> getFirstAndLastMilestone(Long transportPlanId){
    	return null;
    }
    
    //TODO: Megvalósítani az 5. b. feladat szerint
    public void registerDelay(long planId, long milestoneId, int delayInMinutes) {
    }

    //TODO: Megvalósítani az 5. c. feladat szerint
    public void addSection(long planId, long fromMilestoneId, long toMilestoneId, int number) {
    }
}
