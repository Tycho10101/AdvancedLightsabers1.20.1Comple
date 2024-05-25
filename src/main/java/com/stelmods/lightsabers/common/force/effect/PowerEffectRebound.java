package com.stelmods.lightsabers.common.force.effect;

import com.stelmods.lightsabers.common.force.PowerDesc;
import com.stelmods.lightsabers.common.force.PowerDesc.Target;
import com.stelmods.lightsabers.common.force.PowerDesc.Unit;

public class PowerEffectRebound extends PowerEffect
{
    public PowerEffectRebound()
    {
        super(0);
    }
    
    @Override
    public String[] getDesc()
    {
        return new String[] {PowerDesc.create("effect2", Unit.FALL_RESISTANCE, Target.CASTER)};
    }
}
