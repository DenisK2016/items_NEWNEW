package by.dk.training.items.webapp.pages.packages.panelforpackages;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow.WindowClosedCallback;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import by.dk.training.items.dataaccess.filters.PackageFilter;
import by.dk.training.items.dataaccess.filters.ProductFilter;
import by.dk.training.items.datamodel.Package;
import by.dk.training.items.datamodel.Package_;
import by.dk.training.items.datamodel.Product;
import by.dk.training.items.datamodel.UserProfile;
import by.dk.training.items.services.PackageService;
import by.dk.training.items.services.ProductService;
import by.dk.training.items.services.RecipientService;
import by.dk.training.items.services.UserProfileService;
import by.dk.training.items.webapp.app.AuthorizedSession;
import by.dk.training.items.webapp.pages.packages.PackagesPage;
import by.dk.training.items.webapp.pages.packages.formforreg.PackRegPage;
import by.dk.training.items.webapp.pages.packages.setting.SystemSettings;

@AuthorizeAction(roles = { "ADMIN", "COMMANDER" }, action = Action.RENDER)
public class ListPackagesPanel extends Panel {

	private static final long serialVersionUID = 1L;
	boolean admin = AuthorizedSession.get().getRoles().contains("ADMIN");
	@Inject
	private PackageService packageService;
	@Inject
	private UserProfileService userProfileService;
	@Inject
	private RecipientService recipientService;
	@Inject
	private ProductService productService;
	private SortablePackageProvider dataProvider;
	private PackageFilter packageFilter;
	private Long idFilter;
	private Long recipientFilter;
	private BigDecimal priceFilter;
	private Double weightFilter;
	private Long userFilter;
	private Date startDateFilter;
	private Date endDateFilter;
	private String descFilter;
	private String countryFilter;
	private boolean paidFilter;
	private Boolean fineFilter;
	private Boolean myPackFilter;
	private BigDecimal taxFilter;
	private Boolean notPaidFilter;
	private String productFilter;

