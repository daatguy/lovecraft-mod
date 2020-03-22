package io.github.daatguy.mods.minecraft.lovecraft.client.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import io.github.daatguy.mods.minecraft.lovecraft.book.spell.Spell;
import io.github.daatguy.mods.minecraft.lovecraft.core.LovecraftMain;
import io.github.daatguy.mods.minecraft.lovecraft.networking.SpellMessage;
import io.github.daatguy.mods.minecraft.lovecraft.tileentity.TileEntityAltar;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import io.github.daatguy.mods.minecraft.lovecraft.container.ContainerAltar;

public class GuiAltar extends GuiContainer {

	private static final ResourceLocation texture = new ResourceLocation(
			"lovecraft", "textures/gui/altar.png");
	private InventoryPlayer player;
	private TileEntityAltar tile;
	private GuiButton buttonCast;
	private static ContainerAltar container;
	private int[] xPositions = new int[9];
	private int[] yPositions = new int[9];

	final int BUTTON_CAST = 0;

	public GuiAltar(InventoryPlayer player, TileEntityAltar tile) {
		super(container = new ContainerAltar(player, tile));
		this.player = player;
		this.tile = tile;
		xSize = 176;
		ySize = 243;
		xPositions[0] = 79;
		yPositions[0] = 19;
		xPositions[1] = 105;
		yPositions[1] = 29;
		xPositions[2] = 115;
		yPositions[2] = 55;
		xPositions[3] = 105;
		yPositions[3] = 81;
		xPositions[4] = 79;
		yPositions[4] = 91;
		xPositions[5] = 53;
		yPositions[5] = 81;
		xPositions[6] = 43;
		yPositions[6] = 55;
		xPositions[7] = 53;
		yPositions[7] = 29;
		xPositions[8] = 79;
		yPositions[8] = 55;
	}

