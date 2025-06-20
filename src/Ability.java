public class Ability {

    private final String abilityName;
    private int minDamage;
    private int maxDamage;
    private double experience;
    private double experienceRequired;
    private int level;
    private final String statusInflicted;
    private final int cooldown;
    private int currentCooldown;

    //Constructor
    public Ability(String abilityName, int minDamage, int maxDamage, double experienceRequired,
                   String statusInflicted, int cooldown) {
        this.abilityName = abilityName;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.experience = 0;
        this.experienceRequired = experienceRequired;
        this.level = 1;
        this.statusInflicted = statusInflicted;
        this.cooldown = cooldown;
        this.currentCooldown = 0;
    }

    public int getRandomDamage() {
        return (int) (Math.random() * (maxDamage - minDamage + 1)) + minDamage;
    }

    public void gainExperience(double amount) {
        experience += amount;
        if (experience >= experienceRequired) {
            levelUp();
        }
    }

    private void levelUp() {
        level++;
        minDamage += 1;
        maxDamage += 1;
        experience = 0;
        experienceRequired *= 1.5;
        System.out.println(abilityName + " leveled up to level " + level + "!");
    }

    public boolean isReady() {
        return currentCooldown == 0;
    }

    public void use() {
        if (!isReady()) {
            System.out.println(abilityName + " is still on cooldown for " + currentCooldown + " more turns.");
            return;
        }
        // Calculate base cooldown scaled by ability “power” (higher maxDamage → longer CD)
        int powerScale = Math.max(1, maxDamage / Math.max(1, minDamage));
        int baseCd = cooldown * powerScale;
        if (DifficultyManager.getDifficulty().useLongerCooldown()) {
            currentCooldown = baseCd * 2;
        } else {
            currentCooldown = baseCd;
        }
    }

    public void tickCooldown() {
        if (currentCooldown > 0) {
            currentCooldown--;
        }
    }

    // Reset cooldown to 0 (used when starting a new stage)
    public void resetCooldown() {
        this.currentCooldown = 0;
    }

    public String getAbilityName() {
        return abilityName;
    }

    public int getMinDamage() {
        return minDamage;
    }

    public int getMaxDamage() {
        return maxDamage;
    }

    public double getExperience() {
        return experience;
    }

    public double getExperienceRequired() {
        return experienceRequired;
    }

    public int getLevel() {
        return level;
    }

    public String getStatusInflicted() {
        return statusInflicted;
    }

    public int getCooldown() {
        return cooldown;
    }

    public int getCurrentCooldown() {
        return currentCooldown;
    }

    // Increase both min and max damage
    public void buffDamage(int amount) {
        this.minDamage += amount;
        this.maxDamage += amount;
    }
}