package de.schneider_oliver.nafis.block.block;

import de.schneider_oliver.nafis.block.entity.ImprovedForgeBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class ImprovedForgeBlock extends AbstractForgeBlock{

	public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
	public static final VoxelShape OUTLINE_SHAPE_WEST = VoxelShapes.union(createCuboidShape(4, 0, 0, 12, 2, 8), createCuboidShape(5, 0, 4, 11, 4, 12), createCuboidShape(4, 9, 0, 12, 11, 16), createCuboidShape(6.5, 4, 5.25, 9.5, 9, 10.75));
	public static final VoxelShape OUTLINE_SHAPE_EAST = VoxelShapes.union(createCuboidShape(4, 0, 8, 12, 2, 16), createCuboidShape(5, 0, 4, 11, 4, 12), createCuboidShape(4, 9, 0, 12, 11, 16), createCuboidShape(6.5, 4, 5.25, 9.5, 9, 10.75));
	public static final VoxelShape OUTLINE_SHAPE_NORTH = VoxelShapes.union(createCuboidShape(8, 0, 4, 16, 2, 12), createCuboidShape(4, 0, 5, 12, 4, 11), createCuboidShape(0, 9, 4, 16, 11, 12), createCuboidShape(5.25, 4, 6.5, 10.75, 9, 9.5));
	public static final VoxelShape OUTLINE_SHAPE_SOUTH = VoxelShapes.union(createCuboidShape(0, 0, 4, 8, 2, 12), createCuboidShape(4, 0, 5, 12, 4, 11), createCuboidShape(0, 9, 4, 16, 11, 12), createCuboidShape(5.25, 4, 6.5, 10.75, 9, 9.5));
	
	
	public ImprovedForgeBlock(Settings settings) {
		super(settings);
		setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.WEST));
	}

	@Override
	public BlockEntity createBlockEntity(BlockView world) {
		return new ImprovedForgeBlockEntity();
	}

	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
	}

	public BlockState rotate(BlockState state, BlockRotation rotation) {
		return (BlockState)state.with(FACING, rotation.rotate((Direction)state.get(FACING)));
	}

	public BlockState mirror(BlockState state, BlockMirror mirror) {
		return state.rotate(mirror.getRotation((Direction)state.get(FACING)));
	}

	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		Direction dir = state.get(FACING);
		return dir == Direction.EAST ? OUTLINE_SHAPE_EAST : dir == Direction.NORTH ? OUTLINE_SHAPE_NORTH : dir == Direction.SOUTH ? OUTLINE_SHAPE_SOUTH : OUTLINE_SHAPE_WEST;
	}
	
}
