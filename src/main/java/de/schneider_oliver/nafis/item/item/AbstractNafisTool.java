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
package de.schneider_oliver.nafis.item.item;

import static de.schneider_oliver.nafis.utils.NBTKeys.*;
import static de.schneider_oliver.nafis.utils.Strings.*;

import java.util.List;

import de.schneider_oliver.doomedfabric.utils.TextColorUtils;
import de.schneider_oliver.nafis.config.ConfigModules;
import de.schneider_oliver.nafis.config.ToolLevelingModule;
import de.schneider_oliver.nafis.modifiers.modifiers.Modifier;
import net.fabricmc.fabric.api.tool.attribute.v1.DynamicAttributeTool;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tag.Tag;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class AbstractNafisTool extends Item implements DynamicAttributeTool, NafisTool{

	public AbstractNafisTool(Settings settings) {
		super(settings.maxCount(1));
	}

	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		super.appendTooltip(stack, world, tooltip, context);
		
		for(Modifier m: getModifiers(stack)) {
			tooltip.add(m.getTooltip(stack));
		}
		tooltip.add(new LiteralText(""));
		
		if(context.isAdvanced() && ConfigModules.<ToolLevelingModule>getCachedModuleByName(TOOL_LEVEL_MODULE, false).levelingEnabled.get()) {
			CompoundTag tag = stack.getOrCreateSubTag(KEY_LEVELING);
			CompoundTag tag2 = stack.getOrCreateSubTag(KEY_OTHER);
			
			int i2 = tag.getInt(SUBKEY_REPAIREDLEVEL);
			int i0 = ConfigModules.<ToolLevelingModule>getCachedModuleByName(TOOL_LEVEL_MODULE, false).repairsNeededForLevel(i2 + 1);
			int i1 = tag2.getInt(SUBKEY_REPAIREDCOUNT) % i0;
			tooltip.add(new TranslatableText(TRANSLATION_KEY_DURABILITY_LEVEL, i2).styled(TextColorUtils.of(0xBBBBFF)));
			tooltip.add(new TranslatableText(TRANSLATION_KEY_DURABILITY_XP, i1, i0).styled(TextColorUtils.of(0xBBBBFF)));
			if(canLevelMining(stack)) {
				int f2 = tag.getInt(SUBKEY_SPEEDLEVEL);
				float f0 = ConfigModules.<ToolLevelingModule>getCachedModuleByName(TOOL_LEVEL_MODULE, false).hardnessNeededForLevel(f2 + 1);
				float f1 = tag2.getFloat(SUBKEY_HARDNESSBROKEN) % f0;
				tooltip.add(new TranslatableText(TRANSLATION_KEY_SPEED_LEVEL, f2).styled(TextColorUtils.of(0xBBBBFF)));
				tooltip.add(new TranslatableText(TRANSLATION_KEY_SPEED_XP, String.format("%.2f", f1), f0).styled(TextColorUtils.of(0xBBBBFF)));
			}
			if(canLevelAttack(stack)) {
				int g2 = tag.getInt(SUBKEY_DAMAGELEVEL);
				float g0 = ConfigModules.<ToolLevelingModule>getCachedModuleByName(TOOL_LEVEL_MODULE, false).attackDamageNeededForLevel(g2 + 1);
				float g1 = tag2.getFloat(SUBKEY_DAMAGEDEALT) % g0;
				tooltip.add(new TranslatableText(TRANSLATION_KEY_ATTACK_LEVEL, g2).styled(TextColorUtils.of(0xBBBBFF)));
				tooltip.add(new TranslatableText(TRANSLATION_KEY_ATTACK_XP, String.format("%.2f", g1), g0).styled(TextColorUtils.of(0xBBBBFF)));
				
				int h2 = tag.getInt(SUBKEY_ATTACKSPEEDLEVEL);
				float h0 = ConfigModules.<ToolLevelingModule>getCachedModuleByName(TOOL_LEVEL_MODULE, false).attacksNeededForLevel(h2 + 1);
				float h1 = tag2.getFloat(SUBKEY_ATTACKSDONE) % h0;
				tooltip.add(new TranslatableText(TRANSLATION_KEY_ATTACKSPEED_LEVEL, h2).styled(TextColorUtils.of(0xBBBBFF)));
				tooltip.add(new TranslatableText(TRANSLATION_KEY_ATTACKSPEED_XP, String.format("%.2f", h1), h0).styled(TextColorUtils.of(0xBBBBFF)));
			}
		}
		if(!context.isAdvanced()) {
			tooltip.add(new TranslatableText(NafisTool.isBroken(stack) ? "Broken" : "").styled(TextColorUtils.of(0xBB0000)));

			int i0 = stack.getMaxDamage();
			int i1 = stack.getMaxDamage() - stack.getDamage();
			tooltip.add(new TranslatableText(TRANSLATION_KEY_DURABILITY, i1, i0));
		}
		
	}

	@Override
	public float postProcessMiningSpeed(Tag<Item> tag, BlockState state, ItemStack stack, LivingEntity user, float currentSpeed, boolean isEffective) {
		return NafisTool.super.postProcessMiningSpeed(tag, state, stack, user, currentSpeed, isEffective);
	}

	@Override
	public float getMiningSpeedMultiplier(Tag<Item> tag, BlockState state, ItemStack stack, LivingEntity user) {
		return NafisTool.super.getMiningSpeedMultiplier(tag, state, stack, user);
	}

	@Override
	public int getMiningLevel(Tag<Item> tag, BlockState state, ItemStack stack, LivingEntity user) {
		return NafisTool.super.getMiningLevel(tag, state, stack, user);
	}
	
	@Override
	public Text getName(ItemStack stack) {
		return NafisTool.super.getName(stack);
	}
	
	@Override
	public boolean isDamageable() {
		return NafisTool.super.isDamageable();
	}
	
	@Override
	public int getEnchantability() {
		return NafisTool.super.getEnchantability();
	}
	
	@Override
	public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		return NafisTool.super.postHit(stack, target, attacker);
	}
	
	@Override
	public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
		return NafisTool.super.postMine(stack, world, state, pos, miner);
	}
	
	
}
