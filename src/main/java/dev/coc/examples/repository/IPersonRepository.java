package dev.coc.examples.repository;

import dev.coc.common.repository.IGenericFilterableRepository;
import dev.coc.common.repository.PageList;
import dev.coc.examples.common.Searchable;
import dev.coc.examples.persistence.model.Person;
import dev.coc.examples.persistence.model.PersonFilter;

/**
 * Person repository interface
 * @author Mario Diaz
 *
 */
public interface IPersonRepository extends IGenericFilterableRepository<Person, Long, PersonFilter> {
	
	PageList<Person> findAllBySearchable(Searchable searchable);

}
