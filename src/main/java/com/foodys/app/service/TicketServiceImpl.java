package com.foodys.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodys.app.models.FoodOrder;
import com.foodys.app.models.Ticket;
import com.foodys.app.repository.TicketRepository;

@Service
public class TicketServiceImpl implements TicketService{

	@Autowired
	TicketRepository ticketRepo;


	
	@Override
	public List<Ticket> findAll() {
		
		return ticketRepo.findAll();
	}

	@Override
	public Ticket findTicketById(long id) {
		
		return ticketRepo.findTicketById(id);
	}

	@Override
	public long save(Ticket Ticket) {
		
		Ticket inserted_Ticket = ticketRepo.save(Ticket);
		return inserted_Ticket.getId();
	}

	@Override
	public Ticket update(Ticket Ticket) {
		Ticket updated_Ticket = ticketRepo.save(Ticket);
		return updated_Ticket;
	}

	@Override
	public void delete(long id) {
		
		ticketRepo.deleteById(id);
	}


	@Override
	public List<Ticket> findTicketByDriver_id(long driver_id) {
		
		return ticketRepo.findTicketByDriver_id(driver_id);
	}

	@Override
	public List<Ticket> findFoodOrderByDriver_id(long driver_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FoodOrder> findFoodOrderByTicketId(long ticketId) {
		
		return ticketRepo.findFoodOrderByTicketId(ticketId);
		
	}
	
	
	
	
}
