package hu.bme.aut.logistics.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hu.bme.aut.logistics.model.TransportPlan;

public interface TransportPlanRepository extends JpaRepository<TransportPlan, Long> {

	@Query("SELECT t FROM TransportPlan t WHERE t.id=:id")
	@EntityGraph(attributePaths = {"sections"})
	Optional<TransportPlan> findByIdWithSections(Long id);
}
