package com.qichen.basicmagicaldomain.item.custom.rune;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class MagicalRune extends Item {
    protected final int type;
    protected final int range;
    protected final int effect_time;
    public static int Metal=1;
    public static int Wood=2;
    public static int Water=3;
    public static int Fire=4;
    public static int Earth=5;

    public MagicalRune(Properties properties,int type,int range,int effect_time) {
        super(properties);
        this.type=type;
        this.range=range;
        this.effect_time=effect_time;
    }



}
