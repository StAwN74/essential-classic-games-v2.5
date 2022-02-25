package ResetThosePacks;

//import ResetThosePacks.SystemInfo;

//Mompi's flappy
import java.util.ArrayList;
import javafx.animation.Animation;
//import javafx.animation.KeyFrame;
//import javafx.animation.Timeline;
//import javafx.application.Application;
import javafx.scene.Group;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
//import javafx.scene.text.Font;
//import javafx.stage.Stage;
//import javafx.util.Duration;
import javafx.util.converter.IntegerStringConverter;
import javafx.scene.paint.ImagePattern;
import javafx.scene.image.Image;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;

//My imports
import javafx.application.Application;
//import javafx.beans.value.ChangeListener;
//import javafx.beans.value.ObservableValue;
//import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.util.HashMap;
//import javafx.scene.layout.StackPane;
//import javafx.scene.shape.Circle;
//import javafx.scene.control.ListView;
import java.util.List;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
//import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

//Pong imports
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
//import javafx.application.Application;
//import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
//import javafx.stage.Stage;
import javafx.util.Duration;

//import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.time.Instant;
//import java.sql.*;
//import java.util.List;
//import java.util.Optional;

import com.dieselpoint.norm.Database;

/**
 * JavaFX App
 */
public class App extends Application {

	public static String encrypt(String source) {
		String md5 = null;
		try {
				MessageDigest mdEnc = MessageDigest.getInstance("MD5"); //Encryption algorithm
		        mdEnc.update(source.getBytes(), 0, source.length());
		        md5 = new BigInteger(1, mdEnc.digest()).toString(16); // Encrypted string
		    } 
		catch (Exception ex) {
			return null;
		}
		    return md5;
	}
	
	private TextField text = new TextField();
	private PasswordField pass = new PasswordField();
	private Label lbl = new Label("Not connected");
	private Label lbl2 = new Label("Flappy leader: connection required");
	private Button btn = new Button ("Connect");
	private Button btn4 = new Button ("Create User");
	private Button btn3 = new Button ("Pong");
	private Button btn5 = new Button ("Offline Pong");
	private Button btn6 = new Button ("Flappy");
	//private Button btn7 = new Button ("View Stats");
	
    private Database db = new Database();
    
    //use this to make sql requests
    //private TestMyDB userConnect = new TestMyDB();
    
    //private BorderPane root7 = new BorderPane();
    private BorderPane root = new BorderPane();
    private Scene scene = new Scene(root, 800, 600);
    
    //Pong vars
    private static final int width = 800;
	private static final int height = 600;
	private static final int PLAYER_HEIGHT = 100;
	private static final int PLAYER_WIDTH = 15;
	private static final double BALL_R = 15;
	private int ballYSpeed = 1;
	private int ballXSpeed = 1;
	private double playerOneYPos = height / 2;
	private double playerTwoYPos = height / 2;
	private double ballXPos = width / 2;
	private double ballYPos = height / 2;
	private int scoreP1 = 0;
	private int scoreP2 = 0;
	private boolean gameStarted;
	private int playerOneXPos = 0;
	private double playerTwoXPos = width - PLAYER_WIDTH;
	private Stage stage;
	
	public int idPused; // this will save player id at Connect click, to be used at Play click
	public int maxScore; // this will store user max score to be updated
	public int maxFlap = 0; // same for Flappy Game
	public String nameRecall; // will be used in stage title
	
	public Boolean offPong;
	
	//public String myURL = new String("C:/MY DATA/Callac_CSC_dev/Prog/Eclipse/Play_Flappy_Pong/src/resources");
	
