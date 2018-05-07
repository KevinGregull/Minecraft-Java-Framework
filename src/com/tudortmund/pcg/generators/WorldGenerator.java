package com.tudortmund.pcg.generators;

import org.bukkit.World;
import com.tudortmund.pcg.helpers.Bounds;

abstract public class WorldGenerator
{
	private Bounds bounds;
	public void SetBounds(Bounds b)
	{
		this.bounds=b;
	}
	
	public Bounds GetBounds()
	{
		return this.bounds;
	}
	
	abstract public void ProcessWorld(World w);
}
