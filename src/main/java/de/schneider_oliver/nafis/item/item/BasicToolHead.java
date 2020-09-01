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

import de.schneider_oliver.nafis.recipe.sets.NafisSets;
import io.netty.buffer.Unpooled;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;

public class BasicToolHead extends AbstractToolComponent{

	private final int durability, miningLevel;
	private final float attackDamage;
	private final Ingredient repairIngredient;
	
	public BasicToolHead(Settings settings, int durability, int miningLevel, float attackDamage, Ingredient repairIngedrient, Identifier partIdentifier, Identifier materialIdentifier) {
		super(settings, partIdentifier, materialIdentifier);
		this.durability = durability;
		this.miningLevel = miningLevel;
		this.attackDamage = attackDamage;
		this.repairIngredient = repairIngedrient;
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

	@Override
	public CompoundTag writeOtherToTag(CompoundTag tagIn) {
		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
		repairIngredient.write(buf);
		tagIn.putByteArray(SUBKEY_REPAIRINGREDIENT, buf.array());
		return tagIn;
	}

}
