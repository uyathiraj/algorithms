package com.yru.algorithms;

import java.util.Collection;

public class Helper {
	static void printArray(String message, Collection<?> arra) {
		System.out.print(message + " -> ");
		for (Object obj : arra) {
			System.out.print(obj);
			System.out.print(" ");
		}
		System.out.println();
	}

	public static double computePointDistance(double x1, double y1, double x2, double y2) {
		double yD = y2 - y1;
		double xD = x2 - x1;

		double dist = Math.sqrt(Math.abs(yD * yD + xD * xD));
		return dist;

	}
}
