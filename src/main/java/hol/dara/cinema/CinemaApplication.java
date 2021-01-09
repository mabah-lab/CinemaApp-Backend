package hol.dara.cinema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import hol.dara.cinema.entities.Film;
import hol.dara.cinema.entities.Salle;
import hol.dara.cinema.entities.Ticket;
import hol.dara.cinema.service.ICinemaInitService;

@SpringBootApplication
public class CinemaApplication implements CommandLineRunner  {
	@Autowired
	private ICinemaInitService iCinemaInitService;
	@Autowired
	private RepositoryRestConfiguration restConfiguration;
	
	public static void main(String[] args) {
		SpringApplication.run(CinemaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		restConfiguration.exposeIdsFor(Film.class,Salle.class,Ticket.class);
		
		/*
		 * iCinemaInitService.initVilles(); iCinemaInitService.initCinemas();
		 * iCinemaInitService.initSalles(); iCinemaInitService.initPlaces();
		 * iCinemaInitService.initSeances(); iCinemaInitService.initCategories();
		 * iCinemaInitService.initFilms(); iCinemaInitService.initProjections();
		 * iCinemaInitService.initTicket();
		 * 
		 */
		
	}

}
