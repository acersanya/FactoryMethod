package main;

import java.util.ArrayList;
import java.util.List;
import com.netcracker.dao.DaoException;
import com.netcracker.dao.implement.CustomerDaoImpl;
import com.netcracker.dao.implement.EmployeeDaoImpl;
import com.netcracker.dao.implement.ProjectDaoImpl;
import com.netcracker.marshaller.JAXBParser;

import entitites.Customer;
import entitites.Department;
import entitites.Employee;
import entitites.HeadStatus;
import entitites.Occuptation;
import entitites.Project;

public class Runner {



	public static String path = "/home/acersanya/Documents/serial.txt";
	public static String pathEmp = "/home/acersanya/Documents/employee.txt";
	public static String pathXML ="/home/acersanya/parse.xml";

	public static void main(String[] args) {

		EmployeeDaoImpl employeeDAO = new EmployeeDaoImpl();
		CustomerDaoImpl customerDAO = new CustomerDaoImpl();
		ProjectDaoImpl 	projectDAO = new ProjectDaoImpl();
		
		Employee jack = new Employee("Jack", 25, Occuptation.ANALYST, Department.A,HeadStatus.TRUE);
		Employee monika = new Employee("Monika", 36, Occuptation.CODER, Department.B,HeadStatus.TRUE);
		Employee jace = new Employee("Jace", 29, Occuptation.CODER, Department.B);
		Employee hansa = new Employee("Hansa", 26, Occuptation.ARCHITECT, Department.C);
		Employee karl = new Employee("Karl", 26, Occuptation.ARCHITECT, Department.C);
		Customer hell = new Customer("Hellberg");
		Customer phill = new Customer("Phill");
		Project project = new Project(jack, hell, 106000);
		
//		try {
//			projectDAO.addProject(jack, hell, 10500);
//			projectDAO.addProject(monika, phill, 90000);
//			
//		} catch (DaoException e) {
//			e.printStackTrace();
//		}
//		List<Integer> avaibleProjects = projectDAO.getProjectsID();
//		employeeDAO.setOnProject(avaibleProjects.get(0),jace);
//		employeeDAO.setOnProject(avaibleProjects.get(1), jace);
	
		
		
		for(Project i: projectDAO.getProjects()){
			System.out.println(i);
		}
		
		JAXBParser parser = new JAXBParser();
		parser.parse(pathXML, karl);
		parser.parse(path, phill);
		parser.parse(path, project);
		
		

		
	}
}
