package com.hangout.hangout.domain.user.service;
import com.hangout.hangout.domain.user.entity.User;
import com.hangout.hangout.domain.user.repository.UserRepository;
import com.hangout.hangout.global.config.AuthenticationFacade;
import com.hangout.hangout.global.error.ResponseType;
import com.hangout.hangout.global.exception.NotFoundException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

  private final UserRepository userRepository;
  private final AuthenticationFacade authenticationFacade;

  @Transactional
  public void updateNickname(String nickname) {
    User user = authenticationFacade.getCurrentUser();
    user.updateNickname(nickname);
  }

  @Transactional
  public void updateDescription(String description) {
    User user = authenticationFacade.getCurrentUser();
    user.updateDescription(description);
  }

  @Transactional
  public void updatePassword(String email, String exPassword, String newPassword) {
    User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException(ResponseType.USER_NOT_EXIST_EMAIL));
  }

  public Optional<User> findByEmail(String userEmail) {
    return userRepository.findByEmail(userEmail);
  }

  public User findById(Long id) {
    return userRepository.findById(id).orElseThrow(() -> new NotFoundException(ResponseType.USER_NOT_EXIST_ID));
  }
}
