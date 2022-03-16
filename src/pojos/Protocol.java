package pojos;

import java.io.Serializable;
import java.util.Objects;

public class Protocol implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String info;
	public static enum Type {ADVICE,INPLACE,SHIPMENT};
	private Type protocolType;
	
	public Protocol(Integer id, String info) {
		super();
		this.id = id;
		this.info = info;
	}
	
	public Protocol(Integer id, String info, Type protocolType) {
		super();
		this.id = id;
		this.info = info;
		this.protocolType = protocolType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Type getProtocolType() {
		return protocolType;
	}

	public void setProtocolType(Type protocolType) {
		this.protocolType = protocolType;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
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
		Protocol other = (Protocol) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Protocol [id=" + id + ", info=" + info + ", protocolType=" + protocolType + "]";
	}

	
	
	

}
