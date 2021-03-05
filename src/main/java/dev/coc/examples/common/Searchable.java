package dev.coc.examples.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Searchable {
	
	public Filterable filterable = new Filterable();
	public Joinable joinable = new Joinable();
	public Pageable pageable = new Pageable();
	// public Parameterizable parameterizable = new Parameterizable();
	public Sortable sortable = new Sortable();

}
