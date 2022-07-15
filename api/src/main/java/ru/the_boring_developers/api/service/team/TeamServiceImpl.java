package ru.the_boring_developers.api.service.team;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.the_boring_developers.api.service.sport_type.SportTypeService;
import ru.the_boring_developers.api.service.user.UserService;
import ru.the_boring_developers.common.entity.notification.Notification;
import ru.the_boring_developers.common.entity.notification.NotificationType;
import ru.the_boring_developers.common.entity.sport_type.SportType;
import ru.the_boring_developers.common.entity.team.Team;
import ru.the_boring_developers.common.entity.team.dto.CreateTeamDto;
import ru.the_boring_developers.common.entity.team.dto.GetTeamDto;
import ru.the_boring_developers.common.entity.team.link.TeamSportTypeLink;
import ru.the_boring_developers.common.entity.user.link.UserTeamLink;
import ru.the_boring_developers.common.repository.notification.NotificationRepository;
import ru.the_boring_developers.common.repository.sport_type.SportTypeRepository;
import ru.the_boring_developers.common.repository.team.TeamRepository;
import ru.the_boring_developers.common.repository.team_sport_type.TeamSportTypeLinkRepository;
import ru.the_boring_developers.common.repository.user_team_link.UserTeamLinkRepository;
import ru.the_boring_developers.common.utils.DateUtil;
import ru.the_boring_developers.common.utils.Utils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final SportTypeRepository sportTypeRepository;
    private final TeamSportTypeLinkRepository teamSportTypeLinkRepository;
    private final UserTeamLinkRepository userTeamLinkRepository;
    private final NotificationRepository notificationRepository;

    private final UserService userService;
    private final SportTypeService sportTypeService;

    @Override
    public GetTeamDto find(Long userId) {
        Team team = teamRepository.findByUserId(userId);
        return collectDto(team);
    }

    @Override
    public List<GetTeamDto> find(Long userId, List<Long> sportTypes, Boolean empty) {
        return teamRepository.find(sportTypes, empty)
                .stream()
                .map(this::collectDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long userId, Long teamId) {
        teamRepository.delete(userId, teamId);
    }

    @Override
    public Long create(Long userId, CreateTeamDto createTeamDto) {
        Long teamId = teamRepository.create(createTeamDto.getTeam());
        userTeamLinkRepository.create(
                UserTeamLink.builder()
                        .teamId(teamId)
                        .userId(userId)
                        .build()
        );
        for (SportType sportType : createTeamDto.getSportTypes()) {
            teamSportTypeLinkRepository.create(
                    TeamSportTypeLink.builder()
                            .teamId(teamId)
                            .sportTypeId(sportTypeRepository.findByName(sportType.getName()).getId())
                            .build()
            );
        }
        return teamId;
    }

    @Override
    public void delete(Long userId, Long requestedUserId, Long teamId) {
        teamRepository.delete(requestedUserId, teamId);
    }

    @Override
    public void leave(Long userId, Long teamId) {
        UserTeamLink userTeamLink = userTeamLinkRepository.find(userId, teamId);
        if (userTeamLink == null) {
            return;
        }
        if (userTeamLink.isLeader()) {
            UserTeamLink anyUserTeamLink = userTeamLinkRepository.find(teamId).stream().findAny().orElse(null);
            if (anyUserTeamLink == null) {
                teamRepository.delete(teamId);
            }
            userTeamLinkRepository.setLeader(teamId, userId);
        }
        userTeamLinkRepository.delete(userId, teamId);
    }

    @Override
    public void invite(Long userId, Long teamId, Long requestedUserId) {
        notificationRepository.create(
                Notification.builder()
                        .userId(requestedUserId)
                        .created(DateUtil.now())
                        .type(NotificationType.INVITE)
                        .name("Приглашение в команду")
                        .description(Utils.convertToJson(Team.builder().id(teamId)))
                .build());
    }

    private GetTeamDto collectDto(Team team) {
        return GetTeamDto.builder()
                .team(team)
                .sportTypes(sportTypeService.findByTeamId(team.getId()))
                .users(userService.findByTeamId(team.getId()))
                .build();
    }
}
