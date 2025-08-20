package ufumogs.magicmirrormod;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ufumogs.magicmirrormod.item.ModItems;

public class MagicMirrorMod implements ModInitializer {
	public static final String MOD_ID = "magicmirrormod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
	}
}