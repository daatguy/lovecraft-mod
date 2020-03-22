package io.github.daatguy.lovecraft.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.BlockCauldron;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import com.google.common.base.Predicate;

import io.github.daatguy.lovecraft.core.LovecraftMain;

public class ItemEmptyBeaker extends ItemSimple {

	/**
	 * Called when the equipped item is right clicked.
	 */
	public ActionResult<ItemStack> onItemRightClick(World worldIn,
			EntityPlayer playerIn, EnumHand handIn) {

        ItemStack itemstack = playerIn.getHeldItem(handIn);
		RayTraceResult raytraceresult = this.rayTrace(worldIn, playerIn, true);

		if (raytraceresult == null) {
			return new ActionResult(EnumActionResult.PASS, itemstack);
		} else {
			if (raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK) {
				BlockPos blockpos = raytraceresult.getBlockPos();

				if (!worldIn.isBlockModifiable(playerIn, blockpos)
						|| !playerIn.canPlayerEdit(
								blockpos.offset(raytraceresult.sideHit),
								raytraceresult.sideHit, itemstack)) {
					return new ActionResult(EnumActionResult.PASS, itemstack);
				}
				if (worldIn.getBlockState(blockpos).getMaterial() == Material.WATER) {
					worldIn.playSound(playerIn, playerIn.posX, playerIn.posY,
							playerIn.posZ, SoundEvents.ITEM_BOTTLE_FILL,
							SoundCategory.NEUTRAL, 1.0F, 1.0F);
					return new ActionResult(EnumActionResult.SUCCESS,
							this.turnBottleIntoItem(itemstack, playerIn,new ItemStack(LovecraftMain.itemBeaker)));
				} else if((worldIn.getBlockState(blockpos).getBlock() == Blocks.CAULDRON && worldIn.getBlockState(blockpos).getValue(BlockCauldron.LEVEL) > 0)) {
					worldIn.setBlockState(blockpos, Blocks.CAULDRON.getDefaultState().withProperty(BlockCauldron.LEVEL, Integer.valueOf(MathHelper.clamp(worldIn.getBlockState(blockpos).getValue(BlockCauldron.LEVEL)-1, 0, 3))), 2);
			        worldIn.updateComparatorOutputLevel(blockpos, Blocks.CAULDRON);
					worldIn.playSound(playerIn, playerIn.posX, playerIn.posY,
							playerIn.posZ, SoundEvents.ITEM_BOTTLE_FILL,
							SoundCategory.NEUTRAL, 1.0F, 1.0F);
					return new ActionResult(EnumActionResult.SUCCESS,
							this.turnBottleIntoItem(itemstack, playerIn,new ItemStack(LovecraftMain.itemBeaker)));
				}
			}

			return new ActionResult(EnumActionResult.PASS, itemstack);
		}
	}
	
	protected ItemStack turnBottleIntoItem(ItemStack p_185061_1_, EntityPlayer player, ItemStack stack)
    {
        p_185061_1_.shrink(1);

        if (p_185061_1_.isEmpty())
        {
            return stack;
        }
        else
        {
            if (!player.inventory.addItemStackToInventory(stack))
            {
                player.dropItem(stack, false);
            }

            return p_185061_1_;
        }
    }

}
