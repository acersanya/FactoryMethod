package com.netcracker.dao;
import java.util.List;

import entitites.Customer;
import entitites.Employee;
import entitites.Project;
/**
 * 
 * @author acersanya
 *Interface for working with Project object
 */

public abstract class ProjectDao {

	public abstract boolean addProject(Employee manager, Customer client, Integer price) throws DaoException;
	public abstract Project getByID (int id);
	public abstract Integer getId(int headID, int price, int customerID);
	public abstract List<Integer> getProjectsID();
	public abstract List<Project> getProjects();
	public abstract List<Employee> getStuffOnProject(int projectID);
}
