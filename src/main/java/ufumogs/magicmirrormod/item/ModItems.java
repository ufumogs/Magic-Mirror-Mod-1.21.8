package ufumogs.magicmirrormod.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import ufumogs.magicmirrormod.MagicMirrorMod;

public class ModItems {
    public static final Item MAGIC_MIRROR = registerItem("magic_mirror", new Item(new Item.Settings().maxCount(1).registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MagicMirrorMod.MOD_ID,"magic_mirror")))));
    public static final Item ENDLESS_MIRROR = registerItem("endless_mirror", new Item(new Item.Settings().maxCount(1).registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MagicMirrorMod.MOD_ID,"magic_mirror")))));

    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, Identifier.of(MagicMirrorMod.MOD_ID, name), item);
    }

    public static void registerModItems(){
        MagicMirrorMod.LOGGER.info("Registering Mod Items for " + MagicMirrorMod.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(MAGIC_MIRROR);
            entries.add(ENDLESS_MIRROR);
        });
    }
}
