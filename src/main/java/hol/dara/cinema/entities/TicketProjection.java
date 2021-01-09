package hol.dara.cinema.entities;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "ticketProj",types = Ticket.class)
public interface TicketProjection {
	public Long getId();
	public double getPrix();
	public Integer getCodePaiement();
	public String getNomclient();
	public boolean getReserve();
	public Place getPlace();
	
}
