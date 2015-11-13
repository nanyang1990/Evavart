import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
//Note directionmap is initial -2



public class directionAnalysiser {
	int[][] mymap;
	int row;
	int column;
	int[][] directionmap;
	int[][] distancemap;
	int [] exitValue={9,900};
	int index;
	
	//people initial -2
	int peopleTotal=0;
	
	//int peopleLeft=0;


	//int[][] distancemap;
	int [][] exits=null;

	ArrayList<ArrayList<Integer>> map=new ArrayList<ArrayList<Integer>>();
	ArrayList<Integer> arl = new ArrayList<Integer>();
	ArrayList<ArrayList<Integer>> present=new ArrayList<ArrayList<Integer>>();
	ArrayList<ArrayList<Integer>> past=new ArrayList<ArrayList<Integer>>();
	ArrayList<ArrayList<Integer>> saved=new ArrayList<ArrayList<Integer>>();
	
	
	public directionAnalysiser(int[][] map, int index) {
		// TODO Auto-generated constructor stub
		this.index=index;
		mymap=map;
		row=mymap.length;
		column=mymap[0].length;
		directionmap=new int[row][column];
		distancemap=new int[row][column];
		
		//Initialize directionmap 
		for(int x=0;x<row;x++){
			for(int y=0;y<column;y++){
				//Note direction map and distance map are initial -2
				directionmap[x][y]=-2;
				distancemap[x][y]=-2;
				if(mymap[x][y]>0 &&mymap[x][y]!=9){
					peopleTotal+=1;
				}
				
			}
		}
		
	}
	

	
	
	
	
	protected void claculateMap_Dir(){
		System.out.println("claculateMap_Dir() executed, ");
		//get exits' number
		
		int number=0;
		int number2=0;
		for(int x=0; x<row;x++){
			for (int y=0;y<column;y++){
				if(mymap[x][y]==exitValue[index]){
					number++;
				}
			}	
		}
		exits=new int [number][3];
		
		//save exits' number and set walls and exits for direction 3
		for(int x=0; x<row;x++){
			for (int y=0;y<column;y++){
				if(mymap[x][y]==exitValue[index]){
					exits[number2]=new int[] {x,y,exitValue[index]};
					directionmap[x][y]=0;
					number2++;
				}
				if(mymap[x][y]==-1){
					directionmap[x][y]=-1;
				}	
			}	
		}
		System.out.println("exit number="+exits.length);
		
		
	}
	
	protected void claculateMap_Dis(){
		//get exits' number
		int number=0;
		int number2=0;
		for(int x=0; x<row;x++){
			for (int y=0;y<column;y++){
				if(mymap[x][y]==exitValue[index]){
					number++;
				}
			}	
		}
		exits=new int [number][3];
		
		//save exits' number and set walls and exits for direction 3
		for(int x=0; x<row;x++){
			for (int y=0;y<column;y++){
				if(mymap[x][y]==exitValue[index]){
					exits[number2]=new int[] {x,y,exitValue[index]};
					distancemap[x][y]=0;
					number2++;
				}
				if(mymap[x][y]==-1){
					distancemap[x][y]=-1;
				}	
			}	
		}
		System.out.println("exit number="+exits.length);
		
		
	}
	
	protected void getExits(){
		//ArrayList<ArrayList<Integer>> exit=new ArrayList<ArrayList<Integer>>();
		//ArrayList<ArrayList<Integer>> past=new ArrayList<ArrayList<Integer>>();
		
		//save exits in array list
		for(int x=0;x<exits.length;x++){
			if(exits[x]!=null){
				ArrayList<Integer> ar = new ArrayList<Integer>();
				ar.add(exits[x][0]);
				ar.add(exits[x][1]);
				////in directional map exits are marked as 0
				//ar.add(0);
				if(!past.contains(ar)){
					//exit.add(ar);
					past.add(ar);
				}else{
					System.out.println("past already contains arraylist"+ar.toString());
				}
			}
		}
		past=removeDuplicatePoints(past);
		System.out.println("past contains "+past.toString());
		saved.addAll(past);
		
	}
	
	protected void calculateDirection(){
		int flag=0;
		while (flag == 0) {
			for (int x = 0; x < past.size(); x++) {
				ArrayList<ArrayList<Integer>> Neighbor = getNeighbors(
						past.get(x).get(0), past.get(x).get(1));
				
				Neighbor = removeInvalidePoints(Neighbor);

				for (int y = 0; y < Neighbor.size(); y++) {
					if (!saved.contains(Neighbor.get(y)))
						present.add(Neighbor.get(y));
				}
			}
			present = removeInvalidePoints(present);
			present = removeDuplicatePoints(present);
			if(present.size()==0){
				System.out.println("present=0, quit the method.");
				flag=1;
			}
			System.out.println("present=" + present.toString());

			// ArrayList<ArrayList<Integer>> directNext=new
			// ArrayList<ArrayList<Integer>>();

			for (int x = 0; x < present.size(); x++) {

				for (int y = 0; y < past.size(); y++) {
					int a1 = present.get(x).get(0);
					int b1 = present.get(x).get(1);
					int a2 = past.get(y).get(0);
					int b2 = past.get(y).get(1);
					if (a1 == a2 && b1 - b2 == 1) {
						directionmap[a1][b1] = 7;
						// ArrayList<Integer> ar0 = new ArrayList<Integer>();
						// ar0.add(a1);
						// ar0.add(b1);
						// directNext.add(ar0);
					} else if (a1 == a2 && b1 - b2 == -1) {
						directionmap[a1][b1] = 3;

					} else if (a1 - a2 == 1 && b1 == b2) {
						directionmap[a1][b1] = 1;

					} else if (a1 - a2 == -1 && b1 == b2) {
						directionmap[a1][b1] = 5;
						//System.out.println("directionmap seted to 1 at" + a1+","+b1+" past="+a2+","+b2);

					} else if (a1 - a2 == 1 && b1 - b2 == 1) {
						if (directionmap[a1][b1] == -2) {
							directionmap[a1][b1] = 8;
						}
					} else if (a1 - a2 == -1 && b1 - b2 == -1) {
						if (directionmap[a1][b1] == -2) {
							directionmap[a1][b1] = 4;
						}
					} else if (a1 - a2 == 1 && b1 - b2 == -1) {
						if (directionmap[a1][b1] == -2) {
							directionmap[a1][b1] = 2;
						}
					} else if (a1 - a2 == -1 && b1 - b2 == 1) {
						if (directionmap[a1][b1] == -2) {
							directionmap[a1][b1] = 6;
						}
					}
				}
			}
			saved.addAll(past);
			saved.addAll(present);
			saved =removeInvalidePoints(saved);
			saved =removeDuplicatePoints(saved);
			past.clear();
			past.addAll(present);
			past = removeInvalidePoints(past);
			past =removeDuplicatePoints(past);
			present.clear();
			System.out.println("saved=" + saved.toString());
			System.out.println("past=" + past.toString());
			System.out.println("present=" + present.toString());

			

		}
		
		
		
		printDirectionMap();
	}
	
