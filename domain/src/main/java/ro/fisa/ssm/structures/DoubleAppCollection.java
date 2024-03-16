package ro.fisa.ssm.structures;

import lombok.Getter;

import java.util.Collection;

/**
 * Created at 3/16/2024 by Darius
 **/
@Getter
public class DoubleAppCollection<A, B> {
    private SingleAppCollection<A> first;
    private SingleAppCollection<B> second;

    public DoubleAppCollection(Collection<A> first, Collection<B> second) {
        this.first = SingleAppCollection.fromCollection(first);
        this.second = SingleAppCollection.fromCollection(second);
    }
}
