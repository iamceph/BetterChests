/**
 * The code of BetterChests and all related materials like textures is copyrighted material.
 * It may only be redistributed or used for Commercial purposes with the permission of Aroma1997.
 * 
 * All Rights reserved (c) by Aroma1997
 * 
 * See https://github.com/Aroma1997/BetterChests/blob/master/LICENSE.md for more information.
 */

package aroma1997.betterchests.upgrades;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import aroma1997.betterchests.BagInventory;
import aroma1997.betterchests.InventoryFilter.BCFilterFilter;
import aroma1997.betterchests.api.IBetterChest;
import aroma1997.core.util.InvUtil;

public class PlayerFeeding extends BasicUpgrade {

	@Override
	public void updateChest(IBetterChest chest, int tick, World world,
			ItemStack item) {
		if (tick != 36) {
			return;
		}
		if (chest != null && chest instanceof BagInventory) {
			BagInventory inv = (BagInventory) chest;
			EntityPlayer player = (EntityPlayer) inv.getEntity();
			if (!player.getFoodStats().needFood()) {
				return;
			}
			int slot = InvUtil.getFirstItem(inv, ItemFood.class, null,
					new BCFilterFilter(chest.getFiltersForUpgrade(item)));
			if (slot == -1) {
				return;
			}
			ItemStack itemStack = inv.getStackInSlot(slot);
			if (itemStack == null) {
				return;
			}
			ItemFood food = (ItemFood) itemStack.getItem();
			if (20 - player.getFoodStats().getFoodLevel() >= food
					.getHealAmount(itemStack)
					|| player.getFoodStats().getFoodLevel() <= 17
					&& player.getHealth() <= player.getMaxHealth() - 1.0F
					|| player.getFoodStats().getFoodLevel() <= 6) {
				ItemStack ret = food.onItemUseFinish(itemStack, world, player);
				if (ret == null || ret.stackSize == 0) {
					inv.setInventorySlotContents(slot, null);
				} else {
					inv.setInventorySlotContents(slot, ret);
				}

			}
		}
	}

}
