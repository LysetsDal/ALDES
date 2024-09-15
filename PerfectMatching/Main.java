package StableMatching.PerfectMatching;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Stack;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Person> allPersons = new ArrayList<>();
        Stack<Person> unmatched = new Stack<>();
        ArrayList<Person> proposers;
        HashMap<String, String> matchings = new HashMap<>();

        int count = sc.nextInt();
        sc.nextInt(); // Don''t need m
        sc.nextLine(); // Skip the newline after the n and m

        // Parse all persons from input
        for (int i = 0; i < count; i++) {
            String[] line = sc.nextLine().split(" ");
            String[] ranks = Arrays.copyOfRange(line, 1, line.length);

            Person person = new Person(line[0], ranks);

            allPersons.add(person);
        }

        // Create a subList of the proposers & add them to the unmatched stack
        List<Person> subList = allPersons.subList((count / 2), allPersons.size());
        proposers = new ArrayList<>(subList);
        proposers.forEach((p) -> unmatched.add(p));

        
        // Main logic of the algorithm (follows pseudo code)
        while (!unmatched.empty()) {
            Person proposer = unmatched.pop();

            // nextCandidate keeps track of who the proposer hasn't proposed to
            String nextCandidate = proposer.getNextCandidate();
            Person rejecter = findPerson(nextCandidate, allPersons);

            if (!matchings.containsValue(rejecter.name) && rejecter != null) {
                rejecter.currentEngagement = proposer.name;
                matchings.put(proposer.name, rejecter.name);

            } else if (rejecter.prefers(proposer.name)) {
                String oldProposer = rejecter.currentEngagement;
                matchings.remove(oldProposer);

                // Push rejected proposer back onto the unmatched stack
                Person rejectedProposer = findPerson(oldProposer, proposers);
                unmatched.push(rejectedProposer);

                // Add new match
                matchings.put(proposer.name, rejecter.name);
                rejecter.currentEngagement = proposer.name;

            } else {
                unmatched.push(proposer);
            }

        }

        for (String key : matchings.keySet()) {
            System.out.println(key + " " + matchings.get(key));
        }

        sc.close();
    }

    public static Person findPerson(String name, ArrayList<Person> list) {
        for (Person person : list) {
            if (person.name.equals(name)) {
                return person;
            }
        }

        throw new NullPointerException("No person of that name");
    }

    static class Person {

        public String name;
        public String[] rank;
        public int rankPointer = 0;
        public String currentEngagement = "";

        public Person(String name, String[] rank) {
            this.name = name;
            this.rank = rank;
        }

        // Does the rejector prefer the new proposer
        public boolean prefers(String proposer) {
            int currentRank = Integer.MAX_VALUE;
            int proposerRank = Integer.MAX_VALUE;

            for (int i = 0; i < rank.length; i++) {
                String name = rank[i];

                if (name.equals(currentEngagement)) {
                    currentRank = i;
                }
                if (name.equals(proposer)) {
                    proposerRank = i;
                }
            }

            return proposerRank < currentRank ? true : false;
        }

        public String getNextCandidate() {
            if (rankPointer < rank.length) {
                int oldIndex = rankPointer;
                rankPointer++;
                return rank[oldIndex];
            } else {
                return null; // No more candidates to propose to
            }
        }
    }
}
