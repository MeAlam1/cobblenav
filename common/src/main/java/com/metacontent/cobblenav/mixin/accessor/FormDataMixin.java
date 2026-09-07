package com.metacontent.cobblenav.mixin.accessor;

import com.cobblemon.mod.common.api.pokemon.stats.Stat;
import com.cobblemon.mod.common.pokemon.FormData;
import java.util.Map;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FormData.class)
public interface FormDataMixin {

	@Accessor("_evYield")
	void cobblenav$setEvYield(@Nullable Map<Stat, Integer> _evYield);

	@Accessor("_evYield")
	@Nullable
	Map<Stat, Integer> cobblenav$getEvYield();
}
