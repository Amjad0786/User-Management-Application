package in.amjadIt.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import in.amjadIt.dto.LoginDTO;
import in.amjadIt.dto.ResetPwd;
import in.amjadIt.dto.UserDTO;
import in.amjadIt.entity.User;

@Service
public interface UserService {
	
	User doRegister(UserDTO dto);
	
	boolean updatePwd(ResetPwd resetPwd);
	
	Optional<User> getUserById(Integer uid);
	
	Optional<User> getUserByEmail(String email);
	
	void deleteUser(User user);
	
	String doLogin(LoginDTO login);
	

}
