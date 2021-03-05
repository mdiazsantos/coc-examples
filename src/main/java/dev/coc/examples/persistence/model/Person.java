package dev.coc.examples.persistence.model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import dev.coc.common.persistence.model.AbstractEntity;
import dev.coc.examples.common.FilterableAttribute;
import dev.coc.examples.common.JoinableClass;
import dev.coc.examples.common.Metamodel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Person entity
 * @author Mario Diaz
 *
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "coc_examples_person")
public class Person extends AbstractEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME", nullable = false, length = 255)
	private String name;

	@Column(name = "SURNAME", nullable = true, length = 255)
	private String surname;
	
	@JsonIgnore
	@OneToMany(mappedBy = "person", fetch = FetchType.LAZY, orphanRemoval = true)
	private List<Address> addresses = new LinkedList<>();
	
	public static Metamodel getMetamodel() {
		
		Metamodel metamodel = new Metamodel();
		
		metamodel.setClassPackageName(Person.class.getTypeName());
		metamodel.setFilterableAttributes(Arrays.asList(
				new FilterableAttribute("active", "Boolean"),
				new FilterableAttribute("versionLock", "Integer"),
				new FilterableAttribute("updatable", "Boolean"),
				new FilterableAttribute("deletable", "Boolean"),
				new FilterableAttribute("id", "Long"),
				new FilterableAttribute("name", "String"),
				new FilterableAttribute("surname", "String")));
		
		metamodel.setJoinableAttributes(Arrays.asList(new JoinableClass(Address.class.getTypeName(), "addresses")));
		return metamodel;
		
	}

}
