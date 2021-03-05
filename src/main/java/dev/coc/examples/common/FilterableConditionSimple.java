package dev.coc.examples.common;

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
@JsonTypeName("filterableConditionSimple")
public class FilterableConditionSimple extends FilterableCondition {
	
	private static final long serialVersionUID = -6029625548792169682L;
	
	public FilterableConditionSimpleType conditionType;
	public String key;
	public Object value;
	
	// Add "type": "filterableConditionSimple" to JSON
	
}
