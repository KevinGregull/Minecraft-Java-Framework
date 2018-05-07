package com.tudortmund.pcg.templates;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import com.tudortmund.pcg.helpers.Build;
import com.tudortmund.pcg.helpers.Vector3;
import com.tudortmund.pcg.interfaces.Template;


public class Building implements Template
{

	public int sizeX=5;
	public int sizeY=11;
	public int fundamentHeight=3;
	
	@Override
	public void Process(Vector3 start)
	{
		Vector3 min=start.subtract(new Vector3(sizeX/2,0,sizeY/2));
		Vector3 max=start.add(new Vector3(sizeX/2,0,sizeY/2));
		
		System.out.println(start);
		System.out.println(min);
		System.out.println(max);
		
		BuildBase(min,max,fundamentHeight);
		BuildFloor(min.add(new Vector3(0,1,0)),max.add(new Vector3(0,1,0)),5);
		BuildFloor(min.add(new Vector3(-1,6,-1)),max.add(new Vector3(1,6,1)),5);
	}
	
	private void BuildFloor(Vector3 min, Vector3 max, int height)
	{
		Vector3 bl=new Vector3(min.getX(),min.getY(),min.getZ());
		Vector3 br=new Vector3(max.getX(),min.getY(),min.getZ());
		Vector3 tl=new Vector3(min.getX(),min.getY(),max.getZ());
		Vector3 tr=new Vector3(max.getX(),min.getY(),max.getZ());
		
		System.out.println("Floor:");
		System.out.println(min);
		System.out.println(max);
		
		Build.Outline(min,max,Material.LOG);
		Build.Outline(min.add(new Vector3(0,height,0)),max.add(new Vector3(0,height,0)),Material.LOG);
		
		
		List<Material> materialList=new ArrayList<>();
		materialList.add(Material.LOG);
		materialList.add(Material.WOOL);
		materialList.add(Material.WOOL);
		
		Build.CrawlingWallDual(bl.add(new Vector3(0,1,0)),br.add(new Vector3(0,height-1,0)),materialList);
		Build.CrawlingWallDual(br.add(new Vector3(0,1,0)),tr.add(new Vector3(0,height-1,0)),materialList);
		Build.CrawlingWallDual(tr.add(new Vector3(0,1,0)),tl.add(new Vector3(0,height-1,0)),materialList);
		Build.CrawlingWallDual(tl.add(new Vector3(0,1,0)),bl.add(new Vector3(0,height-1,0)),materialList);
	}
	
	private void BuildBase(Vector3 min, Vector3 max, int height)
	{
		System.out.println("Base:");
		Vector3 ground=min.subtract(new Vector3(0,fundamentHeight,0));
		System.out.println(ground);
		System.out.println(max);
		Build.Fill(ground,max,Material.COBBLESTONE);
	}
}
