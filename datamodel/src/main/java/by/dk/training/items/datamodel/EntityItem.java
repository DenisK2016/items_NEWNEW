package by.dk.training.items.datamodel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.TableGenerator;

@MappedSuperclass
public abstract class EntityItem implements Serializable {

	private static final long serialVersionUID = 828113069750017895L;

	@Id
	@TableGenerator(
		    name="idGenerator",
		    table="GENERATOR_ID",
		    pkColumnName = "primary_key",
		    valueColumnName = "last_ganarated_value",
		    pkColumnValue="id",
		    allocationSize=1000,
		    initialValue=1000
		)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="idGenerator")
	@Column(name = "id", nullable = false, unique = true)
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
