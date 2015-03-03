package umk.zychu.inzynierka.repository;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;



import umk.zychu.inzynierka.model.User;

@Repository
public class UserDAOimp implements UserDAO{
	
	@PersistenceContext
	private EntityManager em;

	@Override
	@SuppressWarnings("unchecked")
	public User getUserByEmail(String email) {
		List<User> userList = new ArrayList<User>();
		Query query = em.createQuery("from User u where u.email = :email");
		query.setParameter("email", email);
		userList = query.getResultList();
		if (userList.size() > 0)
			return userList.get(0);
		else
			return null;
	}

	@Override
	public User getUserById(int id) {
		Query query = em.createQuery("from User u where u.id = :id");
		query.setParameter("id", id);
		return (User) query.getSingleResult();
	}

	@Override
	public void saveUser(User user) {
		if (user.isNew()) {
			em.persist(user);
		} else {
			em.merge(user);
		}
	}

	
	@Override
	@SuppressWarnings("unchecked")
	public List<User> getUserFriends(String email) {
		List<User> userFriends = new ArrayList<User>();
		Query query = em.createQuery("SELECT u.hasFriendCollection FROM User u where u.email = :uEmail");
		query.setParameter("uEmail", email);
		userFriends = query.getResultList();
		if(userFriends.size() > 0){
			return userFriends;
		}else{
			return null;
		}
	}
}
