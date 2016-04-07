public abstract class Car {

	private Location location;
	private int minutesLeft;
	private boolean isPaying;

	/**
	 * Constructor for objects of class Car
	 */
	public Car() {

	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public int getMinutesLeft() {
		return minutesLeft;
	}

	public void setMinutesLeft(int minutesLeft) {
		this.minutesLeft = minutesLeft;
	}

	public boolean getIsPaying() {
		return isPaying;
	}

	public void setIsPaying(boolean isPaying) {
		this.isPaying = isPaying;
	}

	public void tick() {
		minutesLeft--;
	}
	
	public int betaald(){
		int payed = 0;
		if (isPaying = true ){
			payed++;
		}
		System.out.println(payed);
		return payed;
	}
	
	public int unpayed(){
		int notPayed = 0 ;
			if (isPaying = false){
				notPayed++;
			}
		System.out.println(notPayed);
		return notPayed;
	}

}