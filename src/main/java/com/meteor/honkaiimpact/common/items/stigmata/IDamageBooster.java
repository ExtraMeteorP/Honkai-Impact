package com.meteor.honkaiimpact.common.items.stigmata;

import com.meteor.honkaiimpact.common.core.DamageCalculator;
import net.minecraft.entity.player.PlayerEntity;

public interface IDamageBooster {

    public DamageCalculator getDamageBoost(PlayerEntity player);

}
