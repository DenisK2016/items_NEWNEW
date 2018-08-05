package by.dk.training.items.webapp.pages.products.panelproducts;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow.WindowClosedCallback;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;

import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButtons;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogIcon;
import com.googlecode.wicket.jquery.ui.widget.dialog.MessageDialog;

import by.dk.training.items.dataaccess.filters.ProductFilter;
import by.dk.training.items.dataaccess.filters.TypeFilter;
import by.dk.training.items.datamodel.Product;
import by.dk.training.items.datamodel.Product_;
import by.dk.training.items.datamodel.Type;
import by.dk.training.items.datamodel.UserProfile;
import by.dk.training.items.services.ProductService;
import by.dk.training.items.services.TypeService;
import by.dk.training.items.services.UserProfileService;
import by.dk.training.items.webapp.app.AuthorizedSession;
import by.dk.training.items.webapp.pages.products.ProductPage;
import by.dk.training.items.webapp.pages.products.formreg.RegistryProductPanel;

@AuthorizeAction(roles = { "ADMIN", "COMMANDER", "OFFICER" }, action = Action.RENDER)
public class ListProductsPanel extends Panel {

	private static final long serialVersionUID = 1L;
	boolean officer = AuthorizedSession.get().getRoles().contains("OFFICER");
	boolean admin = AuthorizedSession.get().getRoles().contains("ADMIN");
	boolean commander = AuthorizedSession.get().getRoles().contains("COMMANDER");
	@Inject
	private ProductService productService;
	@Inject
	private UserProfileService userProfileService;
	@Inject
	private TypeService typeService;
	private SortableProductProvider dataProvider;
	private ProductFilter productFilter;
	private Long idFilter;
	private String nameFilter;
	private BigDecimal priceFilter;
	private Double weightFilter;
	private Boolean bool;
	private Boolean myProduct;
	private Long idUser;
	private String byTypeFilter;

