package th.mfu.boot;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    
    public List<User> findAll();
    public User findByUsername(String username);
}