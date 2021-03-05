package dev.coc.examples.common;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Metamodel {
	
	private String classPackageName;
	private List<FilterableAttribute> filterableAttributes;
	private List<JoinableClass> joinableAttributes;

}
