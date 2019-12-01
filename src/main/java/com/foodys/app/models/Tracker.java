package com.foodys.app.models;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Tracker {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	
	@CreationTimestamp
	LocalDateTime datetime;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	FoodOrder foodOrder;
	
	@Enumerated
	Status status;
	
	String description;
	
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

	public FoodOrder getFoodOrder() {
		return foodOrder;
	}

	public void setFoodOrder(FoodOrder foodOrder) {
		this.foodOrder = foodOrder;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public LocalDateTime getUdatetime() {
		return udatetime;
	}

	public void setUdatetime(LocalDateTime udatetime) {
		this.udatetime = udatetime;
	}
	
	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Tracker() {
		super();
	}
	
	

	public Tracker(long id,  FoodOrder foodOrder, Status status, String description
			) {
		super();
		this.id = id;
	
		this.foodOrder = foodOrder;
		this.status = status;
		this.description = description;
		
	}

	public Tracker(FoodOrder foodOrder, Status status, String description) {
		super();
		this.foodOrder = foodOrder;
		this.status = status;
		this.description = description;
	}

	@Override
	public String toString() {
		return "Tracker [id=" + id + ", datetime=" + datetime + ", foodOrder=" + foodOrder + ", status=" + status
				+ ", description=" + description + ", udatetime=" + udatetime + "]";
	}
	
	

	
	
	
	
	
	
}
