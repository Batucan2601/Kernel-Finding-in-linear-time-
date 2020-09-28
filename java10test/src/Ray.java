import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Ray {
    Vertex start;
    Vertex end;
    int terminate_initiate;
    double slope;
    Line line;
    double thirdx;
    double thirdy;
    // terminate_ininiate 1 -> line terminates at start, starting from infinity
    // terminate initiate 2 -> line terminates at end, starting from infinity
    // terminate_ininiate 3 -> line terminates at infinity, starting from start vertex
    // terminate initiate 4 -> line terminates at infinity , starting from  end vertex
    public Ray(Vertex start , Vertex end, int terminate_initiate ){
        this.start = start;
        this.end = end;
        this.terminate_initiate = terminate_initiate;
        try {
            double y = (start.getCircleY() - end.getCircleY());
            double x = (start.getCircleX()- end.getCircleX());
            slope =  y / x;
        }
        catch (Exception e ){

        }
        if(terminate_initiate == 1 ){ // initiates at start
            if(start.getCircleX()> end.getCircleX() ){
                thirdx = 100000;
                thirdy =  slope* (thirdx - start.getCircleX()) + start.getCircleY();
            }
            else if( start.getCircleX()< end.getCircleX() ){
                thirdx =  -100000;
                thirdy = slope* (thirdx - start.getCircleX()) + start.getCircleY();
            }
            else if( start.getCircleX()== end.getCircleX() ){
                thirdx = start.getCircleX();
                thirdy = 100000;
            }
            line = new Line(thirdx,thirdy, start.getCircleX(),start.getCircleY());
        }
        else if( terminate_initiate == 2 ){
            if(start.getCircleX()> end.getCircleX() ){
                thirdx = 2000;
                thirdy =  slope* (thirdx - start.getCircleX()) + start.getCircleY();
            }
            else if( start.getCircleX()< end.getCircleX() ){
                thirdx =  -2000;
                thirdy = slope* (thirdx - start.getCircleX()) + start.getCircleY();
            }
            else if( start.getCircleX()== end.getCircleX() ){
                thirdx = start.getCircleX();
                thirdy = 2000;
            }
            line = new Line(thirdx,thirdy, end.getCircleX() ,end.getCircleY());
        }
        else if( terminate_initiate == 3 ){
            if(start.getCircleX()> end.getCircleX() ){
                thirdx = 100000;
                thirdy =  slope* (thirdx - start.getCircleX()) + start.getCircleY();
            }
            else if( start.getCircleX()< end.getCircleX() ){
                thirdx =  -100000;
                thirdy = slope* (thirdx - start.getCircleX()) + start.getCircleY();
            }
            else if( start.getCircleX()== end.getCircleX() ){
                thirdx = start.getCircleX();
                if( start.getCircleY() > end.getCircleY()){
                    thirdy = 100000;
                }
                else
                    thirdy = -100000;
            }
            line = new Line(start.getCircleX(),start.getCircleY(),thirdx,thirdy );
        }
        else if( terminate_initiate == 4 ){
            if(start.getCircleX()> end.getCircleX() ){
                thirdx = 2000;
                thirdy =  slope* (thirdx - start.getCircleX()) + start.getCircleY();
            }
            else if( start.getCircleX()< end.getCircleX() ){
                thirdx =  -2000;
                thirdy = slope* (thirdx - start.getCircleX()) + start.getCircleY();
            }
            else if( start.getCircleX()== end.getCircleX() ){
                thirdx = start.getCircleX();
                thirdy = 2000;
            }
            line = new Line(end.getCircleX() ,end.getCircleY(),thirdx,thirdy );
        }
        else if( terminate_initiate == 5 ){
            if(start.getCircleX()> end.getCircleX() ){
                thirdx = -1000000;
                thirdy =  slope* (thirdx - start.getCircleX()) + start.getCircleY();
            }
            else if( start.getCircleX()< end.getCircleX() ){
                thirdx =  1000000;
                thirdy = slope* (thirdx - start.getCircleX()) + start.getCircleY();
            }
            else if( start.getCircleX()== end.getCircleX() ){
                thirdx = start.getCircleX();
                if( start.getCircleY() > end.getCircleY()){
                    thirdy = 1000000;
                }
                else
                    thirdy = -1000000;
            }
            line = new Line(start.getCircleX(),start.getCircleY(),thirdx,thirdy );
        }
    }
    // inout the circles
    public boolean containsPoint( double x, double y ){
        double slope1 = (y-start.getCircleY())/(x-start.getCircleX());
        double slope2 = (y-end.getCircleY()) / (x - end.getCircleX());
        if( slope1 == slope2 && slope1 == slope)
            return true;
        else
            return  false;
    }
    public Line getRay(){
        return line;
    }

}
