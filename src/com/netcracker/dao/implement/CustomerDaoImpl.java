package com.netcracker.dao.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.netcracker.connection.ConnectionPool;
import com.netcracker.connection.JdbcConnectionPool;
import com.netcracker.dao.AbstartDao;
import com.netcracker.dao.CustomerDao;
import com.netcracker.dao.EmployeeDao;

import entitites.Customer;
import entitites.Project;
/**
 * 
 * @author acersanya
 *	Dao implementation for Customer
 */

public class CustomerDaoImpl extends CustomerDao {

	private static final String SQL_QUERY_ADD_CUSTOMER = "INSERT INTO Customers(name) values (?)";
	private static final String SQL_QUERY_GET_CUSTOMER_BY_ID = "SELECT * FROM Customers where id = ?";
	private static final String SQL_QUERY_GET_CUSTOMER_BY_NAME ="SELECT id FROM Customers Where name =?";
	private static final String SQL_GET_PROJECTS = "SELECT * FROM Projects where CustomerID = ?";
	private static final String DAO_EXCEPTION = "Dao exception";
	final static Logger logger = Logger.getLogger(CustomerDaoImpl.class);
	
	static JdbcConnectionPool pool = new JdbcConnectionPool();
	private Connection connection = null;
	private PreparedStatement preparedStatement = null;
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
	 * Adds the customer to DB
	 */
	@Override
	public boolean addCustomer(Customer customer) {
		init();
		try {
			connection = JdbcConnectionPool.getConnectionFromPool();
			preparedStatement = connection.prepareStatement(SQL_QUERY_ADD_CUSTOMER);
			preparedStatement.setString(1, customer.getName());
			preparedStatement.executeUpdate();
			flag = true;
		} catch (SQLException e) {
			logger.error(DAO_EXCEPTION, e);
			e.printStackTrace();
		} finally {
			AbstartDao.close(preparedStatement);
			JdbcConnectionPool.returnConnection(connection);
			flag = false;
		}
		return flag;
	}

	/**
	 * Get the customer and all his projects from db
	 */
	@Override
	public Customer getCustomer(Customer customer) {
		init();
		try {
			connection = JdbcConnectionPool.getConnectionFromPool();
			preparedStatement = connection.prepareStatement(SQL_QUERY_GET_CUSTOMER_BY_ID);
			preparedStatement.setInt(1, customer.getId());
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				customer.setName(rs.getString(2));
				customer.setProject(getProjects(customer));
			}
		} catch (SQLException e) {
			logger.error(DAO_EXCEPTION, e);
			e.printStackTrace();
		} finally {
			AbstartDao.close(preparedStatement);
			JdbcConnectionPool.returnConnection(connection);		
			}
		return customer;
	}

	/**
	 * Remove Customer from DB
	 */
	@Override
	public boolean removeCustomer(Customer customer) {
		init();
		try{
			connection = JdbcConnectionPool.getConnectionFromPool();
			preparedStatement = connection.prepareStatement("");
			preparedStatement.setString(1, customer.getName());
			preparedStatement.executeUpdate();
			flag = true;
		}
		catch(SQLException e){
			logger.error(DAO_EXCEPTION, e);
			e.printStackTrace();
		}
		finally{
			AbstartDao.close(preparedStatement);
			JdbcConnectionPool.returnConnection(connection);		}
		return flag;
	}

	/**
	 * Not implemented yet
	 */
	@Override
	public boolean addProject(Project project) {
		// TODO Auto-generated method stub
		return false;
	}

	
	/**
	 * Get List of customers projects
	 */
	@Override
	public List<Project> getProjects(Customer customer) {
		init();
		List<Project> projects = new ArrayList<>();
		CustomerDaoImpl customerDao = new CustomerDaoImpl();
		int id = customerDao.getId(customer);
		
		try {
			connection = JdbcConnectionPool.getConnectionFromPool();
			preparedStatement = connection.prepareStatement(SQL_GET_PROJECTS);
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				Project project = new Project();
				project.setPrice(rs.getInt(3));
				project.setManager(new EmployeeDaoImpl().getEmpById(rs.getInt(2)));
				project.setId(rs.getInt(1));
				projects.add(project);
			}
		} catch (SQLException e) {
			logger.error(DAO_EXCEPTION, e);
			e.printStackTrace();
		} finally {
			AbstartDao.close(preparedStatement);
			JdbcConnectionPool.returnConnection(connection);		
			}
		return projects;
	}

	/**
	 * Get Customer Id
	 */
	@Override
	public Integer getId(Customer customer) {
		init();
		Integer id = null;
		try{
			connection = JdbcConnectionPool.getConnectionFromPool();
			preparedStatement = connection.prepareStatement(SQL_QUERY_GET_CUSTOMER_BY_NAME);
			preparedStatement.setString(1,customer.getName());
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()){
				 id = new Integer(rs.getString(1));
			}
			return id;
		} catch (SQLException e){
			logger.error(DAO_EXCEPTION, e);
			e.printStackTrace();
		} finally{
			AbstartDao.close(preparedStatement);
			JdbcConnectionPool.returnConnection(connection);
		}
		return null;
	}

	/**
	 * Check if customer is already in db
	 */
	@Override
	public boolean isExist(Customer customer) {
		CustomerDaoImpl customerDao = new CustomerDaoImpl();
		if(customerDao.getId(customer) == null){
			return false;
		}
		return true;
	}

	/**
	 * Get customer by id
	 */
	@Override
	public Customer getByID(int id) {
		Customer customer = new Customer();
		init();
		try{
			connection = JdbcConnectionPool.getConnectionFromPool();
			preparedStatement = connection.prepareStatement(SQL_QUERY_GET_CUSTOMER_BY_ID);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				customer.setId(id);
				customer.setName(resultSet.getString(2));
			}
		} catch (SQLException e){
			logger.error(DAO_EXCEPTION, e);
			e.printStackTrace();
		} finally {
			AbstartDao.close(preparedStatement);
			JdbcConnectionPool.returnConnection(connection);
			
		}
		return customer;
	}	
}
