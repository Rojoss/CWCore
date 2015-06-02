package com.clashwars.cwcore.helpers;

import net.minecraft.server.v1_8_R2.NBTTagCompound;
import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftEntity;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.material.Colorable;
import org.bukkit.potion.PotionEffect;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.List;

public class CWEntity {

    private Entity entity;

    public CWEntity(Entity entity) {
        this.entity = entity;
    }

    public static CWEntity create(EntityType type, Location location) throws IllegalArgumentException {
        if (type.isSpawnable()) {
            return new CWEntity(location.getWorld().spawnEntity(location, type));
        } else {
            return new CWEntity(location.getWorld().spawn(location, type.getEntityClass()));
        }
    }

    public Entity entity() {
        return entity;
    }

    public CWEntity setSize(int size) {
        if (entity instanceof Slime) {
            ((Slime)entity).setSize(size);
        }
        return this;
    }

    public CWEntity setJob(Villager.Profession profession) {
        if (entity instanceof Villager) {
            ((Villager)entity).setProfession(profession);
        }
        return this;
    }

    public CWEntity setDyeColor(DyeColor color) {
        if (entity instanceof Colorable) {
            ((Sheep)entity).setColor(color);
        }
        if (entity instanceof Wolf) {
            ((Wolf)entity).setCollarColor(color);
        }
        return this;
    }

    public CWEntity setHorseColor(Horse.Color color) {
        if (entity instanceof Horse) {
            ((Horse)entity).setColor(color);
        }
        return this;
    }

    public CWEntity setJumpPower(double power) {
        if (entity instanceof Horse) {
            ((Horse)entity).setJumpStrength(power);
        }
        return this;
    }

    public CWEntity setStyle(Horse.Style style) {
        if (entity instanceof Horse) {
            ((Horse)entity).setStyle(style);
        }
        return this;
    }

    public CWEntity setVariant(Horse.Variant variant) {
        if (entity instanceof Horse) {
           ((Horse)entity).setVariant(variant);
        }
        return this;
    }

    public CWEntity setHorseArmor(ItemStack item) {
        if (entity instanceof Horse) {
            ((Horse)entity).getInventory().setArmor(item);
        }
        return this;
    }

    public CWEntity setHand(ItemStack item) {
        if (entity instanceof LivingEntity) {
            ((LivingEntity)entity).getEquipment().setItemInHand(item);
        }
        if (entity instanceof Enderman) {
            ((Enderman)entity).setCarriedMaterial(item.getData());
        }
        if (entity instanceof ArmorStand) {
            ((ArmorStand)entity).setItemInHand(item);
        }
        return this;
    }

    public CWEntity setHelmet(ItemStack item) {
        if (entity instanceof LivingEntity) {
            ((LivingEntity)entity).getEquipment().setHelmet(item);
        }
        if (entity instanceof ArmorStand) {
            ((ArmorStand)entity).setHelmet(item);
        }
        return this;
    }

    public CWEntity setChest(ItemStack item) {
        if (entity instanceof LivingEntity) {
            ((LivingEntity)entity).getEquipment().setChestplate(item);
        }
        if (entity instanceof ArmorStand) {
            ((ArmorStand)entity).getEquipment().setChestplate(item);
        }
        return this;
    }

    public CWEntity setLeggings(ItemStack item) {
        if (entity instanceof LivingEntity) {
            ((LivingEntity)entity).getEquipment().setLeggings(item);
        }
        if (entity instanceof ArmorStand) {
            ((ArmorStand)entity).getEquipment().setLeggings(item);
        }
        return this;
    }

    public CWEntity setBoots(ItemStack item) {
        if (entity instanceof LivingEntity) {
            ((LivingEntity)entity).getEquipment().setBoots(item);
        }
        if (entity instanceof ArmorStand) {
            ((ArmorStand)entity).getEquipment().setBoots(item);
        }
        return this;
    }

    public CWEntity clearEquipment() {
        if (entity instanceof LivingEntity) {
            ((LivingEntity) entity).getEquipment().clear();
        }
        if (entity instanceof ArmorStand) {
            ((ArmorStand) entity).getEquipment().clear();
        }
        return this;
    }

    public CWEntity setEffects(PotionEffect[] effects) {
        return setEffects(Arrays.asList(effects));
    }

    public CWEntity setEffects(List<PotionEffect> effects) {
        if (entity instanceof LivingEntity && effects != null) {
            for (PotionEffect effect : effects) {
                ((LivingEntity) entity).addPotionEffect(effect, true);
            }
        }
        return this;
    }

    public CWEntity setBaby(boolean baby) {
        if (entity instanceof Ageable) {
            if (baby) {
                ((Ageable)entity).setBaby();
            } else {
                ((Ageable)entity).setAdult();
            }
        }
        if (entity instanceof Zombie) {
            ((Zombie)entity).setBaby(baby);
        }
        if (entity instanceof PigZombie) {
            ((PigZombie)entity).setBaby(baby);
        }
        return this;
    }

