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
import com.netcracker.dao.EmployeeDao;
import com.netcracker.dao.ProjectDao;

import entitites.Customer;
import entitites.Department;
import entitites.Employee;
import entitites.HeadStatus;
import entitites.Occuptation;
import entitites.Project;

public class EmployeeDaoImpl extends EmployeeDao {

	private static final String SQL_QUERY_ADD_EMP = "INSERT INTO Employees (name,age,department,occupation) VALUES (?,?,?,?)";
	private static final String SQL_QUERY_ADD_EMP_HEAD= "INSERT INTO Employees (name,age,department,occupation,status) VALUES (?,?,?,?,1)";
	private static final String SQL_QUERY_ADD_EMP_TO_PROJECT = "INSERT INTO EmpProjects (EmpID,ProjectID) VALUES (?,?)";
	private static final String SQL_QUERY_EMP_GET_BY_ID = "SELECT id FROM Employees WHERE  name = ? AND age = ? AND department = ? AND occupation = ?";
	private static final String SQL_QUERY_GET_PROJECTS = "SELECT ProjectID FROM EmpProjects WHERE EmpID = ?";
	private static final String SQL_QUERY_GET_EMP_BY_ID = "SELECT * FROM Employees WHERE id = ?";
	private static final String SQL_QUERY_DEL_BY_ID = "DELETE FROM Employees WHERE id =?";
	private static final String SQL_QUERY_GET_BY_NAME = "SELECT * FROM Employees Where name =?";
	private static final String SQL_QUERY_GET_STAFF = "SELECT * from Employees";
	private static final String DAO_EXCEPTION_NO_USER = "No such user";
	private static final String DAO_USER_ALREADY_EXISTS = "User already exists";
	
	JdbcConnectionPool pool = new JdbcConnectionPool();
	ProjectDaoImpl projDao = new ProjectDaoImpl();
	private Connection connection;
	private PreparedStatement preparedStatement;
	private boolean flag = false;
	
	/**
	 * initialize the connection object
	 */
	public void init(){
		this.connection = null;
		this.preparedStatement = null;
		this.flag = false;
	}

	/**
	 * Check if Employee exist in DB
	 * @param emp 
	 * @return true if exist , false if not
	 */
	public boolean ifExist(Employee emp){
		if(getStuff().isEmpty()){
			return false;
		}
		for(Employee i : getStuff()){
			if(!i.equals(emp)){
				return false;
			}
		}
		return true;
	}
	

	@Override
	/**
	 * Add Employee to DB 
	 * Checks if exist in DB
	 * if true throws DaoException
	 */
	public boolean addEmp(Employee emp) throws DaoException {
		if(ifExist(emp)){
			throw new DaoException(DAO_USER_ALREADY_EXISTS);
		}
		init();
		try {
			connection = JdbcConnectionPool.getConnectionFromPool();
			if(emp.getStatus().equals(HeadStatus.TRUE)){
				preparedStatement = connection.prepareStatement(SQL_QUERY_ADD_EMP_HEAD);
			} else {
				preparedStatement = connection.prepareStatement(SQL_QUERY_ADD_EMP);
			}
			preparedStatement.setString(1, emp.getName());
			preparedStatement.setInt(2, emp.getAge());
			preparedStatement.setString(3, emp.getDep().toString());
			preparedStatement.setString(4, emp.getPosition().toString());
			preparedStatement.executeUpdate();
			flag = true;
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			AbstartDao.close(preparedStatement);
			JdbcConnectionPool.returnConnection(connection);
		}
		return flag;
	}

