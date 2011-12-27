package org.dyndns.pamelloes.SpoutCasino.block;

import org.bukkit.plugin.Plugin;
import org.getspout.spoutapi.block.design.GenericBlockDesign;
import org.getspout.spoutapi.block.design.Quad;
import org.getspout.spoutapi.block.design.Texture;

public class TableBlockDesign extends GenericBlockDesign {
	
	public TableBlockDesign(Plugin plugin, Texture texture) {
		
		setBoundingBox(0, 0, 0, 1, 0.75f, 1);

		setQuadNumber(6);

		setMinBrightness(0.0F).setMaxBrightness(1.0F).setTexture(plugin, texture);

		Quad bottom = new Quad(0, texture.getSubTexture(3));
		bottom.addVertex(0, 0, 0, 0);
		bottom.addVertex(1, 1, 0, 0);
		bottom.addVertex(2, 1, 0, 1);
		bottom.addVertex(3, 0, 0, 1);
		setLightSource(0, 0, -1, 0);

		Quad face1 = new Quad(1, texture.getSubTexture(2));
		face1.addVertex(0, 0, 0, 0);
		face1.addVertex(1, 0, 1, 0);
		face1.addVertex(2, 1, 1, 0);
		face1.addVertex(3, 1, 0, 0);
		setLightSource(1, 0, 0, -1);

		Quad face2 = new Quad(2, texture.getSubTexture(2));
		face2.addVertex(0, 1, 0, 0);
		face2.addVertex(1, 1, 1, 0);
		face2.addVertex(2, 1, 1, 1);
		face2.addVertex(3, 1, 0, 1);
		setLightSource(2, 1, 0, 0);

		Quad face3 = new Quad(3, texture.getSubTexture(2));
		face3.addVertex(0, 1, 0, 1);
		face3.addVertex(1, 1, 1, 1);
		face3.addVertex(2, 0, 1, 1);
		face3.addVertex(3, 0, 0, 1);
		setLightSource(3, 0, 0, 1);

		Quad face4 = new Quad(4, texture.getSubTexture(2));
		face4.addVertex(0, 0, 0, 1);
		face4.addVertex(1, 0, 1, 1);
		face4.addVertex(2, 0, 1, 0);
		face4.addVertex(3, 0, 0, 0);
		setLightSource(4, -1, 0, 0);

		Quad top = new Quad(5, texture.getSubTexture(0));
		top.addVertex(0, 0, 0.75f, 0);
		top.addVertex(1, 0, 0.75f, 1);
		top.addVertex(2, 1, 0.75f, 1);
		top.addVertex(3, 1, 0.75f, 0);
		setLightSource(5, 0, 1, 0);

		setQuad(bottom).setQuad(face1).setQuad(face2).setQuad(face3).setQuad(face4).setQuad(top);
	}
}
