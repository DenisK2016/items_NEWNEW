package by.dk.training.items.webapp.pages.users.panelusers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow.WindowClosedCallback;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.googlecode.wicket.kendo.ui.widget.notification.Notification;

import by.dk.training.items.dataaccess.filters.ProductFilter;
import by.dk.training.items.dataaccess.filters.RecipientFilter;
import by.dk.training.items.dataaccess.filters.TypeFilter;
import by.dk.training.items.dataaccess.filters.UserFilter;
import by.dk.training.items.datamodel.Package;
import by.dk.training.items.datamodel.Product;
import by.dk.training.items.datamodel.Recipient;
import by.dk.training.items.datamodel.Type;
import by.dk.training.items.datamodel.UserProfile;
import by.dk.training.items.services.ProductService;
import by.dk.training.items.services.RecipientService;
import by.dk.training.items.services.TypeService;
import by.dk.training.items.services.UserProfileService;
import by.dk.training.items.webapp.app.renderer.PackageChoiceRenderer;
import by.dk.training.items.webapp.app.renderer.ProductChoiceRenderer;
import by.dk.training.items.webapp.app.renderer.RecipientRenderer;
import by.dk.training.items.webapp.app.renderer.TypeRenderer;
import by.dk.training.items.webapp.pages.packages.panelpackages.PackageInfo;

public class UserInfo extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ModalWindow modalWindow;
	@Inject
	private UserProfileService userProfileService;
	@Inject
	private ProductService productService;
	@Inject
	private TypeService typeService;
	@Inject
	private RecipientService recipientService;
	private UserProfile userProfile;
	private UserFilter userFilter;
	private Package inPack;
	private ProductFilter productFilter;
	private TypeFilter typeFilter;
	private RecipientFilter recipientFilter;

	public UserInfo(ModalWindow modalWindow, UserProfile userProfile) {
		super(modalWindow.getContentId());
		userFilter = new UserFilter();
		userFilter.setFetchCredentials(true);
		userFilter.setFetchPackages(true);
		userFilter.setId(userProfile.getId());
		this.userProfile = userProfileService.findUser(userFilter).get(0);
		this.modalWindow = modalWindow;
		productFilter = new ProductFilter();
		productFilter.setUser(this.userProfile);
		typeFilter = new TypeFilter();
		typeFilter.setUser(userProfile);
		recipientFilter = new RecipientFilter();
		recipientFilter.setUser(userProfile);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		final Notification notification = new Notification("notification");
		this.add(notification);
		final ModalWindow modal2 = new ModalWindow("modal2");
		modal2.setCssClassName("modal_window");
		modal2.setInitialHeight(500);
		modal2.setResizable(false);
		modal2.setWindowClosedCallback(new WindowClosedCallback() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClose(AjaxRequestTarget target) {
				target.add(UserInfo.this);

			}
		});
		this.setOutputMarkupId(true);
		add(modal2);
		add(new Label("id", userProfile.getId()));
		add(new Label("login", userProfile.getLogin()));
		add(new Label("fName", userProfile.getUserCredentials().getFirstName()));
		add(new Label("lName", userProfile.getUserCredentials().getLastName()));
		add(DateLabel.forDatePattern("usercreated", Model.of(userProfile.getUserCredentials().getCreated()),
				"dd-MM-yyyy"));
		add(new Label("status", userProfile.getUserCredentials().getStatus()));
		add(new Label("post", userProfile.getUserCredentials().getPost()));
		add(new Label("rank", userProfile.getUserCredentials().getRank()));
		add(new Label("mail", userProfile.getUserCredentials().getEmail()));
		List<Package> listPack = new ArrayList<>();
		listPack.addAll(userProfile.getPackages());
		add(new DropDownChoice<Package>("packs", new PropertyModel<Package>(this, "inPack"), listPack,
				PackageChoiceRenderer.INSTANCE).add(new AjaxFormComponentUpdatingBehavior("onchange") {

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
					notification.error(target, getString("page.users.info.error"));
				} else {
					modal2.setContent(new PackageInfo(modal2, inPack));
					modal2.show(target);
				}

			}
		});
		add(new DropDownChoice<Product>("listProd", productService.findProduct(productFilter), ProductChoiceRenderer.INSTANCE)
				.setNullValid(true));
		add(new DropDownChoice<Type>("listType", typeService.findType(typeFilter), TypeRenderer.INSTANCE)
				.setNullValid(true));
		add(new DropDownChoice<Recipient>("listRec", recipientService.findRecipient(recipientFilter), RecipientRenderer.INSTANCE)
				.setNullValid(true));
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
	}
}
