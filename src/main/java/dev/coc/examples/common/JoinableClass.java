package dev.coc.examples.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JoinableClass {
	private String classPackageName;
	private String joinableAttribute;
}
