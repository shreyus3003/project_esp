package com.foodys.app.models;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	
	@CreationTimestamp
	LocalDateTime datetime;
	
//	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	FoodOrder foodOrder;
	
	String debitacc;  // (provider email)
	String creditacc; // (driver_email or Foodys account)
	
	double amount;
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
	
	
	
	public String getDebitacc() {
		return debitacc;
	}
	public void setDebitacc(String debitacc) {
		this.debitacc = debitacc;
	}
	public String getCreditacc() {
		return creditacc;
	}
	public void setCreditacc(String creditacc) {
		this.creditacc = creditacc;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LocalDateTime getUdatetime() {
		return udatetime;
	}
	public void setUdatetime(LocalDateTime udatetime) {
		this.udatetime = udatetime;
	}
	
	
	
	
	public FoodOrder getFoodOrder() {
		return foodOrder;
	}
	public void setFoodOrder(FoodOrder foodOrder) {
		this.foodOrder = foodOrder;
	}
	
	public Transaction() {
		super();
	}
	
	
	public Transaction(long id, FoodOrder foodOrder, String debit_acc, String credit_acc, double amount, String description) {
		super();
		this.id = id;
		this.foodOrder = foodOrder;
		this.debitacc = debit_acc;
		this.creditacc = credit_acc;
		this.amount = amount;
		this.description = description;
	}
	
	
	public Transaction(long id, LocalDateTime datetime, FoodOrder foodOrder, String debit_acc, String credit_acc, double amount,
			String description) {
		super();
		this.id = id;
		this.datetime = datetime;
		this.foodOrder = foodOrder;
		this.debitacc = debit_acc;
		this.creditacc = credit_acc;
		this.amount = amount;
		this.description = description;
	}
	
	
	
	public Transaction(FoodOrder foodOrder, String debit_acc, String credit_acc, double amount, String description) {
		super();
		this.foodOrder = foodOrder;
		this.debitacc = debit_acc;
		this.creditacc = credit_acc;
		this.amount = amount;
		this.description = description;
	}
	@Override
	public String toString() {
		return "Transaction [id=" + id + ", datetime=" + datetime + ", debitacc=" + debitacc + ", creditacc="
				+ creditacc + ", amount=" + amount + ", description=" + description + ", udatetime=" + udatetime + "]";
	}
	
	

	
	
}
