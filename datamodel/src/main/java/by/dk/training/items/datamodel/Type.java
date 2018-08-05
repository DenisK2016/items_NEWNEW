package by.dk.training.items.datamodel;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "type")
public class Type extends EntityItem {

	private static final long serialVersionUID = -698861429701533260L;

	@Column(name = "type_name", nullable = false, unique = true)
	private String typeName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_type", referencedColumnName = "id")
	private Type parentType;

	@OneToMany(mappedBy = "parentType", orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Type> childTypes = new ArrayList<>(); // https://stackoverflow.com/questions/3393515/jpa-how-to-have-one-to-many-relation-of-the-same-entity-type

	@ManyToOne(targetEntity = UserProfile.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "idUser")
	private UserProfile idUser;

	public List<Type> getChildTypes() {
		return childTypes;
	}

	public void setChildTypes(Type childTypes) {
		this.childTypes.add(childTypes);
	}

	public UserProfile getIdUser() {
		return idUser;
	}

	public void setIdUser(UserProfile idUser) {
		this.idUser = idUser;
	}

	public Type getParentType() {
		return parentType;
	}

	public void setParentType(Type parentType) {
		this.parentType = parentType;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Override
	public String toString() {
		return "Type [id=" + getId() + ", typeName=" + typeName + ", parentType=" + parentType + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result + ((parentType == null) ? 0 : parentType.hashCode());
		result = prime * result + ((typeName == null) ? 0 : typeName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Type other = (Type) obj;
		if (getId() == null) {
			if (other.getId() != null) {
				return false;
			}
		} else if (!getId().equals(other.getId())) {
			return false;
		}
		if (parentType == null) {
			if (other.parentType != null) {
				return false;
			}
		} else if (!parentType.equals(other.parentType)) {
			return false;
		}
		if (typeName == null) {
			if (other.typeName != null) {
				return false;
			}
		} else if (!typeName.equals(other.typeName)) {
			return false;
		}
		return true;
	}

}
