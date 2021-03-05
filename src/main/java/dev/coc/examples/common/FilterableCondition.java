package dev.coc.examples.common;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({ 
	@JsonSubTypes.Type(value = FilterableConditionSimple.class, name = "filterableConditionSimple"),
	@JsonSubTypes.Type(value = FilterableConditionCompound.class, name = "filterableConditionCompound")
})
public class FilterableCondition implements Serializable{

	private static final long serialVersionUID = 623092928198012106L;	

}
