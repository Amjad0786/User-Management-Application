package in.amjadIt.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.amjadIt.Generated.PasswordGenerator;
import in.amjadIt.dto.LoginDTO;
import in.amjadIt.dto.ResetPwd;
import in.amjadIt.dto.UserDTO;
import in.amjadIt.entity.User;
import in.amjadIt.repo.CityRepo;
import in.amjadIt.repo.CountryRepo;
import in.amjadIt.repo.StateRepo;
import in.amjadIt.repo.UserRepo;

@Service
public class UserServiceImpl implements UserService {
	

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CountryRepo countryRepo;
	
	@Autowired
	private StateRepo stateRepo;
	
	@Autowired
	private CityRepo cityRepo;
	

	@Override
	public User doRegister(UserDTO dto) {

		String password = PasswordGenerator.generatePassword(8);
		
		User user = User.builder()
						.email(dto.getEmail())
						.phno(dto.getPhno())
						.name(dto.getName())
						.country(countryRepo.findById(dto.getCountryId()).get())
						.state(stateRepo.findById(dto.getStateId()).get())
						.city(cityRepo.findById(dto.getCityId()).get())
						.pwd(password)
						.isPwdUpdate(false)
						.build();
		User save = userRepo.save(user);
		return save;
	}

	@Override
	public boolean updatePwd(ResetPwd resetPwd) {
		Optional<User> byEmail = getUserByEmail(resetPwd.getEmail());
		if (byEmail.isPresent()) {
			User user = byEmail.get();
			if (resetPwd.getCnfPwd().equals(resetPwd.getNewPwd()) 
					&& user.getPwd().equals(resetPwd.getOldPwd())) 
			{
				user.setPwdUpdate(true);
				user.setPwd(resetPwd.getNewPwd());
				userRepo.save(user);
				return true;
			}
		}
		return false;
	}

	@Override
	public Optional<User> getUserById(Integer uid) {
		return userRepo.findById(uid);
	}

	@Override
	public void deleteUser(User user) {
		userRepo.delete(user);
	}

	@Override
	public Optional<User> getUserByEmail(String email) {
		return userRepo.findByEmail(email);
		
	}

	@Override
	public String doLogin(LoginDTO login) {
		Optional<User> byEmailAndPwd = userRepo.findByEmailAndPwd(login.getEmail(), login.getPwd());
		if(byEmailAndPwd.isPresent())
		{
			User user = byEmailAndPwd.get();
			if(!user.isPwdUpdate())
			{
				return "Please update your password!";
			}
			else {
				return "Success";
			}
		}
		return "Check Your Email Or Password!";
	}

	
}