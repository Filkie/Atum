package rebelkeithy.mods.atum.blocks;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import rebelkeithy.mods.atum.Atum;
import rebelkeithy.mods.atum.AtumTeleporter;
import rebelkeithy.mods.atum.TickHandler;
import rebelkeithy.mods.particleregistry.ParticleRegistry;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockAtumPortal extends BlockBreakable
{
    public BlockAtumPortal(int par1)
    {
        super(par1, "Atum:portal", Material.portal, false);
        setTickRandomly(true);
        setHardness(-1f);
    }

    /**
     * Ticks the block if it's been scheduled
     */
	@Override
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        super.updateTick(par1World, par2, par3, par4, par5Random);

        if (par1World.provider.isSurfaceWorld() && par5Random.nextInt(2000) < par1World.difficultySetting)
        {
        	/*
            int l;

            for (l = par3; !par1World.doesBlockHaveSolidTopSurface(par2, l, par4) && l > 0; --l)
            {
                ;
            }

            if (l > 0 && !par1World.isBlockNormalCube(par2, l + 1, par4))
            {
                Entity entity = ItemMonsterPlacer.spawnCreature(par1World, 57, (double)par2 + 0.5D, (double)l + 1.1D, (double)par4 + 0.5D);

                if (entity != null)
                {
                    entity.timeUntilPortal = entity.getPortalCooldown();
                }
            }
            */
        }
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
	@Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        return null;
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
	@Override
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        float f;
        float f1;
        /*
        if (par1IBlockAccess.getBlockId(par2 - 1, par3, par4) != this.blockID && par1IBlockAccess.getBlockId(par2 + 1, par3, par4) != this.blockID)
        {
            f = 0.125F;
            f1 = 0.5F;
            this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f1, 0.5F + f, 1.0F, 0.5F + f1);
        }
        else
        {
            f = 0.5F;
            f1 = 0.125F;
            this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f1, 0.5F + f, 1.0F, 0.5F + f1);
        }*/
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
	@Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
	@Override
    public boolean renderAsNormalBlock()
    {
        return true;
    }

    /**
     * Checks to see if this location is valid to create a portal and will return True if it does. Args: world, x, y, z
     */
    public boolean tryToCreatePortal(World par1World, int par2, int par3, int par4)
    {

        	int id0 = par1World.getBlockId(par2, par3-1, par4);
            int id1 = par1World.getBlockId(par2 - 2, par3 + 0, par4);
            int id2 = par1World.getBlockId(par2 - 2, par3 + 1, par4);
            int id3 = par1World.getBlockId(par2, par3 + 0, par4 - 2);
            int id4 = par1World.getBlockId(par2, par3 + 1, par4 - 2);
            int id5 = par1World.getBlockId(par2, par3 + 0, par4 + 2);
            int id6 = par1World.getBlockId(par2, par3 + 1, par4 + 2);
            int id7 = par1World.getBlockId(par2 + 2, par3 + 0, par4);
            int id8 = par1World.getBlockId(par2 + 2, par3 + 1, par4);
            int id9 = par1World.getBlockId(par2 - 1, par3 + 2, par4);
            int id10 = par1World.getBlockId(par2, par3 + 2, par4 - 1);
            int id11 = par1World.getBlockId(par2, par3 + 2, par4 + 1);
            int id12 = par1World.getBlockId(par2 + 1, par3 + 2, par4);
            
            if(id1 == id2 && id2 == id3 && id3 == id4 && id4 == id5 && id6 == id7 && id7 == id8 && id8 == id9 && id9 == id10 && id10 == id11 && id11 == id12 && id12 == Block.sandStone.blockID)
            {
                par1World.setBlock(par2, par3, par4, Atum.portal.blockID, 0, 2);
                par1World.setBlock(par2, par3 + 1, par4, Atum.portal.blockID, 0, 2);
                par1World.setBlock(par2, par3 + 2, par4, Atum.portal.blockID, 0, 2);
                return true;
            }

            return false;
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
	@Override
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        byte b0 = 0;
        byte b1 = 1;

        if (par1World.getBlockId(par2 - 1, par3, par4) == this.blockID || par1World.getBlockId(par2 + 1, par3, par4) == this.blockID)
        {
            b0 = 1;
            b1 = 0;
        }

        int i1;

        for (i1 = par3; par1World.getBlockId(par2, i1 - 1, par4) == this.blockID; --i1)
        {
            ;
        }

        if (par1World.getBlockId(par2, i1 - 1, par4) != Block.sandStone.blockID)
        {
            par1World.setBlockToAir(par2, par3, par4);
        }
        else
        {
            int j1;

            for (j1 = 1; j1 < 4 && par1World.getBlockId(par2, i1 + j1, par4) == this.blockID; ++j1)
            {
                ;
            }

            if (j1 == 3 && par1World.getBlockId(par2, i1 + j1, par4) == Block.sandStone.blockID)
            {
                boolean flag = par1World.getBlockId(par2 - 1, par3, par4) == this.blockID || par1World.getBlockId(par2 + 1, par3, par4) == this.blockID;
                boolean flag1 = par1World.getBlockId(par2, par3, par4 - 1) == this.blockID || par1World.getBlockId(par2, par3, par4 + 1) == this.blockID;

                if (flag && flag1)
                {
                    par1World.setBlockToAir(par2, par3, par4);
                }
                else
                {
                    if ((par1World.getBlockId(par2 + b0, par3, par4 + b1) != Block.sandStone.blockID || par1World.getBlockId(par2 - b0, par3, par4 - b1) != this.blockID) && (par1World.getBlockId(par2 - b0, par3, par4 - b1) != Block.sandStone.blockID || par1World.getBlockId(par2 + b0, par3, par4 + b1) != this.blockID))
                    {
                        par1World.setBlockToAir(par2, par3, par4);
                    }
                }
            }
            else
            {
                par1World.setBlockToAir(par2, par3, par4);
            }
        }
    }

    @SideOnly(Side.CLIENT)

    /**
     * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
     * coordinates.  Args: blockAccess, x, y, z, side
     */
	@Override
    public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        if (par1IBlockAccess.getBlockId(par2, par3, par4) == this.blockID)
        {
            return false;
        }
        else
        {
            boolean flag = par1IBlockAccess.getBlockId(par2 - 1, par3, par4) == this.blockID && par1IBlockAccess.getBlockId(par2 - 2, par3, par4) != this.blockID;
            boolean flag1 = par1IBlockAccess.getBlockId(par2 + 1, par3, par4) == this.blockID && par1IBlockAccess.getBlockId(par2 + 2, par3, par4) != this.blockID;
            boolean flag2 = par1IBlockAccess.getBlockId(par2, par3, par4 - 1) == this.blockID && par1IBlockAccess.getBlockId(par2, par3, par4 - 2) != this.blockID;
            boolean flag3 = par1IBlockAccess.getBlockId(par2, par3, par4 + 1) == this.blockID && par1IBlockAccess.getBlockId(par2, par3, par4 + 2) != this.blockID;
            boolean flag4 = flag || flag1;
            boolean flag5 = flag2 || flag3;
            return flag4 && par5 == 4 ? true : (flag4 && par5 == 5 ? true : (flag5 && par5 == 2 ? true : flag5 && par5 == 3));
        }
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
	@Override
    public int quantityDropped(Random par1Random)
    {
        return 0;
    }

    /**
     * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
     */
	@Override
    public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
    {
        if (par5Entity.ridingEntity == null && par5Entity.riddenByEntity == null)
        {
        	if(par5Entity instanceof EntityPlayerMP)
        	{
        		EntityPlayer player = (EntityPlayerMP) par5Entity;
	            //par5Entity.setInPortal();
	        	//if(par5Entity.getPortalCooldown() == 0)
	        	//if(Math.random() > 0.999)
	        	
	        	if(par5Entity.timeUntilPortal == 0 && par5Entity instanceof EntityPlayerMP)
	        	{
	        		par5Entity.timeUntilPortal = 100;
	        		MinecraftServer minecraftserver = MinecraftServer.getServer();
	        		int dimID = par5Entity.dimension;
	                WorldServer worldserver = minecraftserver.worldServerForDimension(0);
	                WorldServer worldserver1 = minecraftserver.worldServerForDimension(Atum.dimensionID);
	                if(dimID == Atum.dimensionID)
	                {
	                	minecraftserver.getConfigurationManager().transferPlayerToDimension((EntityPlayerMP) par5Entity, 0, new AtumTeleporter(worldserver));
	                	if(par1World.isRemote)
	                		Minecraft.getMinecraft().gameSettings.renderDistance = TickHandler.defaultFog;
	                    //par5Entity.travelToDimension(0);
	                } else {
	                	minecraftserver.getConfigurationManager().transferPlayerToDimension((EntityPlayerMP) par5Entity, Atum.dimensionID, new AtumTeleporter(worldserver1));
	                    //par5Entity.travelToDimension(Atum.dimensionID);
	                }
	                //par5Entity.travelToDimension(Atum.dimensionID);
	                try
					{
	                	EntityPlayerMP p = (EntityPlayerMP)player;
	                	
	                	ObfuscationReflectionHelper.setPrivateValue(EntityPlayerMP.class, p, -1, "lastExperience", "cp", "field_71144_ck");
	                	ObfuscationReflectionHelper.setPrivateValue(EntityPlayerMP.class, p, -1, "lastHealth", "cm", "field_71149_ch");
	                	ObfuscationReflectionHelper.setPrivateValue(EntityPlayerMP.class, p, -1, "lastFoodLevel", "cn", "field_71146_ci");
	                	// Access lastExperience
	                	/*
		                Field lastExperience = EntityPlayerMP.class.getField("field_71144_ck");
		                lastExperience.setAccessible(true);
						lastExperience.set(p, -1);
		                lastExperience.setAccessible(false);
		                

		                // Access lastHealth
	                	/*
		                System.out.println(Arrays.toString(p.getClass().getDeclaredFields()));
		                Field lastHealth = EntityPlayerMP.class.getField("lastHealth");
		                
		                lastHealth.setAccessible(true);
		                lastHealth.set(p, -1);
		                lastHealth.setAccessible(false);
						
	                	p.lastHealth = -1;

		                Field lastFoodLevel = p.getClass().getField("lastFoodLevel");
		                lastFoodLevel.setAccessible(true);
		                lastFoodLevel.set(p, -1);
						lastFoodLevel.setAccessible(false);
						*/
					}
	                catch (Exception e)
					{
						e.printStackTrace();
					}
	        	}

        	}
        }
    }

    @SideOnly(Side.CLIENT)

    /**
     * Returns which pass should this block be rendered on. 0 for solids and 1 for alpha
     */
	@Override
    public int getRenderBlockPass()
    {
        return 1;
    }

    @SideOnly(Side.CLIENT)

    /**
     * A randomly called display update to be able to add particles or other items for display
     */
	@Override
    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (par5Random.nextInt(100) == 0)
        {
            par1World.playSound((double)par2 + 0.5D, (double)par3 + 0.5D, (double)par4 + 0.5D, "portal.portal", 0.5F, par5Random.nextFloat() * 0.4F + 0.8F, false);
        }

        for (int l = 0; l < 8; ++l)
        {
            double d0 = (double)((float)par2 + par5Random.nextFloat());
            double d1 = (double)((float)par3 + par5Random.nextFloat());
            double d2 = (double)((float)par4 + par5Random.nextFloat());
            double d3 = 0.0D;
            double d4 = 0.0D;
            double d5 = 0.0D;
            int i1 = par5Random.nextInt(2) * 2 - 1;
            d3 = ((double)par5Random.nextFloat() - 0.5D) * 0.5D;
            d4 = ((double)par5Random.nextFloat() - 0.5D) * 0.5D;
            d5 = ((double)par5Random.nextFloat() - 0.5D) * 0.5D;

            if (par5Random.nextDouble() > 0.5D)
            {
                d0 = (double)par2 + 0.5D + 0.25D * (double)i1;
                d3 = (double)(par5Random.nextFloat() * 2.0F * (float)i1);
            }
            else
            {
                d2 = (double)par4 + 0.5D + 0.25D * (double)i1;
                d5 = (double)(par5Random.nextFloat() * 2.0F * (float)i1);
            }

            ParticleRegistry.spawnParticle("sandportal", par1World, d0, d1, d2, d3, d4, d5);
        }
    }

    @SideOnly(Side.CLIENT)

    /**
     * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
     */
	@Override
    public int idPicked(World par1World, int par2, int par3, int par4)
    {
        return 0;
    }
}
