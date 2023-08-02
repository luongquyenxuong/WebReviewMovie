package com.example.mock_project.service.impl;

import com.example.mock_project.constant.ConstantUtils;
import com.example.mock_project.constant.Role;
import com.example.mock_project.dto.BaseResponse;
import com.example.mock_project.dto.UserDto;
import com.example.mock_project.entity.RoleEntity;
import com.example.mock_project.entity.UserEntity;
import com.example.mock_project.exception.BaseException;
import com.example.mock_project.mapper.UserMapper;
import com.example.mock_project.repository.UserRepository;
import com.example.mock_project.security.CustomUserDetails;
import com.example.mock_project.security.JwtTokenUtil;
import com.example.mock_project.service.UserService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserMapper userMapper;

    @Override
    public BaseResponse<Void> saveUser(UserEntity user) {
        Optional<UserEntity> userOtp = userRepository.findByUsername(user.getUsername());
        if (userOtp.isPresent()) {
            return BaseResponse.<Void>builder()
                    .status("99")
                    .message("Username already exist")
                    .build();
        }

        Set<RoleEntity> roleEntities = new HashSet<>();
        roleEntities.add(RoleEntity.builder().id(2L).name("ROLE_USER").build());
        user.setRoles(roleEntities);

        // password: 123456 => goidfnckjnsiodkskl43mfslfdkmdkmfdl
        String passwordEncoder = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
        user.setPassword(passwordEncoder);

        userRepository.save(user);

        return BaseResponse.<Void>builder()
                .status("00")
                .message("Register successful")
                .build();
    }

    @Override
    public BaseResponse<List<String>> login(String username, String password) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
            //set thong tin authentication vào Security Context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            List<String> dataTokenAndRole = new ArrayList<>();

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            Optional<? extends GrantedAuthority> role=userDetails.getAuthorities().stream().findFirst();

            if (role.isEmpty()) {
                throw new BaseException(ConstantUtils.BASERESPONSE_STATUS_FAILDED,"Not found role user");
            }

            // Gen token
            String token = jwtTokenUtil.generateToken((UserDetails) authentication.getPrincipal());
            dataTokenAndRole.add(token);
            dataTokenAndRole.add(role.get().toString());
            dataTokenAndRole.add(userDetails.getUser().getFullname());

            return BaseResponse.<List<String>>builder()
                    .status("00")
                    .message("Login successful")
                    .data(dataTokenAndRole)
                    .build();
        } catch (BadCredentialsException e) {
            log.error(e.getMessage(), e);
            log.error("Tài khoản hoặc mật khẩu không chính xác!");
            return BaseResponse.<List<String>>builder()
                    .status("99")
                    .message("Username or password is incorrect")
                    .build();
        }
    }

    @Override
    public List<UserDto> getAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .filter(dto -> dto.getRoleName().equals(Role.USER.name()))
                .collect(Collectors.toList());
    }

    @Override
    public BaseResponse<UserEntity> getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal().equals("anonymousUser")) {
            return BaseResponse.<UserEntity>builder().status("99").message("Not find user").build();
        }

        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        Optional<UserEntity> userEntity = userRepository.findById(user.getUser().getId());
        if (userEntity.isEmpty()) {
            throw new BaseException(ConstantUtils.BASERESPONSE_STATUS_FAILDED,"Not found user");
        }

        return BaseResponse.<UserEntity>builder().status("00").message("Find user successfully").data(userEntity.get()).build();

    }

    @Override
    public BaseResponse<Void> deleted(Long id) {
        userRepository.deleteById(id);
        return BaseResponse.<Void>builder().status("00").message("Deleted user successfully").build();
    }

}
