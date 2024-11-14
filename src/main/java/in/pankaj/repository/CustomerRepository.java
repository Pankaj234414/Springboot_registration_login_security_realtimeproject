package in.pankaj.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.pankaj.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer,Integer>{
	
	public Customer findByEmail(String email);

}
