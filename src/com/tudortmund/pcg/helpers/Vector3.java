package com.tudortmund.pcg.helpers;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;

public class Vector3 extends Location
{
	private static World currentWorld;
	public static void setCurrentWorld(World cur)
	{
		currentWorld=cur;
	}
	public static World getCurrentWorld()
	{
		return currentWorld;
	}

	
	public Vector3(double x,double y,double z)
	{
		super(Vector3.getCurrentWorld(),x,y,z);
	}
	public Vector3(World w,double x,double y,double z)
	{
		super(w,x,y,z);
	}
	public Vector3()
	{
		super(Vector3.getCurrentWorld(),0,0,0);
	}
	public Vector3(Location loc)
	{
		super(loc.getWorld(),loc.getX(),loc.getY(),loc.getZ());
	}
	
	public Vector3 normalize()
	{
		return divide(magnitude());
	}
	
	public Vector3 multiply(double amount)
	{
		Vector3 result=new Vector3();
		result.setX(this.getX()*amount);
		result.setY(this.getY()*amount);
		result.setZ(this.getZ()*amount);
		return result;
	}
	
	public Vector3 divide(double amount)
	{
		return multiply(1/amount);
	}

	public Vector3 add(Vector3 other)
	{
		Vector3 result=new Vector3();
		result.setX(this.getX()+other.getX());
		result.setY(this.getY()+other.getY());
		result.setZ(this.getZ()+other.getZ());
		return result;
	}
	
	public Vector3 subtract(Vector3 other)
	{
		Vector3 result=new Vector3();
		result.setX(this.getX()-other.getX());
		result.setY(this.getY()-other.getY());
		result.setZ(this.getZ()-other.getZ());
		return result;
	}
	
	public Vector3 min(Vector3 other)
	{
		Vector3 result=new Vector3();
		result.setX(Math.min(this.getX(),other.getX()));
		result.setY(Math.min(this.getY(),other.getY()));
		result.setZ(Math.min(this.getZ(),other.getZ()));
		return result;
	}
	
	public Vector3 max(Vector3 other)
	{
		Vector3 result=new Vector3();
		result.setX(Math.max(this.getX(),other.getX()));
		result.setY(Math.max(this.getY(),other.getY()));
		result.setZ(Math.max(this.getZ(),other.getZ()));
		return result;
	}
	
	public BlockFace getPlanarDirection(Vector3 other)
	{
		if (this.getX()==other.getX())
		{
			if (this.getZ()<other.getZ())
			{
				return BlockFace.SOUTH;
			}
			else
			{
				return BlockFace.NORTH;
			}
		}
		
		if (this.getZ()==other.getZ())
		{
			if (this.getX()<other.getX())
			{
				return BlockFace.EAST;
			}
			else
			{
				return BlockFace.WEST;
			}
		}
		
		if (this.getX()<other.getX())
		{
			// EAST
			if (this.getZ()<other.getZ())
			{
				return BlockFace.SOUTH_EAST;
			}
			else
			{
				return BlockFace.NORTH_EAST;
			}
		}
		else
		{
			// WEST
			if (this.getZ()<other.getZ())
			{
				return BlockFace.SOUTH_WEST;
			}
			else
			{
				return BlockFace.NORTH_WEST;
			}
		}
	}
	
	public Vector3 nextVectorOnPlane(Vector3 other)
	{
		System.out.println("Vector:");
		System.out.println(this);
		System.out.println(other);
		System.out.println(this.getPlanarDirection(other));
		Vector3 result=new Vector3(getBlock().getRelative(getPlanarDirection(other)).getLocation());
		System.out.println(result); 
		return result;
	}
	
	public double magnitude()
	{
		return Math.sqrt(Math.pow(this.getX(),2)+Math.pow(this.getY(),2)+Math.pow(this.getZ(),2));
	}
	
	public double distance(Vector3 other)
	{
		return Math.sqrt(Math.pow((this.getX()-other.getX()),2)+Math.pow((this.getY()-other.getY()),2)+Math.pow((this.getZ()-other.getZ()),2));
	}
	
	public String toString()
	{
		return "["+this.getX()+","+this.getY()+","+this.getZ()+"]";
	}
}
