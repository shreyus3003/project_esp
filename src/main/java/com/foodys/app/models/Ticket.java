package com.foodys.app.models;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	
	@CreationTimestamp
	LocalDateTime datetime;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	Driver driver;
	
	@NotEmpty(message = "*Please provide source city")
	String source_city;
	@NotEmpty(message = "*Please provide source state")
	String source_state;
	@NotEmpty(message = "*Please provide destination city")
	String destination_city;
	@NotEmpty(message = "*Please provide destination state")
	String destination_state;
	@NotNull(message = "*Please provide departure date")
	LocalDateTime departure_date;
	@NotNull(message = "*Please provide arrival date")
	LocalDateTime arrival_date;
	
	String ticket_file;
	
	@UpdateTimestamp
	LocalDateTime udatetime;
	
	String verified;	//set by admin once ticket is checked online with issuer.

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

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	public String getSource_city() {
		return source_city;
	}

	public void setSource_city(String source_city) {
		this.source_city = source_city;
	}

	public String getSource_state() {
		return source_state;
	}

	public void setSource_state(String source_state) {
		this.source_state = source_state;
	}

	public String getDestination_city() {
		return destination_city;
	}

	public void setDestination_city(String destination_city) {
		this.destination_city = destination_city;
	}

	public String getDestination_state() {
		return destination_state;
	}

	public void setDestination_state(String destination_state) {
		this.destination_state = destination_state;
	}

	public LocalDateTime getDeparture_date() {
		return departure_date;
	}

	public void setDeparture_date(LocalDateTime departure_date) {
		this.departure_date = departure_date;
	}

	public LocalDateTime getArrival_date() {
		return arrival_date;
	}

	public void setArrival_date(LocalDateTime arrival_date) {
		this.arrival_date = arrival_date;
	}

	public String getTicket_file() {
		return ticket_file;
	}

	public void setTicket_file(String ticket_file) {
		this.ticket_file = ticket_file;
	}

	public LocalDateTime getUdatetime() {
		return udatetime;
	}

	public void setUdatetime(LocalDateTime udatetime) {
		this.udatetime = udatetime;
	}

	public String getVerified() {
		return verified;
	}

	public void setVerified(String verified) {
		this.verified = verified;
	}

	
	public Ticket() {
		super();
	}

	
	public Ticket(long id, Driver driver, @NotEmpty(message = "*Please provide source city") String source_city,
			@NotEmpty(message = "*Please provide source state") String source_state,
			@NotEmpty(message = "*Please provide destination city") String destination_city,
			@NotEmpty(message = "*Please provide destination state") String destination_state,
			@NotNull(message = "*Please provide departure date") LocalDateTime departure_date,
			@NotNull(message = "*Please provide arrival date") LocalDateTime arrival_date, String ticket_file) {
		super();
		this.id = id;
		this.driver = driver;
		this.source_city = source_city;
		this.source_state = source_state;
		this.destination_city = destination_city;
		this.destination_state = destination_state;
		this.departure_date = departure_date;
		this.arrival_date = arrival_date;
		this.ticket_file = ticket_file;
	}

	
	
	public Ticket(Driver driver, @NotEmpty(message = "*Please provide source city") String source_city,
			@NotEmpty(message = "*Please provide source state") String source_state,
			@NotEmpty(message = "*Please provide destination city") String destination_city,
			@NotEmpty(message = "*Please provide destination state") String destination_state,
			@NotNull(message = "*Please provide departure date") LocalDateTime departure_date,
			@NotNull(message = "*Please provide arrival date") LocalDateTime arrival_date, String ticket_file,
			String verified) {
		super();
		this.driver = driver;
		this.source_city = source_city;
		this.source_state = source_state;
		this.destination_city = destination_city;
		this.destination_state = destination_state;
		this.departure_date = departure_date;
		this.arrival_date = arrival_date;
		this.ticket_file = ticket_file;
		this.verified = verified;
	}

	@Override
	public String toString() {
		return "Ticket [id=" + id + ", datetime=" + datetime  + ", source_city="
				+ source_city + ", source_state=" + source_state + ", destination_city=" + destination_city
				+ ", destination_state=" + destination_state + ", departure_date=" + departure_date + ", arrival_date="
				+ arrival_date + ", ticket_file=" + ticket_file + ", udatetime=" + udatetime + ", verified=" + verified
				+ "]";
	}

	
}
