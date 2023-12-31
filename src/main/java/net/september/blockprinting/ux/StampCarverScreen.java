package net.september.blockprinting.ux;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.september.blockprinting.BlockPrinting;

import javax.annotation.ParametersAreNonnullByDefault;

public class StampCarverScreen extends AbstractContainerScreen<StampCarverMenu> {

    private static final ResourceLocation TextureLoc =
            new ResourceLocation(BlockPrinting.MOD_ID, "textures/ux/stamp_carver_ux.png");

    public StampCarverScreen(StampCarverMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        //this.addRenderableWidget(new NavButtonWidget(187, 212, ));
       // this.addRenderableWidget(new NavButtonWidget(219, 220, ));
        //this.addRenderableWidget(new NavButtonWidget(235, 212, ));
        //this.addRenderableWidget(new NavButtonWidget(267, 220, ));
    }

    @Override
    @ParametersAreNonnullByDefault
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pGuiGraphics);
        this.inventoryLabelY = -400;
      //  this.renderLabels(pGuiGraphics, pMouseX, pMouseY);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        pGuiGraphics.blit(TextureLoc, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

    }

    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        super.renderLabels(pGuiGraphics, pMouseX, pMouseY);
    }
}
