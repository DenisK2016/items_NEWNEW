package by.dk.training.items.webapp.pages.packages.formreg;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.RangeValidator;
import org.apache.wicket.validation.validator.StringValidator;

import com.googlecode.wicket.jquery.ui.form.spinner.AjaxSpinner;
import com.googlecode.wicket.jquery.ui.markup.html.link.AjaxSubmitLink;
import com.googlecode.wicket.kendo.ui.widget.notification.Notification;

import by.dk.training.items.dataaccess.filters.PackageFilter;
import by.dk.training.items.dataaccess.filters.RecipientFilter;
import by.dk.training.items.dataaccess.filters.UserFilter;
import by.dk.training.items.datamodel.Package;
import by.dk.training.items.datamodel.Product;
import by.dk.training.items.datamodel.Recipient;
import by.dk.training.items.datamodel.UserProfile;
import by.dk.training.items.services.PackageService;
import by.dk.training.items.services.ProductService;
import by.dk.training.items.services.RecipientService;
import by.dk.training.items.services.UserProfileService;
import by.dk.training.items.webapp.app.AuthorizedSession;
import by.dk.training.items.webapp.app.renderer.ProductChoiceRenderer;
import by.dk.training.items.webapp.app.renderer.RecipientRenderer;
import by.dk.training.items.webapp.app.renderer.UserProfileRenderer;
import by.dk.training.items.webapp.pages.packages.PackagesPage;
import by.dk.training.items.webapp.pages.packages.setting.SystemSettings;

@AuthorizeAction(roles = { "COMMANDER", "OFFICER" }, action = Action.RENDER)
public class RegistryPackPanel extends Panel {

	private static final long serialVersionUID = 5925910399876777497L;

	@Inject
	private PackageService packageService;
	@Inject
	private RecipientService recipientService;
	@Inject
	private UserProfileService userProfileService;
	@Inject
	private ProductService productService;

	private Long idPack;
	private Product inProducts;
	private List<Product> listProd = new ArrayList<>();
	private Integer spin = 1;
	private UserFilter userFilter;
	private RecipientFilter recipientFilter;
	private PackageFilter packageFilter;
	private Package pack;

	private List<Product> allProducts;
	boolean officer;
	private List<Recipient> allRecipients;
	private List<UserProfile> allUsers;
	private final List<String> status;
	private String stat;

	private static BigDecimal maxPrice = SystemSettings.getMaxPrice();
	private static BigDecimal percent = SystemSettings.getPercent().divide(BigDecimal.valueOf(100L));

	{
		allProducts = productService.getAll();
		officer = AuthorizedSession.get().getRoles().contains("OFFICER");
		allRecipients = recipientService.getAll();
		allUsers = userProfileService.getAll();
		status = Arrays
				.asList(new String[] { getString("page.packages.reg.status"), getString("page.packages.reg.status1") });
		stat = getString("page.packages.reg.status1");
	}

	public RegistryPackPanel(String id) {
		super(id);
		pack = new Package();
		pack.setPaymentDeadline(SystemSettings.getPaymentDeadline());

	}

