//implementation of User service interface, providing logic for a couple of methods for accessing User data
package io.github.miwlodar.service;

import io.github.miwlodar.dao.RolesRepository;
import io.github.miwlodar.dao.UserRepository;
import io.github.miwlodar.entity.Role;
import io.github.miwlodar.entity.User;
import io.github.miwlodar.user.CreateUserDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RolesRepository rolesRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RolesRepository rolesRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByUserName(String userName) {
        // checking the database if the user already exists
        return userRepository.findByUserName(userName);
    }

    @Override
    public void save(CreateUserDto createUserDto) {
        User user = new User();

        user.setUserName(createUserDto.getUserName());
        user.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
        user.setFirstName(createUserDto.getFirstName());
        user.setLastName(createUserDto.getLastName());
        user.setEmail(createUserDto.getEmail());

        // giving user default role of "user"
        user.setRoles(Set.of(rolesRepository.findByName("ROLE_USER").get()));

        userRepository.save(user);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(userName);

        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
