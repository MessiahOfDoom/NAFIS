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

import static de.schneider_oliver.nafis.utils.IdentUtils.IDENT_UNKNOWN;
import static de.schneider_oliver.nafis.utils.IdentUtils.ident;
import static de.schneider_oliver.nafis.utils.NBTKeys.*;
import static de.schneider_oliver.nafis.utils.Strings.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.Consumer;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import de.schneider_oliver.nafis.config.ConfigModules;
import de.schneider_oliver.nafis.config.ToolLevelingModule;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.tool.attribute.v1.DynamicAttributeTool;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.tag.Tag;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class NafisTool extends Item implements DynamicAttributeTool{

	public static final UUID ATTACK_DAMAGE_UUID = UUID.fromString("16ea4d5a-63c3-4113-9656-49e8a91679f0");
	public static final UUID ATTACK_SPEED_UUID = UUID.fromString("8f009d38-e413-4941-90ef-3e501cb74704");
	
	public NafisTool(Settings settings) {
		super(settings.maxCount(1));
	}

	public static ItemStack setComponents(ItemStack stack, List<ItemStack> components) {
		CompoundTag tag = new CompoundTag(); // KEY_COMPONENTS
		CompoundTag tag2 = new CompoundTag(); // KEY_PROPERTIES
		CompoundTag tag3 = stack.getOrCreateSubTag(KEY_OTHER);
		for(int i = 0; i < components.size(); i++) {
			if(components.get(i).getItem() instanceof AbstractToolComponent) {
				tag.put(SUBKEY_COMPONENT + i, components.get(i).toTag(new CompoundTag()));
				((AbstractToolComponent)components.get(i).getItem()).writeStatsToTag(tag2);
				((AbstractToolComponent)components.get(i).getItem()).writeOtherToTag(tag3);
			}
		}
		stack.putSubTag(KEY_COMPONENTS, tag);
		stack.putSubTag(KEY_PROPERTIES, tag2);
		stack.putSubTag(KEY_OTHER, tag3);
		return stack;
	}

	public static ArrayList<ItemStack> getComponents(ItemStack stack){
		CompoundTag tag = stack.getOrCreateSubTag(KEY_COMPONENTS);
		ArrayList<ItemStack> out = new ArrayList<>();
		for(String s: tag.getKeys()) {
			if(s.startsWith(SUBKEY_COMPONENT)) {
				CompoundTag t = tag.getCompound(s);
				out.add(ItemStack.fromTag(t));
			}
		}
		if(out.isEmpty()) {
			out.add(new ItemStack(Items.POISONOUS_POTATO));
		}
		if(isBroken(stack)) {
			out.add(new ItemStack(Items.BARRIER));
		}
		return out;
	}

	public Identifier getToolType(ItemStack stack) {
		CompoundTag tag = stack.getOrCreateSubTag(KEY_TYPE);
		if(tag.contains(SUBKEY_TOOLHANDLER) && tag.contains(SUBKEY_TOOLTYPE)) {
			return ident(tag.getString(SUBKEY_TOOLHANDLER), tag.getString(SUBKEY_TOOLTYPE));
		}
		return IDENT_UNKNOWN;
	}

	public float getMiningSpeedMultiplier(ItemStack stack, BlockState state, LivingEntity breakingEntity) {
		float mult = 1;
		if(ConfigModules.<ToolLevelingModule>getCachedModuleByName(TOOL_LEVEL_MODULE, false).levelingEnabled.get()) {
			CompoundTag tag = stack.getOrCreateSubTag(KEY_LEVELING);
			int i = tag.getInt(SUBKEY_SPEEDLEVEL);
			mult += (i * ConfigModules.<ToolLevelingModule>getCachedModuleByName(TOOL_LEVEL_MODULE, false).speedBoost.get().floatValue());
		}
		return getMiningSpeedMultiplierBase(stack, state, breakingEntity) * mult;
	}

	public float getMiningSpeedMultiplierBase(ItemStack stack, BlockState state, LivingEntity breakingEntity) {
		CompoundTag tag = stack.getOrCreateSubTag(KEY_PROPERTIES);
		if(tag.contains(SUBKEY_SPEEDMULT)) {
			return tag.getFloat(SUBKEY_SPEEDMULT);
		}
		return 0;
	}

	public int getMiningLevel(ItemStack stack, BlockState state, LivingEntity breakingEntity) {
		if(isBroken(stack))return 0;
		return getMiningLevelBase(stack, state, breakingEntity);
	}
	
	public int getMiningLevelBase(ItemStack stack, BlockState state, LivingEntity breakingEntity) {
		CompoundTag tag = stack.getOrCreateSubTag(KEY_PROPERTIES);
		if(tag.contains(SUBKEY_MININGLEVEL)) {
			return tag.getInt(SUBKEY_MININGLEVEL);
		}
		return 0;
	}

	public int getMaxDamage(ItemStack stack) {
		float mult = 1;
		if(ConfigModules.<ToolLevelingModule>getCachedModuleByName(TOOL_LEVEL_MODULE, false).levelingEnabled.get()) {
			CompoundTag tag = stack.getOrCreateSubTag(KEY_LEVELING);
			int i = tag.getInt(SUBKEY_REPAIREDLEVEL);
			mult += (i * ConfigModules.<ToolLevelingModule>getCachedModuleByName(TOOL_LEVEL_MODULE, false).durabilityBoost.get().floatValue());
		}
		return (int)(getMaxDamageBase(stack) * mult);
	}
	
	public int getMaxDamageBase(ItemStack stack) {
		CompoundTag tag = stack.getOrCreateSubTag(KEY_PROPERTIES);
		if(tag.contains(SUBKEY_DURABILITY)) {
			return tag.getInt(SUBKEY_DURABILITY);
		}
		return 0;
	}
	
	public static float getAttackModifier(ItemStack stack) {
		if(isBroken(stack))return 0;
		float mult = 1;
		if(ConfigModules.<ToolLevelingModule>getCachedModuleByName(TOOL_LEVEL_MODULE, false).levelingEnabled.get()) {
			CompoundTag tag = stack.getOrCreateSubTag(KEY_LEVELING);
			int i = tag.getInt(SUBKEY_DAMAGELEVEL);
			mult += (i * ConfigModules.<ToolLevelingModule>getCachedModuleByName(TOOL_LEVEL_MODULE, false).attackDamageBoost.get().floatValue());
		}
		return getAttackModifierBase(stack) * mult;
	}

	public static float getAttackModifierBase(ItemStack stack) {
		CompoundTag tag = stack.getOrCreateSubTag(KEY_PROPERTIES);
		if(tag.contains(SUBKEY_ATTACKDAMAGE)) {
			return tag.getFloat(SUBKEY_ATTACKDAMAGE);
		}
		return 0;
	}
	
	public static float getAttackSpeedModifier(ItemStack stack) {
		float mult = 1;
		if(ConfigModules.<ToolLevelingModule>getCachedModuleByName(TOOL_LEVEL_MODULE, false).levelingEnabled.get()) {
			CompoundTag tag = stack.getOrCreateSubTag(KEY_LEVELING);
			int i = tag.getInt(SUBKEY_ATTACKSPEEDLEVEL);
			mult += (i * ConfigModules.<ToolLevelingModule>getCachedModuleByName(TOOL_LEVEL_MODULE, false).attackSpeedBoost.get().floatValue());
		}
		return getAttackSpeedModifierBase(stack) + mult;
	}

	public static float getAttackSpeedModifierBase(ItemStack stack) {
		CompoundTag tag = stack.getOrCreateSubTag(KEY_PROPERTIES);
		if(tag.contains(SUBKEY_ATTACKSPEED)) {
			return tag.getFloat(SUBKEY_ATTACKSPEED);
		}
		return 1;
	}

	public static void setBroken(ItemStack stack, boolean broken) {
		CompoundTag tag = stack.getOrCreateSubTag(KEY_PROPERTIES);
		tag.putBoolean(SUBKEY_BROKEN, broken);
	}
	
	public static boolean isBroken(ItemStack stack) {
		CompoundTag tag = stack.getOrCreateSubTag(KEY_PROPERTIES);
		return tag.getBoolean(SUBKEY_BROKEN);
	}

	public void incrementAmountRepaired(ItemStack stack, int materialsUsed) {
		CompoundTag tag = stack.getOrCreateSubTag(KEY_OTHER);
		int i = tag.getInt(SUBKEY_REPAIREDCOUNT);
		i += materialsUsed;
		tag.putInt(SUBKEY_REPAIREDCOUNT, i);
		int j = 0;
		int k = ConfigModules.<ToolLevelingModule>getCachedModuleByName(TOOL_LEVEL_MODULE, false).durabilityRepairsNeeded.get().intValue();
		
		while(i >= k) {
			j++;
			i -= k;
		}
		
		CompoundTag tag2 = stack.getOrCreateSubTag(KEY_LEVELING);
		tag2.putInt(SUBKEY_REPAIREDLEVEL, j);
	}
	
	public void incrementHardnessBroken(ItemStack stack, float hardnessBroken) {
		CompoundTag tag = stack.getOrCreateSubTag(KEY_OTHER);
		float f = tag.getFloat(SUBKEY_HARDNESSBROKEN);
		f += hardnessBroken;
		tag.putFloat(SUBKEY_HARDNESSBROKEN, f);
		int j = 0;
		float g = ConfigModules.<ToolLevelingModule>getCachedModuleByName(TOOL_LEVEL_MODULE, false).speedHardnessNeeded.get().floatValue();
		
		while(f >= g) {
			j++;
			f -= g;
		}
		CompoundTag tag2 = stack.getOrCreateSubTag(KEY_LEVELING);
		tag2.putInt(SUBKEY_SPEEDLEVEL, j);
	}
	
	public void incrementAttacksDone(ItemStack stack, int attacksDone) {
		CompoundTag tag = stack.getOrCreateSubTag(KEY_OTHER);
		int i = tag.getInt(SUBKEY_ATTACKSDONE);
		i += attacksDone;
		tag.putInt(SUBKEY_ATTACKSDONE, i);
		int j = 0;
		int k = ConfigModules.<ToolLevelingModule>getCachedModuleByName(TOOL_LEVEL_MODULE, false).attackSpeedAttacksNeeded.get().intValue();
		
		while(i >= k) {
			j++;
			i -= k;
		}
		
		CompoundTag tag2 = stack.getOrCreateSubTag(KEY_LEVELING);
		tag2.putInt(SUBKEY_ATTACKSPEEDLEVEL, j);
	}
	
	public void incrementDamageDealt(ItemStack stack, float damageDealt) {
		if(!(stack.getItem() instanceof NafisTool && canLevelAttack(stack)))return;
		CompoundTag tag = stack.getOrCreateSubTag(KEY_OTHER);
		float f = tag.getFloat(SUBKEY_DAMAGEDEALT);
		f += damageDealt;
		tag.putFloat(SUBKEY_DAMAGEDEALT, f);
		int j = 0;
		float g = ConfigModules.<ToolLevelingModule>getCachedModuleByName(TOOL_LEVEL_MODULE, false).attackDamageNeeded.get().floatValue();
		
		while(f >= g) {
			j++;
			f -= g;
		}
		CompoundTag tag2 = stack.getOrCreateSubTag(KEY_LEVELING);
		tag2.putInt(SUBKEY_DAMAGELEVEL, j);
	}
	
	@Override
	public boolean isDamageable() {
		return true;
	}

	public int getEnchantability() {
		return 0;
	}

	public boolean canRepair(ItemStack stack, ItemStack ingredient) {
		return false;
	}
	
	public abstract boolean canLevelMining(ItemStack stack);
	
	public abstract boolean canLevelAttack(ItemStack stack);
	
	public static Ingredient getRepairIngredient(ItemStack stack) {
		CompoundTag tag = stack.getOrCreateSubTag(KEY_OTHER);
		if(tag.contains(SUBKEY_REPAIRINGREDIENT)) {
			return Ingredient.fromPacket(new PacketByteBuf(Unpooled.wrappedBuffer(tag.getByteArray(SUBKEY_REPAIRINGREDIENT))));
		}
		return Ingredient.EMPTY;
	}

	public int getMiningLevel(Tag<Item> tag, BlockState state, ItemStack stack, LivingEntity user) {
		return getMiningLevel(stack, state, user);
	}

	public float getMiningSpeedMultiplier(Tag<Item> tag, BlockState state, ItemStack stack, LivingEntity user) {
		return getMiningSpeedMultiplier(stack, state, user);
	}

	public abstract int damageAfterMine(ItemStack stack);

	public abstract int damageAfterHit(ItemStack stack);

	public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if(!isBroken(stack) && ConfigModules.<ToolLevelingModule>getCachedModuleByName(TOOL_LEVEL_MODULE, false).levelingEnabled.get()) {
			incrementAttacksDone(stack, 1);
		}
		
		ItemStack stack2 = stack.copy();
		if(stack2.damage(damageAfterHit(stack), attacker != null ? attacker.getRandom() : new Random(), null)) {
			stack.damage(stack.getMaxDamage() - 1 - stack.getDamage(), attacker, (Consumer<LivingEntity>)((e) -> {
				((LivingEntity) e).sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
			}));
			setBroken(stack, true);
		}else {
			stack.damage(damageAfterHit(stack), attacker, (Consumer<LivingEntity>)((e) -> {
				((LivingEntity) e).sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
			}));
		}
		
		return true;
	}

	public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
		if (!world.isClient && state.getHardness(world, pos) != 0.0F) {
			if(!isBroken(stack) && ConfigModules.<ToolLevelingModule>getCachedModuleByName(TOOL_LEVEL_MODULE, false).levelingEnabled.get()) {
				incrementHardnessBroken(stack, state.getHardness(world, pos));
			}
			ItemStack stack2 = stack.copy();
			if(stack2.damage(damageAfterMine(stack), miner != null ? miner.getRandom() : new Random(), null)) {
				stack.damage(stack.getMaxDamage() - 1 - stack.getDamage(), miner, (Consumer<LivingEntity>)((e) -> {
					((LivingEntity) e).sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
				}));
				setBroken(stack, true);
			}else {
				stack.damage(damageAfterMine(stack), miner, (Consumer<LivingEntity>)((e) -> {
					((LivingEntity) e).sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
				}));
			}
		}

		return true;
	}

	public String getHeadTranslationKey(ItemStack stack) {
		CompoundTag tag = stack.getOrCreateSubTag(KEY_PROPERTIES);
		if(tag.contains(SUBKEY_MATERIAL)) {
			return tag.getString(SUBKEY_MATERIAL);
		}
		return "nafis.bugged";
	}

	@Override
	public Text getName(ItemStack stack) {
		return new TranslatableText(getTranslationKey(), new TranslatableText(getHeadTranslationKey(stack)));
	}
	
	public float postProcessMiningSpeed(Tag<Item> tag, BlockState state, ItemStack stack, /* @Nullable */ LivingEntity user, float currentSpeed, boolean isEffective) {
		return isBroken(stack) ? Float.MIN_NORMAL : currentSpeed;
	}
	
	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		super.appendTooltip(stack, world, tooltip, context);
		
		if(context.isAdvanced() && ConfigModules.<ToolLevelingModule>getCachedModuleByName(TOOL_LEVEL_MODULE, false).levelingEnabled.get()) {
			CompoundTag tag = stack.getOrCreateSubTag(KEY_OTHER);
			
			int i0 = ConfigModules.<ToolLevelingModule>getCachedModuleByName(TOOL_LEVEL_MODULE, false).durabilityRepairsNeeded.get().intValue();
			int i1 = tag.getInt(SUBKEY_REPAIREDCOUNT) % i0;
			tooltip.add(new TranslatableText(TRANSLATION_KEY_DURABILITY_LEVEL, i1, i0));
			if(canLevelMining(stack)) {
				float f0 = ConfigModules.<ToolLevelingModule>getCachedModuleByName(TOOL_LEVEL_MODULE, false).speedHardnessNeeded.get().floatValue();
				float f1 = tag.getFloat(SUBKEY_HARDNESSBROKEN) % f0;
				tooltip.add(new TranslatableText(TRANSLATION_KEY_SPEED_LEVEL, f1, f0));
			}
			if(canLevelAttack(stack)) {
				float g0 = ConfigModules.<ToolLevelingModule>getCachedModuleByName(TOOL_LEVEL_MODULE, false).attackDamageNeeded.get().floatValue();
				float g1 = tag.getFloat(SUBKEY_DAMAGEDEALT) % g0;
				tooltip.add(new TranslatableText(TRANSLATION_KEY_ATTACK_LEVEL, g1, g0));
				float h0 = ConfigModules.<ToolLevelingModule>getCachedModuleByName(TOOL_LEVEL_MODULE, false).attackSpeedAttacksNeeded.get().floatValue();
				float h1 = tag.getFloat(SUBKEY_ATTACKSDONE) % h0;
				tooltip.add(new TranslatableText(TRANSLATION_KEY_ATTACKSPEED_LEVEL, h1, h0));
			}
		}
		if(!context.isAdvanced()) {
			MutableText brokenText = new TranslatableText(isBroken(stack) ? "Broken" : "");
			brokenText.setStyle(brokenText.getStyle().withColor(TextColor.fromRgb(0xBB0000)));
			tooltip.add(brokenText);

			int i0 = stack.getMaxDamage();
			int i1 = stack.getMaxDamage() - stack.getDamage();
			tooltip.add(new TranslatableText(TRANSLATION_KEY_DURABILITY, i1, i0));
		}
		
	}
	
	public Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(EquipmentSlot slot, ItemStack stack) {
		if(slot != EquipmentSlot.MAINHAND) return super.getAttributeModifiers(slot);
		Multimap<EntityAttribute, EntityAttributeModifier> map = HashMultimap.create(2, 1);
		map.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", (double)getAttackModifier(stack), EntityAttributeModifier.Operation.ADDITION));
		map.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", (double)getAttackSpeedModifier(stack), EntityAttributeModifier.Operation.ADDITION));
		return map;
	}
	
	
}
