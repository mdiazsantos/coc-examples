package dev.coc.examples.common;

import java.util.LinkedList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Filterable {
	
	public List<FilterableCondition> filterableConditions = new LinkedList<>();
	
}
