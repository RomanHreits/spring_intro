package spring.intro.controllers;

import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.intro.dto.UserResponseDto;
import spring.intro.model.User;
import spring.intro.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    private final ModelMapper mapper;

    public UserController(UserService userService, ModelMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping("/{userId}")
    public UserResponseDto get(@PathVariable("userId") Long userId) {
        return convertToDto(userService.getById(userId));
    }

    @GetMapping
    public List<UserResponseDto> getAll() {
        List<User> users = userService.listUsers();
        List<UserResponseDto> userResponseDtoList = new ArrayList<>();
        for (User user: users) {
            userResponseDtoList.add(convertToDto(user));
        }
        return userResponseDtoList;
    }

    @GetMapping("/inject")
    public String injectUsers() {
        return "Created users: "
                + userService.add(new User("Roman", "Hreits", "roma@meta.ua"))
                + " and " + userService.add(new User("Bob", "Bobby", "bob@gmail.com"));
    }

    private UserResponseDto convertToDto(User user) {
        return mapper.map(user, UserResponseDto.class);
    }
}
