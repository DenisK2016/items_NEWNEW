package by.dk.training.items.webapp.pages.profilemenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow.WindowClosedCallback;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ListChoice;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.googlecode.wicket.jquery.ui.markup.html.link.Link;
import com.googlecode.wicket.kendo.ui.widget.notification.Notification;

import by.dk.training.items.dataaccess.filters.UserFilter;
import by.dk.training.items.datamodel.Package;
import by.dk.training.items.datamodel.UserProfile;
import by.dk.training.items.services.PackageService;
import by.dk.training.items.services.UserProfileService;
import by.dk.training.items.webapp.pages.home.HomePage;
import by.dk.training.items.webapp.pages.packages.panelforpackages.PackageInfo;
import by.dk.training.items.webapp.pages.profilemenu.modalwindows.EditInfoPanel;

@AuthorizeAction(roles = { "ADMIN", "COMMANDER", "OFFICER" }, action = Action.RENDER)
public class ProfileMenuPanel extends Panel {

	@Inject
	private UserProfileService userProfileService;
	private UserProfile userProfile;
	private Set<Package> listPack;
	private List<Long> listId = new ArrayList<>();
	private UserFilter filt;
	@Inject
	private PackageService packageService;
	private Long idPack;

	public ProfileMenuPanel(String id, UserProfile userProfile) {
		super(id);
		filt = new UserFilter();
		filt.setFetchCredentials(true);
		filt.setFetchPackages(true);
		filt.setLogin(userProfile.getLogin());
		this.userProfile = userProfileService.find(filt).get(0);
		listPack = this.userProfile.getPackages();
	}

	private static final long serialVersionUID = 1L;

	@Override
	protected void onInitialize() {
		super.onInitialize();
		final Notification notification = new Notification("notification");
		this.add(notification);
		WebMarkupContainer wmk = new WebMarkupContainer("container");
		wmk.setOutputMarkupId(true);
		add(wmk);
		wmk.add(new Link<HomePage>("tohome") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(new HomePage());

			}
		});

		wmk.add(new Label("login", Model.of(userProfile.getLogin())));
		wmk.add(new Label("pass", Model.of(userProfile.getPassword())));
		wmk.add(new Label("firstName", Model.of(userProfile.getUserCredentials().getFirstName())));
		wmk.add(new Label("lastName", Model.of(userProfile.getUserCredentials().getLastName())));
		wmk.add(DateLabel.forDatePattern("usercreated", Model.of(userProfile.getUserCredentials().getCreated()),
				"dd-MM-yyyy"));
		wmk.add(new Label("status", userProfile.getUserCredentials().getStatus().name()));
		wmk.add(new Label("post", Model.of(userProfile.getUserCredentials().getPost())));
		wmk.add(new Label("rank", Model.of(userProfile.getUserCredentials().getRank())));
		wmk.add(new Label("email", Model.of(userProfile.getUserCredentials().getEmail())));
		for (Package p : listPack) {
			listId.add(p.getId());
		}
		ListChoice<Long> listChoice = new ListChoice<Long>("packages", new PropertyModel<Long>(this, "idPack"), listId)
				.setMaxRows(10);
		listChoice.add(new AjaxFormComponentUpdatingBehavior("change") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
			}
		});
		listChoice.setNullValid(true);
		wmk.add(listChoice);

		final ModalWindow modal1;
		wmk.add(modal1 = new ModalWindow("modal1"));
		modal1.setOutputMarkupId(true);
		modal1.setCssClassName("modal_window");
		modal1.setResizable(false);
		modal1.setWindowClosedCallback(new WindowClosedCallback() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClose(AjaxRequestTarget target) {
				target.add(wmk);

			}
		});
		this.setOutputMarkupId(true);

		this.setOutputMarkupId(true);
		wmk.add(new AjaxLink<Void>("infoPackage") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				if (idPack == null) {
					notification.error(target, getString("page.recipients.info.error"));
				} else {
					modal1.setContent(new PackageInfo(modal1, packageService.getPackage(idPack)));
					modal1.show(target);
				}
			}
		});
		wmk.add(new AjaxLink<Void>("setPassword") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				modal1.setContent(new EditInfoPanel(modal1, userProfile, "password"));
				modal1.show(target);
			}
		});

		wmk.add(new AjaxLink<Void>("setFirstName") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				modal1.setContent(new EditInfoPanel(modal1, userProfile, "firstName"));
				modal1.show(target);
			}
		});
		wmk.add(new AjaxLink<Void>("setLastName") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				modal1.setContent(new EditInfoPanel(modal1, userProfile, "lastName"));
				modal1.show(target);
			}
		});
		wmk.add(new AjaxLink<Void>("setPost") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				modal1.setContent(new EditInfoPanel(modal1, userProfile, "post"));
				modal1.show(target);
			}
		});
		wmk.add(new AjaxLink<Void>("setRank") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				modal1.setContent(new EditInfoPanel(modal1, userProfile, "ranks"));
				modal1.show(target);
			}
		});

	}

}
