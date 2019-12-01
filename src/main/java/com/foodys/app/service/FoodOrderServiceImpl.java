package com.foodys.app.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodys.app.models.FoodOrder;
import com.foodys.app.models.Status;
import com.foodys.app.models.Tracker;
import com.foodys.app.models.Transaction;
import com.foodys.app.repository.FoodOrderRepository;
import com.foodys.app.repository.TrackerRepository;
import com.foodys.app.repository.TransactionRepository;

@Service
public class FoodOrderServiceImpl implements FoodOrderService{

	@Autowired
	FoodOrderRepository foodOrderRepo;

	@Autowired
	TransactionRepository transactionRepo;

	
	@Autowired
	private TrackerRepository trackerRepo;

	
	@Override
	public List<FoodOrder> findFoodOrderByProvider_id(long provider_id) {
		
		return foodOrderRepo.findFoodOrderByProvider_id(provider_id);
	}

	
	@Override
	public List<FoodOrder> findAll() {
		
		return foodOrderRepo.findAll();
	}

	@Override
	public FoodOrder findFoodOrderById(long id) {
		
		return foodOrderRepo.findFoodOrderById(id);
	}

	@Override
	public long save(FoodOrder foodOrder) {
		
		FoodOrder inserted_FoodOrder = foodOrderRepo.save(foodOrder);
		return inserted_FoodOrder.getId();
	}

	@Override
	public FoodOrder update(FoodOrder foodOrder) {
		FoodOrder updated_foodOrder = foodOrderRepo.save(foodOrder);
		return updated_foodOrder;
	}

	@Override
	public void delete(long id) {
		
		foodOrderRepo.deleteById(id);
	}


	@Override
	public List<FoodOrder> findFoodOrderByDriver_id(long driver_id) {
		
		return foodOrderRepo.findFoodOrderByDriver_id(driver_id);
	}


	@Override
	public void acceptFoodOrder(FoodOrder foodOrder) {
		
		foodOrder.setStatus(Status.ACCEPTED);
		foodOrderRepo.save(foodOrder);
		Tracker tracker = new Tracker(foodOrder,Status.ACCEPTED,"Food Order has been accepted by Driver :"+foodOrder.getDriver().getFname()+" email:"+foodOrder.getDriver().getEmail());
		trackerRepo.save(tracker);
	}
	
	@Override
	public void collectFoodOrder(FoodOrder foodOrder) {
		
		foodOrder.setStatus(Status.COLLECTED);
		foodOrderRepo.save(foodOrder);
		Tracker tracker = new Tracker(foodOrder,Status.COLLECTED,"Food Order has been collected by Driver :"+foodOrder.getDriver().getFname()+" email:"+foodOrder.getDriver().getEmail());
		trackerRepo.save(tracker);
	}
	
	@Transactional
	@Override
	public void deliverFoodOrder(FoodOrder foodOrder) {
		
		foodOrder.setStatus(Status.DELIVERED);
		foodOrderRepo.save(foodOrder);
		Tracker tracker = new Tracker(foodOrder,Status.DELIVERED,"Food Order has been delivered by Driver :"+foodOrder.getDriver().getFname()+" email:"+foodOrder.getDriver().getEmail());
		trackerRepo.save(tracker);
		
		//create transactions to debit the account of Provider and credit Foodys and Driver accounts.
		
		Transaction t_provider_foodys = new Transaction(foodOrder, foodOrder.getProvider().getEmail(), "Foodys" ,foodOrder.getQuoted_price()*0.1,"10% platform charges");
		transactionRepo.save(t_provider_foodys);
		Transaction t_provider_driver = new Transaction(foodOrder, foodOrder.getProvider().getEmail(), foodOrder.getDriver().getEmail() ,foodOrder.getQuoted_price()*0.9,"90% of Quoted Amount to Driver");
		transactionRepo.save(t_provider_driver);
	}
	
	@Override
	public void returnFoodOrder(FoodOrder foodOrder) {
		
		foodOrder.setStatus(Status.RETURNED);
		foodOrderRepo.save(foodOrder);
		Tracker tracker = new Tracker(foodOrder,Status.RETURNED,"FoodOrder has been returned by Driver :"+foodOrder.getDriver().getFname()+" email:"+foodOrder.getDriver().getEmail());
		trackerRepo.save(tracker);
	}

	@Transactional
	@Override
	public void updateFoodOrderWithFilename(String fileName, long cid) {
		
		FoodOrder foodOrder = foodOrderRepo.findFoodOrderById(cid);
		foodOrder.setCustomer_image(fileName);
		foodOrderRepo.save(foodOrder);
		this.deliverFoodOrder(foodOrder);
		
	}


	@Override
	public void transitFoodOrder(FoodOrder foodOrder) {
		foodOrder.setStatus(Status.TRANSIT);
		foodOrderRepo.save(foodOrder);
		Tracker tracker = new Tracker(foodOrder,Status.TRANSIT,"Food Order In Transit by :"+foodOrder.getDriver().getFname()+" email:"+foodOrder.getDriver().getEmail());
		trackerRepo.save(tracker);
		
	}
	
	
	
	
}