	@Override
	/**
	 * Get Employee be id
	 */
	public Employee getEmpById(int id) {
		Employee emp = new Employee();
		init();
		ResultSet resultSet = null;
		try {
			connection = JdbcConnectionPool.getConnectionFromPool();
			preparedStatement = connection.prepareStatement(SQL_QUERY_GET_EMP_BY_ID);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			emp.setName(resultSet.getString(2));
			emp.setAge(resultSet.getInt(3));
			emp.setDep(Department.valueOf(resultSet.getString(4)));
			emp.setPosition(Occuptation.valueOf(resultSet.getString(5).toUpperCase()));
			if(resultSet.getInt(6) == 0){
				emp.setStatus(HeadStatus.FALSE);
			}else{
				emp.setStatus(HeadStatus.TRUE);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			AbstartDao.close(preparedStatement);
			JdbcConnectionPool.returnConnection(connection);
		}
		return emp;
	}

	/**
	 * Get Employee be id
	 */
	@Override
	public int getEmpId(Employee emp) {
		init();
		ResultSet resultSet = null;
		int id = 0;
		try {
			emp = new EmployeeDaoImpl().getByName(emp.getName());
		} catch (DaoException e) {
			e.printStackTrace();
		}
		try {
			connection = JdbcConnectionPool.getConnectionFromPool();
			preparedStatement = connection.prepareStatement(SQL_QUERY_EMP_GET_BY_ID);
			preparedStatement.setString(1, emp.getName());
			preparedStatement.setInt(2, emp.getAge());
			preparedStatement.setString(3, emp.getDep().toString());
			preparedStatement.setString(4, emp.getPosition().toString());
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			id = resultSet.getInt(1);
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			AbstartDao.close(preparedStatement);
			JdbcConnectionPool.returnConnection(connection);
		}
		return id;
	}

	/**
	 * Add Employee to current existing project
	 */
	@Override
	public boolean setOnProject(int projectId, Employee emp) {
		init();
		try {
			if(!ifExist(emp)){
				try {
				
					addEmp(emp);
				} catch (DaoException e) {
					e.printStackTrace();
				}
			}
			connection = JdbcConnectionPool.getConnectionFromPool();
			emp.setId(new EmployeeDaoImpl().getEmpId(emp));	
			preparedStatement = connection.prepareStatement(SQL_QUERY_ADD_EMP_TO_PROJECT);
			preparedStatement.setInt(1, emp.getId());
			preparedStatement.setInt(2, projectId);
			preparedStatement.executeUpdate();
			flag = true;
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			AbstartDao.close(preparedStatement);
			JdbcConnectionPool.returnConnection(connection);
		}
		return flag;
	}

	/**
	 * Get list of projects Employee is working on
	 */
	@Override
	public List<Project> getProjects(Employee employee) {
		List<Project> projects = new ArrayList<>();
		init();
		try {
			int id = getEmpId(employee);
			connection = JdbcConnectionPool.getConnectionFromPool();
			preparedStatement = connection.prepareStatement(SQL_QUERY_GET_PROJECTS);
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				projects.add(new ProjectDaoImpl().getByID(rs.getInt(1)));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			AbstartDao.close(preparedStatement);
			JdbcConnectionPool.returnConnection(connection);
		}
		return projects;
	}

	/**
	 * Remove Employee by id
	 */
	@Override
	public boolean removeEmpById(int id) {
		boolean flag = false;
		init();
		try{
			connection = JdbcConnectionPool.getConnectionFromPool();
			preparedStatement = connection.prepareStatement(SQL_QUERY_DEL_BY_ID);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
			flag = true;
		} catch (SQLException e){
			e.printStackTrace();
		} finally {
			AbstartDao.close(preparedStatement);
			JdbcConnectionPool.returnConnection(connection);
		}
		return flag;
	}
	
	/**
	 * Get Employee object from db by name
	 */
	@Override
	public Employee getByName(String name) throws DaoException {
		init();
		Employee emp = new Employee();
		try{
			connection = JdbcConnectionPool.getConnectionFromPool();
			preparedStatement = connection.prepareStatement(SQL_QUERY_GET_BY_NAME);
			preparedStatement.setString(1, name);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()){
				emp  = getUser(rs);
			}
		} catch (SQLException e){
			e.printStackTrace();
		} finally {
			AbstartDao.close(preparedStatement);
			JdbcConnectionPool.returnConnection(connection);
		}
		return emp;
	}

	/**
	 * Get all working stuff
	 */
	@Override
	public List<Employee> getStuff() {
		List<Employee> stuff = new ArrayList<>();
		init();
		try{
			connection = JdbcConnectionPool.getConnectionFromPool();
			preparedStatement = connection.prepareStatement(SQL_QUERY_GET_STAFF);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()){
				stuff.add(getUser(rs));
			}
		} catch (SQLException e){
			e.printStackTrace();
			
		} finally {
			AbstartDao.close(preparedStatement);
			JdbcConnectionPool.returnConnection(connection);
		}
		return stuff;
	}
	
	/**
	 * Get Employee from ResultStatement
	 * @param rs - result set
	 * @return Employee
	 * @throws SQLException
	 */
	private Employee getUser(ResultSet rs) throws SQLException{
		Employee emp = new Employee();
		emp.setId(rs.getInt(1));
		emp.setName(rs.getString(2));
		emp.setAge(rs.getInt(3));
		emp.setDep(Department.valueOf(rs.getString(4)));
		emp.setPosition(Occuptation.valueOf(rs.getString(5)));
		if(rs.getInt(6) == 0){
			emp.setStatus(HeadStatus.FALSE);
		} else{
			emp.setStatus(HeadStatus.TRUE);
		}	
		return emp;
	}
	
}
