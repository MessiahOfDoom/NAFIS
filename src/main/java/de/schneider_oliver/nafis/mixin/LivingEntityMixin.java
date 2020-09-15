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
import static de.schneider_oliver.nafis.utils.Strings.TOOL_LEVEL_MODULE;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import de.schneider_oliver.nafis.config.ConfigModules;
import de.schneider_oliver.nafis.config.ToolLevelingModule;
import de.schneider_oliver.nafis.item.item.NafisTool;
import de.schneider_oliver.nafis.modifiers.ModifierRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity{


	public LivingEntityMixin(EntityType<?> type, World world) {super(type, world);}

	@Inject(at = @At("HEAD"), method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z", cancellable = true)
	public void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> info) {
		if(source != null && source.getAttacker() != null && source.getAttacker() instanceof PlayerEntity && !isInvulnerableTo(source)) {
			PlayerEntity player = (PlayerEntity)source.getAttacker();
			if(player.getMainHandStack().getItem() instanceof NafisTool) {
				ItemStack toolStack = player.getMainHandStack();
				if(!NafisTool.isBroken(toolStack) && ConfigModules.<ToolLevelingModule>getCachedModuleByName(TOOL_LEVEL_MODULE, false).levelingEnabled.get()) {
					((NafisTool)toolStack.getItem()).incrementDamageDealt(toolStack, amount);
				}
			}
		}
	}

	@Shadow
	protected abstract Identifier getLootTable();

	@Shadow
	protected abstract LootContext.Builder getLootContextBuilder(boolean causedByPlayer, DamageSource source);

	@Inject(at = @At("HEAD"), method = "dropLoot(Lnet/minecraft/entity/damage/DamageSource;Z)V", cancellable = true)
	public void dropLoot(DamageSource source, boolean causedByPlayer, CallbackInfo info) {
		if(causedByPlayer && source != null && source instanceof EntityDamageSource) {
			EntityDamageSource actualSource = ((EntityDamageSource)source);
			if(!actualSource.isThorns() && actualSource.getAttacker() != null && actualSource.getAttacker() instanceof PlayerEntity) {
				PlayerEntity attacker = (PlayerEntity)actualSource.getAttacker();
				ItemStack stack = attacker.getMainHandStack();
				if(stack.getItem() instanceof NafisTool) {
					if(ModifierRegistry.getModifier(ident("hellish")).getLevel(stack) > 0) {
						Identifier identifier = this.getLootTable();
						LootTable lootTable = this.world.getServer().getLootManager().getTable(identifier);
						LootContext.Builder builder = this.getLootContextBuilder(causedByPlayer, source);
						lootTable.generateLoot(builder.build(LootContextTypes.ENTITY), this::dropStackHellish);
						info.cancel();
					}
				}
			}
		}
	}

	public ItemEntity dropStackHellish(ItemStack stack) {
		
		if(this.world != null && !this.world.isClient) {
			Inventory inventory = new SimpleInventory(3);
			inventory.setStack(0, stack);
			Recipe<?> recipe = this.world.getRecipeManager().getFirstMatch(RecipeType.SMELTING, inventory, world).orElse(null);
			if(recipe != null) {
				ItemStack stack2 = recipe.getOutput().copy();
				stack2.setCount(stack.getCount());
				return this.dropStack(stack2, 0.0F);
			}
		}
		return this.dropStack(stack, 0.0F);
	}
}