    public CWEntity setAge(int age) {
        if (entity instanceof Ageable) {
            ((Ageable)entity).setAge(age);
        }
        return this;
    }

    public CWEntity lockAge(boolean lock) {
        if (entity instanceof  Ageable) {
            ((Ageable)entity).setAgeLock(lock);
        }
        return this;
    }

    public CWEntity setBreed(boolean breed) {
        if (entity instanceof Ageable) {
            ((Ageable)entity).setBreed(breed);
        }
        return this;
    }

    public CWEntity setTamed(boolean tamed, Player owner) {
        if (entity instanceof Tameable) {
            ((Tameable)entity).setTamed(tamed);
            if (owner != null) {
                ((Tameable)entity).setOwner(owner);
            }
        }
        return this;
    }

    public CWEntity setAngry(boolean angry) {
        if (entity instanceof Wolf) {
            ((Wolf)entity).setAngry(angry);
        }
        if (entity instanceof PigZombie) {
            ((PigZombie)entity).setAngry(angry);
        }
        return this;
    }

    public CWEntity setPowered(boolean powered) {
        if (entity instanceof Creeper) {
            ((Creeper)entity).setPowered(powered);
        }
        if (entity instanceof WitherSkull) {
            ((WitherSkull)entity).setCharged(powered);
        }
        return this;
    }

    public CWEntity setSaddle(boolean saddle) {
        if (entity instanceof Pig) {
            ((Pig)entity).setSaddle(saddle);
        }
        if (entity instanceof Horse) {
            if (saddle) {
                ((Horse)entity).getInventory().setSaddle(new ItemStack(Material.SADDLE, 1));
            } else {
                ((Horse)entity).getInventory().setSaddle(new ItemStack(Material.AIR));
            }

        }
        return this;
    }

    public CWEntity setZombieVillager(boolean villager) {
        if (entity instanceof Zombie) {
            ((Zombie)entity).setVillager(villager);
        }
        return this;
    }

    public CWEntity setWitherSkeleton(boolean wither) {
        if (entity instanceof Skeleton) {
            if (wither) {
                ((Skeleton) entity).setSkeletonType(Skeleton.SkeletonType.WITHER);
            } else {
                ((Skeleton) entity).setSkeletonType(Skeleton.SkeletonType.NORMAL);
            }
        }
        return this;
    }

    public CWEntity setAwake(boolean awake) {
        if (entity instanceof Bat) {
            ((Bat)entity).setAwake(awake);
        }
        return this;
    }

    public CWEntity setCatType(Ocelot.Type type) {
        if (entity instanceof Ocelot) {
            ((Ocelot)entity).setCatType(type);
        }
        return this;
    }

    public CWEntity setSitting(boolean sit) {
        if (entity instanceof Ocelot) {
            ((Ocelot)entity).setSitting(sit);
        }
        if (entity instanceof Wolf) {
            ((Wolf)entity).setSitting(sit);
        }
        return this;
    }

    public CWEntity setSheared(boolean sheared) {
        if (entity instanceof Sheep) {
            ((Sheep)entity).setSheared(sheared);
        }
        return this;
    }


    public CWEntity slowCartWhenEmpty(boolean slow) {
        if (entity instanceof Minecart) {
            ((Minecart)entity).setSlowWhenEmpty(slow);
        }
        return this;
    }

    public CWEntity setMaxSpeed(double speed) {
        if (entity instanceof Minecart) {
            ((Minecart)entity).setMaxSpeed(speed);
        }
        if (entity instanceof Boat) {
            ((Boat)entity).setMaxSpeed(speed);
        }
        return this;
    }

    public CWEntity setDerailedVelocity(Vector velocity) {
        if (entity instanceof Minecart) {
            ((Minecart)entity).setDerailedVelocityMod(velocity);
        }
        return this;
    }

    public CWEntity setArrowKnockback(int knockback) {
        if (entity instanceof Arrow) {
            ((Arrow)entity).setKnockbackStrength(knockback);
        }
        return this;
    }

    public CWEntity setCritical(boolean crit) {
        if (entity instanceof Arrow) {
            ((Arrow)entity).setCritical(crit);
        }
        return this;
    }

    public CWEntity setExperience(int exp) {
        if (entity instanceof ExperienceOrb) {
            ((ExperienceOrb)entity).setExperience(exp);
        }
        return this;
    }

    public CWEntity setYield(float yield) {
        if (entity instanceof Explosive) {
            ((Explosive)entity).setYield(yield);
        }
        return this;
    }

