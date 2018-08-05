package by.dk.training.items.webapp.pages.login;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.inject.Inject;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import by.dk.training.items.datamodel.StatusUser;
import by.dk.training.items.datamodel.UserCredentials;
import by.dk.training.items.services.UserProfileService;
import by.dk.training.items.services.mail.Sender;

public class PageConfirmation extends WebPage {

	private static final long serialVersionUID = 8238151980149480294L;

	private static final String EMAIL = "sample1denis1@gmail.com";
	private static final String PASSWORD = "12345678qwertyui";

	private PageParameters parameters;

	@Inject
	private UserProfileService userProfileService;

	public PageConfirmation(PageParameters parameters) {
		super(parameters);
		this.parameters = parameters;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		addRegistrationResult();
		addLinkHome();
		addFooter();
	}

	private void addRegistrationResult() {
		String keyString = parameters.getValues("foo").get(0).toString();
		BigDecimal key = new BigDecimal(keyString).divide(new BigDecimal("123"), 0).divide(new BigDecimal("23"), 0)
				.divide(new BigDecimal("3"), 0);
		UserCredentials user = userProfileService.getUserCredentials(key.longValue());

		if (user == null) {
			add(new Label("thanks", getString("confirm.error")));
		} else if (user.getStatus().equals(StatusUser.CONFIRMATION)) {
			user.setStatus(StatusUser.OFFICER);
			userProfileService.updateUserCredentials(user);
			add(new Label("thanks", getString("confirm.label")));
			String text = getTextMessage(user);
			new Sender(EMAIL, PASSWORD).send(getString("confirm.email.text3"), text, user.getEmail());
		} else {
			add(new Label("thanks", getString("confirm.email.error2")));
		}
	}

	private void addFooter() {
		AbstractReadOnlyModel<Integer> yearModel = new AbstractReadOnlyModel<Integer>() {

			private static final long serialVersionUID = -8851000262854857184L;

			@Override
			public Integer getObject() {
				return Calendar.getInstance().get(Calendar.YEAR);
			}
		};
		WebMarkupContainer footer = new WebMarkupContainer("footer");
		add(footer);
		footer.add(new Label("current-year", yearModel));
	}

	private void addLinkHome() {
		add(new AjaxLink<Object>("home") {

			private static final long serialVersionUID = 1967529838574694382L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(new LoginPage());
			}
		}.add(AttributeModifier.append("title", getString("confirm.home"))));
	}

	private String getTextMessage(UserCredentials user) {
		String text;
		text = String.format("%s %s %s. %s: \n %s:%s \n %s:%s \n %s:%s \n %s:%s \n %s:%s \n %s:%s",
				getString("confirm.email.text"), user.getFirstName(), user.getLastName(),
				getString("confirm.email.text2"), getString("newlogin.login"),
				userProfileService.getUser(user.getId()).getLogin(), getString("newlogin.password"),
				userProfileService.getUser(user.getId()).getPassword(), getString("newlogin.fname"),
				user.getFirstName(), getString("newlogin.lname"), user.getLastName(), getString("newlogin.post"),
				user.getPost(), getString("newlogin.rank"), user.getRank());
		return text;
	}
}
