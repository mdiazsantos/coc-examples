package dev.coc.examples.persistence.model;

import java.io.Serializable;

import dev.coc.common.mapper.filter.AbstractFilter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Person filter
 * @author Mario Diaz
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonFilter extends AbstractFilter implements Serializable {
	
	private static final long serialVersionUID = -8921566229483782840L;
	
	private Long id;

}
