package by.dk.training.items.webapp.pages.types.panelfortypes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow.WindowClosedCallback;
import org.apache.wicket.extensions.markup.html.repeater.tree.DefaultNestedTree;
import org.apache.wicket.extensions.markup.html.repeater.tree.ITreeProvider;
import org.apache.wicket.extensions.markup.html.repeater.tree.content.Folder;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;

import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButtons;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogIcon;
import com.googlecode.wicket.jquery.ui.widget.dialog.MessageDialog;
import com.googlecode.wicket.jquery.ui.widget.menu.ContextMenu;
import com.googlecode.wicket.jquery.ui.widget.menu.ContextMenuBehavior;
import com.googlecode.wicket.jquery.ui.widget.menu.IMenuItem;
import com.googlecode.wicket.jquery.ui.widget.menu.MenuItem;

import by.dk.training.items.dataaccess.filters.TypeFilter;
import by.dk.training.items.datamodel.Type;
import by.dk.training.items.services.TypeService;
import by.dk.training.items.webapp.pages.types.TypePage;

@AuthorizeAction(roles = { "ADMIN", "COMMANDER", "OFFICER" }, action = Action.RENDER)
public class ListTypesPanel extends Panel {

	private static final long serialVersionUID = 1L;
	@Inject
	private TypeService typeService;
	private ModalWindow modalInfo;
	private ContextMenu menu;

	public ListTypesPanel(String id) {
		super(id);

	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		modalInfo = new ModalWindow("modalInfo");
		modalInfo.setCssClassName("modal_window");
		modalInfo.setInitialHeight(400);
		modalInfo.setResizable(false);
		modalInfo.setOutputMarkupId(true);

		modalInfo.setWindowClosedCallback(new WindowClosedCallback() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClose(AjaxRequestTarget target) {
				target.add(ListTypesPanel.this);
			}
		});
		this.setOutputMarkupId(true);
		add(modalInfo);
		final MessageDialog errorDialog = new MessageDialog("errorDialog",
				new StringResourceModel("page.products.dialog.error.title", this, null),
				new StringResourceModel("page.types.error", this, null), DialogButtons.OK, DialogIcon.ERROR) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClose(IPartialPageRequestHandler handler, DialogButton button) {
				setResponsePage(new TypePage());
			}
		};
		this.add(errorDialog);
		Form<Object> form = new Form<>("form");
		FeedbackPanel feedBack = new FeedbackPanel("feedback");
		feedBack.setOutputMarkupId(true);
		feedBack.setVisible(false);
		form.add(feedBack);

		menu = new ContextMenu("contextMenu", newMenuItemList()) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onContextMenu(AjaxRequestTarget target, Component component) {
				this.getItemList().clear();
				List<IMenuItem> listMenu = this.getItemList();
				IMenuItem menuOne = new MenuItem(getString("page.types.menu1")) {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						modalInfo.setContent(new TypeInfo(modalInfo, (Type) component.getDefaultModelObject()));
						modalInfo.show(target);
						super.onClick(target);
					}
				};
				IMenuItem menuTwo = new MenuItem(getString("page.types.menu2")) {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						modalInfo.setContent(new TypeProductsInfo(modalInfo, (Type) component.getDefaultModelObject()));
						modalInfo.show(target);
						super.onClick(target);
					}
				};
				IMenuItem menuThree = new MenuItem(getString("page.types.menu3")) {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						Type currentType = (Type) component.getDefaultModelObject();
						try {
							typeService.delete(currentType.getId());
							setResponsePage(new TypePage());
						} catch (PersistenceException e) {
							errorDialog.open(target);
						}
						super.onClick(target);
					}
				};
				listMenu.add(menuTwo);
				listMenu.add(menuOne);
				listMenu.add(menuThree);
			}

			@Override
			public void onClick(AjaxRequestTarget target, IMenuItem item) {
				target.add(this);
				target.add(feedBack);
			}
		};
		menu.setOutputMarkupId(true);
		add(menu);

		DefaultNestedTree<Type> tree = new DefaultNestedTree<Type>("treeTypes", new TreeTypeProvider()) {

			private static final long serialVersionUID = 1L;

			@Override
			protected Component newContentComponent(String id, IModel<Type> node) {

				Folder<Type> fold = new Folder<Type>(id, this, node) {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					protected IModel<?> newLabelModel(IModel<Type> model) {
						return new PropertyModel<String>(model, "typeName");
					}

					@Override
					protected boolean isClickable() {
						return true;
					}
				};
				fold.add(new ContextMenuBehavior(menu));
				return fold;

			}
		};
		tree.add(AttributeModifier.append("style", "width: 20%; color: #cec7c7;"));
		tree.setOutputMarkupId(true);
		form.add(tree);
		form.setOutputMarkupId(true);
		add(form);

	}

	static List<IMenuItem> newMenuItemList() {
		List<IMenuItem> list = new ArrayList<IMenuItem>();

		return list;
	}

	private class TreeTypeProvider implements ITreeProvider<Type> {

		private static final long serialVersionUID = 1L;
		private TypeFilter typeFilter;

		public TreeTypeProvider() {
			typeFilter = new TypeFilter();
			typeFilter.setFetchParentType(true);
			typeFilter.setFetchUser(true);
			typeFilter.setFetchChildTypes(true);
		}

		@Override
		public void detach() {

		}

		@Override
		public Iterator<Type> getRoots() {
			typeFilter.setId(null);
			List<Type> listT = new ArrayList<Type>(); // Типы у которых нет
														// парентов
			for (Type t : typeService.find(typeFilter)) {
				if (t.getParentType() == null) {
					listT.add(t);
				}
			}
			return listT.iterator();
		}

		@Override
		public boolean hasChildren(Type type) {
			typeFilter.setId(type.getId());
			return type.getChildTypes() == null || !typeService.find(typeFilter).get(0).getChildTypes().isEmpty();
		}

		@Override
		public Iterator<Type> getChildren(Type type) {
			return type.getChildTypes().iterator();
		}

		@Override
		public IModel<Type> model(Type object) {
			return new Model<Type>(object);
		}

	}

}
