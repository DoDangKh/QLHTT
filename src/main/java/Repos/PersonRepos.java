package Repos;

import Entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepos extends JpaRepository<Person, Integer> {

}
