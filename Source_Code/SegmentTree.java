import java.util.*;
import java.io.*;
import java.lang.*;

class SegmentTree{

	int sum[];
	int maxi[];
	int mini[];

	SegmentTree(int a[], int n){
		sum = new int[4*n];
		mini = new int[4*n];
		maxi = new int[4*n];
	}

	void build(int a[], int v, int tl, int tr){
		if(tl == tr){
			sum[v] = a[tl];
			mini[v] = a[tl]                 ;
			maxi[v] = a[tl];
		}
		else{
			int m = (tl+tr)/2;
			build(a, 2*v+1, tl, m);
			build(a, 2*v+2, m+1, tr);
			sum[v] = sum[2*v+1] + sum[2*v+2];
			mini[v] = Math.min(mini[2*v+1], mini[2*v+2]);
			maxi[v] = Math.max(maxi[2*v+1], maxi[2*v+2]);
		}
	}

	int query(int option, int tl, int tr, int ql, int qr, int v){
		if(ql > qr){
			if(option == 3)
				return 0;
			else if(option == 2)
				return Integer.MIN_VALUE;
			else if(option == 1)
				return Integer.MAX_VALUE;
		}
		if(tl == ql && tr == qr){
			if(option == 1)
				return mini[v];
			else if(option == 2)
				return maxi[v];
			else if(option == 3)
				return sum[v];
		}
			int tm = (tl+tr)/2;
			if(option == 1)
				return Math.min(query(option, tl, tm, ql ,Math.min(qr, tm), 2*v+1),
					query(option, tm+1, tr, Math.max(ql, tm+1), qr, 2*v+2));
			else if(option == 2)
				return Math.max(query(option, tl, tm, ql, Math.min(qr, tm), 2*v+1), 
					query(option, tm+1, tr, Math.max(ql, tm+1), qr, 2*v+2));
			else 
				return (query(option, tl, tm, ql, Math.min(qr, tm), 2*v+1) + 
					query(option, tm+1, tr, Math.max(ql, tm+1), qr, 2*v+2));
	}

	void update(int tl, int tr, int ul, int ur, int v, int add_val){
		if(ul > ur)
			return;
		if(ul == tl && ur == tr){
			sum[v] += ((tr-tl+1)*add_val);
			mini[v] += add_val;
			maxi[v] += add_val;
			return;
		}
		else{
			int tm = (tl+tr)/2;
			update(tl, tm, ul, Math.min(ur, tm), 2*v+1, 4);
			update(tm+1, tr, Math.max(tm+1, ul), ur, 2*v+2, 4);
			sum[v] = sum[2*v+1] + sum[2*v+2];
			mini[v] = Math.min(mini[2*v+1], mini[2*v+2]);
			maxi[v] = Math.max(maxi[2*v+1], maxi[2*v+2]);
		}
	}

	public static void main(String args[]){
		int n, option, left, right;
		System.out.println("No. of terms you want to enter");
		Scanner input = new Scanner(System.in);
		n = input.nextInt();

		int arr[] = new int[n];
		for(int i = 0; i < n; i++)
			arr[i] = input.nextInt();
		SegmentTree tree = new SegmentTree(arr, n);
		tree.build(arr, 0, 0, n-1);
		System.out.println("1. minimum value");
		System.out.println("2. maximum value");
		System.out.println("3. Sum");
		System.out.println("4. Update by adding 4 with each element");
		System.out.println("Enter your option");
		option = input.nextInt();
		
		System.out.println("Enter range as left and right (<" + n + ")");
		left = input.nextInt();
		right = input.nextInt();
		left--; right--;
		if(option < 4){
			int output = tree.query(option, 0, n-1, left, right, 0);
			System.out.println(output);
		}
		if(option == 4){
			tree.update(0, n-1, left, right, 0, 4);
		}

	}
}