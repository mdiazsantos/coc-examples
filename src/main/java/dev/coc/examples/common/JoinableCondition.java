package dev.coc.examples.common;

import javax.persistence.criteria.JoinType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinableCondition {
	
	private String parentEntityClassName;
	private String joinEntityClassName;
	private String key;
	private JoinType joinType = JoinType.INNER;
	private FilterableConditionSimple filterableCondition = new FilterableConditionSimple();

}