	public RegistryPackPanel(String id, Package pack) {
		super(id);
		packageFilter = new PackageFilter();
		packageFilter.setId(pack.getId());
		packageFilter.setFetchProduct(true);
		packageFilter.setFetchRecipient(true);
		packageFilter.setFetchUser(true);
		setPack(packageService.findPackage(packageFilter).get(0));
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		createFilters();

		final Notification notification = new Notification("notification");
		this.add(notification);

		final WebMarkupContainer formRegPackMarcupContainer = new WebMarkupContainer("wmcForm");
		formRegPackMarcupContainer.setOutputMarkupId(true);

		Form<Package> registrationPackageForm = new Form<Package>("formRegPack",
				new CompoundPropertyModel<Package>(pack));

		FeedbackPanel feedBackPanel = new FeedbackPanel("feedback");
		feedBackPanel.setVisible(false);
		formRegPackMarcupContainer.add(feedBackPanel);

		registrationPackageForm.add(formRegPackMarcupContainer);

		extractIdPack();

		addIdFieldToFRPContainer(formRegPackMarcupContainer);

		Date startMonth = new Date();
		startMonth(startMonth);
		addDropDownRecipientsToFRPContainer(formRegPackMarcupContainer, startMonth);

		addPriceFieldToFRPContainer(formRegPackMarcupContainer);

		addWeightFieldToFLPContainer(formRegPackMarcupContainer);

		addTaxFieldToFRPContainer(formRegPackMarcupContainer);
		addUsersDropDownToFRPContainer(formRegPackMarcupContainer);
		addDateFieldToFRPContainer(formRegPackMarcupContainer);

		addDescriptionFieldToFRPContainer(formRegPackMarcupContainer);

		addCountrySenderFieldToFRPContainer(formRegPackMarcupContainer);
		addPaymentDeadlineToFRPContainer(formRegPackMarcupContainer);
		addPenaltyFieldToFRPContainer(formRegPackMarcupContainer);
		addPaidRadioToFRPContainer(formRegPackMarcupContainer);
		addSavePackageAjaxLinkToFRPContainer(notification, formRegPackMarcupContainer, feedBackPanel);

		Form<Integer> formProducts = new Form<Integer>("formProd", Model.of(0));

		addProductDropDownToFormProducts(formProducts);

		final WebMarkupContainer formProductsMarcupContainer = new WebMarkupContainer("wmc");
		formProductsMarcupContainer.setOutputMarkupId(true);
		formProducts.add(formProductsMarcupContainer);

		addSelectProductAjaxButtonToFormProducts(notification, formRegPackMarcupContainer, startMonth, formProducts,
				formProductsMarcupContainer);
		addListProductsListViewToFPContainer(formProductsMarcupContainer);

		addExistProductsListViewToFPContainer(formProductsMarcupContainer);
		add(formProducts);
		add(registrationPackageForm);
		addBackToPackagesLink();
		addAjaxSpinnerToFormProducts(formProducts);
		addLableToFRPContainer(formRegPackMarcupContainer);
	}

	private void addLableToFRPContainer(final WebMarkupContainer formRegPackMarcupContainer) {
		if (pack.getId() == null) {
			formRegPackMarcupContainer.add(new Label("regOrUpdate", getString("page.packages.reg.title")));
		} else {
			formRegPackMarcupContainer.add(new Label("regOrUpdate", getString("page.packages.reg.title1")));
		}
	}

	private void addAjaxSpinnerToFormProducts(Form<Integer> formProducts) {
		final AjaxSpinner<Integer> ajaxSpinner = new AjaxSpinner<Integer>("spinner", formProducts.getModel(),
				Integer.class) {

			private static final long serialVersionUID = -6061362237663306115L;

			@Override
			public void onSpin(AjaxRequestTarget target, Integer value) {
				spin = value;
			}
		};
		if (pack.getId() != null) {
			ajaxSpinner.setVisible(false);
		}
		formProducts.add(ajaxSpinner);
	}

