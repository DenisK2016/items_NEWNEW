package by.dk.training.items.webapp.pages.types.panelfortypes;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.ListChoice;
import org.apache.wicket.markup.html.panel.Panel;

import by.dk.training.items.dataaccess.filters.ProductFilter;
import by.dk.training.items.datamodel.Product;
import by.dk.training.items.datamodel.Type;
import by.dk.training.items.services.ProductService;

public class TypeProductsInfo extends Panel {

	private static final long serialVersionUID = 1L;
	private ModalWindow modalWindow;
	private Type type;
	private ProductFilter productFilter;
	@Inject
	private ProductService productService;

	public TypeProductsInfo(ModalWindow modalWindow, Type type) {
		super(modalWindow.getContentId());
		this.setModalWindow(modalWindow);
		this.type = type;
		productFilter = new ProductFilter();
		productFilter.setFetchType(true);
		productFilter.setTypes(this.type);
	}

	@Override
	protected void onInitialize() {
		List<Product> allProductsType = productService.findProduct(productFilter);

		List<Product> prodType = new ArrayList<>();
		for (Product p : allProductsType) {
			if (p.getTypes().get(p.getTypes().size() - 1).equals(type)) {
				prodType.add(p);
			}
		}
		ListChoice<Product> productsType = new ListChoice<>("allProducts", prodType, ProductChoiceRenderer.INSTANCE);
		productsType.setNullValid(true);
		add(productsType);

		super.onInitialize();
	}

	public ModalWindow getModalWindow() {
		return modalWindow;
	}

	public void setModalWindow(ModalWindow modalWindow) {
		this.modalWindow = modalWindow;
	}

}
