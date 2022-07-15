package ru.the_boring_developers.common.repository.user_team_link;

import ru.the_boring_developers.common.entity.user.link.UserTeamLink;

import java.util.List;

public interface UserTeamLinkRepository {

    void create(UserTeamLink userTeamLink);

    void delete(Long userId, Long teamId);

    UserTeamLink find(Long userId, Long teamId);

    List<UserTeamLink> find(Long teamId);

    void setLeader(Long teamId, Long userId);
}
