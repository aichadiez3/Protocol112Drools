package pojos;

import java.io.Serializable;
import java.util.Objects;

public class Symptom implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String symptom;
	
	public Symptom(Integer id, String symptom) {
		super();
		this.id = id;
		this.symptom = symptom;
	}
	
	public Symptom(String symptom) {
		super();
		this.symptom = symptom;
	}

	public String getSymptom() {
		return symptom;
	}

	public void setSymptom(String symptom) {
		this.symptom = symptom;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
		Symptom other = (Symptom) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Symptom [id=" + id + ", symptom=" + symptom + "]";
	}
	
	

}
