package com.foodys.app.service;

import java.util.List;

import com.foodys.app.models.FoodOrder;
import com.foodys.app.models.User;

public interface FoodOrderService {
	
	public List<FoodOrder> findFoodOrderByProvider_id(long provider_id);
	public List<FoodOrder> findFoodOrderByDriver_id(long driver_id);
	public List<FoodOrder> findAll();
	public FoodOrder findFoodOrderById(long id);
	public long save(FoodOrder foodOrder);
	public FoodOrder update(FoodOrder foodOrder);
	public void delete(long id);
	public void acceptFoodOrder(FoodOrder foodOrder);
	public void collectFoodOrder(FoodOrder foodOrder);
	public void deliverFoodOrder(FoodOrder foodOrder);
	public void returnFoodOrder(FoodOrder foodOrder);
	public void updateFoodOrderWithFilename(String fileName, long cid);
	public void transitFoodOrder(FoodOrder foodOrder);

}
