package com.netcracker.dao.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.netcracker.connection.JdbcConnectionPool;
import com.netcracker.dao.AbstartDao;
import com.netcracker.dao.DaoException;
import com.netcracker.dao.ProjectDao;
import entitites.Customer;
import entitites.Employee;
import entitites.HeadStatus;
import entitites.Project;
/**
 * 
 * @author acersanya
 *	Project Dao implementation
 */

public class ProjectDaoImpl extends ProjectDao {

	private static final String SQL_QUERY_GET_PROJ_BY_ID = "SELECT Projects.price, Customers.name, Projects.HeadEmpID, Projects.ProjectID  FROM Projects JOIN Customers ON Projects.CustomerID = Customers.id WHERE ProjectID = ?";
	private static final String SQL_QUERY_ADD_PROJECT = "INSERT INTO Projects (HeadEmpID, price,CustomerId) values (?,?,?)";
	private static final String EXCEPTION_WARNING = "Should be project head";
	private static final String EXCEPTION_NO_SUCH_USER = "No such user or client in db";
	private static final String SQL_QUERY_INSERT_INTO_EMP_RPOJECTS = "INSERT  INTO EmpProjects (EmpID,ProjectID) values (?,?)";
	private static final String SQL_QUERY_GET_PROJECT_ID = "SELECT ProjectID FROM Projects WHERE HeadEmpID = ? AND price = ? AND CustomerID =?";
	private static final String SQL_QUERY_GET_ALL_PROJECTS_ID = "SELECT ProjectID FROM Projects";
	private static final String SQL_QUERY_GET_ALL_PROJECTS = "SELECT * FROM Projects;";
	private static final String SQL_QUERY_GET_EMP_ON_PROJECT ="SELECT EmpID from EmpProjects WHERE ProjectID = ?";

	JdbcConnectionPool pool = new JdbcConnectionPool();
	private Connection connection;
	private PreparedStatement preparedStatement;
	private boolean flag;
	
	/**
	 * initialize the connection object
	 */
	public void init() {
		connection = null;
		preparedStatement = null;
		flag = false;
	}

	/**
	 * Create new project 
	 * If Employee is not manager DaoException will be thrown
	 */
	@Override
	public boolean addProject(Employee head, Customer client, Integer price) throws DaoException {
		init();
		try {
			connection = JdbcConnectionPool.getConnectionFromPool();
			preparedStatement = connection.prepareStatement(SQL_QUERY_ADD_PROJECT);
			EmployeeDaoImpl empDao = new EmployeeDaoImpl();
			CustomerDaoImpl cDao = new CustomerDaoImpl();
			
			if(!empDao.ifExist(head)){
				empDao.addEmp(head);
			}
			Employee emp = empDao.getByName(head.getName());
			if (!emp.getStatus().equals(HeadStatus.TRUE)) {
				throw new DaoException(EXCEPTION_WARNING);
			}
			Integer empId = emp.getId();

			if (!cDao.isExist(client)) {
				cDao.addCustomer(client);
			}
			Integer cstId = cDao.getId(client);

			if (empId.equals(null) || cstId.equals(null)) {
				throw new DaoException(EXCEPTION_NO_SUCH_USER);
			}

		
			preparedStatement.setInt(1, empId);
			preparedStatement.setInt(2, price);
			preparedStatement.setInt(3, cstId);
			preparedStatement.executeUpdate();
			
			Integer projectID = getId(empId,price,cstId);
		

			preparedStatement = connection.prepareStatement(SQL_QUERY_INSERT_INTO_EMP_RPOJECTS);
			preparedStatement.setInt(1, empId);
			preparedStatement.setInt(2, projectID);
			preparedStatement.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			AbstartDao.close(preparedStatement);
			JdbcConnectionPool.returnConnection(connection);
			flag = false;
		}
		return flag;
	}

	/**
	 * Get project by id
	 */
	@Override
	public Project getByID(int id) {
		init();
		Project proj = new Project();
		try {
			connection = JdbcConnectionPool.getConnectionFromPool();
			preparedStatement = connection.prepareStatement(SQL_QUERY_GET_PROJ_BY_ID);
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()){
			proj.setPrice(rs.getInt(1));
			proj.setClient(new Customer(rs.getString(2)));	
			proj.setManager(new EmployeeDaoImpl().getEmpById(rs.getInt(3)));
			proj.setId(rs.getInt(4));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			AbstartDao.close(preparedStatement);
			JdbcConnectionPool.returnConnection(connection);
		}
		return proj;
	}

	/**
	 * Get project id
	 */
	@Override
	public Integer getId(int headID, int price, int customerID) {
		init();
		Integer id = null;
		try{
			connection = JdbcConnectionPool.getConnectionFromPool();
			preparedStatement = connection.prepareStatement(SQL_QUERY_GET_PROJECT_ID);
			preparedStatement.setInt(1, headID);
			preparedStatement.setInt(2, price);
			preparedStatement.setInt(3, customerID);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()){
				id = rs.getInt(1);
			}
		} catch (SQLException e){
			e.printStackTrace();
		} finally {
			AbstartDao.close(preparedStatement);
			JdbcConnectionPool.returnConnection(connection);
		}
		return id;
	}

	/**
	 * Get all current projects id
	 */
	@Override
	public List<Integer> getProjectsID() {
		List<Integer> projectID = new ArrayList<>();
		init();
		try{
			connection = JdbcConnectionPool.getConnectionFromPool();
			preparedStatement = connection.prepareStatement(SQL_QUERY_GET_ALL_PROJECTS_ID);
			ResultSet rs= preparedStatement.executeQuery();
			while(rs.next()){
				projectID.add(rs.getInt(1));
			}
		} catch (SQLException e){
			e.printStackTrace();
		} finally {
			 AbstartDao.close(preparedStatement);
			 JdbcConnectionPool.returnConnection(connection);
		}
		return projectID;
	}

	/**
	 * Get all projects
	 */
	@Override
	public List<Project> getProjects() {
		List<Project> projects = new ArrayList<>();
		init();
		try{
			connection = JdbcConnectionPool.getConnectionFromPool();
			preparedStatement = connection.prepareStatement(SQL_QUERY_GET_ALL_PROJECTS);
			ResultSet rs = preparedStatement.executeQuery();
			Project project = null;
			while(rs.next()){
				project = new Project();
				Integer projectID = rs.getInt(1);
				project.setId(projectID);
				if(rs.getInt(2) == 0){
					project.setManager(new Employee());
				} else {
					project.setManager(new EmployeeDaoImpl().getEmpById(rs.getInt(2)));
				}
				project.setPrice(rs.getInt(3));
				project.setClient(new CustomerDaoImpl().getByID(rs.getInt(4)));
				project.setTeam(getStuffOnProject(projectID));
				projects.add(project);
			}
		} catch (SQLException e){
			e.printStackTrace();
		} finally {
			AbstartDao.close(preparedStatement);
			JdbcConnectionPool.returnConnection(connection);
		}
		return projects;
	}

	/**
	 * Get all stuff working on one Project
	 */
	@Override
	public List<Employee> getStuffOnProject(int projectID) {
		List<Employee> projects = new ArrayList<>();
		init();
		Employee emp = new Employee();
		try{
			connection = JdbcConnectionPool.getConnectionFromPool();
			preparedStatement = connection.prepareStatement(SQL_QUERY_GET_EMP_ON_PROJECT);
			preparedStatement.setInt(1, projectID);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()){
				emp = new EmployeeDaoImpl().getEmpById(rs.getInt(1));
				projects.add(emp);
			}
		} catch (SQLException e){
			e.printStackTrace();
		}
		return projects;
	}
}
