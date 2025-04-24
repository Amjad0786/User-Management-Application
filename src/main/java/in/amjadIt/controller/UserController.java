package in.amjadIt.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import in.amjadIt.dto.EmailRequest;
import in.amjadIt.dto.LoginDTO;
import in.amjadIt.dto.ResetPwd;
import in.amjadIt.dto.UserDTO;
import in.amjadIt.entity.City;
import in.amjadIt.entity.State;
import in.amjadIt.entity.User;
import in.amjadIt.repo.CityRepo;
import in.amjadIt.repo.CountryRepo;
import in.amjadIt.repo.StateRepo;
import in.amjadIt.service.MailService;
import in.amjadIt.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private CountryRepo countryRepo;
	
	@Autowired
	private StateRepo stateRepo;
	
	@Autowired
	private CityRepo cityRepo;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MailService mailService;
	
	
	@GetMapping({ "/register" })
	public String getRegistrationForm(Model model) {
		model.addAttribute("user", new UserDTO());
		model.addAttribute("countries", countryRepo.findAll());
		return "Registration";
	}

	@PostMapping("/insert")
	public String doRegistration(UserDTO user, Model model) {
		
		if(userService.getUserByEmail(user.getEmail()).isPresent())
		{
			model.addAttribute("emailMsg", user.getEmail()+" email already registered!");
			model.addAttribute("user", new UserDTO());
			model.addAttribute("countries", countryRepo.findAll());
			return "Registration";
		}
	    User savedUser = userService.doRegister(user);
	    String subject = "Registration";
	    String resetLink = "http://localhost:8081/reset/" + savedUser.getId();
	    String message = getMailBody(savedUser.getName(),resetLink,savedUser.getPwd());

	    EmailRequest emailRequest = EmailRequest.builder()
	            .to(user.getEmail().trim())
	            .subject(subject)
	            .message(message)
	            .build();

	    boolean status = mailService.sendHtmlEmail(emailRequest);
	    if (!status) {
	        model.addAttribute("err", "Your email is not correct");
	        userService.deleteUser(savedUser);
	        return "Registration";
	    } else {
	    	model.addAttribute("sucMsg", "Check Your Mail! "+user.getEmail());
			model.addAttribute("user", new UserDTO());
			model.addAttribute("countries", countryRepo.findAll());
			return "Registration";
	    }
	}

	@ResponseBody
	@GetMapping("/states/{countryId}")
	public List<State> getStates(@PathVariable Integer countryId) {
		return stateRepo.findByCountryId(countryId);
	}

	@ResponseBody
	@GetMapping("/cities/{stateId}")
	public List<City> getCities(@PathVariable Integer stateId) {
		return cityRepo.findByStateId(stateId);
	}

	@GetMapping("/reset/{id}")
	public String getResetForm(@PathVariable("id") Integer id, Model model) {
		Optional<User> userById = userService.getUserById(id);
		if (userById.isPresent()) {
			User user = userById.get();
			ResetPwd resetPwd = new ResetPwd();
			resetPwd.setEmail(user.getEmail());
			model.addAttribute("resetPwd", resetPwd);
		} else {
			model.addAttribute("msg", "Email Not Found");
		}
		return "ResetForm";
	}

	@PostMapping("/resetPwd")
	public String resetPassword(@ModelAttribute ResetPwd resetPwd, Model model) {

		boolean updatePwd = userService.updatePwd(resetPwd);
		if (updatePwd) {
			model.addAttribute("msg", "Password reset successfully!");
		} else {
			model.addAttribute("errmsg", "Failed To Reset Password!");
		}
		return "ResetForm";
	}
	
	
	@GetMapping("/login")
	public String getLoginForm(Model model)
	{
		model.addAttribute("loginDTO", new LoginDTO());
		return "Login";
	}
	
	@PostMapping("/login")
	public String doLogin(@ModelAttribute LoginDTO loginDto,Model model)
	{
		String doLogin = userService.doLogin(loginDto);
		if(!doLogin.equals("Success"))
		{
			model.addAttribute("errMsg", doLogin);
			model.addAttribute("loginDTO", new LoginDTO());
			return "Login";
		}
		return "redirect:/quote";
	}
	
	private String getMailBody(String username,String resetLink,String pwd)
	{
		return  "<div style=\"font-family: Arial, sans-serif; color: #333; line-height: 1.6; background-color: #f4f4f9; padding: 20px; border-radius: 10px; border: 1px solid #ddd;\">"
	    	    + "<h1 style=\"color: #4CAF50; text-align: center;\">Welcome, " + username + "!</h1>"
	    	    + "<p style=\"text-align: center; font-size: 18px; color: #555;\">We're thrilled to have you on board. Below are your registration details:</p>"
	    	    + "<div style=\"background-color: #ffffff; padding: 15px;text-align: center; border-radius: 8px; border: 1px solid #ddd; margin-top: 20px;\">"
	    	    + "<p style=\"font-size: 16px; color: #333;\"><strong>Password:</strong> <span style=\"font-size: 18px; color: #000; font-weight: bold;\">" + pwd + "</span></p>"
	    	    + "</div>"
	    	    + "<p style=\"font-size: 16px; color: #333; margin-top: 20px; text-align: center;\">For your security, we recommend that you reset your password by clicking the button below:</p>"
	    	    + "<div style=\"text-align: center; margin-top: 20px;\">"
	    	    + "<a href=\"" + resetLink + "\" style=\"display: inline-block; padding: 15px 30px; font-size: 18px; color: white; background-color: #4CAF50; text-decoration: none; border-radius: 50px; font-weight: bold; transition: background-color 0.3s;\">"
	    	    + "Reset Password"
	    	    + "</a>"
	    	    + "</div>"
	    	    + "<p style=\"font-size: 14px; color: #777; margin-top: 20px; text-align: center;\">If you did not register or have any questions, feel free to <a href=\"softsam2004@gmail.com\" style=\"color: #4CAF50; text-decoration: none; font-weight: bold;\">contact us</a>.</p>"
	    	    + "<div style=\"text-align: center; font-size: 14px; color: #999; margin-top: 40px;\">"
	    	    + "<p>&copy; 2025 Khan Company . All rights reserved.</p>"
	    	    + "<p>Follow us on <a href=\"https://twitter.com\" style=\"color: #1da1f2;\">Twitter</a> and <a href=\"https://facebook.com\" style=\"color: #1877f2;\">Facebook</a>.</p>"
	    	    + "</div>"
	    	    + "</div>";
	}
	
	

}
