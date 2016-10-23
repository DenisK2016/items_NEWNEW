/**
 *
 */
package de.hybris.merchandise.continents.source;

import de.hybris.merchandise.core.model.ContinentModel;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;

import java.util.Collection;


/**
 *
 */
public interface ContinentSource
{
	Collection<ContinentModel> getContinentsForConfigAndProperty(IndexConfig indexConfig, IndexedProperty indexedProperty,
			Object model);
TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT
}
