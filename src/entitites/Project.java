package entitites;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author acersanya
 *	Project class
 */
@XmlRootElement
public class Project implements Serializable, Parsable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Employee manager;
	private List<Employee> team;
	private Customer client;
	private Integer price;

	public Project() {

	}

	public Project(Employee manager, Customer client, Integer price) {
		super();
		this.manager = manager;
		this.team = new ArrayList<>();
		this.client = client;
		this.price = price;
	}

	public Integer getId() {
		return id;
	}

	@XmlElement
	public void setId(int id) {
		this.id = id;
	}

	public Employee getManager() {
		return manager;
	}

	public List<Employee> getTeam() {
		return team;
	}

	public Customer getClient() {
		return client;
	}

	public Integer getPrice() {
		return price;
	}
	
	@XmlElement
	public void setManager(Employee manager) {
		this.manager = manager;
	}

	@XmlElement
	public void setTeam(List<Employee> team) {
		this.team = team;
	}

	@XmlElement
	public void setClient(Customer client) {
		this.client = client;
	}

	@XmlElement
	public void setPrice(Integer price) {
		this.price = price;
	}


	@Override
	public String toString() {
		return "Project [id=" + id + ", manager=" + manager + ", team=" + team + ", client=" + client + ", price="
				+ price + "]";
	}

	public List<Employee> employeeOnProject() {
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((client == null) ? 0 : client.hashCode());
		result = prime * result + ((manager == null) ? 0 : manager.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((team == null) ? 0 : team.hashCode());
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
		Project other = (Project) obj;
		if (client == null) {
			if (other.client != null)
				return false;
		} else if (!client.equals(other.client))
			return false;
		if (manager == null) {
			if (other.manager != null)
				return false;
		} else if (!manager.equals(other.manager))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (team == null) {
			if (other.team != null)
				return false;
		} else if (!team.equals(other.team))
			return false;
		return true;
	}

}
