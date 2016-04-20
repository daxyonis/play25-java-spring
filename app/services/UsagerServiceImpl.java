/**
 * 
 */
package services;

import java.util.List;

import models.Usager;
import services.dao.UsagerDAO;
import security.PermissionLevel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Eva
 *
 */
@Service
public class UsagerServiceImpl implements UsagerService {
	
	private UsagerDAO usagerDAO;
	
	@Autowired
	public UsagerServiceImpl(UsagerDAO usagerDAO){
		this.usagerDAO = usagerDAO;
	}

	/* (non-Javadoc)
	 * @see services.UsagerService#findByEmailAddressAndPassword(java.lang.String, java.lang.String)
	 */
	public Usager findByEmailAddressAndPassword(String emailAddress,
			String password) {
		return usagerDAO.findByEmailAddressAndPassword(emailAddress, password);
	}
	
	public PermissionLevel getUserLevel(String emailAddress){
		return usagerDAO.getUserLevel(emailAddress);
	}

	@Override
	public List<Usager> findAll() {
		return usagerDAO.findAll();
	}

	@Override
	public Usager findById(int id) {
		return usagerDAO.findById(id);
	}

	@Override
	public boolean add(Usager user) {
		return usagerDAO.add(user);
	}

	@Override
	public boolean update(Usager user) {
		return usagerDAO.update(user);
	}

	@Override
	public boolean remove(int id) {
		return usagerDAO.remove(id);
	}

}
