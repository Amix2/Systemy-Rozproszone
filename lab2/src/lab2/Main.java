package lab2;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
	
	static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	 
	public static void main(String[] args) throws Exception {
		DistributedMap map = new DistributedMap();
		Boolean exit = false;
		while (!exit) {
			map.print();
			System.out.print("> "); System.out.flush();
			String line = reader.readLine();
			System.out.println(line);
			//if(line == "") continue;
			if(line.equals("exit")) {
				exit = true;
				continue;
			}
			String lineValues[] = line.split(" ");
			switch(lineValues[0]) {
			case "put" :
				if(lineValues.length == 1)
					map.put();
				else
					map.put(lineValues[1], Integer.parseInt(lineValues[2]));
				break;
			case "remove" :
				System.out.println("remove =>> " + map.remove(lineValues[1]));
				break;
			case "contains" :
				System.out.println("containsKey =>> " + map.containsKey(lineValues[1]));
				break;
			case "get":
				System.out.println(map.get(lineValues[1]));
				break;
			}
		}
	}
}
