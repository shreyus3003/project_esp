package com.foodys.app.service;

import java.util.List;

import com.foodys.app.models.FoodOrder;
import com.foodys.app.models.Ticket;

public interface TicketService {
	
	public List<Ticket> findTicketByDriver_id(long driver_id);
	public List<Ticket> findFoodOrderByDriver_id(long driver_id);
	public List<Ticket> findAll();
	public Ticket findTicketById(long id);
	public long save(Ticket ticket);
	public Ticket update(Ticket ticket);
	public void delete(long id);
	public List<FoodOrder> findFoodOrderByTicketId(long ticketId);

}
