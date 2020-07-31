package com.meteor.honkaiimpact.common.core;

public class DamageCalculator {

    private float allDMG = 0F;
    private float physicalDMG = 0F;
    private float thunderDMG = 0F;
    private float iceDMG = 0F;
    private float fireDMG = 0F;

    public DamageCalculator(float allDMG, float physicalDMG, float thunderDMG, float iceDMG, float fireDMG){
        this.allDMG = allDMG;
        this.physicalDMG = physicalDMG;
        this.thunderDMG = thunderDMG;
        this.iceDMG = iceDMG;
        this.fireDMG = fireDMG;
    }

    public float getAllDMG(){
        return allDMG;
    }

    public float getPhysicalDMG(){
        return physicalDMG;
    }

    public float getThunderDMG(){
        return thunderDMG;
    }

    public float getIceDMG(){
        return iceDMG;
    }

    public float getFireDMG(){
        return fireDMG;
    }

}
