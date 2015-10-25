package net.geforcemods.securitycraft.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import net.geforcemods.securitycraft.api.CustomizableSCTE;
import net.geforcemods.securitycraft.api.IExplosive;
import net.geforcemods.securitycraft.api.IOwnable;
import net.geforcemods.securitycraft.api.IPasswordProtected;
import net.geforcemods.securitycraft.gui.components.CustomHoverChecker;
import net.geforcemods.securitycraft.main.mod_SecurityCraft;
import net.geforcemods.securitycraft.tileentity.TileEntitySCTE;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GuiSCManual extends GuiScreen {

	private ResourceLocation infoBookTexture = new ResourceLocation("securitycraft:textures/gui/infoBookTexture.png");
	private ResourceLocation infoBookTitlePage = new ResourceLocation("securitycraft:textures/gui/infoBookTitlePage.png");
	private ResourceLocation infoBookIcons = new ResourceLocation("securitycraft:textures/gui/infoBookIcons.png");
	private static ResourceLocation bookGuiTextures = new ResourceLocation("textures/gui/book.png");
	
    private List<CustomHoverChecker> hoverCheckers = new ArrayList<CustomHoverChecker>();
    private int currentPage = -1;
    private ItemStack[] recipe;
    
	public GuiSCManual() {
		super();
	}
	
	public void initGui(){		
		Keyboard.enableRepeatEvents(true);

        int i = (this.width - 256) / 2;
        byte b0 = 2;
        GuiSCManual.NextPageButton nextButton = new GuiSCManual.NextPageButton(1, i + 210, b0 + 158, true);
        GuiSCManual.NextPageButton prevButton = new GuiSCManual.NextPageButton(2, i + 16, b0 + 158, false);

        this.buttonList.add(nextButton);
        this.buttonList.add(prevButton);
    }
	
	public void drawScreen(int par1, int par2, float par3){		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		if(this.currentPage == -1){
	    	this.mc.getTextureManager().bindTexture(infoBookTitlePage);
		}else{
	    	this.mc.getTextureManager().bindTexture(infoBookTexture);
		}
		
	    int k = (this.width - 256) / 2;
	    this.drawTexturedModalRect(k, 5, 0, 0, 256, 250);
	    
	    if(this.currentPage > -1){
	    	this.fontRendererObj.drawString(mod_SecurityCraft.instance.manualPages.get(currentPage).getItemName(), k + 39, 27, 0, false);	
	    	this.fontRendererObj.drawSplitString(mod_SecurityCraft.instance.manualPages.get(currentPage).getHelpInfo(), k + 18, 45, 225, 0);	
	    }else{
	    	this.fontRendererObj.drawString(StatCollector.translateToLocal("gui.scManual.intro.1"), k + 39, 27, 0, false);	
	    	this.fontRendererObj.drawString(StatCollector.translateToLocal("gui.scManual.intro.2"), k + 60, 159, 0, false);	
	    
	    	if(StatCollector.canTranslate("gui.scManual.author")){
		    	this.fontRendererObj.drawString(StatCollector.translateToLocal("gui.scManual.author"), k + 65, 170, 0, false);
	    	}
	    }
	    
	    for(int i = 0; i < this.buttonList.size(); i++){
            ((GuiButton) this.buttonList.get(i)).drawButton(this.mc, par1, par2);
        }
	    
	    if(this.currentPage > -1){
	    	Item item = mod_SecurityCraft.instance.manualPages.get(currentPage).getItem();
	    	GuiUtils.drawItemStackToGui(mc, item, k + 19, 22, !(mod_SecurityCraft.instance.manualPages.get(currentPage).getItem() instanceof ItemBlock));

	    	this.mc.getTextureManager().bindTexture(infoBookIcons);

	    	TileEntity te = ((item instanceof ItemBlock && ((ItemBlock) item).getBlock() instanceof ITileEntityProvider) ? ((ITileEntityProvider) ((ItemBlock) item).getBlock()).createNewTileEntity(Minecraft.getMinecraft().theWorld, 0) : null);
	    	Block itemBlock = ((item instanceof ItemBlock) ? ((ItemBlock) item).getBlock() : null);
	    	
	    	if(itemBlock != null){    		
	    		if(itemBlock instanceof IExplosive){
	    	    	this.drawTexturedModalRect(k + 107, 117, 54, 1, 18, 18);
		    	}	
	    		
	    		if(te != null){
			    	if(te instanceof IOwnable){
		    	    	this.drawTexturedModalRect(k + 29, 118, 1, 1, 16, 16);
			    	}	
			    	
			    	if(te instanceof IPasswordProtected){
		    	    	this.drawTexturedModalRect(k + 55, 118, 18, 1, 17, 16);
			    	}	
			    	
			    	if(te instanceof TileEntitySCTE && ((TileEntitySCTE) te).isActivatedByView()){
		    	    	this.drawTexturedModalRect(k + 81, 118, 36, 1, 17, 16);
			    	}	
			    			    		 
			    	if(te instanceof CustomizableSCTE){
		    	    	this.drawTexturedModalRect(k + 213, 118, 72, 1, 16, 16);
			    	}
	    		}
	    	}
	    	
	    	if(recipe != null){
		    	for(int i = 0; i < 3; i++){
		    		for(int j = 0; j < 3; j++){
		    			if(((i * 3) + j) >= recipe.length){ break; }
		    			if(this.recipe[(i * 3) + j] == null){ continue; }
		    			
		    			if(this.recipe[(i * 3) + j].getItem() instanceof ItemBlock){
			    	    	GuiUtils.drawItemStackToGui(mc, Block.getBlockFromItem(this.recipe[(i * 3) + j].getItem()), (k + 100) + (j * 20), 144 + (i * 20), !(this.recipe[(i * 3) + j].getItem() instanceof ItemBlock));
		    			}else{
			    	    	GuiUtils.drawItemStackToGui(mc, this.recipe[(i * 3) + j].getItem(), this.recipe[(i * 3) + j].getItemDamage(), (k + 100) + (j * 20), 144 + (i * 20), !(this.recipe[(i * 3) + j].getItem() instanceof ItemBlock));
		    			}		    			   
		    		}
		    	}
	    	}	
	    	
	    	for(int i = 0; i < hoverCheckers.size(); i++){
	    		if(hoverCheckers.get(i) != null && hoverCheckers.get(i).checkHover(par1, par2)){
	    			if(hoverCheckers.get(i).getX() == (k + 29)){
	    				this.drawHoveringText(this.mc.fontRendererObj.listFormattedStringToWidth(StatCollector.translateToLocal("gui.scManual.ownableBlock"), 150), par1, par2, this.mc.fontRendererObj);
	    			}else if(hoverCheckers.get(i).getX() == (k + 55)){
	    				this.drawHoveringText(this.mc.fontRendererObj.listFormattedStringToWidth(StatCollector.translateToLocal("gui.scManual.passwordProtectedBlock"), 150), par1, par2, this.mc.fontRendererObj);
	    			}else if(hoverCheckers.get(i).getX() == (k + 81)){
	    				this.drawHoveringText(this.mc.fontRendererObj.listFormattedStringToWidth(StatCollector.translateToLocal("gui.scManual.viewActivatedBlock"), 150), par1, par2, this.mc.fontRendererObj);
	    			}else if(hoverCheckers.get(i).getX() == (k + 107)){
	    				this.drawHoveringText(this.mc.fontRendererObj.listFormattedStringToWidth(StatCollector.translateToLocal("gui.scManual.explosiveBlock"), 150), par1, par2, this.mc.fontRendererObj);
	    			}else if(hoverCheckers.get(i).getX() == (k + 213)){
	    				this.drawHoveringText(this.mc.fontRendererObj.listFormattedStringToWidth(StatCollector.translateToLocal("gui.scManual.customizableBlock"), 150), par1, par2, this.mc.fontRendererObj);
	    			}
	    		}
	    	}
	    }
	    
	    this.updateButtons();
	}
	
	public void onGuiClosed(){
		super.onGuiClosed();
		Keyboard.enableRepeatEvents(false);
	}
	
	protected void keyTyped(char par1, int par2) throws IOException{
		super.keyTyped(par1, par2);
		
		if(par2 == Keyboard.KEY_LEFT && (this.currentPage - 1) > -2){
			this.currentPage--;
			Minecraft.getMinecraft().thePlayer.playSound("random.click", 0.15F, 1.0F);
			this.updateRecipeAndIcons();
		}else if(par2 == Keyboard.KEY_RIGHT && (this.currentPage + 1) < mod_SecurityCraft.instance.manualPages.size()){
			this.currentPage++;
			Minecraft.getMinecraft().thePlayer.playSound("random.click", 0.15F, 1.0F);
			this.updateRecipeAndIcons();
		}
	}
	
    protected void actionPerformed(GuiButton par1GuiButton){
    	if(par1GuiButton.id == 1 && (this.currentPage + 1) < mod_SecurityCraft.instance.manualPages.size()){
    		this.currentPage++;
    		this.updateRecipeAndIcons();
    	}else if(par1GuiButton.id == 2 && (this.currentPage - 1) > -2){
    		this.currentPage--;
    		this.updateRecipeAndIcons();
    	}
    	    	
    	this.updateButtons();
    }
    
    private void updateRecipeAndIcons(){
    	if(this.currentPage < 0){ 
    		recipe = null; 
    		this.hoverCheckers.clear();
    		return;
    	}
    	
		this.hoverCheckers.clear();

    	for(Object object : CraftingManager.getInstance().getRecipeList()){
			if(object instanceof ShapedRecipes){
				ShapedRecipes recipe = (ShapedRecipes) object;
				
				if(recipe.getRecipeOutput() != null && recipe.getRecipeOutput().getItem() == mod_SecurityCraft.instance.manualPages.get(currentPage).getItem()){
					this.recipe = recipe.recipeItems;
					break;
				}
			}else if(object instanceof ShapelessRecipes){
				ShapelessRecipes recipe = (ShapelessRecipes) object;

				if(recipe.getRecipeOutput() != null && recipe.getRecipeOutput().getItem() == mod_SecurityCraft.instance.manualPages.get(currentPage).getItem()){
					this.recipe = this.toItemStackArray(recipe.recipeItems);
					break;
				}
			}
			
			this.recipe = null;
		}
    	
	    int k = (this.width - 256) / 2;
    	Item item = mod_SecurityCraft.instance.manualPages.get(currentPage).getItem();
    	TileEntity te = ((item instanceof ItemBlock && ((ItemBlock) item).getBlock() instanceof ITileEntityProvider) ? ((ITileEntityProvider) ((ItemBlock) item).getBlock()).createNewTileEntity(Minecraft.getMinecraft().theWorld, 0) : null);
    	Block itemBlock = ((item instanceof ItemBlock) ? ((ItemBlock) item).getBlock() : null);

    	if(te != null){
	    	if(te instanceof IOwnable){
	    		this.hoverCheckers.add(new CustomHoverChecker(118, 118 + 16, k + 29, (k + 29) + 16, 20)); 
	    	}	
	    	
	    	if(te instanceof IPasswordProtected){
	    		this.hoverCheckers.add(new CustomHoverChecker(118, 118 + 16, k + 55, (k + 55) + 16, 20)); 
	    	}
	    	
	    	if(te instanceof TileEntitySCTE && ((TileEntitySCTE) te).isActivatedByView()){
	    		this.hoverCheckers.add(new CustomHoverChecker(118, 118 + 16, k + 81, (k + 81) + 16, 20)); 
	    	}
	    	
	    	if(itemBlock instanceof IExplosive){
	    		this.hoverCheckers.add(new CustomHoverChecker(118, 118 + 16, k + 107, (k + 107) + 16, 20)); 
	    	}
	    	
	    	if(te instanceof CustomizableSCTE){
	    		this.hoverCheckers.add(new CustomHoverChecker(118, 118 + 16, k + 213, (k + 213) + 16, 20)); 
	    	}
    	}
    }
    
    private ItemStack[] toItemStackArray(List items){
    	ItemStack[] array = new ItemStack[9];
    	
    	for(int i = 0; i < items.size(); i++){
    		array[i] = (ItemStack) items.get(i);
    	}
    	
    	return array;
    }
    
    private void updateButtons(){
    	if(this.currentPage == -1){
    		((NextPageButton) this.buttonList.get(1)).visible = false;
    	}else if(this.currentPage == mod_SecurityCraft.instance.manualPages.size() - 1){
    		((NextPageButton) this.buttonList.get(0)).visible = false;
    	}else{
    		((NextPageButton) this.buttonList.get(0)).visible = true;
    		((NextPageButton) this.buttonList.get(1)).visible = true;
    	}
    }

	@SideOnly(Side.CLIENT)
    static class NextPageButton extends GuiButton {
		private final boolean field_146151_o;

		public NextPageButton(int par1, int par2, int par3, boolean par4){
			super(par1, par2, par3, 23, 13, "");
			this.field_146151_o = par4;
		}

		/**
		 * Draws this button to the screen.
		 */
		public void drawButton(Minecraft p_146112_1_, int p_146112_2_, int p_146112_3_){
			if(this.visible){
				boolean flag = p_146112_2_ >= this.xPosition && p_146112_3_ >= this.yPosition && p_146112_2_ < this.xPosition + this.width && p_146112_3_ < this.yPosition + this.height;
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				p_146112_1_.getTextureManager().bindTexture(bookGuiTextures);
				int k = 0;
				int l = 192;

				if(flag){
					k += 23;
				}

				if(!this.field_146151_o){
					l += 13;
				}

				this.drawTexturedModalRect(this.xPosition, this.yPosition, k, l, 23, 13);
			}
		}
	}

}
