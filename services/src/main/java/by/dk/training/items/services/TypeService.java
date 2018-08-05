package by.dk.training.items.services;


import java.util.List;

import javax.transaction.Transactional;

import by.dk.training.items.dataaccess.filters.TypeFilter;
import by.dk.training.items.datamodel.Type;

public interface TypeService {
	@Transactional
	void registerType(Type type);
	
	Type get(Long id);
	
	@Transactional
	void update(Type type);

	@Transactional
	void delete(Long id);
	
	List<Type> findType(TypeFilter typeFilter);

	List<Type> getAll();
	
	Long overallNumberOfTypes(TypeFilter filter);
}