	private void addBackToPackagesLink() {
		add(new Link<PackagesPage>("BackToPackages") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(new PackagesPage());
			}
		});
	}

	private void addExistProductsListViewToFPContainer(final WebMarkupContainer formProductsMarcupContainer) {
		ListView<Product> existProd = new ListView<Product>("existsProd", pack.getProducts()) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Product> item) {
				Product product = item.getModelObject();
				item.add(new Label("existProd", product.getNameProduct()));
			}
		};
		formProductsMarcupContainer.add(existProd);
	}

	private void addListProductsListViewToFPContainer(final WebMarkupContainer formProductsMarcupContainer) {
		ListView<Product> addProd = new ListView<Product>("addsProd", listProd) {

			private static final long serialVersionUID = 8495191390775936688L;

			@Override
			protected void populateItem(ListItem<Product> item) {
				Product product = item.getModelObject();
				item.add(new Label("addProd", product.getNameProduct()));
				item.add(new Link<Void>("deleteProd") {

					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
						BigDecimal priceProduct = product.getPriceProduct();
						if (!(pack.getPrice().equals(new BigDecimal("0.00")))) {
							BigDecimal addPrice = pack.getPrice().subtract(priceProduct);
							pack.setPrice(addPrice);
						}
						listProd.remove(product);
						if (pack.getTax().compareTo(new BigDecimal("0")) == 1) {
							BigDecimal multiply = priceProduct.multiply(getPercent());// %
																						// от
																						// удаляемой
																						// цены
							BigDecimal newTax = pack.getTax().subtract(multiply);
							if (pack.getWeight() > SystemSettings.getMaxWeight()) {
								newTax = newTax.subtract(
										SystemSettings.getPriceWeight().multiply(new BigDecimal(product.getWeight())));
							}
							if (newTax.compareTo(new BigDecimal("0")) == -1) {
								newTax = BigDecimal.ZERO;
							}
							pack.setTax(newTax);
						}
						if (pack.getWeight() != 0) {
							Double weightDelProd = product.getWeight();
							double newWeight = pack.getWeight() - weightDelProd;
							pack.setWeight(newWeight);
						}
					}
				});
			}
		};
		formProductsMarcupContainer.add(addProd);
	}

	private void addSelectProductAjaxButtonToFormProducts(final Notification notification,
			final WebMarkupContainer formRegPackMarcupContainer, Date startMonth, Form<Integer> formProducts,
			final WebMarkupContainer formProductsMarcupContainer) {
		AjaxButton selProd = new AjaxButton("selectProduct") {

			private static final long serialVersionUID = 7690378414159408600L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				try {
					BigDecimal overallPrice = new BigDecimal("0");
					if (inProducts != null) {
						packageFilter.setRecipint(pack.getRecipient());
						packageFilter.setStartDate(startMonth);
						for (Package p : packageService.betweenDatesRecipient(packageFilter)) {
							overallPrice = overallPrice.add(p.getPrice());
							if (overallPrice.compareTo(getMaxPrice()) == 1) {
								break;
							}
						}
						for (int i = 0; i < spin; i++) {
							BigDecimal priceProduct = inProducts.getPriceProduct();
							BigDecimal addPrice = pack.getPrice().add(priceProduct);
							pack.setPrice(addPrice);
							Double weightProduct = inProducts.getWeight();
							pack.setWeight(pack.getWeight() + weightProduct);
							listProd.add(inProducts);
						}
					}
					if (overallPrice.compareTo(getMaxPrice()) == 1) { // Если
						// сумма
						// ввозимых
						// посылок
						// в
						// этом
						// месяце
						// больше
						// 600000,то
						pack.setTax(pack.getPrice().multiply(percent)); // от
																		// цены
																		// которой
																		// берется
																		// 20%
																		// НЕ
																		// отнимается
																		// допустимый
																		// лимит
																		// ввоза
					} else if ((pack.getPrice().compareTo(getMaxPrice()) == 1)
							&& (overallPrice.compareTo(getMaxPrice()) != 1)) { // Если
						// текушая
						// цена
						// продуктов
						// в
						// корзине
						// больше
						// 600000
						// но
						// сумма
						// ввозимых
						// посылок
						// в
						// этом
						// месяце
						// меньше
						// 600000,то
						pack.setTax(
								pack.getPrice().subtract(getMaxPrice().subtract(overallPrice)).multiply(getPercent()));// от
						// цены
						// которой
						// берется
						// 20%
						// отнимается
						// допустимый
						// лимит
						// ввоза
					}
					if (pack.getWeight() > SystemSettings.getMaxWeight()) {
						Double multiplicand = pack.getWeight() - SystemSettings.getMaxWeight(); // лишние
																								// килограммы
						BigDecimal multiply = SystemSettings.getPriceWeight().multiply(new BigDecimal(multiplicand));// цена
																														// за
																														// кило
																														// умноженная
																														// на
																														// лишние
																														// килограммы
						pack.setTax(pack.getTax().add(multiply));
					}
					target.add(formProductsMarcupContainer);
					target.add(formRegPackMarcupContainer);
				} catch (NullPointerException e) {
					notification.error(target, getString("page.packages.reg.error1"));
				}
				super.onSubmit(target, form);
			}
		};
		if (pack.getId() != null) {
			selProd.setVisible(false);
		}
		formProducts.add(selProd);
	}

	private void addProductDropDownToFormProducts(Form<Integer> formProducts) {
		DropDownChoice<Product> choiceProduct = new DropDownChoice<>("products",
				new PropertyModel<>(this, "inProducts"), allProducts, ProductChoiceRenderer.INSTANCE);
		if (pack.getId() != null) {
			choiceProduct.setVisible(false);
		}
		formProducts.add(choiceProduct);
	}

	private void addSavePackageAjaxLinkToFRPContainer(final Notification notification,
			final WebMarkupContainer formRegPackMarcupContainer, FeedbackPanel feedBackPanel) {
		formRegPackMarcupContainer.add(new AjaxSubmitLink("savePack") {

			private static final long serialVersionUID = 4716120981397012755L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				try {
					if (stat.equals(getString("page.packages.reg.status1"))) {
						pack.setPaid(false);
					} else {
						pack.setPaid(true);
					}
					if (pack.getId() != null) {
						packageService.updatePackage(pack);
					} else {
						if (pack.getIdUser() == null) {
							pack.setIdUser(AuthorizedSession.get().getUser());
						}
						pack.setProducts(listProd);
						pack.setId(idPack);
						pack.setPercentPenalty(SystemSettings.getPercentFine());
						packageService.registerPackage(pack);
					}
					setResponsePage(new PackagesPage());
					super.onSubmit(target, form);
				} catch (Exception e) {
					notification.error(target, getString("page.packages.reg.error"));
				}
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				for (FeedbackMessage msg : feedBackPanel.getFeedbackMessagesModel().getObject()) {
					notification.error(target, msg.getMessage());
				}
				super.onError(target, form);
			}
		});
	}

	private void addPaidRadioToFRPContainer(final WebMarkupContainer formRegPackMarcupContainer) {
		RadioChoice<String> choice = new RadioChoice<String>("paid", new PropertyModel<>(this, "stat"), status);
		choice.add(new AjaxFormComponentUpdatingBehavior("change") {

			private static final long serialVersionUID = -8114387975729578118L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
			}
		});
		if (pack.getId() == null) {
			choice.setVisible(false);
		}
		formRegPackMarcupContainer.add(choice);
	}

	private void addPenaltyFieldToFRPContainer(final WebMarkupContainer formRegPackMarcupContainer) {
		TextField<BigDecimal> fine = new TextField<BigDecimal>("fine");
		fine.setEnabled(false);
		formRegPackMarcupContainer.add(fine);
	}

	private void addPaymentDeadlineToFRPContainer(final WebMarkupContainer formRegPackMarcupContainer) {
		TextField<String> paymentDeadline = new TextField<String>("paymentDeadline");
		paymentDeadline.setEnabled(false);
		formRegPackMarcupContainer.add(paymentDeadline);
	}

	private void addCountrySenderFieldToFRPContainer(final WebMarkupContainer formRegPackMarcupContainer) {
		TextField<String> countrySender = new TextField<String>("countrySender");
		countrySender.setRequired(true);
		countrySender.add(StringValidator.maximumLength(100));
		countrySender.add(StringValidator.minimumLength(2));
		formRegPackMarcupContainer.add(countrySender);
	}

	private void addDescriptionFieldToFRPContainer(final WebMarkupContainer formRegPackMarcupContainer) {
		TextField<String> description = new TextField<String>("description");
		formRegPackMarcupContainer.add(description);
	}

	private void addDateFieldToFRPContainer(final WebMarkupContainer formRegPackMarcupContainer) {
		DateTextField receivedField = new DateTextField("date");
		if (pack.getId() == null) {
			pack.setDate(new Date());
		}
		receivedField.add(new DatePicker());
		receivedField.setRequired(true);
		if (officer) {
			receivedField.setEnabled(false);
		}
		formRegPackMarcupContainer.add(receivedField);
	}

	private void addUsersDropDownToFRPContainer(final WebMarkupContainer formRegPackMarcupContainer) {
		DropDownChoice<UserProfile> choiceUser = new DropDownChoice<UserProfile>("idUser", allUsers,
				UserProfileRenderer.INSTANCE);
		choiceUser.setNullValid(true);
		formRegPackMarcupContainer.add(choiceUser);
		if (officer) {
			choiceUser.setVisible(false);
		}
	}

	private void addTaxFieldToFRPContainer(final WebMarkupContainer formRegPackMarcupContainer) {
		TextField<BigDecimal> tax = new TextField<>("tax");
		tax.setEnabled(false);
		formRegPackMarcupContainer.add(tax);
	}

	private void addWeightFieldToFLPContainer(final WebMarkupContainer formRegPackMarcupContainer) {
		TextField<Double> weight = new TextField<Double>("weight");
		weight.add(new AjaxFormComponentUpdatingBehavior("change") {

			private static final long serialVersionUID = -8114387975729578118L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
			}
		});
		weight.setRequired(true);
		weight.add(RangeValidator.<Double>range(0d, 1_000_000_000d));
		formRegPackMarcupContainer.add(weight);
	}

	private void addPriceFieldToFRPContainer(final WebMarkupContainer formRegPackMarcupContainer) {
		TextField<BigDecimal> price = new TextField<BigDecimal>("price");
		price.setRequired(true);
		price.add(RangeValidator.<BigDecimal>range(new BigDecimal(0), new BigDecimal(1_000_000_000)));
		formRegPackMarcupContainer.add(price);
	}

	private void addDropDownRecipientsToFRPContainer(final WebMarkupContainer formRegPackMarcupContainer,
			Date startMonth) {
		DropDownChoice<Recipient> recipients = new DropDownChoice<Recipient>("idRecipient", allRecipients,
				RecipientRenderer.INSTANCE);
		recipients.add(new AjaxFormComponentUpdatingBehavior("change") {

			private static final long serialVersionUID = 1549663032341750121L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				BigDecimal overallPrice = new BigDecimal("0");
				packageFilter.setRecipint(pack.getRecipient());
				packageFilter.setStartDate(startMonth);
				for (Package p : packageService.betweenDatesRecipient(packageFilter)) {
					overallPrice = overallPrice.add(p.getPrice());
					if (overallPrice.compareTo(getMaxPrice()) == 1) {
						break;
					}
				}
				if (overallPrice.compareTo(getMaxPrice()) == 1) {

					pack.setTax(pack.getPrice().multiply(percent));
				} else if ((pack.getPrice().compareTo(getMaxPrice()) == 1)
						&& (overallPrice.compareTo(getMaxPrice()) != 1)) {
					pack.setTax(pack.getPrice().subtract(getMaxPrice().subtract(overallPrice)).multiply(getPercent()));
				}
				if ((overallPrice.compareTo(getMaxPrice()) != 1) && (pack.getPrice().compareTo(getMaxPrice()) != 1)) {
					pack.setTax(new BigDecimal("0"));
				}
				if (pack.getWeight() > SystemSettings.getMaxWeight()) {
					Double multiplicand = pack.getWeight() - SystemSettings.getMaxWeight();
					BigDecimal multiply = SystemSettings.getPriceWeight().multiply(new BigDecimal(multiplicand));
					pack.setTax(pack.getTax().add(multiply));
				}
				target.add(formRegPackMarcupContainer);
			}
		});
		recipients.setRequired(true);
		recipients.setNullValid(false);
		formRegPackMarcupContainer.add(recipients);
	}

	private void addIdFieldToFRPContainer(final WebMarkupContainer formRegPackMarcupContainer) {
		TextField<Long> idField = new TextField<Long>("id", new PropertyModel<>(this, "idPack"));
		idField.setRequired(true);
		idField.add(RangeValidator.<Long>range(new Long(1000000000), new Long(1_000_000_000_000_000_000l)));
		formRegPackMarcupContainer.add(idField);
		if (pack.getId() != null) {
			idField.setEnabled(false);
		}
	}

	private void extractIdPack() {
		while (idPack == null || packageService.getPackageWithId(idPack) != null) {
			idPack = System.currentTimeMillis();
		}
	}

	private void startMonth(Date startMonth) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(startMonth);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		startMonth.setTime(cal.getTime().getTime());
	}

	private void createFilters() {
		userFilter = new UserFilter();
		userFilter.setFetchCredentials(true);
		userFilter.setLogin(AuthorizedSession.get().getUser().getLogin());
		recipientFilter = new RecipientFilter();
		recipientFilter.setFetchPackages(true);
		packageFilter = new PackageFilter();
		packageFilter.setFetchProduct(true);
	}

	public static void setPercent(BigDecimal percent) {
		RegistryPackPanel.percent = percent;
	}

	public static void setMaxPrice(BigDecimal maxPrice) {
		RegistryPackPanel.maxPrice = maxPrice;
	}

	public Long getIdPack() {
		return idPack;
	}

	public void setIdPack(Long idPack) {
		this.idPack = idPack;
	}

	public static BigDecimal getPercent() {
		return percent;
	}

	public static BigDecimal getMaxPrice() {
		return maxPrice;
	}

	public Integer getSpin() {
		return spin;
	}

	public void setSpin(Integer spin) {
		this.spin = spin;
	}

	public Package getPack() {
		return pack;
	}

	public void setPack(Package pack) {
		this.pack = pack;
	}
}
