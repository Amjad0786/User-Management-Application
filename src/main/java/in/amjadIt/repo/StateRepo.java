package in.amjadIt.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.amjadIt.entity.Country;
import in.amjadIt.entity.State;

public interface StateRepo extends JpaRepository<State, Integer>{
	
	 List<State> findByCountryId(Integer cid);

}
