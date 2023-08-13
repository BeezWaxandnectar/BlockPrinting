/*
package net.september.blockprinting.block.entity.legacyCarver;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.september.blockprinting.BlockPrinting;
import org.jetbrains.annotations.NotNull;

public class LegacyStampCarverScreen extends AbstractContainerScreen<LegacyStampCarverMenu> {

    private static final ResourceLocation TextureLoc =
            new ResourceLocation(BlockPrinting.MOD_ID, "textures/ux/stamp_carver_ux.png");

    public LegacyStampCarverScreen(LegacyStampCarverMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
      //  pMenu.registerUpdateListener(this::containerChanged);
    }

    @Override
    protected void init() {
        super.init();

    }


    protected void renderBg(GuiGraphics pGuigraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TextureLoc);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        pGuigraphics.blit(TextureLoc, x, y, 0, 0,  256, 256);
        renderNavButtons(pGuigraphics, x, y, "LEFT", 0);
        renderNavButtons(pGuigraphics, x, y, "LEFT", 2);
        renderNavButtons(pGuigraphics, x, y, "RIGHT", 1);
        renderNavButtons(pGuigraphics, x, y, "RIGHT", 3);
    }

    @Override
    public void render(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    public int SidedArrowSpriteFinder(String dir){

        if (dir.equals("LEFT")){
            return 212;
        }
        if (dir.equals("RIGHT")){
            return 220;

        } else {
            throw new IllegalArgumentException("You must assign the arrows as either LEFT or RIGHT");
        }

    }

    public int NavPlacer(int index) {
        return switch (index) {
            case 0 -> 44;
            case 1 -> 76;
            case 2 -> 92;
            case 3 -> 124;
            default -> -1;
        };
    }

    private void renderNavButtons(GuiGraphics pGuiGraphics, int x, int y, String direction, int index) {
        pGuiGraphics.blit(TextureLoc, x + NavPlacer(index), y + 65,
                SidedArrowSpriteFinder(direction), 0, 8, 16);
    }
*/
/*

    private void containerChanged() {
        this.displayRecipes = this.menu.();
        if (!this.displayRecipes) {
            this.scrollOffs = 0.0F;
            this.startIndex = 0;
        }

    }

*//*



}
*/
