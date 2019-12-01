package com.foodys.app.models;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class FoodOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	
	@CreationTimestamp
	LocalDateTime datetime;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	Provider provider;
	
	
	@OneToMany(mappedBy= "foodOrder", fetch = FetchType.LAZY)
	Set<Tracker> track;
	
	@JsonIgnore
	@OneToMany(mappedBy= "foodOrder", fetch = FetchType.LAZY)
	Set<Transaction> transactions;
	
	@JsonIgnore
	@ManyToOne
	Driver driver;
	
	@NotNull(message = "*Please provide valid weight")
	double weight;
	
	@NotEmpty(message = "*Please provide content description")
	String content_description;
	
	String instructions; 
	
	@NotEmpty(message = "*Please provide customer name")
	String customer_name;
	
	@Pattern(regexp="^([1-9])\\d{9}", message = "*Please provide a valid contact number")
	String customer_contact;
	
	
	@NotEmpty(message = "*Please provide from city")
	String fcity;
	@NotEmpty(message = "*Please provide from state")
	String fstate;
	@NotEmpty(message = "*Please provide from country")
	String fcountry;
	@NotEmpty(message = "*Please provide from pincode")
	String fpincode;
	
	
	@NotEmpty(message = "*Please provide address")
	String address_line1;
	String address_line2;
	@NotEmpty(message = "*Please provide City")
	String city;
	@NotEmpty(message = "*Please provide state")
	String state;
	@NotEmpty(message = "*Please provide country")
	String country;
	@NotEmpty(message = "*Please provide pincode")
	String pincode;
	
	@Enumerated
	Status status;
	
	String customer_image;
	String driver_image;
	
	@NotNull(message = "*Please provide a valid price")
	double quoted_price;
	


	@UpdateTimestamp
	LocalDateTime udatetime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDateTime getDatetime() {
		return datetime;
	}

	public void setDatetime(LocalDateTime datetime) {
		this.datetime = datetime;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public Set<Tracker> getTrack() {
		return track;
	}

	public void setTrack(Set<Tracker> track) {
		this.track = track;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getContent_description() {
		return content_description;
	}

	public void setContent_description(String content_description) {
		this.content_description = content_description;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public String getCustomer_contact() {
		return customer_contact;
	}

	public void setCustomer_contact(String customer_contact) {
		this.customer_contact = customer_contact;
	}

	public String getAddress_line1() {
		return address_line1;
	}

	public void setAddress_line1(String address_line1) {
		this.address_line1 = address_line1;
	}

	public String getAddress_line2() {
		return address_line2;
	}

	public void setAddress_line2(String address_line2) {
		this.address_line2 = address_line2;
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

	public String getCustomer_image() {
		return customer_image;
	}

	public void setCustomer_image(String customer_image) {
		this.customer_image = customer_image;
	}

	public String getDriver_image() {
		return driver_image;
	}

	public void setDriver_image(String driver_image) {
		this.driver_image = driver_image;
	}

	public double getQuoted_price() {
		return quoted_price;
	}

	public void setQuoted_price(double quoted_price) {
		this.quoted_price = quoted_price;
	}

	public LocalDateTime getUdatetime() {
		return udatetime;
	}

	public void setUdatetime(LocalDateTime udatetime) {
		this.udatetime = udatetime;
	}

	
	public Set<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(Set<Transaction> transactions) {
		this.transactions = transactions;
	}
	
	public void addTransaction(Transaction transaction) {
		this.transactions.add(transaction);
	}
	
	
	

	public String getFcity() {
		return fcity;
	}

	public void setFcity(String fcity) {
		this.fcity = fcity;
	}

	public String getFstate() {
		return fstate;
	}

	public void setFstate(String fstate) {
		this.fstate = fstate;
	}

	public String getFcountry() {
		return fcountry;
	}

	public void setFcountry(String fcountry) {
		this.fcountry = fcountry;
	}

	public String getFpincode() {
		return fpincode;
	}

	public void setFpincode(String fpincode) {
		this.fpincode = fpincode;
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}
	
	

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public FoodOrder() {
		super();
	}

	

	public FoodOrder(long id, Provider provider, Set<Tracker> track, Set<Transaction> transactions,
			@NotNull(message = "*Please provide valid weight") double weight,
			@NotEmpty(message = "*Please provide content description") String content_description, String instructions,
			@NotEmpty(message = "*Please provide customer name") String customer_name,
			@Pattern(regexp = "^([1-9])\\d{9}", message = "*Please provide a valid contact number") String customer_contact,
			@NotEmpty(message = "*Please provide address") String address_line1, String address_line2,
			@NotEmpty(message = "*Please provide City") String city,
			@NotEmpty(message = "*Please provide state") String state,
			@NotEmpty(message = "*Please provide country") String country,
			@NotEmpty(message = "*Please provide customer name") String pincode, String customer_image,
			String driver_image, @NotNull(message = "*Please provide a valid price") double quoted_price) {
		super();
		this.id = id;
		this.provider = provider;
		this.track = track;
		this.transactions = transactions;
		this.weight = weight;
		this.content_description = content_description;
		this.instructions = instructions;
		this.customer_name = customer_name;
		this.customer_contact = customer_contact;
		this.address_line1 = address_line1;
		this.address_line2 = address_line2;
		this.city = city;
		this.state = state;
		this.country = country;
		this.pincode = pincode;
		this.customer_image = customer_image;
		this.driver_image = driver_image;
		this.quoted_price = quoted_price;
	}

	public FoodOrder(Provider provider, Set<Tracker> track, Set<Transaction> transactions,
			@NotNull(message = "*Please provide valid weight") double weight,
			@NotEmpty(message = "*Please provide content description") String content_description, String instructions,
			@NotEmpty(message = "*Please provide customer name") String customer_name,
			@Pattern(regexp = "^([1-9])\\d{9}", message = "*Please provide a valid contact number") String customer_contact,
			@NotEmpty(message = "*Please provide address") String address_line1, String address_line2,
			@NotEmpty(message = "*Please provide City") String city,
			@NotEmpty(message = "*Please provide state") String state,
			@NotEmpty(message = "*Please provide country") String country,
			@NotEmpty(message = "*Please provide customer name") String pincode, String customer_image,
			String driver_image, @NotNull(message = "*Please provide a valid price") double quoted_price) {
		super();
		this.provider = provider;
		this.track = track;
		this.transactions = transactions;
		this.weight = weight;
		this.content_description = content_description;
		this.instructions = instructions;
		this.customer_name = customer_name;
		this.customer_contact = customer_contact;
		this.address_line1 = address_line1;
		this.address_line2 = address_line2;
		this.city = city;
		this.state = state;
		this.country = country;
		this.pincode = pincode;
		this.customer_image = customer_image;
		this.driver_image = driver_image;
		this.quoted_price = quoted_price;
	}

	@Override
	public String toString() {
		return "FoodOrder [id=" + id + ", datetime=" + datetime + ", provider=" + provider + ", track=" + track
				+ ", transactions=" + transactions + ", weight=" + weight + ", content_description="
				+ content_description + ", instructions=" + instructions + ", customer_name=" + customer_name
				+ ", customer_contact=" + customer_contact + ", address_line1=" + address_line1 + ", address_line2="
				+ address_line2 + ", city=" + city + ", state=" + state + ", country=" + country + ", pincode="
				+ pincode + ", customer_image=" + customer_image + ", driver_image=" + driver_image
				+ ", quoted_price=" + quoted_price + ", udatetime=" + udatetime + "]";
	}

	
	
	
	
}
