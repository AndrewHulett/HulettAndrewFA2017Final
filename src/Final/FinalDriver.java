package Final;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.TreeSet;

/*
 * MidtermDriver.java - Midterm Project
 * Andy Hulett
 * 10/12/2017
 * Main Driver class for the midterm project.  The purpose is to read data from an edgelist file and create
 * a collection of TwitterUser objects. The clone method creates a deep copy of a TwitterUser.  
 * The get Neighbor method gets the followers of the of a certain user and of those followers' followers 
 * so on to a specified depth.
 */
public class FinalDriver {

	private static HashSet<Integer> ids = new HashSet<Integer>();
	private static HashSet<TwitterUser> temp = new HashSet<TwitterUser>();
	private static TreeSet<TwitterUser> users = new TreeSet<TwitterUser>();
	private static TreeSet<TwitterUser> sortedUsers = new TreeSet<TwitterUser>();

	public static void main(String[] args) {

		FileReader file = null;
		BufferedReader in = null;
		boolean run = true;

		try {
			file = new FileReader("src//social_network.edgelist");
			in = new BufferedReader(file);
			TwitterUser current = null;
			int prevuser = -1;
			String line;
			while ((line = in.readLine()) != null) {

				String[] strs = line.split(" ");
				int i = Integer.parseInt(strs[0]);
				int k = Integer.parseInt(strs[1]);
	
				if (i == prevuser) {
					current.follow(k);
					//System.out.println(i + " is following " + k);
				} else {
					prevuser = i;
					current = new TwitterUser(i);
					current.follow(k);
					temp.add(current);
					ids.add(i);
					//System.out.println(i + " is going into ids");
					//System.out.println(i + " is following " + k);
					if (!ids.contains(k)) {
						current = new TwitterUser(k);
						current.addFollower(i);
						ids.add(k);
						temp.add(current);
						//System.out.println(k + " is going into ids");
						//System.out.println(k + " is being followed by " + i);
					} else {
						//System.out.println(k + " is already in ids");
						for (TwitterUser g : temp) {
							if (g.getID() == k) {
								g.addFollower(i);
								//System.out.println(k + " is being followed by " + i);
								break;
							}
						}
					}

				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException r) {
			r.printStackTrace();
		}

		System.out.println("File Read"); // Debug statement to make certain the file was read without
											// without slowing it down with constant printing.

		/*
		 * for(TwitterUser tt: temp) { for(Integer ii: tt.getFollowing()) {
		 * linearSearch(ii, temp).addFollower(tt.getID());
		 * System.out.println(linearSearch(ii, temp).toString(1)); } }
		 */

		users = new TreeSet<TwitterUser>(temp);// users with lists of people they are following
		sortedUsers = new TreeSet<TwitterUser>(followerSort(new FollowerComparator()));
		// users2 = new TreeSet<TwitterUser>(temp2);// users with lists of people
		// following them

		/*
		 * for(TwitterUser v: users) { for(TwitterUser h: users) {
		 * if(h.getFollowing().contains(v.getID())) { v.addFollower(h.getID()); } } }
		 */
		int res;
		int sec;
		Scanner s = new Scanner(System.in);
		while (run == true) {
			printMenu();
			res = s.nextInt();
			System.out.println();
			switch (res) {
			case 1:
				System.out.println("Enter a User ID: ");
				res = s.nextInt();
				System.out.println(linearSearch(res, users).toString(0));
				System.out.println();
				/*
				 * for (TwitterUser t : users) { if (res == t.getID()) {
				 * System.out.println(t.toString(0)); System.out.println(); } }
				 */
				break;
			case 2:
				System.out.println("Enter a User ID: ");
				res = s.nextInt();
				System.out.println(linearSearch(res, users).toString(1));
				/*
				 * for (TwitterUser t : users) { if (res == t.getID()) {
				 * System.out.println(t.toString(1)); System.out.println(); } }
				 */
				break;
			case 3:
				System.out.println("Enter a User ID: ");
				res = s.nextInt();
				System.out.println();
				TwitterUser clone = clone(linearSearch(res, users));
				System.out.println(clone.toString(0));
				System.out.println("Cloning successful");
				System.out.println();

				break;
			case 4:
				System.out.println("Enter a User ID: ");
				res = s.nextInt();
				System.out.println("Enter a depth: ");
				sec = s.nextInt();
				System.out.println();
				/*
				 * ArrayList<Integer> neighbors = getNeighborhood(res, sec);
				 * removeDupes(neighbors); removeSelf(neighbors, res);
				 * Collections.sort(neighbors); System.out.println(neighbors.toString());
				 * System.out.println(); break;
				 */
			case 5:
				getByPopularity();
				break;
			case 6:
				System.out.println("Goodbye!");
				run = false;
				break;
			default:
				System.out.println("Invalid Entry");
				System.out.println();
				break;
			}
		}

	}

	public static TwitterUser clone(TwitterUser k) { // Creates a deep copy of a TwitterUser
		TwitterUser t = new TwitterUser(k.getID());
		for (int i : k.getFollowing()) {
			t.follow(i);
		}
		return t;
	}

	/*
	 * public static ArrayList<Integer> getNeighborhood(Integer i, int depth) { //
	 * Returns a user's followers and their followers and so on to a certain depth
	 * TwitterUser target = null; for (TwitterUser t : users) { if (t.getID() ==
	 * (int) i) { target = t; } } ; ArrayList<Integer> userIDs = new
	 * ArrayList<Integer>(); if (depth != 0) {
	 * userIDs.addAll(target.getFollowing()); for (int p = 0; p <
	 * target.getFollowing().size(); p++) {
	 * userIDs.addAll(getNeighborhood(target.getFollowing().get(p), depth - 1)); } }
	 * else { return userIDs; } return userIDs; }
	 */

	public static ArrayList<Integer> removeDupes(ArrayList<Integer> list) {
		for (int i = 0; i < list.size(); i++) {
			for (int l = i + 1; l < list.size(); l++) {
				if (list.get(i).equals(list.get(l))) {
					list.remove(l);
				}
			}
		}

		return list;
	}

	public static void removeSelf(ArrayList<Integer> n, Integer id) {
		boolean check = true;
		while (check) {
			int index = n.indexOf(id);
			if (index != -1) {
				n.remove(index);
			} else {
				check = false;
			}
		}
	}

	public static TwitterUser linearSearch(int id, Collection<TwitterUser> cc) {
		for (TwitterUser aa : cc) {
			if (id == aa.getID()) {
				return aa;
			}
		}
		return null;
	}

	public static void getByPopularity() {
		int u = 0;
		System.out.print("The first ten users by popularity are [ ");
		for (TwitterUser b : sortedUsers) {
			if(u > 10) {
				break;
			}
			System.out.print(b.getID() + " ");
			u++;
		}

	}

	public static TreeSet<TwitterUser> followerSort(FollowerComparator a) {
		TreeSet<TwitterUser> sortedUsers = new TreeSet<TwitterUser>(a);
		sortedUsers.addAll(users);
		return sortedUsers;
	}

	public static void printMenu() {
		System.out.println("What would you like to do? \n" + "1. Display all of the ids that a user is following \n"
				+ "2. Display all of the users that are following a user\n" + "3. Create a clone of a user \n"
				+ "4. Display the neighborhood of a user \n" + "5. Get a user based on poularity \n" + "5. Quit");
	}
}
