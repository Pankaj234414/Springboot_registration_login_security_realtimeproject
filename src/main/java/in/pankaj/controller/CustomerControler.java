package in.pankaj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.pankaj.entity.Customer;
import in.pankaj.repository.CustomerRepository;

@RestController
public class CustomerControler {

	@Autowired
	private CustomerRepository customerRepo;
	
	@Autowired
	private PasswordEncoder pwdEncoder;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@PostMapping("/login")
	public ResponseEntity<String> loginCheck(@RequestBody Customer customer){
		
		UsernamePasswordAuthenticationToken token =
			new UsernamePasswordAuthenticationToken(customer.getEmail(),customer.getPwd());
		
		try {
			
			Authentication authenticate = authManager.authenticate(token);	
			if(authenticate.isAuthenticated()) {
				return new ResponseEntity<>("Welcome you are successfully login",HttpStatus.OK);	
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}						
		return new ResponseEntity<>("Invalid Credentials",HttpStatus.BAD_REQUEST);	
		
	}
	
	
	@PostMapping("/registration")
	public ResponseEntity<String> saveCustomer(@RequestBody Customer customer){
		String encodePwd = pwdEncoder.encode(customer.getPwd());
		customer.setPwd(encodePwd);		
		customerRepo.save(customer);	
		return new ResponseEntity<>("Customer Registered",HttpStatus.CREATED);
	}
}
