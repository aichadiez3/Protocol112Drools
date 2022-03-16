package pojos;

import java.io.Serializable;
import java.util.Objects;

public class Patient implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String gender;
	private Boolean chronic;
	private Boolean drugs;
	private String name;
	private String surname;
	private String reference_number;
	private String age_range;
	
	public Patient() {
		super();
	}
	
	public Patient(Integer id) {
		super();
		this.id = id;
	}

	public Patient(String name, String surname, String gender, String age_range, Boolean chronic, Boolean drugs, 
			String reference_number) {
		super();
		this.gender = gender;
		this.age_range = age_range;
		this.chronic = chronic;
		this.drugs = drugs;
		this.name = name;
		this.surname = surname;
		this.reference_number = reference_number;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Boolean getChronic() {
		return chronic;
	}

	public void setChronic(Boolean chronic) {
		this.chronic = chronic;
	}

	public Boolean getDrugs() {
		return drugs;
	}

	public void setDrugs(Boolean drugs) {
		this.drugs = drugs;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getReference_number() {
		return reference_number;
	}

	public void setReference_number(String reference_number) {
		this.reference_number = reference_number;
	}

	public String getAge_range() {
		return age_range;
	}

	public void setAge_range(String age_range) {
		this.age_range = age_range;
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
		Patient other = (Patient) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Patient [id=" + id + ", gender=" + gender + ", age_range="+ age_range + ", chronic=" + chronic + ", drugs=" + drugs + ", name="
				+ name + ", surname=" + surname + ", reference_number=" + reference_number + "]";
	}
	
	

}
