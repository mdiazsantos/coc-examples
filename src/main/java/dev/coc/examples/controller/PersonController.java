package dev.coc.examples.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.coc.common.repository.PageList;
import dev.coc.examples.common.Metamodel;
import dev.coc.examples.common.Searchable;
import dev.coc.examples.persistence.model.Person;
import dev.coc.examples.repository.IPersonRepository;

/**
 * Person controller
 * @author Mario Diaz
 *
 */
@RestController
@RequestMapping(value = "persons")
public class PersonController {
	
	// http://localhost:9099/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config
	
	private final IPersonRepository personRepository;
	
	@Autowired
	public PersonController(IPersonRepository personRepository) {
		this.personRepository = personRepository;
	}
	
	@PostMapping(value = "searchable")
	public ResponseEntity<PageList<Person>> getAll(@RequestBody(required = true) Searchable searchable) {
		ResponseEntity<PageList<Person>> response;
		
		PageList<Person> entities = personRepository.findAllBySearchable(searchable);

		response = ResponseEntity.ok(entities);

		return response;
	}
	
	@GetMapping(value = "metamodel")
	public ResponseEntity<Metamodel> getMetamodel() {

		return ResponseEntity.ok(Person.getMetamodel());
		
	}

}
