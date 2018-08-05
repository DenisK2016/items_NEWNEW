package by.dk.training.items.services;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import by.dk.training.items.datamodel.Recipient;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:service-context-test.xml" })
public class WNewRecipientTest {

	@Inject
	private RecipientService recipientService;

	@Transactional
	@Test
	public void restRecipient() {

//		List<Recipient> allRecipients = recipientService.getAll();
//		for (Recipient r : allRecipients) {
//			recipientService.delete(r.getId());
//		}

		Recipient recipient;

		for (int i = 0; i < 10; i++) {
			recipient = new Recipient();

			createRecipient(recipient, i);
		}

//		RecipientFilter recipientFilter = new RecipientFilter();
//		recipientFilter.setCity("Гродно4");
//		recipientFilter.setAddress("Болдина 3");
//		recipientFilter.setFetchPackages(true);
//		allRecipients = recipientService.find(recipientFilter);
//
//		// Assert.assertEquals(allRecipients.size(), 2);
//		System.out.println(allRecipients);
	}

	private void createRecipient(Recipient recipient, int i) {
		recipient.setAddress("Болдина " + i);
		recipient.setCity("Гродно" + i);
		recipient.setName("Иванов Иван " + i);

		recipientService.registerRecipient(recipient);
	}
}
