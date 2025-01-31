package com.Shultrea.Rin.Ench0_4_0;

import com.Shultrea.Rin.Enchantment_Base_Sector.EnchantmentBase;
import com.Shultrea.Rin.Interfaces.IEnchantmentCurse;
import com.Shultrea.Rin.Main_Sector.ModConfig;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentCurseofPossession extends EnchantmentBase implements IEnchantmentCurse {

	public EnchantmentCurseofPossession() {
		super(Rarity.VERY_RARE, EnumEnchantmentType.ALL, EntityEquipmentSlot.values());
		this.setName("CurseofPossession");
		this.setRegistryName("CurseofPossession");
	}

	@Override
	public boolean isConfigEnabled() {
		return ModConfig.enabled.CurseofPossession;
	}

	@Override
	public int getMinEnchantability(int par1) {
		return 25 * par1;
	}

	@Override
	public int getMaxEnchantability(int par1) {
		return this.getMinEnchantability(par1) + 25;
	}

	@Override
	public boolean canApplyTogether(Enchantment fTest) {
		return super.canApplyTogether(fTest) && !(fTest instanceof EnchantmentCurseofDecay);
	}

	@Override
	public boolean isAllowedOnBooks() {
		return false;
	}

	@Override
	public boolean isCurse() {
		return true;
	}

	@Override
	public boolean isTreasureEnchantment() {
		return true;
	}

	private void damagePlayer(EntityPlayer player) {
		//TODO
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void toss(ItemTossEvent event) {
		EntityItem item = event.getEntityItem();
		ItemStack theThrown = item.getItem().copy();

		int l = EnchantmentHelper.getEnchantmentLevel(this, theThrown);
		if (l <= 0) {
			return;
		}

		EntityPlayer player = event.getPlayer();

		if (!player.isCreative()) {
			boolean flag = player.inventory.addItemStackToInventory(theThrown);

			if (!flag) {
				EntityItem entityItem = player.entityDropItem(theThrown, 1.3f);
				entityItem.setOwner(player.getName());
				entityItem.setNoPickupDelay();
				entityItem.setEntityInvulnerable(true);
			} else {
				this.damagePlayer(player);
			}

			event.setCanceled(true);
		}
	}

	//
}