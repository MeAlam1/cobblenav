package com.metacontent.cobblenav.duck;

import com.cobblemon.mod.common.api.pokemon.feature.SpeciesFeatureProvider;

public interface GlobalSpeciesFeaturesDuck {

	void cobblenav$registerDirectly(String name, SpeciesFeatureProvider<?> provider);
}
