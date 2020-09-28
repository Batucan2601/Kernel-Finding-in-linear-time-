import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Vertex {
    public double x;
    public double y;
    public Circle circle;
    public Vertex(Circle circle){
        this.circle = circle;
        /*if( circle.getCenterX() > 495 || x < circle.getCenterY()  ){
            this.x  = (circle.getCenterX() - 1000) / 10;
        }
        else{

        }*/
        this.y = 50 - circle.getCenterY()/10;
        this.x = -100 + circle.getCenterX()/10;

    }
    public Vertex( double x, double y ){
        double circleCenterx;
        double circleCenterY = (50 - y) * 10;
        circleCenterx = 10*(x + 100);

        Circle circle= new Circle(circleCenterx , circleCenterY , 2.5 , Color.BLACK);
        this.circle = circle;
        this.x = x;
        this.y = y;
    }
    public double getCircleX(){
        return (double)circle.getCenterX();
    }
    public double getCircleY(){
        return (double)circle.getCenterY();
    }
    public Circle getCircle(){
        return circle;
    }

}