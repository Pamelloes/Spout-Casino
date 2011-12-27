package org.dyndns.pamelloes.SpoutCasino;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.dyndns.pamelloes.SpoutCasino.block.TableBlock;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.block.design.Texture;
import org.getspout.spoutapi.material.CustomBlock;
import org.getspout.spoutapi.plugin.SpoutPlugin;

public class SpoutCasino extends SpoutPlugin {

	public static CustomBlock cardtable;
	
	public void onEnable() {
		extractFile("cardtableoutput22.png",true);
		Texture tex = new Texture(this, "plugins/SpoutCasino/cardtableoutput22.png", 128, 128, 64);
		cardtable = new TableBlock(this, tex);
		
		/*SpoutItemStack result = new SpoutItemStack(cardtable, 1);
		SpoutShapedRecipe rec = new SpoutShapedRecipe(result);
		rec.shape("AAA","BAB","BBB");
		rec.setIngredient('A', MaterialData.greenWool);
		rec.setIngredient('B', MaterialData.wood);
		SpoutManager.getMaterialManager().registerSpoutRecipe(rec);*/
	}
	
	public void onDisable() {
		// TODO Auto-generated method stub

	}
	
	
	/**
	 * Extract files from the plugin jar and optionally cache them on the client.
	 * @param regex a pattern of files to extract
	 * @param cache if any files found should be added to the Spout cache
	 * @return if any files were extracted
	 */
	public boolean extractFile(String regex, boolean cache) {
		boolean found = false;
		try {
			JarFile jar = new JarFile(getFile());
			for (Enumeration<JarEntry> entries = jar.entries(); entries.hasMoreElements();) {
				JarEntry entry = (JarEntry) entries.nextElement();
				String name = entry.getName();
				if (name.matches(regex)) {
					if (!getDataFolder().exists()) {
						getDataFolder().mkdir();
					}
					try {
						File file = new File(getDataFolder(), name);
						if (!file.exists()) {
							InputStream is = jar.getInputStream(entry);
							FileOutputStream fos = new FileOutputStream(file);
							while (is.available() > 0) {
								fos.write(is.read());
							}
							fos.close();
							is.close();
							found = true;
						}
						if (cache && name.matches(".*\\.(txt|yml|xml|png|jpg|ogg|midi|wav|zip)$")) {
							SpoutManager.getFileManager().addToPreLoginCache(this, file);
						}
					} catch (Exception e) {
					}
				}
			}
		} catch (Exception e) {
		}
		return found;
	}
}
