package ns.maintainusers.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ns.maintainusers.controller.error.NoUserFoundException;
import ns.maintainusers.controller.error.UserNameExistsException;
import ns.maintainusers.entity.User;
import ns.maintainusers.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
 
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public User getUser(Long id) throws NoUserFoundException {
        Optional<User> userOpt = this.userRepository.findById(id);
        if (userOpt.isPresent()) {
            return userOpt.get();
        }
        throw new NoUserFoundException(id);
    }    

    public User createUser(User newUser) throws UserNameExistsException {
        Optional<User> userOpt = this.userRepository.findByName(newUser.getName());
        if (userOpt.isPresent()) {
            throw new UserNameExistsException(newUser.getName());
        }
        return this.userRepository.save(newUser);
    }    
    
    public void deleteUser(Long id) throws NoUserFoundException {
        this.getUser(id);
        this.userRepository.deleteById(id);
    }

    public void updateUser(Long id, User updatedUser) {
        updatedUser.setId(id);
        this.userRepository.save(updatedUser);
    }     
}
