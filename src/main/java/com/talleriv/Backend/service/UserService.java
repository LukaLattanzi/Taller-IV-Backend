package com.talleriv.Backend.service;

import com.talleriv.Backend.dto.LoginRequest;
import com.talleriv.Backend.dto.RegisterRequest;
import com.talleriv.Backend.dto.Response;
import com.talleriv.Backend.dto.UserDTO;
import com.talleriv.Backend.entity.User;

public interface UserService {
    Response registerUser(RegisterRequest registerRequest);
    Response loginUser(LoginRequest loginRequest);
    Response getAllUsers();
    User getCurrentLoggedInUser();
    Response updateUser(Long id, UserDTO userDTO);
    Response deleteUser(Long id);
    Response getUserTransactions(Long id);
}