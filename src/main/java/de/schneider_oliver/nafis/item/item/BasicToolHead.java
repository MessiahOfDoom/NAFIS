package de.schneider_oliver.nafis.item.item;

import static de.schneider_oliver.nafis.utils.NBTKeys.SUBKEY_DURABILITY;
import static de.schneider_oliver.nafis.utils.NBTKeys.SUBKEY_MININGLEVEL;
import static de.schneider_oliver.nafis.utils.NBTKeys.SUBKEY_MATERIAL;
import static de.schneider_oliver.nafis.utils.NBTKeys.*;

import de.schneider_oliver.nafis.recipe.tags.NafisSets;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;

public class BasicToolHead extends AbstractToolComponent{

	private final int durability, miningLevel;
	private final float attackDamage;
	
	public BasicToolHead(Settings settings, int durability, int miningLevel, float attackDamage, Identifier partIdentifier, Identifier materialIdentifier) {
		super(settings, partIdentifier, materialIdentifier);
		this.durability = durability;
		this.miningLevel = miningLevel;
		this.attackDamage = attackDamage;
		NafisSets.registerItem(this, partIdentifier);
	}

	@Override
	public CompoundTag writeStatsToTag(CompoundTag tagIn) {
		tagIn.putInt(SUBKEY_MININGLEVEL, miningLevel);
		tagIn.putInt(SUBKEY_DURABILITY, durability);
		tagIn.putString(SUBKEY_MATERIAL, translationKeyMaterial());
		tagIn.putFloat(SUBKEY_ATTACKDAMAGE, attackDamage);
		return tagIn;
	}

}