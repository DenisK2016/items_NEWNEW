package by.dk.training.items.webapp.app.renderer;

import org.apache.wicket.markup.html.form.ChoiceRenderer;

import by.dk.training.items.datamodel.Product;

public class ProductChoiceRenderer extends ChoiceRenderer<Product> {

	private static final long serialVersionUID = -5085548139093729532L;
	public static final ProductChoiceRenderer INSTANCE = new ProductChoiceRenderer();

	private ProductChoiceRenderer() {
		super();
	}

	@Override
	public Object getDisplayValue(Product object) {
		String display = object.getId() + " " + object.getNameProduct();
		return display;
	}

	@Override
	public String getIdValue(Product object, int index) {
		return String.valueOf(object.getId());
	}

}
