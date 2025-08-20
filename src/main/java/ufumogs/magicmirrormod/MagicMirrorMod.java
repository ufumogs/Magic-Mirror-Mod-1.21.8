package ufumogs.magicmirrormod;

import net.fabricmc.api.ModInitializer;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ufumogs.magicmirrormod.item.ModItems;

public class MagicMirrorMod implements ModInitializer {
	public static final String MOD_ID = "magicmirrormod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final Identifier TELEPORT_ID = Identifier.of("magicmirrormod", "teleport");
	public static final SoundEvent TELEPORT_SOUND = SoundEvent.of(TELEPORT_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		Registry.register(Registries.SOUND_EVENT, TELEPORT_ID, TELEPORT_SOUND);
	}
}