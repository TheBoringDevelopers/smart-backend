package ru.the_boring_developers.api.service.sport_type;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.the_boring_developers.common.entity.sport_type.SportType;
import ru.the_boring_developers.common.entity.sport_type.dto.CreateSportTypeDto;
import ru.the_boring_developers.common.entity.sport_type.dto.GetSportTypeDto;
import ru.the_boring_developers.common.entity.sport_type.link.SportTypeImageLink;
import ru.the_boring_developers.common.repository.image.ImageRepository;
import ru.the_boring_developers.common.repository.sport_type.SportTypeRepository;
import ru.the_boring_developers.common.repository.sport_type_image_link.SportTypeImageLinkRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SportTypeServiceImpl implements SportTypeService {

    private final SportTypeRepository sportTypeRepository;
    private final SportTypeImageLinkRepository sportTypeImageLinkRepository;
    private final ImageRepository imageRepository;

    @Override
    public List<GetSportTypeDto> findByUserId(Long userId) {
        return collectDto(sportTypeRepository.findByUserId(userId));
    }

    @Override
    public List<GetSportTypeDto> findByTeamId(Long teamId) {
        return collectDto(sportTypeRepository.findByTeamId(teamId));
    }

    @Override
    public List<GetSportTypeDto> findAll() {
        return collectDto(sportTypeRepository.findAll());
    }

    @Override
    public void create(CreateSportTypeDto createSportTypeDto) {
        Long sportTypeId = sportTypeRepository.create(createSportTypeDto.getSportType());
        sportTypeImageLinkRepository.create(
                SportTypeImageLink.builder()
                        .sportTypeId(sportTypeId)
                        .imageId(createSportTypeDto.getImageId())
                        .build()
        );
    }

    private List<GetSportTypeDto> collectDto(List<SportType> sportTypes) {
        return sportTypes.stream()
                .map(sportType -> GetSportTypeDto.builder()
                        .id(sportType.getId())
                        .name(sportType.getName())
                        .image(imageRepository.findBySportTypeId(sportType.getId()))
                        .build())
                .collect(Collectors.toList());
    }
}
