package net.september.blockprinting.ux;

import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.resources.ResourceLocation;
import net.september.blockprinting.BlockPrinting;

public class NavButtonWidget extends ImageButton {

    private static final ResourceLocation TextureLoc =
            new ResourceLocation(BlockPrinting.MOD_ID, "textures/ux/stamp_carver_ux.png");

    private NavButtonWidget(int pX, int pXTexStart, OnPress pOnpress){
        super(pX, 108, 8, 16, pXTexStart, 0, 16, TextureLoc, pOnpress);
    }


    private NavButtonWidget(int pX, int pY, int pWidth, int pHeight, int pXTexStart, int pYTexStart, ResourceLocation pResourceLocation, OnPress pOnPress) {
        super(pX, pY, pWidth, pHeight, pXTexStart, pYTexStart, pResourceLocation, pOnPress);
    }
}
