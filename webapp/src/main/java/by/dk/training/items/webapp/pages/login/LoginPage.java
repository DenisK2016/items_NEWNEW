package by.dk.training.items.webapp.pages.login;

import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow.WindowClosedCallback;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.util.string.Strings;

import com.googlecode.wicket.kendo.ui.widget.notification.Notification;

import by.dk.training.items.webapp.app.localization.LanguageSelectionComponent;

public class LoginPage extends WebPage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String ID_FORM = "form";

	private String username;
	private String password;

	public LoginPage() {
		super();

	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		add(new LanguageSelectionComponent("language-select"));
		final Notification notification = new Notification("notification");

		// if already logged then should not see login page at all
		if (AuthenticatedWebSession.get().isSignedIn()) {
			setResponsePage(Application.get().getHomePage());
		}

		final Form<Void> form = new Form<Void>(ID_FORM);
		FeedbackPanel feedBackPanel = new FeedbackPanel("feedbackpanel");
		feedBackPanel.setOutputMarkupId(true);
		feedBackPanel.setVisible(false);
		form.add(feedBackPanel);
		form.setDefaultModel(new CompoundPropertyModel<LoginPage>(this));
		form.add(new RequiredTextField<String>("username").setRequired(false));
		form.add(new PasswordTextField("password").setRequired(false));
		form.add(notification);
		form.add(new AjaxSubmitLink("submit-btn") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				if (Strings.isEmpty(username) || Strings.isEmpty(password)) {
					notification.error(target, getString("login.error1"));
					return;
				}
				final boolean authResult = AuthenticatedWebSession.get().signIn(username, password);
				if (authResult) {
					// continueToOriginalDestination();
					setResponsePage(Application.get().getHomePage());
				} else {
					notification.error(target, getString("login.error2"));
				}
			}
		});

		add(form);

		final ModalWindow modal1;
		add(modal1 = new ModalWindow("modal1"));
		modal1.setCssClassName("modal_window");
		modal1.setInitialHeight(500);
		modal1.setResizable(false);
		this.setOutputMarkupId(true);
		add(new AjaxLink<Void>("newUser") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				modal1.setContent(new NewLoginPanel(modal1));
				modal1.show(target);
			}
		});

		modal1.setWindowClosedCallback(new WindowClosedCallback() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClose(AjaxRequestTarget target) {
				target.add(LoginPage.this);

			}
		});

		final ModalWindow modal2;
		add(modal2 = new ModalWindow("modal2"));

		modal2.setResizable(false);
		modal2.setCssClassName("modal_window");
		modal2.setInitialHeight(350);
		this.setOutputMarkupId(true);
		add(new AjaxLink<Void>("passwordRestore") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				modal2.setContent(new PasswordRestorePanel(modal2));
				modal2.show(target);
			}
		});

		modal2.setWindowClosedCallback(new WindowClosedCallback() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClose(AjaxRequestTarget target) {
				target.add(LoginPage.this);

			}
		});

	}

}
