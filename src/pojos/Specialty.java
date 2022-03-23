package pojos;

import java.io.Serializable;
import java.util.Objects;

public class Specialty implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name;
	
	public Specialty(String name) {
		super();
		this.name = name;
	}
	
	public Specialty(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
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
		Specialty other = (Specialty) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Specialty [id=" + id + ", name=" + name + "]";
	}

	
	
	

}
