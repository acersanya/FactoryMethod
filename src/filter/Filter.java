package filter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import entitites.Customer;
import entitites.Department;
import entitites.Employee;
import entitites.Project;

public class Filter {

	private Set<Employee> filtered;

	public List<Employee> freeEmployeeDep(Department dep, List<Employee> emp) {
		filtered = new HashSet<>();
		for (Employee i : emp) {
			if (i.getDep().equals(dep) && i.getProjects() == null) {
				filtered.add(i);
			}
		}
		return new ArrayList<>(filtered);
	}

	public List<Employee> freeEmp(List<Employee> emp) {
		filtered = new HashSet<>();
		for (Employee i : emp) {
			if (i.getProjects() == null) {
				filtered.add(i);
			}
		}
		return (List<Employee>) filtered;
	}

	public List<Employee> subordinatesList(Employee manager) {
		filtered = new HashSet<>();
		for (Project i : manager.getProjects()) {
			filtered.addAll(i.getTeam());
		}
		filtered.remove(manager);
		return new ArrayList<>(filtered);
	}

	public List<Employee> empManagers(Employee e) {
		filtered = new HashSet<>();
		for (Project i : e.getProjects()) {
			filtered.add(i.getManager());
		}
		return new ArrayList<>(filtered);
	}

	public List<Employee> empWithSameProject(List<Employee> emp, Employee e) {
		filtered = new HashSet<>();
		for (Employee i : emp) {
			if (i.getProjects().equals(e.getProjects())) {
				filtered.add(i);
			}
		}
		return new ArrayList<>(filtered);
	}

	public List<Project> clientProjects(Customer client) {
		return client.getProject();
	}

	public List<Employee> empForClient(Customer client) {
		Set<Employee> emp = new HashSet<>();
		filtered = new HashSet<>();
		for (Project i : client.getProject()) {
			emp.addAll(i.getTeam());
		}
		filtered.addAll(emp);
		return new ArrayList<>(filtered);
	}
}
