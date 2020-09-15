/*******************************************************************************
 * Copyright (c) 2020 Oliver Schneider
 *
 * This program and the accompanying materials
 * are made available under the terms of the GNU General Public License version 3 (GPLv3)
 * which accompanies this distribution, and is available at
 * https://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * SPDX-License-Identifier: GPL-3.0-only
 *******************************************************************************/
package de.schneider_oliver.nafis.mixin;

import static de.schneider_oliver.nafis.utils.IdentUtils.ident;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import de.schneider_oliver.nafis.item.item.NafisTool;
import de.schneider_oliver.nafis.modifiers.ModifierRegistry;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

@Mixin(Block.class)
public abstract class BlockMixin extends AbstractBlock{

	public BlockMixin(Settings settings) { super(settings); }

	@Inject(at = @At("HEAD"), method = "getDroppedStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)Ljava/util/List;", cancellable = true)
	private static void getDroppedStacks(BlockState state, ServerWorld world, BlockPos pos, BlockEntity blockEntity, Entity entity, ItemStack stack, CallbackInfoReturnable<List<ItemStack>> info) {
		if(blockEntity == null && !stack.isEmpty() && stack.getItem() instanceof NafisTool) {
			if(ModifierRegistry.getModifier(ident("hellish")).getLevel(stack) > 0) {
				LootContext.Builder builder = (new LootContext.Builder(world)).random(world.random).parameter(LootContextParameters.ORIGIN, Vec3d.ofCenter(pos)).parameter(LootContextParameters.TOOL, stack).optionalParameter(LootContextParameters.THIS_ENTITY, entity).optionalParameter(LootContextParameters.BLOCK_ENTITY, blockEntity);
				info.setReturnValue(((BlockMixin)(Object)state.getBlock()).getDroppedStacksHellish(state, builder));
			}
		}
	}

	public List<ItemStack> getDroppedStacksHellish(BlockState state, LootContext.Builder builder) {
		Identifier identifier = this.getLootTableId();
		if (identifier == LootTables.EMPTY) {
			return Collections.emptyList();
		} else {
			LootContext lootContext = builder.parameter(LootContextParameters.BLOCK_STATE, state).build(LootContextTypes.BLOCK);
			ServerWorld serverWorld = lootContext.getWorld();
			LootTable lootTable = serverWorld.getServer().getLootManager().getTable(identifier);
			List<ItemStack> stacks = lootTable.generateLoot(lootContext);
			List<ItemStack> out = new ArrayList<>();
			for(ItemStack stack: stacks) {
				Inventory inventory = new SimpleInventory(3);
				inventory.setStack(0, stack);
				Recipe<?> recipe = serverWorld.getRecipeManager().getFirstMatch(RecipeType.SMELTING, inventory, serverWorld).orElse(null);
				if(recipe != null) {
					ItemStack stack2 = recipe.getOutput().copy();
					stack2.setCount(stack.getCount());
					out.add(stack2);
				}else {
					out.add(stack);
				}
			}
			return out;
		}
	}

}
