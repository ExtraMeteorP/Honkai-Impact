package com.meteor.honkaiimpact.client.particles;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;

import javax.annotation.Nullable;
/**
public class ParticleFactoryGhostFire implements IParticleFactory<ParticleDataGhostFire> {

    private final IAnimatedSprite sprites;

    public ParticleFactoryGhostFire(IAnimatedSprite sprite) {
        this.sprites = sprite;
    }

    @Nullable
    @Override
    public Particle makeParticle(ParticleDataGhostFire typeIn, World worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        ParticleGhostFire particle = new ParticleGhostFire(worldIn, x, y, z, typeIn.getSpeed(), typeIn.getColor(), typeIn.getDiameter());
        particle.selectSpriteRandomly(sprites);
        return particle;
    }

}
**/