	@Override
	public void initGui() {
		buttonList.clear();
		super.initGui();
		buttonList.add(buttonCast = new GuiButton(BUTTON_CAST, guiLeft + 49,
				guiTop + 123, 78, 20, I18n.format("tooltip.altar_cast")));
		buttonList.get(0).enabled = false;
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		switch (button.id) {
		case BUTTON_CAST:
			IItemHandler inventory = this.tile.getCapability(
					CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
			if (!inventory.getStackInSlot(0).isEmpty()) {
				Spell spell = LovecraftMain.spellHandler.spells.get(inventory
						.getStackInSlot(0).getTagCompound().getString("Spell"));
				if (spell.canStart(tile.getWorld(), tile.getPos())) {
					// new SpellMessage(inventory
					// .getStackInSlot(0).getTagCompound().getString("Spell"))
					LovecraftMain.packetHandler.INSTANCE
							.sendToServer(new SpellMessage(inventory
									.getStackInSlot(0).getTagCompound()
									.getString("Spell"), tile));
					if (spell.closesAltar) {
						mc.player.closeScreen();
					}
				}
			}
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);

		IItemHandler inventory = this.tile.getCapability(
				CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

		// Draw Altar Title
		fontRenderer.drawString(new TextComponentTranslation("tile.altar.name")
				.getFormattedText(), 8, 6, Color.darkGray.getRGB());

		// Draw Player Inventory Title
		fontRenderer.drawString(this.player.getDisplayName()
				.getUnformattedText(), 8, ySize - 94, Color.darkGray.getRGB());

		// Draw different versions of the button based on if there is a book in
		// the altar
		if (inventory.getStackInSlot(0).isEmpty()) {

			// Deactivate BUTTON_CAST
			buttonCast.enabled = false;

			if (tile.getObeliskType() == TileEntityAltar.CHARGED_OBELISK) {

				GlStateManager.pushMatrix();
				GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
				Minecraft.getMinecraft().getTextureManager()
						.bindTexture(texture);
				drawTexturedModalRect(143, 124, 177, 37, 18, 18);
				GlStateManager.popMatrix();

				List<String> text = new ArrayList<String>();
				text.add(TextFormatting.GREEN
						+ I18n.format("tooltip.charged_obelisks.found"));
				drawTooltip(text, mouseX, mouseY, 143, 124, 18, 18);
			} else if (tile.getObeliskType() == TileEntityAltar.CARVED_OBELISK) {

				GlStateManager.pushMatrix();
				GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
				Minecraft.getMinecraft().getTextureManager()
						.bindTexture(texture);
				drawTexturedModalRect(143, 124, 177, 19, 18, 18);
				GlStateManager.popMatrix();

				List<String> text = new ArrayList<String>();
				text.add(TextFormatting.GREEN
						+ I18n.format("tooltip.carved_obelisks.found"));
				drawTooltip(text, mouseX, mouseY, 143, 124, 18, 18);
			} else if (tile.getObeliskType() == TileEntityAltar.SIMPLE_OBELISK) {

				GlStateManager.pushMatrix();
				GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
				Minecraft.getMinecraft().getTextureManager()
						.bindTexture(texture);
				drawTexturedModalRect(143, 124, 177, 1, 18, 18);
				GlStateManager.popMatrix();

				List<String> text = new ArrayList<String>();
				text.add(TextFormatting.GREEN
						+ I18n.format("tooltip.simple_obelisks.found"));
				drawTooltip(text, mouseX, mouseY, 143, 124, 18, 18);
			} else {

				// THE GUI TEXTURE HAS THE "NO OBELISK" TEXTURE ALREADY DRAWN ON
				// IT

				List<String> text = new ArrayList<String>();
				text.add(TextFormatting.GREEN
						+ I18n.format("tooltip.empty_obelisks.found"));
				drawTooltip(text, mouseX, mouseY, 143, 124, 18, 18);
			}

			// There is a tome in the altar
		} else {

			Spell spell = LovecraftMain.spellHandler.spells.get(inventory
					.getStackInSlot(0).getTagCompound().getString("Spell"));

			if (spell.obeliskLevel == TileEntityAltar.CHARGED_OBELISK) {
				if (tile.getObeliskType() >= TileEntityAltar.CHARGED_OBELISK) {

					// IF ITS THERE
					// System.out.println("hit");
					GlStateManager.pushMatrix();
					GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
					Minecraft.getMinecraft().getTextureManager()
							.bindTexture(texture);
					drawTexturedModalRect(143, 124, 177, 37, 18, 18);
					GlStateManager.popMatrix();

					List<String> text = new ArrayList<String>();
					text.add(TextFormatting.GREEN
							+ I18n.format("tooltip.charged_obelisks.found"));
					drawTooltip(text, mouseX, mouseY, 143, 124, 18, 18);

				} else {

					// IF IT ISNT
					// System.out.println("fail");
					GlStateManager.pushMatrix();
					GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
					Minecraft.getMinecraft().getTextureManager()
							.bindTexture(texture);
					drawTexturedModalRect(143, 124, 195, 37, 18, 18);
					GlStateManager.popMatrix();

					List<String> text = new ArrayList<String>();
					text.add(TextFormatting.RED
							+ I18n.format("tooltip.charged_obelisks.missing"));
					drawTooltip(text, mouseX, mouseY, 143, 124, 18, 18);

				}
			} else if (spell.obeliskLevel == TileEntityAltar.CARVED_OBELISK) {
				if (tile.getObeliskType() >= TileEntityAltar.CARVED_OBELISK) {

					// IF ITS THERE
					// System.out.println("FOund CARVED OR GREATER");
					// System.out.println("hit");
					GlStateManager.pushMatrix();
					GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
					Minecraft.getMinecraft().getTextureManager()
							.bindTexture(texture);
					drawTexturedModalRect(143, 124, 177, 19, 18, 18);
					GlStateManager.popMatrix();

					List<String> text = new ArrayList<String>();
					text.add(TextFormatting.GREEN
							+ I18n.format("tooltip.carved_obelisks.found"));
					drawTooltip(text, mouseX, mouseY, 143, 124, 18, 18);

				} else {

					// IF IT ISNT
					// System.out.println("DIDNT FIND CARVED OR GREATER");
					// System.out.println("fail");
					GlStateManager.pushMatrix();
					GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
					Minecraft.getMinecraft().getTextureManager()
							.bindTexture(texture);
					drawTexturedModalRect(143, 124, 195, 19, 18, 18);
					GlStateManager.popMatrix();

					List<String> text = new ArrayList<String>();
					text.add(TextFormatting.RED
							+ I18n.format("tooltip.carved_obelisks.missing"));
					drawTooltip(text, mouseX, mouseY, 143, 124, 18, 18);

				}
			} else if (spell.obeliskLevel == TileEntityAltar.SIMPLE_OBELISK) {
				if (tile.getObeliskType() >= TileEntityAltar.SIMPLE_OBELISK) {

					// IF ITS THERE
					// System.out.println("hit");
					GlStateManager.pushMatrix();
					GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
					Minecraft.getMinecraft().getTextureManager()
							.bindTexture(texture);
					drawTexturedModalRect(143, 124, 177, 1, 18, 18);
					GlStateManager.popMatrix();

					List<String> text = new ArrayList<String>();
					text.add(TextFormatting.GREEN
							+ I18n.format("tooltip.simple_obelisks.found"));
					drawTooltip(text, mouseX, mouseY, 143, 124, 18, 18);

				} else {

					// IF IT ISNT
					// System.out.println("fail");
					GlStateManager.pushMatrix();
					GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
					Minecraft.getMinecraft().getTextureManager()
							.bindTexture(texture);
					drawTexturedModalRect(143, 124, 195, 1, 18, 18);
					GlStateManager.popMatrix();

					List<String> text = new ArrayList<String>();
					text.add(TextFormatting.RED
							+ I18n.format("tooltip.simple_obelisks.missing"));
					drawTooltip(text, mouseX, mouseY, 143, 124, 18, 18);

				}
			} else {

				List<String> text = new ArrayList<String>();
				text.add(TextFormatting.GREEN
						+ I18n.format("tooltip.empty_obelisks.found"));
				drawTooltip(text, mouseX, mouseY, 143, 124, 18, 18);

			}

			if (spell.canStart(tile.getWorld(), tile.getPos())) {

				// Activate BUTTON_CAST
				buttonCast.enabled = true;

			} else {

				// Deactivate BUTTON_CAST
				buttonCast.enabled = false;

			}

			// Draw faded items
			/*
			 * for(int i = 0; i < 9; i++) { if(tile.getCapability(
			 * CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)
			 * .getStackInSlot(i + 1).isEmpty() && spell.recipe[i]!=null) {
			 * 
			 * 
			 * } }
			 */

			drawSpellSlotTooltips(mouseX, mouseY, inventory);

		}
	}

	public void drawSpellSlotTooltips(int mouseX, int mouseY,
			IItemHandler inventory) {

		int width = 18;
		int height = 18;

		if (!inventory.getStackInSlot(0).isEmpty()) {
			Spell spell = LovecraftMain.spellHandler.spells.get(inventory
					.getStackInSlot(0).getTagCompound().getString("Spell"));
			for (int i = 0; i < 9; i++) {
				if (inventory.getStackInSlot(i + 1).isEmpty()
						&& spell.recipe[i] != null) {

					List<String> text = new ArrayList<String>();

					// text.add(I18n.format("spell."
					// + inventory.getStackInSlot(0).getTagCompound()
					// .getString("Spell") + ".name")
					// + " requires ");
					text.add(spell.recipe[i].getDisplayName());

					drawTooltip(text, mouseX, mouseY, xPositions[i],
							yPositions[i], 18, 18);
				}
			}
		}
	}

	public void drawSpellSlots(int mouseX, int mouseY, IItemHandler inventory) {

		int width = 18;
		int height = 18;

		if (!inventory.getStackInSlot(0).isEmpty()) {
			Spell spell = LovecraftMain.spellHandler.spells.get(inventory
					.getStackInSlot(0).getTagCompound().getString("Spell"));

			for (int i = 0; i < 9; i++) {
				if (inventory.getStackInSlot(i + 1).isEmpty()
						&& spell.recipe[i] != null) {
					GlStateManager.pushMatrix();
					GlStateManager.translate(guiLeft, guiTop, -150.0f);
					mc.getRenderItem().renderItemAndEffectIntoGUI(
							spell.recipe[i], xPositions[i] + 1,
							yPositions[i] + 1);
					GlStateManager.enableAlpha();
					GlStateManager.enableBlend();
					GlStateManager.popMatrix();

					GlStateManager.pushMatrix();
					GlStateManager.translate(guiLeft, guiTop, 55.0f);
					GlStateManager.enableAlpha();
					GlStateManager.enableBlend();
					GlStateManager.color(1.0f, 1.0f, 1.0f, 0.7f);
					Minecraft.getMinecraft().getTextureManager()
							.bindTexture(texture);
					drawTexturedModalRect(xPositions[i], yPositions[i],
							xPositions[i], yPositions[i], 18, 18);
					GlStateManager.popMatrix();
				}
			}
		}
	}

	public void drawTooltip(List<String> lines, int mouseX, int mouseY,
			int posX, int posY, int width, int height) {
		mouseX = mouseX - guiLeft;
		mouseY = mouseY - guiTop;
		if (mouseX >= posX && mouseX <= posX + width && mouseY >= posY
				&& mouseY <= posY + height) {
			GlStateManager.pushMatrix();
			drawHoveringText(lines, mouseX, mouseY, fontRenderer);
			GlStateManager.popMatrix();
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks,
			int mouseX, int mouseY) {

		GlStateManager.pushMatrix();
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.zLevel = 0.0f;
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		GlStateManager.popMatrix();

		IItemHandler inventory = this.tile.getCapability(
				CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

		drawSpellSlots(mouseX, mouseY, inventory);

	}

}
