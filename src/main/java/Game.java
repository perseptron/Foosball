
public class Game {
    private String team1;
    private String team2;
    private int gametime = 10;
    private int team1score;
    private int team2score;
    public static final int MAXSCORE = 10;

    public Game(String team1, String team2, int gametime) {
        this.team1 = team1;
        this.team2 = team2;
        this.gametime = gametime;
    }

    public Game(String team1, String team2) {
        this.team1 = team1;
        this.team2 = team2;
    }

    public String getTeam1() {
        return team1;
    }

    public String getTeam2() {
        return team2;
    }

    public int getTeam1score() {
        return team1score;
    }

    public int getTeam2score() {
        return team2score;
    }

    public int goalteam1(){
        return ++team1score;
    }
    public int goalteam2(){
        return ++team2score;
    }

    public void newGame(){
        team1score = team2score = 0;
    }


}
