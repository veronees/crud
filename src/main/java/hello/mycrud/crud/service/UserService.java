package hello.mycrud.crud.service;

import hello.mycrud.crud.domain.entity.Gender;
import hello.mycrud.crud.domain.entity.User;
import hello.mycrud.crud.domain.requestdto.UserRequestDto;
import hello.mycrud.crud.domain.responsedto.UserResponseDto;
import hello.mycrud.crud.repository.UserRepository;
import hello.mycrud.crud.repository.jparepository.UserJpaRepository;
import hello.mycrud.crud.util.UserEntityDtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserJpaRepository userJpaRepository;
    private final UserEntityDtoConverter userEntityDtoConverter;

    private final UserRepository userRepository;

    //회원가입
    @Transactional
    public boolean join(UserRequestDto userRequestDto) {
        //username(아이디) 중복 체크
        Optional<User> findByUsername = userJpaRepository.findByUsername(userRequestDto.getUsername());

        if (findByUsername.isPresent()) {
            return false;
        }

        User user = userEntityDtoConverter.convertToEntity(userRequestDto);
        userJpaRepository.save(user);
        return true;
    }

    public boolean joinV2(UserRequestDto userRequestDto) {
        Optional<User> findUser = userRepository.findByUsername(userRequestDto.getUsername());
        if (findUser.isPresent()) {
            return false;
        }

        User user = userEntityDtoConverter.convertToEntity(userRequestDto);
        userRepository.save(user);
        return true;
    }



    // user 정보 수정
    @Transactional
    public UserResponseDto modified(Long userId, String name, String nickname, Gender gender) {
        User findUser = userJpaRepository.findOne(userId);

        findUser.changeInfo(name, nickname, gender);

        return userEntityDtoConverter.convertToDto(findUser);

//        return UserResponseDto.builder()
//                    .username(findUser.getUsername())
//                    .nickname(findUser.getNickname())
//                    .gender(findUser.getGender())
//                    .build();
    }

    public UserResponseDto modifiedV2(Long userId, String name, String nickname, Gender gender) {
        User findUser = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("유저 x"));
        findUser.changeInfo(name, nickname, gender);
        return userEntityDtoConverter.convertToDto(findUser);

    }


    //user 한명 조회
    public UserResponseDto findOneUser(Long userId) {
        User findUser = userJpaRepository.findOne(userId);
        UserResponseDto userResponseDto = userEntityDtoConverter.convertToDto(findUser);

        return userResponseDto;
    }

    public UserResponseDto findOneUserV2(Long userId) {
        User findUser = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("유저 x"));
        return userEntityDtoConverter.convertToDto(findUser);
    }

    //user 전부 조회
    public List<UserResponseDto> findAllUsers() {
        List<User> findAll = userJpaRepository.findAll();
        List<UserResponseDto> userResponseDtoList = new ArrayList<>();
        for (User user : findAll) {
            UserResponseDto userResponseDto = userEntityDtoConverter.convertToDto(user);
            userResponseDtoList.add(userResponseDto);
        }

        return userResponseDtoList;
    }

    public List<UserResponseDto> findAllUsersV2() {
        List<User> findAllUsers = userRepository.findUserAll();
        List<UserResponseDto> userResponseDtoList = new ArrayList<>();

        for (User user : findAllUsers) {
            UserResponseDto userResponseDto = userEntityDtoConverter.convertToDto(user);
            userResponseDtoList.add(userResponseDto);
        }
        return userResponseDtoList;
    }

    //user 디비에서 삭제
    @Transactional
    public void deleteUser(Long userId) {
        userJpaRepository.delete(userId);
    }

    public void deleteUserV2(Long userId) {
        userRepository.deleteById(userId);
    }
}
