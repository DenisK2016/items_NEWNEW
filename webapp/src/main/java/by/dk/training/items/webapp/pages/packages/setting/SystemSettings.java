package by.dk.training.items.webapp.pages.packages.setting;

import java.math.BigDecimal;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.PatternValidator;
import org.apache.wicket.validation.validator.RangeValidator;

import com.googlecode.wicket.jquery.ui.markup.html.link.AjaxLink;
import com.googlecode.wicket.kendo.ui.widget.notification.Notification;

import by.dk.training.items.webapp.app.AuthorizedSession;

public class SystemSettings extends Panel {

	private static final long serialVersionUID = 1L;
	private ModalWindow modalWindow;
	private static BigDecimal maxPrice = new BigDecimal("600000");
	private static Double maxWeight = 25.0;
	private static BigDecimal priceWeight = new BigDecimal("100000");
	private static BigDecimal percent = new BigDecimal("20");
	private static String paymentDeadline = "10";
	private static BigDecimal percentFine = new BigDecimal("1");
	boolean officer = AuthorizedSession.get().getRoles().contains("OFFICER");

	public SystemSettings(ModalWindow modalWindow) {
		super(modalWindow.getContentId());
		this.modalWindow = modalWindow;

	}

	public static String getPaymentDeadline() {
		return paymentDeadline;
	}

	public static void setPaymentDeadline(String paymentDeadline) {
		SystemSettings.paymentDeadline = paymentDeadline;
	}

	public static BigDecimal getPercent() {
		return percent;
	}

	public static void setPercent(BigDecimal percent) {
		SystemSettings.percent = percent;
	}

	public static BigDecimal getMaxPrice() {
		return maxPrice;
	}

	public static void setMaxPrice(BigDecimal maxPrice) {
		SystemSettings.maxPrice = maxPrice;
	}

	public static BigDecimal getPriceWeight() {
		return priceWeight;
	}

	public static void setPriceWeight(BigDecimal priceWeight) {
		SystemSettings.priceWeight = priceWeight;
	}

	public static Double getMaxWeight() {
		return maxWeight;
	}

	public static void setMaxWeight(Double maxWeight) {
		SystemSettings.maxWeight = maxWeight;
	}

	public static BigDecimal getPercentFine() {
		return percentFine;
	}

	public static void setPercentFine(BigDecimal percentFine) {
		SystemSettings.percentFine = percentFine;
	}

	@Override
	protected void onInitialize() {
		Form<Object> formSetting = new Form<>("formSetting");
		final Notification notification = new Notification("notification");
		this.add(notification);
		FeedbackPanel feedBack = new FeedbackPanel("feedback");
		feedBack.setOutputMarkupId(true);
		feedBack.setVisible(false);
		formSetting.add(feedBack);
		add(formSetting);
		TextField<BigDecimal> priceField = new TextField<>("maxPrice", new PropertyModel<>(this, "maxPrice"));
		priceField.add(new AjaxFormComponentUpdatingBehavior("change") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
			}
		});
		priceField.setRequired(true);
		priceField.add(RangeValidator.<BigDecimal> range(new BigDecimal(0), new BigDecimal(1_000_000_000)));
		formSetting.add(priceField);
		TextField<Double> weightField = new TextField<>("maxWeight", new PropertyModel<>(this, "maxWeight"));
		weightField.add(new AjaxFormComponentUpdatingBehavior("change") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
			}
		});
		weightField.setRequired(true);
		weightField.add(RangeValidator.<Double> range(new Double(0), new Double(1_000_000_000)));
		formSetting.add(weightField);
		TextField<BigDecimal> priceWeightField = new TextField<>("priceWeight",
				new PropertyModel<>(this, "priceWeight"));
		priceWeightField.add(new AjaxFormComponentUpdatingBehavior("change") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
			}
		});
		priceWeightField.setRequired(true);
		priceWeightField.add(RangeValidator.<BigDecimal> range(new BigDecimal(0), new BigDecimal(1_000_000_000)));
		formSetting.add(priceWeightField);
		TextField<BigDecimal> percentField = new TextField<>("percent", new PropertyModel<>(this, "percent"));
		percentField.add(new AjaxFormComponentUpdatingBehavior("change") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
			}
		});
		percentField.setRequired(true);
		percentField.add(RangeValidator.<BigDecimal> range(new BigDecimal(0), new BigDecimal(1_000_000_000)));
		formSetting.add(percentField);
		TextField<String> days = new TextField<>("paymentDeadline", new PropertyModel<>(this, "paymentDeadline"));
		days.add(new AjaxFormComponentUpdatingBehavior("change") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
			}
		});
		days.setRequired(true);
		days.add(new PatternValidator("[0-9]+"));
		formSetting.add(days);
		TextField<BigDecimal> fineField = new TextField<>("percentFine", new PropertyModel<>(this, "percentFine"));
		fineField.add(new AjaxFormComponentUpdatingBehavior("change") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
			}
		});
		fineField.setRequired(true);
		fineField.add(RangeValidator.<BigDecimal> range(new BigDecimal(0), new BigDecimal(1_000_000_000)));
		formSetting.add(fineField);
		AjaxSubmitLink linkSave = new AjaxSubmitLink("save") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				super.onSubmit(target, form);
				setMaxPrice(maxPrice);
				setPercent(percent);
				setPercentFine(percentFine);
				setPriceWeight(priceWeight);
				setPaymentDeadline(paymentDeadline);
				modalWindow.close(target);
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				for (FeedbackMessage msg : feedBack.getFeedbackMessagesModel().getObject()) {
					notification.error(target, msg.getMessage());
				}
				super.onError(target, form);
			}
		};
		linkSave.add(AttributeModifier.append("title", getString("newlogin.save")));
		formSetting.add(linkSave);
		formSetting.add(new AjaxLink<Void>("back") {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				modalWindow.close(target);
			}
		});
		if (officer) {
			priceField.setEnabled(false);
			weightField.setEnabled(false);
			priceWeightField.setEnabled(false);
			percentField.setEnabled(false);
			days.setEnabled(false);
			fineField.setEnabled(false);
			linkSave.setVisible(false);
		}
		super.onInitialize();
	}

}
