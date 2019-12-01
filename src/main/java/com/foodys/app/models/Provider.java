package com.foodys.app.models;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Entity
public class Provider extends User{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	
	
	
	@OneToMany(mappedBy= "provider", fetch = FetchType.LAZY)
	Set<FoodOrder> menuItems;

	
	public long getId() {
		return id;
	}

	
	public void setId(long id) {
		this.id = id;
	}
	
	
	
	

	public Set<FoodOrder> getItem() {
		return menuItems;
	}


	public void setItem(Set<FoodOrder> items) {
		this.menuItems = items;
	}


	public Provider() {
		super();
	}


	public Provider(long id, Set<FoodOrder> items) {
		super();
		this.id = id;
		this.menuItems = items;
	}


	public Provider(Long id, @NotEmpty(message = "*Please provide First Name") String fname, String lname, String email,
			@Pattern(regexp = "^([1-9])\\d{9}", message = "*Please provide a valid contact number") String contact,
			String password, String line1, String line2, String city, String state, String country, String pincode,
			int active, int otp, boolean verified, Set<Role> roles) {
		super(id, fname, lname, email, contact, password, line1, line2, city, state, country, pincode, active, otp, verified,
				roles);
		// TODO Auto-generated constructor stub
	}


	public Provider(Long id, @NotEmpty(message = "*Please provide First Name") String fname, String lname, String email,
			@Pattern(regexp = "^([1-9])\\d{9}", message = "*Please provide a valid contact number") String contact,
			String password, String line1, String line2, String city, String state, String country, String pincode,
			int active, int otp, boolean verified) {
		super(id, fname, lname, email, contact, password, line1, line2, city, state, country, pincode, active, otp, verified);
		// TODO Auto-generated constructor stub
	}


	public Provider(@NotEmpty(message = "*Please provide First Name") String fname, String lname, String email,
			@Pattern(regexp = "^([1-9])\\d{9}", message = "*Please provide a valid contact number") String contact,
			String password, String line1, String line2, String city, String state, String country, String pincode,
			int active, int otp, boolean verified, Set<Role> roles) {
		super(fname, lname, email, contact, password, line1, line2, city, state, country, pincode, active, otp, verified,
				roles);
		// TODO Auto-generated constructor stub
	}


	
	
	
	
	
	
}
