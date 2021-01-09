package hol.dara.cinema.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import hol.dara.cinema.entities.Cinema;
@RepositoryRestResource
@CrossOrigin("*")
public interface CinemaRepository  extends JpaRepository<Cinema, Long>{

}