    public CWEntity setExplosionFire(boolean fire) {
        if (entity instanceof Explosive) {
            ((Explosive)entity).setIsIncendiary(fire);
        }
        return this;
    }

    public CWEntity setDirection(Vector direction) {
        if (entity instanceof Fireball) {
            ((Fireball)entity).setDirection(direction);
        }
        return this;
    }

    public CWEntity detonateFirework() {
        if (entity instanceof Firework) {
            ((Firework)entity).detonate();
        }
        return this;
    }

    public CWEntity setFireworkMeta(FireworkMeta meta) {
        if (entity instanceof Firework) {
            ((Firework)entity).setFireworkMeta(meta);
        }
        return this;
    }

    public CWEntity setDirection(BlockFace face, boolean force) {
        if (entity instanceof Hanging) {
            ((Hanging)entity).setFacingDirection(face, force);
        }
        return this;
    }

    public CWEntity setRotation(Rotation rotation) {
        if (entity instanceof ItemFrame) {
            ((ItemFrame)entity).setRotation(rotation);
        }
        return this;
    }

    public CWEntity setArt(Art art, boolean force) {
        if (entity instanceof Painting) {
            ((Painting)entity).setArt(art, force);
        }
        return this;
    }

    public CWEntity setItem(ItemStack stack) {
        if (entity instanceof Item) {
            ((Item)entity).setItemStack(stack);
        }
        if (entity instanceof ItemFrame) {
            ((ItemFrame)entity).setItem(stack);
        }
        if (entity instanceof ThrownPotion) {
            ((ThrownPotion)entity).setItem(stack);
        }
        return this;
    }

    public CWEntity setPickupDelay(int delay) {
        if (entity instanceof Item) {
            ((Item)entity).setPickupDelay(delay);
        }
        return this;
    }

    public CWEntity setShooter(ProjectileSource shooter) {
        if (entity instanceof Projectile) {
            ((Projectile)entity).setShooter(shooter);
        }
        return this;
    }

    public CWEntity setBounce(boolean bounce) {
        if (entity instanceof Projectile) {
            ((Projectile)entity).setBounce(bounce);
        }
        return this;
    }

    public CWEntity setFuseTicks(int ticks) {
        if (entity instanceof TNTPrimed) {
            ((TNTPrimed)entity).setFuseTicks(ticks);
        }
        return this;
    }

    public CWEntity setVelocity(Vector velocity) {
        if (entity instanceof Vehicle) {
            ((Vehicle)entity).setVelocity(velocity);
            return this;
        }
        entity.setVelocity(velocity);
        return this;
    }

    public CWEntity setPose(PoseType type, Vector rotation) {
        return setPose(type, new EulerAngle(rotation.getX(), rotation.getY(), rotation.getZ()));
    }

    public CWEntity setPose(PoseType type, EulerAngle rotation) {
        if (entity instanceof ArmorStand) {
            ArmorStand stand = (ArmorStand)entity;
            if (type == PoseType.HEAD) {
                stand.setHeadPose(rotation);
            } else if (type == PoseType.BODY) {
                stand.setBodyPose(rotation);
            } else if (type == PoseType.LEFT_ARM) {
                stand.setLeftArmPose(rotation);
            } else if (type == PoseType.RIGHT_ARM) {
                stand.setRightArmPose(rotation);
            } else if (type == PoseType.LEFT_LEG) {
                stand.setLeftLegPose(rotation);
            } else if (type == PoseType.RIGHT_LEG) {
                stand.setRightLegPose(rotation);
            }
        }
        return this;
    }

    public CWEntity setArmorstandVisibility(boolean visible) {
        if (entity instanceof ArmorStand) {
            ((ArmorStand)entity).setVisible(visible);
        }
        return this;
    }

    public CWEntity setArmorstandArms(boolean arms) {
        if (entity instanceof ArmorStand) {
            ((ArmorStand)entity).setArms(arms);
        }
        return this;
    }

    public CWEntity setArmorstandPlate(boolean plate) {
        if (entity instanceof ArmorStand) {
            ((ArmorStand)entity).setBasePlate(plate);
        }
        return this;
    }

    public CWEntity setArmorstandGravity(boolean gravity) {
        if (entity instanceof ArmorStand) {
            ((ArmorStand)entity).setGravity(gravity);
        }
        return this;
    }

    public CWEntity setAI(boolean AI) {
        net.minecraft.server.v1_8_R2.Entity nmsEntity = ((CraftEntity)entity()).getHandle();
        NBTTagCompound tag = nmsEntity.getNBTTag();
        if (tag == null) {
            tag = new NBTTagCompound();
        }
        nmsEntity.c(tag);
        tag.setInt("NoAI", 1);
        nmsEntity.f(tag);
        return this;
    }


    //TODO: NMS stuff for pathfinding etc.
}
