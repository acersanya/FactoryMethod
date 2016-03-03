package com.netcracker.dao;

import java.util.List;

import entitites.Department;
import entitites.Employee;
import entitites.Occuptation;
import entitites.Project;

/**
 * 
 * @author acersanya
 *	Interface for working with Employee object
 */

public abstract class EmployeeDao extends AbstartDao {

	public abstract boolean addEmp(Employee emp) throws DaoException;

	public abstract boolean setOnProject(int projectId, Employee emp);

	public abstract List<Project> getProjects(Employee emp);
	
	public abstract int getEmpId(Employee emp);

	public abstract Employee getEmpById(int id);
	
	public abstract boolean removeEmpById(int id);
	
	public abstract Employee getByName(String name) throws DaoException;
	
	public abstract List<Employee> getStuff();
	
}
