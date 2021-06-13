package com.yru.algorithms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.yru.algorithms.models.PointItem;

public class KMeanAlgorithm {

	double computeDistance(PointItem p1, PointItem p2) {

		double yD = (p2.y - p1.y);
		double xD = p2.x - p1.x;

		double dist = Math.sqrt(Math.abs(yD * yD + xD * xD));
		return dist;
	}

	// Divide to cluster
	// Compute total size

	void kMean(List<PointItem> arrays, int maxSize, List<List<PointItem>> routes) {

		int total = computeTotalSize(arrays);

		if (total == 0) {
			return;
		}
		if (maxSize >= total) {
			System.out.println("Got route " + total);
			routes.add(arrays);
			return;
		}

		// lets take middle item in list
		int p1 = Math.abs(new Random().nextInt(arrays.size()));
		int p2 = Math.abs(new Random().nextInt(arrays.size() - 1));
		if (p1 == p2) {
			p2 = p2 + 1;
		}

		List<PointItem> cluster1 = new ArrayList<>();
		List<PointItem> cluster2 = new ArrayList<>();
		System.out.println("P1 " + p1);
		System.out.println("P2 " + p2);

		for (PointItem p : arrays) {
			double d1 = computeDistance(arrays.get(p1), p);
			double d2 = computeDistance(arrays.get(p2), p);
			if (d1 < d2) {
				cluster1.add(p);
			} else {
				cluster2.add(p);
			}

		}
		kMean(cluster1, maxSize, routes);
		kMean(cluster2, maxSize, routes);

	}

	void kMean(List<PointItem> arrays, int maxSize, List<List<PointItem>> routes, int itr) {

		int total = computeTotalSize(arrays);

		if (itr == 1) {
			System.out.println("ITR " + 0);
			// routes.add(arrays);
			return;
		}

		if (total == 0) {
			return;
		}
		
		

		if (itr > arrays.size()) {

			if (total <= maxSize) {
				routes.add(arrays);
				return;
			} else {
				itr = itr - 1;
			}

		}

		if (maxSize >= total) {
			routes.add(arrays);
			return;
		}

		Map<Integer, List<PointItem>> clusters = new HashMap<>();

		List<Integer> cenSet = new ArrayList<>();

		for (int i = 0; i < itr; i++) {

			 int p1 = Math.abs(new Random().nextInt(arrays.size()));

			//int p1 = i;

			cenSet.add(p1);

			List list = new ArrayList<>();
			list.add(arrays.get(p1));
			clusters.put(p1, list);
		}

		printArray("Centroids ", cenSet);
		printArray("Arrays ", arrays);

		for (int i = 0; i < arrays.size(); i++) {

			if (!cenSet.contains(i)) {
				PointItem p = arrays.get(i);
				System.out.println("Point " + p);

				double min = Double.MAX_VALUE;

				int nearestCenter = -1;

				for (int j = 0; j < cenSet.size(); j++) {

					PointItem c = arrays.get(cenSet.get(j));

					double d = computeDistance(p, c);

					if (d <= min && d != 0) {

						min = d;
						nearestCenter = cenSet.get(j);
					} 
				}
				System.out.println("Point " + p.x + "," + p.y);
				System.out.println("Nearest to " + i + " is " + nearestCenter + " dist " + min);
				List list = clusters.get(nearestCenter);
				list.add(p);
				clusters.put(nearestCenter, list);

			}

		}

		printArray("Clusers", clusters.values());

		for (Integer key : clusters.keySet()) {
			kMean(clusters.get(key), maxSize, routes, itr);
		}

	}

	int computeTotalSize(List<PointItem> arrays) {
		int totalSize = 0;
		for (PointItem item : arrays) {
			totalSize += item.size;
		}
		return totalSize;
	}

	void printArray(String message, Collection<?> arra) {
		System.out.print(message + " -> ");
		for (Object obj : arra) {
			System.out.print(obj);
			System.out.print(" ");
		}
		System.out.println();
	}

	public static void main(String[] args) {

		List<PointItem> points = new ArrayList<>();
		points.add(new PointItem(2, 1, 3)); // 0
		points.add(new PointItem(3, 7, 2)); // 1
		points.add(new PointItem(4, 4, 3)); // 2
		points.add(new PointItem(5, 1, 4)); // 3
		points.add(new PointItem(7, 6, 3)); // 4
		points.add(new PointItem(7, 5, 2)); // 5
		points.add(new PointItem(8, 3, 1)); // 6
		points.add(new PointItem(9, 9, 7)); // 7

		List<List<PointItem>> routes = new ArrayList<>();
		new KMeanAlgorithm().kMean(points, 7, routes, 6);

		for (List<PointItem> list : routes) {
			int size = 0;
			System.out.print("Route : ");
			for (PointItem item : list) {
				System.out.print("(" + item.x + "," + item.y + ")");
				size += item.size;
			}
			System.out.println("  -> size " + size);
		}
	}
}
