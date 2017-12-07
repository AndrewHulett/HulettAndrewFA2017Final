/**
 * Andrew Hulett
 * 12/6/2017
 * This is the comparator object used to sort the users list by number of followers
 */
package Final;

import java.util.Comparator;

/**
 * @author Andy
 *
 */
public class FollowerComparator implements Comparator<TwitterUser>{
	
	public FollowerComparator(){
		
	}

	public int compare(TwitterUser o, TwitterUser p) {
		return o.compareFollowers(p);
	}
}
