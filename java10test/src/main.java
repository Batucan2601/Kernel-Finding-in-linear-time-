import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import java.util.Scanner;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.awt.*;
import java.util.concurrent.TimeUnit;

public class main extends Application{
    ArrayList<Circle> circleList = new ArrayList<Circle>();
    ArrayList<Vertex> vertexList = new ArrayList<Vertex>();
    ArrayList<Vertex> upperList = new ArrayList<Vertex>();
    ArrayList<Vertex> lowerList = new ArrayList<Vertex>();
    ArrayList<Vertex> polygonVertices = new ArrayList<Vertex>();
    ArrayList<Line> polygonLines = new ArrayList<Line>();
    ArrayList<Vertex> polygonListCounterClockwise = new ArrayList<>();
    ArrayList<Object> kernel = new ArrayList<>();
    Scene scene;
    Pane root;
    Scanner scan = new Scanner(System.in);
    double highestX, highestY, lowestX, lowestY;
    VBox layout = null;
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("CS478");
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        Line l = new Line(600 ,100 , 800 ,200 );
        boolean a = containsPoint(1000, 300 , l);
        System.out.println( " lien works " + a );
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });

        root = new Pane();
        for( int i = 0; i < 1000; i = i + 10 ){
            Line line1 = new Line(0,i,100000,i);
            if( i != 500 )
                line1.setStroke(Color.RED);
            line1.setOnMouseClicked(new EventHandler<MouseEvent>(){
                public void handle(MouseEvent e ){
                    System.out.println("line clicked ");
                    System.out.println("X = " + e.getSceneX());
                    System.out.println("Y = " + e.getSceneY());
                    Circle circle = new Circle(e.getSceneX() , e.getSceneY() , 2.5, Color.BLACK);
                    root.getChildren().add(circle);
                    circleList.add(circle);
                    Vertex v = new Vertex(circle);
                    System.out.println("the x of vertex is  "+ v.x);
                    System.out.println("the y of vertex is  "+ v.y);
                    vertexList.add(v);
                }
            });
            root.getChildren().addAll(line1);
        }
        for( int i = 0; i < 2000; i = i + 10 ){
            Line line1 = new Line(i ,-1000,i,1000);
            if( i != 1000 )
                line1.setStroke(Color.RED);
            line1.setOnMouseClicked(new EventHandler<MouseEvent>(){
                public void handle(MouseEvent e ){
                    //System.out.println("line clicked ");
                    System.out.println("X = " + e.getSceneX());
                   System.out.println("Y = " + e.getSceneY());
                    Circle circle = new Circle(e.getSceneX() , e.getSceneY() , 2.5, Color.BLACK);
                    root.getChildren().add(circle);
                    circleList.add(circle);
                    Vertex v = new Vertex(circle);
                   // System.out.println("the x of vertex is  "+ v.x);
                   // System.out.println("the y of vertex is  "+ v.y);
                    vertexList.add(v);
                }
            });
            root.getChildren().addAll(line1);
        }
        Button button = new Button();
        scene = new Scene( root, 2000 , 1000);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle (KeyEvent event) {

                if( event.getCode() == KeyCode.B )
                    constructPolygon();
                /*for( int i = 0; i < lowerList.size(); i++ ){
                    System.out.println(lowerList.get(i).x + "      "  + lowerList.get(i).y + "     ");
                }*/
                else if( event.getCode() == KeyCode.K){
                    buildKernel();
                }
                else if(event.getCode() == KeyCode.C){
                    TextField field = new TextField();
                    Button b = new Button("Accept");
                    layout = new VBox(10);
                    layout.getChildren().add(field);
                    layout.getChildren().addAll(b);
                    layout.setLayoutX(1000);
                    layout.setLayoutY(500);
                    root.getChildren().add(layout);
                    String textInput = null;
                    b.setOnAction(e -> getTextInput( field) );
                }
                else if(event.getCode() == KeyCode.R){
                    root.getChildren().remove(layout);
                }
            }
        });
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void constructPolygon(){
        // find the two vertices with the highest and lowest axis
        double lowest = 100000;
        Vertex lowestVertex = null;
        Vertex highestVertex = null;
        double highest = -100000;
        lowestY = 1000000;
        highestY = -1000000;
        highestX = -1000000;
        lowestX = 1000000;
        for( int i = 0; i < vertexList.size(); i++  ){
            if( lowest > vertexList.get(i).x ){
                lowest = vertexList.get(i).x;
                lowestVertex = vertexList.get(i);
            }
            else if( highest < vertexList.get(i).x ){
                highest = vertexList.get(i).x;
                highestVertex = vertexList.get(i);
            }

        }
        //find the lowest highets x,y in terms of circle
        for(int i = 0; i < vertexList.size(); i++ ){
            if( lowestY > vertexList.get(i).getCircleY()){
                lowestY = vertexList.get(i).getCircleY();
            }
            if( highestY < vertexList.get(i).getCircleY()){
                highestY = vertexList.get(i).getCircleY();
            }
            if( lowestX > vertexList.get(i).getCircleX()){
                lowestX = vertexList.get(i).getCircleX();
            }
            if( highestX < vertexList.get(i).getCircleX()){
                highestX = vertexList.get(i).getCircleX();
            }
        }
        for(int i = 0; i < vertexList.size(); i++ ){
            if( (highestVertex != vertexList.get(i) && lowestVertex != vertexList.get(i))  && vertexList.size() > 3){
                if (leftTest(lowestVertex, highestVertex, vertexList.get(i)))
                    upperList.add(vertexList.get(i));
                else
                    lowerList.add(vertexList.get(i));
            }
        }
        ArrayList<Vertex> sortedUpperList = new ArrayList<Vertex>();
        ArrayList<Vertex> sortedLowerList = new ArrayList<Vertex>();
        sortedLowerList = quickSort(lowerList);
        sortedUpperList = quickSort(upperList);
       // System.out.println("inside lowerlist");
        for( int i = 0; i < sortedLowerList.size(); i ++ ){
            //System.out.println(sortedLowerList.get(i).x + "      " + sortedLowerList.get(i).y);
        }
        //System.out.println("outside lowerlist");
        //System.out.println("inside upperlist");
        for( int i = 0; i < sortedUpperList.size(); i ++ ){
            //System.out.println(sortedUpperList.get(i).x +  "      " + sortedUpperList.get(i).y);
        }

        // connecting the upper vertices
        Line firstLine1 = new Line(lowestVertex.getCircleX() , lowestVertex.getCircleY() , sortedUpperList.get(0).getCircleX(),sortedUpperList.get(0).getCircleY());
        root.getChildren().add(firstLine1);
        for( int i = 0; i < sortedUpperList.size()-1; i++ ){
            Vertex v1;
            Vertex v2;
            v1 = sortedUpperList.get(i);
            v2 = sortedUpperList.get(i+1);
            Line line = new Line(v1.getCircleX(),v1.getCircleY(),v2.getCircleX(),v2.getCircleY());
            root.getChildren().add(line);
        }
        Line lastLine1 = new Line(sortedUpperList.get(sortedUpperList.size()-1).getCircleX(),sortedUpperList.get(sortedUpperList.size()-1).getCircleY(),highestVertex.getCircleX() , highestVertex.getCircleY());
        root.getChildren().add(lastLine1);

        // connecting the lower vertices
        Line firstLine2 = new Line(lowestVertex.getCircleX() , lowestVertex.getCircleY() , sortedLowerList.get(0).getCircleX(),sortedLowerList.get(0).getCircleY());
        root.getChildren().add(firstLine2);
        for( int i = 0; i < sortedLowerList.size()-1; i++ ){
            Vertex v1;
            Vertex v2;
            v1 = sortedLowerList.get(i);
            v2 = sortedLowerList.get(i+1);
            Line line = new Line(v1.getCircleX(),v1.getCircleY(),v2.getCircleX(),v2.getCircleY());
            root.getChildren().add(line);
        }
        Line lastLine2 = new Line(sortedLowerList.get(sortedLowerList.size()-1).getCircleX(),sortedLowerList.get(sortedLowerList.size()-1).getCircleY(),highestVertex.getCircleX() , highestVertex.getCircleY());
        root.getChildren().add(lastLine2);
        /*
        System.out.println("testestest");
        System.out.println(sortedUpperList.get(sortedUpperList.size()-1).x + "helllooooooo") ;
        System.out.println(sortedLowerList.get(sortedLowerList.size()-1).x);
        */
        //add the most right
        polygonListCounterClockwise.add(highestVertex);
        for( int i = sortedUpperList.size() - 1; i >= 0;i-- ){
            polygonListCounterClockwise.add(sortedUpperList.get(i));
        }
        polygonListCounterClockwise.add(lowestVertex);
        for(int i = 0; i < sortedLowerList.size(); i++ ){
            polygonListCounterClockwise.add(sortedLowerList.get(i));
        }
        //System.out.println("counting polygon vertices ");
        for(int i =0; i < polygonListCounterClockwise.size(); i++ ){
            //System.out.println(polygonListCounterClockwise.get(i).x + "  " + polygonListCounterClockwise.get(i).y);
        }
        //System.out.println("end of counting polygon vertices ");
         for(int i =0; i < polygonListCounterClockwise.size() - 1; i++ ){
             Line sortedLines = new Line(polygonListCounterClockwise.get(i).x , polygonListCounterClockwise.get(i).y , polygonListCounterClockwise.get(i+1).x, polygonListCounterClockwise.get(i+1).y);
             polygonLines.add(sortedLines);
         }
         Line finalLine = new Line(polygonListCounterClockwise.get(polygonListCounterClockwise.size()-1).x , polygonListCounterClockwise.get(polygonListCounterClockwise.size()-1).y, polygonListCounterClockwise.get(0).x, polygonListCounterClockwise.get(0).y);
         polygonLines.add(finalLine);
    }
    public void autobuild(){
        Vertex v1 = new Vertex(-5 , 4 );
        Vertex v2 = new Vertex( 5 ,4 );
        Vertex v3 = new Vertex(0 , 20 );
        Vertex v4 = new Vertex( 20,15);
        Vertex v5  = new Vertex(11 , 2);
        Vertex v6 = new Vertex(  0, -10);
        vertexList.add(v1);
        vertexList.add(v2);
        vertexList.add(v3);
        vertexList.add(v4);
        vertexList.add(v5);
        vertexList.add(v6);
        root.getChildren().add(v1.getCircle());
        root.getChildren().add(v2.getCircle());
        root.getChildren().add(v3.getCircle());
        root.getChildren().add(v4.getCircle());
        root.getChildren().add(v5.getCircle());
        root.getChildren().add(v6.getCircle());

    }
    public void buildPseudo(int totalVertex) throws InterruptedException {
        for(int i = 0; i < totalVertex; i++ ){
            double x = Math.random() * 2000;
            double y = Math.random() * 1000;
            Circle c = new Circle(x , y , 2.5 , Color.BLACK);
            Vertex v = new Vertex(c);
            vertexList.add(v);
            root.getChildren().add(c);
        }

    }
    public static boolean leftTest( Vertex p1 , Vertex p2, Vertex p3 ){
        double first = p1.x * (p2.y - p3.y);
        double second = (-p1.y)*(p2.x - p3.x);
        double third = (p2.x*p3.y) - (p2.y * p3.x);
        if ( first + second + third >= 0)
            return true;
        else
            return false;
    }

    public static ArrayList<Vertex> quickSort(ArrayList<Vertex> list)
    {
        if (list.isEmpty())
            return list; // start with recursion base case
        ArrayList<Vertex> sorted;  // this shall be the sorted list to return, no need to initialise
        ArrayList<Vertex> smaller = new ArrayList<Vertex>(); // Vertices smaller than pivot
        ArrayList<Vertex> greater = new ArrayList<Vertex>(); // Vertices greater than pivot
        Vertex pivot = list.get(0);  // first Vehicle in list, used as pivot
        int i;
        Vertex j;     // Variable used for Vehicles in the loop
        for (i=1;i<list.size();i++)
        {
            j=list.get(i);
            if (pivot.x >= j.x)   // make sure Vehicle has proper compareTo method
                smaller.add(j);
            else
                greater.add(j);
        }
        smaller=quickSort(smaller);  // capitalise 's'
        greater=quickSort(greater);  // sort both halfs recursively
        smaller.add(pivot);          // add initial pivot to the end of the (now sorted) smaller Vehicles
        smaller.addAll(greater);     // add the (now sorted) greater Vehicles to the smaller ones (now smaller is essentially your sorted list)
        sorted = smaller;            // assign it to sorted; one could just as well do: return smaller

        return sorted;
    }
    public void buildKernel() throws  IllegalArgumentException {
        // first traverse the polygon, find the first reflex, if no reflex paint the whole polygon.
        int currentVertex = -1;
        int startVertex = -1;
        boolean startEdgeUnbounded = true; // if there is infinite at mostleft
        boolean endEdgeUnbounded = true;  // if there is infinite at mostright
        boolean circular = false;  // if the kernel polygon is circular
        int unboundedStartLineIndex = -1;
        int unboundedEndLineIndex  = -1;
        for (int i = 0; i < polygonListCounterClockwise.size(); i++) {
            // normal step
            if (i != 0 && i != polygonListCounterClockwise.size() - 1) {
                if (!leftTest(polygonListCounterClockwise.get(i - 1), polygonListCounterClockwise.get(i), polygonListCounterClockwise.get(i + 1))) {
                    startVertex = i;
                    //System.out.println(polygonListCounterClockwise.get(i).x + "  " + polygonListCounterClockwise.get(i).y);
                    break;
                }
            }
            //  end and start step
            else {
                 if( i == 0 && !leftTest( polygonListCounterClockwise.get(polygonListCounterClockwise.size()-1) , polygonListCounterClockwise.get(0) , polygonListCounterClockwise.get(1))){
                         startVertex = 0;
                         break;
                 }
                 else if( i == polygonListCounterClockwise.size() - 1 && !leftTest( polygonListCounterClockwise.get(polygonListCounterClockwise.size()-2) , polygonListCounterClockwise.get(polygonListCounterClockwise.size()-1) , polygonListCounterClockwise.get(0))){
                        startVertex = polygonListCounterClockwise.size()-1;
                        break;
                 }
            }
        }
        //polygonListCounterClockwise.add(polygonListCounterClockwise.get(0)); // now polygonlist circular


        // construct kernel K1
        currentVertex = startVertex;
        Ray startRay = new Ray(polygonListCounterClockwise.get(startVertex ) , polygonListCounterClockwise.get(startVertex +1 ),3 );
        Ray endRay = new Ray(polygonListCounterClockwise.get(startVertex) , polygonListCounterClockwise.get(startVertex - 1),3);
        Line k1 = startRay.getRay();
        Line k2 = endRay.getRay();
        k1.setStroke(Color.CYAN);
        k2.setStroke(Color.CYAN);
        root.getChildren().add(k1);
        root.getChildren().add(k2);
        kernel.add(k1);
        kernel.add(polygonListCounterClockwise.get(startVertex));
        kernel.add(k2);
        // determine points F and L
        Circle circlef , circlel;
        Vertex f = new Vertex( circlef = new Circle(k1.getEndX() , k1.getEndY(), 2.5, Color.CRIMSON));
        Vertex l = new Vertex( circlel = new Circle(k2.getEndX() , k2.getEndY(), 2.5, Color.CRIMSON));
        int indexf =2;
        int indexl =0;
        root.getChildren().addAll(circlef,circlel);

        System.out.println("lowest point in y == " + lowestY);
        System.out.println("highest point in y == " + highestY);
        System.out.println("lowest point in x == " + lowestX);
        System.out.println("highest point in x == " + highestX);
        ArrayList<Vertex> temp = new ArrayList<>();
        for(int i =0; i < startVertex; i++ ){
            temp.add(polygonListCounterClockwise.get(i));
        }
        for(int i = 0; i < startVertex; i++ ){
            polygonListCounterClockwise.remove(0);
        }
        polygonListCounterClockwise.addAll(temp);
        //System.out.println("enter test test ");
        for( int i = 0; i < polygonListCounterClockwise.size(); i++ ){
            //System.out.println(polygonListCounterClockwise.get(i).x + "    " + polygonListCounterClockwise.get(i).y);
        }
       // System.out.println("enter test test ");
        // algortihm starts here
        //
        //
        //
        currentVertex = startVertex;
        polygonListCounterClockwise.add(polygonListCounterClockwise.get(startVertex));
        Vertex w1 = null;
        Vertex w2 = null;
        int v1Index = -1;
        int v2Index = -1;
        long startTime = System.currentTimeMillis();
            for (int i = 1; i < polygonListCounterClockwise.size() - 1; i++) {
                System.out.println(" the " + i+ "." + " element of polygon ");
                // if the vertex is reflex
                if (!leftTest(polygonListCounterClockwise.get(i - 1), polygonListCounterClockwise.get(i), polygonListCounterClockwise.get(i + 1))) {
                    //1.1
                    if (leftTest(polygonListCounterClockwise.get(i), f, polygonListCounterClockwise.get(i + 1))) { // ustunde olma sarti eklenmeli
                        System.out.println("entered 1.1");
                        Ray i_vi_vi1 = new Ray(polygonListCounterClockwise.get(i+1), polygonListCounterClockwise.get(i ), 5);
                        Line les = i_vi_vi1.getRay();
                        les.setStroke(Color.BLUEVIOLET);
                        //root.getChildren().addAll(les);
                        // search through kernel if the  line intersects
                        int tempIndex = indexf;
                        if( tempIndex < 0 ){
                            tempIndex =0;
                        }
                        for (int j = 0; j < kernel.size(); j++) {
                            if (kernel.get(tempIndex).getClass().getName() == "javafx.scene.shape.Line") {
                                Line l1 = (Line) kernel.get(tempIndex);
                                Ray ray1 = new Ray(polygonListCounterClockwise.get(i + 1), polygonListCounterClockwise.get(i), 5);
                                Line l2 = ray1.getRay();
                                if (doesLinesIntersect(l1, l2)) {
                                        w1 = findIntersection(l1, l2);
                                        v1Index = tempIndex;
                                        break;
                                }
                            }
                            if (tempIndex == indexl) {
                                System.out.println("there is no kernel to be found ");
                                Alert a = new Alert(Alert.AlertType.ERROR);
                                a.show();
                                //System.exit(0);
                            }
                            tempIndex++;
                            if (tempIndex == kernel.size()) {
                                tempIndex = 0;
                            }
                        }
                        tempIndex = indexf;
                        if( tempIndex < 0 ){
                            tempIndex = 0;
                        }
                        for (int j = 0; j < kernel.size(); j++) {
                            if (kernel.get(tempIndex).getClass().getName() == "javafx.scene.shape.Line") {
                                Line l1 = (Line) kernel.get(tempIndex);
                                Ray ray1 = new Ray(polygonListCounterClockwise.get(i + 1), polygonListCounterClockwise.get(i), 5);
                                Line l2 = ray1.getRay();
                                if (doesLinesIntersect(l1, l2)) {
                                    Vertex vTemp;
                                    vTemp = findIntersection(l1, l2);
                                    if (vTemp.getCircleX() != w1.getCircleX() && vTemp.getCircleY() != w1.getCircleY()) {
                                        w2 = findIntersection(l1, l2);
                                        System.out.println("there is an intersection v2 in " + w2.x + "   " + w2.y);
                                        v2Index = tempIndex;
                                        break;
                                    }
                                }
                            }
                            tempIndex--;
                            if (tempIndex <= 0) {
                                break;
                            }
                        }
                        if (w1 != null && w2 != null) {
                            System.out.println("two intersection found in 2.1");
                            int minIndex = Math.min(v1Index, v2Index);
                            int maxIndex = Math.max(v1Index, v2Index);
                            Line ltemp1 = (Line) kernel.get(minIndex);
                            Line ltemp2 = (Line) kernel.get(maxIndex);
                            int tempMin = minIndex;
                            /*for (int j = minIndex; j < maxIndex; j++) {
                                root.getChildren().remove(kernel.get(tempMin));
                            }*/
                            for(int j = 0; j < minIndex ; j++){
                                if( kernel.get(0).getClass().getName() == "Vertex"){
                                    Circle c = ((Vertex)kernel.get(0)).getCircle();
                                    root.getChildren().remove(c);
                                }
                                else{
                                    root.getChildren().remove(kernel.get(0));
                                }
                                kernel.remove(0);
                            }
                            for( int j = kernel.size(); j >maxIndex ; j-- ){
                                if( kernel.get(kernel.size()-1).getClass().getName() == "Vertex"){
                                    Circle c = ((Vertex)kernel.get(kernel.size()-1)).getCircle();
                                    root.getChildren().remove(c);
                                }
                                else{
                                    root.getChildren().remove(kernel.get(kernel.size()-1));
                                }
                                kernel.remove(kernel.get(kernel.size()-1));
                            }
                            Line l1 = new Line(w1.getCircleX(), w1.getCircleY(), w2.getCircleX(), w2.getCircleY());
                            kernel.add(minIndex, w1);
                            kernel.add( minIndex,l1);
                            kernel.add( minIndex ,w2);
                            Circle w1Circle = new Circle(w1.getCircleX(), w1.getCircleY(), 2.5, Color.AZURE);
                            Circle w2Circle = new Circle(w2.getCircleX(), w2.getCircleY(), 2.5, Color.AZURE);
                            root.getChildren().add(w1Circle);
                            root.getChildren().add(w2Circle);
                            l1.setStroke(Color.CYAN);
                            root.getChildren().add(l1);
                        } else if (w1 != null && w2 == null) {
                            System.out.println("only one intersection found in this pass");
                            Line l1 = null;
                            Line l2 = null;
                            for(int j = 0; j < kernel.size(); j++ ){
                                if(kernel.get(j).getClass().getName() == "javafx.scene.shape.Line"){
                                    l1 = (Line)kernel.get(j);
                                }
                            }
                            for( int j = 0; j < kernel.size(); j++ ){
                                if(kernel.get(kernel.size()-1-j).getClass().getName() == "javafx.scene.shape.Line"){
                                    l2 = (Line)kernel.get(kernel.size()-1-j);
                                }
                            }
                            double slope1 = (l1.getEndY() - l1.getStartY()) / (l1.getEndX() - l1.getStartX());
                            double slope2 = (l2.getEndY() - l2.getStartY()) / (l2.getEndX() - l2.getStartX());
                            Ray ray1 = new Ray(polygonListCounterClockwise.get(i+1), polygonListCounterClockwise.get(i), 5);
                            double slopeRay = -ray1.slope;
                            System.out.println("slope1 = " + slope1 + "slope2 = " + slope2 + " slopeRay = " + slopeRay );
                            if ((slope1 >= slopeRay && slope2 <= slopeRay) || (slope1 <= slopeRay && slope2 >= slopeRay)) {

                                for (int j = kernel.size(); j > v1Index; j--) {
                                    //root.getChildren().remove(kernel.get(kernel.size()-1));
                                }
                                Ray ray2 = new Ray(polygonListCounterClockwise.get(i + 1), polygonListCounterClockwise.get(i), 5);
                                Line l3 = ray2.getRay();
                                kernel.add(w1);
                                kernel.add(l3);
                                l3.setStroke(Color.CYAN);
                                root.getChildren().addAll(l3, w1.getCircle());
                                circular = false;
                            } else {
                                System.out.println("slope is not in between two rays");
                                tempIndex = kernel.size() - 1;
                                for (int j = 0; j < kernel.size(); j++) {
                                    if (kernel.get(tempIndex).getClass().getName() == "javafx.scene.shape.Line") {
                                        Line l3 = (Line) kernel.get(tempIndex);
                                        Ray ray2 = new Ray(polygonListCounterClockwise.get(i+1 ), polygonListCounterClockwise.get(i), 5);
                                        Line l4 = ray2.getRay();
                                        if (doesLinesIntersect(l3, l4)) {
                                            Vertex vTemp;
                                            vTemp = findIntersection(l3, l4);
                                            if (vTemp.getCircleX() != w1.getCircleX() && vTemp.getCircleY() != w1.getCircleY() ) {
                                                w2 = findIntersection(l3, l4);
                                                System.out.println("intersection found at v2 " + w2.x + " " + w2.y + " " );
                                                v2Index = tempIndex;
                                                break;
                                            }
                                        }
                                    }
                                    tempIndex--;
                                    if (tempIndex == kernel.size()) {
                                        tempIndex = 0;
                                    }
                                }
                                if( w2 != null) {
                                    int minIndex = Math.min(v1Index, v2Index);
                                    int maxIndex = Math.max(v2Index, v1Index);
                                    System.out.println(minIndex);
                                    System.out.println(maxIndex);
                                    if (minIndex < 0) {
                                        minIndex = 0;
                                    }
                                    for(int j = 0; j < minIndex ; j++ ){
                                        if (kernel.get(minIndex).getClass().getName() == "Vertex") {
                                            Vertex vtemp = (Vertex) kernel.get(0);
                                            Circle c = vtemp.circle;
                                            root.getChildren().remove(c);
                                        } else {
                                            root.getChildren().remove(kernel.get(0));
                                        }
                                        kernel.remove(0);
                                    }
                                    for(int j = 0; j < kernel.size() - maxIndex ; j++ ){
                                        if (kernel.get(minIndex).getClass().getName() == "Vertex") {
                                            Vertex vtemp = (Vertex) kernel.get(kernel.size() - 1);
                                            Circle c = vtemp.circle;
                                            root.getChildren().remove(c);
                                        } else {
                                            root.getChildren().remove(kernel.get(kernel.size() - 1 ));
                                        }
                                        kernel.remove(kernel.size() - 1);
                                    }
                                    /*for (int j = minIndex; j < maxIndex; j++) {
                                        if (kernel.get(minIndex).getClass().getName() == "Vertex") {
                                            Vertex vtemp = (Vertex) kernel.get(minIndex);
                                            Circle c = vtemp.circle;
                                            root.getChildren().remove(c);
                                        } else {
                                            root.getChildren().remove(kernel.get(minIndex));
                                        }
                                        kernel.remove(minIndex);
                                    }*/
                                    System.out.println(w1);
                                    System.out.println(w2);
                                    Line templ = new Line(w1.getCircleX(), w1.getCircleY(), w2.getCircleX(), w2.getCircleY());
                                    Circle tempc1 = w1.circle;
                                    Circle tempc2 = w2.circle;
                                    kernel.add(w1);
                                    kernel.add(templ);
                                    kernel.add(w2);
                                    templ.setStroke(Color.CYAN);
                                    root.getChildren().add(tempc1);
                                    root.getChildren().add(tempc2);
                                    root.getChildren().add(templ);
                                    circular = true;
                                }
                                else{
                                    for (int j = kernel.size(); j > v1Index + 1; j--) {
                                        root.getChildren().remove(j);
                                    }
                                    Ray ray2 = new Ray(w1, polygonListCounterClockwise.get(i + 1), 5);
                                    Line l3 = ray2.getRay();
                                    Vertex vTemp2 = null;
                                    for(int j = 0; j < kernel.size() ; j++ ){
                                        if( kernel.get(kernel.size() - 1 - j).getClass().getName()== "Vertex"){
                                            vTemp2 = (Vertex)kernel.get(kernel.size()-j-1);
                                            break;
                                        }
                                    }
                                    System.out.println(vTemp2);
                                    System.out.println(w1);
                                    Line lTemp = new Line(vTemp2.getCircleX(),vTemp2.getCircleY(),w1.getCircleX(),w1.getCircleY());
                                    kernel.remove(kernel.size()-1);
                                    kernel.add(lTemp);
                                    kernel.add(w1);
                                    kernel.add(l3);
                                    lTemp.setStroke(Color.CYAN);
                                    l3.setStroke(Color.CYAN);
                                    root.getChildren().addAll(w1.getCircle(), l3 , lTemp);
                                }
                            }
                        }
                        // selecting f and l
                        //f
                        if (w2 == null) {
                            Ray ray2 = new Ray(polygonListCounterClockwise.get(i + 1), polygonListCounterClockwise.get(i), 5);
                            Line l2 = ray2.getRay();
                            Circle c;
                            Vertex temp1 = new Vertex(c = new Circle(l2.getEndX(), l2.getEndY(), 2.5, Color.AZURE));
                            f = temp1;
                            indexf = v1Index;
                        } else {
                            f = w2;
                            indexf = v2Index;
                        }
                        //l
                        tempIndex = indexl;
                        // find the vertex to start
                        if (kernel.get(indexl).getClass().getName() != "Vertex") {
                            if (indexl != kernel.size() - 1) {
                                tempIndex++;
                            } else {
                                tempIndex = 0;
                            }
                        }
                        for (int j = 0; j < kernel.size(); j++) {
                            Vertex wu = null;
                            Vertex wu1 = null;
                            int tempIndex2 = tempIndex;
                            while (true) {
                                if (tempIndex2 != tempIndex && kernel.get(tempIndex2).getClass().getName() == "Vertex") {
                                    wu1 = (Vertex) kernel.get(tempIndex2);
                                    break;
                                }
                                tempIndex2++;
                                if (tempIndex2 >= kernel.size()) {
                                    tempIndex2 = 0;
                                }
                                if (tempIndex == tempIndex2)
                                    break;
                            }
                            if (wu != null && wu1 != null) {
                                if (!leftTest(polygonListCounterClockwise.get(i + 1), wu1, wu)) {
                                    l = wu;
                                    indexl = tempIndex;
                                    break;
                                }
                            }
                            tempIndex++;
                            if (tempIndex == kernel.size()) {
                                tempIndex = 0;
                            }
                        }

                    }
                    //1.2
                    else {
                        System.out.println("entered 1.2");
                        //determine fi+1
                        int tempIndex = indexf;
                        for (int j = 0; j < kernel.size(); j++) {
                            Vertex wu = null;
                            Vertex wu1 = null;
                            int tempIndex2 = tempIndex;
                            while (true) {
                                if (tempIndex2 != tempIndex && kernel.get(tempIndex2).getClass().getName() == "Vertex") {
                                    wu1 = (Vertex) kernel.get(tempIndex2);
                                    break;
                                }
                                tempIndex2++;
                                if (tempIndex2 >= kernel.size()) {
                                    tempIndex2 = 0;
                                }
                                if (tempIndex == tempIndex2)
                                    break;
                            }
                            if (wu != null && wu1 != null) {
                                if (leftTest(polygonListCounterClockwise.get(i + 1), wu1, wu)) {
                                    f = wu;
                                    indexf = tempIndex;
                                    break;
                                }
                            }
                            tempIndex++;
                            if (tempIndex == kernel.size()) {
                                tempIndex = 0;
                            }
                        }
                        // determine l
                        tempIndex = indexl;
                        for (int j = 0; j < kernel.size(); j++) {
                            Vertex wu = null;
                            Vertex wu1 = null;
                            int tempIndex2 = tempIndex;
                            while (true) {
                                if (tempIndex2 != tempIndex && kernel.get(tempIndex2).getClass().getName() == "Vertex") {
                                    wu1 = (Vertex) kernel.get(tempIndex2);
                                    break;
                                }
                                tempIndex2++;
                                if (tempIndex2 >= kernel.size()) {
                                    tempIndex2 = 0;
                                }
                                if (tempIndex == tempIndex2)
                                    break;
                            }
                            if (wu != null && wu1 != null) {
                                if (!leftTest(polygonListCounterClockwise.get(i + 1), wu1, wu)) {
                                    l = wu;
                                    indexl = tempIndex;
                                    break;
                                }
                            }
                            tempIndex++;
                            if (tempIndex == kernel.size()) {
                                tempIndex = 0;
                            }
                        }


                    }
                }
                // if the vertex is convex
                else {
                    //2.1
                    if (leftTest(polygonListCounterClockwise.get(i), l, polygonListCounterClockwise.get(i + 1))) {// ustunde olma sarti eklenmeli
                        int tempIndex = indexl;
                        System.out.println("entered 2.1");
                        //System.out.println("indexl is   " + indexl);
                        for (int j = 0; j < kernel.size(); j++) {
                            System.out.println("element no " + tempIndex);
                            if (kernel.get(tempIndex).getClass().getName() == "javafx.scene.shape.Line") {
                                Line l1 = (Line) kernel.get(tempIndex);
                                Ray ray1 = new Ray(polygonListCounterClockwise.get(i), polygonListCounterClockwise.get(i + 1), 5);
                                Line l2 = ray1.getRay();
                                l2.setStroke(Color.BLUEVIOLET);
                                //root.getChildren().add(l2);
                                System.out.println(" " + polygonListCounterClockwise.get(i).getCircleX() + " " + polygonListCounterClockwise.get(i+1).getCircleX());
                                System.out.println("boundaries of polygon == " + l2.getStartX() + " " + l2.getEndX() + " " + l2.getStartY() + " " + l2.getEndY() + " " + ray1.slope);
                                System.out.println("boundaries of kernel == " + l1.getStartX() + " " + l1.getEndX() + " " + l1.getStartY() + " " + l1.getEndY());
                                if (doesLinesIntersect(l1, l2)) {
                                    System.out.println("enter intersection ");
                                    w1 = findIntersection(l1, l2);
                                    System.out.println("2.1 lines intersect but not sure its in boundaries " + w1.getCircleX() + " " + w1.getCircleY());
                                    v1Index = tempIndex;
                                    System.out.println("intersection found at " + w1.getCircleX() + "  " + w1.getCircleY());
                                    System.out.println("v1index is " + v1Index);
                                    System.out.println("exit intersection ");
                                    break;
                                }
                            }
                            //System.out.println("tempindex  is   " + indexl);
                            if (tempIndex == indexf) {
                                System.out.println("there is no kernel in this polygon provided by 2.1 ");
                                Alert a = new Alert(Alert.AlertType.ERROR);
                                a.show();
                            }
                            tempIndex--;
                            if (tempIndex <= 0) {
                                tempIndex = kernel.size() - 1;
                            }
                        }
                        System.out.println("exit looking for first intersection ");
                        System.out.println("enter second intersection");
                        tempIndex = indexl;
                        for (int j = 0; j < kernel.size(); j++) {
                            if (kernel.get(tempIndex).getClass().getName() == "javafx.scene.shape.Line") {
                                Line l1 = (Line) kernel.get(tempIndex);
                                Ray ray1 = new Ray(polygonListCounterClockwise.get(i), polygonListCounterClockwise.get(i + 1), 5);
                                Line l2 = ray1.getRay();
                                if (doesLinesIntersect(l1, l2)) {
                                    Vertex vTemp = null;
                                    vTemp = findIntersection(l1, l2);
                                    if ( vTemp.getCircleY() != w1.getCircleY() && vTemp.getCircleX() != w1.getCircleX()) {
                                        w2 = findIntersection(l1, l2);
                                        System.out.println("\n intersection v2 found at "  + w2.x + " " + w2.y + " \n");
                                        v2Index = tempIndex;
                                        break;
                                    }
                                }
                            }
                            tempIndex++;
                            if (tempIndex == kernel.size()) {
                                tempIndex = 0;
                            }
                        }
                        System.out.println("exit looking for second intersection ");
                        if (w1 != null && w2 != null) {
                            System.out.println("two intersection found in 2.1");
                            int minIndex = Math.min(v1Index, v2Index);
                            int maxIndex = Math.max(v1Index, v2Index);
                            Line ltemp1 = (Line) kernel.get(minIndex);
                            Line ltemp2 = (Line) kernel.get(maxIndex);
                            int tempMin = minIndex;
                            /*for (int j = minIndex; j < maxIndex; j++) {
                                root.getChildren().remove(kernel.get(tempMin));
                            }*/
                            for(int j = 0; j < minIndex ; j++){
                                if( kernel.get(0).getClass().getName() == "Vertex"){
                                    Circle c = ((Vertex)kernel.get(0)).getCircle();
                                    root.getChildren().remove(c);
                                }
                                else{
                                    root.getChildren().remove(kernel.get(0));
                                }
                                kernel.remove(0);
                            }
                            for( int j = kernel.size(); j >maxIndex ; j-- ){
                                if( kernel.get(kernel.size()-1).getClass().getName() == "Vertex"){
                                    Circle c = ((Vertex)kernel.get(kernel.size()-1)).getCircle();
                                    root.getChildren().remove(c);
                                }
                                else{
                                    root.getChildren().remove(kernel.get(kernel.size()-1));
                                }
                                kernel.remove(kernel.get(kernel.size()-1));
                            }
                            Line l1 = new Line(w1.getCircleX(), w1.getCircleY(), w2.getCircleX(), w2.getCircleY());
                            kernel.add(minIndex, w2);
                            kernel.add( minIndex,l1);
                            kernel.add( minIndex ,w1);
                            Circle w1Circle = new Circle(w1.getCircleX(), w1.getCircleY(), 2.5, Color.AZURE);
                            Circle w2Circle = new Circle(w2.getCircleX(), w2.getCircleY(), 2.5, Color.AZURE);
                            root.getChildren().add(w1Circle);
                            root.getChildren().add(w2Circle);
                            l1.setStroke(Color.CYAN);
                            root.getChildren().add(l1);
                        } else if (w1 != null && w2 == null) {
                            System.out.println("only one intersection found in this pass");
                            Line l1 = null;
                            Line l2 = null;
                            for(int j = 0; j < kernel.size(); j++ ){
                                if(kernel.get(j).getClass().getName() == "javafx.scene.shape.Line"){
                                    l1 = (Line)kernel.get(j);
                                }
                            }
                            for( int j = 0; j < kernel.size(); j++ ){
                                if(kernel.get(kernel.size()-1-j).getClass().getName() == "javafx.scene.shape.Line"){
                                    l2 = (Line)kernel.get(kernel.size()-1-j);
                                }
                            }
                            double slope1 = (l1.getEndY() - l1.getStartY()) / (l1.getEndX() - l1.getStartX());
                            double slope2 = (l2.getEndY() - l2.getStartY()) / (l2.getEndX() - l2.getStartX());
                            Ray ray1 = new Ray(polygonListCounterClockwise.get(i), polygonListCounterClockwise.get(i + 1), 5);
                            double slopeRay = -ray1.slope;
                            System.out.println("slope1 = " + slope1 + "slope2 = " + slope2 + " slopeRay = " + slopeRay );
                            if ((slope1 >= slopeRay && slope2 <= slopeRay) || (slope1 <= slopeRay && slope2 >= slopeRay)) {
                                System.out.println("slope is in between two rays");
                                /*for (int j = kernel.size()-1; j > v1Index; j--) {
                                    if( kernel.get(kernel.size()-1).getClass().getName() == "Vertex") {
                                        Circle c = ((Vertex)kernel.get(kernel.size()-1)).getCircle();
                                        root.getChildren().remove(c);
                                    }
                                    else{
                                        root.getChildren().remove(kernel.get(j));
                                    }
                                    kernel.remove(kernel.size()-1);
                                }

                                Ray ray2 = new Ray(w1, polygonListCounterClockwise.get(i + 1), 3);
                                Line l3 = ray2.getRay();
                                Vertex vTemp2 = null;
                                System.out.println(kernel.size());
                                for(int j = 0 ; j < kernel.size(); j++){
                                    if (kernel.get(kernel.size()-1-j).getClass().getName() == "Vertex"){
                                        vTemp2 = (Vertex)kernel.get(kernel.size()-1-j);
                                        System.out.println(vTemp2 + " entered ");
                                        break;
                                    }
                                    System.out.println(kernel.get(j).getClass().getName() + "is passed ");
                                }
                                if( vTemp2 == null && kernel.size() == 1){
                                    Line templ = (Line)kernel.get(0);
                                    Circle c1 = new Circle(templ.getStartX() , templ.getStartY() , 2.5, Color.CYAN);
                                    Circle c2 = new Circle(templ.getEndX() , templ.getEndY() , 2.5, Color.CYAN);
                                    Vertex v1 = new Vertex(c1);
                                    Vertex v2 = new Vertex(c2);
                                    kernel.add(0,v1);
                                    kernel.add(2,v2);;
                                    root.getChildren().addAll(c1,c2);
                                    vTemp2 = v2;
                                }
                                System.out.println(vTemp2);
                                System.out.println(w1);
                                Line lTemp = new Line(vTemp2.getCircleX(),vTemp2.getCircleY(),w1.getCircleX(),w1.getCircleY());
                                kernel.remove(kernel.size()-1);
                                kernel.add(lTemp);
                                kernel.add(w1);
                                kernel.add(l3);
                                l3.setStroke(Color.CYAN);
                                root.getChildren().addAll(w1.getCircle(), l3 , lTemp);*/
                                for( int j = 0; j <= v1Index; j++){
                                    Line templ1 =null;
                                    if( kernel.get(j).getClass().getName() == "javafx.scene.shape.Line")
                                            templ1 = (Line)kernel.get(j);
                                        if(  j == v1Index){
                                            System.out.println("contains point");
                                            Circle c1 = new Circle(templ1.getEndX() , templ1.getEndY() , 2.5 , Color.CYAN);
                                            Vertex tempv = new Vertex(c1);
                                            Ray tempRay = new Ray(w1 , polygonListCounterClockwise.get(i+1) , 5);
                                            Line templ2 = tempRay.getRay();
                                            kernel.remove(j);
                                            kernel.add(v1Index, w1);
                                            kernel.add(v1Index,templ2);
                                            root.getChildren().add(templ2 );
                                            break;
                                        }


                                }

                            } else {
                                System.out.println("slope is not in between two rays");
                                tempIndex = 0;
                                for (int j = 0; j < kernel.size(); j++) {
                                    if (kernel.get(tempIndex).getClass().getName() == "javafx.scene.shape.Line") {
                                        Line l3 = (Line) kernel.get(tempIndex);
                                        Ray ray2 = new Ray(polygonListCounterClockwise.get(i ), polygonListCounterClockwise.get(i+1), 5);
                                        Line l4 = ray2.getRay();
                                        if (doesLinesIntersect(l3, l4)) {
                                            Vertex vTemp;
                                            vTemp = findIntersection(l3, l4);
                                            if (vTemp.getCircleX() != w1.getCircleX() && vTemp.getCircleY() != w1.getCircleY() ) {
                                                w2 = findIntersection(l3, l4);
                                                System.out.println("intersection found at v2 " + w2.x + " " + w2.y + " " );
                                                v2Index = tempIndex;
                                                break;
                                            }
                                        }
                                    }
                                    tempIndex++;
                                    if (tempIndex == kernel.size()) {
                                        tempIndex = 0;
                                    }
                                }
                                if( w2 != null) {
                                    int minIndex = Math.min(v1Index, v2Index);
                                    int maxIndex = Math.max(v2Index, v1Index);
                                    System.out.println(minIndex);
                                    System.out.println(maxIndex);
                                    if (minIndex < 0) {
                                        minIndex = 0;
                                    }
                                    for(int j = 0; j < minIndex ; j++ ){
                                        if (kernel.get(minIndex).getClass().getName() == "Vertex") {
                                            Vertex vtemp = (Vertex) kernel.get(0);
                                            Circle c = vtemp.circle;
                                            root.getChildren().remove(c);
                                        } else {
                                            root.getChildren().remove(kernel.get(0));
                                        }
                                        kernel.remove(0);
                                    }
                                    for(int j = 0; j < kernel.size() - maxIndex ; j++ ){
                                        if (kernel.get(minIndex).getClass().getName() == "Vertex") {
                                            Vertex vtemp = (Vertex) kernel.get(kernel.size() - 1);
                                            Circle c = vtemp.circle;
                                            root.getChildren().remove(c);
                                        } else {
                                            root.getChildren().remove(kernel.get(kernel.size() - 1 ));
                                        }
                                        kernel.remove(kernel.size() - 1);
                                    }
                                    /*for (int j = minIndex; j < maxIndex; j++) {
                                        if (kernel.get(minIndex).getClass().getName() == "Vertex") {
                                            Vertex vtemp = (Vertex) kernel.get(minIndex);
                                            Circle c = vtemp.circle;
                                            root.getChildren().remove(c);
                                        } else {
                                            root.getChildren().remove(kernel.get(minIndex));
                                        }
                                        kernel.remove(minIndex);
                                    }*/
                                    System.out.println(w1);
                                    System.out.println(w2);
                                    Line templ = new Line(w1.getCircleX(), w1.getCircleY(), w2.getCircleX(), w2.getCircleY());
                                    Circle tempc1 = w1.circle;
                                    Circle tempc2 = w2.circle;
                                    kernel.add(w1);
                                    kernel.add(templ);
                                    kernel.add(w2);
                                    templ.setStroke(Color.CYAN);
                                    root.getChildren().add(tempc1);
                                    root.getChildren().add(tempc2);
                                    root.getChildren().add(templ);
                                    circular = true;
                                }
                                else{
                                    for (int j = kernel.size(); j >= v1Index ; j--) {
                                        root.getChildren().remove(j);
                                    }
                                    Ray ray2 = new Ray(w1, polygonListCounterClockwise.get(i + 1), 3);
                                    Line l3 = ray2.getRay();
                                    Vertex vTemp2 = null;
                                    for(int j = 0; j < kernel.size() ; j++ ){
                                        if( kernel.get(kernel.size() - 1 - j).getClass().getName()== "Vertex"){
                                            vTemp2 = (Vertex)kernel.get(kernel.size()-j-1);
                                            break;
                                        }
                                    }
                                    System.out.println(vTemp2);
                                    System.out.println(w1);
                                    Line lTemp = new Line(vTemp2.getCircleX(),vTemp2.getCircleY(),w1.getCircleX(),w1.getCircleY());
                                    kernel.remove(kernel.size()-1);
                                    kernel.add(lTemp);
                                    kernel.add(w1);
                                    kernel.add(l3);
                                    l3.setStroke(Color.CYAN);
                                    lTemp.setStroke(Color.CYAN);
                                    root.getChildren().addAll(w1.getCircle(), l3 , lTemp);
                                }
                            }
                        }
                        //selection of f and l
                        // select f
                        if (w1 != null && w2 != null) {
                            System.out.println("two intersection");
                            Line l1 = new Line(polygonListCounterClockwise.get(i).getCircleX(), polygonListCounterClockwise.get(i).getCircleY(), w1.getCircleX(), w1.getCircleY());
                            boolean vi1 = containsPoint(polygonListCounterClockwise.get(i + 1).getCircleX(), polygonListCounterClockwise.get(i + 1).getCircleY(), l1);
                            if (vi1) {
                                tempIndex = indexf;
                                for (int j = 0; j < kernel.size(); j++) {
                                    Vertex wu = null;
                                    Vertex wu1 = null;
                                    int tempIndex2 = tempIndex;
                                    while (true) {
                                        if (tempIndex2 != tempIndex && kernel.get(tempIndex2).getClass().getName() == "Vertex") {
                                            wu1 = (Vertex) kernel.get(tempIndex2);
                                            break;
                                        }
                                        tempIndex2++;
                                        if (tempIndex2 >= kernel.size()) {
                                            tempIndex2 = 0;
                                        }
                                        if (tempIndex == tempIndex2)
                                            break;
                                    }
                                    if (wu != null && wu1 != null) {
                                        if (leftTest(polygonListCounterClockwise.get(i + 1), wu1, wu)) {
                                            f = wu;
                                            indexf = tempIndex;
                                            break;
                                        }
                                    }
                                    tempIndex++;
                                    if (tempIndex == kernel.size()) {
                                        tempIndex = 0;
                                    }
                                }
                            } else {
                                f = w1;
                                indexf = v1Index;
                            }
                            //select l 2.1.1
                            Line l2 = new Line(polygonListCounterClockwise.get(i).getCircleX(), polygonListCounterClockwise.get(i).getCircleY(), w2.getCircleX(), w2.getCircleY());
                            boolean vi2 = containsPoint(polygonListCounterClockwise.get(i + 1).getCircleX(), polygonListCounterClockwise.get(i + 1).getCircleY(), l1);
                            if (vi2) {
                                l = w2;
                            } else {
                                tempIndex = v2Index;
                                for (int j = 0; j < kernel.size(); j++) {
                                    Vertex wu = null;
                                    Vertex wu1 = null;
                                    int tempIndex2 = tempIndex;
                                    while (true) {
                                        if (tempIndex2 != tempIndex && kernel.get(tempIndex2).getClass().getName() == "Vertex") {
                                            wu1 = (Vertex) kernel.get(tempIndex2);
                                            break;
                                        }
                                        tempIndex2++;
                                        if (tempIndex2 >= kernel.size()) {
                                            tempIndex2 = 0;
                                        }
                                        if (tempIndex == tempIndex2)
                                            break;
                                    }
                                    if (wu != null && wu1 != null) {
                                        if (!leftTest(polygonListCounterClockwise.get(i + 1), wu1, wu)) {
                                            l = wu;
                                            indexl = tempIndex;
                                            break;
                                        }
                                    }
                                    tempIndex++;
                                    if (tempIndex == kernel.size()) {
                                        tempIndex = 0;
                                    }
                                }
                            }
                        }
                        //2.1.2
                        else if (w1 != null && w2 == null) {
                            // select f
                            Line l1 = new Line(polygonListCounterClockwise.get(i).getCircleX(), polygonListCounterClockwise.get(i).getCircleY(), w1.getCircleX(), w1.getCircleY());
                            boolean vi1 = containsPoint(polygonListCounterClockwise.get(i + 1).getCircleX(), polygonListCounterClockwise.get(i + 1).getCircleY(), l1);
                            if (vi1) {
                                tempIndex = indexf;
                                for (int j = 0; j < kernel.size(); j++) {
                                    Vertex wu = null;
                                    Vertex wu1 = null;
                                    int tempIndex2 = tempIndex;
                                    while (true) {
                                        if (tempIndex2 != tempIndex && kernel.get(tempIndex2).getClass().getName() == "Vertex") {
                                            wu1 = (Vertex) kernel.get(tempIndex2);
                                            break;
                                        }
                                        tempIndex2++;
                                        if (tempIndex2 >= kernel.size()) {
                                            tempIndex2 = 0;
                                        }
                                        if (tempIndex == tempIndex2)
                                            break;
                                    }
                                    if (wu != null && wu1 != null) {
                                        if (leftTest(polygonListCounterClockwise.get(i + 1), wu1, wu)) {
                                            f = wu;
                                            indexf = tempIndex;
                                            break;
                                        }
                                    }
                                    tempIndex++;
                                    if (tempIndex == kernel.size()) {
                                        tempIndex = 0;
                                    }
                                }
                            } else {
                                f = w1;
                            }
                            // select l
                            // dusunecem
                            Ray ray2 = new Ray(polygonListCounterClockwise.get(i), polygonListCounterClockwise.get(i + 1), 3);
                            Line l2 = ray2.getRay();
                            Circle c;
                            Vertex temp1 = new Vertex(c = new Circle(l2.getEndX(), l2.getEndY(), 2.5, Color.AZURE));
                            f = temp1;
                            indexf = v1Index;
                        }

                    }
                    //2.2
                    else {
                        //select f
                        System.out.println("entered 2.2");
                        int tempIndex = indexf;
                        if (tempIndex  < 0 ){
                            tempIndex = 0;
                        }
                        for (int j = 0; j < kernel.size(); j++) {
                            Vertex wu = null;
                            Vertex wu1 = null;
                            if (kernel.get(tempIndex).getClass().getName() == "Vertex") {
                                wu = (Vertex) kernel.get(tempIndex);
                                int tempIndex2 = tempIndex;
                                while (true) {
                                    if (tempIndex2 != tempIndex && kernel.get(tempIndex2).getClass().getName() == "Vertex") {
                                        wu1 = (Vertex) kernel.get(tempIndex2);
                                        break;
                                    }
                                    tempIndex2++;
                                    if (tempIndex2 >= kernel.size()) {
                                        tempIndex2 = 0;
                                    }
                                    if (tempIndex == tempIndex2)
                                        break;
                                }
                            }
                            if (wu != null && wu1 != null) {
                                if (leftTest(polygonListCounterClockwise.get(i + 1), wu1, wu)) {
                                    f = wu;
                                    indexf = tempIndex;
                                    System.out.println("f updated ");
                                    break;
                                }
                            }
                            tempIndex++;
                            if (tempIndex == kernel.size()) {
                                tempIndex = 0;
                            }
                        }
                        //select l
                        //dusunecem
                        if (circular) {
                            tempIndex = indexl;
                            // find the vertex to start
                            if (kernel.get(indexl).getClass().getName() != "Vertex") {
                                if (indexl != kernel.size() - 1) {
                                    tempIndex++;
                                } else {
                                    tempIndex = 0;
                                }
                            }
                            for (int j = 0; j < kernel.size(); j++) {
                                Vertex wu = null;
                                Vertex wu1 = null;
                                int tempIndex2 = tempIndex;
                                while (true) {
                                    if (tempIndex2 != tempIndex && kernel.get(tempIndex2).getClass().getName() == "Vertex") {
                                        wu1 = (Vertex) kernel.get(tempIndex2);
                                        break;
                                    }
                                    tempIndex2++;
                                    if (tempIndex2 >= kernel.size()) {
                                        tempIndex2 = 0;
                                    }
                                    if (tempIndex == tempIndex2)
                                        break;
                                }
                                if (wu != null && wu1 != null) {
                                    if (!leftTest(polygonListCounterClockwise.get(i + 1), wu1, wu)) {
                                        l = wu;
                                        indexl = tempIndex;
                                        System.out.println("l updated ");
                                        break;
                                    }
                                }
                                tempIndex++;
                                if (tempIndex == kernel.size()) {
                                    tempIndex = 0;
                                }
                            }

                        }
                    }
                }
                w1 = null;
                w2 = null;
                v1Index = -1;
                v2Index = -1;
                System.out.println("\nicerikler");
                for (int j = 0; j < kernel.size(); j++) {
                    System.out.println(kernel.get(j).getClass().getName());
                    if( kernel.get(j).getClass().getName() == "Vertex"){
                        if( j < kernel.size() - 1 ){
                            if( kernel.get(j+1).getClass().getName() == "Vertex"){
                                Vertex w1temp = (Vertex)(kernel.get(j));
                                Vertex w2temp = (Vertex)(kernel.get(j+1));
                                Line tmpline = new Line( w1temp.getCircleX() , w1temp.getCircleY() , w2temp.getCircleX() , w2temp.getCircleY());
                                tmpline.setStroke(Color.CYAN);
                                root.getChildren().add(tmpline);
                                kernel.add(j+1 , tmpline);
                            }
                        }
                        else if( j == kernel.size()-1){
                            if( kernel.get(0).getClass().getName() == "Vertex"){
                                Vertex w1temp = (Vertex)(kernel.get(j));
                                Vertex w2temp = (Vertex)(kernel.get(0));
                                Line tmpline = new Line( w1temp.getCircleX() , w1temp.getCircleY() , w2temp.getCircleX() , w2temp.getCircleY());
                                tmpline.setStroke(Color.CYAN);
                                root.getChildren().add(tmpline);
                                kernel.add( tmpline);
                            }
                        }
                    }
                }
                System.out.println("icerikler\n");
                for( int j = 0; j < kernel.size(); j++ ){
                    if( kernel.get(j).getClass().getName() == "Vertex"){
                        Vertex v = (Vertex)kernel.get(j);
                        System.out.println("Vertex at x = " + v.x + " y = " + v.y);
                    }
                    else{
                        Line l1 = (Line)kernel.get(j);
                        Circle l1StartC , l1EndC;
                        Vertex l1Start = new Vertex(l1StartC = new Circle(l1.getStartX() , l1.getStartY() , 2.5 , Color.BLACK) );
                        Vertex l1End = new Vertex(l1EndC = new Circle(l1.getEndX() , l1.getEndY() , 2.5 , Color.BLACK) );
                        System.out.println("line starts at = " + l1Start.x  + " " + l1Start.y + " ends at " + l1End.x + " " + l1End.y );
                    }
                }
                for( int j  = 0; j < kernel.size(); j++ ){
                    if( kernel.get(j).getClass().getName() == "javafx.scene.shape.Line"){
                        Line tempLine = (Line)kernel.get(j);
                        root.getChildren().remove(tempLine);
                        if( j != 0 && j != kernel.size()-1 && kernel.get(j+1).getClass().getName() == "Vertex" && kernel.get(j-1).getClass().getName()=="Vertex"){
                            Vertex start = (Vertex)kernel.get(j-1);
                            Vertex end = (Vertex)kernel.get(j+1);
                            kernel.remove(tempLine);
                            tempLine.setStartX(start.getCircleX());
                            tempLine.setStartY(start.getCircleY());
                            tempLine.setEndX(end.getCircleX());
                            tempLine.setEndY(end.getCircleY());
                            tempLine.setStroke(Color.CYAN);
                            root.getChildren().add(tempLine);
                            kernel.add( j , tempLine );
                        }
                        else if(j != 0 && j != kernel.size()-1 &&  (kernel.get(j+1).getClass().getName() == "javafx.scene.shape.Line"|| kernel.get(j-1).getClass().getName() == "javafx.scene.shape.Line" )){
                            if(kernel.get(j+1).getClass().getName() == "javafx.scene.shape.Line" ){
                                Line l1 = (Line)kernel.get(j);
                                Line l2 = (Line)kernel.get(j+1);
                                if( l1.getEndY() == l2.getStartY() && l1.getEndX() == l2.getStartX()){
                                    Circle c = new Circle(l1.getEndX() , l1.getEndY() , 2.5 , Color.CYAN);
                                    Vertex v = new Vertex(c);
                                    kernel.add(j+1 , v);
                                    root.getChildren().add(c);
                                }
                            }

                            if(kernel.get(j-1).getClass().getName() == "javafx.scene.shape.Line" ){
                                Line l1 = (Line)kernel.get(j-1);
                                Line l2 = (Line)kernel.get(j);
                                if( l1.getEndY() == l2.getStartY() && l1.getEndX() == l2.getStartX()){
                                    Circle c = new Circle(l1.getEndX() , l1.getEndY() , 2.5 , Color.CYAN);
                                    Vertex v = new Vertex(c);
                                    kernel.add(j , v);
                                    root.getChildren().add(c);
                                }
                            }
                        }
                    }
                }
                long end = System.currentTimeMillis();
                System.out.println("elapsed time == " + (end - startTime) );
            }
        System.out.println("finished");
        for(int i = 0; i < kernel.size() ; i++){
            if(kernel.get(i).getClass().getName() == "javafx.scene.shape.Line"){
                Line lTemp = (Line)kernel.get(i);
                lTemp.setStroke(Color.CYAN);
                try {
                    root.getChildren().add(lTemp);
                }
                catch (Exception e){

                }
            }
            else{
                Vertex v = (Vertex)kernel.get(i);
                Circle c = v.getCircle();
                c.setStroke(Color.CYAN);
                try {
                    root.getChildren().add(c);
                }
                catch (Exception e){

                }
            }
        }
    }

    public static boolean doesLinesIntersect(Line l1 , Line l2 ){
        Circle l1StartC , l1EndC, l2StartC , l2EndC;
        Vertex l1Start = new Vertex(l1StartC = new Circle(l1.getStartX() , l1.getStartY() , 2.5 , Color.BLACK) );
        Vertex l1End = new Vertex(l1EndC = new Circle(l1.getEndX() , l1.getEndY() , 2.5 , Color.BLACK) );
        Vertex l2Start = new Vertex(l2StartC = new Circle(l2.getStartX() , l2.getStartY() , 2.5 , Color.BLACK) );
        Vertex l2End = new Vertex(l2StartC = new Circle(l2.getEndX() , l2.getEndY() , 2.5 , Color.BLACK) );
        boolean t1  = leftTest(l1Start , l2Start , l1End);
        boolean  t2  = leftTest(l1Start  , l2End , l1End);

        if( t1 == t2 )
            return false;
        else{
            Circle c;
            Vertex tempv = findIntersection(l1,l2);
            if( (tempv.x > l1Start.x && tempv.x > l1End.x) || (tempv.x > l2Start.x && tempv.x > l2End.x) || (tempv.x < l1Start.x && tempv.x < l1End.x) ||(tempv.x < l2Start.x && tempv.x < l2End.x) || (tempv.y > l1Start.y && tempv.y > l1End.y) || (tempv.y > l2Start.y && tempv.y > l2End.y) || (tempv.y < l1Start.y && tempv.y < l1End.y) ||(tempv.y < l2Start.y && tempv.y < l2End.y)){
                System.out.println("returning false, they do not intersect ");
                return false;
            }
            else {
                System.out.println("returning true they do intersect ");
                return  true;
            }
        }

    }
    public static Vertex findIntersection(Line l1 , Line l2 ){
        Circle c1 , c2 , c3 , c4;
        Vertex l1Start = new Vertex(c1 = new Circle(l1.getStartX(), l1.getStartY() , 2.5 , Color.BLACK));
        Vertex l1End = new Vertex(c2 = new Circle(l1.getEndX() , l1.getEndY() , 2.5 , Color.BLACK));
        Vertex l2Start = new Vertex(c3 = new Circle(l2.getStartX() , l2.getStartY() , 2.5 , Color.BLACK));
        Vertex l2End = new Vertex(c4 = new Circle(l2.getEndX() , l2.getEndY() , 2.5 , Color.BLACK));
        double slope1 = (l1Start.y - l1End.y) / (l1Start.x - l1End.x);
        double slope2 = (l2Start.y - l2End.y) / (l2Start.x - l2End.x);
        double b1 =  l1Start.y - (slope1*l1Start.x);
        double b2 =  l2Start.y - (slope2*l2Start.x);
        double x = -(b2 - b1) / (slope2 - slope1);
        double y = slope1 * x + b1;
        Circle c;
        Vertex newVertex = new Vertex(x , y );
        System.out.print("start x of l1  "+ l1Start.x);
        System.out.print("start x of l2  "+ l2Start.x);
        System.out.print("end x of l1  "+ l1End.x);
        System.out.print("end x of l2  "+ l2End.x);
        System.out.print("start y of l1 " + l1Start.y );
        System.out.print("start y of l2 " + l2Start.y );
        System.out.print("end y of l1 " + l1End.y );
        System.out.print("end y of l2 " + l2End.y );
        System.out.print("x "+ x);
        System.out.print("y "+ y + "\n");
        return  newVertex;
    }
    public static boolean containsPoint( double x, double y, Line l ){
        double slope = (l.getEndY() - l.getStartY()) / (l.getEndX() - l.getStartX());
        double slope1 = (y-l.getStartY())/(x-l.getStartX());
        double slope2 = (y-l.getEndY()) / (x - l.getEndX());
        double maxX = Math.max(l.getStartX() , l.getEndX());
        double minX = Math.min(l.getStartX(),l.getEndX());
        double maxY = Math.max(l.getStartY() , l.getEndY());
        double minY = Math.min(l.getStartY(),l.getEndY());
        if( Math.abs(slope1 - slope2) <= 0.1 && Math.abs(slope-slope1) <= 0.1 && y < maxY && y > minY && x > minX && x < maxX)
            return true;
        else
            return  false;
    }
    public void buildKernel2(){
        int currentVertex = -1;
        int startVertex = -1;
        boolean startEdgeUnbounded = true; // if there is infinite at mostleft
        boolean endEdgeUnbounded = true;  // if there is infinite at mostright
        boolean circular = false;  // if the kernel polygon is circular
        int unboundedStartLineIndex = -1;
        int unboundedEndLineIndex  = -1;
        for (int i = 0; i < polygonListCounterClockwise.size(); i++) {
            // normal step
            if (i != 0 && i != polygonListCounterClockwise.size() - 1) {
                if (!leftTest(polygonListCounterClockwise.get(i - 1), polygonListCounterClockwise.get(i), polygonListCounterClockwise.get(i + 1))) {
                    startVertex = i;
                    //System.out.println(polygonListCounterClockwise.get(i).x + "  " + polygonListCounterClockwise.get(i).y);
                    break;
                }
            }
            //  end and start step
            else {
                if( i == 0 && !leftTest( polygonListCounterClockwise.get(polygonListCounterClockwise.size()-1) , polygonListCounterClockwise.get(0) , polygonListCounterClockwise.get(1))){
                    startVertex = 0;
                    break;
                }
                else if( i == polygonListCounterClockwise.size() - 1 && !leftTest( polygonListCounterClockwise.get(polygonListCounterClockwise.size()-2) , polygonListCounterClockwise.get(polygonListCounterClockwise.size()-1) , polygonListCounterClockwise.get(0))){
                    startVertex = polygonListCounterClockwise.size()-1;
                    break;
                }
            }
        }
        //polygonListCounterClockwise.add(polygonListCounterClockwise.get(0)); // now polygonlist circular


        // construct kernel K1
        currentVertex = startVertex;
        Ray startRay = new Ray(polygonListCounterClockwise.get(startVertex ) , polygonListCounterClockwise.get(startVertex +1 ),3 );
        Ray endRay = new Ray(polygonListCounterClockwise.get(startVertex) , polygonListCounterClockwise.get(startVertex - 1),3);
        Line k1 = startRay.getRay();
        Line k2 = endRay.getRay();
        k1.setStroke(Color.CYAN);
        k2.setStroke(Color.CYAN);
        root.getChildren().add(k1);
        root.getChildren().add(k2);
        kernel.add(k2);
        kernel.add(polygonListCounterClockwise.get(startVertex));
        kernel.add(k1);
        // determine points F and L
        Circle circlef , circlel;
        Vertex f = new Vertex( circlef = new Circle(k1.getEndX() , k1.getEndY(), 2.5, Color.CRIMSON));
        Vertex l = new Vertex( circlel = new Circle(k2.getEndX() , k2.getEndY(), 2.5, Color.CRIMSON));
        int indexf =2;
        int indexl =0;
        root.getChildren().addAll(circlef,circlel);

        System.out.println("lowest point in y == " + lowestY);
        System.out.println("highest point in y == " + highestY);
        System.out.println("lowest point in x == " + lowestX);
        System.out.println("highest point in x == " + highestX);
        ArrayList<Vertex> temp = new ArrayList<>();
        for(int i =0; i < startVertex; i++ ){
            temp.add(polygonListCounterClockwise.get(i));
        }
        for(int i = 0; i < startVertex; i++ ){
            polygonListCounterClockwise.remove(0);
        }
        polygonListCounterClockwise.addAll(temp);
        //System.out.println("enter test test ");
        for( int i = 0; i < polygonListCounterClockwise.size(); i++ ){
            //System.out.println(polygonListCounterClockwise.get(i).x + "    " + polygonListCounterClockwise.get(i).y);
        }
        // System.out.println("enter test test ");
        // algortihm starts here
        //
        //
        //
        currentVertex = startVertex;
        polygonListCounterClockwise.add(polygonListCounterClockwise.get(startVertex));
        Vertex w1 = null;
        Vertex w2 = null;
        int v1Index = -1;
        int v2Index = -1;
    }
    public int getTextInput( TextField field){
        int vertexNumber = 0;
        try{
             vertexNumber = Integer.parseInt(field.getText());
             buildPseudo(vertexNumber);
             return  vertexNumber;
        }
        catch (Exception e){
            System.exit(1);
            return  -1;
        }
    }
}

