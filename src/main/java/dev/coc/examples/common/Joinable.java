package dev.coc.examples.common;

import java.util.LinkedList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Joinable {
	
	public List<JoinableCondition> joinableConditions = new LinkedList<>();
	
}
