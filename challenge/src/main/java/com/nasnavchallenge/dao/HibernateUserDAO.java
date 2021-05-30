package com.nasnavchallenge.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nasnavchallenge.model.AppUser;

@Transactional
@Repository("hibernateUserDAO")
public class HibernateUserDAO implements UserDAO {
	private static final String SELECTALLUSERS = "from AppUser";
	private static final String FINDBYUSERNAME="from AppUser where userName=:username";
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	@Resource(name="sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public AppUser saveUser(AppUser user) {
		 this.sessionFactory.getCurrentSession().save(user);
		 return user;

	}

	public void deleteUser(AppUser user) {
		this.sessionFactory.getCurrentSession().delete(user);

	}

	@SuppressWarnings("unchecked")
	public List<AppUser> getAllUsers() {
		return this.sessionFactory.getCurrentSession().createQuery(SELECTALLUSERS).list();

	}

	public AppUser updateUser(AppUser user) {
		 this.sessionFactory.getCurrentSession().saveOrUpdate(user);
		return user;
       
	}

	@Transactional(readOnly = true)
	public AppUser findByID(long id) {
		
		AppUser user=this.sessionFactory.getCurrentSession().get(AppUser.class, id);
	    return user;

	}
	@Override
	public AppUser findByUsername(String username) {
	 AppUser user=(AppUser)this.sessionFactory.getCurrentSession().createQuery(FINDBYUSERNAME).setParameter("username", username).uniqueResult();
	
	 return user;
	}
}
