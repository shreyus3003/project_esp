package com.foodys.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.foodys.app.models.FoodOrder;
import com.foodys.app.models.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

	
	public List<Ticket> findTicketByDriver_id(long driver_id);
	public List<Ticket> findFoodOrderByDriver_id(long driver_id);
	public Ticket findTicketById(long id);
	public List<Ticket> findAll();
	
	@Query("From FoodOrder c where c.fcity=(Select t.source_city From Ticket t where t.id=:id) and c.fstate=(Select t.source_state From Ticket t where t.id=:id) and c.city=(Select t.destination_city From Ticket t where t.id=:id) and c.state=(Select t.destination_state From Ticket t where t.id=:id) and driver IS NULL")
	public List<FoodOrder> findFoodOrderByTicketId(@Param("id")  long ticketId);
	
}
