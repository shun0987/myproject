/*神経衰弱(Nervous Breakdown) 4*4*/
import java.io.*;
import java.util.Random;

class NervousBreakdown{
    int [][] visit=new int[10][10];     //未探索or探索済判定
    char[][] CardF=new char[10][10];    //表面
    char[][] CardR=new char[10][10];    //裏面
    char[] MarkArray={'A','A','B','B','C','C','D','D','E','E','F','F','G','G','H','H'};
    int Trycnt=0,Paircnt=0;
    
    //コンストラクタ 配列を初期化する
    public NervousBreakdown(){
        for(int i=1;i<=4;i++){
            for(int j=1;j<=4;j++){
                visit[i][j]=-1;
                CardF[i][j]='○';
            }
        }
    }

    //例外検出時の処理 未探索andめくった場所を戻す
    public void Initialize(){
        for(int i=1;i<=4;i++){
            for(int j=1;j<=4;j++){
                if(CardF[i][j]!='○'&&visit[i][j]!=1)
                    CardF[i][j]='○';
            }
        }
    }

    //表面:カードの表示
    public void PrintCard(){
        System.out.println("  1  2  3  4 ");
        for(int i=1;i<=4;i++){
            System.out.print(i);
            for(int j=1;j<=4;j++)   System.out.print("|"+CardF[i][j]+"|");
            System.out.println("");
        }
    }

    //裏面:マークの設定
    public void SetCard(){
        int sum=0;
        do{
            Random r1=new Random();
            Random r2=new Random();
            Random r3=new Random();

            int rvalue1=r1.nextInt(4)+1;  //1~4の乱数
            int rvalue2=r2.nextInt(4)+1;  //1~4の乱数
            int rvalue3=r3.nextInt(16);  //0~15の乱数

            if(CardR[rvalue1][rvalue2]=='\0'&&MarkArray[rvalue3]!='\0'){
                CardR[rvalue1][rvalue2]=MarkArray[rvalue3];     //裏面にマークを書き込む
                MarkArray[rvalue3]='\0';    //取り出した場所は空にする
                sum++;
            }
        }while(sum<16);
    }

    //カードをめくる
    public void TurnCard(int a,int b){
        CardF[a][b]=CardR[a][b];
        PrintCard();
    }

    //状況の表示
    public void Status(){
        System.out.println("現在の状況です。");
        System.out.println("手数:"+Trycnt);
        System.out.println("完成ペア:"+Paircnt);
    }

    //周囲8マスに1枚目のペアがあればヒントを出力
    public void Search(int x,int y){
        //上下
        if(CardR[x][y-1]==CardR[x][y]||CardR[x][y+1]==CardR[x][y])
            System.out.println(CardR[x][y]+"のペアは近くにあるかも?");
        //左右
        else if(CardR[x-1][y]==CardR[x][y]||CardR[x+1][y]==CardR[x][y])
            System.out.println(CardR[x][y]+"のペアは近くにあるかも?");
        //左斜め
        else if(CardR[x-1][y-1]==CardR[x][y]||CardR[x-1][y+1]==CardR[x][y])
            System.out.println(CardR[x][y]+"のペアは近くにあるかも?");
        //右斜め
        else if(CardR[x+1][y-1]==CardR[x][y]||CardR[x+1][y+1]==CardR[x][y])
            System.out.println(CardR[x][y]+"のペアは近くにあるかも?");
    }

    //終了時の表示
    public void Result(){
        System.out.println("ゲーム終了!!");
        System.out.println("手数:"+Trycnt);
        //理論上ありうる評価,履歴のカンニングを疑う
        if(Trycnt==8){
            System.out.println("評価:S");
            System.out.println("最短クリアおめでとうございます！");
        }
        if(Trycnt>=9&&Trycnt<=16)   System.out.println("評価:A");
        if(Trycnt>=17&&Trycnt<=24)  System.out.println("評価:B");
        else System.out.println("評価:C");

        System.out.println("お疲れさまでした。また遊んでね!");
    }
}

public class A5 {
    public static void main(String[] args)throws IOException{
        System.out.println("***神経衰弱***");
        System.out.println("16枚のカードが与えられます。");
        System.out.println("A~Hのペアを8つ見つけてください。");
        System.out.println("Ctrl+cで強制終了します。");

        NervousBreakdown NB=new NervousBreakdown();
        NB.SetCard();

        while(NB.Paircnt<8){
            try{
                NB.PrintCard();
                BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
                //1枚目
                System.out.println("調べたいカードの座標を入力してください。");
                System.out.println("1枚目--->");
            
                System.out.print("x座標:");
                String str1=br.readLine();
                System.out.print("y座標:");
                String str2=br.readLine();

                int px1=Integer.parseInt(str1);
                int py1=Integer.parseInt(str2);
                
                //範囲外の整数,探索済みへの判定
                if(px1<1||px1>4){
                    System.out.println("1~4の整数を入力してください。");
                    continue;
                }
                if(py1<1||py1>4){
                    System.out.println("1~4の整数を入力してください。");
                    continue;
                }
                if(NB.visit[py1][px1]==1){
                    System.out.println("すでに調べています。");
                    continue;
                }

                NB.TurnCard(py1, px1);
                System.out.println("座標("+px1+","+py1+")--->"+NB.CardR[py1][px1]);
            
                //2枚目
                System.out.println("調べたいカードの座標を入力してください。");
                System.out.println("2枚目--->");

                System.out.print("x座標:");
                str1=br.readLine();
                System.out.print("y座標:");
                str2=br.readLine();
                
                int px2=Integer.parseInt(str1);
                int py2=Integer.parseInt(str2);

                if(px2<1||px2>4){
                    System.out.println("1~4の整数を入力してください。");
                    NB.CardF[py1][px1]='o';
                    continue;
                }
                if(py2<1||py2>4){
                    System.out.println("1~4の整数を入力してください。");
                    NB.CardF[py1][px1]='o';
                    continue;
                }
                if(NB.visit[py2][px2]==1){
                    System.out.println("すでに調べています。");
                    System.out.println("1枚目から選び直してください。");
                    NB.CardF[py1][px1]='○';
                    continue;
                }
                //被りの判定
                if(px2==px1&&py2==py1){
                    System.out.println("同じカードです。");
                    System.out.println("1枚目から選び直してください。");
                    NB.CardF[py2][px2]='○';
                    continue;
                }
            
                NB.TurnCard(py2, px2);
                System.out.println("座標("+px2+","+py2+")--->"+NB.CardR[py2][px2]);

                if(NB.CardF[py1][px1]==NB.CardF[py2][px2]){
                    System.out.println("ペア完成!");
                    NB.Trycnt++;
                    NB.Paircnt++;
                    NB.visit[py1][px1]=1;   //探索判定はペア完成時に行う
                    NB.visit[py2][px2]=1;
                    NB.Status();
                }
                if(NB.CardF[py1][px1]!=NB.CardF[py2][px2]){
                    System.out.println("残念…");
                    NB.Trycnt++;
                    NB.CardF[py1][px1]='○'; //再び裏返す
                    NB.CardF[py2][px2]='○';
                    NB.Status();   
                    NB.Search(py1, px1);    //ヒント
                }
            
            }catch(NumberFormatException e){
                System.out.println("無効な入力です。");
                NB.Initialize();
                continue;
            }   //例外入力を検知してリトライ
        }
        NB.Result();
    }
}