package com.tudortmund.pcg.generators;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import com.tudortmund.pcg.helpers.Bounds;
import com.tudortmund.pcg.helpers.Build;
import com.tudortmund.pcg.helpers.Vector3;
import com.tudortmund.pcg.templates.Building;


public class TestWorldGenerator extends WorldGenerator
{
	@Override
	public void ProcessWorld(World w)
	{		
		Bounds b=GetBounds();
		
		// Set Vectors to Current World
		Vector3.setCurrentWorld(w);
		
		
		Build.Line(new Vector3(0,60,0),new Vector3(100,120,100),Material.WOOD);

		System.out.println("Running Fill ...");
		Build.Fill(new Vector3(20,100,80),new Vector3(40,120,100),Material.DIRT);
		
		System.out.println("Running Test ...");
		
		
		Build.FillPatternXY(new Vector3(0,100,0),new Vector3(0,110,20),i->this.Pattern(i));
		
		Building build=new Building();
		build.Process(new Vector3(50,100,50));
		 
		/*
		for (int x=b.GetX1();x<b.GetX2();x++)
		{
			for (int y=b.GetZ1();y<b.GetY2();y++)
			{
				w.getBlockAt(x, 80, y).setType(Material.GLASS);
			}
		}*/
	}
	
	private Material Pattern(int c)
	{
		if (c%2==0)
		{
			return Material.WOOL;
		}
		else
		{
			return Material.LOG;
		}
	}
	
	
	
}
