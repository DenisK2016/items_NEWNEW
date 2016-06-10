package by.dk.training.items.webapp.pages.types.panelfortypes;

import org.apache.wicket.markup.html.form.ChoiceRenderer;

import by.dk.training.items.datamodel.Product;

public class ProductChoiceRenderer extends ChoiceRenderer<Product> {

	private static final long serialVersionUID = 1L;
	public static final ProductChoiceRenderer INSTANCE = new ProductChoiceRenderer();

	private ProductChoiceRenderer() {
		super();

	}

	@Override
	public Object getDisplayValue(Product object) {
		return object.getNameProduct();
	}

	@Override
	public String getIdValue(Product object, int index) {
		// TODO Auto-generated method stub
		return String.valueOf(object.getId());
	}

}
