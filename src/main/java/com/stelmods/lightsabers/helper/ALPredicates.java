package com.stelmods.lightsabers.helper;

import com.stelmods.lightsabers.common.force.Power;
import com.stelmods.lightsabers.common.force.PowerData;
import com.stelmods.lightsabers.common.force.PowerManager;
import com.google.common.base.Predicate;

import net.minecraft.entity.player.EntityPlayer;

public class ALPredicates
{
    public static Predicate<Power> isUnlocked(final EntityPlayer player)
    {
        return new Predicate<Power>()
        {
            @Override
            public boolean apply(Power input)
            {
                return PowerManager.hasPowerUnlocked(player, input);
            }
        };
    }
    
    public static Predicate<PowerData> isRelevant(final EntityPlayer player)
    {
        return new Predicate<PowerData>()
        {
            @Override
            public boolean apply(PowerData input)
            {
                return input.isUnlocked() && input.power.isUsable() && ALHelper.getUnlockedChildren(player, input.power).isEmpty();
            }
        };
    }
}
