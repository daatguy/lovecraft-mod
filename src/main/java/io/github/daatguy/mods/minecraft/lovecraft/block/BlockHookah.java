package daatguy.lovecraft.block;

import java.util.List;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import daatguy.lovecraft.core.LovecraftMain;
import daatguy.lovecraft.tileentity.TileEntityHookah;

public class BlockHookah extends BlockSimple implements ITileEntityProvider {

	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final AxisAlignedBB boundingBox = new AxisAlignedBB(0.3125f,
			0f, 0.3125f, 0.6875f, 1.0f, 0.6875f);

	public BlockHookah(Material material) {
		super(material);
		this.setSoundType(SoundType.GLASS);
	}
	
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	private void spawnSmoke(World world, BlockPos pos) {
		for (int i = 0; i < 16; i++) {
			double x = (double) pos.getX() + 0.4D + 0.2D * Math.random();
			double y = (double) pos.getY() + 0.4D + 0.2D * Math.random();
			double z = (double) pos.getZ() + 0.4D + 0.2D * Math.random();
			world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x, y, z,
					0.15D - 0.3D * Math.random(), 0.15D - 0.3D * Math.random(),
					0.15D - 0.3D * Math.random());
		}
		for (int i = 0; i < 8; i++) {
			double x = (double) pos.getX() + 0.4D + 0.2D * Math.random();
			double y = (double) pos.getY() + 0.4D + 0.2D * Math.random();
			double z = (double) pos.getZ() + 0.4D + 0.2D * Math.random();
			world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, x, y, z,
					0.05D - 0.1D * Math.random(), 0.05D - 0.1D * Math.random(),
					0.05D - 0.1D * Math.random());
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos,
			IBlockState state, EntityPlayer playerIn, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		TileEntityHookah tile = ((TileEntityHookah) worldIn.getTileEntity(pos));
		if (playerIn.getHeldItem(hand).getItem() == Items.COAL
				&& !tile.isSmoking() && tile.lockout == 0) {
			playerIn.getHeldItem(hand).shrink(1);
			tile.smokeCount = tile.smokeMax;
			tile.lockout = 5;
			tile.markDirty();
		} else if (playerIn.getHeldItem(hand).getItem() == LovecraftMain.itemDriedFlower
				&& tile.isSmoking() && tile.lockout == 0) {
			System.out.println(worldIn.isRemote);

			playerIn.getHeldItem(hand).setCount(
					playerIn.getHeldItem(hand).getCount() - 1);
			playerIn.addPotionEffect(new PotionEffect(
					LovecraftMain.potionDrugged, 20 * 30));
			playerIn.addPotionEffect(new PotionEffect(Potion.getPotionById(9),
					20 * 31));
			spawnSmoke(worldIn, pos);
			spawnSmoke(worldIn, new BlockPos(playerIn.getPosition().getX(),
					playerIn.getPosition().getY() + playerIn.getEyeHeight(),
					playerIn.getPosition().getZ()));
			tile.lockout = 5;
			worldIn.playSound(pos.getX(), pos.getY(), pos.getZ(),
					SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS,
					1.0F, 1.7F, false);
			//if(worldIn.isRemote) {
			//	Minecraft.getMinecraft().getSoundHandler().playSound(new MovingSoundDrugged((EntityPlayerSP) playerIn));
			//}
			//Minecraft.getMinecraft().getSoundHandler().playSound(new MovingSoundDrugged(playerIn));
			
			/*worldIn.playSound(playerIn, pos,
					SoundEventHandler.DRUGGED, SoundCategory.AMBIENT,
					1.0F, 1.0F);*/
			
		}
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand,
				facing, hitX, hitY, hitZ);
	}

	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		this.setDefaultFacing(worldIn, pos, state);
	}

	private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state) {
		if (!worldIn.isRemote) {
			IBlockState iblockstate = worldIn.getBlockState(pos.north());
			IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
			IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
			IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
			EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);

			if (enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock()
					&& !iblockstate1.isFullBlock()) {
				enumfacing = EnumFacing.SOUTH;
			} else if (enumfacing == EnumFacing.SOUTH
					&& iblockstate1.isFullBlock() && !iblockstate.isFullBlock()) {
				enumfacing = EnumFacing.NORTH;
			} else if (enumfacing == EnumFacing.WEST
					&& iblockstate2.isFullBlock()
					&& !iblockstate3.isFullBlock()) {
				enumfacing = EnumFacing.EAST;
			} else if (enumfacing == EnumFacing.EAST
					&& iblockstate3.isFullBlock()
					&& !iblockstate2.isFullBlock()) {
				enumfacing = EnumFacing.WEST;
			}

			worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing),
					2);
		}
	}

	public IBlockState getStateForPlacement(World worldIn, BlockPos pos,
			EnumFacing facing, float hitX, float hitY, float hitZ, int meta,
			EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING,
				placer.getHorizontalFacing().getOpposite());
	}

	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state,
			EntityLivingBase placer, ItemStack stack) {
		worldIn.setBlockState(pos, state.withProperty(FACING, placer
				.getHorizontalFacing().getOpposite()), 2);
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source,
			BlockPos pos) {
		return boundingBox;
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn,
			BlockPos pos, AxisAlignedBB entityBox,
			List<AxisAlignedBB> collidingBoxes, Entity entityIn,
			boolean isActualState) {
		super.addCollisionBoxToList(pos, entityBox, collidingBoxes, boundingBox);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityHookah();
	}

	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumfacing = EnumFacing.getFront(meta);

		if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
			enumfacing = EnumFacing.NORTH;
		}

		return this.getDefaultState().withProperty(FACING, enumfacing);
	}

	public int getMetaFromState(IBlockState state) {
		return ((EnumFacing) state.getValue(FACING)).getIndex();
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

}
