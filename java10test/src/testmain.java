import javafx.scene.shape.Line;
import javafx.application.Application;
import javafx.stage.Stage;

public class testmain extends Application {
    public static boolean containsPoint( double x, double y, Line l ){
        double slope = (l.getEndY() - l.getStartY()) / (l.getEndX() - l.getStartX());
        double slope1 = (y-l.getStartY())/(x-l.getStartX());
        double slope2 = (y-l.getEndY()) / (x - l.getEndX());
        if( slope1 == slope2 && slope1 == slope)
            return true;
        else
            return  false;
    }
    public  static  void main(String args[]){
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Line l1 = new Line( 600 , 800 , 100 , 200 );
        boolean t = containsPoint(1000 , 2000 , l1 );
        System.out.println(t);
    }
}
