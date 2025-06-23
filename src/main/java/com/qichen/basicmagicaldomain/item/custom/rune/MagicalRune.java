package com.qichen.basicmagicaldomain.item.custom.rune;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public abstract class MagicalRune extends Item {
    private final int type;
    private final int range;
    public static int Metal=1;
    public static int Wood=2;
    public static int Water=3;
    public static int Fire=4;
    public static int Earth=5;

    public MagicalRune(Properties properties,int type,int range) {
        super(properties);
        this.type=type;
        this.range=range;
    }

    public abstract void use_effect(ItemStack itemStack);
}
