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

import static de.schneider_oliver.nafis.utils.Strings.TOOL_LEVEL_MODULE;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import de.schneider_oliver.nafis.config.ConfigModules;
import de.schneider_oliver.nafis.config.ToolLevelingModule;
import de.schneider_oliver.nafis.item.item.NafisTool;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

@Mixin(LivingEntity.class)
public abstract class EntityMixin extends Entity{

	
	public EntityMixin(EntityType<?> type, World world) {super(type, world);}

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
}