	protected void calculateDistance(){
		
		int flag=0;
		int distance=1;
		while (flag == 0) {
			for (int x = 0; x < past.size(); x++) {
				ArrayList<ArrayList<Integer>> Neighbor = getNeighbors(
						past.get(x).get(0), past.get(x).get(1));
				
				Neighbor = removeInvalidePoints(Neighbor);

				for (int y = 0; y < Neighbor.size(); y++) {
					if (!saved.contains(Neighbor.get(y)))
						present.add(Neighbor.get(y));
				}
			}
			present = removeInvalidePoints(present);
			present = removeDuplicatePoints(present);
			if(present.size()==0){
				System.out.println("present=0, quit the method.");
				flag=1;
			}
			System.out.println("present=" + present.toString());

			// ArrayList<ArrayList<Integer>> directNext=new
			// ArrayList<ArrayList<Integer>>();

			for (int x = 0; x < present.size(); x++) {
				
				distancemap[present.get(x).get(0)][present.get(x).get(1)] = distance;
				

				
			}
			saved.addAll(past);
			saved.addAll(present);
			saved =removeInvalidePoints(saved);
			saved =removeDuplicatePoints(saved);
			past.clear();
			past.addAll(present);
			past = removeInvalidePoints(past);
			past =removeDuplicatePoints(past);
			present.clear();
			System.out.println("saved=" + saved.toString());
			System.out.println("past=" + past.toString());
			System.out.println("present=" + present.toString());

			distance++;

		}
		
		
		printDistanceMap();
	}
	
	
	
	protected void printDirectionMap(){
		System.out.println("Print Direction map");
		for(int x=0;x<row;x++){
			for(int y=0;y<column;y++){
				//Note directionmap is initial -2
				System.out.print(directionmap[x][y]);
				
			}
			System.out.println();
		}
	}
	
	
	protected void printDistanceMap(){
		System.out.println("Print Distance map");
		for(int x=0;x<row;x++){
			for(int y=0;y<column;y++){
				//Note directionmap is initial -2
				
					System.out.print(distancemap[x][y]);
				
			}
			System.out.println();
		}
	}
	
	
	
	/* This method returns the given point's existing Neighbors
	 * 
	 */
	protected ArrayList<ArrayList<Integer>> getNeighbors(int x, int y){
		ArrayList<ArrayList<Integer>> Neighbors=new ArrayList<ArrayList<Integer>>();
		if(x>=row || y>=column){
			System.out.println("Error, point out of the given map at "+x+","+y);
		}else
		{
			int TBDneighbour [] []=new int[][] {{x-1,y-1},{x, y-1},{x+1,y-1},{x-1,y},{x+1,y},{x-1,y+1},{x,y+1},{x+1,y+1}};
			
			for(int a=0;a<8;a++){
				
				if(TBDneighbour[a][0]>=0 && TBDneighbour[a][0]<row && TBDneighbour[a][1]>=0 && TBDneighbour[a][1]<column){
					ArrayList<Integer> line=new ArrayList<Integer>();
					line.add(TBDneighbour[a][0]);
					line.add(TBDneighbour[a][1]);
					Neighbors.add(line);
				}
			}
		}
		return Neighbors;
		
	}
	
	protected  ArrayList<ArrayList<Integer>> removeInvalidePoints(ArrayList<ArrayList<Integer>> oldList){
		ArrayList<ArrayList<Integer>> newList=oldList;
		for(int x=0; x<newList.size();x++ ){
			int j=newList.get(x).get(0);
			int k=newList.get(x).get(1);
			if(mymap[j][k]==-1){
				newList.remove(x);
				x=x-1;
			}
			
		}
		return newList;
	}
	
	/* This method removes Duplicate points in a 2D ArrayList
	 * 
	 */
	protected  ArrayList<ArrayList<Integer>> removeDuplicatePoints(ArrayList<ArrayList<Integer>> oldList){
		ArrayList<ArrayList<Integer>> newList=oldList;
		Set<ArrayList<Integer>> set = new LinkedHashSet<ArrayList<Integer>>();
		set.addAll(newList);
		newList.clear();
		newList.addAll(set);
		return newList;
	}
	
	protected int[][] getDirectionmap() {
		return directionmap;
	}
	protected int[][] getDistanceMap() {
		return distancemap;
	}
	protected int[][] getExit2D() {
		return  exits;
	}
	protected int getPeopleTotal() {
		return peopleTotal;
	}

	
}