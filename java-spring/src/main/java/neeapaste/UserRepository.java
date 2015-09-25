package neeapaste;


import org.springframework.data.domain.*;
import org.springframework.data.repository.*;

import java.util.List;

/**
 * Created by neeasade on 9/22/15.
 */
public interface UserRepository extends CrudRepository<User,Long> {
    List<User> findAll();

    User findOne(Long id);

    User findByUsername(String username);

    long count();

    void delete(User entity);

    boolean exists(Long primaryKey);
}
