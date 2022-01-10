package hol.dara.cinema.web;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import hol.dara.cinema.dao.FilmRepository;
import hol.dara.cinema.dao.TicketRepository;
import hol.dara.cinema.entities.Film;
import hol.dara.cinema.entities.Ticket;
import lombok.Data;

@RestController
@CrossOrigin("*")
public class CinemaRestController {
	@Autowired
	FilmRepository filmRepository;
	@Autowired
	TicketRepository ticketRepository;
	@GetMapping(path = "/imageFilm/{id}",produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] images(@PathVariable(name = "id")Long id) throws Exception {
		Film film= filmRepository.findById(id).get();
		String photoName=film.getPhoto();
		File fichier= new File("src/images/"+photoName);
		Path path= Paths.get(fichier.toURI());
		return Files.readAllBytes(path);
	}
	@PostMapping("/payerTickets")
	@Transactional
	public List<Ticket> payerTickets(@RequestBody TicketForm ticketForm){
		List<Ticket> listTickets= new ArrayList<>();
		ticketForm.getTickets().forEach(idticket->{
			Ticket t=ticketRepository.findById(idticket).get();
			t.setCodePaiement(ticketForm.getCodePaiement());
			t.setNomclient(ticketForm.getNomClient());
			t.setReserve(true);
			ticketRepository.save(t);
			listTickets.add(t);
		});
		return listTickets;
	}

}

@Data
class TicketForm{
	private int codePaiement;
	private String nomClient;
	private List<Long> tickets= new ArrayList<>();
	
}
