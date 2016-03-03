package entitites;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Customer implements Serializable, Parsable {
	private static final long serialVersionUID = 1L;
	private String name;
	private List<Project> project;
	private Integer id;
		
	public Customer(){	
	}

	public Integer getId() {
		return id;
	}

	@XmlElement
	public void setId(Integer id) {
		this.id = id;
	}

	public Customer(String name, Project project) {
		super();
		this.project = new ArrayList<>();
		this.name = name;
		this.project.add(project);
	}

	public Customer(String name, List<Project> projects){
		this.project = projects;
	}

	public Customer(String name) {
		this.name = name;
	}

	public void addProject(Project pr) {
		this.project.add(pr);
	}

	public String getName() {
		return name;
	}

	public List<Project> getProject() {
		return project;
	}

	@Override
	public String toString() {
		return "[name=" + name + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((project == null) ? 0 : project.hashCode());
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
		Customer other = (Customer) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (project == null) {
			if (other.project != null)
				return false;
		} else if (!project.equals(other.project))
			return false;
		return true;
	}

	@XmlElement
	public void setName(String name) {
		this.name = name;
	}

	@XmlElement
	public void setProject(List<Project> project) {
		this.project = project;
	}

}
