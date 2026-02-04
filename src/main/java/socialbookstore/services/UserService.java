package socialbookstore.services;

import org.springframework.stereotype.Service;

import socialbookstore.domainmodel.User;


@Service
public interface UserService {
	public void saveUser(User user);
    public boolean isUserPresent(User user);
}