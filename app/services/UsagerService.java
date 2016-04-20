/**
 * 
 */
package services;

import java.util.List;
import models.Usager;
import security.PermissionLevel;

/**
 * @author Eva
 *
 */
public interface UsagerService {
	
	public PermissionLevel getUserLevel(String emailAddress);
		
	public Usager findByEmailAddressAndPassword(String emailAddress, String password);
	
	public List<Usager> findAll();
	
	public Usager findById(int id);
	
	public boolean add(Usager user);
	
	public boolean update(Usager user);
	
	public boolean remove(int id);
}
