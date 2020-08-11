package com.meteor.honkaiimpact.common.items;

import com.meteor.honkaiimpact.HonkaiImpact;
import com.meteor.honkaiimpact.common.entities.EntitySupplyChest;
import com.meteor.honkaiimpact.common.handler.SupplyHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Predicate;

public class ItemFocusedSupplyCard extends Item {

    private static final Predicate<Entity> field_219989_a = EntityPredicates.NOT_SPECTATING.and(Entity::canBeCollidedWith);

    public ItemFocusedSupplyCard() {
        super(new Properties().group(HonkaiImpact.itemGroup).rarity(Rarity.UNCOMMON));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        RayTraceResult raytraceresult = rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.ANY);
        if (raytraceresult.getType() == RayTraceResult.Type.MISS) {
            return ActionResult.resultPass(itemstack);
        } else {
            Vec3d vec3d = playerIn.getLook(1.0F);
            double d0 = 5.0D;
            List<Entity> list = worldIn.getEntitiesInAABBexcluding(playerIn, playerIn.getBoundingBox().expand(vec3d.scale(5.0D)).grow(1.0D), field_219989_a);
            if (!list.isEmpty()) {
                Vec3d vec3d1 = playerIn.getEyePosition(1.0F);

                for(Entity entity : list) {
                    AxisAlignedBB axisalignedbb = entity.getBoundingBox().grow((double)entity.getCollisionBorderSize());
                    if (axisalignedbb.contains(vec3d1)) {
                        return ActionResult.resultPass(itemstack);
                    }
                }
            }

            if (raytraceresult.getType() == RayTraceResult.Type.BLOCK) {
                int times = playerIn.isSneaking() ? Math.min(10, itemstack.getCount()) : 1;
                EntitySupplyChest chest = new EntitySupplyChest(worldIn, playerIn, EntitySupplyChest.FOCUSED_SUPPLY, times);
                chest.setPosition(raytraceresult.getHitVec().x, raytraceresult.getHitVec().y, raytraceresult.getHitVec().z);
                chest.setRotation(playerIn.rotationYaw);
                if (!worldIn.hasNoCollisions(chest, chest.getBoundingBox().grow(-0.1D))) {
                    return ActionResult.resultFail(itemstack);
                } else {
                    if (!worldIn.isRemote) {
                        worldIn.addEntity(chest);
                        itemstack.shrink(times);
                        playerIn.getCooldownTracker().setCooldown(this, 40);
                    }
                    return ActionResult.resultSuccess(itemstack);
                }
            } else {
                return ActionResult.resultPass(itemstack);
            }
        }
    }

}
