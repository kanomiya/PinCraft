package com.kanomiya.mcmod.pincraft.api.pin;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

public interface IPinModel
{
    Vec3d getOffset();

    Vec3d getSize();

    Vec3d getKnotOffset();


    default AxisAlignedBB getRelativeBox()
    {
        Vec3d offset = getOffset();
        Vec3d size = getSize();

        return new AxisAlignedBB(offset.xCoord -size.xCoord/2, offset.yCoord -size.yCoord/2, offset.zCoord -size.zCoord/2, offset.xCoord +size.xCoord/2, offset.yCoord +size.yCoord/2, offset.zCoord +size.zCoord/2).offset(0.5d, 0.5d, 0.5d);
    }

    default Vec3d getRelativeKnotVec()
    {
        AxisAlignedBB relBox = getRelativeBox();
        return new Vec3d((relBox.maxX+relBox.minX)/2, (relBox.maxY+relBox.minY)/2, (relBox.maxZ+relBox.minZ)/2).add(getKnotOffset());
    }


}
