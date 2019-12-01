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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Driver extends User{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	
	
	@JsonIgnore
	@OneToMany(mappedBy= "driver", fetch = FetchType.LAZY)
	Set<Ticket> ticket;

	
	@OneToMany(mappedBy= "driver", fetch = FetchType.LAZY)
	Set<FoodOrder> foodOrders;
	
	public long getId() {
		return id;
	}

	
	public void setId(long id) {
		this.id = id;
	}
	
	
	
	

	public Set<FoodOrder> getFoodOrders() {
		return foodOrders;
	}


	public void setFoodOrders(Set<FoodOrder> foodOrders) {
		this.foodOrders = foodOrders;
	}
	
	public void addFoodOrder(FoodOrder foodOrder) {
		this.foodOrders.add(foodOrder);
	}


	public Set<Ticket> getTicket() {
		return ticket;
	}


	public void setTicket(Set<Ticket> ticket) {
		this.ticket = ticket;
	}


	public Driver() {
		super();
	}


	public Driver(Long id, @NotEmpty(message = "*Please provide First Name") String fname, String lname,
			String email,
			@Pattern(regexp = "^([1-9])\\d{9}", message = "*Please provide a valid contact number") String contact,
			String password, String line1, String line2, String city, String state, String country, String pincode,
			int active, int otp, boolean verified, Set<Role> roles) {
		super(id, fname, lname, email, contact, password, line1, line2, city, state, country, pincode, active, otp, verified,
				roles);
		// TODO Auto-generated constructor stub
	}


	public Driver(Long id, @NotEmpty(message = "*Please provide First Name") String fname, String lname,
			String email,
			@Pattern(regexp = "^([1-9])\\d{9}", message = "*Please provide a valid contact number") String contact,
			String password, String line1, String line2, String city, String state, String country, String pincode,
			int active, int otp, boolean verified) {
		super(id, fname, lname, email, contact, password, line1, line2, city, state, country, pincode, active, otp, verified);
		// TODO Auto-generated constructor stub
	}


	public Driver(@NotEmpty(message = "*Please provide First Name") String fname, String lname, String email,
			@Pattern(regexp = "^([1-9])\\d{9}", message = "*Please provide a valid contact number") String contact,
			String password, String line1, String line2, String city, String state, String country, String pincode,
			int active, int otp, boolean verified, Set<Role> roles) {
		super(fname, lname, email, contact, password, line1, line2, city, state, country, pincode, active, otp, verified,
				roles);
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	
}
