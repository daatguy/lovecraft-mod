package daatguy.lovecraft.client.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import daatguy.lovecraft.book.DeskRecipe;
import daatguy.lovecraft.container.ContainerDesk;
import daatguy.lovecraft.core.LovecraftMain;

public class GuiDesk extends GuiContainer {

	private static final ResourceLocation texture = new ResourceLocation(
			"lovecraft", "textures/gui/desk.png");
	private InventoryPlayer player;
	private GuiButton buttonCast;
	private static ContainerDesk container;
	private int[] xPositions = new int[9];
	private int[] yPositions = new int[9];

	final int BUTTON_CAST = 0;

	public GuiDesk(InventoryPlayer player, BlockPos pos) {
		super(container = new ContainerDesk(player, pos));
		this.player = player;
		xSize = 176;
		ySize = 166;
	}

	@Override
	public void initGui() {
		super.initGui();
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

		fontRenderer.drawString(new TextComponentTranslation("tile.desk.name")
				.getFormattedText(), 95, 12, Color.darkGray.getRGB());

		ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();

		for (int i = 0; i < 3; i++) {
			stacks.add(container.craftMatrix.getStackInSlot(i));
		}

		for (DeskRecipe recipe : LovecraftMain.deskHandler.deskRecipes) {

			ItemStack neededItem = recipe.getNeededNoteItem(stacks);

			// System.out.println(neededItem);

			if (neededItem != null) {
				for (int i = 0; i < container.craftMatrix.getSizeInventory(); i++) {
					if (container.craftMatrix.getStackInSlot(i).isEmpty()) {
						drawGhostItemTooltip(neededItem, mouseX, mouseY,
								container.inventorySlots.get(i + 1).xPos,
								container.inventorySlots.get(i + 1).yPos, neededItem.getItem().equals(Items.STRING));
						break;
					}
				}
				break;
			}
		}
	}

	public void drawGhostItem(ItemStack item, int mouseX, int mouseY, int x, int y) {

		GlStateManager.pushMatrix();
		GlStateManager.translate(guiLeft, guiTop, -150.0f);
		mc.getRenderItem().renderItemAndEffectIntoGUI(item,
				x, y);
		GlStateManager.enableAlpha();
		GlStateManager.enableBlend();
		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		GlStateManager.translate(guiLeft, guiTop, 55.0f);
		GlStateManager.enableAlpha();
		GlStateManager.enableBlend();
		GlStateManager.color(1.0f, 1.0f, 1.0f, 0.7f);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(x, y, x, y, 18, 18);
		GlStateManager.popMatrix();
	}

	public void drawGhostItemTooltip(ItemStack item, int mouseX, int mouseY, int x,
			int y, boolean isBinding) {

		List<String> text = new ArrayList<String>();

		text.add(item.getDisplayName());
		text.add("");
		if (isBinding) {
			text.add(TextFormatting.RED
					+ I18n.format("tile.desk.bindItemDescription1"));
			text.add(TextFormatting.RED
					+ I18n.format("tile.desk.bindItemDescription2"));
		} else {
			text.add(TextFormatting.RED
					+ I18n.format("tile.desk.noteItemDescription1"));
			text.add(TextFormatting.RED
					+ I18n.format("tile.desk.noteItemDescription2"));
		}

		drawTooltip(text, mouseX, mouseY, x, y, 18, 18);
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

		ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();

		for (int i = 0; i < 3; i++) {
			stacks.add(container.craftMatrix.getStackInSlot(i));
		}

		for (DeskRecipe recipe : LovecraftMain.deskHandler.deskRecipes) {
			
			//Fix this to work with itemstacks
			ItemStack neededItem = recipe.getNeededNoteItem(stacks);

			// System.out.println(neededItem);

			if (neededItem != null) {
				for (int i = 0; i < container.craftMatrix.getSizeInventory(); i++) {
					if (container.craftMatrix.getStackInSlot(i).isEmpty()) {
						drawGhostItem(neededItem, mouseX, mouseY,
								container.inventorySlots.get(i + 1).xPos,
								container.inventorySlots.get(i + 1).yPos);
						break;
					}
				}
				break;
			}
		}

	}

}
