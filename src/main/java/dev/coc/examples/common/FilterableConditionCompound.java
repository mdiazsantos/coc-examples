package dev.coc.examples.common;

import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeName("filterableConditionCompound")
public class FilterableConditionCompound extends FilterableCondition {
	
	private static final long serialVersionUID = 7777276365739436996L;
	
	public FilterableConditionCompoundType conditionType;
	public List<FilterableCondition> filterableConditions = new LinkedList<>();
	
	// Add "type": "filterableConditionCompound" to JSON
	
}
