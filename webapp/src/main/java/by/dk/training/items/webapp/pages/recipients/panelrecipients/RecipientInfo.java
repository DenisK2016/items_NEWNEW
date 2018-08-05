package by.dk.training.items.webapp.pages.recipients.panelrecipients;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow.WindowClosedCallback;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;

import com.googlecode.wicket.jquery.ui.markup.html.link.AjaxLink;
import com.googlecode.wicket.kendo.ui.widget.notification.Notification;

import by.dk.training.items.dataaccess.filters.RecipientFilter;
import by.dk.training.items.datamodel.Package;
import by.dk.training.items.datamodel.Recipient;
import by.dk.training.items.services.PackageService;
import by.dk.training.items.services.RecipientService;
import by.dk.training.items.webapp.pages.packages.panelpackages.PackageInfo;

public class RecipientInfo extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private RecipientService recipientService;
	@Inject
	private PackageService packageService;
	private Recipient recipient;
	private RecipientFilter recipientFilter;
	private ModalWindow modalWindow;
	private String inPack;

	public RecipientInfo(ModalWindow modalWindow, Recipient recipient) {
		super(modalWindow.getContentId());
		this.modalWindow = modalWindow;
		recipientFilter = new RecipientFilter();
		recipientFilter.setFetchUser(true);
		recipientFilter.setFetchPackages(true);
		recipientFilter.setId(recipient.getId());
		this.recipient = recipientService.findRecipient(recipientFilter).get(0);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		final Notification notification = new Notification("notification");
		this.add(notification);
		final ModalWindow modal1 = new ModalWindow("modal1");
		modal1.setCssClassName("modal_window");
		modal1.setInitialHeight(500);
		modal1.setResizable(false);
		modal1.setWindowClosedCallback(new WindowClosedCallback() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClose(AjaxRequestTarget target) {
				target.add(RecipientInfo.this);
			}
		});
		this.setOutputMarkupId(true);
		add(modal1);
		add(new Label("nameRec", recipient.getName()));
		add(new Label("cityRec", recipient.getCity()));
		add(new Label("addressRec", recipient.getAddress()));
		String user = String.format("%d, %s", recipient.getIdUser().getId(), recipient.getIdUser().getLogin());
		add(new Label("userRec", user));
		add(new AjaxLink<Void>("back") {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				modalWindow.close(target);
			}
		});
		List<String> listPack = new ArrayList<>();
		for (Package p : recipient.getPackages()) {
			listPack.add(String.valueOf(p.getId()));
		}

		add(new DropDownChoice<String>("packs", new PropertyModel<String>(this, "inPack"), listPack)
				.add(new AjaxFormComponentUpdatingBehavior("onchange") {

					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					protected void onUpdate(AjaxRequestTarget target) {

					}
				}).setOutputMarkupId(true));
		add(new AjaxLink<Void>("infoPackage") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				if (inPack == null) {
					notification.error(target, getString("page.recipients.info.error"));
				} else {
					modal1.setContent(new PackageInfo(modal1, packageService.getPackageWithId(Long.valueOf(inPack))));
					modal1.show(target);
				}
			}
		});
		add(new AjaxLink<Void>("statistic") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				recipientFilter.setFetchPackages(true);
				recipientFilter.setId(recipient.getId());
				modal1.setContent(new RecipientStatistic(modal1, recipient));
				modal1.show(target);
			}
		});
	}
}
