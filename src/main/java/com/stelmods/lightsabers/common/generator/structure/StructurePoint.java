package com.stelmods.lightsabers.common.generator.structure;

import net.minecraft.util.ChunkCoordinates;

public class StructurePoint extends ChunkCoordinates
{
    public StructurePoint(int x, int y, int z)
    {
        super(x, y, z);
    }

    public StructurePoint(StructurePoint p)
    {
        super(p);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof StructurePoint chunkcoordinates))
        {
            return false;
        }
        else
        {
            return posX == chunkcoordinates.posX && posZ == chunkcoordinates.posZ;
        }
    }
}
