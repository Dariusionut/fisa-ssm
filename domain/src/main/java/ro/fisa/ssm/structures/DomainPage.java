package ro.fisa.ssm.structures;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

/**
 * Created at 3/27/2024 by Darius
 **/
@Data
public class DomainPage<T> {
    private Collection<T> content;
    private long totalElements;
    private int numberOfElements;
    private int number;
    private int size;
    private long offset;
    private boolean isFirst;
    private boolean isLast;
    private boolean isEmpty;


    public static <P> DomainPage<P> fromPage(Page<P> page) {
        final Pageable pageable = page.getPageable();
        final DomainPage<P> domainPage = new DomainPage<>();
        domainPage.setContent(page.getContent());
        domainPage.setTotalElements(page.getTotalElements());
        domainPage.setNumberOfElements(page.getNumberOfElements());
        domainPage.setNumber(page.getNumber());
        domainPage.setSize(page.getSize());
        domainPage.setOffset(pageable.getOffset());
        domainPage.setFirst(page.isFirst());
        domainPage.setLast(page.isLast());
        domainPage.setEmpty(page.isEmpty());
        return domainPage;
    }

}
