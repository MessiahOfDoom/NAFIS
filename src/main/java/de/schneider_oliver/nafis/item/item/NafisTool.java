package de.schneider_oliver.nafis.item.item;

import static de.schneider_oliver.nafis.utils.IdentUtils.IDENT_UNKNOWN;
import static de.schneider_oliver.nafis.utils.IdentUtils.ident;
import static de.schneider_oliver.nafis.utils.NBTKeys.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import net.fabricmc.fabric.api.tool.attribute.v1.DynamicAttributeTool;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tag.Tag;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class NafisTool extends Item implements DynamicAttributeTool{

	public NafisTool(Settings settings) {
		super(settings.maxCount(1));
	}

	public static ItemStack setComponents(ItemStack stack, List<ItemStack> components) {
		CompoundTag tag = stack.getOrCreateSubTag(KEY_COMPONENTS);
		CompoundTag tag2 = stack.getOrCreateSubTag(KEY_PROPERTIES);
		for(int i = 0; i < components.size(); i++) {
			if(components.get(i).getItem() instanceof AbstractToolComponent) {
				tag.put(SUBKEY_COMPONENT + i, components.get(i).toTag(new CompoundTag()));
				((AbstractToolComponent)components.get(i).getItem()).writeStatsToTag(tag2);
			}
		}
		stack.putSubTag(KEY_COMPONENTS, tag);
		stack.putSubTag(KEY_PROPERTIES, tag2);
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
		CompoundTag tag = stack.getOrCreateSubTag(KEY_PROPERTIES);
		if(tag.contains(SUBKEY_SPEEDMULT)) {
			return tag.getFloat(SUBKEY_SPEEDMULT);
		}
		return 0;
	}

	public int getMiningLevel(ItemStack stack, BlockState state, LivingEntity breakingEntity) {
		CompoundTag tag = stack.getOrCreateSubTag(KEY_PROPERTIES);
		if(tag.contains(SUBKEY_MININGLEVEL)) {
			return tag.getInt(SUBKEY_MININGLEVEL);
		}
		return 0;
	}

	public int getMaxDamage(ItemStack stack) {
		CompoundTag tag = stack.getOrCreateSubTag(KEY_PROPERTIES);
		if(tag.contains(SUBKEY_DURABILITY)) {
			return tag.getInt(SUBKEY_DURABILITY);
		}
		return 0;
	}

	@Override
	public boolean isDamageable() {
		return true;
	}

	public int getEnchantability() {
		return -1;
	}

	public boolean canRepair(ItemStack stack, ItemStack ingredient) {
		return false;
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
		stack.damage(damageAfterHit(stack), attacker, (Consumer<LivingEntity>)((e) -> {
			((LivingEntity) e).sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
		}));
		return true;
	}

	public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
		if (!world.isClient && state.getHardness(world, pos) != 0.0F) {
			stack.damage(damageAfterMine(stack), miner, (Consumer<LivingEntity>)((e) -> {
				((LivingEntity) e).sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
			}));
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
}
