package ro.fisa.ssm.structures;

import lombok.Data;

import java.util.Collection;

/**
 * Created at 3/27/2024 by Darius
 **/
@Data
public class DomainPage<T> {
    private Collection<T> content;
    private int totalElements;
    private int numberOfElements;
    private int number;
    private int size;
    private int offset;
    private boolean isFirst;
    private boolean isLast;
    private boolean isEmpty;


}
