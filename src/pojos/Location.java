package pojos;

import java.io.Serializable;
import java.util.Objects;

public class Location implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String place;
	private Vehicle vehicle;
    public enum Vehicle {AMBULANCE,HELICOPTER,BOAT};

	
	public Location(Integer id, String place, Vehicle vehicle) {
		super();
		this.id = id;
		this.place = place;
		this.vehicle = vehicle;
	}
	
	public Location(String place, Vehicle vehicle) {
		super();
		this.place = place;
		this.vehicle = vehicle;
	}

	public Location(Integer id, String place) {
		super();
		this.id = id;
		this.place = place;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
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
		Location other = (Location) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Location [id=" + id + ", place=" + place + ", vehicle=" + vehicle + "]";
	}
	
	
	
}
