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

    public User getUser(Long userId) throws NoUserFoundException {
        Optional<User> userOpt = this.userRepository.findById(userId);
        if (userOpt.isPresent()) {
            return userOpt.get();
        }
        throw new NoUserFoundException(userId);
    }    

    public User createUser(User newUser) throws UserNameExistsException {
        Optional<User> userOpt = this.userRepository.findByUserName(newUser.getUserName());
        if (userOpt.isPresent()) {
            log.info(">> userOpt: {}", userOpt.get());
            throw new UserNameExistsException(newUser.getUserName());
        }
        return this.userRepository.save(newUser);
    }    
    
    public void deleteUser(Long userId) throws NoUserFoundException {
        this.getUser(userId);
        this.userRepository.deleteById(userId);
    }

    public void updateUser(Long userId, User updatedUser) {
        updatedUser.setUserId(userId);
        this.userRepository.save(updatedUser);
    }     
}
