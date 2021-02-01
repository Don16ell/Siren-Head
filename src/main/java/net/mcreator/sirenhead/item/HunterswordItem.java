
package net.mcreator.sirenhead.item;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.IItemTier;

import net.mcreator.sirenhead.SirenHeadModElements;

@SirenHeadModElements.ModElement.Tag
public class HunterswordItem extends SirenHeadModElements.ModElement {
	@ObjectHolder("siren_head:huntersword")
	public static final Item block = null;
	public HunterswordItem(SirenHeadModElements instance) {
		super(instance, 9);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new SwordItem(new IItemTier() {
			public int getMaxUses() {
				return 3214;
			}

			public float getEfficiency() {
				return 4f;
			}

			public float getAttackDamage() {
				return 20f;
			}

			public int getHarvestLevel() {
				return 3;
			}

			public int getEnchantability() {
				return 2;
			}

			public Ingredient getRepairMaterial() {
				return Ingredient.EMPTY;
			}
		}, 3, -3f, new Item.Properties().group(ItemGroup.COMBAT)) {
		}.setRegistryName("huntersword"));
	}
}
