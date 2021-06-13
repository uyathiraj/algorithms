package com.yru.algorithms.models;

public class PointItem {

	public PointItem(double x, double y, int size) {
		this.x = x;
		this.y = y;
		this.size = size;
	}

	public double x;
	public double y;

	public int size;

	int sequence;

	float distance;

	@Override
	public String toString() {
		return "(" + this.x + "," + this.y + ")";
	}

}
