package io.github.daatguy.minecraft.mods.lovecraft.client.sound;

import io.github.daatguy.minecraft.mods.lovecraft.core.LovecraftMain;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MovingSoundDrugged extends MovingSound
{
    private final EntityPlayerSP player;
    
    /**
	 * A moving sound to keep the drug noises in your ears if you move
	 */
    public MovingSoundDrugged(EntityPlayerSP playerIn)
    {
        super(SoundEventHandler.DRUGGED, SoundCategory.MASTER);
        this.player = playerIn;
        this.repeat = false;
        this.repeatDelay = 0;
        this.volume = 1.0F;
        this.pitch = 1.0F;
        this.xPosF = (float)this.player.getPositionVector().x;
        this.yPosF = (float)this.player.getPositionVector().y;
        this.zPosF = (float)this.player.getPositionVector().z;
    }

    @Override
    public void update() {
        this.xPosF = (float)this.player.getPositionVector().x;
        this.yPosF = (float)this.player.getPositionVector().y;
        this.zPosF = (float)this.player.getPositionVector().z;
    }

    @Override
    public boolean isDonePlaying() {
    	for(PotionEffect pe : this.player.getActivePotionEffects()) {
    		if(pe.getPotion().equals(LovecraftMain.potionDrugged)) return super.isDonePlaying();
    	}
    	return true;
    }
    
}
