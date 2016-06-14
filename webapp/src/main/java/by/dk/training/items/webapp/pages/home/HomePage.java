package by.dk.training.items.webapp.pages.home;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.googlecode.wickedcharts.highcharts.options.Axis;
import com.googlecode.wickedcharts.highcharts.options.ChartOptions;
import com.googlecode.wickedcharts.highcharts.options.HorizontalAlignment;
import com.googlecode.wickedcharts.highcharts.options.Legend;
import com.googlecode.wickedcharts.highcharts.options.LegendLayout;
import com.googlecode.wickedcharts.highcharts.options.Options;
import com.googlecode.wickedcharts.highcharts.options.SeriesType;
import com.googlecode.wickedcharts.highcharts.options.Title;
import com.googlecode.wickedcharts.highcharts.options.VerticalAlignment;
import com.googlecode.wickedcharts.highcharts.options.series.SimpleSeries;
import com.googlecode.wickedcharts.wicket7.highcharts.Chart;

import by.dk.training.items.services.PackageService;
import by.dk.training.items.webapp.app.AuthorizedSession;
import by.dk.training.items.webapp.pages.AbstractPage;
import by.dk.training.items.webapp.pages.login.RedirectPage;

@AuthorizeInstantiation(value = { "ADMIN", "OFFICER", "COMMANDER", "CONFIRMATION", "BANNED" })
public class HomePage extends AbstractPage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private PackageService packageService;
	private Date dateStart;
	private Date dateEnd;
	private Date d1;
	private Date d2;
	private boolean admin = AuthorizedSession.get().getRoles().contains("ADMIN");
	private boolean commander = AuthorizedSession.get().getRoles().contains("COMMANDER");
	private boolean confirm = AuthorizedSession.get().getRoles().contains("CONFIRMATION");
	private boolean banned = AuthorizedSession.get().getRoles().contains("BANNED");
	private Number year = Calendar.getInstance().get(Calendar.YEAR);
	private List<Number> years = Arrays
			.asList(new Number[] { 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012, 2013, 2014, 2015, 2016, 2017 });
	private Number[] num = new Number[12];

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public HomePage() {
		super();

	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		Label allPacks = new Label("allpacks", Model.of(packageService.countPack()));
		add(allPacks);
		d1 = new Date();
		d2 = new Date();
		changeDate(d1, d2);
		Label lastMonth = new Label("month", Model.of(packageService.countPackBetweenDates(d1, d2)));
		add(lastMonth);
		dateStart = new Date();
		dateEnd = new Date();
		Model<Long> mod = new Model<Long>(packageService.countPackBetweenDates(d1, d2));
		Label lab = new Label("quantity", Model.of(mod));
		lab.setOutputMarkupId(true);
		add(lab);
		DateTextField fieldStart = new DateTextField("datestart", new PropertyModel<>(this, "dateStart"), "dd-MM-yyyy");
		fieldStart.add(new AjaxFormComponentUpdatingBehavior("onchange") {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				mod.setObject(packageService.countPackBetweenDates(dateStart, dateEnd));
				target.add(lab);

			}
		});
		add(fieldStart);
		fieldStart.add(new DatePicker());
		DateTextField fieldEnd = new DateTextField("dateend", new PropertyModel<>(this, "dateEnd"), "dd-MM-yyyy");
		fieldEnd.add(new AjaxFormComponentUpdatingBehavior("onchange") {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {

				mod.setObject(packageService.countPackBetweenDates(dateStart, dateEnd));
				target.add(lab);
			}
		});
		add(fieldEnd);
		fieldEnd.add(new DatePicker());
		Label maxPrice = new Label("maxprice", Model.of(packageService.maxPrice().getPrice()));
		add(maxPrice);
		Label popularCity = new Label("city", Model.of(packageService.oftenCountry()));
		add(popularCity);
		Label role;
		Label info1;
		Label info2;
		if (admin) {
			role = new Label("role", getString("page.home.role1"));
			info1 = new Label("info1", "");
			info2 = new Label("info2", "");
		} else if (commander) {
			role = new Label("role", getString("page.home.role2"));
			info1 = new Label("info1", getString("page.home.title1"));
			info2 = new Label("info2", getString("page.home.title2"));
		} else {
			role = new Label("role", getString("page.home.role3"));
			info1 = new Label("info1", getString("page.home.title1"));
			info2 = new Label("info2", getString("page.home.title2"));
		}
		add(role);
		add(info1);
		add(info2);
		final WebMarkupContainer wmc = new WebMarkupContainer("wmc");
		wmc.setOutputMarkupId(true);
		add(wmc);
		DropDownChoice<Number> listYears = new DropDownChoice<Number>("years", new PropertyModel<Number>(this, "year"),
				years);
		listYears.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				Date date1 = new Date();
				Date date2 = new Date();
				changeYearChart(date1, date2);
				target.add(wmc);
			}
		});
		wmc.add(listYears);
		Options options = new Options();
		options.setTitle(new Title(getString("page.home.chart2")));
		options.setChartOptions(new ChartOptions().setType(SeriesType.COLUMN));
		options.setxAxis(new Axis().setCategories(
				Arrays.asList(new String[] { getString("page.home.chart.month1"), getString("page.home.chart.month2"),
						getString("page.home.chart.month3"), getString("page.home.chart.month4"),
						getString("page.home.chart.month5"), getString("page.home.chart.month6"),
						getString("page.home.chart.month7"), getString("page.home.chart.month8"),
						getString("page.home.chart.month9"), getString("page.home.chart.month10"),
						getString("page.home.chart.month11"), getString("page.home.chart.month12") })));
		options.setyAxis(new Axis().setTitle(new Title(getString("page.home.chart1"))));
		options.setLegend(new Legend().setLayout(LegendLayout.VERTICAL).setAlign(HorizontalAlignment.RIGHT)
				.setVerticalAlign(VerticalAlignment.TOP).setX(-10).setY(100).setBorderWidth(0));
		Date date1 = new Date();
		Date date2 = new Date();
		changeYearChart(date1, date2);
		options.addSeries(new SimpleSeries().setName(getString("page.home.chart1")).setData(Arrays.asList(num)));
		Chart chart = new Chart("chart", options);
		wmc.add(chart);
		if (admin) {
			allPacks.setVisible(false);
			lastMonth.setVisible(false);
			lab.setVisible(false);
			fieldStart.setVisible(false);
			fieldEnd.setVisible(false);
			maxPrice.setVisible(false);
			popularCity.setVisible(false);
			listYears.setVisible(false);
			chart.setVisible(false);
		}
		if (confirm || banned) {
			setResponsePage(RedirectPage.class);
		}
	}

	private void changeYearChart(Date date1, Date date2) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date2);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.YEAR, (int) year);
		for (int i = 0; i < 12; i++) {
			calendar.set(Calendar.MONTH, i);
			date1.setTime(calendar.getTime().getTime());
			if (i == 11) {
				calendar.set(Calendar.MONTH, 0);
				calendar.set(Calendar.YEAR, (int) year + 1);
				date2.setTime(calendar.getTime().getTime());
			} else {
				calendar.set(Calendar.MONTH, i + 1);
				date2.setTime(calendar.getTime().getTime());
			}
			num[i] = packageService.countPackBetweenDates(date1, date2);
		}
	}

	private void changeDate(Date d1, Date d2) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d2);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		d2.setTime(cal.getTime().getTime());
		int month = cal.get(Calendar.MONTH) - 1;
		if (month == -1) {
			month = 11;
			cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 1);
		}
		cal.set(Calendar.MONTH, month);
		d1.setTime(cal.getTime().getTime());
	}
}
