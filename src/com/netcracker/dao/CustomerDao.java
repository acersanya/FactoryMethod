package com.netcracker.dao;

import java.util.List;

import entitites.Customer;
import entitites.Project;

/**
 * 
 * @author acersanya
 *	All necessary methods to work with Customer Object
 *	is defined here.
 */

public abstract class CustomerDao extends AbstartDao {

	public abstract boolean addCustomer(Customer customer);

	public abstract Customer getCustomer(Customer customer);

	public abstract boolean removeCustomer(Customer customer);

	public abstract boolean addProject(Project project);
	
	public abstract List <Project> getProjects(Customer customer);
	
	public abstract Integer getId(Customer customer);
	
	public abstract boolean isExist(Customer customer);
	
	public abstract Customer getByID(int id);
	
}
