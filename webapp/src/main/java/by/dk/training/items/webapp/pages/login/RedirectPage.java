package by.dk.training.items.webapp.pages.login;

import java.util.Calendar;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow.WindowClosedCallback;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.AbstractReadOnlyModel;

public class RedirectPage extends WebPage {

	private static final long serialVersionUID = 1L;

	public RedirectPage() {
		super();
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		add(new Label("msg", getString("redirect.label")));
		final ModalWindow modalMail;
		add(modalMail = new ModalWindow("modalEmail"));
		modalMail.setCssClassName("modal_window");
		modalMail.setResizable(false);
		this.setOutputMarkupId(true);
		add(new AjaxLink<Void>("send") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				modalMail.setContent(new SendMessageWindow(modalMail));
				modalMail.show(target);
			}
		}.add(AttributeModifier.append("title", getString("redirect.sendmsg"))));

		modalMail.setWindowClosedCallback(new WindowClosedCallback() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClose(AjaxRequestTarget target) {
				target.add(RedirectPage.this);

			}
		});

		add(new AjaxLink<Object>("home") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				getSession().invalidate();
				setResponsePage(new LoginPage());
			}
		}.add(AttributeModifier.append("title", getString("confirm.home"))));
		AbstractReadOnlyModel<Integer> yearModel = new AbstractReadOnlyModel<Integer>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Integer getObject() {
				return Calendar.getInstance().get(Calendar.YEAR);
			}
		};
		WebMarkupContainer footer = new WebMarkupContainer("footer");
		add(footer);
		footer.add(new Label("current-year", yearModel));
	}

}
