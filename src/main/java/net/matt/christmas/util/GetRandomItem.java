package net.matt.christmas.util;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.*;

public class GetRandomItem<T> {

    private Map<T, Double> chanceMap;
    private World world;

    public GetRandomItem(Map<T, Double> chanceMap, World world)
    {
        this.world = world;
        this.chanceMap = chanceMap;
    }

    public T getRandomDrop()
    {
        List<T> items = generateDrops(chanceMap);

        if (items.isEmpty()) {
            throw new IllegalStateException("No items available to drop!");
        }

        Random random = world.getRandom();
        int item = MathHelper.nextInt(random, 0, items.size() - 1);
        return items.get(item);
    }

    private List<T> generateDrops(Map<T, Double> map)
    {
        double totalProbability = 0.0;
        for(double val : map.values())
        {
            totalProbability += val;
        }

        // scales all the probabilities
        Map<T, Integer> scaledProbs = new HashMap<>();
        for (Map.Entry<T, Double> item : map.entrySet())
        {
            T key = item.getKey();
            double probability = item.getValue();
            int scaledCount = (int) Math.round((probability / totalProbability) * 100); // scale to 100 items
            if (scaledCount > 0) {
                scaledProbs.put(key, scaledCount);
            }
        }

        // makes lists proportional
        List<T> resultList = new ArrayList<>();
        for (Map.Entry<T, Integer> entry : scaledProbs.entrySet()) {
            T key = entry.getKey();
            int count = entry.getValue();
            for (int i = 0; i < count; i++) {
                resultList.add(key);
            }
        }

        // randomize order
        Collections.shuffle(resultList);

        return resultList;
    }
}
