package neeapaste;


import org.springframework.data.domain.*;
import org.springframework.data.repository.*;

import java.util.List;

/**
 * Created by neeasade on 9/22/15.
 */
public interface PasteRepository extends CrudRepository<Paste,Long> {

    List<Paste> findAll();

    Paste findOne(Long id);

    long count();

    void delete(Paste entity);

    boolean exists(Long primaryKey);
}
