package hol.dara.cinema.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hol.dara.cinema.dao.CategorieRepository;
import hol.dara.cinema.dao.CinemaRepository;
import hol.dara.cinema.dao.FilmRepository;
import hol.dara.cinema.dao.PlaceRepository;
import hol.dara.cinema.dao.ProjectionRepository;
import hol.dara.cinema.dao.SalleRepository;
import hol.dara.cinema.dao.SeanceRepository;
import hol.dara.cinema.dao.TicketRepository;
import hol.dara.cinema.dao.VilleRepository;
import hol.dara.cinema.entities.Categorie;
import hol.dara.cinema.entities.Cinema;
import hol.dara.cinema.entities.Film;
import hol.dara.cinema.entities.Place;
import hol.dara.cinema.entities.Projection;
import hol.dara.cinema.entities.Salle;
import hol.dara.cinema.entities.Seance;
import hol.dara.cinema.entities.Ticket;
import hol.dara.cinema.entities.Ville;

@Service
@Transactional
public class CinemaInitServiceImpl implements ICinemaInitService {
	@Autowired
	private VilleRepository villeRepository;
	@Autowired
	private CinemaRepository cinemaRepository;
	@Autowired
	private SalleRepository salleRepository;
	@Autowired
	private PlaceRepository placeRepository;
	@Autowired
	private ProjectionRepository projectionRepository;
	@Autowired
	private SeanceRepository seanceRepository;
	@Autowired
	private FilmRepository filmRepository;
	@Autowired
	private CategorieRepository categorieRepository;
	@Autowired
	private TicketRepository ticketRepository;

	@Override
	public void initVilles() {
		Stream.of("Bruxelles","Anvers","Liège","Paris","Lyon").forEach(nomVille->{
			Ville ville= new Ville();
			ville.setNom(nomVille);
			villeRepository.save(ville);
		});
		
	}

	@Override
	public void initCinemas() {
		villeRepository.findAll().forEach(v->{
			Stream.of("Le Range","Walli","Renaissance","La source").forEach(nomCine->{
				Cinema cine=new Cinema();
				cine.setNom(nomCine);
				cine.setVille(v);
				cine.setNbreSalles(2+(int)(Math.random()*5));
				cinemaRepository.save(cine);
			});
		});
		
		
	}

	@Override
	public void initSalles() {
		cinemaRepository.findAll().forEach(c->{
			for(int i=0;i<c.getNbreSalles();i++) {
				Salle salle= new Salle();
				salle.setNom("salle "+(i+1));
				salle.setNbrePlaces(10+(int)(Math.random()*15));
				salle.setCinema(c);
				salleRepository.save(salle);
				
			}
		});
		
	}

	@Override
	public void initPlaces() {
		salleRepository.findAll().forEach(s->{
			for(int i=0;i<s.getNbrePlaces();i++) {
				Place place=new Place();
				place.setNumero(i+1);
				place.setSalle(s);
				placeRepository.save(place);
			}
		});
		
	}

	@Override
	public void initSeances() {
		DateFormat datef = new SimpleDateFormat("HH:mm");
		Stream.of("12:00","15:00","19:00","21:00","00:00").forEach(s->{
			Seance seance=new Seance();
			try {
				seance.setHeureDebut(datef.parse(s));
				seanceRepository.save(seance);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
	}

	@Override
	public void initCategories() {
		Stream.of("Fiction","Action","Comédie","Cow boy","Drama").forEach(c->{
			Categorie cat= new Categorie();
			cat.setNom(c);
			categorieRepository.save(cat);
		});
		
	}

	@Override
	public void initFilms() {
		List<Categorie> cat = categorieRepository.findAll();
		double [] durees= new double [] {1,1.5,2,2.5,3};
		Stream.of("24h Chrono","Fast and Furious","iron Man","Transporter 2","Hell Driver").forEach(tf->{
			Film film= new Film();
			film.setTitre(tf);
			film.setPhoto(tf.replaceAll(" ", "")+".jpg");
			film.setCategorie(cat.get(new Random().nextInt(cat.size())));
			film.setDuree(durees[new Random().nextInt(durees.length)]);
			filmRepository.save(film);
		});
		
	}

	@Override
	public void initProjections() {
		double [] prix= new double[] {20,25,30,35,40,45,50};
		List<Film> films= filmRepository.findAll();
		villeRepository.findAll().forEach(ville->{
			ville.getCinemas().forEach(cinema->{
				cinema.getSalles().forEach(salle->{
					int indexFilm= new Random().nextInt(films.size());
					Film film = films.get(indexFilm);	
						seanceRepository.findAll().forEach(seance->{
							Projection proj =new Projection();
							proj.setFilm(film);
							proj.setSalle(salle);
							proj.setDateProj(new Date());
							proj.setSeance(seance);
							proj.setPrix(prix[new Random().nextInt(prix.length)]);
							projectionRepository.save(proj);
						});
				});
			});
		});
		
	}

	@Override
	public void initTicket() {
		projectionRepository.findAll().forEach(proj->{
			proj.getSalle().getPlaces().forEach(place->{
				Ticket ticket= new Ticket();
				ticket.setPlace(place);
				ticket.setPrix(proj.getPrix());
				ticket.setProjection(proj);
				ticket.setReserve(false);
				ticketRepository.save(ticket);
			});
		});
		
	}

}
