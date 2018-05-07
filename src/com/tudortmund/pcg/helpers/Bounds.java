package com.tudortmund.pcg.helpers;

public class Bounds
{
	private int[] bounds = new int[6];
	public Bounds(int[] bounds)
	{
		this.bounds=bounds;
	}
	
	public int GetX1()
	{
		return bounds[0];
	}
	
	public int GetY1()
	{
		return bounds[1];
	}
	
	public int GetZ1()
	{
		return bounds[2];
	}
	
	public int GetX2()
	{
		return bounds[3];
	}
	
	public int GetY2()
	{
		return bounds[4];
	}
	
	public int GetZ2()
	{
		return bounds[5];
	}
}
