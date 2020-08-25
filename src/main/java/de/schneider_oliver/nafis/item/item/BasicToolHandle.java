package de.schneider_oliver.nafis.item.item;

import static de.schneider_oliver.nafis.utils.NBTKeys.SUBKEY_SPEEDMULT;
import static de.schneider_oliver.nafis.utils.NBTKeys.SUBKEY_ATTACKSPEED;

import de.schneider_oliver.nafis.recipe.tags.NafisSets;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;

public class BasicToolHandle extends AbstractToolComponent{

	private final float speedMult, attackSpeed;
	
	public BasicToolHandle(Settings settings, float speedMult, float attackSpeed, Identifier partIdentifier, Identifier materialIdentifier) {
		super(settings, partIdentifier, materialIdentifier);
		this.speedMult = speedMult;
		this.attackSpeed = attackSpeed;
		NafisSets.registerItem(this, partIdentifier);
	}

	@Override
	public CompoundTag writeStatsToTag(CompoundTag tagIn) {
		float f = tagIn.getFloat(SUBKEY_SPEEDMULT);
		tagIn.putFloat(SUBKEY_SPEEDMULT, (f == 0 ? 1 : f) + speedMult);
		float f2 = tagIn.getFloat(SUBKEY_ATTACKSPEED);
		tagIn.putFloat(SUBKEY_ATTACKSPEED, f2 + attackSpeed);
		return tagIn;
	}

}
