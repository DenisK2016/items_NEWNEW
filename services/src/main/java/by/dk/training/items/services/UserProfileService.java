package by.dk.training.items.services;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import by.dk.training.items.dataaccess.filters.UserFilter;
import by.dk.training.items.datamodel.UserCredentials;
import by.dk.training.items.datamodel.UserProfile;

public interface UserProfileService {

	@Transactional
	void registerUser(UserProfile user, UserCredentials userCredentials);

	UserProfile getUser(Long id);

	UserCredentials getUserCredentials(Long id);

	@Transactional
	void updateUserProfile(UserProfile user);

	@Transactional
	void updateUserCredentials(UserCredentials userCredentials);

	@Transactional
	void deleteUserWithId(Long id);

	List<UserProfile> findUser(UserFilter userFilter);

	List<UserProfile> getAll();

	Long overallNumberOfUsers(UserFilter filter);

	UserProfile getUserByNameAndPassword(String login, String password);
	
	Collection<? extends String> resolveRoles(Long id);

}
