package com.clashwars.cwcore.utils;

import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public final class VectorUtils {

	private VectorUtils() {
	}

	public static final Vector rotateAroundAxisX(Vector v, double angle) {
		double y, z, cos, sin;
		cos = Math.cos(angle);
		sin = Math.sin(angle);
		y = v.getY() * cos - v.getZ() * sin;
		z = v.getY() * sin + v.getZ() * cos;
		return v.setY(y).setZ(z);
	}

	public static final Vector rotateAroundAxisY(Vector v, double angle) {
		double x, z, cos, sin;
		cos = Math.cos(angle);
		sin = Math.sin(angle);
		x = v.getX() * cos + v.getZ() * sin;
		z = v.getX() * -sin + v.getZ() * cos;
		return v.setX(x).setZ(z);
	}

	public static final Vector rotateAroundAxisZ(Vector v, double angle) {
		double x, y, cos, sin;
		cos = Math.cos(angle);
		sin = Math.sin(angle);
		x = v.getX() * cos - v.getY() * sin;
		y = v.getX() * sin + v.getY() * cos;
		return v.setX(x).setY(y);
	}

	public static final Vector rotateVector(Vector v, double angleX, double angleY, double angleZ) {
		// double x = v.getX(), y = v.getY(), z = v.getZ();
		// double cosX = Math.cos(angleX), sinX = Math.sin(angleX), cosY =
		// Math.cos(angleY), sinY = Math.sin(angleY), cosZ = Math.cos(angleZ),
		// sinZ = Math.sin(angleZ);
		// double nx, ny, nz;
		// nx = (x * cosY + z * sinY) * (x * cosZ - y * sinZ);
		// ny = (y * cosX - z * sinX) * (x * sinZ + y * cosZ);
		// nz = (y * sinX + z * cosX) * (-x * sinY + z * cosY);
		// return v.setX(nx).setY(ny).setZ(nz);
		// Having some strange behavior up there.. Have to look in it later. TODO
		rotateAroundAxisX(v, angleX);
		rotateAroundAxisY(v, angleY);
		rotateAroundAxisZ(v, angleZ);
		return v;
	}

	public static final double angleToXAxis(Vector vector) {
		return Math.atan2(vector.getX(), vector.getY());
	}


	/** @param startPos
	 *            starting position
	 * @param radius
	 *            distance cone travels
	 * @param degrees
	 *            angle of cone
	 * @param direction
	 *            direction of the cone
	 * @return All block positions inside the cone */
	public static List<Vector> getPositionsInCone(Vector startPos, float radius, float degrees, Vector direction, boolean filterByDistance) {

		List<Vector> positions = new ArrayList<Vector>();        //    Returned list
		float squaredRadius = radius * radius;                     //    We don't want to use square root

		for (float x=startPos.getBlockX()-radius; x<startPos.getBlockX()+radius; x++)
			for (float y=startPos.getBlockY()-radius; y<startPos.getBlockY()+radius; y++)
				for (float z=startPos.getBlockZ()-radius; z<startPos.getBlockZ()+radius; z++) {
					Vector relative = new Vector(x,y,z);
					relative.subtract(startPos);
					if (relative.lengthSquared() > squaredRadius) continue;            //    First check : distance
					if (getAngleBetweenVectors(direction, relative) > degrees) continue;    //    Second check : angle

					Vector v = new Vector(x,y,z);
					double distance = v.distance(startPos);
					if (filterByDistance) {
						int i = 0;
						for (Vector vl : positions) {
							if (distance < vl.distance(startPos)) {
								positions.add(i, v);
							}
							i++;
						}
					} else {
						positions.add(v);
					}
				}
		return positions;
	}


	public static float getAngleBetweenVectors(Vector v1, Vector v2) {
		return Math.abs((float) Math.toDegrees(v1.angle(v2)));
	}

}
