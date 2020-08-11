package com.advancejwars.Entities;


import java.util.HashMap;

@SuppressWarnings({"WeakerAccess", "unused"})
public class UnitStats {

  /**
   * Identifier for unit
   */
  public final String type;
  /**
   * How many tiles of vision range the unit has
   */
  public final int visionRange;
  /** Total health for unit */
  public final int maxHealth;
  /** Base attack points */
  public final int attack;
  /** Base tiles of movement range */
  public final int movement;
  /**
   * Base tiles of movement range
   */
  public final int minAttackRange;
  /**
   * Base tiles of movement range
   */
  public final int maxAttackRange;

  public UnitStats(
      String type,
      int visionRange,
      int maxHealth,
      int attack,
      int movement,
      int minAttackRange,
      int maxAttackRange) {
    this.type = type;
    this.visionRange = visionRange;
    this.maxHealth = maxHealth;
    this.attack = attack;
    this.movement = movement;
    this.minAttackRange = minAttackRange;
    this.maxAttackRange = maxAttackRange;
  }
}
