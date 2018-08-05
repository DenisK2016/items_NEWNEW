package by.dk.training.items.dataaccess;

import java.util.List;

import by.dk.training.items.dataaccess.filters.UserFilter;
import by.dk.training.items.datamodel.UserProfile;

public interface UserProfileDao extends AbstractDao<UserProfile, Long> {
	
	Long overallNumberOfUsers(UserFilter filter);

	List<UserProfile> findUser(UserFilter filter);
	
	UserProfile findUserByLoginAndPassword(String login, String password);
}
