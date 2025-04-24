package in.amjadIt.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.amjadIt.entity.City;

public interface CityRepo extends JpaRepository<City, Integer>{

	public List<City> findByStateId(Integer stateId);
	
}
