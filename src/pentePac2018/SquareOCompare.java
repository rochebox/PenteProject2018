package pentePac2018;

import java.util.Comparator;

public class SquareOCompare implements Comparator<Square>{
  
  @Override
  public int compare(Square s1, Square s2) {
            if(s1.getNextMoveDPriority() < s2.getNextMoveDPriority()){
                return 1;
            } else {
                return -1;
            }
  }
}