	@Override
    public void start(Stage stage) {
    	//var javaVersion = SystemInfo.javaVersion();
        //var javafxVersion = SystemInfo.javafxVersion();

        //var label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        //var lbl = new Label("What doink");
        //var font = new Font(20);
        
        //btn.setFont(20);
        //var root = new BorderPane();
        var box = new HBox();
        var box2 = new HBox();
        var box3 = new HBox();
        
        box.getChildren().add(text);
        box.getChildren().add(pass);
        box.getChildren().add(btn);
        box.getChildren().add(btn4);
        box.getChildren().add(btn5);
        box.setPadding(new Insets(60));
        box2.setPadding(new Insets(60));
        box3.setPadding(new Insets(64));
        
        String statsURL = "https://www.stawn.live";
        Button openURLButton = new Button("View Stats");
        openURLButton.setOnAction(e -> getHostServices().showDocument(statsURL));
        box2.getChildren().add(openURLButton);
        box2.getChildren().add(btn3);
        box2.getChildren().add(btn6);
        
        lbl.setPrefSize(640, 50);
        lbl.setTextFill(Color.INDIANRED);
        lbl.setFont(new Font("Verdana", 14));
        lbl.setPadding(new Insets (60));
        
        lbl2.setTextFill(Color.BLACK);
        lbl2.setFont(new Font("Verdana", 12));
        lbl2.setPadding(new Insets (70));
        
        //btn.setShape(new Circle(30));
        //btn.setPrefSize(50,50);
        btn.setOnAction(new HandleClick());
        btn4.setOnAction(new HandleClick3());
        btn3.setOnAction(new HandleClick2());
        btn5.setOnAction(new HandleClick4());
        btn6.setOnAction(new HandleClick5());
        
        //root.setCenter(listView);
        root.setTop(lbl);
        root.setCenter(box2);
        root.setBottom(box);
        root.setRight(lbl2);
        //root.setRight(box3);
        lbl2.setVisible(false);
        
        //Database connection - we need to do this only once
        db.setJdbcUrl("jdbc:mysql://sql512.main-hosting.eu/u465692193_online_pong?useSSL=false");
        db.setUser("****");
        db.setPassword("****");
        
        //var scene = new Scene(root, 640, 480);
        this.stage = stage; //ty Bonbek //otherwise we have a null stage at HandleClick2
        
        //this.primaryStage = stage;
        
        stage.setResizable(false);
        
        stage.setTitle("Connect&Play");
        stage.setScene(scene);
        stage.show();
        
	}
	
	//Mompi's flappy
	Label scorelabel;
	Label l2;
	Button btnF;
	int score,index;
	//Stage primaryStage;
	Ellipse bird;
	Rectangle ground;
	ArrayList<Rectangle> columns;
	int W=800,H=700;
	int ticks,ymotion;
	Group rootF;
	boolean gameOver;
	Label l;
	LinearGradient ld;
	IntegerStringConverter str;
	Timeline tim;
	Scene sceneF;
	Image cloud;
	ImageView cloudv;
	int X,Y;
	DropShadow ds1;
	void addColumn()
	{
	   	int space=300;
	    int width=100;
	    int height=50+(int)(Math.random()*300);

	   	columns.add(new Rectangle(W+width+(columns.size()*200),H-height-120,width,height));
	    columns.add(new Rectangle(W+width+(columns.size()-1)*200,0,width,H-height-space));
	}
	void Collision(){
	    for(Rectangle column:columns)
	    {
	        if((column.getBoundsInParent().intersects(bird.getBoundsInParent())))
	        {
	        	gameOver=true;
	            if(bird.getCenterX()<=column.getX())
	                bird.setCenterX(column.getX()-2*bird.getRadiusX()+10);
	                else
	                {
	                    if(column.getY()!=0)
	                    {
	                        bird.setCenterY(column.getY()-2*bird.getRadiusY());
	                    }
	                    else if(bird.getCenterY()>column.getHeight()){
	                        bird.setCenterY(column.getHeight());
	                    }
	                }
	           }     
	    }
	    if(bird.getCenterY()>H-120||bird.getCenterY()<0)   
	    {
	    	gameOver=true;
	    }
				
	    if(gameOver)
	    {
	    	bird.setCenterY(H-120-bird.getRadiusY());
	        l.setFont(new Font("Arial",30));
	        l.setLayoutX(stage.getWidth()/2-169);
	        l.setLayoutY(stage.getHeight()/2-90);
	        l.setTextFill(Color.MEDIUMPURPLE);
	        l.setText("GameOver\n Score : "+str.toString(score));
	    }
	}
	
	void Jump(){
		if(!gameOver){
			if(ymotion>0){
				ymotion=0;
	            }
	        ymotion=ymotion-6; // or -8, best is -6 OR -7 (default is -10)
	    }
	}
	
