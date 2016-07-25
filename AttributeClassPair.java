/**
 * 
 * @author Shubham Saxena
 *
 */
public class AttributeClassPair implements Comparable<AttributeClassPair>{
	
	double att;
	int classV;
	
	AttributeClassPair(double att, int classV){
		this.att = att;
		this.classV= classV;
	}

	public String toString(){
		return att+"";//"v:"+att+" class:"+classV;
	}
	
	/**
	 * used for sorting.
	 */
	public int compareTo(AttributeClassPair arg0) {
		if(this.att < arg0.att){
			return -1;
		} else if(this.att > arg0.att){
			return 1;
		} else
			return 0;
	}	
	
}
