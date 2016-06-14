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

	private static final long serialVersionUID = 1L;
	boolean officer = AuthorizedSession.get().getRoles().contains("OFFICER");
	private Package pack;
	@Inject
	private PackageService packageService;
	@Inject
	private RecipientService recipientService;
	@Inject
	private UserProfileService userProfileService;
	@Inject
	private ProductService productService;
	private Long idPack;
	private List<Product> allProducts = productService.getAll();
	private Product inProducts;
	private List<Product> listProd = new ArrayList<>();
	private Integer spin = 1;
	private List<Recipient> allRecipients = recipientService.getAll();
	private List<UserProfile> allUsers = userProfileService.getAll();
	private UserFilter userFilter;
	private RecipientFilter recipientFilter;
	private PackageFilter packageFilter;
	private final List<String> STATUS = Arrays
			.asList(new String[] { getString("page.packages.reg.status"), getString("page.packages.reg.status1") });
	private String stat = getString("page.packages.reg.status1");
	private static BigDecimal maxPrice = SystemSettings.getMaxPrice();
	private static BigDecimal percent = SystemSettings.getPercent().divide(BigDecimal.valueOf(100L));

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
		setPack(packageService.find(packageFilter).get(0));
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

	@Override
	protected void onInitialize() {
		super.onInitialize();
		createFilter();
		final Notification notification = new Notification("notification");
		this.add(notification);
		final WebMarkupContainer wmcForm = new WebMarkupContainer("wmcForm");
		wmcForm.setOutputMarkupId(true);
		Form<Package> form = new Form<Package>("formRegPack", new CompoundPropertyModel<Package>(pack));
		FeedbackPanel feedBackPanel = new FeedbackPanel("feedback");
		feedBackPanel.setVisible(false);
		wmcForm.add(feedBackPanel);
		form.add(wmcForm);
		while (idPack == null || packageService.getPackage(idPack) != null) {
			idPack = System.currentTimeMillis();
		}
		TextField<Long> idField = new TextField<Long>("id", new PropertyModel<>(this, "idPack"));
		idField.setRequired(true);
		idField.add(RangeValidator.<Long> range(new Long(1000000000), new Long(1_000_000_000_000_000_000l)));
		wmcForm.add(idField);
		if (pack.getId() != null) {
			idField.setEnabled(false);
		}
		Date startMonth = new Date();
		startMonth(startMonth);
		DropDownChoice<Recipient> recipients = new DropDownChoice<Recipient>("idRecipient", allRecipients,
				RecipientRenderer.INSTANCE);
		recipients.add(new AjaxFormComponentUpdatingBehavior("change") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				BigDecimal overallPrice = new BigDecimal("0");
				packageFilter.setRecipint(pack.getIdRecipient());
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
				target.add(wmcForm);
			}
		});
		recipients.setRequired(true);
		recipients.setNullValid(false);
		wmcForm.add(recipients);
		TextField<BigDecimal> price = new TextField<BigDecimal>("price");
		price.setRequired(true);
		price.add(RangeValidator.<BigDecimal> range(new BigDecimal(0), new BigDecimal(1_000_000_000)));
		wmcForm.add(price);
		TextField<Double> weight = new TextField<Double>("weight");
		weight.add(new AjaxFormComponentUpdatingBehavior("change") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
			}
		});
		weight.setRequired(true);
		weight.add(RangeValidator.<Double> range(0d, 1_000_000_000d));
		wmcForm.add(weight);
		TextField<BigDecimal> tax = new TextField<>("tax");
		tax.setEnabled(false);
		wmcForm.add(tax);
		DropDownChoice<UserProfile> choiceUser = new DropDownChoice<UserProfile>("idUser", allUsers,
				UserProfileRenderer.INSTANCE);
		choiceUser.setNullValid(true);
		wmcForm.add(choiceUser);
		if (officer) {
			choiceUser.setVisible(false);
		}
		DateTextField receivedField = new DateTextField("date");
		if (pack.getId() == null) {
			pack.setDate(new Date());
		}
		receivedField.add(new DatePicker());
		receivedField.setRequired(true);
		if (officer) {
			receivedField.setEnabled(false);
		}
		wmcForm.add(receivedField);
		TextField<String> description = new TextField<String>("description");
		wmcForm.add(description);
		TextField<String> countrySender = new TextField<String>("countrySender");
		countrySender.setRequired(true);
		countrySender.add(StringValidator.maximumLength(100));
		countrySender.add(StringValidator.minimumLength(2));
		wmcForm.add(countrySender);
		TextField<String> paymentDeadline = new TextField<String>("paymentDeadline");
		paymentDeadline.setEnabled(false);
		wmcForm.add(paymentDeadline);
		TextField<BigDecimal> fine = new TextField<BigDecimal>("fine");
		fine.setEnabled(false);
		wmcForm.add(fine);
		RadioChoice<String> choice = new RadioChoice<String>("paid", new PropertyModel<>(this, "stat"), STATUS);
		choice.add(new AjaxFormComponentUpdatingBehavior("change") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
			}
		});
		if (pack.getId() == null) {
			choice.setVisible(false);
		}
		wmcForm.add(choice);
		wmcForm.add(new AjaxSubmitLink("savePack") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				try {
					if (stat.equals(getString("page.packages.reg.status1"))) {
						pack.setPaid(false);
					} else {
						pack.setPaid(true);
					}
					if (pack.getId() != null) {
						packageService.update(pack);
					} else {
						if (pack.getIdUser() == null) {
							pack.setIdUser(AuthorizedSession.get().getUser());
						}
						pack.setProducts(listProd);
						pack.setId(idPack);
						pack.setPercentFine(SystemSettings.getPercentFine());
						packageService.register(pack);
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
		Form<Integer> formProd = new Form<Integer>("formProd", Model.of(0));
		DropDownChoice<Product> choiceProduct = new DropDownChoice<>("products",
				new PropertyModel<>(this, "inProducts"), allProducts, ProductChoiceRenderer.INSTANCE);
		if (pack.getId() != null) {
			choiceProduct.setVisible(false);
		}
		formProd.add(choiceProduct);
		final WebMarkupContainer wmc = new WebMarkupContainer("wmc");
		wmc.setOutputMarkupId(true);
		formProd.add(wmc);
		AjaxButton selProd = new AjaxButton("selectProduct") {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				try {
					BigDecimal overallPrice = new BigDecimal("0");
					if (inProducts != null) {
						packageFilter.setRecipint(pack.getIdRecipient());
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
					target.add(wmc);
					target.add(wmcForm);
				} catch (NullPointerException e) {
					notification.error(target, getString("page.packages.reg.error1"));
				}
				super.onSubmit(target, form);
			}
		};
		if (pack.getId() != null) {
			selProd.setVisible(false);
		}
		formProd.add(selProd);
		ListView<Product> addProd = new ListView<Product>("addsProd", listProd) {

			private static final long serialVersionUID = 1L;

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
		wmc.add(addProd);
		ListView<Product> existProd = new ListView<Product>("existsProd", pack.getProducts()) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Product> item) {
				Product product = item.getModelObject();
				item.add(new Label("existProd", product.getNameProduct()));
			}
		};
		wmc.add(existProd);
		add(formProd);
		add(form);
		add(new Link<PackagesPage>("BackToPackages") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(new PackagesPage());
			}
		});
		final AjaxSpinner<Integer> ajaxSpinner = new AjaxSpinner<Integer>("spinner", formProd.getModel(),
				Integer.class) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSpin(AjaxRequestTarget target, Integer value) {
				spin = value;
			}
		};
		if (pack.getId() != null) {
			ajaxSpinner.setVisible(false);
		}
		formProd.add(ajaxSpinner);
		if (pack.getId() == null) {
			wmcForm.add(new Label("regOrUpdate", getString("page.packages.reg.title")));
		} else {
			wmcForm.add(new Label("regOrUpdate", getString("page.packages.reg.title1")));
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

	private void createFilter() {
		userFilter = new UserFilter();
		userFilter.setFetchCredentials(true);
		userFilter.setLogin(AuthorizedSession.get().getUser().getLogin());
		recipientFilter = new RecipientFilter();
		recipientFilter.setFetchPackages(true);
		packageFilter = new PackageFilter();
		packageFilter.setFetchProduct(true);
	}
}
