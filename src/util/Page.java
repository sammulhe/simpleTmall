package util;

public class Page {
	private int start;
	private int count;
	private int last;
	private int total;
	
	public int getStart(){
		return this.start;
	}
	public void setStart(int start){
		this.start = start;
	}

	public int getCount(){
		return this.count;
	}
	public void setCount(int count){
		this.count = count;
	}
	
	public int getTotal(){
		return this.total;
	}
	public void setTotal(int total){
		this.total = total;
	}
	
	public int getLast(){
		if(total % count == 0){
			last = total - count;
		}
		else{
			last = total - total % count;
		}
		
		return this.last;
	}
}
