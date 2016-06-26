package com.coding.hackerrank.solutions.atlassian;
import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;


public class Atlassian {

    //Q1 (find if a list is inside another list)
    static int find(LinkedListNode list, LinkedListNode sublist) {
        LinkedListNode current_list = list;
        Boolean flag = true;
        int index = 0;
        while(current_list!=null){
            if(current_list.val.equals(sublist.val)){
                LinkedListNode current_sublist = sublist;
                LinkedListNode saved_list = current_list;
                while(current_sublist != null){
                    if (!current_sublist.val.equals(current_list.val)){
                        flag = false;
                        current_list = saved_list.next;
                        break;
                    }
                    current_sublist = current_sublist.next;
                    current_list = current_list.next;
                }
                if(current_sublist == null){
                    flag =true;
                    break;
                }
            } else {
                current_list = current_list.next;
            }
            index ++;
        }
        if(flag) {
            return index;
        }
        else{
            return -1;
        }
    }

    private static class LinkedListNode {
        String val;
        LinkedListNode next;

        LinkedListNode(String node_value) {
            val = node_value;
            next = null;
        }
    };

    private static LinkedListNode _insert_node_into_singlylinkedlist(LinkedListNode head, String val) {
        if (head == null) {
            head = new LinkedListNode(val);
        } else {
            LinkedListNode end = head;
            while (end.next != null) {
                end = end.next;
            }
            LinkedListNode node = new LinkedListNode(val);
            end.next = node;
        }
        return head;
    }

    //Q2(http://www.geeksforgeeks.org/look-and-say-sequence/)
    private static String LookAndSay(String number, int s) {
        for (int i=0;i<s;i++) {
            number = LookAndSayOneTime(number);
        }
        return number;
    }

    private static String LookAndSayOneTime(String number) {
        StringBuilder result= new StringBuilder();

        char repeat= number.charAt(0);
        number= number.substring(1) + " ";
        int times= 1;

        for(char actual: number.toCharArray()){
            if(actual != repeat){
                result.append(times).append("").append(repeat);
                times= 1;
                repeat= actual;
            }else{
                times+= 1;
            }
        }
        return result.toString();
    }

    //Q3(atlassian3.tiff)
    private static String convert(Long number) {

        String sevenBaseNumber = convertNumber(number, 7);
        StringBuilder result = new StringBuilder();
        result.append(sevenBaseNumber);
        result.reverse();
        sevenBaseNumber = getConvertedString(result.toString());
        return sevenBaseNumber;
    }

    private static String convertNumber(Long number, Integer base) {
        String result = "";
        while (number != 0) {
            long rem = number % base;
            number /= base;
            result += rem;
        }
        return result;
    }

    private static Map<String, String > conversionMap = new HashMap<>();

    private static String getConvertedString(String sevenBaseNumber) {
        conversionMap.put("0","0");
        conversionMap.put("1","a");
        conversionMap.put("2","t");
        conversionMap.put("3","l");
        conversionMap.put("4","s");
        conversionMap.put("5","i");
        conversionMap.put("6","n");

        String result = "";
        for (int i=0;i<sevenBaseNumber.length(); i++) {
            result += conversionMap.get(sevenBaseNumber.substring(i, i+1));
        }
        return result;
    }

    //Q4(atlassian4.tiff)
    private static String compute(String instructions) {
        List<Long> blockCount = new ArrayList<>(10);
        for (int i=0;i<10; i++) {
            blockCount.add(0L);
        }
        int currentPosition = 0;
        boolean holdingBlock = false;
        for (int i=0; i<instructions.length(); i++) {
            char command = instructions.charAt(i);
            if (command == 'P') {
                holdingBlock = true;
                currentPosition = 0;
            }
            else if (command == 'M') {
                currentPosition++;
            }
            else if (command == 'L') {
                if (!holdingBlock) {
                    continue;
                }
                if (currentPosition > 9) {
                    currentPosition = 9;
                }
                if (blockCount.get(currentPosition) < 15) {
                    long cnt = blockCount.get(currentPosition);
                    blockCount.set(currentPosition, cnt+1);
                    holdingBlock = false;
                }
            }
        }
        String result = "";
        for (int i=0; i<10; i++) {
            if (blockCount.get(i) > 9) {
                char c = 'A';
                c += blockCount.get(i)%10;
                result += c;
            }
            else {
                result += blockCount.get(i);
            }
        }
        return result;
    }

    public static void main(String[] args) throws IOException{
        Scanner in = new Scanner(System.in);
        System.out.println(convert(7792875L));
        System.out.println(compute("PMLPMMMLPMLPMML"));
        System.out.println(LookAndSay("122",3));

        /*
5
1
2
3
4
5
3
3
4
5
         */
        int _list_size = Integer.parseInt(in.nextLine()), _list_i;
        String _list_item;
        LinkedListNode _list = null;
        for(_list_i = 0; _list_i < _list_size; _list_i++) {
            try {
                _list_item = in.nextLine();
            } catch (Exception e) {
                _list_item = null;
            }
            _list = _insert_node_into_singlylinkedlist(_list, _list_item);
        }


        int _sublist_size = Integer.parseInt(in.nextLine()), _sublist_i;
        String _sublist_item;
        LinkedListNode _sublist = null;
        for(_sublist_i = 0; _sublist_i < _sublist_size; _sublist_i++) {
            try {
                _sublist_item = in.nextLine();
            } catch (Exception e) {
                _sublist_item = null;
            }
            _sublist = _insert_node_into_singlylinkedlist(_sublist, _sublist_item);
        }

        int res = find(_list, _sublist);
        System.out.println(String.valueOf(res));
    }

}




