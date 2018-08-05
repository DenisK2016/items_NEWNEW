package by.dk.training.items.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import by.dk.training.items.dataaccess.TypeDao;
import by.dk.training.items.dataaccess.filters.TypeFilter;
import by.dk.training.items.datamodel.Type;
import by.dk.training.items.services.TypeService;

@Service
public class TypeServiceImpl implements TypeService {

	private static Logger LOGGER = LoggerFactory.getLogger(UserServiceProfileImpl.class);
	@Inject
	private TypeDao typeDao;

	@Override
	public void registerType(Type type) {
		Type t = new Type();
		if (type.getParentType() != null) {
			t = type.getParentType();
			type.setParentType(null);
		}
		typeDao.insert(type);
		if (t.getId() != null) {
			type.setParentType(t);
			typeDao.update(type);
			type.getParentType().setChildTypes(type);
		}
		LOGGER.info("Type register: {}", type);
	}

	@Override
	public Type get(Long id) {
		LOGGER.info("Type select: {}", typeDao.get(id));
		return typeDao.get(id);
	}

	@Override
	public void update(Type type) {
		LOGGER.info("Type update, new and old: {}", type, typeDao.get(type.getId()));
		typeDao.update(type);
	}

	@Override
	public void delete(Long id) {
		Type type = typeDao.get(id);
		LOGGER.info("Type delete: {}", type);
		deleteParentFromChildren(type);
		deleteChildFromParent(type);
		typeDao.delete(id);
	}

	private void deleteChildFromParent(Type type) {
		type.setParentType(null);
		typeDao.update(type);
	}

	private void deleteParentFromChildren(Type type) {
		TypeFilter filter = new TypeFilter();
		filter.setParentType(type);
		List<Type> children = typeDao.find(filter);
		for (Type child : children) {
			child.setParentType(null);
			typeDao.update(child);
		}
	}

	@Override
	public List<Type> findType(TypeFilter typeFilter) {
		LOGGER.info("Type find by filter: {}", typeFilter);
		return typeDao.find(typeFilter);
	}

	@Override
	public List<Type> getAll() {
		LOGGER.info("Type getAll: {}", "All Types");
		return typeDao.getAll();
	}

	@Override
	public Long overallNumberOfTypes(TypeFilter filter) {
		LOGGER.info("Type count: {}", filter);
		return typeDao.overallNumberOfTypes(filter);
	}
}
