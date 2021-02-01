
package net.mcreator.sirenhead.item;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.item.Rarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Item;
import net.minecraft.block.BlockState;

import net.mcreator.sirenhead.SirenHeadModElements;

@SirenHeadModElements.ModElement.Tag
public class AssaultriflebulletItem extends SirenHeadModElements.ModElement {
	@ObjectHolder("siren_head:assaultriflebullet")
	public static final Item block = null;
	public AssaultriflebulletItem(SirenHeadModElements instance) {
		super(instance, 6);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new ItemCustom());
	}
	public static class ItemCustom extends Item {
		public ItemCustom() {
			super(new Item.Properties().group(ItemGroup.COMBAT).maxStackSize(50).rarity(Rarity.UNCOMMON));
			setRegistryName("assaultriflebullet");
		}

		@Override
		public int getItemEnchantability() {
			return 0;
		}

		@Override
		public int getUseDuration(ItemStack itemstack) {
			return 0;
		}

		@Override
		public float getDestroySpeed(ItemStack par1ItemStack, BlockState par2Block) {
			return 1F;
		}
	}
}
