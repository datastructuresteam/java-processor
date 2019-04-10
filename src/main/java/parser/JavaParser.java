package parser;


import java.util.*;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;


public class JavaParser {

  private List<String> _formattedContent = new LinkedList<>();

  	public JavaParser(List<String> fileContent) {
      Objects.requireNonNull(fileContent);

      _formattedContent = fixComments(fileContent);
      _formattedContent = handleClassStart(fileContent);
      _formattedContent = handleLoopStart(fileContent);
      _formattedContent = handleMethodStart(fileContent);
      _formattedContent = handleBlockClosing(fileContent);
      _formattedContent = fixSemicolons(fileContent);
      //_formattedContent = enforceLegalNames(fileContent);
  }

  public List<String> getFormattedContent() {
		return _formattedContent;
	}

  // Method to fix comments(returns ArrayList)
	public static List<String> fixComments(List<String> list) {

		ListIterator<String> itr = list.listIterator();
		int count = 0;
		String str;

		// Loop thru ArrayList elements
		while (itr.hasNext()) {
			str = list.get(count);

			// Make sure the element is not empty...otherwise do not execute this block
			if (!str.isEmpty()) {
				if (str.charAt(0) == '/' && str.charAt(1) != '/') {
					str = "/" + str;
				}

				if (str.startsWith("//") && !str.endsWith(".")) {
					str = str + ".";
				}

				list.set(count, str);
			}
			count++;

			// Break out once the size of the ArrayList has been reached to avoid
			// IndexOutOfBounds Exception
			if (count == list.size()) {
				break;
			}
		}
		return list;
	}

      //Method to fix opening braces for classes
      public static List<String> handleClassStart(List<String> list){
          Iterator itr = list.listIterator();
          int count=0;
          String str;
          String str2="";

          while(itr.hasNext()) {
              str = list.get(count);

              //Check if next line exists, if so then initialize str2
              if(list.size() > count+1)
                  str2 = list.get(count+1);

              //Check if line contains a modifier
              if(str.contains("public") || str.contains("private") || str.contains("protected")) {

                  //Check if line is a class declaration and if there is no opening brace
                  if (str.contains("class") && !str.contains("{") && !str2.contains("{"))
                      str = str + "{";

              }
              list.set(count, str);
              count++;

              //Break out if count has reached the max
              if(count == list.size())
                  break;
          }
          return list;
      }

      //Method to fix opening braces for loops
      public static List<String> handleLoopStart(List<String> list){
          Iterator itr = list.listIterator();
          int count=0;
          String str;
          String str2="";

          while(itr.hasNext()) {
              str = list.get(count);

              //Check if next line exists, if so then initialize str2
              if(list.size() > count+1)
              str2 = list.get(count+1);

              //Check if line contains a loop and no opening brace
              if((str.contains("for") || str.contains("while")) && str.contains(")"))
                  if(!str.contains("{") && !str2.contains("{"))
                      str = str + "{";

              list.set(count, str);
              count++;

              //Break out if count has reached the max
              if(count == list.size())
                  break;
          }

          return list;
      }

      //Method to fix opening braces for methods
      public static List<String> handleMethodStart(List<String> list){
          Iterator itr = list.listIterator();
          int count=0;
          String str;
          String str2="";

          while(itr.hasNext()) {
              str = list.get(count);

              //Check if next line exists, if so then initialize str2
              if(list.size() > count+1)
              str2 = list.get(count+1);

              //Check if line contains a modifier
              if(str.contains("public") || str.contains("private") || str.contains("protected")) {

                  //Check if line is a method and if it has no opening brace
                  if (str.contains(")") && !str.contains("{") && !str2.contains("{"))
                      str = str + "{";

              }
              list.set(count, str);
              count++;

              //Break out if count has reached the max
              if(count == list.size())
                  break;
          }
          return list;
      }


      //Method to fix closing braces
      public static List<String> handleBlockClosing(List<String> list){
          Iterator itr = list.listIterator();
          int leftCount=0;
          int rightCount=0;
          String str="";

            for(int i=0; itr.hasNext(); i++){
              str = list.get(i);

                for(char l : str.toCharArray()) {
                    if (l == '{') {
                        leftCount++;
                    }
                }

                for(char r : str.toCharArray()) {
                    if (r == '}') {
                        rightCount++;
                    }
                }

              if(i+1 == list.size())
                  break;
          }

              //Compare number of left braces to right braces
              //if have equal number of opening and closing braces
              if (leftCount == rightCount)
                  return list;

              //if have more opening braces
              else if (leftCount > rightCount) {

                  while (leftCount > rightCount) {
                      list.add("}");
                      rightCount++;
                  }

              //if have more closing braces
              } else if (rightCount > leftCount) {

                  while (rightCount > leftCount) {
                      for(int i=0; itr.hasNext(); i++) {
                          str = list.get(i);
                          if (str.contains("}"))
                              break;
                      }
                      list.remove(list.lastIndexOf(str));
                      leftCount++;
                  }
              }

          return list;
      }

      //Method to fix semicolons
      public static List<String> fixSemicolons(List<String> list){
          Iterator itr = list.listIterator();
          String str ="";
          int count=0;

          while(itr.hasNext()){
              str = list.get(count);

              //Make sure it is a statement and not a class, method, or loop declaration
              if(!str.contains("//") && !str.contains("public") && !str.contains("{") && !str.contains("}") && !str.contains(")"))
                if(!str.isBlank() && !str.contains(";"))
                  str = str + ";";

              list.set(count, str);
              count++;

              if(count == list.size())
                  break;
          }
          return list;
      }

      //Method to fix illegal names
      /*public static List<String> enforceLegalNames(List<String> list){
          String[] tokens;
          List<String> parts = new LinkedList<String>();
          Iterator itr = list.listIterator();
          String strLine ="";
          String token ="";
          int count=0;
          int tokenCount=0;

          while(itr.hasNext()){
              strLine = list.get(count);

              if(strLine.contains(";") && !strLine.contains("//")) {
                  tokens = strLine.split("\\s+");
                  token = tokens[0];
                  System.out.println(token);

                  for(int i=0; i<tokens.length;i++)
                      System.out.print(tokens[i]);
                  System.out.print("\n");

                  while (SourceVersion.isKeyword(token) || token.contains("String")) {
                      System.out.println("The following is a keyword: "+token);
                      token = tokens[++tokenCount];
                      System.out.println("This is my new token:"+token);
                  }

                  if(Character.isDigit(token.charAt(0))) {
                      token = token.substring(1);
                      parts = Arrays.asList(tokens);
                      parts.set(tokenCount, token);
                      System.out.println(parts);
                  }
                  tokenCount=0;
              }

              strLine = String.join(" ", parts);
              list.set(count,strLine);
              count++;

              if(count == list.size())
                  break;
          }

          return list;
      }*/

}
