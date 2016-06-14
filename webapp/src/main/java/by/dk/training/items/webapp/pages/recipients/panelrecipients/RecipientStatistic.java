package by.dk.training.items.webapp.pages.recipients.panelrecipients;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;

import com.googlecode.wickedcharts.highcharts.options.Axis;
import com.googlecode.wickedcharts.highcharts.options.ChartOptions;
import com.googlecode.wickedcharts.highcharts.options.Options;
import com.googlecode.wickedcharts.highcharts.options.SeriesType;
import com.googlecode.wickedcharts.highcharts.options.Title;
import com.googlecode.wickedcharts.highcharts.options.series.SimpleSeries;
import com.googlecode.wickedcharts.wicket7.highcharts.Chart;

import by.dk.training.items.dataaccess.filters.PackageFilter;
import by.dk.training.items.datamodel.Recipient;
import by.dk.training.items.services.PackageService;

public class RecipientStatistic extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private PackageService packageService;
	private Recipient recipient;
	private Number year = Calendar.getInstance().get(Calendar.YEAR);
	private List<Number> years = Arrays
			.asList(new Number[] { 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012, 2013, 2014, 2015, 2016, 2017 });
	private Number[] num = new Number[12];
	private Date date1;
	private Date date2;

	public RecipientStatistic(ModalWindow modalWindow, Recipient recipient) {
		super(modalWindow.getContentId());
		this.recipient = recipient;
	}

	@Override
	protected void onInitialize() {
		final WebMarkupContainer wmc = new WebMarkupContainer("wmc");
		wmc.setOutputMarkupId(true);
		add(wmc);
		DropDownChoice<Number> listYears = new DropDownChoice<Number>("years", new PropertyModel<Number>(this, "year"),
				years);
		listYears.add(new AjaxFormComponentUpdatingBehavior("change") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				PackageFilter filter = new PackageFilter();
				filter.setRecipint(recipient);
				date1 = new Date();
				date2 = new Date();
				dates(filter, date1, date2);
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
		PackageFilter filter = new PackageFilter();
		filter.setRecipint(recipient);
		date1 = new Date();
		date2 = new Date();
		dates(filter, date1, date2);
		options.addSeries(new SimpleSeries().setName(getString("page.home.chart1")).setData(Arrays.asList(num)));
		Chart chart = new Chart("chart", options);
		wmc.add(chart);
		super.onInitialize();
	}

	private void dates(PackageFilter filter, Date date1, Date date2) {
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
			filter.setStartDate(date1);
			filter.setEndDate(date2);
			num[i] = packageService.countBetweenDatesRecipient(filter);
		}
	}
}