	public ListPackagesPanel(String id) {
		super(id);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void onInitialize() {
		super.onInitialize();

		packageFilter = new PackageFilter();
		final ModalWindow modal1 = new ModalWindow("modal1");
		modal1.setCssClassName("modal_window");
		modal1.setInitialHeight(500);
		modal1.setResizable(false);
		modal1.setWindowClosedCallback(new WindowClosedCallback() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClose(AjaxRequestTarget target) {
				target.add(ListPackagesPanel.this);

			}
		});
		this.setOutputMarkupId(true);
		add(modal1);
		final WebMarkupContainer wmc = new WebMarkupContainer("wmc");
		wmc.setOutputMarkupId(true);
		dataProvider = new SortablePackageProvider(packageFilter);
		DataView<Package> dataView = new DataView<Package>("packagelist", dataProvider, 20) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(Item<Package> item) {
				Package pack = item.getModelObject();

				item.add(new AjaxLink<Void>("infoPackage") {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						modal1.setContent(new PackageInfo(modal1, pack));
						modal1.show(target);
					}
				});
				item.add(new Label("packageid", pack.getId()));
				item.add(new Label("price", pack.getPrice()));
				item.add(new Label("weight", pack.getWeight()));
				item.add(new Label("tax", pack.getTax()));
				Label deadline = null;
				long l = 0;
				try {
					l = (((pack.getDate().getTime() + Long.valueOf(pack.getPaymentDeadline()) * 24 * 60 * 60 * 1000)
							- (new Date().getTime())) / 1000 / 60 / 60 / 24) + 1; /// Сколько
																					/// осталось
																					/// дней
																					/// для
																					/// оплаты
																					/// посылки
					deadline = new Label("deadline", l);
					if (l < 0) {
						deadline.add(AttributeModifier.append("style", "color: red;"));
					}

				} catch (NumberFormatException e) {

				} finally {
					if (pack.getPaid()) {
						pack.setPaymentDeadline(getString("page.packages.list.paid"));
						packageService.updatePackage(pack);
						deadline = new Label("deadline", pack.getPaymentDeadline());
						deadline.add(AttributeModifier.append("style", "color: green;"));
					}
					if (!pack.getPaid() && pack.getPaymentDeadline().equals(getString("page.packages.list.paid"))) {
						l = (((pack.getDate().getTime()
								+ Long.valueOf(SystemSettings.getPaymentDeadline()) * 24 * 60 * 60 * 1000)
								- (new Date().getTime())) / 1000 / 60 / 60 / 24) + 1;
						pack.setPaymentDeadline(String.valueOf(SystemSettings.getPaymentDeadline()));
						packageService.updatePackage(pack);
						deadline = new Label("deadline", l);
						if (l < 0) {
							deadline.add(AttributeModifier.append("style", "color: red;"));
						}

					}
					item.add(deadline);
				}

				if (l < 0 && !deadline.getDefaultModelObjectAsString().equals(getString("page.packages.list.paid"))) {
					BigDecimal multiply = pack.getPercentPenalty().divide(new BigDecimal("100"))
							.multiply(new BigDecimal(l)); /// Количество
					/// процентов,
					/// в
					/// зависимости
					/// от
					/// дней
					/// просрочки(1%
					/// каждый
					/// день)
					BigDecimal multiply2 = pack.getTax().multiply(multiply); /// Сумма
																				/// штрафа,
																				/// в
																				/// завасимости
																				/// от
																				/// процента
					pack.setPenalty(multiply2.abs());
					packageService.updatePackage(pack);
				} else {
					pack.setPenalty(new BigDecimal("0"));
					packageService.updatePackage(pack);
				}
				item.add(new Label("fine", pack.getPenalty()));
				AjaxCheckBox chkb = new AjaxCheckBox("paid", Model.of(pack.getPaid())) {

					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					protected void onUpdate(AjaxRequestTarget target) {
						pack.setPaid(!pack.getPaid());
						packageService.updatePackage(pack);
						target.add(wmc);

					}
				};
				chkb.setEnabled(false);
				chkb.setVisible(false);
				item.add(chkb);
				Link<?> deleteLink = new Link("deletelink", item.getModel()) {

					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
						packageService.deletePackageWithId(pack.getId());
						setResponsePage(new PackagesPage());
					}
				};
				item.add(deleteLink);
				Link<PackRegPage> updateLink = new Link<PackRegPage>("updatePack") {

					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
						setResponsePage(new PackRegPage(pack));
					}
				};
				item.add(updateLink);
				if (admin) {
					deleteLink.setVisible(false);
					updateLink.setVisible(false);
				}

			}
		};
		wmc.add(dataView);
		add(wmc);

		wmc.add(new OrderByBorder("orderById", Package_.id, dataProvider));
		wmc.add(new OrderByBorder("orderByPrice", Package_.price, dataProvider));
		wmc.add(new OrderByBorder("orderByTax", Package_.tax, dataProvider));
		wmc.add(new OrderByBorder("orderByWeight", Package_.weight, dataProvider));
		wmc.add(new OrderByBorder("orderByDead", Package_.paymentDeadline, dataProvider));
		wmc.add(new OrderByBorder("orderByFine", Package_.penalty, dataProvider));
		wmc.add(new OrderByBorder("orderByPaid", Package_.paid, dataProvider));

		wmc.add(new PagingNavigator("navigator", dataView));

		TextField<Long> idFilt = new TextField<Long>("idFilter", new PropertyModel<Long>(this, "idFilter"));
		idFilt.add(new AjaxFormComponentUpdatingBehavior("onkeyup") {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				packageFilter.setId(idFilter);
				dataProvider.setPackageFilter(packageFilter);
				target.add(wmc);
			}
		});
		add(idFilt);

		TextField<Long> recipientFilt = new TextField<Long>("recipientFilter",
				new PropertyModel<Long>(this, "recipientFilter"));
		recipientFilt.add(new AjaxFormComponentUpdatingBehavior("onkeyup") {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				if (recipientFilter != null && recipientService.getRecipient(recipientFilter) != null) {
					packageFilter.setRecipint(recipientService.getRecipient(recipientFilter));
					dataProvider.setPackageFilter(packageFilter);
				} else if (recipientFilter != null && recipientService.getRecipient(recipientFilter) == null) {
					PackageFilter pf = new PackageFilter();
					pf.setId(0L);
					dataProvider.setPackageFilter(pf);
				} else {
					packageFilter.setRecipint(null);
					dataProvider.setPackageFilter(packageFilter);
				}
				target.add(wmc);
			}
		});
		add(recipientFilt);

		TextField<BigDecimal> priceFilt = new TextField<BigDecimal>("priceFilter",
				new PropertyModel<BigDecimal>(this, "priceFilter"));
		priceFilt.add(new AjaxFormComponentUpdatingBehavior("onkeyup") {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				packageFilter.setPrice(priceFilter);
				dataProvider.setPackageFilter(packageFilter);
				target.add(wmc);
			}
		});
		add(priceFilt);

		TextField<Double> weightFilt = new TextField<Double>("weightFilter",
				new PropertyModel<Double>(this, "weightFilter"));
		weightFilt.add(new AjaxFormComponentUpdatingBehavior("onkeyup") {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				packageFilter.setWeight(weightFilter);
				dataProvider.setPackageFilter(packageFilter);
				target.add(wmc);
			}
		});
		add(weightFilt);

		TextField<BigDecimal> taxFilt = new TextField<BigDecimal>("taxFilter",
				new PropertyModel<BigDecimal>(this, "taxFilter"));
		taxFilt.add(new AjaxFormComponentUpdatingBehavior("onkeyup") {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				packageFilter.setTax(taxFilter);
				dataProvider.setPackageFilter(packageFilter);
				target.add(wmc);
			}
		});
		add(taxFilt);

		TextField<Long> userFilt = new TextField<Long>("userFilter", new PropertyModel<Long>(this, "userFilter"));
		userFilt.add(new AjaxFormComponentUpdatingBehavior("onkeyup") {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				if (userFilter != null && userProfileService.getUser(userFilter) != null) {
					packageFilter.setUser(userProfileService.getUser(userFilter));
					dataProvider.setPackageFilter(packageFilter);
				} else if (userFilter != null && userProfileService.getUser(userFilter) == null) {
					PackageFilter pf = new PackageFilter();
					pf.setId(0L);
					dataProvider.setPackageFilter(pf);
				} else {
					packageFilter.setUser(null);
					dataProvider.setPackageFilter(packageFilter);
				}
				target.add(wmc);
			}
		});
		add(userFilt);

		DateTextField startDateFilt = new DateTextField("startDateFilter", new PropertyModel<>(this, "startDateFilter"),
				"dd-MM-yyyy");
		startDateFilt.add(new AjaxFormComponentUpdatingBehavior("onchange") {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				packageFilter.setStartDate(startDateFilter);
				dataProvider.setPackageFilter(packageFilter);
				target.add(wmc);
			}
		});
		startDateFilt.add(new AjaxFormComponentUpdatingBehavior("onkeyup") {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				packageFilter.setStartDate(startDateFilter);
				dataProvider.setPackageFilter(packageFilter);
				target.add(wmc);
			}

		});
		startDateFilt.add(new DatePicker());
		add(startDateFilt);

		DateTextField endDateFilt = new DateTextField("endDateFilter", new PropertyModel<>(this, "endDateFilter"),
				"dd-MM-yyyy");
		endDateFilt.add(new AjaxFormComponentUpdatingBehavior("onchange") {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				packageFilter.setEndDate(endDateFilter);
				dataProvider.setPackageFilter(packageFilter);
				target.add(wmc);
			}
		});
		endDateFilt.add(new AjaxFormComponentUpdatingBehavior("onkeyup") {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				packageFilter.setEndDate(endDateFilter);
				dataProvider.setPackageFilter(packageFilter);
				target.add(wmc);
			}

		});
		endDateFilt.add(new DatePicker());
		add(endDateFilt);

		TextField<String> descFilt = new TextField<String>("descFilter", new PropertyModel<String>(this, "descFilter"));
		descFilt.add(new AjaxFormComponentUpdatingBehavior("onkeyup") {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				packageFilter.setDescription(descFilter);
				dataProvider.setPackageFilter(packageFilter);
				target.add(wmc);
			}
		});
		add(descFilt);

		TextField<String> countryFilt = new TextField<String>("countryFilter",
				new PropertyModel<String>(this, "countryFilter"));
		countryFilt.add(new AjaxFormComponentUpdatingBehavior("onkeyup") {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				packageFilter.setCountrySender(countryFilter);
				dataProvider.setPackageFilter(packageFilter);
				target.add(wmc);
			}
		});
		add(countryFilt);

		AjaxCheckBox paidFilt = new AjaxCheckBox("paidFilter", new PropertyModel<Boolean>(this, "paidFilter")) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				if (paidFilter == true) {
					packageFilter.setPaid(paidFilter);
				} else {
					packageFilter.setPaid(null);
				}
				dataProvider.setPackageFilter(packageFilter);
				target.add(wmc);
			}
		};
		add(paidFilt);

		AjaxCheckBox fineFilt = new AjaxCheckBox("fineFilter", new PropertyModel<Boolean>(this, "fineFilter")) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				if (fineFilter == true) {
					packageFilter.setPaid(false);
				} else {

					packageFilter.setPaid(null);
				}
				dataProvider.setPackageFilter(packageFilter);
				target.add(wmc);
			}
		};
		add(fineFilt);

		AjaxCheckBox notPaidFilt = new AjaxCheckBox("notPaidFilter",
				new PropertyModel<Boolean>(this, "notPaidFilter")) {

			/**
					 * 
					 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				if (notPaidFilter == true) {
					packageFilter.setPaid(false);
				} else {

					packageFilter.setPaid(null);
				}
				dataProvider.setPackageFilter(packageFilter);
				target.add(wmc);
			}
		};
		add(notPaidFilt);

		AjaxCheckBox myPackFilt = new AjaxCheckBox("myPackFilter", new PropertyModel<Boolean>(this, "myPackFilter")) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				if (myPackFilter) {
					Long idUser = AuthorizedSession.get().getUser().getId();
					UserProfile currUser = userProfileService.getUser(idUser);
					packageFilter.setUser(currUser);
				} else {
					packageFilter.setUser(null);
				}
				dataProvider.setPackageFilter(packageFilter);
				target.add(wmc);
			}
		};
		add(myPackFilt);
		TextField<String> productFilt = new TextField<String>("productFilter",
				new PropertyModel<String>(this, "productFilter"));
		productFilt.add(new AjaxFormComponentUpdatingBehavior("onkeyup") {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				ProductFilter filter = new ProductFilter();
				filter.setNameProduct(productFilter);
				List<Product> find = productService.findProduct(filter);
				Product product = null;
				if (productFilter != null && !find.isEmpty()) {
					product = find.get(0);
					packageFilter.setProduct(product);
				} else if (productFilter != null && find.isEmpty()) {
					product = new Product();
					product.setId(0L);
					packageFilter.setProduct(product);
				} else {
					packageFilter.setProduct(null);
				}
				dataProvider.setPackageFilter(packageFilter);
				target.add(wmc);
			}
		});
		add(productFilt);

	}

	private class SortablePackageProvider extends SortableDataProvider<Package, Serializable> {

		private static final long serialVersionUID = 1L;

		private PackageFilter packageFilter;

		public void setPackageFilter(PackageFilter packageFilter) {
			this.packageFilter = packageFilter;
		}

		public SortablePackageProvider(PackageFilter packageFilter) {
			super();
			this.packageFilter = packageFilter;
			packageFilter.setFetchProduct(true);
			packageFilter.setFetchRecipient(true);
			packageFilter.setFetchUser(true);
			setSort((Serializable) Package_.id, SortOrder.ASCENDING);
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		public Iterator<Package> iterator(long first, long count) {

			Serializable property = getSort().getProperty();
			SortOrder propertySortOrder = getSortState().getPropertySortOrder(property);

			packageFilter.setSortProperty((SingularAttribute) property);
			packageFilter.setSortOrder(propertySortOrder.equals(SortOrder.ASCENDING) ? true : false);

			packageFilter.setLimit((int) count);
			packageFilter.setOffset((int) first);
			List<Package> lis = new ArrayList<>();

			boolean ff = (fineFilter != null && fineFilter == true);
			boolean npf = (notPaidFilter != null && notPaidFilter == true);
			if (ff & npf) {
				return packageService.findPackage(packageFilter).iterator();
			}
			if (ff) {
				for (Package p : packageService.findPackage(packageFilter)) {
					try {
						long l = (((p.getDate().getTime() + Long.valueOf(p.getPaymentDeadline()) * 24 * 60 * 60 * 1000)
								- (new Date().getTime())) / 1000 / 60 / 60 / 24) + 1;
						if (l < 0) {

							lis.add(p);
						}
					} catch (NumberFormatException e) {
						continue;
					}
				}

				return lis.iterator();
			}
			if (npf) {
				for (Package p : packageService.findPackage(packageFilter)) {
					try {
						long l = (((p.getDate().getTime() + Long.valueOf(p.getPaymentDeadline()) * 24 * 60 * 60 * 1000)
								- (new Date().getTime())) / 1000 / 60 / 60 / 24) + 1;
						if (l >= 0) {
							lis.add(p);
						}
					} catch (NumberFormatException e) {
						continue;
					}
				}
				return lis.iterator();
			}
			return packageService.findPackage(packageFilter).iterator();

		}

		@Override
		public long size() {
			return packageService.overallNumberOfPackages(packageFilter);
		}

		@Override
		public IModel<Package> model(Package object) {
			return new Model<Package>(object);
		}

	}

}
