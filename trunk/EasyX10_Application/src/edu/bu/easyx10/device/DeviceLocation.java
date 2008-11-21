package edu.bu.easyx10.device;

/**
 * Class used to store the location of a device in a building
 *
 * @author winderjj
 * @version Please Refer to Subversion
 * @date Nov 21, 2008
 */
public class DeviceLocation {

	private int floorNumber;
	
	// The x and y coordinates of the device.
	private int x;
	private int y;
	
	public DeviceLocation(int floor, int x, int y){
		floorNumber = floor;
		this.x = x;
		this.y = y;
	}
	
	public DeviceLocation(){
		// Empty Constructor
	}
	
	/**
	 * @return the floorNumber
	 */
	public int getFloorNumber() {
		return floorNumber;
	}
	/**
	 * @param floorNumber the floorNumber to set
	 */
	public void setFloorNumber(int floorNumber) {
		this.floorNumber = floorNumber;
	}
	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}
	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}
	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}
	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}
}
