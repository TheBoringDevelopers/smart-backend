package ru.the_boring_developers.common.entity.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import ru.the_boring_developers.common.entity.image.Image;
import ru.the_boring_developers.common.entity.sport_type.dto.GetSportTypeDto;
import ru.the_boring_developers.common.entity.user.User;

import java.util.List;

@Setter
@Getter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class GetUserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private Long age;
    private String city;
    private String description;
    private List<GetSportTypeDto> sportTypes;
    private Image image;

    public static GetUserDto map(User user, List<GetSportTypeDto> sportTypes, Image image) {
        return GetUserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .patronymic(user.getPatronymic())
                .age(user.getAge())
                .city(user.getCity())
                .description(user.getDescription())
                .sportTypes(sportTypes)
                .image(image)
                .build();
    }
}
