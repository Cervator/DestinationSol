package com.miloshpetrov.sol2.game.planet;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.JsonValue;
import com.miloshpetrov.sol2.TextureManager;
import com.miloshpetrov.sol2.files.HullConfigManager;
import com.miloshpetrov.sol2.game.GameColors;
import com.miloshpetrov.sol2.game.ShipConfig;
import com.miloshpetrov.sol2.game.item.ItemManager;
import com.miloshpetrov.sol2.game.item.TradeConfig;

import java.util.ArrayList;
import java.util.List;

public class PlanetConfig {
  public final String configName;
  public final float minGrav;
  public final float maxGrav;
  public final List<DecoConfig> deco;
  public final List<ShipConfig> groundEnemies;
  public final List<ShipConfig> highOrbitEnemies;
  public final PlanetTiles planetTiles;
  public final ShipConfig stationConfig;
  public final SkyConfig skyConfig;
  public final ArrayList<TextureAtlas.AtlasRegion> cloudTexs;
  public final ArrayList<ShipConfig> lowOrbitEnemies;
  public final int rowCount;
  public final boolean smoothLandscape;
  public final TradeConfig tradeConfig;
  public final boolean hardOnly;
  public final boolean easyOnly;

  public PlanetConfig(String configName, float minGrav, float maxGrav, List<DecoConfig> deco,
    List<ShipConfig> groundEnemies,
    List<ShipConfig> highOrbitEnemies, ArrayList<ShipConfig> lowOrbitEnemies,
    ArrayList<TextureAtlas.AtlasRegion> cloudTexs, PlanetTiles planetTiles,
    ShipConfig stationConfig, SkyConfig skyConfig, int rowCount, boolean smoothLandscape, TradeConfig tradeConfig,
    boolean hardOnly, boolean easyOnly)
  {
    this.configName = configName;
    this.minGrav = minGrav;
    this.maxGrav = maxGrav;
    this.deco = deco;
    this.groundEnemies = groundEnemies;
    this.highOrbitEnemies = highOrbitEnemies;
    this.lowOrbitEnemies = lowOrbitEnemies;
    this.cloudTexs = cloudTexs;
    this.planetTiles = planetTiles;
    this.stationConfig = stationConfig;
    this.skyConfig = skyConfig;
    this.rowCount = rowCount;
    this.smoothLandscape = smoothLandscape;
    this.tradeConfig = tradeConfig;
    this.hardOnly = hardOnly;
    this.easyOnly = easyOnly;
  }

  static PlanetConfig load(TextureManager textureManager, HullConfigManager hullConfigs, FileHandle configFile, JsonValue sh, GameColors cols,
    ItemManager itemManager) {
    float minGrav = sh.getFloat("minGrav");
    float maxGrav = sh.getFloat("maxGrav");
    List<DecoConfig> deco = DecoConfig.load(sh, textureManager, configFile);
    ArrayList<ShipConfig> groundEnemies = ShipConfig.loadList(sh.get("groundEnemies"), hullConfigs, itemManager);
    ArrayList<ShipConfig> highOrbitEnemies = ShipConfig.loadList(sh.get("highOrbitEnemies"), hullConfigs, itemManager);
    ArrayList<ShipConfig> lowOrbitEnemies = ShipConfig.loadList(sh.get("lowOrbitEnemies"), hullConfigs, itemManager);
    ShipConfig stationConfig = ShipConfig.load(hullConfigs, sh.get("station"), itemManager);
    String cloudPackName = sh.getString("cloudTexs");
    ArrayList<TextureAtlas.AtlasRegion> cloudTexs = textureManager.getPack(cloudPackName, configFile);
    String groundFolder = sh.getString("groundTexs");
    PlanetTiles planetTiles = new PlanetTiles(textureManager, groundFolder, configFile);
    SkyConfig skyConfig = SkyConfig.load(sh.get("sky"), cols);
    int rowCount = sh.getInt("rowCount");
    boolean smoothLandscape = sh.getBoolean("smoothLandscape", false);
    TradeConfig tradeConfig = TradeConfig.load(itemManager, sh.get("trading"), hullConfigs);
    boolean hardOnly = sh.getBoolean("hardOnly", false);
    boolean easyOnly = sh.getBoolean("easyOnly", false);
    return new PlanetConfig(sh.name, minGrav, maxGrav, deco, groundEnemies, highOrbitEnemies, lowOrbitEnemies, cloudTexs,
      planetTiles, stationConfig, skyConfig, rowCount, smoothLandscape, tradeConfig, hardOnly, easyOnly);
  }
}
