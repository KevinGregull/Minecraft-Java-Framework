package com.tudortmund.pcg.helpers;

import java.util.List;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.Predicate;
import org.bukkit.Location;
import org.bukkit.Material;
import com.tudortmund.pcg.interfaces.Pattern;

public class Build
{
	public static void Line(Vector3 from,Vector3 to,Material mat)
	{
		Vector3 dir=to.subtract(from).normalize();
		double dist=from.distance(to);
		
		for (int i=0;i<dist;i++)
		{
			Vector3 loc=from.add(dir.multiply(i));
			loc.getBlock().setType(mat);
		}
	}
	
	public static void Fill(Vector3 from,Vector3 to,Material mat)
	{
		Vector3 min=from.min(to);
		Vector3 max=from.max(to); 
		
		for (int x=min.getBlockX();x<=max.getBlockX();x++) 
		{
			for (int y=min.getBlockY();y<=max.getBlockY();y++)
			{
				for (int z=min.getBlockZ();z<=max.getBlockZ();z++)
				{
					from.getWorld().getBlockAt(x,y,z).setType(mat);
				}
			}
		}
	}
	
	public static void FillAround(Vector3 center,int sizeX,int sizeY,int sizeZ,Material mat)
	{
		for (int x=center.getBlockX()-sizeX;x<=center.getBlockX()+sizeX;x++)
		{
			for (int y=center.getBlockY()-sizeY;y<=center.getBlockY()+sizeY;y++)
			{
				for (int z=center.getBlockZ()-sizeZ;z<=center.getBlockZ()+sizeZ;z++)
				{
					center.getWorld().getBlockAt(x,y,z).setType(mat);
				}
			}
		}
	}
	
	public static void Outline(Vector3 from, Vector3 to,Material mat)
	{
		Vector3 min=from.min(to);
		Vector3 max=from.max(to); 
		
		for (int x=min.getBlockX();x<=max.getBlockX();x++) 
		{
			for (int y=min.getBlockY();y<=max.getBlockY();y++)
			{
				for (int z=min.getBlockZ();z<=max.getBlockZ();z++)
				{
					int cornerHits=0;
					if (x==from.getBlockX() || x==to.getBlockX())
						cornerHits++;
					if (y==from.getBlockY() || y==to.getBlockY())
						cornerHits++;
					if (z==from.getBlockZ() || z==to.getBlockZ())
						cornerHits++;
							
					if (cornerHits>=2)
					{
						from.getWorld().getBlockAt(x,y,z).setType(mat);
					}
				}
			}
		}
	}
	
	public static void FillPatternXY(Vector3 from,Vector3 to,Pattern<Integer> f)
	{
		Vector3 min=from.min(to);
		Vector3 max=from.max(to); 
		
		int count=0;
		for (int x=min.getBlockX();x<=max.getBlockX();x++) 
		{
			for (int z=min.getBlockZ();z<=max.getBlockZ();z++)
			{
				count++;
				for (int y=min.getBlockY();y<=max.getBlockY();y++)
				{
					from.getWorld().getBlockAt(x,y,z).setType(f.accept(count));
				}
			}
		}
	}
	
	public static void CrawlingWall(Vector3 from,Vector3 to,List<Material> mats)
	{
		Vector3 min=from.min(to);
		Vector3 max=from.max(to); 
		
		int matCount=0;
		for (int x=min.getBlockX();x<=max.getBlockX();x++) 
		{
			for (int z=min.getBlockZ();z<=max.getBlockZ();z++)
			{
				Build.Line(new Vector3(x,min.getY(),z),new Vector3(x,max.getY(),z),mats.get(matCount));
				matCount++;
				if (matCount>=mats.size())
				{
					matCount=0;
				}
			}
		}
	}
	
	public static void CrawlingWallDual(Vector3 from,Vector3 to,List<Material> mats,int c)
	{
		Build.Fill(from,new Vector3(from.getX(),to.getY(),from.getZ()),mats.get(c));
		
		// If currently on Same Block abort
		if (from.getX()==to.getX() && from.getZ()==to.getZ())
		{
			return;
		}
		
		Build.Fill(to,new Vector3(to.getX(),from.getY(),to.getZ()),mats.get(c));
		c++;
		if (c>=mats.size())
		{
			c=0;
		}
		
		// If next Block is same Block abort
		if (from.nextVectorOnPlane(to).getX()==to.getX() && from.nextVectorOnPlane(to).getZ()==to.getZ())
		{
			return;
		}
		CrawlingWallDual(from.nextVectorOnPlane(to),to.nextVectorOnPlane(from),mats,c);
	}
	
	public static void CrawlingWallDual(Vector3 from,Vector3 to,List<Material> mats)
	{
		CrawlingWallDual(from,to,mats,0);
	}
	
}
