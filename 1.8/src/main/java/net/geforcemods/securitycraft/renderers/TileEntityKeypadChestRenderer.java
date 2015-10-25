package net.geforcemods.securitycraft.renderers;

import java.util.Calendar;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.model.ModelLargeChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntityKeypadChestRenderer extends TileEntitySpecialRenderer
{
    private static final ResourceLocation trappedDouble = new ResourceLocation("textures/entity/chest/trapped_double.png");
    private static final ResourceLocation christmasDouble = new ResourceLocation("securitycraft:textures/entity/chest/christmas_double.png");
    private static final ResourceLocation normalDoubleUnactive = new ResourceLocation("securitycraft:textures/entity/chest/doubleChestUnactive.png");
    private static final ResourceLocation normalDoubleActive = new ResourceLocation("securitycraft:textures/entity/chest/doubleChestActive.png");
    private static final ResourceLocation trappedNormal = new ResourceLocation("textures/entity/chest/trapped.png");
    private static final ResourceLocation christmasNormal = new ResourceLocation("securitycraft:textures/entity/chest/christmas.png");
    private static final ResourceLocation normalSingleUnactive = new ResourceLocation("securitycraft:textures/entity/chest/chestUnactive.png");
    private static final ResourceLocation normalSingleActive = new ResourceLocation("securitycraft:textures/entity/chest/chestActive.png");
    private ModelChest field_147510_h = new ModelChest();
    private ModelChest field_147511_i = new ModelLargeChest();
    private boolean field_147509_j;

    public TileEntityKeypadChestRenderer()
    {
        Calendar calendar = Calendar.getInstance();

        if (calendar.get(2) + 1 == 12 && calendar.get(5) >= 24 && calendar.get(5) <= 26)
        {
            this.field_147509_j = true;
        }
    }
    
    public void func_180538_a(TileEntityChest p_180538_1_, double p_180538_2_, double p_180538_4_, double p_180538_6_, float p_180538_8_, int p_180538_9_)
    {
        int j;

        if (!p_180538_1_.hasWorldObj())
        {
            j = 0;
        }
        else
        {
            Block block = p_180538_1_.getBlockType();
            j = p_180538_1_.getBlockMetadata();

            if (block instanceof BlockChest && j == 0)
            {
                ((BlockChest)block).checkForSurroundingChests(p_180538_1_.getWorld(), p_180538_1_.getPos(), p_180538_1_.getWorld().getBlockState(p_180538_1_.getPos()));
                j = p_180538_1_.getBlockMetadata();
            }

            p_180538_1_.checkForAdjacentChests();
        }

        if (p_180538_1_.adjacentChestZNeg == null && p_180538_1_.adjacentChestXNeg == null)
        {
            ModelChest modelchest;

            if (p_180538_1_.adjacentChestXPos == null && p_180538_1_.adjacentChestZPos == null)
            {
                modelchest = this.field_147510_h;

                if (p_180538_9_ >= 0)
                {
                    this.bindTexture(DESTROY_STAGES[p_180538_9_]);
                    GlStateManager.matrixMode(5890);
                    GlStateManager.pushMatrix();
                    GlStateManager.scale(4.0F, 4.0F, 1.0F);
                    GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
                    GlStateManager.matrixMode(5888);
                }
                else if (p_180538_1_.getChestType() == 1)
                {
                    this.bindTexture(trappedNormal);
                }
                else if (this.field_147509_j)
                {
                    this.bindTexture(christmasNormal);
                }
                else
                {
                	if(p_180538_1_.lidAngle >= 0.9){
                		this.bindTexture(normalSingleActive);
                	}else{
                		this.bindTexture(normalSingleUnactive);
                	}
                }
            }
            else
            {
                modelchest = this.field_147511_i;

                if (p_180538_9_ >= 0)
                {
                    this.bindTexture(DESTROY_STAGES[p_180538_9_]);
                    GlStateManager.matrixMode(5890);
                    GlStateManager.pushMatrix();
                    GlStateManager.scale(8.0F, 4.0F, 1.0F);
                    GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
                    GlStateManager.matrixMode(5888);
                }
                else if (p_180538_1_.getChestType() == 1)
                {
                    this.bindTexture(trappedDouble);
                }
                else if (this.field_147509_j)
                {
                    this.bindTexture(christmasDouble);
                }
                else
                {
                	if(p_180538_1_.lidAngle >= 0.9){
                		this.bindTexture(normalDoubleActive);
                	}else{
                		this.bindTexture(normalDoubleUnactive);
                	}
                }
            }

            GlStateManager.pushMatrix();
            GlStateManager.enableRescaleNormal();

            if (p_180538_9_ < 0)
            {
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            }

            GlStateManager.translate((float)p_180538_2_, (float)p_180538_4_ + 1.0F, (float)p_180538_6_ + 1.0F);
            GlStateManager.scale(1.0F, -1.0F, -1.0F);
            GlStateManager.translate(0.5F, 0.5F, 0.5F);
            short short1 = 0;

            if (j == 2)
            {
                short1 = 180;
            }

            if (j == 3)
            {
                short1 = 0;
            }

            if (j == 4)
            {
                short1 = 90;
            }

            if (j == 5)
            {
                short1 = -90;
            }

            if (j == 2 && p_180538_1_.adjacentChestXPos != null)
            {
                GlStateManager.translate(1.0F, 0.0F, 0.0F);
            }

            if (j == 5 && p_180538_1_.adjacentChestZPos != null)
            {
                GlStateManager.translate(0.0F, 0.0F, -1.0F);
            }

            GlStateManager.rotate((float)short1, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(-0.5F, -0.5F, -0.5F);
            float f1 = p_180538_1_.prevLidAngle + (p_180538_1_.lidAngle - p_180538_1_.prevLidAngle) * p_180538_8_;
            float f2;

            if (p_180538_1_.adjacentChestZNeg != null)
            {
                f2 = p_180538_1_.adjacentChestZNeg.prevLidAngle + (p_180538_1_.adjacentChestZNeg.lidAngle - p_180538_1_.adjacentChestZNeg.prevLidAngle) * p_180538_8_;

                if (f2 > f1)
                {
                    f1 = f2;
                }
            }

            if (p_180538_1_.adjacentChestXNeg != null)
            {
                f2 = p_180538_1_.adjacentChestXNeg.prevLidAngle + (p_180538_1_.adjacentChestXNeg.lidAngle - p_180538_1_.adjacentChestXNeg.prevLidAngle) * p_180538_8_;

                if (f2 > f1)
                {
                    f1 = f2;
                }
            }

            f1 = 1.0F - f1;
            f1 = 1.0F - f1 * f1 * f1;
            modelchest.chestLid.rotateAngleX = -(f1 * (float)Math.PI / 2.0F);
            modelchest.renderAll();
            GlStateManager.disableRescaleNormal();
            GlStateManager.popMatrix();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

            if (p_180538_9_ >= 0)
            {
                GlStateManager.matrixMode(5890);
                GlStateManager.popMatrix();
                GlStateManager.matrixMode(5888);
            }
        }
    }

    public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par3, double par4, float par5, int par6){
    	this.func_180538_a((TileEntityChest) par1TileEntity, par2, par3, par4, par5, par6);
    }

}