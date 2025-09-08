package th.mfu.boot;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    public UserRepository repo;
   
    @PostMapping("/users")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        // check if user with the username exists
        User existingUser = repo.findByUsername(user.getUsername());
        if (existingUser != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        
        // save the user
        repo.save(user);
        
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username) {
        User user = repo.findByUsername(username);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> list() {
        List<User> users = repo.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        // check if user with the id exists
        Optional<User> user = repo.findById(id);
        if (!user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        // delete the user
        repo.deleteById(id);
    
        return new ResponseEntity<>(HttpStatus.OK);
    }
}