/*
Problem: https://leetcode.com/problems/minimum-domino-rotations-for-equal-row/
TC: O(n)
SC: O(1)
*/
class Solution {
    public int minDominoRotations(int[] tops, int[] bottoms) {
        if (tops == null || tops.length == 0)
            return 0;
        
        int minRotations = getMinRotations(tops, bottoms, tops[0]);
        if (minRotations != -1) {
            return minRotations;
        }
        
        return getMinRotations(tops, bottoms, bottoms[0]);
    }
    
    private int getMinRotations(int tops[], int bottoms[], int value) {
        int topRotations = 0;
        int bottomRotations = 0;
        
        for (int i = 0; i < tops.length; ++i) {
            if (tops[i] != value && bottoms[i] != value)
                return -1;
            
            // We need if-else-if to account for the case where bottoms[i] = tops[i] = value
            if (tops[i] != value)
                ++topRotations;
            else if (bottoms[i] != value)
                ++bottomRotations;
        }
        
        return Math.min(topRotations, bottomRotations);
    }
}