	public ListProductsPanel(String id) {
		super(id);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void onInitialize() {
		super.onInitialize();
		productFilter = new ProductFilter();
		final MessageDialog errorDialog = new MessageDialog("errorDialog",
				new StringResourceModel("page.products.dialog.error.title", this, null),
				new StringResourceModel("page.products.dialog.error.message", this, null), DialogButtons.OK,
				DialogIcon.ERROR) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClose(IPartialPageRequestHandler handler, DialogButton button) {
				setResponsePage(new ProductPage());
			}
		};
		this.add(errorDialog);
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
				target.add(ListProductsPanel.this);

			}
		});
		this.setOutputMarkupId(true);
		add(modal1);
		final ModalWindow modalUpdate = new ModalWindow("modalUpdate");
		modalUpdate.setCssClassName("modal_window");
		modalUpdate.setResizable(false);
		modalUpdate.setWindowClosedCallback(new WindowClosedCallback() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClose(AjaxRequestTarget target) {
				target.add(ListProductsPanel.this);

			}
		});
		this.setOutputMarkupId(true);
		add(modalUpdate);
		dataProvider = new SortableProductProvider(productFilter);
		DataView<Product> dataView = new DataView<Product>("productlist", dataProvider, 20) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(Item<Product> item) {
				Product product = item.getModelObject();
				item.add(new AjaxLink<Void>("infoProduct") {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						modal1.setContent(new ProductInfo(modal1, product));
						modal1.show(target);
					}
				}.add(AttributeModifier.append("title", getString("page.products.info.title"))));
				item.add(new Label("productid", product.getId())
						.add(AttributeModifier.append("title", getString("page.products.id.title"))));
				item.add(new Label("productname", product.getNameProduct())
						.add(AttributeModifier.append("title", getString("page.products.name.title"))));
				item.add(new Label("productprice", product.getPriceProduct())
						.add(AttributeModifier.append("title", getString("page.products.price.title"))));
				item.add(new Label("productweight", product.getWeight())
						.add(AttributeModifier.append("title", getString("page.products.weight.title"))));
				item.add(new CheckBox("productstatus", Model.of(product.getStatus())).setEnabled(false)
						.add(AttributeModifier.append("title", getString("page.products.status.title"))));
				Long id = product.getIdUser().getId();
				Label idUser = new Label("idUser", id);
				idUser.add(AttributeModifier.append("title", getString("page.products.creater.title")));
				item.add(idUser);
				AjaxLink<?> delLink = new AjaxLink("deletelink", item.getModel()) {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						try {
							productService.delete(product.getId());
							setResponsePage(new ProductPage());
						} catch (PersistenceException e) {
							errorDialog.open(target);
						}

					}
				};
				delLink.add(AttributeModifier.append("title", getString("page.products.delete.title")));
				item.add(delLink);
				if (officer) {
					delLink.setVisible(false);
					idUser.setVisible(false);
				}
				AjaxLink<Void> update = new AjaxLink<Void>("updateLink") {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						modalUpdate.setContent(new RegistryProductPanel(modalUpdate, product));
						modalUpdate.show(target);
					}
				};
				update.add(AttributeModifier.append("title", getString("page.products.update.title")));
				item.add(update);
				Long idCurrent = AuthorizedSession.get().getUser().getId();
				if (idCurrent != id && !admin && !commander) {
					update.setVisible(false);
				}
				if (admin) {
					delLink.setVisible(false);
					update.setVisible(false);
				}
			}
		};
		WebMarkupContainer wmk = new WebMarkupContainer("container");
		wmk.setOutputMarkupId(true);
		wmk.add(dataView);
		add(wmk);
		wmk.add(new OrderByBorder("orderById", Product_.id, dataProvider)
				.add(AttributeModifier.append("title", getString("page.products.sort.id"))));
		wmk.add(new OrderByBorder("orderByProductName", Product_.nameProduct, dataProvider)
				.add(AttributeModifier.append("title", getString("page.products.sort.name"))));
		wmk.add(new OrderByBorder("orderByPrice", Product_.priceProduct, dataProvider)
				.add(AttributeModifier.append("title", getString("page.products.sort.price"))));
		wmk.add(new OrderByBorder("orderByWeight", Product_.weight, dataProvider)
				.add(AttributeModifier.append("title", getString("page.products.sort.weight"))));
		wmk.add(new OrderByBorder("orderByStatus", Product_.status, dataProvider)
				.add(AttributeModifier.append("title", getString("page.products.sort.status"))));
		OrderByBorder ord = new OrderByBorder("orderByUser", Product_.idUser, dataProvider);
		ord.add(AttributeModifier.append("title", getString("page.products.sort.creater")));
		wmk.add(ord);
		if (officer) {
			ord.setVisible(false);
		}
		wmk.add(new PagingNavigator("navigator", dataView));
		add(wmk);
		TextField<Long> idFilt = new TextField<Long>("idFilter", new PropertyModel<Long>(this, "idFilter"));
		idFilt.add(new AjaxFormComponentUpdatingBehavior("keyup") {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				productFilter.setId(idFilter);
				dataProvider.setProductFilter(productFilter);
				target.add(wmk);
			}
		});
		add(idFilt);
		TextField<String> nameFilt = new TextField<String>("nameFilter", new PropertyModel<String>(this, "nameFilter"));
		nameFilt.add(new AjaxFormComponentUpdatingBehavior("keyup") {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				productFilter.setNameProduct(nameFilter);
				dataProvider.setProductFilter(productFilter);
				target.add(wmk);
			}
		});
		add(nameFilt);

		TextField<BigDecimal> priceFilt = new TextField<BigDecimal>("priceFilter",
				new PropertyModel<BigDecimal>(this, "priceFilter"));
		priceFilt.add(new AjaxFormComponentUpdatingBehavior("keyup") {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				productFilter.setPriceProduct(priceFilter);
				dataProvider.setProductFilter(productFilter);
				target.add(wmk);
			}
		});
		add(priceFilt);

		TextField<Double> weightFilt = new TextField<Double>("weightFilter",
				new PropertyModel<Double>(this, "weightFilter"));
		weightFilt.add(new AjaxFormComponentUpdatingBehavior("keyup") {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				productFilter.setWeightProduct(weightFilter);
				dataProvider.setProductFilter(productFilter);
				target.add(wmk);
			}
		});
		add(weightFilt);
		TextField<Long> idUse = new TextField<Long>("userFilter", new PropertyModel<Long>(this, "idUser"));
		idUse.add(new AjaxFormComponentUpdatingBehavior("keyup") {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				if (idUser != null && userProfileService.getUser(idUser) != null) {
					productFilter.setUser(userProfileService.getUser(idUser));
					dataProvider.setProductFilter(productFilter);
				} else if (idUser != null && userProfileService.getUser(idUser) == null) {
					ProductFilter pf = new ProductFilter();
					pf.setId(0L);
					dataProvider.setProductFilter(pf);
				} else {
					productFilter.setUser(null);
					dataProvider.setProductFilter(productFilter);
				}
				target.add(wmk);
			}
		});
		add(idUse);
		if (officer) {
			idUse.setVisible(false);
		}
		AjaxCheckBox chkb = new AjaxCheckBox("status", new PropertyModel<Boolean>(this, "bool")) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				productFilter.setStatus(bool);
				dataProvider.setProductFilter(productFilter);
				target.add(wmk);
			}
		};
		chkb.add(AttributeModifier.append("title", getString("page.products.chk2")));
		add(chkb);
		AjaxCheckBox myProd = new AjaxCheckBox("myProd", new PropertyModel<Boolean>(this, "myProduct")) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				if (myProduct) {
					Long idUser = AuthorizedSession.get().getUser().getId();
					UserProfile currUser = userProfileService.getUser(idUser);
					productFilter.setUser(currUser);
				} else {
					productFilter.setUser(null);
				}
				dataProvider.setProductFilter(productFilter);
				target.add(wmk);
			}
		};
		myProd.add(AttributeModifier.append("title", getString("page.products.chk3")));
		add(myProd);
		if (admin) {
			myProd.setVisible(false);
		}
		TextField<Long> byType = new TextField<Long>("typeFilter", new PropertyModel<Long>(this, "byTypeFilter"));
		byType.add(new AjaxFormComponentUpdatingBehavior("keyup") {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				TypeFilter filter = new TypeFilter();
				filter.setTypeName(byTypeFilter);
				List<Type> type = typeService.findType(filter);
				if (byTypeFilter != null && !type.isEmpty()) {
					productFilter.setTypes(type.get(0));
					dataProvider.setProductFilter(productFilter);
				} else if (type.isEmpty()) {
					Type t = new Type();
					t.setId(0L);
					productFilter.setTypes(t);
					dataProvider.setProductFilter(productFilter);
				} else {
					productFilter.setTypes(null);
					dataProvider.setProductFilter(productFilter);
				}
				target.add(wmk);
			}
		});
		add(byType);
	}

	private class SortableProductProvider extends SortableDataProvider<Product, Serializable> {

		private static final long serialVersionUID = 1L;
		private ProductFilter productFilter;

		public void setProductFilter(ProductFilter productFilter) {
			this.productFilter = productFilter;
		}

		public SortableProductProvider(ProductFilter productFilter) {
			super();
			this.productFilter = productFilter;
			productFilter.setFetchUser(true);
			setSort((Serializable) Product_.id, SortOrder.ASCENDING);
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		public Iterator<Product> iterator(long first, long count) {

			Serializable property = getSort().getProperty();
			SortOrder propertySortOrder = getSortState().getPropertySortOrder(property);
			productFilter.setSortProperty((SingularAttribute) property);
			productFilter.setSortOrder(propertySortOrder.equals(SortOrder.ASCENDING) ? true : false);
			productFilter.setLimit((int) count);
			productFilter.setOffset((int) first);
			return productService.findProduct(productFilter).iterator();

		}

		@Override
		public long size() {
			return productService.overallNumberOfProducts(productFilter);
		}

		@Override
		public IModel<Product> model(Product object) {
			return new Model<Product>(object);
		}
	}
}
