package by.dk.training.items.services;

import java.util.List;

import javax.transaction.Transactional;

import by.dk.training.items.dataaccess.filters.RecipientFilter;
import by.dk.training.items.datamodel.Recipient;

public interface RecipientService {

	@Transactional
	void registerRecipient(Recipient recipient);

	Recipient getRecipient(Long id);

	@Transactional
	void update(Recipient recipient);

	@Transactional
	void delete(Long id);

	List<Recipient> findRecipient(RecipientFilter recipientFilter);

	List<Recipient> getAll();

	Long overallNumberOfRecipients(RecipientFilter filter);

	List<Recipient> getDuplicate(RecipientFilter filter);
}
