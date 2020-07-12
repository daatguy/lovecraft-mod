package daatguy.lovecraft.block;

import daatguy.lovecraft.book.DeskHandler;
import daatguy.lovecraft.client.sound.SoundEventHandler;
import daatguy.lovecraft.core.LovecraftMain;
import daatguy.lovecraft.item.SubItemsHandler;
import daatguy.lovecraft.tileentity.TileEntityCarving;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.client.resources.I18n;

public class BlockCarvedBlock extends BlockSimple implements ITileEntityProvider {

	public static final PropertyDirection FACING = BlockDirectional.FACING;

	public BlockCarvedBlock(Material material) {
		super(material);
	}

	private void setDefaultDirection(World worldIn, BlockPos pos,
			IBlockState state) {
		if (!worldIn.isRemote) {
			EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);
			boolean flag = worldIn.getBlockState(pos.north()).isFullBlock();
			boolean flag1 = worldIn.getBlockState(pos.south()).isFullBlock();

			if (enumfacing == EnumFacing.NORTH && flag && !flag1) {
				enumfacing = EnumFacing.SOUTH;
			} else if (enumfacing == EnumFacing.SOUTH && flag1 && !flag) {
				enumfacing = EnumFacing.NORTH;
			} else {
				boolean flag2 = worldIn.getBlockState(pos.west()).isFullBlock();
				boolean flag3 = worldIn.getBlockState(pos.east()).isFullBlock();

				if (enumfacing == EnumFacing.WEST && flag2 && !flag3) {
					enumfacing = EnumFacing.EAST;
				} else if (enumfacing == EnumFacing.EAST && flag3 && !flag2) {
					enumfacing = EnumFacing.WEST;
				}
			}

			worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing),
					2);
		}
	}

	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		super.onBlockAdded(worldIn, pos, state);
		this.setDefaultDirection(worldIn, pos, state);
	}

	public IBlockState getStateForPlacement(World worldIn, BlockPos pos,
			EnumFacing facing, float hitX, float hitY, float hitZ, int meta,
			EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING,
				EnumFacing.getDirectionFromEntityLiving(pos, placer));
	}

	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state,
			EntityLivingBase placer, ItemStack stack) {
		worldIn.setBlockState(
				pos,
				state.withProperty(FACING,
						EnumFacing.getDirectionFromEntityLiving(pos, placer)),
				2);
		// Carving placed set carving data from stack data
		TileEntityCarving tile = (TileEntityCarving) worldIn.getTileEntity(pos);
		if (tile == null) {
			return;
		}
		try {
			NBTTagCompound nbt = ((NBTTagCompound) stack.getTagCompound()
					.getTag("BlockEntityTag"));
			tile.carving = nbt.getString("Carving");
			tile.language = nbt.getInteger("Language");
		} catch (Exception e) {}
	}

	@Override
	public boolean isNormalCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isTopSolid(IBlockState state) { // Is this just ignored!?
		return !state.getValue(FACING).equals(EnumFacing.UP);
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn,
			IBlockState state, BlockPos pos, EnumFacing face) {
		return face == state.getValue(FACING) ? BlockFaceShape.BOWL
				: BlockFaceShape.SOLID;
	}

	@Override
	public boolean isBlockNormalCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING,
				EnumFacing.getFront(meta));
	}

	public int getMetaFromState(IBlockState state) {
		int i = 0;
		i = i | ((EnumFacing) state.getValue(FACING)).getIndex();
		return i;
	}

	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(FACING,
				rot.rotate((EnumFacing) state.getValue(FACING)));
	}

	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return state.withRotation(mirrorIn.toRotation((EnumFacing) state
				.getValue(FACING)));
	}

	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING });
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos,
			IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing side, float hitX, float hitY, float hitZ) {
		if (side != world.getBlockState(pos).getValue(FACING))
			return false;
		ItemStack heldItem = player.getHeldItem(hand);
		TileEntityCarving tile = (TileEntityCarving) world.getTileEntity(pos);
		if (tile.language == SubItemsHandler.COMMON) {
			if (world.isRemote) {
				showCarving(player, tile.carving);
			}
			return true;
		} else if (tile.carving != "carving.null") {
			if (heldItem.getItem() == LovecraftMain.itemRubbingKit) {
				heldItem.shrink(1);
				world.playSound(pos.getX() + 0.5f, pos.getY() + 0.5f,
						pos.getZ() + 0.5f, SoundEventHandler.RUBBING,
						SoundCategory.BLOCKS, 0.3f,
						(float) (Math.random() * 0.1D + 0.95D), false);
				ItemStack stack = new ItemStack(LovecraftMain.itemRubbing);
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setString("Carving", tile.carving);
				nbt.setInteger("Language", tile.language);
				nbt.setBoolean("Translated", false);
				stack.setTagCompound(nbt);
				if (!player.inventory.addItemStackToInventory(stack)) {
					player.dropItem(stack, false);
				}
				return true;
			} else if (tile.language != SubItemsHandler.COMMON
					&& heldItem.getItem() == LovecraftMain.itemBook
					&& heldItem.hasTagCompound()
					&& heldItem.getTagCompound().hasKey("Book")
					&& tile.language == DeskHandler.getIDFromDict(heldItem
							.getTagCompound().getString("Book"))) {
				if (world.isRemote) {
					showCarving(player, tile.carving);
				}
				return true;
			} else if (tile.language != SubItemsHandler.COMMON) {
				if (world.isRemote) {
					showCarvingFail(player, tile.language);
				}
				return true;
			}
		}
		return false;
	}

	public void showCarvingFail(EntityPlayer player, int language) {
		player.sendMessage(new TextComponentString(TextFormatting.RED
				+ I18n.format("carving.untranslated")
						.replace(
								"*C",
								I18n.format(SubItemsHandler
										.getLanguageFromID(language)))));
	}

	public void showCarving(EntityPlayer player, String carving) {
		player.sendMessage(new TextComponentString(TextFormatting.WHITE
				+ I18n.format(carving)));
	}

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		ItemStack itemstack = super.getItem(worldIn, pos, state);
		TileEntityCarving carving = (TileEntityCarving) worldIn
				.getTileEntity(pos);
		NBTTagCompound compound = carving.saveToNbt(new NBTTagCompound());
		if (!compound.hasNoTags()) {
			itemstack.setTagInfo("BlockEntityTag", compound);
		}
		return itemstack;
	}

	@Override
	public void onBlockHarvested(World world, BlockPos pos, IBlockState state,
			EntityPlayer player) {
		TileEntity tileentity = world.getTileEntity(pos);
		if (tileentity instanceof TileEntityCarving) {
			((TileEntityCarving)tileentity).destroyedByCreativePlayer = player.capabilities.isCreativeMode;

		}
        this.dropBlockAsItem(world, pos, state, 0);
		super.onBlockHarvested(world, pos, state, player);
	}

	@Override
	public void dropBlockAsItemWithChance(World world, BlockPos pos,
			IBlockState state, float chance, int fortune) {
		if (!world.isRemote && !world.restoringBlockSnapshots) {
			TileEntity tileentity = world.getTileEntity(pos);
			if (tileentity instanceof TileEntityCarving) {
				TileEntityCarving carving = (TileEntityCarving) tileentity;
				NBTTagCompound itemtags = new NBTTagCompound();
				itemtags.setTag("BlockEntityTag",
						carving.saveToNbt(new NBTTagCompound()));
				ItemStack itemstack = new ItemStack(Item.getItemFromBlock(this));
				itemstack.setTagCompound(itemtags);
				if (!carving.destroyedByCreativePlayer) {
					spawnAsEntity(world, pos, itemstack);
				}
			}
		}
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityCarving();
	}

}
