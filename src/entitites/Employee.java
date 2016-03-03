package entitites;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author acersanya
 *	Employee class with getter and setters
 */
@XmlRootElement
public class Employee implements Serializable, Parsable{

	private static final long serialVersionUID = 1L;
	private String name;
	private int age;
	private Occuptation position;
	private List<Project> projects;
	private Department dep;
	private HeadStatus status;
	private Integer id;

	public Integer getId() {
		return id;
	}

	@XmlElement
	public void setId(Integer id) {
		this.id = id;
	}

	public Employee(String name) {
		this.name = name;
	}

	public Employee(String name, int age, Occuptation position, Department dep) {
		super();
		this.name = name;
		this.age = age;
		this.position = position;
		this.dep = dep;
		this.status = HeadStatus.FALSE;
	}

	public Employee(String name, int age, Occuptation position, Department dep, HeadStatus status) {
		super();
		this.name = name;
		this.age = age;
		this.position = position;
		this.dep = dep;
		this.status = HeadStatus.FALSE;
		this.status = status;
	}

	public Employee() {
	}

	public Employee(String name, int age, Occuptation position, Department dep, Project project) {
		super();
		this.projects.add(project);
		this.name = name;
		this.age = age;
		this.position = position;
		this.dep = dep;
	}

	@XmlElement
	public void setName(String name) {
		this.name = name;
	}

	@XmlElement
	public void setAge(int age) {
		this.age = age;
	}

	@XmlElement
	public void setPosition(Occuptation position) {
		this.position = position;
	}

	@XmlElement
	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	@XmlElement
	public void setDep(Department dep) {
		this.dep = dep;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public Occuptation getPosition() {
		return position;
	}

	public void addProject(Project project) {
		projects.add(project);
	}

	public List<Project> getProjects() {
		return projects;
	}

	@Override
	public String toString() {
		return "Employee [name=" + name + ", age=" + age + ", position=" + position+"]";
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Department getDep() {
		return dep;
	}

	public HeadStatus getStatus() {
		return status;
	}

	@XmlElement
	public void setStatus(HeadStatus status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		result = prime * result + ((dep == null) ? 0 : dep.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (age != other.age)
			return false;
		if (dep != other.dep)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (position != other.position)
			return false;
		return true;
	}

}
