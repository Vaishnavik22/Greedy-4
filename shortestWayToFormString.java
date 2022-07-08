/*
Problem: https://leetcode.com/problems/shortest-way-to-form-string/

A subsequence of a string is a new string that is formed from the original string by deleting some (can be none) of the characters without disturbing the relative positions of the remaining characters. (i.e., "ace" is a subsequence of "abcde" while "aec" is not).
Given two strings source and target, return the minimum number of subsequences of source such that their concatenation equals target. If the task is impossible, return -1.

Example 1:

Input: source = "abc", target = "abcbc"
Output: 2
Explanation: The target "abcbc" can be formed by "abc" and "bc", which are subsequences of source "abc".

Example 2:

Input: source = "abc", target = "acdbc"
Output: -1
Explanation: The target string cannot be constructed from the subsequences of source string due to the character "d" in target string.

Example 3:

Input: source = "xyz", target = "xzyxz"
Output: 3
Explanation: The target string can be constructed as follows "xz" + "y" + "xz".
 

Constraints:

- 1 <= source.length, target.length <= 1000
- source and target consist of lowercase English letters.
*/


// Approach 1:
// TC: O(m * n)
// SC: O()
class Solution {
    public int shortestWay(String source, String target) {
        if (target == null || target.length() == 0) 
            return 0;
        
        if (source == null || source.length() == 0)
            return -1;
        
        int result = 1;
        int sPos = 0;
        int tPos = 0;
        
        // Ideally, we should use a hash set. Using hash map makes the next approach easier to understand
        // Store character, index in hash map
        HashMap<Character, Integer> map = new HashMap<>();
        
        for (int i = 0; i < source.length(); ++i) {
            map.put(source.charAt(i), i);
        }
        
        while (tPos < target.length()) {
            char tChar = target.charAt(tPos);
            char sChar = source.charAt(sPos);
            
            if (!map.containsKey(tChar)) {
                return -1;
            }
            
            if (sChar == tChar) {
                // Increment position in target and source
                ++tPos;
                ++sPos;
            } else {
                // Increment position on the source.
                // We are allowed to skip characters in the source since we're looking at subsequences
                ++sPos;
            }
            
            
            if (tPos < target.length() && sPos == source.length()) {
                // reset sPos to start of source string
                sPos = 0;
                ++result;
            }
        }
        return result;
    }
}


// Approach 2 - using binary search
/*
We will maintain a hash map of character and the list of indices the character is found in the source string.
Instead of moving in a linear manner through the source string, we will simply use binary search to locate the position of the character we're interested in
within the list.
*/
class Solution {
    public int shortestWay(String source, String target) {
        if (target == null || target.length() == 0) 
            return 0;
        
        if (source == null || source.length() == 0)
            return -1;
        
        int result = 1;
        int sPos = 0;
        int tPos = 0;
        
        HashMap<Character, List<Integer>> map = new HashMap<>();
        
        for (int i = 0; i < source.length(); ++i) {
            char sChar = source.charAt(i);
            if (!map.containsKey(sChar)) {
                map.put(sChar, new ArrayList<>());
            }
            
            map.get(sChar).add(i);
        }
        
        
        while (tPos < target.length()) {
            char tChar = target.charAt(tPos);
            if (!map.containsKey(tChar)) {
                return -1;
            }
            
            List<Integer> list = map.get(tChar);
            /*
            Look for sPos in the list using binary search.
            The binary search function returns the index of sPos in list if it exists.
            If sPos doesn't exist in the list, binary search returns the following index: positionTCharShouldBePresent = -(insertionPoint) - 1 
            where insertionPoint is the index in list where it should have been present.
            (Ref: https://www.geeksforgeeks.org/collections-binarysearch-java-examples/)
            So, if sPos doesn't exist, we would get a negative number for positionTCharShouldBePresent.
            */
            int positionTCharShouldBePresent = Collections.binarySearch(list, sPos);
            
            if (positionTCharShouldBePresent < 0) {
                // If positionTCharShouldBePresent is negative, make it positive to find the next closest index in list where tChar is present.
                positionTCharShouldBePresent = -positionTCharShouldBePresent - 1;
            }
            
            /* 
            If after making positionTCharShouldBePresent positive, positionTCharShouldBePresent is equal to the size of the list, next closest index in list where tChar is                 present does not exist i.e another occurrence of tChar does not exist in the array. So we need to reset sPos.
            */
            if (positionTCharShouldBePresent == list.size()) {
                sPos = 0;
                ++result;
            } else {
                // We found a match in the characters, so increase sPos and tPos
                sPos = list.get(positionTCharShouldBePresent) + 1;
                ++tPos;
            }
        }
        return result;
    }
}
