package com.actisys.userservice.service;

import com.actisys.dto.CreateUserRequest;
import com.actisys.kafkacommunication.wallet.CreateWalletEvent;
import com.actisys.kafkacommunication.wallet.RefundMoneyEvent;
import com.actisys.userservice.dto.UserDTO;
import com.actisys.userservice.model.UserRole;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public interface UserService {
    void register(CreateUserRequest createUserRequest);

    UserDTO getInfo(UUID uuid);

    List<UserDTO> getAll();

    UserDTO changeRole(UUID userId, UserRole role);

    UserDTO handleAccess(UUID userId);

    void deleteUser(UUID userId);

    void updateUserRoom(UUID uuid, int room);

    void topUp(CreateWalletEvent event);

    void withdrawMoney(CreateWalletEvent event);

    void refundMoneys(RefundMoneyEvent event);
}