	void start1()
	{
		bird.setCenterX(W/2-10);
	    bird.setCenterY(H/2-10);
	    gameOver=false;
	    ymotion=0;
	    score=0;
		scorelabel.setText("Score : "+str.toString(score));
		rootF.getChildren().remove(btnF);
		rootF.getChildren().removeAll(columns);
		columns.clear();
		int i=0;
	    while(i<100)
	    {
	        addColumn();
			i++;
		}
		l2.setText("Press the Up arrow key to start!");
		l2.setFont(new Font("Arial",27));
		l2.setLayoutX(stage.getWidth()/2-196);
		l2.setLayoutY(stage.getHeight()/2-120);
		l2.setTextFill(Color.PURPLE);
		
		tim.pause();
		rootF.getChildren().add(l2);
		
		sceneF.setOnKeyReleased(k -> {
			String code = k.getCode().toString();
			if(code=="UP" || code =="SPACE")
			{
				rootF.getChildren().addAll(columns);
				rootF.getChildren().remove(l2);
				tim.play();
			}
		});
	}
	
	//Pong's run using canvas
	private void run(GraphicsContext gc) {
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, width, height);
		gc.setFill(Color.WHITE);
		gc.setFont(Font.font(25));
		if(gameStarted) {
			ballXPos+=ballXSpeed;
			ballYPos+=ballYSpeed;
			//Setting Bot pos
			if(ballXPos < width - width  / 4) {
				playerTwoYPos = ballYPos - PLAYER_HEIGHT / 2;
			}  else {
				playerTwoYPos =  ballYPos > playerTwoYPos + PLAYER_HEIGHT / 2 ?playerTwoYPos += 1: playerTwoYPos - 1;
			}
			gc.fillOval(ballXPos, ballYPos, BALL_R, BALL_R);
		} else {
			//restarting round
			gc.setStroke(Color.YELLOW);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.strokeText("Click to Start", width / 2, height / 2);
			ballXPos = width / 2;
			ballYPos = height / 2;
			ballXSpeed = new Random().nextInt(2) == 0 ? 1: -1;
			ballYSpeed = new Random().nextInt(2) == 0 ? 1: -1;
		}
		//Dealing with end of round
		if(ballYPos > height || ballYPos < 0) ballYSpeed *=-1;
		if(ballXPos < playerOneXPos - PLAYER_WIDTH) {
			scoreP2++;
			gameStarted = false;
		}
		if(ballXPos > playerTwoXPos + PLAYER_WIDTH) {  //meaning ball is out of sight
			scoreP1++;
			//if scoreP1 > maxScore then db.update()
			if (scoreP1 > maxScore && offPong == false) {
				TestMyDB user2 = db.where("NomP=?", nameRecall).first(TestMyDB.class);
	            user2.BestScore = scoreP1;
	            maxScore = scoreP1;
	            //user2.NomG = "Pong";
	            db.update(user2); // adding score to DB
	            System.out.println("New best score !");
	            stage.setTitle("Connect&Play - " +nameRecall +" : Connected - Max score : " +maxScore);
			}
			//new round
			gameStarted = false;
		}
		//Accelerating ball
		if( ((ballXPos + BALL_R > playerTwoXPos) && ballYPos >= playerTwoYPos && ballYPos <= playerTwoYPos + PLAYER_HEIGHT) || 
			((ballXPos < playerOneXPos + PLAYER_WIDTH) && ballYPos >= playerOneYPos && ballYPos <= playerOneYPos + PLAYER_HEIGHT)) {
			ballYSpeed += 1 * Math.signum(ballYSpeed);
			ballXSpeed += 1 * Math.signum(ballXSpeed);
			ballXSpeed *= -1;
			ballYSpeed *= -1;
		}
		//Displayed text
		gc.fillText(scoreP1 + "\t\t\t\t\t\t\t\t" + scoreP2, width / 2, 100);
		gc.fillRect(playerTwoXPos, playerTwoYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
		gc.fillRect(playerOneXPos, playerOneYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
	}
    
	//HandleClick() on connect button
    class HandleClick implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
        	offPong = false;
        	System.out.println(event.getEventType()); // affiche le type de l'event
        	System.out.println(event.getTarget()); // affiche la cible de l'event
        	//Button btn = (Button) event.getSource(); // affiche la source de l'event
        	
        	String nameRecon = text.getText();
        	nameRecall = nameRecon;
        	System.out.println(nameRecon);
        	String passRecon = pass.getText();
        	System.out.println(passRecon);
        	
        	//String fakeRecon = "bla"; // for testing purpose
            
            //checking what line is selected. None for now
            //System.out.println(tempRecon.PassP);
            //db.insert(tempRecon); // gives error because NameP can't be null according to db restrictions
            
        	//Create a table object then use it to check if name and pass exist
            //String PassP = db.sql("select PassP from players where NomP like 'Bast'").first(String.class);
        	
        	//Creating a table related object from TestMyDB class
            TestMyDB user = db.where("NomP=? AND PassP=?", nameRecon, encrypt(passRecon)).first(TestMyDB.class);
            
            //Best score display
            //TestMyDB leader = db.where("NomG=?", "Pong").orderBy("BestScore").first(TestMyDB.class);
            TestMyDB leader2 = db.sql("select NomP, BestFlap from players order by BestFlap DESC LIMIT 1").first(TestMyDB.class);
            TestMyDB leaderEx = db.sql("select NomP, BestScore from players order by BestFlap DESC LIMIT 1").first(TestMyDB.class);
            //List<HashMap> list = db.sql("select NomP, BestScore from players order by BestScore DESC LIMIT 3").results(HashMap.class);
            
            //Compare entered text and pass with DB. If so, player is connected.
            if (user != null) { // user with nameRecon AND passRecon exists
            	idPused = user.id; // saving user id
            	maxScore = user.BestScore; // NB: BestScore can't be null (default 0)
            	maxFlap = user.BestFlap;
            	System.out.println("Connected"); // just checking
            	lbl.setText("Connected");
            	lbl.setTextFill(Color.BLACK);
            	//if(leader2.BestFlap > 1) {
            		//lbl2.setText("Flappy leader: " +leader2.NomP +" with " +leader2.BestFlap +" points");
            	//}
            	//else {
            		//lbl2.setText("Flappy leader: " +leader2.NomP +" with " +leader2.BestFlap +" point");
            	//}
            	lbl2.setText("Pong leader: " +leaderEx.NomP +" with " +leaderEx.BestScore +" point(s)\n" +"Flappy leader: " +leader2.NomP +" with " +leader2.BestFlap +" point(s)");
            	lbl2.setTextFill(Color.BLACK);
            	lbl2.setVisible(true);
            	stage.setTitle("Connect&Play - " +nameRecon +" : Connected - Previous max score at Flap : " +maxFlap);
            	//Supposed to block any further bad attempt?
            }
            else {
            	//Second connection with wrong credentials
            	lbl.setText("Not connected");
            	lbl.setTextFill(Color.INDIANRED);
            	lbl2.setText("Flappy leader: connection required");
            	lbl2.setTextFill(Color.BLACK);
            	lbl2.setVisible(false);
            	stage.setTitle("Connect&Play");
            	idPused = 0;
            	maxScore = 0;
            	maxFlap = 0;
            	nameRecon = "";
            }
        }
    }
    
    //HandleClick() on play button
    class HandleClick2 implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
        	offPong = false;
        	System.out.println(event.getEventType()); // affiche le type de l'event
        	System.out.println(event.getTarget()); // affiche la cible de l'event
        	//Button btn = (Button) event.getSource();
        	System.out.println(nameRecall);
        	System.out.println(idPused);
        	System.out.println(maxScore);
        	
        	if (lbl.getText() == "Connected") {
        		System.out.println("I am supposed to launch Pong and add a session");
        		
        		//Add a session entry in sessions table of database
        		//Creating a table related object from TestMySessions class
        		TestMySessions session = new TestMySessions();
                session.NomG = "Pong";
                session.idG = 1;
                session.id = idPused; // using temp var idPused
                //setting DateTime of session in DB
                Timestamp ts = Timestamp.from(Instant.now());
                session.DateS = ts;
                //Adding the session
                if (idPused != 0) {
                	db.insert(session);
                	System.out.println("New session started");
                }
        		
        		//Pong canvas
                Canvas canvas = new Canvas(width, height);
        		GraphicsContext gc = canvas.getGraphicsContext2D();
        		Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), e -> run(gc)));
        		tl.setCycleCount(Timeline.INDEFINITE);
        		canvas.setOnMouseMoved(e ->  playerOneYPos  = e.getY());
        		canvas.setOnMouseClicked(e ->  gameStarted = true);
        		stage.setScene(new Scene(new StackPane(canvas)));
        		stage.setResizable(false);
        		stage.setTitle("Connect&Play - " +nameRecall +" : Connected - Previous max score at Pong : " +maxScore);
        		stage.show();
        		tl.play();
        		System.out.println("Game successfully launched");
        	}
        }
    }
    
    //HandleClick() on Create button
    class HandleClick3 implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
        	offPong = false;
        	System.out.println(event.getEventType()); // affiche le type de l'event
        	System.out.println(event.getTarget()); // affiche la cible de l'event
        	//Button btn = (Button) event.getSource();
        	System.out.println(nameRecall);
        	System.out.println(idPused);
        	System.out.println(maxScore);
        	
        	String nameCreate = text.getText();
        	System.out.println(nameCreate);
        	String passCreate = pass.getText();
        	System.out.println(passCreate);
        	
        	//Creating a table related object from TestMyDB class
        	TestMyDB user3 = db.where("NomP=?", nameCreate).first(TestMyDB.class);
        	//idPused = user3.idP; //temporary fix if idP is null here
        	
        	if (user3 == null) { // user doesn't already exist
        		if(nameCreate != "" && passCreate != "") {
        			TestMyDB user3b = new TestMyDB(); // user3 is null, we need a new object
        			user3b.NomP = nameCreate;
        			user3b.setPassP (encrypt(passCreate)); // using the setter of TestMyDB class because PassP is private
        			//user3b.NomG = "Pong"; // Needed IF NomGg is NOT NULL
        			//NB: user3b.BestScore is an INT and set to 0 by JavaFX, so the DataBase NULL restriction won't give an error
        			
        			db.insert(user3b);
        			System.out.println("New user created"); // just checking via sysout
        			lbl.setText("Please connect with new credentials.");
        			lbl.setTextFill(Color.PURPLE);
        			lbl2.setText("Flappy leader: connection required");
                	lbl2.setTextFill(Color.BLACK);
                	lbl2.setVisible(false);
        			stage.setTitle("Connect&Play");
        		}
        		else {
        			lbl.setText("Please add a name and a password to do this.");
        			lbl.setTextFill(Color.INDIANRED);
        			lbl2.setText("Flappy leader: connection required");
                	lbl2.setTextFill(Color.BLACK);
                	lbl2.setVisible(false);
        			stage.setTitle("Connect&Play");
                	idPused = 0;
                	maxScore = 0;
                	maxFlap = 0;
        		}
        	}
        	else if (user3 != null){
        		System.out.println("This name already exists");
        		lbl.setText("This name already exists.");
        		lbl.setTextFill(Color.INDIANRED);
    			lbl2.setText("Flappy leader: connection required");
            	lbl2.setTextFill(Color.BLACK);
            	lbl2.setVisible(false);
        		stage.setTitle("Connect&Play");
            	idPused = 0;
            	maxScore = 0;
            	maxFlap = 0;
        	}
        }
    }
    
    //HandleClick() on offline button
    class HandleClick4 implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
        	offPong = true;
        	idPused = 0;
        	maxScore = 0;
        	maxFlap = 0;
        	System.out.println(event.getEventType()); // affiche le type de l'event
        	System.out.println(event.getTarget()); // affiche la cible de l'event
        	//Button btn = (Button) event.getSource();
        	System.out.println(nameRecall);
        	System.out.println(idPused);
        	System.out.println(maxScore);
    		stage.setTitle("Connect&Play");
        	
        	//Pong canvas
            Canvas canvas = new Canvas(width, height);
        	GraphicsContext gc = canvas.getGraphicsContext2D();
        	Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), e -> run(gc)));
        	tl.setCycleCount(Timeline.INDEFINITE);
        	canvas.setOnMouseMoved(e ->  playerOneYPos  = e.getY());
        	canvas.setOnMouseClicked(e ->  gameStarted = true);
        	stage.setScene(new Scene(new StackPane(canvas)));
        	stage.setResizable(false);
        	stage.show();
        	tl.play();
        	System.out.println("Offline Mode launched");
        }
    }
    
    //HandleClick() on Flap button
    class HandleClick5 implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
        	offPong = false;
        	System.out.println(event.getEventType()); // affiche le type de l'event
        	System.out.println(event.getTarget()); // affiche la cible de l'event
        	//Button btn = (Button) event.getSource();
        	System.out.println(nameRecall);
        	System.out.println(idPused);
        	System.out.println(maxFlap);
        	
        	if (lbl.getText() == "Connected") {
        		System.out.println("I am supposed to launch Flap and add a session");
        		
        		//Add a session entry in sessions table of database
        		//Creating a table related object from TestMySessions class
        		TestMySessions session = new TestMySessions();
                session.NomG = "Flap";
                session.idG = 2;
                session.idP = idPused; // using temp var idPused
                //setting DateTime of session in DB
                Timestamp ts = Timestamp.from(Instant.now());
                session.DateS = ts;
                //Adding the session
                if (idPused != 0) {
                	db.insert(session);
                	System.out.println("New session started");
                }
        		
        		//Pong canvas
                //Canvas canvas = new Canvas(width, height);
        		//GraphicsContext gc = canvas.getGraphicsContext2D();
        		//Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), e -> run(gc)));
        		//tl.setCycleCount(Timeline.INDEFINITE);
        		//canvas.setOnMouseMoved(e ->  playerOneYPos  = e.getY());
        		//canvas.setOnMouseClicked(e ->  gameStarted = true);
        		//stage.setScene(new Scene(new StackPane(canvas)));
        		//stage.show();
        		//tl.play();
                
                //Flap game
                
                //Mompi's
                //primaryStage=window;
                //stage.setTitle("Flappy Bird");
                stage.setHeight(H);
                stage.setWidth(W);
                stage.setResizable(false);
                
                rootF=new Group();
            	//Shadow for bird
            	DropShadow ds1 = new DropShadow();
                ds1.setOffsetY(4.0f);
                ds1.setOffsetX(4.0f);
                ds1.setColor(Color.GREY);
            	//shadow for button
            	DropShadow ds2 = new DropShadow();
                ds1.setOffsetY(4.0f);
                ds1.setOffsetX(4.0f);
                ds1.setColor(Color.BLACK);
            	
            	Image img=new Image("file:resources/birdFrame0.png");
            	Image imgWin=new Image("file:resources/birdFrame0King2.png");
            	ImagePattern ip=new ImagePattern(img);
            	ImagePattern ip2=new ImagePattern(imgWin);
                
                cloud=new Image("file:resources/cloud.png");
            	cloudv=new ImageView(cloud);
            	X=W+(int)cloud.getWidth();
            	cloudv.setX(X);
            	Y=10+(int)(Math.random()*100);
            	cloudv.setY(Y);
            	
                bird=new Ellipse();
            	bird.setFill(ip);
                bird.setRadiusX((img.getWidth()/2)+4);
                bird.setRadiusY((img.getHeight()/2)+4);
                bird.setCenterX(W/2-10);
                bird.setCenterY(H/2-10);
            	bird.setEffect(ds1);
            	
                index=0;
                ymotion=0;
                
                str=new IntegerStringConverter();
                
                l=new Label();
                l2=new Label();
                scorelabel=new Label();
                //scorelabel.setText("Score"+str.toString(score));
                scorelabel.setFont(new Font("Arial",20));
                
                ld=new LinearGradient(0.0, 0.0, 1.0, 0.0, true,
                                                     CycleMethod.NO_CYCLE,
                                                     new Stop(0.0, Color.GREY),
                                                     new Stop(1.0, Color.BLACK));
                
                columns=new ArrayList<Rectangle>();
                
                ground=new Rectangle(0,H-120,W,120);
            	ground.setFill(Color.DARKGREEN);
                
                tim=new Timeline();
                tim.setCycleCount(Animation.INDEFINITE);
                
                gameOver=false;
            	
            	btnF = new Button();
            	btnF.setText("Restart");
            	btnF.setTranslateX(350);
            	btnF.setTranslateY(600);
            	btnF.setPrefSize(100,50);
            	btnF.setTextFill(Color.BLUE);
                btnF.setFont(new Font("Arial",20));
            	btnF.setEffect(ds2);
            	
                KeyFrame kf=new KeyFrame(Duration.millis(20),e -> {
                	ticks++;
                    if(ticks%2==0&&ymotion<10) // or %3 && <10, best is %2 < 10 (default values are %2 and 15)
            		{
                    	ymotion=ymotion+1; // or +1 (default is 2)		
            		}
            		X=X-2;
            		cloudv.setX(X);
            		if(X<(0-(int)cloud.getWidth()))
            		{
            			X=W+(int)cloud.getWidth();
            			cloudv.setX(X);
            			Y=10+(int)(Math.random()*100);
            			cloudv.setY(Y);
            			}
                        int y=(int)bird.getCenterY()+ymotion;   
                        bird.setCenterY(y);
                        sceneF.setOnKeyReleased(kU -> {
            			String code = kU.getCode().toString();
            			if(code=="UP" || code =="SPACE")
            			{
            				Jump();
            			}
            		});
                    Collision();
                    if(gameOver)
                    {
                    	//if scoreP1 > maxFlap then db.update()
                        if ((score) > maxFlap || ((score) >= 50 && maxFlap >= 100)) {
                        	TestMyDB userF = db.where("NomP=?", nameRecall).first(TestMyDB.class);
                        	if ((score) > maxFlap) {
                        		userF.BestFlap = (score);
                            	maxFlap = (score);
                            	//user2.NomG = "Flap";
                            	db.update(userF); // adding score to DB
                            	System.out.println("New best FLAPPY score !");
                        	}
                        	else if ((score) >= 50 && maxFlap >= 100) {
                        		if ((score) >= 50 && (score) < 100){
                        			userF.BestFlap = userF.BestFlap + 50;
                                	maxFlap = userF.BestFlap;
                                	//user2.NomG = "Flap";
                                	db.update(userF); // adding score to DB
                                	System.out.println("50 points added to DB.");
                                	l.setText("GameOver\nYou earned 50 more points!");
                        		}
                        		else if(score == 100) {
                        			userF.BestFlap = userF.BestFlap + 100;
                                	maxFlap = userF.BestFlap;
                                	//user2.NomG = "Flap";
                                	db.update(userF); // adding score to DB
                                	System.out.println("Game over again! 100 points added to DB.");
                                	l.setText("GameOver\nYou earned 100 more points!");
                        		}
                        	}
                        	stage.setTitle("Connect&Play - " +nameRecall +" : Connected - Max score : " +maxFlap);
                   		}
                        
                        if(!(rootF.getChildren().contains(l)))
                        	rootF.getChildren().addAll(l,btnF);
                        sceneF.setOnKeyReleased(kZ -> {
                			String codeZ = kZ.getCode().toString();
                			if(codeZ=="UP")
                			{
                				rootF.getChildren().remove(l);
                				start1();
                			}
                		});
                        btnF.setOnMouseClicked(k ->
                        {
                        	rootF.getChildren().remove(l);
            				start1();
            			});
                    }
                });

                KeyFrame kf2 = new KeyFrame(Duration.millis(20),e->{
                	for(int i=0;i<columns.size();i++)
                    {
                		Rectangle column=columns.get(i);
                        column.setFill(ld);
            			column.setEffect(ds1);
                        column.setX((column.getX()-5));
                        
                        if(column.getY()==0&&bird.getCenterX()+bird.getRadiusX()>column.getX()+column.getWidth()/2-5&&bird.getCenterX()+bird.getRadiusX()<column.getX()+column.getWidth()/2+5)
                        {
                            score++;
                            if (score == 100) {
                                scorelabel.setText("Score : GODLIKE (100) !!!");
                			    scorelabel.setTextFill(Color.GOLDENROD);
//                				int j = 0;
//                				while(j<5)
//                				{
//                				    addColumn();
//                					j++;
//                				}
                				//rootF.getChildren().addAll(columns);
                            }
                            else if (score == 50) {
                            	scorelabel.setText("Score : You're halfway there !!!");
                				scorelabel.setTextFill(Color.DARKGOLDENROD);
                				bird.setFill(ip2);
                            }
                            else {
                            	scorelabel.setText("Score : "+str.toString(score));
                				scorelabel.setTextFill(Color.DARKBLUE);
                            }
                            
            				//I used to update DB score here but laggy
                        }
                    }
                    for(int i=0;i<columns.size();i++)
                    {
                    	Rectangle column=columns.get(i);   
                        if((column.getX()+column.getWidth())<0)
                        {
                        	columns.remove(i);
                        }
                    }
                });

                tim.getKeyFrames().addAll(kf,kf2);
                
                rootF.getChildren().add(cloudv);
            	rootF.getChildren().addAll(scorelabel);
            	rootF.getChildren().add(ground);
            	rootF.getChildren().add(bird);
            	
            	sceneF=new Scene(rootF);
            	sceneF.setFill(Color.LIGHTBLUE);
            	start1();
            	
                stage.setScene(sceneF);
                stage.setHeight(H);
                stage.setWidth(W);
                stage.setTitle("Connect&Play - " +nameRecall +" : Connected - Previous max score at Flap : " +maxFlap);
                stage.show();
                
        		System.out.println("Game successfully launched");
        	}
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
