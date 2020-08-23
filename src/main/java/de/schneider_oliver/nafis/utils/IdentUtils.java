package de.schneider_oliver.nafis.utils;

import net.minecraft.util.Identifier;

public class IdentUtils {

	public static final Identifier IDENT_UNKNOWN = ident("unknown");
	
	public static final Identifier TOOL_PART_BASIC_HANDLE = ident("basic_tool_handle");
	public static final Identifier TOOL_PART_PICKAXE_HEAD = ident("pickaxe_head");
	public static final Identifier TOOL_PART_AXE_HEAD = ident("axe_head");
	public static final Identifier TOOL_PART_SHOVEL_HEAD = ident("shovel_head");
	public static final Identifier TOOL_PART_HOE_HEAD = ident("hoe_head");
	
	public static Identifier ident(String name) {
		return new Identifier(Reference.MOD_ID, name);
	}
	
	public static Identifier ident(String modid, String name) {
		return new Identifier(modid, name);
	}
	
}
