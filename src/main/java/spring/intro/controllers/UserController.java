package spring.intro.controllers;

import java.util.List;
import java.util.stream.Collectors;
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
        return mapper.map(userService.getById(userId), UserResponseDto.class);
    }

    @GetMapping
    public List<UserResponseDto> getAll() {
        return userService.listUsers().stream()
                .map(user -> mapper.map(user, UserResponseDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/inject")
    public String injectUsers() {
        User roman = userService.add(new User("Roman", "Hreits", "roma@meta.ua"));
        User bob = userService.add(new User("Bob", "Bobby", "bob@gmail.com"));
        return "Created users: " + roman + " and " + bob;
    }
}
