package dev.coc.examples.repository.impl;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import dev.coc.common.mapper.filter.Filter;
import dev.coc.common.repository.AbstractRepository;
import dev.coc.common.repository.PageList;
import dev.coc.examples.common.Filterable;
import dev.coc.examples.common.FilterableConditionCompound;
import dev.coc.examples.common.FilterableConditionSimple;
import dev.coc.examples.common.Joinable;
import dev.coc.examples.common.Pageable;
import dev.coc.examples.common.Searchable;
import dev.coc.examples.common.SortType;
import dev.coc.examples.common.Sortable;
import dev.coc.examples.persistence.model.Person;
import dev.coc.examples.persistence.model.PersonFilter;
import dev.coc.examples.repository.IPersonRepository;

/**
 * Person repository
 * @author Mario Diaz
 *
 */
@Repository
@Transactional(propagation = Propagation.MANDATORY)
public class PersonRepositoryImpl extends AbstractRepository<Person, Long> implements IPersonRepository {

	public PersonRepositoryImpl() {
		super(Person.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PageList<Person> findAllByFilter(Filter<PersonFilter> filter) {
		PersonFilter personFilter = filter.getContent();

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Person> criteriaQuery = criteriaBuilder.createQuery(clazz);		
		Root<Person> person = criteriaQuery.from(clazz);	
		List<Predicate> preds = new ArrayList<>();

		// Filtering
		if(personFilter != null) {
			if(personFilter.getActive() != null) {
				preds.add(criteriaBuilder.equal(person.<Boolean>get(PROPERTY_ACTIVE), personFilter.getActive()));
			}

			if(personFilter.getModificationDate() != null) {
				preds.add(criteriaBuilder.equal(person.<ZonedDateTime>get(PROPERTY_MODIFICATION_DATE), ZonedDateTime.parse(personFilter.getModificationDate())));
			}

			if(personFilter.getVersionLock() != null) {
				preds.add(criteriaBuilder.equal(person.<Integer>get(PROPERTY_VERSION_LOCK), personFilter.getVersionLock()));
			}

			if(personFilter.getUpdatable() != null) {
				preds.add(criteriaBuilder.equal(person.<Boolean>get(PROPERTY_UPDATABLE), personFilter.getUpdatable()));
			}

			if(personFilter.getDeletable() != null) {
				preds.add(criteriaBuilder.equal(person.<Boolean>get(PROPERTY_DELETABLE), personFilter.getDeletable()));
			}

			if(personFilter.getId() != null) {
				preds.add(criteriaBuilder.equal(person.<Long>get(PROPERTY_ID), personFilter.getId()));
			}

		}

		// Sorting
		List<Order> orderList = new ArrayList<>();
		orderList.add(criteriaBuilder.asc(person.<String>get(PROPERTY_NAME)));

		criteriaQuery.select(person);		
		criteriaQuery.where(preds.toArray(new Predicate[0]));
		criteriaQuery.orderBy(orderList);	

		TypedQuery<Person> query = entityManager.createQuery(criteriaQuery);		

		if(filter.getPage() != null && filter.getSize() != null) {
			query.setFirstResult(filter.getOffset(filter.getPage(), filter.getSize()));
			query.setMaxResults(filter.getSize());
		}

		CriteriaQuery<Long> criteriaQueryCount = criteriaBuilder.createQuery(Long.class);
		criteriaQueryCount.select(criteriaBuilder.count(criteriaQueryCount.from(clazz)));
		criteriaQueryCount.where(preds.toArray(new Predicate[0]));

		Long rownum = entityManager.createQuery(criteriaQueryCount).getSingleResult();

		PageList<Person> pageList = new PageList<>();
		pageList.setResults(query.getResultList());
		pageList.setCount(rownum);

		if(filter.getPage() != null && filter.getSize() != null) {
			pageList.setPage(filter.getPage());
			pageList.setSize(filter.getSize());
		}			

		return pageList;

	}

	@Override
	@Transactional(readOnly = true)
	public PageList<Person> findAllBySearchable(Searchable searchable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Person> criteriaQuery = criteriaBuilder.createQuery(clazz);		
		Root<Person> person = criteriaQuery.from(clazz);	
		List<Predicate> preds = new LinkedList<>();
		List<Order> orders = new LinkedList<>();

		// Sortable
		sortable(searchable.getSortable(), person, orders, criteriaBuilder);

		// Joinable
		joinable(searchable.getJoinable(), person, preds, criteriaBuilder);

		// Filterable
		filterable(searchable.getFilterable(), person, preds, criteriaBuilder);

		criteriaQuery.select(person);		
		criteriaQuery.where(preds.toArray(new Predicate[0]));
		criteriaQuery.orderBy(orders);	

		TypedQuery<Person> query = null;
		
		try {
			
			query = entityManager.createQuery(criteriaQuery);
			
		} catch (Exception e) {
			
			throw new CannotExecuteTypedQueryException();
			
		}

		// Pageable
		pageable(searchable.getPageable(), query);

		// Execute query
		List<Person> persons = query.getResultList();

		// Build page list
		PageList<Person> pageList = new PageList<>();
		pageList.setResults(query.getResultList());
		pageList.setCount((long) persons.size());
		pageList.setPage(searchable.getPageable().getPage());
		pageList.setSize(searchable.getPageable().getSize());		

		return pageList;

	}

	private static void sortable(Sortable sortable, Root<?> root, List<Order> orders, CriteriaBuilder criteriaBuilder) {

		if(!sortable.getSortableProperties().isEmpty()) {
			sortable.getSortableProperties().stream().forEach(sortableProperty -> {
				if(sortableProperty.getKey() != null && sortableProperty.getSortType() != null) {
					try {

						if(sortableProperty.getSortType().equals(SortType.ASC)) {
							orders.add(criteriaBuilder.asc(root.get(sortableProperty.getKey())));

						} else {

							orders.add(criteriaBuilder.desc(root.get(sortableProperty.getKey())));

						}

					} catch (Exception e) {

						System.out.println("Evicted sortable property");

					}
				}
			});
		}
	}

	private static void filterable(Filterable filterable, Root<?> root, List<Predicate> preds, CriteriaBuilder criteriaBuilder) {

		if(!filterable.getFilterableConditions().isEmpty()) { 
			filterable.getFilterableConditions().stream().forEach(filterableCondition -> {

				if(filterableCondition instanceof FilterableConditionSimple) {

					FilterableConditionSimple aux = (FilterableConditionSimple) filterableCondition;

					if(aux.getKey() != null && aux.getValue() != null && aux.getConditionType() != null) {

						try {

							Predicate pred = buildPredicateSimple(aux, root.get(aux.getKey()), criteriaBuilder);

							if(pred != null) {

								preds.add(pred);

							}

						} catch(Exception e) {

							System.out.println("Evicted filterable condition");

						}
					}
				}

				if(filterableCondition instanceof FilterableConditionCompound) {

					FilterableConditionCompound aux = (FilterableConditionCompound) filterableCondition;

					if(aux.getConditionType() != null) {

						try {

							Predicate pred = buildPredicateCompound(aux, root, criteriaBuilder);

							if(pred != null) {

								preds.add(pred);

							}

						} catch(Exception e) {

							System.out.println("Evicted filterable condition");

						}

					}
				}

			});
		}

	}

	private static void pageable(Pageable pageable, TypedQuery<?> query) {

		query.setFirstResult(Pageable.getOffset(pageable.getPage(), pageable.getSize()));
		query.setMaxResults(pageable.getSize());

	}

	private static void joinable(Joinable joinable, Root<?> root, List<Predicate> preds, CriteriaBuilder criteriaBuilder) {

		if(!joinable.getJoinableConditions().isEmpty()) {
			joinable.getJoinableConditions().stream().forEach(joinableCondition -> {
				if(joinableCondition.getParentEntityClassName() != null && joinableCondition.getJoinEntityClassName() != null
						&& joinableCondition.getKey() != null) {

					try {

						Join<?, ?> join = getJoin(root,
								Class.forName(joinableCondition.getParentEntityClassName()), 
								Class.forName(joinableCondition.getJoinEntityClassName()),
								joinableCondition.getKey(), 
								joinableCondition.getJoinType());

						Predicate pred = buildPredicateSimple(joinableCondition.getFilterableCondition(), 
								join.get(joinableCondition.getFilterableCondition().getKey()), criteriaBuilder);

						if(pred != null) {

							preds.add(pred);

						}

					} catch (Exception e) {

						System.out.println("Evicted joinable condition");

					}
				}
			});
		}

	}

	private static <K, E> Join<K, E> getJoin(Root<?> root, K parentEntity, E joinEntity, String joinProperty, JoinType joinType) {
		Join<K, E> join = root.join(joinProperty, joinType);

		return join;
	}

	@SuppressWarnings("unchecked")
	private static Predicate buildPredicateSimple(FilterableConditionSimple filterableConditionSimple,
			Expression<?> expression,
			CriteriaBuilder criteriaBuilder) {

		Predicate pred = null;

		try {

			switch(filterableConditionSimple.getConditionType()) {

			case EQUAL:
				pred = criteriaBuilder.equal(expression, filterableConditionSimple.getValue());
				break;

			case LIKE:
				pred = criteriaBuilder.like((Expression<String>) expression, "%" + (String) filterableConditionSimple.getValue() + "%");
				break;

			default:
				break;
			}

		} catch (Exception e) {

			System.out.println("Evicted buildPredicateSimple");

		}

		return pred;

	}

	private static Predicate buildPredicateCompound(FilterableConditionCompound filterableConditionCompound,
			Root<?> root,
			CriteriaBuilder criteriaBuilder) {

		Predicate pred = null;

		try {
			switch(filterableConditionCompound.getConditionType()) {

			case AND:
				List<Predicate> preds = new LinkedList<>();

				filterableConditionCompound.getFilterableConditions().stream().forEach(filterableCondition -> {
					
					Predicate predAux = null;

					if(filterableCondition instanceof FilterableConditionSimple) {

						FilterableConditionSimple aux = (FilterableConditionSimple) filterableCondition;
						predAux = buildPredicateSimple(aux, root.get(aux.getKey()), criteriaBuilder);

					}

					if(filterableCondition instanceof FilterableConditionCompound) {

						FilterableConditionCompound aux = (FilterableConditionCompound) filterableCondition;
						predAux = buildPredicateCompound(aux, root, criteriaBuilder);

					}
					
					if(predAux != null) {
						preds.add(predAux);
					}

				});

				pred = criteriaBuilder.and(preds.toArray(new Predicate[0]));
				break;

			default:
				break;

			}

		} catch (Exception e) {

			System.out.println("Evicted buildPredicateCompound");

		}

		return pred;

	}
	
	class CannotExecuteTypedQueryException extends RuntimeException {

		private static final long serialVersionUID = 3214544156933207758L;
		
	}

}


