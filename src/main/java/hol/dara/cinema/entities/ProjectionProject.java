package hol.dara.cinema.entities;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.rest.core.config.Projection;

@Projection(name="p1",types = {hol.dara.cinema.entities.Projection.class})
public interface ProjectionProject {
	public Long getId();
	public double getPrix();
	public Date getDateProj();
	public Salle getSalle();
	public Film getFilm();
	public Seance getSeance();
	public Collection<Ticket> getTickets();
}
