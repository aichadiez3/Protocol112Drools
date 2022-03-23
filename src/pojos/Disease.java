package pojos;

import java.io.Serializable;
import java.util.Objects;

public class Disease implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name;
	private String symptomsList;
	
	public Disease(String name) {
		super();
		this.name = name;
	}

	public Disease(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public Disease(Integer id, String name, String symptomList) {
		super();
		this.id = id;
		this.name = name;
		this.symptomsList = symptomList;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSymptomsList() {
		return symptomsList;
	}

	public void setSymptomsList(String symptomsList) {
		this.symptomsList = symptomsList;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Disease other = (Disease) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Disease [id=" + id + ", name=" + name + ", symptomsList="
				+ symptomsList + "]";
	}

	

	
	

}
