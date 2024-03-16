package ro.fisa.ssm.structures;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Collections;

/**
 * Created at 3/16/2024 by Darius
 **/

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SingleAppCollection<T> {

    private Collection<T> content;
    private int size;

    public static <T> SingleAppCollection<T> fromCollection(Collection<T> collection) {

        return new SingleAppCollection<>(collection, collection.size());
    }

    public @SuppressWarnings("unused") Collection<T> getContent() {
        return Collections.unmodifiableCollection(this.content);
    }


}
