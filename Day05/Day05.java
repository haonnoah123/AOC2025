import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Tools.FileImporter;
import Tools.Pair;

class Day05 {
    public static void main(String[] args) {
        List<String> input = FileImporter.getInput("Day05/Day05Input.txt");
        List<Pair<Long, Long>> ranges = getRanges(input);
        List<Long> ingredientIds = getIngredientIds(input);
        System.out.println(partOne(ranges, ingredientIds));
        System.out.println(partTwo(ranges));
    }

    public static int partOne(List<Pair<Long, Long>> ranges, List<Long> ingredientIds) {
        // Brute force check if the id is in any of the ranges
        int count = 0;
        for (Long id : ingredientIds) {
            for (Pair<Long, Long> range : ranges) {
                if (id >= range.getLeft() && id <= range.getRight()) {
                    count++;
                    break;
                }
            }
        }
        return count;
    }

    public static long partTwo(List<Pair<Long, Long>> ranges) {
        // First sort ranges
        ranges.sort((a, b) -> a.compareTo(b));

        // We want to compress the ranges which we can do by checking if the left we are
        // considering adding is <= any of our new "compact ranges" and if it is we know
        // since we sorted it we just replace that with the old compact left and the
        // bigger of the two rights.
        List<Pair<Long, Long>> compactRanges = new ArrayList<>();
        for (Pair<Long, Long> range : ranges) {
            boolean added = false;
            for (int i = 0; i < compactRanges.size(); i++) {
                Pair<Long, Long> compactRange = compactRanges.get(i);
                if (range.getLeft() <= compactRange.getRight()) {
                    compactRange = new Pair<Long, Long>(compactRange.getLeft(),
                            Math.max(compactRange.getRight(), range.getRight()));
                    compactRanges.set(i, compactRange);
                    added = true;
                    break;
                }
            }
            if (!added)
                compactRanges.add(range);
        }

        // Add up all the compact ranges (inclusive so add 1 to each)
        long count = 0;
        for (Pair<Long, Long> range : compactRanges) {
            count += range.getRight() - range.getLeft() + 1;
        }

        return count;
    }

    public static List<Pair<Long, Long>> getRanges(List<String> input) {
        List<Pair<Long, Long>> ranges = new ArrayList<>();
        int i = 0;
        while (!input.get(i).isBlank()) {
            long[] tempRange = Arrays
                    .stream(input.get(i).split("-"))
                    .mapToLong(Long::parseLong)
                    .toArray();
            ranges.add(new Pair<Long, Long>(tempRange[0], tempRange[1]));
            i++;
        }
        return ranges;
    }

    public static List<Long> getIngredientIds(List<String> input) {
        List<Long> ingredientIds = new ArrayList<>();
        int i = 0;
        while (!input.get(i++).isBlank())
            ;
        while (i < input.size()) {
            ingredientIds.add(Long.parseLong(input.get(i)));
            i++;
        }
        return ingredientIds;
    }
}
