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
import static de.schneider_oliver.nafis.utils.Strings.TOOL_LEVEL_MODULE;
import static de.schneider_oliver.nafis.utils.Strings.TRANSLATION_KEY_BUGGED;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import de.schneider_oliver.nafis.config.ConfigModules;
import de.schneider_oliver.nafis.config.ToolLevelingModule;
import de.schneider_oliver.nafis.mixin.ItemMixin;
import de.schneider_oliver.nafis.modifiers.ModifierRegistry;
import de.schneider_oliver.nafis.modifiers.modifiers.Modifier;
import io.netty.buffer.Unpooled;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
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
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface NafisTool {

	public default ItemStack setComponents(ItemStack stack, List<ItemStack> components) {
		CompoundTag tag = new CompoundTag(); // KEY_COMPONENTS
		CompoundTag tag2 = new CompoundTag(); // KEY_PROPERTIES
		CompoundTag tag3 = stack.getOrCreateSubTag(KEY_OTHER);
		stack.putSubTag(KEY_MODIFIER, new CompoundTag());
		EnchantmentHelper.set(new HashMap<>(), stack);
		for(int i = 0; i < components.size(); i++) {
			if(components.get(i).getItem() instanceof AbstractToolComponent) {
				tag.put(SUBKEY_COMPONENT + i, components.get(i).toTag(new CompoundTag()));
				((AbstractToolComponent)components.get(i).getItem()).writeStatsToTag(tag2);
				((AbstractToolComponent)components.get(i).getItem()).writeOtherToTag(tag3);
				stack = ((AbstractToolComponent)components.get(i).getItem()).writeModifiersToStack(stack);
			}
		}
		stack.putSubTag(KEY_COMPONENTS, tag);
		stack.putSubTag(KEY_PROPERTIES, tag2);
		stack.putSubTag(KEY_OTHER, tag3);
		ModifierRegistry.apply(stack);
		return stack;
	}

	public default ArrayList<ItemStack> getComponents(ItemStack stack){
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

	public default Identifier getToolType(ItemStack stack) {
		CompoundTag tag = stack.getOrCreateSubTag(KEY_TYPE);
		if(tag.contains(SUBKEY_TOOLHANDLER) && tag.contains(SUBKEY_TOOLTYPE)) {
			return ident(tag.getString(SUBKEY_TOOLHANDLER), tag.getString(SUBKEY_TOOLTYPE));
		}
		return IDENT_UNKNOWN;
	}
	
	public default Set<Modifier> getModifiers(ItemStack stack){
		return stack.getOrCreateSubTag(KEY_MODIFIER).getKeys().stream().map(a->{
			return ModifierRegistry.getModifier(new Identifier(a));
		}).filter(a -> !a._equals(ModifierRegistry.EMPTY_MODIFIER)).collect(Collectors.toSet());
	}

	public default float getMiningSpeedMultiplier(ItemStack stack, BlockState state, LivingEntity breakingEntity) {
		float mult = 1;
		if(ConfigModules.<ToolLevelingModule>getCachedModuleByName(TOOL_LEVEL_MODULE, false).levelingEnabled.get()) {
			CompoundTag tag = stack.getOrCreateSubTag(KEY_LEVELING);
			int i = tag.getInt(SUBKEY_SPEEDLEVEL);
			mult += ConfigModules.<ToolLevelingModule>getCachedModuleByName(TOOL_LEVEL_MODULE, false).speedBoostForLevel(i);
		}
		return getMiningSpeedMultiplierBase(stack, state, breakingEntity) * mult * getMiningSpeedMultiplierMods(stack, state, breakingEntity);
	}
	
	public default float getMiningSpeedMultiplierMods(ItemStack stack, BlockState state, LivingEntity breakingEntity) {
		return getModifiers(stack).stream().map(a -> a.getMiningSpeedMult(stack, state, breakingEntity)).reduce(0F, (a, b) -> a+b);
	}

	public default float getMiningSpeedMultiplierBase(ItemStack stack, BlockState state, LivingEntity breakingEntity) {
		CompoundTag tag = stack.getOrCreateSubTag(KEY_PROPERTIES);
		if(tag.contains(SUBKEY_SPEEDMULT)) {
			return tag.getFloat(SUBKEY_SPEEDMULT) + getMiningSpeedMultiplierBaseMods(stack, state, breakingEntity);
		}
		return 0 + getMiningSpeedMultiplierBaseMods(stack, state, breakingEntity);
	}
	
	public default float getMiningSpeedMultiplierBaseMods(ItemStack stack, BlockState state, LivingEntity breakingEntity) {
		return getModifiers(stack).stream().map(a -> a.getMiningSpeedMod(stack, state, breakingEntity)).reduce(0F, (a, b) -> a+b);
	}

	public default int getMiningLevel(ItemStack stack, BlockState state, LivingEntity breakingEntity) {
		if(isBroken(stack))return 0;
		return getMiningLevelBase(stack, state, breakingEntity) + getMiningLevelBaseMods(stack, state, breakingEntity);
	}
	
	public default int getMiningLevelBase(ItemStack stack, BlockState state, LivingEntity breakingEntity) {
		CompoundTag tag = stack.getOrCreateSubTag(KEY_PROPERTIES);
		if(tag.contains(SUBKEY_MININGLEVEL)) {
			return tag.getInt(SUBKEY_MININGLEVEL);
		}
		return 0;
	}
	
	public default int getMiningLevelBaseMods(ItemStack stack, BlockState state, LivingEntity breakingEntity) {
		return getModifiers(stack).stream().map(a -> a.getMiningLevelMod(stack, state, breakingEntity)).reduce(0, (a, b) -> a+b);
	}

	public default int getMaxDamage(ItemStack stack) {
		float mult = 1;
		if(ConfigModules.<ToolLevelingModule>getCachedModuleByName(TOOL_LEVEL_MODULE, false).levelingEnabled.get()) {
			CompoundTag tag = stack.getOrCreateSubTag(KEY_LEVELING);
			int i = tag.getInt(SUBKEY_REPAIREDLEVEL);
			mult += ConfigModules.<ToolLevelingModule>getCachedModuleByName(TOOL_LEVEL_MODULE, false).durabilityBoostForLevel(i);
		}
		return (int)(getMaxDamageBase(stack) * mult * getMaxDamageMultMods(stack));
	}
	
	public default float getMaxDamageMultMods(ItemStack stack) {
		return getModifiers(stack).stream().map(a -> a.getMaxDamageMult(stack)).reduce(1F, (a, b) -> a*b);
	}
	
	public default int getMaxDamageBase(ItemStack stack) {
		CompoundTag tag = stack.getOrCreateSubTag(KEY_PROPERTIES);
		if(tag.contains(SUBKEY_DURABILITY)) {
			return tag.getInt(SUBKEY_DURABILITY) + getMaxDamageBaseMods(stack);
		}
		return 0 + getMaxDamageBaseMods(stack);
	}
	
	public default int getMaxDamageBaseMods(ItemStack stack) {
		return getModifiers(stack).stream().map(a -> a.getMaxDamageMod(stack)).reduce(0, (a, b) -> a+b);
	}
	
	public default float getAttackModifier(ItemStack stack) {
		if(isBroken(stack))return 0;
		float mult = 1;
		if(ConfigModules.<ToolLevelingModule>getCachedModuleByName(TOOL_LEVEL_MODULE, false).levelingEnabled.get()) {
			CompoundTag tag = stack.getOrCreateSubTag(KEY_LEVELING);
			int i = tag.getInt(SUBKEY_DAMAGELEVEL);
			mult += ConfigModules.<ToolLevelingModule>getCachedModuleByName(TOOL_LEVEL_MODULE, false).attackDamageBoostForLevel(i);
		}
		return getAttackModifierBase(stack) * mult * getAttackModifierMods(stack);
	}
	
	public default float getAttackModifierMods(ItemStack stack) {
		return getModifiers(stack).stream().map(a -> a.getAttackMult(stack)).reduce(1F, (a, b) -> a*b);
	}

	public default float getAttackModifierBase(ItemStack stack) {
		CompoundTag tag = stack.getOrCreateSubTag(KEY_PROPERTIES);
		if(tag.contains(SUBKEY_ATTACKDAMAGE)) {
			return tag.getFloat(SUBKEY_ATTACKDAMAGE) + getAttackModifierBaseMods(stack);
		}
		return 0 + getAttackModifierBaseMods(stack);
	}
	
	public default float getAttackModifierBaseMods(ItemStack stack) {
		return getModifiers(stack).stream().map(a -> a.getAttackMod(stack)).reduce(0F, (a, b) -> a+b);
	}
	
	public default float getAttackSpeedModifier(ItemStack stack) {
		float mult = 1;
		if(ConfigModules.<ToolLevelingModule>getCachedModuleByName(TOOL_LEVEL_MODULE, false).levelingEnabled.get()) {
			CompoundTag tag = stack.getOrCreateSubTag(KEY_LEVELING);
			int i = tag.getInt(SUBKEY_ATTACKSPEEDLEVEL);
			mult += ConfigModules.<ToolLevelingModule>getCachedModuleByName(TOOL_LEVEL_MODULE, false).attackSpeedBoostForLevel(i);
		}
		return (getAttackSpeedModifierBase(stack) + mult) * getAttackSpeedModifierMods(stack);
	}
	
	public default float getAttackSpeedModifierMods(ItemStack stack) {
		return getModifiers(stack).stream().map(a -> a.getAttackSpeedMult(stack)).reduce(1F, (a, b) -> a*b);
	}

	public default float getAttackSpeedModifierBase(ItemStack stack) {
		CompoundTag tag = stack.getOrCreateSubTag(KEY_PROPERTIES);
		if(tag.contains(SUBKEY_ATTACKSPEED)) {
			return tag.getFloat(SUBKEY_ATTACKSPEED) + getAttackSpeedModifierBaseMods(stack);
		}
		return 1 + getAttackSpeedModifierBaseMods(stack);
	}
	
	public default float getAttackSpeedModifierBaseMods(ItemStack stack) {
		return getModifiers(stack).stream().map(a -> a.getAttackSpeedMod(stack)).reduce(0F, (a, b) -> a+b);
	}

	public static void setBroken(ItemStack stack, boolean broken) {
		CompoundTag tag = stack.getOrCreateSubTag(KEY_PROPERTIES);
		tag.putBoolean(SUBKEY_BROKEN, broken);
	}
	
	public static boolean isBroken(ItemStack stack) {
		CompoundTag tag = stack.getOrCreateSubTag(KEY_PROPERTIES);
		return tag.getBoolean(SUBKEY_BROKEN);
	}

	public default void incrementAmountRepaired(ItemStack stack, int materialsUsed) {
		CompoundTag tag = stack.getOrCreateSubTag(KEY_OTHER);
		int i = tag.getInt(SUBKEY_REPAIREDCOUNT);
		i += materialsUsed;
		CompoundTag tag2 = stack.getOrCreateSubTag(KEY_LEVELING);
		int j = tag2.getInt(SUBKEY_REPAIREDLEVEL);
		int k = ConfigModules.<ToolLevelingModule>getCachedModuleByName(TOOL_LEVEL_MODULE, false).repairsNeededForLevel(j + 1);
		
		while(i >= k) {
			j++;
			i -= k;
			k = ConfigModules.<ToolLevelingModule>getCachedModuleByName(TOOL_LEVEL_MODULE, false).repairsNeededForLevel(j + 1);
		}
		tag.putInt(SUBKEY_REPAIREDCOUNT, i);
		tag2.putInt(SUBKEY_REPAIREDLEVEL, j);
	}
	
	public default void incrementHardnessBroken(ItemStack stack, float hardnessBroken) {
		CompoundTag tag = stack.getOrCreateSubTag(KEY_OTHER);
		float f = tag.getFloat(SUBKEY_HARDNESSBROKEN);
		f += hardnessBroken;
		CompoundTag tag2 = stack.getOrCreateSubTag(KEY_LEVELING);
		int j = tag2.getInt(SUBKEY_SPEEDLEVEL);
		float g = ConfigModules.<ToolLevelingModule>getCachedModuleByName(TOOL_LEVEL_MODULE, false).hardnessNeededForLevel(j + 1);
		
		while(f >= g) {
			j++;
			f -= g;
			g = ConfigModules.<ToolLevelingModule>getCachedModuleByName(TOOL_LEVEL_MODULE, false).hardnessNeededForLevel(j + 1);
		}
		tag.putFloat(SUBKEY_HARDNESSBROKEN, f);
		tag2.putInt(SUBKEY_SPEEDLEVEL, j);
	}
	
	public default void incrementAttacksDone(ItemStack stack, int attacksDone) {
		CompoundTag tag = stack.getOrCreateSubTag(KEY_OTHER);
		int i = tag.getInt(SUBKEY_ATTACKSDONE);
		i += attacksDone;
		CompoundTag tag2 = stack.getOrCreateSubTag(KEY_LEVELING);
		int j = tag2.getInt(SUBKEY_ATTACKSPEEDLEVEL);
		int k = ConfigModules.<ToolLevelingModule>getCachedModuleByName(TOOL_LEVEL_MODULE, false).attacksNeededForLevel(j + 1);
		
		while(i >= k) {
			j++;
			i -= k;
			k = ConfigModules.<ToolLevelingModule>getCachedModuleByName(TOOL_LEVEL_MODULE, false).attacksNeededForLevel(j + 1);
		}
		tag.putInt(SUBKEY_ATTACKSDONE, i);
		tag2.putInt(SUBKEY_ATTACKSPEEDLEVEL, j);
	}
	
	public default void incrementDamageDealt(ItemStack stack, float damageDealt) {
		if(!(stack.getItem() instanceof NafisTool && canLevelAttack(stack)))return;
		CompoundTag tag = stack.getOrCreateSubTag(KEY_OTHER);
		float f = tag.getFloat(SUBKEY_DAMAGEDEALT);
		f += damageDealt;
		CompoundTag tag2 = stack.getOrCreateSubTag(KEY_LEVELING);
		int j = tag2.getInt(SUBKEY_DAMAGELEVEL);
		float g = ConfigModules.<ToolLevelingModule>getCachedModuleByName(TOOL_LEVEL_MODULE, false).attackDamageNeededForLevel(j + 1);
		
		while(f >= g) {
			j++;
			f -= g;
			g = ConfigModules.<ToolLevelingModule>getCachedModuleByName(TOOL_LEVEL_MODULE, false).attackDamageNeededForLevel(j + 1);
		}
		tag.putFloat(SUBKEY_DAMAGEDEALT, f);
		tag2.putInt(SUBKEY_DAMAGELEVEL, j);
	}
	
	public int damageAfterMine(ItemStack stack);

	public int damageAfterHit(ItemStack stack);
	
	public default String getHeadTranslationKey(ItemStack stack) {
		CompoundTag tag = stack.getOrCreateSubTag(KEY_PROPERTIES);
		if(tag.contains(SUBKEY_MATERIAL)) {
			return tag.getString(SUBKEY_MATERIAL);
		}
		return TRANSLATION_KEY_BUGGED;
	}
	

	public boolean canLevelMining(ItemStack stack);
	
	public boolean canLevelAttack(ItemStack stack);
	
	public default Text getName(ItemStack stack) {
		return new TranslatableText(((Item)(Object)this).getTranslationKey(), new TranslatableText(getHeadTranslationKey(stack)));
	}
	

	public default Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(EquipmentSlot slot, ItemStack stack) {
		if(slot != EquipmentSlot.MAINHAND) return ((Item)(Object)this).getAttributeModifiers(slot);
		Multimap<EntityAttribute, EntityAttributeModifier> map = HashMultimap.create();
		map.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ItemMixin.getADMI(), "Weapon modifier", (double)getAttackModifier(stack), EntityAttributeModifier.Operation.ADDITION));
		map.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ItemMixin.getADMI(), "Weapon modifier", (double)getAttackSpeedModifier(stack), EntityAttributeModifier.Operation.ADDITION));
		map.putAll(getModifiersMods(slot, stack));
		return map;
	}
	
	public default Multimap<EntityAttribute, EntityAttributeModifier> getModifiersMods(EquipmentSlot slot, ItemStack stack) {
		Multimap<EntityAttribute, EntityAttributeModifier> map = HashMultimap.create();
		getModifiers(stack).forEach(m -> {
			if(m.hasAttributeModifiers(stack)) {
				map.putAll(m.getAttributeModifiers(stack));
			}
		});
		return map;
	}

	
	
	public default boolean isDamageable() {
		return true;
	}

	public default int getEnchantability() {
		return 0;
	}

	public default boolean canRepair(ItemStack stack, ItemStack ingredient) {
		return false;
	}
	
	public default Ingredient getRepairIngredient(ItemStack stack) {
		CompoundTag tag = stack.getOrCreateSubTag(KEY_OTHER);
		if(tag.contains(SUBKEY_REPAIRINGREDIENT)) {
			return Ingredient.fromPacket(new PacketByteBuf(Unpooled.wrappedBuffer(tag.getByteArray(SUBKEY_REPAIRINGREDIENT))));
		}
		return Ingredient.EMPTY;
	}

	public default int getMiningLevel(Tag<Item> tag, BlockState state, ItemStack stack, LivingEntity user) {
		return getMiningLevel(stack, state, user);
	}

	public default float getMiningSpeedMultiplier(Tag<Item> tag, BlockState state, ItemStack stack, LivingEntity user) {
		return getMiningSpeedMultiplier(stack, state, user);
	}
	
	public default boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if(!isBroken(stack) && ConfigModules.<ToolLevelingModule>getCachedModuleByName(TOOL_LEVEL_MODULE, false).levelingEnabled.get()) {
			incrementAttacksDone(stack, 1);
		}
		
		ItemStack stack2 = stack.copy();
		int damage = damageAfterHitInternal(stack, target);
		if(stack2.damage(damage, attacker != null ? attacker.getRandom() : new Random(), null)) {
			stack.damage(stack.getMaxDamage() - 1 - stack.getDamage(), attacker, (Consumer<LivingEntity>)((e) -> {
				((LivingEntity) e).sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
			}));
			setBroken(stack, true);
		}else {
			stack.damage(damage, attacker, (Consumer<LivingEntity>)((e) -> {
				((LivingEntity) e).sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
			}));
		}
		
		return true;
	}

	public default boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
		if (!world.isClient && state.getHardness(world, pos) != 0.0F) {

			
			if(!isBroken(stack) && !world.isClient && ConfigModules.<ToolLevelingModule>getCachedModuleByName(TOOL_LEVEL_MODULE, false).levelingEnabled.get()) {
				incrementHardnessBroken(stack, state.getHardness(world, pos));
			}
			ItemStack stack2 = stack.copy();
			int damage = damageAfterMineInternal(stack, state);
			if(stack2.damage(damage, miner != null ? miner.getRandom() : new Random(), null)) {
				stack.damage(stack.getMaxDamage() - 1 - stack.getDamage(), miner, (Consumer<LivingEntity>)((e) -> {
					((LivingEntity) e).sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
				}));
				setBroken(stack, true);
			}else {
				stack.damage(damage, miner, (Consumer<LivingEntity>)((e) -> {
					((LivingEntity) e).sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
				}));
			}
		}

		return true;
	}
	
	public default int damageAfterMineInternal(ItemStack stack, BlockState state) {
		return damageAfterMine(stack) + damageAfterMineInternalMods(stack, state);
	}
	
	public default int damageAfterMineInternalMods(ItemStack stack, BlockState state) {
		return getModifiers(stack).stream().map(a -> a.damageAfterMineMod(stack, state)).reduce(0, (a, b) -> a+b);
	}
	
	public default int damageAfterHitInternal(ItemStack stack, LivingEntity target) {
		return damageAfterHit(stack) + damageAfterHitInternalMods(stack, target);
	}
	
	public default int damageAfterHitInternalMods(ItemStack stack, LivingEntity target) {
		return getModifiers(stack).stream().map(a -> a.damageAfterHitMod(stack, target)).reduce(0, (a, b) -> a+b);
	}
	
	public default float postProcessMiningSpeed(Tag<Item> tag, BlockState state, ItemStack stack, /* @Nullable */ LivingEntity user, float currentSpeed, boolean isEffective) {
		return isBroken(stack) ? Float.MIN_NORMAL : currentSpeed;
	}
	
	public default float getRepairCost(ItemStack stack) {
		return 4F + getRepairCostMods(stack);
	}
	
	public default int getRepairCostMods(ItemStack stack) {
		return getModifiers(stack).stream().map(a -> a.repairMatNeededMod(stack)).reduce(0, (a, b) -> a+b);
	}
}
