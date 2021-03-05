package dev.coc.examples.common;

import java.util.LinkedList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Sortable {
	
	public List<SortableProperty> sortableProperties = new LinkedList<>();
	
}
