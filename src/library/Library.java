/*REFERENCE
* https://www.youtube.com/watch?v=q8Z3CmruGzI
* https://www.youtube.com/watch?v=7GZppdccFfs
* https://www.youtube.com/watch?v=ncOxOPRBUgM
* http://www.mysqltutorial.org/mysql-jdbc-blob*/

package library;

import library.UI.Window;


public class Library {
	private static Window window;

	public static void main(String[] args) {
		window = new Window();
	}

	//set to be static so it can change the panel in the user interface easily
	public static Window getWindow() {
		return window;
	}
}
