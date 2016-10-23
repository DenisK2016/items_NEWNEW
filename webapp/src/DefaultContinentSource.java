/**
 *
 */
package de.hybris.merchandise.continents.source;

import de.hybris.merchandise.core.model.ContinentModel;
import de.hybris.merchandise.core.model.NationModel;
import de.hybris.merchandise.core.services.ContinentService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;


/**
 *
 */
public class DefaultContinentSource implements ContinentSource
{
	private static final Logger LOG = Logger.getLogger(DefaultContinentSource.class);

	private ModelService modelService;
	private String continentsQualifier;
	private String rootContinent;
	private ContinentService continentService;

	/**
	 * @return the modelService
	 */
	public ModelService getModelService()
	{
		return modelService;
	}

	/**
	 * @param modelService
	 *           the modelService to set
	 */
	@Required
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

	/**
	 * @return the continentsQualifier
	 */
	public String getContinentsQualifier()
	{
		return continentsQualifier;
	}

	/**
	 * @param continentsQualifier
	 *           the continentsQualifier to set
	 */
	@Required
	public void setContinentsQualifier(final String continentsQualifier)
	{
		this.continentsQualifier = continentsQualifier;
	}

	/**
	 * @return the rootContinent
	 */
	public String getRootContinent()
	{
		return rootContinent;
	}

	/**
	 * @param rootContinent
	 *           the rootContinent to set
	 */
	@Required
	public void setRootContinent(final String rootContinent)
	{
		this.rootContinent = rootContinent;
	}

	/**
	 * @return the continentService
	 */
	public ContinentService getContinentService()
	{
		return continentService;
	}

	/**
	 * @param continentService
	 *           the continentService to set
	 */
	@Required
	public void setContinentService(final ContinentService continentService)
	{
		this.continentService = continentService;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.hybris.merchandise.continents.source.ContinentSource#getContinentsForConfigAndProperty(de.hybris.platform.
	 * solrfacetsearch.config.IndexConfig, de.hybris.platform.solrfacetsearch.config.IndexedProperty, java.lang.Object)
	 */
	@Override
	public Collection<ContinentModel> getContinentsForConfigAndProperty(final IndexConfig indexConfig,
			final IndexedProperty indexedProperty, final Object model)
	{
		final Set<NationModel> nations = getNations(model);
		final Set<ContinentModel> directContinents = getDirectContinents(nations);

		//		if (directContinents != null && !directContinents.isEmpty())
		//		{
		//			// Lookup the root categories - null if no root categories
		//			//	final Set<CategoryModel> rootCategories = lookupRootCategories(indexConfig.getCatalogVersions());
		//			final Collection<CatalogVersionModel> catalogVersions = Collections
		//					.singletonList(((ProductModel) model).getCatalogVersion());
		//			final Set<CategoryModel> rootCategories = lookupRootCategories(catalogVersions);
		//
		//			final Set<CategoryModel> allCategories = new HashSet<CategoryModel>();
		//			for (final CategoryModel category : directContinents)
		//			{
		//				allCategories.addAll(getAllCategories(category, rootCategories));
		//			}
		//			return allCategories;
		//		}
		//		else
		//		{
		return Collections.emptyList();
		//		}
	}

	protected Set<NationModel> getNations(final Object model)
	{
		if (model instanceof NationModel)
		{
			return Collections.singleton((NationModel) model);
		}
		return Collections.emptySet();
	}

	protected Set<ContinentModel> getDirectContinents(final Set<NationModel> nations)
	{
		final Set<ContinentModel> continents = new HashSet<ContinentModel>();

		for (final NationModel nation : nations)
		{
			final Collection<ContinentModel> directContinents = getModelService().getAttributeValue(nation,
					getContinentsQualifier());
			if (directContinents != null && !directContinents.isEmpty())
			{
				continents.addAll(directContinents);
			}
		}

		return continents;
	}
}
