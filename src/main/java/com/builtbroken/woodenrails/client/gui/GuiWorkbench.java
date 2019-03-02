package com.builtbroken.woodenrails.client.gui;

import javax.annotation.Nullable;

import com.builtbroken.woodenrails.container.ContainerCartWorkbench;

import net.minecraft.client.gui.GuiButtonImage;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.recipebook.GuiRecipeBook;
import net.minecraft.client.gui.recipebook.IRecipeShownListener;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * @author p455w0rd
 *
 */
public class GuiWorkbench extends GuiContainer implements IRecipeShownListener {

	private static final ResourceLocation CRAFTING_TABLE_GUI_TEXTURES = new ResourceLocation("textures/gui/container/crafting_table.png");
	private static final ResourceLocation RECIPE_BUTTON_TEXTURE = new ResourceLocation("textures/gui/recipe_button.png");
	private final GuiRecipeBook recipeBookGui = new GuiRecipeBook();
	private boolean widthTooNarrow;
	private final InventoryPlayer field_212354_A;

	public GuiWorkbench(InventoryPlayer playerInv, World worldIn) {
		this(playerInv, worldIn, BlockPos.ORIGIN);
	}

	public GuiWorkbench(InventoryPlayer playerInv, World worldIn, BlockPos blockPosition) {
		super(new ContainerCartWorkbench(playerInv, worldIn, blockPosition));
		field_212354_A = playerInv;
	}

	@Override
	protected void initGui() {
		super.initGui();
		widthTooNarrow = width < 379;
		recipeBookGui.func_201520_a(width, height, mc, widthTooNarrow, (ContainerRecipeBook) inventorySlots);
		guiLeft = recipeBookGui.updateScreenPosition(widthTooNarrow, width, xSize);
		children.add(recipeBookGui);
		this.addButton(new GuiButtonImage(10, guiLeft + 5, height / 2 - 49, 20, 18, 0, 0, 19, RECIPE_BUTTON_TEXTURE) {
			@Override
			public void onClick(double mouseX, double mouseY) {
				recipeBookGui.func_201518_a(widthTooNarrow);
				recipeBookGui.toggleVisibility();
				GuiWorkbench.this.guiLeft = recipeBookGui.updateScreenPosition(widthTooNarrow, GuiWorkbench.this.width, GuiWorkbench.this.xSize);
				setPosition(GuiWorkbench.this.guiLeft + 5, GuiWorkbench.this.height / 2 - 49);
			}
		});
	}

	@Override
	@Nullable
	public IGuiEventListener getFocused() {
		return recipeBookGui;
	}

	@Override
	public void tick() {
		super.tick();
		recipeBookGui.tick();
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		if (recipeBookGui.isVisible() && widthTooNarrow) {
			drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
			recipeBookGui.render(mouseX, mouseY, partialTicks);
		}
		else {
			recipeBookGui.render(mouseX, mouseY, partialTicks);
			super.render(mouseX, mouseY, partialTicks);
			recipeBookGui.renderGhostRecipe(guiLeft, guiTop, true, partialTicks);
		}

		renderHoveredToolTip(mouseX, mouseY);
		recipeBookGui.renderTooltip(guiLeft, guiTop, mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		fontRenderer.drawString(I18n.format("container.crafting"), 28.0F, 6.0F, 4210752);
		fontRenderer.drawString(field_212354_A.getDisplayName().getFormattedText(), 8.0F, ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(CRAFTING_TABLE_GUI_TEXTURES);
		int i = guiLeft;
		int j = (height - ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, xSize, ySize);
	}

	@Override
	protected boolean isPointInRegion(int p_195359_1_, int p_195359_2_, int p_195359_3_, int p_195359_4_, double p_195359_5_, double p_195359_7_) {
		return (!widthTooNarrow || !recipeBookGui.isVisible()) && super.isPointInRegion(p_195359_1_, p_195359_2_, p_195359_3_, p_195359_4_, p_195359_5_, p_195359_7_);
	}

	@Override
	public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
		if (recipeBookGui.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_)) {
			return true;
		}
		else {
			return widthTooNarrow && recipeBookGui.isVisible() ? true : super.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_);
		}
	}

	@Override
	protected boolean hasClickedOutside(double p_195361_1_, double p_195361_3_, int p_195361_5_, int p_195361_6_, int p_195361_7_) {
		boolean flag = p_195361_1_ < p_195361_5_ || p_195361_3_ < p_195361_6_ || p_195361_1_ >= p_195361_5_ + xSize || p_195361_3_ >= p_195361_6_ + ySize;
		return recipeBookGui.func_195604_a(p_195361_1_, p_195361_3_, guiLeft, guiTop, xSize, ySize, p_195361_7_) && flag;
	}

	@Override
	protected void handleMouseClick(Slot slotIn, int slotId, int mouseButton, ClickType type) {
		super.handleMouseClick(slotIn, slotId, mouseButton, type);
		recipeBookGui.slotClicked(slotIn);
	}

	@Override
	public void recipesUpdated() {
		recipeBookGui.recipesUpdated();
	}

	@Override
	public void onGuiClosed() {
		recipeBookGui.removed();
		super.onGuiClosed();
	}

	@Override
	public GuiRecipeBook func_194310_f() {
		return recipeBookGui;
	}
}