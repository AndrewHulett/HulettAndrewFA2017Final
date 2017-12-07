package Final;
/**
 * TwitterUser.java - Midterm Project
 * 10/12/2017
 * This is the class for the TwitterUser object for the Midterm Project
 */

/**
 * @author Andy Hulett
 *
 */

import java.util.*;

public class TwitterUser implements Cloneable, Comparable<TwitterUser> {

	// Variables
	private int id;
	private HashSet<Integer> followingIDs = new HashSet<Integer>();
	private HashSet<Integer> followerIDs = new HashSet<Integer>();

	// Constructor
	public TwitterUser(Integer id) {
		setID(id);
	}

	public void setID(Integer num) { // sets the users id number
		id = num;
	}

	public int getID() {// gets the users id number
		return id;
	}

	public void follow(Integer k) { // following list
		this.followingIDs.add(k);
	}

	public void addFollower(Integer p) {
		this.followerIDs.add(p);
	}
	
	public TreeSet<Integer> getFollowing() {
		return new TreeSet<Integer>(followingIDs);
	}
	
	public TreeSet<Integer> getFollowers(){
		return new TreeSet<Integer>(followerIDs);
	}

	public int compareTo(TwitterUser o) {// Compares users by id numbers
		return this.getID() - o.getID();
	}
	
	public int compareFollowers(TwitterUser t) {
		if(this.getFollowers().size() != t.getFollowers().size()) {
			return this.getFollowers().size() - t.getFollowers().size();
		}else if(this.getFollowing().size() != t.getFollowing().size()) {
			return this.getFollowing().size() - t.getFollowing().size();
		}else {
			return this.getID() - t.getID();
		}
	}

	public String toString(int l) {
		if (l == 0) {
			TreeSet<Integer> temp = new TreeSet<Integer>(followingIDs);
			String str = "ID: " + this.getID() + " Following: [ ";
			for (Integer i : temp) {
				str = str + i + " ";
			}
			str = str + "]";
			return str;
		}else{
			TreeSet<Integer> temp2 = new TreeSet<Integer>(followerIDs);
			String str = "ID: " + this.getID() + " Followers: [ ";
			for (Integer z : temp2) {
				str = str + z + " ";
			}
			str = str + "]";
			return str;
		}

	}

}
