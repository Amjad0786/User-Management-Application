package in.amjadIt.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.amjadIt.entity.Country;

public interface CountryRepo extends JpaRepository<Country, Integer>{
    
}

