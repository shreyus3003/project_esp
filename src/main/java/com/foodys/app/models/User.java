package com.foodys.app.models;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@Inheritance(
	    strategy = InheritanceType.JOINED
	)
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	
	
	@CreationTimestamp
	LocalDateTime datetime;
	
	@NotEmpty(message = "*Please provide First Name")
	String fname;
	String lname;
	String email;	
	
	
	@Pattern(regexp="^([1-9])\\d{9}", message = "*Please provide a valid contact number")

	String contact;
	
	@JsonIgnore
	String password;
	
	//address fields
	String line1;
	String line2;
	String city;
	String state;
	String country;
	String pincode;
	int active;
	int otp;
	boolean verified;
	
	
	@UpdateTimestamp
	LocalDateTime udatetime;
	
	
	
	
	
	
	
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;
	
	
	public User() {
			
		}
	
	
	
	public User(@NotEmpty(message = "*Please provide First Name") String fname, String lname, String email,
			@Pattern(regexp = "^([1-9])\\d{9}", message = "*Please provide a valid contact number") String contact,
			String password, String line1, String line2, String city, String state, String country, String pincode, 
			int active, int otp, boolean verified, Set<Role> roles) {
		super();
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.contact = contact;
		this.password = password;
		this.line1 = line1;
		this.line2 = line2;
		this.city = city;
		this.state = state;
		this.country = country;
		this.pincode = pincode;
		
		this.active = active;
		this.otp = otp;
		this.verified = verified;
		this.roles = roles;
		
	}



	@Override
	public String toString() {
		return "User [id=" + id + ", fname=" + fname + ", lname=" + lname + ", email=" + email + ", contact=" + contact + ", line1=" + line1
				+ ", line2=" + line2 + ", city=" + city + ", state=" + state + ", country=" + country + ", pincode="
				+ pincode + "]";
	
	}
	
	
	
	public User(Long id, @NotEmpty(message = "*Please provide First Name") String fname, String lname, String email,
			@Pattern(regexp = "^([1-9])\\d{9}", message = "*Please provide a valid contact number") String contact,
			String password, String line1, String line2, String city, String state, String country, String pincode, 
			int active, int otp, boolean verified) {
		super();
		this.id = id;
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.contact = contact;
		this.password = password;
		this.line1 = line1;
		this.line2 = line2;
		this.city = city;
		this.state = state;
		this.country = country;
		this.pincode = pincode;
		this.active = active;
		this.otp = otp;
		this.verified = verified;
	}

	public User(Long id, @NotEmpty(message = "*Please provide First Name") String fname, String lname, String email,
			@Pattern(regexp = "^([1-9])\\d{9}", message = "*Please provide a valid contact number") String contact,
			String password, String line1, String line2, String city, String state, String country, String pincode,
			int active, int otp, boolean verified, Set<Role> roles) {
		super();
		this.id = id;
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.contact = contact;
		this.password = password;
		this.line1 = line1;
		this.line2 = line2;
		this.city = city;
		this.state = state;
		this.country = country;
		this.pincode = pincode;
		this.active = active;
		this.otp = otp;
		this.verified = verified;
		this.roles = roles;
	}
	
	

	public String getLine1() {
		return line1;
	}
	public void setLine1(String line1) {
		this.line1 = line1;
	}
	public String getLine2() {
		return line2;
	}
	public void setLine2(String line2) {
		this.line2 = line2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	
	
	@JsonIgnore
	public String getPassword() {
		return password;
	}
	
	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public int getOtp() {
		return otp;
	}

	public void setOtp(int otp) {
		this.otp = otp;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}



	
	
	
	

}
