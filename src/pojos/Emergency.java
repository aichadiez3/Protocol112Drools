package pojos;

import java.io.Serializable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Emergency implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer code;
	private String date;
	private Integer severity;
	private String direction;
	public Protocol protocol;
	public Location location;
	public Specialty specialty;
	private Disease disease;
	private Patient patient;
	
	public Emergency() {
		super();
		this.date = setDate();
		this.code = setCode();
	}
	
	public Emergency(Integer id) {
		super();
		this.id = id;
		this.date = setDate();
		this.code = setCode();
	}
	
	
	public Emergency(Integer id, Integer code, String date, Integer severity) {
		super();
		this.id = id;
		this.code = code;
		this.date = date;
		this.severity = severity;
	}
	
	
	
	public Emergency(Integer id, Integer code, String date, Integer severity, String direction, Protocol protocol,
			Location location, Specialty specialty, Disease disease) {
		super();
		this.id = id;
		this.code = code;
		this.date = date;
		this.severity = severity;
		this.direction = direction;
		this.protocol = protocol;
		this.location = location;
		this.specialty = specialty;
		this.disease = disease;
	}

	public Emergency(Integer id, Patient patient) {
		super();
		this.id = id;
		this.date = setDate();
		this.code = setCode();
	}
	
	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}


	public Specialty getSpecialty() {
		return specialty;
	}

	public void setSpecialty(Specialty specialty) {
		this.specialty = specialty;
	}

	public Disease getDisease() {
		return disease;
	}

	public void setDisease(Disease disease) {
		this.disease = disease;
	}

	public Integer getSeverity() {
		return severity;
	}
	public void setSeverity(Integer severity) {
		this.severity = severity;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCode() {
		return code;
	}
	public Integer setCode() {
		Integer code = (int) (Math.random()*(999-0+1)+0);
		return code;
	}
	public String getDate() {
		return date;
	}
	public String setDate() {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String regDate = formatter.format(date);
		return regDate;
	}
	
	public void setProtocol(Protocol protocol) {
		this.protocol = protocol;
	}
	
	public Protocol getProtocol() {
		return protocol;
	}
	
	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
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
		Emergency other = (Emergency) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Emergency [id=" + id + ", code=" + code + ", date=" + date + ", severity=" + severity + ", direction="
				+ direction + ", protocol=" + protocol + ", location=" + location + ", specialty=" + specialty
				+ ", disease=" + disease + ", patient=" + patient + "]";
	}

	
	
	
	
	
	
}
