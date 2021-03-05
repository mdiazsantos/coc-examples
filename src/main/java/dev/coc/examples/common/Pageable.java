package dev.coc.examples.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pageable {
	
	public Integer page = 0;
	public Integer size = Integer.MAX_VALUE;
	
	public static Integer getOffset(Integer page, Integer size) {
		return page * size;
	}
}
