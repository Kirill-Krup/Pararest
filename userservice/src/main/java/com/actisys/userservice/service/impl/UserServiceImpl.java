package com.actisys.userservice.service.impl;

import com.actisys.dto.CreateUserRequest;
import com.actisys.userservice.dto.UserDTO;
import com.actisys.userservice.exception.UserNotFoundException;
import com.actisys.userservice.mapper.UserMapper;
import com.actisys.userservice.model.User;
import com.actisys.userservice.model.UserRole;
import com.actisys.userservice.repository.UserRepository;
import com.actisys.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public void register(CreateUserRequest createUserRequest) {
        log.info("register user" + createUserRequest.getId());
        User user = User.builder()
                .id(createUserRequest.getId())
                .login(createUserRequest.getUser().getLogin())
                .email(createUserRequest.getUser().getEmail())
                .birthday(createUserRequest.getUser().getBirthday())
                .phone(createUserRequest.getUser().getPhone())
                .fullName(createUserRequest.getUser().getFullName())
                .role(UserRole.USER)
                .wallet(new BigDecimal(0))
                .roomNumber(null)
                .blocked(false)
                .build();
        userRepository.save(user);
    }

    @Override
    public UserDTO getInfo(UUID uuid) {
        User user = userRepository.findById(uuid).orElseThrow(()->new UserNotFoundException(uuid));
        return userMapper.toDto(user);
    }

    @Override
    public List<UserDTO> getAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toDto).collect(Collectors.toList());
    }
}
