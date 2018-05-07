package com.tudortmund.pcg;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.CharSet;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import com.tudortmund.pcg.generators.TestWorldGenerator;
import com.tudortmund.pcg.helpers.Bounds;
import org.bukkit.World;
import org.bukkit.WorldCreator;

public class MinecraftPCG extends JavaPlugin
{
	public void onDisable()
	{
		for (Player p: Bukkit.getServer().getOnlinePlayers())
		{
			p.teleport(Bukkit.getServer().getWorld("world").getSpawnLocation());
		}
		for (World w: Bukkit.getServer().getWorlds())
		{
			Bukkit.getServer().unloadWorld(w,true);
		}
	}
	
	public void onEnable()
	{
		// Load Worlds
		try
		{
			List<String> lines = Files.readAllLines(Paths.get("./INSTRUCTIONS.txt"),Charset.defaultCharset());
			for (String dataString : lines) 
			{
				String[] data=dataString.split(",");
				String worldName=data[0];
				String newWorld=data[1];
				int[] bounds = new int[6]; // x1, y1, z1, x2, y2, z2,
				bounds[0] =Integer.parseInt(data[2]);
				bounds[1] =Integer.parseInt(data[3]);
				bounds[2] =Integer.parseInt(data[4]);
				bounds[3] =Integer.parseInt(data[5]);
				bounds[4] =Integer.parseInt(data[6]);
				bounds[5] =Integer.parseInt(data[7]);
				Bounds b=new Bounds(bounds);

				// Delete old Generated World
				File oldWorldFile=new File("./"+worldName);
				File newWorldFile=new File("./"+newWorld);
				if (newWorldFile.exists())
				{
					FileUtils.deleteDirectory(newWorldFile);
					Thread.sleep(500);
				}
				FileUtils.copyDirectory(oldWorldFile, newWorldFile);
				
				// Create World in Server
				World currentWorld=Bukkit.getServer().createWorld(WorldCreator.name(newWorld));
				
				// Run the World Processor
				System.out.println("Loading Chunks in World "+newWorld+" ...");
				for (int x=-5;x<=5;x++)
				{
					for (int y=-5;y<=5;y++)
					{
						currentWorld.loadChunk(x, y, true);
					}
				}

				// TODO: Implement your own generator!
				System.out.println("Running PCG ...");
				TestWorldGenerator wg=new TestWorldGenerator();
				wg.SetBounds(b);
				wg.ProcessWorld(currentWorld);
			}
		}
		catch (Exception ex)
		{
			System.out.println("Could not load Instruction File...");
			ex.printStackTrace();
		}
		
		// For Batch running
		Bukkit.getServer().shutdown();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if (command.getName().equalsIgnoreCase("toworld"))
		{
			if (sender instanceof Player)
			{
				if (args.length==1)
				{
					this.TeleportToWorld((Player)sender,args[0]);
					return true;
				}
			}
		}
		return false;
	}
	
	private void TeleportToWorld(Player p,String worldName)
	{
		Location l=Bukkit.getServer().getWorld(worldName).getSpawnLocation();
		System.out.println("Teleporting Player to World "+worldName+"...");
		p.teleport(l);
		p.setGameMode(GameMode.SPECTATOR);
	}
	
}
