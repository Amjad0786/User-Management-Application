package in.amjadIt.lodder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import in.amjadIt.entity.City;
import in.amjadIt.entity.Country;
import in.amjadIt.entity.State;
import in.amjadIt.repo.CityRepo;
import in.amjadIt.repo.CountryRepo;
import in.amjadIt.repo.StateRepo;

@Component
public class DataLoader implements CommandLineRunner{
	
	@Autowired
    private CountryRepo countryRepo;

    @Autowired
    private StateRepo stateRepo;

    @Autowired
    private CityRepo cityRepo;
    
    @Override
    public void run(String... args) throws Exception {
    	// Insert countries
        Country usa = new Country();
        usa.setName("USA");
        countryRepo.save(usa);

        Country india = new Country();
        india.setName("India");
        countryRepo.save(india);

        Country canada = new Country();
        canada.setName("Canada");
        countryRepo.save(canada);

        Country australia = new Country();
        australia.setName("Australia");
        countryRepo.save(australia);

        // Insert states for USA
        State california = new State();
        california.setName("California");
        california.setCountry(usa);
        stateRepo.save(california);

        State texas = new State();
        texas.setName("Texas");
        texas.setCountry(usa);
        stateRepo.save(texas);

        State florida = new State();
        florida.setName("Florida");
        florida.setCountry(usa);
        stateRepo.save(florida);

        // Insert states for India
        State maharashtra = new State();
        maharashtra.setName("Maharashtra");
        maharashtra.setCountry(india);
        stateRepo.save(maharashtra);

        State karnataka = new State();
        karnataka.setName("Karnataka");
        karnataka.setCountry(india);
        stateRepo.save(karnataka);

        State gujarat = new State();
        gujarat.setName("Gujarat");
        gujarat.setCountry(india);
        stateRepo.save(gujarat);

        // Insert states for Canada
        State ontario = new State();
        ontario.setName("Ontario");
        ontario.setCountry(canada);
        stateRepo.save(ontario);

        State quebec = new State();
        quebec.setName("Quebec");
        quebec.setCountry(canada);
        stateRepo.save(quebec);

        // Insert states for Australia
        State newSouthWales = new State();
        newSouthWales.setName("New South Wales");
        newSouthWales.setCountry(australia);
        stateRepo.save(newSouthWales);

        State victoria = new State();
        victoria.setName("Victoria");
        victoria.setCountry(australia);
        stateRepo.save(victoria);

        // Insert cities for USA
        City losAngeles = new City();
        losAngeles.setName("Los Angeles");
        losAngeles.setState(california);
        cityRepo.save(losAngeles);

        City houston = new City();
        houston.setName("Houston");
        houston.setState(texas);
        cityRepo.save(houston);

        City miami = new City();
        miami.setName("Miami");
        miami.setState(florida);
        cityRepo.save(miami);

        // Insert cities for India
        City mumbai = new City();
        mumbai.setName("Mumbai");
        mumbai.setState(maharashtra);
        cityRepo.save(mumbai);

        City bangalore = new City();
        bangalore.setName("Bangalore");
        bangalore.setState(karnataka);
        cityRepo.save(bangalore);

        City ahmedabad = new City();
        ahmedabad.setName("Ahmedabad");
        ahmedabad.setState(gujarat);
        cityRepo.save(ahmedabad);

        // Insert cities for Canada
        City toronto = new City();
        toronto.setName("Toronto");
        toronto.setState(ontario);
        cityRepo.save(toronto);

        City montreal = new City();
        montreal.setName("Montreal");
        montreal.setState(quebec);
        cityRepo.save(montreal);

        // Insert cities for Australia
        City sydney = new City();
        sydney.setName("Sydney");
        sydney.setState(newSouthWales);
        cityRepo.save(sydney);

        City melbourne = new City();
        melbourne.setName("Melbourne");
        melbourne.setState(victoria);
        cityRepo.save(melbourne);

        System.out.println("Data successfully loaded into the database!");
    }

}
